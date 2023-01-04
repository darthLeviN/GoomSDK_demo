package com.goomsdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.util.Log;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import android.graphics.Color;

import us.zoom.sdk.ZoomVideoSDK;
import us.zoom.sdk.ZoomVideoSDKErrors;
import us.zoom.sdk.ZoomVideoSDKInitParams;
import us.zoom.sdk.ZoomVideoSDKRawDataMemoryMode;
import us.zoom.sdk.ZoomVideoSDKDelegate;
import us.zoom.sdk.ZoomVideoSDKAudioHelper;
import us.zoom.sdk.ZoomVideoSDKAudioRawData;
import us.zoom.sdk.ZoomVideoSDKChatHelper;
import us.zoom.sdk.ZoomVideoSDKChatMessage;
import us.zoom.sdk.ZoomVideoSDKChatMessageDeleteType;
import us.zoom.sdk.ZoomVideoSDKLiveStreamHelper;
import us.zoom.sdk.ZoomVideoSDKLiveStreamStatus;
import us.zoom.sdk.ZoomVideoSDKLiveTranscriptionHelper;
import us.zoom.sdk.ZoomVideoSDKMultiCameraStreamStatus;
import us.zoom.sdk.ZoomVideoSDKNetworkStatus;
import us.zoom.sdk.ZoomVideoSDKPasswordHandler;
import us.zoom.sdk.ZoomVideoSDKPhoneFailedReason;
import us.zoom.sdk.ZoomVideoSDKPhoneStatus;
import us.zoom.sdk.ZoomVideoSDKProxySettingHandler;
import us.zoom.sdk.ZoomVideoSDKRawDataPipe;
import us.zoom.sdk.ZoomVideoSDKRecordingConsentHandler;
import us.zoom.sdk.ZoomVideoSDKRecordingStatus;
import us.zoom.sdk.ZoomVideoSDKSSLCertificateInfo;
import us.zoom.sdk.ZoomVideoSDKShareHelper;
import us.zoom.sdk.ZoomVideoSDKShareStatus;
import us.zoom.sdk.ZoomVideoSDKUser;
import us.zoom.sdk.ZoomVideoSDKUserHelper;
import us.zoom.sdk.ZoomVideoSDKVideoCanvas;
import us.zoom.sdk.ZoomVideoSDKVideoHelper;
import us.zoom.sdk.ZoomVideoSDKVideoView;
import us.zoom.sdk.ZoomVideoSDKVideoAspect;

import com.goomsdk.view.DrawLayout;

import org.godotengine.godot.Dictionary;

public class CameraStackLayout extends FrameLayout {

    private List<ZoomVideoSDKUser> _userList = new Vector<>();
    private HashMap<ZoomVideoSDKUser, ZoomVideoSDKVideoView> videoViews = new HashMap<>();
    private HashMap<String, DrawLayout> drawLayouts = new HashMap<>();

    public void reload_layouts() {
        {
            // layout of self.
            setBackgroundColor(Color.GREEN);
            DrawLayout mdl = null;
            if (drawLayouts.containsKey("main")) {
                mdl = drawLayouts.get("main");
            } else {
                mdl = new DrawLayout();
            }

            mdl.configureTarget(this);
        }
    }

    public CameraStackLayout(Context context) {
        super(context);
        reload_layouts();
    }

    public CameraStackLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        reload_layouts();
    }

    public void addUser(ZoomVideoSDKUser user) {
        if (!_userList.contains(user)) {
            _userList.add(user);
            ZoomVideoSDKVideoView videoView = new ZoomVideoSDKVideoView(getContext());
            ZoomVideoSDKVideoCanvas canvas = user.getVideoCanvas();
            canvas.subscribe(videoView, ZoomVideoSDKVideoAspect.ZoomVideoSDKVideoAspect_Original);
            addView(videoView);
            videoViews.put(user, videoView);
        }
    }

    public void removeUser(ZoomVideoSDKUser user) {
        _userList.remove(user);
        ZoomVideoSDKVideoView view = videoViews.get(user);
        ZoomVideoSDKVideoCanvas canvas = user.getVideoCanvas();
        canvas.unSubscribe(view);
        videoViews.remove(user);
        removeView(view);
    }

    public void clear() {
        for(ZoomVideoSDKUser user : _userList) {
            removeUser(user);
        }
        _userList.clear();
        videoViews.clear();
    }

    public void configureLayout(Dictionary cdict) {
        drawLayouts.clear();
        for (Map.Entry<String, Object> entry : cdict.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!(value instanceof Dictionary)) {
                // ignore invalid option
                Log.d("Godot", String.format("Invalid Layout configuration for key '%s'", key));
                continue;
            }
            Log.i("Godot", String.format("Parsing layout options for key '%s'", key));
            Dictionary v = (Dictionary) value;
            drawLayouts.put(key, new DrawLayout(v));
        }
        reload_layouts();
    }



}
