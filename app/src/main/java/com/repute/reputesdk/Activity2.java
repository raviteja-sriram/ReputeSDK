package com.repute.reputesdk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {

    TextView receivedMsg;
    Button button;
    TextView toSend;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        setTitle("Activity 3");

        receivedMsg = (TextView)findViewById(R.id.textView);
        button = (Button)findViewById(R.id.button);
        toSend = (TextView)findViewById(R.id.editText2);

        Intent intent = getIntent();

        String str = intent.getStringExtra("str1");

        receivedMsg.setText(str);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("str2", toSend.getText().toString());
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
