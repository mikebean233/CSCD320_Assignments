import java.util.Arrays;

public class Tester {
    public static void main(String[] args){
        Integer[] originalInOrderArray   = new Integer[]{ 9, 5, 1, 7, 2,12, 8, 4, 3,11};
        Integer[] originalPostOrderArray = new Integer[]{ 9, 1, 2,12, 7, 5, 3,11, 4, 8};

        BinaryTree myTree = BinaryTree.BuildTree(originalInOrderArray, originalPostOrderArray);

        Object[] producedInOrderArray   = myTree.inOrderTraversal();
        Object[] producedPostOrderArray = myTree.postOrderTraversal();

        System.out.println("Original in order array: " + Arrays.toString(originalInOrderArray));
        System.out.println("produced in order array: " + Arrays.toString(producedInOrderArray));
        System.out.println();

        System.out.println("Original post order array: " + Arrays.toString(originalPostOrderArray));
        System.out.println("produced post order array: " + Arrays.toString(producedPostOrderArray));
        System.out.println();

        System.out.println("Do the in order arrays match?   " + (Arrays.equals(originalInOrderArray  , producedInOrderArray  ) ? "yes" : "no"));
        System.out.println("Do the post order arrays match? " + (Arrays.equals(originalPostOrderArray, producedPostOrderArray) ? "yes" : "no"));
    }
}
