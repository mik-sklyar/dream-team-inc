package data;

import java.util.*;

class CustomLinkedList<E> {
    private ListNode head;
    private ListNode tail;

    private int size;

    //@Override
    public int size() {
        return size;
    }

    //@Override
    public boolean isEmpty() {
        return head == null;
    }

    //@Override
    public boolean contains(Object o) {
        ListNode current = head;

        for (int i = 0; i < size; i++) {
            if (current.val.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    //@Override
    public Iterator<E> iterator() {
        return new CustomIterator();
    }

    //@Override
    public Object[] toArray() {
        Object[] arr = new Object[size];

        ListNode current = head;

        for (int i = 0; i < size; i++) {
            arr[i] = current;
            current = current.next;
        }

        return arr;
    }

    //@Override
    public <T> T[] toArray(T[] arr) {
        if (arr.length < this.size) {
            return (T[]) Arrays.copyOf(toArray(), this.size, arr.getClass());
        } else {
            System.arraycopy(toArray(), 0, arr, 0, this.size);
            if (arr.length > this.size) {
                arr[this.size] = null;
            }

            return arr;
        }
    }

    //@Override
    public boolean add(E val) {
        if (tail != null) {
            tail = tail.next = new ListNode(val);
        } else {
            tail = head = new ListNode(val);
        }

        size++;
        return true;
    }

    //@Override
    public boolean remove(Object obj) {
        if (head == null) {
            return false;
        }

        ListNode currentIn = head;

        int i = 0;

        for (; i < size; i++) {
            if (obj.equals(currentIn.val)) {
                break;
            }
            currentIn = currentIn.next;
        }

        if (currentIn == null) {
            return false;
        } else {
            deleteAtIndex(i);
            return true;
        }
    }

    //@Override
    public boolean addAll(Collection<? extends E> someCollection) {
        if (someCollection == null) {
            return false;
        }

        for (E i : someCollection) {
            add(i);
        }

        return true;
    }

    //@Override
    public boolean removeAll(Collection<?> someCollection) {
        boolean b = false;

        for (var i : someCollection) {
            b = remove(i);
        }

        return b;
    }

    //Override
    public boolean retainAll(Collection<?> someCollection) {
        boolean isRetain = false;
        ListNode current = head;
        //some = 3,2,1 this = 1.2,4,5

        for (int i = 0; i < size; i++) {
            for (var safe : someCollection) {
                if (current.val.equals(safe)) {
                    isRetain = true;
                    break;
                }
            }
            if (!isRetain) {
                if (i > 0 && i < size - 1) {
                    current.next = current.next.next;
                    size--;
                } else if (i == 0) {
                    deleteAtStart();
                } else if (i == size - 1) {
                    deleteAtTail();
                }
            }
            current = current.next;
            isRetain = false;
        }

        return isRetain;
    }

    //@Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    public void addAtHead(E val) {
        if (head != null) {
            head = new ListNode(val, head);
        } else {
            tail = head = new ListNode(val);
        }
        size++;
    }

    public void addAtIndex(int index, E val) {
        if (index > 0 && index < size) {
            ListNode current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }

            ListNode buffer = current.next;
            current = current.next = new ListNode(val);
            current.next = buffer;

            size++;
        } else if (index == size) {
            add(val);
        } else if (index == 0) {
            addAtHead(val);
        }
    }

    public void deleteAtIndex(int index) {
        if (index > 0 && index < size - 1) {
            ListNode current = head;

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
            ListNode current = head;

            while (current.next.next != null) {
                current = current.next;
            }

            current.next = null;

            tail = current;
            size--;
        } else if (size == 1) {
            clear();
            size = 0;
        }
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        ListNode current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.val;
    }

    private final class ListNode {
        ListNode next;
        E val;

        ListNode(E val) {
            this.val = val;
        }

        ListNode(E val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    private class CustomIterator implements Iterator<E> {
        private ListNode current;

        CustomIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E data = current.val;
            current = current.next;
            return data;
        }
    }
}
