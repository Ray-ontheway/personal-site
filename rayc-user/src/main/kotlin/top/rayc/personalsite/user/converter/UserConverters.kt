package top.rayc.personalsite.user.converter

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import top.rayc.personalsite.user.controller.vo.req.RoleCreateReq
import top.rayc.personalsite.user.controller.vo.req.RoleUpdateReq
import top.rayc.personalsite.user.controller.vo.req.UserCreateReq
import top.rayc.personalsite.user.controller.vo.req.UserUpdateReq
import top.rayc.personalsite.user.controller.vo.resp.RoleResp
import top.rayc.personalsite.user.controller.vo.resp.UserResp
import top.rayc.personalsite.user.entity.Role
import top.rayc.personalsite.user.entity.User

@Mapper(componentModel = "spring")
interface RoleConverter {
//    companion object {
//        val INSTANCE: RoleConverter
//            get() = Mappers.getMapper(RoleConverter::class.java)
//    }

    fun convertFromCreateReq(createReq: RoleCreateReq): Role
    fun convertFromUpdateReq(createReq: RoleUpdateReq): Role

    fun convertToResp(role: Role): RoleResp

    fun convertToRepsList(roles: List<Role>): List<UserResp>
}

@Mapper(componentModel = "spring")
interface UserConverter {
//    companion object {
//        val INSTANCE: UserConverter
//            get() = Mappers.getMapper(UserConverter::class.java)
//    }

    fun convertFromCreateReq(createReq: UserCreateReq): User
    fun convertFromUpdateReq(updateReq: UserUpdateReq): User

    fun convertToResp(user: User): UserResp
    fun convertToRespList(users: List<User>): List<UserResp>
}