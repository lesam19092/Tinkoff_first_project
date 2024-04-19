package edu.java.service;

import edu.java.model.dto.Link;
import edu.java.model.dto.LinkSof;
import java.sql.Timestamp;
import java.util.List;

public interface LinkRepository {
    List<Link> findAll();

    List<Link> getOldLinks(int delay);

    void add(Link link);


    void remove(Long id);


    void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime);

    LinkSof getLinkPropertiesById(Long id);

    void updateCountOfCommentsById(Long id, Long count);

    void updateCountOfAnswersById(Long id, Long count);

}
