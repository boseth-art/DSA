import java.util.LinkedList;
import java.util.Queue;
import java.util.NoSuchElementException;

/**
 * Implements a LIFO Stack using two FIFO Queues.
 * This is the 'Push Friendly' version, where push is O(1) and pop is O(N).
 */
public class PushFriendlyQueueStack<T> {
    
    // Main queue used to store elements. New elements are always enqueued here.
    private Queue<T> q1; 
    
    // Temporary queue used only during the O(N) pop operation.
    private Queue<T> q2; 

    // Constructor initializes the two queues using LinkedLists (a common Queue implementation).
    public PushFriendlyQueueStack() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }

    /**
     * Pushes an item onto the top of the stack. (O(1) complexity)
     * In the push-friendly model, we simply enqueue the new element to q1.
     * The LIFO order is NOT maintained here, it's enforced during pop.
     * @param data The element to be pushed.
     */
    public void push(T data) {
        // Simple enqueue is an O(1) operation.
        q1.offer(data);
        System.out.println("Pushed: " + data + " (O(1))");
    }

    /**
     * Removes and returns the item at the top of the stack. (O(N) complexity)
     * To enforce LIFO, all elements except the last one must be moved to q2.
     * @return The element at the top (the newest element).
     * @throws NoSuchElementException if the stack is empty.
     */
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty. Cannot perform pop.");
        }

        // 1. Move (N-1) elements from q1 to q2.
        // The loop stops when only the newest element (the one to be popped) remains in q1.
        while (q1.size() > 1) {
            q2.offer(q1.poll());
        }

        // 2. Dequeue and store the newest element (the LIFO element) from q1.
        T topElement = q1.poll();

        // 3. Swap the names of q1 and q2. This makes the former temporary queue (q2)
        //    the new main queue (q1) for subsequent pushes.
        Queue<T> temp = q1;
        q1 = q2;
        q2 = temp;
        
        System.out.println("Popped: " + topElement + " (O(N))");
        return topElement;
    }

    /**
     * Returns the element at the top of the stack without removing it.
     * Uses the O(N) pop logic but only peeks at the value.
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty. Cannot perform peek.");
        }
        
        // Use the same transfer logic as pop to find the last element.
        while (q1.size() > 1) {
            q2.offer(q1.poll());
        }
        
        T topElement = q1.peek(); // Use peek() instead of poll() to keep it in q1 for now
        
        // Move the element back to q2 before swapping
        q2.offer(q1.poll()); 

        // Swap the names of q1 and q2
        Queue<T> temp = q1;
        q1 = q2;
        q2 = temp;

        return topElement;
    }

    /**
     * Checks if the stack is empty.
     * @return true if the main queue (q1) is empty, false otherwise.
     */
    public boolean isEmpty() {
        return q1.isEmpty();
    }

    // --- Main method for demonstration ---
    public static void main(String[] args) {
        PushFriendlyQueueStack<Integer> stack = new PushFriendlyQueueStack<>();

        System.out.println("--- Push Operations (O(1)) ---");
        stack.push(10); // 10 is the newest, should be popped first
        stack.push(20); 
        stack.push(30); 
        // Current logical stack: [10 (bottom), 20, 30 (top)]
        // Current internal q1: [10 (front), 20, 30 (rear)]

        System.out.println("\n--- Pop Operations (O(N)) ---");
        // Pop 30: 10 and 20 move to q2, 30 is polled from q1. q1 and q2 swap.
        stack.pop(); 
        
        stack.push(40); // 40 is enqueued to the new q1 (which held 10, 20 before swap)
        // Current logical stack: [10 (bottom), 20, 40 (top)]
        // Current internal q1: [10 (front), 20, 40 (rear)]

        // Pop 40: 10 and 20 move to q2, 40 is polled from q1. q1 and q2 swap.
        stack.pop(); 

        System.out.println("\nIs stack empty? " + stack.isEmpty());

        // Pop 20
        stack.pop();

        System.out.println("\nIs stack empty? " + stack.isEmpty());
        
        // Pop 10
        stack.pop();

        System.out.println("\nIs stack empty? " + stack.isEmpty());
        
        // stack.pop(); // Uncommenting this will throw NoSuchElementException
    }
}