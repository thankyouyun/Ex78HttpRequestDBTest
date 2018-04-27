package com.goodyun.ex78httprequestdbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TalkActivity extends AppCompatActivity {

    ListView lv;
    TalkAdapter talkAdapter;

    ArrayList<TalkItem> talkItems = new ArrayList<>();

    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        //데이터를 서버에서 읽어오기..
        loadDB();

        lv= findViewById(R.id.listview);
        talkAdapter = new TalkAdapter(getLayoutInflater(),talkItems);
        lv.setAdapter(talkAdapter);

        timer.schedule(task,2000,1000);


    }//onCreate

    TimerTask task =new TimerTask() {
        @Override
        public void run() {
            loadDB();

        }
    };


    void loadDB(){
        //Volley library 사용 가능..


        new Thread(){
            @Override
            public void run() {
                String serverUrl="http://toutt123.dothome.co.kr/android/loadDB.php";

                try {
                    URL url = new URL(serverUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is,"utf-8");
                    BufferedReader reader = new BufferedReader(isr);
                    StringBuffer buffer = new StringBuffer();
                    String line =reader.readLine();
                    while(true){
                        buffer.append(line);
                        line = reader.readLine();
                        if(line==null) break;
                        buffer.append("\n");
                    }

                    //읽어온 데이터 문자열에서 DB에 row(레코드)별로 배열로 분리하기..
                    String[] rows = buffer.toString().split(";");

                    talkItems.clear();//기존의 리스트 삭제..

                    for(String row : rows){
                        String[] datas = row.split("&");
                        if(datas.length!=5) continue;


                        int no = Integer.parseInt(datas[0]);
                        String name =datas[1];


                        String msg =datas[2];
                        String imgPath="http://toutt123.dothome.co.kr/android/"+datas[3];
                        String date=datas[4];

                        talkItems.add(0,new TalkItem(no,name,msg,imgPath,date));


                        //listview의 갱신..
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                talkAdapter.notifyDataSetChanged();

                            }
                        });

                    }


                } catch (MalformedURLException e) {
                } catch (IOException e) {
                }


            }
        }.start();


    }


}
