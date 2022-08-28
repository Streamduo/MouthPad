package com.mouth.pad.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 *

 * @ClassName:      SpaceItemDecoration
 * @Description:    recycleview增加间隔
 * @Author:         FuDuo
 * @CreateDate:     2020/12/3 14:39
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/12/3 14:39
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int size;
    private int mOrientation;

    public SpaceItemDecoration(int mOrientation, int size) {
        this.mOrientation = mOrientation;
        this.size = size;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        if (position > parent.getAdapter().getItemCount() - 1) {
            return;
        }
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, size);
        } else {
            outRect.set(0, 0, size, 0);
        }
    }

}
