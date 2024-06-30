package top.rayc.personalsite.user.controller.vo.resp

import top.rayc.personalsite.user.entity.Permission
import java.time.LocalDateTime

data class RoleResp (
    var id: Long,
    var roleCode: String?,
    var roleName: String?,
    var description: String?,
    var createAt: LocalDateTime?,
    var updateAt: LocalDateTime?,
    var permissions: List<Permission>?
)

data class UserResp(
    var id: Long?,
    var userId: String?,
    var phone: String?,
    var email: String?,
    var registeredAt: LocalDateTime?,
    var roles: List<RoleResp>?,
)
