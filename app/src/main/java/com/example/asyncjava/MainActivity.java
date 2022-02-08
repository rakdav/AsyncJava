package com.example.asyncjava;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText UrlAddress;
    private Button load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UrlAddress=findViewById(R.id.urladdress);
        load=findViewById(R.id.load);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link=UrlAddress.getText().toString();
                new DownLoadImage(findViewById(R.id.image)).execute(link);
            }
        });
    }

    private class DownLoadImage extends AsyncTask<String,Void, Bitmap>{
        ImageView imageView;

        public DownLoadImage(ImageView _imageView) {
            this.imageView = _imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings)
        {
            String url=strings[0];
            Bitmap icon=null;
            InputStream in=null;
            try
            {
                in=new  URL(url).openStream();
                icon= BitmapFactory.decodeStream(in);
                Log.d("succ","Load is OK");
            }
            catch (Exception e)
            {
                Log.d("Error",e.getMessage());
            }
            finally {
                if(in!=null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return icon;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}