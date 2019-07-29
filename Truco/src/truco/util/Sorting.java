/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.util;

/**
 *
 * @author Leandro Teixeira
 */
public class Sorting {
    public static Object[] BubbleSort(int[]array){
        int[]sortedArray=array;
        for (int i=0;i<sortedArray.length;i++){
            for(int j=i;j<sortedArray.length;j++){
                if (sortedArray[i]>sortedArray[j])
                    sortedArray=swap(sortedArray,i,j);
            }
        }
        return sortedArray;
    }
    public static int[] SelectionSort(int[]array){
        int[]sortedArray=array;
        int maior=0;
        for (int i=0;i<sortedArray.length;i++){
            for(int j=i;j<sortedArray.length;j++){
                if (sortedArray[maior]>sortedArray[j])
                    maior=j;
            }
            sortedArray=swap(sortedArray,i,maior);
        }
        return sortedArray;
    }
    public static int[] InsertionSort(int[]array){
        int[]sortedArray=array;
        for (int i=1;i<sortedArray.length;i++){
            int maior=sortedArray[i];
            int j=i;
            while (j>0&&sortedArray[j-1]>maior){
                sortedArray[j]=sortedArray[--j];
            }
            sortedArray[j]=maior;
        }
        return sortedArray;
    }
/*    public static int[] QuickSort(int[]array){
        int[]sortedArray=array;
        
        return sortedArray;
    }
    public static int[] MergeSort(int[]array){
        int[]sortedArray=array;
        
        return sortedArray;
    }*/

    
    public static Object[] BubbleSort(Object[]array){
        Object[]sortedArray=array;
        for (int i=0;i<sortedArray.length;i++){
            for(int j=i;j<sortedArray.length;j++){
                if (Integer.parseInt(sortedArray[i].toString())>Integer.parseInt(sortedArray[j].toString()))
                    sortedArray=swap(sortedArray,i,j);
            }
        }
        return sortedArray;
    }
    public static Object[] SelectionSort(Object[]array){
        Object[]sortedArray=array;
        int maior=0;
        for (int i=0;i<sortedArray.length;i++){
            for(int j=i;j<sortedArray.length;j++){
                if (Integer.parseInt(sortedArray[maior].toString())>Integer.parseInt(sortedArray[j].toString()))
                    maior=j;
            }
            sortedArray=swap(sortedArray,i,maior);
        }
        return sortedArray;
    }
    public static Object[] InsertionSort(Object[]array){
        Object[]sortedArray=array;
        for (int i=1;i<sortedArray.length;i++){
            int maior=Integer.parseInt(sortedArray[i].toString());
            int j=i;
            while (j>0&&Integer.parseInt(sortedArray[j-1].toString())>maior){
                sortedArray[j]=sortedArray[--j];
            }
            sortedArray[j]=maior;
        }
        return sortedArray;
    }
    
    
    private static int[] swap(int[] sortedArray, int i, int j) {
        int aux=sortedArray[i];
        sortedArray[i]=sortedArray[j];
        sortedArray[j]=aux;
        return sortedArray;
    }
    
    private static Object[] swap(Object[] sortedArray, int i, int j) {
        Object aux=sortedArray[i];
        sortedArray[i]=sortedArray[j];
        sortedArray[j]=aux;
        return sortedArray;
    }
}
