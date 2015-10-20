package pos.com.br.easy_game.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import pos.com.br.easy_game.R;
import pos.com.br.easy_game.async.GenericAsyncTask;
import pos.com.br.easy_game.entity.Atualizavel;
import pos.com.br.easy_game.entity.Usuario;
import pos.com.br.easy_game.enuns.Method;

public class LoginActivity extends Activity implements Atualizavel {

    private String servico = "login";
    private EditText etLogin = null;
    private EditText etSenha = null;
    private Button btLogar;
    private Boolean camposValidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogar = (Button) findViewById(R.id.btn_logar);
        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    camposValidos = true;
                    etLogin = (EditText) findViewById(R.id.txt_login);
                    etSenha = (EditText) findViewById(R.id.txt_senha);
                    if (etLogin.getText().toString().equals("")) {
                        camposValidos = false;
                        Toast.makeText(getApplicationContext(), "Login obrigatório.", Toast.LENGTH_SHORT).show();
                    }
                    if (etSenha.getText().toString().equals("")) {
                        camposValidos = false;
                        Toast.makeText(getApplicationContext(), "Senha obrigatória.", Toast.LENGTH_SHORT).show();

                    }
                    if (camposValidos) {
                        JSONObject autenticar = new JSONObject();
                        String login = etLogin.getText().toString();
                        autenticar.put("login", login);
                        String senha = etSenha.getText().toString();
                        autenticar.put("senha", senha);
                        new GenericAsyncTask(LoginActivity.this, LoginActivity.this, Method.POST, String.format("%s", servico), autenticar.toString()).execute();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });
    }


    @Override
    public void atualizar(JSONObject jsonObject) throws JSONException {
        Gson gson = new Gson();
        if (jsonObject.has("objeto")) {

            if ("true".equals(jsonObject.getJSONObject("objeto").getString("retorno"))){
                Intent telaInicial = new Intent(LoginActivity.this, TelaInicialActivity.class);
                startActivity(telaInicial);
                finish();
            } else {
                Toast.makeText(this, "Login/Senha Inválidos!", Toast.LENGTH_SHORT).show();
            }

        } else if (jsonObject.has("erro")) {
            //TODO Toast;
            Toast.makeText(this, "Erro ao logar!", Toast.LENGTH_SHORT).show();
        }
    }
}
