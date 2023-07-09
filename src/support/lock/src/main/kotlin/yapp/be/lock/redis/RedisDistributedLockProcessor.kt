package yapp.be.lock.redis

import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import yapp.be.exceptions.CustomException
import yapp.be.lock.DistributedLockProcessor
import yapp.be.lock.exceptions.LockExceptionType
import java.util.concurrent.TimeUnit

@Component
class RedisDistributedLockProcessor(
    private val redissonClient: RedissonClient
) : DistributedLockProcessor {
    override fun operateWithLock(
        key: String,
        timeOut: Long,
        leaseTime: Long,
        operation: () -> Any?
    ): Any? {
        println("I'm Calling -> $key ${Thread.currentThread().name}")
        val rLock = redissonClient.getLock(key)
        val start = System.currentTimeMillis()

        try {
            /**
             * leaseTime : Lock 점유 시간
             * waitTime  : Lock 대기 시간
             */
            println("TryLock -> $key ${Thread.currentThread().name}")
            val isLockAcquired = rLock.tryLock(timeOut, leaseTime, TimeUnit.MILLISECONDS)
            println(isLockAcquired)
            if (!isLockAcquired) {
                throw CustomException(
                    type = LockExceptionType.LOCK_ACQUIRED_FAILED,
                    message = "Lock 획득에 실패하였습니다."
                )
            }
            return operation()
        } finally {
            if (rLock.isHeldByCurrentThread) {
                rLock.unlock()
                println("UnLock -> $key ${Thread.currentThread().name}")
                println("Process Time -> ${System.currentTimeMillis() - start}")
            }
        }
    }
}
