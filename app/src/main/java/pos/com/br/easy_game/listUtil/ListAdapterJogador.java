package pos.com.br.easy_game.listUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pos.com.br.easy_game.R;
import pos.com.br.easy_game.entity.Jogador;


/**
 * Created by alexandre on 05/09/15.
 */
public class ListAdapterJogador extends ArrayAdapter<Jogador> {


    public ListAdapterJogador(Context context, int resource, List<Jogador> listJogadores) {
        super(context, resource,listJogadores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_row_jogador, null);

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
        return v;
    }
}
