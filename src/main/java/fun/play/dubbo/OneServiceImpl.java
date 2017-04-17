package fun.play.dubbo;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class OneServiceImpl implements OneService{
    public String doSth(){
    	return "Hello,client";
    }

	@Override
	public String timeOut() {
		try {
			TimeUnit.MINUTES.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "ok";
	}
}
