package top.rayc.personalsite.user.service.impl

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.rayc.personalsite.user.constant.CachePrefixEnum
import top.rayc.personalsite.user.controller.vo.req.UserCreateReq
import top.rayc.personalsite.user.controller.vo.req.UserUpdateReq
import top.rayc.personalsite.user.controller.vo.resp.RoleResp
import top.rayc.personalsite.user.controller.vo.resp.UserResp
import top.rayc.personalsite.user.converter.UserConverter
import top.rayc.personalsite.user.entity.User
import top.rayc.personalsite.user.entity.UserRole
import top.rayc.personalsite.user.mapper.UserMapper
import top.rayc.personalsite.user.mapper.UserRoleMapper
import top.rayc.personalsite.user.security.authentication.UsernamePasswordAuthentication
import top.rayc.personalsite.user.service.IUserService
import top.rayc.personalsite.utility.email.MailHelper
import top.rayc.personalsite.utility.logger.LoggerDelegate
import top.rayc.personalsite.utility.page.PageObject
import top.rayc.personalsite.utility.utils.IdUtils
import top.rayc.personalsite.utility.utils.OptCodeUtil
import top.rayc.personalsite.utility.vo.BaseResult

@Service
class UserService(
    @Autowired val passwordEncoder: PasswordEncoder,
    @Autowired val optUtil: OptCodeUtil,
    @Autowired val emailHelper: MailHelper,
    @Autowired val userRoleMapper: UserRoleMapper,
    @Autowired val roleService: RoleService,
): ServiceImpl<UserMapper, User>(), IUserService {
    val logger by LoggerDelegate()
    private val userConverter: UserConverter = Mappers.getMapper(UserConverter::class.java)

    override fun auth(username: String, password: String): UsernamePasswordAuthentication {
        logger.error("auth: {}, {}", username, password)
        val storeUser = this.getOne(KtQueryWrapper(User()).eq(User::email, username))
        val encodePassword = passwordEncoder.encode(password)
        if (storeUser == null) {
            logger.error("storeUser is null")
        }
        if (storeUser == null || !passwordEncoder.matches(password, storeUser.password)) {
            throw BadCredentialsException("用户名或密码错误")
        }
        return UsernamePasswordAuthentication(username, password)
    }

    private val EMAIL_REGISTER_TEMPLATE = """
        您好，您正在使用 %s 邮箱，注册 Rayc 账号.
        如果不是本人操作，请忽略。
        验证码: %s
        ---
        www.rayc.top - 作为一个程序员，在做喜欢的事情的路上
    """.trimIndent()

    override fun applyOptCode(email: String): ResponseEntity<BaseResult<String>> {
        val storedUser = this.getOne(KtQueryWrapper(User()).eq(User::email, email))
        if (storedUser != null) {
            throw RuntimeException("该邮箱已有账号");
        }
        val optCode = optUtil.generateCode(CachePrefixEnum.USER_OPT_CODE_PREFIX.value + email)
        if (!emailHelper.send("账号注册", String.format(EMAIL_REGISTER_TEMPLATE, email, optCode), arrayOf(email))) {
            throw RuntimeException("验证码发送失败")
        }
        return ResponseEntity.ok(BaseResult.success("验证码发送成功"))
    }

    @Transactional
    override fun createUser(createReq: UserCreateReq): ResponseEntity<BaseResult<UserResp>> {
        logger.error("create user -- 1: {}", createReq)
        if (!optUtil.validateCode(CachePrefixEnum.USER_OPT_CODE_PREFIX.value + createReq.email, createReq.optCode)) {
            throw RuntimeException("验证码过期")
        }
        val newUser = userConverter.convertFromCreateReq(createReq)
        logger.error("create user -- 2: {}", createReq)

        newUser.userId = IdUtils.generateUserID()
        this.save(newUser)

        // TODO 用户默认的角色设置, 后续需要优化
        val roleIds = if (createReq.roleIds.isEmpty()) {
            createReq.roleIds
        } else listOf(3L)
        val userResp = updateUserRoles(newUser.id!!, roleIds)
        return ResponseEntity.ok(BaseResult.success("注册成功", userResp))
    }

    private fun updateUserRoles(userId: Long, roleIds: List<Long>): UserResp {
        val result = roleIds.map { UserRole(userId, it) }
            .let {
                userRoleMapper.insert(it)
            }
        return findUserRespById(userId)
    }



    private fun findUserRespById(userId: Long): UserResp {
        val user = this.getById(userId).let { userConverter.convertToResp(it) }
        user.roles = findRoleRespsByUserId(userId)
        return user
    }
    private fun findRoleRespsByUserId(userId: Long): List<RoleResp> {
        return userRoleMapper.selectList(KtQueryWrapper(UserRole()).eq(UserRole::userId, userId))
            .map { it.roleId }
            .map { roleService.findRoleRespByRoleId(it!!) }
    }

    /**
     * 如果 属性为null mybatis plus 会忽略掉该属性对应字段的更新
     *
     * @param updateReq
     * @return
     */
    @Transactional
    override fun updateUser(updateReq: UserUpdateReq): ResponseEntity<BaseResult<UserResp>> {
        val newUser = userConverter.convertFromUpdateReq(updateReq)
        if (this.updateById(newUser)) {
            return ResponseEntity.ok(BaseResult.success("更新成功", findUserRespById(newUser.id!!)))
        }
        return ResponseEntity.ok(BaseResult.error("更新失败"))
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateUserRole(userId: Long, roleIds: List<Long>): ResponseEntity<BaseResult<UserResp>> {
        val storeUrs = userRoleMapper.selectList(KtQueryWrapper(UserRole()).eq(UserRole::userId, userId))
        val waitToInsert = roleIds.toMutableList()
        val waitToDelete = mutableListOf<Long>()
        storeUrs.forEach { storeUr ->
            if (!roleIds.contains(storeUr.roleId)) {
                waitToDelete.add(storeUr.roleId!!)
            }
            waitToInsert.remove(storeUr.roleId)
        }
        if (waitToInsert.isNotEmpty()) {
            userRoleMapper.insert(waitToInsert.map { UserRole(userId = userId, roleId = it) })
        }
        if (waitToDelete.isNotEmpty()) {
            userRoleMapper.delete(KtQueryWrapper(UserRole()).eq(UserRole::userId, userId).`in`(UserRole::roleId, waitToDelete))
        }
        return ResponseEntity.ok(BaseResult.success("更新成功", findUserRespById(userId)))
    }

    override fun updatePassword(userId: Long, password: String, optCode: String): ResponseEntity<BaseResult<UserResp>> {
        TODO("Not yet implemented")
    }

    override fun findUserResp(userId: Long): ResponseEntity<BaseResult<UserResp>> {
        return ResponseEntity.ok(BaseResult.success("查询成功", findUserRespById(userId)))
    }

    fun findUserResp(email: String): UserResp {
        val userResp = this.getOne(KtQueryWrapper(User()).eq(User::email, email))
            .let { userConverter.convertToResp(it) }
            .let {
                it.roles = findRoleRespsByUserId(it.id!!)
                it
            }
        return userResp
    }

    fun curUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        val email = authentication.principal as String
        logger.error("curUser: {}", email)
        return this.getOne(KtQueryWrapper(User()).eq(User::email, email))
    }

    override fun currentUserResp(): ResponseEntity<BaseResult<UserResp>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val email = authentication.principal as String
        val userResp  = findUserResp(email)
        return ResponseEntity.ok(BaseResult.success("查询成功", userResp))
    }

    override fun pageUser(pageIdx: Int, pageSize: Int): ResponseEntity<BaseResult<PageObject<UserResp>>> {
        val page: IPage<User> = Page(pageIdx.toLong(), pageSize.toLong())
        val pageResult = this.page(page)
        val result = PageObject(pageIdx, pageSize, pageResult.total,
            pageResult.records
                .map {
                    findUserRespById(it.id!!)
                })
        return ResponseEntity.ok(BaseResult.success("查询成功", result))
    }

}