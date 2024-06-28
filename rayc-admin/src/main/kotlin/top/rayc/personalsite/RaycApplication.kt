package top.rayc.personalsite

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RaycApplication

fun main(args: Array<String>) {
    runApplication<RaycApplication>(*args)
}