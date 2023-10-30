package yapp.be.domain.common.model

import yapp.be.exceptions.CustomException
import yapp.be.exceptions.SystemExceptionType

enum class AlimtalkMessageTemplate(
    val templateCode: String,
    val title: String,
) : AlimtalkMessageGenerateFunction {
    VOLUNTEER_UPDATE_EVENT(
        templateCode = "VOLUNTEER1",
        title = "[이벤트 정보 업데이트]",
    ) {
        override fun getMessage(variables: Map<String, String>): String {
            val name = variables["name"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[name]을 찾을 수 없음)")
            val event = variables["event"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[event]을 찾을 수 없음)")
            val date = variables["date"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[date]을 찾을 수 없음)")
            val time = variables["time"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[time]을 찾을 수 없음)")

            return """
            ${name}님, 참석/대기 신청하신 이벤트의 정보가 변경되어 연락드렸어요.

            - 이벤트명: $event
            - 날짜: $date
            - 시간: $time
            """.trimIndent()
        }
    },
    VOLUNTEER_REMIND_EVENT(
        templateCode = "VOLUNTEER2",
        title = "[이벤트 리마인더]"
    ) {
        override fun getMessage(variables: Map<String, String>): String {
            val name = variables["name"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[name]을 찾을 수 없음)")
            val event = variables["event"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[event]을 찾을 수 없음)")
            val date = variables["date"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[date]을 찾을 수 없음)")
            val time = variables["time"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[time]을 찾을 수 없음)")

            return """
            ${name}님, 내일 참석이 예정되어 있는 이벤트가 있어요!

            - 이벤트명: $event
            - 날짜: $date
            - 시간: $time
            """.trimIndent()
        }
    },

    VOLUNTEER_DELETE_EVENT(
        templateCode = "VOLUNTEER3",
        title = "[이벤트 삭제 알림]"
    ) {
        override fun getMessage(variables: Map<String, String>): String {
            val name = variables["name"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[name]을 찾을 수 없음)")
            val event = variables["event"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[event]을 찾을 수 없음)")
            val date = variables["date"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[date]을 찾을 수 없음)")
            val time = variables["time"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[time]을 찾을 수 없음)")

            return """
            ${name}님, 참석/대기 신청하신 이벤트가 보호소의 사정으로 인해 일정 삭제되어 연락드렸어요.

            - 이벤트명: $event
            - 날짜: $date
            - 시간: $time
            """.trimIndent()
        }
    },

    WAITING_VOLUNTEER_AVAILABLE_EVENT(
        templateCode = "VOLUNTEER4",
        title = "[이벤트 참석 가능 알림]"
    ) {
        override fun getMessage(variables: Map<String, String>): String {
            val name = variables["name"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[name]을 찾을 수 없음)")
            val event = variables["event"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[event]을 찾을 수 없음)")
            val date = variables["date"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[date]을 찾을 수 없음)")
            val time = variables["time"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[time]을 찾을 수 없음)")

            return """
            ${name}님, 대기 신청하신 이벤트에 빈 자리가 생겨 연락드렸어요! 선착순이니 얼른 확인해보세요!

            - 이벤트명: $event
            - 날짜: $date
            - 시간: $time
            """.trimIndent()
        }
    },
    SHELTER_REMIND_EVENT(
        templateCode = "SHELTER1",
        title = "[이벤트 리마인더]"
    ) {
        override fun getMessage(variables: Map<String, String>): String {
            val name = variables["name"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[name]을 찾을 수 없음)")
            val event = variables["event"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[event]을 찾을 수 없음)")
            val date = variables["date"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[date]을 찾을 수 없음)")
            val time = variables["time"] ?: throw CustomException(type = SystemExceptionType.INTERNAL_SERVER_ERROR, "[알림톡][초기화][실패] (cause=[time]을 찾을 수 없음)")

            return """
            ${name}님, 내일 예정되어 있는 이벤트가 있어요!

            - 이벤트명: $event
            - 날짜: $date
            - 시간: $time
            """.trimIndent()
        }
    };
}
