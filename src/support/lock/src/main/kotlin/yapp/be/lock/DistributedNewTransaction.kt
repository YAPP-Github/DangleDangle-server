package yapp.be.lock

import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@Component
class DistributedNewTransaction {
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 4)
    fun proceed(joinPoint: ProceedingJoinPoint): Any? {
        return joinPoint.proceed()
    }
}
