use cmsplusmain;

INSERT INTO vendor (deleted, created_datetime, vendor_phone, vendor_username, vendor_dept, vendor_name, vendor_email, vendor_password) VALUES
                                                                                                                                           (0, '2024-07-08 10:00:00', '01012345678', 'vendor1', '영업부', '김철수', 'vendor1@example.com', 'password1'),
                                                                                                                                           (0, '2024-07-08 10:01:00', '01023456789', 'vendor2', '마케팅부', '이영희', 'vendor2@example.com', 'password2'),
                                                                                                                                           (0, '2024-07-08 10:02:00', '01034567890', 'vendor3', '인사부', '박민수', 'vendor3@example.com', 'password3'),
                                                                                                                                           (0, '2024-07-08 10:03:00', '01045678901', 'vendor4', '개발부', '정지원', 'vendor4@example.com', 'password4'),
                                                                                                                                           (0, '2024-07-08 10:04:00', '01056789012', 'vendor5', '고객지원부', '최유리', 'vendor5@example.com', 'password5'),
                                                                                                                                           (0, '2024-07-08 10:05:00', '01067890123', 'vendor6', '재무부', '강태풍', 'vendor6@example.com', 'password6'),
                                                                                                                                           (0, '2024-07-08 10:06:00', '01078901234', 'vendor7', '기획부', '손미래', 'vendor7@example.com', 'password7'),
                                                                                                                                           (0, '2024-07-08 10:07:00', '01089012345', 'vendor8', '연구개발부', '윤별', 'vendor8@example.com', 'password8'),
                                                                                                                                           (0, '2024-07-08 10:08:00', '01090123456', 'vendor9', '디자인부', '장미화', 'vendor9@example.com', 'password9'),
                                                                                                                                           (0, '2024-07-08 10:09:00', '01001234567', 'vendor10', '총무부', '한동석', 'vendor10@example.com', 'password10');

INSERT INTO member (deleted, member_auto_billing, member_auto_invoice_send, member_enroll_date, created_datetime, vendor_id, member_phone, member_name, member_email, member_invoice_send_method, member_status) VALUES
                                                                                                                                                                                                                     (0, 1, 1, '2024-07-01', '2024-07-08 11:00:00', 1, '01112345678', '학생1', 'student1@example.com', 'EMAIL', 'ENABLED'),
                                                                                                                                                                                                                     (0, 1, 0, '2024-07-02', '2024-07-08 11:01:00', 2, '01123456789', '학생2', 'student2@example.com', 'SMS', 'ENABLED'),
                                                                                                                                                                                                                     (0, 0, 1, '2024-07-03', '2024-07-08 11:02:00', 3, '01134567890', '학생3', 'student3@example.com', 'EMAIL', 'ENABLED'),
                                                                                                                                                                                                                     (0, 1, 1, '2024-07-04', '2024-07-08 11:03:00', 4, '01145678901', '학생4', 'student4@example.com', 'SMS', 'ENABLED'),
                                                                                                                                                                                                                     (0, 0, 0, '2024-07-05', '2024-07-08 11:04:00', 5, '01156789012', '학생5', 'student5@example.com', 'EMAIL', 'DISABLED'),
                                                                                                                                                                                                                     (0, 1, 0, '2024-07-06', '2024-07-08 11:05:00', 6, '01167890123', '학생6', 'student6@example.com', 'SMS', 'ENABLED'),
                                                                                                                                                                                                                     (0, 0, 1, '2024-07-07', '2024-07-08 11:06:00', 7, '01178901234', '학생7', 'student7@example.com', 'EMAIL', 'ENABLED'),
                                                                                                                                                                                                                     (0, 1, 1, '2024-07-08', '2024-07-08 11:07:00', 8, '01189012345', '학생8', 'student8@example.com', 'SMS', 'ENABLED'),
                                                                                                                                                                                                                     (0, 0, 0, '2024-07-09', '2024-07-08 11:08:00', 9, '01190123456', '학생9', 'student9@example.com', 'EMAIL', 'DISABLED'),
                                                                                                                                                                                                                     (0, 1, 1, '2024-07-10', '2024-07-08 11:09:00', 10, '01101234567', '학생10', 'student10@example.com', 'SMS', 'ENABLED');

