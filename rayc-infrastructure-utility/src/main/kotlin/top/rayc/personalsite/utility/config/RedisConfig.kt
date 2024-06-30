package top.rayc.personalsite.utility.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer

@Configuration
class RedisConfig {
    @Bean
    fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate: RedisTemplate<String, Any> = RedisTemplate();
        val jsonValueSerializer = GenericJackson2JsonRedisSerializer()

        redisTemplate.apply {
            connectionFactory = factory
            keySerializer = RedisSerializer.string()
            valueSerializer = RedisSerializer.string()
            hashKeySerializer = jsonValueSerializer
            hashValueSerializer = jsonValueSerializer
        }

        return redisTemplate
    }
}