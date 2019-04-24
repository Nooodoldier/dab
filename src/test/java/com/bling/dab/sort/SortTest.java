package com.bling.dab.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author: hxp
 * @date: 2019/4/24 10:31
 * @description:简单的排序（冒泡，选择，插入，希尔，归并）
 */
public class SortTest {

    /**
     * 冒泡
     * @param array
     * @return
     */
    public static int[] bubbleSort(int[] array){
        if(array.length == 0){
            return array;
        }
        for (int i=0;i<array.length;i++){
            for (int j = 0; j <array.length-1-i ; j++) {
                if(array[j+1]<array[j]){
                    int temp = array[j+1];
                    array[j+1]=array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }


    /**
     * 选择
     * @param array
     * @return
     */
    public static int[] selectionSort(int[] array){
        if(array.length == 0){
            return array;
        }
        for (int i = 0; i <array.length ; i++) {
            int minIndex = i;
            for (int j = i; j <array.length ; j++) {
                if(array[j]<array[minIndex]){
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
        return array;
    }

    /**
     * 插入
     * @param array
     * @return
     */
    public static int[] insertionSort(int[] array){
        if(array.length == 0){
            return array;
        }
        int current;
        for (int i = 0; i <array.length -1 ; i++) {
            current = array[i+1];
            int preIndex = i;
            while (preIndex >= 0 && current <array[preIndex]){
                array[preIndex+1] = array[preIndex];
                preIndex--;
            }
            array[preIndex+1] = current;
            
        }
        return array;
    }


    /**
     * 希尔
     * @param array
     * @return
     */
    public static int[] shellSort(int[] array){
        if(array.length == 0){
            return array;
        }
        int len = array.length;
        int temp,gap=len/2;
        while (gap>0){
            for (int i = gap; i <len ; i++) {
                temp = array[i];
                int preIndex = i -gap;
                while (preIndex >= 0 && array[preIndex] >temp){
                    array[preIndex +gap] = array[preIndex];
                    preIndex -= gap;
                }
                array[preIndex + gap] = temp;
            }
            gap /= 2;
        }
        return array;
    }


    /**
     * 归并
     * @param array
     * @return
     */
    public static int[] mergeSort(int[] array){
        if(array.length < 2){
            return array;
        }
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array,0,mid);
        int[] right = Arrays.copyOfRange(array,mid,array.length);
        return merge(mergeSort(left),mergeSort(right));
    }

    /**
     * 归并排序是将俩段排序好的数组结合成一个排序数组
     * @param left
     * @param right
     * @return
     */
    public static int[] merge(int[] left,int[] right){
        int[] result = new int[left.length + right.length];
        for (int index = 0, i = 0, j = 0; index < result.length ; index++) {
            if(i >= left.length){
                result[index] = right[j++];
            }else if(j >= right.length){
                result[index] = left[i++];
            }else if(left[i] > right[j]){
                result[index] = right[j++];
            }else {
                result[index] = left[i++];
            }
        }
        return result;
    }

    @Test
    public void sortIntTest(){

        int[] array = new int[]{8,19,32,1,5,20,3,40,99,34,0,100};

        //int[] arraySort = bubbleSort(array);

        //int[] arraySort = selectionSort(array);

        //int[] arraySort = insertionSort(array);

        //int[] arraySort = shellSort(array);

        //int[] arraySort = mergeSort(array);

        Arrays.sort(array);

        System.out.print(Arrays.toString(array));
    }
}
