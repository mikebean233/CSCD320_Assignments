//import java.util.Arrays;

public class Tester {
	public static void main(String[] args){
		WeightedGraph graph = new WeightedGraph();

		int[][] edgeData = new int[][]{
		//    v1  v2  weight
			{ 0  ,1  ,4  },
			{ 0  ,2  ,6  },
			{ 0  ,3  ,16 },
			{ 1  ,7  ,24 },
			{ 2  ,3  ,8  },
			{ 2  ,5  ,5  },
			{ 2  ,7  ,23 },
			{ 3  ,5  ,10 },
			{ 3  ,4  ,21 },
			{ 4  ,5  ,14 },
			{ 4  ,6  ,7  },
			{ 5  ,6  ,11 },
			{ 5  ,7  ,18 },
			{ 6  ,7  ,7  }
		};

		for(int[] thisEdge : edgeData)
			graph.addEdge(new Edge(thisEdge[0], thisEdge[1], thisEdge[2]));

		WeightedGraph MST = graph.getMST();

		System.out.println("The MST contains the following edges:");
		for(Edge thisEdge : MST.edges())
			System.out.println(thisEdge);

		//System.out.println("------------- GRAPH ----------------");
		//System.out.println(Arrays.toString(graph.edges().toArray(new Edge[]{})));

		//System.out.println("--------------- MST ----------------");
		//System.out.println(Arrays.toString(MST.edges().toArray(new Edge[]{})));
	}
}
