package com.example.juan.trabalhodiadesafio.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.juan.trabalhodiadesafio.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Adapter extends BaseAdapter {

    private Context c;
//    private Cursor registros;
    private List<JSONObject> lista;
    LayoutInflater inflater;

    public Adapter(Context c, List<JSONObject> lista){
        this.c = c;
        this.lista = lista;
        this.inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
    }


    //Adapter metodos
    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        try {
            return lista.get(i).getInt("idusuario");
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getItemNome(int i) {
        try {
            return lista.get(i).getString("nome");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = inflater.inflate(R.layout.elemento_listar, null);

        TextView tvNome = (TextView) v.findViewById(R.id.tvNome);
        TextView tvExc = (TextView) v.findViewById(R.id.tvExc);

        String nome = "teste";
        String dados = "teste2";
        try {
            nome = lista.get(i).getString("nome");
            dados = lista.get(i).getString("dadosacelerometro");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvNome.setText(nome);
        tvExc.setText(dados);

        return v;
    }


}





























