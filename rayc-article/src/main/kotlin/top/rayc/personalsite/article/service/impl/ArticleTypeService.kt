package top.rayc.personalsite.article.service.impl

import cn.hutool.core.util.IdUtil
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.mapstruct.factory.Mappers
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import top.rayc.personalsite.article.controller.vo.req.ArticleTypeCreate
import top.rayc.personalsite.article.controller.vo.req.ArticleTypeUpdate
import top.rayc.personalsite.article.controller.vo.resp.ArticleTypeResp
import top.rayc.personalsite.article.converter.ArticleTypeConverter
import top.rayc.personalsite.article.entity.ArticleType
import top.rayc.personalsite.article.mapper.ArticleTypeMapper
import top.rayc.personalsite.article.service.IArticleTypeService
import top.rayc.personalsite.utility.logger.LoggerDelegate
import top.rayc.personalsite.utility.vo.BaseResult

@Service
class ArticleTypeService: ServiceImpl<ArticleTypeMapper, ArticleType>(), IArticleTypeService {
    val logger by LoggerDelegate()
    private val articleTypeConverter = Mappers.getMapper(ArticleTypeConverter::class.java)

    override fun createType(typeReq: ArticleTypeCreate): ResponseEntity<BaseResult<ArticleTypeResp>> {
        val type = articleTypeConverter.convertFromCreateReq(typeReq)
        type.uid = IdUtil.objectId()
        this.save(type)
        val fullType = this.getOne(KtQueryWrapper(ArticleType::class.java).eq(ArticleType::id, type.id))
        return ResponseEntity.ok(BaseResult.success("新增分类成功", articleTypeConverter.convertToResp(fullType!!)))
    }

    override fun updateType(typeUpdate: ArticleTypeUpdate): ResponseEntity<BaseResult<ArticleTypeResp>> {
        val type = articleTypeConverter.convertFromUpdateReq(typeUpdate)
        val typeWrapper = KtQueryWrapper(ArticleType::class.java).eq(ArticleType::id, type.id)
        this.update(type, typeWrapper)
        val fullType = this.getOne(typeWrapper)
        return ResponseEntity.ok(BaseResult.success("更新分类成功", articleTypeConverter.convertToResp(fullType!!)))
    }

    override fun deleteType(id: Long): ResponseEntity<BaseResult<String>> {
        this.removeById(id)
        return ResponseEntity.ok(BaseResult.success("删除分类成功"))
    }

    override fun allTypes(): ResponseEntity<BaseResult<List<ArticleTypeResp>>> {
        val types = this.list()
        return ResponseEntity.ok(BaseResult.success("查询分类成功", articleTypeConverter.convertToRepsList(types)))
    }
}