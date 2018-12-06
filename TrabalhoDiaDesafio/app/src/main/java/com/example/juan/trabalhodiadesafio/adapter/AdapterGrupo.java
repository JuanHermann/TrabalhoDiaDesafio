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

public class AdapterGrupo extends BaseAdapter {

    private LayoutInflater inflater;
    private List<JSONObject> lista;
    private List<Integer> exercicio;

    public AdapterGrupo(Context context, List<JSONObject> lista, List<Integer> exercicio) {
        this.inflater = LayoutInflater.from(context);
        this.lista = lista;
        this.exercicio = exercicio;
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
            return lista.get(i).getInt("idgrupo");
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
            holder = new AdapterGrupo.ViewHolder();

            holder.nome = view.findViewById(R.id.textItem1);
            holder.nivelAtividade = view.findViewById(R.id.textItem2);

            view.setTag(holder);
        } else {
            holder = (AdapterGrupo.ViewHolder) view.getTag();
        }

        String descricao = "teste";
        String dados = "teste2";

        try {
            descricao = lista.get(i).getString("descricao");
            dados = exercicio.get(i).toString();
//            dados = lista.get(i).getString("dadosacelerometro");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.nome.setText(descricao);
        holder.nivelAtividade.setText(dados);

        return view;
    }

    private static class ViewHolder {
        private TextView nome;
        private TextView nivelAtividade;
    }

}
