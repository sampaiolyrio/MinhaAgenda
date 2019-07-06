package br.com.lyrio.minhaagenda;

import android.widget.EditText;
import android.widget.RatingBar;

import br.com.lyrio.minhaagenda.modelo.Pessoa;

public class FormularioHelper {
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private Pessoa pessoa;


    public FormularioHelper(FormularioActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
         campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
         campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
         campoSite = (EditText) activity.findViewById(R.id.formulario_site);
         campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
         pessoa = new Pessoa();


    }

    public Pessoa pegaPessoa() {
        pessoa.setNome(campoNome.getText().toString());
        pessoa.setEndereco(campoEndereco.getText().toString());
        pessoa.setTelefone(campoTelefone.getText().toString());
        pessoa.setSite(campoSite.getText().toString());
        pessoa.setNota(Double.valueOf(campoNota.getProgress()));

        return pessoa;
    }

    public void preencheFormulario(Pessoa pessoa) {

        campoNome.setText(pessoa.getNome());
        campoEndereco.setText(pessoa.getEndereco());
        campoTelefone.setText(pessoa.getTelefone());
        campoSite.setText(pessoa.getSite());
        campoNota.setProgress(pessoa.getNota().intValue());
        this.pessoa = pessoa;
    }
}