INSERT INTO payment (deleted, created_datetime, payment_type, payment_consent_status, payment_status) VALUES
                                                                                                          (0, '2024-07-08 12:00:00', 'AUTO', 'ACCEPT', 'ENABLED'),
                                                                                                          (0, '2024-07-08 12:01:00', 'VIRTUAL_ACCOUNT', 'NONE', 'ENABLED'),
                                                                                                          (0, '2024-07-08 12:02:00', 'BUYER', 'NOT_USED', 'ENABLED'),
                                                                                                          (0, '2024-07-08 12:03:00', 'AUTO', 'WAIT', 'ENABLED'),
                                                                                                          (0, '2024-07-08 12:04:00', 'VIRTUAL_ACCOUNT', 'NONE', 'DISABLED'),
                                                                                                          (0, '2024-07-08 12:05:00', 'BUYER', 'NOT_USED', 'ENABLED'),
                                                                                                          (0, '2024-07-08 12:06:00', 'AUTO', 'ACCEPT', 'ENABLED'),
                                                                                                          (0, '2024-07-08 12:07:00', 'VIRTUAL_ACCOUNT', 'NONE', 'ENABLED'),
                                                                                                          (0, '2024-07-08 12:08:00', 'BUYER', 'NOT_USED', 'DISABLED'),
                                                                                                          (0, '2024-07-08 12:09:00', 'AUTO', 'WAIT', 'ENABLED');

INSERT INTO contract (contract_day, contract_end_date, contract_start_date, deleted, contract_total_price, created_datetime, member_id, payment_id, vendor_id, contract_name, contract_status) VALUES
                                                                                                                                                                                                   (15, '2025-07-14', '2024-07-15', 0, 1000000, '2024-07-08 13:00:00', 1, 1, 1, '1년 계약', 'ENABLED'),
                                                                                                                                                                                                   (1, '2025-01-31', '2024-08-01', 0, 500000, '2024-07-08 13:01:00', 2, 2, 1, '6개월 계약', 'ENABLED'),
                                                                                                                                                                                                   (10, '2024-12-09', '2024-09-10', 0, 300000, '2024-07-08 13:02:00', 3, 3, 1, '3개월 계약', 'ENABLED'),
                                                                                                                                                                                                   (20, '2025-07-19', '2024-07-20', 0, 1200000, '2024-07-08 13:03:00', 4, 4, 1, '1년 특별 계약', 'ENABLED'),
                                                                                                                                                                                                   (5, '2024-10-04', '2024-08-05', 0, 200000, '2024-07-08 13:04:00', 5, 5, 5, '2개월 단기 계약', 'DISABLED'),
                                                                                                                                                                                                   (1, '2025-07-31', '2024-08-01', 0, 1500000, '2024-07-08 13:05:00', 6, 6, 6, '1년 프리미엄 계약', 'ENABLED'),
                                                                                                                                                                                                   (15, '2025-01-14', '2024-07-15', 0, 600000, '2024-07-08 13:06:00', 7, 7, 7, '6개월 집중 계약', 'ENABLED'),
                                                                                                                                                                                                   (25, '2024-10-24', '2024-07-25', 0, 250000, '2024-07-08 13:07:00', 8, 8, 8, '3개월 특별 계약', 'ENABLED'),
                                                                                                                                                                                                   (1, '2025-07-31', '2024-08-01', 0, 1800000, '2024-07-08 13:08:00', 9, 9, 9, '1년 VIP 계약', 'DISABLED'),
                                                                                                                                                                                                   (10, '2024-12-09', '2024-09-10', 0, 400000, '2024-07-08 13:09:00', 10, 10, 10, '3개월 집중 계약', 'ENABLED');

