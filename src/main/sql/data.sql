-- 帳號資訊
INSERT INTO `account_info` (`account`, `password`, `phone_number`, `level`) VALUES
('user1', '12345678', '0912345678', 1),
('user2', '12345678', '0923456789', 1),
('admin', 'password', NULL, 10);

-- 分類
INSERT INTO `category` (`name`, `memo`) VALUES
('Snacks', '零食'),
('Beverage', '飲料'),
('Food', '鮮食');

-- 商品
INSERT INTO `item` (`name`, `price`, `describe`, `category_id`) VALUES
('樂事洋芋片(原味)', 35, '普通的洋芋片', 1),
('雪碧', 30, '很甜的透明氣泡飲', 2),
('三角御飯糰(肉鬆)', 25, '最便宜的御飯糰', 3);

-- 庫存
INSERT INTO `stock` (`id`, `quantity`) VALUES
(1, 50),
(2, 100),
(3, 20);

-- 訂單細節紀錄
INSERT INTO `order_detail_history` (`order_number`, `item_id`, `quantity`, `amount`) VALUES
('#ORD001', 1, 2, 70),
('#ORD001', 2, 1, 30),
('#ORD002', 2, 2, 60),
('#ORD002', 3, 3, 75),
('#ORD003', 1, 2, 70),
('#ORD003', 3, 1, 25);

-- 訂單記錄
INSERT INTO `order_history` (`account_id`, `order_number`, `payment_amount`, `create_time`) VALUES
(1, 'ORD001', 100, '2023-11-20 12:00:00'),
(2, 'ORD002', 135, '2023-11-21 14:30:00'),
(1, 'ORD003', 95, '2023-11-22 16:45:00');
