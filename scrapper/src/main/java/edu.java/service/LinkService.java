package edu.java.service;

import edu.java.model.dto.Link;
import edu.java.model.dto.LinkSof;
import java.util.List;

public interface LinkService {
    List<Link> getLinks();

    List<Link> getOldLinks();

    int addLink(Link link);

    int removeLink(Long id);

    LinkSof getLinkPropertiesById(Long id);

}
