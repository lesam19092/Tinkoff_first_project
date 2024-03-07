package edu.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.model.request.AddLinkRequest;
import edu.java.model.request.RemoveLinkRequest;
import edu.java.model.response.LinkResponse;
import edu.java.model.response.ListLinksResponse;
import jakarta.validation.Valid;
import java.io.IOException;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(LinksApiController.class);
    private final String acceptString = "Accept";
    private final String applicationJsonString = "application/json";
    private final String errorString = "Couldn't serialize response for content type application/json";


    private final ObjectMapper objectMapper;

    @Autowired
    public LinksApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<LinkResponse> linksDelete(
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
        @Valid
        @RequestBody
        RemoveLinkRequest body
    ) {
        String accept = acceptString;
        if (accept != null && accept.contains(applicationJsonString)) {
            return new ResponseEntity<LinkResponse>(new LinkResponse(), HttpStatus.OK);
        }

        return new ResponseEntity<LinkResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ListLinksResponse> linksGet(
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId
    ) {
        String accept = acceptString;

        if (accept != null && accept.contains(applicationJsonString)) {
            try {
                return new ResponseEntity<ListLinksResponse>(objectMapper.readValue(
                    "{\n  \"size\" : 6,\n  \"links\" : [ {\n    \"id\" : 0,\n    \"url\" : \"http://example.com/aeiou\"\n  }, {\n    \"id\" : 0,\n    \"url\" : \"http://example.com/aeiou\"\n  } ]\n}",
                    ListLinksResponse.class
                ), HttpStatus.OK);
            } catch (IOException e) {
                LOGGER.error(errorString, e);
                return new ResponseEntity<ListLinksResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<ListLinksResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<LinkResponse> linksPost(
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
        AddLinkRequest body
    ) {
        String accept =  acceptString;

        if (accept != null && accept.contains(applicationJsonString)) {
            try {
                return new ResponseEntity<LinkResponse>(objectMapper.readValue(
                    "{\n  \"id\" : 0,\n  \"url\" : \"http://example.com/aeiou\"\n}",
                    LinkResponse.class
                ), HttpStatus.OK);
            } catch (IOException e) {
                LOGGER.error(errorString, e);
                return new ResponseEntity<LinkResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<LinkResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
