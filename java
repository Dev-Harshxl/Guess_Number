


public static String transformExpression(String expression) {
    // Replace '--' with '+' when it appears between two numbers or parentheses
    expression = expression.replaceAll("(?<=[0-9)])-{2}(?=[0-9(])", "+");

    // Handle unary minus before numbers or parentheses
    expression = expression.replaceAll("(?<![0-9.)])-(\\d+(\\.\\d+)?|\\()", "(0-$1)");

    return expression;
}


expression = expression.replaceAll("((?<=[0-9)])|\\G)\\(", "*(");




public static String transformExpression(String expression) {
    // Add '*' between a number and an opening parenthesis if not already present
    expression = expression.replaceAll("(?<=[0-9)])\\(", "*(");

    // Replace '--' with '+' when it appears between two numbers or parentheses
    expression = expression.replaceAll("(?<=[0-9)])-{2}(?=[0-9(])", "+");

    // Handle unary minus before numbers or parentheses
    expression = expression.replaceAll("(?<![0-9.)])-(\\d+(\\.\\d+)?|\\()", "(0-$1)");

    return expression;
}
