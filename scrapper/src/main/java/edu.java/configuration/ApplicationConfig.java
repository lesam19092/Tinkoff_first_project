package edu.java.configuration;

import edu.java.configuration.retryconfig.RetryStrategy;
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
    int linkDelay,

    @NotNull
    AccessType databaseAccessType,
    @NotNull
    String retryOn,
    @NotNull
    RetryStrategy retryStrategy,
    @NotNull
    int retryMaxAttempts,
    @NotNull
    int retryDelay) {
    public record Scheduler(boolean enable,
                            @NotNull Duration interval,
                            @NotNull Duration forceCheckDelay) {
    }

}
