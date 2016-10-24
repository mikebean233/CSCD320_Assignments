import java.util.Arrays;
import java.util.Collection;

public class Tester {
    public static void main(String[] args){
        BST<Integer> myBST = new BST<>();

        Collection<Integer> data = Arrays.asList(8,3,1,6,4,7,10,14,13);

        for(Integer thisInteger : data)
            myBST.insert(thisInteger);

        myBST.printAllPath(System.out);
        System.out.println("Tree Size: " + myBST.size());
    }
}
/*
    printing all of the paths takes place in the printAllPath() method
    of the AbstractBinaryTree class.

    printing is acomplished by recursively calling printAllpath until a leaf node is reached.

    Each time the method is run it will recursively call it's self using cur.left, then cur.right
    as the cur parameter.

    during each recursive call a clone of thisPath is created, this is necessary so that
    we can keep track of different paths.

    the variable printedPathCount is used to keep track of then number of paths that have
    been printed, this makes it possible to
 */