package com.example.exercise;

import android.content.Context;
import android.content.Intent;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ContentFragment5_1 extends Fragment {

    protected View mView;
    protected Context mContext;
    private RecyclerView recyclerView;

    public ContentFragment5_1() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_content_fragment5_1, container, false);
        recyclerView = mView.findViewById(R.id.right_list);
        setupRecyclerView();
        //TextView tv_first = mView.findViewById(R.id.text_view);
        //tv_first.setText("Content for A_1");
        return mView;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter<TestItem> adapter = new MyAdapter<>(getDataForOption1(), R.layout.right_list_item_layout);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(getContext(), DetailActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private List<TestItem> getDataForOption1() {
        List<TestItem> data = new ArrayList<>();
        data.add(new TestItem("No.902 HEVC编码-4K-自研播放器", "Main/5.0 3840x2160@24fps AAC/48000Hz", checkCapability("video/hevc", "Main/5.0 3840x2160@24fps AAC/48000Hz")));
        data.add(new TestItem("No.903 Dolby Vision-HEVC编码-4K-系统播放器", "Main/5.1 3840x2160@50fps EAC3/48000Hz", checkCapability("video/hevc", "Main/5.1 3840x2160@50fps EAC3/48000Hz")));
        data.add(new TestItem("IMAX Playback Capability Test", "Main/5.0 3840x2160@24fps DTS HD/48000Hz"));
        return data;
    }

    private String checkCapability (String mimeType, String profile) {
        if (deviceSupports(mimeType, profile)) {
            return null;
        } else {
            return "根据设备系统属性，检测到设备不支持该项能力";
        }

    }

    private boolean deviceSupports(String mimeType, String profile) {
        MediaCodecList codecList = new MediaCodecList(MediaCodecList.ALL_CODECS);
        MediaCodecInfo[] codecInfos = codecList.getCodecInfos();

        for (MediaCodecInfo codecInfo : codecInfos) {
            if (!codecInfo.isEncoder()) {
                String[] supportedTypes = codecInfo.getSupportedTypes();
                for (String type : supportedTypes) {
                    if (type.equalsIgnoreCase(mimeType)) {
                        MediaCodecInfo.CodecCapabilities capabilities = codecInfo.getCapabilitiesForType(type);
                        MediaCodecInfo.VideoCapabilities videoCapabilities = capabilities.getVideoCapabilities();

                        String[] details = profile.split(" ");
                        if (details.length < 3) continue;
                        String profileLevel = details[0];
                        String resolution = details[1].split("@")[0];
                        String framerate = details[1].split("@")[1].replace("fps", "");
                        String audioFormat = details[2];

                        String[] res = resolution.split("x");
                        int width = Integer.parseInt(res[0]);
                        int height = Integer.parseInt(res[1]);

                        float frameRateValue;
                        try {
                            frameRateValue = Float.parseFloat(framerate);
                        } catch (NumberFormatException e) {
                            Log.e("DeviceSupport", "帧率解析错误:" + framerate, e);
                            continue;
                        }
                        //float frameRateValue = Float.parseFloat(framerate.replace("fps", ""));

                        boolean profileMatch = false;
                        boolean levelMatch = false;
                        boolean resolutionMatch = videoCapabilities != null && videoCapabilities.isSizeSupported(width, height);
                        boolean framerateMatch = videoCapabilities != null && videoCapabilities.getSupportedFrameRatesFor(width, height).contains((double) frameRateValue);
                        boolean audioFormatMatch = true;

                        for (MediaCodecInfo.CodecProfileLevel profileLevelInfo : capabilities.profileLevels) {
                            if (profileLevel.equals("Main/5.1")) {
                                profileMatch = profileLevelInfo.profile == MediaCodecInfo.CodecProfileLevel.HEVCProfileMain;
                                levelMatch = profileLevelInfo.level >= MediaCodecInfo.CodecProfileLevel.HEVCHighTierLevel51;
                            } else if (profileLevel.equals("Main/5.0")) {
                                profileMatch = profileLevelInfo.profile == MediaCodecInfo.CodecProfileLevel.HEVCProfileMain;
                                levelMatch = profileLevelInfo.level >= MediaCodecInfo.CodecProfileLevel.HEVCHighTierLevel5;
                            }

                            Log.d("DeviceSupport", "Profile Match: " + profileMatch);
                            Log.d("DeviceSupport", "Level Match: " + levelMatch);
                            Log.d("DeviceSupport", "Resolution Match: " + resolutionMatch);
                            Log.d("DeviceSupport", "Framerate Match: " + framerateMatch);
                            Log.d("DeviceSupport", "Audio Format Match: " + audioFormatMatch);


                            if (profileMatch && levelMatch && resolutionMatch && framerateMatch && audioFormatMatch) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}