-- ============================================================
-- TB_REVIEW  : 상품 리뷰
-- TB_REVIEW_IMAGE : 리뷰 이미지 (최대 5장)
-- ============================================================

-- ── 리뷰 본문 ────────────────────────────────────────────────
CREATE TABLE public.tb_review (
    id                BIGSERIAL       PRIMARY KEY,
    social_account_id BIGINT          NOT NULL,               -- 작성자 (TB_SOCIAL_ACCOUNT.ID)
    order_item_id     BIGINT          NOT NULL,               -- 주문 아이템 (TB_ORDER_ITEM.ID) - 1주문1리뷰 보장
    product_id        BIGINT          NOT NULL,               -- 상품 (TB_PRODUCT.ID)
    product_det_id    BIGINT          NULL,                   -- 상품 옵션 (TB_PRODUCT_DET.ID)
    rating            SMALLINT        NOT NULL                -- 별점 1~5
                          CHECK (rating BETWEEN 1 AND 5),
    content           TEXT            NOT NULL,               -- 리뷰 본문
    is_blinded        CHAR(1)         NOT NULL DEFAULT 'N',   -- 관리자 블라인드 여부 (Y/N)
    point_awarded     CHAR(1)         NOT NULL DEFAULT 'N',   -- 리뷰 포인트 지급 여부 (Y/N)
    del_yn            CHAR(1)         NOT NULL DEFAULT 'N',   -- 삭제 여부 (Y/N)
    cre_tm            TIMESTAMP       NOT NULL DEFAULT NOW(),
    upt_tm            TIMESTAMP       NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE  public.tb_review                  IS '상품 리뷰';
COMMENT ON COLUMN public.tb_review.id               IS 'PK';
COMMENT ON COLUMN public.tb_review.social_account_id IS '작성자 소셜 계정 ID';
COMMENT ON COLUMN public.tb_review.order_item_id    IS '주문 아이템 ID (1 주문아이템 = 1 리뷰)';
COMMENT ON COLUMN public.tb_review.product_id       IS '상품 ID';
COMMENT ON COLUMN public.tb_review.product_det_id   IS '상품 옵션 ID';
COMMENT ON COLUMN public.tb_review.rating           IS '별점 (1~5)';
COMMENT ON COLUMN public.tb_review.content          IS '리뷰 본문';
COMMENT ON COLUMN public.tb_review.is_blinded       IS '블라인드 여부 Y/N';
COMMENT ON COLUMN public.tb_review.point_awarded    IS '리뷰 포인트 지급 여부 Y/N';
COMMENT ON COLUMN public.tb_review.del_yn           IS '삭제 여부 Y/N';
COMMENT ON COLUMN public.tb_review.cre_tm           IS '등록일시';
COMMENT ON COLUMN public.tb_review.upt_tm           IS '수정일시';

-- 주문 아이템 1건당 리뷰 1개 제한 (삭제된 리뷰 제외)
CREATE UNIQUE INDEX uix_tb_review_order_item_id
    ON public.tb_review (order_item_id)
    WHERE del_yn = 'N';

-- 상품별 리뷰 목록 조회용
CREATE INDEX ix_tb_review_product_id
    ON public.tb_review (product_id)
    WHERE del_yn = 'N' AND is_blinded = 'N';

-- 사용자별 리뷰 목록 조회용
CREATE INDEX ix_tb_review_social_account_id
    ON public.tb_review (social_account_id)
    WHERE del_yn = 'N';


-- ── 리뷰 이미지 ──────────────────────────────────────────────
CREATE TABLE public.tb_review_image (
    id         BIGSERIAL   PRIMARY KEY,
    review_id  BIGINT      NOT NULL REFERENCES public.tb_review(id),
    img_seq    SMALLINT    NOT NULL,                          -- 이미지 순서 (1~5)
    sys_file_nm VARCHAR(300) NOT NULL,                        -- S3 저장 파일명
    org_file_nm VARCHAR(300) NULL,                           -- 원본 파일명
    del_yn     CHAR(1)     NOT NULL DEFAULT 'N',
    cre_tm     TIMESTAMP   NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE  public.tb_review_image              IS '리뷰 이미지';
COMMENT ON COLUMN public.tb_review_image.id           IS 'PK';
COMMENT ON COLUMN public.tb_review_image.review_id    IS '리뷰 ID';
COMMENT ON COLUMN public.tb_review_image.img_seq      IS '이미지 순서 (1~5)';
COMMENT ON COLUMN public.tb_review_image.sys_file_nm  IS 'S3 저장 파일명';
COMMENT ON COLUMN public.tb_review_image.org_file_nm  IS '원본 파일명';
COMMENT ON COLUMN public.tb_review_image.del_yn       IS '삭제 여부 Y/N';
COMMENT ON COLUMN public.tb_review_image.cre_tm       IS '등록일시';

CREATE INDEX ix_tb_review_image_review_id
    ON public.tb_review_image (review_id)
    WHERE del_yn = 'N';
