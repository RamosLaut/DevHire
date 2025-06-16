package TP_Final.devhire.Config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
@Configuration
public class EnvironmentConfig {

    static {
        Dotenv dotenv = Dotenv.load();

        System.setProperty("jwt.secret", Objects.requireNonNull(dotenv.get("JWT_SECRET")));
        System.setProperty("spring.datasource.username", Objects.requireNonNull(dotenv.get("DB_USERNAME")));
        System.setProperty("spring.datasource.password", Objects.requireNonNull(dotenv.get("DB_PASSWORD")));
    }
}
