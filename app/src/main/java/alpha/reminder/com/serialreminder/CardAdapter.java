package alpha.reminder.com.serialreminder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kruku on 07.06.2016.
 */

public class CardAdapter extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<Film> films;

    public CardAdapter(Activity context, ArrayList<Film> films) {
        super(context,R.layout.adapter_view,films);
        this.context = context;
        this.films = films;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rotView = inflater.inflate(R.layout.adapter_view, null, true);
        final ImageView img = (ImageView) rotView.findViewById(R.id.Photo);

        TextView title = (TextView) rotView.findViewById(R.id.title);


        img.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_magnify_black_48dp));
        img.setDrawingCacheEnabled(true);

        TextView txt = (TextView) rotView.findViewById(R.id.year);
        title.setText(films.get(position).getTitle());
        txt.setText(films.get(position).getYear());
        txt.setTextSize(20);
        title.setTextSize(15);

        return rotView;
    }
}
