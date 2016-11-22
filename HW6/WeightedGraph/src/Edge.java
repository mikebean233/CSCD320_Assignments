import java.util.Comparator;

public class Edge implements Comparable<Edge>
{
	public int v,  w;
	private final double weight;
	public Edge(int v, int w, double weight)
	{
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	
	// slightly tricky accessor methods (enables client code like below in comments)
	public int either() 
	{  return v; }
	
	// slightly tricky accessor methods (enables client user code like below in comments)
	public int other(int vertex){      
		if (vertex == v) 
	           return w;      
		else
	           return v;
	}
	/* The for loop below process all edges in G
	for (int v = 0; v < G.V(); v++){  
	   for (Edge e : G.adj(v))     
	   {   	
	   		int w = e.other(v);              
	   		// process edge v-w     
	   }
	}
	*/
	
	public double weight()
	{  return weight; }
	
	public final static Comparator<Edge> BY_WEIGHT = new ByWeightComparator();
	private static class ByWeightComparator implements Comparator<Edge>
	{	
		public int compare(Edge e, Edge f)	
		{		
			if (e.weight < f.weight) return -1;		
			if (e.weight > f.weight) return +1;		
			return 0;
		}
	}
	public int compareTo(Edge that)	
	{		
		if      (this.weight < that.weight) return -1;		
		else if (this.weight > that.weight) return +1;		
		else				      			return  0;
	}

} //end of Edge class

/*
Two different compare methods for edges
-compareTo() so that edges are Comparable (for use in SET)
-compare() so that client users can compare edges by weight.
*/











