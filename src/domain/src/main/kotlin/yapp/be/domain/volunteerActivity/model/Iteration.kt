package yapp.be.domain.volunteerActivity.model

import java.time.LocalDate
import yapp.be.model.enums.volunteerActivity.IterationCycle

data class Iteration(
    val iterationStartAt: LocalDate,
    val iterationEndAt: LocalDate,
    val cycle: IterationCycle,
)
