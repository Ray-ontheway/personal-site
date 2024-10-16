package top.rayc.personalsite.article.service.impl

import cn.hutool.core.util.IdUtil
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.rayc.personalsite.article.controller.vo.req.ArticleCreate
import top.rayc.personalsite.article.controller.vo.req.ArticleUpdate
import top.rayc.personalsite.article.controller.vo.resp.ArticleResp
import top.rayc.personalsite.article.controller.vo.resp.ArticleTagResp
import top.rayc.personalsite.article.converter.ArticleConverter
import top.rayc.personalsite.article.converter.ArticleTagConverter
import top.rayc.personalsite.article.converter.ArticleTypeConverter
import top.rayc.personalsite.article.entity.Article
import top.rayc.personalsite.article.entity.ArticleTagLink
import top.rayc.personalsite.article.mapper.ArticleMapper
import top.rayc.personalsite.article.service.IArticleService
import top.rayc.personalsite.user.converter.UserConverter
import top.rayc.personalsite.user.service.impl.UserService
import top.rayc.personalsite.utility.page.PageObject
import top.rayc.personalsite.utility.vo.BaseResult

@Service
class ArticleService(
    @Autowired val articleTagLinkService: ArticleTagLinkService,
    @Autowired val articleTagService: ArticleTagService,
    @Autowired val articleTypeService: ArticleTypeService,
    @Autowired val userService: UserService,
): ServiceImpl<ArticleMapper, Article>(), IArticleService {

    private val articleConverter = Mappers.getMapper(ArticleConverter::class.java)
    private val articleTagConverter = Mappers.getMapper(ArticleTagConverter::class.java)
    private val articleTypeConverter = Mappers.getMapper(ArticleTypeConverter::class.java)
    private val userConverter = Mappers.getMapper(UserConverter::class.java)

    private fun transformArticleToResp(article: Article): ArticleResp {
        log.error("查询文章: $article")
        val tagIds = articleTagLinkService.list(KtQueryWrapper(ArticleTagLink::class.java).eq(ArticleTagLink::articleId, article.id)).map { it.tagId }
        val tags = articleTagService.listByIds(tagIds).map { articleTagConverter.convertToResp(it) }
        log.error("查询tag文章成功: $tags")

        val type = articleTypeService.getById(article.typeId)?.let { articleTypeConverter.convertToResp(it) }
        log.error("查询type文章成功: $type")

        val articleResp = articleConverter.convertToResp(article)
        log.error("查询article文章成功: $articleResp")
        articleResp.tags = tags
        articleResp.type = type
        articleResp.author = userService.getById(article.createBy)?.let { userConverter.convertToResp(it) }
        log.error("查询article文章成功")

        return articleResp
    }

    private fun findArticleById(id: Long): ArticleResp {
        val article = this.getById(id) ?: throw RuntimeException("文章不存在")
        log.error("查询文章成功: $article")
        return transformArticleToResp(article)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun createArticle(articleCreate: ArticleCreate): ResponseEntity<BaseResult<ArticleResp>> {
        val article = articleConverter.convertFromCreateReq(articleCreate)
        article.uid = IdUtil.objectId()
        val currentUser = userService.curUser().let { userConverter.convertToResp(it) }
        article.createBy = currentUser.id
        if (!this.save(article)) {
            throw RuntimeException("新增文章失败")
        }
        val articleTags = articleCreate.tagIds.map { tagId -> ArticleTagLink(tagId = tagId, articleId = article.id) }
        if (!articleTagLinkService.saveBatch(articleTags)) {
            throw RuntimeException("新增文章失败")
        }
        log.error("新增文章成功")
        return ResponseEntity.ok().body(BaseResult.success("新增文章成功", findArticleById(article.id!!)))
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateArticle(articleUpdate: ArticleUpdate): ResponseEntity<BaseResult<ArticleResp>> {
        log.error("更新文章: $articleUpdate")
        val article = articleConverter.convertFromUpdateReq(articleUpdate)
        log.error("更新文章: $article")
        this.updateById(article)
        val existTagsLink = articleTagLinkService.list(KtQueryWrapper(ArticleTagLink::class.java).eq(ArticleTagLink::articleId, article.id))
        val waitToDeleteTagLink = existTagsLink.filter { !articleUpdate.tagIds.contains(it.tagId) }
        val waitToAddTagLink = articleUpdate.tagIds.filter { tagId -> existTagsLink.none { it.tagId == tagId } }
        // TODO 后续有时间，优化一下
        if (waitToDeleteTagLink.isNotEmpty()) {
            articleTagLinkService.remove(KtQueryWrapper(ArticleTagLink::class.java).`in`(ArticleTagLink::tagId, waitToDeleteTagLink.map { it.tagId }))
        }
        if (waitToAddTagLink.isNotEmpty()) {
            val articleTags = waitToAddTagLink.map { tagId -> ArticleTagLink(tagId = tagId, articleId = article.id) }
            articleTagLinkService.saveBatch(articleTags)
        }
        return ResponseEntity.ok().body(BaseResult.success("更新成功", findArticleById(article.id!!)))
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteArticle(id: Long): ResponseEntity<BaseResult<String>> {
        log.error("删除文章: $id")
        this.removeById(id)
        articleTagLinkService.remove(KtQueryWrapper(ArticleTagLink::class.java).eq(ArticleTagLink::articleId, id))
        return ResponseEntity.ok().body(BaseResult.success("删除成功"))
    }

    override fun articleDetail(uid: String): ResponseEntity<BaseResult<ArticleResp>> {
        val article = this.getOne(KtQueryWrapper(Article::class.java).eq(Article::uid, uid)) ?: throw RuntimeException("文章不存在")
        return ResponseEntity.ok(BaseResult.success("查询成功", transformArticleToResp(article)))
    }

    override fun pageArticles(page: Int, size: Int): ResponseEntity<BaseResult<PageObject<ArticleResp>>> {
        val articlePage = this.page(Page<Article>(page.toLong(), size.toLong()))
        val articleResps = articlePage.records.map { transformArticleToResp(it) }

        return ResponseEntity.ok(BaseResult.success("查询成功", PageObject(page, size, articlePage.total, articleResps)))
    }

}