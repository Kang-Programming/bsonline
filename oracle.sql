-- 회원 마스터 테이블 (user_b): 회원 정보를 관리합니다.
CREATE TABLE user_b (
    user_id VARCHAR2(255) DEFAULT SYS_GUID() NOT NULL PRIMARY KEY, -- 회원 ID (유니크), 필수 (자동 생성 UUID)
    password VARCHAR2(255) NOT NULL, -- 비밀번호 (암호화), 필수
    name VARCHAR2(255) NOT NULL, -- 이름, 필수
    email VARCHAR2(255) UNIQUE NOT NULL, -- 이메일 (유니크), 필수
    phone_number VARCHAR2(255), -- 전화번호
    address VARCHAR2(255), -- 주소
    registration_date VARCHAR2(255), -- 가입 일시 (%Y-%m-%d %H:%M:%S)
    last_login_date VARCHAR2(255), -- 최근 로그인 일시 (%Y-%m-%d %H:%M:%S)
    encrypted_field_1 VARCHAR2(255), -- 암호화된 필드 1
    encrypted_field_2 VARCHAR2(255), -- 암호화된 필드 2
    encrypted_field_3 VARCHAR2(255), -- 암호화된 필드 3
    created_at VARCHAR2(255) DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), -- 생성 일시 (YYYYMMDDHH24MISS), 기본값: 현재 시간
    updated_at VARCHAR2(255)  -- 수정 일시 (%Y-%m-%d %H:%M:%S)
);
COMMENT ON TABLE user_b IS '회원 정보를 관리하는 마스터 테이블';
COMMENT ON COLUMN user_b.user_id IS '회원 ID (유니크, 자동 생성 UUID)';
COMMENT ON COLUMN user_b.password IS '비밀번호 (암호화)';
COMMENT ON COLUMN user_b.name IS '이름';
COMMENT ON COLUMN user_b.email IS '이메일 (유니크)';
COMMENT ON COLUMN user_b.phone_number IS '전화번호';
COMMENT ON COLUMN user_b.address IS '주소';
COMMENT ON COLUMN user_b.registration_date IS '가입 일시 (%Y-%m-%d %H:%M:%S)';
COMMENT ON COLUMN user_b.last_login_date IS '최근 로그인 일시 (%Y-%m-%d %H:%M:%S)';
COMMENT ON COLUMN user_b.encrypted_field_1 IS '암호화된 필드 1';
COMMENT ON COLUMN user_b.encrypted_field_2 IS '암호화된 필드 2';
COMMENT ON COLUMN user_b.encrypted_field_3 IS '암호화된 필드 3';
COMMENT ON COLUMN user_b.created_at IS '생성 일시 (YYYYMMDDHH24MISS)';
COMMENT ON COLUMN user_b.updated_at IS '수정 일시 (%Y-%m-%d %H:%M:%S)';

-- 페이지 뷰 로그 테이블 (page_view_l): 회원의 페이지 뷰 이력을 기록합니다.
CREATE TABLE page_view_l (
    log_id VARCHAR2(255) DEFAULT SYS_GUID() NOT NULL PRIMARY KEY, -- 로그 고유 ID, 필수 (자동 생성 UUID)
    access_timestamp VARCHAR2(255), -- 접근 시간 (%Y-%m-%d %H:%M:%S)
    user_id VARCHAR2(255) NOT NULL, -- 회원 ID (user_b 테이블 참조), 필수
    page VARCHAR2(255), -- 페이지 URL 또는 식별자
    created_at VARCHAR2(255) DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), -- 생성 일시 (YYYYMMDDHH24MISS), 기본값: 현재 시간
    CONSTRAINT fk_page_view_user_id FOREIGN KEY (user_id) REFERENCES user_b(user_id)
);
COMMENT ON TABLE page_view_l IS '회원의 페이지 뷰 이력을 기록하는 로그 테이블';
COMMENT ON COLUMN page_view_l.log_id IS '로그 고유 ID (자동 생성 UUID)';
COMMENT ON COLUMN page_view_l.access_timestamp IS '접근 시간 (%Y-%m-%d %H:%M:%S)';
COMMENT ON COLUMN page_view_l.user_id IS '회원 ID (user_b 테이블 참조)';
COMMENT ON COLUMN page_view_l.page IS '페이지 URL 또는 식별자';
COMMENT ON COLUMN page_view_l.created_at IS '생성 일시 (YYYYMMDDHH24MISS)';

