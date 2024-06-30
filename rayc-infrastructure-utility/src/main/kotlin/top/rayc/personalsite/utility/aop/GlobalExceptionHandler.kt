package top.rayc.personalsite.utility.aop

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import top.rayc.personalsite.utility.exception.MessageCode
import top.rayc.personalsite.utility.logger.LoggerDelegate
import top.rayc.personalsite.utility.vo.BaseResult

@RestControllerAdvice
class GlobalExceptionHandler {

    val log by LoggerDelegate()

    /**
     * 未定义的错误处理
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(Exception::class)
    fun exceptionHandler(req: HttpServletRequest, e: Exception): BaseResult<Any> {
        return BaseResult.error(MessageCode.UN_KNOWN)
    }
}