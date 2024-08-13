package com.example.exercise;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VideoActivity extends AppCompatActivity {
    private FrameLayout loadingFrame;
    private TextView loadingTitle;
    private VideoView videoView;
    private TextView timerText, commandText, commandListText, titleText, totalDurationText, progressText, resolutionText, playbackSpeedText, bufferingRateText, downloadSpeedText, memoryUsageText, cpuUsageText, avInfoText;
    private RelativeLayout textContainer, leftContainer;
    private Handler handler;
    private Runnable updateTimeRunnable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video);
        Log.d("VideoActivity", "Activity created");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN );

        loadingFrame = findViewById(R.id.loading_frame);
        loadingTitle = findViewById(R.id.loading_title);
        videoView = findViewById(R.id.video_view);
        leftContainer = findViewById(R.id.left_container);
        textContainer = findViewById(R.id.text_container);
        timerText = findViewById(R.id.timer_text);
        commandText = findViewById(R.id.command_text);
        commandListText = findViewById(R.id.command_list_text);
        titleText = findViewById(R.id.title_text);
        totalDurationText = findViewById(R.id.total_duration_text);
        progressText = findViewById(R.id.progress_text);
        resolutionText = findViewById(R.id.resolution_text);
        playbackSpeedText = findViewById(R.id.playback_speed_text);
        bufferingRateText = findViewById(R.id.buffering_rate_text);
        downloadSpeedText = findViewById(R.id.download_speed_text);
        memoryUsageText = findViewById(R.id.memory_usage_text);
        cpuUsageText = findViewById(R.id.cpu_usage_text);
        avInfoText = findViewById(R.id.av_info_text);

        handler = new Handler(Looper.getMainLooper());


        //int position = getIntent().getIntExtra("EXTRA_POSITION", -1);
        String title = getIntent().getStringExtra("EXTRA_TITLE");
        Log.d("VideoActivity", "Title: " + title);

        showLoadingFragment(title);

        loadingFrame.postDelayed(() -> {
            hideLoadingFragment();
            showVideoContent(title);
        }, 2000);
    }

    private void showLoadingFragment(String title) {
        LoadingFragment loadingFragment = LoadingFragment.newInstance(title);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.loading_frame, loadingFragment);
        transaction.commit();
        textContainer.setVisibility(View.GONE);
        leftContainer.setVisibility(View.GONE);
    }

    private void hideLoadingFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(getSupportFragmentManager().findFragmentById(R.id.loading_frame));
        transaction.commit();
    }

    private void showVideoContent(String title) {
        videoView.setVisibility(View.VISIBLE);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.twice));

        //String videoUrl = "";
        videoView.start();

        loadingFrame.setVisibility(View.GONE);

        titleText.setVisibility(View.VISIBLE);
        appendStyledText(titleText, "标题： ", title);

        leftContainer.setVisibility(View.VISIBLE);

        timerText.setVisibility(View.VISIBLE);

        commandText.setVisibility(View.VISIBLE);
        commandListText.setVisibility(View.VISIBLE);

        textContainer.setVisibility(View.VISIBLE);

        //titleText.setVisibility(View.VISIBLE);
        totalDurationText.setVisibility(View.VISIBLE);
        updateTotalDuration();

        progressText.setVisibility(View.VISIBLE);
        resolutionText.setVisibility(View.VISIBLE);
        playbackSpeedText.setVisibility(View.VISIBLE);
        bufferingRateText.setVisibility(View.VISIBLE);
        downloadSpeedText.setVisibility(View.VISIBLE);
        memoryUsageText.setVisibility(View.VISIBLE);
        cpuUsageText.setVisibility(View.VISIBLE);
        avInfoText.setVisibility(View.VISIBLE);

        startUpdatingTimer();



    }

    private void appendStyledText(TextView textView, String orangePart, String whitePart) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString orangeSpan = new SpannableString(orangePart);
        orangeSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFF8C00")), 0, orangePart.length(), 0);
        SpannableString whiteSpan = new SpannableString(whitePart);
        whiteSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFF")), 0, whitePart.length(), 0);
        builder.append(orangeSpan).append(whiteSpan);
        textView.setText(builder);
    }

    private void updateTotalDuration() {
        videoView.setOnPreparedListener(mp -> {
            int duration = videoView.getDuration();
            String formattedDuration = formatTime(duration);
            appendStyledText(totalDurationText, "总时长: ", formattedDuration);
            totalDurationText.setVisibility(View.VISIBLE);
        });
    }

    private void startUpdatingTimer() {
        updateTimeRunnable = new Runnable() {
            @Override
            public void run() {
                int currentPosition = videoView.getCurrentPosition();
                String formattedTime = formatTime(currentPosition);
                timerText.setText(formattedTime);

                if (currentPosition >= 30000 && currentPosition < 31000) {
                    fastForwardVideo(20000);
                }

                handler.postDelayed(this, 1000);
            }
        };
        handler.post(updateTimeRunnable);
    }

    private void fastForwardVideo(int milliseconds) {
        if (videoView != null && videoView.isPlaying()) {
            int currentPosition = videoView.getCurrentPosition();
            int duration = videoView.getDuration();
            int newPosition = currentPosition + milliseconds;

            if (newPosition > duration) {
                newPosition = duration;
            }

            videoView.seekTo(newPosition);

            Toast.makeText(this, "快进20秒", Toast.LENGTH_SHORT).show();
        }
    }

    private String formatTime(int milliseconds) {
        int seconds = milliseconds / 1000;
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
    }

}