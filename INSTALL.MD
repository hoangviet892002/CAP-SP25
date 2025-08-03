# CAP-SP25 - Hướng dẫn chạy dự án

## 1. Yêu cầu hệ thống

- Java 17 trở lên
- Maven 3.6+
- MySQL 8+

## 2. Tạo database MySQL

1. Đăng nhập vào MySQL:
   ```sh
   mysql -u root -p
   ```
2. Tạo database mới:
   ```sql
   CREATE DATABASE your_db_name CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
3. (Tùy chọn) Tạo user riêng và cấp quyền:
   ```sql
   CREATE USER 'your_user'@'%' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON your_db_name.* TO 'your_user'@'%';
   FLUSH PRIVILEGES;
   ```

## 3. Cấu hình biến môi trường

- Copy file `.example.env` thành `.env`:
  ```sh
  cp .example.env .env
  ```
- Mở file `.env` và cấu hình các biến liên quan đến MySQL:
  ```env
  MYSQL_HOST=localhost
  MYSQL_PORT=3306
  MYSQL_DB=your_db_name
  MYSQL_USER=your_user
  MYSQL_PASSWORD=your_password
  # Các biến khác như REDIS, JWT, ...
  ```

## 4. Build và chạy ứng dụng

- Build project:
  ```sh
  ./mvnw clean install
  ```
- Chạy ứng dụng:
  ```sh
  ./mvnw spring-boot:run
  ```
  Hoặc chạy trực tiếp từ IDE (IntelliJ, VSCode, ...)

## 5. Liquibase - Quản lý database tự động

- Khi ứng dụng khởi động, Liquibase sẽ tự động tạo/tối ưu database theo các file YAML trong `src/main/resources/db/changelog/`.
- Để thêm migration mới, chỉ cần thêm changeSet vào file `db.changelog-master.yaml`.
- Không cần chạy lệnh Liquibase thủ công.

## 6. API Documentation

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) (hoặc port bạn cấu hình)

## 7. Troubleshooting

- Nếu gặp lỗi kết nối database, kiểm tra lại biến môi trường và quyền truy cập MySQL.
- Nếu cần reset database, xóa các bảng và Liquibase sẽ tự tạo lại khi chạy lại app.

---

**Chúc bạn thành công!**
