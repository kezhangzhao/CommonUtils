package com.kzz.utilslibrary;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * author : zhangzhao.ke
 * time   : 2019/01/04
 * desc   : TextView监听类，所有的TextView不为空会返回true
 */

public class TextChangedUtils {

    /**
     * 设置监听
     * @param listener 回调
     * @param views TextViews
     */
    public static void setMyTextViewsListener(final MyTextViewsListener listener, final TextView... views) {
        checkEmpty(listener, views);
        for (TextView view : views) {
            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    checkEmpty(listener, views);
                }
            });
        }
    }

    /**
     * 检验是否有空的TextView
     * @param listener 回调
     * @param views TextViews
     */
    private static void checkEmpty(MyTextViewsListener listener, TextView... views) {
        int emptyNum = views.length;
        for (TextView view : views) {
            if (!TextUtils.isEmpty(view.getText().toString()))
                emptyNum--;
        }
        if (emptyNum != 0) {
            listener.result(false);
        } else {
            listener.result(true);
        }
    }

    /**
     * 回调
     */
    public interface MyTextViewsListener {
        void result(boolean isEmpty);
    }
}
