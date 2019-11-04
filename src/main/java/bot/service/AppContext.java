package bot.service;

import bot.configuration.MyBot;
import bot.model.User;

public class AppContext {
    private final User user;
    private final MyBot bot;
    private final String input;



   public static AppContext of(User user,MyBot bot,String input){

        return new AppContext(user,bot,input);
    }

    private  AppContext(User user, MyBot bot, String input) {
        this.user = user;
        this.bot = bot;
        this.input = input;
    }

    public User getUser() {
        return user;
    }

    public MyBot getBot() {
        return bot;
    }

    public String getInput() {
        return input;
    }
}
