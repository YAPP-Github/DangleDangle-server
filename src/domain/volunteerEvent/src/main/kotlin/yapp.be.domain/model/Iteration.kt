package yapp.be.domain.model

import java.time.LocalDate
import yapp.be.model.enums.volunteerevent.IterationCycle

data class Iteration(
    val iterationStartAt: LocalDate,
    val iterationEndAt: LocalDate,
    val cycle: IterationCycle,
)
