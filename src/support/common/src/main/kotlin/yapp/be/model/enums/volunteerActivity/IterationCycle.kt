package yapp.be.model.enums.volunteerActivity

import java.time.LocalDateTime

enum class IterationCycle(
    private val description: String
) {
    EVERYDAY("매일") {
        override fun getNextDate(base: LocalDateTime): LocalDateTime {
            return base.plusDays(1)
        }
    },
    WEEKLY("매주") {
        override fun getNextDate(base: LocalDateTime): LocalDateTime {
            return base.plusWeeks(1)
        }
    },
    BIWEEKLY("격주") {
        override fun getNextDate(base: LocalDateTime): LocalDateTime {
            return base.plusWeeks(2)
        }
    },
    MONTHLY("매월") {
        override fun getNextDate(base: LocalDateTime): LocalDateTime {
            return base.plusMonths(1)
        }
    };

    abstract fun getNextDate(base: LocalDateTime): LocalDateTime
}
