package edu.java.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "links_sof")
public class LinkSof {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "link_id")
    private long linkId;
    @Column(name = "countOfAnswers")
    private long countOfAnswer;
    @Column(name = "countOfComments")
    private long countOfComments;
}
