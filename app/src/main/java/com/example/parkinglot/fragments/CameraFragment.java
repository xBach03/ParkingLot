package com.example.parkinglot.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.parkinglot.R;
import com.example.parkinglot.database.AuthenticationManager;
import com.example.parkinglot.database.DatabaseHelper;
import com.example.parkinglot.database.daos.VehicleDao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CameraFragment extends Fragment {

    private static final int FILE_CHOOSER_RESULT_CODE = 1;
    private WebView webView;
    private ValueCallback<Uri[]> upload;
    private SQLiteDatabase db;
    private AuthenticationManager authenticationManager;
    private static final String HOME_URL = "http://192.168.100.138:6868";
    private static final String GET_URL = "http://192.168.100.138:6868/get-license";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        webView = view.findViewById(R.id.webView);
        configureWebView();
        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void configureWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.loadUrl(HOME_URL);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (upload != null) {
                    upload.onReceiveValue(null);
                }
                upload = filePathCallback;

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");

                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                try {
                    startActivityForResult(chooserIntent, FILE_CHOOSER_RESULT_CODE);
                } catch (Exception e) {
                    Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (upload == null) return;
            Uri[] results = null;
            if (resultCode == getActivity().RESULT_OK) {
                String dataString = data.getDataString();
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
            upload.onReceiveValue(results);
            upload = null;
            if (results != null && results.length > 0) {
                new UploadTask().execute(GET_URL);
            }
        }
    }
    private class UploadTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "Processing Image", "Please wait...", true);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String url = params[0];
            boolean isProcessingComplete = false;
            try {
                while (!isProcessingComplete) {
                    isProcessingComplete = checkProcessingStatus(url);
                    if (!isProcessingComplete) {
                        Thread.sleep(2000); // Poll every 2 seconds
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isProcessingComplete) {
            super.onPostExecute(isProcessingComplete);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (isProcessingComplete) {
                new JsonTask().execute(GET_URL);
            } else {
                Toast.makeText(getActivity(), "License plate processing failed", Toast.LENGTH_SHORT).show();
            }
        }

        private boolean checkProcessingStatus(String url) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL statusUrl = new URL(url);
                connection = (HttpURLConnection) statusUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }
                String response = buffer.toString();
                Log.d("UploadTask", "Response: " + response);

                JSONObject jsonResponse = new JSONObject(response);
                String status = jsonResponse.getString("status");
                return "success".equals(status);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }
    private class JsonTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String[] params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                    Log.d("Response: ", "> " + line);
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Check if the result is not null
            if (result != null) {
                // Handle the result here, for example, you can parse the JSON response
                // and update your UI accordingly
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    // Extract data from the JSON object as needed
                    String licensePlateContent = jsonResponse.getString("licensePlateContent");
                    // Update database
                    authenticationManager = AuthenticationManager.getInstance(requireContext());
                    db = DatabaseHelper.getInstance(requireContext()).getWritableDatabase();
                    VehicleDao vehicleDao = new VehicleDao(db);
                    vehicleDao.scanPlate(licensePlateContent, authenticationManager.getCurrentUser().getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("Null result", "something gone wrong");
            }
        }
    }
}
