package com.bisapp.android_animations;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class AnimationFragment extends Fragment implements View.OnClickListener {

    private Button rotateBtn;
    private Button scaleBtn;
    private Button moveBtn,fadeBtn;
    private TextView firstTextview;
    private ObjectAnimator objectAnimator;
    private boolean isAnimationStarted;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstTextview = view.findViewById(R.id.textview_first);
        moveBtn = view.findViewById(R.id.moveBtn);
        scaleBtn = view.findViewById(R.id.scaleBtn);
        fadeBtn = view.findViewById(R.id.fadeBtn);
        rotateBtn = view.findViewById(R.id.rotateBtn);

        moveBtn.setOnClickListener(this);
        scaleBtn.setOnClickListener(this);
        rotateBtn.setOnClickListener(this);
        fadeBtn.setOnClickListener(this);


    }

    private void moveTextview(){
        isAnimationStarted = true;
        objectAnimator = ObjectAnimator.ofFloat(firstTextview, "translationY", 0,600);
        objectAnimator.setDuration(2000)
                .setInterpolator(new AnticipateOvershootInterpolator());
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setRepeatCount(1);
        objectAnimator.start();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.moveBtn:
                if (isAnimationStarted && objectAnimator != null){
                    objectAnimator.cancel();
                    isAnimationStarted = false;
                }else {
                    moveTextview();
                }
                break;
        }

    }
}