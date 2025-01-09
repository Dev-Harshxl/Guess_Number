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






------+--+---

import java.util.Stack;

public class ExpressionEvaluator {

    // Function to check if the input expression is valid
    public static boolean isValidExpression(String expr) {
        // Regex to detect invalid cases: multiple consecutive operators or misplaced operators
        String invalidRegex = ".*[+*/]{2,}.*|.*[-]{3,}.*|.*[-+*/]-[+*/].*";
        
        // Check if the expression matches the invalid pattern
        if (expr.matches(invalidRegex)) {
            return false; // Invalid expression
        }
        
        // Ensure that the expression starts and ends with a valid character (digit or parenthesis)
        if (expr.matches("^[+*/].*") || expr.matches(".*[+*/-]$")) {
            return false;
        }
        
        return true; // Valid expression
    }

    // Function to evaluate the given infix expression
    public static double evaluateExpression(String expr) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();
        int i = 0;
        
        while (i < expr.length()) {
            char c = expr.charAt(i);
            
            // Skip whitespace (if any)
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            // Handle numbers (including multi-digit and decimal)
            if (Character.isDigit(c) || c == '.') {
                StringBuilder num = new StringBuilder();
                while (i < expr.length() && 
                       (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    num.append(expr.charAt(i));
                    i++;
                }
                numbers.push(Double.parseDouble(num.toString()));
                continue;
            }

            // Handle opening parenthesis
            if (c == '(') {
                operators.push(c);
            }
            // Handle closing parenthesis
            else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.pop(); // Remove '('
            }
            // Handle operators (+, -, *, /)
            else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(c);
            }
            i++;
        }

        // Apply remaining operations
        while (!operators.isEmpty()) {
            numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    // Function to return precedence of operators
    public static int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    // Function to apply an operation and return the result
    public static double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': 
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
        }
        return 0;
    }

    // Main function to test the code
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (true) {
            System.out.print("Enter an infix expression: ");
            String expr = scanner.nextLine();
            
            if (isValidExpression(expr)) {
                try {
                    double result = evaluateExpression(expr);
                    System.out.println("Result: " + result);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid expression. Please try again.");
            }
        }
    }
}








import java.util.Stack;
import java.util.regex.Pattern;

public class ExpressionEvaluator {

    public static boolean isValidExpression(String expression) {
        String consecutiveOperators = ".*[+*/]{2,}.*|.*[-]{3,}.*|.*[-+*/]-[+*/].*";
        String startOperator = "^[+*/].*";
        String endOperator = ".*[+\\-*/]$";
        String validCharacters = "[0-9+\\-*/().]*";

        if (!Pattern.matches(validCharacters, expression)) return false;
        if (Pattern.matches(consecutiveOperators, expression)) return false;
        if (Pattern.matches(startOperator, expression)) return false;
        if (Pattern.matches(endOperator, expression)) return false;
        if (!areParenthesesBalanced(expression)) return false;

        return true;
    }

    public static boolean areParenthesesBalanced(String expression) {
        Stack<Character> stack = new Stack<>();
        for (char ch : expression.toCharArray()) {
            if (ch == '(') stack.push(ch);
            else if (ch == ')') {
                if (stack.isEmpty()) return false;
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    public static double evaluateExpression(String expression) {
        expression = transformExpression(expression);
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

    public static String transformExpression(String expression) {
        return expression.replaceAll("(?<![0-9.)])-(\\d+(\\.\\d+)?|\\()", "(0-$1)");
    }

    public static String infixToPostfix(String expression) {
        Stack<Character> operators = new Stack<>();
        StringBuilder postfix = new StringBuilder();
        int i = 0;

        while (i < expression.length()) {
            char c = expression.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }
            if (Character.isDigit(c) || c == '.') {
                StringBuilder num = new StringBuilder();
                while (i < expression.length() && 
                      (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    num.append(expression.charAt(i));
                    i++;
                }
                postfix.append(num).append(' ');
                continue;
            }
            if (c == '(') operators.push(c);
            else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    postfix.append(operators.pop()).append(' ');
                }
                operators.pop();
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    postfix.append(operators.pop()).append(' ');
                }
                operators.push(c);
            }
            i++;
        }

        while (!operators.isEmpty()) {
            postfix.append(operators.pop()).append(' ');
        }

        return postfix.toString().trim();
    }

    public static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                switch (token.charAt(0)) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                }
            }
        }

        return stack.pop();
    }

    public static boolean isNumber(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
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






------+--+---

import java.util.Stack;

public class ExpressionEvaluator {

    // Function to check if the input expression is valid
    public static boolean isValidExpression(String expr) {
        // Regex to detect invalid cases: multiple consecutive operators or misplaced operators
        String invalidRegex = ".*[+*/]{2,}.*|.*[-]{3,}.*|.*[-+*/]-[+*/].*";

        // Check if the expression matches the invalid pattern
        if (expr.matches(invalidRegex)) {
            return false; // Invalid expression
        }

        // Ensure that the expression starts and ends with a valid character (digit or parenthesis)
        if (expr.matches("^[+*/].*") || expr.matches(".*[+*/-]$")) {
            return false;
        }

        return true; // Valid expression
    }

    // Function to evaluate the given infix expression
    public static double evaluateExpression(String expr) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();
        int i = 0;

        while (i < expr.length()) {
            char c = expr.charAt(i);

            // Skip whitespace (if any)
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            // Handle numbers (including multi-digit and decimal)
            if (Character.isDigit(c) || c == '.') {
                StringBuilder num = new StringBuilder();
                while (i < expr.length() && 
                       (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    num.append(expr.charAt(i));
                    i++;
                }
                numbers.push(Double.parseDouble(num.toString()));
                continue;
            }

            // Handle opening parenthesis
            if (c == '(') {
                operators.push(c);
            }
            // Handle closing parenthesis
            else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.pop(); // Remove '('
            }
            // Handle operators (+, -, *, /)
            else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(c);
            }
            i++;
        }

        // Apply remaining operations
        while (!operators.isEmpty()) {
            numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    // Function to return precedence of operators
    public static int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    // Function to apply an operation and return the result
    public static double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': 
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
        }
        return 0;
    }

    // Main function to test the code
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (true) {
            System.out.print("Enter an infix expression: ");
            String expr = scanner.nextLine();

            if (isValidExpression(expr)) {
                try {
                    double result = evaluateExpression(expr);
                    System.out.println("Result: " + result);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid expression. Please try again.");
            }
        }
    }
}








