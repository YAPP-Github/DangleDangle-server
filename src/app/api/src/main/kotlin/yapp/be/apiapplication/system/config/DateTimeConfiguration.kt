package yapp.be.apiapplication.system.config

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import java.time.format.DateTimeFormatter.ofPattern
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
class DateTimeConfiguration : Jackson2ObjectMapperBuilderCustomizer {
    private val dateTimeFormat: String = "yyyy-MM-dd HH:mm:ss"
    private val dateFormat: String = "yyyy-MM-dd"
    private val timeFormat: String = "HH:mm"

    override fun customize(jacksonObjectMapperBuilder: Jackson2ObjectMapperBuilder) {
        jacksonObjectMapperBuilder
            .serializers(
                LocalDateTimeSerializer(ofPattern(dateTimeFormat)),
                LocalDateSerializer(ofPattern(dateFormat)),
                LocalTimeSerializer(ofPattern(timeFormat))
            )
            .deserializers(
                LocalDateTimeDeserializer(ofPattern(dateTimeFormat)),
                LocalDateDeserializer(ofPattern(dateFormat)),
                LocalTimeDeserializer(ofPattern(timeFormat))
            )
    }
}
