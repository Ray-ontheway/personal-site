package top.rayc.personalsite.utility.vo

import org.slf4j.MDC
import top.rayc.personalsite.utility.exception.MessageCode
import top.rayc.personalsite.utility.logger.LoggerDelegate
import java.io.Serializable

class BaseResult<T> (
    val code: Int,
    val message: String,
    val traceId: String? = null,
    val data: T? = null,
): Serializable {
    companion object {
        private const val TRACE_LOG_ID = "traceId"

        val logger by LoggerDelegate()

        fun <T> success(msg: String = MessageCode.SUCCESS.msg, data: T? = null): BaseResult<T> {
            logger.error("msg: $msg")
            logger.error("status: ${MessageCode.SUCCESS.status}")
            logger.error("mdc: ${MDC.get(TRACE_LOG_ID)}")
            logger.error("data: {}", data)
            return BaseResult(MessageCode.SUCCESS.status, msg, MDC.get(TRACE_LOG_ID), data)
        }

        fun <T> error(msgCode: MessageCode, data: T? = null): BaseResult<T> {
            return BaseResult(msgCode.status, msgCode.msg, MDC.get(TRACE_LOG_ID), data)
        }

        fun <T> error(status: Int = MessageCode.SYSTEM_ERROR.status, msg: String = MessageCode.UN_KNOWN.msg, data: T? = null): BaseResult<T> {
            return BaseResult(status, msg, MDC.get(TRACE_LOG_ID), data)
        }

        fun <T> error(msg: String, data: T? = null): BaseResult<T> {
            return BaseResult(MessageCode.SYSTEM_ERROR.status, msg, MDC.get(TRACE_LOG_ID), data)
        }

    }
}