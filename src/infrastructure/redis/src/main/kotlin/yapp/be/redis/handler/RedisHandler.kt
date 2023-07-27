package yapp.be.redis.handler

import org.springframework.data.redis.connection.stream.Consumer
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.PendingMessage
import org.springframework.data.redis.core.Cursor
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import yapp.be.domain.model.Event
import java.time.Duration

@Component
class RedisHandler(
    private val stringRedisTemplate: StringRedisTemplate,
    private val redisTemplate: RedisTemplate<String, Any>
) {
    fun getData(key: String): String? {
        val valueOperations = stringRedisTemplate.opsForValue()
        return valueOperations[key]
    }

    fun getKeys(pattern: String): Set<String> {
        val result: MutableSet<String> = mutableSetOf()
        val scanOptions = ScanOptions.scanOptions().match(pattern).count(10).build()
        val keys: Cursor<String> = stringRedisTemplate.scan(scanOptions)

        while (keys.hasNext()) {
            result.add(keys.next())
        }

        return result
    }

    fun setData(key: String, value: String) {
        val valueOperations = stringRedisTemplate.opsForValue()
        valueOperations[key] = value
    }

    fun setDataExpire(key: String, value: String, duration: Duration) {
        if (stringRedisTemplate.hasKey(key)) {
            stringRedisTemplate.delete(key)
        }
        stringRedisTemplate.opsForValue().set(key, value, duration)
    }

    fun deleteData(key: String) {
        stringRedisTemplate.delete(key)
    }

    fun xAdd(value: ObjectRecord<String, Event>) {
        redisTemplate.opsForStream<Any, Any>()
            .add(value)
    }

    fun xAddPipelined(value: List<MapRecord<ByteArray, ByteArray, ByteArray>>) {
        redisTemplate.executePipelined { connection ->
            value.forEach { connection.xAdd(it) }
            null
        }
    }

    fun getPendingMessages(streamKey: String, consumerGroup: String) =
        redisTemplate.opsForStream<Any, Any>().pending(streamKey, consumerGroup)

    fun getPendingMessagesPerConsumer(streamKey: String, consumerGroup: String, consumerName: String) =
        redisTemplate.opsForStream<Any, Any>().pending(streamKey, Consumer.from(consumerGroup, consumerName))

    fun claimMessage(
        streamKey: String,
        consumerGroup: String,
        consumer: String,
        timeout: Long,
        pendingMessage: PendingMessage,
    ): List<MapRecord<String, String, String>> {
        println("Claiming message ${pendingMessage.id}")
        return redisTemplate.opsForStream<String, String>().claim(
            streamKey,
            consumerGroup,
            consumer,
            Duration.ofMillis(timeout),
            pendingMessage.id
        )
    }

    fun acknowledge(group: String, record: MapRecord<String, String, String>) =
        redisTemplate.opsForStream<Any, Any>().acknowledge(group, record)
}
