package fun.play.alog.sort;

import org.apache.commons.lang.RandomStringUtils;

@SuppressWarnings({"rawtypes","unchecked"})
public abstract class SortBase {

	static String[] stringsToBeSort; 
	static{
		stringsToBeSort = new String[1000_000];
		stringsToBeSort[0] = RandomStringUtils.randomAlphanumeric(20);
		for (int i = 1; i < stringsToBeSort.length; i++) {
			stringsToBeSort[i] = RandomStringUtils.randomAlphanumeric(20);
		}
	}
	
	static void verify(Comparable[] items){
		verify(items, 0, items.length - 1 );
	}

	static void verify(Comparable[] items, int low, int high) {
		for (int i = low; i < high - 1; i++) {
			if(stringsToBeSort[i].compareTo(stringsToBeSort[i+1]) > 0){
				throw new IllegalStateException(
						String.format("items[%d,%s] >= items[%d,%s]",i,stringsToBeSort[i],i+1,stringsToBeSort[i+1]));
			}
		}
	}
	
	static boolean less(Comparable c, Comparable v) {
		return c.compareTo(v) < 0;
	}
	
	public static boolean less(Comparable[] items,int i, int j) {
		return items[i].compareTo(items[j]) < 0;
	}
	
	public static void exchg(Comparable[] items, int i, int j) {
		Comparable c = items[i]; items[i] = items[j]; items[j] = c;
	}
}
