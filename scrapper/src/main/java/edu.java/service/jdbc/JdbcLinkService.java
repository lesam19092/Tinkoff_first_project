package edu.java.service.jdbc;

import edu.java.model.dto.Link;
import edu.java.model.dto.LinkSof;
import edu.java.repository.LinkRepository;
import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkRepository {

    private final LinkRepository linkRepository;

    public List<Link> findAll() {
        return linkRepository.findAll();
    }

    public List<Link> getOldLinks(int delay) {
        return linkRepository.getOldLinks(delay);
    }

    public void add(Link link) {
        linkRepository.add(link);
    }

    public void remove(Long id) {
        linkRepository.remove(id);
    }

    public void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime) {
        linkRepository.updateLinkLastCheckTimeById(id, lastCheckTime);
    }

    public LinkSof getLinkPropertiesById(Long id) {
        return linkRepository.getLinkPropertiesById(id);
    }

    public void updateCountOfCommentsById(Long id, Long count) {
        linkRepository.updateCountOfCommentsById(id, count);
    }

    public void updateCountOfAnswersById(Long id, Long count) {
        linkRepository.updateCountOfAnswersById(id, count);

    }

}
