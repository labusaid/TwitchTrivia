package com.latheabusaid.twitchtrivia;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TwitchTrivia";

    String siteUrl = "https://twitch-trivia.herokuapp.com";
    String currentQ = "no question found";
    private String m_Text = "";

    OkHttpClient client = new OkHttpClient();

    Call getFromUrl(String url, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nameButtonClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
//                get request @url/game/start?name=m_Text
                try {
                    getFromUrl(siteUrl + "/game/start?name=" + m_Text, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.d(TAG, "onResponse: game started");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                get request @url/game/question/current
                try {
                    getFromUrl(siteUrl + "/game/question/current", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            currentQ = response.body().string();
                            Log.d(TAG, "onResponse: " + currentQ);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void readyButtonClicked(View view) throws IOException {
//        get request @url/game/question/ready
        getFromUrl(siteUrl + "/game/question/ready", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void answer1Clicked(View view) {
//        get request @url/game/question/answer?choice=num
    }

    public void answer2Clicked(View view) {
    }

    public void answer3Clicked(View view) {
    }

    public void answer4Clicked(View view) {
    }
}
