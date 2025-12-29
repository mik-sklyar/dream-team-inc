package data;

import java.util.*;

@SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
public class CustomLinkedList<E> implements List<E> {
    private ListNode head;
    private ListNode tail;

    private int size;

    public CustomLinkedList() {}
    public CustomLinkedList(List<E> list) {
        if (list != null && !list.isEmpty()) {
            this.addAll(list);
        }
    }

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
    public boolean containsAll(Collection<?> collection) {
        if (collection.isEmpty()) {
            return false;
        }

        HashSet<Boolean> set = new HashSet<>();

        for (var i : collection) {
            set.add(contains(i));
        }

        return !set.contains(false);
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];

        ListNode current = head;

        for (int i = 0; i < size; i++) {
            arr[i] = current.val;
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
    public void add(int index, E val) {
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
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
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
    public boolean addAll(int index, Collection<? extends E> someCollection) {
        if (someCollection == null) {
            return false;
        }

        var arr = (E[]) someCollection.toArray();

        for (int j = arr.length - 1; j >= 0; j--) {
            add(index, arr[j]);
        }
        return true;
    }

    @Override
    public boolean remove(Object obj) {
        if (head == null) {
            return false;
        }

        ListNode current = head;

        int i = 0;

        for (; i < size; i++) {
            if (obj.equals(current.val)) {
                break;
            }
            current = current.next;
        }

        if (current == null) {
            return false;
        } else {
            deleteAtIndex(i);
            return true;
        }
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

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
    public E set(int i, E e) {
        if (i < 0 || i > size - 1) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
        }

        ListNode current = head;

        for (int j = 0; j < i; j++) {
            current = current.next;
        }

        E oldValue = current.val;
        current.val = e;

        return oldValue;
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
    public Iterator<E> iterator() {
        return new CustomIterator();
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
        if (start < 0) {
            throw new IndexOutOfBoundsException("fromIndex = " + start);
        }

        CustomLinkedList<E> list = new CustomLinkedList<>();

        ListNode current = head;

        for (int i = 0; i < start; i++) {
            current = current.next;
        }

        for (; start < end; start++) {
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
        Objects.requireNonNull(someCollection);
        boolean modified = false;
        Iterator<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            if (!someCollection.contains(iterator.next())) {
                iterator.remove();
                modified = true;
            }
        }

        return modified;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
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

    public void addAtHead(E val) {
        if (head != null) {
            head = new ListNode(val, head);
        } else {
            tail = head = new ListNode(val);
        }
        size++;
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
        int cursor;

        CustomIterator() {
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
                current = current.next;
                cursor++;
                return data;
            }
        }
    }

    private class CustomListIterator extends CustomLinkedList<E>.CustomIterator implements ListIterator<E> {
        CustomListIterator(int index) {
            super();

            cursor = index;

            for (int i = 0; i < cursor; i++) {
                current = current.next;
            }
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
            return CustomLinkedList.this.get(--cursor);
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
            CustomLinkedList.this.add(cursor, e);
        }

        @Override
        public void remove() {
            deleteAtIndex(cursor);
        }
    }
}
