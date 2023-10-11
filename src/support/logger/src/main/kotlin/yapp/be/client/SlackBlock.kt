package yapp.be.client

interface SlackBlock {
    val type: String
    val text: SlackBlock
}

data class HeaderSection(
    override val text: SlackBlock
) : SlackBlock {
    override val type: String = "header"
}
