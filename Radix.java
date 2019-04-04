public class Radix{

  private class MyLinkedList<E>{

    private class Node<E>{
      private E data;
      private Node<E> next, prev;

      public Node(E num, Node<E> nextOne, Node<E> prevOne){
        data = num;
        next = nextOne;
        prev = prevOne;
      }

      public E get(){
        return data;
      }

      public boolean set(E value){
        if (value != null){
          data = value;
          return true;
        }
        return false;
      }

      public boolean hasNext(){
        return (next != null);
      }

      public Node<E> next(){
        return next;
      }

      public Node<E> prev(){
        return prev;
      }

      public boolean prevChange(Node<E> changeTo){
        if (changeTo != null){
          prev = changeTo;
          return true;
        }
        return false;
      }

      public boolean nextChange(Node<E> changeTo){
        if (changeTo != null){
          next = changeTo;
          return true;
        }
        return false;
        }

      public void nextExnull(Node<E> val){
        next = val;
      }

      public void prevExnull(Node<E> val){
        prev = val;
      }
    }



    private int size = 0;
    private Node<E> start,end;
    private Node<E> current = start;

    public MyLinkedList(){
    }


    public String toString(){
      String ans = "[";
      current = start;
      for (int i = 0 ; i < size ; i++){
        ans += current.get() + ", ";
        current = current.next();
      }
      if (ans.length() > 1){
        ans = ans.substring(0,ans.length()-2);
      }
      return ans + "]";
    }

    public int size(){
      return size;
    }

    public boolean add(E value){
      if (start == null){
        start = new Node<E>(value, end, null);
        end = start;
        size++;
        return true;
      }
      else{
        Node<E> newEnd = new Node<E>(value, null, end);
        size++;
        boolean worked = end.nextChange(newEnd);
        end = newEnd;
        return worked;
      }
    }

    public E get(int index){
      if (index < 0 || index > size - 1){
        throw new IndexOutOfBoundsException("Please input an index between 0 and " + size);
      }
      if (index < (size / 2) ){
        current = start;
        for (int i = 0 ; i < index ; i++){
          current = current.next();
        }
      }
      else{
        current = end;
        for (int i = size - 1 ; i > index ; i--){
          current = current.prev();
        }
      }

      return current.get();
    }

    public E set(int index, E value){
      //does a single loop, so it is still 0(N^2)
      E old = get(index);
      current.set(value);
      return old;
    }

    public boolean contains(E value){
      current = start;
      while (current.hasNext() && current.get() != value){
        current = current.next();
      }
      //return true if the loop found a matching value before the end OR value == end value
      return (current != end || value == end.get());
    }

    public int indexOf(E value){
      current = start;
      int i = 0;
      while (i < size && current.get() != value){
        current = current.next();
        i++;
      }
      if (i == size)
        return -1;
      return i;
    }

    public void add(int index, E value){
      if (index == size){
        add(value);
      }
      else if (index == 0){
        start.prevChange(new Node<E>(value, start, null));
        start = start.prev();
        size++;
      }
      else{
        get(index);
        Node<E> toBeAdded = new Node<E>(value, current, current.prev());
        current.prev().nextChange(toBeAdded);
        current.prevChange(toBeAdded);
        size++;
      }
    }

    public E remove(int index){
      get(index);
      if (index == 0 || index == size - 1){
        if (index == 0)
          start = current.next();
        else
          end = current.prev();
      }
      else{
        current.prev().nextChange(current.next());
        current.next().prevChange(current.prev());
      }
      size--;
      return current.get();
    }

    public boolean remove(E value){
      boolean worked = contains(value);
      if (current.prev() != null)
        current.prev().nextExnull(current.next());
      else{
        remove(0);
        size++;
      }
      if (current.next() != null)
        current.next().prevExnull(current.prev());
      else{
        remove(size - 1);
        size++;
      }
      size--;
      return worked;
    }

    public void extend(MyLinkedList<E> other){
      while (other.size() > 0){
        add(other.get(0));
        other.remove(0);
      }
    }

  }

  public static void radixsort(int[] data){
    Radix obj = new Radix();
    obj.radixNSsort(data);
  }

  private void radixNSsort(int[] data){
    @SuppressWarnings("unchecked")
    MyLinkedList<Integer>[] buckets = new MyLinkedList[20];

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
