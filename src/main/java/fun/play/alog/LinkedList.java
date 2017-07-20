package fun.play.alog;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;

public class LinkedList<T> implements Collection<T>{
	private class Node {
		Node next;
		T val;

		public Node(Node next, T val) {
			this.next = next;
			this.val = val;
		}
	}

	private Node head;
	private Node tail;
	private int size;

	public T head() {
		return this.head.val;
	}

	public T tail() {
		return this.tail.val;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean add(T item) {
		assert null != item;
		Node node = new Node(null, item);
		if (isEmpty()) {
			head = node;
			tail = node;
		} else {
			tail.next = node;
			tail = node;
		}
		size++;
		return true;
	}

	void addToHead(T item) {
		Node node = new Node(null, item);
		if (isEmpty()) {
			head = node;
			tail = node;
		} else {
			node.next = head;
			head = node;
		}
		size++;
	}

	public boolean remove(Object item) {
		Node cur = head;
		Node prev = null;
		while (cur != null) {
			if (Objects.equals(item,cur.val)) {
				if (cur == head) {
					head = cur.next;
				} else {
					prev.next = cur.next;
				}

				if (cur == tail) {
					tail = prev;
				}
				size--;
				cur = null;
				return true;
			}
			prev = cur;
			cur = cur.next;
		}
		return false;
	}

	public T removeHead() {
		if (head != null) {
			T ret = head.val;
			head = head.next;
			size--;
			if (null == head) {
				tail = null;
			}
			return ret;
		}
		return null;
	}

	public T get(T item){
		Node cur = head;
		while (cur != null) {
			if (Objects.equals(cur.val,item)) {
				return cur.val;
			}
			cur = cur.next;
		}
		return null;
	}
	
	public boolean contains(Object item) {
		Node cur = head;
		while (cur != null) {
			if (Objects.equals(cur.val,item)) {
				return true;
			}
			cur = cur.next;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(this.getClass().getSimpleName());
		sb.append(" [");
		Node cur = head;
		while (cur != null) {
			if(head != cur)sb.append(" -> ");
			sb.append(cur.val);
			cur = cur.next;
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		LinkedList<String> linkedList = new LinkedList<>();
		int i = 0;
		while (i++ < 10_000) {
			String s1 = RandomStringUtils.randomAlphanumeric(50);
			String s2 = RandomStringUtils.randomAlphanumeric(50);
			String s3 = RandomStringUtils.randomAlphanumeric(50);
			String s4 = RandomStringUtils.randomAlphabetic(50);
			String s5 = RandomStringUtils.randomAlphabetic(50);
			linkedList.add(s1);
			linkedList.add(s2);
			linkedList.add(s3);
			linkedList.addToHead(s4);
			linkedList.addToHead(s5);
			int size = i * 3 + 2;
			
			Assert.assertEquals(size, linkedList.size());
			linkedList.remove(s2);
			Assert.assertEquals(size-1, linkedList.size());
			Assert.assertFalse(linkedList.contains(s2));

			String head = linkedList.removeHead();
			Assert.assertEquals(s5, head);
			Assert.assertFalse(linkedList.contains(s5));
			Assert.assertEquals(size-2,linkedList.size());

		}

	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			Node cur = head;
			
			@Override
			public boolean hasNext() {
				return cur != null; 
			}

			@Override
			public T next() {
				T next = cur.val;
				cur = cur.next;
				return next;
			}
		};
	}

	@Override
	public Object[] toArray() {
		Object[] objs = new Object[size()];
		int j = 0;
		Node cur = head;
		while(cur != null){
			objs[j++] = cur.val;
			cur = cur.next;
		}
		return objs;
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object object : c) {
			if(!contains(object))return false;
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		for (T t : c) {
			add(t);
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object object : c) {
			if(!remove(object))return false;
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		LinkedList<T>.Node cur = head;
		while(cur != null){
			LinkedList<T>.Node next = cur.next;
			cur = null;
			cur = next;
		}
		size = 0;
	}
}
