package br.com.lyrio.minhaagenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.lyrio.minhaagenda.converter.PessoaConverter;
import br.com.lyrio.minhaagenda.dao.PessoaDAO;
import br.com.lyrio.minhaagenda.modelo.Pessoa;

/**
 * Created by renan on 20/01/16.
 */
public class EnviaPessoasTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private ProgressDialog dialog;

    public EnviaPessoasTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde", "Enviando alunos...", true, true);
    }

    @Override
    protected String doInBackground(Void... params) {
        PessoaDAO dao = new PessoaDAO(context);
        List<Pessoa> pessoas = dao.buscaPessoas();
        dao.close();

        PessoaConverter conversor = new PessoaConverter();
        String json = conversor.converteParaJSON(pessoas);

        WebClient client = new WebClient();
        String resposta = client.post(json);
        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
