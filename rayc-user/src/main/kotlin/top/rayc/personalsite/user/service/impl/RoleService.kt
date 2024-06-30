package top.rayc.personalsite.user.service.impl

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.fasterxml.jackson.databind.ser.Serializers.Base
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.rayc.personalsite.user.controller.vo.req.RoleCreateReq
import top.rayc.personalsite.user.controller.vo.req.RoleUpdateReq
import top.rayc.personalsite.user.controller.vo.resp.RoleResp
import top.rayc.personalsite.user.converter.RoleConverter
import top.rayc.personalsite.user.entity.Permission
import top.rayc.personalsite.user.entity.Role
import top.rayc.personalsite.user.entity.RolePermission
import top.rayc.personalsite.user.entity.UserRole
import top.rayc.personalsite.user.mapper.PermissionMapper
import top.rayc.personalsite.user.mapper.RoleMapper
import top.rayc.personalsite.user.mapper.RolePermissionMapper
import top.rayc.personalsite.user.mapper.UserRoleMapper
import top.rayc.personalsite.user.service.IRoleService
import top.rayc.personalsite.utility.exception.MessageCode
import top.rayc.personalsite.utility.logger.LoggerDelegate
import top.rayc.personalsite.utility.page.PageObject
import top.rayc.personalsite.utility.vo.BaseResult

@Service
class RoleService(
    @Autowired val permissionMapper: PermissionMapper,
    @Autowired val rolePermissionMapper: RolePermissionMapper,
    @Autowired val userRoleMapper: UserRoleMapper,
): ServiceImpl<RoleMapper, Role>(), IRoleService {
    val log by LoggerDelegate()
    private val roleConverter: RoleConverter = Mappers.getMapper(RoleConverter::class.java)

    @Transactional
    override fun create(createReq: RoleCreateReq): ResponseEntity<BaseResult<RoleResp>> {
        val storedUser = this.getOne(KtQueryWrapper(Role()).eq(Role::roleName, createReq.roleName))
        if (storedUser != null) {
            throw RuntimeException("角色已经存在")
        }
        val newRole = roleConverter.convertFromCreateReq(createReq)
        if (!this.save(newRole)) {
            throw RuntimeException("角色创建失败")
        }
        val insertResult = createReq.permIds.map { RolePermission(newRole.id, it) }
            .let { if (it.isNotEmpty()) rolePermissionMapper.insert(it) else 0 }

        return ResponseEntity.ok(BaseResult.success("角色创建成功", findRoleRespByRoleId(newRole.id!!)))
    }

    fun findRoleRespByRoleId(roleId: Long): RoleResp {
        val role = this.baseMapper.selectById(roleId)
        val permissions = findPermissionsById(roleId)
        val roleResp = roleConverter.convertToResp(role)
        roleResp.permissions = permissions
        return roleResp
    }

    fun findPermissionsById(roleId: Long): List<Permission> {
        return rolePermissionMapper.selectList(KtQueryWrapper(RolePermission()).eq(RolePermission::roleId, roleId))
            .map { it.permissionId }
            .let {
                permissionMapper.selectBatchIds(it)
            }
    }


    @Transactional
    override fun update(updateReq: RoleUpdateReq): ResponseEntity<BaseResult<RoleResp>> {
        val storedRole = this.getById(updateReq.roleId)
        val newRole = roleConverter.convertFromUpdateReq(updateReq)
        this.updateById(newRole)

        val storedRolePermissionIds = findPermissionsById(newRole.id!!).map { it.id }
        val deleteResult = storedRolePermissionIds
            .filter { !updateReq.permIds.contains(it) }
            .map { RolePermission(updateReq.roleId, it) }
            .let {
                if (it.isNotEmpty()) rolePermissionMapper.deleteByIds(it) else 0
            }
        val insertResult = updateReq.permIds
            .filter { !storedRolePermissionIds.contains(it) }
            .map { RolePermission(updateReq.roleId, it) }
            .let {
                if (it.isNotEmpty()) rolePermissionMapper.insert(it) else 0
            }
        return ResponseEntity.ok(BaseResult.success("更新成功", findRoleRespByRoleId(newRole.id!!)))
    }

    @Transactional
    override fun findById(id: Long): ResponseEntity<BaseResult<RoleResp>> {
        return ResponseEntity.ok(BaseResult.success(MessageCode.SUCCESS.msg, findRoleRespByRoleId(id)))
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun pageRole(pageIdx: Int, pageSize: Int): ResponseEntity<BaseResult<PageObject<RoleResp>>> {
        val page: IPage<Role> = Page(pageIdx.toLong(), pageSize.toLong())
        val pageResult = this.page(page)
        val result = PageObject(
                pageIdx,
            pageSize,
            total = pageResult.total,
            records = pageResult.records
                .map { roleConverter.convertToResp(it) }
                .map {
                    it.permissions = findPermissionsById(roleId = it.id)
                    it
                }.toList()
        )
        return ResponseEntity.ok(BaseResult.success("Success", result))
    }

    fun findRolesByUserId(userId: Long): List<Role> {
        return userRoleMapper
            .selectList(KtQueryWrapper(UserRole()).eq(UserRole::userId, userId))
            .map { it.roleId }
            .let { rids ->
                this.listByIds(rids)
            }
    }
}