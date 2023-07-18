package yapp.be

import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ContextLoadTest : BehaviorSpec({
    Given("모든 설정정보들을 읽고") {
        When("Bean들이 모두 로딩되었다면") {
            Then("테스트에 통과해야 한다.")
        }
    }
})
