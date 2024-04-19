package edu.java.component;

import edu.java.model.Link;
import java.net.URISyntaxException;

public interface LinkUpdater {
    void updateLinkForGithub(Link link) throws URISyntaxException;

    void updateLinkForStackOverFlow(Link link) throws URISyntaxException;
}
