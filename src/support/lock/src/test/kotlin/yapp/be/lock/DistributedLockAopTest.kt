package yapp.be.lock

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@SpringBootTest
class DistributedLockAopTest @Autowired constructor(
    private val redissonService: RedissonService
) : StringSpec({

    "분산락 적용 동시성 테스트" {
        val successCount = AtomicLong()
        ConcurrencyHelper.execute(
            { redissonService.test(1) },
            successCount
        )
        println(successCount.toLong())
        redissonService.apply shouldBe 20
    }

    "분산락 객체 접근 테스트" {
        // when
        val successCount = AtomicLong()
        ConcurrencyHelper.execute(
            { redissonService.testObject(Person(20, Gender.FEMALE, "김사람")) },
            successCount,
        )
        // then
        println(successCount.toLong())
        Assertions.assertThat(successCount.toLong()).isEqualTo(1L)
    }
})

@Service
class RedissonService(
    var apply: Int = 0,
    var status: Boolean = false
) {
    @DistributedLock(
        prefix = "testLock",
        identifiers = ["id"],
        timeOut = 3000L,
        leaseTime = 5000L
    )
    fun test(id: Int) {
        apply += 1
    }

    @DistributedLock(
        prefix = "testObjectLock",
        identifiers = ["person"],
        timeOut = 3000L,
        leaseTime = 5000L
    )
    fun testObject(person: Person) {
        if (!status) {
            status = true
        } else {
            throw RuntimeException("이미 참여한 봉사입니다.")
        }
    }
}

data class Person(
    val age: Int = 0,
    val gender: Gender = Gender.FEMALE,
    val name: String,
)

enum class Gender {
    MALE, FEMALE
}
