package top.rayc.personalsite.user.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id
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
    fun applyOptCode(@RequestParam email: String) = userService.applyOptCode(email)

    @PostMapping
    fun createUser(@RequestBody createReq: UserCreateReq) = userService.createUser(createReq)

    @PutMapping
    fun updateUser(@RequestBody updateReq: UserUpdateReq) = userService.updateUser(updateReq)

    @GetMapping
    fun currentUserInfo() = userService.currentUserResp()

    // TODO 这里的用户获取逻辑，后期要把userId改成 创建用户时，生成的userId，不用数据库的自增长ID
    @GetMapping("/{userId}")
    fun findUserById(@PathVariable("userId") userId: Long) = userService.findUserResp(userId)

}