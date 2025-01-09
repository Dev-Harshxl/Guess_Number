public static String transformExpression(String expression) {
    // Replace multiple consecutive '-' with single or alternating signs
    expression = expression.replaceAll("--", "+");  // Convert '--' to '+'
    expression = expression.replaceAll("\\+\\-", "-");  // Convert '+-' to '-'
    expression = expression.replaceAll("-\\+", "-");  // Convert '-+' to '-'

    // Handle unary minus before numbers or parentheses
    expression = expression.replaceAll("(?<![0-9.)])-(\\d+(\\.\\d+)?|\\()", "(0-$1)");

    return expression;
}






public static String transformExpression(String expression) {
    // Replace multiple consecutive '-' with a single '+', but only outside parentheses
    expression = expression.replaceAll("(?<!\\d)-{2}(?=\\d)", "+");

    // Handle unary minus before numbers or parentheses
    expression = expression.replaceAll("(?<![0-9.)])-(\\d+(\\.\\d+)?|\\()", "(0-$1)");

    return expression;
}







public static String transformExpression(String expression) {
    // Handle unary minus before numbers or parentheses
    expression = expression.replaceAll("(?<![0-9.)])-(\\d+(\\.\\d+)?|\\()", "(0-$1)");

    // Replace '--' with '+' only when it occurs outside parentheses or unary contexts
    expression = expression.replaceAll("(?<=[0-9)])-{2}(?=[0-9(])", "+");

    return expression;
}
