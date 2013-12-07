package crackinginterview;

/**
 * Created by Sobercheg on 12/6/13.
 */
public class KthToLastInList {

    static class MutableInt {
        int value;

        MutableInt(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }

    public Node getKthToLastInList(Node head, int k, MutableInt index) {
        if (head == null) return null;
        Node node = getKthToLastInList(head.next, k, index);
        // but this is wrong!
        index.value++;
        if (index.value == k) {
            return head;
        }

        return node;
    }

    public static void main(String[] args) {
        KthToLastInList kthToLastInList = new KthToLastInList();
        Node root = new Node(1, new Node(2, new Node(3, new Node(4, new Node(5, new Node(6, new Node(7)))))));
        MutableInt index = new MutableInt(0);
        Node kthNode = kthToLastInList.getKthToLastInList(root, 3, index);
        System.out.println(kthNode);
        System.out.println(index);
    }

}
