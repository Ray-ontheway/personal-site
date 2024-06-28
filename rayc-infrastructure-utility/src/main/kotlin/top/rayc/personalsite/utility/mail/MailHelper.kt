package top.rayc.personalsite.utility.mail

import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
@Slf4j
class MailHelper {
    @Autowired private lateinit var mailSender: JavaMailSender

//    @Value("\${spring.mail.username}")
}
