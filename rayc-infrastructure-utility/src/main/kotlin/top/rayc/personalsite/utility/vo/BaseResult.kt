package top.rayc.personalsite.utility.vo

import org.slf4j.MDC
import top.rayc.personalsite.utility.exception.MessageCode
import java.io.Serializable

class BaseResult<T> (
    val code: Int,
    val message: String,
    val traceId: String,
    val data: T? = null,
): Serializable {
    companion object {
        private const val TRACE_LOG_ID = "traceId"

        fun <T> success(msg: String?, data: T?): BaseResult<T> {
            return BaseResult(MessageCode.SUCCESS.status, msg ?: MessageCode.SUCCESS.msg, MDC.get(TRACE_LOG_ID), data)
        }

        fun <T> error(status: Int?, msg: String?, data: T?): BaseResult<T> {
            return BaseResult(status ?: MessageCode.UN_KNOWN.status, msg ?: MessageCode.UN_KNOWN.msg, MDC.get(TRACE_LOG_ID), data)
        }
    }
}