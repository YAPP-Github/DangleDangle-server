package yapp.be.test.common

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.string.shouldMatch
import io.kotest.matchers.string.shouldNotMatch
import yapp.be.model.support.EMAIL_REGEX
import yapp.be.model.support.PASSWORD_REGEX
import yapp.be.model.support.PHONE_REGEX

class regExpTest : BehaviorSpec({

    Given("이메일 정규식") {
        val regex = EMAIL_REGEX
        When("패턴에 맞다면") {
            Then("통과해야 한다.") {
                "test@example.com" shouldMatch regex
                "test.test@example.com" shouldMatch regex
                "test+test@example.co.uk" shouldMatch regex
            }
        }
        When("패턴에 일치하지 않는다면") {
            Then("에러가 발생해야 한다.") {
                "testexample.com" shouldNotMatch regex
                "test@" shouldNotMatch regex
                "test example" shouldNotMatch regex
            }
        }
    }

    Given("비밀번호 정규식") {
        val regex = PASSWORD_REGEX
        When("패턴에 맞다면") {
            Then("통과해야 한다.") {
                "abcd12345678" shouldMatch regex
                "12345678!@#$%" shouldMatch regex
                "abcd12345678!" shouldMatch regex
            }
        }
        When("패턴에 일치하지 않는다면") {
            Then("에러가 발생해야 한다.") {
                "abcd" shouldNotMatch regex
                "abcdefgh" shouldNotMatch regex
                "abcd12345678901011" shouldNotMatch regex
            }
        }
    }

    Given("전화번호 정규식") {
        val regex = PHONE_REGEX
        When("패턴에 맞다면") {
            Then("통과해야 한다.") {
                "023456789" shouldMatch regex
                "01034567890" shouldMatch regex
            }
        }
        When("패턴에 일치하지 않는다면") {
            Then("에러가 발생해야 한다.") {
                "02-345-6789" shouldNotMatch regex
                "010-3456-7890" shouldNotMatch regex
            }
        }
    }
})