-- 브랜드 마스터 테이블 (brand_b): 상품 브랜드를 관리합니다.
CREATE TABLE brand_b (
    brand_id VARCHAR2(255) DEFAULT SYS_GUID() PRIMARY KEY, -- 브랜드 고유 ID, 필수 (기본 키, 자동 생성 UUID)
    brand_name VARCHAR2(255) UNIQUE NOT NULL, -- 브랜드명, 필수
    shipping_cost VARCHAR2(255), -- 브랜드별 배송비
    description VARCHAR2(4000), -- 브랜드 상세 설명
    created_at VARCHAR2(255) DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), -- 생성 일시 (YYYY-MM-DD HH24:MI:SS), 기본값: 현재 시간
    updated_at VARCHAR2(255)  -- 수정 일시 (%Y-%m-%d %H:%M:%S)
);
COMMENT ON TABLE brand_b IS '상품 브랜드를 관리하는 마스터 테이블';
COMMENT ON COLUMN brand_b.brand_id IS '브랜드 고유 ID (기본 키, 자동 생성 UUID)';
COMMENT ON COLUMN brand_b.brand_name IS '브랜드명';
COMMENT ON COLUMN brand_b.shipping_cost IS '브랜드별 배송비';
COMMENT ON COLUMN brand_b.description IS '브랜드 상세 설명';
COMMENT ON COLUMN brand_b.created_at IS '생성 일시 (YYYY-MM-DD HH24:MI:SS)';
COMMENT ON COLUMN brand_b.updated_at IS '수정 일시 (%Y-%m-%d %H:%M:%S)';

-- 상품 마스터 테이블 (product_b)
CREATE TABLE product_b (
    product_id VARCHAR2(255) DEFAULT SYS_GUID() PRIMARY KEY, -- 상품 고유 ID, 필수 (기본 키, 자동 생성 UUID)
    product_code VARCHAR2(255) NOT NULL UNIQUE, -- 상품 코드, 필수, 유니크
    product_name VARCHAR2(255), -- 상품명
    brand_id VARCHAR2(255) NOT NULL, -- 브랜드 ID (brand_b 테이블 참조), 필수
    category VARCHAR2(255), -- 상품 종류
    color VARCHAR2(255), -- 상품 색상
	price VARCHAR2(255) NOT NULL, -- 가격
    description VARCHAR2(4000), -- 상품 상세 설명
    created_at VARCHAR2(255) DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), -- 생성 일시 (YYYY-MM-DD HH24:MI:SS), 기본값: 현재 시간
    updated_at VARCHAR2(255),  -- 수정 일시 (%Y-%m-%d %H:%M:%S)
    CONSTRAINT fk_product_brand_id FOREIGN KEY (brand_id) REFERENCES brand_b(brand_id)
);
COMMENT ON TABLE product_b IS '상품의 기본적인 정보를 관리하는 마스터 테이블';
COMMENT ON COLUMN product_b.product_id IS '상품 고유 ID (기본 키, 자동 생성 UUID)';
COMMENT ON COLUMN product_b.product_code IS '상품 코드 (필수, 유니크)';
COMMENT ON COLUMN product_b.product_name IS '상품명';
COMMENT ON COLUMN product_b.brand_id IS '브랜드 ID (brand_b 테이블 참조)';
COMMENT ON COLUMN product_b.category IS '상품 종류';
COMMENT ON COLUMN product_b.color IS '상품 색상';
COMMENT ON COLUMN product_b.price IS '가격';
COMMENT ON COLUMN product_b.description IS '상품 상세 설명';
COMMENT ON COLUMN product_b.created_at IS '생성 일시 (YYYY-MM-DD HH24:MI:SS)';
COMMENT ON COLUMN product_b.updated_at IS '수정 일시 (%Y-%m-%d %H:%M:%S)';

