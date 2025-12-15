import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Implements a LIFO Stack using an array that automatically resizes
 * (doubles its capacity) when the stack becomes full.
 */
public class UnboundedArrayStack<T> {

    private static final int INITIAL_CAPACITY = 10;
    private T[] stackArray;
    private int top; // Index of the next available slot, or the size of the stack

    /**
     * Constructor: Initializes the stack with a default capacity.
     */
    @SuppressWarnings("unchecked")
    public UnboundedArrayStack() {
        // Java does not allow direct creation of generic arrays, so we cast.
        stackArray = (T[]) new Object[INITIAL_CAPACITY];
        top = 0; // Stack is initially empty
    }

    /**
     * Adds an element to the top of the stack (LIFO).
     * If the array is full, it calls the resize method.
     * @param element The element to be pushed.
     */
    public void push(T element) {
        // Check if the array is full (top == stackArray.length)
        if (top == stackArray.length) {
            resize(2 * stackArray.length); // Double the capacity
        }
        stackArray[top] = element;
        top++;
        System.out.println("Pushed: " + element + " (Capacity: " + stackArray.length + ")");
    }

    /**
     * Removes and returns the element at the top of the stack (LIFO).
     * @return The element at the top.
     * @throws NoSuchElementException if the stack is empty.
     */
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty. Cannot perform pop.");
        }
        top--;
        T element = stackArray[top];
        stackArray[top] = null; // Help with garbage collection (optional but good practice)
        
        // Optional: Shrink the array if it becomes too sparse (e.g., less than 1/4 full)
        if (top > 0 && top == stackArray.length / 4) {
            resize(stackArray.length / 2);
        }
        
        System.out.println("Popped: " + element + " (Current Size: " + top + ")");
        return element;
    }

    /**
     * Returns the element at the top of the stack without removing it.
     * @return The element at the top.
     * @throws NoSuchElementException if the stack is empty.
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty. Cannot perform peek.");
        }
        return stackArray[top - 1];
    }

    /**
     * Resizes the internal array to the new capacity.
     * This is the mechanism that makes the array "unbounded."
     * @param newCapacity The desired new size for the array.
     */
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        System.out.println(">>> Resizing from " + stackArray.length + " to " + newCapacity);
        // Create a new array of the desired size
        T[] newArray = (T[]) new Object[newCapacity];
        
        // Copy all elements from the old array to the new one
        for (int i = 0; i < top; i++) {
            newArray[i] = stackArray[i];
        }
        
        // Replace the old array reference with the new one
        stackArray = newArray;
    }

    /**
     * Checks if the stack is empty.
     * @return true if the stack contains no elements, false otherwise.
     */
    public boolean isEmpty() {
        return top == 0;
    }

    /**
     * Returns the number of elements in the stack.
     * @return The current size of the stack.
     */
    public int size() {
        return top;
    }
    
    // --- Main method for demonstration ---
    public static void main(String[] args) {
        UnboundedArrayStack<String> stack = new UnboundedArrayStack<>();
        
        System.out.println("--- Pushing Elements (Initial Capacity: " + INITIAL_CAPACITY + ") ---");
        // Push 11 elements to force a resize
        for (int i = 1; i <= 11; i++) {
            stack.push("Item " + i);
        }

        System.out.println("\nTop element (Peek): " + stack.peek());
        System.out.println("Current Stack Size: " + stack.size());

        System.out.println("\n--- Popping Elements ---");
        // Pop several elements, forcing a shrink (if enabled)
        for (int i = 0; i < 9; i++) {
            stack.pop();
        }
        
        System.out.println("\nFinal Stack Size: " + stack.size());
        System.out.println("Is stack empty? " + stack.isEmpty());
    }
}