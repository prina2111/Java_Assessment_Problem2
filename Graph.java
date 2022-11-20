import java.util.HashMap;
import java.util.List;
import java.util.*;

class Graph {
    
    private Map<String,Integer> weights = new HashMap<>();
    private Map<String,Node> nodes = new HashMap<>();
    private Set<List<String>> ans = new HashSet<>();
    
    private class Node{
        String label;

        List<Edge> edges = new ArrayList<>();
        public Node(String label) {
            this.label = label;
        }

        public void addEdge(Node to, int weight){
            edges.add(new Edge(this, to, weight));
            String temp = this.label+"-"+to.label;
            weights.putIfAbsent(temp,weight);
        }

        public List<Edge> getEdges() {
            return edges;
        }


    }

    private class Edge{
        Node from;
        Node to;

        public Edge(Node from, Node to, int weight) {
            this.from = from;
            this.to = to;
        }
        
    }
    
    public void addNode(String label){
        var node = new Node(label);
        nodes.putIfAbsent(label, node);
       
    }
    
    public void addEdge(String from, String to, int weight){
        var fromNode = nodes.get(from);

        if(fromNode == null)
            return;

        var toNode = nodes.get(to);
        if(toNode == null)
            return;

        fromNode.addEdge(toNode, weight);
        toNode.addEdge(fromNode, weight);
    }

     public void getAvgDistance(String from, String to) {
        Node fromNode = nodes.get(from);
        var toNode = nodes.get(to);
        
        Queue<List<String> > q = new LinkedList<>();
        
        List<String> path = new ArrayList<>();
	    path.add(fromNode.label);
	    q.offer(path);

        while(!q.isEmpty()){
             
            path = q.poll();
            String last = path.get(path.size() - 1);
		    if (last.equals(toNode.label))
		        ans.add(path);
            
            List<String> lastNode = new ArrayList<>();
            for(var edge: nodes.get(last).getEdges()) {
                lastNode.add(edge.to.label);
            
		        for(int i = 0; i < lastNode.size(); i++)
		        {
			        if(!path.contains((lastNode.get(i))))
			        {
				        List<String> newpath = new ArrayList<>(path);
				        newpath.add(lastNode.get(i));
				        q.offer(newpath);
			        }
		        }
		    }
        }

    }
    
    public static void main(String[] args){
    Graph graph = new Graph();
      graph.addNode("A");  
      graph.addNode("B");
      graph.addNode("C");
      graph.addNode("D");
      graph.addNode("E");
      graph.addEdge("A","B",12);
      graph.addEdge("B","A",12);
      graph.addEdge("A","C",13);
      graph.addEdge("C","A",13);
      graph.addEdge("A","E",8);
      graph.addEdge("E","A",8);
      graph.addEdge("A","D",11);
      graph.addEdge("D","A",11);
      graph.addEdge("B","C",3);
      graph.addEdge("C","B",3);
      graph.addEdge("E","C",4);
      graph.addEdge("C","E",4);
      graph.addEdge("E","D",7);
      graph.addEdge("D","E",7);
      String from = "A";
      String to = "E";
      graph.getAvgDistance(from,to);
      int finalAns = 0;
      for(List<String> l : graph.ans){
          int sum=0;
          for(int i=0;i<l.size()-1;i++){
              String key = l.get(i)+"-"+l.get(i+1);
             sum+=graph.weights.get(key);
              
          }
          finalAns += sum;
      }
      System.out.print("The Average Distance between the nodes " + from + " and " + to + " is: ");
      System.out.println( (float)finalAns/graph.ans.size());  
    }

}