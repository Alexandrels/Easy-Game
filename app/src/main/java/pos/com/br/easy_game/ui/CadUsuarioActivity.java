package pos.com.br.easy_game.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import pos.com.br.easy_game.R;
import pos.com.br.easy_game.async.CoordenadaAsyncTask;
import pos.com.br.easy_game.async.GenericAsyncTask;
import pos.com.br.easy_game.classes.CoordenadasService;
import pos.com.br.easy_game.entity.Atualizavel;
import pos.com.br.easy_game.entity.Jogador;
import pos.com.br.easy_game.enuns.Method;

public class CadUsuarioActivity extends Activity implements Atualizavel {

    private String servico = "usuario";
    private JSONObject usuario = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usuario);
        try {
            JSONObject latLong = new CoordenadasService(CadUsuarioActivity.this).getCoordenadas();
            if (latLong != null) {
                usuario.put("latitude", latLong.get("latitude").toString());
                usuario.put("longitude", latLong.get("longitude").toString());
            }
        } catch (JSONException je) {
        }
        ;

        Button salvar = (Button) findViewById(R.id.salvar);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText ttLogin = (EditText) findViewById(R.id.loginUs);
                    EditText ttSenha = (EditText) findViewById(R.id.senhaUs);

                    if (ttLogin != null) {
                        String login = ttLogin.getText().toString();
                        usuario.put("login", login);
                    }
                    if (ttSenha != null) {
                        String senha = ttSenha.getText().toString();
                        usuario.put("senha", senha);
                    }
                    //JSONObject latLong = new CoordenadasService(CadUsuarioActivity.this).getCoordenadas();
//                    if(latLong != null){
//                        usuario.put("latitude",latLong.get("latitude").toString());
//                        usuario.put("longitude",latLong.get("longitude").toString());
//                    }

                    new GenericAsyncTask(CadUsuarioActivity.this, CadUsuarioActivity.this, Method.POST, String.format("%s", servico), usuario.toString()).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });
    }

    public void clear(ViewGroup group) {

        int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
        }
    }

    @Override
    public void atualizar(JSONObject jsonObject) {
        Jogador jogador = new Jogador();
        if (jsonObject.has("objeto")) {
            Toast.makeText(this, "Usuario cadastrado", Toast.LENGTH_SHORT).show();
            clear((ViewGroup) findViewById(R.id.formularioUsuario));
        } else if (jsonObject.has("erro")) {
            //TODO Toast;
            Toast.makeText(this, "Erro ao cadastrar!", Toast.LENGTH_SHORT).show();
        }
    }
}
