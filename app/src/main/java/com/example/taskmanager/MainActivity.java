package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        setSingleEvent(gridLayout);

    }


    private void setSingleEvent(GridLayout gridLayout) {
        //loop all child item of grid
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            //cast object to CardView
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == 0) {
                        Intent intent = new Intent(MainActivity.this, DailyMainActivity.class);
                        startActivity(intent);
                    } else if (finalI == 1) {
                        Intent intent = new Intent(MainActivity.this, DailyMainActivity.class);
                        startActivity(intent);
                    } else if (finalI == 2) {
                        Intent intent = new Intent(MainActivity.this, DailyMainActivity.class);
                        startActivity(intent);
                    } else if (finalI == 3) {
                        Intent intent = new Intent(MainActivity.this, DailyMainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Что-то не то", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}


//    private void setToggleEvent(GridLayout gridLayout) {
//        //loop all child item of grid
//        for (int i = 0; i < gridLayout.getChildCount(); i++) {
//
//            //cast object to CardView
//            CardView cardView = (CardView) gridLayout.getChildAt(i);
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1)
//                    {
//                        //change background color
//                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
//                     //   Toast.makeText(MainActivity.this, "State True", Toast.LENGTH_SHORT).show();
//
//                    }
//                    else {
//                        //change background color
//                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
//                        //Toast.makeText(MainActivity.this, "State False ", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }
//
//    private void setSingleEvent(GridLayout gridLayout) {
//        //loop all child item of grid
//        for (int i=0; i<gridLayout.getChildCount();i++){
//            //cast object to CardView
//            CardView cardView = (CardView)gridLayout.getChildAt(i);
//            int finalI = i;
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Toast.makeText(MainActivity.this, "Clicked at index " + finalI, Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//
//    }
//
//

