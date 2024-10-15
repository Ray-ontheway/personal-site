package top.rayc.personalsite.article.service

import org.springframework.http.ResponseEntity
import top.rayc.personalsite.article.controller.vo.req.*
import top.rayc.personalsite.article.controller.vo.resp.ArticleResp
import top.rayc.personalsite.article.controller.vo.resp.ArticleTagResp
import top.rayc.personalsite.article.controller.vo.resp.ArticleTypeResp
import top.rayc.personalsite.article.entity.ArticleTag
import top.rayc.personalsite.article.entity.ArticleType
import top.rayc.personalsite.utility.page.PageObject
import top.rayc.personalsite.utility.vo.BaseResult

interface IArticleTagService {
    fun createTag(tagReq: ArticleTagCreate): ResponseEntity<BaseResult<ArticleTagResp>>

    fun updateTag(tagUpdate: ArticleTagUpdate): ResponseEntity<BaseResult<ArticleTagResp>>

    fun deleteTag(id: Long): ResponseEntity<BaseResult<String>>

    fun allTags(): ResponseEntity<BaseResult<List<ArticleTagResp>>>
}

interface IArticleTypeService {
    fun createType(typeReq: ArticleTypeCreate): ResponseEntity<BaseResult<ArticleTypeResp>>

    fun updateType(typeUpdate: ArticleTypeUpdate): ResponseEntity<BaseResult<ArticleTypeResp>>

    fun deleteType(id: Long): ResponseEntity<BaseResult<String>>

    fun allTypes(): ResponseEntity<BaseResult<List<ArticleTypeResp>>>
}

interface IArticleService {
    fun createArticle(articleCreate: ArticleCreate): ResponseEntity<BaseResult<ArticleResp>>

    fun updateArticle(articleUpdate: ArticleUpdate): ResponseEntity<BaseResult<ArticleResp>>

    fun deleteArticle(id: Long): ResponseEntity<BaseResult<String>>

    fun articleDetail(uid: String): ResponseEntity<BaseResult<ArticleResp>>

    fun pageArticles(page: Int, size: Int): ResponseEntity<BaseResult<PageObject<ArticleResp>>>
}