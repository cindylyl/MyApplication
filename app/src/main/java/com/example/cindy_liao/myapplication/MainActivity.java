package com.example.cindy_liao.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText et_movie_title, et_gernes, et_actor1, et_actor2, et_actor3, et_year, et_director, et_country;
//    String movie_title =et_movie_title.getText().toString();
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private static final int SERVERPORT = 8000;
    private static final String SERVER_IP = "10.241.38.163";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_movie_title=(EditText) findViewById(R.id.movie_title_id);
        et_gernes=(EditText) findViewById(R.id.genres_id);
        et_actor1=(EditText) findViewById(R.id.actor1_id);
        et_actor2=(EditText) findViewById(R.id.actor2_id);
        et_actor3=(EditText) findViewById(R.id.actor3_id);
        et_year=(EditText) findViewById(R.id.year_id);
        et_director=(EditText) findViewById(R.id.director_id);
        et_country=(EditText) findViewById(R.id.country_id);




        Button button=(Button) findViewById(R.id.button);
        button.setOnClickListener(buttonConnectOnClickListener);


    }


    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    MyClientTask myClientTask = new MyClientTask(
                            et_movie_title.getText().toString(),
                            Integer.parseInt(et_gernes.getText().toString()));


                    String result= null;
                    try {
                        result = myClientTask.execute(et_movie_title.getText().toString()).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(MainActivity.this,Result.class);
                    intent.putExtra(Intent.EXTRA_TEXT,result);
                    startActivity(intent);

                }};

//    @Override
//    public void onClick(View view) {
//        System.out.println("onclick");
//
//    }




    public class MyClientTask extends AsyncTask<String, Void, String> {

        String dstAddress;
        int dstPort;
        String response = "";
        String msg = "";

        MyClientTask(String addr, int port){
            dstAddress = SERVER_IP;
            dstPort = SERVERPORT;
        }

        @Override
        protected String doInBackground(String... arg0) {

            Socket socket = null;

            try {
                socket = new Socket(dstAddress, dstPort);

                ByteArrayOutputStream byteArrayOutputStream =
                        new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];

                int bytesRead;
                InputStream inputStream = socket.getInputStream();
                socket.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);


                JSONObject json = new JSONObject();
                json.put("movie_title", arg0[0]);


                out.println(json.toString());
                System.out.println("finished out");

                while (!in.ready());
                System.out.println("ready");


                while (in.ready()) {
                    msg = msg + (char)in.read();
                }
                System.out.println(msg);


            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally{
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return msg;
        }

//        @Override
//        protected void onPostExecute(Void result) {
//            et_gernes.setText(response);
//            super.onPostExecute(result);
//        }

    }


}
