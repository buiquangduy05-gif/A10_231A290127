# Báo Cáo Thực Hành Lab A10: GridView, RecyclerView & ViewPager2

> **Trường Đại học Văn Hiến (VHU)**  
> **Khoa Công nghệ Thông tin**  
> **Môn học:** INT4211 – Lập trình trên các thiết bị di động  
> **Sinh viên thực hiện:** Bùi Quang Duy  
> **Mã số sinh viên (MSSV):** 231A290127  
> **Package name:** `vn.edu.vhu.ltdd.a10gallery`  

---

## 📱 1. Giới thiệu dự án

Dự án mô phỏng ứng dụng **Album Ảnh (A10 Gallery)** chuyên nghiệp trên nền tảng Android, áp dụng kiến trúc hiển thị danh sách / lưới hiện đại chuẩn của Google nhằm thay thế hoàn toàn các widget đời cũ (`ListView`, `GridView`, `Gallery`).

Ứng dụng gồm 2 màn hình chính:
1. **MainActivity (Màn hình lưới ảnh):** Hiển thị danh sách ảnh bằng `RecyclerView` với bo góc `MaterialCardView`, hỗ trợ đổi số cột lúc chạy.
2. **ViewerActivity (Màn hình xem ảnh lớn):** Sử dụng `ViewPager2` để lướt xem ảnh toàn màn hình với thao tác vuốt ngang mượt mà, đồng thời cập nhật tức thời bộ đếm số trang (`1 / 8`, `5 / 8`).

---

## 🚀 2. Chi tiết các bài nâng cao đã thực hiện

Ứng dụng đã hoàn thành đầy đủ **2 bài tập nâng cao (NC2 & NC4)** theo hướng dẫn thực hành:

### 🌟 Bài nâng cao NC2: Lưới ảnh so le (`StaggeredGridLayoutManager` - Phong cách Pinterest)
* **Mục tiêu:** Hiển thị danh sách ảnh có kích thước và chiều cao tự nhiên theo tỉ lệ gốc thay vì bị ép cắt cứng theo một kích thước đồng nhất.
* **Cách thực hiện:**
  * **Layout (`item_anh.xml`):** Bỏ thuộc tính cố định `layout_height="110dp"`, chuyển sang `layout_height="wrap_content"` kết hợp `android:adjustViewBounds="true"` và `scaleType="fitCenter"` giúp ảnh co giãn đúng tỉ lệ thật.
  * **Code (`MainActivity.java`):** Sử dụng `StaggeredGridLayoutManager` cho `RecyclerView`:
    ```java
    rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    ```
  * **Đổi số cột linh hoạt:** Khi bấm nút *"Đổi số cột"*, hàm `doiSoCot()` điều chỉnh `setSpanCount(soCot)` (2 ↔ 3 ↔ 4 cột) trên `StaggeredGridLayoutManager` và tự động sắp xếp lại các cột ảnh lệch tầng rất tự nhiên.

---

### 🌟 Bài nâng cao NC4: Thêm tính năng xóa ảnh với hiệu ứng trượt (`notifyItemRemoved`)
* **Mục tiêu:** Cung cấp tính năng xóa phần tử trong danh sách và kích hoạt hiệu ứng animation trượt mượt mà của `RecyclerView`, chứng minh sự vượt trội so với `notifyDataSetChanged()`.
* **Cách thực hiện:**
  * **Giao diện nút xóa (`item_anh.xml`):** Bổ sung nút `ImageButton` icon thùng rác (`@android:drawable/ic_menu_delete`) nổi ở góc trên bên phải mỗi ô ảnh với nền bo tròn mờ.
  * **Cấu trúc dữ liệu động:** Chuyển đổi dữ liệu từ mảng tĩnh `int[]` sang `ArrayList<Integer>` động để hỗ trợ thao tác xóa item.
  * **Xử lý sự kiện & Dialog xác nhận:** Khi người dùng bấm nút thùng rác, một `AlertDialog` sẽ hiện lên yêu cầu xác nhận xóa ảnh để tránh thao tác nhầm.
  * **Kích hoạt Animation chuẩn trong Adapter (`AnhAdapter.java`):**
    ```java
    public void xoaPhanTu(int viTri) {
        if (viTri >= 0 && viTri < dslAnh.size()) {
            dslAnh.remove(viTri);
            chuThich.remove(viTri);
            // Kích hoạt hiệu ứng mờ dần / thu nhỏ và trượt dồn vị trí
            notifyItemRemoved(viTri);
            notifyItemRangeChanged(viTri, dslAnh.size() - viTri);
        }
    }
    ```
  * **Hiệu ứng thu được:** Khi xóa ảnh, ô đó biến mất mượt mà (fade-out), các ô ảnh liền kề tự động lướt lên lấp chỗ trống mà không làm màn hình bị nhấp nháy hay giật lag.

