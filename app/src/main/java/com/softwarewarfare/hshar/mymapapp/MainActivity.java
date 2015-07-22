package com.example.zatara.mymapapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.zatara.mymapapp.YelpAPI;
import com.softwarewarfare.hshar.mymapapp.Business;
import com.softwarewarfare.hshar.mymapapp.BusinessesActivity;
import com.softwarewarfare.hshar.mymapapp.SearchActivity;
import com.softwarewarfare.hshar.mymapapp.Yelp;
import com.yelp.parcelgen.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity /*implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener */{

    public static final int DIALOG_PROGRESS = 42;
    private Yelp mYelp;

    GoogleMap m_map;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    boolean mapReady=false;
    MarkerOptions myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mYelp = new Yelp(getString(R.string.consumer_key), getString(R.string.consumer_secret),
                getString(R.string.api_token), getString(R.string.api_token_secret));

        final LatLng AustinLatLng = new LatLng(30.310854, -97.706188);
        final LatLng BaghdadLatLng = new LatLng(33.323736, 44.366205);
        final LatLng PhillyLatLng = new LatLng(39.975292, -75.174338);

/*
        Button btnMe = (Button) findViewById(R.id.btnMe);
        btnMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady) {
                    LatLng myLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    myMarker = new MarkerOptions()
                            .position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                            .title("Me!");
                    m_map.addMarker(myMarker);
                    m_map.animateCamera(CameraUpdateFactory.newLatLng(myLatLng));
                }
            }
        });

        Button btnBaghdad = (Button) findViewById(R.id.btnBaghdad);
        btnBaghdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady) {
                    m_map.animateCamera(CameraUpdateFactory.newLatLng(BaghdadLatLng));
                }
            }
        });


        Button btnPhilly = (Button) findViewById(R.id.btnPhilly);
        btnPhilly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    m_map.animateCamera(CameraUpdateFactory.newLatLng(PhillyLatLng));
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
*/
    }
/*
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
*/
    /*
    @Override
    public void onMapReady(GoogleMap map) {
        m_map = map;
        LatLng newYork = new LatLng(40.7484, -73.9857);
        CameraPosition target = CameraPosition.builder().target(newYork).zoom(14).build();
        m_map.moveCamera(CameraUpdateFactory.newCameraPosition(target));

        buildGoogleApiClient();
        mGoogleApiClient.connect();
        mapReady = true;
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    public void onConnected(Bundle connectionHint) {

        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            // mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            Log.i("MyMapApp", "My Location is: " + mLastLocation.getLatitude() + ", " + mLastLocation.getLongitude());
            // mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        } else {
            Toast.makeText(this, "No location detected!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i("MyMapApp", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i("MyMapApp", "Connection suspended");
        mGoogleApiClient.connect();
    }
    */

    public void search(View v) {
        String terms = ((EditText)findViewById(R.id.searchTerm)).getText().toString();
        String location = ((EditText)findViewById(R.id.location)).getText().toString();
        showDialog(DIALOG_PROGRESS);
        new AsyncTask<String, Void, ArrayList<Business>>() {

            @Override
            protected ArrayList<Business> doInBackground(String... params) {
                String result = mYelp.search(params[0], params[1]);
                try {
                    JSONObject response = new JSONObject(result);
                    if (response.has("businesses")) {
                        return JsonUtil.parseJsonList(
                                response.getJSONArray("businesses"), Business.CREATOR);
                    }
                } catch (JSONException e) {
                    return null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Business> businesses) {
                onSuccess(businesses);
            }

        }.execute(terms, location);
    }

    public void onSuccess(ArrayList<Business> businesses) {
        // Launch BusinessesActivity with an intent that includes the received businesses
        dismissDialog(DIALOG_PROGRESS);
        if (businesses != null) {
            Intent intent = new Intent(MainActivity.this, BusinessesActivity.class);
            intent.putParcelableArrayListExtra(BusinessesActivity.EXTRA_BUSINESSES, businesses);
            startActivity(intent);
        } else {
            Toast.makeText(this, "An error occured during search", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Dialog onCreateDialog(int id) {
        if (id == DIALOG_PROGRESS) {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Loading...");
            return dialog;
        } else {
            return null;
        }
    }
}
