package br.com.lyrio.minhaagenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.lyrio.minhaagenda.dao.PessoaDAO;
import br.com.lyrio.minhaagenda.modelo.Pessoa;

public class ListaPessoasActivity extends AppCompatActivity {

    private ListView listaPessoas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoas);

        listaPessoas = (ListView) findViewById(R.id.lista_pessoas);

        listaPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Pessoa pessoa = (Pessoa) listaPessoas.getItemAtPosition(position);

                Intent intentVaiProFormulario = new Intent(ListaPessoasActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("pessoa", pessoa);
                startActivity(intentVaiProFormulario);
            }

        });


        Button novaPessoa = (Button) findViewById(R.id.nova_pessoa);
        novaPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProformulario = new Intent(ListaPessoasActivity.this, FormularioActivity.class);
                startActivity(intentVaiProformulario);
            }
        });

        registerForContextMenu(listaPessoas);

    }

    private void carregaLista() {
        PessoaDAO dao = new PessoaDAO (this);
        List<Pessoa> pessoas = dao.buscaPessoas();
        dao.close();


        ArrayAdapter<Pessoa> adapter = new ArrayAdapter<Pessoa>(this, android.R.layout.simple_list_item_1, pessoas);
        listaPessoas.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Pessoa pessoa = (Pessoa) listaPessoas.getItemAtPosition(info.position);

                PessoaDAO dao = new PessoaDAO(ListaPessoasActivity.this);
                dao.deleta(pessoa);
                dao.close();
                carregaLista();

                return false;
            }
        });

    }




}
