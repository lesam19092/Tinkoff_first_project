package edu.java.bot.commands;

import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandStartTest {


    private final String unknownUser = "Пользователь не существует. Зарегистрируйтесь с помощью команды /start";
    private final String emptyList = "Вы не отслеживаете ни одной ссылки";

    private CommandStart commandStart;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
        commandStart = new CommandStart(userService);
    }

    @Test
    void command() {
        assertEquals(commandStart.command(),"/start");
    }

    @Test
    void description() {
        assertEquals(commandStart.description(),"Позволяет зарегистрироваться в нашей системе.");
    }

    @Test
    void handle() {
    }

    @Test
    void handleNotRegisted(){
        User mockUser = mock(User.class);

        when(mockUser.getId()).thenReturn(-1l);

        assertEquals(commandStart.handle(null),unknownUser);

    }


}
