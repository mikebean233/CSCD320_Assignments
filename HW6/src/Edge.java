class Edge implements Comparable<Edge>{
	private static int idCount = 0;
	private int _v1, _v2, _weight, id;

	public Edge(int v1, int v2, int weight){
		_v1 = v1;
		_v2 = v2;
		_weight = weight;
		id = idCount++;
	}

	@Override
	public boolean equals(Object that){
		return (that instanceof Edge) ? id == ((Edge)that).id : false;
	}

	@Override
	public int compareTo(Edge that){
		if(that == null)
			throw new NullPointerException();

		return _weight - that._weight;
	}

	public int weight(){return _weight;}
	public int v1(){return _v1;}
	public int v2(){return _v2;}

	@Override
	public String toString(){
		return _v1 + "->" + _v2;
	}
}