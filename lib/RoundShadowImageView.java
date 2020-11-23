package com.huanhailiuxin.jet2020.othertest.shadow;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.huanhailiuxin.jet2020.R;

/**
 * @author HuanHaiLiuXin
 * @github https://github.com/HuanHaiLiuXin
 * @date 2020/11/23
 */
public class RoundShadowImageView extends AppCompatImageView {
    private Paint paint;
    private Shader shader;
    int[] colors;
    float[] stops;
    private float contentSize;
    @FloatRange(from = 0.0F, to = 1.0F)
    private float shadowRatio = 0.30F;
    private float shadowRadius = 0.0F;
    private float shadowCenterX, shadowCenterY;
    @ShadowPosition
    private int shadowPosition = ShadowPosition.BOTTOM;
    private float shadowCircleAngle = 0F;
    private boolean useShadowCircleAngle = false;
    private int red, green, blue;
    private int shadowColor = Color.RED;
    private @FloatRange(from = 0F, to = 1F)
    float shadowStartAlpha = 0.5F;
    private boolean isLtr = true;

    public RoundShadowImageView(Context context) {
        this(context, null, 0);
    }

    public RoundShadowImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundShadowImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        isLtr = getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundShadowImageView);
            shadowRatio = typedArray.getFloat(R.styleable.RoundShadowImageView_shadowRatio, shadowRatio);
            shadowCircleAngle = typedArray.getFloat(R.styleable.RoundShadowImageView_shadowCircleAngle, shadowCircleAngle);
            if (shadowCircleAngle > 0F) {
                useShadowCircleAngle = true;
            }
            if (!useShadowCircleAngle) {
                shadowPosition = typedArray.getInt(R.styleable.RoundShadowImageView_shadowPosition, shadowPosition);
            }
            shadowColor = typedArray.getColor(R.styleable.RoundShadowImageView_shadowColor, shadowColor);
            gainRGB();
            shadowStartAlpha = typedArray.getFloat(R.styleable.RoundShadowImageView_shadowStartAlpha, shadowStartAlpha);
            typedArray.recycle();
        }
    }

    private void gainRGB() {
        red = Color.red(shadowColor);
        green = Color.green(shadowColor);
        blue = Color.blue(shadowColor);
    }

    private void gainShadowCenterAndShader() {
        gainShadowCenter();
        gainShader();
    }

    private void gainShadowCenter() {
        shadowRadius = contentSize / 2F;
        if (useShadowCircleAngle) {
            double radians = Math.toRadians(shadowCircleAngle + 90);
            shadowCenterX = (float) (getWidth() / 2 + Math.cos(radians) * shadowRadius * shadowRatio);
            shadowCenterY = (float) (getHeight() / 2 + Math.sin(radians) * shadowRadius * shadowRatio);
        } else {
            switch (shadowPosition) {
                case ShadowPosition.START:
                    if (isLtr) {
                        shadowCenterX = getWidth() / 2 - shadowRadius * shadowRatio;
                    } else {
                        shadowCenterX = getWidth() / 2 + shadowRadius * shadowRatio;
                    }
                    shadowCenterY = getHeight() / 2;
                    break;
                case ShadowPosition.TOP:
                    shadowCenterY = getHeight() / 2 - shadowRadius * shadowRatio;
                    shadowCenterX = getWidth() / 2;
                    break;
                case ShadowPosition.END:
                    if (isLtr) {
                        shadowCenterX = getWidth() / 2 + shadowRadius * shadowRatio;
                    } else {
                        shadowCenterX = getWidth() / 2 - shadowRadius * shadowRatio;
                    }
                    shadowCenterY = getHeight() / 2;
                    break;
                case ShadowPosition.BOTTOM:
                    shadowCenterY = getHeight() / 2 + shadowRadius * shadowRatio;
                    shadowCenterX = getWidth() / 2;
                    break;
                default:
                    shadowCenterY = getHeight() / 2 + shadowRadius * shadowRatio;
                    shadowCenterX = getWidth() / 2;
                    break;
            }
        }
    }

    private void gainShader() {
        colors = new int[]{
                Color.TRANSPARENT,
                Color.argb((int) (shadowStartAlpha * 255), red, green, blue),
                Color.argb((int) (shadowStartAlpha * 255 / 2), red, green, blue),
                Color.argb(0, red, green, blue)
        };
        stops = new float[]{
                (1F - shadowRatio) * 0.95F,
                1F - shadowRatio,
                1F - shadowRatio * 0.50F,
                1F
        };
        shader = new RadialGradient(shadowCenterX, shadowCenterY, shadowRadius, colors, stops, Shader.TileMode.CLAMP);
    }

    private void contentSizeChanged() {
        contentSize = Math.min(getWidth(), getHeight()) / (1 + this.shadowRatio);
        setPadding((int) (getWidth() - contentSize) / 2, (int) (getHeight() - contentSize) / 2, (int) (getWidth() - contentSize) / 2, (int) (getHeight() - contentSize) / 2);
        gainShadowCenterAndShader();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        contentSizeChanged();
    }

    public void setShadowRatio(@FloatRange(from = 0.0F, to = 1.0F) float shadowRatio) {
        shadowRatio = shadowRatio % 1F;
        if (shadowRatio != this.shadowRatio) {
            this.shadowRatio = shadowRatio;
            contentSizeChanged();
            invalidate();
        }
    }

    public void setShadowColor(@ColorInt int shadowColor) {
        if (shadowColor != this.shadowColor) {
            this.shadowColor = shadowColor;
            gainRGB();
            gainShader();
            invalidate();
        }
    }

    public void setShadowStartAlpha(@FloatRange(from = 0F, to = 1F) float shadowStartAlpha) {
        shadowStartAlpha = shadowStartAlpha % 1F;
        if (shadowStartAlpha != this.shadowStartAlpha) {
            this.shadowStartAlpha = shadowStartAlpha;
            gainShader();
            invalidate();
        }
    }

    public void setShadowCircleAngle(float shadowCircleAngle) {
        shadowCircleAngle = Math.abs(shadowCircleAngle) % 360.0F;
        if (shadowCircleAngle != this.shadowCircleAngle) {
            this.shadowCircleAngle = shadowCircleAngle;
            if (this.shadowCircleAngle > 0F) {
                useShadowCircleAngle = true;
            }
            gainShadowCenterAndShader();
            invalidate();
        }
    }

    public void setShadowPosition(@ShadowPosition int shadowPosition){
        if(useShadowCircleAngle || shadowPosition != this.shadowPosition){
            useShadowCircleAngle = false;
            this.shadowPosition = shadowPosition;
            gainShadowCenterAndShader();
            invalidate();
        }
    }

    public float getShadowRatio() {
        return shadowRatio;
    }

    public float getShadowCircleAngle() {
        return shadowCircleAngle;
    }

    public int getShadowColor() {
        return shadowColor;
    }

    public float getShadowStartAlpha() {
        return shadowStartAlpha;
    }

    public int getShadowPosition() {
        return shadowPosition;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setShader(shader);
        canvas.drawCircle(shadowCenterX, shadowCenterY, shadowRadius, paint);
        paint.setShader(null);
        super.onDraw(canvas);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        boolean newLtr = getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR;
        if (newLtr != isLtr) {
            this.isLtr = newLtr;
            gainShadowCenterAndShader();
            invalidate();
        }
    }
}
