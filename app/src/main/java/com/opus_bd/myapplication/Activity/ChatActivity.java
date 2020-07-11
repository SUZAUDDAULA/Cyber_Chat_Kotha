package com.opus_bd.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opus_bd.myapplication.Activity.Call.PlaceCallActivity;
import com.opus_bd.myapplication.Adapter.ChatAdapter;
import com.opus_bd.myapplication.Model.IndividualChatModel;
import com.opus_bd.myapplication.R;
import com.opus_bd.myapplication.Utils.Constants;
import com.opus_bd.myapplication.Utils.SharedPrefManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    public static final String EXTRA_RECEIVER_ID = "extra_receiver_id";
    public static final String EXTRA_RECEIVER_NAME = "extra_receiver_NAME";
    public static final String EXTRA_RECEIVER_PHOTO = "extra_receiver_Photo";
    @BindView(R.id.rvChats)
    RecyclerView rvChats;
    @BindView(R.id.ivEmoji)
    ImageView ivEmoji;
    @BindView(R.id.etMessage)
    EditText etMessage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvProfileName)
    TextView tvProfileName;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.civProfileImage)
    CircleImageView civProfileImage;
    @BindView(R.id.fabSent)
    FloatingActionButton fabSent;
    ArrayList<IndividualChatModel> individualChatModels = new ArrayList<>();
    int receiverId;
    String name, photo;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initToolbar();

        rootLayout.setBackground(getResources().getDrawable(R.drawable.img_chat_background));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            receiverId = bundle.getInt(EXTRA_RECEIVER_ID);
            name = bundle.getString(EXTRA_RECEIVER_NAME);
            photo = bundle.getString(EXTRA_RECEIVER_PHOTO);

        }
        tvProfileName.setText(name);
        try {
            Glide.with(this)
                    .applyDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.ic_person)
                            .error(R.drawable.ic_person))
                    .load(Constants.BASE_URL + photo)
                    .into(civProfileImage);
        } catch (Exception e) {
        }
        initRecyclerView();
        // voicecallClicked();

    }

    private void initToolbar() {
        //toolbar.setNavigationIcon(R.drawable.app_logo);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Tools.setSystemBarColor(this);
    }


    private void initRecyclerView() {
        //showProgressBar(true);
        chatAdapter = new ChatAdapter(individualChatModels, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        rvChats.setLayoutManager(layoutManager);
        rvChats.setHasFixedSize(true);
        rvChats.setAdapter(chatAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Constants.MESSAGE);

        myRef.child(getChatKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                individualChatModels.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //showProgressBar(false);

                    IndividualChatModel individualChatModel = snapshot.getValue(IndividualChatModel.class);
                    if (individualChatModel != null)
                        individualChatModels.add(individualChatModel);
                }
                chatAdapter = new ChatAdapter(individualChatModels, ChatActivity.this);
                rvChats.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Could not get data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.fabSent)
    public void sendBtnOnClick() {

        if (TextUtils.isEmpty(etMessage.getText().toString()))
            return;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Constants.MESSAGE);

        IndividualChatModel individualChatModel = new IndividualChatModel();
        individualChatModel.setSenderID(Integer.parseInt(SharedPrefManager.getInstance(this).getUserID()));
        individualChatModel.setReceiverID(receiverId);
        individualChatModel.setMessage(etMessage.getText().toString());

        String key = getChatKey();
        myRef.child(key).push().setValue(individualChatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etMessage.setText("");
            }
        });

    }

    private String getChatKey() {
        String chatKey = SharedPrefManager.getInstance(this).getUserID() + "_" + receiverId;
        if (receiverId < Integer.parseInt(SharedPrefManager.getInstance(this).getUserID()))
            chatKey = receiverId + "_" + SharedPrefManager.getInstance(this).getUserID();

        return chatKey;
    }

    private void showProgressBar(boolean visible) {
        if (visible) {
            progressbar.setVisibility(View.VISIBLE);
            rootLayout.setAlpha(Constants.PROGRESSBAR_ALPHA);

        } else {
            progressbar.setVisibility(View.GONE);
            rootLayout.setAlpha(Constants.PROGRESSBAR_AFTER_FINISH_ALPHA);
        }
    }

    public void VideoONCLick() {

        Intent intent = new Intent(this, PlaceCallActivity.class);
        PlaceCallActivity.TARGET_CALL_ID = String.valueOf(receiverId);

/*
            intent.putExtra("" + sessionManager.getUserName(), "DOCTOR_NAME");
            intent.putExtra("" + partner_name, "PATIENT_NAME");
            intent.putExtra("" + partner_photo, "USER_PHOTO");*/
        // intent.putExtra("" + 61442, "targerUser");
        this.startActivity(intent);


    }

    public void CallONCLick() {

        Constants.recipientId = String.valueOf(receiverId);
        Intent intent = new Intent(this, com.opus_bd.myapplication.Activity.VoiceCall.PlaceCallActivity.class);
        this.startActivity(intent);


    }

    @OnClick(R.id.ivAttachment)
    public void FileAttached() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select files"), 1);
    }

    @OnClick(R.id.ivEmoji)
    public void EmojiAttached() {


        /*ivEmoji.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {

            }
            @Override
            public void onKeyboardClose() {
            }
        });*/
    }

    @OnClick(R.id.ivCamera)
    public void ImageAttached() {
        Options options = Options.init()
                .setRequestCode(100)                                           //Request code for activity results
                .setCount(10)                                                   //Number of images to restict selection count
                .setFrontfacing(false)                                         //Front Facing camera on start
                //  .setPreSelectedUrls(returnValue)                               //Pre selected Image Urls
                .setExcludeVideos(false)                                       //Option to exclude videos
                .setVideoDurationLimitinSeconds(30)                            //Duration for video recording
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                .setPath("/pix/images");                                       //Custom Path For media Storage

        Pix.start(ChatActivity.this, options);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(ChatActivity.this, Options.init().setRequestCode(100));
                } else {
                    Toast.makeText(ChatActivity.this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemCall:
                CallONCLick();
                return true;
            case R.id.itemVideo:
                VideoONCLick();
                return true;
            case R.id.itemSetting:
                Toast.makeText(getApplicationContext(), "Setting",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemNewGroup:
                Toast.makeText(getApplicationContext(), "New Group", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemLogOut:
                Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