-- 상품 옵션 상세 테이블 (product_option_d)
CREATE TABLE product_option_d (
    product_option_id VARCHAR2(255) DEFAULT SYS_GUID() NOT NULL PRIMARY KEY, -- 상품 옵션 고유 ID, 필수 (자동 생성 UUID)
    product_id VARCHAR2(255) NOT NULL, -- 상품 ID (product_b 테이블 참조), 필수
    option_name VARCHAR2(255), -- 옵션명 (예: 재질)
    option_value VARCHAR2(255), -- 옵션 값 (예: 면)
    additional_price VARCHAR2(255), -- 옵션 추가 가격
    created_at VARCHAR2(255) DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), -- 생성 일시 (YYYY-MM-DD HH24:MI:SS), 기본값: 현재 시간
    updated_at VARCHAR2(255), -- 수정 일시 (%Y-%m-%d %H:%M:%S)
    CONSTRAINT fk_product_option_product_id FOREIGN KEY (product_id) REFERENCES product_b(product_id),
    CONSTRAINT unique_product_option UNIQUE (product_id, option_name, option_value)
);
COMMENT ON TABLE product_option_d IS '상품의 옵션 정보를 관리하는 상세 테이블';
COMMENT ON COLUMN product_option_d.product_option_id IS '상품 옵션 고유 ID (자동 생성 UUID)';
COMMENT ON COLUMN product_option_d.product_id IS '상품 ID (product_b 테이블 참조)';
COMMENT ON COLUMN product_option_d.option_name IS '옵션명 (예: 사이즈, 재질)';
COMMENT ON COLUMN product_option_d.option_value IS '옵션 값 (예: XL, 면)';
COMMENT ON COLUMN product_option_d.additional_price IS '옵션 추가 가격';
COMMENT ON COLUMN product_option_d.created_at IS '생성 일시 (YYYY-MM-DD HH24:MI:SS)';
COMMENT ON COLUMN product_option_d.updated_at IS '수정 일시 (%Y-%m-%d %H:%M:%S)';

-- 상품 재고 상세 테이블 (product_stock_d)
CREATE TABLE product_stock_d (
    product_stock_id VARCHAR2(255) DEFAULT SYS_GUID() NOT NULL PRIMARY KEY, -- 상품 재고 고유 ID, 필수 (자동 생성 UUID)
    product_id VARCHAR2(255) NOT NULL, -- 상품 ID (product_b 테이블 참조), 필수
    product_option_id VARCHAR2(255), -- 상품 옵션 고유 ID (product_option_d 테이블 참조)
    product_size VARCHAR2(255) NOT NULL, -- 사이즈 (상품 사이즈), 필수
    qty VARCHAR2(255), -- 재고 수량
    created_at VARCHAR2(255) DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), -- 생성 일시 (YYYY-MM-DD HH24:MI:SS), 기본값: 현재 시간
    updated_at VARCHAR2(255), -- 수정 일시 (%Y-%m-%d %H:%M:%S)
    CONSTRAINT fk_product_stock_product_id FOREIGN KEY (product_id) REFERENCES product_b(product_id),
    CONSTRAINT fk_product_stock_option_id FOREIGN KEY (product_option_id) REFERENCES product_option_d(product_option_id),
    CONSTRAINT unique_product_stock UNIQUE (product_id, product_option_id, product_size)
);
COMMENT ON TABLE product_stock_d IS '상품의 재고 정보를 관리하는 상세 테이블 (사이즈 정보 통합)';
COMMENT ON COLUMN product_stock_d.product_stock_id IS '상품 재고 고유 ID (자동 생성 UUID)';
COMMENT ON COLUMN product_stock_d.product_id IS '상품 ID (product_b 테이블 참조)';
COMMENT ON COLUMN product_stock_d.product_option_id IS '상품 옵션 고유 ID (product_option_d 테이블 참조, NULL 가능)';
COMMENT ON COLUMN product_stock_d.product_size IS '사이즈';
COMMENT ON COLUMN product_stock_d.qty IS '재고 수량';
COMMENT ON COLUMN product_stock_d.created_at IS '생성 일시 (YYYY-MM-DD HH24:MI:SS)';
COMMENT ON COLUMN product_stock_d.updated_at IS '수정 일시 (%Y-%m-%d %H:%M:%S)';

