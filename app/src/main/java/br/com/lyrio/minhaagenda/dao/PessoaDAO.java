package br.com.lyrio.minhaagenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.lyrio.minhaagenda.modelo.Pessoa;

public class PessoaDAO extends SQLiteOpenHelper {

    public PessoaDAO(Context context) {
        super(context, "minhaagenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE pessoas ( " +
                "id INTEGER PRIMARY KEY" +
                ", nome TEXT NOT NULL" +
                ", endereco TEXT" +
                ", telefone TEXT" +
                ", site TEXT" +
                ", nota REAL"+
                ", caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE Pessoas ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql); // indo para versao 2
        }

    }

    public void insere(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDaPessoa(pessoa);


        db.insert("pessoas", null, dados);

    }

    private ContentValues pegaDadosDaPessoa(Pessoa pessoa) {
        ContentValues dados = new ContentValues();
        dados.put ("nome", pessoa.getNome());
        dados.put("endereco", pessoa.getEndereco());
        dados.put("telefone", pessoa.getTelefone());
        dados.put("site", pessoa.getSite());
        dados.put("nota", pessoa.getNota());
        dados.put("caminhoFoto", pessoa.getCaminhoFoto());
        return dados;
    }

    public List<Pessoa> buscaPessoas() {
        String sql = "SELECT * FROM pessoas p order by p.nome";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<Pessoa> pessoas = new ArrayList<Pessoa>();
        while(c.moveToNext()){
            Pessoa pessoa = new Pessoa();
            pessoa.setId(c.getLong(c.getColumnIndex("id")));
            pessoa.setNome(c.getString(c.getColumnIndex("nome")));
            pessoa.setEndereco(c.getString(c.getColumnIndex("endereco")));
            pessoa.setTelefone(c.getString(c.getColumnIndex("telefone")));
            pessoa.setSite(c.getString(c.getColumnIndex("site")));
            pessoa.setNota(c.getDouble(c.getColumnIndex("nota")));
            pessoa.setCaminhoFoto((c.getString(c.getColumnIndex("caminhoFoto"))));
            pessoas.add(pessoa);

        }
        c.close();

        return pessoas;
    }

    public void deleta(Pessoa pessoa) {
    SQLiteDatabase db = getWritableDatabase();
    String[] params = {pessoa.getId().toString()};
    db.delete("Pessoas", "id = ?", params );
    }

    public void altera(Pessoa pessoa) {
        SQLiteDatabase db =getWritableDatabase();

        ContentValues dados = pegaDadosDaPessoa(pessoa);
        String[] params = {pessoa.getId().toString()};
        db.update("Pessoas", dados,"id = ?", params);

    }
}
