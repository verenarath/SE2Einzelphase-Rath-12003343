package com.rath.SE2einzelphase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    EditText Matrikelnummer;
    Button BerechnenButton;
    Button ServerButton;
    TextView Ausgabefeld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BerechnenButton = findViewById(R.id.BerechnenButton);
        ServerButton = findViewById(R.id.ServerButton);
        Matrikelnummer = findViewById(R.id.Matrikelnummer);
        Ausgabefeld = findViewById(R.id.AusgabeFeld);
        ServerButton.setOnClickListener(view -> {
            serverConnection();

        });
    }
    public void serverConnection (){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Socket serverSocket = new Socket("se2-isys.aau.at", 53212);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                    DataOutputStream outputStream = new DataOutputStream(serverSocket.getOutputStream());

                    outputStream.writeBytes(Matrikelnummer.getText().toString()+ '\n');
                    String antwort = bufferedReader.readLine();

                    serverSocket.close();
                    Ausgabefeld.setText(antwort);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}