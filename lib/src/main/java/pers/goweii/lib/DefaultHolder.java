package pers.goweii.lib;

import android.view.View;

/**
 * @author Cuizhen
 * @date 2018/5/26-下午2:07
 */
public class DefaultHolder<E> extends BaseHolder<E> {

    public DefaultHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void bindData(E data) {
    }
}
