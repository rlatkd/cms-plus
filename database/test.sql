use cmsplusmain;

-- Vendor 테이블
INSERT INTO vendor (deleted, created_datetime, vendor_phone, vendor_username, vendor_dept, vendor_name, vendor_email, vendor_password)
VALUES
    (0, NOW(), '01012345678', 'vendor1', '영업부', '김영희', 'vendor1@example.com', 'password1'),
    (0, NOW(), '01023456789', 'vendor2', '마케팅부', '이철수', 'vendor2@example.com', 'password2'),
    (0, NOW(), '01034567890', 'vendor3', '인사부', '박지영', 'vendor3@example.com', 'password3'),
    (0, NOW(), '01045678901', 'vendor4', '개발부', '최민수', 'vendor4@example.com', 'password4'),
    (0, NOW(), '01056789012', 'vendor5', '재무부', '정은지', 'vendor5@example.com', 'password5'),
    (0, NOW(), '01067890123', 'vendor6', '고객지원부', '강동원', 'vendor6@example.com', 'password6'),
    (0, NOW(), '01078901234', 'vendor7', '연구개발부', '송혜교', 'vendor7@example.com', 'password7'),
    (0, NOW(), '01089012345', 'vendor8', '품질관리부', '윤계상', 'vendor8@example.com', 'password8'),
    (0, NOW(), '01090123456', 'vendor9', '생산부', '한가인', 'vendor9@example.com', 'password9'),
    (0, NOW(), '01001234567', 'vendor10', '기획부', '조인성', 'vendor10@example.com', 'password10');

-- Member 테이블
INSERT INTO member (deleted, member_auto_billing, member_auto_invoice_send, member_enroll_date, created_datetime, vendor_id, member_phone, member_name, member_email, member_invoice_send_method, member_status)
VALUES
    (0, 1, 1, '2024-01-01', NOW(), 1, '01112345678', '학생1', 'student1@example.com', 'EMAIL', 'ENABLED'),
    (0, 1, 0, '2024-01-02', NOW(), 1, '01123456789', '학생2', 'student2@example.com', 'SMS', 'ENABLED'),
    (0, 0, 1, '2024-01-03', NOW(), 2, '01134567890', '학생3', 'student3@example.com', 'EMAIL', 'ENABLED'),
    (0, 1, 1, '2024-01-04', NOW(), 2, '01145678901', '학생4', 'student4@example.com', 'SMS', 'ENABLED'),
    (0, 0, 0, '2024-01-05', NOW(), 3, '01156789012', '학생5', 'student5@example.com', 'EMAIL', 'DISABLED'),
    (0, 1, 0, '2024-01-06', NOW(), 3, '01167890123', '학생6', 'student6@example.com', 'SMS', 'ENABLED'),
    (0, 0, 1, '2024-01-07', NOW(), 4, '01178901234', '학생7', 'student7@example.com', 'EMAIL', 'ENABLED'),
    (0, 1, 1, '2024-01-08', NOW(), 4, '01189012345', '학생8', 'student8@example.com', 'SMS', 'ENABLED'),
    (0, 0, 0, '2024-01-09', NOW(), 5, '01190123456', '학생9', 'student9@example.com', 'EMAIL', 'ENABLED'),
    (0, 1, 1, '2024-01-10', NOW(), 5, '01101234567', '학생10', 'student10@example.com', 'SMS', 'DISABLED');

-- Product 테이블
INSERT INTO product (deleted, product_price, created_datetime, vendor_id, product_name, product_status)
VALUES
    (0, 50000, NOW(), 1, '수학 기초', 'ENABLED'),
    (0, 60000, NOW(), 1, '영어 회화', 'ENABLED'),
    (0, 70000, NOW(), 2, '과학 실험', 'ENABLED'),
    (0, 55000, NOW(), 2, '국어 문법', 'ENABLED'),
    (0, 65000, NOW(), 3, '사회 탐구', 'ENABLED'),
    (0, 75000, NOW(), 3, '코딩 기초', 'ENABLED'),
    (0, 80000, NOW(), 4, '미술 실기', 'ENABLED'),
    (0, 45000, NOW(), 4, '음악 이론', 'DISABLED'),
    (0, 85000, NOW(), 5, '체육 특강', 'ENABLED'),
    (0, 90000, NOW(), 5, '논술 지도', 'ENABLED');

