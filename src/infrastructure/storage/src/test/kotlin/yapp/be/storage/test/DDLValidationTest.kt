package yapp.be.storage.test

import io.kotest.core.spec.style.BehaviorSpec
import yapp.be.storage.config.StorageTest

@StorageTest
class DDLValidationTest : BehaviorSpec({
    Given("Entity & DDL 일치 여부 테스트") {
        When("Entity와 DDL이 일치한다면") {
            Then("에러가 발생해서는 안된다") {
            }
        }
    }
})