INSERT INTO product (deleted, product_price, created_datetime, vendor_id, product_name, product_status) VALUES
                                                                                                            (0, 100000, '2024-07-08 14:00:00', 1, '기초 영어', 'ENABLED'),
                                                                                                            (0, 150000, '2024-07-08 14:01:00', 2, '중급 수학', 'ENABLED'),
                                                                                                            (0, 200000, '2024-07-08 14:02:00', 3, '고급 과학', 'ENABLED'),
                                                                                                            (0, 80000, '2024-07-08 14:03:00', 4, '초급 프로그래밍', 'ENABLED'),
                                                                                                            (0, 120000, '2024-07-08 14:04:00', 5, '중급 일본어', 'DISABLED'),
                                                                                                            (0, 180000, '2024-07-08 14:05:00', 6, '고급 중국어', 'ENABLED'),
                                                                                                            (0, 90000, '2024-07-08 14:06:00', 7, '기초 역사', 'ENABLED'),
                                                                                                            (0, 130000, '2024-07-08 14:07:00', 8, '중급 물리', 'ENABLED'),
                                                                                                            (0, 160000, '2024-07-08 14:08:00', 9, '고급 화학', 'DISABLED'),
                                                                                                            (0, 110000, '2024-07-08 14:09:00', 10, '중급 생물', 'ENABLED');

INSERT INTO billing_standard (billing_standard_contract_date, deleted, contract_id, created_datetime, member_id, billing_standard_status, billing_standard_type) VALUES
                                                                                                                                                                     ('2024-07-15', 0, 1, '2024-07-08 15:00:00', 1, 'ENABLED', 'REGULAR'),
                                                                                                                                                                     ('2024-08-01', 0, 2, '2024-07-08 15:01:00', 2, 'ENABLED', 'REGULAR'),
                                                                                                                                                                     ('2024-09-10', 0, 3, '2024-07-08 15:02:00', 3, 'ENABLED', 'IRREGULAR'),
                                                                                                                                                                     ('2024-07-20', 0, 4, '2024-07-08 15:03:00', 4, 'ENABLED', 'REGULAR'),
                                                                                                                                                                     ('2024-08-05', 0, 5, '2024-07-08 15:04:00', 5, 'DISABLED', 'REGULAR'),
                                                                                                                                                                     ('2024-08-01', 0, 6, '2024-07-08 15:05:00', 6, 'ENABLED', 'REGULAR'),
                                                                                                                                                                     ('2024-07-15', 0, 7, '2024-07-08 15:06:00', 7, 'ENABLED', 'IRREGULAR'),
                                                                                                                                                                     ('2024-07-25', 0, 8, '2024-07-08 15:07:00', 8, 'ENABLED', 'REGULAR'),
                                                                                                                                                                     ('2024-08-01', 0, 9, '2024-07-08 15:08:00', 9, 'DISABLED', 'REGULAR'),
                                                                                                                                                                     ('2024-09-10', 0, 10, '2024-07-08 15:09:00', 10, 'ENABLED', 'IRREGULAR');

