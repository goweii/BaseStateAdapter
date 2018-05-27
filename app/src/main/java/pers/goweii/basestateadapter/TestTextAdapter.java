package pers.goweii.basestateadapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pers.goweii.lib.BaseHolder;
import pers.goweii.lib.BaseStateAdapter;

/**
 * @author CuiZhen
 * @date 2018/5/27
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class TestTextAdapter extends BaseStateAdapter<String, BaseHolder<String>>{

    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    @Override
    protected int getHolderType(int position) {
        if (position % 3 == 0){
            return ONE;
        } else if (position % 3 == 1){
            return TWO;
        } else {
            return THREE;
        }
    }

    @Override
    protected BaseHolder<String> getViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            default:
                return new TextHolder1(inflater.inflate(R.layout.item_text_1, parent, false));
            case ONE:
                return new TextHolder1(inflater.inflate(R.layout.item_text_1, parent, false));
            case TWO:
                return new TextHolder2(inflater.inflate(R.layout.item_text_2, parent, false));
            case THREE:
                return new TextHolder3(inflater.inflate(R.layout.item_text_3, parent, false));
        }
    }

    class TextHolder1 extends BaseHolder<String> {

        TextHolder1(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(String data) {
            TextView textView = getView(R.id.tv_text);
            textView.setText(data + "TextHolder1");
        }
    }

    class TextHolder2 extends BaseHolder<String> {

        TextHolder2(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(String data) {
            TextView textView = getView(R.id.tv_text);
            textView.setText(data + "TextHolder2");
        }
    }

    class TextHolder3 extends BaseHolder<String> {

        TextHolder3(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(String data) {
            TextView textView = getView(R.id.tv_text);
            textView.setText(data + "TextHolder3");
        }
    }
}
