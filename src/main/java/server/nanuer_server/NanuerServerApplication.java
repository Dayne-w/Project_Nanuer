package server.nanuer_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//데이터 베이스 연결 안해서 오류나서 넣어둔거
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableJpaAuditing
@SpringBootApplication
public class NanuerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NanuerServerApplication.class, args);
		System.out.println("hello");
	}

}
