package fun.play.kafka;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import com.google.common.base.Stopwatch;

public class Consumer {
    public static void main(String[] args) throws InterruptedException {
	ExecutorService es = Executors.newCachedThreadPool();
	Stopwatch stopwatch = Stopwatch.createStarted();
	CountDownLatch latch = new CountDownLatch(4);
	for (int i = 0; i < 4; i++) {//四个消费者
	    es.submit(new Runnable() {
		    @Override
		    public void run() {
			try {
			    createConsumerAndPoll();
			    latch.countDown();
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
		    }
		});
	}
	latch.await();
	System.err.println("elapsed:" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    private static AtomicInteger consumedTotal = new AtomicInteger();
	
    private static KafkaConsumer<String, String> createConsumerAndPoll() throws InterruptedException {
	Properties cprops = new Properties();
	cprops.put("bootstrap.servers", "localhost:32780");
	cprops.put("group.id", "uber"); //组id
	cprops.put("enable.auto.commit", "true");
	cprops.put("auto.commit.interval.ms", "1000");
	cprops.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	cprops.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	KafkaConsumer<String, String> consumer = new KafkaConsumer<>(cprops);
	consumer.subscribe(Arrays.asList("uber"));//只有订阅方法，server才会自动给当前消费者分配分区
	
	consumer.poll(1000);
	Set<TopicPartition> assignment = consumer.assignment();
	for (TopicPartition topicPartition : assignment) {
	    System.err.printf("%s.pos=%d\n",topicPartition,consumer.position(topicPartition));
	    consumer.seek(topicPartition,0L);
	}
	while(true){
	    ConsumerRecords<String, String> records = consumer.poll(200);
	    if (!records.isEmpty()) {
		System.err.printf("%s,%d\n",Thread.currentThread(),records.count());
		consumedTotal.addAndGet(records.count());
	    }else{
		
	    }
	    if(consumedTotal.get() > 1000_0000)break;//所有消费者消费够１０００万条记录才停止
	}
	assignment = consumer.assignment();
	for (TopicPartition topicPartition : assignment) {
	    System.err.printf("%s.pos=%d\n",topicPartition,consumer.position(topicPartition));
	}
	return consumer;
    }
}
