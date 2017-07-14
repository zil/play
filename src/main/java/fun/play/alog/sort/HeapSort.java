package fun.play.alog.sort;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.RandomStringUtils;

import com.google.common.base.Stopwatch;

@SuppressWarnings("rawtypes")
public class HeapSort {

	static void sort(Comparable[] items){
		int N = items.length - 1;
		for (int i = N / 2; i >=1; i--) {
			sink(items,i,N);
		}
		
		for(int n = N;n > 1; n--){
			exchg(items,1,n);
			sink(items, 1,n-1);
		}
		
	}

	private static void exchg(Comparable[] items, int i, int j) {
		Comparable c = items[i]; items[i] = items[j]; items[j] = c;
	}

	private static void sink(Comparable[] items, int i, int n) {
		while (2*i <= n) {
			int j = 2*i;
			
			if(j < n && less(items,j,j+1))j++;
			
			if(!less(items, i, j))break;
				
			exchg(items, i, j);
			i = j;
		}
	}

	@SuppressWarnings("unchecked")
	private static boolean less(Comparable[] items,int i, int j) {
		return items[i].compareTo(items[j]) < 0;
	}
	
	public static void main(String[] args) {
		String[] strings = new String[3_000_000];
		strings[0] = "";
		for (int i = 1; i < strings.length; i++) {
			strings[i] = RandomStringUtils.randomAlphanumeric(30);
		}
		Stopwatch stopwatch = Stopwatch.createStarted();
		HeapSort.sort(strings);
		stopwatch.stop();
		System.err.printf("elapsed:%d\n",stopwatch.elapsed(TimeUnit.MILLISECONDS));
		for (int i = 1; i < strings.length - 1; i++) {
			if(strings[i].compareTo(strings[i+1]) >= 0){
				throw new IllegalStateException("not sort: i="+strings[i] + ",j="+strings[i+1]);
			}
		}
	}
}
