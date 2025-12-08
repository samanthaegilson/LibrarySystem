package ca.umanitoba.cs.egilsons.domain.stack;

import ca.umanitoba.cs.comp2450.stack.Stack;
import com.google.common.base.Preconditions;

/**
 * A linked list stack to help {@link ca.umanitoba.cs.egilsons.logic.FindItem} with backtracking.
 *
 * @param <T> the type of stack
 */
public class LinkedListStack<T> implements Stack<T> {
    private Node<T> top;
    private int nodeCount;

    /**
     * An inner node class
     *
     * @param <T> the type of node
     */
    private static class Node<T> {
        private T data;
        private Node<T> next;

        /**
         * A constructor for Node. Receives the data and the next node
         *
         * @param data the data the node contains
         * @param next the next node in the list
         */
        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    private void checkLinkedListStack() {
        Preconditions.checkState(nodeCount >= 0, "Node count should never be below 0.");
    }

    /**
     * A constructor for LinkedListStack. Creates an empty stack
     */
    public LinkedListStack() {
        this.top = null;
        this.nodeCount = 0;
        checkLinkedListStack();
    }

    /**
     * Adds an item to the top of the stack
     *
     * @param item the item to add
     */
    public void push(T item) {
        checkLinkedListStack();
        this.top = new Node<T>(item, this.top);
        nodeCount++;
        checkLinkedListStack();
    }

    /**
     * Removes an item from the top of the stack
     *
     * @return the removed item
     * @throws EmptyStackException if the stack is empty
     */
    public T pop() throws EmptyStackException {
        checkLinkedListStack();
        Node<T> removed = this.top;
        if (!isEmpty()) {
            this.top = this.top.next;
            removed.next = null;
            nodeCount--;
        } else {
            throw new EmptyStackException("Empty Stack");
        }
        checkLinkedListStack();
        return removed.data;
    }

    /**
     * Gets the size of the stack
     *
     * @return the size of the stack
     */
    public int size() {
        checkLinkedListStack();
        return this.nodeCount;
    }

    /**
     * Checks if the stack is empty
     *
     * @return if the stack is empty or not
     */
    public boolean isEmpty() {
        checkLinkedListStack();
        boolean empty = false;
        if (nodeCount == 0) {
            empty = true;
        }
        checkLinkedListStack();
        return empty;
    }

    /**
     * Looks at the top item of the stack
     *
     * @return the top item of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public T peek() throws EmptyStackException {
        checkLinkedListStack();
        if (isEmpty()) {
            throw new EmptyStackException("Empty Stack");
        }
        checkLinkedListStack();
        return this.top.data;
    }
}
