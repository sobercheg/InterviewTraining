package crackinginterview;

/**
 * Created by Sobercheg on 12/6/13.
 */
public class DeleteListNode {

    public void deleteNode(Node node) {
        if (node == null) return;
        if (node.next == null) return;
        node.data = node.next.data;
        node.next = node.next.next;
        node.next.next = null;
    }

}
