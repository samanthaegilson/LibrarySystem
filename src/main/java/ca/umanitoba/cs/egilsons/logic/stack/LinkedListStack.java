package ca.umanitoba.cs.egilsons.logic.stack;

public class LinkedListStack<T> implements Stack<T> {
    private Node<T> top;
    private int nodeCount;

    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    public LinkedListStack() {
        this.top = null;
        this.nodeCount = 0;
    }

    public void push(T item) {
        this.top = new Node<T>(item, this.top);
        nodeCount++;
    }

    public T pop() throws EmptyStackException {
        Node<T> removed = this.top;
        if (!isEmpty()) {
            this.top = this.top.next;
            removed.next = null;
            nodeCount--;
        } else {
            throw new EmptyStackException("Empty Stack"); // Should be no text in logic layer???
        }
        return removed.data;
    }

    public int size() {
        return this.nodeCount;
    }

    public boolean isEmpty() {
        boolean empty = false;
        if (nodeCount == 0) {
            empty = true;
        }
        return empty;
    }

    public T peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("Empty Stack"); // Should be no text in logic layer???
        }
        return this.top.data;
    }
}
