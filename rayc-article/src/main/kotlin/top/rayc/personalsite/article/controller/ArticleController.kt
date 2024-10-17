package top.rayc.personalsite.article.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
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

    @DeleteMapping("/{id}")
    fun deleteArticle(@PathVariable("id") id: Long): ResponseEntity<BaseResult<String>> {
        return articleService.deleteArticle(id)
    }

    @GetMapping
    fun articleDetail(@PathVariable("uid") uid: String): ResponseEntity<BaseResult<ArticleResp>> {
        return articleService.articleDetail(uid)
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/page")
    fun pageArticles(@RequestParam("pageIdx") pageIdx: Int,
                     @RequestParam("pageSize") pageSize: Int): ResponseEntity<BaseResult<PageObject<ArticleResp>>> {
        return articleService.pageArticles(pageIdx, pageSize)
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/drafts")
    fun articleDrafts(): ResponseEntity<BaseResult<List<ArticleResp>>> {
        return articleService.articleDrafts()
    }

    @GetMapping("/essays")
    fun articleEssaysPage(@RequestParam("pageIdx") pageIdx: Long,
                          @RequestParam("pageSize") pageSize: Long): ResponseEntity<BaseResult<PageObject<ArticleResp>>> {
        return articleService.articleEssayPage(pageIdx, pageSize)
    }

}