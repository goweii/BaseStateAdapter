package pers.goweii.lib;

import android.view.View;

/**
 * @author Cuizhen
 * @date 2018/5/26-下午5:26
 */
public interface OnClickListener {
    /**
     * 点击事件回调
     *
     * @param view     点击的view
     * @param position 所在位置
     */
    void onClick(View view, int position);
}
