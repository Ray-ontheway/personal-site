package top.rayc.personalsite.article.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import top.rayc.personalsite.article.controller.vo.req.ArticleTypeCreate
import top.rayc.personalsite.article.controller.vo.req.ArticleTypeUpdate
import top.rayc.personalsite.article.controller.vo.resp.ArticleTypeResp
import top.rayc.personalsite.article.service.impl.ArticleTypeService
import top.rayc.personalsite.utility.vo.BaseResult

@RequestMapping("/article/type")
class ArticleTypeController(
    @Autowired val articleTypeService: ArticleTypeService
) {

    @PostMapping
    fun createType(@RequestBody @Validated typeCreate: ArticleTypeCreate): ResponseEntity<BaseResult<ArticleTypeResp>> {
        return articleTypeService.createType(typeCreate)
    }

    @PutMapping
    fun updateType(@RequestBody @Validated typeUpdate: ArticleTypeUpdate): ResponseEntity<BaseResult<ArticleTypeResp>> {
        return articleTypeService.updateType(typeUpdate)
    }

    @DeleteMapping("/{id}")
    fun deleteType(@PathVariable("id") id: Long): ResponseEntity<BaseResult<String>> {
        return articleTypeService.deleteType(id)
    }

    @GetMapping("/all")
    fun allTypes(): ResponseEntity<BaseResult<List<ArticleTypeResp>>> {
        return articleTypeService.allTypes()
    }

}