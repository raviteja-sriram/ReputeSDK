package com.repute.reputesdk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.repute.sdk.MainClass;

public class MainActivity extends AppCompatActivity {

    TextView outputStr;
    Button button;
    TextView toConcat;

    String input = "inputStr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputStr = (TextView)findViewById(R.id.textView2);
        button = (Button)findViewById(R.id.submit);
        toConcat = (TextView)findViewById(R.id.editText);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainClass.class);
                i.putExtra("str1", toConcat.getText().toString());
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                outputStr.setText(data.getStringExtra("str2"));
            }
        }
    }
}
