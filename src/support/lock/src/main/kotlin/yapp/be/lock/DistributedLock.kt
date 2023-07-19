package yapp.be.lock

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class DistributedLock(
    val prefix: String,
    val identifiers: Array<String>
)
