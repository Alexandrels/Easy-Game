package pos.com.br.easy_game.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;

import pos.com.br.easy_game.R;
import pos.com.br.easy_game.async.GenericAsyncTask;
import pos.com.br.easy_game.entity.Atualizavel;
import pos.com.br.easy_game.entity.Usuario;
import pos.com.br.easy_game.enuns.Method;


public class CadJogadorActivity extends Activity implements Atualizavel {

    private String servico = "jogador";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_jogador);

        Button salvar = (Button) findViewById(R.id.salvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText ttNome = (EditText) findViewById(R.id.nomeJogdor);
                    EditText ttPosicao = (EditText) findViewById(R.id.posicaoJg);
                    EditText ttTelefone = (EditText) findViewById(R.id.telefoneJg);
                    EditText ttEndereco = (EditText) findViewById(R.id.enderecoJg);
                    JSONObject jogador = new JSONObject();
                    if (ttNome != null) {
                        String nome = ttNome.getText().toString();
                        jogador.put("nome", nome);
                    }
                    if (ttPosicao != null) {
                        String posicao = ttPosicao.getText().toString();
                        jogador.put("posicao", posicao);
                    }
                    if (ttTelefone != null) {
                        String telefone = ttTelefone.getText().toString();
                        jogador.put("telefone", telefone);
                    }
                    if (ttEndereco != null) {
                        String endereco = ttEndereco.getText().toString();
                        jogador.put("endereco", endereco);
                    }
                    Gson gson = new Gson();
                    new GenericAsyncTask(CadJogadorActivity.this, CadJogadorActivity.this, Method.POST, String.format("%s", servico), jogador.toString()).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });


    }

    public void clear(ViewGroup group) {

        if(group != null){
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View view = group.getChildAt(i);
                if (view instanceof EditText) {
                    ((EditText) view).setText("");
                }
            }
        }

    }


    @Override
    public void atualizar(JSONObject jsonObject) {
        Gson gson = new Gson();
        Usuario usuario = new Usuario();
         if (jsonObject.has("objeto")) {
            Toast.makeText(this, "Usuario cadastrado", Toast.LENGTH_SHORT).show();
            clear((ViewGroup) findViewById(R.id.formularioUsuario));
        } else if (jsonObject.has("erro")) {
            //TODO Toast;
            Toast.makeText(this, "Erro ao cadastrar!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
