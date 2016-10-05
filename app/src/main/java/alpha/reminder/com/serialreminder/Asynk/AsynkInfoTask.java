package alpha.reminder.com.serialreminder.Asynk;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import alpha.reminder.com.serialreminder.Singletone.SingletoneInfo;

/**
 *
 */

public class AsynkInfoTask extends AsyncTask<Void, Void, Void> {

    private String id;
    private Context context;
    private Delegate delegate;
    private ProgressDialog progressDialog;
    private static String plot = "";

    public AsynkInfoTask(Context context, String id, Delegate delegate) {
        this.id = id;
        this.context = context;
        this.delegate = delegate;
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public interface Delegate {
        public void onPostExecuteDone();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        JSONObject resultObject = requestResult("http://www.omdbapi.com/?i=" + id);
        if (resultObject != null) {
            plot = (String) resultObject.get("Plot");
        } else {
            Log.w("Film info", "Very bad");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        delegate.onPostExecuteDone();
        progressDialog.dismiss();
    }

    public static String getPlot() {
        return plot;
    }

    private JSONObject requestResult(String request) {

        JSONParser parser = new JSONParser();
        URL url = null;
        try {
            url = new URL(request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject resulObject = null;

        try {
            if (connection != null && connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String response = new String();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                response += reader.readLine();
                resulObject = (JSONObject) parser.parse(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resulObject;
    }

    private void addInfo(JSONObject info) {

    }
}
