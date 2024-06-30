package top.rayc.personalsite.utility.email

data class ToMail (
    val tos: Array<String>,
    val subject: String,
    val content: String
)