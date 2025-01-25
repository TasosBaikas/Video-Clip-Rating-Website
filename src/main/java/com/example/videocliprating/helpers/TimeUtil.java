package com.example.videocliprating.helpers;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class TimeUtil {

    /**
     * Converts a LocalDateTime in Europe/Athens time zone to UTC.
     *
     * @param athensTime the LocalDateTime in Europe/Athens time zone
     * @return the corresponding LocalDateTime in UTC
     */
    public LocalDateTime convertAthensToUTC(LocalDateTime athensTime) {
        // Define the Athens time zone
        ZoneId athensZoneId = ZoneId.of("Europe/Athens");

        // Define the UTC time zone
        ZoneId utcZoneId = ZoneId.of("UTC");

        // Convert LocalDateTime in Athens zone to ZonedDateTime
        ZonedDateTime athensZonedDateTime = athensTime.atZone(athensZoneId);

        // Convert to UTC
        ZonedDateTime utcZonedDateTime = athensZonedDateTime.withZoneSameInstant(utcZoneId);

        // Return the LocalDateTime in UTC
        return utcZonedDateTime.toLocalDateTime();
    }

}
