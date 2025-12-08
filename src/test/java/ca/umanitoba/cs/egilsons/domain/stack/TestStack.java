package ca.umanitoba.cs.egilsons.domain.stack;

import ca.umanitoba.cs.comp2450.stack.Stack;
import ca.umanitoba.cs.comp2450.stack.impl.*;
import ca.umanitoba.cs.egilsons.tests.TestResults;

public class TestStack {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        System.out.println("push(): ");
        testEmptyPush();
        testOneItemPush();

        System.out.println("isEmpty(): ");
        testEmptyIsEmpty();
        testOneItemIsEmpty();
        testTwoItemIsEmpty();

        System.out.println("peek(): ");
        testEmptyPeek();
        testOneItemPeek();
        testTwoItemPeek();

        System.out.println("pop(): ");
        testEmptyPop();
        testOneItemPop();
        testTwoItemPop();

        System.out.println("size(): ");
        testEmptySize();
        testOneItemSize();
        testTwoItemSize();

        return new TestResults(successes, failures);
    }

    public Stack<String> getInstance() {
        return new LinkedListStack<>();
        //return new BadStack1<>();
        //return new BadStack2<>();
        //return new BadStack3<>();
        // return new BadStack4<>();
        //return new BadStack5<>();
    }

    private void testEmptyPush() {
        Stack<String> stack = getInstance();
        try {
            stack.push("hello");

            if (!stack.isEmpty()) {
                int size = stack.size();
                if (size == 1) {
                    String top = stack.peek();
                    if (top.equals("hello")) {
                        String removed = stack.pop();
                        if (removed.equals("hello")) {
                            pass("hello pushed successfully");
                        } else {
                            fail("Stack top is not what was expected, got " + top + " expected hello.");
                        }
                    } else {
                        fail("Stack top is not what was expected, got " + top + " expected hello.");
                    }
                } else {
                    fail("Stack size is not what was expected, got " + size + " expected 1.");
                }
            } else {
                fail("Empty stack should not be empty after push.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testOneItemPush() {
        Stack<String> stack = getInstance();
        try {
            stack.push("hello");
            stack.push("world");

            if (!stack.isEmpty()) {
                int size = stack.size();
                if (size == 2) {
                    String top = stack.peek();
                    if (top.equals("world")) {
                        String removed = stack.pop();
                        if (removed.equals("world")) {
                            pass("world pushed successfully");
                        } else {
                            fail("Stack top is not what was expected, got " + top + " expected world.");
                        }
                    } else {
                        fail("Stack top is not what was expected, got " + top + " expected world.");
                    }
                } else {
                    fail("Stack size is not what was expected, got " + size + " expected 2.");
                }
            } else {
                fail("Stack with one item should not be empty after push.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testEmptyIsEmpty() {
        Stack<String> stack = getInstance();
        try {
            if (stack.isEmpty()) {
                pass("Stack is correctly empty");
            } else {
                fail("Empty stack should be empty");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testOneItemIsEmpty() {
        Stack<String> stack = getInstance();
        try {
            stack.push("hello");

            if (!stack.isEmpty()) {
                pass("One item stack is correctly not empty.");
            } else {
                fail("Stack with one item should not be empty.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testTwoItemIsEmpty() {
        Stack<String> stack = getInstance();
        try {
            stack.push("hello");
            stack.push("world");

            if (!stack.isEmpty()) {
                pass("Two item stack is correctly not empty.");
            } else {
                fail("Stack with two items should not be empty.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testEmptyPeek() {
        Stack<String> stack = getInstance();
        try {
            String top = stack.peek();
            fail("Peek should have thrown exception, returned " + top);
        } catch (Stack.EmptyStackException e) {
            pass("EmptyStackException successfully thrown after peek.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testOneItemPeek() {
        Stack<String> stack = getInstance();
        try {
            stack.push("hello");

            String top = stack.peek();
            if (top.equals("hello")) {
                pass("hello was the top of stack.");
            } else {
                fail("Peek did not return what was expected, got " + top + " expected hello.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testTwoItemPeek() {
        Stack<String> stack = getInstance();
        try {
            stack.push("hello");
            stack.push("world");

            String top = stack.peek();
            if (top.equals("world")) {
                pass("world was the top of stack.");
            } else {
                fail("Peek did not return what was expected, got " + top + " expected world.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown");
            e.printStackTrace();
        }
    }

    private void testEmptyPop() {
        Stack<String> stack = getInstance();
        try {
            String top = stack.pop();
            fail("Pop should have thrown exception, returned " + top);
        } catch (Stack.EmptyStackException e) {
            pass("EmptyStackException successfully thrown.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testOneItemPop() {
        Stack<String> stack = getInstance();
        try {
            stack.push("hello");

            String top = stack.pop();
            if (top.equals("hello")) {
                int size = stack.size();
                if (size == 0) {
                    if (stack.isEmpty()) {
                        pass("hello was removed from the top of stack.");
                    } else {
                        fail("Stack size should be empty after removing only item.");
                    }
                } else {
                    fail("Stack size is not what was expected, got " + size + " expected 0.");
                }
            } else {
                fail("Pop did not return what was expected, got " + top + " expected hello.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testTwoItemPop() {
        Stack<String> stack = getInstance();
        try {
            stack.push("hello");
            stack.push("world");

            String top = stack.pop();
            if (top.equals("world")) {
                if (!stack.isEmpty()) {
                    String nextTop = stack.peek();
                    if (nextTop.equals("hello")) {
                        int size = stack.size();
                        if (size == 1) {
                            pass("world was removed from the top of stack.");
                        } else {
                            fail("Stack size is not what was expected, got " + size + " expected 1.");
                        }
                    } else {
                        fail("Next item in stack is not what was expected, got " + nextTop + " expected hello.");
                    }
                } else {
                    fail("Stack should not be empty. There should still be one item");
                }
            } else {
                fail("Pop did not return what was expected, got " + top + " expected world.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testEmptySize() {
        Stack<String> stack = getInstance();
        try {
            int size = stack.size();
            if (size == 0) {
                pass("Stack successfully has a size of 0.");
            } else {
                fail("Stack is not what was expected, got " + size + " expected 0.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testOneItemSize() {
        Stack<String> stack = getInstance();
        try {
            stack.push("hello");

            int size = stack.size();
            if (size == 1) {
                pass("Stack successfully has a size of 1.");
            } else {
                fail("Stack size is not what was expected, got " + size + " expected 1.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testTwoItemSize() {
        Stack<String> stack = getInstance();
        try {
            stack.push("hello");
            stack.push("world");

            int size = stack.size();
            if (size == 2) {
                pass("Stack successfully has a size of 2.");
            } else {
                fail("Stack size is not what was expected, got " + size + " expected 2.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void pass(String message) {
        successes++;
        System.out.println("PASS: " + message);
    }

    private void fail(String message) {
        failures++;
        System.out.println("FAIL: " + message);
    }
}
