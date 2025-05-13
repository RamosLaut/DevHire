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
		UserEntity userEntity = UserEntity.builder().name("lautaro").build();
		UserEntity userEntity1 = UserEntity.builder().name("juan").build();
		PublicationEntity publicationEntity = PublicationEntity.builder().content("hola mundo").publicationDate(Timestamp.from(Instant.now())).build();
		PublicationEntity publicationEntity2 = PublicationEntity.builder().content("API devhire").publicationDate(Timestamp.from(Instant.now())).build();
		PublicationEntity publicationEntity3 = PublicationEntity.builder().content("Esta funcionando?").publicationDate(Timestamp.from(Instant.now())).build();
		PublicationService.save(publicationEntity);
		PublicationService.save(publicationEntity2);
		PublicationService.save(publicationEntity3);
	}

}
