package fun.play.alog.sort;

@SuppressWarnings("rawtypes")
public class MergeSort extends SortBase{
	private static Comparable[] aux;
	
	static void sort(Comparable[] items){
		aux = new Comparable[items.length];
		mergeIt(items,0,items.length - 1);
	}
	private static void mergeIt(Comparable[] items, int low, int high) {
		if(low >= high)return;
		
		int mid = (low + high) / 2;
		mergeIt(items, low, mid);
		mergeIt(items, mid + 1, high);
		merge(items,low,mid, high);
	}
	
	private static void merge(Comparable[] items, int low, int mid, int high) {
		for (int i = low; i <= high; i++) {
			aux[i] = items[i];
		}
		
		int k = low,i=low,j=mid + 1;
		while (k<=high) {
			if(i > mid){
				items[k++] = aux[j++]; 
			}else if (j > high) {
				items[k++] = aux[i++];
			}else if (less(aux, i, j)) {//not less(items,i,j) debug it for 90 minutes!!
				items[k++] = aux[i++];
			}else {
				items[k++] = aux[j++];
			}
		}
	}
	
	public static void main(String[] args) {
		MergeSort.sort(stringsToBeSort);
		verify(stringsToBeSort);
	}
}
