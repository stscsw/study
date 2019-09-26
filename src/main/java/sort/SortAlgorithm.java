package sort;

import org.junit.Test;


import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ArrayUtils.swap;

/**
 * 冒泡排序  选择排序  插入排序  希尔排序
 */
public class SortAlgorithm {

    public static int[] ii;

    static {
        Random r = new Random();
        ii = new int[100000];
        for (int i = 0; i < ii.length; i++) {
            ii[i] = r.nextInt(2000000000);
        }
    }

    /**
     * 冒泡排序
     * 将数组依次进行两两对比，如果后一个值大于前一个值，
     * 这两个值就相互交换，这样最大的数就被替换到最前的位置，实现大数冒泡。
     * <p>
     * 冒泡排序效率：当有N个元素的数组时，外循环要进行N次，内循环平均要
     * 进行N/2次，那么总的循环次数是 N^2/2 次,它的执行时间是O(N^2)。
     * 但是它的交换次数比较多，如果数据是随机的，那么平均有一半元素可能需
     * 要交换，总的交换次数是 N^2/4 次。
     */
    @Test
    public void bubbleSort() {
        //外循环依次将out值依次递减。
        //内循环是寻找arr数组从 0--out下标中的最大值，将它替换到out位置。
        int[] arr = ii;
        int size = arr.length;
        long startTime = System.currentTimeMillis();
        // 外层循环，保证out之后的值 是按照从小到大顺序排列的。
        for (int out = size - 1; out > 0; out--) {
            // 内层循环，将最大的值移动到最后out位置，实现了大数冒泡
            for (int in = 0; in < out; in++) {
                if (arr[in] > arr[in + 1]) {
                    swap(arr, in, in + 1);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时" + (endTime-startTime));
//        Arrays.stream(arr).forEach(System.out::println);
    }

    /**
     * 选择排序
     * 选择排序和冒泡排序一样简单，但是它数据交换的次数比冒泡排序少的多。
     * 使用循环，找到数组中最小值，替换到遍历起始位置。
     * <p>
     * 选择排序效率：和冒泡排序一样，它的总循环次数是 N^2/2 ，它的执行时间是O(N^2)。但是它的交换次数最多只有N次。
     */
    @Test
    public void selectSort() {
        //外循环将out值依次递增。
        //内循环找到从out位置开始到数组结束位置arr.length -1中最小值，和out位置的值进行交换。
        int[] arr = ii;
        int size = arr.length;
        long startTime = System.currentTimeMillis();
        // 外层循环，保证out之前的都是有序的
        for (int out = 0; out < size; out++) {
            int mixIndex = out;
            // 内层循环，找到out位置最小值
            for (int in = out + 1; in < size; in++) {
                if (arr[mixIndex] > arr[in]) {
                    mixIndex = in;
                }
            }
            if (mixIndex != out) {
                swap(arr, mixIndex, out);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时" + (endTime-startTime));
    }

    /**
     * 可以看出选择和冒泡很像，它们的主要区别是：
     * 选择排序在内循环的时候，只是寻找最值(最大值或者最小值)，不进行交换，而是在内循环结束时进行交换。
     * 冒泡排序在内循环的时候，就进行交换，使得最值能够移动到末尾。
     */


    /**
     * 插入排序
     * 这个排序的执行时间对于随机的数据排序效率比冒泡和选择排序都少，但是对于极端数据(比如说完全倒序的数据)可能没有选择好，交换的次数过多了
     * 默认out位置之前(不包括out)的元素都是有序的。
     * 对于out位置的元素，要先与out之前的元素依次进行比较。
     * 找到应该插入的位置in,将in位置之后(不包括in)的数据全部右移一位.
     * 再将in位置存放out位置原来的值。这样就实现了插入之后的前out(包括out)的数组还是有序的。
     * <p>
     * 我们要在数组中插入一个元素，那么从被插入位置开始，之后的值都要右移一位，那我们必须进行递减循环(从大到小),
     * 这样才能实现前一个位置元素覆盖后一个位置的元素。同理如果删除一个元素，就必须递增循环，实现后一个位置元素覆盖前一个位置元素。
     * <p>
     * 插入排序效率：外循环要进行N次，内循环如果是最极端的情况(数组是倒序的),那么进行的次数就是1到N次，平均就是N/2次，
     * 如果是随机数据的话，那么平均次数就是N/4，所以它的总循环次数是N^2/4 到 N^2/2 次，它的执行时间也是O(N^2)。但是它的交互次数和循环次数一样多。
     */
    @Test
    public void insertSort() {
        //外循环从1开始，第0位置的元素肯定是有序的(因为就它一个值)，接下来将1到arr.length-1的元素依次插入到这个局部有序数组正确位置，
        //内循环进行递减循环比较，找到out位置的元素在局部有序数组中正确的位置。
        int[] arr = ii;
        int size = arr.length;
        long startTime = System.currentTimeMillis();
        //  out之前的是有序的，所以第0位置是有序的，然后将后面的数据插入到这个局部有序数组对应位置
        for (int out = 1; out < size; out++) {
            // 记录下out位置的值，将它替换至正确的位置
            int temp = arr[out];
            int in = out;
            // arr[in - 1] <= temp 表示temp就应该插入in位置，
            // 因为temp比in+1位置的元素小，但是又比in-1位置元素大(包括等于)
            while (in - 1 >= 0 && arr[in - 1] > temp) {
                arr[in] = arr[in - 1];
                in--;
            }
            if (in != out) arr[in] = temp;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时" + (endTime-startTime));
//        System.out.println(Arrays.stream(arr).boxed().map(item -> item.toString()).collect(Collectors.joining(",")));
    }


    /**
     * 希尔排序是建立在插入排序基础上的。它解决了插入排序的一个问题。
     * <p>
     * 考虑一种情况，对于一个随机数组，如果有很小的数据在最右端的位置，那么要将它移动到最左端位置，需要进行很多次内循环，因为我们每次的循环间隔是1。
     * 那么有没有一种方式，可以使一些右端位置较小的值，快速移动到左端位置呢？希尔排序就是解决这个问题的。
     * 但是注意希尔排序对极端数据(完全倒序数组)没有什么优化。
     * <p>
     * 希尔排序与插入排序相比较，最主要的变化有两点：
     * <p>
     * 在内循环中每次步长不在是1了，而是一个h, 这个值开始很大，这样就可以将较小的值，进行大跨越式地向前移动。
     * 多加了一个最外层循环，它逐渐递减步长h的值。注意这个h的选择，它必须保证最后一次递减的值是1， 这样才能在内循环中，保证每个值都进行过比较了。
     * <p>
     * 至于h = h * 3 + 1这个数学家研究出来的间隔序列。为什么这个间隔序列可以提高效率，具体原理我肯定是说不清楚的。
     * 希尔排序效率：没有办法从理论上分析出希尔排序的效率，对于随机数据，它的执行时间大概是O(N*( log N)^2)。
     */
    @Test
    public void shellSort() {
        int[] arr = ii;
        int size = arr.length;
        int h = 1;
        long startTime = System.currentTimeMillis();
        while (h <= size / 3) {
            //  这里加1是保证h = (h - 1) / 3最后一个值是1，
            h = h * 3 + 1;
        }
        while (h > 0) {
            for (int out = h; out < size; out++) {
                int temp = arr[out];
                int in = out;
                // arr[in - h] <= temp 表示temp就应该插入in位置，因为temp比in-h位置的值大，又比in+h位置的值小
                while (in - h >= 0 && arr[in - h] > temp) {
                    arr[in] = arr[in - h];
                    in = in - h;
                }
                if (in != out) arr[in] = temp;
            }

            h = (h - 1) / 3;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时" + (endTime-startTime));
//        System.out.println(Arrays.stream(arr).boxed().map(item -> item.toString()).collect(Collectors.joining(",")));
    }


    /**
     * 划分算法
     */
    @Test
    public void partition() {
        int[] arr = ii;
        int pivot = 0;
        // 将left开始设置为-1，因为下面判断使用arr[++left]，会先自增。
        // 如果使用arr[left++]，那么进行判断arr[++left] < pivot时的left值
        // 和进行交换swap(arr, left, right)就不是同一个值了。
        int left = -1;
        // 同left一样，比真实坐标大一的位置
        int right = arr.length;
        while (left < right) {
            //这里;表示空语句，只有不满足条件，循环跳出。
            while (left < right && arr[++left] < pivot) ;
            while (left < right && arr[--right] > pivot) ;
            swap(arr, left, right);
        }
        System.out.println(Arrays.stream(arr).boxed().map(item -> item.toString()).collect(Collectors.joining(",")));
        System.out.println(right);
        System.out.println(left);
    }


    @Test
    public void fastSort() {
        int[] arr = ii;
        long startTime = System.currentTimeMillis();
        fastSort(arr, 0, arr.length - 1);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时" + (endTime-startTime));
//        System.out.println(Arrays.stream(arr).boxed().map(item -> item.toString()).collect(Collectors.joining(",")));
    }

    private static void fastSort(int[] arr, int left, int right) {
        // 跳出递归的条件
        if (right - left <= 0) return;
        // 使用arr[right]作为关键值
        int mid = partition(arr, left, right, arr[right]);
        fastSort(arr, left, mid - 1);
        fastSort(arr, mid + 1, right);
    }


    private static int partition(int[] arr, int left, int right, int pivot) {
        // 记录下开始时的right位置，它代表了关键值，最后要将这个关键值移动到中间位置。
        // 开始时，right没有加一，因为right位置作为关键值，最后要移动到中间位置。
        int originRight = right;
        left = left - 1;
        while (left < right) {
            while (left < right && arr[++left] < pivot) ;
            while (left < right && arr[--right] > pivot) ;
            swap(arr, left, right);
        }
        // right位置的值，一定不小于pivot，和最开始right位置值进行交换。
        // 这样才能满足right位置的值不小于right之前位置的值，又不大于right之后位置的值
        swap(arr, right, originRight);
        return right;
    }

}
