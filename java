import java.util.Stack;

public class ExpressionEvaluator {

    // Function to transform the expression by handling unary minus
    public static String transformExpression(String expression) {
        expression = " " + expression; // Add space to handle leading unary minus
        expression = expression.replaceAll("(?<![0-9)])-(\\d+)", "(0 - $1)");
        return expression.trim();
    }

    // Function to evaluate the transformed infix expression
    public static int evaluateInfix(String expression) {
        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int i = 0, n = expression.length();

        while (i < n) {
            char ch = expression.charAt(i);

            // Skip whitespace
            if (Character.isWhitespace(ch)) {
                i++;
                continue;
            }

            // If it's a digit, parse the full number (to handle multi-digit numbers)
            if (Character.isDigit(ch)) {
                int num = 0;
                while (i < n && Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                values.push(num);
                continue;
            }

            // If it's an opening parenthesis, push to operators stack
            if (ch == '(') {
                operators.push(ch);
            }
            // If it's a closing parenthesis, solve the entire sub-expression
            else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // Remove the opening parenthesis
            }
            // If it's an operator, push to operators stack after solving higher precedence operators
            else if (isOperator(ch)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(ch);
            }
            i++;
        }

        // Apply remaining operators
        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    // Function to check if a character is an operator
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    // Function to return precedence of operators
    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') return 1;
        if (operator == '*' || operator == '/') return 2;
        return 0;
    }

    // Function to apply an operator to two operands
    private static int applyOperator(char operator, int b, int a) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b; // Assume no division by zero
        }
        return 0;
    }

    public static void main(String[] args) {
        String expression = "3 - (-4) * 5 + 12 / -6";
        String transformedExpression = transformExpression(expression);
        System.out.println("Transformed Expression: " + transformedExpression);
        int result = evaluateInfix(transformedExpression);
        System.out.println("Result: " + result);
    }
}





----++++++------++++++++--

import java.util.Stack;

public class ExpressionEvaluator {

    // Function to transform the expression by handling unary minus
    public static String transformExpression(String expression) {
        expression = " " + expression; // Add space to handle leading unary minus
        expression = expression.replaceAll("(?<![0-9.)])-(\\d+(\\.\\d+)?)", "(0 - $1)");
        return expression.trim();
    }

    // Function to evaluate the transformed infix expression
    public static double evaluateInfix(String expression) {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int i = 0, n = expression.length();

        while (i < n) {
            char ch = expression.charAt(i);

            // Skip whitespace
            if (Character.isWhitespace(ch)) {
                i++;
                continue;
            }

            // If it's a digit or a decimal point, parse the full number
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder num = new StringBuilder();
                while (i < n && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    num.append(expression.charAt(i));
                    i++;
                }
                values.push(Double.parseDouble(num.toString()));
                continue;
            }

            // If it's an opening parenthesis, push to operators stack
            if (ch == '(') {
                operators.push(ch);
            }
            // If it's a closing parenthesis, solve the entire sub-expression
            else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // Remove the opening parenthesis
            }
            // If it's an operator, push to operators stack after solving higher precedence operators
            else if (isOperator(ch)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(ch);
            }
            i++;
        }

        // Apply remaining operators
        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    // Function to check if a character is an operator
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    // Function to return precedence of operators
    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') return 1;
        if (operator == '*' || operator == '/') return 2;
        return 0;
    }

    // Function to apply an operator to two operands
    private static double applyOperator(char operator, double b, double a) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b; // Assume no division by zero
        }
        return 0;
    }

    public static void main(String[] args) {
        String expression = "3.5 - (-4.2) * 5.1 + 12.0 / -6.0";
        String transformedExpression = transformExpression(expression);
        System.out.println("Transformed Expression: " + transformedExpression);
        double result = evaluateInfix(transformedExpression);
        System.out.println("Result: " + result);
    }
}
