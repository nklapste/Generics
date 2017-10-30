import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lab 4: Generics <br />
 * The {@code GenericStack} class
 */
public class GenericStack<T> {
    private List<T> stack =  new ArrayList<>();

    /**
     * GenericStack constructor
     */
    public GenericStack(){

    }

    /**
     * Query the top element
     * @return          {@code T} the top element
     */
    public T peek() {
        // TODO: Lab 4 Part 1-1 -- GenericStack, finish the peek method
        // if the stack is empty do nothing
        if(this.stack.isEmpty()){
            return null;
            // query first element of stack
        } else {
            //TODO query first element of stack
            return this.stack.get(this.stack.size()-1);
        }
    }

    /**
     * Add a new element as top element
     * @param value     {@code T} the new element
     */
    public void push(T value) {
        // TODO: Lab 4 Part 1-2 -- GenericStack, finish the push method
        this.stack.add(value);
    }

    /**
     * Remove the top element
     * @return          {@code T} the removed element
     */
    public T pop() {
        // TODO: Lab 4 Part 1-3 -- GenericStack, finish the pop method
        if(this.stack.isEmpty()){
            return null;
        } else {
            return this.stack.remove(this.stack.size()-1);
        }
    }

    /**
     * Query the size of the stack
     * @return          {@code int} size of the element
     */
    public int size() {
        // TODO: Lab 4 Part 1-4 -- GenericStack, finish the size method
        return this.stack.size();
    }

    /**
     * Check if the stack is empty of not
     * @return          {@code boolean} {@code true} for empty; {@code false} for not
     */
    public boolean isEmpty() {
        // TODO: Lab 4 Part 1-5 -- GenericStack, finish the isEmpty method
        return this.stack.size() == 0;
    }

    /**
     * Calculate a postfix expression
     * @param exp       {@code String} the postfix expression
     * @return          {@code Double} the value of the expression
     */
    public static Double calcPostfixExpression(String exp) {
        // TODO: Lab 4 Part 1-6 -- GenericStack, calculate postfix expression
        // parse exp string in to a list of expression symbols
        List<String> expList = new ArrayList<String>(Arrays.asList(exp.split(" ")));
        // init generic stack
        GenericStack<Double> calcStack = new GenericStack<Double>();
        for (String expSym : expList){
            if(expSym.matches("-?\\d+(\\.\\d+)?")){
                calcStack.push(Double.parseDouble(expSym));
            } else if (expSym.matches("\\+|\\-|\\*|\\/|\\^")){
                calcStack.push(calcOperator(calcStack, expSym));
            } else {
                //TODO throw error
            }
        }

        // pop result off
        return calcStack.pop();

    }

    private static Double calcOperator(GenericStack<Double> calcStack, String operator) {
        if(calcStack.size()<2){
            //TODO throw error
        }
        Double num2 = calcStack.pop();
        Double num1 = calcStack.pop();
        Double result = null;
        switch (operator){
            case "+": result = num1 + num2; break;
            case "-": result = num1 - num2; break;
            case "*": result = num1 * num2; break;
            case "/": result = num1 / num2; break;
            case "^": result = Math.pow(num1, num2); break;
            default: break; //TODO throw error
        }
        return result;
    }

    /**
     * Main entry
     * @param args      {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
        String[] expressions = {
                "4 1 +",                    // 1: = 5
                "2 6 -",                    // 2: = -4
                "3 3 *",                    // 3: = 9
                "1 4 /",                    // 4: = 0.25
                "2 3 ^",                    // 5: = 8
                "2 1 + 4 * 8 - 3 ^ 6 -",    // 6: 58
        }; // String[] expressions = { ... };
        for (String s: expressions)
            System.out.println(s + " = " + calcPostfixExpression(s));
    }

}
