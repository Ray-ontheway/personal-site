package top.rayc.personalsite.article.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.FieldStrategy
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.time.LocalDateTime

@TableName("article_type")
data class ArticleType(
    @TableId(type = IdType.AUTO)
    var id: Long? = null,
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    var uid: String? = null,
    var name: String? = null,
    var catKey: String? = null,
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    var description: String? = null,
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    var visitedCount: Int? = null,
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    var isDeleted: Boolean? = null,
    @TableField(fill = FieldFill.INSERT)
    var createAt: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updateAt: LocalDateTime? = null,
)

@TableName("article_tag")
data class ArticleTag(
    @TableId(type = IdType.AUTO)
    var id: Long? = null,
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    var uid: String? = null,
    var name: String? = null,
    var catKey: String? = null,
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    var description: String? = null,
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    var visitedCount: Int? = null,
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    var isDeleted: Boolean? = null,
    @TableField(fill = FieldFill.INSERT)
    var createAt: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updateAt: LocalDateTime? = null
)

@TableName("article")
data class Article(
    @TableId(type = IdType.AUTO)
    var id: Long? = null,
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    var uid: String? = null,
    var title: String? = null,
    var summary: String? = null,
    var content: String? = null,
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    var typeId: Long? = null,
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    var tags: List<String>? = null,
    var categoryId: Long? = null,
    var publishAt: LocalDateTime? = null,
    var createBy: Long? = null,
    var visitedCount: Int = 0,
    var isDeleted: Boolean = false,
    @TableField(fill = FieldFill.INSERT)
    var createAt: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updateAt: LocalDateTime? = null,
)

@TableName("article_category")
data class ArticleCategory (
    @TableId(type = IdType.AUTO)
    var id: Long? = null,
    var uid: String? = null,
    var name: String? = null,
    var summary: String? = null,
    var coverUri: String? = null,
    var createBy: Long? = null,
    var createAt: LocalDateTime?,
    var updateAt: LocalDateTime?,
    var visitedCount: Int = 0,
    var articleCount: Int = 0,
    var isDeleted: Boolean = false
)

@TableName("article_type_link")
data class ArticleTypeLink(
    @TableId(type = IdType.AUTO)
    var id: Long? = null,
    var typeId: Long? = null,
    var articleId: Long? = null
)
@TableName("article_tag_link")
data class ArticleTagLink(
    @TableId(type = IdType.AUTO)
    var id: Long? = null,
    var tagId: Long? = null,
    var articleId: Long? = null
)
@TableName("article_category_link")
data class ArticleCategoryLink(
    @TableId(type = IdType.AUTO)
    var id: Long? = null,
    var categoryId: Long? = null,
    var articleId: Long? = null
)