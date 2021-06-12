package com.neocaptainnemo.calculator_master;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.neocaptainnemo.calculator_master.R;
import com.google.android.material.radiobutton.MaterialRadioButton;

import org.w3c.dom.Text;

public class SettingsActivity extends AppCompatActivity {

    public static final String APP_THEME = "Theme_Calculator";
    public static final String KEY_RESULT = "KEY_RESULT";

    private static final int APP_THEME_LIGHT_CODE_STYLE = 0;
    private static final int APP_THEME_DARK_CODE_STYLE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.AppThemeLight));
        setContentView(R.layout.activity_settings);
        TextView textView = findViewById(R.id.txt);
        String value = getIntent().getStringExtra(APP_THEME);
        textView.setText(value);
        findViewById(R.id.authorized).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_RESULT, "Autorized");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        initThemeChooser();

        Button btnReturn = findViewById(R.id.btnReturn);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Метод finish() завершает активити
                finish();
            }
        });
        findViewById(R.id.not_authorized).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }
//инициализация кнопок
    private void initThemeChooser() {
        initRadioButton(findViewById(R.id.radioButtonMaterialLight),
                APP_THEME_LIGHT_CODE_STYLE);
        initRadioButton(findViewById(R.id.radioButtonMaterialDark),
                APP_THEME_DARK_CODE_STYLE);
        RadioGroup rg = findViewById(R.id.radioButtons);
        ((MaterialRadioButton) rg.getChildAt(getCodeStyle(APP_THEME_LIGHT_CODE_STYLE)))
                .setChecked(true);
    }

    // Все инициализации кнопок очень похожи, поэтому создадим метод для переиспользования
    private void initRadioButton(View button, final int codeStyle) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // сохраним настройки
                setAppTheme(codeStyle);
                // пересоздадим активити, чтобы тема применилась
                recreate();
            }
        });
    }

    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));
    }

    // Сохранение настроек
    private void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        // Настройки сохраняются посредством специального класса editor.
        SharedPreferences.Editor editor = sharedPref.edit();
        //Сохраняем настройки: ключ-значение
        editor.putInt(APP_THEME, codeStyle);
        //отложенное сохранение
        editor.apply();
    }

    // Чтение настроек, параметр «тема»
    private int getCodeStyle(int codeStyle) {
        // Работаем через специальный класс сохранения и чтения настроек
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        //Прочитать тему, если настройка не найдена - взять по умолчанию
        return sharedPref.getInt(APP_THEME, codeStyle);
    }

    private int codeStyleToStyleId(int codeStyle) {
        switch (codeStyle) {
            case APP_THEME_LIGHT_CODE_STYLE:
                return R.style.AppThemeLight;
            case APP_THEME_DARK_CODE_STYLE:
                return R.style.AppThemeDark;
            default:
                return R.style.my_style_calc_button_numeral;
        }
    }
}