package com.neocaptainnemo.calculator_master;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.neocaptainnemo.calculator_master.R;
import com.neocaptainnemo.calculator_master.CalculatorModel;
import com.neocaptainnemo.calculator_master.MainActivity;
import com.neocaptainnemo.calculator_master.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    CalculatorModel calculator;

    private TextView text; //поле для ввода расчетов
    private static final String KEY_CALCULATOR = "Calculator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        int[] numberIds = new int[]{
                R.id.button_zero,
                R.id.button_one,
                R.id.button_two,
                R.id.button_three,
                R.id.button_four,
                R.id.button_five,
                R.id.button_six,
                R.id.button_seven,
                R.id.button_eight,
                R.id.button_nine

        };

        int[] actionsIds = new int[]{
                R.id.button_AC,
                R.id.button_C,
                R.id.button_addition,
                R.id.button_dot,
                R.id.button_multiplication,
                R.id.button_division,
                R.id.button_percent,
                R.id.button_subtraction,
                R.id.button_equal
        };

        text = findViewById(R.id.text);

        calculator = new CalculatorModel();

        View.OnClickListener numberButtonClickListener = view -> {
            calculator.onNumPressed(view.getId());
            text.setText(calculator.getText());
        };

        View.OnClickListener actionButtonOnclickListener = view -> {
            calculator.onActionPressed(view.getId());
            text.setText(calculator.getText());
        };
        for (int numberId : numberIds) {
            findViewById(numberId).setOnClickListener(numberButtonClickListener);
        }

        for (int actionsId : actionsIds) {
            findViewById(actionsId).setOnClickListener(actionButtonOnclickListener);
        }
    }

    //сохранение данных
    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putParcelable(KEY_CALCULATOR, calculator);
    }

    //восстановление данных
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        calculator = instanceState.getParcelable(KEY_CALCULATOR);
        text.setText(calculator.getText());
    }

    //обработка кнопки
    private void initView() {
        Button btnSettings = findViewById(R.id.button_choice_of_theme);
        btnSettings.setOnClickListener(v -> {
//// Чтобы стартовать активити, надо подготовить интент
//// В данном случае это будет явный интент, поскольку здесь
//передаётся класс активити
            Intent runSettings = new Intent(MainActivity.this,
                    SettingsActivity.class);
//// Метод стартует активити, указанную в интенте
            startActivity(runSettings);
        });
    }
}