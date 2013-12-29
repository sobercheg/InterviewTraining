package leetcode.oj;

import java.util.Arrays;

import static leetcode.oj.Solution.ListNode;
import static leetcode.oj.Solution.TreeNode;
import static leetcode.oj.Solution.Point;

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
        solutionTest.testZigzagConvert();
        solutionTest.testReverse();
        solutionTest.testAtoi();
        solutionTest.testIsPalindrome();
        solutionTest.testIsMatch();
        solutionTest.testMaxArea();
        solutionTest.testIntToRoman();
        solutionTest.testRomanToInt();
        solutionTest.testLongestCommonPrefix();
        solutionTest.testThreeSum();
        solutionTest.testThreeSumClosest();
        solutionTest.testFourSum();
        solutionTest.testKSum();
        solutionTest.testLetterCombinations();
        solutionTest.testRemoveNthFromEnd();
        solutionTest.testIsValid();
        solutionTest.testGenerateParenthesis();
        solutionTest.testMaxPoints();
        solutionTest.testMaxProfitI();
        solutionTest.testMaxProfitII();
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
//      unspecified behavior
//      assertEquals("d", solution.longestPalindrome("abcd"));
        assertEquals("bb", solution.longestPalindrome("bb"));
        assertEquals("aba", solution.longestPalindrome("abaa"));
        assertEquals("aaabaaa", solution.longestPalindrome("aaabaaaa"));
    }

    public void testZigzagConvert() {
        assertEquals("0481357926", solution.convert("0123456789", 3));
        assertEquals("PAHNAPLSIIGYIR", solution.convert("PAYPALISHIRING", 3));
        assertEquals("AB", solution.convert("AB", 3));
        assertEquals("ABC", solution.convert("ACB", 2));
        assertEquals("ABCED", solution.convert("ABCDE", 4));
    }

    public void testReverse() {
        assertEquals(123, solution.reverse(321));
        assertEquals(-321, solution.reverse(-123));
        assertEquals(1, solution.reverse(1));
    }

    public void testAtoi() {
        assertEquals(123, solution.atoi("123"));
        assertEquals(123, solution.atoi("  123"));
        assertEquals(1, solution.atoi("  1 2 3 "));
        assertEquals(123, solution.atoi("123www"));
        assertEquals(0, solution.atoi(" - 123www"));
        assertEquals(0, solution.atoi(" - 123.5"));
        assertEquals(0, solution.atoi(".5"));
        assertEquals(0, solution.atoi("w"));
        assertEquals(0, solution.atoi("w5"));
        assertEquals(0, solution.atoi("--1"));
        assertEquals(1, solution.atoi("+1"));
        assertEquals(0, solution.atoi("++1"));
        assertEquals(0, solution.atoi(" +0 123"));
        assertEquals(2147483647, solution.atoi("2147483648"));
        assertEquals(2147483647, solution.atoi(" 10522545459"));
    }

    public void testIsPalindrome() {
        assertEquals(true, solution.isPalindrome(1));
        assertEquals(true, solution.isPalindrome(11));
        assertEquals(true, solution.isPalindrome(121));
        assertEquals(true, solution.isPalindrome(1221));
        assertEquals(false, solution.isPalindrome(1223));
        assertEquals(false, solution.isPalindrome(122322));
        assertEquals(true, solution.isPalindrome(9898989));
    }

    public void testIsMatch() {
        assertEquals(false, solution.isMatch("bbab", "b*a*"));
        assertEquals(true, solution.isMatch("", "a*"));
        assertEquals(false, solution.isMatch("aa", "a"));
        assertEquals(true, solution.isMatch("a", "a*a"));
        assertEquals(true, solution.isMatch("bac", "c*.*b*aa*.a*"));
        assertEquals(true, solution.isMatch("bbbba", ".*a*a"));
        assertEquals(true, solution.isMatch("aa", "aa"));
        assertEquals(false, solution.isMatch("aaa", "aa"));
        assertEquals(true, solution.isMatch("aa", "a*"));
        assertEquals(true, solution.isMatch("aa", ".*"));
        assertEquals(true, solution.isMatch("ab", ".*"));
        assertEquals(true, solution.isMatch("aab", "c*a*b"));
        assertEquals(true, solution.isMatch("aab", "a.b"));
        assertEquals(false, solution.isMatch("aacacbcbabbaaaccb", "a*b.c*c*aa*c*b*c*c"));
        assertEquals(true, solution.isMatch("baccbbcbcacacbbc", "c*.*b*c*ba*b*b*.a*"));
        assertEquals(false, solution.isMatch("aaaaaaaaaaaaab", "a*a*a*a*a*a*a*a*a*a*c"));
        assertEquals(true, solution.isMatch("cbaacacaaccbaabcb", "c*b*b*.*ac*.*bc*a*"));
    }

    public void testMaxArea() {
        assertEquals(2, solution.maxArea(new int[]{1, 2, 3}));
        assertEquals(2, solution.maxArea(new int[]{3, 2, 1}));
        assertEquals(0, solution.maxArea(new int[]{1}));
        assertEquals(4, solution.maxArea(new int[]{2, 1, 2}));
        assertEquals(2, solution.maxArea(new int[]{1, 2, 1}));
        assertEquals(4, solution.maxArea(new int[]{1, 2, 4, 3}));
    }

    public void testIntToRoman() {
        assertEquals("MCMLIV", solution.intToRoman(1954));
    }

    public void testRomanToInt() {
        assertEquals(1954, solution.romanToInt("MCMLIV"));
    }

    public void testLongestCommonPrefix() {
        assertEquals("a", solution.longestCommonPrefix(new String[]{"aa", "a"}));
        assertEquals("ab", solution.longestCommonPrefix(new String[]{"abc", "abde"}));
        assertEquals("ab", solution.longestCommonPrefix(new String[]{"ab", "abde"}));
        assertEquals("", solution.longestCommonPrefix(new String[]{"ab", "bbde"}));
    }

    public void testThreeSum() {
        assertEquals(Arrays.asList(Arrays.asList(-1, 0, 1), Arrays.asList(-1, -1, 2)), solution.threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        assertEquals(Arrays.asList(Arrays.asList(0, 0, 0)), solution.threeSum(new int[]{0, 0, 0}));
        assertEquals(Arrays.asList(Arrays.asList(-1, 0, 1)), solution.threeSum(new int[]{1, -1, -1, 0}));
        assertEquals(Arrays.asList(Arrays.asList(-2, 1, 1), Arrays.asList(-2, 0, 2)), solution.threeSum(new int[]{-2, 0, 1, 1, 2}));
    }

    public void testThreeSumClosest() {
        assertEquals(2, solution.threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
        assertEquals(3, solution.threeSumClosest(new int[]{1, 1, 1, 1}, 3));
        assertEquals(1, solution.threeSumClosest(new int[]{1, 1, -1, -1, 3}, 1));
    }

    public void testFourSum() {
        testKSum();
    }

    public void testKSum() {
        assertEquals(Arrays.asList(Arrays.asList(-1, 0, 1), Arrays.asList(-1, -1, 2)), solution.kSum(new int[]{-1, 0, 1, 2, -1, -4}, 0, 3));
        assertEquals(Arrays.asList(Arrays.asList(0, 0, 0)), solution.kSum(new int[]{0, 0, 0}, 0, 3));
        assertEquals(Arrays.asList(Arrays.asList(-1, 0, 1)), solution.kSum(new int[]{1, -1, -1, 0}, 0, 3));
        assertEquals(Arrays.asList(Arrays.asList(-2, 1, 1), Arrays.asList(-2, 0, 2)), solution.kSum(new int[]{-2, 0, 1, 1, 2}, 0, 3));
        assertEquals(Arrays.asList(Arrays.asList(-1, 0, 0, 1), Arrays.asList(-2, -1, 1, 2), Arrays.asList(-2, 0, 0, 2)),
                solution.fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
        assertEquals(Arrays.asList(Arrays.asList(-3, -1, 0, 4)), solution.kSum(new int[]{-3, -1, 0, 2, 4, 5}, 0, 4));
        assertEquals(Arrays.asList(Arrays.asList(-4, 0, 1, 2), Arrays.asList(-1, -1, 0, 1)), solution.kSum(new int[]{-1, 0, 1, 2, -1, -4}, -1, 4));

    }

    public void testLetterCombinations() {
        assertEquals(Arrays.asList("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"), solution.letterCombinations("23"));
        assertEquals(Arrays.asList(""), solution.letterCombinations(""));
    }

    private void testRemoveNthFromEnd() {
        // remove head
        ListNode head = new ListNode(1, new ListNode(2));
        ListNode removed = solution.removeNthFromEnd(head, 2);
        assertEquals(2, removed.val);
        assertEquals(null, removed.next);

        // one element, n = 1
        head = new ListNode(1);
        removed = solution.removeNthFromEnd(head, 1);
        assertEquals(null, removed);

        // normal list
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        removed = solution.removeNthFromEnd(head, 2);
        assertEquals(1, removed.val);
        assertEquals(2, removed.next.val);
        assertEquals(3, removed.next.next.val);
        assertEquals(5, removed.next.next.next.val);
        assertEquals(null, removed.next.next.next.next);
    }

    public void testIsValid() {
        assertEquals(false, solution.isValid("["));
        assertEquals(false, solution.isValid("]"));
        assertEquals(false, solution.isValid("()]"));
        assertEquals(false, solution.isValid("[()"));
        assertEquals(true, solution.isValid("()"));
        assertEquals(true, solution.isValid("({})[]"));
    }

    public void testGenerateParenthesis() {
        assertEquals(Arrays.asList(""), solution.generateParenthesis(0));
        assertEquals(Arrays.asList("()"), solution.generateParenthesis(1));
        assertEquals(Arrays.asList("(())", "()()"), solution.generateParenthesis(2));
        assertEquals(Arrays.asList("((()))", "(()())", "(())()", "()(())", "()()()"), solution.generateParenthesis(3));
    }

    public void testMaxPoints() {
        assertEquals(3, solution.maxPoints(new Point[]{new Point(0, 0), new Point(0, 4), new Point(2, 3), new Point(4, 2)}));
        assertEquals(4, solution.maxPoints(new Point[]{new Point(0, 0), new Point(0, 0), new Point(2, 2), new Point(2, 2)}));
        assertEquals(3, solution.maxPoints(new Point[]{new Point(0, 0), new Point(1, 1), new Point(0, 0)}));
        assertEquals(3, solution.maxPoints(new Point[]{new Point(1, 1), new Point(1, 1), new Point(1, 1)}));
    }

    public void testMaxProfitI() {
        assertEquals(6, solution.maxProfitI(new int[]{2, 1, 3, 2, 7, 0, 1}));
    }

    public void testMaxProfitII() {
        assertEquals(1, solution.maxProfitI(new int[]{1, 2}));
    }

    /**
     * *************************** Helper test methods ***********************
     */
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
