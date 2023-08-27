package yapp.be.apiapplication.volunteer.scheduler

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import yapp.be.apiapplication.volunteer.service.VolunteerManageService

@Component
class VolunteerScheduler(
    private val volunteerManageService: VolunteerManageService
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    @Scheduled(cron = "0 */30 * * * *")
    fun deleteVolunteer() {
        val deletedVolunteers =
            volunteerManageService.getAllDeletedVolunteers()

        deletedVolunteers.forEach { volunteer ->
            runCatching {
                volunteerManageService
                    .deleteVolunteerAndAllRelatedContents(volunteer.id)
            }.onSuccess {
                log.info("Deleted Scheduler Success volunteerId: ${volunteer.id}")
            }
                .onFailure {
                    log.info("Deleted Scheduler Fail volunteerId: ${volunteer.id}")
                }
        }
    }
}
