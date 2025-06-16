package TP_Final.devhire;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "DevHire API",
				version = "1.0",
				description = "API para conectar desarrolladores con empresas mediante ofertas de empleo."
		)
)
@SpringBootApplication
public class DevHireApplication {
	public static void main(String[] args) {
		SpringApplication.run(DevHireApplication.class, args);
	}

}
