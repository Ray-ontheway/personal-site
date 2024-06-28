package top.rayc.personalsite.utility.cache

import java.time.LocalDateTime

data class CacheData (val data: Any, val expireAt: LocalDateTime)