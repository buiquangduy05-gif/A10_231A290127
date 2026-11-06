package vn.edu.vhu.ltdd.a10gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "A10_231A290127";
    public static final String EXTRA_VI_TRI = "extra_vi_tri";

    // NC4: Sử dụng ArrayList động thay cho mảng tĩnh int[] để hỗ trợ xóa phần tử
    public static final ArrayList<Integer> ANH = new ArrayList<>();

    static {
        ANH.add(R.drawable.anh1);
        ANH.add(R.drawable.anh2);
        ANH.add(R.drawable.anh3);
        ANH.add(R.drawable.anh4);
        ANH.add(R.drawable.anh5);
        ANH.add(R.drawable.anh6);
        ANH.add(R.drawable.anh7);
        ANH.add(R.drawable.anh8);
    }

    private final List<String> chuThich = new ArrayList<>();
    private AnhAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        khoiTaoChuThich();

        RecyclerView rv = findViewById(R.id.rvAnh);
        // NC2: Lưới so le StaggeredGridLayoutManager
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rv.setHasFixedSize(true);

        adapter = new AnhAdapter(
                ANH,
                chuThich,
                viTri -> {
                    Log.d(TAG, "Mở ảnh lớn tại vị trí " + viTri);
                    Intent i = new Intent(this, ViewerActivity.class);
                    i.putExtra(EXTRA_VI_TRI, viTri);
                    startActivity(i);
                },
                viTri -> {
                    // NC4: Hiển thị dialog xác nhận xóa ảnh
                    new AlertDialog.Builder(this)
                            .setTitle("Xác nhận xóa")
                            .setMessage("Bạn có chắc muốn xóa ảnh tại vị trí " + (viTri + 1) + " không?")
                            .setPositiveButton("Xóa", (dialog, which) -> {
                                adapter.xoaPhanTu(viTri);
                                Toast.makeText(this, "Đã xóa ảnh thành công!", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Hủy", null)
                            .show();
                }
        );
        rv.setAdapter(adapter);

        findViewById(R.id.btnDoiCot).setOnClickListener(v -> doiSoCot(rv));
    }

    private void khoiTaoChuThich() {
        chuThich.clear();
        for (int i = 0; i < ANH.size(); i++) {
            chuThich.add(getString(R.string.anh_so, i + 1));
        }
    }

    private void doiSoCot(RecyclerView rv) {
        if (rv.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager lm = (StaggeredGridLayoutManager) rv.getLayoutManager();
            int soCot = lm.getSpanCount() + 1;
            if (soCot > 4) soCot = 2;
            lm.setSpanCount(soCot);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, getString(R.string.so_cot, soCot), Toast.LENGTH_SHORT).show();
        }
    }
}
