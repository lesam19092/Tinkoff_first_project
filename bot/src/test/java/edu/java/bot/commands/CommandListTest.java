package edu.java.bot.commands;

import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandListTest {

    private final String unknownUser = "Пользователь не существует. Зарегистрируйтесь с помощью команды /start";
    private final String emptyList = "Вы не отслеживаете ни одной ссылки";

    private CommandList commandList;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
        commandList = new CommandList(userService);
    }

    @Test
    void command() {
        assertEquals(commandList.command(), "/list");
    }

    @Test
    void description() {
        assertEquals(commandList.description(), "Позволяет получить список отслеживаемых ссылок.");
    }

    @Test
    void getTrackingLinksNotRegistred() {
        assertEquals(commandList.getTrackingLinks(-1L), unknownUser);
    }

    @Test
    void getTrackingLinksEmptyList() {
        User mockUser = mock(User.class);

        List<URI> sites = new ArrayList<>();

        when(mockUser.getSites()).thenReturn(sites);
        when(mockUser.getId()).thenReturn(10L);
        userService.saveUser(mockUser);
        assertEquals(commandList.getTrackingLinks(10L), emptyList);

    }

    @Test
    void getTrackingLinks() throws URISyntaxException {
        User mockUser = mock(User.class);
        List<URI> sites = new ArrayList<>();
        sites.add(new URI("https://github.com"));

        when(mockUser.getSites()).thenReturn(sites);
        when(mockUser.getId()).thenReturn(10L);
        userService.saveUser(mockUser);
        assertEquals(commandList.getTrackingLinks(10L), "1) https://github.com\n");


    }
}
