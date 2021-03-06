/*
 *
 * Name - Ahmed Isthaffa
 * UoW ID - W1761936
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;





public class FordFulkerson {
    static int source=0;  //source of the flow network
    static Graph graph;   // graph for the flow network
    static int numOfNodes; //  number of vertices in the graph
    static int sink;   //  sink of the flow network(target)

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("1 -> to read the file and calculate the max flow \n" +
                    "2 -> to remove a edge and calculate the max flow \n" +
                    "3 -> to change the capacity of a edge and calculate the max flow \n" +
                    "4 -> to add anew edge and calculate the max flow \n" +
                    "5 -> to exit ");
          int  option= sc.nextInt();


          if(option==5){
              break;
          }
          switch (option){

              case 1:
                  System.out.println("Enter the file name to calculate the max flow");
                  sc.nextLine();
                  String filename = sc.nextLine();
                  System.out.println("------------------------------------------------");

                  System.out.println("      Displaying the nodes and it's edges      ");
                  System.out.println("------------------------------------------------");
                  //read the file and printing the graph
                  fileParser(filename);
                  System.out.println("------------------------------------------------");


//                Stopwatch stopwatch=new Stopwatch();
                  System.out.println("         Calculating Max flow          ");
                  System.out.println("------------------------------------------------");
                  System.out.println("source - "+source);
                  System.out.println("sink - "+sink);
                  System.out.println("-----------------");
                  System.out.println("Max Flow : " + fordFulkerson(graph, source, sink));  //getting the max flow
                  System.out.println("------------------------------------------------");
//                System.out.println("timing in seconds :" + stopwatch.elapsedTime());
                  break;
              case 2:
                  System.out.println("Enter the starting and ending of the edge to be removed :");
                  System.out.println("start :");
                  int start =sc.nextInt();
                  System.out.println("end :");
                  int end =sc.nextInt();
                  System.out.println("removing the "+start+" -> "+end+" edge");
                  removeEdge(start,end);
                  System.out.println("         Calculating Max flow          ");
                  System.out.println("------------------------------------------------");
                  System.out.println("source - "+source);
                  System.out.println("sink - "+sink);
                  System.out.println("-----------------");
                  System.out.println("Max Flow : " + fordFulkerson(graph, source, sink));  //getting the max flow
                  System.out.println("------------------------------------------------");
//                System.out.println("timing in seconds :" + stopwatch.elapsedTime());
                  break;
              case 3:
                  System.out.println("Enter the staring and ending of the edge and the capacity to be changed ");
                  System.out.println("start :");
                  int i =sc.nextInt();
                  System.out.println("end :");
                  int j =sc.nextInt();
                  System.out.println("capacity :");
                  int c =sc.nextInt();
                  System.out.println("changing  the capacity of "+i+" -> "+j+" edge to "+c);
                  changeCapacity(i,j,c);
                  System.out.println("         Calculating Max flow          ");
                  System.out.println("------------------------------------------------");
                  System.out.println("source - "+source);
                  System.out.println("sink - "+sink);
                  System.out.println("-----------------");
                  System.out.println("Max Flow : " + fordFulkerson(graph, source, sink));  //getting the max flow
                  System.out.println("------------------------------------------------");
//                System.out.println("timing in seconds :" + stopwatch.elapsedTime());
                  break;
              case 4:
                  System.out.println("Enter the starting , ending and the capacity  of the edge to be added:");
                  System.out.println("start :");
                  int s =sc.nextInt();
                  System.out.println("end :");
                  int t =sc.nextInt();
                  System.out.println("capacity :");
                  int e=sc.nextInt();
                  System.out.println("adding the edge "+s+" -> "+t);
                  addEdge(s,t,e);
                  System.out.println("         Calculating Max flow          ");
                  System.out.println("------------------------------------------------");
                  System.out.println("source - "+source);
                  System.out.println("sink - "+sink);
                  System.out.println("-----------------");
                  System.out.println("Max Flow : " + fordFulkerson(graph, source, sink));  //getting the max flow
                  System.out.println("------------------------------------------------");
//                System.out.println("timing in seconds :" + stopwatch.elapsedTime());

                  break;
              default:
                  System.out.println("choose any option from above");




          }
        }









    }





//    static void fileParser2(String filename){
//        //read the integer from a file
//        //set up the initial database
//        In in = new In(filename);
//        int[] list = in.readAllInts();
//        int numOfNodes=list[0];
//        int start;  //starting point of the edge
//        int end;  //ending point of the edge
//        int capacity;  //capacity of the edge
//        graph=new Graph(numOfNodes);
//        for (int i = 1; i <list.length ; i=i+3) {
//            start=list[i];
//            end=list[i+1];
//            capacity=list[i+2];
//
//            graph.addEdge(start,end,capacity);
//
//        }
//
//
//        graph.printGraph();
//
//    }


    private static void removeEdge(int i,int j){
        graph.removeEdge(i,j);

    };

    private static void addEdge(int i,int j,int capacity){
        graph.addEdge(i,j,capacity);
    }

    private static void changeCapacity(int i,int j,int capacity){
        graph.addEdge(i,j,capacity);
    }







    static void fileParser(String filename){


        File file=new File(filename);

        try {
            Scanner sc=new Scanner(file);
            numOfNodes=sc.nextInt();
            sink=numOfNodes-1;
            graph=new Graph(numOfNodes);
            sc.nextLine();
            while(sc.hasNextLine()) {
                String readLine=sc.nextLine();
                String[] s=readLine.split(" ");
                int start=Integer.parseInt(s[0]);
                int end=Integer.parseInt(s[1]);
                int capacity=Integer.parseInt(s[2]);

                graph.addEdge(start,end,capacity);
            }
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

        graph.printGraph();

    }






    private static int fordFulkerson(Graph g, int source, int sink) {
        //error validation
        if (source == sink) {
            return 0;
        }

        int V = g.getNumOfVertices(); //number of vertices

        // create residual graph
        Graph rg = new Graph(V);
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                rg.getAdjMatrix()[i][j] = g.getAdjMatrix()[i][j]; //map the original graph to the residual graph
            }
        }

        //store path with parent of each node
        int[] parent = new int[V];

        //starting the stopwatch

        long start = System.currentTimeMillis();
        int max_flow = 0; // max flow value

        // while a path exists from source to sink (augmenting path)
        while (bfs(rg, source, sink, parent)) {
            int path_flow = Integer.MAX_VALUE; // to store path flow

            ArrayList<Integer> pathView = new ArrayList<>(); //stores the augmenting path

            // find maximum flow of path augmented by bfs method
            for (int i = sink; i != source; i = parent[i]) {
                pathView.add(i); //add visited node to the path
                int j = parent[i];
                path_flow = Math.min(path_flow, rg.getAdjMatrix()[j][i]);
            }

            Collections.reverse(pathView);


            System.out.println("Augmenting path: "+" 0 --> " + pathView.toString().replace("[", "").replace("]", "").replace(","," --> ") + " | Path flow: "+ path_flow);
            pathView.clear();

            // update residual graph capacities
            // reverse edges along the path
            for (int i = sink; i != source; i = parent[i]) {
                int j = parent[i];
                rg.getAdjMatrix()[j][i] -= path_flow;
                rg.getAdjMatrix()[i][j] += path_flow;
            }

            // Add path flow to max flow
            System.out.println("flow = "+max_flow+" + " +path_flow+" = "+(max_flow+path_flow));
            max_flow += path_flow;
        }
        long stop = System.currentTimeMillis();
        //calculating the time in milliseconds
        double time = (stop - start) / 1000.0;
        System.out.println("Elapsed time = " + time + " seconds");

        return max_flow;
    }

    private static boolean bfs(Graph rg, int source, int sink, int[] parent) {
        //array to store visited vertices
        boolean[] visited = new boolean[rg.getNumOfVertices()];
        for (int i = 0; i < rg.getNumOfVertices(); i++)
            visited[i] = false;

        LinkedList<Integer> queue = new LinkedList<>(); //queue which stores the vertices to be visited in order

        //visit source
        queue.add(source);
        visited[source] = true;
        parent[source] = 0;  //defining the parent node of the visited node

        //loop through all vertices
        while (!queue.isEmpty()) {
            int i = queue.poll();  //removing the first element
            // check neighbours of vertex i
            for (Integer j : rg.neighbourVertices(i)) {
                // if not visited and positive value then visit
                if ((!visited[j]) && (rg.getAdjMatrix()[i][j] > 0)) {
                    queue.add(j);
                    visited[j] = true;
                    parent[j] = i;  //defining the parent node of the visited node
                }
            }
        }

        //returns true if it reached to sink
        return visited[sink];
    }










}
