Kính gửi thầy Phụng, em xin báo cáo tiến độ Đồ án Cloud Computing - Tuần 1.

**1. THÔNG TIN CHUNG**
- **Tên đề tài:** Hệ thống quản lý thư viện trực tuyến (Full-stack trên AWS)
- **Thành viên thực hiện:** Nguyễn Trần Anh Quốc (làm cá nhân)
- **Link GitHub Public:** https://github.com/anhquoc11/CLOUD-COMPUTING

**2. PHÂN CÔNG VÀ LIỆT KÊ CÔNG VIỆC CHI TIẾT**

Sinh viên **Nguyễn Trần Anh Quốc** đã hoàn thành các công việc sau trong Tuần 1:

* **Công việc 1: Khởi tạo và cấu hình hạ tầng AWS (RDS, S3, Tagging)**
  - Minh chứng hình ảnh RDS, S3 và sơ đồ kiến trúc (đã đẩy lên GitHub): 
  - Thư mục chứa toàn bộ ảnh minh chứng: https://github.com/anhquoc11/CLOUD-COMPUTING/tree/main/library-backend/docs 
  - Sơ đồ kiến trúc hệ thống:
   https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/docs/images/so-do-kien-truc.png
  - Minh chứng Database RDS: https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/docs/images/aws-rds-stopped.png
  - Minh chứng Storage S3: https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/docs/images/aws-s3-buckets.png
  - Test API bằng Thunder Client: https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/docs/images/api-test-thunder-client.png

* **Công việc 2: Xây dựng Base Project Spring Boot và kết nối Database**
  - Cấu hình file properties để kết nối với MySQL trên AWS RDS:
  - https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/resources/application.properties
* **Công việc 3: Viết API cho tính năng Sách và test bằng Thunder Client**
  - Khởi tạo Entity, Repository và Controller:
  - https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/java/com/nguyentrananhquoc/library_backend/controller/BookController.java
  - https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/java/com/nguyentrananhquoc/library_backend/entity/Book.java

**3. KHAI BÁO SỬ DỤNG AI HỖ TRỢ**
- **Công cụ:** Gemini 1.5 Pro
- **Mục đích:** Hỗ trợ tìm hiểu lỗi kết nối MySQL 8.0 trên môi trường Cloud (`Public Key Retrieval` và `Unknown database`) trong lúc code ở máy tính cá nhân.
- **Nội dung AI tạo ra:** Đề xuất thêm tham số `?allowPublicKeyRetrieval=true&useSSL=false&createDatabaseIfNotExist=true` vào đường dẫn kết nối.
- **Phần em đã sửa / hoàn thiện (Minh chứng):** Em đã hiểu cơ chế bảo mật và áp dụng vào cấu hình. Vì em đã nhờ AI hỗ trợ sửa lỗi ngay trên máy cá nhân trước khi push code lên GitHub lần đầu, nên mã nguồn trên GitHub là phiên bản đã hoàn thiện.
- **Minh chứng qua link file đã cấu hình:** 
- https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/resources/application.properties


