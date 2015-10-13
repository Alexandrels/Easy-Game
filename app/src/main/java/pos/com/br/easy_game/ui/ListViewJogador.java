package pos.com.br.easy_game.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pos.com.br.easy_game.R;
import pos.com.br.easy_game.async.GenericAsyncTask;
import pos.com.br.easy_game.entity.Atualizavel;
import pos.com.br.easy_game.entity.Jogador;
import pos.com.br.easy_game.enuns.Method;
import pos.com.br.easy_game.listUtil.ListAdapterJogador;


public class ListViewJogador extends Activity implements Atualizavel {
    private ListView listaJogadores;
    private String servico = "jogador";
    private List<Jogador> jogadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_jogador);

        listaJogadores = (ListView) findViewById(R.id.listaJogadores);

        new GenericAsyncTask(this,this, Method.GET,servico).execute();

        Button novoJogador = (Button) findViewById(R.id.btn_add_jogador);
        novoJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiCadJogador = new Intent(ListViewJogador.this,CadJogadorActivity.class);
                startActivity(vaiCadJogador);
            }
        });

    }

    @Override
    public void atualizar(JSONObject jsonObject) {
        Gson gson = new Gson();
        jogadores = new ArrayList<>();
        if (jsonObject.has("array")) {
            try {
                JSONArray array = jsonObject.getJSONArray("array");
                for (int i = 0; i < array.length(); i++) {
                    jogadores.add(gson.fromJson(array.getJSONObject(i).toString(), Jogador.class));
                }
                ListAdapter adapter = new ListAdapterJogador(this,R.layout.activity_list_view_jogador,jogadores);
                listaJogadores.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(jsonObject.has("erro")) {
            //TODO Toast;
        }
    }

}
