package alpha.reminder.com.serialreminder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kruku on 07.06.2016.
 */

public class CardAdapterSearch extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<Film> films;

    public CardAdapterSearch(Activity context, ArrayList<Film> films) {
        super(context,R.layout.serch_adapter_view,films);
        this.context = context;
        this.films = films;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.serch_adapter_view, null, true);
        final ImageView img = (ImageView) rootView.findViewById(R.id.Photo);
        final Film film = films.get(position);
        TextView title = (TextView) rootView.findViewById(R.id.title);

        img.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_magnify_black_48dp));
        img.setDrawingCacheEnabled(true);
        TextView txt = (TextView) rootView.findViewById(R.id.year);

        title.setText(film.getTitle());
        txt.setText(film.getYear());
        txt.setTextSize(20);
        title.setTextSize(15);

        ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.favourite);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(rootView.getContext());
                dbHelper.insertFilm(film);
            }
        });

        return rootView;
    }
}
