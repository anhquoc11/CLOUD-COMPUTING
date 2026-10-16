// Cấu hình URL Backend: Mặc định chạy local 8080, sau này deploy Elastic Beanstalk chỉ cần đổi URL này
const API_BASE_URL = "http://library-backend-app-env.eba-hetmedpc.us-east-1.elasticbeanstalk.com/api/books";

// Lưu danh sách sách trong bộ nhớ để phục vụ tìm kiếm nhanh
let allBooks = [];

// DOM Elements
const booksContainer = document.getElementById("books-container");
const loadingSpinner = document.getElementById("loading-spinner");
const emptyState = document.getElementById("empty-state");
const searchInput = document.getElementById("search-input");

const formSection = document.getElementById("book-form-section");
const bookForm = document.getElementById("book-form");
const formTitle = document.getElementById("form-title");
const btnToggleForm = document.getElementById("btn-toggle-form");
const btnCancel = document.getElementById("btn-cancel");
const btnSubmit = document.getElementById("btn-submit");

const bookIdInput = document.getElementById("book-id");
const titleInput = document.getElementById("title");
const authorInput = document.getElementById("author");
const isbnInput = document.getElementById("isbn");
const coverFileInput = document.getElementById("cover-file");

// Ảnh mặc định nếu sách chưa có ảnh bìa
const DEFAULT_COVER = "https://placehold.co/400x550/e2e8f0/475569?text=No+Cover";

// 1. Tải danh sách sách từ Backend
async function fetchBooks() {
    loadingSpinner.style.display = "block";
    emptyState.style.display = "none";
    booksContainer.innerHTML = "";

    try {
        const response = await fetch(API_BASE_URL);
        if (!response.ok) throw new Error("Không thể kết nối đến Backend");

        allBooks = await response.json();
        loadingSpinner.style.display = "none";

        if (allBooks.length === 0) {
            emptyState.style.display = "block";
        } else {
            renderBooks(allBooks);
        }
    } catch (error) {
        loadingSpinner.innerHTML = `⚠️ Lỗi kết nối: ${error.message}. Hãy đảm bảo Backend Spring Boot và AWS RDS đang chạy!`;
        console.error(error);
    }
}

// 2. Render danh sách thẻ sách
function renderBooks(books) {
    booksContainer.innerHTML = "";

    books.forEach(book => {
        const card = document.createElement("div");
        card.className = "book-card";

        const coverUrl = book.coverImageUrl ? book.coverImageUrl : DEFAULT_COVER;

        card.innerHTML = `
            <img src="${coverUrl}" alt="${book.title}" class="book-cover" onerror="this.src='${DEFAULT_COVER}'">
            <div class="book-info">
                <h4 class="book-title">${book.title}</h4>
                <p class="book-author">Tác giả: <strong>${book.author}</strong></p>
                <span class="book-isbn">ISBN: ${book.isbn}</span>
                <div class="book-actions">
                    <button class="btn btn-edit" onclick="onEditBook(${book.id})">✏️ Sửa</button>
                    <button class="btn btn-danger" onclick="onDeleteBook(${book.id}, '${book.title}')">🗑️ Xóa</button>
                </div>
            </div>
        `;
        booksContainer.appendChild(card);
    });
}

// 3. Upload ảnh lên AWS S3
async function uploadCoverToS3(file) {
    const formData = new FormData();
    formData.append("file", file);

    const response = await fetch(`${API_BASE_URL}/upload-cover`, {
        method: "POST",
        body: formData
    });

    if (!response.ok) {
        throw new Error("Lỗi tải ảnh lên AWS S3");
    }

    const data = await response.json();
    return data.imageUrl;
}

// 4. Xử lý Submit Form (Thêm mới hoặc Cập nhật)
bookForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const id = bookIdInput.value;
    const isEditing = !!id;

    btnSubmit.disabled = true;
    btnSubmit.innerText = "Đang xử lý...";

    try {
        let coverImageUrl = null;

        // Nếu người dùng có chọn ảnh mới thì upload lên S3 trước
        if (coverFileInput.files.length > 0) {
            btnSubmit.innerText = "Đang upload ảnh lên AWS S3...";
            coverImageUrl = await uploadCoverToS3(coverFileInput.files[0]);
        }

        const bookPayload = {
            title: titleInput.value.trim(),
            author: authorInput.value.trim(),
            isbn: isbnInput.value.trim(),
            available: true
        };

        if (coverImageUrl) {
            bookPayload.coverImageUrl = coverImageUrl;
        }

        btnSubmit.innerText = "Đang lưu vào AWS RDS...";

        let response;
        if (isEditing) {
            // Cập nhật sách
            response = await fetch(`${API_BASE_URL}/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(bookPayload)
            });
        } else {
            // Thêm sách mới
            response = await fetch(API_BASE_URL, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(bookPayload)
            });
        }

        if (!response.ok) throw new Error("Lưu thông tin thất bại");

        // Thành công: Reset form và tải lại danh sách
        resetForm();
        fetchBooks();
        alert(isEditing ? "Cập nhật sách thành công!" : "Thêm sách mới lên AWS thành công!");
    } catch (error) {
        alert("Đã xảy ra lỗi: " + error.message);
        console.error(error);
    } finally {
        btnSubmit.disabled = false;
        btnSubmit.innerText = "Lưu vào Hệ Thống";
    }
});

// 5. Chỉnh sửa sách
window.onEditBook = function(id) {
    const book = allBooks.find(b => b.id === id);
    if (!book) return;

    bookIdInput.value = book.id;
    titleInput.value = book.title;
    authorInput.value = book.author;
    isbnInput.value = book.isbn;
    coverFileInput.value = ""; // Reset file input

    formTitle.innerText = "Chỉnh sửa thông tin sách";
    btnSubmit.innerText = "Cập nhật thay đổi";
    formSection.style.display = "block";
    formSection.scrollIntoView({ behavior: "smooth" });
};

// 6. Xóa sách
window.onDeleteBook = async function(id, title) {
    if (!confirm(`Bạn có chắc chắn muốn xóa cuốn sách "${title}" khỏi hệ thống không?`)) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: "DELETE"
        });

        if (!response.ok) throw new Error("Xóa sách thất bại");

        fetchBooks();
    } catch (error) {
        alert("Lỗi khi xóa: " + error.message);
    }
};

// 7. Tìm kiếm sách
searchInput.addEventListener("input", (e) => {
    const query = e.target.value.toLowerCase().trim();
    const filtered = allBooks.filter(b => 
        b.title.toLowerCase().includes(query) ||
        b.author.toLowerCase().includes(query) ||
        b.isbn.toLowerCase().includes(query)
    );
    renderBooks(filtered);
});

// 8. Đóng / Mở Form
btnToggleForm.addEventListener("click", () => {
    resetForm();
    formSection.style.display = formSection.style.display === "none" ? "block" : "none";
});

btnCancel.addEventListener("click", () => {
    resetForm();
    formSection.style.display = "none";
});

function resetForm() {
    bookForm.reset();
    bookIdInput.value = "";
    formTitle.innerText = "Thêm cuốn sách mới";
    btnSubmit.innerText = "Lưu vào Hệ Thống";
    formSection.style.display = "none";
}

// Khởi chạy khi trang web tải xong
fetchBooks();