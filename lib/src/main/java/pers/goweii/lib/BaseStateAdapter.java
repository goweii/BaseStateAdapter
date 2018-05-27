package pers.goweii.lib;

import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cuizhen
 * @date 2018/5/26-上午10:41
 */
public abstract class BaseStateAdapter<E, H extends BaseHolder<E>> extends RecyclerView.Adapter<BaseHolder<E>> {

    protected List<E> dataList = null;

    private OnClickListener onItemClickListener = null;
    private SparseArray<OnClickListener> onViewClickListeners = null;

    /**
     * 获取数据集合
     *
     * @return List<E>
     */
    public final List<E> getData() {
        return dataList;
    }

    /**
     * 获取对应位置的数据
     *
     * @param position 位置
     * @return E
     */
    public final E getData(int position) {
        if (dataList == null) {
            return null;
        }
        if (dataList.size() <= position) {
            return null;
        }
        return dataList.get(position);
    }

    /**
     * 设置新数据
     *
     * @param data List<E>
     */
    public final void setData(List<E> data) {
        this.dataList = data;
        if (this.dataList == null) {
            this.dataList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    /**
     * 添加一个数据
     *
     * @param data E
     */
    public final void addData(E data) {
        if (data == null) {
            return;
        }
        if (this.dataList == null) {
            this.dataList = new ArrayList<>();
        }
        int index = this.dataList.size();
        this.dataList.add(data);
        notifyItemInserted(index);
    }

    /**
     * 添加一个数据集合
     *
     * @param data List<E>
     */
    public final void addData(List<E> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        if (this.dataList == null) {
            data = new ArrayList<>();
        }
        int index = this.dataList.size();
        this.dataList.addAll(data);
        notifyItemRangeInserted(index, data.size());
    }

    /**
     * 移除对应位置的数据
     *
     * @param position 位置
     */
    public final void removeData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 清空数据并显示为空布局
     */
    public final void emptyData() {
        if (this.dataList == null) {
            this.dataList = new ArrayList<>();
        } else {
            this.dataList.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 置空数据并显示为加载布局
     */
    public final void loadData() {
        this.dataList = null;
        notifyDataSetChanged();
    }

    /**
     * 对itemView添加点击事件
     *
     * @param onItemClickListener OnClickListener
     */
    public final void setOnItemClickListener(@NonNull OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 给一个view添加点击事件
     *
     * @param viewId              添加点击事件的view的id
     * @param onViewClickListener OnClickListener
     */
    public final void addOnViewClickListener(@IdRes int viewId, @NonNull OnClickListener onViewClickListener) {
        if (onViewClickListeners == null) {
            onViewClickListeners = new SparseArray<>();
        }
        onViewClickListeners.put(viewId, onViewClickListener);
    }

    /**
     * 对多个view添加点击事件
     *
     * @param onClickListener OnClickListener
     * @param viewIds         如不添加这个参数则对itemView添加点击事件，效果同{@link #setOnItemClickListener(OnClickListener)}
     */
    public final void addOnClickListener(@NonNull OnClickListener onClickListener, @IdRes int... viewIds) {
        if (viewIds == null || viewIds.length == 0) {
            this.onItemClickListener = onClickListener;
            return;
        }
        if (onViewClickListeners == null) {
            onViewClickListeners = new SparseArray<>();
        }
        for (int viewId : viewIds) {
            onViewClickListeners.put(viewId, onClickListener);
        }
    }

    /**
     * 你可以重写这个方法实现不同位置显示不同的布局
     *
     * @param position 位置
     * @return 返回一个类型常量标识，当然你可以去继承{@link Type}接口定义这些常量以方便管理
     */
    @IntRange(from = 0)
    protected int getHolderType(int position) {
        return Type.NORMAL;
    }

    /**
     * 依据holder类型的常量去创建一个ViewHolder
     *
     * @param parent     ViewGroup
     * @param holderType 类型常量标识，你需要重写这个方法返回{@link #getHolderType(int)}
     * @return H 你应该去继承{@link BaseHolder<E>}定义你的ViewHolder
     */
    protected abstract H getViewHolder(@NonNull ViewGroup parent, int holderType);

    /**
     * 定义加载中的布局文件
     *
     * @return 布局文件id
     */
    @LayoutRes
    protected int getLoadLayoutId() {
        return R.layout.loading;
    }

    /**
     * 定义空布局文件
     *
     * @return 空布局id
     */
    @LayoutRes
    protected int getEmptyLayoutId() {
        return R.layout.empty;
    }

    @NonNull
    @Override
    public final BaseHolder<E> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case State.LOADING:
                return new DefaultHolder<>(inflater.inflate(getLoadLayoutId(), parent, false));
            case State.EMPTY:
                return new DefaultHolder<>(inflater.inflate(getEmptyLayoutId(), parent, false));
            default:
                H holder = getViewHolder(parent, viewType);
                holder.setOnItemClickListener(onItemClickListener);
                holder.setOnViewClickListener(onViewClickListeners);
                return holder;
        }
    }

    @Override
    public final void onBindViewHolder(@NonNull BaseHolder<E> holder, int position) {
        switch (holder.getItemViewType()) {
            case State.LOADING:
            case State.EMPTY:
                break;
            default:
                holder.bindData(dataList.get(holder.getAdapterPosition()));
                break;
        }
    }

    @Override
    public final int getItemViewType(int position) {
        if (dataList == null) {
            return State.LOADING;
        } else if (dataList.size() == 0) {
            return State.EMPTY;
        } else {
            return getHolderType(position);
        }
    }

    @Override
    public final int getItemCount() {
        if (dataList == null) {
            return 1;
        } else if (dataList.size() == 0) {
            return 1;
        } else {
            return dataList.size();
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager instanceof GridLayoutManager) {
                final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                final int spanCount = gridLayoutManager.getSpanCount();
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (dataList == null) {
                            return spanCount;
                        } else if (dataList.size() == 0) {
                            return spanCount;
                        } else {
                            return 1;
                        }
                    }
                });
            }
        }
    }
}
