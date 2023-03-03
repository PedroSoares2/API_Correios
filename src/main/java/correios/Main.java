package correios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Main {
	
	private static ConfigurableApplicationContext ctx;
	
	public static void main(String[] args) {
		ctx = SpringApplication.run(Main.class, args);
	}
	
	public static void close(int code) {
		SpringApplication.exit(ctx, () -> code);
	}

}