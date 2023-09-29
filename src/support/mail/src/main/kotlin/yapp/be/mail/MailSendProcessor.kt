package yapp.be.mail

import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine

@Component
class MailSendProcessor(
    private val mailSender: JavaMailSender,
    private val templateEngine: SpringTemplateEngine
) {

    @Async
    @Throws(Exception::class)
    fun sendEmailMessage(command: MailSendCommand) {
        val message = mailSender.createMimeMessage()
        message.addRecipients(MimeMessage.RecipientType.TO, command.to)
        message.subject = "[댕글댕글]-${command.title}"
        message.setText(
            setContext(
                template = command.template,
                variables = command.variables
            ),
            "utf-8", "html"
        )

        mailSender.send(message)
    }

    private fun setContext(template: String, variables: Map<String, String>): String {
        val context = Context()
        variables.forEach { (key, value) -> context.setVariable(key, value) }
        return templateEngine.process(template, context)
    }
}
