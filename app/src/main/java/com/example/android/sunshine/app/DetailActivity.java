package com.example.android.sunshine.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present. (settings)
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

     return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        // member forecast string
        private String mForecast;
        private static final String FORECAST_SHARE_HASHTAG = "#SunShineApp";
        private static final String LOG_TAG = DetailFragment.class.getSimpleName();


        public DetailFragment() {
            setHasOptionsMenu(true);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            // get the intent data in the DetailActivity (from the intent delivered me here)
            Intent intent = getActivity().getIntent();

            //====================================================
            mForecast = intent.getStringExtra(intent.EXTRA_TEXT);
            //====================================================

            //text view
            Context context = getActivity();
            TextView textView = new TextView(context);
            textView.setTextSize(40);
            textView.setText(mForecast);
            ((TextView) rootView.findViewById(R.id.forecast_detail)).setText(mForecast);
            return rootView;
        }

        public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
            inflater.inflate(R.menu.detailfragment,menu);
            MenuItem menuItem = menu.findItem(R.id.action_share);
            ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
            //solution could be here http://stackoverflow.com/questions/19358510/why-menuitemcompat-getactionprovider-returns-null
            if(mShareActionProvider != null){
                mShareActionProvider.setShareIntent(createForecastShareIntent());
            }else{
                Log.d(LOG_TAG, "Share Action Provider is NULL!");
            }

        }

        private Intent createForecastShareIntent(){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,mForecast + FORECAST_SHARE_HASHTAG);
            return shareIntent;
        }


    }
}
