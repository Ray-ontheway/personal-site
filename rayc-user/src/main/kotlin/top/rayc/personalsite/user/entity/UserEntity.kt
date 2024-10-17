package top.rayc.personalsite.user.entity

import com.baomidou.mybatisplus.annotation.*
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
    var id: Long? = 0,
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    var roleCode: String,
    var roleName: String,
    var description: String,
    @TableField(fill = FieldFill.INSERT)
    var createAt: LocalDateTime,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updateAt: LocalDateTime
)

@TableName("permission")
data class Permission(
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = 0,
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    var permissionCode: String?,
    var permissionName: String?,
    var description: String?,
    @TableField(fill = FieldFill.INSERT)
    var createAt: LocalDateTime?,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updateAt: LocalDateTime?
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
