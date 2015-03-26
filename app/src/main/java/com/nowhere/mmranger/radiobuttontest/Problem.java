package com.nowhere.mmranger.radiobuttontest;

import java.util.Random;

/**
 * Created by mmranger on 1/23/15.
 */
public class Problem {
    private int firstNumber, secondNumber, userAttempt, correctAnswer;
    private char charSign;
    private String strSign;

    public void setCharSign(char charSign) {
        this.charSign = charSign;
    }
    public char getCharSign() {
        return charSign;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }
    public int getFirstNumber() {
        return firstNumber;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(){
        switch (getCharSign()){
            case '+': this.correctAnswer = getFirstNumber() + getSecondNumber();
                break;
            case '*': this.correctAnswer = getFirstNumber() * getSecondNumber();
                break;
            case '-': this.correctAnswer = getFirstNumber() - getSecondNumber();
                break;
        }
    }

    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }
    public int getSecondNumber() {
        return secondNumber;
    }

    public void setStrSign(String strSign) {
        this.strSign = strSign;
    }
    public String getStrSign() {
        return strSign;
    }

    public void setUserAttempt(int userAttempt) {
        this.userAttempt = userAttempt;
    }
    public int getUserAttempt() {
        return userAttempt;
    }

    public Problem(char charSign, String strSign){
        //Generates the two random numbers
        setCharSign(charSign);
        setStrSign(strSign);
        //Puts the larger number to left for subtraction
        //to avoid negative results
        if(charSign == '-') {
            int aNum = GenerateRandom();
            int bNum = GenerateRandom();
            if(aNum > bNum){
                setFirstNumber(aNum);
                setSecondNumber(bNum);
            }else{
                setFirstNumber(bNum);
                setSecondNumber(aNum);
            }
        }else{
            setFirstNumber(GenerateRandom());
            setSecondNumber(GenerateRandom());
        }
        setCorrectAnswer();
    }

    private int GenerateRandom() {
        //Method generates a random number between 1 and 10
        int START = 1;
        int END = 10;
        Random random = new Random();

        if (START > END) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }

        return random.nextInt(END)+1;
    }
}
