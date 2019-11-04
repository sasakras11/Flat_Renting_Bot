package bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public enum  State {

    Start{                               //when user start dialog with bot
        @Override
        public void enter(AppContext context) {
    sendMessage(context,"Привіт! Я ваш тоббі і я шукаю квартирки за вилизування очка)");
        }

        @Override
        public State nextState() {
            return EnterCity ;
        }
    },

      EnterCity{                   //when user need to put city for search
          @Override
          public void enter(AppContext context) {
       sendMessage(context,"Яке місто для оренди ви розглядаєте?");
          }

          @Override
          public void HandleInput(AppContext context) {

              context.getUser().setCity(context.getInput());          }

          @Override
          public State nextState() {
              return EnterRoomsNumber;
          }
      },
    EnterRoomsNumber{
        @Override
        public void enter(AppContext context) {
         sendMessage(context,"Скільки кімнат має бути? Напишіть число");
        }

        @Override
        public void HandleInput(AppContext context) {
            context.getUser().setRooms_number(Integer.parseInt(context.getInput()));
        }

        @Override
        public State nextState() {
            return EnterPriceFrom;
        }
    },

       EnterPriceFrom{
           @Override
           public void enter(AppContext context) {
                  sendMessage(context,"Добре,Яка мінімальна ціна оренди тебе цікавить?");
           }

           @Override
           public void HandleInput(AppContext context) {
          context.getUser().setPriceFrom(Integer.parseInt(context.getInput()));
           }

           @Override
           public State nextState() {
               return EnterPriceTo;
           }
       },
    EnterPriceTo{
        @Override
        public void enter(AppContext context) {
             sendMessage(context,"Окей! Яка максимальна ціна оренди?");
        }

        @Override
        public void HandleInput(AppContext context) {
            context.getUser().setPriceTo(Integer.parseInt(context.getInput()));
        }

        @Override
        public State nextState() {
            return Approved;
        }


    },

    Approved(false){
        @Override
        public void enter(AppContext context) {
            sendMessage(context,"Дякую!Ось вільні квартири");

        }

        @Override
        public State nextState() {
            return Start;
        }
    };


    public boolean isInputNeeded() {
        return inputNeeded;
    }

    private final boolean inputNeeded;   //told's bot need he write to user or need to wait
    private static State[] states;

    State(boolean inputNeeded) {
        this.inputNeeded = inputNeeded;

    }
    State(){
        this.inputNeeded = true;
    }




    public static State byId(int id)
    {
        if(states==null){
            states = State.values();
        }
        return states[id];
    }
    public static State getInitialState(){
        return  byId(0);
    }

      protected void sendMessage(AppContext context,String message){       //sending message
        SendMessage msg = new SendMessage().
                setChatId(context.getUser().getUser_chat_id()).
                          setText(message);

          try {
              context.getBot().execute(msg);
          } catch (TelegramApiException e) {
              e.printStackTrace();
          }

      }



      public void HandleInput(AppContext context){

      }

      public abstract void enter(AppContext context);
    public abstract State nextState();


}
