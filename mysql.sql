-- 회원 마스터 테이블 (user_b)
CREATE TABLE user_b (
    user_id VARCHAR(255) DEFAULT (UUID()) NOT NULL PRIMARY KEY COMMENT '회원 ID (유니크, 자동 생성 UUID)',
    password VARCHAR(255) NOT NULL COMMENT '비밀번호 (암호화)',
    name VARCHAR(255) NOT NULL COMMENT '이름',
    email VARCHAR(255) UNIQUE NOT NULL COMMENT '이메일 (유니크)',
    phone_number VARCHAR(255) COMMENT '전화번호',
    address VARCHAR(255) COMMENT '주소',
    registration_date VARCHAR(255) COMMENT '가입 일시 (%Y-%m-%d %H:%M:%S)',
    last_login_date VARCHAR(255) COMMENT '최근 로그인 일시 (%Y-%m-%d %H:%M:%S)',
    encrypted_field_1 VARCHAR(255) COMMENT '암호화된 필드 1',
    encrypted_field_2 VARCHAR(255) COMMENT '암호화된 필드 2',
    encrypted_field_3 VARCHAR(255) COMMENT '암호화된 필드 3',
    created_at VARCHAR(255) DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '생성 일시 (YYYYMMDDHH24MISS)',
    updated_at VARCHAR(255) COMMENT '수정 일시 (%Y-%m-%d %H:%M:%S)'
) COMMENT '회원 정보를 관리하는 마스터 테이블';

-- 페이지 뷰 로그 테이블 (page_view_l)
CREATE TABLE page_view_l (
    log_id VARCHAR(255) DEFAULT (UUID()) NOT NULL PRIMARY KEY COMMENT '로그 고유 ID (자동 생성 UUID)',
    access_timestamp VARCHAR(255) COMMENT '접근 시간 (%Y-%m-%d %H:%M:%S)',
    user_id VARCHAR(255) NOT NULL COMMENT '회원 ID (user_b 테이블 참조)',
    page VARCHAR(255) COMMENT '페이지 URL 또는 식별자',
    created_at VARCHAR(255) DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '생성 일시 (YYYYMMDDHH24MISS)',
    FOREIGN KEY (user_id) REFERENCES user_b(user_id)
) COMMENT '회원의 페이지 뷰 이력을 기록하는 로그 테이블';

-- 브랜드 마스터 테이블 (brand_b)
CREATE TABLE brand_b (
    brand_id VARCHAR(255) DEFAULT (UUID()) PRIMARY KEY COMMENT '브랜드 고유 ID (기본 키, 자동 생성 UUID)',
    brand_name VARCHAR(255) UNIQUE NOT NULL COMMENT '브랜드명',
    shipping_cost VARCHAR(255) COMMENT '브랜드별 배송비',
    description VARCHAR(4000) COMMENT '브랜드 상세 설명',
    created_at VARCHAR(255) DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '생성 일시 (YYYYMMDDHH24MISS)',
    updated_at VARCHAR(255) COMMENT '수정 일시 (%Y-%m-%d %H:%M:%S)'
) COMMENT '상품 브랜드를 관리하는 마스터 테이블';

-- 상품 마스터 테이블 (product_b)
CREATE TABLE product_b (
    product_id VARCHAR(255) DEFAULT (UUID()) PRIMARY KEY COMMENT '상품 고유 ID (기본 키, 자동 생성 UUID)',
    product_code VARCHAR(255) NOT NULL UNIQUE COMMENT '상품 코드 (필수, 유니크)',
    product_name VARCHAR(255) COMMENT '상품명',
    brand_id VARCHAR(255) NOT NULL COMMENT '브랜드 ID (brand_b 테이블 참조)',
    category VARCHAR(255) COMMENT '상품 종류',
    color VARCHAR(255) COMMENT '상품 색상',
    price VARCHAR(255) NOT NULL COMMENT '가격',
    description VARCHAR(4000) COMMENT '상품 상세 설명',
    created_at VARCHAR(255) DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '생성 일시 (YYYYMMDDHH24MISS)',
    updated_at VARCHAR(255) COMMENT '수정 일시 (%Y-%m-%d %H:%M:%S)',
    FOREIGN KEY (brand_id) REFERENCES brand_b(brand_id)
) COMMENT '상품의 기본적인 정보를 관리하는 마스터 테이블';