-- Payment 테이블
INSERT INTO payment (deleted, created_datetime, payment_type, payment_consent_status, payment_method, payment_status)
VALUES
    (0, NOW(), 'AUTO_PAYMENT-CARD', 'ACCEPT', 'CARD', 'ENABLED'),
    (0, NOW(), 'AUTO_PAYMENT-CMS', 'ACCEPT', 'CMS', 'ENABLED'),
    (0, NOW(), 'VIRTUAL_ACCOUNT_PAYMENT', 'ACCEPT', 'ACCOUNT', 'ENABLED'),
    (0, NOW(), 'BUYER_PAYMENT', 'WAIT', 'CARD', 'ENABLED'),
    (0, NOW(), 'AUTO_PAYMENT-CARD', 'ACCEPT', 'CARD', 'ENABLED'),
    (0, NOW(), 'AUTO_PAYMENT-CMS', 'ACCEPT', 'CMS', 'ENABLED'),
    (0, NOW(), 'VIRTUAL_ACCOUNT_PAYMENT', 'NONE', 'ACCOUNT', 'DISABLED'),
    (0, NOW(), 'BUYER_PAYMENT', 'ACCEPT', 'CMS', 'ENABLED'),
    (0, NOW(), 'AUTO_PAYMENT-CARD', 'WAIT', 'CARD', 'ENABLED'),
    (0, NOW(), 'AUTO_PAYMENT-CMS', 'ACCEPT', 'CMS', 'ENABLED');

-- Auto_payment 테이블
INSERT INTO auto_payment (auto_payment_simpconsent_request_date, payment_id, auto_payment_consent_img_url, auto_payment_sign_img_url)
VALUES
    (NOW(), 1, 'https://example.com/consent1.jpg', 'https://example.com/sign1.jpg'),
    (NOW(), 2, 'https://example.com/consent2.jpg', 'https://example.com/sign2.jpg'),
    (NOW(), 5, 'https://example.com/consent5.jpg', 'https://example.com/sign5.jpg'),
    (NOW(), 6, 'https://example.com/consent6.jpg', 'https://example.com/sign6.jpg'),
    (NOW(), 9, 'https://example.com/consent9.jpg', 'https://example.com/sign9.jpg'),
    (NOW(), 10, 'https://example.com/consent10.jpg', 'https://example.com/sign10.jpg');

-- Card_auto_payment 테이블
INSERT INTO card_auto_payment (card_auto_owner_birth, payment_id, card_auto_card_number, card_auto_card_owner)
VALUES
    ('1990-01-01', 1, '1234-5678-9012-3456', '김철수'),
    ('1985-05-05', 5, '2345-6789-0123-4567', '이영희'),
    ('1992-10-10', 9, '3456-7890-1234-5678', '박민수');

-- CMS_auto_payment 테이블
INSERT INTO cms_auto_payment (card_auto_owner_birth, cms_auto_account_bank, payment_id, cms_auto_account_number, cms_auto_account_owner)
VALUES
    ('1988-12-25', 0, 2, '123-456-789012', '정수연'),
    ('1995-07-07', 1, 6, '234-567-890123', '최영민'),
    ('1983-03-15', 0, 10, '345-678-901234', '강지영');

