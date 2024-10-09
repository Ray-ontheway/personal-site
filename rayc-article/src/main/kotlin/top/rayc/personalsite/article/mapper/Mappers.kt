package top.rayc.personalsite.article.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import top.rayc.personalsite.article.entity.*

@Mapper
interface ArticleTypeMapper : BaseMapper<ArticleType> {}

@Mapper
interface ArticleTagMapper : BaseMapper<ArticleTag> {}

@Mapper
interface ArticleMapper : BaseMapper<Article> {}

@Mapper
interface ArticleTypeLinkMapper : BaseMapper<ArticleTypeLink> {}

@Mapper
interface ArticleTagLinkMapper : BaseMapper<ArticleTagLink> {}

@Mapper
interface ArticleCategoryLinkMapper : BaseMapper<ArticleCategoryLink> {}
