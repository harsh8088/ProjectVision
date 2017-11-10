package com.hrawat.projectvision;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hrawat.projectvision.faceDetection.ImageTrackerActivity;
import com.hrawat.projectvision.faceDetection.faceDetails.FaceTrackerActivity;
import com.hrawat.projectvision.textDetection.TextDetectionActivity;

public class HomeActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        Button btnImageDetect = findViewById(R.id.btn_image_detection);
        btnImageDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ImageTrackerActivity.class);
                startActivity(intent);
            }
        });
        Button btnFaceDetect = findViewById(R.id.btn_face_detection);
        btnFaceDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FaceTrackerActivity.class);
                startActivity(intent);
            }
        });
        Button btnTextDetect = findViewById(R.id.btn_text_detection);
        btnTextDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TextDetectionActivity.class);
                startActivity(intent);
            }
        });
    }
}
