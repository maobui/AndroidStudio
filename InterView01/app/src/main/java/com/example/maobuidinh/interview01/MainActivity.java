package com.example.maobuidinh.interview01;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    Button btnGetText;
    Button btnGetImage;
    TextView tvResult;
    ImageView imgResult;

    private ProgressDialog progressDialog;
    private Bitmap bitmap = null;
    private String text = "";
    private Drawable imageDownloaded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetText = (Button) findViewById(R.id.btnGetText);
        btnGetImage = (Button) findViewById(R.id.btnGetImage);
        tvResult = (TextView) findViewById(R.id.txtResult);
        imgResult = (ImageView) findViewById(R.id.imgResult);

        btnGetText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

//				downloadText("http://www.google.com.vn");
                new HttpAsyncTask().execute("http://www.google.com.vn");

            }
        });

        btnGetImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//				downloadImage("http://www.tinthethao365.com.vn/uploads/news/PostImg/2016/05/12/83/957343b08c58d3.jpg");
                downloadImage("http://mobile.gameloft.com/assets/images/banners/win/1838.jpg");
//				new DownloadTask().execute("http://www.google.com.vn");
            }
        });
    }

    public String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            inputStream = openHttpConnection(url);
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            //super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this, "", "Fetching Text...");
        }
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Toast.makeText(getBaseContext(), "Received!\n" + result, Toast.LENGTH_LONG).show();
            tvResult.setText(result);
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private void downloadImage(String urlStr) {
        progressDialog = ProgressDialog.show(this, "", "Fetching Image...");
        final String url = urlStr;

        new Thread() {
            public void run() {
                InputStream in = null;
                Message msg = Message.obtain();
                msg.what = 1;
                try {
                    in = openHttpConnection(url);
                    if (in != null)
                    {
                        // creat image in Drawable .
                        imageDownloaded = Drawable.createFromStream(in, "temp");

                        //
                        bitmap = BitmapFactory.decodeStream(in);
                        Bundle b = new Bundle();
                        b.putParcelable("bitmap", bitmap);
                        msg.setData(b);
                        in.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                messageHandler.sendMessage(msg);

            }
        }.start();

    }
    public  void downloadText(String urlStr)
    {
        progressDialog = ProgressDialog.show(this, "", "Fetching Text...");
        final String url = urlStr;
        new Thread () {
            public void run() {
                int BUFFER_SIZE = 2000;
                InputStream in = null;
                Message msg = Message.obtain();
                msg.what=2;
                try{
                    in = openHttpConnection(url);
                    String text = convertInputStreamToString(in);
                    Bundle b = new Bundle();
                    b.putString("text", text);
                    msg.setData(b);
                    in.close();

                }catch (IOException e) {
                    e.printStackTrace();
                }

                messageHandler.sendMessage(msg);
            }
        }.start();
    }
    private Handler messageHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
//                    imgResult.setImageBitmap((Bitmap)(msg.getData().getParcelable("bitmap")));
                    imgResult.setImageDrawable(imageDownloaded);
                    break;
                case 2:
                    Toast.makeText(getBaseContext(), "Received!\n" + msg.getData().getString("text"), Toast.LENGTH_LONG).show();
                    tvResult.setText(msg.getData().getString("text"));
                    break;
            }
            progressDialog.dismiss();
        }
    };

    private InputStream openHttpConnection(String urlStr) {
        InputStream in = null;
        int resCode = -1;

        try {
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();
            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException ("URL is not an Http URL");
            }

            HttpURLConnection httpConn = (HttpURLConnection)urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            //httpConn.setConnectTimeout(3000);

            resCode = httpConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
            Log.d("aaaaaaaaaaaaa ", "resCode : " + resCode);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d( "io exception.  url: %s",urlStr + "\n"+ e.toString());
        }
        return in;
    }


    private class DownloadTask extends AsyncTask<String, Long, File> {
        protected File doInBackground(String... urls) {
            int resCode = -1;
            try {
                URL url = new URL(urls[0]);
                URLConnection urlConn = url.openConnection();

                if (!(urlConn instanceof HttpURLConnection)) {
                    throw new IOException("URL is not an Http URL");
                }
                HttpURLConnection httpConn = (HttpURLConnection) urlConn;
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                resCode = httpConn.getResponseCode();

                File file = null;
                if (resCode == HttpURLConnection.HTTP_OK) {
                    file = File.createTempFile("download", ".tmp");
                    InputStream in = httpConn.getInputStream();
                    byte[] buffer = new byte[in.available()];
                    in.read(buffer);

                    OutputStream out = new FileOutputStream(file);
                    out.write(buffer);

                }
                return file;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Long... progress) {
            Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        protected void onPostExecute(File file) {
            if (file != null)
                Log.d("MyApp", "Downloaded file to: " + file.getAbsolutePath());
            else
                Log.d("MyApp", "Download failed");
        }
    }
}
