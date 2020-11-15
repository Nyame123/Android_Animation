package com.bisapp.android_animations;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class DrawableAnimationFragment extends Fragment {

    private AnimationDrawable blinkingStarBackground;
    private boolean isStarted = false;
    private AnimatedVectorDrawable animatedVectorDrawable;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView blinkingStars = view.findViewById(R.id.blinking_stars);
        ImageView morphImage = view.findViewById(R.id.morph_img);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            getAnimatedVectorDrawable(morphImage);
        //blinkingStars.getDrawable().setLevel(50);
        blinkingStarBackground = (AnimationDrawable) blinkingStars.getBackground();


        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStarted = !isStarted;
                if (isStarted) blinkingStarBackground.start();
                else blinkingStarBackground.stop();
            }
        });
        view.findViewById(R.id.animate_vecctor_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStarted = !isStarted;
                if (isStarted) animatedVectorDrawable.start();
                else animatedVectorDrawable.stop();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAnimatedVectorDrawable(ImageView morphImage) {
        animatedVectorDrawable = (AnimatedVectorDrawable) morphImage.getDrawable();
    }
}