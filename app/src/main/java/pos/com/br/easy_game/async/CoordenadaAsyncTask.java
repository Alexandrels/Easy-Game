package pos.com.br.easy_game.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import pos.com.br.easy_game.classes.CoordenadasService;
import pos.com.br.easy_game.entity.Atualizavel;

/**
 * Created by alexandre on 26/09/15.
 */
public class CoordenadaAsyncTask extends AsyncTask<Void, Void, JSONObject> {
    private Atualizavel atualizavel;
    private Context context;
    private JSONObject jsonObject;

    public CoordenadaAsyncTask(Atualizavel atualizavel,Context context) {
        super();
        this.atualizavel = atualizavel;
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        jsonObject = new CoordenadasService(context).getCoordenadas();
        if (jsonObject != null) {
            return jsonObject;
        }

        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        try {
            atualizavel.atualizar(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
