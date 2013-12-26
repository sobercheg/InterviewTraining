package leetcode.oj;

import java.util.Arrays;

/**
 * Created by Sobercheg on 12/24/13.
 * Unit tests for LeetCode OJ solutions.
 */
public class SolutionTest {
    private final Solution solution = new Solution();

    public static void main(String[] args) {
        SolutionTest solutionTest = new SolutionTest();
        solutionTest.testTwoSum();
        solutionTest.testEvalRPN();
        solutionTest.testPostorderTraversal();
        solutionTest.testPreorderTraversal();
        solutionTest.testLengthOfLongestSubstring();
//        solutionTest.testFindMedianSortedArrays();
        solutionTest.testAddTwoNumbers();
        solutionTest.testLongestPalindrome();
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
        assertEquals(Arrays.asList(1), solution.postorderTraversal(new TreeNode(1)));

        TreeNode root = new TreeNode(1,
                null, new TreeNode(2,
                new TreeNode(3), null));
        assertEquals(Arrays.asList(3, 2, 1), solution.postorderTraversal(root));

    }

    public void testPreorderTraversal() {
        assertEquals(Arrays.asList(), solution.postorderTraversal(null));
        assertEquals(Arrays.asList(1), solution.postorderTraversal(new TreeNode(1)));

        TreeNode root = new TreeNode(1,
                null, new TreeNode(2,
                new TreeNode(3), null));
        assertEquals(Arrays.asList(1, 2, 3), solution.preorderTraversal(root));

        TreeNode root2 = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(3,
                                new TreeNode(4), new TreeNode(5)),
                        new TreeNode(6)), new TreeNode(7));

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), solution.preorderTraversal(root2));

    }

    public void testLengthOfLongestSubstring() {
        assertEquals(0, solution.lengthOfLongestSubstring(null));
        assertEquals(0, solution.lengthOfLongestSubstring(""));
        assertEquals(3, solution.lengthOfLongestSubstring("abc"));
        assertEquals(3, solution.lengthOfLongestSubstring("abca"));
        assertEquals(3, solution.lengthOfLongestSubstring("abcabcbb"));
        assertEquals(12, solution.lengthOfLongestSubstring("wlrbbmqbhcdarzowkkyhiddqscdxrjmowfrxsjybldbefsarcbynecdyggxxpklorellnmpapqfwkhopkmco"));
    }

    // TODO: fix solution
    public void testFindMedianSortedArrays() {
        assertEquals(60.0, solution.findMedianSortedArrays(new int[]{1, 2, 100, 101}, new int[]{50, 60, 70}));
        assertEquals(26.0, solution.findMedianSortedArrays(new int[]{1, 2}, new int[]{50, 60}));
        assertEquals(3.0, solution.findMedianSortedArrays(new int[]{1, 3, 5}, new int[]{2, 4}));
        assertEquals(3.0, solution.findMedianSortedArrays(new int[]{1, 3, 5}, new int[]{2, 4}));
        assertEquals(1.0, solution.findMedianSortedArrays(new int[]{1}, new int[]{}));
        assertEquals(2.5, solution.findMedianSortedArrays(new int[]{2, 3}, new int[]{}));
        assertEquals(1.5, solution.findMedianSortedArrays(new int[]{1, 2}, new int[]{1, 2}));
        assertEquals(1.0, solution.findMedianSortedArrays(new int[]{1, 1}, new int[]{1, 2}));
        assertEquals(7.0, solution.findMedianSortedArrays(new int[]{8, 9}, new int[]{1, 2, 3, 4, 5, 6, 7, 10}));
        assertEquals(2.0, solution.findMedianSortedArrays(new int[]{1, 2, 2}, new int[]{1, 2, 3}));
    }

    public void testAddTwoNumbers() {
        ListNode val1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode val2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode sum = solution.addTwoNumbers(val1, val2);
        assertEquals(7, sum.val);
        assertEquals(0, sum.next.val);
        assertEquals(8, sum.next.next.val);
        assertEquals(null, sum.next.next.next);

        val1 = new ListNode(1);
        val2 = new ListNode(9, new ListNode(9));
        sum = solution.addTwoNumbers(val1, val2);
        assertEquals(0, sum.val);
        assertEquals(0, sum.next.val);
        assertEquals(1, sum.next.next.val);
        assertEquals(null, sum.next.next.next);


    }

    public void testLongestPalindrome() {
        assertEquals("aba", solution.longestPalindrome("cabaq"));
        assertEquals("abba", solution.longestPalindrome("abba"));
        assertEquals("d", solution.longestPalindrome("abcd"));
        assertEquals("bb", solution.longestPalindrome("bb"));
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
