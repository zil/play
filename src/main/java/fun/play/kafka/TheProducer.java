package fun.play.kafka;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;

public class TheProducer 
{
    public static void main( String[] args ) throws IOException
    {
	CharMatcher doubleQuoteTrimmer = CharMatcher.is('"');
	//把每行记录按逗号分割后的值去除首尾的双引号
	Splitter splitter = Splitter.on(',').trimResults(doubleQuoteTrimmer);
	
	Stopwatch stopwatch = Stopwatch.createStarted();
	Producer<String, String> producer = newProducer();
	String line;
	try (InputStream in = TheProducer.class.getResourceAsStream("uber-raw-data-janjune-15.csv")){
	    LineNumberReader lineNumberReader;//行数太多，需流式读入，否则一次读入堆会溢出
	    lineNumberReader = new LineNumberReader(new InputStreamReader(in, Charsets.UTF_8));
			
	    while((line = lineNumberReader.readLine()) != null){
		List<String> columns = splitter.splitToList(line);
		if(columns.size() != 4) continue;
				
		String key = String.join("_", columns.subList(1, 3));
		int lineNumber = lineNumberReader.getLineNumber();
		int partition = lineNumber % 4;//记录依所在行数发送到不同的四个分区
		ProducerRecord<String, String> record =
		    new ProducerRecord<>("uber",partition,key,line);
		producer.send(record);
	    }
	    //处理的条数
	    System.err.println("line: "+lineNumberReader.getLineNumber());
	} catch (Exception e) {
	    e.printStackTrace();
	}

    	System.err.println("写入耗时（毫秒）:" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
	producer.close(3,TimeUnit.SECONDS);
    }

    private static Producer<String, String> newProducer() {
	Properties props = new Properties();
	props.put("bootstrap.servers", "localhost:32780");
	props.put("acks", "all");
	props.put("retries", 0);
	props.put("batch.size", 16384);
	props.put("request.timeout.ms", 1000);
	props.put("max.block.ms", 1000);
	props.put("timeout.ms", 3000);
	props.put("linger.ms", 1);
	props.put("buffer.memory", 33554432);
	props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

	return new KafkaProducer<>(props);
    }
}
