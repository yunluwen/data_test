package base;

import java.util.Arrays;

/**
 * 快排的时间复杂度：
 *
 */
public class QuickSort {

    /**
     * 排序(递归)
     * @param arr
     * @param left
     * @param right
     */
    public static void quickSort(int [] arr,int left,int right){
        int pivot = 0;
        if(left < right) {
            pivot = partition(arr,left,right);
            quickSort(arr,left,pivot-1);
            quickSort(arr,pivot+1,right);
        }
    }

    /**
     * 拆分
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private static int partition(int[] arr,int left,int right) {
        //{23,58,13,10,57,62,65,106,78,95,85}

        int key = arr[left];  //65,这个key一直都是第一个值
        while(left<right){    //left小于right,直到left和right相遇
            //循环如果right的值大于等于key
            while(left < right && arr[right] >= key) {
                //将right指针向左移动
                right--;
            }
            //如果循环right的值小于left的值，那将left和right交换位置
            arr[left] = arr[right];          ////{23,58,95,10,57,62,13,106,78,23,85}

            //循环如果left的值小于等于key
            while(left < right && arr[left] <= key) {
                left++;
            }
                                          //     交换的位置left           right的位置              上一个right的位置
            arr[right] = arr[left];       //{23,58,   13   ,10,57,62,       13       , 106,78,     95      ,85}
        }                                 //                   （执行到这里的时候，right==left,循环终止）

        arr[left] = key;                  //{23,58,   13   ,10,57,62,       65       , 106,78,     95      ,85}
        return left;                      //获取拆分元素后的窗口变量的下标
    }

    public static void main(String[] args) {

        int [] arr = {65,58,95,10,57,62,13,106,78,23,85};
        System.out.println("排序前："+Arrays.toString(arr));
        quickSort(arr,0,arr.length-1);
        System.out.println("排序后："+Arrays.toString(arr));

    }
}
