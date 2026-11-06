package vn.edu.vhu.ltdd.a10gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AnhAdapter extends RecyclerView.Adapter<AnhAdapter.AnhViewHolder> {

    public interface OnAnhClickListener {
        void onAnhClick(int viTri);
    }

    /** NC4: Interface callback khi bấm nút xóa ảnh */
    public interface OnAnhXoaListener {
        void onAnhXoa(int viTri);
    }

    static class AnhViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgAnh;
        final TextView tvChuThich;
        final ImageButton btnXoaAnh;

        AnhViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnh = itemView.findViewById(R.id.imgAnh);
            tvChuThich = itemView.findViewById(R.id.tvChuThich);
            btnXoaAnh = itemView.findViewById(R.id.btnXoaAnh);
        }
    }

    private final List<Integer> dslAnh;
    private final List<String> chuThich;
    private final OnAnhClickListener listener;
    private final OnAnhXoaListener xoaListener;

    public AnhAdapter(List<Integer> dslAnh, List<String> chuThich, OnAnhClickListener listener, OnAnhXoaListener xoaListener) {
        this.dslAnh = dslAnh;
        this.chuThich = chuThich;
        this.listener = listener;
        this.xoaListener = xoaListener;
    }

    @NonNull
    @Override
    public AnhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_anh, parent, false);
        return new AnhViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnhViewHolder holder, int position) {
        holder.imgAnh.setImageResource(dslAnh.get(position));
        holder.tvChuThich.setText(chuThich.get(position));

        holder.itemView.setOnClickListener(v -> {
            int p = holder.getBindingAdapterPosition();
            if (p != RecyclerView.NO_POSITION && listener != null) {
                listener.onAnhClick(p);
            }
        });

        // NC4: Bắt sự kiện bấm nút xóa ảnh
        if (holder.btnXoaAnh != null) {
            holder.btnXoaAnh.setOnClickListener(v -> {
                int p = holder.getBindingAdapterPosition();
                if (p != RecyclerView.NO_POSITION && xoaListener != null) {
                    xoaListener.onAnhXoa(p);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dslAnh.size();
    }

    /**
     * NC4: Xóa phần tử khỏi danh sách và kích hoạt hiệu ứng trượt của RecyclerView
     */
    public void xoaPhanTu(int viTri) {
        if (viTri >= 0 && viTri < dslAnh.size()) {
            dslAnh.remove(viTri);
            chuThich.remove(viTri);
            // Kích hoạt animation xóa và trượt dồn các ô phía sau
            notifyItemRemoved(viTri);
            notifyItemRangeChanged(viTri, dslAnh.size() - viTri);
        }
    }
}
