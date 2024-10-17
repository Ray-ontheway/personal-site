package top.rayc.personalsite.utility.exception

import org.slf4j.LoggerFactory

class MessageCode (
    val status: Int,
    val msg: String,
) {

    private val logger = LoggerFactory.getLogger(MessageCode::class.java)

    companion object {
        val SUCCESS = MessageCode(200, "success")
        val UN_KNOWN = MessageCode(10001, "unknown error")
        val SYSTEM_ERROR = MessageCode(10002, "system error")
        val COMPONENT_ERROR = MessageCode(10003, "component error")
        val RUNTIME_ERROR = MessageCode(10004, "runtime error")
        val REMOTE_ERROR = MessageCode(10005, "remote error")
        val USER_EXISTED = MessageCode(10006, "user existed")
    }

}