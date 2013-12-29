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

    /**
     * <a href="http://oj.leetcode.com/problems/reverse-integer/">Reverse Integer</a>
     * Reverse digits of an integer.
     * <p/>
     * Example1: x = 123, return 321
     * Example2: x = -123, return -321
     * <p/>
     * Solution: keep dividing the number by 10 and append num%10 to the result left side as result = result * 10 + right
     */
    public int reverse(int x) {
        int num = x < 0 ? -x : x;
        int reversed = 0;

        while (num > 0) {
            int right = num % 10;
            num /= 10;
            reversed = reversed * 10 + right;
        }

        return x < 0 ? -reversed : reversed;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/string-to-integer-atoi/">String to Integer (atoi)</a>
     * Implement atoi to convert a string to an integer.
     * <p/>
     * Hint: Carefully consider all possible input cases. If you want a challenge, please do not see below and ask yourself what are the possible input cases.
     * <p/>
     * Notes: It is intended for this problem to be specified vaguely (ie, no given input specs). You are responsible to gather all the input requirements up front.
     * <p/>
     * My initial requirements:
     * 1. Whitespaces in front are allowed: "    -42" -> -42
     * 2. Trailing non-digits are allowed: "44www" -> 44
     * 3. Leading non-digits (except whitespaces) are NOT allowed: "ww34" -> 0
     * 4. Whitespaces in the middle are NOT allowed: " 4 3" -> 0
     * 5. Fractional digits are ignored: "45.943" -> 45
     * 6. Only one negative sign is allowed.
     * 7. If there is an overflow return Integer.MAX_VALUE for positive or Integer.MIN_VALUE for negative numbers
     */
    public int atoi(String str) {
        boolean isNegative = false;
        boolean isPositive = false;
        boolean isBeginning = true;
        int num = 0;

        for (char ch : str.toCharArray()) {

            // R1. Ignore leading whitespaces
            // R4. Whitespaces in the middle are NOT allowed
            if (ch == ' ' && isBeginning) continue;

            // R6. Only one negative sign is allowed
            if (isNegative && ch == '-') {
                break;
            }
            if (isPositive && ch == '+') {
                break;
            }

            if (ch == '-') {
                isBeginning = false;
                isNegative = true;
                continue;
            }

            if (ch == '+') {
                isBeginning = false;
                isPositive = true;
                continue;
            }

            if (ch >= '0' && ch <= '9') {
                isBeginning = false;
                int newNum = num * 10 + (ch - '0');

                // R7. Preventing overflow
                if (!isNegative && (newNum) / 10 != num) {
                    return Integer.MAX_VALUE;
                }
                if (isNegative && (newNum) / 10 != num) {
                    return Integer.MIN_VALUE;
                }
                num = newNum;
                continue;
            }

            // R2. Ignore trailing non-digits
            // R5. Fractional digits are ignored
            // R3. Leadning non-digits are NOT allowed
            if (ch < '0' || ch > '9') {
                break;
            }
        }

        return isNegative ? -num : num;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/palindrome-number/">http://oj.leetcode.com/problems/palindrome-number/</a>
     * Determine whether an integer is a palindrome. Do this without extra space.
     */
    public boolean isPalindrome(int x) {
        if (x < 0) return false;
        if (x < 10) return true;

        int copy = x;
        int len = 0;
        while (copy > 0) {
            len++;
            copy /= 10;
        }

        while (len > 1) {
            int pow = (int) (Math.pow(10, len - 1));
            if (x % 10 != (x / pow)) return false;
            x = x - ((x / pow) * pow);
            x = x / 10;
            len -= 2;
        }

        return true;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/regular-expression-matching/">Regular Expression Matching</a>
     * Implement regular expression matching with support for '.' and '*'.
     * <p/>
     * '.' Matches any single character.
     * '*' Matches zero or more of the preceding element.
     * <p/>
     * The matching should cover the entire input string (not partial).
     * <p/>
     * The function prototype should be:
     * bool isMatch(const char *s, const char *p)
     * <p/>
     * Some examples:
     * isMatch("aa","a") → false
     * isMatch("aa","aa") → true
     * isMatch("aaa","aa") → false
     * isMatch("aa", "a*") → true
     * isMatch("aa", ".*") → true
     * isMatch("ab", ".*") → true
     * isMatch("aab", "c*a*b") → true
     */
    public boolean isMatch(String s, String p) {
        log("----- Start matching [%s] by [%s] -----", s, p);
        // compress pattern: a*a*a* -> a*
        StringBuilder compressedPattern = new StringBuilder();
        char prev = '!';
        int i = 0;
        while (i < p.length()) {
            if (i + 1 < p.length() && p.charAt(i + 1) == '*') {
                if (p.charAt(i) == prev) {
                    i += 2;
                } else {
                    prev = p.charAt(i);
                    compressedPattern.append(prev);
                    i++;
                }
            } else {
                compressedPattern.append(p.charAt(i));
                if (p.charAt(i) != '*') prev = '!';
                i++;
            }
        }
        log("Compressed pattern: " + compressedPattern.toString());
        return isMatch(s, compressedPattern.toString(), 0, 0, 0);
    }

    private boolean isMatch(String s, String p, int sfrom, int pfrom, int level) {
        log("L[%d]: matching [%s] by [%s]", level, s.substring(sfrom), p.substring(pfrom));
        // base cases
        if (sfrom >= s.length()) {
            if (pfrom >= p.length()) return true; // [] vs []

            if (pfrom + 1 < p.length() && p.charAt(pfrom + 1) == '*')
                return isMatch(s, p, sfrom, pfrom + 2, level + 1); // [] vs [a*...] -> [] vs [...]

            if (pfrom == p.length() - 2 && p.charAt(p.length() - 1) == '*')
                return true; // match [] against [a*]

            return false; // [] vs [a], [] vs [aa*] (all other cases)
        }


        if (sfrom < s.length() && pfrom >= p.length()) {
            return false; // all pattern chars are exhausted but the string is not matched ([abc] vs [])
        }

        boolean result = false;

        if (s.charAt(sfrom) == p.charAt(pfrom) || p.charAt(pfrom) == '.') {
            // aa:a* or aa:.*
            log("L[%d]: First symbol matches (equals or .)", level);
            if (pfrom + 1 < p.length() && p.charAt(pfrom + 1) == '*') {
                log("L[%d]: Next pattern symbol is *, so try matching [%s] against [%s] or [%s], or [%s] against [%s]", level, s.substring(sfrom + 1), p.substring(pfrom), p.substring(pfrom + 2), s.substring(sfrom), p.substring(pfrom + 2));
                result = isMatch(s, p, sfrom + 1, pfrom, level + 1) // try to match next input char with the same regex (a* or .*)
                        || isMatch(s, p, sfrom, pfrom + 2, level + 1) // or skip this regex part (a* or .*) and try to match same string
                        || isMatch(s, p, sfrom + 1, pfrom + 2, level + 1); // or go further, skip this regex part (a* or .*)
                log("L[%d]: Result of matching [%s] against [%s] or [%s], or [%s] against [%s] is [%s]", level, s.substring(sfrom + 1), p.substring(pfrom), p.substring(pfrom + 2), s.substring(sfrom), p.substring(pfrom + 2), result);

            } else {
                log("L[%d]: Trying regular char-char or char-. match [%s] [%s]", level, s.substring(sfrom + 1), p.substring(pfrom + 1));
                result = isMatch(s, p, sfrom + 1, pfrom + 1, level + 1); // regular char-char or char-. match
                log("L[%d]: Result of regular char-char or char-. match [%s] [%s] is [%s]", level, s.substring(sfrom + 1), p.substring(pfrom + 1), result);

            }
        } else if (s.charAt(sfrom) != p.charAt(pfrom) && pfrom + 1 < p.length() && p.charAt(pfrom + 1) == '*') {
            // aa:b*
            log("L[%d]: skipping non-matching star piece: [%s] against [%s]", level, s.substring(sfrom), p.substring(pfrom));
            result = isMatch(s, p, sfrom, pfrom + 2, level + 1); // skip this b* regex
            log("L[%d]: Result of skipping non-matching star piece: [%s] against [%s] is [%s]", level, s.substring(sfrom), p.substring(pfrom), result);

        }
        log("L[%d]: Returning result for [%s] against [%s] as [%s]", level, s.substring(sfrom), p.substring(pfrom), result);
        return result;
    }

    private void log(String msg, Object... args) {
        // System.out.println(String.format(msg, args));
    }

    /**
     * <a href="http://oj.leetcode.com/problems/container-with-most-water/">Container With Most Water</a>
     * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). n vertical
     * lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0).
     * Find two lines, which together with x-axis forms a container, such that the container contains the most water.
     * <p/>
     * Note: You may not slant the container.
     * <p/>
     * Solution: let's do three passes:
     * 1. From left to right raising the watermark as line height becomes higher
     * <p/>
     * 2. From right to left lowering the watermark to the current line height
     * <p/>
     * 3. Calculate maxArea during Pass 3
     */
    public int maxArea(int[] height) {
        if (height.length < 2) return 0;

        int[] watermark = new int[height.length];

        // Pass 1
        int currentWatermark = height[0];
        for (int i = 0; i < height.length; i++) {
            if (height[i] > currentWatermark) {
                currentWatermark = height[i];
            }
            watermark[i] = currentWatermark;
        }

        // Pass 2
        currentWatermark = height[height.length - 1];
        for (int i = height.length - 1; i > 0; i--) {

            if (height[i] > currentWatermark) {
                currentWatermark = height[i];
            }
            watermark[i - 1] = Math.min(currentWatermark, watermark[i - 1]);
        }

        // Pass 3
        int i = 0;
        int j = watermark.length - 2;
        int maxVolume = (j - i + 1) * Math.min(watermark[0], watermark[watermark.length - 2]);

        int prevWatermark;
        while (i < j) {
            prevWatermark = Math.min(watermark[i], watermark[j]);
            while (watermark[i] <= prevWatermark && i < j) i++;
            while (watermark[j] <= prevWatermark && i < j) j--;

            int currentMax = (j - i + 1) * Math.min(watermark[i], watermark[j]);
            if (currentMax > maxVolume) {
                maxVolume = currentMax;
            }
        }

        return maxVolume;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/integer-to-roman/">Integer to Roman</a>
     * Given an integer, convert it to a roman numeral.
     * <p/>
     * Input is guaranteed to be within the range from 1 to 3999.
     */
    public String intToRoman(int num) {
        int[] nums = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romans = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            int i = 0;
            // find the largest
            while (nums[i] > num) {
                i++;
            }
            sb.append(romans[i]);
            num -= nums[i];
        }
        return sb.toString();
    }

    /**
     * <a href="http://oj.leetcode.com/problems/roman-to-integer/">Roman to Integer</a>
     * Given a roman numeral, convert it to an integer.
     * <p/>
     * Input is guaranteed to be within the range from 1 to 3999.
     */
    public int romanToInt(String s) {
        // the order matters here: we want to match CM before M, CD before D and so on
        String[] romans = new String[]{"CM", "M", "CD", "D", "XC", "C", "XL", "L", "IX", "X", "IV", "V", "I"};
        int[] nums = new int[]{900, 1000, 400, 500, 90, 100, 40, 50, 9, 10, 4, 5, 1};
        int num = 0;
        int offset = 0;
        while (offset < s.length()) {
            for (int i = 0; i < romans.length; i++) {
                if (s.startsWith(romans[i], offset)) {
                    num += nums[i];
                    offset += romans[i].length();
                    break;
                }
            }
        }

        return num;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/longest-common-prefix/">Longest Common Prefix</a>
     * Write a function to find the longest common prefix string amongst an array of strings.
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        int rightMost = strs[0].length() - 1;
        int pos = 0;
        for (; pos <= rightMost; pos++) {
            for (String str : strs) {
                if (str.length() < pos + 1 || str.charAt(pos) != strs[0].charAt(pos))
                    return strs[0].substring(0, pos);
            }
        }
        return strs[0];
    }

    /**
     * <a href="http://oj.leetcode.com/problems/3sum/">3Sum</a>
     * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0?
     * Find all unique triplets in the array which gives the sum of zero.
     * <p/>
     * Note:
     * <p/>
     * Elements in a triplet (a,b,c) must be in non-descending order. (ie, a ≤ b ≤ c)
     * The solution set must not contain duplicate triplets.
     * <p/>
     * For example, given array S = {-1 0 1 2 -1 -4},
     * <p/>
     * A solution set is:
     * (-1, 0, 1)
     * (-1, -1, 2)
     * <p/>
     * Solution: the idea is to extend the k=a+b (two sum) problem on a sorted list: for every k find a and b by moving
     * left and right pointers toward each other until a+b+k=0.
     */
    public ArrayList<ArrayList<Integer>> threeSum(int[] num) {
        ArrayList<ArrayList<Integer>> sums = new ArrayList<ArrayList<Integer>>();
        Arrays.sort(num);
        if (num.length < 3) return sums;
        int n = num.length;
        for (int k = 2; k < n; k++) {
            // prevent duplicates
            if (k < n - 1 && num[k] == num[k + 1]) continue;
            int a = 0;
            int b = k - 1;
            while (a < b) {
                // prevent duplicates
                if (a > 0 && num[a] == num[a - 1]) {
                    a++;
                    continue;
                }
                // prevent duplicates
                if (b < k - 1 && num[b] == num[b + 1]) {
                    b--;
                    continue;
                }
                int sum = num[a] + num[k] + num[b];
                if (sum > 0) b--;
                else if (sum < 0) a++;
                else {
                    sums.add(new ArrayList<Integer>(Arrays.asList(num[a], num[b], num[k])));
                    a++;
                    b--;
                }
            }
        }

        return sums;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/3sum-closest/">3Sum Closest</a>
     * Given an array S of n integers, find three integers in S such that the sum is closest to a given number, target.
     * Return the sum of the three integers. You may assume that each input would have exactly one solution.
     * <p/>
     * For example, given array S = {-1 2 1 -4}, and target = 1.
     * <p/>
     * The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
     * <p/>
     * Idea: the problem seems similar to the 3sum problem. We need to keep only one 3set and the closest sum to target so far.
     */
    public int threeSumClosest(int[] num, int target) {
        if (num.length == 0) return 0;
        if (num.length == 1) return num[0];
        if (num.length == 2) return num[0] + num[1];
        if (num.length == 3) return num[0] + num[1] + num[2];

        Arrays.sort(num);
        int n = num.length;
        int bestDiff = Integer.MAX_VALUE;
        int bestSum = Integer.MAX_VALUE;
        for (int k = 2; k < n; k++) {
            // skip duplicates
            if (k < n - 1 && num[k] == num[k + 1]) continue;
            for (int a = 0, b = k - 1; a < b; ) {
                if (a > 0 && num[a] == num[a - 1]) {
                    a++;
                    continue;
                }
                if (b < k - 1 && num[b] == num[b + 1]) {
                    b--;
                    continue;
                }
                int sum = num[a] + num[k] + num[b];
                if (Math.abs(sum - target) < bestDiff) {
                    bestSum = sum;
                    bestDiff = Math.abs(sum - target);
                }

                if (sum < target) {
                    a++;
                } else {
                    b--;
                }
            }

        }

        return bestSum;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/4sum/">4Sum</a>
     * Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target?
     * Find all unique quadruplets in the array which gives the sum of target.
     * <p/>
     * Note:
     * <p/>
     * Elements in a quadruplet (a,b,c,d) must be in non-descending order. (ie, a ≤ b ≤ c ≤ d)
     * The solution set must not contain duplicate quadruplets.
     * <p/>
     * For example, given array S = {1 0 -1 0 -2 2}, and target = 0.
     * <p/>
     * A solution set is:
     * (-1,  0, 0, 1)
     * (-2, -1, 1, 2)
     * (-2,  0, 0, 2)
     */
    public ArrayList<ArrayList<Integer>> fourSum(int[] num, int target) {
        if (num.length < 4) return new ArrayList<ArrayList<Integer>>();
        List<ArrayList<Integer>> kSums = kSum(num, target, 4);
        return new ArrayList<ArrayList<Integer>>(kSums);
    }

    /**
     * <a href="http://cs.stackexchange.com/questions/2973/generalised-3sum-k-sum-problem">k-SUM problem</a>
     * <p/>
     * For even k: Compute a sorted list S of all sums of k/2 input elements.
     * Check whether S contains both some number x and its negation −x. The algorithm runs in O(nk/2logn) time.
     * <p/>
     * For odd k: Compute the sorted list S of all sums of (k−1)/2 input elements.
     * For each input element a, check whether S contains both x and a−x, for some number x.
     * (The second step is essentially the O(n2)-time algorithm for 3SUM.) The algorithm runs in O(n(k+1)/2) time.
     */
    public List<ArrayList<Integer>> kSum(int[] num, int target, int k) {
        if (num.length < k) return new ArrayList<ArrayList<Integer>>();
        // Step 1. Compute all sums of k/2 input elements
        // Probably this step brings most complexity and running time
        // Probably the algo can be redesigned to use n choose k rather than n choose k/2. It'll greatly simplify
        // the code avoiding combination steps
        List<List<Integer>> nChooseKIndices = choose(num.length, k / 2);
        Map<Integer, List<List<Integer>>> sumToCombinations = new HashMap<Integer, List<List<Integer>>>();
        for (List<Integer> indices : nChooseKIndices) {
            int sum = 0;
            for (int index : indices) {
                sum += num[index];
            }
            if (!sumToCombinations.containsKey(sum)) {
                sumToCombinations.put(sum, new ArrayList<List<Integer>>());
            }
            sumToCombinations.get(sum).add(indices);
        }

        Set<ArrayList<Integer>> kSums = new HashSet<ArrayList<Integer>>();

        // Step 2. Check whether S contains both some number x and its sibling target−x
        if (k % 2 == 0) {
            for (int sum : sumToCombinations.keySet()) {
                int antiSum = target - sum;
                if (sumToCombinations.containsKey(antiSum)) {
                    List<List<Integer>> finalCombos = new ArrayList<List<Integer>>();
                    for (int i = 0; i < sumToCombinations.get(sum).size(); i++) {
                        for (int j = sum == antiSum ? i + 1 : 0; j < sumToCombinations.get(antiSum).size(); j++) {
                            List<Integer> finalCombo = new ArrayList<Integer>();
                            finalCombo.addAll(sumToCombinations.get(sum).get(i));
                            finalCombo.addAll(sumToCombinations.get(antiSum).get(j));
                            if (new HashSet<Integer>(finalCombo).size() < finalCombo.size()) continue;
                            finalCombos.add(finalCombo);
                        }
                    }

                    for (List<Integer> indices : finalCombos) {
                        ArrayList<Integer> finalValues = new ArrayList<Integer>();
                        for (int index : indices) {
                            finalValues.add(num[index]);
                        }
                        Collections.sort(finalValues);
                        kSums.add(finalValues);
                    }
//                    break;
                }
            }

        } else {
            //  For each input element z, check whether S contains both x and target+z−x, for some number x.
            for (int z = 0; z < num.length; z++) {
                for (int sum : sumToCombinations.keySet()) {
                    int antiSum = target - sum - num[z];
                    if (sumToCombinations.containsKey(antiSum)) {
                        List<List<Integer>> finalCombos = new ArrayList<List<Integer>>();
                        for (int i = 0; i < sumToCombinations.get(sum).size(); i++) {
                            for (int j = (sum == antiSum) ? i + 1 : 0; j < sumToCombinations.get(antiSum).size(); j++) {
                                List<Integer> finalCombo = new ArrayList<Integer>();
                                finalCombo.addAll(sumToCombinations.get(sum).get(i));
                                finalCombo.addAll(sumToCombinations.get(antiSum).get(j));
                                if (finalCombo.contains(z)) continue;
                                if (new HashSet<Integer>(finalCombo).size() < finalCombo.size()) continue;
                                finalCombo.add(z);
                                finalCombos.add(finalCombo);
                            }
                        }

                        for (List<Integer> indices : finalCombos) {
                            ArrayList<Integer> finalValues = new ArrayList<Integer>();
                            for (int index : indices) {
                                finalValues.add(num[index]);
                            }
                            Collections.sort(finalValues);
                            kSums.add(finalValues);
                        }
//                        break;
                    }
                }
            }

        }

        return new ArrayList<ArrayList<Integer>>(kSums);
    }

    private static List<List<Integer>> choose(int n, int k) {
        List<List<Integer>> combos = new ArrayList<List<Integer>>();
        boolean[] visited = new boolean[n];
        choose(combos, n, visited, 0, k);
        return combos;
    }

    private static void choose(List<List<Integer>> combinations, int n, boolean[] visited, int from, int limit) {
        // base case: all indices are visited
        if (limit == 0) {
            List<Integer> combo = new ArrayList<Integer>();
            for (int i = 0; i < visited.length; i++) {
                if (visited[i]) combo.add(i);
            }
            combinations.add(combo);
            return;
        }

        for (int i = from; i < n; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            choose(combinations, n, visited, i, limit - 1);
            visited[i] = false;
        }
    }


    /**
     * <a href="http://oj.leetcode.com/problems/letter-combinations-of-a-phone-number/">Letter Combinations of a Phone Number</a>
     * Given a digit string, return all possible letter combinations that the number could represent.
     * <p/>
     * A mapping of digit to letters (just like on the telephone buttons) is given below.
     * <p/>
     * Input: Digit string "23"
     * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
     * <p/>
     * Solution: Think of letters as digits of a 3 or 4 based numerical system. "Increment" the number until overflow
     */
    public ArrayList<String> letterCombinations(String digits) {
        if (digits == null || digits.isEmpty()) return new ArrayList<String>(Arrays.asList(""));
        // map of digit to letters
        Map<Character, String> digitMap = new HashMap<Character, String>();
        digitMap.put('1', "1");
        digitMap.put('2', "abc");
        digitMap.put('3', "def");
        digitMap.put('4', "ghi");
        digitMap.put('5', "jkl");
        digitMap.put('6', "mno");
        digitMap.put('7', "pqrs");
        digitMap.put('8', "tuv");
        digitMap.put('9', "wxyz");

        int[] counters = new int[digits.length()];
        boolean overflow = false;
        ArrayList<String> combinations = new ArrayList<String>();
        // keep incrementing counters while there is no overflow
        int n = digits.length() - 1;
        while (!overflow) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < counters.length; i++) {
                String chars = digitMap.get(digits.charAt(i));
                sb.append(chars.charAt(counters[i]));
            }
            combinations.add(sb.toString());
            int position = n;
            counters[position]++;
            while (counters[position] >= digitMap.get(digits.charAt(position)).length()) {

                counters[position] = 0;
                position--;
                if (position < 0) {
                    overflow = true;
                    break;
                }
                counters[position]++;
            }

        }
        return combinations;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/remove-nth-node-from-end-of-list/">Remove Nth Node From End of List</a>
     * Given a linked list, remove the nth node from the end of list and return its head.
     * <p/>
     * For example,
     * <p/>
     * Given linked list: 1->2->3->4->5, and n = 2.
     * <p/>
     * After removing the second node from the end, the linked list becomes 1->2->3->5.
     * <p/>
     * Note:
     * Given n will always be valid.
     * Try to do this in one pass.
     * <p/>
     * Solution: use two pointers: one starts immediately, another after n moves. As soon as the first pointer reaches
     * the end of the list (node.next == null) the second will point to the element right before the element to delete.
     * Now just rewire a couple of pointers.
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fastPointer = head;
        ListNode slowPointer = head;
        for (int i = 0; i < n; i++)
            fastPointer = fastPointer.next;

        // need to remove the first element, head.
        if (fastPointer == null) {
            return head.next;
        }

        while (fastPointer.next != null) {
            fastPointer = fastPointer.next;
            slowPointer = slowPointer.next;
        }

        ListNode toDelete = slowPointer.next;
        slowPointer.next = toDelete.next;
        return head;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/valid-parentheses/">Valid Parentheses</a>
     * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
     * <p/>
     * The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
     */
    public boolean isValid(String s) {
        Map<Character, Character> par = new HashMap<Character, Character>();
        par.put('(', ')');
        par.put('{', '}');
        par.put('[', ']');

        LinkedList<Character> stack = new LinkedList<Character>();

        for (char ch : s.toCharArray()) {
            // opening paren: add the corresponding closing paren to the stack
            if (par.containsKey(ch)) {
                stack.push(par.get(ch));
            }
            // should be a closing paren: make sure the top stack element is exactly this paren.
            else if (!stack.isEmpty() && stack.peek() == ch) {
                stack.pop();
            }
            // invalid paren or character
            else return false;
        }

        // there missing closing parens if the stack is not empty
        return stack.isEmpty();
    }

    /**
     * <a href="http://oj.leetcode.com/problems/generate-parentheses/">Generate Parentheses</a>
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
     * <p/>
     * For example, given n = 3, a solution set is:
     * <p/>
     * "((()))", "(()())", "(())()", "()(())", "()()()"
     */
    public ArrayList<String> generateParenthesis(int n) {
        char[] parens = new char[n * 2];
        return new ArrayList<String>(generateParenthesis(parens, n, 0, 0, 0));
    }

    private List<String> generateParenthesis(char[] parens, int n, int opened, int closed, int len) {
        if (len == n * 2) {
            return Arrays.asList(new String(parens));
        }

        List<String> allParens = new ArrayList<String>();
        if (opened < n) {
            parens[len] = '(';
            allParens.addAll(generateParenthesis(parens, n, opened + 1, closed, len + 1));
        }
        if (closed < opened) {
            parens[len] = ')';
            allParens.addAll(generateParenthesis(parens, n, opened, closed + 1, len + 1));
        }

        return allParens;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/max-points-on-a-line/">Max Points on a Line</a>
     * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
     * <p/>
     * Solution: calculate all slopes for all points, put Points with the same slope to a hashmap.
     * Note: process equal points separately
     */
    public int maxPoints(Point[] points) {
        if (points.length < 3) return points.length;

        long precision = 1000000;
        Map<Long, Integer> slopeToNumOfPointsMap;
        int maxPoints;
        int totalMaxPoints = 2;
        long slope;
        for (Point point1 : points) {
            slopeToNumOfPointsMap = new HashMap<Long, Integer>();
            int equal = 0;
            maxPoints = 1;
            for (Point point2 : points) {
                if (point1 == point2) continue;
                if (point1.x == point2.x && point1.y == point2.y) {
                    equal++;
                    continue;
                } else if (point1.x == point2.x)
                    slope = Long.MAX_VALUE;
                else slope = (long) (precision * ((double) (point1.y - point2.y) / (point1.x - point2.x)));
                if (!slopeToNumOfPointsMap.containsKey(slope)) {
                    slopeToNumOfPointsMap.put(slope, 1);
                }
                int numOfPoints = slopeToNumOfPointsMap.get(slope) + 1;
                slopeToNumOfPointsMap.put(slope, numOfPoints);
                if (numOfPoints > maxPoints) maxPoints = numOfPoints;
            }
            if (maxPoints + equal > totalMaxPoints) {
                totalMaxPoints = maxPoints + equal;
            }
        }

        return totalMaxPoints;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/best-time-to-buy-and-sell-stock/">Best Time to Buy and Sell Stock</a>
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * <p/>
     * If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock),
     * design an algorithm to find the maximum profit.
     */
    public int maxProfitI(int[] prices) {
        int buy = 0;
        int maxDiff = 0;

        for (int i = 0; i < prices.length; i++) {
            if (prices[i] - prices[buy] > maxDiff) {
                maxDiff = prices[i] - prices[buy];
            }
            if (prices[i] < prices[buy]) {
                buy = i;
            }

        }
        return maxDiff;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/">Best Time to Buy and Sell Stock II</a>
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * <p/>
     * Design an algorithm to find the maximum profit. You may complete as many transactions as you like
     * (ie, buy one and sell one share of the stock multiple times).
     * However, you may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     * <p/>
     * Solution: just track when the price curve changes direction: sell on previous day, buy on this day
     */
    public int maxProfitII(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i - 1] > prices[i]) continue;
            profit += prices[i] - prices[i - 1];
        }
        return profit;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/">Best Time to Buy and Sell Stock III</a>
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * <p/>
     * Design an algorithm to find the maximum profit. You may complete at most two transactions.
     * <p/>
     * Note:
     * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     * <p/>
     * Solution: divide prices into two regions around 0, 1, ..., n-1 and calculate the sum of maxProfits of each
     * region (keep the best profit).
     * Hint: we need to reuse n-1 solutions to calculate n solutions to avoid Time Limit Exceeded errors, so it is
     * infeasible to reuse maxProfitI(). For the right part we should go from the rightmost price to left. In this case
     * we need to maintain the highest sell prices rather than lowest buy price.
     */
    public int maxProfitIII(int[] prices) {
        int maxProfit = 0;
        int n = prices.length;
        int prevLeftBuy = 0;
        int[] leftMax = new int[n];
        int prevRightSell = n - 1;
        int[] rightMax = new int[n];

        for (int i = 1; i < n; i++) {
            if (prices[i] - prices[prevLeftBuy] > leftMax[i - 1]) {
                leftMax[i] = prices[i] - prices[prevLeftBuy];
            } else leftMax[i] = leftMax[i - 1];
            if (prices[i] < prices[prevLeftBuy]) {
                prevLeftBuy = i;
            }

            // right region: [n-2, n-1], [n-3,n-1], ... [0, n-1]
            int r = n - i - 1;
            if (prices[prevRightSell] - prices[r] > rightMax[r + 1]) {
                rightMax[r] = prices[prevRightSell] - prices[r];
            } else rightMax[r] = rightMax[r + 1];
            if (prices[r] > prices[prevRightSell]) {
                prevRightSell = r;
            }
        }

        for (int i = 0; i < leftMax.length; i++) {
            if (leftMax[i] + rightMax[i] > maxProfit) {
                maxProfit = leftMax[i] + rightMax[i];
            }
        }

        return maxProfit;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/merge-k-sorted-lists/">Merge k Sorted Lists</a>
     * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
     * <p/>
     * Solution: use a TreeMap for storing list heads sorted by their value. Get first map entry, advance the corresponding
     * list position (if available) and put ths value-list pair to the map.
     */
    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        if (lists == null || lists.isEmpty()) return null;

        PriorityQueue<ListNode> heap = new PriorityQueue<ListNode>(lists.size(), new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return Integer.compare(o1.val, o2.val);
            }
        });

        // initial population
        for (ListNode list : lists) {
            if (list == null) continue;
            heap.add(list);
        }
        ListNode sortedHead = null;
        ListNode sortedNext = null;

        while (!heap.isEmpty()) {
            ListNode smallestHead = heap.poll();
            // init sorted list head if necessary
            if (sortedHead == null) {
                sortedHead = new ListNode(smallestHead.val);
                sortedNext = sortedHead;
            } else {
                // create new sorted list node
                ListNode newNode = new ListNode(smallestHead.val);
                sortedNext.next = newNode;
                sortedNext = newNode;
            }

            // push next pointer if possible
            if (smallestHead.next != null) {
                heap.offer(smallestHead.next);
            }
        }
        return sortedHead;
    }

    /************************** Data structures ****************************/

    /**
     * Definition for binary tree
     */
    public static class TreeNode {
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

    public static class ListNode {
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

        @Override
        public String toString() {
            return "" + val;
        }
    }

    /**
     * Definition for a point.
     */
    public static class Point {
        int x;
        int y;

        Point() {
            x = 0;
            y = 0;
        }

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}

