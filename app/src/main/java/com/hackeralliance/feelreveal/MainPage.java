package com.hackeralliance.feelreveal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

    }

    public void launchActivity(View view){

        int id = view.getId();
        Button button = (Button) findViewById(R.id.button);
        if(button.getText().equals("Start"))
        {
            button.setText("Stop");
        }
        else if (button.getText().equals("Stop"))
        {
            button.setText("Start");
        }

    }
}
