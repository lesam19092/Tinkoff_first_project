package dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorResponse {

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("code")
    private String code = null;

    @JsonProperty("exceptionName")
    private String exceptionName = null;

    @JsonProperty("exceptionMessage")
    private String exceptionMessage = null;

    @JsonProperty("stacktrace")
    private List<String> stacktrace = null;

    public ApiErrorResponse(
        String description,
        String code,
        String exceptionName,
        String exceptionMessage,
        List<String> stacktrace
    ) {
        this.description = description;
        this.code = code;
        this.exceptionName = exceptionName;
        this.exceptionMessage = exceptionMessage;
        this.stacktrace = stacktrace;
    }

    public ApiErrorResponse description(String description) {
        this.description = description;
        return this;
    }

    public ApiErrorResponse code(String code) {
        this.code = code;
        return this;
    }

    public ApiErrorResponse exceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
        return this;
    }

    public ApiErrorResponse exceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        return this;
    }

    public ApiErrorResponse stacktrace(List<String> stacktrace) {
        this.stacktrace = stacktrace;
        return this;
    }

    public ApiErrorResponse addStacktraceItem(String stacktraceItem) {
        if (this.stacktrace == null) {
            this.stacktrace = new ArrayList<String>();
        }
        this.stacktrace.add(stacktraceItem);
        return this;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiErrorResponse apiErrorResponse = (ApiErrorResponse) o;
        return Objects.equals(this.description, apiErrorResponse.description)
            && Objects.equals(this.code, apiErrorResponse.code)
            && Objects.equals(this.exceptionName, apiErrorResponse.exceptionName)
            && Objects.equals(this.exceptionMessage, apiErrorResponse.exceptionMessage)
            && Objects.equals(this.stacktrace, apiErrorResponse.stacktrace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, code, exceptionName, exceptionMessage, stacktrace);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ApiErrorResponse {\n");

        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    code: ").append(toIndentedString(code)).append("\n");
        sb.append("    exceptionName: ").append(toIndentedString(exceptionName)).append("\n");
        sb.append("    exceptionMessage: ").append(toIndentedString(exceptionMessage)).append("\n");
        sb.append("    stacktrace: ").append(toIndentedString(stacktrace)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
