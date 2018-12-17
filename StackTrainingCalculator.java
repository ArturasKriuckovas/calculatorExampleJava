import java.util.Stack;
import java.util.ArrayList;
import java.math.BigDecimal;
public class StackTrainingCalculator {
    public static String[] allSymbols = {"+", "-", "/", "*","(", ")"};
    public static String[] operators = {"+", "-", "/", "*"};
    public static void main(String[] args) {
        Stack<String> mStack = new Stack<>();
        String postFix = "232+*";
        String inFixValue = "12.1*(2+3)*(8.5+2.1)";
        System.out.println(evaluatePostFix(convertToPostfix(getListOfValues(inFixValue))));
    }

    public static ArrayList<String> getListOfValues(String expression){
        ArrayList<String> list = new ArrayList<>();
        String tempValue = "";
        for (int i = 0; i < expression.length(); i++){
            String item = Character.toString(expression.charAt(i));
            if(checkIfContains(item, allSymbols)){
                list.add(item);
            } else {
                tempValue += item;
                if(checkIfContains(Character.toString(expression.charAt(i+1)), allSymbols)){
                    list.add(tempValue);
                    tempValue = "";
                }
            }
        }
        return list;
    }

    public static String evaluatePostFix(ArrayList<String> value){
        Stack<String> mStack = new Stack<>();
        for (int i = 0; i < value.size(); i++){
            if(checkIfContains(value.get(i), operators)){
                String right = mStack.pop();
                String left = mStack.pop();
                mStack.push(evaluate(left, right, value.get(i)));
            } else {
                mStack.push(value.get(i));
            }
        }
        return mStack.pop();
    }

    public static String evaluate(String a, String b, String operator){
        BigDecimal left = new BigDecimal(a);
        BigDecimal right = new BigDecimal(b);
        BigDecimal result = new BigDecimal(0);
        switch (operator) {
            case "+":
                result = left.add(right);
                break;
            case "-":
                result = left.subtract(right);
                break;
            case "*":
                result = left.multiply(right);
                break;
            case "/":
                result = left.divide(right);
                break;
            default: break;            
        }
        return String.valueOf(result);
    }

    static boolean checkIfContains(String c, String[] array) {
        for (String x : array) {
            if (x.equals(c)) {
                return true;
            }
        }
        return false;
    }
    static int getLevelOfOperator(String operator){
        int value = 0;
        if (operator.equals("+") || operator.equals("-")){
            value = 1;
        } else if (operator.equals("*") || operator.equals("/")){
            value = 2;
        }
        return value;
        
    }

    static ArrayList<String> convertToPostfix(ArrayList<String> infix){
        Stack<String> mStack = new Stack<>();
        ArrayList<String> list = new ArrayList<>();
        String postFix = "";
        for (int i = 0; i < infix.size(); i++){
            String item = infix.get(i);

            boolean isOperator = checkIfContains(item, operators);
            if(!isOperator && !item.equals("(") && !item.equals(")")){
                list.add(item);
            } else if (item.equals("(")){
                mStack.push(item);
            } else if (item.equals(")")){
                while(!mStack.isEmpty() && !mStack.peek().equals("(")){
                    list.add(mStack.pop());
                }
                mStack.pop();
            } else if (isOperator){
                if (mStack.empty() || mStack.peek().equals("(")){
                    mStack.push(item);
                } else {
                    while(!mStack.isEmpty() && !mStack.peek().equals("(")
                            && getLevelOfOperator(item) <= getLevelOfOperator(mStack.peek())) {
                    list.add(mStack.pop());
                    }
                    mStack.push(item);
                }
            }
        }
        while (!mStack.isEmpty()){
            list.add(mStack.pop());
        }
        return list;

    }
}