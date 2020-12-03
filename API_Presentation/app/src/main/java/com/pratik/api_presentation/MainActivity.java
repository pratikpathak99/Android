package com.pratik.api_presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView insert_name;
    EditText name;
    LinearLayout mainLayout;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        progressDialog= new ProgressDialog (this);
        insert_name = findViewById(R.id.insert_show);
        name = findViewById(R.id.editTextEmail);
        mainLayout=(LinearLayout)this.findViewById(R.id.mainLayout);
    }

    public void insert(View view) {
        mainLayout.setVisibility(LinearLayout.INVISIBLE);
        new insert().execute("https://andridapk.000webhostapp.com/indus_university/insert_API.php");
    }
    public class insert extends AsyncTask<String,Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute ();
            progressDialog.setMessage ("Please Wait");
            progressDialog.show ();
        }

        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String passurl = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL (passurl);
                urlConnection = (HttpURLConnection) url.openConnection ();
                urlConnection.setDoOutput (true); //POST method

                urlConnection.setRequestMethod ("POST");//POST method

                Uri.Builder builder = new Uri.Builder ();//POST method
                builder.appendQueryParameter ("name", name.getText().toString());//POST method

                OutputStream output = urlConnection.getOutputStream ();//POST method
                BufferedWriter bufferedwriter = new BufferedWriter (new OutputStreamWriter(output, "UTF-8"));//POST method
                bufferedwriter.write (builder.build ().getEncodedQuery ());//POST method
                bufferedwriter.flush ();//POST method
                bufferedwriter.close ();//POST method
                output.close ();//POST method
                urlConnection.connect ();

                InputStream inputStream = urlConnection.getInputStream ();//input stream
                StringBuffer buffer = new StringBuffer ();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader (new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine ()) != null)
                    buffer.append (inputLine + "\n");
                if (buffer.length () == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                JsonResponse = buffer.toString ();//response data
                Log.i ("Registration", JsonResponse);
                //send to post execute
                return JsonResponse;
            } catch (IOException e) {
                e.printStackTrace ();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect ();
                }
                if (reader != null) {
                    try {
                        reader.close ();
                    } catch (final IOException e) {
                        Log.e ("Registration", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute (s);
            System.out.println ("Response:" + s);
            try {
                //fetch the data from server.
                JSONArray jsonArray = new JSONArray (s);
                JSONObject object = jsonArray.getJSONObject (0);
                String status = object.getString("msg");
                if(status.equals("Successfully")){
                    insert_name.setText("Insert Data Successfully");
                }
                else {
                    insert_name.setText("error..");
                }
                progressDialog.dismiss ();
            }
            catch (JSONException e)
            {
                e.printStackTrace ();
            }
        }
    }
}