import java.util.Stack;
import java.util.regex.Pattern;

public class ExpressionEvaluator {

    public static boolean isValidExpression(String expression) {
        String consecutiveOperators = ".*[+*/]{2,}.*|.*[-]{3,}.*|.*[-+*/]-[+*/].*";
        String startOperator = "^[+*/].*";
        String endOperator = ".*[+\\-*/]$";
        String validCharacters = "[0-9+\\-*/().]*";

        if (!Pattern.matches(validCharacters, expression)) return false;
        if (Pattern.matches(consecutiveOperators, expression)) return false;
        if (Pattern.matches(startOperator, expression)) return false;
        if (Pattern.matches(endOperator, expression)) return false;
        if (!areParenthesesBalanced(expression)) return false;

        return true;
    }

    public static boolean areParenthesesBalanced(String expression) {
        Stack<Character> stack = new Stack<>();
        for (char ch : expression.toCharArray()) {
            if (ch == '(') stack.push(ch);
            else if (ch == ')') {
                if (stack.isEmpty()) return false;
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    public static double evaluateExpression(String expression) {
        expression = transformExpression(expression);
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

    public static String transformExpression(String expression) {
        return expression.replaceAll("(?<![0-9.)])-(\\d+(\\.\\d+)?|\\()", "(0-$1)");
    }

    public static String infixToPostfix(String expression) {
        Stack<Character> operators = new Stack<>();
        StringBuilder postfix = new StringBuilder();
        int i = 0;

        while (i < expression.length()) {
            char c = expression.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }
            if (Character.isDigit(c) || c == '.') {
                StringBuilder num = new StringBuilder();
                while (i < expression.length() && 
                      (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    num.append(expression.charAt(i));
                    i++;
                }
                postfix.append(num).append(' ');
                continue;
            }
            if (c == '(') operators.push(c);
            else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    postfix.append(operators.pop()).append(' ');
                }
                operators.pop();
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    postfix.append(operators.pop()).append(' ');
                }
                operators.push(c);
            }
            i++;
        }

        while (!operators.isEmpty()) {
            postfix.append(operators.pop()).append(' ');
        }

        return postfix.toString().trim();
    }

    public static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                switch (token.charAt(0)) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                }
            }
        }

        return stack.pop();
    }

    public static boolean isNumber(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    public static void main(String[] args) {
        String expression = "-5 + 3 * (2 - 4)";

        if (isValidExpression(expression)) {
            double result = evaluateExpression(expression);
            System.out.println("Result: " + result);
        } else {
            System.out.println("Invalid expression! Please enter a valid infix expression.");
        }
   




import java.util.Scanner;

public class ExpressionEvaluator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression;

        System.out.println("Enter an infix expression to evaluate (type 'exit' to quit):");

        while (true) {
            System.out.print("\nExpression: ");
            expression = scanner.nextLine().trim();

            // Check for exit condition
            if (expression.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the program.");
                break;
            }

            if (isValidExpression(expression)) {
                double result = evaluateExpression(expression);
                System.out.println("Result: " + result);
            } else {
                System.out.println("Invalid expression! Please enter a valid infix expression.");
            }
        }

        scanner.close();
    }
}

