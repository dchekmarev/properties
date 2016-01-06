package com.crossover.properties;

import java.util.Arrays;
import java.util.stream.Collectors;

public

class Solution {
  public static int solution1(int N) {
    String oldestStr = Arrays.asList(Integer.toString(N).split("")).stream()
        .sorted((o1, o2) -> o2.compareTo(o1))
        .collect(Collectors.joining());
    if (oldestStr.length() >= "100000000".length() && oldestStr.compareTo("100000000") > 0) {
      return -1;
    }
    return Integer.parseInt(oldestStr);
  }


  static
  public int solution3(int[] A) {
    for (int i = 0; i < A.length - 1; i++) {
      A[i] -= A[i + 1];
    }
    int cnt = 0;
    for (int i = 0; i < A.length - 1; i++) {
      int eqCnt = 0;
      for (int j = i + 1; j < A.length - 1 && A[i] == A[j]; j++) {
        eqCnt++;
      }
      for (; eqCnt > 0; eqCnt--) {
        cnt += eqCnt;
        i++;
      }
    }
    if (cnt > 1000000000) {
      return -1;
    }
    return cnt;
  }


  static
  int solution4(int n) {
    int[] d = new int[30];
    int l = 0;
    int p;
    while (n > 0) {
      d[l] = n % 2;
      n /= 2;
      l++;
    }
    for (p = 1; p <= (1 + l) / 2; ++p) {
//    for (p = 1; p < 1 + l; ++p) {
      int i;
      boolean ok = true;
      for (i = 0; i < l - p; ++i) {
        if (d[i] != d[i + p]) {
          ok = false;
          break;
        }
      }
      if (ok) {
        return p;
      }
    }
    return -1;
  }


  static int toInt(int[] bits) {
    int sum = 0;
    for (int i = 0; i < bits.length; i++) {
      sum += (bits[i] << i) * (i % 2 == 1 ? -1 : 1);
    }
    return sum;
  }

  static
  public int[] solution(int M) {
    if (M == 0) {
      return new int[0];
    }
    int[] bits = new int[31];
    int sum = 0;

    int older = 0;
    for (int i = 30; i >= 0; i--) {
      int add = (int) Math.pow(-2, i);
      int newSum = sum + add;
      if (Math.abs(M - newSum) < Math.abs(M - sum)) {
        older = Math.max(i, older);
        bits[i] = 1;
        sum = newSum;
      }
      if (i > 0) {
        int add2 = (int) Math.pow(-2, i) + (int) Math.pow(-2, i - 1);
        int newSum2 = sum + add2;
        if (Math.abs(M - newSum2) < Math.abs(M - sum)) {
          older = Math.max(i, older);
          bits[i] = 1;
          bits[i - 1] = 1;
          sum = newSum2;
          i--;
        }
      }
    }

    return Arrays.copyOf(bits, older + 1);
  }


  public static void main(String[] args) {
    for (int i = 0; i < 50; i++) {
      test(i);
    }
    test(123123);
  }

  private static void test(int x) {
    int[] result = solution(x);
    String res = Arrays.stream(result).mapToObj(Integer::toString).collect(Collectors.joining(","));
    System.out.println(x + " -> " + res + "( " + result.length + " items)" + " -> " + toInt(result));
  }

  public static void main4(String[] args) {
//    System.out.println(solution(3)); // 111, period=1
    System.out.println(solution4(10)); // 1010, period=2
    System.out.println(solution4(9)); // 1001, period=-1
    System.out.println(solution4(Integer.parseInt("11011011011", 2)));
//    System.out.println(solution(36)); // 100100, period=3
  }

  public static void main1(String[] args) {
    System.out.println(solution1(0));
    System.out.println(solution1(1));
    System.out.println(solution1(123123));
    System.out.println(solution1(12345678));
    System.out.println(solution1(999999990));
    System.out.println(solution1(100000001));
  }

  public static void main2(String[] args) {
    System.out.println(solution3(new int[] {}));
    System.out.println(solution3(new int[] {1}));
    System.out.println(solution3(new int[] {1, 1}));
    System.out.println(solution3(new int[] {1, 1, 1}));
    System.out.println(solution3(new int[] {1, 1, 1, 1}));
    System.out.println(solution3(new int[] {1, 1, 1, 1, 1, 1}));
    System.out.println(solution3(new int[] {1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 1, -1, 1, 2, 3}));
  }


}