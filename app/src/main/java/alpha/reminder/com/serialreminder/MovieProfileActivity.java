package alpha.reminder.com.serialreminder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;

/**
 * Created by Eugeniy Krukun on 08.07.2016.
 */

public class MovieProfileActivity  extends Activity {
    private String title;
    private Bitmap poster;
    private ImageView posterImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_profile);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        poster = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("poster"), 0, intent.getByteArrayExtra("poster").length);
        posterImage = (ImageView) findViewById(R.id.film_poster);
        posterImage.setImageBitmap(poster);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);
    }
}
