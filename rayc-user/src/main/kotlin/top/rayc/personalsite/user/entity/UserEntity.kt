package top.rayc.personalsite.user.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.Nulls
import java.time.LocalDateTime

@TableName("user_credential")
data class User (
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null,
    var userId: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var password: String? = null,
    var registeredAt: LocalDateTime? = null
)

@TableName("role")
data class Role(
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null,
    var roleCode: String? = null,
    var roleName: String? = null,
    var description: String? = null,
    var createAt: LocalDateTime? = null,
    var updateAt: LocalDateTime? = null
)

@TableName("permission")
data class Permission(
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null,
    var permissionCode: String? = null,
    var permissionName: String? = null,
    var description: String? = null,
    var createAt: LocalDateTime? = null,
    var updateAt: LocalDateTime? = null
)

@TableName("role_permission")
data class RolePermission (
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null,
    var roleId: Long? = null,
    var permissionId: Long? = null,
)

@TableName("user_role")
data class UserRole(
    var id: Long? = null,
    var userId: Long? = null,
    var roleId: Long? = null,
)
