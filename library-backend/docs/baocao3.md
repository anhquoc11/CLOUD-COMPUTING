Kính gửi thầy Phụng, em xin báo cáo tiến độ Đồ án Cloud Computing - Tuần 3 

**1. THÔNG TIN CHUNG**
- **Tên đề tài:** Hệ thống quản lý thư viện trực tuyến (Full-stack trên AWS)
- **Thành viên thực hiện:** Nguyễn Trần Anh Quốc (làm cá nhân)
- **Link GitHub Public:** https://github.com/anhquoc11/CLOUD-COMPUTING
- **Bối cảnh tóm tắt chuỗi công việc (Tuần 1 & Tuần 2):**
  - *Tuần 1:* Khởi tạo hạ tầng mạng cơ bản, tạo CSDL AWS RDS MySQL, tạo Bucket S3 lưu ảnh và khởi tạo Base Spring Boot.
  - *Tuần 2:* Hoàn thiện Full-stack Web (Frontend lưu trữ trên S3 Static Website Hosting, Backend triển khai trên AWS Elastic Beanstalk, tích hợp S3 upload ảnh bìa và Swagger UI).
  - *Tuần 3:* Trọng tâm là **Bảo mật (AWS Secrets Manager)**, **Giám sát (AWS CloudWatch Alarms & Dashboard)** và **Tự động hóa hạ tầng (AWS CloudFormation IaC)**.
- **Danh sách Commit Tuần 3:**
  - Commit 1 (Mã nguồn & Cấu hình Cloud): https://github.com/anhquoc11/CLOUD-COMPUTING/commit/3caab4fb60c4a4a4d791ea04a27fb2e115d6f9cd
    *(Nội dung: Cấu hình AWS Secrets Manager cho Database và template CloudFormation IaC)*
  - Commit 2 (Hình ảnh minh chứng Cloud): https://github.com/anhquoc11/CLOUD-COMPUTING/commit/6fcf899402b6cfaf1885323ea3dc76d90f24f012
    *(Nội dung: Bổ sung 5 hình ảnh minh chứng Tuần 3 trên AWS Console)*

**2. PHÂN CÔNG VÀ LIỆT KÊ CÔNG VIỆC CHI TIẾT TUẦN 3 (TỔNG THỜI GIAN: 9.0 GIỜ)**
- Thư mục chứa toàn bộ ảnh minh chứng Tuần 3: https://github.com/anhquoc11/CLOUD-COMPUTING/tree/main/library-backend/docs/images

* **Công việc 1: Nâng cao Bảo mật Database với AWS Secrets Manager (2.5 giờ)**
  - Khởi tạo Secret `capstone/library/rds` trên AWS Secrets Manager lưu trữ an toàn thông tin đăng nhập CSDL RDS MySQL kèm thẻ Tag quản lý (`Project: LibraryManagement`, `Owner: NguyenTranAnhQuoc`).
  - Cập nhật `application.properties`: Loại bỏ hoàn toàn username và mật khẩu dạng plain-text, chuyển sang cấu hình `aws.secretsmanager.secret-name`.
  - Xây dựng class `DatabaseConfig.java` sử dụng AWS SDK v2 (`SecretsManagerClient`) kết hợp IAM `DefaultCredentialsProvider` để tự động lấy secret và nạp vào DataSource khi khởi động.
  - Minh chứng Secret trên AWS Console:
    https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/docs/images/aws-secrets-manager.png
  - Minh chứng Backend đọc Secret và khởi động thành công:
    https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/docs/images/backend-secrets-manager-started.png
  - Minh chứng mã nguồn:
    - https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/resources/application.properties
    - https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/java/com/nguyentrananhquoc/library_backend/config/DatabaseConfig.java

* **Công việc 2: Thiết lập Giám sát và Cảnh báo với AWS CloudWatch (2.5 giờ)**
  - Tạo CloudWatch Alarm 1 (`Backend-EC2-High-CPU-Utilization`): Cảnh báo khi mức sử dụng CPU của máy chủ Backend $\ge 80\%$ qua SNS email.
  - Tạo CloudWatch Alarm 2 (`RDS-High-Database-Connections`): Cảnh báo khi số lượng kết nối Database RDS $\ge 20$ connections.
  - Xây dựng CloudWatch Dashboard (`Library-Management-Dashboard`) tổng hợp trực quan 2 biểu đồ giám sát sức khỏe hệ thống: CPU máy ảo Backend và số lượng kết nối Database RDS.
  - Minh chứng danh sách CloudWatch Alarms:
    https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/docs/images/aws-cloudwatch-alarms.png
  - Minh chứng CloudWatch Dashboard:
    https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/docs/images/aws-cloudwatch-dashboard.png

