import java.util.Stack;

public class ExpressionSolver {
    public static void main(String[] args) {
        // Example mathematical expression
        String expression = "3 + 5 * (2 - 8)";
        
        // Remove spaces
        expression = expression.replaceAll("\\s+", "");
        
        // Evaluate the expression
        try {
            double result = evaluateExpression(expression);
            System.out.println("The result of the expression \"" + expression + "\" is: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Function to evaluate a mathematical expression
    public static double evaluateExpression(String expression) throws Exception {
        // Convert infix expression to postfix
        String postfix = infixToPostfix(expression);
        // Evaluate the postfix expression
        return evaluatePostfix(postfix);
    }

    // Function to convert infix to postfix
    public static String infixToPostfix(String expression) throws Exception {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // If the character is a digit, add it to the result
            if (Character.isDigit(c)) {
                result.append(c);
            } 
            // If the character is an operator
            else if (isOperator(c)) {
                result.append(' '); // Add space for separating numbers/operators
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(c);
            } 
            // If the character is an opening parenthesis
            else if (c == '(') {
                stack.push(c);
            } 
            // If the character is a closing parenthesis
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(' ').append(stack.pop());
                }
                if (stack.isEmpty() || stack.pop() != '(') {
                    throw new Exception("Invalid expression: Mismatched parentheses");
                }
            }
        }

        // Pop all remaining operators from the stack
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                throw new Exception("Invalid expression: Mismatched parentheses");
            }
            result.append(' ').append(stack.pop());
        }

        return result.toString();
    }

    // Function to evaluate postfix expression
    public static double evaluatePostfix(String postfix) throws Exception {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.trim().split("\\s+");

        for (String token : tokens) {
            // If the token is a number
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            } 
            // If the token is an operator
            else if (isOperator(token.charAt(0))) {
                if (stack.size() < 2) {
                    throw new Exception("Invalid expression: Insufficient values");
                }
                double b = stack.pop();
                double a = stack.pop();
                stack.push(applyOperator(a, b, token.charAt(0)));
            } 
            else {
                throw new Exception("Invalid expression: Unknown token \"" + token + "\"");
            }
        }

        if (stack.size() != 1) {
            throw new Exception("Invalid expression: Too many values");
        }

        return stack.pop();
    }

    // Function to apply an operator to two operands
    public static double applyOperator(double a, double b, char operator) throws Exception {
        return switch (operator) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) throw new Exception("Division by zero");
                yield a / b;
            }
            default -> throw new Exception("Invalid operator: " + operator);
        };
    }

    // Function to check if a character is an operator
    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Function to get the precedence of an operator
    public static int precedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1;
        };
    }
}