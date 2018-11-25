package com.example.juan.trabalhodiadesafio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ListarActivity extends AppCompatActivity {

    private ListView lvRegistros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        lvRegistros = (ListView) findViewById(R.id.lvRegistros);
    }
}
