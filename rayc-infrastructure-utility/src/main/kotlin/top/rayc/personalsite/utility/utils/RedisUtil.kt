package top.rayc.personalsite.utility.utils

//import cn.hutool.json.JSONUtil
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import top.rayc.personalsite.utility.cache.CacheData
import top.rayc.personalsite.utility.logger.LoggerDelegate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Component
class RedisUtil {
    val logger by LoggerDelegate()

    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate

    val mapper = jacksonObjectMapper()


    fun set(key: String, value: Any, time: Long, timeUnit: TimeUnit) {
        val data = CacheData(data = value, LocalDateTime.now().plusSeconds(timeUnit.toSeconds(time)))
//        logger.error("data json: ${JSONUtil.toJsonStr(data)}")
        val json = mapper.writeValueAsString(data)
        stringRedisTemplate.opsForValue().set(key, json, time, timeUnit)
    }

    @Suppress("UNCHECKED_CAST")
    fun <R> get(key: String, clazz: Class<R>): R? {
        val value = stringRedisTemplate.opsForValue().get(key) ?: return null
//        val data = JSONUtil.toBean(value, CacheData::class.java) ?: return null
        val data = mapper.readValue(value, CacheData::class.java)
        if (LocalDateTime.now().isAfter(data.expireAt)) {
            return null
        }
        return data.data as R
    }

}