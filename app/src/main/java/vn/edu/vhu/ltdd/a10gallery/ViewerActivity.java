package vn.edu.vhu.ltdd.a10gallery;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

public class ViewerActivity extends AppCompatActivity {

    private ViewPager2 pager;
    private TextView tvChiSo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        pager = findViewById(R.id.pager);
        tvChiSo = findViewById(R.id.tvChiSo);

        // Gắn adapter chứa danh sách ảnh từ MainActivity
        pager.setAdapter(new AnhPagerAdapter(MainActivity.ANH));

        // Nhận chỉ số vị trí ảnh được truyền qua Intent
        int viTri = getIntent().getIntExtra(MainActivity.EXTRA_VI_TRI, 0);
        // Hiển thị ngay ảnh được bấm, false = không chạy animation cuộn lướt từ đầu
        pager.setCurrentItem(viTri, false);
        capNhatChiSo(viTri);

        // Đăng ký sự kiện lắng nghe khi người dùng vuốt chuyển trang ảnh
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                capNhatChiSo(position);
            }
        });
    }

    private void capNhatChiSo(int viTri) {
        // Cập nhật text hiển thị dạng "1 / 8", "2 / 8",...
        tvChiSo.setText(getString(R.string.chi_so, viTri + 1, MainActivity.ANH.size()));
    }
}
