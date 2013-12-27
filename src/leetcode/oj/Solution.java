package leetcode.oj;

import java.util.*;

/**
 * Created by Sobercheg on 12/24/13.
 * http://oj.leetcode.com/problems/
 */
public class Solution {

    /**
     * <a href="http://oj.leetcode.com/problems/two-sum/">Two Sum</a>
     * Problem: Given an array of integers, find two numbers such that they add up to a specific target number.
     * <p/>
     * First idea:
     * A naive approach would be to compare all elements with each other and find the first pair matching target.
     * <p/>
     * Better idea:
     * A better solution is to have a map of numbers to their indices: if the map contains (target-number[i])
     * return the number index and the current index. Otherwise add a new element to the map.
     */
    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> numToIndexMap = new HashMap<Integer, Integer>(numbers.length);
        for (int i = 0; i < numbers.length; i++) {
            int toTarget = target - numbers[i];
            if (numToIndexMap.containsKey(toTarget)) {
                return new int[]{numToIndexMap.get(toTarget) + 1, i + 1};
            }
            numToIndexMap.put(numbers[i], i);
        }

        // solution not found
        return new int[]{-1, -1};
    }


    /**
     * <a href="http://oj.leetcode.com/problems/evaluate-reverse-polish-notation/">Evaluate Reverse Polish Notation</a>
     * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
     * <p/>
     * Solution:
     * <pre>
     * Let's use a Stack of operands. Iterate over the array of tokens and do the following:
     *  - if the next token is an operand (number) put it to Stack
     *  - else
     *  - - pop last two Stack values
     *  - - apply the token operator
     *  - - push the result back to Stack
     *  At the end Stack should have only one element which is the result.
     *  If Stack has 0 or more than 1 element the input expression was wrong.
     * </pre>
     * Limitation: only integers are used. So division operations will produce integers.
     */
    public int evalRPN(String[] tokens) {
        LinkedList<Integer> stack = new LinkedList<Integer>();
        for (String token : tokens) {
            // Assume all tokens are valid: only numbers and the four operators are allowed (+, -, *, /)

            // token is an operator
            if ("+".equals(token)) {
                stack.push(stack.pop() + stack.pop());
            } else if ("-".equals(token)) {
                int subtrahend = stack.pop();
                stack.push(stack.pop() - subtrahend);
            } else if ("*".equals(token)) {
                stack.push(stack.pop() * stack.pop());
            } else if ("/".equals(token)) {
                int divisor = stack.pop();
                stack.push(stack.pop() / divisor);
            } else { // token is an operand
                stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }

    /**
     * <a href="http://oj.leetcode.com/problems/binary-tree-preorder-traversal/">Binary Tree Preorder Traversal</a>
     * Given a binary tree, return the preorder traversal of its nodes' values.
     * <p/>
     * Idea: use Stack
     * Init: push root
     * Iteration: be careful with the order of pushing element to Stack: right children should be placed first (not left)
     * so that left children could be popped first (LIFO).
     */
    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        if (root == null) return new ArrayList<Integer>();
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        ArrayList<Integer> preorder = new ArrayList<Integer>();
        TreeNode current;
        stack.push(root);
        while (!stack.isEmpty()) {
            current = stack.pop();
            preorder.add(current.val);
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
        }

        return preorder;
    }


    /**
     * <a href="http://oj.leetcode.com/problems/binary-tree-postorder-traversal/">Binary Tree Postorder Traversal</a>
     * Given a binary tree, return the postorder traversal of its nodes' values.
     * Note: Recursive solution is trivial, could you do it iteratively?
     * <p/>
     * Idea:
     * For an iterative solution we'll need a stack or two. Postorder means recursive visiting of left child,
     * right child and then the node element itself. So, the algorithm may look like this:
     * 1. Init: push the tree root to Stack
     * 2. Peek (not pop!) TreeNode from stack (if not empty)
     * 3. If has a left child AND it was not visited push it to stack and skip next steps (loop continue)
     * 4. If has a right child AND it was not visited push it to stack and skip next steps (loop continue)
     * 5. Pop TreeNode from stack and print
     * 6. Add TreeNode to visited
     * <p/>
     * Note: obviously, this is not the best solution since it uses O(n) space. I believe there exists an O(1) space
     * solution.
     */
    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        if (root == null) return new ArrayList<Integer>();
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        Set<TreeNode> visited = new HashSet<TreeNode>();
        ArrayList<Integer> postorder = new ArrayList<Integer>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            if (node.left != null && !visited.contains(node.left)) {
                stack.push(node.left);
                continue;
            }
            if (node.right != null && !visited.contains(node.right)) {
                stack.push(node.right);
                continue;
            }
            TreeNode nextNode = stack.poll();
            postorder.add(nextNode.val);
            visited.add(nextNode);
        }

        return postorder;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/longest-substring-without-repeating-characters/">Longest Substring Without Repeating Characters</a>
     * Given a string, find the length of the longest substring without repeating characters.
     * For example, the longest substring without repeating letters for "abcabcbb" is "abc", which the length is 3.
     * For "bbbbb" the longest substring is "b", with the length of 1.
     * <p/>
     * Solution: remember character positions in a char->int HashMap.
     * If the next char IS in the map:
     * - remove all map entries for chars from startPosition to position of the encountered character
     * - set startPosition = currentPosition + 1
     * - update current character position in the map
     * Else:
     * - increment currentPosition
     * - check if currentPosition - startPosition > maxSubstring. If yes, update maxSubstring and remember positions.
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        int startPosition = 0;
        int maxSubstring = 0;
        Map<Character, Integer> positionMap = new HashMap<Character, Integer>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (positionMap.containsKey(ch)) {
                for (int j = startPosition; j < positionMap.get(ch); j++) {
                    positionMap.remove(s.charAt(j));
                }
                startPosition = positionMap.get(ch) + 1;
            } else {
                if (i - startPosition > maxSubstring) {
                    maxSubstring = i - startPosition;
                }
            }
            positionMap.put(ch, i);

        }

        return maxSubstring + 1;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/median-of-two-sorted-arrays/">Median of Two Sorted Arrays</a>
     * There are two sorted arrays A and B of size m and n respectively. Find the median of the two sorted arrays.
     * The overall run time complexity should be O(log (m+n)).
     * <p/>
     * Observations: if we could merge two arrays (O(m+n)) we would just pick the (m+n)/2 element (if m+n is odd).
     * TODO: the solution is wrong and does not work.
     */
    public double findMedianSortedArrays(int A[], int B[]) {
        int m = A.length;
        int n = B.length;
        if (n == 0) return (m % 2 == 0) ? (double) (A[(m - 1) / 2] + A[m / 2]) / 2 : A[m / 2];
        if (m == 0) return (n % 2 == 0) ? (double) (B[(n - 1) / 2] + B[n / 2]) / 2 : B[n / 2];
        if (m == 2 && n == 2) return (double) (Math.max(A[0], B[0]) + Math.min(A[1], B[1])) / 2;
        int jumpA = m / 2;
        int jumpB = n / 2;
        int kA = 0;
        int kB = 0;
        boolean lastA = false;
        while (jumpA > 0 && jumpB > 0) {
            if (kA + kB <= (m + n) / 2) {
                if (A[kA] < B[kB]) {
                    kA += jumpA;
                    jumpA /= 2;
                    lastA = true;
                } else {
                    kB += jumpB;
                    jumpB /= 2;
                    lastA = false;
                }
            } else {
                if (A[kA] < B[kB] && kB - jumpB >= 0) {
                    kB -= jumpB;
                    jumpB /= 2;
                    lastA = false;
                } else if (A[kA] <= B[kB] && kA - jumpA >= 0) {
                    kA -= jumpA;
                    jumpA /= 2;
                    lastA = true;
                } else {
                    break;
                }
            }
        }
        if ((m + n) % 2 == 0 && m == n) return (double) (A[kA] + B[kB]) / 2;
        if (lastA) return A[kA];
        return B[kB];
    }

    /**
     * <a href="oj.leetcode.com/problems/add-two-numbers/">Add Two Numbers</a>
     * You are given two linked lists representing two non-negative numbers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
     * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
     * Output: 7 -> 0 -> 8
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode result = new ListNode(0);
        ListNode currentNode = result;
        int carryover = 0;
        boolean isFirst = true;
        ListNode l1c = l1;
        ListNode l2c = l2;
        while (true) {
            if (l1c == null && l2c == null) {
                break;
            }
            int sum = 0;
            if (l1c != null) {
                sum = l1c.val;
                l1c = l1c.next;
            }
            if (l2c != null) {
                sum += l2c.val;
                l2c = l2c.next;
            }

            sum += carryover;
            if (sum >= 10) {
                carryover = 1;
                sum = sum - 10;
            } else {
                carryover = 0;
            }

            if (isFirst) {
                currentNode = new ListNode(sum);
                result = currentNode;
                isFirst = false;
            } else {
                currentNode.next = new ListNode(sum);
                currentNode = currentNode.next;
            }
        }
        if (carryover > 0) {
            currentNode.next = new ListNode(carryover);
        }
        return result;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/longest-palindromic-substring/">Longest Palindromic Substring</a> Given
     * a string S, find the longest palindromic substring in S. You may assume that the maximum length of S is 1000, and
     * there exists one unique longest palindromic substring.
     * <p/>
     * Solution 1: via dynamic programming.
     * A[i,j] = (A[i+1, j-1] AND Si == Sj)
     * Init: A[i,i] = true, A[i,i+1] = (Si == Si+1)
     */
    public String longestPalindrome(String s) {
//        return longestPalindromeDP(s);
        return longestPalindromeExpansion(s);
    }

    private String longestPalindromeDP(String s) {
        int n = s.length();
        if (n == 0 || n == 1) return s;
        boolean A[][] = new boolean[n][n];

        // init
        for (int i = 0; i < n; i++) {
            A[i][i] = true;
        }

        int longestLen = 0;
        String longest = "" + s.charAt(n - 1);

        for (int i = 0; i < n - 1; i++) {
            A[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
            if (A[i][i + 1]) longest = s.substring(i, i + 2);
        }

        if (n < 3) return longest;

        // iteration
        for (int len = 2; len < n; len++) {
            for (int i = 0; i < n - len; i++) {
                A[i][i + len] = A[i + 1][i + len - 1] && s.charAt(i) == s.charAt(i + len);
                if (A[i][i + len] && len > longestLen) {
                    longestLen = len;
                    longest = s.substring(i, i + len + 1);
                }
            }
        }
        return longest;
    }

    private String longestPalindromeExpansion(String s) {
        int n = s.length();
        if (n == 0 || n == 1) return s;
        int from = 0;
        int to = 0;
        int maxLength = -1;

        for (int i = 0; i < n; i++) {
            // expand around a char (aBa)
            for (int expansion = 1; expansion < n; expansion++) {
                if (i - expansion < 0 || i + expansion >= n) break;
                if (s.charAt(i - expansion) != s.charAt(i + expansion)) break;
                if ((expansion * 2 + 1) > maxLength) {
                    maxLength = expansion * 2 + 1;
                    from = i - expansion;
                    to = i + expansion;
                }
            }
            // expand around two chars (aBBa)
            for (int expansion = 0; expansion < n - 1; expansion++) {
                if (i - expansion < 0 || i + expansion + 1 >= n) break;
                if (s.charAt(i - expansion) != s.charAt(i + expansion + 1)) break;
                if ((expansion + 1) * 2 >= maxLength) {
                    maxLength = (expansion + 1) * 2;
                    from = i - expansion;
                    to = i + expansion + 1;
                }
            }
        }

        return s.substring(from, to + 1);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/zigzag-conversion/">ZigZag Conversion</a>
     * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this:
     * (you may want to display this pattern in a fixed font for better legibility):
     * P   A   H   N
     * A P L S I I G
     * Y   I   R
     * And then read line by line: "PAHNAPLSIIGYIR"
     * <p/>
     * Write the code that will take a string and make this conversion given a number of rows.
     * <p/>
     * 0      10      20       30       40      50
     * 1    9 11    19 21    29 31    39 41    49
     * 2   8  12   18  22   28  32   38  42   48
     * 3  7   13  17   23  27   33  37   43  47
     * 4 6    14 16    24 26    34 36    44 46
     * 5      15       25       35       45
     * <p/>
     * 0   4   8
     * 1 3 5 7 9
     * 2   6   10
     * <p/>
     * Solution: just look at the examples and implement what you see.
     */
    public String convert(String s, int nRows) {
        int n = nRows;
        if (n <= 1) return s;
        int l = s.length();
        if (l <= n) return s;
        StringBuilder out = new StringBuilder();

        for (int row = 0; row < n; row++) {
            for (int i = 0; i <= l / ((n - 1) * 2) + 1; i++) {
                int at = i * (n - 1) * 2;
                if (row == 0 && at < l) {
                    out.append(s.charAt(at));
                } else if (row == n - 1 && at + n - 1 < l) {
                    out.append(s.charAt(at + n - 1));
                } else if (row > 0 && row < n - 1) {
                    if (at - row >= 0 && at - row < l) {
                        out.append(s.charAt(at - row));
                    }
                    if (at + row < l && at + row >= 0) {
                        out.append(s.charAt(at + row));
                    }
                }
            }
        }

        return out.toString();
    }
}

/**
 * Definition for binary tree
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }

    TreeNode(int x, TreeNode left, TreeNode right) {
        this(x);
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "" + val;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}