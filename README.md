# DaNang_EcoWarning

[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://www.gnu.org/licenses/gpl-3.0.html)

![App Screenshot](https://res.cloudinary.com/dx0t2c7n5/image/upload/v1762444619/z7196973180809_5f5fcb3f00bdbdb54a381e2b51f3506a_gpgn6p.jpg)

🎉 Chào mừng bạn đến với kho mã nguồn của DaNang_EcoWarning!

Đây là một dự án mã nguồn mở được thiết kế với mục tiêu phân tích và trực quan hóa dữ liệu môi trường tại Đà Nẵng, đồng thời xây dựng một hệ thống cảnh báo sớm và tương tác cộng đồng.

## 📖 Mục lục

* 📍 [Giới thiệu](#-giới-thiệu)
* ✨ [Tính năng](#-tính-năng)
* 🏗️ [Tổng quan hệ thống](#️-tổng-quan-hệ-thống)
* 🚀 [Hướng dẫn cài đặt](#-hướng-dẫn-cài-đặt)
* 🤝 [Đóng góp cho dự án](#-đóng-góp-cho-dự-án)
* 📫 [Liên hệ](#-liên-hệ)
* 📜 [License](#-license)

## 📍 Giới thiệu

DaNang_EcoWarning được phát triển trong khuôn khổ cuộc thi Phần Mềm Nguồn Mở - Olympic Tin học Sinh viên Việt Nam 2025.

Dự án được thực hiện bởi đội HaUI.HIT_NovaForge và được open source hoàn toàn theo giấy phép [**GNU General Public License v3.0**](https://www.gnu.org/licenses/gpl-3.0.html).

Để biết thêm chi tiết về cuộc thi, bạn có thể xem tại [**đây**](https://drive.google.com/file/d/12VAz-SZmXfLHMtwood6u485VD8FljHnk/view?usp=sharing).  

Thông tin về cuộc thi mã nguồn mở của Olympic Tin Học Sinh Viên năm 2025 tại [**đây**](https://vfossa.vn/thong-bao/de-thi-phan-mem-nguon-mo-olp-2025-749.html).

Mục tiêu chính của dự án:
- Phân tích dữ liệu về thời tiết, khí hậu, nông nghiệp và các khu vực phòng chống thiên tai (thu thập từ các nguồn dữ liệu mở).
- Đánh giá sự tác động của khí hậu đến sản xuất nông nghiệp và các rủi ro thiên tai tiềm ẩn.
- Xây dựng một dịch vụ cho phép người dùng báo cáo trực tiếp các sự cố (như ngập lụt, sạt lở) để đóng góp vào bản đồ rủi ro cộng đồng.

## ✨ Tính năng

Dự án tập trung vào các chức năng chính sau:

* 📊 Trực quan hóa dữ liệu: Hiển thị dữ liệu về thời tiết, khí hậu và tình hình nông nghiệp tại khu vực Đà Nẵng.
* 🔬 Phân tích tác động: Phân tích ảnh hưởng của các yếu tố thời tiết và khí hậu đến sản xuất nông nghiệp.
* 🔔 Cảnh báo thiên tai: Cung cấp các cảnh báo sớm về các rủi ro thiên tai tiềm ẩn (như bão, lũ, sạt lở).
* 👥 Báo cáo cộng đồng: Cho phép người dùng gửi các báo cáo (report) về các sự cố thiên tai mà họ chứng kiến.

## 🏗️ Tổng quan hệ thống
### 🖥️ Front-end

Dưới đây là tóm tắt các công nghệ, thư viện, và framework frontend chính đã được sử dụng để xây dựng dự án dashboard.

- [React (với JSX)](https://react.dev/): Thư viện JavaScript.
- [Vite](https://vitejs.dev/): Công cụ build và server phát triển.
- [SASS (SCSS)](https://sass-lang.com/): Công nghệ tiền xử lý CSS chính, giúp viết CSS có tổ chức.
- [Recharts](https://recharts.org/): Thư viện vẽ biểu đồ.
- [GoongJS](https://docs.goong.io/): Thư viện bản đồ.
- [React Icons](https://react-icons.github.io/react-icons/): Cung cấp bộ icon.
- [React Router (react-router-dom)](https://reactrouter.com/): Xử lý việc chuyển trang.
- [Axios](https://axios-http.com/): Client HTTP chính để thực hiện các lệnh gọi API.
- [React Hooks](https://react.dev/reference/react/hooks): Các hàm cốt lõi của React để quản lý trạng thái, tác vụ bất đồng bộ, và tham chiếu.

### 🗄️ Back-end

Back-end của hệ thống được thiết kế theo kiến trúc microservices, với các công nghệ sử dụng như sau:

- [SpringBoot](https://spring.io/projects/spring-boot): Dựng API cho dự án.
- [Spring Data JPA (Hibernate)](https://spring.io/projects/spring-data-jpa): Công nghệ truy cập và làm việc với cơ sở dữ liệu.
- [PostgreSQL](https://www.postgresql.org): Cơ sở dữ liệu quan hệ.
- [Cloudinary](https://cloudinary.com/): Dịch vụ cloud để lưu trữ, quản lý và phân phối hình ảnh.
- [Docker](https://www.docker.com/): Containerize các service.
- [Docker Compose](https://docs.docker.com/compose): Quản lý các container.
- [Swagger](https://springdoc.org): Tự động tạo tài liệu và giao diện thử nghiệm API.
- [Apache Commons CSV](https://commons.apache.org/proper/commons-csv): Hỗ trợ đọc và phân tích cú pháp dữ liệu từ các file csv.

<img loading="lazy" src="./backend/docs/images/system_architecture.svg" alt="Architecture" width="100%" height=600>

## 🚀 Hướng dẫn cài đặt

Tất cả các images build từ services backend bạn có thể tìm thấy tại [Docker Hub](https://hub.docker.com/u/khuong18).

### 📋 Yêu Cầu
Để cài đặt và chạy được dự án, trước tiên bạn cần phải cài đặt các công cụ bên dưới. Hãy thực hiện theo các hướng dẫn cài đặt sau, lưu ý chọn hệ điều hành phù hợp với máy tính của bạn:

- [Docker-Installation](https://docs.docker.com/get-started/get-docker/)
- [Docker-Compose-Installation](https://docs.docker.com/compose/install/)
- [Node.js (v18 trở lên)](https://nodejs.org/)

### 🛠️ Cấu hình
Trước hết, hãy clone dự án về máy tính của bạn:
```bash
  git clone https://github.com/0152neich/DaNang_EcoWarning.git
```

#### Chạy Back-end hệ thống

- Trước khi chạy dự án, bạn cần cung cấp các biến môi trường

1.  Trong thư mục gốc `backend`, tìm file `.env.example`.
2.  Tạo một bản sao của nó và đổi tên thành `.env`.
3.  Mở file `.env` và điền các giá trị bí mật của bạn.

Tiếp theo, Đầu tiên, cd vào thư mục backend:
```bash
  cd backend
```
- Start các services với 1 lệnh docker-compose:
```bash
  docker-compose up -d
```
- Port Binding

| Service | Port |
| :-------------------------| :-----------|
| `API Gateway` | `8080:8080` |
| `Config Server` | `8888:8888` |
| `Discovery Server` | `8761:8761` |
| `Collector Data Service` | `8081:8081` |
| `Search Service` | `8082:8082` |
| `Citizen Report Service` | `8083:8083` |

#### Chạy Web-App
- Đầu tiên, cd vào thư mục /frontend/Danang_EcoWarning_FE: 

```bash
  cd frontend
  cd Danang_EcoWarning_FE
```
- Cài đặt các thư mục cần thiết:
```bash
  npm install
```
- Chạy web-app development mode:
```bash
  npm run dev
```
Lúc này web-app sẽ được chạy ở địa chỉ http://localhost:5173. Đến đây, bạn đã cài đặt xong. Còn nếu bạn muốn chạy dự án ở môi trường production, hãy ngừng development server và chạy các lệnh sau:

- Build frontend web-app
```bash
  npm run build
```
- Chạy web-app production mode
```bash
  npm run preview
```
Lúc này web-app sẽ chạy ở địa chỉ http://localhost:4173/.

#### CI/CD

Project CI/CD sử dụng [GitHub Actions](https://github.com/features/actions) để tự động hóa quá trình kiểm thử (CI) và [Vercel](https://vercel.com/) để tự động hóa quá trình triển khai (CD) cho Frontend.

Các workflows của GitHub Actions được lưu tại: `.github/workflows`, với các quy trình chính như sau:

* **`backend-ci.yaml`**: Tự động build và chạy kiểm thử cho các microservices trong thư mục `backend` mỗi khi có Pull Request.
* **`frontend-ci.yaml`**: Tự động chạy kiểm tra cho ứng dụng React trong thư mục `Frontend` mỗi khi có Pull Request.
* **`build-docker-images.yaml`**: (Tùy chọn) Tự động build Docker images cho các service `backend` và đẩy (push) lên một registry (như [Docker Hub](https://hub.docker.com/) hoặc [GitHub Container Registry](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-container-registry)) khi code được hợp nhất (merge) vào nhánh `main`.

Việc triển khai (Deploy) Frontend được [Vercel](https://vercel.com/) xử lý tự động. Vercel được liên kết với repository [GitHub](https://github.com/) và sẽ tự động build (`npm run build`) và deploy phiên bản mới mỗi khi có code được đẩy lên nhánh chính (`main`).

## 🤝 Đóng góp cho dự án

* [Bug Report ⚠️](https://github.com/0152neich/DaNang_EcoWarning/issues/new?title=🐛%20Bug%20Report:%20)
* [Request Feature 👩‍💻](https://github.com/0152neich/DaNang_EcoWarning/issues/new?title=✨%20Feature%20Request:%20)

Nếu bạn muốn đóng góp cho dự án, hãy đọc [CONTRIBUTING.md](CONTRIBUTING.md) để biết thêm chi tiết.
Mọi đóng góp của các bạn đều được trân trọng, đừng ngần ngại gửi pull request cho dự án.

## 📫 Liên hệ

- Phạm Minh Khương: [pkhuong535@gmail.com](mailto:pkhuong535@gmail.com)
- Nguyễn Lê Hoài Nam: [namngyenhoai21@gmail.com](mailto:namngyenhoai21@gmail.com)
- Đào Duy Chiến: [](mailto:)

## 📜 License

This project is licensed under the terms of the [GPL V3 license](https://www.gnu.org/licenses/gpl-3.0.html).
