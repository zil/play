package fun.play.alog;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;

public class Queue<T> {
	private LinkedList<T> ll;
	public Queue() { this.ll = new LinkedList<>(); }
	
	void add(T item){
		ll.add(item);
	}
	T take(){
		return ll.removeHead();
	}
	T peek(){
		return ll.head();
	}
	int size(){
		return ll.size();
	}
	boolean isEmpty(){
		return ll.isEmpty();
	}
	
	public static void main(String[] args) throws InterruptedException {
		LinkedBlockingQueue<Object> realBlkQueue = new LinkedBlockingQueue<>();
		Queue<Object> queue = new Queue<>();
		Assert.assertEquals(realBlkQueue.isEmpty(),queue.isEmpty());
		
		for (int i = 0; i < 100_0000; i++) {
			Object q1 = RandomStringUtils.randomAlphabetic(30);
			Object q2 = RandomStringUtils.randomAlphabetic(30);
			Object q3 = RandomStringUtils.randomAlphabetic(30);
			
			realBlkQueue.add(q1);queue.add(q1);
			realBlkQueue.add(q2);queue.add(q2);
			realBlkQueue.add(q3);queue.add(q3);
			
			Assert.assertEquals(realBlkQueue.size(), queue.size());
			Assert.assertEquals(realBlkQueue.peek(), queue.peek());

			Assert.assertEquals(realBlkQueue.take(), queue.take());
			Assert.assertEquals(realBlkQueue.size(), queue.size());
			Assert.assertEquals(realBlkQueue.peek(), queue.peek());
		}
		
		while (!realBlkQueue.isEmpty()) {
			Assert.assertFalse(queue.isEmpty());
			queue.take();
			realBlkQueue.take();
		}
		
		Assert.assertTrue(queue.isEmpty());
	}
}
