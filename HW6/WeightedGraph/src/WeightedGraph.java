import java.util.*;

// Weighted Undirected Graph
public class WeightedGraph
{
	private int V;
	private Set<Edge>[] adj; //adj[i] stores all edges connecting to vertex i
	public WeightedGraph(int V)
	{
		this.V = V;
		adj = (Set<Edge>[]) new Set[V];
		for (int v = 0; v < V; v++)      
			adj[v] = new HashSet<Edge>();
	}//
	
	public void addEdge(Edge e){	
		int v = e.v,  w = e.w;
		adj[v].add(e);
		adj[w].add(e);
	}
	
	public Iterable<Edge> adj(int v)
	{  return adj[v];  }

}