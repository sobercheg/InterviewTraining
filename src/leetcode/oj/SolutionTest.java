package leetcode.oj;

import java.util.Arrays;

/**
 * Created by Sobercheg on 12/24/13.
 */
public class SolutionTest {
    private final Solution solution = new Solution();

    public static void main(String[] args) {
        SolutionTest solutionTest = new SolutionTest();
        solutionTest.testTwoSum();
        solutionTest.testEvalRPN();
        solutionTest.testPostorderTraversal();
    }

    public void testTwoSum() {
        assertEquals(new int[]{2, 3}, solution.twoSum(new int[]{3, 1, 5, 8}, 6));
        assertEquals(new int[]{-1, -1}, solution.twoSum(new int[]{3, 1, 5, 8}, 7));
    }

    public void testEvalRPN() {
        assertEquals(9, solution.evalRPN(new String[]{"2", "1", "+", "3", "*"}));
        assertEquals(6, solution.evalRPN(new String[]{"4", "13", "5", "/", "+"}));
    }

    public void testPostorderTraversal() {
        Solution.TreeNode root = new Solution.TreeNode(1);
        Solution.TreeNode right = new Solution.TreeNode(2);
        Solution.TreeNode rightLeft = new Solution.TreeNode(3);
        right.left = rightLeft;
        root.right = right;
        assertEquals(Arrays.asList(3, 2, 1), solution.postorderTraversal(root));
        assertEquals(Arrays.asList(1), solution.postorderTraversal(new Solution.TreeNode(1)));
        assertEquals(Arrays.asList(), solution.postorderTraversal(null));

    }

    public static void assertEquals(Object expected, Object actual) {
        if (checkNulls(expected, actual)) return;
        if (!expected.equals(actual))
            throw new IllegalStateException(String.format("Expected [%s] is not equal to actual [%s]", expected, actual));
    }

    public static void assertEquals(int[] expected, int[] actual) {
        if (checkNulls(expected, actual)) return;
        if (!Arrays.equals(expected, actual))
            throw new IllegalStateException(String.format("Expected [%s] is not equal to actual [%s]",
                    Arrays.toString(expected), Arrays.toString(actual)));
    }

    private static boolean checkNulls(Object expected, Object actual) {
        if (expected == null && actual == null) return true;
        if (expected == null) throw new IllegalStateException("Actual is not null");
        if (actual == null) throw new IllegalStateException("Expected is not null");
        return false;
    }
}
