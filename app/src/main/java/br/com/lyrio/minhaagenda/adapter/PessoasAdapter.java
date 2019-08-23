package br.com.lyrio.minhaagenda.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.lyrio.minhaagenda.ListaPessoasActivity;
import br.com.lyrio.minhaagenda.modelo.Pessoa;

public class PessoasAdapter extends BaseAdapter {

    private final List<Pessoa> pessoas;
    private final Context context;

    public PessoasAdapter(Context context, List<Pessoa> pessoas) {
        this.context = context;
        this.pessoas = pessoas;

    }

    @Override
    public int getCount() {
        return pessoas.size();

    }

    @Override
    public Object getItem(int position) {
        return pessoas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pessoas.get(position).getId();
    }

    @Override
    public View getView(int position, View converterView, ViewGroup parent) {
        TextView view = new TextView(context);
        Pessoa pessoa = pessoas.get(position);
        view.setText(pessoa.toString());
        return view;
    }
}
