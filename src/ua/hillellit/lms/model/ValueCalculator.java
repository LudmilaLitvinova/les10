package ua.hillellit.lms.model;

import java.util.Arrays;

public class ValueCalculator {

  int sizeArr = 1_000_000;
  int halfSizeArr = sizeArr / 2;
  float[] arr = new float[sizeArr];
  float[] arr1 = new float[halfSizeArr];
  float[] arr2 = new float[halfSizeArr];

  public void doCalc() {
    long start = System.currentTimeMillis();
    Arrays.fill(arr, 1f);
    System.arraycopy(arr, 0, arr1, 0, halfSizeArr);
    System.arraycopy(arr, halfSizeArr, arr2, 0, halfSizeArr);

    MyRunnable myRun1 = new MyRunnable(arr1);
    MyRunnable myRun2 = new MyRunnable(arr2);

    Thread th1 = new Thread(myRun1);
    Thread th2 = new Thread(myRun2);

    th1.start();
    th2.start();
    try {
      th1.join();
      th2.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    arr1 = myRun1.getArr();
    arr2 = myRun2.getArr();

    System.arraycopy(arr1, 0, arr, 0, halfSizeArr);

    System.arraycopy(arr2, 0, arr, halfSizeArr, halfSizeArr);

    System.out.println(System.currentTimeMillis() - start + " ms");
  }

}