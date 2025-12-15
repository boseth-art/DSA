import java.util.LinkedList;
import java.util.Queue;
import java.util.NoSuchElementException;

/**
 * Implements a LIFO Stack using two FIFO Queues.
 * This is the 'Pop Friendly' version, where pop is O(1) and push is O(N).
 */
public class PopFriendlyQueueStack<T> {
    
    // Main queue (q1) holds the current stack elements. 
    // The top element of the stack is always at the front of q1.
    private Queue<T> q1; 
    
    // Temporary queue (q2) is used only during the O(N) push operation.
    private Queue<T> q2; 

    // Constructor initializes the two queues.
    public PopFriendlyQueueStack() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }

    /**
     * Pushes an item onto the top of the stack. (O(N) complexity)
     * To enforce LIFO order, the new element must be placed at the front of q1.
     * @param data The element to be pushed (new top element).
     */
    public void push(T data) {
        
        // 1. Enqueue the new element into the temporary queue (q2).
        q2.offer(data);

        // 2. Transfer ALL elements from q1 to q2.
        // This ensures the new element (in q2's front) will be followed by all old elements.
        while (!q1.isEmpty()) {
            q2.offer(q1.poll());
        }

        // 3. Swap the names of q1 and q2. 
        // The temporary queue (q2), which now contains the new element at the front 
        // followed by all old elements, becomes the new main queue (q1).
        Queue<T> temp = q1;
        q1 = q2;
        q2 = temp;
        
        System.out.println("Pushed: " + data + " (O(N))");
    }

    /**
     * Removes and returns the item at the top of the stack. (O(1) complexity)
     * Since push() ensures the newest element is at the front of q1, 
     * a simple dequeue operation is enough.
     * @return The element at the top (the newest element).
     * @throws NoSuchElementException if the stack is empty.
     */
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty. Cannot perform pop.");
        }
        
        // Simple dequeue is an O(1) operation, as the LIFO element is at the front.
        T topElement = q1.poll();
        System.out.println("Popped: " + topElement + " (O(1))");
        return topElement;
    }

    /**
     * Returns the element at the top of the stack without removing it. (O(1) complexity)
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty. Cannot perform peek.");
        }
        return q1.peek();
    }

    /**
     * Checks if the stack is empty.
     */
    public boolean isEmpty() {
        return q1.isEmpty();
    }

    // --- Main method for demonstration ---
    public static void main(String[] args) {
        PopFriendlyQueueStack<Integer> stack = new PopFriendlyQueueStack<>();

        System.out.println("--- Push Operations (O(N)) ---");
        // Push 10: q1=[10]
        stack.push(10); 
        // Push 20: q2=[20], q1=[10] moves to q2, q2=[20, 10]. Swap. q1=[20, 10]
        stack.push(20); 
        // Push 30: q2=[30], q1=[20, 10] moves to q2, q2=[30, 20, 10]. Swap. q1=[30, 20, 10]
        stack.push(30); 
        // Current logical stack: [10 (bottom), 20, 30 (top)]
        // Current internal q1: [30 (front - top), 20, 10 (rear - bottom)]

        System.out.println("\n--- Pop Operations (O(1)) ---");
        // Pop 30 (O(1))
        stack.pop(); 
        
        // Push 40 (O(N))
        stack.push(40); 
        // Current internal q1: [40 (front - top), 20, 10 (rear - bottom)]

        // Pop 40 (O(1))
        stack.pop(); 
        
        System.out.println("\nIs stack empty? " + stack.isEmpty());

        // Pop 20
        stack.pop();

        System.out.println("Is stack empty? " + stack.isEmpty());
        
        // Pop 10
        stack.pop();

        System.out.println("Is stack empty? " + stack.isEmpty());
    }
}