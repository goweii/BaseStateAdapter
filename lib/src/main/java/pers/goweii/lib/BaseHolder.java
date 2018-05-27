package pers.goweii.lib;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * @author Cuizhen
 * @date 2018/5/26-下午2:04
 */
public abstract class BaseHolder<E> extends RecyclerView.ViewHolder {

    private SparseArray<View> views = null;

    public BaseHolder(final View itemView) {
        super(itemView);
    }

    public final <V extends View> V getView(@IdRes int viewId) {
        if (views == null) {
            views = new SparseArray<>();
        }
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            if (view == null) {
                views.put(viewId, itemView);
            } else {
                views.put(viewId, view);
            }
        }
        return (V) view;
    }

    /**
     * 绑定数据
     *
     * @param data E
     */
    protected abstract void bindData(E data);

    final void setOnItemClickListener(final OnClickListener onItemClickListener) {
        if (onItemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(itemView, getAdapterPosition());
                }
            });
        }
    }

    final void setOnViewClickListener(SparseArray<OnClickListener> onViewClickListeners) {
        if (onViewClickListeners != null) {
            for (int i = 0; i < onViewClickListeners.size(); i++) {
                int viewId = onViewClickListeners.keyAt(i);
                final OnClickListener onClickListener = onViewClickListeners.valueAt(i);
                final View view = getView(viewId);
                if (view != null) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickListener.onClick(view, getAdapterPosition());
                        }
                    });
                }
            }
        }
    }
}