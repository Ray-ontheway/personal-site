package top.rayc.personalsite.user.controller

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.rayc.personalsite.user.controller.vo.req.RoleCreateReq
import top.rayc.personalsite.user.controller.vo.req.RoleUpdateReq
import top.rayc.personalsite.user.controller.vo.resp.RoleResp
import top.rayc.personalsite.user.service.impl.RoleService
import top.rayc.personalsite.utility.vo.BaseResult

//@RestController
//@RequestMapping("/role")
class RoleController(
    @Autowired val roleService: RoleService
) {

//    @PostMapping
    fun createRole(@Valid @RequestBody createReq: RoleCreateReq): ResponseEntity<BaseResult<RoleResp>> {
        return roleService.create(createReq)
    }

//    @PutMapping
    fun updateRole(@Valid @RequestBody updateReq: RoleUpdateReq) = roleService.update(updateReq)

//    @GetMapping
    fun findById(@RequestParam id: Long) = roleService.findById(id)

//    @GetMapping("/page")
    fun page(@RequestParam pageIdx: Int, @RequestParam pageSize: Int) = roleService.pageRole(pageIdx, pageSize)

}