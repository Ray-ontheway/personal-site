package top.rayc.personalsite.article.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import top.rayc.personalsite.article.entity.ArticleTagLink
import top.rayc.personalsite.article.mapper.ArticleTagLinkMapper

@Service
class ArticleTagLinkService: ServiceImpl<ArticleTagLinkMapper, ArticleTagLink>() {
}