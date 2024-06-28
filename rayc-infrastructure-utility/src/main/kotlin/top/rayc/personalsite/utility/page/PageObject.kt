package top.rayc.personalsite.utility.page

data class PageObject<T> (
    val pageIdx: Int,
    val pageSize: Int,
    val total: Long,
    val records: List<T>
)
