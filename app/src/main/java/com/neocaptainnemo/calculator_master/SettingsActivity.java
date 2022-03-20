package com.neocaptainnemo.calculator_master;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.neocaptainnemo.calculator_master.R;
import com.google.android.material.radiobutton.MaterialRadioButton;

public class SettingsActivity extends AppCompatActivity {

    private static final String APP_THEME = "Theme_Calculator";

    private static final int APP_THEME_LIGHT_CODE_STYLE = 0;
    private static final int APP_THEME_DARK_CODE_STYLE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.AppThemeLight));
        setContentView(R.layout.activity_settings);
        initThemeChooser();

        Button btnReturn = findViewById(R.id.btnReturn);

        btnReturn.setOnClickListener(v -> {
// Метод finish() завершает активити
            finish();
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
        button.setOnClickListener(v -> {
            // сохраним настройки
            setAppTheme(codeStyle);
            // пересоздадим активити, чтобы тема применилась
            recreate();
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