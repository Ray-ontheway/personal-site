package top.rayc.personalsite.utility.utils

import cn.hutool.core.util.IdUtil

object IdUtils {
    fun generateUserID(): String = "rayc_${IdUtil.nanoId(8)}"
}