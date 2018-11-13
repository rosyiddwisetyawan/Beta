package com.media.beta.fragment_menu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.media.beta.R;
import com.media.beta.SimpleMail;


public class Profile extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private EditText toEmailEditText;
    private EditText subjectEditText;
    private EditText messageEditText;

    public Profile() {
        // Required empty public constructor
    }

    public static Profile newInstance() {
        Profile fragment = new Profile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        LinearLayout button = (LinearLayout) view.findViewById(R.id.sos_browser);
        LinearLayout button2 = (LinearLayout) view.findViewById(R.id.sos_facebook);
        LinearLayout button3 = (LinearLayout) view.findViewById(R.id.sos_instagram);
        LinearLayout button4 = (LinearLayout) view.findViewById(R.id.sos_twitter);
        LinearLayout button5 = (LinearLayout) view.findViewById(R.id.sos_youtube);
        Button sendemail = (Button) view.findViewById(R.id.sendEmailButton);

        //assign views
        toEmailEditText = (EditText)view.findViewById(R.id.toEmailEditText);
        subjectEditText = (EditText)view.findViewById(R.id.subjectEditText);
        messageEditText = (EditText)view.findViewById(R.id.messageEditText);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        sendemail.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapView mapView = (MapView) view.findViewById(R.id.fragment_map_trip);
        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng marker = new LatLng(-6.178979, 106.821611);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 12));

        googleMap.addMarker(new MarkerOptions().title("Kementrian Pariwisata\n" +
                "Sapta Pesona Building,\nJl. Medan Merdeka Barat No.17, Jakarta").position(new LatLng(-6.178979, 106.821611)));
        googleMap.addMarker(new MarkerOptions().title("Hello Google Maps!").position(marker));

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true); // This is how I had implemented the setMyLocationEnabled method

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sos_browser :
                Intent web =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("http://www.indonesia.travel/gb/en/home"));
                startActivity(web);
                break;
            case R.id.sos_facebook :
                Intent fb =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://www.facebook.com/indonesiatravel/"));
                startActivity(fb);
                break;
            case R.id.sos_instagram :
                Intent instagram =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://www.instagram.com/indtravel/"));
                startActivity(instagram);
                break;
            case R.id.sos_twitter :
                Intent twitter =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://www.twitter.com/indtravel/"));
                startActivity(twitter);
                break;
            case R.id.sos_youtube :
                Intent youtube =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://www.youtube.com/c/indonesiatravel"));
                startActivity(youtube);
                break;
            case R.id.sendEmailButton :
                String to = toEmailEditText.getText().toString().trim();
                String subject = subjectEditText.getText().toString();
                String message = messageEditText.getText().toString();
                new SimpleMail().sendEmail(to, subject, message);
                Toast toast = Toast.makeText(getContext(),
                        "Success",
                        Toast.LENGTH_SHORT);
                toast.show();
                toEmailEditText.setText("");
                subjectEditText.setText("");
                messageEditText.setText("");
                break;
        }
    }
}
