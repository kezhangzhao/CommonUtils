package com.kzz.utilslibrary;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author : zhangzhao.ke
 * time   : 2018/11/12
 * desc   : 字符串变色工具类
 */

public class StringFormatUtil {

    private SpannableStringBuilder spBuilder;
    private String wholeStr, highlightStr;
    private Context mContext;
    private int color;

    /**
     * 只有第一个相同的字才会变色
     *
     * @param context      上下文
     * @param wholeStr     全部文字
     * @param highlightStr 改变颜色的文字
     * @param color        颜色
     */
    public StringFormatUtil(Context context, String wholeStr, String highlightStr, int color) {
        this.mContext = context;
        this.wholeStr = wholeStr;
        this.highlightStr = highlightStr;
        this.color = color;
    }

    public StringFormatUtil fillColor() {
        int start;
        int end;
        if (!TextUtils.isEmpty(wholeStr) && !TextUtils.isEmpty(highlightStr)) {
            if (wholeStr.contains(highlightStr)) {
                //返回highlightStr字符串wholeStr字符串中第一次出现处的索引。
                start = wholeStr.indexOf(highlightStr);
                end = start + highlightStr.length();
            } else {
                return null;
            }
        } else {
            return null;
        }
        spBuilder = new SpannableStringBuilder(wholeStr);
        color = mContext.getResources().getColor(color);
        CharacterStyle charaStyle = new ForegroundColorSpan(color);
        spBuilder.setSpan(charaStyle, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringBuilder getResult() {
        if (spBuilder != null) {
            return spBuilder;
        }
        return null;
    }

    /**
     * 所有同个字都会变色
     *
     * @param mContext 上下文
     * @param text     全部文字
     * @param keyword  改变颜色的文字
     * @param color    颜色
     * @return SpannableString
     */
    public static SpannableString matcherSearchText(Context mContext, String text, String keyword, int color) {
        if (!TextUtils.isEmpty(text)) {
            int myColor = mContext.getResources().getColor(color);
            SpannableString ss = new SpannableString(text);
            if (!TextUtils.isEmpty(keyword)) {
                Pattern pattern = Pattern.compile(keyword);
                Matcher matcher = pattern.matcher(ss);
                while (matcher.find()) {
                    int start = matcher.start();
                    int end = matcher.end();
                    ss.setSpan(new ForegroundColorSpan(myColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            return ss;
        } else {
            return null;
        }
    }

    /**
     * 所有同个字都会变色(不区分大小写)
     *
     * @param mContext 上下文
     * @param text     全部文字
     * @param keyword  改变颜色的文字
     * @param color    颜色
     * @return SpannableString
     */
    public static SpannableString matcherSearchTitle(Context mContext, String text, String keyword, int color) {
        if (!TextUtils.isEmpty(text)) {
            SpannableString ss = new SpannableString(text);
            if (!TextUtils.isEmpty(keyword)) {
                String string = text.toLowerCase();
                String key = keyword.toLowerCase();
                for (int i = 0; i < key.length(); i++) {
                    String keyPosition = key.substring(i, i + 1);
                    Pattern pattern = Pattern.compile(keyPosition);
                    Matcher matcher = pattern.matcher(string);
                    while (matcher.find()) {
                        int start = matcher.start();
                        int end = matcher.end();
                        ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(color)), start, end,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
            return ss;
        } else {
            return null;
        }
    }

}
