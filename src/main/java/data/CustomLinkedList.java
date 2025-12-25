package data;

class CustomLinkedList {
    private ListNode head;
    private ListNode current;
    private ListNode tail;
    private ListNode buffer;

    private int size;

    private final static class ListNode {
        ListNode next;
        int val;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
