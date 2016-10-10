package com.behivordemo.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.behivordemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 16/10/10.
 */

public class SignInView extends View {
    private static final int DEF_HEIGHT = 85;
    private static final int DEF_PADDING = 10;
    private static final int TEXT_MARGIN_TOP = 13;
    private static final float SECTION_SCALE = 1.2F / 2;
    private static final float SIGN_IN_BALL_SCALE = 1F / 6;
    private static final float SIGN_BG_RECT_SCALE = 1F / 4;
    private static final long DEF_ANIM_TIME = 1000;

    private int signInBgColor;
    private int signInPbColor;
    private int signInCheckColor;
    private int singInTextColor;
    private int singInTextSize;

    private Paint signInBgPaint;
    private Paint signInPbPaint;
    private Paint signInCheckPaint;
    private Paint signInTextPaint;

    private int viewWidth;
    private int viewHeight;
    private int viewPadding;
    private int signInBallRadio;
    private int signRectHeight;

    private RectF signInBgRectF;
    private int circleY;
    private int descY;

    private List<String> viewData;
    private List<Point> circlePoints;
    private List<Point> descPoints;
    private List<Path> signInPaths;
    private List<RectF> signInPbRectFs;


    private int currentSignTag;
    private ValueAnimator valueAnimator;
    private float persent;
    private RectF progressRectF;

    public SignInView(Context context) {
        this(context, null);
    }

