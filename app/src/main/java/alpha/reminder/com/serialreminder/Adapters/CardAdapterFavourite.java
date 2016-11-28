package alpha.reminder.com.serialreminder.Adapters;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import alpha.reminder.com.serialreminder.DBHelper.DBHelper;
import alpha.reminder.com.serialreminder.Entity.Film;
import alpha.reminder.com.serialreminder.MainActivity;
import alpha.reminder.com.serialreminder.Profile.MovieProfileActivity;
import alpha.reminder.com.serialreminder.R;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by kruku on 07.06.2016.
 */

public class CardAdapterFavourite extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<Film> films;

    public CardAdapterFavourite(Activity context, ArrayList<Film> films) {
        super(context, R.layout.search_adapter_view, films);
        this.context = context;
        this.films = films;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.favourite_adapter_view, null, true);
        final ImageView img = (ImageView) rootView.findViewById(R.id.Photo);
        final Film film = films.get(position);

        final TextView title = (TextView) rootView.findViewById(R.id.title);
        rootView.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MovieProfileActivity.class);
                intent.putExtra("title", film.getTitle());
                intent.putExtra("poster", film.getPosterBytes());
                intent.putExtra("type", film.getType());
                intent.putExtra("year", film.getYear());
                intent.putExtra("film_id", film.getId());
                intent.putExtra("released", film.getReleased());

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    Pair<View, String> imagePair = Pair.create((View) img, img.getTransitionName());
                    Pair<View, String> holderPair = Pair.create((View) title, title.getTransitionName());
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, imagePair, holderPair);
                    startActivity(context, intent, options.toBundle());
                } else {
                    startActivity(context, intent, null);
                }
            }
        });

        img.setImageBitmap(film.getPoster());
        img.setDrawingCacheEnabled(true);
        TextView year = (TextView) rootView.findViewById(R.id.year);
        ImageButton delete = (ImageButton) rootView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper helper = new DBHelper(rootView.getContext());
                helper.deleteFilm(film);
                remove(getItem(position));
                notifyDataSetChanged();
                Snackbar.make(rootView, film.getTitle() + " deleted from you favourite list", Snackbar.LENGTH_SHORT).show();
            }
        });
        title.setText(film.getTitle());
        year.setText(film.getYear());
        year.setTextSize(20);
        title.setTextSize(15);

        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void getReveal(View view) {
        int centerX = (view.getLeft() + view.getRight()) / 2;
        int centerY = (view.getTop() + view.getBottom()) / 2;

        int startRadius = 0;
        int endRadius = Math.max(view.getWidth(), view.getHeight());

        Animator anim =
                ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

        view.setVisibility(View.VISIBLE);
        anim.start();
    }
}
