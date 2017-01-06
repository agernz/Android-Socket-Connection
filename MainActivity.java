package com.agernz.thebox;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private static final int SERVERPORT = 9006;
    private static final String SERVERIP = "192.168.1.20";

    private String test;
    private TextView textv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //auto generated code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textv = (TextView) findViewById(R.id.tv);
        textv.setText("Press Button for Temperature");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get message from server
                new Thread(new ClientThread()).start();
            }

        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //update text veiw
    public void showmsg(String msg)
    {
        textv.setText(msg);
    }

    class ClientThread implements Runnable
    {
        @Override
        public void run()
        {
            try {
                //set up socket to talk to server
                Socket sock = new Socket(SERVERIP, SERVERPORT);

                //***Send message to server***//
                OutputStreamWriter out = new OutputStreamWriter(sock.getOutputStream());
                out.write("end");
                out.flush();

                //***Recieve message from server***//
                //get data from server
                InputStreamReader in = new InputStreamReader(sock.getInputStream());
                //add data to buffer
                BufferedReader reader = new BufferedReader(in);
                StringBuilder msg = new StringBuilder();

                while ((test = reader.readLine()) != null) {
                    msg.append(test);
                }

                //display message to app
                showmsg(msg.toString());

                //close reader
                out.close();
                //close output writer
                reader.close();
                //close socket
                sock.close();

            }
            //catch and display errors
            catch (UnknownHostException e)
            {
                textv.setText(e.toString());
            }catch (IOException e)
            {
                textv.setText(e.toString());
            }catch (Exception e)
            {
                textv.setText(e.toString());
            }
        }
    }
}