-- Contract 테이블
INSERT INTO contract (contract_day, contract_end_date, contract_start_date, deleted, contract_total_price, created_datetime, member_id, payment_id, vendor_id, contract_name, contract_status)
VALUES
    (15, '2024-12-31', '2024-01-01', 0, 500000, NOW(), 1, 1, 1, '1년 수강권', 'ENABLED'),
    (1, '2024-06-30', '2024-01-01', 0, 300000, NOW(), 2, 2, 1, '6개월 수강권', 'ENABLED'),
    (10, '2024-03-31', '2024-01-01', 0, 150000, NOW(), 3, 3, 2, '3개월 수강권', 'ENABLED'),
    (20, '2024-12-31', '2024-01-01', 0, 600000, NOW(), 4, 4, 2, '1년 종합반', 'ENABLED'),
    (5, '2024-06-30', '2024-01-01', 0, 250000, NOW(), 5, 5, 3, '6개월 기초반', 'DISABLED'),
    (25, '2024-12-31', '2024-01-01', 0, 700000, NOW(), 6, 6, 3, '1년 심화반', 'ENABLED'),
    (1, '2024-03-31', '2024-01-01', 0, 180000, NOW(), 7, 7, 4, '3개월 단과반', 'ENABLED'),
    (15, '2024-12-31', '2024-01-01', 0, 550000, NOW(), 8, 8, 4, '1년 특별반', 'ENABLED'),
    (10, '2024-06-30', '2024-01-01', 0, 320000, NOW(), 9, 9, 5, '6개월 집중반', 'ENABLED'),
    (20, '2024-12-31', '2024-01-01', 0, 650000, NOW(), 10, 10, 5, '1년 마스터반', 'DISABLED');

-- Billing_standard 테이블
INSERT INTO billing_standard (billing_standard_contract_date, deleted, contract_id, created_datetime, member_id, billing_standard_status, billing_standard_type)
VALUES
    ('2024-01-01', 0, 1, NOW(), 1, 'ENABLED', 'REGULAR'),
    ('2024-01-02', 0, 2, NOW(), 2, 'ENABLED', 'REGULAR'),
    ('2024-01-03', 0, 3, NOW(), 3, 'ENABLED', 'IRREGULAR'),
    ('2024-01-04', 0, 4, NOW(), 4, 'ENABLED', 'REGULAR'),
    ('2024-01-05', 0, 5, NOW(), 5, 'DISABLED', 'REGULAR'),
    ('2024-01-06', 0, 6, NOW(), 6, 'ENABLED', 'IRREGULAR'),
    ('2024-01-07', 0, 7, NOW(), 7, 'ENABLED', 'REGULAR'),
    ('2024-01-08', 0, 8, NOW(), 8, 'ENABLED', 'REGULAR'),
    ('2024-01-09', 0, 9, NOW(), 9, 'ENABLED', 'IRREGULAR'),
    ('2024-01-10', 0, 10, NOW(), 10, 'DISABLED', 'REGULAR');

-- Billing 테이블
INSERT INTO billing (billing_date, billing_end_date, billing_start_date, deleted, billing_standard_id, created_datetime, billing_status)
VALUES
    ('2024-02-01', '2024-02-29', '2024-02-01', 0, 1, NOW(), 'CREATED'),
    ('2024-02-01', '2024-02-29', '2024-02-01', 0, 2, NOW(), 'NON_PAID'),
    ('2024-02-01', '2024-02-29', '2024-02-01', 0, 3, NOW(), 'PAID'),
    ('2024-02-01', '2024-02-29', '2024-02-01', 0, 4, NOW(), 'WAITING_PAYMENT'),
    ('2024-02-01', '2024-02-29', '2024-02-01', 0, 5, NOW(), 'CREATED'),
    ('2024-02-01', '2024-02-29', '2024-02-01', 0, 6, NOW(), 'NON_PAID'),
    ('2024-02-01', '2024-02-29', '2024-02-01', 0, 7, NOW(), 'PAID'),
    ('2024-02-01', '2024-02-29', '2024-02-01', 0, 8, NOW(), 'WAITING_PAYMENT'),
    ('2024-02-01', '2024-02-29', '2024-02-01', 0, 9, NOW(), 'CREATED'),
    ('2024-02-01', '2024-02-29', '2024-02-01', 0, 10, NOW(), 'NON_PAID');

