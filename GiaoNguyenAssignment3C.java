package com.mycompany.giaonguyenassignment3c;

import java.util.*;
import java.io.*;
public class GiaoNguyenAssignment3C 
{
    public static void main(String[] args) throws FileNotFoundException 
    {
        String city1, city2;
        String[] line;
        HashSet<String> uniqueVertices = new HashSet<>(); //act as 2nd storage to check for duplicates
        ArrayList<String> vertices = new ArrayList<>(); //to store unique cities
        File text = new File("Assignment3CData.txt");
        Scanner infile = new Scanner(text);
        
        while(infile.hasNext())
        {
            line = infile.nextLine().split("(, )|\t"); 
            city1 = line[0];
            city2 = line[1];
            if(!uniqueVertices.contains(city1))
            {
                vertices.add(city1);
                uniqueVertices.add(city1);
            }
            if(!uniqueVertices.contains(city2))
            {
                vertices.add(city2);
                uniqueVertices.add(city2);
            }
        }
        infile.close();
        
        //convert the array list into an array of strings of unique cities
        String[] GiaoVertices = vertices.toArray(new String[0]); 
        //create a 2D array of integers to store edges/pairs of city1-city2 and city2-city1
        int[][] GiaoEdges = {{0, 1}, {0, 2}, {1,0}, {1, 39}, {2, 0}, {2, 32},{3, 4}, {4, 3}, {4, 7}, {5, 6},
            {5, 15}, {6, 5}, {6, 15}, {6, 34}, {7, 4}, {7, 8}, {7, 9}, {8, 7}, {8, 30}, {8, 33}, {8, 34}, {8, 39},
            {9, 7}, {9, 29}, {9, 39}, {9, 44}, {10, 11}, {10, 36}, {11, 10}, {11, 19}, {12, 13}, {12, 24}, 
            {13, 12}, {13, 46}, {14, 15}, {15, 14}, {15, 5}, {15, 6}, {15, 27}, {15, 37}, {16, 17}, {16, 18},
            {16, 52}, {17, 16}, {17, 20}, {17, 25}, {18, 16}, {18, 34}, {19, 11}, {19, 20}, {19, 21}, {19, 40}, 
            {20, 17}, {20, 19}, {20, 21}, {20, 27}, {20, 49}, {21, 19}, {21, 20}, {21, 43}, {22, 23}, {22, 24}, 
            {22, 48}, {23, 22}, {23, 27}, {24, 12}, {24, 22}, {24, 31}, {24, 44}, {24, 51}, {25, 17}, {25, 26}, 
            {26, 25}, {26, 47}, {26, 54}, {27, 15}, {27, 20}, {27, 23}, {28, 29}, {29, 28}, {29, 9}, {29, 42}, 
            {29, 48}, {30, 8}, {30, 53}, {31, 24}, {32, 2}, {32, 46}, {33, 8}, {33, 45}, {34, 6}, {34, 8}, {34, 18},
            {34, 40}, {35, 36}, {35, 47}, {36, 10}, {36, 35}, {37, 15}, {38, 39}, {39, 1}, {39, 8}, {39, 9}, {39, 38}, 
            {40, 19}, {40, 34}, {41, 42}, {42, 29}, {42, 41}, {43, 21}, {43, 44}, {44, 9}, {44, 24}, {44, 43}, 
            {44, 46}, {44, 47}, {44, 50}, {45, 33}, {46, 13}, {46, 22}, {46, 44}, {46, 47}, {47, 46}, {47, 26}, 
            {47, 35}, {47, 44}, {47, 52}, {48, 22}, {48, 29}, {49, 20}, {50, 44}, {50, 51}, {51, 24}, {51, 50}, 
            {52, 16}, {52, 47}, {53, 30}, {54, 26}};
        
        //create a graph from 2 arrays above
        GiaoUnweightedGraph<String> graph1 = new GiaoUnweightedGraph<>(GiaoVertices, GiaoEdges);
        System.out.println("Graph1");
        graph1.GiaoPrintGraph();
        System.out.println("Breadth-first print");
        graph1.GiaoPrintBreadthFirst();
        System.out.println("Depth-first print from vertex 50");
        graph1.GiaoPrintDepthFirst(50);
        System.out.println("All edges of this graph");
        graph1.printEdges();
        System.out.println("The vertex at index 22: " + graph1.getVertex(22)+ " and its neighbor(s): "
        + graph1.getNeighbors(22));
        String c23 = graph1.getVertex(23), c24 = graph1.getVertex(24), c48 = graph1.getVertex(48);
        System.out.printf("These neighbors are %s, %s, and %s, respectively.\n", c23, c24, c48);
        System.out.println("The degree at vertex 22: " + graph1.getDegree(22));
        System.out.println("Atlanta is at index " + graph1.getIndex("Atlanta"));
        graph1.addEdge(53, 0);
        System.out.println("(1) the graph after new edge is added: ");
        graph1.printEdges();
        graph1.addVertex("Garland");
        graph1.addEdge(graph1.getIndex("Garland"), 20);
        System.out.println("(2) after Garland is added and its edge to " + graph1.getVertex(20));
        graph1.printEdges();
        boolean deleted = graph1.remove(53, 0);
        if(deleted == false)
            System.out.println("remove(u, v) has failed the functionality check");
        else
            System.out.println("remove(u, v) passed the functionality check");
        System.out.println("(3) the graph after the new edge between Albany and Portland is deleted: ");
        graph1.printEdges();
        boolean isRemoved = graph1.remove("Portland");
        if(isRemoved == false)
            System.out.println("removed(V v) has failed the functionality check");
        else
            System.out.println("remove(V v) passed the functionality check");
        graph1.addVertex("Rochester");
        graph1.addEdge(graph1.getIndex("Rochester"), 30);
        System.out.println("(4) the graph after Portland and its incident edges are replaced by new vertex: ");
        graph1.printEdges();
        graph1.clear();
        if(graph1.getSize() == 0)
            System.out.println("Successfully cleared the graph");
        else
            System.out.println("clear() has failed the functionality check");
    }
}
