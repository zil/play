package fun.play.alog.search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;

import fun.play.alog.LinkedList;

@SuppressWarnings("unchecked")
public class HashTable<K,V> {
	private static final int DEFAULT_CAPACITY = 16;
	private static int ENLARGE_FACTOR= 75;

	class Entry{
		K k;
		V v;
		Entry(K k,V v){
			this.k = k;
			this.v = v;
		}
		@Override
		public boolean equals(Object obj) {
			if(null == obj || !(obj instanceof HashTable.Entry)){
				return false;
			}
			return this.k.equals(((Entry)obj).k);
		}
		@Override
		public String toString() {
			return String.format("%s[%s:%s]", this.getClass().getSimpleName(),k,v);
		}
		
	}
	
	private int capacity = DEFAULT_CAPACITY;
	private LinkedList<Entry> table[];
	private int size;
	
	public HashTable() {
		init();
	}
	
	public HashTable(int capacity){
		this.capacity = capacity;
		init();
	}

	private void init() {
		table = new LinkedList[this.capacity];
		for (int i = 0; i < table.length; i++) {
			table[i]= new LinkedList<>();
		}
	}
	
	public V get(K k){
		LinkedList<Entry> row = table[slot(k)];
		Entry item = new Entry(k, null);
		Entry entry = row.get(item);
		return null != entry ? entry.v : null;
	}
	
	public boolean remove(K k){
		if(null == k){
			throw new IllegalStateException("k must not be null");
		}
		
		int slot = slot(k);
		LinkedList<Entry> row = table[slot];
		Entry item = new Entry(k, null);
		if(row.contains(item)){
			row.remove(item);
			size--;
			return true;
		}
		
		return false;
		
	}

	int slot(K k) {
		return hash(k) % capacity;
	}
	
	public synchronized void put(K k,V v){
		if(null == k || null == v){
			throw new IllegalStateException("k and v must not be null");
		}
		
		LinkedList<Entry> row = table[slot(k)];
		Entry entry = new Entry(k, v);
		Entry entryInTable = row.get(entry);
		if(entryInTable != null){
			entryInTable.v = v;
		}else {
			row.add(entry);
			size++;
		}
		
		maybeEnlarge();
	}

	private void maybeEnlarge() {
		if(size * 100 / capacity > ENLARGE_FACTOR){
			int newCapacity = 2 * capacity;
			
			LinkedList<Entry>[] enlargedTable = new LinkedList[newCapacity];
			for (int i = 0; i < enlargedTable.length; i++) {
				enlargedTable[i] = new LinkedList<>();
			}
			
			for (LinkedList<Entry> linkedList:table) {
				for (Entry entry : linkedList) {
					int newSlot = hash(entry.k) % newCapacity;
					
					enlargedTable[newSlot].add(entry);
				}
			}
			table = enlargedTable;
			capacity = newCapacity;
		}
	}

	private int hash(K k) {
		return Objects.hashCode(k) & 0x7FFFFFFF;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < table.length; i++) {
			LinkedList<Entry> row = table[i];
			if(null != row) sb.append(i).append(" -> ").append(row).append('\n');
		}

		return sb.toString();
	}
	
	public static void main(String[] args) {
		HashMap<String,Integer> hashMap = new HashMap<>();
		HashTable<String, Integer> hashTable = new HashTable<>();
		
		for (int i = 0; i < 100_0000; i++) {
			String key = RandomStringUtils.randomAlphabetic(20);
			hashMap.put(key, i);
			hashTable.put(key, i);
		}

		Set<String> keySet = new HashSet<>(hashMap.keySet());
		for (String string : keySet) {
			Assert.assertTrue(hashTable.contains(string));
			Assert.assertEquals(hashMap.get(string), hashTable.get(string));
			hashMap.remove(string);hashTable.remove(string);
			Assert.assertEquals(hashMap.size(), hashTable.size());
			Assert.assertFalse(hashTable.contains(string));
		}
		Assert.assertEquals(hashMap.size(), hashTable.size());
		
	}

	private boolean contains(K key) {
		int slot = slot(key);
		LinkedList<HashTable<K, V>.Entry> linkedList = table[slot];
		for (Entry entry : linkedList) {
			if(entry.k.equals(key))return true;
		}
		return false;
	}

	public int size() {
		return this.size;
	}
	
}
