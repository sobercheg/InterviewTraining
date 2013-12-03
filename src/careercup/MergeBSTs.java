package careercup;

import java.util.Iterator;

/**
 * Created by Sobercheg on 11/26/13.
 */
public class MergeBSTs {
    private static class Node<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;
        private Node<T> parent;

        public Node(T data, Node<T> parent) {
            this.data = data;
            this.parent = parent;
        }

        public Node() {

        }
    }

    private static class TreeIterator<T> implements Iterator {
        private Node<T> node;

        public TreeIterator(Node<T> root) {
            // find smallest
            this.node = root;
            while (node.left != null) {
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new IllegalStateException("No more elements");
            T value = peek();

            // move logic
            if (node.right == null) { // no right children
                Node<T> parent = node.parent;
                while (parent != null && parent.right == node) {
                    node = parent;
                    parent = parent.parent;
                }
                node = parent;
            } else { // there is the right child
                node = node.right;
                while (node.left != null) {
                    node = node.left;
                }
            }
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("no remove");
        }

        public T peek() {
            return node.data;
        }
    }


    public Node createBalancedBSTFromTrees(Node<Integer> root1, Node<Integer> root2) {
        return createBalancedBSTFromTrees(new TreeIterator<Integer>(root1), new TreeIterator<Integer>(root2), 0, getTreeSize(root1) + getTreeSize(root2));
    }

    private Node createBalancedBSTFromTrees(TreeIterator<Integer> treeIterator1, TreeIterator<Integer> treeIterator2, int from, int to) {
        if (to < from) return null;
        if (!treeIterator1.hasNext() && !treeIterator2.hasNext()) return null;
        int mid = (from + to) / 2;

        Node parent = new Node();
        parent.left = createBalancedBSTFromTrees(treeIterator1, treeIterator2, from, mid - 1);
        if (parent.left != null) {
            parent.left.parent = parent;
        }
        Integer data;
        if (!treeIterator1.hasNext()) {
            data = treeIterator2.next();
        } else if (!treeIterator2.hasNext()) {
            data = treeIterator1.next();
        } else if (treeIterator1.peek() >= treeIterator2.peek()) {
            data = treeIterator2.next();
        } else {
            data = treeIterator1.next();
        }
        parent.data = data;
        parent.right = createBalancedBSTFromTrees(treeIterator1, treeIterator2, mid + 1, to);
        if (parent.right != null) {
            parent.right.parent = parent;
        }

        return parent;
    }

    public int getTreeSize(Node root) {
        if (root == null) return 0;
        return 1 + getTreeSize(root.left) + getTreeSize(root.right);
    }


    public static void main(String[] args) {
        MergeBSTs bsts = new MergeBSTs();
        Node<Integer> root = new Node<Integer>(5, null);
        Node<Integer> left = new Node<Integer>(1, root);
        Node<Integer> right = new Node<Integer>(10, root);
        root.left = left;
        root.right = right;
        TreeIterator<Integer> iterator = new TreeIterator<Integer>(root);
        System.out.println(iterator.hasNext());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.hasNext());

        Node<Integer> root2 = new Node<Integer>(50, null);
        Node<Integer> left2 = new Node<Integer>(11, root2);
        root2.left = left2;
        Node<Integer> left3 = new Node<Integer>(2, left2);
        left2.left = left3;

        Node balanced = bsts.createBalancedBSTFromTrees(root, root2);

    }

}
