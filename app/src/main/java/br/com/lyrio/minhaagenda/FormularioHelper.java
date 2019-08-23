package br.com.lyrio.minhaagenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.lyrio.minhaagenda.modelo.Pessoa;

public class FormularioHelper {
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private Pessoa pessoa;
    private ImageView campoFoto;


    public FormularioHelper(FormularioActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
         campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
         campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
         campoSite = (EditText) activity.findViewById(R.id.formulario_site);
         campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
         pessoa = new Pessoa();
         campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);


    }

    public Pessoa pegaPessoa() {
        pessoa.setNome(campoNome.getText().toString());
        pessoa.setEndereco(campoEndereco.getText().toString());
        pessoa.setTelefone(campoTelefone.getText().toString());
        pessoa.setSite(campoSite.getText().toString());
        pessoa.setNota(Double.valueOf(campoNota.getProgress()));
        pessoa.setCaminhoFoto((String) campoFoto.getTag());

        return pessoa;
    }

    public void preencheFormulario(Pessoa pessoa) {

        campoNome.setText(pessoa.getNome());
        campoEndereco.setText(pessoa.getEndereco());
        campoTelefone.setText(pessoa.getTelefone());
        campoSite.setText(pessoa.getSite());
        campoNota.setProgress(pessoa.getNota().intValue());
        this.pessoa = pessoa;
        carregaImagem(pessoa.getCaminhoFoto());
    }

    public void carregaImagem(String caminhoFoto) {
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 400, 300, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setRotation(270);
            campoFoto.setTag(caminhoFoto);
        }
    }
}
