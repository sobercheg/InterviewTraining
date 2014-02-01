package leetcode.oj;

import java.util.*;

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

            if (input != that.input) return false;
            if (state != that.state) return false;

            return true;
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
}
