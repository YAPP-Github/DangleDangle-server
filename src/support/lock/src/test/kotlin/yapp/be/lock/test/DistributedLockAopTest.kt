package yapp.be.lock.test

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import yapp.be.lock.ConcurrencyHelper
import yapp.be.lock.SampleService
import yapp.be.lock.config.LockTest

@LockTest
class DistributedLockAopTest(
    private val service: SampleService
) : BehaviorSpec({

    Given("분산락이 적용되어 을 때") {
        When("여러개의 요청이 동시에 들어왔다") {
            val successCount = ConcurrencyHelper.execute { service.invokeWithLock(id = 1) }
            Then("하나의 요청만 성공해야 한다.") {
                successCount shouldBe 1
            }
        }
    }

    Given("분산락이 적용되어 있지 않을 때") {
        When("여러개의 요청이 동시에 들어왔다면") {
            val successCount = ConcurrencyHelper.execute { service.invokeWithoutLock(1) }
            Then("동시성 문제가 발생한다.") {
                successCount shouldBe ConcurrencyHelper.NUMBER_OF_THREADS
            }
        }
    }
})
