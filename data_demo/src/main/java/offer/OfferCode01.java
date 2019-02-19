package offer;

/**
 * 注意二维数组的结构，是数组中存放多个数组
 * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
 * 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class OfferCode01 {

    public static boolean Find(int target, int [][] array) {
        int rows = array.length;    //行
        int cols = array[0].length; //列，array[0],array[1]...都没问题
        int i=rows-1,j=0;
        while(i>=0 && j<cols){      //从左下的位置开始比较
            if(target<array[i][j])  //小于
                i--;                //上升一个位置
            else if(target>array[i][j])  //大于
                j++;                //右移一个位置
            else
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] array = {{1,2,5,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
        System.out.println(Find(5,array));
    }
}


