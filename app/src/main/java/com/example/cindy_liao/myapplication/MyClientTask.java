package com.example.cindy_liao.myapplication;

import android.os.AsyncTask;

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
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by cindy_liao on 16/03/2017.
 */

public class MyClientTask extends AsyncTask<String, Void, String> {

    private String dstAddress="10.243.42.157";
    private int dstPort=5001;
    private String response = "";
    private String[] tag = {"movie_title","genres", "actor_1_name","actor_2_name", "actor_3_name",
            "title_year", "director_name","country"};
    private String msg = "";

    MyClientTask(){
//        dstAddress = SERVER_IP;
//        dstPort = SERVERPORT;
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


            // transform input value to json format
            JSONObject json = new JSONObject();
            json.put("start_no",Integer.valueOf(arg0[0]));
            for(int i=1;i<arg0.length;i++){
                if (! arg0[i].equals("")){
                    json.put(tag[i-1],arg0[i]);
                }
            }


            System.out.println(json.toString());

            out.println(json.toString());
            System.out.println("finished out");

            while (!in.ready());
            System.out.println("ready");


            while (in.ready()) {
//                    msg = msg + (char)in.read();
                msg = in.readLine();
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
