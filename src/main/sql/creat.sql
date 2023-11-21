CREATE DATABASE `UnmannedStoreDB`;

CREATE TABLE `account_info`(  -- 帳號資訊
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`account` VARCHAR(50) NOT NULL UNIQUE,  -- 帳號
	`password` VARCHAR(50) NOT NULL,  -- 密碼
	`phone_number` VARCHAR(30),  -- 電話號碼
	`level` INT NOT NULL DEFAULT 0  -- 權限
);
CREATE TABLE `category`(  -- 分類
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(30) NOT NULL,  -- 分類名稱
	`memo` VARCHAR(50)  -- 備忘錄
);
CREATE TABLE `item`(  -- 商品
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(30) NOT NULL,  -- 商品名稱
	`price` INT NOT NULL,  -- 商品單價
	`describe` VARCHAR(100),  -- 商品描述
	`category_id` INT NOT NULL, FOREIGN KEY (`category_id`) REFERENCES `category`(`id`)ON DELETE CASCADE  -- 商品分類
);
CREATE TABLE `stock`(  -- 庫存
	`id` INT PRIMARY KEY UNIQUE, FOREIGN KEY (`id`) REFERENCES `item`(`id`) ON DELETE CASCADE,  -- 商品ID
	`quantity` INT NOT NULL DEFAULT 0  -- 庫存數量
);
CREATE TABLE `order_detail_history`(  -- 訂單細節紀錄
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`order_number` VARCHAR(30) NOT NULL,INDEX `idx_order_number` (`order_number`),  -- 訂單編號
	`item_id` INT NOT NULL, FOREIGN KEY (`item_id`) REFERENCES `item`(`id`) ON DELETE CASCADE,  -- 商品ID
	`quantity` INT NOT NULL,  -- 購買數量
	`amount` INT NOT NULL  -- 價錢
);
CREATE TABLE `order_history`(  -- 訂單記錄
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `account_id` INT NOT NULL, FOREIGN KEY (`account_id`) REFERENCES `account_info`(`id`) ON DELETE CASCADE,  -- 帳號ID
	`order_number` VARCHAR(30) NOT NULL UNIQUE,INDEX `idx_order_number` (`order_number`),  -- 訂單編號
	`payment_amount` INT NOT NULL,  -- 總價
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP, INDEX `idx_create_time` (`create_time`) -- 創建時間
);