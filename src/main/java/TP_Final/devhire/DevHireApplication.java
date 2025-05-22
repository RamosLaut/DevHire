package TP_Final.devhire;

import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Entities.UserEntity;
import TP_Final.devhire.Services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.time.Instant;


@SpringBootApplication
public class DevHireApplication {
	public static void main(String[] args) {
		SpringApplication.run(DevHireApplication.class, args);
	}
	UserEntity user = UserEntity.builder().dni("42454601").email("lauti@email.com").lastName("Ramos").location("Argentina").build();
	PublicationEntity publication = PublicationEntity.builder().content("Hola devuelta").publicationDate(Timestamp.from(Instant.now())).user(user).build();
}
