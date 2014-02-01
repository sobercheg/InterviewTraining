package leetcode.oj;

import java.util.Arrays;

import static leetcode.oj.Solution.ListNode;
import static leetcode.oj.SolutionTest.assertArrayEquals;
import static leetcode.oj.SolutionTest.assertEquals;

/**
 * Created by Sobercheg on 1/29/14.
 */
public class Solution2Test {
    private final Solution2 solution = new Solution2();

    public static void main(String[] args) {
        Solution2Test solutionTest = new Solution2Test();
        solutionTest.testMinPathSum();
        solutionTest.testMergeTwoLists();
        solutionTest.testAddBinary();
        solutionTest.testPlusOne();
        solutionTest.testIsNumber();
        solutionTest.testFullJustify();
        solutionTest.testClimbStairs();
        solutionTest.testSimplifyPath();
        solutionTest.testMinDistance();
        solutionTest.testSetZeros();
        solutionTest.testMinWindow();
        solutionTest.testSearchMatrix();
    }

    public void testMinPathSum() {
        assertEquals(18, solution.minPathSum(new int[][]{
                {1, 2, 3},
                {4, 5, 5},
                {6, 8, 7},
        })); // 1 2 3 5 7

        assertEquals(1, solution.minPathSum(new int[][]{
                {1}
        }));
    }

    public void testMergeTwoLists() {
        ListNode list1 = new ListNode(1, new ListNode(3, new ListNode(5)));
        ListNode list2 = new ListNode(2, new ListNode(4, new ListNode(6)));
        ListNode merged = solution.mergeTwoLists(list1, list2);
        assertEquals(1, merged.val);
        assertEquals(2, merged.next.val);
        assertEquals(3, merged.next.next.val);
        assertEquals(4, merged.next.next.next.val);
        assertEquals(5, merged.next.next.next.next.val);
        assertEquals(6, merged.next.next.next.next.next.val);
        assertEquals(null, merged.next.next.next.next.next.next);

        list1 = null;
        list2 = new ListNode(1);
        merged = solution.mergeTwoLists(list1, list2);
        assertEquals(1, merged.val);
        assertEquals(null, merged.next);

        list1 = new ListNode(2);
        list2 = new ListNode(1);
        merged = solution.mergeTwoLists(list1, list2);
        assertEquals(1, merged.val);
        assertEquals(2, merged.next.val);
        assertEquals(null, merged.next.next);
    }

    public void testAddBinary() {
        assertEquals("100", solution.addBinary("11", "1"));
    }

    public void testPlusOne() {
        assertEquals(new int[]{1, 0, 0}, solution.plusOne(new int[]{9, 9}));
    }

    public void testIsNumber() {
        assertEquals(true, solution.isNumber("1"));
        assertEquals(true, solution.isNumber("1e1"));
        assertEquals(true, solution.isNumber("-1e1"));
        assertEquals(true, solution.isNumber("-1."));
        assertEquals(true, solution.isNumber("-1.2 "));
        assertEquals(true, solution.isNumber("-1.2e23"));
        assertEquals(true, solution.isNumber("-1.2e-23"));
        assertEquals(false, solution.isNumber("-1.2e-23e"));
        assertEquals(false, solution.isNumber("a"));
        assertEquals(false, solution.isNumber(" -."));
    }

    public void testFullJustify() {
        assertEquals(Arrays.asList(
                "This    is    an",
                "example  of text",
                "justification.  "),
                solution.fullJustify(
                        new String[]{"This", "is", "an", "example", "of", "text", "justification."}, 16));

        assertEquals(Arrays.asList(
                "                "),
                solution.fullJustify(
                        new String[]{""}, 16));

        assertEquals(Arrays.asList("a"), solution.fullJustify(new String[]{"a"}, 1));

        assertEquals(Arrays.asList(
                "Listen",
                "to    ",
                "many, ",
                "speak ",
                "to   a",
                "few.  "),
                solution.fullJustify(
                        new String[]{"Listen", "to", "many,", "speak", "to", "a", "few."}, 6));

        assertEquals(Arrays.asList(
                "world  owes  you a living; the",
                "world                         "),
                solution.fullJustify(
                        new String[]{"world", "owes", "you", "a", "living;", "the", "world"}, 30));

    }

    public void testClimbStairs() {
        assertEquals(3, solution.climbStairs(3));
        assertEquals(5, solution.climbStairs(4));
    }

    public void testSimplifyPath() {
        assertEquals("/home", solution.simplifyPath("/home/"));
        assertEquals("/c", solution.simplifyPath("/a/./b/../../c/"));
    }

    public void testMinDistance() {
        assertEquals(3, solution.minDistance("bugaga", "bgzgz"));
        assertEquals(1, solution.minDistance("a", ""));
    }

    public void testSetZeros() {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {1, 0, 4},
                {1, 1, 1},
        };
        solution.setZeroes(matrix);
        assertArrayEquals(matrix, new int[][]{
                {1, 0, 3},
                {0, 0, 0},
                {1, 0, 1},
        });

        matrix = new int[][]{
                {1, 2},
                {1, 0},
        };
        solution.setZeroes(matrix);
        assertArrayEquals(matrix, new int[][]{
                {1, 0},
                {0, 0},
        });

        matrix = new int[][]{
                {0, 0, 0, 5},
                {4, 3, 1, 4},
                {0, 1, 1, 4},
                {1, 2, 1, 3},
                {0, 0, 1, 1}
        };
        solution.setZeroes(matrix);
        assertArrayEquals(matrix, new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 3},
                {0, 0, 0, 0}
        });
    }

    public void testMinWindow() {
        assertEquals("ba", solution.minWindow("bba", "ab"));
        assertEquals("BANC", solution.minWindow("ADOBECODEBANC", "ABC"));
        assertEquals("", solution.minWindow("ADOBECODEBANC", "ABQ"));
        assertEquals("a", solution.minWindow("a", "a"));
        assertEquals("b", solution.minWindow("ab", "b"));
        assertEquals("aa", solution.minWindow("aa", "aa"));
        assertEquals("aec", solution.minWindow("cabefgecdaecf", "cae"));
        assertEquals("cwae", solution.minWindow("cabwefgewcwaefgcf", "cae"));
    }

    public void testSearchMatrix() {
        int[][] matrix = new int[][]{
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 50},
        };
        assertEquals(true, solution.searchMatrix(matrix, 3));
        assertEquals(false, solution.searchMatrix(matrix, 4));
        assertEquals(false, solution.searchMatrix(matrix, 0));
        assertEquals(false, solution.searchMatrix(matrix, 70));
        assertEquals(true, solution.searchMatrix(matrix, 11));

        matrix = new int[][]{
                {1, 3},
        };
        assertEquals(true, solution.searchMatrix(matrix, 1));
        assertEquals(true, solution.searchMatrix(matrix, 3));
        assertEquals(false, solution.searchMatrix(matrix, 2));
    }
}
