package edu.java.scrapper;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcLinkTest extends IntegrationTest {
    /*@Autowired
    private JdbcLinkDao linkRepository;
    @Autowired
    private JdbcChatDao chatRepository;
    @Autowired
    private JdbcClient jdbcClient;

    @Test
    @Transactional
    @Rollback
    void addTest() {
       *//* // Mock data
        LinkDto linkDto = new LinkDto();
        linkDto.setUrl("https://www.example.com");
        linkDto.setChatId(12345L);
        linkDto.setLastCheckTime(OffsetDateTime.now());
        linkDto.setCreatedAt(OffsetDateTime.now());
        linkDto.setCreatedBy("test_user");

        // Mock jdbcTemplate behavior
        Map<String, Object> params = new HashMap<>();
        params.put("url", linkDto.getUrl());
        params.put("chatId", linkDto.getChatId());
        params.put("lastCheckTime", linkDto.getLastCheckTime());
        params.put("createdAt", linkDto.getCreatedAt());
        params.put("createdBy", linkDto.getCreatedBy());

        BigInteger mockId = BigInteger.valueOf(1L);
        GeneratedKeyHolder mockKeyHolder = Mockito.mock(GeneratedKeyHolder.class);
        Mockito.when(mockKeyHolder.getKeyAs(BigInteger.class)).thenReturn(mockId);

        Mockito.when(JdbcClient.class).invoke("update", Mockito.anyString(), Mockito.anyMap())
            .thenReturn(1);
        Mockito.when(jdbcClient.update(Mockito.anyString(), Mockito.anyMap(), Mockito.any(KeyHolder.class)))
            .thenReturn(1, mockKeyHolder);

        // Call the method
        linkRepository.add(linkDto);

        // Assertions
        assertEquals(mockId.longValue(), linkDto.getId());
        Mockito.verify(jdbcClient, times(1)).update(Mockito.anyString(), params);
*//*
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
    }*/
}
