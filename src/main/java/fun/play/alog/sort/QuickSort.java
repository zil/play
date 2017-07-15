package fun.play.alog.sort;

@SuppressWarnings("rawtypes")
public class QuickSort extends SortBase {

	public static void sort(Comparable[] items) {
		sort(items,0,items.length - 1);
	}
	
	private static void sort(Comparable[] items, int low, int high) {
		if(low >= high)return;
		
		int k = partition(items,low,high);
		sort(items,low,k-1);
		sort(items,k+1,high);
	}

	private static int partition(Comparable[] items, int low, int high) {
		int i = low;
		int j = high + 1;
		Comparable v = items[low];
		
		while(true){
			while(less(items[++i],v))if(i==high)break;
			while(less(v,items[--j]))if(j==low)break;
			if(i>=j)break;
			
			exchg(items, i, j);
		}
		exchg(items, low, j);
		return j;
	}

	public static void main(String[] args) {
		QuickSort.sort(stringsToBeSort);
		verify(stringsToBeSort);
	}

}
