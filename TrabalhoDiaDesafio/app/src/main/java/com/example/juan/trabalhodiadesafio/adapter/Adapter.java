package com.example.juan.trabalhodiadesafio.adapter;

import android.content.Context;
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

    private LayoutInflater inflater;
    private List<JSONObject> lista;

    public Adapter(Context context, List<JSONObject> lista) {
        this.inflater = LayoutInflater.from(context);
        this.lista = lista;
    }

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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.elemento_listar, null);
            holder = new ViewHolder();

            holder.nome = view.findViewById(R.id.textItem1);
            holder.nivelAtividade = view.findViewById(R.id.textItem2);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String nome = "teste";
        String dados = "teste2";

        try {
            nome = lista.get(i).getString("nome");
            dados = lista.get(i).getString("dadosacelerometro");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.nome.setText(nome);
        holder.nivelAtividade.setText(dados);

        return view;
    }

    private static class ViewHolder {
        private TextView nome;
        private TextView nivelAtividade;
    }

}






























