package fun.play.alog.search;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;

public class BST<K extends Comparable<K>, V> {
	class Node{
		public Node(K k, V v, int size) {
			this.key = k;
			this.val = v;
			this.size = size;
		}
		K key;
		V val;
		int size;
		Node left;
		Node right;
	}
	
	private Node root;
	
	public void put(K k,V v) {
		if(null == v){
			root = delete(root,k);
		}else{
			root = put(root, k,v);
		}
	}

	private BST<K, V>.Node put(BST<K, V>.Node node, K k, V v) {
		if(null == node) {
			return new Node(k, v, 1);
		}
		if(k.compareTo(node.key) < 0){
			node.left = put(node.left,k, v);
		}else if(k.compareTo(node.key) > 0){
			node.right = put(node.right,k, v);
		}else{
			node.val = v;
		}
		
		node.size = 1 + size(node.left) + size(node.right);
		return node;
	}

	private int size(BST<K, V>.Node node) {
		return node != null ? node.size : 0;
	}

	public void delete(K key) {
		delete(root, key);
	}

	private Node delete(Node node, K k) {
		if(null == node)return node;
		
		if(node.key.compareTo(k) < 0){
			node.left = delete(node.left, k);
		}else if (node.key.compareTo(k) > 0) {
			node.right = delete(node.right, k);
		}else {
			if(node.left == null){
				return node.right;
			}else if (node.right == null) {
				return node.left;
			}else {
				Node tmp = node;
				node = min(tmp.right);
				Node right= delMin(tmp.right);
				node.right = right;
				node.left = tmp.left;
			}
		}
		node.size = 1 + size(node.left) + size(node.right);
		return node;
	}

	private Node min(BST<K, V>.Node node) {
		Node t = node;
		while(t.left !=null )t = t.left;
		
		return t;
		
	}

	public void delMin(){
		root = delMin(root);
	}
	private Node delMin(Node node) {
		if(node.left == null){
			return node.right;
		}else {
			node.left = delMin(node.left);
		}
		node.size = 1 + size(node.left) + size(node.right);
		return node;
	}

	public boolean isBST(){
		return isBST(root,null,null);
	}
	
	private boolean isBST(Node node, K min , K max) {
		if(null == node)return true;
		
		K key = node.key;
		
		if(min != null && key.compareTo(min) <= 0) return false;
		if(max != null && key.compareTo(max) >= 0) return false;
		return isBST(node.left, null,node.key) && isBST(node.right, node.key, null);
	}

	public void chkSize(){
		chkSize(root);
	}
	
	private void chkSize(Node node) {
		if(null == node)return;
		
		Assert.assertEquals(node.size, 1+size(node.left) + size(node.right));
		chkSize(node.left);
		chkSize(node.right);
	}

	public static void main(String[] args) {
		BST<String,String> bst = new BST<>();
		for (int i = 0; i < 100_0000; i++) {
			String string = RandomStringUtils.randomAlphabetic(20);
			bst.put(string, string);
			
			if(i % 100==0){
				bst.delete(string);
			}
		}
		Assert.assertTrue(bst.isBST());
		bst.chkSize();
	}

}
