package pos.com.br.easy_game.listUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pos.com.br.easy_game.R;
import pos.com.br.easy_game.entity.Jogador;

/**
 * Created by alexandre on 17/10/15.
 */
public class ListAdapterCheckboxJogador extends ArrayAdapter<Jogador> {

    public List<Jogador> getSelecionados() {
        return selecionados;
    }

    private final List<Jogador> selecionados = new ArrayList<Jogador>();

    public ListAdapterCheckboxJogador(Context context, int resource, List<Jogador> listJogadores) {
        super(context, resource,listJogadores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_row_checkbox_jogador, null);

        }
        Jogador p = getItem(position);
        if (p != null) {
            TextView tt2 = (TextView) v.findViewById(R.id.rowJogadorNome);
            TextView tt3 = (TextView) v.findViewById(R.id.rowJogadorPosicao);

            if (tt2 != null) {
                tt2.setText(p.getNome().toString());
            }
            if (tt3 != null) {
                tt3.setText(p.getPosicao().toString());
            }
        }
        // Recuperando o checkbox
        CheckBox chk = (CheckBox) v.findViewById(R.id.chkJogadores);

        // Definindo um "valor" para o checkbox
        chk.setTag(p);

        /** Definindo uma ação ao clicar no checkbox. Aqui poderiamos armazenar um valor chave
         * que identifique o objeto selecionado para que o mesmo possa ser, por exemplo, excluído
         * mais tarde.
         */
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox chk = (CheckBox) v;
                Jogador jogador = (Jogador) chk.getTag();
                if(chk.isChecked()) {
//                    Toast.makeText(getContext(), "Checbox de " + jogador.getId() + " marcado!", Toast.LENGTH_SHORT).show();
                    if(!selecionados.contains(jogador))
                        selecionados.add(jogador);
                } else {
//                    Toast.makeText(getContext(), "Checbox de " + jogador.getId() + " desmarcado!", Toast.LENGTH_SHORT).show();
                    if(selecionados.contains(jogador))
                        selecionados.remove(jogador);
                }
            }
        });
        if(selecionados.contains(p)) {
            chk.setChecked(true);
        } else {
            chk.setChecked(false);
        }

        return v;
    }
}
