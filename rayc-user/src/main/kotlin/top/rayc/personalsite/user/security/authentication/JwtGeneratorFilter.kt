package top.rayc.personalsite.user.security.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.filter.OncePerRequestFilter
import top.rayc.personalsite.user.controller.vo.req.UserLoginReq
import top.rayc.personalsite.utility.logger.LoggerDelegate
import top.rayc.personalsite.utility.utils.JwtTokenUtil
import top.rayc.personalsite.utility.vo.BaseResult
import java.nio.charset.StandardCharsets
import kotlin.jvm.Throws

class JwtGeneratorFilter(
    val manager: AuthenticationManager,
    val jwtSigningKey: String = ".ymLTU8rq83j4fmJZj60wh4OrMNuntIj4fmJ1refreywscascsa",
    val jwtDuration: Long = 1000 * 60 * 60 * 24 * 7
): OncePerRequestFilter() {

    val log by LoggerDelegate()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.error("执行json数据用户登录认真，JWTToken 颁布")
        var userLoginReq = UserLoginReq("", "")

        try {
            val objectMapper = ObjectMapper()
            userLoginReq = objectMapper.readValue(request.inputStream, UserLoginReq::class.java)
            userLoginReq.formatHttpValue()
        } catch (e: Exception) {
            log.error("${e.message}", e)
        }
        log.error("userLoginReq: $userLoginReq")

        val authentication = UsernamePasswordAuthenticationToken(userLoginReq.username, userLoginReq.password)
        manager.authenticate(authentication)

        val jwtToken = JwtTokenUtil.generateJwtToken(jwtSigningKey, authentication, jwtDuration)

        val data = mapOf("accessToken" to jwtToken, "expires" to jwtDuration)

        val result = BaseResult.success("登录成功", data)

        response.apply {
            characterEncoding = StandardCharsets.UTF_8.name()
            contentType = MediaType.APPLICATION_JSON_VALUE
            status = HttpStatus.OK.value()
            writer.write(ObjectMapper().writeValueAsString(result))
        }

    }

    @Throws(ServletException::class) // 为了在和Java相互操作时能正确, 纯Kotlin不需要这样处理
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        log.error("request.path: ${request.servletPath}")
        return request.let {
            log.error("isLogin: ${it.servletPath}; contentType: ${it.contentType}; method: ${it.method}")
            val result = it.servletPath != "/login" || it.contentType != MediaType.APPLICATION_JSON_VALUE || it.method != "POST"
            log.error("是否跳过GeneratorFilter: $result")
            result
        }
    }

}