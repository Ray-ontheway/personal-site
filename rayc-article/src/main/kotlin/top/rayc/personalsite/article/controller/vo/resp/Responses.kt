package top.rayc.personalsite.article.controller.vo.resp

import top.rayc.personalsite.article.entity.ArticleType
import top.rayc.personalsite.user.controller.vo.resp.UserResp
import top.rayc.personalsite.user.entity.User
import java.time.LocalDateTime


data class ArticleResp (
    val id: Long,
    val title: String,
    val summary: String,
    val content: String,
    val type: ArticleType,
    val tags: List<String>,
    val categoryId: Long? = null,
    val isPublish: Boolean = false,
    val createBy: User
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