package top.rayc.personalsite.article.converter

import org.mapstruct.Mapper
import top.rayc.personalsite.article.controller.vo.req.ArticleTagCreate
import top.rayc.personalsite.article.controller.vo.req.ArticleTagUpdate
import top.rayc.personalsite.article.controller.vo.req.ArticleTypeCreate
import top.rayc.personalsite.article.controller.vo.req.ArticleTypeUpdate
import top.rayc.personalsite.article.controller.vo.resp.ArticleTagResp
import top.rayc.personalsite.article.controller.vo.resp.ArticleTypeResp
import top.rayc.personalsite.article.entity.ArticleTag
import top.rayc.personalsite.article.entity.ArticleType

@Mapper(componentModel = "spring")
interface ArticleTypeConverter {
    fun convertFromCreateReq(createReq: ArticleTypeCreate): ArticleType
    fun convertFromUpdateReq(createReq: ArticleTypeUpdate): ArticleType

    fun convertToResp(articleType: ArticleType): ArticleTypeResp

    fun convertToRepsList(articleTypes: List<ArticleType>): List<ArticleTypeResp>
}

interface ArticleTagConverter {
    fun convertFromCreateReq(createReq: ArticleTagCreate): ArticleTag
    fun convertFromUpdateReq(createReq: ArticleTagUpdate): ArticleTag

    fun convertToResp(articleTag: ArticleTag): ArticleTagResp

    fun convertToRepsList(articleTags: List<ArticleTag>): List<ArticleTagResp>
}