-- Billing_product 테이블
INSERT INTO billing_product (billing_prodcut_discount_price, billing_product_extra_price, billing_product_quantity, deleted, billing_id, created_datetime, product_id)
VALUES
    (5000, 0, 1, 0, 1, NOW(), 1),
    (0, 2000, 2, 0, 2, NOW(), 2),
    (3000, 0, 1, 0, 3, NOW(), 3),
    (0, 1000, 3, 0, 4, NOW(), 4),
    (2000, 500, 1, 0, 5, NOW(), 5),
    (1000, 0, 2, 0, 6, NOW(), 6),
    (0, 3000, 1, 0, 7, NOW(), 7),
    (4000, 0, 1, 0, 8, NOW(), 8),
    (0, 1500, 2, 0, 9, NOW(), 9),
    (2500, 1000, 1, 0, 10, NOW(), 10);

-- Buyer_payment 테이블
INSERT INTO buyer_payment (payment_id)
VALUES (4), (8);

-- Buyer_payment_method 테이블
INSERT INTO buyer_payment_method (buyer_payment_payment_id, buyer_payment_method)
VALUES
    (4, 'CARD'),
    (8, 'CMS');

-- Virtual_account_payment 테이블 (계속)
INSERT INTO virtual_account_payment (virtual_payment_bank_code, payment_id, virtual_payment_account_number, virtual_payment_account_owner)
VALUES
    (0, 3, '1234567890123', '김철수'),
    (1, 7, '2345678901234', '이영희');

-- Contract_product 테이블
INSERT INTO contract_product (contract_product_price, contract_product_quantity, deleted, contract_id, created_datetime, product_id, contract_product_name)
VALUES
    (50000, 1, 0, 1, NOW(), 1, '수학 기초'),
    (120000, 2, 0, 2, NOW(), 2, '영어 회화'),
    (70000, 1, 0, 3, NOW(), 3, '과학 실험'),
    (165000, 3, 0, 4, NOW(), 4, '국어 문법'),
    (65000, 1, 0, 5, NOW(), 5, '사회 탐구'),
    (150000, 2, 0, 6, NOW(), 6, '코딩 기초'),
    (80000, 1, 0, 7, NOW(), 7, '미술 실기'),
    (90000, 2, 0, 8, NOW(), 8, '음악 이론'),
    (85000, 1, 0, 9, NOW(), 9, '체육 특강'),
    (180000, 2, 0, 10, NOW(), 10, '논술 지도');

-- Setting_simpconsent 테이블
INSERT INTO setting_simpconsent (deleted, created_datetime)
VALUES
    (0, NOW()),
    (0, NOW()),
    (0, NOW()),
    (0, NOW()),
    (0, NOW()),
    (0, NOW()),
    (0, NOW()),
    (0, NOW()),
    (0, NOW()),
    (0, NOW());

-- Simpconsent_vendor_auto_payment_method 테이블
INSERT INTO simpconsent_vendor_auto_payment_method (simp_consent_setting_setting_simpconsent_id, simpconsent_auto_payment_method)
VALUES
    (1, 'CARD'),
    (1, 'ACCOUNT'),
    (2, 'CMS'),
    (3, 'CARD'),
    (3, 'ACCOUNT'),
    (4, 'CMS'),
    (5, 'CARD'),
    (6, 'ACCOUNT'),
    (7, 'CMS'),
    (8, 'CARD');

-- Simpconsent_vendor_product 테이블
INSERT INTO simpconsent_vendor_product (product_id, setting_simpconsent_id)
VALUES
    (1, 1),
    (2, 1),
    (3, 2),
    (4, 3),
    (5, 4),
    (6, 5),
    (7, 6),
    (8, 7),
    (9, 8),
    (10, 9);