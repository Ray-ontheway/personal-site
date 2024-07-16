package top.rayc.personalsite.user.service

import org.springframework.http.ResponseEntity
import top.rayc.personalsite.user.controller.vo.req.RoleCreateReq
import top.rayc.personalsite.user.controller.vo.req.RoleUpdateReq
import top.rayc.personalsite.user.controller.vo.req.UserCreateReq
import top.rayc.personalsite.user.controller.vo.req.UserUpdateReq
import top.rayc.personalsite.user.controller.vo.resp.RoleResp
import top.rayc.personalsite.user.controller.vo.resp.UserResp
import top.rayc.personalsite.user.security.authentication.UsernamePasswordAuthentication
import top.rayc.personalsite.utility.page.PageObject
import top.rayc.personalsite.utility.vo.BaseResult

interface IUserService {
    fun auth(username: String, password: String): UsernamePasswordAuthentication

    /**
     * 申请注册验证吗
     *
     * @param email
     * @return
     */
    fun applyOptCode(email: String): ResponseEntity<BaseResult<String>>
    fun createUser(createReq: UserCreateReq): ResponseEntity<BaseResult<UserResp>>

    /**
     * 用户更新个人信息
     *
     * @param updateReq
     * @return
     */
    fun updateUser(updateReq: UserUpdateReq): ResponseEntity<BaseResult<UserResp>>

    /**
     * 管理员更新角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    fun updateUserRole(userId: Long, roleIds: List<Long>): ResponseEntity<BaseResult<UserResp>>

    /**
     * 用户用验证码更新密码
     *
     * @param userId
     * @param password
     * @param optCode
     * @return
     */
    fun updatePassword(userId: Long, password: String, optCode: String): ResponseEntity<BaseResult<UserResp>>

    fun findUserResp(userId: Long): ResponseEntity<BaseResult<UserResp>>

    fun currentUserResp(): ResponseEntity<BaseResult<UserResp>>

    fun pageUser(pageIdx: Int, pageSize: Int): ResponseEntity<BaseResult<PageObject<UserResp>>>
}

interface IRoleService {
    fun create(createReq: RoleCreateReq): ResponseEntity<BaseResult<RoleResp>>
    fun update(updateReq: RoleUpdateReq): ResponseEntity<BaseResult<RoleResp>>
    fun findById(id: Long): ResponseEntity<BaseResult<RoleResp>>

    fun pageRole(pageIdx: Int, pageSize: Int): ResponseEntity<BaseResult<PageObject<RoleResp>>>
}