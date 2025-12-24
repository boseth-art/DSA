public class ArrayCircularQueue {
    private int[] queue;
    private int front, rear, size, capacity;

    public ArrayCircularQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new int[capacity];
        this.front = 0;
        this.size = 0;
        this.rear = -1;
    }

    public void enqueue(int item) {
        if (size == capacity) return; // Full
        rear = (rear + 1) % capacity;
        queue[rear] = item;
        size++;
    }

    public int dequeue() {
        if (size == 0) return -1; // Empty
        int item = queue[front];
        front = (front + 1) % capacity;
        size--;
        return item;
    }
}