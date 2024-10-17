package top.rayc.personalsite.article.controller.vo.req

data class ArticleTypeCreate (
    val name: String,
    val catKey: String,
    val description: String,
)
data class ArticleTypeUpdate (
    val id: Long,
    val uid: String,
    val name: String,
    val catKey: String,
    val description: String
)

data class ArticleTagCreate (
    val name: String,
    val catKey: String,
    val description: String,
)
data class ArticleTagUpdate (
    val id: Long,
    val uid: String,
    val name: String,
    val catKey: String,
    val description: String
)

data class ArticleCreate (
    val title: String,
    val summary: String,
    val content: String,
    val cover: String? = null,
    val typeId: Long,
    val tagIds: List<Long>,
    val isPublished: Boolean,
)

data class ArticleUpdate (
    val id: Long,
    val title: String,
    val summary: String,
    val content: String,
    val cover: String?,
    val typeId: Long,
    val tagIds: List<Long>,
    val isPublish: Boolean?,
)

@Deprecated("暂时不用")
data class ArticleCategoryCreate (
    val name: String,
    val summary: String,
    val coverUri: String? = null,
)

@Deprecated("暂时不用")
data class ArticleCategoryUpdate (
    val id: Long,
    val name: String,
    val summary: String,
    val coverUri: String? = null,
)
