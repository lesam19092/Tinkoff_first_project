package edu.java.service.jpa;

import edu.java.model.dto.Link;
import edu.java.model.dto.LinkSof;
import edu.java.repository.LinkRepository;
import edu.java.service.jpa.impl.JpaLinkRepositoryImpl;
import edu.java.service.jpa.impl.JpaSofRepositoryImpl;
import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaLinkService implements LinkRepository {

    private final JpaLinkRepositoryImpl jpaLinkRepository;
    private final JpaSofRepositoryImpl jpaSofRepository;

    @Override
    public List<Link> findAll() {
        return jpaLinkRepository.findAll();
    }

    @Override
    public List<Link> getOldLinks(int delay) {
        return jpaLinkRepository.findOldLinks(delay);
    }

    @Override
    public void add(Link link) {
        jpaLinkRepository.save(link);
    }

    @Override
    public void remove(Long id) {
        jpaLinkRepository.removeLinkById(id);
    }

    @Override
    public void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime) {
        jpaLinkRepository.updateLinkLastCheckTimeById(lastCheckTime, id);
    }

    @Override
    public LinkSof getLinkPropertiesById(Long id) {
        return jpaSofRepository.getLinkSofByLinkId(id);
    }

    @Override
    public void updateCountOfCommentsById(Long id, Long count) {
        jpaLinkRepository.updateCountOfCommentsById(id, count);
    }

    @Override
    public void updateCountOfAnswersById(Long id, Long count) {
        jpaLinkRepository.updateCountOfAnswersById(id, count);
    }
}
