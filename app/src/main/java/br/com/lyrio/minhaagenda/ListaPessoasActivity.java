package br.com.lyrio.minhaagenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.lyrio.minhaagenda.dao.PessoaDAO;
import br.com.lyrio.minhaagenda.modelo.Pessoa;

public class ListaPessoasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoas);

        PessoaDAO dao = new PessoaDAO (this);
        List<Pessoa> pessoas = dao.buscaPessoas();
        dao.close();


        ListView listaPessoas = (ListView) findViewById(R.id.lista_pessoas);
        ArrayAdapter<Pessoa> adapter = new ArrayAdapter<Pessoa>(this, android.R.layout.simple_list_item_1, pessoas);
        listaPessoas.setAdapter(adapter);

        Button novaPessoa = (Button) findViewById(R.id.nova_pessoa);
        novaPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProformulario = new Intent(ListaPessoasActivity.this, FormularioActivity.class);
                startActivity(intentVaiProformulario);
            }
        });

    }
}
