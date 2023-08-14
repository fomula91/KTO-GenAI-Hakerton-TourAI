package com.example.tourai_joseph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PropencityResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propencity_result);

        SharedPreferences sharedPreferences = getSharedPreferences("selectTravel",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        Button nextBtn = findViewById(R.id.pp_result_next_button);

        String character = getIntent().getStringExtra("character");
        String travelPreferences = getIntent().getStringExtra("travelPreferences");
        editor.putString("character", character);
        editor.commit();

        TextView tv_character = findViewById(R.id.tv_character);
        tv_character.setText('"' + character + '"');

        TextView tv_travelPreferences = findViewById(R.id.tv_travelPreferences);
        tv_travelPreferences.setText('"' + travelPreferences + '"');

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(PropencityResult.this, SelectTravelArea.class);
                startActivity(intent);*/
            }
        });
    }
}