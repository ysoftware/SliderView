package ru.firstpro.photoeditor;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import static ru.firstpro.photoeditor.SliderViewHorizontalIndicator.Anchor.left;

/**
 * Created by ysoftware on 24.05.17.
 */

public class SliderViewHorizontalIndicator extends RelativeLayout {
	public SliderViewHorizontalIndicator(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		foregroundView = new RelativeLayout(context);
		addView(foregroundView);
	}

	/* VALUES */

	public void setMaxValue(float v) {
		maxValue = v;
		if (minValue >= maxValue) { maxValue = minValue + 0.1f; }
		reloadViews();
	}

	public void setMinValue(float v) {
		minValue = v;
		if (minValue >= maxValue) { minValue = maxValue - 0.1f; }
		reloadViews();
	}

	public void setValue(float v) {
		value = v;
		if (value > maxValue) { value = maxValue; }
		else if (value < minValue) { value = minValue; }
		reloadViews();
	}

	public float getValue() {
		return value;
	}

	public float getMinValue() {
		return minValue;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public float getMiddleValue() {
		return (minValue + maxValue) / 2;
	}

	/* SETTINGS */

	enum Anchor { left, right, center }

	public void setAnchor(Anchor a) {
		anchor = a;
		reloadViews();
	}

	public void setRelativePadding(float v) {
		relativePadding = v;
		if (relativePadding >= 1) { relativePadding = 0.99f; }
		else if (relativePadding < 0) { relativePadding = 0f; }
		reloadViews();
	}

	public void setForegroundColor(int c) {
		foregroundColor = c;
		foregroundView.setBackgroundColor(foregroundColor);
	}

	public Anchor getAnchor() {
		return anchor;
	}

	public float getRelativePadding() {
		return relativePadding;
	}

	public int getForegroundColor() {
		return foregroundColor;
	}

	/* PRIVATE */

	private float maxValue = 1f;
	private float minValue = 0f;
	private float value = 0.5f;
	private float relativePadding = 0f;
	private int foregroundColor = Color.parseColor("#FFF25F"); // a nice yellow color by default
	private Anchor anchor = left;
	private RelativeLayout foregroundView;

	void reloadViews() {
		foregroundView.setBackgroundColor(foregroundColor);

		float dif = maxValue - minValue;
		float width = getMeasuredWidth();
		int height = getMeasuredHeight();

		switch (anchor) {
			case center: {
				Boolean isPositive = getMiddleValue() - value < 0;
				float relativeValue = getMiddleValue() - value;
				float center = width / 2;
				float indicatorWidth = center - (center * relativePadding);
				float position = Math.abs(indicatorWidth / dif * 2 * relativeValue);

				int _width = Math.round(position);
				int _x = Math.round(isPositive ? center : center - position);

				LayoutParams params = new LayoutParams(_width, height);
				params.setMarginStart(_x);
				foregroundView.setLayoutParams(params);
				break;
			}
			case left:
			case right: {
				float indicatorWidth = width - (width * relativePadding);
				float position = indicatorWidth * (value - minValue) / (maxValue - minValue);

				int _width = Math.round(position);
				int _x = anchor == left ? 0 : Math.round(width - position);

				LayoutParams params = new LayoutParams(_width, height);
				params.setMarginStart(_x);
				foregroundView.setLayoutParams(params);
				break;
			}
		}
	}
}