package com.example.user.serverconnect;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "MainActivity";

    CognitoCachingCredentialsProvider credentialsProvider;
    AmazonS3 s3;
    TransferUtility transferUtility;

    Button uploadBtn;
    ImageView imageview;
    File f = new File("d4.jpg");

    private String userChoosenTask;
    Uri imageUri;
    String imagePath;
    private Uri mImageUri;
    private int PICTURE_CHOICE = 1;
    private int REQUEST_CAMERA = 2;
    private int SELECT_FILE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadBtn = (Button) findViewById(R.id.uploadBtn);
        imageview = (ImageView) findViewById(R.id.imageview1);

        uploadBtn.setOnClickListener(this);

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "ap-northeast-2:c4b1db36-76bf-487d-a6be-52b90351b424", // Identity Pool ID
                Regions.AP_NORTHEAST_2 // Region
        );
        s3 = new AmazonS3Client(credentialsProvider);
        s3.setRegion(Region.getRegion(Regions.US_WEST_2));
        s3.setEndpoint("s3.ap-northeast-2.amazonaws.com");

        transferUtility = new TransferUtility(s3, getApplicationContext());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.uploadBtn:
                TransferObserver observer = transferUtility.upload(
                        "s3apparel",
                        f.getName(),
                        f
                );
                break;
        }
    }
    /**   selectImage() 함수를 통해 선택한 이미지를 파일 객체 f 에 담는 동작 진행   **/
}
