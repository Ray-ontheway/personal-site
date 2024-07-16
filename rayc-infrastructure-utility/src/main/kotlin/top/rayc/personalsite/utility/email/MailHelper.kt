package top.rayc.personalsite.utility.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import top.rayc.personalsite.utility.logger.LoggerDelegate

@Component
class MailHelper {
    val logger by LoggerDelegate()

    @Autowired private lateinit var mailSender: JavaMailSender

    @Value("\${spring.mail.username}") var _from: String? = null

    fun send(mail: ToMail): Boolean {
        val msg = SimpleMailMessage().apply {
            from = _from
            setTo(*mail.tos)
            subject = mail.subject
            text = mail.content
        }
        return try {
            logger.error("send mail to {}", msg)
            mailSender.send(msg)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun send(subject: String, content: String, tos: Array<String>): Boolean {
        return send(ToMail(tos, subject, content))
    }

}
