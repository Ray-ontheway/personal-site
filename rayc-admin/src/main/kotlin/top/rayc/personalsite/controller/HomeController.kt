package top.rayc.personalsite.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import top.rayc.personalsite.utility.logger.LoggerDelegate

@RestController
class HomeController {

    val log by LoggerDelegate()

    @GetMapping("/hello")
    fun hello(): String {
        log.error("使用Slf4j + Kotlin 委托 实现一个日志工具简单化使用")

        return "hello world devtool"
    }

}