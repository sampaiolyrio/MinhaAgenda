package br.com.lyrio.minhaagenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.lyrio.minhaagenda.modelo.Pessoa;

public class PessoaDAO extends SQLiteOpenHelper {

    public PessoaDAO(@androidx.annotation.Nullable Context context) {
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
}
