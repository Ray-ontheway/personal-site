package top.rayc.personalsite

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement
import top.rayc.personalsite.utility.logger.LoggerDelegate

@EnableTransactionManagement
@SpringBootApplication
class RaycApplication

fun main(args: Array<String>) {

    runApplication<RaycApplication>(*args)
}