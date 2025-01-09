public static String transformExpression(String expression) {
    // Replace multiple consecutive '-' with single or alternating signs
    expression = expression.replaceAll("--", "+");  // Convert '--' to '+'
    expression = expression.replaceAll("\\+\\-", "-");  // Convert '+-' to '-'
    expression = expression.replaceAll("-\\+", "-");  // Convert '-+' to '-'

    // Handle unary minus before numbers or parentheses
    expression = expression.replaceAll("(?<![0-9.)])-(\\d+(\\.\\d+)?|\\()", "(0-$1)");

    return expression;
}
