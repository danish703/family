package com.bsccsitnepal.myfamily.family;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener{

    private GoogleMap mMap;
    LocationManager locationManager;
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    String id;
    public String mylocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        id = sharedpreferences.getString("id","0");


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getlocation();
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(MapsActivity.this,"Current Location"+location.getLatitude()+","+location.getLongitude(),Toast.LENGTH_SHORT).show();
        update_location(location.getLongitude(),location.getLatitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(MapsActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    public void update_location(Double lo,Double la){
        final Double longitude = lo;
        final Double latitude = la;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.location_update_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MapsActivity.this,response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MapsActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("longitude",longitude.toString());
                params.put("latitude",latitude.toString());
                params.put("user",id);
                return  params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getlocation(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.location_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                   for(int i = 0;i<jsonArray.length();i++){
                        String logitude = jsonArray.getJSONObject(i).getString("logitude").toString();
                        String lattitude = jsonArray.getJSONObject(i).getString("latitude").toString();
                        String user = jsonArray.getJSONObject(i).getString("user").toString();
                        LatLng mylatlnag = new LatLng(Double.parseDouble(logitude) ,Double.parseDouble(lattitude));
                        mMap.addMarker(new MarkerOptions().position(mylatlnag).title(user));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(mylatlnag));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MapsActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //params.put("user",id);
                return  params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
