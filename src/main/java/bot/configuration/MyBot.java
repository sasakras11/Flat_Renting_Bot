package bot.configuration;


import bot.model.User;
import bot.service.AppContext;
import bot.service.BotService;
import bot.service.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.NoSuchElementException;

@Component
public class MyBot  extends TelegramLongPollingBot {

    private BotService service;

    @Autowired

    public void setService(BotService service) {
        this.service = service;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(!update.getMessage().hasText()){
            return;
        }
        final String text = update.getMessage().getText();
        final long chatId = update.getMessage().getChatId();

        User user = service.getByChatId(chatId);


        AppContext context  = null;
        State state;


        if(user==null) {

            state = State.getInitialState();

            user = new User(chatId, state.ordinal());
            service.saveUser(user);

            context = AppContext.of(user, this, text);
            state.enter(context);
            System.out.println("new user registered" + chatId);

        }
        else {

            context = AppContext.of(user, this, text);
            state = State.byId(user.getState());
            System.out.println("Update received for user in state"+state);
        }

     state.HandleInput(context);

        do{
             state = state.nextState();
             state.enter(context);

        }while(!state.isInputNeeded());
        user.setState(state.ordinal());
        service.saveUser(user);


    }

    @Override
    public String getBotUsername() {
        return "boot_testBot";
    }

    @Override
    public String getBotToken() {
        return "1008411604:AAEOA75nYRpvCpJ2OimMIp94UJBwZW_hlQk";
    }
}
