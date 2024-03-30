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
@Table(name = "links_sof")
public class LinkSof {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "link_id")
    private long linkId;
    @Column(name = "count_of_answers")
    private long countOfAnswer;
    @Column(name = "count_of_comments")
    private long countOfComments;
}
