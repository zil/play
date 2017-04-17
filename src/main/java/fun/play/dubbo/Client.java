package fun.play.dubbo;

import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {

	public static void main(String[] args) {
		System.err.println("client init..");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("dubbo-client.xml");
		OneService bean = ctx.getBean(OneService.class);
		for (int i = 0; i < 1000; i++) {
			/*bean.doSth();
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			bean.timeOut();
		}
	}

}
