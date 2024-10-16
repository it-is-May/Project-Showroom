package com.mycompany.giaonguyenassignment3c;

import java.util.*;
public class GiaoUnweightedGraph<V> implements GiaoGraph<V> {
    protected List<V> vertices = new ArrayList<>(); // Store vertices
    protected List<List<Edge>> neighbors 
    = new ArrayList<>(); // Adjacency lists

    /** Construct an empty graph */
    public GiaoUnweightedGraph() {
    }

    /** Construct a graph from vertices and edges stored in arrays */
    public GiaoUnweightedGraph(V[] vertices, int[][] edges) {
    for (int i = 0; i < vertices.length; i++)
      addVertex(vertices[i]);

    createAdjacencyLists(edges, vertices.length);
    }

    /** Construct a graph from vertices and edges stored in List */
    public GiaoUnweightedGraph(List<V> vertices, List<Edge> edges) {
    for (int i = 0; i < vertices.size(); i++)
      addVertex(vertices.get(i));

    createAdjacencyLists(edges, vertices.size());
    }

    /** Construct a graph for integer vertices 0, 1, 2 and edge list */
    public GiaoUnweightedGraph(List<Edge> edges, int numberOfVertices) {
    for (int i = 0; i < numberOfVertices; i++) 
      addVertex((V)(Integer.valueOf(i))); // vertices is {0, 1, ...}

    createAdjacencyLists(edges, numberOfVertices);
    }

    /** Construct a graph from integer vertices 0, 1, and edge array */
    public GiaoUnweightedGraph(int[][] edges, int numberOfVertices) {
    for (int i = 0; i < numberOfVertices; i++) 
      addVertex((V)(Integer.valueOf(i))); // vertices is {0, 1, ...}

    createAdjacencyLists(edges, numberOfVertices);
    }

    /** Create adjacency lists for each vertex */
    private void createAdjacencyLists(
      int[][] edges, int numberOfVertices) {
    for (int i = 0; i < edges.length; i++) {
      addEdge(edges[i][0], edges[i][1]);
    }
    }

    /** Create adjacency lists for each vertex */
    private void createAdjacencyLists(List<Edge> edges, int numberOfVertices) {
        for (Edge edge: edges) {
          addEdge(edge.u, edge.v);
        }
    }

    @Override /** Return the number of vertices in the graph */
    public int getSize() {
        return vertices.size();
    }

    @Override /** Return the vertices in the graph */
    public List<V> getVertices() {
        return vertices;
    }

    @Override /** Return the object for the specified vertex */
    public V getVertex(int index) {
        return vertices.get(index);
    }

    @Override /** Return the index for the specified vertex object */
    public int getIndex(V v) {
    return vertices.indexOf(v);
    }

    @Override /** Return the neighbors of the specified vertex */
    public List<Integer> getNeighbors(int index) {
    List<Integer> result = new ArrayList<>();
    for (Edge e: neighbors.get(index))
      result.add(e.v);

    return result;
    }

    @Override /** Return the degree for a specified vertex */
    public int getDegree(int v) {
        return neighbors.get(v).size();
    }

    @Override /** Print the edges */
    public void printEdges() {
        for (int u = 0; u < neighbors.size(); u++) {
            System.out.print(getVertex(u) + " (" + u + "): ");
            for (Edge e: neighbors.get(u)) {
                System.out.print("(" + getVertex(e.u) + ", " +
                    getVertex(e.v) + ") ");
            }
            System.out.println();
        }
    }

    @Override /** Clear the graph */
    public void clear() {
        vertices.clear();
        neighbors.clear();
    }

    @Override /** Add a vertex to the graph */  
    public boolean addVertex(V vertex) {
        if (!vertices.contains(vertex)) {
          vertices.add(vertex);
          neighbors.add(new ArrayList<Edge>());
          return true;
        }
        else {
          return false;
        }
    }

    @Override /** Add an edge to the graph */  
    public boolean addEdge(Edge e) {
    if (e.u < 0 || e.u > getSize() - 1)
      throw new IllegalArgumentException("No such index: " + e.u);

    if (e.v < 0 || e.v > getSize() - 1)
      throw new IllegalArgumentException("No such index: " + e.v);

    if (!neighbors.get(e.u).contains(e)) {
      neighbors.get(e.u).add(e);
      return true;
    }
    else {
      return false;
    }
    }

    @Override /** Add an edge to the graph */  
    public boolean addEdge(int u, int v) {
        return addEdge(new Edge(u, v));
    }

    @Override /** Obtain a DFS tree starting from vertex u */
    /** To be discussed in Section 28.7 */
    public SearchTree dfs(int v) {
    List<Integer> searchOrder = new ArrayList<>();
    int[] parent = new int[vertices.size()];
    for (int i = 0; i < parent.length; i++)
      parent[i] = -1; // Initialize parent[i] to -1

    // Mark visited vertices
    boolean[] isVisited = new boolean[vertices.size()];

    // Recursively search
    dfs(v, parent, searchOrder, isVisited);

    // Return a search tree
    return new SearchTree(v, parent, searchOrder);
    }

    /** Recursive method for DFS search */
    private void dfs(int v, int[] parent, List<Integer> searchOrder, boolean[] isVisited) {
        // Store the visited vertex
        searchOrder.add(v);
        isVisited[v] = true; // Vertex v visited

        for (Edge e : neighbors.get(v)) { // Note that e.u is v 
          int w = e.v; // e.v is w in Listing 28.8
          if (!isVisited[w]) { 
            parent[w] = v; // The parent of w is v
            dfs(w, parent, searchOrder, isVisited); // Recursive search
          }
        }
    }

