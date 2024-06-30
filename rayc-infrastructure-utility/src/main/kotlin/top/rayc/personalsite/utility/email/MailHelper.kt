package top.rayc.personalsite.utility.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component

class MailHelper {
    @Autowired private lateinit var mailSender: JavaMailSender

    @Value("\${spring.mail.username}") var from: String? = null

    fun send(mail: ToMail): Boolean {
        val msg = SimpleMailMessage().apply {
            from = from
            setTo(*mail.tos)
            subject = mail.subject
            text = mail.content
        }
        return try {
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
