package top.rayc.personalsite.utility.utils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Component
class OptCodeUtil {
    @Autowired
    private lateinit var redisUtil: RedisUtil

    private val chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val charLen = chars.length
    private val random = Random(System.currentTimeMillis())

    fun generateCode(cacheKey: String): String {
        val sb = StringBuilder()
        for (i in 0 until 6) {
            sb.append(chars[random.nextInt(charLen)])
        }
        val code = sb.toString()
        redisUtil.set(cacheKey, code, 15 * 60, TimeUnit.SECONDS)
        return code
    }

    fun validateCode(cacheKey: String, code: String): Boolean {
        val cacheCode = redisUtil.get(cacheKey, String::class.java) ?: return false
        return cacheCode == code
    }
}