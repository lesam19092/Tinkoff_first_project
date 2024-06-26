package edu.java.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.net.URI;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Link implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = UriConverter.class)
    @Column(name = "url")
    private URI url;
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "last_check_time")

    private Timestamp lastCheckTime;
    @Column(name = "created_at")

    private Timestamp createdAt;
}
