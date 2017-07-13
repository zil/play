package fun.play.alog.sort;

import javax.sound.midi.SysexMessage;

@SuppressWarnings("rawtypes")
public class BasicSort {
	public static void selectSort(Comparable[] items) {
		int length = items.length;
		for (int i = 0; i < length-1; i++) {
			int min = i;
			for (int j = i+1; j < items.length; j++) {
				if(less(items[j],items[min])){
					min = j;
				}
			}
			exchange(items,i,min);
		}
	}
	
	public static void insertSort(Comparable[] items) {
		int N = items.length;
		for (int i = 1; i < N; i++) {
			int idx = i;
			while(idx >= 1 && less(items[idx], items[idx - 1])){
				exchange(items, idx, idx - 1);
				idx--;
			}
			
		}
	}
	
	public static void shellSort(Comparable[] items) {
		int N = items.length;
		int h = 1;
		while(h < N / 3) h = h * 3 + 1; // why +1
		System.err.println("N="+N);
		while(h >= 1){
			System.err.printf("h=%d\n",h);
			for (int i = h; i < N; i++) {
				int idx = i;
				while(idx >= h && less(items[idx], items[idx - h])){
					exchange(items, idx, idx - h);
					idx-=h;
				}
			}
			h /= 3;
		}
	}

	private static void exchange(Comparable[] items, int i, int j) {
		Comparable tmp = items[i];
		items[i]=items[j];
		items[j]=tmp;
	}
	
	@SuppressWarnings("unchecked") 
	private static boolean less(Comparable a, Comparable b) {
		return a.compareTo(b) < 0;
	}
	
	public static void main(String[] args) {
		String[] strings = {"R","a","n","d","o","m","S","t","r","i","n","g"};
		printStrings(strings);

		BasicSort.selectSort(strings);
		printStrings(strings);
		
		strings = new String[]{"R","a","n","d","o","m","S","t","r","i","n","g"};
		BasicSort.insertSort(strings);
		printStrings(strings);
		
		strings = new String[]{"R","a","n","d","o","m","S","t","r","i","n","g"};
		BasicSort.shellSort(strings);
		printStrings(strings);
	}

	private static void printStrings(String[] strs) {
		for (String s: strs) {
			System.err.print(s);
		}
		System.err.println();
	}
}
