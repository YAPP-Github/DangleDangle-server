package yapp.be.lock

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Aspect
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE + 1)
class DistributedLockAspect(
    private val keyGenerator: LockKeyGenerator,
    private val distributedLockProcessor: DistributedLockProcessor,
    private val distributedNewTransaction: DistributedNewTransaction,
) {

    @Around("@annotation(distributedLock)")
    fun lock(pjp: ProceedingJoinPoint, distributedLock: DistributedLock): Any? {
        val identifiers = distributedLock.identifiers
        val dynamicKey = keyGenerator.generate(
            arguments = pjp.args,
            identifiers = identifiers,
            methodSignature = pjp.signature as MethodSignature
        )
        val key = "${distributedLock.prefix}:$dynamicKey"
        val result = distributedLockProcessor.operateWithLock(
            key = key,
            timeOut = distributedLock.timeOut,
            leaseTime = distributedLock.leaseTime,
            operation = { distributedNewTransaction.proceed(pjp) }
        )

        return result
    }
}
