package com.example.classcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {


    private TextView resultTextView;
    private TextView expressionTextView;
    private  ArrayList<String> postfix = new ArrayList<String>();
    private  Stack<Character> operatorStack = new Stack<>();
    private  Stack<String> postfixSolver =new Stack<>();
    private  boolean mistake=false;
    private  boolean balanceBrackets=false;
    private  String expression;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        resultTextView=(TextView)findViewById(R.id.resultTextView);
        resultTextView.setMovementMethod(new ScrollingMovementMethod());
        expressionTextView=(TextView)findViewById(R.id.expressionTextView);
        expressionTextView.setMovementMethod(new ScrollingMovementMethod());

    }


    public void onClickNumber(View v){

        String numberView="";

        switch (v.getId()){

        //Operands
            case R.id.btn0 :
                numberView+="0";
                break;
            case R.id.btn1 :
                numberView+="1";
                break;
            case R.id.btn2 :
                numberView+="2";
                break;
            case R.id.btn3 :
                numberView+="3";
                break;
            case R.id.btn4 :
                numberView+="4";
                break;
            case R.id.btn5 :
                numberView+="5";
                break;
            case R.id.btn6 :
                numberView+="6";
                break;
            case R.id.btn7 :
                numberView+="7";
                break;
            case R.id.btn8 :
                numberView+="8";
                break;
            case R.id.btn9 :
                numberView+="9";
                break;
            case R.id.btnPoint :
                numberView+=".";
                break;

        //Operators
            case R.id.btnOpenParentheses :
                numberView+="(";
                break;
            case R.id.btnCloseParentheses :
                numberView+=")";
                break;
            case R.id.btnPlus :
                numberView+="+";
                break;
            case R.id.btnMinus :
                numberView+="-";
                break;
            case R.id.btnMult :
                numberView+="X";
                break;
            case R.id.btnDiv :
                numberView+="÷";
        }
        expressionTextView.append(numberView);
    }


    public void onClickClr(View v) {

      expressionTextView.setText(null);
        resultTextView.setText(null);
        postfix.clear();
        operatorStack.clear();
        postfixSolver.clear();
        mistake = false;
        balanceBrackets = false;
        expression=null;

    }

    public void onClickBack(View v) {
        String back=expressionTextView.getText().toString();
        if (back.length() >1 ) {
            back = back.substring(0, back.length() - 1);
            expressionTextView.setText(back);
            resultTextView.setText(null);
            postfix.clear();
            operatorStack.clear();
            postfixSolver.clear();
            mistake = false;
            balanceBrackets = false;
            expression=null;
        }
        else if (back.length() <=1 ) {
            expressionTextView.setText(null);
            resultTextView.setText(null);
            postfix.clear();
            operatorStack.clear();
            postfixSolver.clear();
            mistake = false;
            balanceBrackets = false;
            expression=null;
        }

    }



    public void onClickResult(View v) {

        expression = expressionTextView.getText().toString();
        String result = "";


        if(expression==null){
            Toast.makeText(getApplicationContext(), "Please put expression", Toast.LENGTH_LONG).show();
            postfix.clear();
            operatorStack.clear();
            postfixSolver.clear();
            mistake = false;
            balanceBrackets = false;
            expression=null;

        }
        else {
            userInputCheck();
            balanceBrackets();

            System.out.println(expression);

            if (mistake == false && balanceBrackets == true) {
                infixToPostfix();

                System.out.println(postfix);

                result = postfixSolution(postfix);

                System.out.println(result);

                if (result == "W1") {
                    Toast.makeText(getApplicationContext(), "Sorry some thing wrong happened", Toast.LENGTH_LONG).show();
                    postfix.clear();
                    operatorStack.clear();
                    postfixSolver.clear();
                    mistake = false;
                    balanceBrackets = false;
                    expression=null;
                }
                else {
                    resultTextView.setText(result);
                    postfix.clear();
                    operatorStack.clear();
                    postfixSolver.clear();
                    mistake = false;
                    balanceBrackets = false;
                    expression=null;
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Check your expression", Toast.LENGTH_LONG).show();
                postfix.clear();
                operatorStack.clear();
                postfixSolver.clear();
                mistake = false;
                balanceBrackets = false;
                expression=null;
            }
        }

    }

    public String postfixSolution(ArrayList<String> postfix){

        //<Operand> <Operand> <Operator>

        float operand1,operand2;
        String lastElementOfStack;


        int size;

        for(int i=0;i<postfix.size();i++){


            if(postfix.get(i).equals("+") || postfix.get(i).equals("-") || postfix.get(i).equals("X") || postfix.get(i).equals("÷")){

                operand2=Float.parseFloat(postfixSolver.pop());
                operand1=Float.parseFloat(postfixSolver.pop());

                switch (postfix.get(i)){
                    case "+": postfixSolver.push(String.valueOf(operand1+operand2));
                              break;
                    case "-": postfixSolver.push(String.valueOf(operand1-operand2));
                        break;

                    case "X": postfixSolver.push(String.valueOf(operand1*operand2));
                        break;

                    case "÷": postfixSolver.push(String.valueOf(operand1/operand2));
                        break;
                }
            }

            else{

                    postfixSolver.push(postfix.get(i));

            }
        }

        size=postfixSolver.size();


        if(size==1) {
            lastElementOfStack = postfixSolver.pop();


            System.out.println(lastElementOfStack);

            String[] parts = lastElementOfStack.split("[.]");
            String leftPartOfPoint = parts[0];
            String rightPartOfPoint = parts[1];
            int rightPartPoint=Integer.parseInt(rightPartOfPoint);


                if(rightPartPoint>0){
                    return leftPartOfPoint+"."+rightPartOfPoint;
                }
                else{

                    return leftPartOfPoint;
                }
        }


        else{
            return "W1";
        }


    }








    public void infixToPostfix(){

        String number="";

        for(int i=0;i<expression.length();i++){


            //Check if my character is Operator
            if(expression.charAt(i)=='+' || expression.charAt(i)=='-' || expression.charAt(i)=='X' || expression.charAt(i)=='÷')
            {

                if (number!=""){
                    postfix.add(String.valueOf(number));
                    number="";
                }



                if(operatorStack.empty()){
                    operatorStack.push(expression.charAt(i));
                }
                else if(operatorStack.empty()==false && precidence(expression.charAt(i))>precidence(operatorStack.peek()) ){
                    operatorStack.push(expression.charAt(i));
                }
                else if(operatorStack.empty()==false && precidence(expression.charAt(i))<precidence(operatorStack.peek()) && operatorStack.peek()!='('){

                    while(operatorStack.empty()==false && precidence(expression.charAt(i))<precidence(operatorStack.peek()) && operatorStack.peek()!='(') {
                        postfix.add(String.valueOf(operatorStack.pop()));
                    }
                    operatorStack.push(expression.charAt(i));
                }

                else if(operatorStack.empty()==false && precidence(expression.charAt(i))<precidence(operatorStack.peek()) && operatorStack.peek()=='('){
                    operatorStack.push(expression.charAt(i));
                }

                else if(operatorStack.empty()==false && precidence(expression.charAt(i))==precidence(operatorStack.peek()) ){
                    while(operatorStack.empty()==false && precidence(expression.charAt(i))==precidence(operatorStack.peek())) {
                        postfix.add(String.valueOf(operatorStack.pop()));
                    }
                    operatorStack.push(expression.charAt(i));
                }
            }

            //Check if my character is Brackets
            else if(expression.charAt(i)=='(' || expression.charAt(i)==')'){

                if (number!=""){
                    postfix.add(String.valueOf(number));
                    number="";
                }


                if(expression.charAt(i)=='('){
                    operatorStack.push(expression.charAt(i));
                }

                else if(expression.charAt(i)==')'){
                    while(operatorStack.peek()!='('){
                        postfix.add(String.valueOf(operatorStack.pop()));
                    }
                    operatorStack.pop();
                }
            }

            //Check if my character is Numbers
            else if (expression.charAt(i)=='0' || expression.charAt(i)=='1' || expression.charAt(i)=='2' ||
                    expression.charAt(i)=='3' || expression.charAt(i)=='4' || expression.charAt(i)=='5' ||
                    expression.charAt(i)=='6' || expression.charAt(i)=='7' || expression.charAt(i)=='8' ||
                    expression.charAt(i)=='9'  || expression.charAt(i)=='.' )
            {
                number+=String.valueOf(expression.charAt(i));
               // postfix.add(String.valueOf(expression.charAt(i)));
            }

        }

        //Check if there is anything at my number then add this at my Arraylist
        if (number!=""){
            postfix.add(String.valueOf(number));
            number="";
        }

        //Out of loop check if there is anything in my stack
        if(operatorStack.empty()==false) {
            while(operatorStack.empty()==false){
                postfix.add(String.valueOf(operatorStack.pop()));
            }
        }


    }


        /*
                Order of operators
               ......................

               1) Parentheses  ()  {}  []

               2) Exponents     ^
                                   If more than one then right to left

               3) Multiplication & Division
                                   Left to Right

               4) Addition & Substraction
                                   Left to Right
        */

    public int precidence(char a){

      if (a=='(') return 5;
      else if (a=='X' || a=='÷') return 4;
      else if (a=='+' || a=='-') return 3;
      else return -1;
    }



    public  void userInputCheck(){
        Stack<Character>wrongUserInputChecker=new Stack<>();

        for(int i=0;i<expression.length();i++) {

            System.out.println("=>");
            System.out.println(expression.charAt(i));
            System.out.println("I'm at after \" for(int i=0;i<expression.length();i++) {\" ");


            if(wrongUserInputChecker.empty()==true){
                wrongUserInputChecker.push(expression.charAt(i));
            }

           else if ( (wrongUserInputChecker.empty() == false) && (i==expression.length()-1) && (expression.charAt(i)=='(' || expression.charAt(i)=='.' || expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == 'X' || expression.charAt(i) == '÷')){

                    Toast.makeText(getApplicationContext(), "Please check your input", Toast.LENGTH_LONG).show();
                    mistake=true;
                    wrongUserInputChecker.clear();

                    System.out.println("I'm at after \"  else if ( (wrongUserInputChecker.empty() == false) && (i==expression.length()-1)){\" ");
                    break;


            }
            //Check if my character is Operator
            else if (expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == 'X' || expression.charAt(i) == '÷') {
                if (wrongUserInputChecker.empty() == false && (wrongUserInputChecker.peek() == '+' || wrongUserInputChecker.peek() == '-' || wrongUserInputChecker.peek() == 'X' || wrongUserInputChecker.peek() == '÷') && (expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == 'X' || expression.charAt(i) == '÷')) {
                    Toast.makeText(getApplicationContext(), "Please check your input", Toast.LENGTH_LONG).show();
                    mistake=true;
                    wrongUserInputChecker.clear();

                    System.out.println("I'm at after \"  else if (expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == 'X' || expression.charAt(i) == '÷') { \" ");

                    break;
                }
                else {
                    wrongUserInputChecker.push(expression.charAt(i));
                }
            }

            else if (expression.charAt(i) == '(') {
                if (wrongUserInputChecker.empty() == false && wrongUserInputChecker.peek() == '.' && expression.charAt(i) == '(') {
                    Toast.makeText(getApplicationContext(), "Please check your input", Toast.LENGTH_LONG).show();
                    mistake=true;
                    wrongUserInputChecker.clear();

                    System.out.println("I'm at after \"     else if (expression.charAt(i) == '(') { \" ");
                    break;
                }
                else if (wrongUserInputChecker.empty() == false &&
                        (wrongUserInputChecker.peek() == '0' || wrongUserInputChecker.peek() == '1' || wrongUserInputChecker.peek() == '2' ||
                                wrongUserInputChecker.peek() == '3' || wrongUserInputChecker.peek() == '4' || wrongUserInputChecker.peek() == '5' ||
                                wrongUserInputChecker.peek() == '6' || wrongUserInputChecker.peek() == '7' || wrongUserInputChecker.peek() == '8' ||
                                wrongUserInputChecker.peek() == '9') &&
                        expression.charAt(i) == '(') {
                    wrongUserInputChecker.push('X');
                    wrongUserInputChecker.push(expression.charAt(i));
                }
                else {
                    wrongUserInputChecker.push(expression.charAt(i));
                }

            }

            else if (expression.charAt(i) == ')') {
                if (wrongUserInputChecker.empty() == false && wrongUserInputChecker.peek() == '.' && expression.charAt(i) == ')') {
                    Toast.makeText(getApplicationContext(), "Please check your input", Toast.LENGTH_LONG).show();
                    mistake=true;
                    wrongUserInputChecker.clear();

                    System.out.println("I'm at after \"    else if (expression.charAt(i) == ')') {  \" ");
                    break;
                }
                else if (wrongUserInputChecker.empty() == false &&
                        (wrongUserInputChecker.peek() == '+' || wrongUserInputChecker.peek() == '-' || wrongUserInputChecker.peek() == 'X' ||
                                wrongUserInputChecker.peek() == '÷') &&
                        expression.charAt(i) == ')') {
                    Toast.makeText(getApplicationContext(), "Please check your input", Toast.LENGTH_LONG).show();
                    mistake=true;
                    wrongUserInputChecker.clear();

                    System.out.println("I'm at after \"    else if (wrongUserInputChecker.empty() == false &&\n" +
                            "                        (wrongUserInputChecker.peek() == '+' || wrongUserInputChecker.peek() == '-' || wrongUserInputChecker.peek() == 'X' ||\n" +
                            "                                wrongUserInputChecker.peek() == '÷') &&\n" +
                            "                        expression.charAt(i) == ')') {  \" ");

                    break;
                }
                else {
                    wrongUserInputChecker.push(expression.charAt(i));
                }

            }

            else if (expression.charAt(i) == '.') {

                if (wrongUserInputChecker.empty() == false & (wrongUserInputChecker.peek() == '+' || wrongUserInputChecker.peek() == '-' || wrongUserInputChecker.peek() == 'X' || wrongUserInputChecker.peek() == '÷') && expression.charAt(i) == '.') {
                    Toast.makeText(getApplicationContext(), "Please check your input", Toast.LENGTH_LONG).show();
                    mistake=true;
                    wrongUserInputChecker.clear();

                    System.out.println("I'm at after \"    else if (expression.charAt(i) == '.') {  \" ");

                    break;

                }

                else if (wrongUserInputChecker.empty() == false && wrongUserInputChecker.peek() == '(' && expression.charAt(i) == '.') {
                    Toast.makeText(getApplicationContext(), "Please check your input", Toast.LENGTH_LONG).show();
                    mistake=true;
                    wrongUserInputChecker.clear();

                    System.out.println("I'm at after \"    else if (expression.charAt(i) == '.') {  \" ");
                    break;

                }
                else if (wrongUserInputChecker.empty() == false && wrongUserInputChecker.peek() == ')' && expression.charAt(i) == '.') {
                    Toast.makeText(getApplicationContext(), "Please check your input", Toast.LENGTH_LONG).show();
                    mistake=true;
                    wrongUserInputChecker.clear();

                    System.out.println("I'm at after \"    else if (expression.charAt(i) == '.') {  \" ");
                    break;
                }
                else {
                    wrongUserInputChecker.push(expression.charAt(i));
                }
            }


           else if (expression.charAt(i)=='0' || expression.charAt(i)=='1' || expression.charAt(i)=='2' ||
                    expression.charAt(i)=='3' || expression.charAt(i)=='4' || expression.charAt(i)=='5' ||
                    expression.charAt(i)=='6' || expression.charAt(i)=='7' || expression.charAt(i)=='8' ||
                    expression.charAt(i)=='9'   ){

                System.out.println("I'm at after XXXXXXXXXXXXXXXXXXXXXXXXXXX ");



                if(wrongUserInputChecker.empty() == false && wrongUserInputChecker.peek() == '÷' && expression.charAt(i)=='0'){
                    System.out.println("I'm here");
                    Toast.makeText(getApplicationContext(), "Can't devide by Zero", Toast.LENGTH_LONG).show();
                    mistake=true;
                    wrongUserInputChecker.clear();

                    System.out.println("I'm at after \"      else if (expression.charAt(i)=='0' || expression.charAt(i)=='1' || expression.charAt(i)=='2' ||\n" +
                            "                    expression.charAt(i)=='3' || expression.charAt(i)=='4' || expression.charAt(i)=='5' ||\n" +
                            "                    expression.charAt(i)=='6' || expression.charAt(i)=='7' || expression.charAt(i)=='8' ||\n" +
                            "                    expression.charAt(i)=='9'   ){  \" ");
                    break;
                }

               else if(wrongUserInputChecker.empty() == false && wrongUserInputChecker.peek() == ')' && (expression.charAt(i)=='0' || expression.charAt(i)=='1' || expression.charAt(i)=='2' ||
                        expression.charAt(i)=='3' || expression.charAt(i)=='4' || expression.charAt(i)=='5' ||
                        expression.charAt(i)=='6' || expression.charAt(i)=='7' || expression.charAt(i)=='8' ||
                        expression.charAt(i)=='9'   )){
                    wrongUserInputChecker.push('X');
                    wrongUserInputChecker.push(expression.charAt(i));
                }
                else {
                    wrongUserInputChecker.push(expression.charAt(i));
                }
            }

        }

        System.out.println(wrongUserInputChecker);
        expression="";
        while (wrongUserInputChecker.empty()==false){
            expression+=wrongUserInputChecker.pop();
        }

        String temp;
        temp=expression;

        expression="";

        for(int i=temp.length()-1;i>=0;i--){
            expression+=temp.charAt(i);
        }

        wrongUserInputChecker.clear();
    }



    public void balanceBrackets(){
        int balance=0;

        for(int i=0;i<expression.length();i++){
            if(expression.charAt(i)=='('){
                balance=balance+1;
            }
            else if(expression.charAt(i)==')'){
                balance=balance-1;
            }
        }

        if(balance==0){
            balanceBrackets=true;
        }
        else{
            balanceBrackets=false;
        }
    }
}


