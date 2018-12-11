package com.example.juan.trabalhodiadesafio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.juan.trabalhodiadesafio.R;
import com.example.juan.trabalhodiadesafio.controller.InfoUsuarioController;
import com.example.juan.trabalhodiadesafio.model.Grupo;
import com.example.juan.trabalhodiadesafio.model.InfoUsuario;

import java.util.List;

public class GrupoAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Grupo> lista;

    public GrupoAdapter(Context context, List<Grupo> lista) {
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
            return lista.get(i).getIdgrupo();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_grupos, null);
            holder = new ViewHolder();

            holder.nome = view.findViewById(R.id.textItem1);
            holder.media = view.findViewById(R.id.textItem2);
            holder.numDeMembros = view.findViewById(R.id.textItem3);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String nome = "";
        Double media = 0D;
        Integer numDeMembros = 0;

        try {
            nome = lista.get(i).getDescricao();

            List<InfoUsuario> listaUsuarios = new InfoUsuarioController().getByGrupo(lista.get(i).getIdgrupo());

            media = calcularMedia(listaUsuarios);

            numDeMembros = listaUsuarios.size();
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.nome.setText(nome);
        holder.media.setText(String.valueOf(media));
        holder.numDeMembros.setText(String.valueOf(numDeMembros));

        return view;
    }

    private static class ViewHolder {
        private TextView nome;
        private TextView media;
        private TextView numDeMembros;
    }

    private Double calcularMedia(List<InfoUsuario> listaUsuarios) {
        Integer numMembros = listaUsuarios.size();
        Double soma = 0D;

        for (InfoUsuario usuario : listaUsuarios) {
            soma += Float.parseFloat(usuario.getDadosacelerometro());
        }

        return soma / numMembros;
    }

}
