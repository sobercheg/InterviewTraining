package crackinginterview;

/**
 * Created by Sobercheg on 12/7/13.
 */
class Node {
    int data;
    Node next;

    Node(int data, Node next) {
        this.data = data;
        this.next = next;
    }

    Node(int data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + data +
                '}';
    }
}
