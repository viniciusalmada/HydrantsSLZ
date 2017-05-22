package br.com.viniciusalmada.hidrantesslz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.viniciusalmada.hidrantesslz.domains.Hydrant;

public class HydrantMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hydrant_maps);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("teste");
        db.setValue("This is a test only").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*MapView mapView = (MapView) findViewById(R.id.map);
        mapView.getMapAsync(this);*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(final GoogleMap googleMap) {
        Toast.makeText(this, "onMapReady", Toast.LENGTH_SHORT).show();
        DatabaseReference hydRef = FirebaseDatabase.getInstance().getReference().child("hydrants");
        hydRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMap = googleMap;
                Iterable<DataSnapshot> it = dataSnapshot.getChildren();
                LatLng lastLat = new LatLng(-2.538851, -44.242504);
                for (DataSnapshot ds : it) {
                    Hydrant h = ds.getValue(Hydrant.class);
                    LatLng ll = new LatLng(h.getLat(), h.getLgn());
                    mMap.addMarker(new MarkerOptions().position(ll).title(h.getName()));
                    Log.d("TAG", "onDataChange: " + ds.getValue().toString());
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLat,12f));
                mMap.setOnMarkerClickListener(HydrantMapsActivity.this);
                mMap.setOnInfoWindowClickListener(HydrantMapsActivity.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       /* mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    Circle oldCircle;
    Circle circle;

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (oldCircle != null)
            oldCircle.remove();
        circle = mMap.addCircle(new CircleOptions().center(marker.getPosition()).radius(300));
        oldCircle = circle;
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
    }
}
