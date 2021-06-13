package com.neocaptainnemo.calculator_master;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.neocaptainnemo.calculator_master.R;
import com.neocaptainnemo.calculator_master.CalculatorModel;
import com.neocaptainnemo.calculator_master.MainActivity;
import com.neocaptainnemo.calculator_master.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    CalculatorModel calculator;

    private TextView text; //поле для ввода расчетов
    private static final String KEY_CALCULATOR = "КАЛЬКУЛЯТОР";
//    public static final int LOGIN_REQUEST = 1;

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
//                R.id.button_Login,
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

        View.OnClickListener numberButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onNumPressed(view.getId());
                text.setText(calculator.getText());
            }
        };

        View.OnClickListener actionButtonOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onActionPressed(view.getId());
                text.setText(calculator.getText());
            }
        };
        for (int i = 0; i < numberIds.length; i++) {
            findViewById(numberIds[i]).setOnClickListener(numberButtonClickListener);
        }

        for (int i = 0; i < actionsIds.length; i++) {
            findViewById(actionsIds[i]).setOnClickListener(actionButtonOnclickListener);
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
        calculator = (CalculatorModel) instanceState.getParcelable(KEY_CALCULATOR);
        text.setText(calculator.getText());
    }

    //обработка кнопки
    private void initView() {
        Button btnSettings = findViewById(R.id.button_choice_of_theme);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Чтобы стартовать активити, нужно подготовить интент
// В данном случае это будет явный интент, поскольку здесь передаётся класс активити
                Intent runSettings = new Intent(MainActivity.this,
                        SettingsActivity.class);
//                runSettings.putExtra(SettingsActivity.APP_THEME, "Необходимо авторизоваться!");
                runSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
// Метод стартует активити, указанную в интенте
//                startActivityForResult(runSettings, LOGIN_REQUEST);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == LOGIN_REQUEST){
//            if (resultCode == Activity.RESULT_OK){
//                if (data!= null){
//                    Toast.makeText(this, data.getStringExtra(SettingsActivity.KEY_RESULT), Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//    }
}