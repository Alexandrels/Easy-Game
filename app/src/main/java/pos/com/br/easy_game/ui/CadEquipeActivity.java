package pos.com.br.easy_game.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pos.com.br.easy_game.R;
import pos.com.br.easy_game.async.GenericAsyncTask;
import pos.com.br.easy_game.entity.Atualizavel;
import pos.com.br.easy_game.entity.Jogador;
import pos.com.br.easy_game.enuns.Method;
import pos.com.br.easy_game.listUtil.ListAdapterCheckboxJogador;

public class CadEquipeActivity extends Activity implements Atualizavel {
    private ListView listaJogadores;
    private String servicoJogador = "jogador";
    private String servicoEquipe = "equipe";
    private List<Jogador> jogadores;
    private ListAdapterCheckboxJogador adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_equipe);

        listaJogadores = (ListView) findViewById(R.id.listaJogadores);

        new GenericAsyncTask(this, this, Method.GET, servicoJogador).execute();

        Button novoJogador = (Button) findViewById(R.id.btn_add_equipe);
        novoJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!adapter.getSelecionados().isEmpty()) {
                    Toast.makeText(v.getContext(), "Não esta vazia tem :" + adapter.getSelecionados().size(), Toast.LENGTH_SHORT).show();
                    EditText ttNome = (EditText) findViewById(R.id.nomeJogador);
                    try {
                        JSONObject equipe = new JSONObject();
                        JSONArray jogadores = new JSONArray();
                        if (ttNome != null) {
                            String nome = ttNome.getText().toString();
                            equipe.put("nome", nome);
                        }
                        for (Jogador jogador : adapter.getSelecionados()) {
                            JSONObject jogadorJson = new JSONObject();
                            jogadorJson.put("id", jogador.getId().toString());
                            jogadores.put(jogadorJson);
                        }
                        equipe.put("jogadores",jogadores);
                        new GenericAsyncTask(CadEquipeActivity.this, CadEquipeActivity.this, Method.POST, String.format("%s", servicoEquipe), equipe.toString()).execute();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(v.getContext(), "esta vazia tem :", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void atualizar(JSONObject jsonObject) {
        Gson gson = new Gson();
        jogadores = new ArrayList<>();
        if (jsonObject.has("equipe")) {
            Toast.makeText(this, "Equipe cadastrada", Toast.LENGTH_SHORT).show();

        } else if (jsonObject.has("array")) {
            try {
                JSONArray array = jsonObject.getJSONArray("array");
                for (int i = 0; i < array.length(); i++) {
                    jogadores.add(gson.fromJson(array.getJSONObject(i).toString(), Jogador.class));
                }
                adapter = new ListAdapterCheckboxJogador(this, R.layout.activity_cad_equipe, jogadores);
                listaJogadores.setAdapter(adapter);
                adapter.getSelecionados();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (jsonObject.has("erro")) {
            //TODO Toast;
        }
    }
}
