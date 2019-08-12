package vn.edu.poly.socketdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.client.IO;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.poly.socketdemo.adapter.ChatAdapter;
import vn.edu.poly.socketdemo.model.Chat;

public class MainActivity extends AppCompatActivity {
    private TextView tvTime;
    private EditText mInputMessageView;
    private ImageView btnSend;
    private RecyclerView lvChat;
    private List<Chat> chats;
    private ChatAdapter chatAdapter;
    private String time="";
    private LinearLayoutManager linearLayoutManager;



    public void initViews() {

        mInputMessageView = findViewById(R.id.mInputMessageView);
        btnSend = findViewById(R.id.btnSend);
        lvChat = findViewById(R.id.lvChat);

        chats = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, chats);
        linearLayoutManager = new LinearLayoutManager(this);
        lvChat.setLayoutManager(linearLayoutManager);
        lvChat.setAdapter(chatAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = mInputMessageView.getText().toString().trim();
                if (TextUtils.isEmpty(message)) {
                    return;
                }

                mInputMessageView.setText("");
                mSocket.emit("new message", message);


                Chat chat = new Chat();
                chat.name = username;
                chat.message = message;

                chats.add(chat);
                chatAdapter.notifyDataSetChanged();
                lvChat.scrollToPosition(chatAdapter.getItemCount() - 1);
                Log.e("name ", ChatAdapter.name + "");


            }
        });
    }

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket("https://socket-io-chat.now.sh");
        } catch (URISyntaxException e) {
        }
    }

    private String username = "Tuấn Việt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
//        tvTime = (TextView) findViewById(R.id.tvTime);

//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
//         time = dateFormat.format(cal.getTime());
//        Log.e("time", time);


        mInputMessageView = findViewById(R.id.mInputMessageView);

        mSocket.emit("add user", username);

        mSocket.connect();

        mSocket.on("new message", onNewMessage);


    }


    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");

                        Chat chat = new Chat();
                        chat.name = username;
                        chat.message = message;

                        chats.add(chat);
                        chatAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view
                    //addMessage(username, message);
                }
            });
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("new message", onNewMessage);
    }
}
