package pos.com.br.easy_game.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import pos.com.br.easy_game.R;

public class TelaInicialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tela_inicial, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_tela_inicial_cad_usuario:
                Intent cadUsuario = new Intent(this, CadUsuarioActivity.class);
                startActivity(cadUsuario);
                break;
            case R.id.menu_tela_inicial_cad_jogador:
                Intent cadJogador = new Intent(this, CadJogadorActivity.class);
                startActivity(cadJogador);
                break;
            case R.id.menu_tela_inicial_mapa:
                Intent mapa = new Intent(this, MapsTeamActivity.class);
                startActivity(mapa);
                break;
            case R.id.menu_tela_inicial_list_jogador:
                Intent listJogador = new Intent(this, ListViewJogador.class);
                startActivity(listJogador);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
