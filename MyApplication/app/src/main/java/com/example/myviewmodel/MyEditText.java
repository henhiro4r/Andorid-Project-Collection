package com.example.myviewmodel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyEditText extends android.support.v7.widget.AppCompatEditText {

    Drawable mClearButton;

    public MyEditText(Context context) {
        super(context);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setHint("Masukan nama Anda");
        setTextAlignment(TEXT_ALIGNMENT_VIEW_START);
    }

    private void init(){
        mClearButton = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_black_24dp, null);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if ((getCompoundDrawablesRelative()[2] != null)){
                    float clearButtonStart;
                    float clearButtonEnd;
                    boolean isCliced = false;
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL){
                        clearButtonEnd = mClearButton.getIntrinsicWidth() + getPaddingStart();
                        if (motionEvent.getX() < clearButtonEnd){
                            isCliced = true;
                        }
                    }else{
                        clearButtonStart = (getWidth() - getPaddingEnd() - mClearButton.getIntrinsicWidth());
                        if (motionEvent.getX() > clearButtonStart){
                            isCliced = true;
                        }
                    }
                    if (isCliced){
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                            mClearButton = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_black_24dp, null);
                            showClearButton();
                            return true;
                        }else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                            mClearButton = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_black_24dp, null);
                            if (getText() != null){
                                getText().clear();
                            }
                            hideClearButton();
                            return true;
                        }else {
                            return false;
                        }
                    }else {
                        return false;
                    }
                }
                return false;
            }
        });

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().isEmpty()){
                    showClearButton();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mClearButton, null);
    }

    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
    }
}
