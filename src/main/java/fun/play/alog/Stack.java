package fun.play.alog;

import java.util.Iterator;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;

public class Stack<T> implements Iterable<T>{
	LinkedList<T> ll;
	public Stack() {
		ll = new LinkedList<>();
	}
	public void push(T val){
		ll.addToHead(val);
	}
	public T pop(){
		return ll.removeHead();
	}
	public T head(){
		return ll.head();
	}
	public int size(){
		return ll.size();
	}
	public boolean isEmpty(){
		return ll.isEmpty();
	}
	
	public static void main(String[] args) {
		java.util.Stack<String> realStack = new java.util.Stack<>();
		Stack<String> stack = new Stack<>();
		Assert.assertEquals(realStack.isEmpty(), stack.isEmpty());
		for (int i = 0; i < 100_0000; i++) {
			String s1 = RandomStringUtils.randomAlphanumeric(30);
			String s2 = RandomStringUtils.randomAlphanumeric(30);
			String s3 = RandomStringUtils.randomAlphanumeric(30);
			stack.push(s1); realStack.push(s1);
			stack.push(s2); realStack.push(s2);
			stack.push(s3); realStack.push(s3);
			
			Assert.assertEquals(realStack.size(), stack.size());
			Assert.assertEquals(realStack.peek(), stack.head());
			Assert.assertEquals(realStack.pop(), stack.pop());
			Assert.assertEquals(realStack.peek(), stack.head());
			Assert.assertEquals(realStack.isEmpty(), stack.isEmpty());
			
			stack.push(s1); realStack.push(s1);
			stack.push(s2); realStack.push(s2);
			stack.push(s3); realStack.push(s3);
		}
		
	}
	@Override
	public Iterator<T> iterator() {
		return ll.iterator();
	}
}
