package br.com.lyrio.minhaagenda.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.lyrio.minhaagenda.modelo.Pessoa;

/**
 * Created by renan on 15/01/16.
 */
public class PessoaConverter {
    public String converteParaJSON(List<Pessoa> pessoas) {
        JSONStringer js = new JSONStringer();

        try {
            js.object().key("list").array().object().key("aluno").array();
            for (Pessoa pessoa : pessoas) {
                js.object();
                js.key("nome").value(pessoa.getNome());
                js.key("nota").value(pessoa.getNota());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();
    }
}
