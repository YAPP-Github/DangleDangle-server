package yapp.be.application.common

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldMatch
import io.kotest.matchers.string.shouldNotMatch

class regExpTest : FunSpec({
    val regExpPw: String = "^(?!((?:[A-Za-z]+)|(?:[~!@#\$%^&*()_+=]+)|(?:[0-9]+))\$)[A-Za-z\\d~!@#\$%^&*()_+=]{8,15}\$"
    val regExpPhone: String = "^\\d{2,3}\\d{3,4}\\d{4}\$"
    val regExpMobile: String = "^\\d{3}\\d{3,4}\\d{4}\$"
    context("비밀번호 정규식 테스트") {
        "abcd" shouldNotMatch regExpPw
        "abcdefgh" shouldNotMatch regExpPw
        "abcd12345678" shouldMatch regExpPw
        "12345678!@#$%" shouldMatch regExpPw
        "abcd12345678!" shouldMatch regExpPw
        "abcd12345678901011" shouldNotMatch regExpPw
    }

    context("전화번호 정규식 테스트") {
        "023456789" shouldMatch regExpPhone
        "02-345-6789" shouldNotMatch regExpPhone
    }

    context("휴대폰 정규식 테스트") {
        "01034567890" shouldMatch regExpMobile
        "013456780" shouldNotMatch regExpMobile
        "010-3456-7890" shouldNotMatch regExpMobile
    }
})
