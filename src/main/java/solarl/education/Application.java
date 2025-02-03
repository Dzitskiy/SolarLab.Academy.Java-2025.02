package solarl.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        System.out.println("Application is running...");

        Thread.sleep(2000);
        TestBean testBean = (TestBean) context.getBean("testBean");

        testBean.run();

        Thread.sleep(2000);
        System.out.println("Application stopping...");
        context.close();
    }
}
