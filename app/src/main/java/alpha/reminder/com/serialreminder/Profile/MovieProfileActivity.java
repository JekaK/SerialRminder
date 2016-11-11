package alpha.reminder.com.serialreminder.Profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import alpha.reminder.com.serialreminder.Asynk.AsynkInfoTask;
import alpha.reminder.com.serialreminder.DBHelper.DBHelper;
import alpha.reminder.com.serialreminder.R;

/**
 * Created by Eugeniy Krukun on 08.07.2016.
 */

public class MovieProfileActivity extends Activity implements View.OnClickListener {
    private String title, type, year, film_id;
    private Bitmap poster;
    private ImageView posterImage;
    private TextView viewTitle;
    private ExpandableLayout expandableLayout0, expandableLayout1;
    private TextView viewType, viewYear, viewDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_profile);
        final DBHelper dbHelper = new DBHelper(this);
        viewTitle = (TextView) findViewById(R.id.placeNameHolder);

        expandableLayout0 = (ExpandableLayout) findViewById(R.id.expandable_layout_0);
        expandableLayout1 = (ExpandableLayout) findViewById(R.id.expandable_layout_1);
        viewTitle.setOnClickListener(this);
        viewYear = (TextView) findViewById(R.id.year);
        viewType = (TextView) findViewById(R.id.type);
        viewDescription = (TextView) findViewById(R.id.description);
        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        year = intent.getStringExtra("year");
        type = intent.getStringExtra("type");
        film_id = intent.getStringExtra("film_id");

        poster = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("poster"), 0, intent.getByteArrayExtra("poster").length);
        posterImage = (ImageView) findViewById(R.id.Photo);
        posterImage.setImageBitmap(poster);

        viewType.setText("Type: " + type);
        viewTitle.setText(title);
        if (!dbHelper.getFilmByFilmId(film_id).getReleased().equals("")) {
            viewDescription.setText(dbHelper.getFilmByFilmId(film_id).getDescription());
            viewYear.setText("Year: " + dbHelper.getFilmByFilmId(film_id).getReleased());
        } else {
            final AsynkInfoTask asynkInfoTask = new AsynkInfoTask(this, film_id, new AsynkInfoTask.Delegate() {
                @Override
                public void onPostExecuteDone() {
                    viewDescription.setText(AsynkInfoTask.getPlot());
                    viewYear.setText("Year: " + AsynkInfoTask.getReleased());
                    dbHelper.updateFilmReleaseByFilmId(film_id, AsynkInfoTask.getReleased());
                    dbHelper.updateFilmPlotByFilmID(film_id, AsynkInfoTask.getPlot());
                }
            });
            asynkInfoTask.execute();
        }
    }

    @Override
    public void onClick(View v) {
        if (expandableLayout0.isExpanded()) {
            expandableLayout0.collapse();
            expandableLayout1.collapse();
        } else {
            expandableLayout0.expand();
            expandableLayout1.expand();
        }
    }
}
