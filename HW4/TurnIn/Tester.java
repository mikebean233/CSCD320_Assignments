import java.util.Arrays;

public class Tester {
    public static void main(String[] args){
        BST myBST = new BST(new Integer[]{8, 3, 10, 1, 6, 14, 4, 7, 13});
        System.out.println(Arrays.toString(myBST.breadthFirstTraversal()));
    }
}
