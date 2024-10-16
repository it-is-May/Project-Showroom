package com.mycompany.giaonguyenassignment3a;

/**Since balance methods are private and placed within delete(), 
 * unable call them in main 
 * Purpose: to test insert, search, delete, inorder, preorder, postorder,
 * and GiaoNguyenPrint method on instances of user-defined BST and AVL tree classes */

import java.util.*;
public class GiaoNguyenAssignment3A {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int[] inData = new int[10];
        GiaoNguyenBinarySearchTree<Integer> tree1 = new GiaoNguyenBinarySearchTree<>();
        GiaoNguyenAVLTree<Integer> tree2 = new GiaoNguyenAVLTree<>();
        
        System.out.print("Enter 10 integers: ");
        for(int i = 0; i < inData.length; i++){
            inData[i] = input.nextInt();
            tree1.insert(inData[i]);
            tree2.insert(inData[i]);
        }
        
        //test binary search tree
        
        System.out.println("Original BST"); //print the original tree1
        tree1.GiaoNguyenPrint();
        int temp1 = 37;     //a random integer to search in the tree
        System.out.println("Search for 89 to delete");
        if(tree1.search(temp1)) { 
            tree1.delete(temp1); //delete if it's true
            System.out.print("(1) Inorder: ");
            tree1.inorder();
            System.out.print("\n(1) Preorder: ");
            tree1.preorder();
            System.out.print("\n(1) Postorder: ");
            tree1.postorder();
            System.out.println("\n(1) After deletion");
            tree1.GiaoNguyenPrint();
        }
        else {
            System.out.print("(no change) ");
            tree1.GiaoNguyenPrint();
        }
        System.out.println("Search for element at index 5 of the input array to delete");
        if(tree1.search(inData[5])) {
            tree1.delete(inData[5]); //delete if it's true
            System.out.print("(1) Inorder: ");
            tree1.inorder();
            System.out.print("\n(1) Preorder: ");
            tree1.preorder();
            System.out.print("\n(1) Postorder: ");
            tree1.postorder();
            System.out.println("\n(1) After deletion");
            tree1.GiaoNguyenPrint();
        }
        else{
            System.out.print("(no change) ");
            tree1.GiaoNguyenPrint();
        }
        tree1.insert(100);  //add new element to the tree
        System.out.println("\n(2) Adding 100 to the BST");
        tree1.GiaoNguyenPrint();
        System.out.println();
        
        //test AVL tree
        
        System.out.println("\nOriginal AVL Tree"); //print the original tree2
        tree2.GiaoNguyenPrint();
        int temp2 = 15;     //a random integer to search in the tree
        System.out.println("Search for 15 to delete");
        if(tree2.search(temp2)) {
            tree2.delete(temp2); //delete if it's true
            System.out.print("(1) Inorder: ");
            tree2.inorder();
            System.out.print("\n(1) Preorder: ");
            tree2.preorder();
            System.out.print("\n(1) Postorder: ");
            tree2.postorder();
            System.out.println("\n(1) After deletion");
            tree2.GiaoNguyenPrint();
        }
        else {
            System.out.print("(no change) ");
            tree2.GiaoNguyenPrint();
        }
        System.out.println("Search for element at index 2 of the input array to delete");
        if(tree2.search(inData[2])) {
            tree2.delete(inData[2]); //delete if it's true
            System.out.print("(1) Inorder: ");
            tree2.inorder();
            System.out.print("\n(1) Preorder: ");
            tree2.preorder();
            System.out.print("\n(1) Postorder: ");
            tree2.postorder();
            System.out.println("\n(1) After deletion");
            tree2.GiaoNguyenPrint();
        }
        else {
            System.out.print("(no change) ");
            tree2.GiaoNguyenPrint();
        }
        tree2.insert(1);    //add new element to the tree
        System.out.println("\n(2) Adding 1 to the AVL Tree");
        tree2.GiaoNguyenPrint();
        System.out.println();
    }
}
