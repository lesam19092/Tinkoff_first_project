package edu.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.model.dto.Link;
import edu.java.model.request.AddLinkRequest;
import edu.java.model.request.RemoveLinkRequest;
import edu.java.model.response.LinkResponse;
import edu.java.model.response.ListLinksResponse;
import edu.java.repository.LinkRepository;
import io.github.bucket4j.Bucket;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinksApiController implements LinksApi {

    private final LinkRepository linkRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(LinksApiController.class);
    private final String acceptString = "Accept";
    private final String applicationJsonString = "application/json";
    private final String errorString = "Couldn't serialize response for content type application/json";

    private final ObjectMapper objectMapper;
    @Autowired
    private Bucket bucket;

    @Autowired
    public LinksApiController(LinkRepository linkRepository, ObjectMapper objectMapper) {
        this.linkRepository = linkRepository;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<LinkResponse> linksDelete(
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
        @Valid
        @RequestBody
        RemoveLinkRequest body
    ) {
        try {
            if (bucket.tryConsume(1)) {

                linkRepository.remove(tgChatId);
                return new ResponseEntity<LinkResponse>(objectMapper.readValue(
                    "{\n  \"id\" : 1,\n  \"url\" : \"http://example.com/aeiou\"\n}",
                    LinkResponse.class
                ), HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            }

        } catch (IOException e) {
            LOGGER.error(errorString, e);
            return new ResponseEntity<LinkResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<ListLinksResponse> linksGet(
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId
    ) {
        try {
            if (bucket.tryConsume(1)) {
                for (Link link : linkRepository.findAll()) {
                    LOGGER.info(link.getUrl().toString());
                }
                return new ResponseEntity<ListLinksResponse>(objectMapper.readValue(
                    "{\n  \"size\" : 6,\n  \"links\" : [ {\n    \"id\" : 0,\n    \"url\" : \"http://example.com/aeiou\"\n  }, {\n    \"id\" : 0,\n    \"url\" : \"http://example.com/aeiou\"\n  } ]\n}",
                    ListLinksResponse.class
                ), HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            }
        } catch (IOException e) {
            LOGGER.error(errorString, e);
            return new ResponseEntity<ListLinksResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<LinkResponse> linksPost(
        @Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema())
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
        @Parameter(in = ParameterIn.DEFAULT,
                   description = "",
                   required = true,
                   schema = @Schema()) @Valid @RequestBody
        AddLinkRequest body
    ) {
        try {
            if (bucket.tryConsume(1)) {

                int time = Integer.parseInt(System.getenv("time"));
                Link link = new Link();
                link.setChatId(tgChatId);
                link.setUrl(URI.create("https://github.com/lesam19092/laba2"));
                link.setCreatedAt(new Timestamp(time));
                link.setLastCheckTime(new Timestamp(time));

                return new ResponseEntity<LinkResponse>(objectMapper.readValue(
                    "{\n  \"id\" : 0,\n  \"url\" : \"http://example.com/aeiou\"\n}",
                    LinkResponse.class
                ), HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            }
        } catch (IOException e) {
            LOGGER.error(errorString, e);
            return new ResponseEntity<LinkResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


