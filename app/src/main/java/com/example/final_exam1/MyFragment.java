package com.example.final_exam1;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class MyFragment extends Fragment {
    protected RecyclerView.Adapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
        }
    };

    /**
     * 해당 함수는 프래그먼트에 있는 RecyclerView의 UI을 갱신하기 위한 함수입니다.
     */
    public void updateUI() {
        Message message = handler.obtainMessage();
        handler.sendMessage(message);
    }
}
