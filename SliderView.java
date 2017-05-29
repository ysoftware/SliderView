package ru.firstpro.photoeditor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import static ru.firstpro.photoeditor.SliderViewHorizontalIndicator.Anchor.left;

/**
   Created by ysoftware on 13.04.17.
 */


public class SliderView extends RelativeLayout {

	interface Delegate {
		void sliderViewNewValue(SliderView sliderView, float newValue);
		void sliderViewWillChangeValue(SliderView sliderView, float oldValue);
		void sliderViewDidChangeValue(SliderView sliderView, float newValue);
	}

	public SliderView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		setBackgroundColor(Color.TRANSPARENT);
	}

	/* PROPERTIES */

	@Nullable Delegate delegate;

	@Nullable public SliderViewHorizontalIndicator getIndicator() {
		return indicator;
	}

	public void setIndicator(@Nullable SliderViewHorizontalIndicator indicator) {
		this.indicator = indicator;
		if (indicator != null) {
			indicator.setMinValue(minValue);
			indicator.setMaxValue(maxValue);
			indicator.setValue(value);
			indicator.reloadViews();
		}
	}

	/* VALUES */

	public void setValue(float v) {
		value = v;
		if (value > maxValue) { value = maxValue; }
		else if (value < minValue) { value = minValue; }
		if (indicator != null) indicator.setValue(value);
	}

	public void setMinValue(float v) {
		minValue = v;
		if (minValue >= maxValue) { minValue = maxValue - 0.1f; }
		if (indicator != null) indicator.setMinValue(minValue);
	}

	public void setMaxValue(float v) {
		this.maxValue = v;
		if (minValue >= maxValue) { maxValue = minValue + 0.1f; }
		if (indicator != null) indicator.setMaxValue(maxValue);
	}

	public float getValue() {
		return value;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public float getMinValue() {
		return minValue;
	}

	public float getMiddleValue() {
		return (minValue + maxValue) / 2;
	}

	public void setMiddleValue() { setValue(getMiddleValue()); }

	/* SETTINGS */

	public Boolean isEnabled = true;

	public void setSensitivity(float s) {
		this.sensitivity = s;
		if (sensitivity <= 0) { sensitivity = 0.01f; }
	}

	public void setResolution(float r) {
		this.resolution = r;
		if (resolution > 0) { resolution = 0f; }
	}

	public float getResolution() {
		return resolution;
	}

	public float getSensitivity() {
		return sensitivity;
	}

	/* PRIVATE */

	private float value = 0.5f;
	private float minValue = 0f;
	private float maxValue = 1f;
	private float resolution = 0f;
	private float sensitivity = 1.5f;

	private Boolean isChangingValue = false;
	private PointF touchDownPosition;
	private @Nullable SliderViewHorizontalIndicator indicator;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = MotionEventCompat.getActionMasked(event);

		if (!isEnabled) { return false; }

		switch(action) {
			case MotionEvent.ACTION_DOWN:
				if (!isChangingValue) {
					if (delegate != null) delegate.sliderViewWillChangeValue(this, value);
					touchDownPosition = new PointF(event.getX(), event.getY());
					isChangingValue = true;
				}
				return true;
			case MotionEvent.ACTION_MOVE:
				float translation = (event.getX() - touchDownPosition.x) -
						(event.getY() - touchDownPosition.y);
				if (Math.abs(translation) >= resolution) {
					float dif = maxValue - minValue;
					float size = Math.max(getMeasuredWidth(), getMeasuredHeight());
					float panSize = size / sensitivity;
					float adaptation = panSize / dif;

					setValue(value += translation / adaptation);
					if (delegate != null) delegate.sliderViewNewValue(this, value);
					touchDownPosition = new PointF(event.getX(), event.getY());
				}
				return true;
			default:
				if (isChangingValue) {
					isChangingValue = false;
					if (delegate != null) delegate.sliderViewDidChangeValue(this, value);
				}
				return true;
		}
	}
}

