import java.util.Stack;

public class StackQueue<T> {
    private Stack<T> stackIn = new Stack<>();
    private Stack<T> stackOut = new Stack<>();

    public void enqueue(T data) {
        stackIn.push(data);
    }

    public T dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        
        // Only move elements if the output stack is empty
        if (stackOut.isEmpty()) {
            while (!stackIn.isEmpty()) {
                stackOut.push(stackIn.pop());
            }
        }
        return stackOut.pop();
    }

    public boolean isEmpty() {
        return stackIn.isEmpty() && stackOut.isEmpty();
    }
}