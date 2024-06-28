package top.rayc.personalsite.utility.utils

import cn.hutool.json.JSONUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import top.rayc.personalsite.utility.cache.CacheData
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Component
class RedisUtil {
    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate

    fun set(key: String, value: Any, time: Long, timeUnit: TimeUnit) {
        val data = CacheData(data = value, LocalDateTime.now().plusSeconds(timeUnit.toSeconds(time)))
        stringRedisTemplate.opsForValue().set(key, data.toString(), time, timeUnit)
    }

    @Suppress("UNCHECKED_CAST")
    fun <R> get(ket: String, clazz: Class<R>): R? {
        val value = stringRedisTemplate.opsForValue().get(ket) ?: return null
        val data = JSONUtil.toBean(value, CacheData::class.java) ?: return null
        if (LocalDateTime.now().isAfter(data.expireAt)) {
            return null
        }
        return data.data as R
    }

}