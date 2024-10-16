package top.rayc.personalsite.article.converter

import org.mapstruct.Mapper
import top.rayc.personalsite.article.controller.vo.req.*
import top.rayc.personalsite.article.controller.vo.resp.ArticleResp
import top.rayc.personalsite.article.controller.vo.resp.ArticleTagResp
import top.rayc.personalsite.article.controller.vo.resp.ArticleTypeResp
import top.rayc.personalsite.article.entity.Article
import top.rayc.personalsite.article.entity.ArticleTag
import top.rayc.personalsite.article.entity.ArticleType

@Mapper(componentModel = "spring")
interface ArticleTypeConverter {
    fun convertFromCreateReq(createReq: ArticleTypeCreate): ArticleType
    fun convertFromUpdateReq(createReq: ArticleTypeUpdate): ArticleType

    fun convertToResp(articleType: ArticleType): ArticleTypeResp

    fun convertToRepsList(articleTypes: List<ArticleType>): List<ArticleTypeResp>
}

@Mapper(componentModel = "spring")
interface ArticleTagConverter {
    fun convertFromCreateReq(createReq: ArticleTagCreate): ArticleTag
    fun convertFromUpdateReq(createReq: ArticleTagUpdate): ArticleTag

    fun convertToResp(articleTag: ArticleTag): ArticleTagResp

    fun convertToRepsList(articleTags: List<ArticleTag>): List<ArticleTagResp>
}

@Mapper(componentModel = "spring")
interface ArticleConverter {
    fun convertFromCreateReq(createReq: ArticleCreate): Article
    fun convertFromUpdateReq(createReq: ArticleUpdate): Article

    fun convertToResp(article: Article): ArticleResp

    fun convertToRepsList(articles: List<Article>): List<ArticleResp>
}