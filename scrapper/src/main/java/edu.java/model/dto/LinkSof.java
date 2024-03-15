package edu.java.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.net.URI;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkSof {
    private long id;
    private long linkId;
    private long countOfAnswer;
    private long countOfComments;
}
