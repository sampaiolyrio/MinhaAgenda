package br.com.lyrio.minhaagenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.lyrio.minhaagenda.ListaPessoasActivity;
import br.com.lyrio.minhaagenda.R;
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
        Pessoa pessoa = pessoas.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = converterView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView campoNome = (TextView) view.findViewById(R.id.item_nome);
        campoNome.setText(pessoa.getNome());

        TextView campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
        campoTelefone.setText(pessoa.getTelefone());

        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        String caminhoFoto = pessoa.getCaminhoFoto();
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setRotation(270);
        }

        return view;
    }
}
