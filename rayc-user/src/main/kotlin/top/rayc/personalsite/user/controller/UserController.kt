package top.rayc.personalsite.user.controller

import jakarta.validation.constraints.Email
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.rayc.personalsite.user.controller.vo.req.UserCreateReq
import top.rayc.personalsite.user.controller.vo.req.UserUpdateReq
import top.rayc.personalsite.user.service.impl.UserService

@RestController
@RequestMapping("/user")
class UserController(
    @Autowired val userService: UserService
) {

    @GetMapping("/test")
    fun test(): String = "Spring Security 已经默认放开所有的url"

    @GetMapping("/opt/code")
    fun applyOptCode(@RequestParam @Email email: String) = userService.applyOptCode(email)

    @PostMapping
    fun createUser(@RequestBody createReq: UserCreateReq) = userService.createUser(createReq)

    @PutMapping
    fun updateUser(@RequestBody updateReq: UserUpdateReq) = userService.updateUser(updateReq)

    @PutMapping("/roles")
    fun updateUserRole(@RequestParam userId: Long, @RequestParam roleIds: List<Long>) =
        userService.updateUserRole(userId, roleIds)

    fun updatePassword(userId: Long, password: String, optCode: String) =
        userService.updatePassword(userId, password, optCode)

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    fun currentUserInfo() = userService.currentUserResp()

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/page")
    fun pageUsers(@RequestParam pageIdx: Int, @RequestParam pageSize:Int) =
        userService.pageUser(pageIdx, pageSize)



    // TODO 这里的用户获取逻辑，后期要把userId改成 创建用户时，生成的userId，不用数据库的自增长ID
    @GetMapping("/{userId}")
    fun findUserById(@PathVariable("userId") userId: Long) = userService.findUserResp(userId)

}