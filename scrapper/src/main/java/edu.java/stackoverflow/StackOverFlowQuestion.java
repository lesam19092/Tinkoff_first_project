package edu.java.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StackOverFlowQuestion {
    @JsonProperty("question_id")
    private Long questionId;

    @JsonProperty("is_answered")
    private boolean isAnswered;

    @JsonProperty("title")
    private String title;

    @JsonProperty("last_activity_date")
    private Long lastActivity;

    public Timestamp getLastActivityAsTimestamp() {

        return Timestamp.valueOf(Instant.ofEpochMilli(
                lastActivity * 1000)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime());
    }
}
