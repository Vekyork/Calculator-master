package com.neocaptainnemo.calculator_master;

import android.os.Parcel;
import android.os.Parcelable;

import com.neocaptainnemo.calculator_master.R;

public class CalculatorModel implements Parcelable {
    private int firstArg;
    private int secondArg;

    private final StringBuilder INPUT_STR = new StringBuilder();

    private int actionSelected;//какое действие выбрал пользователь

    private State state;

    public CalculatorModel() {
        state = State.FIRST_ARG_INPUT;
    }

    public static final Creator<CalculatorModel> CREATOR = new Creator<CalculatorModel>() {
        @Override
        public CalculatorModel createFromParcel(Parcel in) {
            return new CalculatorModel(in);
        }

        @Override
        public CalculatorModel[] newArray(int size) {
            return new CalculatorModel[size];
        }
    };

    protected CalculatorModel(Parcel in) {
        firstArg = in.readInt();
        secondArg = in.readInt();
        actionSelected = in.readInt();
    }

    public void onActionPressed(int actionId) {
        if (actionId == R.id.button_equal && state == State.SECOND_ARG_INPUT && INPUT_STR.length() > 0) {
            secondArg = Integer.parseInt(INPUT_STR.toString());
            state = State.RESULT_SHOW;
            INPUT_STR.setLength(0);
            switch (actionSelected) {
                case R.id.button_addition:
                    INPUT_STR.append(firstArg + secondArg);
                    break;
                case R.id.button_subtraction:
                    INPUT_STR.append(firstArg - secondArg);
                    break;
                case R.id.button_multiplication:
                    INPUT_STR.append(firstArg * secondArg);
                    break;
                case R.id.button_division:
                    INPUT_STR.append(firstArg / secondArg);
                    break;
            }
        } else if (actionId == R.id.button_AC) {
            INPUT_STR.setLength(0);
            state = State.FIRST_ARG_INPUT;
        } else if (actionId == R.id.button_C && INPUT_STR.length() > 0) {
            INPUT_STR.setLength(INPUT_STR.length() - 1);
        } else if (actionId == R.id.button_percent && INPUT_STR.length() > 0) {
            Double firstArgDouble = Double.parseDouble(INPUT_STR.toString());
            state = State.RESULT_SHOW;
            INPUT_STR.setLength(0);//не забываем очищать поле
            Double secondArgDouble = 100.0;
            INPUT_STR.append(firstArgDouble / secondArgDouble);
        } else if (INPUT_STR.length() > 0 && state == State.FIRST_ARG_INPUT) {
            firstArg = Integer.parseInt(INPUT_STR.toString());
            state = State.OPERATION_SELECTED;
            actionSelected = actionId;
        }
    }

    public void onNumPressed(int buttonId) {

        if (state == State.RESULT_SHOW) {
            state = State.FIRST_ARG_INPUT; //если нам показывается результат, то переходим к
            // вводу первого аргумента
            INPUT_STR.setLength(0);
        }

        if (state == State.OPERATION_SELECTED) {
            state = State.SECOND_ARG_INPUT;
            INPUT_STR.setLength(0);
        }

        if (INPUT_STR.length() < 9) {
            switch (buttonId) {
                case R.id.button_zero:
                    INPUT_STR.append("0");
                    break;
                case R.id.button_one:
                    INPUT_STR.append("1");
                    break;
                case R.id.button_two:
                    INPUT_STR.append("2");
                    break;
                case R.id.button_three:
                    INPUT_STR.append("3");
                    break;
                case R.id.button_four:
                    INPUT_STR.append("4");
                    break;
                case R.id.button_five:
                    INPUT_STR.append("5");
                    break;
                case R.id.button_six:
                    INPUT_STR.append("6");
                    break;
                case R.id.button_seven:
                    INPUT_STR.append("7");
                    break;
                case R.id.button_eight:
                    INPUT_STR.append("8");
                    break;
                case R.id.button_nine:
                    INPUT_STR.append("9");
                    break;
            }
        }

    }

    public String getText() {
        StringBuilder str = new StringBuilder();
        switch (state) {
            case OPERATION_SELECTED:
                return str.append(firstArg).append(' ')
                        .append(getOperationChar())
                        .toString();
            case SECOND_ARG_INPUT:
                return str.append(firstArg).append(' ')
                        .append(getOperationChar())
                        .append(' ')
                        .append(INPUT_STR)
                        .toString();
            case RESULT_SHOW:
                return str.append(firstArg).append(' ')
                        .append(getOperationChar())
                        .append(' ')
                        .append(secondArg)
                        .append(" = ")
                        .append(INPUT_STR.toString())
                        .toString();
            default:
                return INPUT_STR.toString();
        }
    }

    private char getOperationChar() {
        switch (actionSelected) {
            case R.id.button_percent:
                return '%';
            case R.id.button_dot:
                return '.';
            case R.id.button_addition:
                return '+';
            case R.id.button_subtraction:
                return '-';
            case R.id.button_multiplication:
                return '*';
            case R.id.button_division:
            default:
                return '/';
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(firstArg);
        dest.writeInt(secondArg);
        dest.writeInt(actionSelected);
    }

    private enum State {
        FIRST_ARG_INPUT,
        OPERATION_SELECTED,
        SECOND_ARG_INPUT,
        RESULT_SHOW,
    }

}
