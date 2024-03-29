package edu.java.service.jdbc;

import edu.java.model.dto.Link;
import edu.java.service.LinkRepository;
import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkService {

    private final LinkRepository linkRepository;

    public List<Link> getLinks() {
        return linkRepository.findAll();
    }

    public List<Link> getOldLinks(int delay) {
        return linkRepository.getOldLinks(delay);
    }

    public void addLink(Link link) {
        linkRepository.add(link);
    }

    public void removeLink(Long id) {
        linkRepository.remove(id);
    }

    public void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime) {
        linkRepository.updateLinkLastCheckTimeById(id, lastCheckTime);
    }

}
