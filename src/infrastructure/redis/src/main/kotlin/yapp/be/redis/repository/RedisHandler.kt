package yapp.be.redis.repository

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Component
class RedisHandler(
    private val stringRedisTemplate: StringRedisTemplate
) {

    @Transactional(readOnly = true)
    fun getData(key: String): String? {
        val valueOperations = stringRedisTemplate.opsForValue()
        return valueOperations[key]
    }

    @Transactional(readOnly = true)
    fun getKeys(pattern: String): Set<String> {
        return stringRedisTemplate.keys(pattern)
    }

    @Transactional(readOnly = true)
    fun isExists(key: String): Boolean {
        return stringRedisTemplate.hasKey(key)
    }

    @Transactional
    fun setData(key: String, value: String) {
        val valueOperations = stringRedisTemplate.opsForValue()
        valueOperations[key] = value
    }

    @Transactional
    fun setDataExpire(key: String, value: String, duration: Duration) {
        stringRedisTemplate.opsForValue().set(key, value, duration)
    }

    @Transactional
    fun deleteData(key: String) {
        stringRedisTemplate.delete(key)
    }
}
