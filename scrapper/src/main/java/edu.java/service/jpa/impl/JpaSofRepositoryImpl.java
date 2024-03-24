package edu.java.service.jpa.impl;

import edu.java.model.dto.LinkSof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface JpaSofRepositoryImpl extends JpaRepository<LinkSof, Long> {
    LinkSof getLinkSofByLinkId(Long id);
}
