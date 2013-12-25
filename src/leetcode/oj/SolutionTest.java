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
        solutionTest.testPreorderTraversal();
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
        assertEquals(Arrays.asList(), solution.postorderTraversal(null));
        assertEquals(Arrays.asList(1), solution.postorderTraversal(new Solution.TreeNode(1)));

        Solution.TreeNode root = new Solution.TreeNode(1,
                null, new Solution.TreeNode(2,
                new Solution.TreeNode(3), null));
        assertEquals(Arrays.asList(3, 2, 1), solution.postorderTraversal(root));

    }

    public void testPreorderTraversal() {
        assertEquals(Arrays.asList(), solution.postorderTraversal(null));
        assertEquals(Arrays.asList(1), solution.postorderTraversal(new Solution.TreeNode(1)));

        Solution.TreeNode root = new Solution.TreeNode(1,
                null, new Solution.TreeNode(2,
                new Solution.TreeNode(3), null));
        assertEquals(Arrays.asList(1, 2, 3), solution.preorderTraversal(root));

        Solution.TreeNode root2 = new Solution.TreeNode(1,
                new Solution.TreeNode(2,
                        new Solution.TreeNode(3,
                                new Solution.TreeNode(4), new Solution.TreeNode(5)),
                        new Solution.TreeNode(6)), new Solution.TreeNode(7));

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), solution.preorderTraversal(root2));

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