---

## 🛠️ 3. Kiến trúc kỹ thuật nổi bật

1. **Tách biệt hiển thị và điều hướng:**
   * `AnhAdapter` chỉ lo việc hiển thị giao diện và ánh xạ dữ liệu.
   * Hành vi điều hướng sang `ViewerActivity` hoặc mở dialog xóa được ủy quyền cho `MainActivity` thông qua các Interface callback (`OnAnhClickListener`, `OnAnhXoaListener`).
2. **Tránh lỗi vị trí với `getBindingAdapterPosition()`:**
   * Sử dụng `holder.getBindingAdapterPosition()` thay cho biến `position` tĩnh, luôn kiểm tra điều kiện `p != RecyclerView.NO_POSITION` để ngăn ngừa lỗi văng ứng dụng (crash) khi tương tác với ô vừa bị xóa.
3. **ViewPager2 kế thừa từ RecyclerView:**
   * `AnhPagerAdapter` kế thừa trực tiếp từ `RecyclerView.Adapter<PageViewHolder>`, tận dụng cơ chế quản lý bộ nhớ ưu việt và lưu/khôi phục vị trí trang tự động khi xoay màn hình nhờ `android:id="@+id/pager"`.

---

## 📋 4. Hướng dẫn kiểm thử và trải nghiệm

| # | Thao tác | Kết quả quan sát được |
|---|----------|------------------------|
| **1** | Mở ứng dụng | Danh sách 8 ảnh hiển thị thành lưới so le 2 cột đều đặn, không bị méo ảnh. |
| **2** | Cuộn lên / xuống | Cuộn cực kỳ mượt mà; `onCreateViewHolder` chỉ chạy tạo số View vừa đủ màn hình. |
| **3** | Bấm nút **"Đổi số cột"** | Lưới tự động chuyển đổi giữa 2, 3 và 4 cột lệch tầng; Adapter giữ nguyên 100%. |
| **4** | Chạm vào một ảnh | Mở `ViewerActivity` hiển thị đúng ảnh lớn và cập nhật chỉ số (ví dụ: `5 / 8`). |
| **5** | Vuốt sang trái / phải | Lướt chuyển trang mượt mà; bộ đếm trang nhảy tương ứng theo thời gian thực. |
| **6** | Xoay ngang điện thoại | Màn hình xem ảnh tự căn giữa vừa vặn khung hình và giữ đúng vị trí ảnh đang xem. |
| **7** | Bấm nút **Xóa ảnh (icon thùng rác)** | Dialog hỏi xác nhận hiện ra; xác nhận xóa sẽ thấy hiệu ứng trượt dồn vị trí (`notifyItemRemoved`). |

---

## 📂 5. Cấu trúc thư mục mã nguồn

```
A10_231A290127/
├── app/
│   ├── src/main/java/vn/edu/vhu/ltdd/a10gallery/
│   │   ├── MainActivity.java         # Màn hình chính lưới ảnh (NC2, NC4)
│   │   ├── ViewerActivity.java       # Màn hình xem ảnh toàn màn hình ViewPager2
│   │   ├── AnhAdapter.java           # Adapter cho RecyclerView + hàm xoaPhanTu()
│   │   └── AnhPagerAdapter.java     # Adapter cho ViewPager2
│   └── src/main/res/
│       ├── layout/
│       │   ├── activity_main.xml     # Layout chính chứa RecyclerView
│       │   ├── activity_viewer.xml   # Layout xem ảnh ViewPager2
│       │   ├── item_anh.xml          # Layout một ô ảnh (MaterialCardView + nút xóa)
│       │   └── page_anh.xml          # Layout một trang ảnh phóng to
│       └── values/strings.xml        # Chuỗi tài nguyên đa ngôn ngữ
├── BuiQuangDuy_231A290127_LABA10.docx # File báo cáo Word hoàn chỉnh
└── README.md                         # Tài liệu hướng dẫn & mô tả bài làm
```
