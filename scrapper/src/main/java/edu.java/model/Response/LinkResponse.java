package edu.java.model.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;
import java.util.Objects;
import javax.annotation.Generated;
import org.springframework.validation.annotation.Validated;


@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-02-29T17:43:31.402605197Z[GMT]")
public class LinkResponse   {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("url")
    private URI url = null; //TOOD изменить на URL

    public LinkResponse id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     * @return id
     **/
    @Schema(description = "")

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LinkResponse url(URI url) {
        this.url = url;
        return this;
    }


    @Schema(description = "")

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LinkResponse linkResponse = (LinkResponse) o;
        return Objects.equals(this.id, linkResponse.id)
            && Objects.equals(this.url, linkResponse.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LinkResponse {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    url: ").append(toIndentedString(url)).append("\n");
        sb.append("}");
        return sb.toString();
    }


    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
