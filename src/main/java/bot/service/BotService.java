package bot.service;

import bot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;


@Component
public class BotService {
   private UserRepository repository;

 @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public void  saveUser(User user){
         repository.save(user);
     }

     public User getByChatId(long id) {

         if (repository.findById(id).isPresent()) {

             return repository.findById(id).get();
                }
             return null;



     }

}
