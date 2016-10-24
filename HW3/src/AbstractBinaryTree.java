import java.io.PrintStream;
import java.util.*;

public abstract class AbstractBinaryTree <E>{
    private long currentTreeSize = 0;
    private long lastNodeId = -1;
    private long printedPathCount = 0;
    protected Node root;

    protected class Node{
        public Object data;
        private long id;
        public Node leftChild, rightChild, parent;

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

    public void insertAll(Collection<E> data){
        if(data == null)
            throw new NullPointerException();

        for(E thisItem : data){
            insert(thisItem);
        }
    }

    public abstract void insert(E data);
    public final String size(){return currentTreeSize + "";}

    protected synchronized Node buildNode(E data){
        return new Node(data, ++lastNodeId);
    }

    protected synchronized final long modifyTreeSize(int amount){
        this.currentTreeSize += amount;
        return this.currentTreeSize;
    }

    public Object[] inOrderTraversal(){return inOrderTraversal(this.root).toArray();}

    @SuppressWarnings("unchecked")
    private ArrayList<E> inOrderTraversal(Node root){
        if(root == null)
            return new ArrayList<>();

        ArrayList<E> thisList = new ArrayList<>();
        thisList.addAll(inOrderTraversal(root.leftChild));
        thisList.add((E)root.data);
        thisList.addAll(inOrderTraversal(root.rightChild));
        return thisList;
    }

    public Object[] postOrderTraversal(){return postOrderTraversal(this.root).toArray();}

    @SuppressWarnings("unchecked")
    public ArrayList<E> postOrderTraversal(Node root){
        if(root == null)
            return new ArrayList<>();

        ArrayList<E> thisList = new ArrayList<>();
        thisList.addAll(postOrderTraversal(root.leftChild));
        thisList.addAll(postOrderTraversal(root.rightChild));
        thisList.add((E)root.data);
        return thisList;
    }

    public synchronized final void printAllPath(PrintStream out){
        if(out == null)
            throw new NullPointerException();

        printedPathCount = 0;
        printAllPath(this.root, out, new ArrayList<E>());
    }

    @SuppressWarnings("unchecked")
    private void printAllPath(Node cur, PrintStream out, ArrayList<E> thisPath){
        if(cur == null) {
            return;
        }
        thisPath.add((E)cur.data);

        if(cur.leftChild == null && cur.rightChild == null) {
            out.print("Path " + (++printedPathCount) + ": ");
            for(E thisItem : thisPath.subList(0, thisPath.size() - 1))
                out.print(thisItem + "->");
            out.println(thisPath.get(thisPath.size() - 1));
        }
        else
        {
            printAllPath(cur.leftChild , out, new ArrayList<E>(thisPath));
            printAllPath(cur.rightChild, out, new ArrayList<E>(thisPath));
        }
    }
}