public class OptimizedInsertionSort {
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            /* Shift elements of arr[0..i-1] that are greater than key
               to one position ahead of their current position 
            */
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            // Place the key in its correct sorted position
            arr[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        int[] data = {12, 11, 13, 5, 6};
        insertionSort(data);
        System.out.println("Sorted Array: " + java.util.Arrays.toString(data));
    }
}