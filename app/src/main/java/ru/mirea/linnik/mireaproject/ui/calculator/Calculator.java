package ru.mirea.linnik.mireaproject.ui.calculator;

import androidx.annotation.NonNull;

import ru.mirea.linnik.mireaproject.R;

public class Calculator {
    private StringBuilder firsOperand = new StringBuilder();
    private StringBuilder secondOperand = new StringBuilder();
    private StringBuilder input = new StringBuilder();
    private Double result;
    private String operation;
    private boolean mayOperation = false;
    private boolean mayEqual = false;
    private State state;

    private enum State {
        firstOperandIn,
        secondOperandIn
    }

    public Calculator() {
        state = State.firstOperandIn;
    }

    @NonNull
    @Override
    public String toString() {
        return input.toString();
    }

    void onClickNumber(int buttonID) {
        if (state == State.firstOperandIn) {
            switch (buttonID) {
                case R.id.btn_0:
                    if (firsOperand.length() != 0) {
                        firsOperand.append("0");
                        input.append("0");
                    }
                    break;
                case R.id.btn_1:
                    firsOperand.append("1");
                    input.append("1");
                    mayOperation = true;
                    break;
                case R.id.btn_2:
                    firsOperand.append("2");
                    input.append("2");
                    mayOperation = true;
                    break;
                case R.id.button_3:
                    firsOperand.append("3");
                    input.append("3");
                    mayOperation = true;
                    break;
                case R.id.btn_4:
                    firsOperand.append("4");
                    input.append("4");
                    mayOperation = true;
                    break;
                case R.id.btn_5:
                    firsOperand.append("5");
                    input.append("5");
                    mayOperation = true;
                    break;
                case R.id.button_6:
                    firsOperand.append("6");
                    input.append("6");
                    mayOperation = true;
                    break;
                case R.id.btn_7:
                    firsOperand.append("7");
                    input.append("7");
                    mayOperation = true;
                    break;
                case R.id.btn_8:
                    firsOperand.append("8");
                    input.append("8");
                    mayOperation = true;
                    break;
                case R.id.button_9:
                    firsOperand.append("9");
                    input.append("9");
                    mayOperation = true;
                    break;

            }

        } else {
            mayOperation = false;
            switch (buttonID) {
                case R.id.btn_0:
                    if (secondOperand.length() != 0) {
                        secondOperand.append("0");
                        input.append("0");
                    }
                    break;
                case R.id.btn_1:
                    secondOperand.append("1");
                    input.append("1");
                    mayEqual = true;
                    break;
                case R.id.btn_2:
                    secondOperand.append("2");
                    input.append("2");
                    mayEqual = true;
                    break;
                case R.id.button_3:
                    secondOperand.append("3");
                    input.append("3");
                    mayEqual = true;
                    break;
                case R.id.btn_4:
                    secondOperand.append("4");
                    input.append("4");
                    mayEqual = true;
                    break;
                case R.id.btn_5:
                    secondOperand.append("5");
                    input.append("5");
                    mayEqual = true;
                    break;
                case R.id.button_6:
                    secondOperand.append("6");
                    input.append("6");
                    mayEqual = true;
                    break;
                case R.id.btn_7:
                    secondOperand.append("7");
                    input.append("7");
                    mayEqual = true;
                    break;
                case R.id.btn_8:
                    secondOperand.append("8");
                    input.append("8");
                    mayEqual = true;
                    break;
                case R.id.button_9:
                    secondOperand.append("9");
                    input.append("9");
                    mayEqual = true;
                    break;
            }
        }
    }

    void onClickOperation(int buttonID) {
        if (mayOperation || mayEqual || buttonID == R.id.button_clear) {
            switch (buttonID) {
                case R.id.button_plus:
                    operation = "+";
                    input.append("+");
                    mayOperation = false;
                    state = State.secondOperandIn;
                    break;
                case R.id.button_minus:
                    operation = "-";
                    input.append("-");
                    mayOperation = false;
                    state = State.secondOperandIn;
                    break;
                case R.id.button_multi:
                    operation = "*";
                    input.append("*");
                    mayOperation = false;
                    state = State.secondOperandIn;
                    break;
                case R.id.button_division:
                    operation = "/";
                    input.append("/");
                    mayOperation = false;
                    state = State.secondOperandIn;
                    break;
                case R.id.button_clear:
                    firsOperand.delete(0, firsOperand.length());
                    secondOperand.delete(0, secondOperand.length());
                    input.delete(0, input.length());
                    mayOperation = false;
                    mayEqual = false;
                    state = State.firstOperandIn;
                    break;
                case R.id.button_equal:
                    if (!mayEqual)
                        break;
                    input.delete(0, input.length());
                    switch (operation) {
                        case "+":
                            result = Double.parseDouble(firsOperand.toString()) + Double.parseDouble(secondOperand.toString());
                            if (result % 1 == 0) {
                                input.append(Math.round(result));
                            } else
                                input.append(result);
                            break;
                        case "-":
                            result = Double.parseDouble(firsOperand.toString()) - Double.parseDouble(secondOperand.toString());
                            if (result % 1 == 0) {
                                input.append(Math.round(result));
                            } else
                                input.append(result);
                            break;
                        case "*":
                            result = Double.parseDouble(firsOperand.toString()) * Double.parseDouble(secondOperand.toString());
                            if (result % 1 == 0) {
                                input.append(Math.round(result));
                            } else
                                input.append(result);
                            break;
                        case "/":
                            result = Double.parseDouble(firsOperand.toString()) / Double.parseDouble(secondOperand.toString());
                            if (result % 1 == 0) {
                                input.append(Math.round(result));
                            } else
                                input.append(result);
                            break;
                    }
                    firsOperand.delete(0, firsOperand.length());
                    if (result != 0) {
                        firsOperand.append(input);
                        mayOperation = true;
                    }
                    mayEqual = false;
                    secondOperand.delete(0, secondOperand.length());
                    state = State.firstOperandIn;
                    break;
            }
        }
    }
}
