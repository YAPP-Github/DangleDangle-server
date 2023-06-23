package yapp.be.redis.service

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
@Service
class RedisService(
    private val stringRedisTemplate: StringRedisTemplate
) {
    fun getData(key: String): String? {
        val valueOperations = stringRedisTemplate.opsForValue()
        return valueOperations[key]
    }

    fun getKeys(pattern: String): Set<String> {
        return stringRedisTemplate.keys(pattern)
    }

    fun setData(key: String, value: String) {
        val valueOperations = stringRedisTemplate.opsForValue()
        valueOperations[key] = value
    }

    fun setDataExpire(key: String, value: String, duration: Long) {
        if (stringRedisTemplate.hasKey(key)) {
            stringRedisTemplate.delete(key)
        }
        stringRedisTemplate.opsForValue().set(key, value, Duration.ofSeconds(duration))
    }

    fun deleteData(key: String) {
        stringRedisTemplate.delete(key)
    }
}
