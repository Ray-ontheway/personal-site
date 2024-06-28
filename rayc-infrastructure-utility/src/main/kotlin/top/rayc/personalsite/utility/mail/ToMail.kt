package top.rayc.personalsite.utility.mail

data class ToMail (
    val tos: Array<String>,
    val subject: String,
    val content: String
)