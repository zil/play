package fun.play.alog.list;

public class LinkedList<T> {
	private class Node{
		Node next;
		T val;
		public Node(Node next,T val) {
			this.next = next;
			this.val = val;
		}
	}
	
	private Node head;
	private Node tail;
	private int size;
	
	public Node head(){return this.head;};
	public Node tail(){return this.tail;};
	public int size(){ return size; }
	public boolean empty(){return size == 0;}
	
	void add(T val){
		assert null != val;
		Node node = new Node(null, val);
		if(empty()){
			head = node;
			tail = node;
		}else {
			tail.next = node;
			tail = node;
		}
		size++;
	}
	
	void addToHead(T val){
		Node node = new Node(null, val);
		if(empty()){
			head = node;
			tail = node;
		}else {
			node.next = head;
			head = node;
		}
		size++;
	}
	
	void remove(Node node){
		Node cur = head;
		Node prev = null;
		while (cur != null) {
			if(node == cur){
				if(cur == head){
					head = cur.next;
				}else {
					prev.next = cur.next;
				}
				
				if(cur == tail){
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
	
	T removeHead(){
		if(head != null){
			T ret = head.val;
			head = head.next;
			size--;
			if(null == head){
				tail = null;
			}
			return ret;
		}
		return null;
	}
	
	boolean contains(T val){
		Node cur = head;
		while(cur!=null){
			if(cur.val == val){
				return true;
			}
			cur = cur.next;
		}
		return false;
	}
		
	public static void main(String[] args) {
		LinkedList<String> linkedList = new LinkedList<>();
		linkedList.add("hillo");
		linkedList.add("jkkk");
		linkedList.add("jkkk");
		linkedList.add("jkkk");
		linkedList.add("jkkk");
		assert linkedList.size() == 5;
		linkedList.removeHead();
		linkedList.removeHead();
		assert linkedList.size() == 3;
		
	}
}