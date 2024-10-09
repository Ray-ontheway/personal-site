package top.rayc.personalsite.article.controller.vo.req

data class ArticleTypeCreate (
    val name: String,
    val description: String
)

data class ArticleTypeUpdate (
    val id: Long,
    val name: String,
    val description: String
)

data class ArticleCreate (
    val title: String,
    val summary: String,
    val content: String,
    val typeId: Long,
    val tags: List<String>,
    val tagIds: List<Long>,
    val categoryId: Long?, 
    var isPublish: Boolean = false
)

data class ArticleUpdate (
    val id: Long,
    val title: String,
    val summary: String,
    val content: String,
    val typeId: Long,
    val tags: List<String>,
    val tagIds: List<Long>,
    val categoryId: Long?,
    var isPublish: Boolean = false
)

data class ArticleCategoryCreate (
    val name: String,
    val summary: String,
    val coverUri: String? = null,
)

data class ArticleCategoryUpdate (
    val id: Long,
    val name: String,
    val summary: String,
    val coverUri: String? = null,
)
