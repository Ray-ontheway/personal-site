package top.rayc.personalsite.utility.cache

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.time.LocalDateTime

// SpringBoot 中kotlin的null安全, 形同虚设
// 使用val 或者不为空
// 使用Jackson 可以使用 val + 不为空的类型; 使用jackson需要引入jackson-datatype-jsr310, 并添加 @JsonDeserialize 和 @JsonSerialize 注解
data class CacheData (val data: Any,
                      @JsonDeserialize(using = LocalDateTimeDeserializer::class)
                      @JsonSerialize(using = LocalDateTimeSerializer::class)
                      val expireAt: LocalDateTime)