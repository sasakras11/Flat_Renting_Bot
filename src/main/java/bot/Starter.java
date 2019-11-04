package bot;

import bot.service.BotService;
import bot.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableJpaRepositories
@SpringBootApplication(exclude = WebMvcAutoConfiguration.class)
public class Starter {

@Autowired
    UserRepository repository;

    public static void main(String[] args) {
        //Add this line to initialize bots context
        ApiContextInitializer.init();



        SpringApplication.run(Starter.class);



    }


}
