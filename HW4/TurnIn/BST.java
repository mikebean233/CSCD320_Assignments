public class BST extends AbstractBinaryTree<Integer>{
    public BST(){}

    public BST(Integer[] data){
        if(data == null)
            throw new NullPointerException();

        insertAll(data);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void insert(Integer data) {
        if(data == null)
            throw new NullPointerException();

        if(root == null){
            root = buildNode(data, true);
            return;
        }

        Node currentNode = root;
        while(currentNode != null){
            if(currentNode.data.equals(data))
                return;
            else if(((Comparable<Integer>)currentNode.data).compareTo(data) > 0)
                currentNode = (currentNode.leftChild == null)  ? (currentNode.leftChild  = buildNode(data, true)) : currentNode.leftChild;
            else
                currentNode = (currentNode.rightChild == null) ? (currentNode.rightChild = buildNode(data, true)) : currentNode.rightChild;
        }
    }
}
