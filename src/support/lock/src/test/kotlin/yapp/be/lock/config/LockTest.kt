package yapp.be.lock.config

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(classes = [LockTestConfiguration::class])
annotation class LockTest()
