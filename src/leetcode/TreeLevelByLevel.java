package leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Sobercheg on 12/2/13.
 */
public class TreeLevelByLevel {

    static class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "" + data;
        }
    }

    public void printTree(Node root) {
        Queue<Node> q = new LinkedList<Node>();
        q.offer(root);
        Node sentinel = new Node(-1);
        q.offer(sentinel);
        while (!q.isEmpty()) {
            Node node = q.poll();
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
        Node root = new Node(5);
        Node left = new Node(3);
        Node right = new Node(8);
        Node rightLeft = new Node(7);
        root.right = right;
        root.left = left;
        right.left = rightLeft;
        TreeLevelByLevel tree = new TreeLevelByLevel();
        tree.printTree(root);
    }
}
