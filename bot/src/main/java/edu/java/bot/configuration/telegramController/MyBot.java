package edu.java.bot.configuration.telegramController;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class MyBot extends TelegramBot {


    private final TelegramBot bot ;
    public MyBot(String botToken, TelegramBot bot) {
        super(botToken);
        this.bot = new TelegramBot(botToken);;
        }

        public void dosmth(){
            bot.setUpdatesListener(updates -> {
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }, e -> {
                if (e.response() != null) {
                    e.response().errorCode();
                    e.response().description();
                } else {
                    e.printStackTrace();
                }
            });
        }

        public SendResponse SendMessage(Update update){
            long chatId = update.message().chat().id();
            SendResponse response = bot.execute(new SendMessage(chatId, "Hello!"));
            return response;
        }







}
