package top.rayc.personalsite.article.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.rayc.personalsite.article.controller.vo.req.ArticleCreate
import top.rayc.personalsite.article.controller.vo.req.ArticleUpdate
import top.rayc.personalsite.article.controller.vo.resp.ArticleResp
import top.rayc.personalsite.article.service.impl.ArticleService
import top.rayc.personalsite.utility.page.PageObject
import top.rayc.personalsite.utility.vo.BaseResult

@RestController
@RequestMapping("/article")
class ArticleController(
    @Autowired val articleService: ArticleService
) {

    @PostMapping
    fun createArticle(@RequestBody @Validated articleCreate: ArticleCreate): ResponseEntity<BaseResult<ArticleResp>> {
        return articleService.createArticle(articleCreate)
    }

    @PutMapping
    fun updateArticle(@RequestBody @Validated articleUpdate: ArticleUpdate): ResponseEntity<BaseResult<ArticleResp>> {
        return articleService.updateArticle(articleUpdate)
    }

    @DeleteMapping
    fun deleteArticle(@PathVariable("id") id: Long): ResponseEntity<BaseResult<String>> {
        return articleService.deleteArticle(id)
    }

    @GetMapping
    fun articleDetail(@PathVariable("uid") uid: String): ResponseEntity<BaseResult<ArticleResp>> {
        return articleService.articleDetail(uid)
    }

    @GetMapping("/page")
    fun pageArticles(@RequestParam("pageIdx") pageIdx: Int,
                     @RequestParam("pageSize") pageSize: Int): ResponseEntity<BaseResult<PageObject<ArticleResp>>> {
        return articleService.pageArticles(pageIdx, pageSize)
    }

}