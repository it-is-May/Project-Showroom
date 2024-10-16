package com.mycompany.giaonguyenassignment3b;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
public class GiaoNguyenHashSet<E> implements Collection<E> {
  // Define the default hash table size. Must be a power of 2
  private final static int DEFAULT_INITIAL_CAPACITY = 4;
  
  // Define the maximum hash table size. 1 << 30 is same as 2^30
  private final static int MAXIMUM_CAPACITY = 1 << 30; 
  
  // Current hash table capacity. Capacity is a power of 2
  private int capacity;
  
  // Define default load factor
  private final static float DEFAULT_MAX_LOAD_FACTOR = 0.75f; 

  // Specify a load factor threshold used in the hash table
  private float loadFactorThreshold; 
  
  // The number of elements in the set
  private int size = 0; 
  
  // Hash table is an array with each cell that is a linked list
  private LinkedList<E>[] table;

  /** Construct a set with the default capacity and load factor */
  public GiaoNguyenHashSet() {  
    this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);    
  }
  
  /** Construct a set with the specified initial capacity and 
   * default load factor */
  public GiaoNguyenHashSet(int initialCapacity) { 
    this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);    
  }
  
  /** Construct a set with the specified initial capacity 
   * and load factor */
  public GiaoNguyenHashSet(int initialCapacity, float loadFactorThreshold) { 
    if (initialCapacity > MAXIMUM_CAPACITY)
      this.capacity = MAXIMUM_CAPACITY;
    else
      this.capacity = trimToPowerOf2(initialCapacity);
    
    this.loadFactorThreshold = loadFactorThreshold;    
    table = new LinkedList[capacity];
  }
  
  @Override /** Remove all elements from this set */ 
  public void clear() {
    size = 0;
    removeElements();
  }

  @Override /** Return true if the element is in the set */
  public boolean contains(Object e) {
    int bucketIndex = hash(e.hashCode());
    if (table[bucketIndex] != null) {
      LinkedList<E> bucket = table[bucketIndex]; 
      return bucket.contains(e);
    }
    
    return false;
  }
  
  @Override /** Add an element to the set */
  public boolean add(E e) {
    if (contains(e)) // Duplicate element not stored
      return false;
    
    if (size + 1 > capacity * loadFactorThreshold) {
      if (capacity == MAXIMUM_CAPACITY)
        throw new RuntimeException("Exceeding maximum capacity");
    
      rehash();
    }
    
    int bucketIndex = hash(e.hashCode());
    
    // Create a linked list for the bucket if it is not created
    if (table[bucketIndex] == null) {
      table[bucketIndex] = new LinkedList<E>();
    }

    // Add e to hashTable[index]
    table[bucketIndex].add(e);

    size++; // Increase size
    
    return true;
  }

  @Override /** Remove the element from the set */
  public boolean remove(Object e) {
    if (!contains(e))
      return false;
    
    int bucketIndex = hash(e.hashCode());
    
    // Create a linked list for the bucket if it is not created
    if (table[bucketIndex] != null) {
      LinkedList<E> bucket = table[bucketIndex]; 
      bucket.remove(e);
    }

    size--; // Decrease size
    
    return true;
  }

  @Override /** Return true if the set contains no elements */
  public boolean isEmpty() {
    return size == 0;
  }

  @Override /** Return the number of elements in the set */
  public int size() {
    return size;
  }

  @Override /** Return an iterator for the elements in this set */
  public java.util.Iterator<E> iterator() {
    return new GiaoNguyenHashSetIterator(this);
  }
  
  /** Inner class for iterator */
  private class GiaoNguyenHashSetIterator implements java.util.Iterator<E> {
    // Store the elements in a list
    private java.util.ArrayList<E> list;
    private int current = 0; // Point to the current element in list
    private GiaoNguyenHashSet<E> set;
    
    /** Create a list from the set */
    public GiaoNguyenHashSetIterator(GiaoNguyenHashSet<E> set) {
      this.set = set;
      list = setToList();
    }

    @Override /** Next element for traversing? */
    public boolean hasNext() {
      return current < list.size();
    }

    @Override /** Get current element and move cursor to the next */
    public E next() {
      return list.get(current++);
    }

    @Override /** Remove the element returned by the last next() */
    public void remove() {
        if(current == 0)
          throw new IllegalStateException("next() has not been called yet");
        E elementRemove = list.get(current - 1); //get the element returned by the last next()
        set.remove(elementRemove); //remove it from the set
        list.remove(elementRemove); //remove it from the list
        current--; //to keep track of the current element
      
    }
  }  
  
  /** Hash function */
  private int hash(int hashCode) {
    return hashCode & (capacity - 1);
  }

  /** Return a power of 2 for initialCapacity */
  private int trimToPowerOf2(int initialCapacity) {
    int capacity = 1;
    while (capacity < initialCapacity) {
      capacity <<= 1;
    }
    
    return capacity;
  }
  
  /** Remove all e from each bucket */
  private void removeElements() {
    for (int i = 0; i < capacity; i++) {
      if (table[i] != null) {
        table[i].clear();
      }
    }
  }
  
  /** Rehash the set */
  private void rehash() {
    java.util.ArrayList<E> list = setToList(); // Copy to a list
    capacity <<= 1; // Double capacity      
    table = new LinkedList[capacity]; // Create a new hash table
    size = 0; // Reset size 
    
    for (E element: list) {
      add(element); // Add from the old table to the new table
    }
  }

  /** Copy elements in the hash set to an array list */
  private java.util.ArrayList<E> setToList() {
    java.util.ArrayList<E> list = new java.util.ArrayList<>();
    
    for (int i = 0; i < capacity; i++) {
      if (table[i] != null) {
        for (E e: table[i]) {
          list.add(e);
        }
      }
    }  
    
    return list;
  }

  @Override
  public String toString() {
    java.util.ArrayList<E> list = setToList();
    StringBuilder builder = new StringBuilder("[");
    
    // Add the elements except the last one to the string builder
    for (int i = 0; i < list.size() - 1; i++) {
      builder.append(list.get(i) + ", ");
    }
    
    // Add the last element in the list to the string builder
    if (list.size() == 0)
      builder.append("]");
    else
      builder.append(list.get(list.size() - 1) + "]");
    
    return builder.toString();
  }

  @Override /** add all elements in arg0 to this set*/
  public boolean addAll(Collection<? extends E> arg0) {
    for(E e: arg0)
        add(e);
    return size() == arg0.size();
  }

  @Override /** check if this collection contains all elements of arg0 */ 
  public boolean containsAll(Collection<?> arg0) {
    for(Object e: arg0)
    {
        if(!contains(e))
            return false;
    }
    return true;
  }

  @Override /** remove elements of arg0 in this collection*/
  public boolean removeAll(Collection<?> arg0) {
    boolean modified = false;
    for(Object e: arg0)
    {
        if(contains(e)) {
            modified = remove(e);
        }
    }
    return modified;
  }

   @Override /** keep elements of arg0 in this collection & delete the rest*/
  public boolean retainAll(Collection<?> arg0) {
    boolean modified = false;
    for(Object e: arg0)
    {
        if(!contains(e))
        {
            modified = remove(e);
        }
    }
    return modified;
  }

  @Override /** return an array of Object for the elements in this collection */
  public Object[] toArray() {
    Object[] newArr = new Object[size()]; 
 
    int i = 0;
    
    for(E o: this)
    {
        newArr[i++] = o;
    }
    return newArr;
  }

  @Override /** return an array of type T for the elements in arg0 */
  public <T> T[] toArray(T[] arg0) {
    Object[] newArr = new Object[arg0.length]; //create an array of Objects to store the elements

    //checking the length of the input array of T type with the tree size

    /** If the input array length is less than the tree size, return an array of the same type
    * array.getClass() method helps determining the type.*/
    
    if (arg0.length < size())
        return (T[]) Arrays.copyOf(arg0, size(), arg0.getClass());

    /** copy the elements into an array of Objects, not creating a new array*/
    System.arraycopy(newArr, 0, arg0, 0, size());

    /** If the array length is greater than the tree size, fill the blanks
    * with null.*/

    if (arg0.length > size()) 
        arg0[size()] = null;

    return arg0;
  }
  
  public void GiaoNguyenOutput()
  {
    for (int i = 0; i < capacity; i++) {
      if (table[i] != null && !table[i].isEmpty())
        for(E element: table[i])
            System.out.println(element);
    }
    System.out.println();
  }
}
