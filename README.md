# 🗺️ Trello Travel Check-in App

Ứng dụng này cho phép bạn đánh dấu và ghi lại hành trình du lịch của mình bằng cách tận dụng **Trello** như một hệ thống quản lý dữ liệu.

## 🚀 Mục tiêu dự án

Trello Travel Check-in App giúp bạn:

- Ghi lại các địa điểm đã đến du lịch
- Check-in tại các điểm tham quan cụ thể trong mỗi địa điểm
- Lưu giữ hình ảnh hoặc thông tin mô tả kỷ niệm chuyến đi

## 🔗 Tích hợp với Trello

Ứng dụng đã được tích hợp với Trello thông qua API, với cách ánh xạ dữ liệu như sau:

| Trello Entity | Ý nghĩa trong ứng dụng                                 |
|---------------|--------------------------------------------------------|
| **Board**     | Một địa điểm du lịch (ví dụ: Đà Lạt)  TRIP             |
| **List**      | Một điểm check-in tại địa điểm đó     Place            |
| **Card**      | Một bức ảnh, mô tả, hoặc kỷ niệm tại nơi đó.  Memories |

Ví dụ:
- **Board:** Đà Lạt
    - **List:** Hồ Xuân Hương
        - **Card:** Ảnh chụp hoàng hôn tại hồ
    - **List:** Đồi chè Cầu Đất
        - **Card:** Ảnh đồi chè sáng sớm

## ✅ Tính năng hiện tại

- [x] Kết nối với Trello qua API
- [x] Đồng bộ toàn bộ danh sách board
- [ ] Hiển thị cấu trúc địa điểm - điểm check-in - ảnh
- [ ] Thêm tính năng tạo mới điểm check-in từ app
- [ ] Hỗ trợ offline cache


