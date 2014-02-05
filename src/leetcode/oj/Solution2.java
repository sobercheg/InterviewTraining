package leetcode.oj;

import java.util.*;

import static leetcode.oj.Solution.ListNode;
import static leetcode.oj.Solution.TreeNode;

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

    /**
     * <a href="http://oj.leetcode.com/problems/valid-number/">Valid Number</a>
     * Validate if a given string is numeric.
     * <p/>
     * Some examples:
     * "0" => true
     * " 0.1 " => true
     * "abc" => false
     * "1 a" => false
     * "2e10" => true
     * <p/>
     * Note: It is intended for the problem statement to be ambiguous. You should gather all requirements up front before implementing one.
     * <p/>
     * The solution is based on automata-based programming http://en.wikipedia.org/wiki/Automata-based_programming,
     * http://discuss.leetcode.com/questions/241/valid-number
     */
    static enum State {
        START_SPACE(0),
        START_DIGIT(1),
        SIGN_READ(2),
        DOT_READ(3),
        DIGIT_AFTER_DOT(4),
        EXPONENT(5),
        EXPONENT_SIGN(6),
        EXPONENT_DIGIT(7),
        END_SPACE(8);

        State(int value) {
            this.value = value;
        }

        int value;
    }

    static enum Input {
        INVALID(0),
        SPACE(1),
        SIGN(2),
        EXPONENT(3),
        DIGIT(4),
        DOT(5);

        Input(int value) {
            this.value = value;
        }

        int value;

        public static Input of(char ch) {
            if (ch == ' ') return SPACE;
            if (ch == '+' || ch == '-') return SIGN;
            if (ch == 'e' || ch == 'E') return EXPONENT;
            if (ch >= '0' && ch <= '9') return DIGIT;
            if (ch == '.') return DOT;
            return INVALID;

        }
    }

    static class Transition {
        State state;
        Input input;

        Transition(State state, Input input) {
            this.state = state;
            this.input = input;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Transition that = (Transition) o;

            return input == that.input && state == that.state;

        }

        @Override
        public int hashCode() {
            int result = state != null ? state.hashCode() : 0;
            result = 31 * result + (input != null ? input.hashCode() : 0);
            return result;
        }
    }

    public boolean isNumber(String s) {
        Map<Transition, State> transitions = new HashMap<Transition, State>();
        transitions.put(new Transition(State.START_SPACE, Input.SPACE), State.START_SPACE);
        transitions.put(new Transition(State.START_SPACE, Input.DIGIT), State.START_DIGIT);
        transitions.put(new Transition(State.START_SPACE, Input.SIGN), State.SIGN_READ);
        transitions.put(new Transition(State.START_SPACE, Input.DOT), State.DOT_READ);

        transitions.put(new Transition(State.START_DIGIT, Input.DIGIT), State.START_DIGIT);
        transitions.put(new Transition(State.START_DIGIT, Input.DOT), State.DIGIT_AFTER_DOT);
        transitions.put(new Transition(State.START_DIGIT, Input.EXPONENT), State.EXPONENT);
        transitions.put(new Transition(State.START_DIGIT, Input.SPACE), State.END_SPACE);

        transitions.put(new Transition(State.DOT_READ, Input.DIGIT), State.DIGIT_AFTER_DOT);

        transitions.put(new Transition(State.SIGN_READ, Input.DIGIT), State.START_DIGIT);
        transitions.put(new Transition(State.SIGN_READ, Input.DOT), State.DOT_READ);

        transitions.put(new Transition(State.DIGIT_AFTER_DOT, Input.DIGIT), State.DIGIT_AFTER_DOT);
        transitions.put(new Transition(State.DIGIT_AFTER_DOT, Input.SPACE), State.END_SPACE);
        transitions.put(new Transition(State.DIGIT_AFTER_DOT, Input.EXPONENT), State.EXPONENT);

        transitions.put(new Transition(State.EXPONENT, Input.DIGIT), State.EXPONENT_DIGIT);
        transitions.put(new Transition(State.EXPONENT, Input.SIGN), State.EXPONENT_SIGN);

        transitions.put(new Transition(State.EXPONENT_SIGN, Input.DIGIT), State.EXPONENT_DIGIT);

        transitions.put(new Transition(State.EXPONENT_DIGIT, Input.DIGIT), State.EXPONENT_DIGIT);
        transitions.put(new Transition(State.EXPONENT_DIGIT, Input.SPACE), State.END_SPACE);

        transitions.put(new Transition(State.END_SPACE, Input.SPACE), State.END_SPACE);

        State currentState = State.START_SPACE;
        for (int i = 0; i < s.length(); i++) {
            Input input = Input.of(s.charAt(i));
            currentState = transitions.get(new Transition(currentState, input));
            if (currentState == null) return false;
        }

        return currentState == State.START_DIGIT
                || currentState == State.DIGIT_AFTER_DOT
                || currentState == State.END_SPACE
                || currentState == State.EXPONENT_DIGIT;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/plus-one/">Plus One</a>
     * Given a number represented as an array of digits, plus one to the number.
     */
    public int[] plusOne(int[] digits) {
        int carryover = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int sum = digits[i] + carryover;
            if (sum > 9) {
                digits[i] = sum % 10;
                carryover = 1;
            } else {
                digits[i] += carryover;
                carryover = 0;
            }

        }
        if (carryover == 0) return digits;
        int[] newArray = new int[digits.length + 1];
        System.arraycopy(digits, 0, newArray, 1, digits.length);
        newArray[0] = carryover;
        return newArray;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/text-justification/">Text Justification</a>
     * Given an array of words and a length L, format the text such that each line has exactly L characters and
     * is fully (left and right) justified.
     * <p/>
     * You should pack your words in a greedy approach; that is, pack as many words as you can in each line.
     * Pad extra spaces ' ' when necessary so that each line has exactly L characters.
     * <p/>
     * Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line
     * do not divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.
     * <p/>
     * For the last line of text, it should be left justified and no extra space is inserted between words.
     * <p/>
     * For example,
     * words: ["This", "is", "an", "example", "of", "text", "justification."]
     * L: 16.
     * <p/>
     * Return the formatted lines as:
     * <p/>
     * [
     * "This    is    an",
     * "example  of text",
     * "justification.  "
     * ]
     * <p/>
     * Note: Each word is guaranteed not to exceed L in length.
     */
    public ArrayList<String> fullJustify(String[] words, int L) {
        ArrayList<String> justified = new ArrayList<String>();

        if (L == 0) {
            Collections.addAll(justified, words);
            return justified;
        }

        int stringLength = 0;
        int numOfWordsInLine = 0;
        int lineStartWordIndex = 0;
        for (int i = 0; i < words.length; i++) {
            stringLength += words[i].length() + 1;
            numOfWordsInLine++;
            if (stringLength + (i < words.length - 1 ? words[i + 1].length() : 0) > L) {
                int spacesToInsert = L - (stringLength - 1);
                int baseSpaces = numOfWordsInLine > 1 ? spacesToInsert / (numOfWordsInLine - 1) : 0;
                int additionalSpaces = numOfWordsInLine > 1 ? spacesToInsert % (numOfWordsInLine - 1) : L - stringLength;
                StringBuilder line = new StringBuilder();
                for (int j = lineStartWordIndex; j < i; j++) {
                    line.append(words[j]);
                    line.append(' ');
                    for (int s = 0; s < baseSpaces + (additionalSpaces > 0 ? 1 : 0); s++)
                        line.append(' ');
                    if (additionalSpaces > 0)
                        additionalSpaces--;
                }
                line.append(words[i]);
                if (numOfWordsInLine == 1 && stringLength - 1 < L) {
                    line.append(' ');
                    for (int s = 0; s < baseSpaces + additionalSpaces; s++)
                        line.append(' ');
                }
                justified.add(line.toString());
                lineStartWordIndex = i + 1;
                numOfWordsInLine = 0;
                stringLength = 0;
            }

        }

        if (lineStartWordIndex == words.length) return justified;

        StringBuilder lastLine = new StringBuilder();

        for (int i = lineStartWordIndex; i < words.length; i++) {
            lastLine.append(words[i]);
            if (lastLine.length() < L) lastLine.append(' ');
        }

        if (lastLine.length() < L) {
            int len = lastLine.length();
            for (int i = 0; i < L - len; i++) lastLine.append(' ');
        }

        if (lastLine.length() > 0)
            justified.add(lastLine.toString());
        return justified;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/climbing-stairs/">Climbing Stairs</a>
     * You are climbing a stair case. It takes n steps to reach to the top.
     * <p/>
     * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
     */
    public int climbStairs(int n) {
        return climbStairs(n, new int[n + 1]);
    }

    private int climbStairs(int n, int[] memo) {
        if (n <= 2) return n;
        if (memo[n] > 0)
            return memo[n];
        memo[n] = climbStairs(n - 2, memo) + climbStairs(n - 1, memo);
        return memo[n];
    }

    /**
     * <a href="http://oj.leetcode.com/problems/simplify-path/">Simplify Path</a>
     * Given an absolute path for a file (Unix-style), simplify it.
     * <p/>
     * For example,
     * path = "/home/", => "/home"
     * path = "/a/./b/../../c/", => "/c"
     */
    public String simplifyPath(String path) {
        LinkedList<String> elements = new LinkedList<String>();
        for (String element : path.split("/")) {
            if (element.isEmpty()) continue;
            if (element.equals(".")) continue;
            if (element.equals(".."))
                elements.poll();
            else
                elements.push(element);
        }
        if (elements.isEmpty()) return "/";
        Collections.reverse(elements);
        StringBuilder simplifiedPath = new StringBuilder();
        for (String element : elements) {
            simplifiedPath.append("/");
            simplifiedPath.append(element);
        }
        return simplifiedPath.toString();
    }

    /**
     * <a href="http://oj.leetcode.com/problems/edit-distance/">Edit Distance</a>
     * Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)
     * <p/>
     * You have the following 3 operations permitted on a word:
     * <p/>
     * a) Insert a character
     * b) Delete a character
     * c) Replace a character
     */
    public int minDistance(String word1, String word2) {

        int m = word1.length();
        int n = word2.length();
        int[][] distance = new int[m + 1][n + 1];

        for (int i = 0; i < m; i++) {
            distance[i + 1][0] = i + 1;
        }
        for (int j = 0; j < n; j++) {
            distance[0][j + 1] = j + 1;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (word1.charAt(i) == word2.charAt(j))
                    distance[i + 1][j + 1] = distance[i][j];
                else distance[i + 1][j + 1] = 1 +
                        Math.min(Math.min(
                                distance[i][j + 1],
                                distance[i + 1][j]),
                                distance[i][j]);
            }
        }

        return distance[m][n];
    }

    /**
     * <a href="http://oj.leetcode.com/problems/set-matrix-zeroes/">Set Matrix Zeros</a>
     * Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in place.
     */
    public void setZeroes(int[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;

        boolean upperEdgeHasZeros = false;
        for (int i = 0; i < width; i++) {
            if (matrix[0][i] == 0) {
                upperEdgeHasZeros = true;
                break;
            }
        }

        boolean leftEdgeHasZeros = false;
        for (int i = 0; i < height; i++) {
            if (matrix[i][0] == 0) {
                leftEdgeHasZeros = true;
                break;
            }
        }

        for (int i = 1; i < height; i++) {
            for (int j = 1; j < width; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    break;
                }
            }
        }

        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                if (matrix[j][i] == 0) {
                    matrix[0][i] = 0;
                    break;
                }
            }
        }

        for (int i = 1; i < height; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 0; j < width; j++)
                    matrix[i][j] = 0;
            }
        }

        for (int i = 1; i < width; i++) {
            if (matrix[0][i] == 0) {
                for (int j = 0; j < height; j++)
                    matrix[j][i] = 0;
            }
        }

        if (upperEdgeHasZeros) {
            for (int i = 0; i < width; i++)
                matrix[0][i] = 0;
        }

        if (leftEdgeHasZeros) {
            for (int i = 0; i < height; i++)
                matrix[i][0] = 0;
        }

    }

    /**
     * <a href="http://oj.leetcode.com/problems/minimum-window-substring/">Minimum Window Substring</a>
     * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
     * <p/>
     * For example,
     * S = "ADOBECODEBANC"
     * T = "ABC"
     * <p/>
     * Minimum window is "BANC".
     * <p/>
     * Note:
     * If there is no such window in S that covers all characters in T, return the emtpy string "".
     * <p/>
     * If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.
     * <p/>
     * Solution: http://oj.leetcode.com/problems/minimum-window-substring/
     */
    public String minWindow(String S, String T) {

        int[] needToFind = new int[256];
        for (char ch : T.toCharArray()) {
            needToFind[ch]++;
        }
        int[] hasFound = new int[256];

        int minSubstringStart = 0;
        int minSubstringEnd = -1;
        int minLen = Integer.MAX_VALUE;

        int count = 0;
        int begin = 0;
        for (int i = 0; i < S.length(); i++) {
            char ch = S.charAt(i);

            hasFound[ch]++;

            if (hasFound[ch] <= needToFind[ch])
                count++;

            if (count == T.length()) {
                for (int j = begin; j <= i; j++) {
                    char sch = S.charAt(j);
                    if (hasFound[sch] > needToFind[sch])
                        hasFound[sch]--;
                    else {
                        begin = j;
                        break;
                    }
                }

                if (i - begin < minLen) {
                    minSubstringStart = begin;
                    minSubstringEnd = i;
                    minLen = minSubstringEnd - minSubstringStart;
                }
            }
        }

        return S.substring(minSubstringStart, minSubstringEnd + 1);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/search-a-2d-matrix/">Search a 2D Matrix</a>
     * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
     * <p/>
     * Integers in each row are sorted from left to right.
     * The first integer of each row is greater than the last integer of the previous row.
     * <p/>
     * For example,
     * <p/>
     * Consider the following matrix:
     * <p/>
     * [
     * [1,   3,  5,  7],
     * [10, 11, 16, 20],
     * [23, 30, 34, 50]
     * ]
     * <p/>
     * Given target = 3, return true.
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int start = 0;
        int c = matrix[0].length;

        int end = matrix.length * matrix[0].length - 1;
        int mid = 0;
        while (start <= end) {
            mid = (end + start) / 2;
            int val = matrix[mid / c][mid % c];
            if (val > target)
                end = mid - 1;
            else if (val < target)
                start = mid + 1;
            else break;
        }

        return matrix[mid / c][mid % c] == target;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/sort-colors/">Sort Colors</a>
     * Given an array with n objects colored red, white or blue, sort them so that objects of the same color are
     * adjacent, with the colors in the order red, white and blue.
     * <p/>
     * Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
     * <p/>
     * Note:
     * You are not suppose to use the library's sort function for this problem.
     * <p/>
     * click to show follow up.
     * <p/>
     * Follow up:
     * A rather straight forward solution is a two-pass algorithm using counting sort.
     * First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's,
     * then 1's and followed by 2's.
     * <p/>
     * Could you come up with an one-pass algorithm using only constant space?
     * <p/>
     * Solution: 3-way partitioning (see Sedgewick)
     */
    public void sortColors(int[] A) {
        int n = A.length - 1;
        int start = 0;
        int end = n;
        int i = 0;

        while (i <= end) {
            if (A[i] == 0) {
                swap(A, start, i);
                start++;
                i++;
            } else if (A[i] == 2) {
                swap(A, i, end);
                end--;
            } else i++;
        }
    }

    private void swap(int[] A, int i, int j) {
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/word-search/">Word Search</a>
     * Given a 2D board and a word, find if the word exists in the grid.
     * <p/>
     * The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are
     * those horizontally or vertically neighboring. The same letter cell may not be used more than once.
     * <p/>
     * For example,
     * Given board =
     * <p/>
     * [
     * ["ABCE"],
     * ["SFCS"],
     * ["ADEE"]
     * ]
     * <p/>
     * word = "ABCCED", -> returns true,
     * word = "SEE", -> returns true,
     * word = "ABCB", -> returns false.
     */
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (exist(board, word, 0, visited, i, j))
                    return true;
            }
        }
        return false;
    }

    private boolean exist(char[][] board, String word, int letter, boolean[][] visited, int y, int x) {
        if (letter == word.length())
            return true;

        if (y < 0 || x < 0 || y >= board.length || x >= board[0].length)
            return false;

        if (visited[y][x] || board[y][x] != word.charAt(letter))
            return false;

        visited[y][x] = true;

        boolean exists = exist(board, word, letter + 1, visited, y + 1, x)
                || exist(board, word, letter + 1, visited, y - 1, x)
                || exist(board, word, letter + 1, visited, y, x + 1)
                || exist(board, word, letter + 1, visited, y, x - 1);
        visited[y][x] = false;

        return exists;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/combinations/">Combinations</a>
     * Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.
     * <p/>
     * For example,
     * If n = 4 and k = 2, a solution is:
     * <p/>
     * [
     * [2,4],
     * [3,4],
     * [2,3],
     * [1,2],
     * [1,3],
     * [1,4],
     * ]
     */
    public ArrayList<ArrayList<Integer>> combine(int n, int k) {
        return combine(n, k, 0, 0, new ArrayList<Integer>());
    }

    private ArrayList<ArrayList<Integer>> combine(int n, int k, int level, int start, ArrayList<Integer> combo) {
        if (level == k) {
            ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
            result.add(new ArrayList<Integer>(combo));
            return result;
        }
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        for (int i = start; i < n; i++) {
            combo.add(i + 1);
            result.addAll(combine(n, k, level + 1, i + 1, combo));
            combo.remove((Integer) (i + 1));
        }
        return result;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/subsets/">Subsets</a>
     * Given a set of distinct integers, S, return all possible subsets.
     * <p/>
     * Note:
     * <p/>
     * Elements in a subset must be in non-descending order.
     * The solution set must not contain duplicate subsets.
     * <p/>
     * For example,
     * If S = [1,2,3], a solution is:
     * <p/>
     * [
     * [3],
     * [1],
     * [2],
     * [1,2,3],
     * [1,3],
     * [2,3],
     * [1,2],
     * []
     * ]
     */
    public ArrayList<ArrayList<Integer>> subsets(int[] S) {
        /*
        ArrayList<ArrayList<Integer>> combos = new ArrayList<ArrayList<Integer>>();
        for (int k = 0; k <= S.length; k++)
            subsetsNofK(S, 0, 0, k, new ArrayList<Integer>(), combos);
        return combos;
        */
        return subsetsBits(S);
    }

    private void subsetsNofK(int[] S, int level, int start, int maxLevel, ArrayList<Integer> combo, ArrayList<ArrayList<Integer>> combos) {
        if (level == maxLevel) {
            ArrayList<Integer> result = new ArrayList<Integer>(combo);
            Collections.sort(result);
            combos.add(result);
            return;
        }

        for (int i = start; i < S.length; i++) {
            combo.add(S[i]);
            subsetsNofK(S, level + 1, i + 1, maxLevel, combo, combos);
            combo.remove((Integer) S[i]);
        }
    }

    private ArrayList<ArrayList<Integer>> subsetsBits(int[] S) {
        long num = 1 << S.length;
        ArrayList<ArrayList<Integer>> combos = new ArrayList<ArrayList<Integer>>();

        for (long i = 0; i < num; i++) {
            ArrayList<Integer> combo = new ArrayList<Integer>();
            for (long n = i, index = 0; n > 0; n >>= 1, index++) {
                if ((n & 1) == 1) combo.add(S[(int) index]);
            }
            Collections.sort(combo);
            combos.add(combo);
        }

        return combos;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/remove-duplicates-from-sorted-array-ii/">Remove Duplicates from Sorted Array II</a>
     * Follow up for "Remove Duplicates":
     * What if duplicates are allowed at most twice?
     * <p/>
     * For example,
     * Given sorted array A = [1,1,1,2,2,3],
     * <p/>
     * Your function should return length = 5, and A is now [1,1,2,2,3].
     */
    public int removeDuplicates(int[] A) {
        LinkedList<Integer> buf = new LinkedList<Integer>();
        int storePointer = 0;
        for (int i = 0; i < A.length; i++) {
            boolean shouldAddIthNumber =
                    i - 1 < 0 ||
                            ((A[i] == A[i - 1] && (i - 2 < 0 || A[i - 1] != A[i - 2]))) ||
                            (A[i] != A[i - 1]);
            if (shouldAddIthNumber) {
                if (buf.size() == 3) {
                    A[storePointer++] = buf.removeFirst();
                }
                buf.add(A[i]);
            }
        }
        for (Integer b : buf) {
            A[storePointer++] = b;
        }
        return storePointer;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/search-in-rotated-sorted-array-ii/">Search in Rotated Sorted Array II</a>
     * Follow up for "Search in Rotated Sorted Array":
     * What if duplicates are allowed?
     * <p/>
     * Would this affect the run-time complexity? How and why?
     * <p/>
     * Write a function to determine if a given target is in the array.
     * <p/>
     * Solution is based on http://oj.leetcode.com/discuss/223/when-there-are-duplicates-the-worst-case-is-could-we-do-better
     */
    public boolean search(int[] A, int target) {
        int l = 0;
        int r = A.length - 1;
        int mid;
        while (l <= r) {
            mid = (l + r) / 2;
            if (A[mid] == target) return true;
            if (A[l] < A[mid]) { // left half is sorted
                if (A[l] <= target && A[mid] > target) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else if (A[l] > A[mid]) { // right half is sorted
                if (A[r] >= target && A[mid] < target) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            } else l++;
        }
        return false;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/remove-duplicates-from-sorted-list/">Remove Duplicates from Sorted List</a>
     * Given a sorted linked list, delete all duplicates such that each element appear only once.
     * <p/>
     * For example,
     * Given 1->1->2, return 1->2.
     * Given 1->1->2->3->3, return 1->2->3.
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode readNode = head.next;
        ListNode writeNode = head;
        writeNode.next = null;
        while (readNode != null) {
            if (writeNode.val != readNode.val) {
                writeNode.next = readNode;
                writeNode = writeNode.next;
            }
            readNode = readNode.next;
        }
        writeNode.next = null;
        return head;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/remove-duplicates-from-sorted-list-ii/">Remove Duplicates from Sorted List II</a>
     * Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.
     * <p/>
     * For example,
     * Given 1->2->3->3->4->4->5, return 1->2->5.
     * Given 1->1->1->2->3, return 2->3.
     */
    public ListNode deleteDuplicatesII(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode readNode = head;
        ListNode writeNode = null;
        ListNode newHead = null;
        ListNode prevNode = null;

        while (readNode != null) {
            boolean shouldWriteNode =
                    (prevNode == null && readNode.next == null) ||
                            ((prevNode == null || prevNode.val != readNode.val)
                                    && (readNode.next == null || readNode.next.val != readNode.val));
            if (shouldWriteNode) {
                if (writeNode == null) {
                    writeNode = readNode;
                } else {
                    writeNode.next = readNode;
                    writeNode = writeNode.next;
                }
                if (newHead == null) {
                    newHead = writeNode;
                }

            }
            if (prevNode == null) {
                prevNode = readNode;
            } else {
                prevNode = prevNode.next;
            }
            readNode = readNode.next;

        }
        if (writeNode != null)
            writeNode.next = null;
        return newHead;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/largest-rectangle-in-histogram/">Largest Rectangle in Histogram</a>
     * Given n non-negative integers representing the histogram's bar height where the width of each bar is 1,
     * find the area of largest rectangle in the histogram.
     * <p/>
     * Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].
     * <p/>
     * The largest rectangle is shown in the shaded area, which has area = 10 unit.
     * <p/>
     * For example,
     * Given height = [2,1,5,6,2,3],
     * return 10.
     * </p>
     * Solution: divide and conquer (pretty much like the maximum subarray problem)
     */
    public int largestRectangleArea(int[] height) {
        if (height == null || height.length == 0) return 0;
        return largestRectangleArea(height, 0, height.length - 1);
    }

    private int largestRectangleArea(int[] A, int l, int r) {
        if (l == r) return A[l];
        int mid = (l + r) / 2;
        int leftArea = largestRectangleArea(A, l, mid);
        int rightArea = largestRectangleArea(A, mid + 1, r);
        int crossingArea = crossingArea(A, l, r, mid);
        return Math.max(leftArea, Math.max(rightArea, crossingArea));
    }

    private int crossingArea(int[] A, int l, int r, int mid) {
        int left = mid;
        int right = mid;
        int leftMin = A[left];
        int rightMin = A[right];
        int bestArea = 0;

        while (left >= l && right <= r) {
            leftMin = Math.min(A[left], leftMin);
            rightMin = Math.min(A[right], rightMin);

            bestArea = Math.max(bestArea,
                    Math.min(leftMin, rightMin) * (right - left + 1));
            if (left > l && (right == r || A[left - 1] > A[right + 1])) {
                left--;
            } else {
                right++;
            }
        }
        return bestArea;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/binary-tree-inorder-traversal/">Binary Tree Inorder Traversal</a>
     * Given a binary tree, return the inorder traversal of its nodes' values.
     * <p/>
     * For example:
     * Given binary tree {1,#,2,3},
     * <p/>
     * 1
     * \
     * 2
     * /
     * 3
     * <p/>
     * return [1,3,2].
     * <p/>
     * Note: Recursive solution is trivial, could you do it iteratively?
     * <p/>
     * confused what "{1,#,2,3}" means? > read more on how binary tree is serialized on OJ.
     */
    public ArrayList<Integer> inorderTraversal(TreeNode root) {
        if (root == null) return new ArrayList<Integer>();
        TreeNode node = root;
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        ArrayList<Integer> inorder = new ArrayList<Integer>();
        boolean done = false;
        while (!done) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                if (stack.isEmpty()) {
                    done = true;
                } else {
                    node = stack.peek();
                    stack.pop();
                    inorder.add(node.val);
                    node = node.right;
                }
            }
        }
        return inorder;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/unique-binary-search-trees/">Unique Binary Search Trees</a>
     * Given n, how many structurally unique BST's (binary search trees) that store values 1...n?
     * <p/>
     * For example,
     * Given n = 3, there are a total of 5 unique BST's.
     * <p/>
     * 1         3     3      2      1
     * \       /     /      / \      \
     * 3     2     1      1   3      2
     * /     /       \                 \
     * 2     1         2                 3
     */
    public int numTrees(int n) {
        if (n < 2) return 1;
        int total = 0;
        for (int i = 0; i < n; i++) {
            total += numTrees(i) * numTrees(n - i - 1);
        }
        return total;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/unique-binary-search-trees-ii/">Unique Binary Search Trees II</a>
     * Given n, generate all structurally unique BST's (binary search trees) that store values 1...n.
     * <p/>
     * For example,
     * Given n = 3, your program should return all 5 unique BST's shown below.
     * <p/>
     * 1         3     3      2      1
     * \       /     /      / \      \
     * 3     2     1      1   3      2
     * /     /       \                 \
     * 2     1         2                 3
     * <p/>
     * confused what "{1,#,2,3}" means? > read more on how binary tree is serialized on OJ.
     */
    public ArrayList<TreeNode> generateTrees(int n) {
        return buildTrees(0, n - 1);
    }

    private ArrayList<TreeNode> buildTrees(int from, int to) {
        if (from > to) {
            ArrayList<TreeNode> list = new ArrayList<TreeNode>();
            list.add(null);
            return list;
        }

        ArrayList<TreeNode> allTrees = new ArrayList<TreeNode>();
        for (int i = from; i <= to; i++) {
            ArrayList<TreeNode> leftSubtrees = buildTrees(from, i - 1);
            ArrayList<TreeNode> rightSubtrees = buildTrees(i + 1, to);
            for (TreeNode leftSubtree : leftSubtrees) {
                for (TreeNode rightSubtree : rightSubtrees) {
                    TreeNode newTree = new TreeNode(i + 1);
                    newTree.left = leftSubtree;
                    newTree.right = rightSubtree;
                    allTrees.add(newTree);
                }
            }
        }

        return allTrees;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/validate-binary-search-tree/">Validate Binary Search Tree</a>
     * Given a binary tree, determine if it is a valid binary search tree (BST).
     * <p/>
     * Assume a BST is defined as follows:
     * <p/>
     * The left subtree of a node contains only nodes with keys less than the node's key.
     * The right subtree of a node contains only nodes with keys greater than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     * <p/>
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isValidBST(TreeNode root, int left, int right) {
        if (root == null) return true;
        if (root.val <= left || root.val >= right) return false;
        return isValidBST(root.left, left, root.val) && isValidBST(root.right, root.val, right);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/same-tree/">Same Tree</a>
     * Given two binary trees, write a function to check if they are equal or not.
     * <p/>
     * Two binary trees are considered equal if they are structurally identical and the nodes have the same value.
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null) return false;
        if (q == null) return false;
        if (p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/">Construct Binary Tree from Preorder and Inorder Traversal</a>
     * Given preorder and inorder traversal of a tree, construct the binary tree.
     * <p/>
     * Note:
     * You may assume that duplicates do not exist in the tree.
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int[] pi = new int[1];
        return buildInPreTree(preorder, inorder, pi, 0, inorder.length - 1);
    }

    private TreeNode buildInPreTree(int[] p, int[] i, int[] pi, int leftIn, int rightIn) {
        if (leftIn > rightIn) return null;

        int val = p[pi[0]];
        TreeNode root = new TreeNode(val);
        pi[0]++;
        int foundIn = -1;
        for (int k = leftIn; k <= rightIn; k++) {
            if (i[k] == val) {
                foundIn = k;
                break;
            }
        }
        root.left = buildInPreTree(p, i, pi, leftIn, foundIn - 1);
        root.right = buildInPreTree(p, i, pi, foundIn + 1, rightIn);

        return root;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/symmetric-tree/">Symmetric Tree</a>
     * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
     * <p/>
     * For example, this binary tree is symmetric:
     * <p/>
     * 1
     * / \
     * 2   2
     * / \ / \
     * 3  4 4  3
     * <p/>
     * But the following is not:
     * <p/>
     * 1
     * / \
     * 2   2
     * \   \
     * 3    3
     * <p/>
     * Note:
     * Bonus points if you could solve it both recursively and iteratively.
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        // return isSymmetricRecursive(root.left, root.right);
        return isSymmetricIterative(root.left, root.right);
    }

    private boolean isSymmetricRecursive(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        if (left.val != right.val) return false;
        return isSymmetricRecursive(left.left, right.right) && isSymmetricRecursive(left.right, right.left);
    }

    private boolean isSymmetricIterative(TreeNode l, TreeNode r) {
        if (l == null && r == null) return true;
        if (l == null || r == null) return false;
        LinkedList<TreeNode> q1 = new LinkedList<TreeNode>();
        q1.add(l);
        LinkedList<TreeNode> q2 = new LinkedList<TreeNode>();
        q2.add(r);

        while (!q1.isEmpty() && !q2.isEmpty()) {
            TreeNode left = q1.removeFirst();
            TreeNode right = q2.removeFirst();
            if (left.val != right.val) return false;
            if (left.left != null && right.right != null) {
                q1.addLast(left.left);
                q2.addLast(right.right);
            } else if (left.left != null || right.right != null) {
                return false;
            }

            if (left.right != null && right.left != null) {
                q1.addLast(left.right);
                q2.addLast(right.left);
            } else if (left.right != null || right.left != null) {
                return false;
            }
        }

        return q1.isEmpty() && q2.isEmpty();

    }

    /**
     * <a href="http://oj.leetcode.com/problems/maximal-rectangle/">Maximal Rectangle</a>
     * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing all ones and return its area.
     */
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return 0;
        int maxRect = 0;
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] intMatrix = new int[m][n];
        for (int row = 0; row < m; row++) {
            int[] histogramRow = new int[n];
            for (int i = 0; i < n; i++) {
                intMatrix[row][i] = matrix[row][i] - '0';
                if (row > 0 && intMatrix[row][i] > 0)
                    intMatrix[row][i] += intMatrix[row - 1][i];
                histogramRow[i] = intMatrix[row][i];
            }
            int maxArea = largestRectangleArea(histogramRow);
            maxRect = Math.max(maxRect, maxArea);
        }
        return maxRect;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/partition-list/">Partition List</a>
     * Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.
     * <p/>
     * You should preserve the original relative order of the nodes in each of the two partitions.
     * <p/>
     * For example,
     * Given 1->4->3->2->5->2 and x = 3,
     * return 1->2->2->4->3->5.
     */
    public ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null) return head;
        ListNode readNode = head;
        ListNode lessWrite = null;
        ListNode greaterWrite = null;
        ListNode lessWriteHead = null;
        ListNode greaterWriteHead = null;

        while (readNode != null) {
            if (readNode.val < x) {
                if (lessWrite == null) lessWrite = readNode;
                else {
                    lessWrite.next = readNode;
                    lessWrite = lessWrite.next;
                }
                if (lessWriteHead == null) lessWriteHead = readNode;
            } else {
                if (greaterWrite == null) greaterWrite = readNode;
                else {
                    greaterWrite.next = readNode;
                    greaterWrite = greaterWrite.next;
                }
                if (greaterWriteHead == null) greaterWriteHead = readNode;
            }

            readNode = readNode.next;
        }
        if (greaterWrite != null) greaterWrite.next = null;
        if (lessWrite != null) lessWrite.next = null;
        if (greaterWriteHead == null) return lessWriteHead;
        if (lessWriteHead == null) return greaterWriteHead;
        if (lessWrite != null) lessWrite.next = greaterWriteHead;
        return lessWriteHead;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/interleaving-string/">Interleaving String</a>
     * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
     * <p/>
     * For example,
     * Given:
     * s1 = "aabcc",
     * s2 = "dbbca",
     * <p/>
     * When s3 = "aadbbcbcac", return true.
     * When s3 = "aadbbbaccc", return false.
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1 == null || s1.isEmpty()) return s2.equals(s3);
        if (s2 == null || s2.isEmpty()) return s1.equals(s3);
        int m = s1.length();
        int n = s2.length();
        if (s3.length() != m + n) return false;

        boolean[][] A = new boolean[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 && j == 0) {
                    A[i][j] = true;
                    continue;
                }

                char s3ch = s3.charAt(i + j - 1);
                if (i == 0)
                    A[i][j] = s2.charAt(j - 1) == s3ch && A[i][j - 1];
                else if (j == 0)
                    A[i][j] = s1.charAt(i - 1) == s3ch && A[i - 1][j];
                else if (s1.charAt(i - 1) == s3ch && s2.charAt(j - 1) != s3ch)
                    A[i][j] = A[i - 1][j];
                else if (s1.charAt(i - 1) != s3ch && s2.charAt(j - 1) == s3ch)
                    A[i][j] = A[i][j - 1];
                else if (s1.charAt(i - 1) == s3ch && s2.charAt(j - 1) == s3ch)
                    A[i][j] = A[i][j - 1] || A[i - 1][j];

            }
        }

        return A[m][n];
    }

    /**
     * <a href="http://oj.leetcode.com/problems/reorder-list/">Reorder List</a>
     * Given a singly linked list L: L0→L1→…→Ln-1→Ln,
     * reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…
     * <p/>
     * You must do this in-place without altering the nodes' values.
     * <p/>
     * For example,
     * Given {1,2,3,4}, reorder it to {1,4,2,3}.
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        reorderListNoStack(head);
    }

    private void reorderListStack(ListNode head) {
        ListNode slowPointer = head;
        LinkedList<ListNode> stack = new LinkedList<ListNode>();
        int totalNodes = 0;
        while (slowPointer != null) {
            stack.push(slowPointer);
            slowPointer = slowPointer.next;
            totalNodes++;
        }

        while (totalNodes > 0) {
            ListNode lastNode = stack.poll();
            lastNode.next = null;
            ListNode nextNode = head.next;
            if (head.next != lastNode) {
                head.next = lastNode;
                lastNode.next = nextNode;
                head = nextNode;
            }
            totalNodes--;
            totalNodes--;
        }
    }

    // Based on http://oj.leetcode.com/discuss/236/does-this-problem-solution-time-complexity-space-comlexity
    private void reorderListNoStack(ListNode head) {

        ListNode start = head;

        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // slow now points to the second half
        ListNode reverseHead = slow.next;
        ListNode prev = null;
        while (reverseHead != null) {
            ListNode nextRead = reverseHead.next;
            reverseHead.next = prev;
            prev = reverseHead;
            reverseHead = nextRead;
        }

        slow.next = null;

        ListNode secondHalfHead = prev;
        while (secondHalfHead != null) {
            ListNode firstHalfNext = start.next;
            ListNode secondHalfNext = secondHalfHead.next;
            secondHalfHead.next = firstHalfNext;
            start.next = secondHalfHead;
            secondHalfHead = secondHalfNext;
            start = firstHalfNext;
        }

    }

}
