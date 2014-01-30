package leetcode.oj;

import java.util.Arrays;

import static leetcode.oj.Solution.ListNode;

/**
 * Created by Sobercheg on 1/29/14.
 * http://oj.leetcode.com/problems/
 */
public class Solution2 {

    /**
     * <a href="http://oj.leetcode.com/problems/minimum-path-sum/">Minimum Path Sum</a>
     * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right
     * which minimizes the sum of all numbers along its path.
     * <p/>
     * Note: You can only move either down or right at any point in time.
     */
    public int minPathSum(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 && j == 0) continue;
                grid[i][j] += Math.min(
                        i == 0 ? Integer.MAX_VALUE : grid[i - 1][j],
                        j == 0 ? Integer.MAX_VALUE : grid[i][j - 1]);
            }
        }
        return grid[n - 1][m - 1];
    }

    /**
     * <a href="http://oj.leetcode.com/problems/merge-two-sorted-lists/">Merge Two Sorted Lists</a>
     * Merge two sorted linked lists and return it as a new list. The new list should be made by splicing
     * together the nodes of the first two lists.
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode prevNode = null;
        ListNode newHead = null;

        while (l1 != null || l2 != null) {
            if (l1 != null && l2 != null && l1.val < l2.val || l2 == null) {
                if (prevNode == null)
                    prevNode = l1;
                else {
                    prevNode.next = l1;
                    prevNode = prevNode.next;
                }
                l1 = l1.next;
            } else {
                if (prevNode == null)
                    prevNode = l2;
                else {
                    prevNode.next = l2;
                    prevNode = prevNode.next;
                }
                l2 = l2.next;
            }

            if (newHead == null)
                newHead = prevNode;
        }
        return newHead;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/add-binary/">Add Binary</a>
     * Given two binary strings, return their sum (also a binary string).
     * <p/>
     * For example,
     * a = "11"
     * b = "1"
     * Return "100".
     */
    public String addBinary(String a, String b) {
        // pad with zeros: "11", "1" -> "11", "01"
        char[] zeros = new char[Math.abs(a.length() - b.length())];
        Arrays.fill(zeros, '0');
        if (a.length() < b.length())
            a = new String(zeros) + a;
        else
            b = new String(zeros) + b;

        StringBuilder result = new StringBuilder();
        int carryover = 0;
        for (int i = a.length() - 1; i >= 0; i--) {
            int sum = (a.charAt(i) - '0') + (b.charAt(i) - '0') + carryover;
            carryover = sum > 1 ? 1 : 0;
            result.insert(0, (char) ('0' + sum % 2));
        }
        if (carryover > 0) result.insert(0, '1');
        return result.toString();
    }
}
