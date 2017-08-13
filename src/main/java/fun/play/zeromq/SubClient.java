package fun.play.zeromq;

import java.time.Instant;
import java.util.Scanner;

import org.zeromq.ZMQ;

public class SubClient {
	public static void main(String[] args) {
		ZMQ.Context context = ZMQ.context(1);
		
		ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
		subscriber.connect("ipc://weather");
		
		//  Subscribe to zipcode, default is NYC, 10001
		String filter = (args.length > 0) ? args[0] : "10001 ";
		subscriber.subscribe(filter.getBytes());
		
		//  Process 100 updates
		int update_nbr;
		long total_temp = 0;
		for (update_nbr = 0; update_nbr < 100; update_nbr++) {
		    //  Use trim to remove the tailing '0' character
		    String string = subscriber.recvStr(0).trim();
		    try(Scanner scanner = new Scanner(string)){
		    	scanner.nextInt();
		    	total_temp += scanner.nextInt();
		    }
		    System.out.printf("%s -> %d\n", Instant.now(),update_nbr);
		}
		System.out.println("Average temperature for zipcode '"
		        + filter + "' was " + (int) (total_temp / update_nbr));
		
		subscriber.close();
		context.term();
    }

}
