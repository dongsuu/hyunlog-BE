package donghyunlee.hyunlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HyunlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(HyunlogApplication.class, args);
	}

}