-- 상품 옵션 상세 테이블 (product_option_d)
CREATE TABLE product_option_d (
    product_option_id VARCHAR(255) DEFAULT (UUID()) NOT NULL PRIMARY KEY COMMENT '상품 옵션 고유 ID (자동 생성 UUID)',
    product_id VARCHAR(255) NOT NULL COMMENT '상품 ID (product_b 테이블 참조)',
    option_name VARCHAR(255) COMMENT '옵션명 (예: 재질)',
    option_value VARCHAR(255) COMMENT '옵션 값 (예: 면)',
    additional_price VARCHAR(255) COMMENT '옵션 추가 가격',
    created_at VARCHAR(255) DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '생성 일시 (YYYYMMDDHH24MISS)',
    updated_at VARCHAR(255) COMMENT '수정 일시 (%Y-%m-%d %H:%M:%S)',
    FOREIGN KEY (product_id) REFERENCES product_b(product_id),
    UNIQUE (product_id, option_name, option_value)
) COMMENT '상품의 옵션 정보를 관리하는 상세 테이블';

-- 상품 재고 상세 테이블 (product_stock_d)
CREATE TABLE product_stock_d (
    product_stock_id VARCHAR(255) DEFAULT (UUID()) NOT NULL PRIMARY KEY COMMENT '상품 재고 고유 ID (자동 생성 UUID)',
    product_id VARCHAR(255) NOT NULL COMMENT '상품 ID (product_b 테이블 참조)',
    product_option_id VARCHAR(255) COMMENT '상품 옵션 고유 ID (product_option_d 테이블 참조)',
    product_size VARCHAR(255) NOT NULL COMMENT '사이즈',
    qty VARCHAR(255) COMMENT '재고 수량',
    created_at VARCHAR(255) DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '생성 일시 (YYYYMMDDHH24MISS)',
    updated_at VARCHAR(255) COMMENT '수정 일시 (%Y-%m-%d %H:%M:%S)',
    FOREIGN KEY (product_id) REFERENCES product_b(product_id),
    FOREIGN KEY (product_option_id) REFERENCES product_option_d(product_option_id),
    UNIQUE (product_id, product_option_id, product_size)
) COMMENT '상품의 재고 정보를 관리하는 상세 테이블 (사이즈 정보 통합)';

-- 주문 마스터 테이블 (order_b)
CREATE TABLE order_b (
    order_id VARCHAR(255) DEFAULT (UUID()) PRIMARY KEY COMMENT '주문 고유 ID (기본 키, 자동 생성 UUID)',
    user_id VARCHAR(255) NOT NULL COMMENT '회원 ID (user_b 테이블 참조)',
    delivery_address VARCHAR(255) NOT NULL COMMENT '배송지',
    order_status VARCHAR(255) NOT NULL COMMENT '주문 상태',
    order_amount VARCHAR(255) NOT NULL COMMENT '주문 금액',
    discount_amount VARCHAR(255) NOT NULL COMMENT '할인 금액',
    shipping_cost VARCHAR(255) NOT NULL COMMENT '배송비',
    order_date VARCHAR(255) COMMENT '주문 일시 (%Y-%m-%d %H:%M:%S)',
    payment_method VARCHAR(255) COMMENT '결제 수단',
    transaction_id VARCHAR(255) UNIQUE COMMENT '결제 트랜잭션 ID',
    created_at VARCHAR(255) DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '생성 일시 (YYYYMMDDHH24MISS)',
    updated_at VARCHAR(255) COMMENT '수정 일시 (%Y-%m-%d %H:%M:%S)',
    FOREIGN KEY (user_id) REFERENCES user_b(user_id)
) COMMENT '주문 정보를 관리하는 마스터 테이블';

-- 주문 상품 상세 테이블 (order_item_d)
CREATE TABLE order_item_d (
    order_item_id VARCHAR(255) DEFAULT (UUID()) NOT NULL PRIMARY KEY, -- 주문 상품 상세 고유 ID, 필수 (자동 생성 UUID)
    order_id VARCHAR(255) NOT NULL, -- 주문 ID (order_b 테이블 참조), 필수
    product_id VARCHAR(255) NOT NULL, -- 상품 ID (product_b 테이블 참조), 필수
    product_stock_id VARCHAR(255) NOT NULL, -- 상품 재고 ID (product_stock_d 테이블 참조), 필수
    qty VARCHAR(255), -- 주문 수량
    product_price VARCHAR(255), -- 상품 가격
    created_at VARCHAR(14) DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'), -- 생성 일시 (YYYY-MM-DD HH24:MI:SS), 기본값: 현재 시간
    updated_at VARCHAR(255), -- 수정 일시 (%Y-%m-%d %H:%M:%S)
    CONSTRAINT fk_order_item_order_id FOREIGN KEY (order_id) REFERENCES order_b(order_id),
    CONSTRAINT fk_order_item_product_id FOREIGN KEY (product_id) REFERENCES product_b(product_id),
    CONSTRAINT fk_order_item_stock_id FOREIGN KEY (product_stock_id) REFERENCES product_stock_d(product_stock_id),
    CONSTRAINT unique_order_item UNIQUE (order_id, product_stock_id)
);