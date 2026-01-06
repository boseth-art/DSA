class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class LinkedListQueue {
    private Node front, rear;

    public LinkedListQueue() {
        this.front = this.rear = null;
    }

    // Enqueue: Add an element to the end of the queue
    public void enqueue(int data) {
        Node newNode = new Node(data);

        // If queue is empty, new node is both front and rear
        if (this.rear == null) {
            this.front = this.rear = newNode;
            System.out.println(data + " added to queue");
            return;
        }

        // Add the new node at the end and update rear
        this.rear.next = newNode;
        this.rear = newNode;
        System.out.println(data + " added to queue");
    }

    // Dequeue: Remove the front element
    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue Underflow");
            return -1;
        }

        int value = this.front.data;
        this.front = this.front.next;

        // If front becomes null, update rear to null as well
        if (this.front == null) {
            this.rear = null;
        }
        return value;
    }

    // Peek: Get the front element without removing it
    public int peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return -1;
        }
        return this.front.data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public static void main(String[] args) {
        LinkedListQueue queue = new LinkedListQueue();

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        System.out.println("Front element: " + queue.peek());
        System.out.println("Dequeued: " + queue.dequeue());
        System.out.println("Dequeued: " + queue.dequeue());
        
        System.out.println("Is queue empty? " + queue.isEmpty());
    }
}