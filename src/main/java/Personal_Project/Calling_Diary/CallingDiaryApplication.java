package Personal_Project.Calling_Diary;

import Personal_Project.Calling_Diary.repository.MemberRepository;
import org.junit.Assert;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class CallingDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CallingDiaryApplication.class, args);
	}
}
