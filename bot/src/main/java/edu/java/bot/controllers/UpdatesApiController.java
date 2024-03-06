package edu.java.bot.controllers;

import edu.java.bot.model.request.LinkUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdatesApiController implements UpdatesApi {

    private final HttpServletRequest request;

    @Autowired
    public UpdatesApiController(HttpServletRequest request) {
        this.request = request;
    }

    public ResponseEntity<Void> updatesPost(
        @RequestBody LinkUpdateRequest body
    ) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
