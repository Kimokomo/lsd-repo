package root;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext("root");

        String[] beanNames = context.getBeanDefinitionNames();

        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

        System.out.println("----------------------------------------");

    }
}
