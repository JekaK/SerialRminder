package alpha.reminder.com.serialreminder;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by kruku on 07.06.2016.
 */

public class FavouriteFragment extends Fragment {
    private ListView listView;
    private CardAdapterFavourite adapter;
    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favourite_fragment, container, false);
        dbHelper = new DBHelper(rootView.getContext());
        listView = (ListView) rootView.findViewById(R.id.favourite_list);

        ArrayList<Film> films = dbHelper.getAllFilms();
        if (films != null) {
            adapter = new CardAdapterFavourite(getActivity(), films);
            listView.setAdapter(adapter);
        }
        return rootView;
    }
}
