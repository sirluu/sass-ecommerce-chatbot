# Hướng dẫn Build & Deploy

Tài liệu này mô tả cách build và deploy **frontend (Next.js)** lên **Vercel** và **backend (Flask API)** lên **Railway** cho dự án S-TORE.

## Tổng quan kiến trúc

```
┌─────────────────────┐         HTTPS          ┌─────────────────────┐
│  Vercel             │  ───────────────────►  │  Railway            │
│  apps/web (Next.js) │   NEXT_PUBLIC_API_URL    │  server (Flask API) │
│  Port: 443 (Vercel) │                          │  Port: $PORT        │
└─────────────────────┘                          └──────────┬──────────┘
                                                           │
                                              ┌────────────┼────────────┐
                                              ▼            ▼            ▼
                                         PostgreSQL    Pinecone    Google Gemini
                                         (Railway)     (cloud)     (API key)
```

**Thứ tự deploy khuyến nghị:** Backend (Railway) trước → lấy URL API → deploy Frontend (Vercel) với biến môi trường trỏ về backend.

---

## Yêu cầu trước khi deploy

| Thành phần | Yêu cầu |
|------------|---------|
| Frontend | Node.js 18+, npm hoặc bun |
| Backend | Python 3.12+ |
| Tài khoản | [Vercel](https://vercel.com), [Railway](https://railway.app) |
| API keys | Google AI Studio, Pinecone |

### Chuẩn bị Pinecone

1. Tạo index trên [Pinecone Console](https://app.pinecone.io/).
2. Cấu hình index:
   - **Dimensions:** `384` (model `all-MiniLM-L6-v2`)
   - **Metric:** `cosine`
3. Ghi lại tên index (mặc định dự án dùng `ecommerce-products`).

---

## Phần 1: Deploy Backend lên Railway

### 1.1. Build local (kiểm tra trước khi deploy)

```bash
cd server

# Tạo virtual environment
python -m venv .venv

# Windows
.venv\Scripts\activate

# macOS / Linux
source .venv/bin/activate

# Cài dependencies
pip install -r requirements.txt

# Tạo file .env (tham khảo biến môi trường bên dưới)
# cp .env.example .env

# Chạy migration
set FLASK_APP=app.py        # Windows
# export FLASK_APP=app.py   # macOS / Linux
flask db upgrade

# Chạy server
python app.py
```

Kiểm tra: mở `http://localhost:5000/api/health` — phản hồi `"status": "healthy"`.

**Production local (Gunicorn):**

```bash
set FLASK_ENV=production
set PORT=5000
gunicorn --config gunicorn.conf.py app:app
```

### 1.2. Tạo project trên Railway

1. Đăng nhập [Railway Dashboard](https://railway.app/dashboard).
2. **New Project** → **Deploy from GitHub repo** → chọn repo `sass-ecommerce-chatbot`.
3. Trong service backend, mở **Settings**:
   - **Root Directory:** `server`
   - **Watch Paths:** `server/**` (tùy chọn, tránh redeploy khi chỉ sửa frontend)

### 1.3. Thêm PostgreSQL

1. Trong cùng Railway project: **+ New** → **Database** → **PostgreSQL**.
2. Railway tự inject biến `DATABASE_URL` vào service backend (cần **Reference Variable** nếu DB và API là 2 service riêng).

> **Lưu ý:** Railway có thể cung cấp URL dạng `postgres://...`. SQLAlchemy 2.x yêu cầu `postgresql://`. Nếu gặp lỗi kết nối DB, thêm biến sau:
>
> ```
> DATABASE_URL=postgresql://user:pass@host:port/dbname
> ```
>
> (Thay `postgres://` thành `postgresql://` ở đầu chuỗi.)

### 1.4. Biến môi trường (Railway → Variables)

| Biến | Bắt buộc | Mô tả | Ví dụ |
|------|----------|-------|-------|
| `FLASK_ENV` | Có | Chế độ Flask | `production` |
| `FLASK_APP` | Có | Entry point cho Flask CLI (migration) | `app.py` |
| `SECRET_KEY` | Có | Flask secret key (chuỗi ngẫu nhiên dài) | `your-random-secret` |
| `JWT_SECRET_KEY` | Có | Secret ký JWT | `your-jwt-secret` |
| `DATABASE_URL` | Có | PostgreSQL connection string | Tự động từ Railway PostgreSQL |
| `GOOGLE_API_KEY` | Có | Google AI Studio API key | `AIza...` |
| `PINECONE_API_KEY` | Có | Pinecone API key | `pcsk_...` |
| `PINECONE_INDEX_NAME` | Có | Tên Pinecone index | `ecommerce-products` |
| `FRONTEND_URL` | Khuyến nghị | URL frontend (CORS / redirect) | `https://your-app.vercel.app` |
| `JWT_ACCESS_TOKEN_EXPIRES` | Không | Thời hạn access token (giây) | `3600` |
| `PORT` | Không | Railway tự gán | — |

`PINECONE_ENVIRONMENT` có trong `config.py` nhưng Pinecone SDK mới không bắt buộc nếu dùng serverless index.

### 1.5. Start Command

Railway dùng Nixpacks detect Python. Cấu hình **Start Command** (Settings → Deploy):

```bash
pip install -r requirements.txt && flask db upgrade && gunicorn --config gunicorn.conf.py app:app
```

Hoặc dùng script có sẵn (Linux container):

```bash
bash start.sh
```

File `start.sh` thực hiện: cài dependencies → `flask db upgrade` → chạy Gunicorn với `gunicorn.conf.py`.

**Gunicorn** (`gunicorn.conf.py`):
- Bind: `0.0.0.0:$PORT`
- Workers: `1` (phù hợp bộ nhớ khi load Sentence Transformers)
- Timeout: `30s`

### 1.6. Chạy migration & index sản phẩm

Sau deploy lần đầu:

**Migration** — đã chạy tự động nếu start command có `flask db upgrade`.

**Index Pinecone** (một lần, sau khi DB có dữ liệu):

1. Railway → service backend → **Settings** → bật **One-off command** hoặc dùng Railway CLI:

```bash
railway link
railway run python -m scripts.index_all_products
```

2. Script đọc sản phẩm từ DB và upsert embedding lên Pinecone.

> Lần khởi động đầu, app tự `db.create_all()` và seed sample products qua `DatabaseSeeder` (xem `server/app.py`).

### 1.7. Kiểm tra backend production

Thay `YOUR_RAILWAY_URL` bằng domain Railway (Settings → Networking → Generate Domain):

```bash
curl https://YOUR_RAILWAY_URL/api/health
curl https://YOUR_RAILWAY_URL/api/chat/health
```

Phản hồi mong đợi: `"success": true`, `"status": "healthy"`.

---

## Phần 2: Deploy Frontend lên Vercel

### 2.1. Build local (kiểm tra trước khi deploy)

```bash
cd apps/web

# Cài dependencies
npm install
# hoặc: bun install

# Tạo .env.local
echo NEXT_PUBLIC_API_URL=http://localhost:5000 > .env.local

# Development
npm run dev
# hoặc: bun dev

# Production build
npm run build
npm run start
```

Mở `http://localhost:3000` — UI phải gọi API qua `NEXT_PUBLIC_API_URL`.

Frontend đọc API URL tại `apps/web/lib/api.ts`:

```typescript
const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:5000'
```

### 2.2. Import project trên Vercel

1. Đăng nhập [Vercel Dashboard](https://vercel.com/dashboard).
2. **Add New** → **Project** → import repo GitHub.
3. Cấu hình **Root Directory:** `apps/web` (monorepo — bắt buộc).

### 2.3. Build settings (Vercel)

| Setting | Giá trị |
|---------|---------|
| Framework Preset | Next.js |
| Root Directory | `apps/web` |
| Build Command | `npm run build` (mặc định) |
| Output Directory | `.next` (mặc định Next.js) |
| Install Command | `npm install` |
| Node.js Version | 18.x hoặc 20.x |

### 2.4. Biến môi trường (Vercel → Settings → Environment Variables)

| Biến | Bắt buộc | Mô tả | Ví dụ |
|------|----------|-------|-------|
| `NEXT_PUBLIC_API_URL` | Có | URL backend Railway **không** có `/api` ở cuối | `https://your-api.up.railway.app` |

> Biến có prefix `NEXT_PUBLIC_` được embed vào bundle client-side. Chỉ dùng cho URL công khai, không đặt secret ở đây.

Áp dụng cho **Production**, **Preview**, và **Development** (nếu dùng Vercel dev).

### 2.5. Deploy

1. **Deploy** — Vercel build và publish.
2. Sau deploy, cập nhật `FRONTEND_URL` trên Railway = URL Vercel (ví dụ `https://s-tore.vercel.app`).
3. Redeploy backend nếu cần áp dụng CORS (hiện backend cho phép `origins: *`).

### 2.6. Kiểm tra frontend production

1. Mở URL Vercel.
2. **Register / Login** — xác nhận JWT auth hoạt động.
3. **Products** — danh sách sản phẩm load từ API.
4. **Chat** — gửi tin nhắn, kiểm tra Gemini + Pinecone.

Mở DevTools → Network: request phải đi tới `https://YOUR_RAILWAY_URL/api/...`.

---

## Phần 3: Kết nối Frontend ↔ Backend

Sau khi cả hai đã deploy:

```
Vercel (frontend)                    Railway (backend)
─────────────────                    ─────────────────
NEXT_PUBLIC_API_URL  ──────────────► https://xxx.up.railway.app
                                     FRONTEND_URL = https://yyy.vercel.app
```

**Checklist:**

- [ ] Backend `/api/health` trả về 200
- [ ] `NEXT_PUBLIC_API_URL` trỏ đúng domain Railway (không trailing slash)
- [ ] `FRONTEND_URL` trên Railway khớp domain Vercel
- [ ] PostgreSQL đã migrate (`flask db upgrade`)
- [ ] Pinecone index đã tạo và sản phẩm đã index
- [ ] `GOOGLE_API_KEY` và `PINECONE_API_KEY` hợp lệ

---

## Phần 4: CI/CD tự động

### Railway

- Push lên branch được link → Railway tự build & deploy service `server/`.
- Mỗi PR có thể dùng **Preview Environment** (Railway Pro).

### Vercel

- Push lên `main` → Production deploy (`apps/web`).
- Pull Request → Preview URL riêng.
- Preview cần `NEXT_PUBLIC_API_URL` trỏ staging backend hoặc cùng backend production.

---

## Phần 5: Xử lý sự cố

### Frontend build fail trên Vercel

| Triệu chứng | Cách xử lý |
|-------------|------------|
| `Module not found` | Kiểm tra Root Directory = `apps/web` |
| TypeScript errors | Chạy `npm run build` local, sửa lỗi trước khi push |
| Env undefined | Thêm `NEXT_PUBLIC_API_URL` trên Vercel, redeploy |

### Backend không start trên Railway

| Triệu chứng | Cách xử lý |
|-------------|------------|
| Crash khi boot | Xem **Deploy Logs** — thường thiếu API key hoặc lỗi DB |
| `postgres://` error | Đổi `DATABASE_URL` sang `postgresql://` |
| OOM (Out of Memory) | Giữ `workers = 1` trong `gunicorn.conf.py`; nâng plan Railway |
| Migration fail | Đặt `FLASK_APP=app.py`; chạy `flask db upgrade` thủ công qua CLI |

### CORS / Auth lỗi trên production

- Backend đã bật `CORS(origins="*")` — lỗi CORS hiếm khi xảy ra.
- JWT lưu trong `localStorage` — domain Vercel và Railway khác origin vẫn hoạt động.
- Nếu 401 liên tục: kiểm tra `JWT_SECRET_KEY` không đổi giữa các lần redeploy (token cũ sẽ invalid).

### Chat / search không hoạt động

1. `GET /api/chat/health` — kiểm tra Gemini.
2. Pinecone: index name, dimension 384, API key đúng.
3. Chạy lại `python -m scripts.index_all_products` nếu DB có sản phẩm nhưng search rỗng.

### Ảnh sản phẩm không hiển thị

Next.js chỉ cho phép domain ảnh trong `next.config.js`:

```javascript
images: {
  domains: ['images.pexels.com'],
}
```

Thêm domain mới vào `domains` nếu dùng nguồn ảnh khác, rồi redeploy Vercel.

---

## Phần 6: Tóm tắt lệnh

### Backend (Railway / local production)

```bash
cd server
pip install -r requirements.txt
export FLASK_APP=app.py
export FLASK_ENV=production
flask db upgrade
gunicorn --config gunicorn.conf.py app:app
```

### Frontend (Vercel / local production)

```bash
cd apps/web
npm install
export NEXT_PUBLIC_API_URL=https://your-api.up.railway.app
npm run build
npm run start
```

### Index vector (sau deploy backend)

```bash
cd server
python -m scripts.index_all_products
```

---

## Tham chiếu file trong repo

| File | Vai trò |
|------|---------|
| `apps/web/package.json` | Scripts `build`, `start`, `dev` |
| `apps/web/lib/api.ts` | Cấu hình `NEXT_PUBLIC_API_URL` |
| `apps/web/next.config.js` | Cấu hình Next.js (image domains) |
| `server/app.py` | Flask app, health check, DB seed |
| `server/config.py` | Biến môi trường backend |
| `server/start.sh` | Script khởi động production |
| `server/gunicorn.conf.py` | Cấu hình Gunicorn cho Railway |
| `server/requirements.txt` | Python dependencies |
| `server/migrations/` | Alembic database migrations |
