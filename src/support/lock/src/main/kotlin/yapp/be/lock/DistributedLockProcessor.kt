package yapp.be.lock

interface DistributedLockProcessor {
    fun operateWithLock(
        key: String,
        timeOut: Long,
        leaseTime: Long,
        operation: () -> Any?
    ): Any?
}
