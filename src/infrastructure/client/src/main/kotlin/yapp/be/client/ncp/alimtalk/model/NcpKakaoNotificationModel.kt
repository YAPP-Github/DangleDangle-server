package yapp.be.client.ncp.alimtalk.model

sealed interface NcpKakaoNotificationModel {

    data class Request(
        val plusFriendId: String,
        val templateCode: String,
        val messages: List<Message>,
    ) {
        data class Message(
            val to: String,
            val title: String,
            val content: String,
            val buttons: List<Button>,
        ) {
            data class Button(
                val type: String,
                val name: String,
                val linkMobile: String,
                val linkPc: String,
            )
        }
    }

    data class Response(
        val requestId: String,
        val requestTime: String,
        val statusCode: String,
        val statusName: String,
        val messages: List<Message>,
    ) {
        data class Message(
            val messageId: String,
            val countryCode: String,
            val to: String,
            val content: String,
            val requestStatusCode: String,
            val requestStatusName: String,
            val requestStatusDesc: String,
            val useSmsFailover: Boolean,
        )
    }
}
