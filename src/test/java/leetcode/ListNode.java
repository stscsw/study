package leetcode;

/**
 * @author Lier
 * @since 2022/6/16 14:30
 */
public class ListNode {

    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        ListNode listNode = this;
        StringBuilder sb = new StringBuilder();
        while (listNode != null) {
            sb.append(listNode.val);
            listNode = listNode.next;
        }
        return sb.reverse().toString();
    }
}
