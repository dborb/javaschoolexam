package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
import java.util.*;


public class Calculator {

    /**
     * checking if a string is an operation
     *
     * @param string operation
     * @return is string a operation
     */
    boolean isOperation(String string){
        String operations = "+-*/";
        if(operations.indexOf(string) != -1){
            return true;
        }
        return false;
    }

    /**
     * convert value into four decimal plases format
     *
     * @param value the value to be provided in the correct format
     * @return rounded value or null if value is incorrect
     */
    private static String round(Double value) {
        if(value == null){
            return null;
        }
        DecimalFormat format = new DecimalFormat("#.####");
        return format.format(value).replaceAll(",",".");
    }

    /**
     * validation of symbol
     *
     * @param symbol a symbol that need to be checked
     * @return is symbol allowed
     */
    boolean allowedSymbols(char symbol){
        String symbols = "+-*/.()1234567890";
        if(symbols.indexOf(symbol) != -1){
            return true;
        }
        return false;
    }

    /**
     * converts a statement to RPN
     *
     * @param statement given expression that need to be calculate
     * @return ArrayList of string where data goes in RPN order or null if it's incorrect
     */
    private ArrayList<String> getRPN(String statement){
        Stack<Character> stack = new Stack<>();
        ArrayList<String> rpnStringTokens = new ArrayList<>();
        HashMap<Character, Integer> pr = new HashMap<>();

        pr.put('+', 1);
        pr.put('-', 1);
        pr.put('*', 2);
        pr.put('/', 2);
        pr.put('(', 0);

        double tempValue = 0;
        double fractionalPart = 0;

        boolean fractionalFlag = false;

        for(int i = 0; i < statement.length(); ++i){

            if(!allowedSymbols(statement.charAt(i))){
                return null;
            }
            if(i == ' '){
                continue;
            }
            if(Character.isDigit(statement.charAt(i))){
                if(fractionalFlag){
                    fractionalPart++;
                }
                tempValue *= 10;
                tempValue += (statement.charAt(i) - '0');

            }
            else if(statement.charAt(i) == '.'){
                if(fractionalFlag){
                    return null;
                }
                fractionalFlag = true;
            }
            else{
                if(i < statement.length() - 1 && statement.charAt(i) == statement.charAt(i + 1) && statement.charAt(i) != '(' && statement.charAt(i) != ')'){
                    return null;
                }
                fractionalFlag = false;
                if(tempValue != 0){
                    tempValue = tempValue / Math.pow(10, fractionalPart);
                    rpnStringTokens.add("" + tempValue);
                    tempValue = 0;
                    fractionalPart = 0;
                }
                if(statement.charAt(i) == ')'){
                    while(true){
                        if(stack.empty()){
                            return null;
                        }
                        char c = stack.pop();
                        if(c == '('){
                            break;
                        }
                        rpnStringTokens.add("" + c);
                    }
                }
                else if(statement.charAt(i) == '('){
                    stack.push(statement.charAt(i));
                }
                else{
                    if(statement.charAt(i) == '-' && statement.charAt(i+1) == '('){
                        stack.push('+');
                        rpnStringTokens.add("-1");
                        stack.push('*');
                        continue;
                    }
                    if(!stack.empty() && (pr.get(stack.peek()) >= pr.get(statement.charAt(i)))) {
                        rpnStringTokens.add(stack.pop().toString());
                    }
                    stack.push(statement.charAt(i));
                }
            }
        }

        if(tempValue != 0){
            tempValue = tempValue / Math.pow(10, fractionalPart);
            rpnStringTokens.add("" + tempValue);
        }

        while (!stack.empty()){
            if(stack.peek() == '('){
                return null;
            }
            rpnStringTokens.add(stack.pop().toString());
        }

        return rpnStringTokens;
    }

    /**
     * Calculate the given expression
     *
     * @param rpnStringTokens string tokens in RPN order
     * @return double value contains result of evaluation or null if statement is invalid
     */
    public Double proceessdRPN(ArrayList<String> rpnStringTokens){
        Stack<Double> workingStack = new Stack<>();
        double num2;
        double num1;

        for(String i : rpnStringTokens){
            if(isOperation(i)){
                num2 = workingStack.pop();
                num1 = workingStack.pop();
                switch (i){
                    case "+":
                        workingStack.push(num1 + num2);
                        break;
                    case "-":
                        workingStack.push(num1 - num2);
                        break;
                    case "*":
                        workingStack.push(num1 * num2);
                        break;
                    case "/":
                        if(num2 == 0){
                            return null;
                        }
                        workingStack.push(num1 / num2);
                        break;
                }
            }
            else{
                workingStack.push(Double.parseDouble(i));
            }
        }

        return workingStack.pop();
    }

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {

        if(statement == null || statement.length() == 0){
            return null;
        }

        ArrayList<String> rpnStringTokens = getRPN(statement);
        if(rpnStringTokens == null){
            return null;
        }
        Double result = proceessdRPN(rpnStringTokens);

        return round(result);
    }

}
