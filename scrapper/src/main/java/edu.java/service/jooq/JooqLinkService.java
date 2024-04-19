package edu.java.service.jooq;

import edu.java.model.dto.Link;
import edu.java.model.dto.LinkSof;
import edu.java.repository.LinkRepository;
import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JooqLinkService implements LinkRepository {
    private final LinkRepository linkRepository;

    @Override
    public List<Link> findAll() {
        return linkRepository.findAll();
    }

    @Override
    public List<Link> getOldLinks(int delay) {
        return linkRepository.getOldLinks(delay);
    }

    @Override
    public void add(Link link) {
        linkRepository.add(link);
    }

    @Override
    public void remove(Long id) {
        linkRepository.remove(id);
    }

    @Override
    public void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime) {
        linkRepository.updateLinkLastCheckTimeById(id, lastCheckTime);
    }

    @Override
    public LinkSof getLinkPropertiesById(Long id) {
        return linkRepository.getLinkPropertiesById(id);
    }

    @Override
    public void updateCountOfCommentsById(Long id, Long count) {
        linkRepository.updateCountOfCommentsById(id, count);

    }

    @Override
    public void updateCountOfAnswersById(Long id, Long count) {
        linkRepository.updateCountOfCommentsById(id, count);

    }
}
