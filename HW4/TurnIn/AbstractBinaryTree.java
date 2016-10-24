import java.util.*;
import java.util.LinkedList;

public abstract class AbstractBinaryTree <E>{
    private int currentTreeSize = 0;
    private long lastNodeId = -1;
    protected Node root;

    protected class Node{
        public Object data;
        private long id;
        public Node leftChild, rightChild;

        public Node(Object inData, long inId){
            if(inData == null)
                throw new NullPointerException();

            this.data = inData;
            this.id = inId;
        }
        @Override
        public String toString(){return this.data.toString();}
        public long getId(){return this.id;}
    }

    public void insertAll(E[] data){
        if(data == null)
            throw new NullPointerException();

        for(E thisItem: data)
            insert(thisItem);
    }

    public void insertAll(Collection<E> data){
        if(data == null)
            throw new NullPointerException();

        for(E thisItem : data){
            insert(thisItem);
        }
    }

    public abstract void insert(E data);
    public final int size(){return currentTreeSize;}

    protected synchronized Node buildNode(E data, boolean incrementSize){
        if(incrementSize)
            currentTreeSize ++;

        return new Node(data, ++lastNodeId);
    }

    protected synchronized final long modifyTreeSize(int amount){
        this.currentTreeSize += amount;
        return this.currentTreeSize;
    }

    public Integer[] breadthFirstTraversal(){
        Integer[] output = new Integer[currentTreeSize];

        if(root == null)
            return output;

        int outputIndex = 0;
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);

        Node thisNode;

        while(!queue.isEmpty()){
            output[outputIndex++] = (Integer)(thisNode = queue.removeFirst()).data;
            if(thisNode.leftChild  != null) queue.add(thisNode.leftChild);
            if(thisNode.rightChild != null) queue.add(thisNode.rightChild);
        }
        return output;
    }
}