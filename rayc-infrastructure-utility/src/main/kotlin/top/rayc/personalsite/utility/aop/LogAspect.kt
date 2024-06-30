package top.rayc.personalsite.utility.aop

import cn.hutool.core.lang.UUID
import cn.hutool.json.JSON
import cn.hutool.json.JSONUtil
import com.fasterxml.jackson.databind.ser.Serializers.Base
import com.fasterxml.jackson.databind.util.JSONPObject
import io.micrometer.common.util.StringUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.MDC
import org.springframework.stereotype.Component
import top.rayc.personalsite.utility.logger.LoggerDelegate
import top.rayc.personalsite.utility.vo.BaseReq
import kotlin.jvm.Throws
import kotlin.math.log

@Aspect
@Component
class LogAspect {

    val log by LoggerDelegate()

    private val TRACE_LOG_ID = "traceId"

    @Pointcut("execution(* top.rayc.personalsite.*service.*.impl.*.*(..))")
    fun logPointcut(){}

    @Around("logPointcut")
    @Throws
    fun doAround(pjp: ProceedingJoinPoint): Any {
        var traceId = MDC.get(TRACE_LOG_ID)
        if (StringUtils.isBlank(traceId)) {
            if (pjp.args.isNotEmpty() && pjp.args[0] is BaseReq) {
                val req: BaseReq = pjp.args[0] as BaseReq
                if (req.traceId.isNotBlank()) {
                    traceId = req.traceId
                }
            }
            MDC.put(TRACE_LOG_ID, traceId.ifBlank  { UUID.randomUUID().toString() })
        }

        val startAt = System.currentTimeMillis()
        log.info("入参: {}", JSONUtil.toJsonStr(pjp.args))

        val result = pjp.proceed()

        log.info("耗时 {} ms, 出参: {}", System.currentTimeMillis() - startAt, JSONUtil.toJsonStr(result))
        return result
    }

}