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
        super(context, "minhaagenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE pessoas ( " +
                "id INTEGER PRIMARY KEY" +
                ", nome TEXT NOT NULL" +
                ", endereco TEXT" +
                ", telefone TEXT" +
                ", site TEXT" +
                ", nota REAL );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS pessoas;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put ("nome", pessoa.getNome());
        dados.put("endereco", pessoa.getEndereco());
        dados.put("telefone", pessoa.getTelefone());
        dados.put("site", pessoa.getSite());
        dados.put("nota", pessoa.getNota());


        db.insert("pessoas", null, dados);

    }

    public List<Pessoa> buscaPessoas() {
        String sql = "SELECT * FROM pessoas";
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

            pessoas.add(pessoa);

        }
        c.close();

        return pessoas;
    }
}
