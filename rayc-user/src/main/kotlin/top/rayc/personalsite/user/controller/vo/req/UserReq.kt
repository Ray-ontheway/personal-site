package top.rayc.personalsite.user.controller.vo.req

import java.time.LocalDateTime

data class UserCreateReq(
    var email: String,
    var password: String,
    var optCode: String,
    val roleIds: List<Long> = arrayListOf()
)

data class UserLoginReq (
    var username: String? = null,
    var password: String? = null,
) {
    // 通过Http读取JSON数据到对象中，会出现 "null", 把这种数据替换掉
    fun formatHttpValue() {
        if (username == "null") username = null
        if (password == "null") password = null
    }
}

data class UserUpdateReq(
    var id: Long,
    var userId: String,
    var phone: String?,
    var email: String,
)

data class RoleCreateReq(
    var roleName: String,
    var description: String? = null,
    var permIds: List<Long>
)

data class RoleUpdateReq(
    var roleId: Long,
    var roleName: String,
    var description: String?,
    var permIds: List<Long>
)
