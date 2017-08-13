package fun.play.redis;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;

import fun.play.kafka.TheProducer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Counting {
	public static void main(String[] args) {
    	CharMatcher doubleQuoteTrimmer = CharMatcher.is('"');
    	//把每行记录按逗号分割后的值去除首尾的双引号
    	Splitter splitter = Splitter.on(',').trimResults(doubleQuoteTrimmer);
    	
    	List<String> bufferList = Lists.newArrayListWithCapacity(100);
    	Stopwatch stopwatch = Stopwatch.createStarted();
    	String line;
    	try ( JedisPool pool = new JedisPool();
    		  InputStream in = TheProducer.class.getResourceAsStream("/uber-raw-data-janjune-15.csv")){
  
    		//Stream<String>
    		LineNumberReader lineNumberReader;//行数太多，需流式读入，否则一次读入堆会溢出
    		lineNumberReader = new LineNumberReader(new InputStreamReader(in, Charsets.UTF_8));
    		
    		while((line = lineNumberReader.readLine()) != null){
    			List<String> columns = splitter.splitToList(line);
    			if(columns.size() != 4) continue;
    			
    			String key = String.join("_", columns);
    			bufferList.add(key);
    			if(bufferList.size() == 20){
    				try(Jedis resource = pool.getResource()) {
    					resource.pfadd("uberCount",
    							bufferList.get(0),
    							bufferList.get(1),
    							bufferList.get(2),
    							bufferList.get(3),
    							bufferList.get(4),
    							bufferList.get(5),
    							bufferList.get(6),
    							bufferList.get(7),
    							bufferList.get(8),
    							bufferList.get(9),
    							bufferList.get(10),
    							bufferList.get(11),
    							bufferList.get(12),
    							bufferList.get(13),
    							bufferList.get(14),
    							bufferList.get(15),
    							bufferList.get(16),
    							bufferList.get(17),
    							bufferList.get(18),
    							bufferList.get(19) 
    							);
    				} catch (Exception e) {
    					System.err.printf("counting error:%s\n",e.getMessage());
    				}
    				bufferList.clear();
    			}
    		}
    		//处理的条数
    		System.err.println("line: "+lineNumberReader.getLineNumber());
    		try (Jedis jedis = pool.getResource()){
				long pfcount = jedis.pfcount("uberCount");
				System.err.println("hyperloglog.count:" + pfcount);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	System.err.println("elapsed.ms:" + stopwatch.elapsed(TimeUnit.SECONDS));

	}
}
