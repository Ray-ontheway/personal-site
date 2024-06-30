package top.rayc.personalsite.user.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import top.rayc.personalsite.user.entity.Permission
import top.rayc.personalsite.user.mapper.PermissionMapper

@Service
class PermissionService() : ServiceImpl<PermissionMapper, Permission>() {
}