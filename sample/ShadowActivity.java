package com.huanhailiuxin.jet2020.othertest.shadow;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.huanhailiuxin.jet2020.BaseActivity;
import com.huanhailiuxin.jet2020.R;

public class ShadowActivity extends BaseActivity {
    private RoundShadowImageView shadowImageView;
    ObjectAnimator animator;
    private float shadowCircleAngle,shadowRatio,shadowStartAlpha;
    private @ShadowPosition int shadowPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow);
        setTitle("试验阴影");
        shadowImageView = findViewById(R.id.shadowImageView);
        shadowCircleAngle = shadowImageView.getShadowCircleAngle();
        shadowRatio = shadowImageView.getShadowRatio();
        shadowStartAlpha = shadowImageView.getShadowStartAlpha();
        shadowPosition = shadowImageView.getShadowPosition();
    }
    private void reset(){
        shadowImageView.setShadowPosition(shadowPosition);
        shadowImageView.setShadowCircleAngle(shadowCircleAngle);
        shadowImageView.setShadowRatio(shadowRatio);
        shadowImageView.setShadowStartAlpha(shadowStartAlpha);
    }
    private void initAnimator(){
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setDuration(4000);
        animator.setInterpolator(new LinearInterpolator());
    }
    private void initAngleAnimator(){
        if(animator != null){
           animator.cancel();
        }
        animator = ObjectAnimator.ofFloat(shadowImageView,"shadowCircleAngle",0F,360F);
        initAnimator();
    }
    public void rotateTheShadow(View view) {
        reset();
        initAngleAnimator();
        animator.start();
    }
    private void initShadowRatioAnimator(){
        if(animator != null){
            animator.cancel();
        }
        animator = ObjectAnimator.ofFloat(shadowImageView,"shadowRatio",0.1F,0.5F);
        initAnimator();
    }
    public void modifyShadowRatio(View view) {
        reset();
        initShadowRatioAnimator();
        animator.start();
    }
    private void initShadowStartAlphaAnimator(){
        if(animator != null){
            animator.cancel();
        }
        animator = ObjectAnimator.ofFloat(shadowImageView,"shadowStartAlpha",0.1F,0.9F);
        initAnimator();
    }
    public void modifyShadowStartAlpha(View view) {
        reset();
        initShadowStartAlphaAnimator();
        animator.start();
    }

    private int shadowPositionIndex = 0;
    private @ShadowPosition int[] shadowPositions = new int[]{
            ShadowPosition.START,
            ShadowPosition.TOP,
            ShadowPosition.END,
            ShadowPosition.BOTTOM
    };
    public void modifyShadowPosition(View view) {
        if(animator != null){
            animator.cancel();
        }
        reset();
        int index = shadowPositionIndex ++ % shadowPositions.length;
        shadowImageView.setShadowPosition(shadowPositions[index]);
    }
}