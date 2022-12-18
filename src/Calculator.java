public class Calculator {
    public float calculate(float num1, float num2,char operator) {
        float result = 0;
        switch(operator) {
        case '+':
            result = num1+num2;
            break;
        case '-':
            result = num1-num2;
            break;
        case '*':
            result = num1*num2;
            break;
        case '/':
            result = num1/num2;
            break;
        case '^':
            result = (float) Math.pow(num1, num2);
            break;
        }
        return result;
    }
}