* **Công việc 3: Tự động hóa Hạ tầng bằng AWS CloudFormation - IaC (2.5 giờ)**
  - Viết file template mẫu `cloudformation/template.yaml` (chuẩn định dạng `2010-09-09`) định nghĩa toàn bộ hạ tầng: S3 Bucket lưu ảnh sách, S3 Bucket Static Hosting Frontend, Security Group mở cổng 3306 cho RDS, và CloudWatch Alarm giám sát CPU.
  - Triển khai thành công Stack `library-infrastructure-stack` trên AWS CloudFormation đạt trạng thái `CREATE_COMPLETE`.
  - Minh chứng file mã nguồn IaC:
    https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/cloudformation/template.yaml
  - Minh chứng Stack CloudFormation tạo thành công:
    https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/docs/images/aws-cloudformation-stack.png

* **Công việc 4: Tối ưu mã nguồn Spring Boot & Kiểm thử tích hợp (1.5 giờ)**
  - Tối ưu hóa các Controller và Service (`BookController`, `BookService`, `S3Service`) chuyển sang cơ chế Constructor Injection, làm sạch toàn bộ cảnh báo mã nguồn.
  - Đóng gói file `.jar` hoàn chỉnh hỗ trợ Secrets Manager.

**3. KHAI BÁO SỬ DỤNG AI HỖ TRỢ (TUẦN 3)**
- **Công cụ:** Gemini / Antigravity
- **Mục đích:**
  - Hỗ trợ cú pháp AWS SDK for Secrets Manager v2 (`SecretsManagerClient`, `GetSecretValueRequest`) kết hợp Jackson ObjectMapper để parse chuỗi credentials JSON.
  - Hướng dẫn cấu trúc cú pháp YAML cho AWS CloudFormation template (các thuộc tính của S3, Security Group, CloudWatch Alarm) và sửa lỗi định dạng phiên bản `AWSTemplateFormatVersion`.
- **Phần em đã sửa / hoàn thiện (Minh chứng):**
  - Hiểu sâu nguyên lý bảo mật của Secrets Manager (tách biệt thông tin nhạy cảm khỏi mã nguồn, kiểm soát truy cập qua IAM Role).
  - Tự tay tạo Secret trên Console, gắn thẻ Tag (`Project`, `Owner`), cấu hình CloudWatch Alarms và thiết lập Dashboard trực quan.
  - Trực tiếp upload template và triển khai Stack thành công trên AWS CloudFormation.
- **Minh chứng qua link file đã cấu hình:**
  - https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/resources/application.properties
  - https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/library-backend/src/main/java/com/nguyentrananhquoc/library_backend/config/DatabaseConfig.java
  - https://github.com/anhquoc11/CLOUD-COMPUTING/blob/main/cloudformation/template.yaml

**4. KẾ HOẠCH THỰC HIỆN TUẦN 4 (KIỂM THỬ TỔNG THỂ, TỐI ƯU CHI PHÍ, BÁO CÁO TỔNG KẾT & VIDEO DEMO)**

Trong Tuần 4, sinh viên dự kiến thực hiện các nội dung trọng tâm sau:

* **Mục 1: Kiểm thử tổng thể End-to-End (E2E Testing)**
  - Tái khởi động môi trường Beanstalk với bản build mới nhất (có Secrets Manager), kiểm tra kết nối trơn tru giữa Frontend S3 $\rightarrow$ Backend Beanstalk $\rightarrow$ Secrets Manager & RDS MySQL $\rightarrow$ S3 Images.
* **Mục 2: Đánh giá kiến trúc theo $\ge 3$ Trụ cột AWS Well-Architected Framework**
  - Viết phần giải thích chi tiết trong báo cáo tổng kết:
    - *Trụ cột Security:* Tách biệt subnet, dùng IAM LabRole/LabInstanceProfile, bảo mật mật khẩu qua Secrets Manager.
    - *Trụ cột Operational Excellence:* Giám sát chủ động qua CloudWatch Alarms & Dashboard, tự động hóa hạ tầng bằng CloudFormation IaC.
    - *Trụ cột Cost Optimization:* Sử dụng S3 Static Hosting thay vì dựng web server, dùng cấu hình Single Instance t3.micro, gắn tag theo dõi chi phí và tắt tài nguyên định kỳ.
* **Mục 3: Báo cáo Chi phí & Dọn dẹp tài nguyên (Cost Report & Clean up)**
  - Chụp ảnh minh chứng AWS Cost Explorer / Billing tóm tắt chi phí thực tế sử dụng trong suốt 4 tuần học.
  - Dọn dẹp và xóa các tài nguyên phụ trợ để giải phóng ngân sách Lab.
* **Mục 4: Viết Báo cáo Tổng kết Hoàn chỉnh & Quay Video Demo ($\le$ 10 phút)**
  - Tổng hợp báo cáo toàn diện gửi GVHD.
  - Quay video demo $\le$ 10 phút giới thiệu kiến trúc, demo tính năng web và giải thích mã nguồn/cấu hình.