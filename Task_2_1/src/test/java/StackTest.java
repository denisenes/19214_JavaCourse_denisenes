import junit.framework.TestCase;

public class StackTest extends TestCase {

    public void test() {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(0);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        checkIntegerStack(stack, new Integer[] {0, 1, 2, 3});

        stack.pop();
        Integer t = stack.pop();
        assertEquals(t, (Integer)2);

        checkCount(stack, 2);

        stack.pop();
        stack.pop();
        checkCount(stack, 0);

        //if we pop() from empty stack then nothing happens
        stack.pop();
        checkCount(stack, 0);

        Stack<String> strStack = new Stack<String>();
        strStack.push("I");
        strStack.push("am");
        strStack.push("not");
        strStack.push("a");
        strStack.push("robot");

        checkCount(strStack, 5);
        checkStringStack(strStack, new String[] {"I", "am", "not", "a", "robot"});
        String ts = strStack.pop();
        assertEquals(ts, "robot");
        ts = strStack.pop();
        assertEquals(ts, "a");
        checkStringStack(strStack, new String[] {"I", "am", "not"});

    }

    private void checkIntegerStack(Stack<Integer> stack, Integer[] testArr) {
        int i = 0;
        for (Integer it : stack) {
            assertEquals(it, testArr[i]);
            i++;
        }
    }

    private void checkCount(Stack stack, int c) {
        assertEquals(stack.count(), c);
    }

    private void checkStringStack(Stack<String> stack, String[] testArr) {
        int i = 0;
        for (String it : stack) {
            assertEquals(it, testArr[i]);
            i++;
        }
    }

}