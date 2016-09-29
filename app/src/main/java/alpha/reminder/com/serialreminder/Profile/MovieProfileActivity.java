package alpha.reminder.com.serialreminder.Profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import alpha.reminder.com.serialreminder.R;

/**
 * Created by Eugeniy Krukun on 08.07.2016.
 */

public class MovieProfileActivity extends Activity implements View.OnClickListener {
    private String title, type, year;
    private Bitmap poster;
    private ImageView posterImage;
    private TextView viewTitle;
    private ExpandableLayout expandableLayout0, expandableLayout1;
    private TextView viewType, viewYear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_profile);

        viewTitle = (TextView) findViewById(R.id.placeNameHolder);

        expandableLayout0 = (ExpandableLayout) findViewById(R.id.expandable_layout_0);
        expandableLayout1 = (ExpandableLayout) findViewById(R.id.expandable_layout_1);
        viewTitle.setOnClickListener(this);

        viewYear = (TextView) findViewById(R.id.year);
        viewType = (TextView) findViewById(R.id.type);

        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        year = intent.getStringExtra("year");
        type = intent.getStringExtra("type");

        poster = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("poster"), 0, intent.getByteArrayExtra("poster").length);
        posterImage = (ImageView) findViewById(R.id.Photo);
        posterImage.setImageBitmap(poster);

        viewType.setText("Type: " + type);
        viewYear.setText("Year: " + year);
        viewTitle.setText(title);
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
