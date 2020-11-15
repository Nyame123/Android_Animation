package com.bisapp.android_animations;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeScroll;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class AnimationFragment extends Fragment implements View.OnClickListener {

    private Button rotateBtn;
    private Button scaleBtn;
    private Button moveBtn, moveBtnX, playTogether, fadeBtn;
    private TextView firstTextview;
    private ObjectAnimator objectAnimator;
    private boolean isAnimationStarted;
    private AnimatorSet set;
    private LinearLayout firstLay;

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
        moveBtnX = view.findViewById(R.id.moveBtnX);
        playTogether = view.findViewById(R.id.cominedAnim);
        scaleBtn = view.findViewById(R.id.scaleBtn);
        fadeBtn = view.findViewById(R.id.fadeBtn);
        rotateBtn = view.findViewById(R.id.rotateBtn);
        firstLay = view.findViewById(R.id.lay_first);
       /* LayoutTransition transition = ((LinearLayout)view.findViewById(R.id.lay_first)).setLayoutTransition(LayoutTransition.APPEARING);
        transition.enableTransitionType(LayoutTransition.CHANGING);*/

        moveBtn.setOnClickListener(this);
        moveBtnX.setOnClickListener(this);
        playTogether.setOnClickListener(this);
        scaleBtn.setOnClickListener(this);
        rotateBtn.setOnClickListener(this);
        fadeBtn.setOnClickListener(this);


    }

    private void moveTextviewOnYaxis() {
        isAnimationStarted = true;
        objectAnimator = ObjectAnimator.ofFloat(firstTextview, "translationY", 0, 600);
        objectAnimator.setDuration(2000)
                .setInterpolator(new AnticipateOvershootInterpolator());
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setRepeatCount(1);
        objectAnimator.start();



    }

    private void moveTextviewOnXaxis() {
        isAnimationStarted = true;
        objectAnimator = ObjectAnimator.ofFloat(firstTextview, "translationX", 0, 600);
        objectAnimator.setDuration(2000)
                .setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setRepeatCount(1);
        objectAnimator.start();

    }

    private void rotateTextView() {
        isAnimationStarted = true;
        objectAnimator = ObjectAnimator.ofFloat(firstTextview,
                "rotation", 0, 90);
        objectAnimator.setDuration(2000)
                .setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setRepeatCount(1);
        objectAnimator.start();

    }

    private void fadeTextview() {
        isAnimationStarted = true;
        objectAnimator = ObjectAnimator.ofFloat(firstTextview,
                "alpha", 0, 1);
        objectAnimator.setDuration(2000)
                .setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setRepeatCount(1);
        objectAnimator.start();

    }

    private void scaleTextview() {
        isAnimationStarted = true;
        objectAnimator = ObjectAnimator.ofFloat(firstTextview,
                "scaleY", 1, 20);
        objectAnimator.setDuration(2000)
                .setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setRepeatCount(1);
        objectAnimator.start();

    }

    private void animateWithViewAnimation(){
        firstTextview.animate().scaleXBy(20).start();
    }

    private void combineAnimations() {
        isAnimationStarted = true;
        ObjectAnimator animX = ObjectAnimator.ofFloat(firstTextview,
                "x", 50f);
        animX.setDuration(2000)
                .setInterpolator(new LinearInterpolator());

        ObjectAnimator animY = ObjectAnimator.ofFloat(firstTextview,
                "y", 100f);
        animY.setDuration(2000)
                .setInterpolator(new LinearInterpolator());

        ObjectAnimator scaleAnim = ObjectAnimator.ofFloat(firstTextview,
                "scaleY", 1, 20);
        scaleAnim.setDuration(2000)
                .setInterpolator(new LinearInterpolator());


        set = new AnimatorSet();
        set.playTogether(scaleAnim, animX, animY);
        set.start();
    }

    private void loadAnimatorFromXml(){
        AnimatorSet animatorInflater = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),R.animator.pivot_scale_animator);
        animatorInflater.setTarget(firstTextview);
        animatorInflater.start();

    }

    private void usingPropertyValueHolder() {
        isAnimationStarted = true;
        PropertyValuesHolder animX = PropertyValuesHolder.ofFloat("x", 50f);
        PropertyValuesHolder animY = PropertyValuesHolder.ofFloat("y", 100f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 20);

        ObjectAnimator.ofPropertyValuesHolder(firstTextview,animX, animY, scaleY).start();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.moveBtn:
                if (isAnimationStarted && objectAnimator != null) {
                    objectAnimator.cancel();
                    isAnimationStarted = false;
                } else {
                    moveTextviewOnYaxis();
                }
                enableTransitionsOn(firstLay);
                break;
            case R.id.moveBtnX:
                if (isAnimationStarted && objectAnimator != null) {
                    objectAnimator.cancel();
                    isAnimationStarted = false;
                } else {
                    moveTextviewOnXaxis();
                }
                break;
            case R.id.rotateBtn:
                if (isAnimationStarted && objectAnimator != null) {
                    objectAnimator.cancel();
                    isAnimationStarted = false;
                } else {
                    rotateTextView();
                }
                break;
            case R.id.fadeBtn:
                if (isAnimationStarted && objectAnimator != null) {
                    objectAnimator.cancel();
                    isAnimationStarted = false;
                } else {
                    fadeTextview();
                }
                break;
            case R.id.scaleBtn:
                if (isAnimationStarted && objectAnimator != null) {
                    objectAnimator.cancel();
                    isAnimationStarted = false;
                } else {
                    scaleTextview();
                }
                break;
            case R.id.cominedAnim:
                /*if (isAnimationStarted && set != null) {
                    set.cancel();
                    isAnimationStarted = false;
                } else {
                    combineAnimations();
                }*/
                //usingPropertyValueHolder();
                loadAnimatorFromXml();

                break;
        }

    }

    private void enableTransitionsOn(ViewGroup viewGroup) {
        //firstLay.setLayoutTransition(new LayoutTransition());
        TransitionSet set = new TransitionSet();
        set.addTransition(new Fade());
        set.addTransition(new ChangeBounds());
        TransitionManager.beginDelayedTransition(viewGroup, set);
        playTogether.setVisibility(isAnimationStarted? View.VISIBLE: View.GONE);
    }
}