INSERT INTO billing (billing_date, billing_end_date, billing_start_date, deleted, billing_standard_id, created_datetime, billing_memo, billing_status) VALUES
                                                                                                                                                           ('2024-08-01', '2024-08-31', '2024-08-01', 0, 1, '2024-07-08 16:00:00', '8월 정기 청구', 'CREATED'),
                                                                                                                                                           ('2024-08-01', '2024-08-31', '2024-08-01', 0, 2, '2024-07-08 16:01:00', '8월 정기 청구', 'NON_PAID'),
                                                                                                                                                           ('2024-09-01', '2024-09-30', '2024-09-01', 0, 3, '2024-07-08 16:02:00', '9월 추가 청구', 'PAID'),
                                                                                                                                                           ('2024-08-01', '2024-08-31', '2024-08-01', 0, 4, '2024-07-08 16:03:00', '8월 정기 청구', 'WAITING_PAYMENT'),
                                                                                                                                                           ('2024-08-01', '2024-08-31', '2024-08-01', 0, 5, '2024-07-08 16:04:00', '8월 정기 청구', 'CREATED'),
                                                                                                                                                           ('2024-08-01', '2024-08-31', '2024-08-01', 0, 6, '2024-07-08 16:05:00', '8월 정기 청구', 'NON_PAID'),
                                                                                                                                                           ('2024-08-01', '2024-08-31', '2024-08-01', 0, 7, '2024-07-08 16:06:00', '8월 추가 청구', 'PAID'),
                                                                                                                                                           ('2024-08-01', '2024-08-31', '2024-08-01', 0, 8, '2024-07-08 16:07:00', '8월 정기 청구', 'WAITING_PAYMENT'),
                                                                                                                                                           ('2024-08-01', '2024-08-31', '2024-08-01', 0, 9, '2024-07-08 16:08:00', '8월 정기 청구', 'CREATED'),
                                                                                                                                                           ('2024-09-01', '2024-09-30', '2024-09-01', 0, 10, '2024-07-08 16:09:00', '9월 추가 청구', 'NON_PAID');

INSERT INTO contract_product (contract_product_price, contract_product_quantity, deleted, contract_id, created_datetime, product_id, contract_product_name) VALUES
                                                                                                                                                                (100000, 1, 0, 1, '2024-07-08 17:00:00', 1, '기초 영어'),
                                                                                                                                                                (150000, 1, 0, 2, '2024-07-08 17:01:00', 2, '중급 수학'),
                                                                                                                                                                (200000, 1, 0, 3, '2024-07-08 17:02:00', 3, '고급 과학'),
                                                                                                                                                                (80000, 2, 0, 4, '2024-07-08 17:03:00', 4, '초급 프로그래밍'),
                                                                                                                                                                (120000, 1, 0, 5, '2024-07-08 17:04:00', 5, '중급 일본어'),
                                                                                                                                                                (180000, 1, 0, 6, '2024-07-08 17:05:00', 6, '고급 중국어'),
                                                                                                                                                                (90000, 2, 0, 7, '2024-07-08 17:06:00', 7, '기초 역사'),
                                                                                                                                                                (130000, 1, 0, 8, '2024-07-08 17:07:00', 8, '중급 물리'),
                                                                                                                                                                (160000, 1, 0, 9, '2024-07-08 17:08:00', 9, '고급 화학'),
                                                                                                                                                                (110000, 2, 0, 10, '2024-07-08 17:09:00', 10, '중급 생물');

INSERT INTO billing_product (billing_prodcut_discount_price, billing_product_extra_price, billing_product_quantity, deleted, billing_id, created_datetime, product_id) VALUES
                                                                                                                                                                           (0, 0, 1, 0, 1, '2024-07-08 18:00:00', 1),
                                                                                                                                                                           (5000, 0, 1, 0, 2, '2024-07-08 18:01:00', 2),
                                                                                                                                                                           (0, 10000, 1, 0, 3, '2024-07-08 18:02:00', 3),
                                                                                                                                                                           (0, 0, 2, 0, 4, '2024-07-08 18:03:00', 4),
                                                                                                                                                                           (10000, 0, 1, 0, 5, '2024-07-08 18:04:00', 5),
                                                                                                                                                                           (0, 5000, 1, 0, 6, '2024-07-08 18:05:00', 6),
                                                                                                                                                                           (0, 0, 2, 0, 7, '2024-07-08 18:06:00', 7),
                                                                                                                                                                           (5000, 0, 1, 0, 8, '2024-07-08 18:07:00', 8),
                                                                                                                                                                           (0, 10000, 1, 0, 9, '2024-07-08 18:08:00', 9),
                                                                                                                                                                           (0, 0, 2, 0, 10, '2024-07-08 18:09:00', 10);

