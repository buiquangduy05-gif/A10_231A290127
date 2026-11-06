package vn.edu.vhu.ltdd.a10gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter cho ViewPager2 — mỗi "trang" là một ảnh chiếm trọn màn hình.
 */
public class AnhPagerAdapter extends RecyclerView.Adapter<AnhPagerAdapter.PageViewHolder> {

    static class PageViewHolder extends RecyclerView.ViewHolder {
        final ImageView img;

        PageViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgLon);
        }
    }

    private final int[] anh;

    public AnhPagerAdapter(int[] anh) {
        this.anh = anh;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.page_anh, parent, false);
        return new PageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        holder.img.setImageResource(anh[position]);
    }

    @Override
    public int getItemCount() {
        return anh.length;
    }
}