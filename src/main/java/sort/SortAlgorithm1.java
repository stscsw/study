package sort;


import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.Random;

/**
 * 冒泡排序  选择排序  插入排序  希尔排序  快速算法
 */
public class SortAlgorithm1 {

    public static int[] arr;

    static {
        Random r = new Random();
        arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(200);
        }
    }


    //冒泡
    @Test
    public void bubbleSort() {
        for (int out = arr.length - 1; out > 0; out--) {
            for (int in = 0; in < out; in++) {
                if (arr[in] > arr[in + 1]) {
                    ArrayUtils.swap(arr, in, in + 1);
                }
            }
        }
        for (int i : arr) {
            System.out.print(i + ",");
        }
    }

    //选择
    @Test
    public void selectSort() {
        int size = arr.length;
        for (int out = 0; out < size; out++) {
            int midIndex = out;
            for (int in = out + 1; in < size; in++) {
                if (arr[midIndex] > arr[in]) {
                    midIndex = in;
                }
            }
            if (midIndex != out) {
                ArrayUtils.swap(arr, midIndex, out);
            }
        }
        for (int i : arr) {
            System.out.print(i + ",");
        }
    }


    //插入算法
    @Test
    public void insertSort() {
        for (int out = 1; out < arr.length; out++) {
            int in = out;
            int temp = arr[out];
            while (in - 1 >= 0 && arr[in - 1] > temp) {
                ArrayUtils.swap(arr, in, in - 1);
                in = in - 1;
            }
            if (in != out)
                arr[in] = temp;
        }
        for (int i : arr) {
            System.out.print(i + ",");
        }
    }


    //希里算法
    @Test
    public void shellSort() {
        int size = arr.length;
        int h = 1;
        while (h <= size / 3) {
            h = h * 3 + 1;
        }
        while (h > 0) {
            for (int out = h; out < size; out++) {
                int in = out;
                int temp = arr[out];
                while (in - h >= 0 && arr[in - h] > temp) {
                    arr[in] = arr[in - h];
                    in = in - h;
                }
                if (in != out)
                    arr[in] = temp;
            }
            h = (h - 1) / 3;
        }
        for (int i : arr) {
            System.out.print(i + ",");
        }
    }


    @Test
    public void partition() {
        int size = arr.length;
        int pivot = 100;
        int left = -1;
        int right = size;
        while (left < right) {
            while (left < right && arr[++left] < pivot) ;
            while (left < right && arr[--right] > pivot) ;
            if (left != right) {
                ArrayUtils.swap(arr, left, right);
            }
        }
        System.out.println(left + "   " + right);
        for (int i : arr) {
            System.out.print(i + ",");
        }
    }

    //快速算法
    @Test
    public void fastSort() {
        fastSort(arr, 0, arr.length-1);
        for (int i : arr) {
            System.out.print(i + ",");
        }
    }


    private static void fastSort(int[] arr, int left, int right) {
        // 跳出递归的条件
        if (right - left <= 0) return;
        // 使用arr[right]作为关键值
        int mid = partition(arr, left, right, arr[right]);
        fastSort(arr, left, mid - 1);
        fastSort(arr, mid + 1, right);
    }


    public static int partition(int[] arr, int left, int right, int pivot) {
        int originalRight = right;
        left = left - 1;
        while (left < right) {
            while (left < right && arr[++left] < pivot) ;
            while (left < right && arr[--right] > pivot) ;
            ArrayUtils.swap(arr, left, right);
        }
        ArrayUtils.swap(arr, left, originalRight);
        return left;
    }


}
