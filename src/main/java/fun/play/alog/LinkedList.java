package fun.play.alog;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;

public class LinkedList<T> {
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

	public boolean empty() {
		return size == 0;
	}

	void add(T val) {
		assert null != val;
		Node node = new Node(null, val);
		if (empty()) {
			head = node;
			tail = node;
		} else {
			tail.next = node;
			tail = node;
		}
		size++;
	}

	void addToHead(T val) {
		Node node = new Node(null, val);
		if (empty()) {
			head = node;
			tail = node;
		} else {
			node.next = head;
			head = node;
		}
		size++;
	}

	void remove(T val) {
		Node cur = head;
		Node prev = null;
		while (cur != null) {
			if (val == cur.val) {
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
				break;
			}
			prev = cur;
			cur = cur.next;
		}
	}

	T removeHead() {
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

	boolean contains(T val) {
		Node cur = head;
		while (cur != null) {
			if (cur.val == val) {
				return true;
			}
			cur = cur.next;
		}
		return false;
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
}
