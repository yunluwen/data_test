package datastatus.sort;

/**
 * 冒泡排序
 */
public class BubbleSort {

    public static void bubbleSort(int[] arr){
        int length = arr.length;
        for(int i=0;i<length-1;i++){
            for(int j=0;j<length-i-1;j++){
                int temp = arr[j+1];
                arr[j+1] = arr[j];
                arr[j] = temp;
            }
        }
    }
}
