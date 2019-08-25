package br.com.lyrio.minhaagenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Browser;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.lyrio.minhaagenda.adapter.PessoasAdapter;
import br.com.lyrio.minhaagenda.converter.PessoaConverter;
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

        PessoasAdapter adapter = new PessoasAdapter(this, pessoas);
        listaPessoas.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_pessoas, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enviar_notas:
               PessoaDAO dao = new PessoaDAO( this);
               List<Pessoa> pessoas = dao.buscaPessoas();
               dao.close();

                PessoaConverter conversor = new PessoaConverter();
                String json =  conversor.converteParaJSON(pessoas);

                WebClient client = new WebClient();
                String resposta = client.post(json);


                Toast.makeText(this, json, Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Pessoa pessoa = (Pessoa) listaPessoas.getItemAtPosition(info.position);

        MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(ActivityCompat.checkSelfPermission(ListaPessoasActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaPessoasActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {

                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + pessoa.getTelefone()));
                    startActivity(intentLigar);
                }

                    return false;

                }
            });

        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:"+pessoa.getTelefone()));
        itemSMS.setIntent(intentSMS);

        MenuItem itemMapa= menu.add("Visualizar no Mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q="+pessoa.getEndereco()));
        itemMapa.setIntent(intentMapa);

        MenuItem itemSite = menu.add("Visitar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        String site = pessoa.getSite();
        if(!site.startsWith("http://")){
         site = "http://" + site;

        }

        intentSite.setData(Uri.parse(pessoa.getSite()));
        itemSite.setIntent(intentSite);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                PessoaDAO dao = new PessoaDAO(ListaPessoasActivity.this);
                dao.deleta(pessoa);
                dao.close();
                carregaLista();

                return false;
            }
        });

    }




}
