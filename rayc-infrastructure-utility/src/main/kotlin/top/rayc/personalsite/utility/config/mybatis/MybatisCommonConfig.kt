package top.rayc.personalsite.utility.config.mybatis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MybatisCommonConfig {

    @Bean
    fun dateTimeMetaObjectHandler(): DateTimeMetaObjectHandler {
        return DateTimeMetaObjectHandler()
    }

}