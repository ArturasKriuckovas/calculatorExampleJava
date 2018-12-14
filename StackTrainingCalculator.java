import java.util.Stack;
public class StackTrainingCalculator {
    public static String[] operators = {"+", "-", "/", "*"};
    public static void main(String[] args) {
        Stack<String> mStack = new Stack<>();
        String postFix = "232+*";
        String inFixValue = "1*(2+3)*(8+2)";
        System.out.println(evaluatePostFix(convertToPostfix(inFixValue)));
    }

    public static String evaluatePostFix(String value){
        Stack<String> mStack = new Stack<>();
        for (int i = 0; i < value.length(); i++){
            if(checkIfContains(Character.toString(value.charAt(i)), operators)){
                String right = mStack.pop();
                String left = mStack.pop();
                mStack.push(evaluate(left, right, Character.toString(value.charAt(i))));
            } else {
                mStack.push(Character.toString(value.charAt(i)));
            }
        }
        return mStack.pop();
    }

    public static String evaluate(String a, String b, String operator){
        String result = "";
        switch (operator) {
            case "+":
                result = String.valueOf(Integer.parseInt(a) + Integer.parseInt(b));
                break;
            case "-":
                result = String.valueOf(Integer.parseInt(a) - Integer.parseInt(b));
                break;
            case "*":
                result = String.valueOf(Integer.parseInt(a) * Integer.parseInt(b));
                break;
            case "/":
                result = String.valueOf(Integer.parseInt(a) / Integer.parseInt(b));
                break;
            default: break;            
        }
        return result;
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

    static String convertToPostfix(String infix){
        Stack<String> mStack = new Stack<>();
        String postFix = "";
        for (int i = 0; i < infix.length(); i++){
            boolean isOperator = checkIfContains(item, operators);
            String item = Character.toString(infix.charAt(i));
            if(!isOperator && !item.equals("(") && !item.equals(")")){
                postFix += Character.toString(infix.charAt(i));
            } else if (item.equals("(")){
                mStack.push(item);
            } else if (item.equals(")")){
                while(!mStack.isEmpty() && !mStack.peek().equals("(")){
                    postFix += mStack.pop();
                }
                mStack.pop();
            } else if (isOperator){
                if (mStack.empty() || mStack.peek().equals("(")){
                    mStack.push(item);
                } else {
                    while(!mStack.isEmpty() && !mStack.peek().equals("(")
                            && getLevelOfOperator(item) <= getLevelOfOperator(mStack.peek())) {
                    postFix += mStack.pop();
                    }
                    mStack.push(item);
                }
            }
        }
        while (!mStack.isEmpty()){
            postFix += mStack.pop();
        }
        return postFix;
    }
}