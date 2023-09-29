package yapp.be.mail

data class MailSendCommand(
    val title: String,
    val to: String,
    val template: String,
    val variables: Map<String, String>
)
