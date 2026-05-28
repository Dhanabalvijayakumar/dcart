
CREATE TABLE users(
    user_id BIGINT AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    mobile VARCHAR(15) UNIQUE,
    password VARCHAR(255),
    role ENUM('USER', 'ADMIN') NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT e_commerce_users_pk PRIMARY KEY (user_id)
);

CREATE TABLE categories(
    category_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),

    CONSTRAINT e_commerce_categories_pk PRIMARY KEY (category_id)
);

CREATE TABLE products(
    product_id BIGINT AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2),
    brand VARCHAR(250) NOT NULL,
    rating DOUBLE,
    discount_percentage DOUBLE,
    category_id BIGINT,
    image_url VARCHAR(1000),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT e_commerce_products_pk PRIMARY KEY (product_id),
    CONSTRAINT e_commerce_products_fk FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE cart(
    cart_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT,

    CONSTRAINT e_commerce_cart_pk PRIMARY KEY (cart_id),
    CONSTRAINT e_commerce_cart_fk FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE cart_items(
    cart_item_id BIGINT NOT NULL AUTO_INCREMENT,
    cart_id BIGINT,
    product_id BIGINT,
    quantity INT,

    CONSTRAINT e_commerce_cart_items_pk PRIMARY KEY (cart_item_id),
    CONSTRAINT e_commerce_cart_items_fk1 FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
    CONSTRAINT e_commerce_cart_items_fk2 FOREIGN KEY (product_id) REFERENCES products(product_id)
);

ALTER TABLE cart_items
MODIFY cart_item_id BIGINT NOT NULL AUTO_INCREMENT;

CREATE TABLE orders(
    order_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT,
    total_price DECIMAL(10,2),
    status ENUM('PLACED', 'SHIPPED', 'DELIVERED', 'CANCELLED') NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT e_commerce_orders_pk PRIMARY KEY (order_id),
    CONSTRAINT e_commerce_orders_fk FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- alter table orders drop column order_item_id;

CREATE TABLE order_items(
    order_item_id BIGINT NOT NULL AUTO_INCREMENT,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT,
    price DECIMAL(10,2),

    CONSTRAINT e_commerce_order_items_pk PRIMARY KEY (order_item_id),
    CONSTRAINT e_commerce_order_items_fk1 FOREIGN KEY (order_id) REFERENCES orders(order_id),
    CONSTRAINT e_commerce_order_items_fk2 FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE wishlist(
    wishlist_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT,
    product_id BIGINT,

    CONSTRAINT e_commerce_wishlist_pk PRIMARY KEY (wishlist_id),
    CONSTRAINT e_commerce_wishlist_fk1 FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT e_commerce_wishlist_fk2 FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE token_blacklist(
    token_id BIGINT NOT NULL AUTO_INCREMENT,
    token VARCHAR(1000),

    CONSTRAINT e_commerce_token_pk PRIMARY KEY(token_id)
);

CREATE TABLE user_preferences(
    preference_id BIGINT AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    score INT DEFAULT 0,

    CONSTRAINT e_commerce_user_preferences_pk PRIMARY KEY (preference_id),
    CONSTRAINT e_commerce_user_preferences_fk1 FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT e_commerce_user_preferences_fk2 FOREIGN KEY (category_id) REFERENCES categories(category_id)
);



-- =========================================
-- CATEGORY TABLE DATA
-- =========================================

INSERT INTO categories(name) VALUES
('Mobiles'),
('Fashion'),
('Books'),
('Home Appliances'),
('Electronics'),
('Laptops'),
('Footwear'),
('Beauty'),
('Sports'),
('Accessories');


-- =========================================
-- PRODUCTS TABLE DATA (100 PRODUCTS)
-- =========================================

INSERT INTO products
(product_name, description, price, brand, rating, discount_percentage, category_id, image_url)
VALUES


('iPhone 15','Apple A16 flagship smartphone',79999,'Apple',4.8,10,1,
'https://images.unsplash.com/photo-1695048133142-1a20484d2569?w=500&auto=format&fit=crop&q=60'),

('Samsung Galaxy S24','Samsung premium Android phone',74999,'Samsung',4.7,12,1,
'https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?w=500&auto=format&fit=crop&q=60'),

('OnePlus 12','Fast and smooth flagship phone',64999,'OnePlus',4.6,15,1,
'https://images.unsplash.com/photo-1598327105666-5b89351aff97?w=500&auto=format&fit=crop&q=60'),

('Google Pixel 8','Pure Android experience',69999,'Google',4.7,11,1,
'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=500&auto=format&fit=crop&q=60'),

('Xiaomi 14','High performance flagship device',58999,'Xiaomi',4.5,18,1,
'https://images.unsplash.com/photo-1580910051074-3eb694886505?w=500&auto=format&fit=crop&q=60'),

('Realme GT 6','Gaming smartphone with AMOLED display',35999,'Realme',4.4,20,1,
'https://images.unsplash.com/photo-1512499617640-c74ae3a79d37?w=500&auto=format&fit=crop&q=60'),

('Vivo X100','Camera focused premium phone',52999,'Vivo',4.5,13,1,
'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=500&auto=format&fit=crop&q=60'),

('Oppo Reno 11','Stylish mid-range smartphone',32999,'Oppo',4.3,16,1,
'https://images.unsplash.com/photo-1585060544812-6b45742d762f?w=500&auto=format&fit=crop&q=60'),

('Nothing Phone 2','Transparent design smartphone',44999,'Nothing',4.6,10,1,
'https://images.unsplash.com/photo-1567581935884-3349723552ca?w=500&auto=format&fit=crop&q=60'),

('Moto Edge 50','Curved display Android phone',38999,'Motorola',4.4,17,1,
'https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=500&auto=format&fit=crop&q=60'),


('Men Round Neck T-Shirt','Comfort cotton t-shirt',799,'H&M',4.2,35,2,
'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500&auto=format&fit=crop&q=60'),

('Slim Fit Jeans','Blue denim jeans for men',1999,'Levis',4.4,20,2,
'https://images.unsplash.com/photo-1542272604-787c3835535d?w=500&auto=format&fit=crop&q=60'),

('Women Casual Top','Soft cotton casual top',1299,'Zara',4.3,22,2,
'https://images.unsplash.com/photo-1483985988355-763728e1935b?w=500&auto=format&fit=crop&q=60'),

('Hoodie Sweatshirt','Winter wear hoodie',2499,'Puma',4.5,18,2,
'https://images.unsplash.com/photo-1503342217505-b0a15ec3261c?w=500&auto=format&fit=crop&q=60'),

('Formal Shirt','Office wear formal shirt',1499,'Allen Solly',4.3,25,2,
'https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=500&auto=format&fit=crop&q=60'),

('Women Kurti','Traditional stylish kurti',1199,'Biba',4.4,30,2,
'https://images.unsplash.com/photo-1529139574466-a303027c1d8b?w=500&auto=format&fit=crop&q=60'),

('Leather Jacket','Premium biker jacket',4999,'Roadster',4.6,15,2,
'https://images.unsplash.com/photo-1521223890158-f9f7c3d5d504?w=500&auto=format&fit=crop&q=60'),

('Track Pant','Sportswear track pant',999,'Adidas',4.2,28,2,
'https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?w=500&auto=format&fit=crop&q=60'),

('Women Saree','Traditional silk saree',3999,'Pothys',4.7,12,2,
'https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=500&auto=format&fit=crop&q=60'),

('Kids Wear Combo','Combo pack for kids',1799,'Max',4.3,20,2,
'https://images.unsplash.com/photo-1512436991641-6745cdb1723f?w=500&auto=format&fit=crop&q=60'),


('Java Programming','Complete Java guide',699,'Oracle Press',4.8,10,3,
'https://images.unsplash.com/photo-1512820790803-83ca734da794?w=500&auto=format&fit=crop&q=60'),

('Spring Boot Guide','Master Spring Boot',799,'Packt',4.7,15,3,
'https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?w=500&auto=format&fit=crop&q=60'),

('ReactJS Handbook','Frontend development book',599,'OReilly',4.6,20,3,
'https://images.unsplash.com/photo-1495446815901-a7297e633e8d?w=500&auto=format&fit=crop&q=60'),

('Python Crash Course','Learn Python quickly',899,'No Starch',4.9,12,3,
'https://images.unsplash.com/photo-1516979187457-637abb4f9353?w=500&auto=format&fit=crop&q=60'),

('Data Structures','DSA concepts explained',699,'Pearson',4.7,18,3,
'https://images.unsplash.com/photo-1521587760476-6c12a4b040da?w=500&auto=format&fit=crop&q=60'),

('Machine Learning Basics','AI beginner guide',999,'McGraw Hill',4.8,14,3,
'https://images.unsplash.com/photo-1519681393784-d120267933ba?w=500&auto=format&fit=crop&q=60'),

('Clean Code','Professional coding practices',799,'Prentice Hall',4.9,10,3,
'https://images.unsplash.com/photo-1511108690759-009324a90311?w=500&auto=format&fit=crop&q=60'),

('Microservices Architecture','Modern backend systems',899,'Manning',4.6,16,3,
'https://images.unsplash.com/photo-1507842217343-583bb7270b66?w=500&auto=format&fit=crop&q=60'),

('Docker Deep Dive','Containerization explained',749,'Nigel Poulton',4.7,15,3,
'https://images.unsplash.com/photo-1526243741027-444d633d7365?w=500&auto=format&fit=crop&q=60'),

('System Design Interview','Scalable architecture concepts',1099,'Alex Xu',4.9,11,3,
'https://images.unsplash.com/photo-1512820790803-83ca734da794?w=500&auto=format&fit=crop&q=60');



INSERT INTO products
(product_name, description, price, brand, rating, discount_percentage, category_id, image_url)
VALUES

('Microwave Oven','Convection microwave oven',8999,'LG',4.5,18,4,
'https://images.unsplash.com/photo-1574269909862-7e1d70bb8078?w=500&auto=format&fit=crop&q=60'),

('Air Conditioner','1.5 Ton inverter AC',35999,'Daikin',4.7,20,4,
'https://images.unsplash.com/photo-1581092335397-9583eb92d232?w=500&auto=format&fit=crop&q=60'),

('Refrigerator','Double door refrigerator',28999,'Samsung',4.6,16,4,
'https://images.unsplash.com/photo-1584568694244-14fbdf83bd30?w=500&auto=format&fit=crop&q=60'),

('Washing Machine','Fully automatic washing machine',24999,'IFB',4.5,15,4,
'https://images.unsplash.com/photo-1626806787461-102c1bfaaea1?w=500&auto=format&fit=crop&q=60'),

('Vacuum Cleaner','High suction vacuum cleaner',6999,'Philips',4.4,22,4,
'https://images.unsplash.com/photo-1558317374-067fb5f30001?w=500&auto=format&fit=crop&q=60'),

('Water Purifier','RO + UV water purifier',12999,'Kent',4.3,19,4,
'https://images.pexels.com/photos/4239031/pexels-photo-4239031.jpeg?auto=compress&cs=tinysrgb&w=600'),

('Induction Stove','Fast heating induction cooktop',3499,'Prestige',4.4,25,4,
'https://images.unsplash.com/photo-1586208958839-06c17cacdf08?w=500&auto=format&fit=crop&q=60'),

('Mixer Grinder','750W kitchen mixer grinder',4999,'Butterfly',4.2,28,4,
'https://images.unsplash.com/photo-1570222094114-d054a817e56b?w=500&auto=format&fit=crop&q=60'),

('Electric Kettle','1.5L electric kettle',1499,'Havells',4.5,30,4,
'https://images.unsplash.com/photo-1516315720917-231ef9acce48?w=500&auto=format&fit=crop&q=60'),

('Air Fryer','Healthy cooking air fryer',7999,'Philips',4.6,17,4,
'https://images.unsplash.com/photo-1585238342024-78d387f4a707?w=500&auto=format&fit=crop&q=60'),



('Bluetooth Earbuds','Wireless noise cancelling earbuds',2999,'Boat',4.3,32,5,
'https://images.unsplash.com/photo-1606220588913-b3aacb4d2f46?w=500&auto=format&fit=crop&q=60'),

('Smart Watch','Fitness tracking smartwatch',4999,'Noise',4.4,25,5,
'https://images.unsplash.com/photo-1517430816045-df4b7de11d1d?w=500&auto=format&fit=crop&q=60'),

('Gaming Keyboard','RGB mechanical keyboard',3499,'Redgear',4.5,20,5,
'https://images.unsplash.com/photo-1511467687858-23d96c32e4ae?w=500&auto=format&fit=crop&q=60'),

('Wireless Mouse','Ergonomic wireless mouse',1499,'Logitech',4.6,18,5,
'https://images.unsplash.com/photo-1527814050087-3793815479db?w=500&auto=format&fit=crop&q=60'),

('Portable Speaker','Bluetooth portable speaker',3999,'JBL',4.7,15,5,
'https://images.unsplash.com/photo-1589003077984-894e133dabab?w=500&auto=format&fit=crop&q=60'),

('Power Bank','20000mAh fast charging power bank',2499,'Mi',4.5,28,5,
'https://images.unsplash.com/photo-1609091839311-d5365f9ff1c5?w=500&auto=format&fit=crop&q=60'),

('DSLR Camera','Professional DSLR camera',55999,'Canon',4.8,12,5,
'https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=500&auto=format&fit=crop&q=60'),

('LED Monitor','24 inch Full HD monitor',11999,'Acer',4.6,19,5,
'https://images.unsplash.com/photo-1527443154391-507e9dc6c5cc?w=500&auto=format&fit=crop&q=60'),

('Gaming Headset','Surround sound gaming headset',2999,'HyperX',4.5,24,5,
'https://images.unsplash.com/photo-1585298723682-7115561c51b7?w=500&auto=format&fit=crop&q=60'),

('USB Hub','Multi-port USB hub',999,'TP-Link',4.2,30,5,
'https://images.unsplash.com/photo-1625842268584-8f3296236761?w=500&auto=format&fit=crop&q=60'),


('MacBook Air M2','Apple lightweight laptop',114999,'Apple',4.9,8,6,
'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=500&auto=format&fit=crop&q=60'),

('ASUS Vivobook 16','Intel i7 productivity laptop',67999,'ASUS',4.7,15,6,
'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=500&auto=format&fit=crop&q=60'),

('Lenovo IdeaPad Gaming','Gaming laptop with RTX graphics',74999,'Lenovo',4.6,17,6,
'https://images.unsplash.com/photo-1515879218367-8466d910aaa4?w=500&auto=format&fit=crop&q=60'),

('HP Pavilion','Performance laptop for students',58999,'HP',4.5,20,6,
'https://images.unsplash.com/photo-1498050108023-c5249f4df085?w=500&auto=format&fit=crop&q=60'),

('Dell Inspiron','Everyday office laptop',54999,'Dell',4.4,18,6,
'https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=500&auto=format&fit=crop&q=60'),

('Acer Nitro 5','Mid-range gaming laptop',72999,'Acer',4.6,14,6,
'https://images.unsplash.com/photo-1593640408182-31c70c8268f5?w=500&auto=format&fit=crop&q=60'),

('MSI Katana','Gaming performance laptop',89999,'MSI',4.7,12,6,
'https://images.unsplash.com/photo-1587614382346-4ec70e388b28?w=500&auto=format&fit=crop&q=60'),

('Samsung Galaxy Book','Thin and portable laptop',64999,'Samsung',4.5,18,6,
'https://images.unsplash.com/photo-1541807084-5c52b6b3adef?w=500&auto=format&fit=crop&q=60'),

('LG Gram','Ultra lightweight laptop',99999,'LG',4.8,10,6,
'https://images.unsplash.com/photo-1496171367470-9ed9a91ea931?w=500&auto=format&fit=crop&q=60'),

('Surface Laptop','Premium productivity laptop',104999,'Microsoft',4.7,11,6,
'https://images.unsplash.com/photo-1516383607781-913a19294fd1?w=500&auto=format&fit=crop&q=60'),



('Running Shoes','Comfortable running shoes',3999,'Nike',4.7,20,7,
'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=500&auto=format&fit=crop&q=60'),

('Casual Sneakers','Stylish white sneakers',2999,'Adidas',4.5,25,7,
'https://images.unsplash.com/photo-1460353581641-37baddab0fa2?w=500&auto=format&fit=crop&q=60'),

('Formal Shoes','Leather office shoes',4999,'Bata',4.4,18,7,
'https://images.unsplash.com/photo-1549298916-b41d501d3772?w=500&auto=format&fit=crop&q=60'),

('Sports Shoes','Lightweight sports footwear',3499,'Puma',4.5,22,7,
'https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?w=500&auto=format&fit=crop&q=60'),

('Women Heels','Elegant high heels',2799,'Metro',4.3,24,7,
'https://images.unsplash.com/photo-1543163521-1bf539c55dd2?w=500&auto=format&fit=crop&q=60'),

('Sandals','Comfort casual sandals',1499,'Crocs',4.2,30,7,
'https://images.unsplash.com/photo-1608256246200-53e635b5b65f?w=500&auto=format&fit=crop&q=60'),

('Loafers','Premium casual loafers',3599,'Woodland',4.4,19,7,
'https://images.unsplash.com/photo-1543508282-6319a3e2621f?w=500&auto=format&fit=crop&q=60'),

('Flip Flops','Daily use flip flops',699,'Sparx',4.1,35,7,
'https://images.unsplash.com/photo-1503341455253-b2e723bb3dbb?w=500&auto=format&fit=crop&q=60'),

('Boots','Stylish leather boots',5999,'Timberland',4.6,16,7,
'https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?w=500&auto=format&fit=crop&q=60'),

('Canvas Shoes','Casual canvas shoes',1999,'Converse',4.5,23,7,
'https://images.unsplash.com/photo-1491553895911-0055eca6402d?w=500&auto=format&fit=crop&q=60');


INSERT INTO products
(product_name, description, price, brand, rating, discount_percentage, category_id, image_url)
VALUES

('Face Wash','Oil control face wash',299,'Nivea',4.4,15,8,
'https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=500&auto=format&fit=crop&q=60'),

('Moisturizer','Hydrating skin moisturizer',499,'Cetaphil',4.7,12,8,
'https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?w=500&auto=format&fit=crop&q=60'),

('Sunscreen SPF 50','UV protection sunscreen',699,'Minimalist',4.6,18,8,
'https://images.unsplash.com/photo-1596462502278-27bfdc403348?w=500&auto=format&fit=crop&q=60'),

('Perfume','Long lasting fragrance',2499,'Dior',4.8,10,8,
'https://images.unsplash.com/photo-1541643600914-78b084683601?w=500&auto=format&fit=crop&q=60'),

('Lipstick','Matte finish lipstick',799,'Maybelline',4.5,22,8,
'https://images.unsplash.com/photo-1586495777744-4413f21062fa?w=500&auto=format&fit=crop&q=60'),

('Hair Dryer','Professional hair dryer',1999,'Philips',4.4,20,8,
'https://images.unsplash.com/photo-1521590832167-7bcbfaa6381f?w=500&auto=format&fit=crop&q=60'),

('Shampoo','Anti dandruff shampoo',399,'Head & Shoulders',4.3,25,8,
'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=500&auto=format&fit=crop&q=60'),

('Face Serum','Vitamin C brightening serum',899,'The Ordinary',4.6,14,8,
'https://images.unsplash.com/photo-1556228453-efd6c1ff04f6?w=500&auto=format&fit=crop&q=60'),

('Body Lotion','Daily nourishing lotion',499,'Vaseline',4.4,18,8,
'https://images.unsplash.com/photo-1619451334792-150fd785ee74?w=500&auto=format&fit=crop&q=60'),

('Trimmer','Rechargeable beard trimmer',1799,'Mi',4.5,21,8,
'https://images.unsplash.com/photo-1621607512022-6aecc4fed814?w=500&auto=format&fit=crop&q=60'),



('Cricket Bat','Professional cricket bat',2999,'SG',4.6,18,9,
'https://images.unsplash.com/photo-1624880357913-a8539238245b?w=500&auto=format&fit=crop&q=60'),

('Football','FIFA quality football',1499,'Nike',4.5,22,9,
'https://images.unsplash.com/photo-1517466787929-bc90951d0974?w=500&auto=format&fit=crop&q=60'),

('Badminton Racket','Lightweight badminton racket',1999,'Yonex',4.7,15,9,
'https://images.unsplash.com/photo-1622279457486-62dcc4a431d6?w=500&auto=format&fit=crop&q=60'),

('Basketball','Indoor outdoor basketball',1299,'Spalding',4.5,20,9,
'https://images.unsplash.com/photo-1519861531473-9200262188bf?w=500&auto=format&fit=crop&q=60'),

('Tennis Kit','Complete tennis starter kit',4999,'Wilson',4.6,14,9,
'https://images.unsplash.com/photo-1542144582-1ba00456b5e3?w=500&auto=format&fit=crop&q=60'),

('Yoga Mat','Anti-slip yoga mat',999,'Boldfit',4.4,28,9,
'https://images.unsplash.com/photo-1518611012118-696072aa579a?w=500&auto=format&fit=crop&q=60'),

('Dumbbell Set','Adjustable dumbbell set',3499,'Kore',4.5,24,9,
'https://images.unsplash.com/photo-1517836357463-d25dfeac3438?w=500&auto=format&fit=crop&q=60'),

('Cycling Helmet','Safety cycling helmet',1599,'BTWIN',4.3,19,9,
'https://images.unsplash.com/photo-1517649763962-0c623066013b?w=500&auto=format&fit=crop&q=60'),

('Skipping Rope','Fitness skipping rope',399,'Strauss',4.2,35,9,
'https://images.pexels.com/photos/4498606/pexels-photo-4498606.jpeg?auto=compress&cs=tinysrgb&w=600'),

('Gym Bag','Large capacity gym bag',1499,'Puma',4.4,23,9,
'https://images.unsplash.com/photo-1514996937319-344454492b37?w=500&auto=format&fit=crop&q=60'),


('Leather Wallet','Premium leather wallet',1499,'Wildhorn',4.5,20,10,
'https://images.unsplash.com/photo-1627123424574-724758594e93?w=500&auto=format&fit=crop&q=60'),

('Backpack','Travel laptop backpack',2499,'Skybags',4.6,18,10,
'https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?w=500&auto=format&fit=crop&q=60'),

('Sunglasses','UV protection sunglasses',1999,'RayBan',4.7,15,10,
'https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=500&auto=format&fit=crop&q=60'),

('Smart Band','Fitness smart band',2999,'Mi',4.4,22,10,
'https://images.unsplash.com/photo-1575311373937-040b8e1fd5b6?w=500&auto=format&fit=crop&q=60'),

('Cap','Stylish casual cap',699,'Nike',4.2,30,10,
'https://images.unsplash.com/photo-1521369909029-2afed882baee?w=500&auto=format&fit=crop&q=60'),

('Travel Trolley','Hard case trolley bag',4999,'Safari',4.5,18,10,
'https://images.unsplash.com/photo-1527631746610-bca00a040d60?w=500&auto=format&fit=crop&q=60'),

('Laptop Sleeve','Protective laptop sleeve',999,'AmazonBasics',4.3,25,10,
'https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=500&auto=format&fit=crop&q=60'),

('Keychain','Metal premium keychain',299,'Urban Forest',4.1,35,10,
'https://images.unsplash.com/photo-1526045612212-70caf35c14df?w=500&auto=format&fit=crop&q=60'),

('Watch Strap','Silicone smartwatch strap',799,'Noise',4.3,28,10,
'https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500&auto=format&fit=crop&q=60'),

('Phone Case','Shockproof mobile case',499,'Spigen',4.5,26,10,
'https://images.unsplash.com/photo-1601593346740-925612772716?w=500&auto=format&fit=crop&q=60');


