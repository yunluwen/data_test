package base;

import java.util.Arrays;

/**
 * 快速排序测试
 */
public class TestQuerySort {

    public int partition(int[] arr,int left,int right){
        int key = arr[left];

        while(left<right){

            while(left<right && arr[right]>=key){
                right--;
            }
            arr[left] = arr[right];

            while(left<right && arr[left]<=key){
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = key;
        return left;
    }

    public void quickSort(int arr[],int left,int right){
        int a=0;
        if(left<right){
            a=partition(arr,left,right);
            quickSort(arr,left,a-1);
            quickSort(arr,a+1,right);
        }
    }

    public static void main(String[] args) {
        int[] arr = {5,2,4,6,3,25,4,3,425,6,75342,2,267,23};
        //int [] arr = {65,58,95,10,57,62,13,106,78,23,85};
        TestQuerySort testQuerySort = new TestQuerySort();
        testQuerySort.quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

}
