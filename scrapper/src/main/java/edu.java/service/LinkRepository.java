package edu.java.service;

import edu.java.model.dto.Link;
import java.sql.Timestamp;
import java.util.List;

public interface LinkRepository {
    List<Link> findAll();

    List<Link> getOldLinks(int delay);

    void add(Link link);

    void remove(Long id);

    void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime);

}
