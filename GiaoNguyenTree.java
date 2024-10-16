package com.mycompany.giaonguyenassignment3a;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public interface GiaoNguyenTree<E> extends Collection<E> 
{
    /** Return true if the element is in the tree */
  public boolean search(E e);

  /** Insert element e into the binary tree
   * Return true if the element is inserted successfully */
  public boolean insert(E e);

  /** Delete the specified element from the tree
   * Return true if the element is deleted successfully */
  public boolean delete(E e);
  
  /** Get the number of elements in the tree */
  public int getSize();
  
  /** Inorder traversal from the root*/
  public default void inorder() {
  }

  /** Postorder traversal from the root */
  public default void postorder() {
  }

  /** Preorder traversal from the root */
  public default void preorder() {
  }
  
  @Override /** Return true if the tree is empty */
  public default boolean isEmpty() {
    return this.size() == 0;
  }

  @Override
  public default boolean contains(Object e) {
    return search((E)e);
  }
  
  @Override
  public default boolean add(E e) {
    return insert(e);
  }
  
  @Override
  public default boolean remove(Object e) {
    return delete((E)e);
  }
  
  @Override
  public default int size() {
    return getSize();
  }
  
  @Override /** return true if this collection contains all elements in collection c 
  * This should be similar to List as it extends Collection*/
  public default boolean containsAll(Collection<?> c) {
    for(Object o: c)
    {
        if(!c.contains(o))
            return false; //exit the loop immediately and return false
    } 
    return true;
  }

  @Override /** add all elements in collection c into this collection
  * return true if the size of this tree is changed. */
  public default boolean addAll(Collection<? extends E> c) {
    for (E object : c) 
    {
        add(object);
    }
    return size() == c.size();
  }

  @Override /** removes all the elements in c from this collection */
  public default boolean removeAll(Collection<?> c) {
    int oldSize = size();
    if(c.isEmpty()) {
        return false; //if c is an empty tree, no further action
    }
    else {
        for(Iterator<E> i = iterator(); i.hasNext();){ //for loop will continue if hasNext() is true
            E o = i.next();
            if(c.contains(o))
                remove(o);
        }
    } 
    return oldSize == size(); //if the tree is modified
  }

  @Override
  public default boolean retainAll(Collection<?> c) {
    if(c.isEmpty())
        return false;  //if c is an empty tree, no further action
    else{
        Iterator<E> i = iterator();
        while(i.hasNext()){
            E element = i.next();
            if(!c.contains(element))
                remove(element);
        }
    }
    return true;
  }

  @Override /** Return an array of Object for the elements in this collection */
  public default Object[] toArray() 
  {
    //an array of Object to contain all elements in the tree
    Object[] newArr = new Object[size()]; 
    int i = 0;
    for(E e: this){ //traverse the tree, copy element into variablee e & add it to the array
        newArr[i++] = e;
    }
    return newArr;
	
  }

  @Override /** return an array of T[] type
  * this is similar to MyList interface */
  public default <T> T[] toArray(T[] array) 
  {
    Object[] newArr = new Object[array.length]; //create an array of Objects to store the elements
 
    //checking the length of the input array of T type with the tree size

    /** If the input array length is less than the tree size, return an array of the same type
    * array.getClass() method helps determining the type.*/
    if (array.length < size())
        return (T[]) Arrays.copyOf(array, size(), array.getClass());

    /** copy the elements into an array of Objects, not creating a new array*/
    System.arraycopy(newArr, 0, array, 0, size());

    /** If the array length is greater than the tree size, fill the blanks
    * with null.*/
    if (array.length > size()) 
        array[size()] = null;

    return array;
  }
}

