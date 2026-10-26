# 威秀影城訂票系統

<img src="android/app/src/main/res/drawable-v24/a.jpg" alt="VIESHOW CINEMAS" width="220">

大學軟體工程課程的分組專題，使用 Android App、PHP API 與 MySQL 模擬電影訂票流程。

## 功能

- 會員與非會員操作流程，包含會員註冊、登入與資料查詢。
- 查詢影城、電影與場次。
- 選擇票種、餐飲與座位，建立訂票紀錄。
- 查詢與取消訂單。
- 以會員餘額模擬付款與儲值。

## 專案結構

| 資料夾 | 內容 |
|---|---|
| [android](android/) | Android Studio 專案、Java 程式、XML 畫面與圖片素材。 |
| [backend](backend/) | PHP API，處理會員、場次、座位與訂票資料。 |
| [database](database/schema.sql) | `booking_system` 資料庫結構，包含 13 張表及關聯。 |

## 開發環境

| 項目 | 設定 |
|---|---|
| Android | Java 8 語法、最低 SDK 21、compile／target SDK 32。 |
| 建置工具 | Android Gradle Plugin 7.2.1、Gradle 7.3.3；AGP 7.2 的 JDK 基準版本為 11。 |
| 後端 | PHP、mysqli、MySQL；原專案使用 XAMPP。 |

建置工具的版本相容性可參考 [Android 官方文件](https://developer.android.com/build/releases/agp-7-2-0-release-notes)。

## 連線設定

1. 將 [schema.sql](database/schema.sql) 匯入本機 MySQL，建立 `booking_system` 資料庫。檔案只有資料表結構，不附原始會員與訂位資料；影城、電影、場次、票種及餐飲資料需自行建立。
2. 將 `backend` 內的 PHP 檔案放到 XAMPP 的 `htdocs/vieshow`，把 [config.example.php](backend/config.example.php) 複製為 `config.php`，填入自己的資料庫帳號與密碼。
3. 使用 Android Studio 開啟 `android` 資料夾。App 的 API 預設為 `http://10.0.2.2/vieshow/`，適用於 Android Emulator 連線到開發電腦上的後端。

使用實體手機或其他後端位置時，在個人的 Gradle 設定（`~/.gradle/gradle.properties`）加入 `VIESHOW_API_BASE_URL`，值為自己的 API 根網址，由 [ApiConfig.java](android/app/src/main/java/com/example/vieshow/ApiConfig.java) 統一套用。模擬器位址的說明可參考 [Android 官方文件](https://developer.android.com/studio/run/emulator-networking-address)。

私人連線設定 `backend/config.php` 不納入版本控制。付款與儲值流程沿用課堂實作，以資料庫中的會員餘額進行操作。

`backend/getallseatID.php` 是未啟用的舊端點，對應的座位總表未附於原始 SQL。App 的選位流程使用 `emptyseat.php` 與 `booking_seat`。
