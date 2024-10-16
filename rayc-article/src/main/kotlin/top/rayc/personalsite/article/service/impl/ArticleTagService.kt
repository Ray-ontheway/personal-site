package top.rayc.personalsite.article.service.impl

import cn.hutool.core.util.IdUtil
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.mapstruct.factory.Mappers
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import top.rayc.personalsite.article.controller.vo.req.ArticleTagCreate
import top.rayc.personalsite.article.controller.vo.req.ArticleTagUpdate
import top.rayc.personalsite.article.controller.vo.resp.ArticleTagResp
import top.rayc.personalsite.article.converter.ArticleTagConverter
import top.rayc.personalsite.article.entity.ArticleTag
import top.rayc.personalsite.article.mapper.ArticleTagMapper
import top.rayc.personalsite.article.service.IArticleTagService
import top.rayc.personalsite.utility.logger.LoggerDelegate
import top.rayc.personalsite.utility.vo.BaseResult

@Service
class ArticleTagService() : ServiceImpl<ArticleTagMapper, ArticleTag>(), IArticleTagService {
    val logger by LoggerDelegate()
    private val articleConverter = Mappers.getMapper(ArticleTagConverter::class.java)

    override fun createTag(tagReq: ArticleTagCreate): ResponseEntity<BaseResult<ArticleTagResp>> {
        val tag = articleConverter.convertFromCreateReq(tagReq)
        tag.uid = IdUtil.objectId()
        this.save(tag)
        val fullTag = this.getOne(KtQueryWrapper(ArticleTag::class.java).eq(ArticleTag::id, tag.id))
        return ResponseEntity.ok(BaseResult.success("新增成功", articleConverter.convertToResp(fullTag!!)))
    }

    override fun updateTag(tagUpdate: ArticleTagUpdate): ResponseEntity<BaseResult<ArticleTagResp>> {
        val tag = articleConverter.convertFromUpdateReq(tagUpdate)
        val tagWrapper = KtQueryWrapper(ArticleTag::class.java).eq(ArticleTag::id, tag.id)
        this.update(tag, tagWrapper)
        val fullTag = this.getOne(tagWrapper)
        return ResponseEntity.ok(BaseResult.success("更新成功", articleConverter.convertToResp(fullTag!!)))
    }

    override fun deleteTag(id: Long): ResponseEntity<BaseResult<String>> {
        this.removeById(id)
        return ResponseEntity.ok(BaseResult.success("删除成功"))
    }

    override fun allTags(): ResponseEntity<BaseResult<List<ArticleTagResp>>> {
        val tags = this.list()
        return ResponseEntity.ok(BaseResult.success("查询成功", articleConverter.convertToRepsList(tags)))
    }
}