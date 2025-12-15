import java.util.Stack;
import java.util.NoSuchElementException;

/**
 * Implements a FIFO Queue using two LIFO Stacks.
 * This is the 'Enqueue Friendly' version: enqueue is O(1) and dequeue is Amortized O(1), 
 * but worst-case O(N).
 */
public class EnqueueFriendlyStackQueue<T> {
    
    // Stack used for receiving new elements (Enqueue)
    private Stack<T> inStack; 
    
    // Stack used for delivering elements (Dequeue)
    private Stack<T> outStack; 

    // Constructor
    public EnqueueFriendlyStackQueue() {
        inStack = new Stack<>();
        outStack = new Stack<>();
    }

    /**
     * Adds an element to the rear of the queue. (O(1) complexity)
     * @param element The element to be enqueued.
     */
    public void enqueue(T element) {
        // Simple push to the inStack is a fast O(1) operation.
        inStack.push(element);
        System.out.println("Enqueued: " + element + " (O(1))");
    }

    /**
     * Removes and returns the element from the front of the queue.
     * Worst-Case: O(N) when the transfer is needed.
     * Amortized: O(1) because each element is moved only twice (once in, once out).
     * @return The element at the front (the oldest element).
     * @throws NoSuchElementException if the queue is empty.
     */
    public T dequeue() {
        // If both stacks are empty, the queue is empty.
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty. Cannot perform dequeue.");
        }
        
        // Check if outStack is empty. If it is, we need to transfer elements.
        if (outStack.isEmpty()) {
            // Transfer all elements from inStack to outStack. (O(N) operation)
            // This is the step that reverses the order, turning LIFO (inStack) into FIFO (outStack).
            transferStacks();
        }
        
        // Now that the outStack has the FIFO element at the top, pop it. (O(1) operation)
        T frontElement = outStack.pop();
        System.out.println("Dequeued: " + frontElement);
        return frontElement;
    }

    /**
     * Private helper method to move all elements from inStack to outStack.
     */
    private void transferStacks() {
        System.out.println(">>> Transferring elements from inStack to outStack...");
        while (!inStack.isEmpty()) {
            outStack.push(inStack.pop());
        }
    }

    /**
     * Returns the element at the front of the queue without removing it.
     * @return The element at the front.
     * @throws NoSuchElementException if the queue is empty.
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty. Cannot perform peek.");
        }
        
        // Ensure outStack is loaded for FIFO access
        if (outStack.isEmpty()) {
            transferStacks();
        }
        
        return outStack.peek();
    }

    /**
     * Checks if the queue is empty.
     * @return true if both stacks are empty, false otherwise.
     */
    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }
    
    // --- Main method for demonstration ---
    public static void main(String[] args) {
        EnqueueFriendlyStackQueue<String> queue = new EnqueueFriendlyStackQueue<>();

        System.out.println("--- Enqueue Operations (O(1)) ---");
        queue.enqueue("A"); // InStack: [A]
        queue.enqueue("B"); // InStack: [A, B]
        queue.enqueue("C"); // InStack: [A, B, C]

        System.out.println("\n--- Dequeue Operations (Amortized O(1)) ---");
        // 1. Dequeue A: outStack is empty. Transfer (C, B, A). OutStack: [A, B, C]. Pop A.
        queue.dequeue(); 
        
        // 2. Dequeue B: outStack is NOT empty. Pop B. (O(1))
        queue.dequeue(); 
        
        queue.enqueue("D"); // InStack: [D]
        
        // 3. Dequeue C: outStack is NOT empty. Pop C. (O(1))
        queue.dequeue(); 

        // 4. Dequeue D: outStack is empty. Transfer (D). OutStack: [D]. Pop D.
        queue.dequeue(); 

        System.out.println("\nIs queue empty? " + queue.isEmpty()); 
    }
}