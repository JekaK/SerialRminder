package alpha.reminder.com.serialreminder.Asynk;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 *
 */

public class AsynkInfoTask extends AsyncTask<Void, Void, Void> {

    private String id;
    private Context context;
    private Delegate delegate;
    private ProgressDialog progressDialog;

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
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        delegate.onPostExecuteDone();
        progressDialog.dismiss();
    }
}
