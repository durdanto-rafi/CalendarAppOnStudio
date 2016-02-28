package com.uniqgroup.utility;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CircleAngleAnimation extends Animation {

    private Circle circle;

    private float oldAngle;
    private float newAngle;

    public CircleAngleAnimation(Circle circle2, int newAngle) {
        this.oldAngle = circle2.getAngle();
        this.newAngle = newAngle;
        this.circle = circle2;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = oldAngle - ((oldAngle-newAngle ) * interpolatedTime);

        circle.setAngle(angle);
        circle.requestLayout();
    }
}