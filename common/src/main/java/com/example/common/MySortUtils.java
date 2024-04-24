package com.example.common;

public enum MySortUtils {

    INSTANCE;

    public void bubble(int[] a, int size) {
        for (int i = 0; i < size; i++) {
            boolean flag = false;
            for (int j = 0; j < i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    flag = true;
                }
                if (!flag) {
                    break;
                }
            }
        }
    }

    public void insertionSort(int[] a, int size) {
        for (int i = 1; i < size; i++) {
            int value = a[i];
            int j = i - 1;
            for (; j >= 0; --j) {
                if (a[j] > value) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
            }
            a[j + 1] = value;
        }
    }


}
