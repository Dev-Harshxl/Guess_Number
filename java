import java.util.Stack;
import java.util.regex.Pattern;

public class ExpressionEvaluator {

    // Function to validate the expression
    public static boolean isValidExpression(String expression) {
        // Regex for consecutive operators, starting/ending operators, and invalid cases
        String consecutiveOperators = ".*[+*/]{2,}.*|.*[+*/]-[+*/].*|.*[+*/]-{2,}.*";
        String startOperator = "^[+*/].*";
        String endOperator = ".*[+\\-*/]$";
        String validCharacters = "[0-9+\\-*/().]*";

        // Check for invalid characters
        if (!Pattern.matches(validCharacters, expression)) {
            return false;
        }

        // Check for consecutive operators
        if (Pattern.matches(consecutiveOperators, expression)) {
            return false;
        }

        // Check for starting with invalid operators (+, *, /)
        if (Pattern.matches(startOperator, expression)) {
            return false;
        }

        // Check for ending with an operator
        if (Pattern.matches(endOperator, expression)) {
            return false;
        }

        // Check for balanced parentheses
        if (!areParenthesesBalanced(expression)) {
            return false;
        }

        return true;
    }

    // Function to check balanced parentheses using a stack
    public static boolean areParenthesesBalanced(String expression) {
        Stack<Character> stack = new Stack<>();
        for (char ch : expression.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty()) {
                    return false;  // Unmatched closing parenthesis
                }
                stack.pop();
            }
        }
        return stack.isEmpty();  // Stack should be empty if parentheses are balanced
    }

    // Function to evaluate an infix expression (handles integers and decimals)
    public static double evaluateExpression(String expression) {
        // Transform expression to handle unary minus
        expression = transformExpression(expression);

        // Convert infix to postfix
        String postfix = infixToPostfix(expression);

        // Evaluate postfix expression
        return evaluatePostfix(postfix);
    }

    // Function to transform expression by handling unary minus
    public static String transformExpression(String expression) {
        return expression.replaceAll("(?<![0-9.)])-(\\d+(\\.\\d+)?)", "(0-$1)");
    }

    // Function to convert infix to postfix
    public static String infixToPostfix(String expression) {
        // Implementation of infix to postfix conversion (Shunting-yard algorithm)
        // [Code remains unchanged, use your existing method]
        return "";  // Placeholder
    }

    // Function to evaluate postfix expression
    public static double evaluatePostfix(String postfix) {
        // Implementation of postfix evaluation
        // [Code remains unchanged, use your existing method]
        return 0.0;  // Placeholder
    }

    public static void main(String[] args) {
        String expression = "-5 + 3 * (2 - 4)";
        
        if (isValidExpression(expression)) {
            double result = evaluateExpression(expression);
            System.out.println("Result: " + result);
        } else {
            System.out.println("Invalid expression! Please enter a valid infix expression.");
        }
    }
}