INSERT INTO payment_method_info (deleted, created_datetime, payment_method_info_id, payment_method_info_method) VALUES
                                                                                                                    (0, '2024-07-08 19:00:00', 1, 'CARD'),
                                                                                                                    (0, '2024-07-08 19:01:00', 2, 'ACCOUNT'),
                                                                                                                    (0, '2024-07-08 19:02:00', 3, 'CMS'),
                                                                                                                    (0, '2024-07-08 19:03:00', 4, 'CARD'),
                                                                                                                    (0, '2024-07-08 19:04:00', 5, 'ACCOUNT'),
                                                                                                                    (0, '2024-07-08 19:05:00', 6, 'CMS'),
                                                                                                                    (0, '2024-07-08 19:06:00', 7, 'CARD'),
                                                                                                                    (0, '2024-07-08 19:07:00', 8, 'ACCOUNT'),
                                                                                                                    (0, '2024-07-08 19:08:00', 9, 'CMS'),
                                                                                                                    (0, '2024-07-08 19:09:00', 10, 'CARD');

INSERT INTO auto_payment (payment_id, payment_method_info_id, payment_simpconsent_request_date, payment_consent_img_url, payment_sign_img_url) VALUES
                                                                                                                                                   (1, 1, '2024-07-08 20:00:00', 'https://example.com/consent1.jpg', 'https://example.com/sign1.jpg'),
                                                                                                                                                   (4, 4, '2024-07-08 20:03:00', 'https://example.com/consent4.jpg', 'https://example.com/sign4.jpg'),
                                                                                                                                                   (7, 7, '2024-07-08 20:06:00', 'https://example.com/consent7.jpg', 'https://example.com/sign7.jpg'),
                                                                                                                                                   (10, 10, '2024-07-08 20:09:00', 'https://example.com/consent10.jpg', 'https://example.com/sign10.jpg');

INSERT INTO virtual_account_payment (payment_id, virtual_payment_account_number, virtual_payment_bank_code, virtual_payment_account_owner) VALUES
                                                                                                                                               (2, '1234567890123', 'KB', '김철수'),
                                                                                                                                               (5, '2345678901234', 'SH', '이영희'),
                                                                                                                                               (8, '3456789012345', 'WR', '박민수');

INSERT INTO buyer_payment (payment_id) VALUES
                                           (3),
                                           (6),
                                           (9);

INSERT INTO buyer_payment_method (buyer_payment_payment_id, buyer_payment_method) VALUES
                                                                                      (3, 'CARD'),
                                                                                      (3, 'ACCOUNT'),
                                                                                      (6, 'CMS'),
                                                                                      (6, 'CARD'),
                                                                                      (9, 'ACCOUNT');

INSERT INTO card_payment (card_info_owner_birth, payment_method_info_id, card_info_number, card_info_owner) VALUES
                                                                                                                ('1990-01-01', 1, '1234-5678-9012-3456', '김철수'),
                                                                                                                ('1985-05-05', 4, '2345-6789-0123-4567', '이영희'),
                                                                                                                ('1988-08-08', 7, '3456-7890-1234-5678', '박민수'),
                                                                                                                ('1992-12-12', 10, '4567-8901-2345-6789', '정지원');

INSERT INTO cms_payment (cms_owner_birth, payment_method_info_id, cms_account_number, cms_account_owner, cms_account_bank) VALUES
                                                                                                                               ('1991-03-03', 3, '123456789012345', '최유리', 'KB'),
                                                                                                                               ('1987-07-07', 6, '234567890123456', '강태풍', 'SH'),
                                                                                                                               ('1993-09-09', 9, '345678901234567', '손미래', 'WR');