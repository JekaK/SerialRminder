package alpha.reminder.com.serialreminder.Fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.util.Log;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import alpha.reminder.com.serialreminder.Adapters.CardAdapterSearch;
import alpha.reminder.com.serialreminder.Entity.Film;
import alpha.reminder.com.serialreminder.R;
import alpha.reminder.com.serialreminder.Singletone.SingletoneInfo;
import alpha.reminder.com.serialreminder.util.NetworkUtils;


/**
 * Created by kruku on 07.06.2016.
 */

public class SearchFragment extends Fragment {
    private ListView listView;
    private CardAdapterSearch adapter;

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
        adapter = new CardAdapterSearch(getActivity(), SingletoneInfo.getInstance().getFilms());
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
        private ProgressDialog progressDialog;
        private String searchName;
        private String request;
        private Resources resources;

        @TargetApi(Build.VERSION_CODES.M)
        public SearchAsynkTask(String searchName, Resources resources) {
            this.searchName = searchName;
            this.resources = resources;
            progressDialog = new ProgressDialog(SearchFragment.this.getContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            request = resources.getString(R.string.HTTP_REQUEST) + searchName + resources.getString(R.string.PAGE) + "1";
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            JSONObject resultObject = requestResult(request);
            if (resultObject != null) {
                String totalRes = (String) resultObject.get("totalResults");
                int pageCount = pageCounter(Integer.parseInt(totalRes));
                int currentCount = 2;

                JSONArray array = (JSONArray) resultObject.get("Search");

                if (array != null) {
                    SingletoneInfo.getInstance().clearAll();
                    addInfo(array);
                    while (currentCount <= pageCount) {
                        request = request.substring(0, request.length() - 1) + currentCount;
                        resultObject = requestResult(request);
                        array = (JSONArray) resultObject.get("Search");

                        //crashes when uncommented while search something like "trello"
                        if (array != null) {
                            addInfo(array);
                        } else {
                            Log.w("Search", "array is null in 'while' block");
                        }

                        currentCount++;
                    }
                } else {
                    Log.w("Search", "JSONarray is null");
                }
            } else {
                Log.w("Search", "requestResult is null");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.clear();
            adapter = new CardAdapterSearch(getActivity(), SingletoneInfo.getInstance().getFilms());
            listView.setAdapter(adapter);
            progressDialog.dismiss();

        }
    }

    public void addInfo(@NonNull JSONArray array) {
        for (int i = 0; i < array.size(); i++) {
            JSONObject oneRes = (JSONObject) array.get(i);
            String posterURL = NetworkUtils.validateURL((String) oneRes.get("Poster"));
            Bitmap poster = null;
            try {
                poster = BitmapFactory.decodeStream((InputStream) new URL(posterURL).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (poster == null) {
                poster = BitmapFactory.decodeResource(getResources(), R.drawable.question);
            }
            Film film = new Film();
            film.setType((String) oneRes.get("Type"));
            film.setTitle((String) oneRes.get("Title"));
            film.setYear((String) oneRes.get("Year"));
            film.setId((String) oneRes.get("imdbID"));
            film.setPoster(poster);
            SingletoneInfo.getInstance().addFilm(film);
        }
    }

    public JSONObject requestResult(String request) {

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
        JSONObject resultObject = null;

        try {
            if (connection != null && connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String response = new String();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                response += reader.readLine();
                resultObject = (JSONObject) parser.parse(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultObject;
    }

    private int pageCounter(int sum) {
        int count = 1;
        while (sum > 0) {
            sum /= 10;
            count++;
        }
        return count;
    }
}
