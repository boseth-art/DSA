public class DoublyLinkedList<T> {
    private class Node {
        T data;
        Node next, prev;
        Node(T data) { this.data = data; }
    }

    private Node head, tail;

    public void addLast(T data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public void removeLast() {
        if (tail == null) return;
        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
    }
}