public static String transformExpression(String expression) {
    // Replace '--' with '+' when it appears between two numbers or parentheses
    expression = expression.replaceAll("(?<=[0-9)])-{2}(?=[0-9(])", "+");

    // Handle unary minus before numbers or parentheses
    expression = expression.replaceAll("(?<![0-9.)])-(\\d+(\\.\\d+)?|\\()", "(0-$1)");

    return expression;
}
