package edu.java.service.jooq.impl;

import edu.java.model.dto.Link;
import edu.java.model.dto.LinkSof;
import edu.java.repository.LinkRepository;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static edu.java.jooq.Tables.LINK;
import static edu.java.jooq.Tables.LINKS_SOF;

@Repository
public class JooqLinkRepositoryImpl implements LinkRepository {
    @Autowired
    private final DSLContext dslContext;

    public JooqLinkRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void add(Link entity) {
        dslContext.insertInto(LINK)
            .set(LINK.URL, entity.getUrl().toString())
            .set(LINK.CHAT_ID, entity.getChatId())
            .set(LINK.LAST_CHECK_TIME, offsetDateTimeFromTimestamp(entity.getLastCheckTime()))
            .set(LINK.CREATED_AT, offsetDateTimeFromTimestamp(entity.getCreatedAt()))
            .returning(LINK)
            .fetchOne();
    }

    public List<Link> findAll() {
        return dslContext.selectFrom(LINK).fetchInto(Link.class);
    }

    public void remove(Long id) {
        dslContext.deleteFrom(LINK)
            .where(LINK.ID.equal(id))
            .execute();
    }

    public List<Link> getOldLinks(int linkDelay) {
        String sql = String.format(
            "SELECT * FROM link WHERE current_timestamp - last_check_time >  interval '%d seconds'",
            linkDelay
        ); //TODO refactor
        return dslContext.resultQuery(sql)
            .fetchInto(Link.class);
    }

    public void updateLinkLastCheckTimeById(Long id, Timestamp timestamp) {
        dslContext.update(LINK)
            .set(LINK.LAST_CHECK_TIME, offsetDateTimeFromTimestamp(timestamp))
            .where(LINK.ID.eq(id))
            .returning(LINK)
            .fetchOne();
    }

    public LinkSof getLinkPropertiesById(Long id) {
        return dslContext.selectFrom(LINKS_SOF)
            .where(LINKS_SOF.LINK_ID.eq(id))
            .fetchOneInto(LinkSof.class);
    }

    public void updateCountOfCommentsById(Long id, Long count) {
        dslContext.update(LINKS_SOF)
            .set(LINKS_SOF.COUNTOFCOMMENTS, count)
            .where(LINKS_SOF.LINK_ID.eq(id))
            .returning(LINKS_SOF)
            .fetchOne();
    }

    public void updateCountOfAnswersById(Long id, Long count) {
        dslContext.update(LINKS_SOF)
            .set(LINKS_SOF.COUNTOFANSWERS, count)
            .where(LINKS_SOF.LINK_ID.eq(id))
            .returning(LINKS_SOF)
            .fetchOne();
    }

    private OffsetDateTime offsetDateTimeFromTimestamp(Timestamp timestamp) {
        return OffsetDateTime.ofInstant(timestamp.toInstant(), ZoneId.of("UTC"));
    }

}
