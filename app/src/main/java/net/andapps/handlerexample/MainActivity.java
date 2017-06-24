package net.andapps.handlerexample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private Button mProgressButton, mProgressBarButton;
    ProgressDialog mProgressDialog;
    Handler mHandler;
    ProgressBar mProgressBar;
    Handler mHandlerProgressBar = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mProgressBar.incrementProgressBy(10);
        }
    };
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressButton = (Button) findViewById(R.id.buttonProgress);
        mProgressBarButton = (Button) findViewById(R.id.buttonProgressBar);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchProgressDialog();
            }
        });
        mHandler = new Handler();

        mProgressBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchProgressBar();
            }
        });
    }

    public void launchProgressDialog() {
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setTitle("Simulando descarga ...");
        mProgressDialog.setMessage("Descarga en progreso ...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setProgress(0);
        mProgressDialog.setMax(100);
        mProgressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(mProgressDialog.getProgress() <= mProgressDialog.getMax()){
                        Thread.sleep(1000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.incrementProgressBy(10);
                            }
                        });
                        if(mProgressDialog.getProgress() == mProgressDialog.getMax()){
                            mProgressDialog.dismiss();
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void launchProgressBar() {
        mProgressBar.setProgress(0);
        mProgressBar.setMax(100);
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 20; i++){
                    try {
                        Thread.sleep(1000);
                        mHandlerProgressBar.sendMessage(mHandlerProgressBar.obtainMessage());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        isRunning = true;
        mThread.start();
    }
}