    @Override /** Starting bfs search from vertex v */
    /** To be discussed in Section 28.9 */
    public SearchTree bfs(int v) {
    List<Integer> searchOrder = new ArrayList<>();
    int[] parent = new int[vertices.size()];
    for (int i = 0; i < parent.length; i++)
      parent[i] = -1; // Initialize parent[i] to -1

    java.util.LinkedList<Integer> queue =
      new java.util.LinkedList<>(); // list used as a queue
    boolean[] isVisited = new boolean[vertices.size()];
    queue.offer(v); // Enqueue v
    isVisited[v] = true; // Mark it visited

    while (!queue.isEmpty()) {
      int u = queue.poll(); // Dequeue to u
      searchOrder.add(u); // u searched
      for (Edge e: neighbors.get(u)) { // Note that e.u is u
        int w = e.v; // e.v is w in Listing 28.11
        if (!isVisited[w]) { 
          queue.offer(w); // Enqueue w
          parent[w] = u; // The parent of w is u
          isVisited[w] = true; // Mark w visited
        }
      }
    }

    return new SearchTree(v, parent, searchOrder);
    }

    /** Tree inner class inside the UnweightedGraph class */
    /** To be discussed in Section 28.6 */
    public class SearchTree {
        private int root; // The root of the tree
        private int[] parent; // Store the parent of each vertex
        private List<Integer> searchOrder; // Store the search order

        /** Construct a tree with root, parent, and searchOrder */
        public SearchTree(int root, int[] parent, 
            List<Integer> searchOrder) {
          this.root = root;
          this.parent = parent;
          this.searchOrder = searchOrder;
        }

        /** Return the root of the tree */
        public int getRoot() {
          return root;
        }

        /** Return the parent of vertex v */
        public int getParent(int v) {
          return parent[v];
        }

        /** Return an array representing search order */
        public List<Integer> getSearchOrder() {
          return searchOrder;
        }

        /** Return number of vertices found */
        public int getNumberOfVerticesFound() {
          return searchOrder.size();
        }

        /** Return the path of vertices from a vertex to the root */
        public List<V> getPath(int index) {
          ArrayList<V> path = new ArrayList<>();

          do {
            path.add(vertices.get(index));
            index = parent[index];
          }
          while (index != -1);

          return path;
        }

        /** Print a path from the root to vertex v */
        public void printPath(int index) {
          List<V> path = getPath(index);
          System.out.print("A path from " + vertices.get(root) + " to " +
            vertices.get(index) + ": ");
          for (int i = path.size() - 1; i >= 0; i--)
            System.out.print(path.get(i) + " ");
        }

        /** Print the whole tree
         * Note: this method is alternated from the original method in the book 
         * to fit the output screen*/
        public void printTree() {
          System.out.println("Root is: " + vertices.get(root));
          System.out.println("Edges: ");
          for (int i = 0; i < parent.length; i++) {
            if (parent[i] != -1) {
              // Display an edge
              System.out.println("(" + vertices.get(parent[i]) + ", " +
                vertices.get(i) + ") ");
            }
          }
          System.out.println();
        }
    }

    @Override /** Remove vertex v and return true if successful */  
    public boolean remove(V v) {
        if(!vertices.contains(v))
            return false;
        int index = getIndex(v);
        
        //get all incident edges to the vertex & clear them
        List<Integer> incident = getNeighbors(index);
        for(int incidentEdge: incident)
        {
            remove(index, incidentEdge); //calling the second remove method below
            remove(incidentEdge, index);
        }
        
        //remove the vertex from the list of vertices & neighbors
        vertices.remove(v); 
        neighbors.remove(index);
        
        if(!vertices.contains(v) && !neighbors.contains(index))
        {
            return true;
        }
        return false; 
    }

    @Override /** Remove edge (u, v) and return true if successful */  
    public boolean remove(int u, int v) {
        int edgeListSize = neighbors.size();
        //check for invalid edge indices
        if( v < 0 || v >= edgeListSize || u < 0 || u >= edgeListSize)
            throw new IllegalArgumentException("Invalid edge indices");

        for(Edge e: neighbors.get(u))
        {
            if(e.v == v)
            {
                neighbors.get(u).remove(e);
                return true;
            }
        }
        return false; 
    }
    
    /** Print the whole graph */
    public void GiaoPrintGraph()
    {
        System.out.println("Number of vertices in this graph: " + getSize());
        for(int i = 0; i < vertices.size(); i++)
        {
            System.out.print("Vertex " + vertices.get(i)+ ": ");
            for(Edge e: neighbors.get(i))
            {
                System.out.print(getVertex(e.v) + " ");
            }
            System.out.println();
        }
    }
    
    /** Traverses the graph in breadth-first order and print the search tree*/
    public void GiaoPrintBreadthFirst()
    {
       SearchTree tree = bfs(0); //create a bfs tree starting with vertex 0
       tree.printTree(); //print the whole tree
    }
    
    /** Traverses the graph in depth-first order starting at a vertex v as a parameter
     * and print the vertices*/
    public void GiaoPrintDepthFirst(int v)
    {
        SearchTree tree = dfs(v); //create a dfs tree starting with vertex v
        List<Integer> listOfVertices = tree.getSearchOrder();
        System.out.println("Vertices of dfs tree, displayed in 2 columns: ");
        for(int i = 0; i < listOfVertices.size(); i++)
        {
            System.out.println(getVertex(listOfVertices.get(i)));
        }
    }
}
