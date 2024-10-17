package top.rayc.personalsite.user.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import top.rayc.personalsite.user.entity.*

@Mapper
interface PermissionMapper: BaseMapper<Permission> {
}

@Mapper
interface RoleMapper: BaseMapper<Role> {
}

@Mapper
interface RolePermissionMapper: BaseMapper<RolePermission> {
}

@Mapper
interface UserMapper: BaseMapper<User> {
}

@Mapper
interface UserRoleMapper: BaseMapper<UserRole> {
}
