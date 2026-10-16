Kính gửi thầy Phụng, em xin báo cáo tiến độ Đồ án Cloud Computing - Tuần 2 

**1. THÔNG TIN CHUNG**
- **Tên đề tài:** Hệ thống quản lý thư viện trực tuyến (Full-stack trên AWS)
- **Thành viên thực hiện:** Nguyễn Trần Anh Quốc (làm cá nhân)
- **Link GitHub Public:** https://github.com/anhquoc11/CLOUD-COMPUTING

**2. PHÂN CÔNG VÀ LIỆT KÊ CÔNG VIỆC CHI TIẾT**

Sinh viên **Nguyễn Trần Anh Quốc** đã hoàn thành các công việc sau trong Tuần 2 (Tổng thời gian: 9.0 giờ):

* **Công việc 1: Tích hợp AWS SDK for S3 và hoàn thiện lưu trữ ảnh bìa sách**
  - Cấu hình Bean `S3Client` sử dụng `DefaultCredentialsProvider` tận dụng quyền `LabInstanceProfile` của Lab.
  - Viết `S3Service` xử lý upload ảnh lên bucket S3 `capstone-library-images-nguyentrananhquoc-hcmute` và trả về URL ảnh.
  - Cập nhật Entity `Book` bổ sung thuộc tính `coverImageUrl`.
  - Minh chứng mã nguồn (đã đẩy lên GitHub):
    - Cấu hình S3: https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/java/com/nguyentrananhquoc/library_backend/config/S3Config.java
    - Service upload S3: https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/java/com/nguyentrananhquoc/library_backend/service/S3Service.java
    - Entity Book: https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/java/com/nguyentrananhquoc/library_backend/entity/Book.java

* **Công việc 2: Hoàn thiện toàn bộ API CRUD Sách, tích hợp Swagger UI và CORS**
  - Bổ sung các phương thức: Tìm chi tiết theo ID, Thêm mới, Cập nhật thông tin, Xóa sách và Upload ảnh bìa.
  - Tích hợp OpenAPI/Swagger UI tự động sinh tài liệu API trực quan (`/swagger-ui/index.html`).
  - Cấu hình CORS `@CrossOrigin(origins = "*")` phục vụ gọi API từ giao diện Web.
  - Minh chứng mã nguồn và tài liệu API:
    - Controller API: https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/java/com/nguyentrananhquoc/library_backend/controller/BookController.java
    - Service xử lý: https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/java/com/nguyentrananhquoc/library_backend/service/BookService.java
    - Minh chứng giao diện Swagger API: https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/docs/images/api-swagger-docs.png

* **Công việc 3: Xây dựng Giao diện người dùng (Frontend) và Triển khai lên AWS S3 Static Website Hosting**
  - Xây dựng giao diện web Responsive (HTML5, Modern CSS, Fetch API) hỗ trợ xem sách thẻ bài kèm ảnh bìa, tìm kiếm tức thì và form thêm/sửa sách.
  - Khởi tạo S3 Bucket `capstone-library-frontend-nguyentrananhquoc-hcmute`, bật Static Website Hosting và gắn tag quản lý (`Project: LibraryManagement`, `Owner: NguyenTranAnhQuoc`).
  - Thiết lập Bucket Policy mở quyền đọc công khai cho website.
  - Link Website Frontend trên S3: http://capstone-library-frontend-nguyentrananhquoc-hcmute.s3-website-us-east-1.amazonaws.com
  - Minh chứng hình ảnh Website hoạt động trên S3:
    https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/docs/images/s3-frontend-running.png

* **Công việc 4: Đóng gói và Triển khai Backend lên AWS Elastic Beanstalk (Java 21)**
  - Cấu hình cổng `server.port=5000` tương thích Nginx reverse-proxy của Beanstalk và đóng gói file JAR.
  - Khởi tạo môi trường Beanstalk chạy Java 21 Corretto với cấu hình Single Instance (tối ưu chi phí).
  - Gán quyền `LabRole` và `LabInstanceProfile` theo đúng chuẩn quy định của AWS Learner Lab.
  - Gắn tag tài nguyên `Project` và `Owner`.
  - Link Backend API: http://library-backend-app-env.eba-hetmedpc.us-east-1.elasticbeanstalk.com
  - Minh chứng trạng thái Beanstalk Health: Ok:
    https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/docs/images/aws-elastic-beanstalk-ok.png
  - Minh chứng Database RDS MySQL kết nối ổn định:
    https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/docs/images/aws-rds-available.png

**3. KHAI BÁO SỬ DỤNG AI HỖ TRỢ**
- **Công cụ:** Gemini / Antigravity
- **Mục đích:**
  - Hỗ trợ tìm hiểu cú pháp tích hợp AWS SDK for S3 v2 (`S3Client`, `PutObjectRequest`) cho Spring Boot và Java 21.
  - Đề xuất giải pháp khắc phục lỗi cổng khi chạy trên AWS Elastic Beanstalk (Nginx reverse-proxy chuyển tiếp lưu lượng vào cổng 5000 thay vì cổng mặc định 8080).
  - Hướng dẫn thiết lập phân quyền `LabRole` và `LabInstanceProfile` theo đúng chính sách bảo mật của AWS Learner Lab để tránh lỗi thiếu quyền IAM Role.
- **Phần em đã sửa / hoàn thiện (Minh chứng):**
  - Em đã hiểu cơ chế xác thực IAM trên AWS (sử dụng `DefaultCredentialsProvider` kết hợp `LabInstanceProfile` để kết nối S3 an toàn mà không cần lưu thông tin nhạy cảm trong mã nguồn) và áp dụng cấu hình vào dự án.
  - Hiểu rõ nguyên lý hoạt động của Nginx reverse-proxy trên Elastic Beanstalk để cấu hình `server.port=5000` chính xác trước khi đóng gói JAR.
  - Trực tiếp thao tác cấu hình và khởi tạo toàn bộ hạ tầng trên AWS Management Console (tạo S3 Bucket, thiết lập Bucket Policy mở truy cập web, tạo môi trường Beanstalk, cấu hình Security Group, gắn thẻ Tag `Project`/`Owner`), đóng gói ứng dụng và kiểm thử hoàn chỉnh luồng dữ liệu trên Cloud.
- **Minh chứng qua link file đã cấu hình:** 
  - https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/resources/application.properties
  - https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/java/com/nguyentrananhquoc/library_backend/config/S3Config.java