    public SignInView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignInView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("--TAG---", "构造函数--->>");
        initAttrs(context, attrs, defStyleAttr);
        initToolsAndData();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("--TAG---", "onSizeChanged--->>");
        viewPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_PADDING, getResources().getDisplayMetrics());
        int textMarginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TEXT_MARGIN_TOP, getResources().getDisplayMetrics());

        viewWidth = w;
        viewHeight = h;
        signInBallRadio = (int) (viewHeight * SIGN_IN_BALL_SCALE / 2);
        signRectHeight = (int) (signInBallRadio * SIGN_BG_RECT_SCALE);

        signInBgRectF = new RectF(viewPadding + signInBallRadio, viewHeight * SECTION_SCALE - signInBallRadio - signRectHeight, viewWidth - viewPadding - signInBallRadio, viewHeight * SECTION_SCALE - signInBallRadio);

        circleY = (int) (signInBgRectF.top + signRectHeight / 2);
        descY = (int) (viewHeight * SECTION_SCALE) + signInBallRadio + textMarginTop;
        calculateCirclePoints(viewData);

        progressRectF = signInPbRectFs.get(0);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("--TAG---", "onMeasure--->>");

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureHeight;

        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            measureHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_HEIGHT, getResources().getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSignInBgRect(canvas);
        drawSignInPbRectNoAnim(canvas);
        drawSignInNormalCircle(canvas);
        drawSingInCheckCircle(canvas);
        drawSignDesc(canvas);
    }

    /**
     * 属性动画的形式绘制进度条
     *
     * @param canvas
     */
    private void drawSignInPbRectWithAnim(final Canvas canvas) {
        if (isNeedReturn()) {
            return;
        }
        final RectF targetRectF = signInPbRectFs.get(currentSignTag);
        RectF beforeRectF;
        if (currentSignTag >= 1) {
            beforeRectF = signInPbRectFs.get(currentSignTag - 1);
            progressRectF.left = beforeRectF.left;
        }
        progressRectF.right = targetRectF.right * persent;
        canvas.drawRect(progressRectF, signInPbPaint);
    }


    private void drawSignInPbRectNoAnim(final Canvas canvas) {
        if (isNeedReturn()) {
            return;
        }
        canvas.drawRect(signInPbRectFs.get(currentSignTag), signInPbPaint);
    }

    private boolean isNeedReturn() {
        return currentSignTag < 0 || currentSignTag >= viewData.size();
    }

    private void drawSingInCheckCircle(Canvas canvas) {
        if (isNeedReturn()) {
            return;
        }
        for (int i = -1; i < currentSignTag; i++) {
            Point p = circlePoints.get(i + 1);
            Path path = signInPaths.get(i + 1);
            canvas.drawCircle(p.x, p.y, signInBallRadio, signInPbPaint);
            canvas.drawPath(path, signInCheckPaint);
        }
    }

    private void drawSignDesc(Canvas canvas) {
        for (int i = 0; i < viewData.size(); i++) {
            Point p = descPoints.get(i);
            canvas.drawText(viewData.get(i), p.x, p.y, signInTextPaint);
        }
    }

    private void drawSignInNormalCircle(Canvas canvas) {
        for (Point p : circlePoints) {
            canvas.drawCircle(p.x, p.y, signInBallRadio, signInBgPaint);
        }
    }

    private void drawSignInBgRect(Canvas canvas) {
        canvas.drawRect(signInBgRectF, signInBgPaint);
    }

    private Paint creatPaint(int paintColor, int textSize, Paint.Style style, int lineWidth) {
        Paint paint = new Paint();
        paint.setColor(paintColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(lineWidth);
        paint.setDither(true);
        paint.setTextSize(textSize);
        paint.setStyle(style);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        return paint;
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SignInView, defStyleAttr, R.style.def_sign_in_style);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.SignInView_sign_in_bg_clor:
                    signInBgColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.SignInView_sign_in_pb_clor:
                    signInPbColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.SignInView_sign_in_check_clor:
                    signInCheckColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.SignInView_sign_in_text_clor:
                    singInTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.SignInView_sign_in_text_size:
                    singInTextSize = typedArray.getDimensionPixelSize(attr, 0);
                    break;
            }
        }
        typedArray.recycle();
    }

    private void initToolsAndData() {
        circlePoints = new ArrayList<>();
        descPoints = new ArrayList<>();
        signInPaths = new ArrayList<>();
        signInPbRectFs = new ArrayList<>();
        currentSignTag = -1;
        valueAnimator = getValA(DEF_ANIM_TIME);

        signInBgPaint = creatPaint(signInBgColor, 0, Paint.Style.FILL, 0);
        signInPbPaint = creatPaint(signInPbColor, 0, Paint.Style.FILL, 0);
        signInCheckPaint = creatPaint(signInCheckColor, 0, Paint.Style.STROKE, 3);
        signInTextPaint = creatPaint(singInTextColor, singInTextSize, Paint.Style.FILL, 0);
    }

    public void setSignInData(List<String> signInData) {
        Log.e("--TAG---", "外界设置数据--->>");
        if (null != signInData) {
            viewData = signInData;
        }
    }

    public void signInEvent() {
        currentSignTag++;
        if (currentSignTag >= viewData.size()) {
            currentSignTag = viewData.size() - 1;
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                persent = Float.valueOf(valueAnimator.getAnimatedValue().toString());
                invalidate();
                Log.e("TAG--->>", "persent--->" + persent);
            }
        });
        valueAnimator.start();
        invalidate();
    }

    public void resetSignView() {
        currentSignTag = -1;
        invalidate();
    }

    private void calculateCirclePoints(List<String> viewData) {
        if (null != viewData) {
            int intervalSize = viewData.size() - 1;
            int onePiece = (viewWidth - 2 * viewPadding - signInBallRadio * 2 * viewData.size()) / intervalSize;
            for (int i = 0; i < viewData.size(); i++) {
                Point circleP = new Point(viewPadding + i * onePiece + ((i + 1) * 2 - 1) * signInBallRadio, circleY);
                Point descP = new Point((int) (viewPadding + i * onePiece + ((i + 1) * 2 - 1) * signInBallRadio - signInTextPaint.measureText(viewData.get(i)) / 2), descY);

                Path signInPath = new Path();
                signInPath.moveTo(circleP.x - signInBallRadio / 2, circleP.y);
                signInPath.lineTo(circleP.x, circleP.y + signInBallRadio / 2);
                signInPath.lineTo(circleP.x + signInBallRadio / 2, circleP.y - signInBallRadio + signInBallRadio / 2);

                RectF signInPbRectF = new RectF(viewPadding + signInBallRadio, viewHeight * SECTION_SCALE - signInBallRadio - signRectHeight, circleP.x, viewHeight * SECTION_SCALE - signInBallRadio);

                signInPaths.add(signInPath);
                circlePoints.add(circleP);
                descPoints.add(descP);
                signInPbRectFs.add(signInPbRectF);
            }
        }
    }

    private ValueAnimator getValA(long countdownTime) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.F);
        valueAnimator.setDuration(countdownTime);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(0);
        return valueAnimator;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("--TAG---", "onAttachedToWindow--->>");
    }
}
