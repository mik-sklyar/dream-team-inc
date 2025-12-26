package data;

import java.util.*;
import java.util.stream.Collectors;

class CustomLinkedList<E> implements List<E> {
    private ListNode head;
    private ListNode tail;

    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
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

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];

        ListNode current = head;

        for (int i = 0; i < size; i++) {
            arr[i] = current;
            current = current.next;
        }

        return arr;
    }

    @Override
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

    @Override
    public boolean add(E val) {
        if (tail != null) {
            tail = tail.next = new ListNode(val);
        } else {
            tail = head = new ListNode(val);
        }

        size++;
        return true;
    }

    @Override
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

    @Override
    public boolean containsAll(Collection<?> collection) {
        if (collection == null) {return false;}

        HashMap<Object, Integer> map = new HashMap<>();
        for (var i : collection) {
            map.put(i, 0);
        }

        ListNode current = head;
        for (int i = 0; i < size; i++) {
            if (map.containsKey(current.val)) {
                map.put(current.val, map.get(current.val) + 1);
            }
            current = current.next;
        }

        HashSet<Integer> set = new HashSet<>(map.values());

        return !set.contains(0);
    }

    @Override
    public boolean addAll(Collection<? extends E> someCollection) {
        if (someCollection == null) {
            return false;
        }

        for (E i : someCollection) {
            add(i);
        }

        return true;
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> someCollection) {
        if (someCollection == null || i < 0 || i >= size) {return false;}

        ListNode current = head;

        for (int j = 0; j < i; j++) {
            current = current.next;
        }

        ListNode afterShiftNode = current;

        for (var j : someCollection) {
            current.next = new ListNode(j);
            current = current.next;
        }

        current.next = afterShiftNode;

        return true;
    }

    @Override
    public E set(int i, E e) {
        if (i < 0 || i >= size) {return null;}

        ListNode current = head;

        for (int j = 0; j < i; j++) {
            current = current.next;
        }

        E oldValue = current.val;
        current.val = e;

        return oldValue;
    }

    @Override
    public void add(int i, E e) {
        addAtIndex(i, e);
    }

    @Override
    public E remove(int index) {
        E old = null;
        if (index > 0 && index < size - 1) {
            ListNode current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }

            old = current.next.val;

            current.next = current.next.next;
            size--;
        } else if (index == 0) {
            if (size == 1) {
                old = head.val;
                clear();
            } else if (head != null) {
                old = head.val;
                head = head.next;
                size--;
            }
        } else if (index == size - 1) {
            if (size > 1) {
                ListNode current = head;

                while (current.next.next != null) {
                    current = current.next;
                }

                old = current.next.val;
                current.next = null;

                tail = current;
                size--;
            } else if (size == 1) {
                old = head.val;
                clear();
                size = 0;
            }
        }
        return old;
    }

    @Override
    public int indexOf(Object obj) {
        ListNode current = head;

        for (int i = 0; i < size; i++) {
            if (obj.equals(current.val)) {
                return i;
            }
            current = current.next;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object obj) {
        ListNode current = head;
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (obj.equals(current.val)) {
                index = i;
            }
            current = current.next;
        }

        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CustomListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        return new CustomListIterator(i);
    }

    @Override
    public List<E> subList(int start, int end) {
        CustomLinkedList<E> list = new CustomLinkedList<>();

        ListNode current = head;

        for (int i = 0; i < start; i++) {
            current = current.next;
        }

        for (;start <= end; start++) {
            list.add(current.val);
            current = current.next;
        }

        return list;
    }


    @Override
    public boolean removeAll(Collection<?> someCollection) {
        boolean b = false;

        for (var i : someCollection) {
            b = remove(i);
        }

        return b;
    }

    @Override
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

    @Override
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

    @Override
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
        ListNode current;
        ListNode previous;

        int cursor;

        CustomIterator() {
            cursor = 0;
            previous = null;
            current = head;
        }

        @Override
        public boolean hasNext() {
            return this.cursor != size;
        }

        @Override
        public E next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            } else {
                E data = current.val;
                previous = current;
                current = current.next;
                cursor++;
                return data;
            }
        }
    }

    private class CustomListIterator extends CustomLinkedList<E>.CustomIterator implements ListIterator<E> {

        CustomListIterator(int index) {
            if (index < 0 || index > size()) {
                throw new IndexOutOfBoundsException();
            }
            this.cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return this.cursor != 0;
        }

        @Override
        public int nextIndex() {
            return this.cursor;
        }

        @Override
        public int previousIndex() {
            return this.cursor - 1;
        }

        @Override
        public E previous() {
            return previous.val;
        }

        @Override
        public void set(E e) {
            ListNode current = head;

            for (int j = 0; j < cursor; j++) {
                current = current.next;
            }

            current.val = e;
        }

        @Override
        public void add(E e) {
            addAtIndex(cursor, e);
        }

        @Override
        public void remove() {
            deleteAtIndex(cursor);
        }
    }
}
