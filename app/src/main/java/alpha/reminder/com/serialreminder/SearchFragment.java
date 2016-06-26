package alpha.reminder.com.serialreminder;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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


/**
 * Created by kruku on 07.06.2016.
 */

public class SearchFragment extends Fragment {
    private ListView listView;
    private CardAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.search_list);
        adapter = new CardAdapter(getActivity(),SingletoneInfo.getInstance().getFilms());
        listView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                SearchAsynkTask asynkTask = new SearchAsynkTask(query, getResources());
                asynkTask.execute();

                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);


        super.onCreateOptionsMenu(menu, inflater);
    }

    class SearchAsynkTask extends AsyncTask<Void, Void, Void> {

        private String searchName;
        private JSONObject resulObject;
        private String request;
        private Resources resources;

        public SearchAsynkTask(String searchName, Resources resources) {
            this.searchName = searchName;
            this.resources = resources;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            request = resources.getString(R.string.HTTP_REQUEST) + searchName;
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONParser parser = new JSONParser();
            URL url = null;
            HttpURLConnection connection = null;
            try {
                url = new URL(request);
                connection = (HttpURLConnection) url.openConnection();
                if (connection != null && connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String response = new String();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    response += reader.readLine();

                    resulObject = (JSONObject) parser.parse(response);

                    JSONArray array = (JSONArray) resulObject.get("Search");
                    SingletoneInfo.getInstance().clearAll();
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject oneRes = (JSONObject) array.get(i);
                        Film film = new Film();
                        film.setTitle((String) oneRes.get("Title"));
                        film.setYear((String) oneRes.get("Year"));

                        SingletoneInfo.getInstance().addFilm(film);
                    }
                    SingletoneInfo.getInstance().showTitles();

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.clear();
            adapter = new CardAdapter(getActivity(),SingletoneInfo.getInstance().getFilms());
            listView.setAdapter(adapter);
        }
    }
}
