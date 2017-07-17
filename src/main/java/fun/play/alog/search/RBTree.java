package fun.play.alog.search;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;

public class RBTree<K extends Comparable<K>, V> {
	class Node{
		K key;
		V val;
		int color;
		Node left;
		Node right;
		int size;
		
		public Node(K key,V val,int size, int color) {
			this.key = key;
			this.val = val;
			this.size = size;
			this.color = color;
		}
	}
	
	private Node root;
	private final static int BLACK = 0;
	private final static int RED = 1;
	
	public V get(K k){
		return get(root,k);
	}
	
	private V get(Node n, K k) {
		if(null == n)return null;
		
		int cmp = k.compareTo(n.key);
		if(cmp < 0){
			return get(n.left, k);
		}else if (cmp > 0) {
			return get(n.right, k);
		}else {
			return n.val;
		}
	}

	void put(K key,V val){ root = put(root,key,val);root.color = BLACK; }
	
	private Node put(Node node, K key, V val) {
		if(null == node){
			return new Node(key, val, 1,RED);
		}
		
		int cmp = key.compareTo(node.key);
		if(cmp < 0){
			node.left = put(node.left, key, val);
		}else if(cmp > 0){
			node.right = put(node.right, key, val);
		}else {
			node.val = val;
		}
		
		if(isRed(node.right) && !isRed(node.left)){
			node = rotateLeft(node);
		}
		
		if (isRed(node.left) && isRed(node.left.left)) {
			node = rotateRight(node);
		}
		
		if (isRed(node.left) && isRed(node.right)) {
			flipColor(node);
		}
		
		node.size = 1 + size(node.left) + size(node.right);
		return node;
	}

	private int size(RBTree<K, V>.Node node) {
		return null != node ? node.size : 0;
	}

	private void flipColor(RBTree<K, V>.Node h) {
		h.color = RED;
		h.left.color= BLACK;
		h.right.color= BLACK;
	}

	private Node rotateRight(RBTree<K, V>.Node h) {
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color;
		x.size = h.size;
		h.color = RED;
		
		h.size = 1 + size(h.left) + size(h.right);
		return x;
	}

	private Node rotateLeft(RBTree<K, V>.Node h) {
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		x.size = h.size;
		h.color = RED;
		
		h.size = 1 + size(h.left) + size(h.right);
		
		return x;
	}

	private boolean isRed(Node node) {
		return null != node && node.color == RED;
	}

	public int size(){
		return root.size;
	}
	
	public boolean check(){
		if(!isBST())System.err.println("not a BST");
		if(!is23())System.err.println("not a 2-3 tree");
		if(!isBalanced())System.err.println("not balanced");
		return isBST() && is23() && isBalanced();
	}
    private boolean isBST() {
        return isBST(root, null, null);
    }

    private boolean isBST(Node x, K min, K max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    } 

    private boolean is23() { return is23(root); }
    private boolean is23(Node x) {
        if (x == null) return true;
        if (isRed(x.right)) return false;
        if (x != root && isRed(x) && isRed(x.left))
            return false;
        return is23(x.left) && is23(x.right);
    } 

    private boolean isBalanced() { 
        int black = 0;
        Node x = root;
        while (x != null) {
            if (!isRed(x)) black++;
            x = x.left;
        }
        return isBalanced(root, black);
    }

    private boolean isBalanced(Node x, int black) {
        if (x == null) {
        	if(black != 0) System.err.println("to.null.black="+black);
        	return black == 0;
        }
        if (!isRed(x)) black--;
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    } 

    public static void main(String[] args) {
        RBTree<String, Integer> st = new RBTree<String, Integer>();
        int N = 100_0000;
        for (int i = 0; i < N; i++){
        	st.put(RandomStringUtils.randomAlphanumeric(20), i); 
        }
        
        Assert.assertTrue(st.check());
        Assert.assertEquals(N,st.size());

    }

}
