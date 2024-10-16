package top.rayc.personalsite.article.controller.vo.resp

import top.rayc.personalsite.article.entity.ArticleTag
import top.rayc.personalsite.article.entity.ArticleType
import top.rayc.personalsite.user.controller.vo.resp.UserResp
import top.rayc.personalsite.user.entity.User
import java.time.LocalDateTime


data class ArticleResp (
    val id: Long,
    val uid: String,
    val title: String,
    val summary: String,
    val content: String,
    val cover: String?,
    var type: ArticleTypeResp?,
    var tags: List<ArticleTagResp>,
    val visitedCount: Int = 0,
    val isPublish: Boolean = false,
    var author: UserResp?,
    val updateAt: LocalDateTime,
)

data class ArticleCategoryResp (
    val id: Long,
    val uid: String,
    val name: String,
    val summary: String,
    val coverUri: String? = null,
    val createAt: LocalDateTime? = null,
    val visitedCount: Int = 0,
    val articleCount: Int = 0,
    val createBy: UserResp
)

data class ArticleTagResp (
    val id: Long,
    val uid: String,
    val name: String,
    val catKey: String,
    val description: String,
    val visitedCount: Int = 0,
    val createAt: LocalDateTime? = null,
    val updateAt: LocalDateTime? = null,
)

data class ArticleTypeResp (
    val id: Long,
    val uid: String,
    val name: String,
    val catKey: String,
    val description: String? = null,
    val visitedCount: Int,
    val createAt: LocalDateTime,
    val updateAt: LocalDateTime,
)