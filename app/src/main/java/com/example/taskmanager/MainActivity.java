package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            initView();

            //set event
            // setSingleEvent (gridLayout);

            setToggleEvent(gridLayout);


        }

    private void setToggleEvent(GridLayout gridLayout) {
        //loop all child item of grid
        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            //cast object to CardView
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1)
                    {
                        //change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        Toast.makeText(MainActivity.this, "State True", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        //change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        Toast.makeText(MainActivity.this, "State False ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
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

    private void initView () {
        gridLayout = findViewById(R.id.gridLayout);
    }
}