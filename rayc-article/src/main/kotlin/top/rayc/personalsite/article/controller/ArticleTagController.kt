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
import org.springframework.web.bind.annotation.RestController
import top.rayc.personalsite.article.controller.vo.req.ArticleTagCreate
import top.rayc.personalsite.article.controller.vo.req.ArticleTagUpdate
import top.rayc.personalsite.article.controller.vo.resp.ArticleTagResp
import top.rayc.personalsite.article.service.impl.ArticleTagService
import top.rayc.personalsite.utility.vo.BaseResult

@RestController
@RequestMapping("/article/tag")
class ArticleTagController(
    @Autowired val articleTagService: ArticleTagService
) {

    @PostMapping
    fun createTag(@Validated @RequestBody tagReq: ArticleTagCreate): ResponseEntity<BaseResult<ArticleTagResp>> {
        return articleTagService.createTag(tagReq)
    }

    @PutMapping
    fun updateTag(@Validated @RequestBody tagUpdate: ArticleTagUpdate): ResponseEntity<BaseResult<ArticleTagResp>> {
        return articleTagService.updateTag(tagUpdate)
    }

    @DeleteMapping("/{id}")
    fun deleteTag(@PathVariable("id") id: Long): ResponseEntity<BaseResult<String>> {
        return articleTagService.deleteTag(id)
    }

    @GetMapping("/all")
    fun allTags(): ResponseEntity<BaseResult<List<ArticleTagResp>>> {
        return articleTagService.allTags()
    }

}