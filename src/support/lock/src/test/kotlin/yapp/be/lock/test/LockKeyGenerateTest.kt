package yapp.be.lock.test

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.aspectj.lang.reflect.MethodSignature
import yapp.be.lock.LockKeyGenerator
import yapp.be.lock.SampleObj

class LockKeyGenerateTest : BehaviorSpec({

    Given("파라미터가 Primitive타입 일 때") {
        val keyGenerator = LockKeyGenerator()

        val lockIdentifiers: Array<String> = arrayOf("p1", "p2")
        val parameterNames: Array<String> = arrayOf("p1", "p2")
        val parameterValues: Array<Any> = arrayOf(1, "2")
        val methodSignature = mockk<MethodSignature>()
        When("Key를 생성 한다면") {
            every { methodSignature.parameterNames } returns parameterNames
            val key = keyGenerator.generate(
                identifiers = lockIdentifiers,
                arguments = parameterValues,
                methodSignature = methodSignature
            )

            Then("파라미터의 값과 구분자를 통해 Key를 생성한다. ") {
                key shouldBe parameterValues.joinToString(":")
            }
        }
    }

    Given("파라미터가 Object 타입이 일 때") {
        val keyGenerator = LockKeyGenerator()
        val parameterObject = SampleObj(p1 = 1, p2 = "2")

        val lockIdentifiers: Array<String> = arrayOf("obj.p1", "obj.p2")

        val parameterNames = arrayOf("obj")
        val parameterValues: Array<Any> = arrayOf(parameterObject)
        val methodSignature = mockk<MethodSignature>()

        When("Key를 생성 한다면") {
            every { methodSignature.parameterNames } returns parameterNames
            val key = keyGenerator.generate(
                identifiers = lockIdentifiers,
                arguments = parameterValues,
                methodSignature = methodSignature
            )
            Then("파라미터의 값과 구분자를 통해 Key를 생성한다. ") {
                key shouldBe "${parameterObject.p1}:${parameterObject.p2}"
            }
        }
    }
})
