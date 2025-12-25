package data;

class CustomLinkedList {
    private ListNode head;
    private ListNode current;
    private ListNode tail;
    private ListNode buffer;

    private int size;

    public int size() {
        return size;
    }

    public void clear() {
        head = tail = buffer = current = null;
        size = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void addAtHead(int val) {
        if (head != null) {
            head = new ListNode(val, head);
        } else {
            tail = head = new ListNode(val);
        }
        size++;
    }

    public void addAtTail(int val) {
        if (tail != null) {
            tail = tail.next = new ListNode(val);
        } else {
            tail = head = new ListNode(val);
        }

        size++;
    }

    public void addAtIndex(int index, int val) {
        if (index > 0 && index < size){
            current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }

            buffer = current.next;
            current = current.next = new ListNode(val);
            current.next = buffer;

            size++;
        } else if (index == size){
            addAtTail(val);
        } else if (index == 0) {
            addAtHead(val);
        }
    }

    public void deleteAtIndex(int index) {
        if (index > 0 && index < size - 1) {
            current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }

            current.next = current.next.next;
            size--;
        } else if (index == 0) {
            deleteAtStart();
        } else if (index == size - 1) {
            deleteAtTail();
        }
    }

    public void deleteAtStart() {
        if (size == 1) {
            clear();
        } else if (head != null) {
            head = head.next;
            size--;
        }
    }
    public void deleteAtTail() {
        if (size > 1) {
            current = head;

            while (current.next.next != null) {
                current = current.next;
            }

            current.next = null;

            tail = current;
            size--;
        } else if (size == 1) {
            head = null;
            size = 0;
        }
    }

    public int get(int index) {
        if (index < 0 || index >= size){return -1;}

        current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.val;
    }

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
