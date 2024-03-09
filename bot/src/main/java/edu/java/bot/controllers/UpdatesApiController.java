package edu.java.bot.controllers;

import edu.java.bot.model.request.LinkUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdatesApiController implements UpdatesApi {

    public ResponseEntity<Void> updatesPost(
        @RequestBody LinkUpdateRequest body
    ) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
