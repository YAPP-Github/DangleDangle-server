package yapp.be.redis.repository

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration
@Repository
class RedisHandler(
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
