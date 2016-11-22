import java.util.*;

public class WeightedGraph {
	int vertexCount = 0;

	Hashtable<Integer,Set<Edge>> adjacencyMap = new Hashtable<>();

	public WeightedGraph addEdge(Edge newEdge){
		if(!adjacencyMap.containsKey(newEdge.v1()))
			adjacencyMap.put(newEdge.v1(), new HashSet<>());

		int highestLocalVertex = Math.max(newEdge.v1(), newEdge.v2());
		vertexCount = Math.max(vertexCount, highestLocalVertex + 1);

		adjacencyMap.get(newEdge.v1()).add(newEdge);
		return this;
	}

	public List<Edge> edges(){
		ArrayList<Edge> result = new ArrayList<>();
		for(Integer thisVertex: adjacencyMap.keySet())
			result.addAll(adjacencyMap.get(thisVertex));

		return result;
	}

	// Use Kruskals Algorithm to build a MST
	public WeightedGraph getMST(){
		WeightedGraph output = new WeightedGraph();

		List<Edge> edges = edges();
		Collections.sort(edges, (o1, o2) -> o1.weight() - o2.weight());

		UnionFind unionFind = new UnionFind(vertexCount);

		for(Edge thisEdge: edges){
			if(!unionFind.makesCycle(thisEdge.v1(), thisEdge.v2())){
				unionFind.addEdge(thisEdge);
				output.addEdge(thisEdge);
			}
		}
		return output;
	}

	public class UnionFind{
		int data[];
		int setCount = 0;

		public UnionFind(int size){
	   		data = new int[size];

			for(int i = 0; i < data.length; ++i)
				data[i] = -1;
		}

		public void addEdge(Edge newEdge){
			int v1    = newEdge.v1();
			int v2    = newEdge.v2();
			int v1Set = data[newEdge.v1()];
			int v2Set = data[newEdge.v2()];

			// Case 1: Neither vertex is in a set
			if(v1Set == -1 && v2Set == -1) {
				data[v1] = data[v2] = setCount++;
			}
			// Case 2: Only one vertex is in a set
			else if(v1Set == -1 || v2Set == -1 ) {
				if (v1Set == -1)
					data[v1] = v2Set;
				else
					data[v2] = v1Set;
			}
			// Case 3: Both vertices are in the same Set
			else if(v1Set == v2Set) {
				doNothing();
			}
			// Case4: Both Vertices are in different sets
			else
				union(v1, v2);
		}

		private void doNothing(){}

		public boolean makesCycle(int vertex1, int vertex2){
			int v1Set = data[vertex1];
			int v2Set = data[vertex2];

			if(v1Set == -1 || v2Set == -1)
				return false;

			return v1Set == v2Set;
		}

		public int find(int vertex){
			return data[vertex];
		}

		private void union(int vertex1, int vertex2){
			int newSetId = data[vertex1];
			int oldSetId = data[vertex2];

			if(newSetId == oldSetId)
				return;

			for(int i = 0; i < data.length; ++i)
				if(data[i] == oldSetId)
					data[i] = newSetId;
		}
	}
}