-- 주문 마스터 테이블 (order_b)
CREATE TABLE order_b (
    order_id VARCHAR2(255) DEFAULT SYS_GUID() PRIMARY KEY, -- 주문 고유 ID, 필수 (기본 키, 자동 생성 UUID)
    user_id VARCHAR2(255) NOT NULL, -- 회원 ID (user_b 테이블 참조), 필수
    delivery_address VARCHAR2(255) NOT NULL, -- 배송지, 필수
    order_status VARCHAR2(255) NOT NULL, -- 주문 상태 (결제전, 후, 배송중, 배송완료 등), 필수
    order_amount VARCHAR2(255) NOT NULL, -- 주문 금액
    discount_amount VARCHAR2(255) NOT NULL, -- 할인 금액
    shipping_cost VARCHAR2(255) NOT NULL, -- 배송비
    order_date VARCHAR2(255), -- 주문 일시 (%Y-%m-%d %H:%M:%S)
    payment_method VARCHAR2(255), -- 결제 수단
    transaction_id VARCHAR2(255) UNIQUE, -- 결제 트랜잭션 ID
    created_at VARCHAR2(255) DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), -- 생성 일시 (YYYY-MM-DD HH24:MI:SS), 기본값: 현재 시간
    updated_at VARCHAR2(255),  -- 수정 일시 (%Y-%m-%d %H:%M:%S)
    CONSTRAINT fk_order_user_id FOREIGN KEY (user_id) REFERENCES user_b(user_id)
);
COMMENT ON TABLE order_b IS '주문에 대한 기본적인 정보를 관리하는 마스터 테이블';
COMMENT ON COLUMN order_b.order_id IS '주문 고유 ID (기본 키, 자동 생성 UUID)';
COMMENT ON COLUMN order_b.user_id IS '회원 ID (user_b 테이블 참조)';
COMMENT ON COLUMN order_b.delivery_address IS '배송지';
COMMENT ON COLUMN order_b.order_status IS '주문 상태 (결제전, 후, 배송중, 배송완료 등)';
COMMENT ON COLUMN order_b.order_amount IS '주문 금액';
COMMENT ON COLUMN order_b.discount_amount IS '할인 금액';
COMMENT ON COLUMN order_b.shipping_cost IS '배송비';
COMMENT ON COLUMN order_b.order_date IS '주문 일시 (%Y-%m-%d %H:%M:%S)';
COMMENT ON COLUMN order_b.payment_method IS '결제 수단';
COMMENT ON COLUMN order_b.transaction_id IS '결제 트랜잭션 ID';
COMMENT ON COLUMN order_b.created_at IS '생성 일시 (YYYY-MM-DD HH24:MI:SS)';
COMMENT ON COLUMN order_b.updated_at IS '수정 일시 (%Y-%m-%d %H:%M:%S)';

-- 주문 상품 상세 테이블 (order_item_d)
CREATE TABLE order_item_d (
    order_item_id VARCHAR2(255) DEFAULT SYS_GUID() NOT NULL PRIMARY KEY, -- 주문 상품 상세 고유 ID, 필수 (자동 생성 UUID)
    order_id VARCHAR2(255) NOT NULL, -- 주문 ID (order_b 테이블 참조), 필수
    product_id VARCHAR2(255) NOT NULL, -- 상품 ID (product_b 테이블 참조), 필수
    product_stock_id VARCHAR2(255) NOT NULL, -- 상품 재고 ID (product_stock_d 테이블 참조), 필수
    qty VARCHAR2(255), -- 주문 수량
    product_price VARCHAR2(255), -- 상품 가격
    created_at VARCHAR2(255) DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), -- 생성 일시 (YYYY-MM-DD HH24:MI:SS), 기본값: 현재 시간
    updated_at VARCHAR2(255), -- 수정 일시 (%Y-%m-%d %H:%M:%S)
    CONSTRAINT fk_order_item_order_id FOREIGN KEY (order_id) REFERENCES order_b(order_id),
    CONSTRAINT fk_order_item_product_id FOREIGN KEY (product_id) REFERENCES product_b(product_id),
    CONSTRAINT fk_order_item_stock_id FOREIGN KEY (product_stock_id) REFERENCES product_stock_d(product_stock_id),
    CONSTRAINT unique_order_item UNIQUE (order_id, product_stock_id)
);
COMMENT ON TABLE order_item_d IS '각 주문에 포함된 상품 정보를 관리하는 상세 테이블';
COMMENT ON COLUMN order_item_d.order_item_id IS '주문 상품 상세 고유 ID (자동 생성 UUID)';
COMMENT ON COLUMN order_item_d.order_id IS '주문 ID (order_b 테이블 참조)';
COMMENT ON COLUMN order_item_d.product_id IS '상품 ID (product_b 테이블 참조)';
COMMENT ON COLUMN order_item_d.product_stock_id IS '상품 재고 ID (product_stock_d 테이블 참조)';
COMMENT ON COLUMN order_item_d.qty IS '주문 수량';
COMMENT ON COLUMN order_item_d.product_price IS '상품 가격';
COMMENT ON COLUMN order_item_d.created_at IS '생성 일시 (YYYY-MM-DD HH24:MI:SS)';
COMMENT ON COLUMN order_item_d.updated_at IS '수정 일시 (%Y-%m-%d %H:%M:%S)';