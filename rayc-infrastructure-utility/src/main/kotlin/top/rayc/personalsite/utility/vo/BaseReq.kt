package top.rayc.personalsite.utility.vo

abstract class BaseReq {
    abstract val traceId: String
    abstract val pageIdx: Int
    abstract val pageSize: Int
}

data class Req(
    val msg: String = "", override val traceId: String, override val pageIdx: Int, override val pageSize: Int
): BaseReq()
