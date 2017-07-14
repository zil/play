package fun.play.alog.sort;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

@SuppressWarnings("rawtypes")
public class HeapSort extends SortBase{

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

	private static void sink(Comparable[] items, int i, int n) {
		while (2*i <= n) {
			int j = 2*i;
			
			if(j < n && less(items,j,j+1))j++;
			
			if(!less(items, i, j))break;
				
			exchg(items, i, j);
			i = j;
		}
	}
	
	public static void main(String[] args) {
		Stopwatch stopwatch = Stopwatch.createStarted();
		HeapSort.sort(stringsToBeSort);
		stopwatch.stop();
		System.err.printf("elapsed:%d\n",stopwatch.elapsed(TimeUnit.MILLISECONDS));
		verify(stringsToBeSort);
	}
}
