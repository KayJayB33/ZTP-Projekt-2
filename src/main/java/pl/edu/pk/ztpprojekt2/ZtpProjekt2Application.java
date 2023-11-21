package pl.edu.pk.ztpprojekt2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ZtpProjekt2Application {

    public static void main(String[] args) {
        SpringApplication.run(ZtpProjekt2Application.class, args);
    }

}
