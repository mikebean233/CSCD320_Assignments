public class BST<E extends Comparable<E>> extends AbstractBinaryTree<E> {
    public void insert(E data) {
        insertInternal(data);
    }

    private synchronized void insertInternal(E data){
        if(this.root == null) {
            this.root = buildNode(data);
            modifyTreeSize(1);
            return;
        }
        insertNode(this.root, data);
    }

    @SuppressWarnings("unchecked")
    private boolean insertNode(Node currentNode, E data ){
        if(data == null || currentNode == null)
            throw new NullPointerException();

        if(data.compareTo((E)currentNode.data) < 0){
            if(currentNode.leftChild == null)
                currentNode.leftChild = buildNode(data);
            else
                return insertNode(currentNode.leftChild, data);
        }
        else if(data.compareTo((E)currentNode.data) > 0){
            if(currentNode.rightChild == null)
                currentNode.rightChild = buildNode(data);
            else
                return insertNode(currentNode.rightChild, data);
        }
        else{
            return false;
        }
        modifyTreeSize(1);
        return true;
    }
}
