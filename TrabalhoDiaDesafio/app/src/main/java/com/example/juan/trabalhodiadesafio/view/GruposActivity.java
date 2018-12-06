package com.example.juan.trabalhodiadesafio.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.juan.trabalhodiadesafio.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;

@EActivity(R.layout.activity_grupos)
@OptionsMenu(R.menu.menu_grupos)
public class GruposActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.grupos));
    }

}
