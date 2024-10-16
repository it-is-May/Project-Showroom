package com.mycompany.giaonguyenassignment3b;

import java.util.*;
import java.io.*;

public class GiaoNguyenAssignment3B {

    public static void main(String[] args) throws FileNotFoundException {
        File textfile = new File("Assignment3BData.txt");
        Scanner infile = new Scanner(textfile);
        
        GiaoNguyenHashMap<String, Integer> hashMap = new GiaoNguyenHashMap<>();
        GiaoNguyenHashSet<String> hashSet = new GiaoNguyenHashSet<>();
        String[] line;  //to store the current line
        String state;   //to store state
        int totalPop;   //to store 2019's estimated total population of the state
        
        
        while(infile.hasNext()) //open the file once to input into hash map and hash set
        {
            line = infile.nextLine().split("(, )|\t"); //read the whole line and split into an array
            state = line[0];
            totalPop = Integer.parseInt(line[1]);
            hashMap.put(state, totalPop);
            hashSet.add(state);
        }
        infile.close();
        
        //test the functionality of GiaoNguyenHashMap & GiaoNguyenMap
        System.out.println("Hash Map with size of: " + hashMap.size());
        hashMap.GiaoNguyenOutput();
        GiaoNguyenHashMap<String, Integer> map2 = new GiaoNguyenHashMap<>(5);
        if(hashMap.containsKey("Minnesota"))
        {
            System.out.println("Minnesota is in the hash map, its population: " + hashMap.get("Minnesota"));
        }
        if(hashMap.containsValue(4903185))
        {
            System.out.println("In the hash map, Alabama has 4903185 as its population.");
        }
        System.out.println("A set containing entries of this hash map.");
        System.out.println(hashMap.entrySet());
        System.out.println("A set containing keys of this hash map.");
        System.out.println(hashMap.keySet());
        
        //input for 2nd hash map
        map2.put("Minnesota", hashMap.get("Minnesota")); 
        map2.put("Texas", hashMap.get("Texas"));
        map2.put("District of Columbia", hashMap.get("District of Columbia"));
        map2.put("U.S. Virgin Islands", hashMap.get("U.S. Virgin Islands"));
        map2.put("Northern Mariana Islands", hashMap.get("Northern Mariana Islands"));
        hashMap.remove("Minnesota");
        if(hashMap.containsKey("Minnesota"))
        {
            System.out.println("remove() functionality has failed.");
            hashMap.GiaoNguyenOutput();
        }
        else
        {
            System.out.println("(1) After removing Minnesota from the hash map ");
            hashMap.GiaoNguyenOutput();
        }
        
        hashMap.clear();
        if(hashMap.isEmpty())
            System.out.println("Hash map is successfully clear.");
        else
            System.out.println("clear() functionality test has failed, size of hash map: "
                + hashMap.size());
        System.out.println("2nd hash map instance:");
        map2.GiaoNguyenOutput();
        
        //test functionality of GiaoNguyenHashSet
        System.out.println("Hash set with size of: " + hashSet.size());
        hashSet.GiaoNguyenOutput();
        GiaoNguyenHashSet<String> set2 = new GiaoNguyenHashSet<>(4);
        set2.add("Iowa");
        set2.add("Pennsylvania");
        set2.add("Wyoming");
        set2.add("North Carolina");
        hashSet.removeAll(set2); //remove all elements of set2 in original hash set
        if(hashSet.containsAll(set2))
        {
            System.out.println("containsAll() functionality test has failed.");
        }
        else
        {
            System.out.println("Successfully make a separate set and delete duplicate"
                + " in the original set. \nSet 2 with size of: " + set2.size());
            set2.GiaoNguyenOutput();
            System.out.println("Modified hash set with size of: " + hashSet.size());
            hashSet.GiaoNguyenOutput();
            
        }
        boolean modified = hashSet.retainAll(set2);
        if(modified == true)
        {
            System.out.println("retainAll() has logic error. Modified should output false");
            System.out.println("Size of the hash set: " + hashSet.size());
        }
        else
        {
            System.out.println("retainAll() passed the functionality test");
            System.out.println("Size of the hash set: " + hashSet.size());
        }
        hashSet.clear();
        if(hashSet.isEmpty())
            System.out.println("Successfully clear the whole hash set, size of hash set: " 
            + hashSet.size());
        else
            System.out.println("clear() has failed the functionality test, size of hash set: " 
            + hashSet.size());
      
    }
}
