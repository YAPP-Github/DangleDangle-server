package yapp.be.redis.repository

import org.springframework.data.redis.core.Cursor
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration


@Component
class RedisHandler(
    private val stringRedisTemplate: StringRedisTemplate
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
}
