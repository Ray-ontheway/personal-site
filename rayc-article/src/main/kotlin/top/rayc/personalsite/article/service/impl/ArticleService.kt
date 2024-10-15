package top.rayc.personalsite.article.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import top.rayc.personalsite.article.controller.vo.req.ArticleCreate
import top.rayc.personalsite.article.controller.vo.req.ArticleUpdate
import top.rayc.personalsite.article.controller.vo.resp.ArticleResp
import top.rayc.personalsite.article.entity.Article
import top.rayc.personalsite.article.mapper.ArticleMapper
import top.rayc.personalsite.article.service.IArticleService
import top.rayc.personalsite.utility.page.PageObject
import top.rayc.personalsite.utility.vo.BaseResult

@Service
class ArticleService: ServiceImpl<ArticleMapper, Article>(), IArticleService {
    override fun createArticle(articleCreate: ArticleCreate): ResponseEntity<BaseResult<ArticleResp>> {
        TODO("Not yet implemented")
    }

    override fun updateArticle(articleUpdate: ArticleUpdate): ResponseEntity<BaseResult<ArticleResp>> {
        TODO("Not yet implemented")
    }

    override fun deleteArticle(id: Long): ResponseEntity<BaseResult<String>> {
        TODO("Not yet implemented")
    }

    override fun articleDetail(uid: String): ResponseEntity<BaseResult<ArticleResp>> {
        TODO("Not yet implemented")
    }

    override fun pageArticles(page: Int, size: Int): ResponseEntity<BaseResult<PageObject<ArticleResp>>> {
        TODO("Not yet implemented")
    }

}