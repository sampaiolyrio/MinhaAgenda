package br.com.lyrio.minhaagenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;

import br.com.lyrio.minhaagenda.dao.PessoaDAO;
import br.com.lyrio.minhaagenda.modelo.Pessoa;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper =  new FormularioHelper(this);

        Intent intent = getIntent();
        Pessoa pessoa = (Pessoa) intent.getSerializableExtra("pessoa");
        if (pessoa != null){

            helper.preencheFormulario(pessoa);

        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:

            Pessoa pessoa = helper.pegaPessoa();
            PessoaDAO dao = new PessoaDAO(this);

            if (pessoa.getId() != null){

                dao.altera(pessoa);

            } else{

                dao.insere(pessoa);
            }



            dao.close();


             Toast.makeText(FormularioActivity.this, "Dados " + pessoa.getNome()+" Salvos!", Toast.LENGTH_SHORT).show();


            finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
