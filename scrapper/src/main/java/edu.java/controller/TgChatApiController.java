package edu.java.controller;

import edu.java.model.dto.Chat;
import edu.java.repository.ChatRepository;
import io.github.bucket4j.Bucket;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TgChatApiController implements TgChatApi {

    private final ChatRepository chatRepository;

    @Autowired
    private Bucket bucket;
    private static final Logger LOGGER = LoggerFactory.getLogger(TgChatApiController.class);

    @Autowired
    public TgChatApiController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public ResponseEntity<Void> tgChatIdDelete(
        @Parameter(in = ParameterIn.PATH,
                   description = "",
                   required = true,
                   schema = @Schema())
        @PathVariable("id") Long id
    ) {
        if (bucket.tryConsume(1)) {
            chatRepository.remove(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    public ResponseEntity<Void> tgChatIdPost(
        @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id")
        Long id
    ) {
        if (bucket.tryConsume(1)) {
            Chat chat = new Chat();
            chat.setChatId(id);
            chatRepository.add(chat);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

}
