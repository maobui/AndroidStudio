package com.example.maobuidinh.interview02;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRun = (Button) findViewById(R.id.btnRun);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++)
                {
                    new MyThread(""+ i).start();
                    new MyAsyncTask().execute(i);
                }
            }
        });

    }

    private class MyAsyncTask extends AsyncTask<Integer , Void, Void>
    {
        @Override
        protected Void doInBackground(Integer... params) {
            Log.d("MyAsyncTask", "AsyncTask : " + params[0]);
            return null;
        }
    }

    private  class MyThread extends Thread
    {
        public MyThread(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
//            super.run();
            Log.d("MyThread", "Thread : " + getName() );
        }
    }
}
