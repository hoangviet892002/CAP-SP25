# Hướng dẫn sử dụng folder Postman

- Mỗi module sẽ có một thư mục riêng trong `postman/` (ví dụ: `auth/`, `user/`, `product/` ...).
- Trong mỗi thư mục module sẽ chứa các collection Postman liên quan (file `.postman_collection.json`).
- Đặt tên file theo chuẩn: `<module>-module.postman_collection.json`.
- Khi thêm module mới, tạo folder và collection tương tự.
- Có thể import từng collection vào Postman để test API theo từng module.

**Ví dụ cấu trúc:**

```
postman/
  ├── auth/
  │     └── auth-module.postman_collection.json
  ├── user/
  │     └── user-module.postman_collection.json
  └── README.md
```
