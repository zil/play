package fun.play.dubbo;

import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class App {
    public static void main(String[] args) throws InterruptedException{
     ApplicationContext ctxServer = new ClassPathXmlApplicationContext("dubbo-server.xml");
	 TimeUnit.MINUTES.sleep(5);
    }
}
