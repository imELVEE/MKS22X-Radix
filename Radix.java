public class Radix{
  public static void radixsort(int[] data){
    @SuppressWarnings("unchecked")
    MyLinkedList<Integer>[] buckets = new MyLinkedList[19];

    for (int i = 0 ; i < buckets.length ; i++){
      buckets[i] = new MyLinkedList<Integer>();
    }

    int max = 0;
    for (int num: data){
      max = Math.max(max,Math.abs(num));
    }

    for (int digit = 1 ; max*10/digit != 0 ; digit *= 10){
      for (int num: data){
        if (num < 0){
          buckets[9+(num/digit%10)].add(num);
        }
        else{
          buckets[10+(num/digit%10)].add(num);
        }
      }
      for (int i = 1 ; i < buckets.length ; i++){
        buckets[0].extend(buckets[i]);
      }
      for (int i = 0 ; i < data.length ; i++){
        data[i] = buckets[0].remove(0);
      }
      for (int i = 0 ; i < buckets.length ; i++){
        buckets[i] = new MyLinkedList<Integer>();
      }
    }

/*
    System.out.print("[");
    for (MyLinkedList<Integer> b: buckets){
      System.out.print(b);
    }
    System.out.println("]");

    System.out.print("[");
    for (int num: data){
      System.out.print(num + " ");
    }
    System.out.println("]");
    System.out.println();
    */

  }
}
