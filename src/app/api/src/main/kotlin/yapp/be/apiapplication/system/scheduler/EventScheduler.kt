package yapp.be.apiapplication.system.scheduler

import org.springframework.scheduling.annotation.Scheduled
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase

class EventScheduler(
    private val getVolunteerEventUseCase: GetVolunteerEventUseCase,
) {
    @Scheduled(cron = "\${event.pending-messages.check-interval}")
    fun eventReminder() {
    }
}
