package com.pratik.imagefetch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUrl;
    private Button buttonLoad;
    private NetworkImageView imageView;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLoad = (Button) findViewById(R.id.buttonLoad);
        imageView = (NetworkImageView) findViewById(R.id.imageView);

        loadImage();


    }
    private void loadImage(){
        String url = "https://andridapk.000webhostapp.com/me.jpg";


        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(imageView,
                R.drawable.ic_launcher_background, android.R.drawable
                        .ic_dialog_alert));
        imageView.setImageUrl(url, imageLoader);
    }
}