package leetcode;

import common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Sobercheg on 12/2/13.
 */
public class TreeLevelByLevel {

    public void printTree(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        TreeNode sentinel = new TreeNode(-1);
        q.offer(sentinel);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (q.isEmpty()) break;
            if (node != sentinel) {
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
                System.out.print(node.data + " ");
            } else {
                System.out.println();
                q.offer(sentinel);
            }
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        TreeNode left = new TreeNode(3);
        TreeNode right = new TreeNode(8);
        TreeNode rightLeft = new TreeNode(7);
        root.right = right;
        root.left = left;
        right.left = rightLeft;
        TreeLevelByLevel tree = new TreeLevelByLevel();
        tree.printTree(root);
    }
}
