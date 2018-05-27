package pers.goweii.basestateadapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pers.goweii.lib.OnClickListener;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TestTextAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void on10click(View view) {
        mAdapter.loadData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    mList.add("测试数据" + i);
                }
                mAdapter.setData(mList);
            }
        }, 1000);
    }

    public void on0click(View view) {
        mAdapter.loadData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mList.clear();
                mAdapter.setData(mList);
            }
        }, 1000);
    }

    public void onClearClick(View view) {
        mAdapter.emptyData();
    }

    public void onLoadingClick(View view) {
        mAdapter.loadData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestTextAdapter();
        mAdapter.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this, "你点击了" + position + "位置的" + "itemView", Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this, "你点击了" + position + "位置的" + "tv_text", Toast.LENGTH_SHORT).show();
            }
        }, R.id.tv_text);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<String> mList = new ArrayList<>();
}
