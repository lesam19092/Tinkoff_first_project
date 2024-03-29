package edu.java.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(

    @NotNull
    @Bean
    Scheduler scheduler,
    @NotEmpty
    String gitUrl,
    @NotEmpty
    String stackUrl,
    int linkDelay
) {
    public record Scheduler(boolean enable,
                            @NotNull Duration interval,
                            @NotNull Duration forceCheckDelay) {
    }

}
