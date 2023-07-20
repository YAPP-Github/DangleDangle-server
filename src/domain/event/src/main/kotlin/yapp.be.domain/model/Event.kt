package yapp.be.domain.model

import java.time.Instant

abstract class Event(val eventTime: Instant =  Instant.now())
