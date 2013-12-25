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
     * Definition for binary tree
     */
    static class TreeNode {
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

}
