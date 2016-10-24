import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class BinaryTree extends AbstractBinaryTree<Integer>{
    @Override
    public void insert(Integer data) {
        throw new UnsupportedOperationException("This implementation of Binary tree does not support the insert() function");
    }

    private BinaryTree(Integer inOrder[], Integer postOrder[]){
        super();

        this.root = buildNode(postOrder[postOrder.length - 1]);
        insert(this.root, inOrder, postOrder, new ArrayList<>( Arrays.asList((Integer)root.data)));
    }

    public static BinaryTree BuildTree(Integer inOrder[], Integer postOrder[]){
        if(inOrder == null && postOrder == null)
            throw new NullPointerException();

        if(inOrder.length != postOrder.length)
            throw new IllegalArgumentException("inOrder and postOrder must be the same length");

        Hashtable<Integer, Boolean> postOrderTable = new Hashtable<>();
        for(Integer postOrderInt : postOrder){
            postOrderTable.put(postOrderInt, true);
        }

        for(Integer inOrderInt : inOrder) {
            if(!postOrderTable.containsKey(inOrderInt))
                throw new IllegalArgumentException("inOrder and postOrder must contain the same set of values!!");
        }

        // Now that we know we have two arrays that contain the same data, we can continue
        return new BinaryTree(inOrder, postOrder);
    }

    @SuppressWarnings("unchecked")
    private void insert(Node root, Integer inOrder[], Integer postOrder[], ArrayList<Integer> foundRoots){
        if(root == null)
            return;

        ArrayList<Integer> leftChildren  = new ArrayList<Integer>();
        ArrayList<Integer> rightChildren = new ArrayList<Integer>();
        int i = 0;

        // move the cursor up to the start of the child nodes in the in-order array
        while(foundRoots.contains(inOrder[i]) && !inOrder[i].equals(root.data))
            ++i;

        // Determine what nodes are in our right and left subTrees
        while(!inOrder[i].equals((Integer)root.data))
            leftChildren.add(inOrder[i++]);
        ++i;
        while(i < inOrder.length && !foundRoots.contains(inOrder[i]))
            rightChildren.add(inOrder[i++]);

        i = 0;

        // move the cursor up to the start of the child nodes in the post-order array
        while(foundRoots.contains(postOrder[i]) && !postOrder[i].equals(root.data))
            ++i;

        // Determine the left and right child root nodes
        if(!leftChildren.isEmpty()) {
            while (leftChildren.contains(postOrder[i]))
                ++i;
            foundRoots.add( (Integer)(root.leftChild =  buildNode(postOrder[i - 1])).data );
            modifyTreeSize(1);
        }

        if(!rightChildren.isEmpty()){
            while(!postOrder[i].equals((Integer)root.data))
                ++i;
            foundRoots.add( (Integer)(root.rightChild =  buildNode(postOrder[i - 1])).data );
            modifyTreeSize(1);
        }

        insert(root.leftChild, inOrder, postOrder, foundRoots);
        insert(root.rightChild, inOrder, postOrder, foundRoots);
    }
}