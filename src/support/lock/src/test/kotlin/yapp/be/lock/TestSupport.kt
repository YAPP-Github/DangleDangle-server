package yapp.be.lock

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicLong
import org.springframework.stereotype.Service

class ConcurrencyHelper {
    companion object {
        val NUMBER_OF_THREADS = 20
        val NUMBER_OF_THREAD_POOL = 20
        fun execute(operation: () -> Any?): Int {
            val successCount = AtomicLong()
            val executorService = Executors.newFixedThreadPool(NUMBER_OF_THREAD_POOL)
            val latch = CountDownLatch(NUMBER_OF_THREADS)
            for (i in 1..NUMBER_OF_THREADS) {
                executorService.submit {
                    try {
                        operation()
                        successCount.getAndIncrement()
                    } catch (e: Throwable) {
                        Exception(e)
                    } finally {
                        latch.countDown()
                    }
                }
            }
            latch.await()
            return successCount.toInt()
        }
    }
}

@Service
class SampleService {
    @DistributedLock(
        prefix = "testLock",
        identifiers = ["id"],
    )
    fun invokeWithLock(id: Int) {
        // Lock의 TimeOut이 3초기 때문에 하나가 실행되기 위해서는 로직이 3초간 실행되어야한다.
        Thread.sleep(3000L)
    }

    fun invokeWithoutLock(id: Int) {
        // Lock의 TimeOut이 3초기 때문에 하나가 실행되기 위해서는 로직이 3초간 실행되어야한다.
        Thread.sleep(3000L)
    }
}
