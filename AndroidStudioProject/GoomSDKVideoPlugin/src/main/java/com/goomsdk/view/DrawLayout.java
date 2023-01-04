package com.goomsdk.view;

import android.view.View;
import android.widget.FrameLayout;
import java.util.Map;
import android.util.Log;

import org.godotengine.godot.Dictionary;

public class DrawLayout {
    int top = 0; int left = 0; int height = 100; int width = 100;

    public DrawLayout() {

    }

    public DrawLayout(Dictionary cdict) {
        for (Map.Entry<String, Object> entry : cdict.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(!(value instanceof Integer)) {
                // ignore invalid option
                Log.d("Godot", String.format("Invalid Option passed to DrawLayout with key = '%s'", key));
                continue;
            }
            Integer v = (Integer) value;

            switch (key) {
                case "top":
                    top = v;
                    break;
                case "left":
                    left = v;
                    break;
                case "width":
                    width = v;
                    break;
                case "height":
                    height = v;
                    break;
                default:
                    Log.i("Godot", String.format("Ignoring option passed to DrawLayout with key = '%s'", key));
            }

        }
        Log.i("Godot", String.format("Created Layout with (l=%d,t=%d,w=%d,h=%d)", left, top, width, height));
    }

    public void configureTarget(View target) {
        int w = width;
        int h = height;
        int l = left;
        int t = top;
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(width, height);
        layoutParams.leftMargin = left;
        layoutParams.topMargin = top;

        target.setLayoutParams(layoutParams);
    }

}
