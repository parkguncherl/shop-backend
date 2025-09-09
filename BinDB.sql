/* DB 2024-12-09 테이블 커멘트 컬럼 현행화 */

create table tb_api_auth_token
(
    id               serial
        primary key,
    user_id          serial,
    acc_token        varchar(1000) not null,
    acc_token_expire timestamp    default CURRENT_TIMESTAMP,
    ref_token        varchar(1000) not null,
    ref_token_expire timestamp    default CURRENT_TIMESTAMP,
    cre_user         varchar(100) default 'SYSTEM'::character varying,
    cre_tm           timestamp    default CURRENT_TIMESTAMP
);

comment on table tb_api_auth_token is 'API 인증 토큰 테이블';

comment on column tb_api_auth_token.id is '아이디';

comment on column tb_api_auth_token.user_id is '회원 아이디';

comment on column tb_api_auth_token.acc_token is 'Access 토큰';

comment on column tb_api_auth_token.acc_token_expire is 'Access 토큰 만료일시';

comment on column tb_api_auth_token.ref_token is 'Refresh 토큰';

comment on column tb_api_auth_token.ref_token_expire is 'Refresh 토큰 만료일시';

comment on column tb_api_auth_token.cre_user is '생성자';

comment on column tb_api_auth_token.cre_tm is '등록일시';

alter table tb_api_auth_token
    owner to binblur2024;

-- auto-generated definition
create table tb_asn
(
    id           serial
        primary key,
    logis_id     integer      not null,
    partner_id   integer      not null,
    sku_id       integer      not null,
    factory_id   integer,
    asn_type     char         not null,
    work_ymd     date         not null,
    asn_origin   varchar(100) not null,
    asn_stat_cd  char         not null,
    tot_amt      integer,
    gen_cnt      integer,
    asn_cnt      integer,
    out_ymd      date,
    cre_user     varchar(100) default 'SYSTEM'::character varying,
    cre_tm       timestamp    default now(),
    upd_user     varchar(100) default 'SYSTEM'::character varying,
    upd_tm       timestamp    default now(),
    del_yn       char         default 'N'::bpchar,
    chit_no      integer      default 0,
    bef_id       integer      default 0,
    org_work_ymd date
);

comment on table tb_asn is '발주 테이블';

comment on column tb_asn.id is '아이디(PK)';

comment on column tb_asn.logis_id is '물류사 ID(FK)';

comment on column tb_asn.partner_id is '파트너 ID(FK)';

comment on column tb_asn.sku_id is 'SKU ID(FK)';

comment on column tb_asn.factory_id is '공장 ID(FK)';

comment on column tb_asn.asn_type is '발주 유형';

comment on column tb_asn.work_ymd is '작업일';

comment on column tb_asn.asn_origin is 'ASN 출처';

comment on column tb_asn.asn_stat_cd is 'ASN 상태 코드';

comment on column tb_asn.tot_amt is '총 금액';

comment on column tb_asn.gen_cnt is '발주 수량';

comment on column tb_asn.asn_cnt is 'ASN 수량';

comment on column tb_asn.out_ymd is '출고일';

comment on column tb_asn.cre_user is '생성자';

comment on column tb_asn.cre_tm is '생성 일시';

comment on column tb_asn.upd_user is '수정자';

comment on column tb_asn.upd_tm is '수정 일시';

comment on column tb_asn.del_yn is '삭제 여부';

comment on column tb_asn.chit_no is '전표번호';

comment on column tb_asn.bef_id is '테이블ID(PK)이력참조키';

alter table tb_asn
    owner to binblur2024;

create index tb_asn_sku_id_idx
    on tb_asn (sku_id);

-- auto-generated definition
create table tb_attr
(
    id            serial
        primary key,
    attr_name     varchar(100)                                     not null,
    attr_eng_name varchar(100)                                     not null,
    attr_type     char                                             not null,
    attr_cat      char                                             not null,
    attr_desc     varchar(1000),
    code_upper    varchar(50),
    cre_user      varchar(100) default 'SYSTEM'::character varying not null,
    cre_tm        timestamp    default now()                       not null,
    upd_user      varchar(100) default 'SYSTEM'::character varying not null,
    upd_tm        timestamp    default now()                       not null,
    del_yn        char         default 'N'::bpchar                 not null
);

comment on table tb_attr is '속성 테이블';

comment on column tb_attr.id is '아이디(PK)';

comment on column tb_attr.attr_name is '속성명';

comment on column tb_attr.attr_eng_name is '속성의 영문명';

comment on column tb_attr.attr_type is '속성 타입 (숫자, 문자, 날짜, json)';

comment on column tb_attr.attr_cat is '속성 유형 (단일, 다중, 범위)';

comment on column tb_attr.attr_desc is '속성 설명';

comment on column tb_attr.code_upper is '상위 코드';

comment on column tb_attr.cre_user is '생성자';

comment on column tb_attr.cre_tm is '생성 일시';

comment on column tb_attr.upd_user is '수정자';

comment on column tb_attr.upd_tm is '수정 일시';

comment on column tb_attr.del_yn is '삭제 여부';

alter table tb_attr
    owner to binblur2024;

-- auto-generated definition
create table tb_code
(
    id         serial
        primary key,
    code_upper varchar(50)   not null,
    code_cd    varchar(50)   not null,
    code_nm    varchar(1000) not null,
    code_desc  varchar(1000),
    code_etc_1 varchar(1000),
    code_etc_2 varchar(1000),
    code_order integer       not null,
    cre_user   varchar(100) default 'SYSTEM'::character varying,
    cre_tm     timestamp    default now(),
    upd_user   varchar(100) default 'SYSTEM'::character varying,
    upd_tm     timestamp    default now(),
    del_yn     char         default 'N'::bpchar
);

comment on table tb_code is '공통코드 정보';

comment on column tb_code.id is '아이디(PK)';

comment on column tb_code.code_upper is '상위_코드';

comment on column tb_code.code_cd is '공통코드';

comment on column tb_code.code_nm is '공통코드명';

comment on column tb_code.code_desc is '코드_설명';

comment on column tb_code.code_etc_1 is '코드_기타1';

comment on column tb_code.code_etc_2 is '코드_기타2';

comment on column tb_code.code_order is '코드_순서';

comment on column tb_code.cre_user is '등록자';

comment on column tb_code.cre_tm is '등록_일시';

comment on column tb_code.upd_user is '수정자';

comment on column tb_code.upd_tm is '수정_일시';

comment on column tb_code.del_yn is '삭제_여부';

alter table tb_code
    owner to binblur2024;

-- auto-generated definition
create table tb_compo
(
    id        serial
        primary key,
    prod_id   integer     not null,
    compo_cd  char        not null,
    compo_val varchar(10) not null,
    cre_user  varchar(100) default 'SYSTEM'::character varying,
    cre_tm    date         default now(),
    upd_user  varchar(100) default 'SYSTEM'::character varying,
    upd_tm    date         default now(),
    del_yn    char         default 'N'::bpchar
);

comment on table tb_compo is '혼용율 Entity';

comment on column tb_compo.id is '아이디(PK)';

comment on column tb_compo.prod_id is '상품ID(FK)';

comment on column tb_compo.compo_cd is '혼용율 코드';

comment on column tb_compo.compo_val is '혼용율 값';

comment on column tb_compo.cre_user is '생성자';

comment on column tb_compo.cre_tm is '생성 일자';

comment on column tb_compo.upd_user is '수정자';

comment on column tb_compo.upd_tm is '수정 일자';

comment on column tb_compo.del_yn is '삭제 여부';

alter table tb_compo
    owner to binblur2024;


-- auto-generated definition
create table tb_contact
(
    id               serial
        primary key,
    user_id          integer,
    tran_type        char          not null,
    uri              varchar(500)  not null,
    uri_nm           varchar(1000) not null,
    input_param_cntn varchar(4000),
    contact_ip       varchar(1000),
    cre_user         varchar(100) default 'SYSTEM'::character varying,
    cre_tm           timestamp    default now(),
    upd_user         varchar(100) default 'SYSTEM'::character varying,
    upd_tm           timestamp    default now(),
    del_yn           char         default 'N'::bpchar
);

comment on table tb_contact is '사용자 접속 기록 Entity';

comment on column tb_contact.id is '아이디(PK)';

comment on column tb_contact.user_id is '사용자_ID(FK)';

comment on column tb_contact.tran_type is '거래구분';

comment on column tb_contact.uri is 'URI';

comment on column tb_contact.uri_nm is 'URI 명';

comment on column tb_contact.input_param_cntn is '입력 변수 내용';

comment on column tb_contact.contact_ip is '접속 IP';

comment on column tb_contact.cre_user is '생성자';

comment on column tb_contact.cre_tm is '생성 일자';

comment on column tb_contact.upd_user is '수정자';

comment on column tb_contact.upd_tm is '수정 일자';

comment on column tb_contact.del_yn is '삭제 여부';

alter table tb_contact
    owner to binblur2024;


-- auto-generated definition
create table tb_daily_inven
(
    id          serial
        primary key,
    work_ymd    date    not null,
    sku_id      integer not null,
    partner_id  integer not null,
    center_cnt  integer not null,
    retail_cnt  integer not null,
    sample_cnt  integer not null,
    tot_cnt     integer not null,
    sail_cnt    integer not null,
    sail_amt    integer not null,
    return_cnt  integer not null,
    return_amt  integer not null,
    seller_cnt  integer not null,
    rotate_rate varchar(10),
    cre_user    varchar(100) default 'SYSTEM'::character varying,
    cre_tm      timestamp    default now(),
    upd_user    varchar(100) default 'SYSTEM'::character varying,
    upd_tm      timestamp    default now(),
    del_yn      char         default 'N'::bpchar
);

comment on table tb_daily_inven is '일별재고현황';

comment on column tb_daily_inven.id is '아이디(PK)';

comment on column tb_daily_inven.work_ymd is '영업일';

comment on column tb_daily_inven.sku_id is '스큐ID';

comment on column tb_daily_inven.partner_id is '파트너ID';

comment on column tb_daily_inven.center_cnt is '센터재고';

comment on column tb_daily_inven.retail_cnt is '매장재고';

comment on column tb_daily_inven.sample_cnt is '샘플건수';

comment on column tb_daily_inven.tot_cnt is '전체건수';

comment on column tb_daily_inven.sail_cnt is '판매건수';

comment on column tb_daily_inven.sail_amt is '판매금액';

comment on column tb_daily_inven.return_cnt is '반품건수';

comment on column tb_daily_inven.return_amt is '반품금액';

comment on column tb_daily_inven.seller_cnt is '판매처건수';

comment on column tb_daily_inven.rotate_rate is '회전율';

comment on column tb_daily_inven.cre_user is '등록자';

comment on column tb_daily_inven.cre_tm is '등록_일시';

comment on column tb_daily_inven.upd_user is '수정자';

comment on column tb_daily_inven.upd_tm is '수정_일시';

comment on column tb_daily_inven.del_yn is '삭제_여부';

alter table tb_daily_inven
    owner to binblur2024;

-- auto-generated definition
create table tb_expenses
(
    id         serial
        primary key,
    partner_id integer,
    tran_date  date        not null,
    account_cd varchar(50) not null,
    in_amt     integer     not null,
    out_amt    integer     not null,
    note_cntn  varchar(1000),
    cre_user   varchar(100) default 'SYSTEM'::character varying,
    cre_tm     timestamp    default now(),
    upd_user   varchar(100) default 'SYSTEM'::character varying,
    upd_tm     timestamp    default now(),
    del_yn     char         default 'N'::bpchar,
    order_id   integer,
    bef_id     integer      default 0
);

comment on table tb_expenses is '경비 Entity';

comment on column tb_expenses.id is '아이디(PK)';

comment on column tb_expenses.partner_id is '파트너ID(FK)';

comment on column tb_expenses.tran_date is '거래일시';

comment on column tb_expenses.account_cd is '계정과목';

comment on column tb_expenses.in_amt is '입금금액';

comment on column tb_expenses.out_amt is '출금금액';

comment on column tb_expenses.note_cntn is '비고';

comment on column tb_expenses.cre_user is '생성자';

comment on column tb_expenses.cre_tm is '생성 일자';

comment on column tb_expenses.upd_user is '수정자';

comment on column tb_expenses.upd_tm is '수정 일자';

comment on column tb_expenses.del_yn is '삭제 여부';

comment on column tb_expenses.order_id is '주문id';

alter table tb_expenses
    owner to binblur2024;

-- auto-generated definition
create table tb_factory
(
    id            serial
        primary key,
    partner_id    integer      not null,
    factory_cd    char         not null,
    comp_nm       varchar(100) not null,
    comp_tel_no   varchar(30),
    person_nm     varchar(100),
    person_tel_no varchar(30),
    comp_no       varchar(100),
    detail_info   varchar(1000),
    comp_email    varchar(40),
    now_amt       integer,
    sleep_yn      char         default 'N'::bpchar,
    cre_user      varchar(100) default 'SYSTEM'::character varying,
    cre_tm        timestamp    default now(),
    upd_user      varchar(100) default 'SYSTEM'::character varying,
    upd_tm        timestamp    default now(),
    del_yn        char         default 'N'::bpchar,
    gubun1        varchar(10),
    gubun2        varchar(10),
    ceo_nm        varchar(100),
    ceo_tel_no    varchar(30),
    comp_fax_no   varchar(30),
    comp_addr     varchar(500),
    rem_prn_yn    char,
    tran_yn       char,
    etc_scr_cntn  varchar(1000),
    etc_chit_cntn varchar(1000),
    reg_ymd       date,
    bef_id        integer      default 0
);

comment on table tb_factory is '생산처';

comment on column tb_factory.id is '아이디(PK)';

comment on column tb_factory.partner_id is '파트너ID';

comment on column tb_factory.factory_cd is 'A:봉제,  B...';

comment on column tb_factory.comp_nm is '회사명';

comment on column tb_factory.comp_tel_no is '회사전번';

comment on column tb_factory.person_nm is '담당자명';

comment on column tb_factory.person_tel_no is '담당자전화번호';

comment on column tb_factory.comp_no is '사업자번호';

comment on column tb_factory.detail_info is '상세정보';

comment on column tb_factory.comp_email is '이메일';

comment on column tb_factory.now_amt is '현잔';

comment on column tb_factory.sleep_yn is '휴면여부';

comment on column tb_factory.cre_user is '생성자';

comment on column tb_factory.cre_tm is '생성일시';

comment on column tb_factory.upd_user is '수정자';

comment on column tb_factory.upd_tm is '수정일시';

comment on column tb_factory.del_yn is '삭제여부';

comment on column tb_factory.gubun1 is '구분1';

comment on column tb_factory.gubun2 is '구분2';

comment on column tb_factory.ceo_nm is '대표명';

comment on column tb_factory.ceo_tel_no is '대표전호번호';

comment on column tb_factory.comp_fax_no is '팩스번호';

comment on column tb_factory.comp_addr is '회사주소';

comment on column tb_factory.rem_prn_yn is '잔액인쇄';

comment on column tb_factory.tran_yn is '처리여부';

comment on column tb_factory.etc_scr_cntn is '기타비고화면출력';

comment on column tb_factory.etc_chit_cntn is '비고전표';

comment on column tb_factory.reg_ymd is '등록일';

comment on column tb_factory.bef_id is '테이블ID(PK)이력참조키';

alter table tb_factory
    owner to binblur2024;

-- auto-generated definition
create table tb_factory_inout
(
    id          serial
        primary key,
    stock_id    integer,
    factory_id  integer not null,
    chit_no     integer,
    dw_tp       char,
    tran_ymd    date    not null,
    work_ymd    date    not null,
    cash_amt    integer,
    account_amt integer,
    dc_amt      integer,
    tot_cnt     integer not null,
    tot_amt     integer not null,
    bef_amt     integer,
    etc_cntn    varchar(5000),
    cre_user    varchar(100) default 'SYSTEM'::character varying,
    cre_tm      timestamp    default now(),
    upd_user    varchar(100) default 'SYSTEM'::character varying,
    upd_tm      timestamp    default now(),
    del_yn      char         default 'N'::bpchar,
    bef_id      integer      default 0
);

comment on table tb_factory_inout is '공장입출금';
comment on column tb_factory_inout.id is '아이디(PK)';
comment on column tb_factory_inout.stock_id is '입고ID(FK)';
comment on column tb_factory_inout.factory_id is '공장ID(FK)';
comment on column tb_factory_inout.chit_no is '전표번호';
comment on column tb_factory_inout.dw_tp is '입출금 유형';
comment on column tb_factory_inout.tran_ymd is '거래일자';
comment on column tb_factory_inout.work_ymd is '영업일자';
comment on column tb_factory_inout.cash_amt is '현금';
comment on column tb_factory_inout.account_amt is '통장입금';
comment on column tb_factory_inout.dc_amt is '할인금액';
comment on column tb_factory_inout.tot_cnt is '총수량';
comment on column tb_factory_inout.tot_amt is '총금액';
comment on column tb_factory_inout.bef_amt is '전잔액';
comment on column tb_factory_inout.etc_cntn is '비고';
comment on column tb_factory_inout.cre_user is '등록자';
comment on column tb_factory_inout.cre_tm is '등록_일시';
comment on column tb_factory_inout.upd_user is '수정자';
comment on column tb_factory_inout.upd_tm is '수정_일시';
comment on column tb_factory_inout.del_yn is '삭제_여부';
comment on column tb_factory_inout.bef_id is '테이블ID(PK)이력참조키';

alter table tb_factory_inout
    owner to binblur2024;


-- auto-generated definition
create table tb_favorites
(
    id       serial
        primary key,
    user_id  integer      not null,
    auth_cd  char(3),
    menu_uri varchar(500) not null,
    dist_seq integer      not null,
    cre_user varchar(100) default 'SYSTEM'::character varying,
    cre_tm   date         default now(),
    upd_user varchar(100) default 'SYSTEM'::character varying,
    upd_tm   date         default now(),
    del_yn   char         default 'N'::bpchar
);

comment on table tb_favorites is '즐겨찾기';

comment on column tb_favorites.id is '아이디(PK)';

comment on column tb_favorites.user_id is '사용자ID(FK)';

comment on column tb_favorites.auth_cd is '권한코드';

comment on column tb_favorites.menu_uri is '메뉴_URI';

comment on column tb_favorites.dist_seq is '전시순서';

comment on column tb_favorites.cre_user is '등록자';

comment on column tb_favorites.cre_tm is '등록_일시';

comment on column tb_favorites.upd_user is '수정자';

comment on column tb_favorites.upd_tm is '수정_일시';

comment on column tb_favorites.del_yn is '삭제_여부';

alter table tb_favorites
    owner to binblur2024;

-- auto-generated definition
create table tb_file
(
    id        serial
        primary key,
    file_type varchar(10) not null,
    cre_user  varchar(100) default 'SYSTEM'::character varying,
    cre_tm    date         default now(),
    upd_user  varchar(100) default 'SYSTEM'::character varying,
    upd_tm    date         default now(),
    del_yn    char         default 'N'::bpchar
);

comment on table tb_file is '파일 정보 테이블';

comment on column tb_file.id is '아이디 (PK)';

comment on column tb_file.file_type is '파일 유형';

comment on column tb_file.cre_user is '등록자';

comment on column tb_file.cre_tm is '등록 일시';

comment on column tb_file.upd_user is '수정자';

comment on column tb_file.upd_tm is '수정 일시';

comment on column tb_file.del_yn is '삭제 여부';

alter table tb_file
    owner to binblur2024;

-- auto-generated definition
create table tb_file_det
(
    id          serial
        primary key,
    file_id     integer       not null,
    file_seq    integer       not null,
    bucket_name varchar(100)  not null,
    sys_file_nm varchar(100)  not null,
    file_ext    varchar(100)  not null,
    file_nm     varchar(1000) not null,
    file_size   integer       not null,
    cre_user    varchar(100) default 'SYSTEM'::character varying,
    cre_tm      date         default now(),
    upd_user    varchar(100) default 'SYSTEM'::character varying,
    upd_tm      date         default now(),
    del_yn      char         default 'N'::bpchar
);

comment on table tb_file_det is '파일상세';

comment on column tb_file_det.id is '아이디(PK)';

comment on column tb_file_det.file_id is '파일ID(FK)';

comment on column tb_file_det.file_seq is '순서';

comment on column tb_file_det.sys_file_nm is '시스템파일명';

comment on column tb_file_det.file_ext is '파일확장자';

comment on column tb_file_det.file_nm is '파일명';

comment on column tb_file_det.file_size is '파일사이즈';

comment on column tb_file_det.cre_user is '등록자';

comment on column tb_file_det.cre_tm is '등록_일시';

comment on column tb_file_det.upd_user is '수정자';

comment on column tb_file_det.upd_tm is '수정_일시';

comment on column tb_file_det.del_yn is '삭제_여부';

alter table tb_file_det
    owner to binblur2024;

-- auto-generated definition
create table tb_home
(
    home_nm         varchar(100) not null,
    home_fax_no     varchar(30),
    home_addr       varchar(30),
    home_tel_no     varchar(30),
    ceo_nm          varchar(100),
    rep_tel_no      varchar(30),
    home_comp_no    varchar(100),
    home_email      varchar(40),
    home_account_nm varchar(100),
    home_account    varchar(100),
    cre_user        varchar(100) default 'SYSTEM'::character varying,
    cre_tm          date         default now(),
    upd_user        varchar(100) default 'SYSTEM'::character varying,
    upd_tm          date         default now(),
    del_yn          char         default 'N'::bpchar
);

comment on table tb_home is '당사정보';

comment on column tb_home.home_nm is '업체명';

comment on column tb_home.home_fax_no is '당사팩스번호';

comment on column tb_home.home_addr is '당사주소';

comment on column tb_home.home_tel_no is '회사전화번호';

comment on column tb_home.ceo_nm is '대표';

comment on column tb_home.rep_tel_no is '회사연락처';

comment on column tb_home.home_comp_no is '사업자_번호';

comment on column tb_home.home_email is '회사이메일(세금계산서용)';

comment on column tb_home.home_account_nm is '입금처';

comment on column tb_home.home_account is '회사입금계좌';

comment on column tb_home.cre_user is '등록자';

comment on column tb_home.cre_tm is '등록_일시';

comment on column tb_home.upd_user is '수정자';

comment on column tb_home.upd_tm is '수정_일시';

comment on column tb_home.del_yn is '삭제_여부';

alter table tb_home
    owner to binblur2024;

-- auto-generated definition
create table tb_inspect
(
    id            serial
        primary key,
    inspect_cd    integer       not null,
    inspect_title varchar(200)  not null,
    inspect_cntn  varchar(4000) not null,
    inspect_ymd   date,
    cre_user      varchar(100) default 'SYSTEM'::character varying,
    cre_tm        timestamp    default now(),
    upd_user      varchar(100) default 'SYSTEM'::character varying,
    upd_tm        timestamp    default now(),
    del_yn        char         default 'N'::bpchar
);

comment on table tb_inspect is '실사';

comment on column tb_inspect.id is '아이디(PK)';

comment on column tb_inspect.inspect_cd is '실사구분(화주/자체)';

comment on column tb_inspect.inspect_title is '실사명';

comment on column tb_inspect.inspect_cntn is '실사내용';

comment on column tb_inspect.inspect_ymd is '실사일자';

comment on column tb_inspect.cre_user is '등록자';

comment on column tb_inspect.cre_tm is '등록_일시';

comment on column tb_inspect.upd_user is '수정자';

comment on column tb_inspect.upd_tm is '수정_일시';

comment on column tb_inspect.del_yn is '삭제_여부';

alter table tb_inspect
    owner to binblur2024;

-- auto-generated definition
create table tb_inspect_det
(
    id             serial
        primary key,
    inspect_loc_id integer,
    sku_id         integer,
    bef_cnt        integer,
    aft_cnt        integer,
    chg_yn         char,
    cre_user       varchar(100) default 'SYSTEM'::character varying,
    cre_tm         date         default now(),
    upd_user       varchar(100) default 'SYSTEM'::character varying,
    upd_tm         date         default now(),
    del_yn         char         default 'N'::bpchar
);

comment on table tb_inspect_det is '실사상세';

comment on column tb_inspect_det.id is '아이디(PK)';

comment on column tb_inspect_det.inspect_loc_id is '실사대상ID(FK)';

comment on column tb_inspect_det.sku_id is '스큐ID';

comment on column tb_inspect_det.bef_cnt is '변경전수량';

comment on column tb_inspect_det.aft_cnt is '변경후수량';

comment on column tb_inspect_det.chg_yn is '변경여부';

comment on column tb_inspect_det.cre_user is '등록자';

comment on column tb_inspect_det.cre_tm is '등록_일시';

comment on column tb_inspect_det.upd_user is '수정자';

comment on column tb_inspect_det.upd_tm is '수정_일시';

comment on column tb_inspect_det.del_yn is '삭제_여부';

alter table tb_inspect_det
    owner to binblur2024;

-- auto-generated definition
create table tb_inspect_loc
(
    id         serial
        primary key,
    inspect_id integer,
    logis_id   integer not null,
    zone       char,
    loc_id     integer not null,
    cre_user   varchar(100) default 'SYSTEM'::character varying,
    cre_tm     date         default now(),
    upd_user   varchar(100) default 'SYSTEM'::character varying,
    upd_tm     date         default now(),
    del_yn     char         default 'N'::bpchar
);

comment on table tb_inspect_loc is '실사대상';

comment on column tb_inspect_loc.id is '아이디(PK)';

comment on column tb_inspect_loc.inspect_id is '실사ID(FK)';

comment on column tb_inspect_loc.logis_id is '창고ID';

comment on column tb_inspect_loc.zone is 'ZONE(복합키)';

comment on column tb_inspect_loc.loc_id is '위치ID';

comment on column tb_inspect_loc.cre_user is '등록자';

comment on column tb_inspect_loc.cre_tm is '등록_일시';

comment on column tb_inspect_loc.upd_user is '수정자';

comment on column tb_inspect_loc.upd_tm is '수정_일시';

comment on column tb_inspect_loc.del_yn is '삭제_여부';

alter table tb_inspect_loc
    owner to binblur2024;

-- auto-generated definition
create table tb_inven
(
    id            serial
        primary key,
    partner_id    integer not null,
    stock_id      integer not null,
    sku_id        integer not null,
    logis_id      integer not null,
    loc_id        integer,
    inven_ymd     date,
    inven_stat_cd char    not null,
    job_det_id    integer,
    cre_user      varchar(100) default 'SYSTEM'::character varying,
    cre_tm        timestamp    default now(),
    upd_user      varchar(100) default 'SYSTEM'::character varying,
    upd_tm        timestamp    default now(),
    del_yn        char         default 'N'::bpchar
);

comment on table tb_inven is '재고';

comment on column tb_inven.id is '아이디(PK)';

comment on column tb_inven.partner_id is '파트너ID';

comment on column tb_inven.stock_id is '입하ID';

comment on column tb_inven.sku_id is '스큐ID';

comment on column tb_inven.logis_id is '창고ID';

comment on column tb_inven.loc_id is '위치ID';

comment on column tb_inven.inven_ymd is '입고일';

comment on column tb_inven.inven_stat_cd is '재고상태';

comment on column tb_inven.job_det_id is '작업상세ID';

comment on column tb_inven.cre_user is '등록자';

comment on column tb_inven.cre_tm is '등록일시';

comment on column tb_inven.upd_user is '수정자';

comment on column tb_inven.upd_tm is '수정일시';

comment on column tb_inven.del_yn is '삭제여부';

alter table tb_inven
    owner to binblur2024;

create index tb_inven_sku_id_stat_cd_idx
    on tb_inven (sku_id desc, inven_stat_cd asc);

create index tb_inven_job_det_id_idx
    on tb_inven (job_det_id desc);


-- auto-generated definition
create table tb_inven_chg
(
    id           serial
        primary key,
    loc_id       integer,
    sku_id       integer,
    bef_cnt      integer,
    aft_cnt      integer,
    inven_chg_cd char,
    chg_yn       char,
    cre_user     varchar(100) default 'SYSTEM'::character varying,
    cre_tm       date         default now(),
    upd_user     varchar(100) default 'SYSTEM'::character varying,
    upd_tm       date         default now(),
    del_yn       char         default 'N'::bpchar
);

comment on table tb_inven_chg is '재고변경';

comment on column tb_inven_chg.id is '아이디(PK)';

comment on column tb_inven_chg.loc_id is '위치ID';

comment on column tb_inven_chg.sku_id is '스큐ID';

comment on column tb_inven_chg.bef_cnt is '변경전수량';

comment on column tb_inven_chg.aft_cnt is '변경후수량';

comment on column tb_inven_chg.inven_chg_cd is '재고변경사유';

comment on column tb_inven_chg.chg_yn is '변경여부';

comment on column tb_inven_chg.cre_user is '등록자';

comment on column tb_inven_chg.cre_tm is '등록 일시';

comment on column tb_inven_chg.upd_user is '수정자';

comment on column tb_inven_chg.upd_tm is '수정 일시';

comment on column tb_inven_chg.del_yn is '삭제 여부';

alter table tb_inven_chg
    owner to binblur2024;

-- auto-generated definition
create table tb_inven_dec
(
    id         serial
        primary key,
    loc_id     integer     not null,
    sku_id     integer     not null,
    bef_cnt    integer     not null,
    aft_cnt    integer     not null,
    inv_chg_cd varchar(20) not null,
    dec_cntn   text,
    cre_user   varchar(100) default 'SYSTEM'::character varying,
    cre_tm     timestamp    default now(),
    upd_user   varchar(100) default 'SYSTEM'::character varying,
    upd_tm     timestamp    default now(),
    del_yn     char         default 'N'::bpchar
);

comment on table tb_inven_dec is '재고 감소 이력';

comment on column tb_inven_dec.id is '재고 차감 ID';

comment on column tb_inven_dec.loc_id is '로케이션 ID';

comment on column tb_inven_dec.sku_id is 'SKU ID';

comment on column tb_inven_dec.bef_cnt is '차감 전 수량';

comment on column tb_inven_dec.aft_cnt is '차감 후 수량';

comment on column tb_inven_dec.inv_chg_cd is '재고 변경 코드';

comment on column tb_inven_dec.dec_cntn is '차감 내용/사유';

comment on column tb_inven_dec.cre_user is '등록자';

comment on column tb_inven_dec.cre_tm is '등록일시';

comment on column tb_inven_dec.upd_user is '수정자';

comment on column tb_inven_dec.upd_tm is '수정일시';

comment on column tb_inven_dec.del_yn is '삭제여부';

alter table tb_inven_dec
    owner to binblur2024;

-- auto-generated definition
create table tb_inven_loc_chg_log
(
    id           serial
        constraint tb_inven_chg_log_pkey
            primary key,
    sku_id       integer      not null,
    bef_loc      varchar(100) not null,
    aft_loc      varchar(100) not null,
    aft_cnt      integer      not null,
    inven_chg_cd varchar(20)  not null,
    chg_etc      text,
    cre_user     varchar(100) default 'SYSTEM'::character varying,
    cre_tm       timestamp    default now(),
    upd_user     varchar(100) default 'SYSTEM'::character varying,
    upd_tm       timestamp    default now(),
    del_yn       char         default 'N'::bpchar
);

comment on table tb_inven_loc_chg_log is '재고 로케이션 변경 이력';

comment on column tb_inven_loc_chg_log.id is '로케이션 변경 ID';

comment on column tb_inven_loc_chg_log.sku_id is 'SKU ID';

comment on column tb_inven_loc_chg_log.bef_loc is '이전 로케이션';

comment on column tb_inven_loc_chg_log.aft_loc is '이후 로케이션';

comment on column tb_inven_loc_chg_log.aft_cnt is '변경 후 수량';

comment on column tb_inven_loc_chg_log.inven_chg_cd is '재고 변경 상태 코드';

comment on column tb_inven_loc_chg_log.chg_etc is '변경 비고';

comment on column tb_inven_loc_chg_log.cre_user is '등록자';

comment on column tb_inven_loc_chg_log.cre_tm is '등록일시';

comment on column tb_inven_loc_chg_log.upd_user is '수정자';

comment on column tb_inven_loc_chg_log.upd_tm is '수정일시';

comment on column tb_inven_loc_chg_log.del_yn is '삭제여부';

alter table tb_inven_loc_chg_log
    owner to binblur2024;

-- auto-generated definition
create table tb_job
(
    id          serial
        primary key,
    tran_ymd    date                             not null,
    partner_id  integer                          not null,
    logis_id    integer                          not null,
    order_id    integer,
    job_type    char                             not null,
    job_stat_cd char                             not null,
    chit_no     integer,
    seller_id   integer                          not null,
    tran_cnt    integer                          not null,
    tran_seq    integer                          not null,
    job_etc     varchar(4000),
    on_site_yn  char         default 'N'::bpchar not null,
    cre_user    varchar(100) default 'SYSTEM'::character varying,
    cre_tm      timestamp    default now(),
    upd_user    varchar(100) default 'SYSTEM'::character varying,
    upd_tm      timestamp    default now(),
    del_yn      char         default 'N'::bpchar
);

comment on table tb_job is '작업';

comment on column tb_job.id is '아이디(PK)';

comment on column tb_job.tran_ymd is '창고ID';

comment on column tb_job.partner_id is '파트너ID';

comment on column tb_job.logis_id is '창고ID';

comment on column tb_job.order_id is '주문ID';

comment on column tb_job.job_type is '작업구분코드';

comment on column tb_job.job_stat_cd is '작업상태코드';

comment on column tb_job.chit_no is '전표번호';

comment on column tb_job.seller_id is '소매처ID';

comment on column tb_job.tran_cnt is '처리대상수량';

comment on column tb_job.tran_seq is '처리순서';

comment on column tb_job.job_etc is '비고';

comment on column tb_job.on_site_yn is '매장여부';

comment on column tb_job.cre_user is '등록자';

comment on column tb_job.cre_tm is '등록일시';

comment on column tb_job.upd_user is '수정자';

comment on column tb_job.upd_tm is '수정일시';

comment on column tb_job.del_yn is '삭제여부';

alter table tb_job
    owner to binblur2024;

-- auto-generated definition
create table tb_job_det
(
    id           serial
        primary key,
    job_id       integer,
    order_det_id integer,
    job_cnt      integer,
    sku_id       integer,
    real_cnt     integer,
    cre_user     varchar(100) default 'SYSTEM'::character varying,
    cre_tm       timestamp    default now(),
    upd_user     varchar(100) default 'SYSTEM'::character varying,
    upd_tm       timestamp    default now(),
    del_yn       char         default 'N'::bpchar
);

comment on table tb_job_det is '작업상세';

comment on column tb_job_det.id is '아이디(PK)';

comment on column tb_job_det.job_id is '작업ID';

comment on column tb_job_det.order_det_id is '주문상세ID';

comment on column tb_job_det.job_cnt is '작업수량';

comment on column tb_job_det.sku_id is '스큐ID';

comment on column tb_job_det.real_cnt is '실제건수';

comment on column tb_job_det.cre_user is '등록자';

comment on column tb_job_det.cre_tm is '등록일시';

comment on column tb_job_det.upd_user is '수정자';

comment on column tb_job_det.upd_tm is '수정일시';

comment on column tb_job_det.del_yn is '삭제여부';

alter table tb_job_det
    owner to binblur2024;

-- auto-generated definition
create table tb_loc
(
    id       serial
        primary key,
    logis_id integer,
    zone_cd  varchar(10),
    location varchar(10) not null,
    loc_cntn varchar(1000),
    cre_user varchar(100) default 'SYSTEM'::character varying,
    cre_tm   timestamp    default now(),
    upd_user varchar(100) default 'SYSTEM'::character varying,
    upd_tm   timestamp    default now(),
    del_yn   char         default 'N'::bpchar
);

comment on table tb_loc is '위치';

comment on column tb_loc.id is '아이디';

comment on column tb_loc.logis_id is '창고ID';

comment on column tb_loc.zone_cd is 'ZONE코드';

comment on column tb_loc.location is '위치';

comment on column tb_loc.loc_cntn is '위치설명';

comment on column tb_loc.cre_user is '등록자';

comment on column tb_loc.cre_tm is '등록 일시';

comment on column tb_loc.upd_user is '수정자';

comment on column tb_loc.upd_tm is '수정 일시';

comment on column tb_loc.del_yn is '삭제 여부';

alter table tb_loc
    owner to binblur2024;


-- auto-generated definition
create table tb_logis
(
    id            serial
        primary key,
    logis_key     varchar(50),
    logis_nm      varchar(100) not null,
    logis_addr    varchar(100) not null,
    logis_desc    varchar(100) not null,
    logis_tel_no  varchar(30),
    person_nm     varchar(100) not null,
    person_tel_no varchar(30)  not null,
    center_info   varchar(1000),
    cre_user      varchar(100) default 'SYSTEM'::character varying,
    cre_tm        timestamp    default now(),
    upd_user      varchar(100) default 'SYSTEM'::character varying,
    upd_tm        timestamp    default now(),
    del_yn        char         default 'N'::bpchar
);

comment on table tb_logis is '창고';

comment on column tb_logis.id is '아이디(PK)';

comment on column tb_logis.logis_key is '물류창고키';

comment on column tb_logis.logis_nm is '물류창고명';

comment on column tb_logis.logis_addr is '물류창고위치';

comment on column tb_logis.logis_desc is '물류창고설명';

comment on column tb_logis.logis_tel_no is '회사전화번호';

comment on column tb_logis.person_nm is '담당자_명';

comment on column tb_logis.person_tel_no is '담당장_전화_번호';

comment on column tb_logis.center_info is '상세_정보';

comment on column tb_logis.cre_user is '등록자';

comment on column tb_logis.cre_tm is '등록_일시';

comment on column tb_logis.upd_user is '수정자';

comment on column tb_logis.upd_tm is '수정_일시';

comment on column tb_logis.del_yn is '삭제_여부';

alter table tb_logis
    owner to binblur2024;

-- auto-generated definition
create table tb_menu
(
    id          serial
        primary key,
    up_menu_cd  char(2)      not null,
    menu_cd     varchar(4)   not null,
    menu_order  integer      not null,
    menu_nm     varchar(100) not null,
    menu_eng_nm varchar(100),
    menu_uri    varchar(500),
    cre_user    varchar(100) default 'SYSTEM'::character varying,
    cre_tm      timestamp    default now(),
    upd_user    varchar(100) default 'SYSTEM'::character varying,
    upd_tm      timestamp    default now(),
    del_yn      char         default 'N'::bpchar
);

comment on table tb_menu is '메뉴 정보';

comment on column tb_menu.id is '아이디 (PK)';

comment on column tb_menu.up_menu_cd is '상위 메뉴 코드';

comment on column tb_menu.menu_cd is '메뉴 코드';

comment on column tb_menu.menu_order is '메뉴 순서';

comment on column tb_menu.menu_nm is '메뉴명';

comment on column tb_menu.menu_eng_nm is '메뉴 영문명';

comment on column tb_menu.menu_uri is '메뉴 URI';

comment on column tb_menu.cre_user is '등록자';

comment on column tb_menu.cre_tm is '등록 일시';

comment on column tb_menu.upd_user is '수정자';

comment on column tb_menu.upd_tm is '수정 일시';

comment on column tb_menu.del_yn is '삭제 여부';

alter table tb_menu
    owner to binblur2024;

-- auto-generated definition
create table tb_menu_auth
(
    id            serial
        primary key,
    auth_cd       varchar(50) not null,
    menu_cd       varchar(50) not null,
    menu_read_yn  char        not null,
    menu_upd_yn   char        not null,
    menu_excel_yn char        not null,
    cre_user      varchar(100) default 'SYSTEM'::character varying,
    cre_tm        timestamp    default now(),
    upd_user      varchar(100) default 'SYSTEM'::character varying,
    upd_tm        timestamp    default now(),
    del_yn        char         default 'N'::bpchar
);

comment on table tb_menu_auth is '메뉴_권한';

comment on column tb_menu_auth.id is '아이디(PK)';

comment on column tb_menu_auth.auth_cd is '권한_코드';

comment on column tb_menu_auth.menu_cd is '메뉴_코드';

comment on column tb_menu_auth.menu_read_yn is '조회권한_여부';

comment on column tb_menu_auth.menu_upd_yn is '수정권한_여부';

comment on column tb_menu_auth.menu_excel_yn is '엑셀다운권한_여부';

comment on column tb_menu_auth.cre_user is '등록자';

comment on column tb_menu_auth.cre_tm is '등록_일시';

comment on column tb_menu_auth.upd_user is '수정자';

comment on column tb_menu_auth.upd_tm is '수정_일시';

comment on column tb_menu_auth.del_yn is '삭제_여부';

alter table tb_menu_auth
    owner to binblur2024;


-- auto-generated definition
create table tb_merge_det
(
    id              serial
        primary key,
    merge_seller_id integer,
    change_tp       char,
    change_id       integer not null
);

comment on table tb_merge_det is '판매처통합상세';

comment on column tb_merge_det.id is '아이디(PK)';

comment on column tb_merge_det.merge_seller_id is '통합이력ID(FK)';

comment on column tb_merge_det.change_tp is '변경구분';

comment on column tb_merge_det.change_id is '변경ID';

alter table tb_merge_det
    owner to binblur2024;

-- auto-generated definition
create table tb_merge_seller
(
    id             serial
        primary key,
    bef_partner_id integer,
    af_partner_id  varchar(1000) not null,
    cre_user       varchar(100) default 'SYSTEM'::character varying,
    cre_tm         date         default now(),
    upd_user       varchar(100) default 'SYSTEM'::character varying,
    upd_tm         date         default now(),
    del_yn         char         default 'N'::bpchar
);

comment on table tb_merge_seller is '판매처통합이력';

comment on column tb_merge_seller.id is '아이디(PK)';

comment on column tb_merge_seller.bef_partner_id is '변경전ID';

comment on column tb_merge_seller.af_partner_id is '변경후ID';

comment on column tb_merge_seller.cre_user is '등록자';

comment on column tb_merge_seller.cre_tm is '등록_일시';

comment on column tb_merge_seller.upd_user is '수정자';

comment on column tb_merge_seller.upd_tm is '수정_일시';

comment on column tb_merge_seller.del_yn is '삭제_여부';

alter table tb_merge_seller
    owner to binblur2024;

-- auto-generated definition
create table tb_michul
(
    id         serial
        primary key,
    partner_id integer not null,
    seller_id  integer not null,
    work_ymd   date    not null,
    sku_id     integer not null,
    sku_cnt    integer,
    michul_etc varchar(500),
    cre_user   varchar(100) default 'SYSTEM'::character varying,
    cre_tm     timestamp    default now(),
    upd_user   varchar(100) default 'SYSTEM'::character varying,
    upd_tm     timestamp    default now(),
    del_yn     char         default 'N'::bpchar
);

comment on table tb_michul is '미출';

comment on column tb_michul.id is '아이디(PK)';

comment on column tb_michul.partner_id is '파트너ID';

comment on column tb_michul.seller_id is '판매처ID';

comment on column tb_michul.work_ymd is '영업일자';

comment on column tb_michul.sku_id is 'SKU_ID';

comment on column tb_michul.sku_cnt is '수량';

comment on column tb_michul.michul_etc is '비고';

comment on column tb_michul.cre_user is '등록자';

comment on column tb_michul.cre_tm is '등록_일시';

comment on column tb_michul.upd_user is '수정자';

comment on column tb_michul.upd_tm is '수정_일시';

comment on column tb_michul.del_yn is '삭제_여부';

alter table tb_michul
    owner to binblur2024;

-- auto-generated definition
create table tb_money
(
    id         serial
        primary key,
    partner_id integer not null,
    work_ymd   date    not null,
    won_50000  integer not null,
    won_10000  integer not null,
    won_5000   integer not null,
    won_1000   integer not null,
    won_500    integer not null,
    won_100    integer not null,
    won_etc    integer not null,
    won_tot    integer not null,
    sett_50000 integer not null,
    sett_10000 integer not null,
    sett_5000  integer not null,
    sett_1000  integer not null,
    sett_500   integer not null,
    sett_100   integer not null,
    sett_etc   integer not null,
    sett_tot   integer not null,
    cre_user   varchar(100) default 'SYSTEM'::character varying,
    cre_tm     timestamp    default now(),
    upd_user   varchar(100) default 'SYSTEM'::character varying,
    upd_tm     timestamp    default now(),
    del_yn     char         default 'N'::bpchar,
    bef_id     integer      default 0
);

comment on table tb_money is '시제';

comment on column tb_money.id is '아이디(PK)';

comment on column tb_money.partner_id is '파트너ID';

comment on column tb_money.work_ymd is '영업일자';

comment on column tb_money.won_50000 is '오만원';

comment on column tb_money.won_10000 is '만원';

comment on column tb_money.won_5000 is '오천원';

comment on column tb_money.won_1000 is '천원';

comment on column tb_money.won_500 is '오백원';

comment on column tb_money.won_100 is '백원';

comment on column tb_money.won_etc is '돈_기타';

comment on column tb_money.won_tot is '돈통총금액';

comment on column tb_money.sett_50000 is '정산오만원';

comment on column tb_money.sett_10000 is '정산만원';

comment on column tb_money.sett_5000 is '정산오천원';

comment on column tb_money.sett_1000 is '정산천원';

comment on column tb_money.sett_500 is '정산오백원';

comment on column tb_money.sett_100 is '정산백원';

comment on column tb_money.sett_etc is '정산_기타';

comment on column tb_money.sett_tot is '정산_합계';

comment on column tb_money.cre_user is '등록자';

comment on column tb_money.cre_tm is '등록_일시';

comment on column tb_money.upd_user is '수정자';

comment on column tb_money.upd_tm is '수정_일시';

comment on column tb_money.del_yn is '삭제_여부';

comment on column tb_money.bef_id is '변경전ID';

alter table tb_money
    owner to binblur2024;


-- auto-generated definition
create table tb_notice
(
    id          serial
        primary key,
    notice_cd   char(5)       not null,
    title       varchar(1000) not null,
    notice_cntn varchar(4000),
    move_uri    varchar(1000),
    auth_cds    varchar(100),
    cre_user    varchar(100) default 'SYSTEM'::character varying,
    cre_tm      date         default now(),
    upd_user    varchar(100) default 'SYSTEM'::character varying,
    upd_tm      date         default now(),
    del_yn      char         default 'N'::bpchar,
    read_cnt    integer
);

comment on table tb_notice is '공지';

comment on column tb_notice.id is '아이디(PK)';

comment on column tb_notice.notice_cd is '공지구분';

comment on column tb_notice.title is '제목';

comment on column tb_notice.notice_cntn is '내용';

comment on column tb_notice.move_uri is '이동URL';

comment on column tb_notice.auth_cds is '열람권한';

comment on column tb_notice.cre_user is '등록자';

comment on column tb_notice.cre_tm is '등록일시';

comment on column tb_notice.upd_user is '수정자';

comment on column tb_notice.upd_tm is '수정일시';

comment on column tb_notice.del_yn is '삭제여부';

comment on column tb_notice.read_cnt is '조회수';

alter table tb_notice
    owner to binblur2024;

-- auto-generated definition
create table tb_old_misong
(
    id         serial
        primary key,
    partner_id integer      not null,
    tran_ymd   date         not null,
    seller_nm  varchar(500) not null,
    chit_no    integer,
    pum_bun    varchar(4000),
    prod_nm    varchar(500),
    color      varchar(100),
    size       varchar(100),
    deal_amt   integer,
    comp_yn    char         default 'N'::bpchar,
    misong_cnt integer,
    send_cnt   integer,
    remain_cnt integer,
    remain_amt integer,
    etc_cntn   varchar(4000),
    cre_user   varchar(100) default 'SYSTEM'::character varying,
    cre_tm     date         default now(),
    upd_user   varchar(100) default 'SYSTEM'::character varying,
    upd_tm     date         default now(),
    del_yn     char         default 'N'::bpchar
);

comment on table tb_old_misong is '과거이력 미송';

comment on column tb_old_misong.id is '아이디(PK)';

comment on column tb_old_misong.partner_id is '파트너ID';

comment on column tb_old_misong.tran_ymd is '전송일자';

comment on column tb_old_misong.seller_nm is '판매처명';

comment on column tb_old_misong.chit_no is '영수증번호';

comment on column tb_old_misong.pum_bun is '품번';

comment on column tb_old_misong.prod_nm is '상품명';

comment on column tb_old_misong.color is '색상';

comment on column tb_old_misong.size is '사이즈';

comment on column tb_old_misong.deal_amt is '거래금액';

comment on column tb_old_misong.comp_yn is '완료여부';

comment on column tb_old_misong.misong_cnt is '미송량';

comment on column tb_old_misong.send_cnt is '전송량';

comment on column tb_old_misong.remain_cnt is '잔여량';

comment on column tb_old_misong.remain_amt is '잔여금액';

comment on column tb_old_misong.etc_cntn is '비고';

comment on column tb_old_misong.cre_user is '등록자';

comment on column tb_old_misong.cre_tm is '등록_일시';

comment on column tb_old_misong.upd_user is '수정자';

comment on column tb_old_misong.upd_tm is '수정_일시';

comment on column tb_old_misong.del_yn is '삭제_여부';

alter table tb_old_misong
    owner to binblur2024;

-- auto-generated definition
create table tb_old_sale
(
    id         serial,
    partner_id integer not null,
    tran_ymd   date,
    seller_nm  varchar(500),
    chit_no    integer,
    gubun      varchar(100),
    pum_bun    varchar(4000),
    prod_nm    varchar(500),
    color      varchar(100),
    size       varchar(100),
    user_nm    varchar(100),
    in_amt     integer,
    deal_amt   integer,
    dan_dc     integer,
    sale_cnt   integer,
    sale_amt   integer,
    return_cnt integer,
    return_amt integer,
    cre_user   varchar(100) default 'SYSTEM'::character varying,
    cre_tm     date         default now(),
    upd_user   varchar(100) default 'SYSTEM'::character varying,
    upd_tm     date         default now(),
    del_yn     char         default 'N'::bpchar
);

comment on table tb_old_sale is '과거이력 판매원장';

comment on column tb_old_sale.id is '아이디(PK)';

comment on column tb_old_sale.partner_id is '파트너ID';

comment on column tb_old_sale.tran_ymd is '거래일자';

comment on column tb_old_sale.seller_nm is '판매처명';

comment on column tb_old_sale.chit_no is '전표번호';

comment on column tb_old_sale.gubun is '구분 (판매/반품 등)';

comment on column tb_old_sale.pum_bun is '품번';

comment on column tb_old_sale.prod_nm is '상품명';

comment on column tb_old_sale.color is '색상';

comment on column tb_old_sale.size is '사이즈';

comment on column tb_old_sale.user_nm is '사용자명';

comment on column tb_old_sale.in_amt is '입고가';

comment on column tb_old_sale.deal_amt is '거래단가';

comment on column tb_old_sale.dan_dc is '단가 dc';

comment on column tb_old_sale.sale_cnt is '판매수량';

comment on column tb_old_sale.sale_amt is '판매금액';

comment on column tb_old_sale.return_cnt is '반품수량';

comment on column tb_old_sale.return_amt is '반품금액';

comment on column tb_old_sale.cre_user is '등록자';

comment on column tb_old_sale.cre_tm is '등록일시';

comment on column tb_old_sale.upd_user is '수정자';

comment on column tb_old_sale.upd_tm is '수정일시';

comment on column tb_old_sale.del_yn is '삭제여부 (Y/N)';

alter table tb_old_sale
    owner to binblur2024;

-- auto-generated definition
create table tb_old_sample
(
    id          serial
        primary key,
    partner_id  integer      not null,
    tran_ymd    date         not null,
    seller_nm   varchar(500) not null,
    chit_no     integer,
    pum_bun     varchar(4000),
    prod_nm     varchar(500),
    color       varchar(100),
    size        varchar(100),
    deal_amt    integer,
    comp_yn     char         default 'N'::bpchar,
    sample_cnt  integer,
    collect_cnt integer,
    remain_cnt  integer,
    remain_amt  integer,
    etc_cntn    varchar(4000),
    cre_user    varchar(100) default 'SYSTEM'::character varying,
    cre_tm      date         default now(),
    upd_user    varchar(100) default 'SYSTEM'::character varying,
    upd_tm      date         default now(),
    del_yn      char         default 'N'::bpchar
);

comment on table tb_old_sample is '과거이역 샘플회수';

comment on column tb_old_sample.id is '아이디(PK)';

comment on column tb_old_sample.partner_id is '파트너ID';

comment on column tb_old_sample.tran_ymd is '샘플일';

comment on column tb_old_sample.seller_nm is '판매처';

comment on column tb_old_sample.chit_no is '전표번호';

comment on column tb_old_sample.pum_bun is '품번';

comment on column tb_old_sample.prod_nm is '품명';

comment on column tb_old_sample.color is '칼라';

comment on column tb_old_sample.size is '사이즈';

comment on column tb_old_sample.deal_amt is '거래단가';

comment on column tb_old_sample.comp_yn is '완료';

comment on column tb_old_sample.sample_cnt is '샘플 수량';

comment on column tb_old_sample.collect_cnt is '회수 수량';

comment on column tb_old_sample.remain_cnt is '잔량 수량';

comment on column tb_old_sample.remain_amt is '잔량 금액';

comment on column tb_old_sample.etc_cntn is '비고';

comment on column tb_old_sample.cre_user is '등록자';

comment on column tb_old_sample.cre_tm is '등록일자';

comment on column tb_old_sample.upd_user is '수정자';

comment on column tb_old_sample.upd_tm is '수정일자';

comment on column tb_old_sample.del_yn is '삭제 여부';

alter table tb_old_sample
    owner to binblur2024;

-- auto-generated definition
create table tb_old_stock
(
    id         serial
        primary key,
    partner_id integer not null,
    tran_ymd   date,
    stock_nm   varchar(500),
    chit_no    integer,
    gubun      varchar(100),
    pum_bun    varchar(4000),
    prod_nm    varchar(500),
    color      varchar(100),
    size       varchar(100),
    deal_amt   integer,
    dan_dc     integer,
    stock_cnt  integer,
    stock_amt  integer,
    return_cnt integer,
    return_amt integer,
    cre_user   varchar(100) default 'SYSTEM'::character varying,
    cre_tm     date         default now(),
    upd_user   varchar(100) default 'SYSTEM'::character varying,
    upd_tm     date         default now(),
    del_yn     char         default 'N'::bpchar
);

comment on table tb_old_stock is '과거이력 입고원장';

comment on column tb_old_stock.id is 'ID';

comment on column tb_old_stock.partner_id is '파트너ID';

comment on column tb_old_stock.tran_ymd is '거래일';

comment on column tb_old_stock.stock_nm is '입고처';

comment on column tb_old_stock.chit_no is '전표번호';

comment on column tb_old_stock.gubun is '구분';

comment on column tb_old_stock.pum_bun is '품번';

comment on column tb_old_stock.prod_nm is '품명';

comment on column tb_old_stock.color is '칼라';

comment on column tb_old_stock.size is '사이즈';

comment on column tb_old_stock.deal_amt is '거래단가';

comment on column tb_old_stock.dan_dc is '단가DC';

comment on column tb_old_stock.stock_cnt is '입고량';

comment on column tb_old_stock.stock_amt is '입고금액';

comment on column tb_old_stock.return_cnt is '반출량';

comment on column tb_old_stock.return_amt is '반출금액';

comment on column tb_old_stock.cre_user is '등록자';

comment on column tb_old_stock.cre_tm is '등록일자';

comment on column tb_old_stock.upd_user is '수정자';

comment on column tb_old_stock.upd_tm is '수정일자';

comment on column tb_old_stock.del_yn is '삭제 여부';

alter table tb_old_stock
    owner to binblur2024;

-- auto-generated definition
create table tb_order
(
    id              serial
        primary key,
    partner_id      integer                not null,
    seller_id       integer                not null,
    seller_nm       varchar(100)           not null,
    work_ymd        date                   not null,
    chit_no         integer                not null,
    order_cd        char                   not null,
    tot_sku_cnt     integer,
    tot_order_amt   integer,
    pay_type        char                   not null,
    bundle_yn       char                   not null,
    hold_yn         char                   not null,
    vat_yn          char                   not null,
    jang_ggi_cnt    integer      default 0 not null,
    order_stat_cd   char(2)                not null,
    etc_print_yn    char         default 'N'::bpchar,
    order_etc       varchar(4000),
    sample_order_id integer,
    cust_stat_cd    varchar(50),
    logis_cd        varchar(50),
    logis_amt       integer,
    on_site_yn      char         default 'N'::bpchar,
    cre_user        varchar(100) default 'SYSTEM'::character varying,
    cre_tm          timestamp    default now(),
    upd_user        varchar(100) default 'SYSTEM'::character varying,
    upd_tm          timestamp    default now(),
    del_yn          char         default 'N'::bpchar,
    bef_id          integer      default 0
);

comment on table tb_order is '주문';

comment on column tb_order.id is '아이디(PK)';

comment on column tb_order.partner_id is '파트너ID';

comment on column tb_order.seller_id is '소매처ID';

comment on column tb_order.seller_nm is '소매처명';

comment on column tb_order.work_ymd is '영업일자';

comment on column tb_order.chit_no is '전표번호';

comment on column tb_order.order_cd is '주문분류';

comment on column tb_order.tot_sku_cnt is '전체수량';

comment on column tb_order.tot_order_amt is '전체금액';

comment on column tb_order.pay_type is '입금유형';

comment on column tb_order.bundle_yn is '묶음여부';

comment on column tb_order.hold_yn is '보류여부';

comment on column tb_order.vat_yn is '부가세여부';

comment on column tb_order.jang_ggi_cnt is '장끼발행건수';

comment on column tb_order.order_stat_cd is '주문상태';

comment on column tb_order.etc_print_yn is '비고출력여부';

comment on column tb_order.order_etc is '비고';

comment on column tb_order.sample_order_id is '샘플주문ID';

comment on column tb_order.cust_stat_cd is '사용자상태';

comment on column tb_order.logis_cd is '물류바코드';

comment on column tb_order.logis_amt is '물류비';

comment on column tb_order.on_site_yn is '매장판매여부';

comment on column tb_order.cre_user is '등록자';

comment on column tb_order.cre_tm is '등록_시간';

comment on column tb_order.upd_user is '수정자';

comment on column tb_order.upd_tm is '수정_시간';

comment on column tb_order.del_yn is '삭제_여부';

comment on column tb_order.bef_id is '테이블ID(PK)이력참조키';

alter table tb_order
    owner to binblur2024;

-- auto-generated definition
create table tb_order_det
(
    id            serial
        primary key,
    order_id      integer                          not null,
    sku_id        integer                          not null,
    sku_nm        varchar(200)                     not null,
    order_seq     integer                          not null,
    order_det_cd  bpchar                           not null,
    base_amt      integer                          not null,
    real_amt      integer,
    tot_amt       integer                          not null,
    dc_amt        integer                          not null,
    sku_cnt       integer                          not null,
    order_det_etc varchar(500),
    finish_yn     char         default 'N'::bpchar not null,
    cre_user      varchar(100) default 'SYSTEM'::character varying,
    cre_tm        timestamp    default now(),
    upd_user      varchar(100) default 'SYSTEM'::character varying,
    upd_tm        timestamp    default now(),
    del_yn        char         default 'N'::bpchar,
    bef_id        integer      default 0
);

comment on table tb_order_det is '주문상세';

comment on column tb_order_det.id is '아이디(PK)';

comment on column tb_order_det.order_id is '주문ID';

comment on column tb_order_det.sku_id is '스큐ID';

comment on column tb_order_det.sku_nm is '스큐이름';

comment on column tb_order_det.order_seq is '순서';

comment on column tb_order_det.order_det_cd is '주문상세코드';

comment on column tb_order_det.base_amt is '단가';

comment on column tb_order_det.real_amt is '실제금액';

comment on column tb_order_det.tot_amt is '총금액';

comment on column tb_order_det.dc_amt is 'DC금액';

comment on column tb_order_det.sku_cnt is '수량';

comment on column tb_order_det.order_det_etc is '비고';

comment on column tb_order_det.finish_yn is '완료여부';

comment on column tb_order_det.cre_user is '등록자';

comment on column tb_order_det.cre_tm is '등록시간';

comment on column tb_order_det.upd_user is '수정자';

comment on column tb_order_det.upd_tm is '수정시간';

comment on column tb_order_det.del_yn is '삭제여부';

comment on column tb_order_det.bef_id is '테이블ID(PK)이력참조키';

alter table tb_order_det
    owner to binblur2024;

create index tb_order_det_sku_id_idx
    on tb_order_det (sku_id);

-- auto-generated definition
create table tb_order_det_hist
(
    id           integer not null,
    order_id     integer not null,
    sku_id       integer not null,
    tran_seq     integer not null,
    order_seq    integer not null,
    order_det_cd bpchar  not null,
    base_amt     integer not null,
    tot_amt      integer not null,
    dc_amt       integer not null,
    tran_cnt     integer not null,
    sku_cnt      integer not null,
    cre_user     varchar(100) default 'SYSTEM'::character varying,
    cre_tm       timestamp    default now()
);

comment on table tb_order_det_hist is '과거이력 주문상세';

comment on column tb_order_det_hist.id is '아이디(PK)';

comment on column tb_order_det_hist.order_id is '주문ID';

comment on column tb_order_det_hist.sku_id is 'SKU ID';

comment on column tb_order_det_hist.tran_seq is '거래순번';

comment on column tb_order_det_hist.order_seq is '주문순번';

comment on column tb_order_det_hist.order_det_cd is '주문상세코드';

comment on column tb_order_det_hist.base_amt is '기본금액';

comment on column tb_order_det_hist.tot_amt is '총금액';

comment on column tb_order_det_hist.dc_amt is '할인금액';

comment on column tb_order_det_hist.tran_cnt is '거래수량';

comment on column tb_order_det_hist.sku_cnt is 'SKU 수량';

comment on column tb_order_det_hist.cre_user is '등록자';

comment on column tb_order_det_hist.cre_tm is '등록일시';

alter table tb_order_det_hist
    owner to binblur2024;

-- auto-generated definition
create table tb_order_hist
(
    id              integer not null,
    partner_id      integer,
    seller_id       integer,
    work_ymd        date    not null,
    chit_no         integer,
    order_cd        char(5) not null,
    pay_type        char    not null,
    bundle_yn       char    not null,
    hold_yn         char    not null,
    vat_yn          char    not null,
    jang_ggi_cnt    integer,
    order_stat_cd   bpchar  not null,
    order_etc       varchar(4000),
    sample_order_id integer,
    cust_stat_cd    varchar(50),
    logis_cd        varchar(50),
    logis_amt       integer,
    cre_user        varchar(100) default 'SYSTEM'::character varying,
    cre_tm          timestamp    default now()
);

comment on table tb_order_hist is '과거이력 주문';

comment on column tb_order_hist.id is '아이디(PK)';

comment on column tb_order_hist.partner_id is '파트너ID';

comment on column tb_order_hist.seller_id is '판매자ID';

comment on column tb_order_hist.work_ymd is '작업일자';

comment on column tb_order_hist.chit_no is '전표번호';

comment on column tb_order_hist.order_cd is '주문코드';

comment on column tb_order_hist.pay_type is '결제유형';

comment on column tb_order_hist.bundle_yn is '번들여부';

comment on column tb_order_hist.hold_yn is '보류여부';

comment on column tb_order_hist.vat_yn is '부가세여부';

comment on column tb_order_hist.jang_ggi_cnt is '장기기한수';

comment on column tb_order_hist.order_stat_cd is '주문상태코드';

comment on column tb_order_hist.order_etc is '주문기타정보';

comment on column tb_order_hist.sample_order_id is '샘플주문ID';

comment on column tb_order_hist.cust_stat_cd is '고객상태코드';

comment on column tb_order_hist.logis_cd is '배송코드';

comment on column tb_order_hist.logis_amt is '배송비';

comment on column tb_order_hist.cre_user is '등록자';

comment on column tb_order_hist.cre_tm is '등록일시';

alter table tb_order_hist
    owner to binblur2024;


-- auto-generated definition
create table tb_partner
(
    id               integer       default nextval('tb_partner_id_seq'::regclass) not null
        primary key,
    upper_partner_id integer                                                      not null,
    file_id          integer,
    logis_id         integer                                                      not null,
    partner_nm       varchar(100)                                                 not null,
    partner_eng_nm   varchar(100),
    short_nm         varchar(10)   default NULL::character varying,
    partner_tel_no   varchar(30)                                                  not null,
    rep_nm           varchar(100)                                                 not null,
    rep_tel_no       varchar(30)                                                  not null,
    comp_no          varchar(100)                                                 not null,
    partner_email    varchar(40)   default NULL::character varying,
    add_time         integer,
    detail_info      varchar(1000) default NULL::character varying,
    cre_user         varchar(100)  default 'SYSTEM'::character varying,
    cre_tm           timestamp     default now(),
    upd_user         varchar(100)  default 'SYSTEM'::character varying,
    upd_tm           timestamp     default now(),
    del_yn           char          default 'N'::bpchar,
    etc_acc_cntn     varchar(4000),
    misong_yn        char          default 'N'::bpchar,
    size_info        varchar(4000),
    order_short_nm   varchar(50),
    comp_info        varchar(4000),
    gubun_info       varchar(4000),
    settl_cd         varchar(4),
    comp_prn_cd      varchar(50),
    sample_prn_yn    char,
    partner_addr     varchar(500),
    partner_addr_etc varchar(500),
    sns_type         char(2),
    sns_id           varchar(100),
    etc_cntn         varchar(4000),
    sel_gb1_cntn     varchar(4000),
    sel_gb2_cntn     varchar(4000),
    fac_gb1_cntn     varchar(4000),
    fac_gb2_cntn     varchar(4000)
);

comment on table tb_partner is '화주';

comment on column tb_partner.id is '아이디 (PK)';

comment on column tb_partner.upper_partner_id is '상위파트너ID (FK)';

comment on column tb_partner.file_id is '파일ID (FK)';

comment on column tb_partner.logis_id is '창고ID (FK)';

comment on column tb_partner.partner_nm is '회사명';

comment on column tb_partner.partner_eng_nm is '회사영문명';

comment on column tb_partner.short_nm is '약어';

comment on column tb_partner.partner_tel_no is '회사_전화_번호';

comment on column tb_partner.rep_nm is '대표자_명';

comment on column tb_partner.rep_tel_no is '대표자_전화_번호';

comment on column tb_partner.comp_no is '사업자_번호';

comment on column tb_partner.partner_email is '회사이메일';

comment on column tb_partner.add_time is '시스템설정시간';

comment on column tb_partner.detail_info is '상세_정보';

comment on column tb_partner.cre_user is '등록자';

comment on column tb_partner.cre_tm is '등록_일시';

comment on column tb_partner.upd_user is '수정자';

comment on column tb_partner.upd_tm is '수정_일시';

comment on column tb_partner.del_yn is '삭제_여부';

comment on column tb_partner.etc_acc_cntn is '비고(계좌)';

comment on column tb_partner.misong_yn is '미송승인여부';

comment on column tb_partner.size_info is '사이즈정보';

comment on column tb_partner.order_short_nm is '제작약어';

comment on column tb_partner.comp_info is '혼용율정보';

comment on column tb_partner.gubun_info is '구분정보';

comment on column tb_partner.settl_cd is '정산코드';

comment on column tb_partner.comp_prn_cd is '혼용율인쇄코드';

comment on column tb_partner.sample_prn_yn is '샘플동일상품출력여부';

comment on column tb_partner.partner_addr is '주소';

comment on column tb_partner.partner_addr_etc is '주소기타';

comment on column tb_partner.sns_type is 'SNS_TYPE';

comment on column tb_partner.sns_id is 'SNS_ID';

comment on column tb_partner.etc_cntn is '기타';

comment on column tb_partner.sel_gb1_cntn is '셀러구분내용1';

comment on column tb_partner.sel_gb2_cntn is '셀러구분내용2';

comment on column tb_partner.fac_gb1_cntn is '공장구분내용1';

comment on column tb_partner.fac_gb2_cntn is '공장구분내용2';

alter table tb_partner
    owner to binblur2024;

-- auto-generated definition
create table tb_partner_code
(
    id           serial
        primary key,
    partner_id   integer       not null,
    code_upper   varchar(50)   not null,
    code_cd      varchar(50)   not null,
    code_nm      varchar(1000) not null,
    def_code_val varchar(100),
    code_desc    varchar(1000),
    code_order   integer       not null,
    cre_user     varchar(100) default 'SYSTEM'::character varying,
    cre_tm       timestamp    default now(),
    upd_user     varchar(100) default 'SYSTEM'::character varying,
    upd_tm       timestamp    default now(),
    del_yn       char         default 'N'::bpchar,
    code_etc     varchar(500)
);

comment on table tb_partner_code is '파트너 코드 테이블';

comment on column tb_partner_code.id is '아이디 (PK)';

comment on column tb_partner_code.partner_id is '파트너ID (FK)';

comment on column tb_partner_code.code_upper is '상위_코드';

comment on column tb_partner_code.code_cd is '코드';

comment on column tb_partner_code.code_nm is '코드명';

comment on column tb_partner_code.def_code_val is '기본값';

comment on column tb_partner_code.code_desc is '코드_설명';

comment on column tb_partner_code.code_order is '코드_순서';

comment on column tb_partner_code.cre_user is '등록자';

comment on column tb_partner_code.cre_tm is '등록_일시';

comment on column tb_partner_code.upd_user is '수정자';

comment on column tb_partner_code.upd_tm is '수정_일시';

comment on column tb_partner_code.del_yn is '삭제_여부';

alter table tb_partner_code
    owner to binblur2024;

-- auto-generated definition
create table tb_partner_print
(
    id            serial
        primary key,
    partner_id    integer       not null,
    file_id       integer,
    logo_print_yn char          not null,
    logo_loc_cd   char,
    title_yn      char,
    title_mng     varchar(4000) not null,
    title_nor     varchar(4000) not null,
    top_yn        char,
    top_mng       varchar(4000) not null,
    top_nor       varchar(4000),
    bottom_yn     char,
    bottom_mng    varchar(4000),
    bottom_nor    varchar(4000) not null,
    cre_user      varchar(100) default 'SYSTEM'::character varying,
    cre_tm        timestamp    default now(),
    upd_user      varchar(100) default 'SYSTEM'::character varying,
    upd_tm        timestamp    default now(),
    del_yn        char         default 'N'::bpchar
);

comment on table tb_partner_print is '파트너 인쇄 테이블';

comment on column tb_partner_print.id is '아이디 (PK)';

comment on column tb_partner_print.partner_id is '파트너ID (FK)';

comment on column tb_partner_print.file_id is '파일ID (FK)';

comment on column tb_partner_print.logo_print_yn is '이미지인쇄여부';

comment on column tb_partner_print.logo_loc_cd is '로고위치';

comment on column tb_partner_print.title_yn is '타이틀_여부';

comment on column tb_partner_print.title_mng is '타이틀_관리';

comment on column tb_partner_print.title_nor is '타이틀_일반';

comment on column tb_partner_print.top_yn is '상단_여부';

comment on column tb_partner_print.top_mng is '상단_관리';

comment on column tb_partner_print.top_nor is '상단_일반';

comment on column tb_partner_print.bottom_yn is '하단_여부';

comment on column tb_partner_print.bottom_mng is '하단_관리';

comment on column tb_partner_print.bottom_nor is '하단_일반';

comment on column tb_partner_print.cre_user is '등록자';

comment on column tb_partner_print.cre_tm is '등록_일시';

comment on column tb_partner_print.upd_user is '수정자';

comment on column tb_partner_print.upd_tm is '수정_일시';

comment on column tb_partner_print.del_yn is '삭제_여부';

alter table tb_partner_print
    owner to binblur2024;

-- auto-generated definition
create table tb_pay
(
    id            serial
        primary key,
    partner_id    integer                not null,
    seller_id     integer                not null,
    seller_nm     varchar(100)           not null,
    order_id      integer,
    inout_cd      char                   not null,
    pay_method_cd char                   not null,
    work_ymd      date                   not null,
    tran_ymd      date                   not null,
    cash_amt      integer                not null,
    account_amt   integer                not null,
    return_amt    integer                not null,
    tot_amt       integer                not null,
    discount_amt  integer                not null,
    pay_amt       integer                not null,
    first_bef_amt integer      default 0 not null,
    bef_amt       integer      default 0 not null,
    chit_no       integer                not null,
    cust_stat_cd  varchar(10),
    etc_print_yn  char,
    pay_etc       varchar(500),
    cre_user      varchar(100) default 'SYSTEM'::character varying,
    cre_tm        timestamp    default now(),
    upd_user      varchar(100) default 'SYSTEM'::character varying,
    upd_tm        timestamp    default now(),
    del_yn        char         default 'N'::bpchar,
    bef_id        integer      default 0,
    org_work_ymd  date
);

comment on table tb_pay is '입출금(이력존재)';

comment on column tb_pay.id is '아이디 (PK)';

comment on column tb_pay.partner_id is '파트너ID (FK)';

comment on column tb_pay.seller_id is '판매처ID (FK)';

comment on column tb_pay.seller_nm is '판매처이름';

comment on column tb_pay.order_id is '주문ID (FK)';

comment on column tb_pay.inout_cd is '입출구분';

comment on column tb_pay.pay_method_cd is '결제방법';

comment on column tb_pay.work_ymd is '영업일자';

comment on column tb_pay.tran_ymd is '거래일시';

comment on column tb_pay.cash_amt is '현금입금';

comment on column tb_pay.account_amt is '통장입금';

comment on column tb_pay.return_amt is '입금예정금액';

comment on column tb_pay.tot_amt is '총금액';

comment on column tb_pay.discount_amt is '할인금액';

comment on column tb_pay.pay_amt is '결제금액';

comment on column tb_pay.first_bef_amt is '최초전잔';

comment on column tb_pay.bef_amt is '전잔';

comment on column tb_pay.chit_no is '전표번호';

comment on column tb_pay.cust_stat_cd is '사용자상태';

comment on column tb_pay.etc_print_yn is '비고출력여부';

comment on column tb_pay.pay_etc is '비고 (영수증출력시)';

comment on column tb_pay.cre_user is '등록자';

comment on column tb_pay.cre_tm is '등록_일시';

comment on column tb_pay.upd_user is '수정자';

comment on column tb_pay.upd_tm is '수정_일시';

comment on column tb_pay.del_yn is '삭제_여부';

comment on column tb_pay.bef_id is '변경전ID (기샘플주문ID)';

comment on column tb_pay.org_work_ymd is '원래 work_ymd(기산일 처리 아니면 work_ymd 와동일)';

alter table tb_pay
    owner to binblur2024;

-- auto-generated definition
create table tb_pay_hist
(
    id            integer not null,
    partner_id    integer not null,
    seller_id     integer not null,
    order_id      integer,
    inout_cd      char    not null,
    pay_method_cd char    not null,
    work_ymd      date    not null,
    cash_amt      integer not null,
    account_amt   integer not null,
    return_amt    integer not null,
    tot_amt       integer not null,
    discount_amt  integer not null,
    pay_amt       integer not null,
    first_bef_amt integer,
    bef_amt       integer      default 0,
    cre_user      varchar(100) default 'SYSTEM'::character varying,
    cre_tm        timestamp    default now()
);

comment on table tb_pay_hist is '입출금 이력';

comment on column tb_pay_hist.id is '아이디 (PK)';

comment on column tb_pay_hist.partner_id is '파트너ID (FK)';

comment on column tb_pay_hist.seller_id is '판매처ID (FK)';

comment on column tb_pay_hist.order_id is '주문ID';

comment on column tb_pay_hist.inout_cd is '입출구분';

comment on column tb_pay_hist.pay_method_cd is '결제방법';

comment on column tb_pay_hist.work_ymd is '영업일자';

comment on column tb_pay_hist.cash_amt is '현금입금';

comment on column tb_pay_hist.account_amt is '통장입금';

comment on column tb_pay_hist.return_amt is '입금예정금액';

comment on column tb_pay_hist.tot_amt is '총금액';

comment on column tb_pay_hist.discount_amt is '할인금액';

comment on column tb_pay_hist.pay_amt is '결제금액';

comment on column tb_pay_hist.first_bef_amt is '최초전잔';

comment on column tb_pay_hist.bef_amt is '전잔 (기본값 0)';

comment on column tb_pay_hist.cre_user is '등록자';

comment on column tb_pay_hist.cre_tm is '등록_일시';

alter table tb_pay_hist
    owner to binblur2024;

-- auto-generated definition
create table tb_prod
(
    id           serial
        primary key,
    prod_cd      varchar(30)  not null,
    partner_id   integer,
    prod_nm      varchar(100) not null,
    file_id      integer,
    form_yn      char         default NULL::bpchar,
    cre_user     varchar(100) default 'SYSTEM'::character varying,
    cre_tm       timestamp    default now(),
    upd_user     varchar(100) default 'SYSTEM'::character varying,
    upd_tm       timestamp    default now(),
    del_yn       char         default 'N'::bpchar,
    img_file_id  integer,
    user_prod_cd varchar(100)
);

comment on table tb_prod is '상품정보';

comment on column tb_prod.id is '아이디';

comment on column tb_prod.prod_cd is '상품코드';

comment on column tb_prod.partner_id is '파트너ID';

comment on column tb_prod.prod_nm is '상품명';

comment on column tb_prod.file_id is '파일ID';

comment on column tb_prod.form_yn is '규칙일치여부';

comment on column tb_prod.cre_user is '등록자';

comment on column tb_prod.cre_tm is '등록시간';

comment on column tb_prod.upd_user is '수정자';

comment on column tb_prod.upd_tm is '수정시간';

comment on column tb_prod.del_yn is '삭제여부';

comment on column tb_prod.img_file_id is '상품이미지ID';

comment on column tb_prod.user_prod_cd is '테이블ID(PK)이력참조키';

alter table tb_prod
    owner to binblur2024;

-- auto-generated definition
create table tb_seller
(
    id              serial
        primary key,
    partner_id      integer      not null,
    seller_nm       varchar(100) not null,
    seller_tp       char         not null,
    seller_cd       char,
    seller_fax_no   varchar(30),
    seller_addr     varchar(100),
    seller_tel_no   varchar(30),
    ceo_nm          varchar(100),
    person_nm       varchar(100),
    person_tel_no   varchar(30),
    ceo_tel_no      varchar(30),
    comp_no         varchar(100),
    etc_scr_cntn    varchar(1000),
    etc_chit_cntn   varchar(1000),
    etc_acc_cntn    varchar(1000),
    busi_type_nm    varchar(100),
    busi_sect_nm    varchar(100),
    working_day     varchar(10),
    comp_email      varchar(40),
    sleep_yn        char         default 'N'::bpchar,
    comp_prn_cd     char         default 'C'::bpchar,
    remain_yn       char         default 'N'::bpchar,
    treat_yn        char         default 'N'::bpchar,
    bill_yn         char         default 'N'::bpchar,
    vat_yn          char         default 'N'::bpchar,
    limit_amt       integer,
    purchase_amt    integer,
    now_amt         integer,
    file_id         integer,
    cre_user        varchar(100) default 'SYSTEM'::character varying,
    cre_tm          timestamp    default now(),
    upd_user        varchar(100) default 'SYSTEM'::character varying,
    upd_tm          timestamp    default now(),
    del_yn          char         default 'N'::bpchar,
    pay_agency      varchar(200),
    pay_cyl_info    varchar(100),
    gubun1          varchar(10),
    gubun2          varchar(10),
    reg_ymd         date,
    comp_nm         varchar(500),
    seller_addr_etc varchar(500),
    sns_type        char(2),
    sns_id          varchar(100),
    etc_cntn        varchar(4000),
    bef_id          integer      default 0
);

comment on table tb_seller is '소매처';

comment on column tb_seller.id is '아이디';

comment on column tb_seller.partner_id is '파트너Id';

comment on column tb_seller.seller_nm is '업체명';

comment on column tb_seller.seller_tp is '판매처_구분';

comment on column tb_seller.seller_cd is '판매처유형코드';

comment on column tb_seller.seller_fax_no is '판매처팩스번호';

comment on column tb_seller.seller_addr is '판매처주소';

comment on column tb_seller.seller_tel_no is '회사전화번호';

comment on column tb_seller.ceo_nm is '대표';

comment on column tb_seller.person_nm is '담당자';

comment on column tb_seller.person_tel_no is '담당자연락처';

comment on column tb_seller.ceo_tel_no is '대표연락처';

comment on column tb_seller.comp_no is '사업자_번호';

comment on column tb_seller.etc_scr_cntn is '비고(화면)';

comment on column tb_seller.etc_chit_cntn is '비고(전표)';

comment on column tb_seller.etc_acc_cntn is '계좌(전표)';

comment on column tb_seller.busi_type_nm is '업태';

comment on column tb_seller.busi_sect_nm is '종목';

comment on column tb_seller.working_day is '영업일';

comment on column tb_seller.comp_email is '회사이메일';

comment on column tb_seller.sleep_yn is '휴먼연부';

comment on column tb_seller.comp_prn_cd is '혼용률인쇄YN';

comment on column tb_seller.remain_yn is '잔액인쇄YN';

comment on column tb_seller.treat_yn is '처리확인YN';

comment on column tb_seller.bill_yn is '계산서YN';

comment on column tb_seller.vat_yn is '부가세YN';

comment on column tb_seller.limit_amt is '금액상한';

comment on column tb_seller.purchase_amt is '현매입액';

comment on column tb_seller.now_amt is '현잔';

comment on column tb_seller.file_id is '파일ID';

comment on column tb_seller.cre_user is '등록자';

comment on column tb_seller.cre_tm is '등록_일시';

comment on column tb_seller.upd_user is '수정자';

comment on column tb_seller.upd_tm is '수정_일시';

comment on column tb_seller.del_yn is '삭제_여부';

comment on column tb_seller.pay_agency is '결제대행업체';

comment on column tb_seller.pay_cyl_info is '결제주기';

comment on column tb_seller.gubun1 is '구분1';

comment on column tb_seller.gubun2 is '구분2';

comment on column tb_seller.reg_ymd is '등록일자';

comment on column tb_seller.comp_nm is '사업자명';

comment on column tb_seller.seller_addr_etc is '주소기타';

comment on column tb_seller.sns_type is 'sns타입';

comment on column tb_seller.sns_id is 'sns id';

comment on column tb_seller.etc_cntn is '기타';

comment on column tb_seller.bef_id is '테이블ID(PK)이력참조키';

alter table tb_seller
    owner to binblur2024;

-- auto-generated definition
create table tb_seller_spc
(
    id         serial
        primary key,
    partner_id integer not null,
    seller_id  integer not null,
    prod_id    integer not null,
    dc_amt     integer,
    cre_user   varchar(100) default 'SYSTEM'::character varying,
    cre_tm     timestamp    default now(),
    upd_user   varchar(100) default 'SYSTEM'::character varying,
    upd_tm     timestamp    default now(),
    del_yn     char         default 'N'::bpchar
);

comment on table tb_seller_spc is '판매처 특별 단가';

comment on column tb_seller_spc.id is '아이디 (PK)';

comment on column tb_seller_spc.partner_id is '파트너ID (FK)';

comment on column tb_seller_spc.seller_id is '셀러ID (FK)';

comment on column tb_seller_spc.prod_id is '상품ID (FK)';

comment on column tb_seller_spc.dc_amt is 'DC금액';

comment on column tb_seller_spc.cre_user is '등록자';

comment on column tb_seller_spc.cre_tm is '등록_일시';

comment on column tb_seller_spc.upd_user is '수정자';

comment on column tb_seller_spc.upd_tm is '수정_일시';

comment on column tb_seller_spc.del_yn is '삭제_여부';

alter table tb_seller_spc
    owner to binblur2024;

-- auto-generated definition
create table tb_sku
(
    id            serial
        primary key,
    sku_cd        varchar(50)  not null,
    prod_id       integer      not null,
    sku_nm        varchar(200) not null,
    sku_simple_nm varchar(200) default NULL::character varying,
    sku_size      varchar(10)  default NULL::character varying,
    sku_color     varchar(50)  default NULL::character varying,
    std_color_cd  char(3)      default NULL::bpchar,
    sell_amt      integer,
    mar_rate      varchar(10)  default NULL::character varying,
    org_amt       integer,
    file_id       integer,
    cre_user      varchar(100) default 'SYSTEM'::character varying,
    cre_tm        timestamp    default now(),
    upd_user      varchar(100) default 'SYSTEM'::character varying,
    upd_tm        timestamp    default now(),
    del_yn        char         default 'N'::bpchar,
    sleep_yn      char         default 'N'::bpchar,
    std_sell_amt  integer,
    design_id     integer,
    prod_attr_cd  char,
    gubun_cntn    varchar(500),
    comp_cntn     varchar(500),
    func_cd       char(3),
    func_det_cd   char(6),
    season_cd     char(4),
    seller_id     integer,
    release_ymd   date,
    min_asn_cnt   integer,
    fabric        char(4),
    design_nm     varchar(50),
    in_amt        integer,
    retail_amt    integer,
    sku_cntn      varchar(1000),
    ext_bar_code  varchar(100),
    in_bar_code   varchar(100),
    yochug        varchar(10),
    reg_ymd       date,
    bef_id        integer      default 0
);

comment on table tb_sku is '스큐';

comment on column tb_sku.id is '아이디(PK)';

comment on column tb_sku.sku_cd is '스큐코드';

comment on column tb_sku.prod_id is '상품ID(FK)';

comment on column tb_sku.sku_nm is '스큐명';

comment on column tb_sku.sku_simple_nm is '스큐초성명';

comment on column tb_sku.sku_size is '사이즈';

comment on column tb_sku.sku_color is '색상';

comment on column tb_sku.std_color_cd is '표준색상코드';

comment on column tb_sku.sell_amt is '판매가';

comment on column tb_sku.mar_rate is '마진율';

comment on column tb_sku.org_amt is '원가';

comment on column tb_sku.file_id is '파일ID(FK)';

comment on column tb_sku.cre_user is '생성자';

comment on column tb_sku.cre_tm is '생성일시';

comment on column tb_sku.upd_user is '수정자';

comment on column tb_sku.upd_tm is '수정일시';

comment on column tb_sku.del_yn is '삭제여부';

comment on column tb_sku.sleep_yn is '휴면여부';

comment on column tb_sku.std_sell_amt is '기준가';

comment on column tb_sku.design_id is '디자이너ID';

comment on column tb_sku.prod_attr_cd is '상품속성(제작여부)';

comment on column tb_sku.gubun_cntn is '구분내용';

comment on column tb_sku.comp_cntn is '혼용율';

comment on column tb_sku.func_cd is '기능분류';

comment on column tb_sku.func_det_cd is '기능세분류';

comment on column tb_sku.season_cd is '시즌';

comment on column tb_sku.seller_id is '소매ID(제작인경우)';

comment on column tb_sku.release_ymd is '출시년월일';

comment on column tb_sku.min_asn_cnt is '최소발주수량';

comment on column tb_sku.fabric is '원단코드';

comment on column tb_sku.design_nm is '디자이너명';

comment on column tb_sku.in_amt is '입고가';

comment on column tb_sku.retail_amt is '소매가';

comment on column tb_sku.sku_cntn is '비고';

comment on column tb_sku.ext_bar_code is '외부바코드';

comment on column tb_sku.in_bar_code is '내부바코드';

comment on column tb_sku.yochug is '요척';

comment on column tb_sku.reg_ymd is '등록일자';

comment on column tb_sku.bef_id is '테이블ID(PK)이력참조키';

alter table tb_sku
    owner to binblur2024;

create index tb_sku_prod_id_idx
    on tb_sku (prod_id);


-- auto-generated definition
create table tb_sku_factory
(
    id         serial
        primary key,
    factory_id integer,
    sku_id     integer,
    main_yn    char         default 'N'::bpchar,
    gagong_amt integer not null,
    etc_cntn   varchar(4000),
    cre_user   varchar(100) default 'SYSTEM'::character varying,
    cre_tm     timestamp    default now(),
    upd_user   varchar(100) default 'SYSTEM'::character varying,
    upd_tm     timestamp    default now(),
    del_yn     char         default 'N'::bpchar
);

comment on table tb_sku_factory is '스큐공장';

comment on column tb_sku_factory.id is '아이디(PK)';

comment on column tb_sku_factory.factory_id is '공장ID(FK)';

comment on column tb_sku_factory.sku_id is '스큐ID(FK)';

comment on column tb_sku_factory.main_yn is '메인여부';

comment on column tb_sku_factory.gagong_amt is '임가공비';

comment on column tb_sku_factory.etc_cntn is '비고';

comment on column tb_sku_factory.cre_user is '등록자';

comment on column tb_sku_factory.cre_tm is '등록_일시';

comment on column tb_sku_factory.upd_user is '수정자';

comment on column tb_sku_factory.upd_tm is '수정_일시';

comment on column tb_sku_factory.del_yn is '삭제_여부';

alter table tb_sku_factory
    owner to binblur2024;

-- auto-generated definition
create table tb_stock
(
    id            serial
        primary key,
    partner_id    integer,
    logis_id      integer not null,
    sku_id        integer not null,
    asn_id        integer,
    stock_cd      char,
    stock_ymd     date    not null,
    stock_cnt     integer,
    zone          char,
    stock_stat_cd char,
    stock_rsn_cd  char(2),
    stock_etc     varchar(500),
    cre_user      varchar(100) default 'SYSTEM'::character varying,
    cre_tm        date         default now(),
    upd_user      varchar(100) default 'SYSTEM'::character varying,
    upd_tm        date         default now(),
    del_yn        char         default 'N'::bpchar
);

comment on table tb_stock is '입하';

comment on column tb_stock.id is '아이디(PK)';

comment on column tb_stock.partner_id is '파트너ID';

comment on column tb_stock.logis_id is '창고ID';

comment on column tb_stock.sku_id is '스큐ID';

comment on column tb_stock.asn_id is 'ASNID';

comment on column tb_stock.stock_cd is '입하구분';

comment on column tb_stock.stock_ymd is '입하일';

comment on column tb_stock.stock_cnt is '입하수량';

comment on column tb_stock.zone is '존코드';

comment on column tb_stock.stock_stat_cd is '입하상태코드';

comment on column tb_stock.stock_rsn_cd is '입하사유코드';

comment on column tb_stock.stock_etc is '비고';

comment on column tb_stock.cre_user is '등록자';

comment on column tb_stock.cre_tm is '등록일시';

comment on column tb_stock.upd_user is '수정자';

comment on column tb_stock.upd_tm is '수정일시';

comment on column tb_stock.del_yn is '삭제여부';

alter table tb_stock
    owner to binblur2024;

-- auto-generated definition
create table tb_store_req
(
    id           serial
        primary key,
    partner_id   integer not null,
    sku_id       integer not null,
    job_det_id   integer,
    asn_id       integer,
    work_ymd     date    not null,
    sku_cnt      integer,
    store_req_cd char,
    req_stat_cd  char,
    tran_tm      timestamp,
    loss_cnt     integer,
    etc_cntn     varchar(5000),
    cre_user     varchar(100) default 'SYSTEM'::character varying,
    cre_tm       timestamp    default now(),
    upd_user     varchar(100) default 'SYSTEM'::character varying,
    upd_tm       timestamp    default now(),
    del_yn       char         default 'N'::bpchar
);

comment on table tb_store_req is '매장 요청';

comment on column tb_store_req.id is '아이디 (PK)';

comment on column tb_store_req.partner_id is '파트너ID (FK)';

comment on column tb_store_req.sku_id is '스큐ID (FK)';

comment on column tb_store_req.job_det_id is '작업상세ID (FK)';

comment on column tb_store_req.asn_id is '발주ID (FK)';

comment on column tb_store_req.work_ymd is '영업일자';

comment on column tb_store_req.sku_cnt is '수량';

comment on column tb_store_req.store_req_cd is '매장요청코드';

comment on column tb_store_req.req_stat_cd is '매장요청상태';

comment on column tb_store_req.tran_tm is '처리일시';

comment on column tb_store_req.loss_cnt is '분실개수';

comment on column tb_store_req.etc_cntn is '비고';

comment on column tb_store_req.cre_user is '등록자';

comment on column tb_store_req.cre_tm is '등록_일시';

comment on column tb_store_req.upd_user is '수정자';

comment on column tb_store_req.upd_tm is '수정_일시';

comment on column tb_store_req.del_yn is '삭제_여부';

alter table tb_store_req
    owner to binblur2024;

-- auto-generated definition
create table tb_transfer
(
    id         serial
        primary key,
    partner_id integer not null,
    trans_tp   char    not null,
    trans_json json    not null,
    result_cd  char    not null,
    result_msg varchar(4000),
    cre_user   varchar(100) default 'SYSTEM'::character varying,
    cre_tm     timestamp    default now(),
    upd_user   varchar(100) default 'SYSTEM'::character varying,
    upd_tm     timestamp    default now(),
    del_yn     char         default 'N'::bpchar
);

comment on table tb_transfer is '이관';

comment on column tb_transfer.id is '아이디 (PK)';

comment on column tb_transfer.partner_id is '파트너ID';

comment on column tb_transfer.trans_tp is '이관구분';

comment on column tb_transfer.trans_json is '이관정보';

comment on column tb_transfer.result_cd is '결과코드';

comment on column tb_transfer.result_msg is '결과메시지';

comment on column tb_transfer.cre_user is '등록자';

comment on column tb_transfer.cre_tm is '등록_일시';

comment on column tb_transfer.upd_user is '수정자';

comment on column tb_transfer.upd_tm is '수정_일시';

comment on column tb_transfer.del_yn is '삭제_여부';

alter table tb_transfer
    owner to binblur2024;

-- auto-generated definition
create table tb_user
(
    id               serial
        primary key,
    login_id         varchar(100)                     not null,
    login_pass       varchar(1000)                    not null,
    user_nm          varchar(100)                     not null,
    partner_id       integer,
    auth_cd          char(3)                          not null,
    belong_nm        varchar(100),
    dept_nm          varchar(100),
    position_nm      varchar(100),
    login_fail_cnt   integer      default 0,
    last_login_tm    timestamp,
    first_login_yn   char,
    otp_no           varchar(10),
    otp_issu_tm      timestamp,
    use_yn           char         default 'Y'::bpchar not null,
    lock_yn          char         default 'N'::bpchar not null,
    last_pass_chg_tm timestamp                        not null,
    phone_no         varchar(1000),
    otp_fail_cnt     integer      default 0,
    cre_user         varchar(100) default 'SYSTEM'::character varying,
    cre_tm           timestamp    default now(),
    upd_user         varchar(100) default 'SYSTEM'::character varying,
    upd_tm           timestamp    default now(),
    del_yn           char         default 'N'::bpchar,
    org_partner_id   integer,
    work_ymd         date,
    work_logis_id    integer,
    user_type        char                             not null
);

comment on table tb_user is '사용자 정보';

comment on column tb_user.id is '아이디(PK)';

comment on column tb_user.login_id is '로그인 아이디';

comment on column tb_user.login_pass is '로그인비밀번호';

comment on column tb_user.user_nm is '사용자명';

comment on column tb_user.partner_id is '화주아이디';

comment on column tb_user.auth_cd is '권한코드';

comment on column tb_user.belong_nm is '소속명';

comment on column tb_user.dept_nm is '부서명';

comment on column tb_user.position_nm is '직책명';

comment on column tb_user.login_fail_cnt is '로그인 실패 회수';

comment on column tb_user.last_login_tm is '최근 로그인 일시';

comment on column tb_user.first_login_yn is '로그인 실패 회수';

comment on column tb_user.otp_no is 'otp 발행번호';

comment on column tb_user.otp_issu_tm is 'otp 발생일시';

comment on column tb_user.use_yn is '사용여부';

comment on column tb_user.lock_yn is '계정잠김여부';

comment on column tb_user.last_pass_chg_tm is '최근 비밀번호 변경 일시';

comment on column tb_user.phone_no is '전화번호';

comment on column tb_user.otp_fail_cnt is 'otp 실패 회수';

comment on column tb_user.cre_user is '생성자';

comment on column tb_user.cre_tm is '등록일시';

comment on column tb_user.upd_user is '수정자';

comment on column tb_user.upd_tm is '수정일시';

comment on column tb_user.del_yn is '삭제여부';

comment on column tb_user.org_partner_id is '화주아이디';

comment on column tb_user.work_logis_id is '작업하는 물류센터 id';

comment on column tb_user.user_type is '회원 유형 (01:일반회원, 02:판매자회원)';

alter table tb_user
    owner to binblur2024;

-- auto-generated definition
create table tb_user_menu
(
    id       serial
        primary key,
    user_id  integer                          not null,
    menu_cd  varchar(4)                       not null,
    auth_yn  char         default 'N'::bpchar not null,
    cre_user varchar(100) default 'SYSTEM'::character varying,
    cre_tm   timestamp    default now(),
    upd_user varchar(100) default 'SYSTEM'::character varying,
    upd_tm   timestamp    default now(),
    del_yn   char         default 'N'::bpchar
);

comment on table tb_user_menu is '사용자 메뉴';

comment on column tb_user_menu.id is '아이디 (PK)';

comment on column tb_user_menu.user_id is '사용자 ID (FK)';

comment on column tb_user_menu.menu_cd is '메뉴 코드';

comment on column tb_user_menu.auth_yn is '권한 여부';

comment on column tb_user_menu.cre_user is '등록자';

comment on column tb_user_menu.cre_tm is '등록 일시';

comment on column tb_user_menu.upd_user is '수정자';

comment on column tb_user_menu.upd_tm is '수정 일시';

comment on column tb_user_menu.del_yn is '삭제 여부';

alter table tb_user_menu
    owner to binblur2024;

-- auto-generated definition
create table tb_user_out
(
    id               serial
        primary key,
    login_id         varchar(100)                     not null,
    login_pass       varchar(1000)                    not null,
    user_nm          varchar(100)                     not null,
    partner_id       integer,
    auth_cd          varchar(50)                      not null,
    belong_nm        varchar(100),
    dept_nm          varchar(100),
    position_nm      varchar(100),
    login_fail_cnt   integer      default 0,
    last_login_tm    timestamp,
    first_login_yn   char,
    otp_no           varchar(10),
    otp_issu_tm      timestamp,
    use_yn           char         default 'Y'::bpchar not null,
    lock_yn          char         default 'N'::bpchar not null,
    last_pass_chg_tm timestamp                        not null,
    phone_no         varchar(1000),
    otp_fail_cnt     integer      default 0,
    cre_user         varchar(100) default 'SYSTEM'::character varying,
    cre_tm           timestamp    default now(),
    upd_user         varchar(100) default 'SYSTEM'::character varying,
    upd_tm           timestamp    default now(),
    del_yn           char         default 'N'::bpchar,
    org_partner_id   integer
);

comment on column tb_user_out.id is '아이디(PK)';

comment on column tb_user_out.login_id is '로그인 아이디';

comment on column tb_user_out.login_pass is '로그인 비밀번호';

comment on column tb_user_out.user_nm is '회원명';

comment on column tb_user_out.partner_id is '화주아이디';

comment on column tb_user_out.auth_cd is '권한코드';

comment on column tb_user_out.belong_nm is '소속명';

comment on column tb_user_out.dept_nm is '부서명';

comment on column tb_user_out.position_nm is '직책명';

comment on column tb_user_out.login_fail_cnt is '로그인 실패 회수';

comment on column tb_user_out.last_login_tm is '최근 로그인 일시';

comment on column tb_user_out.first_login_yn is '로그인 실패 회수';

comment on column tb_user_out.otp_no is 'otp 발행번호';

comment on column tb_user_out.otp_issu_tm is 'otp 발생일시';

comment on column tb_user_out.use_yn is '사용여부';

comment on column tb_user_out.lock_yn is '계정잠김여부';

comment on column tb_user_out.last_pass_chg_tm is '최근 비밀번호 변경 일시';

comment on column tb_user_out.phone_no is '전화번호';

comment on column tb_user_out.otp_fail_cnt is 'otp 실패 회수';

comment on column tb_user_out.cre_user is '등록자';

comment on column tb_user_out.cre_tm is '등록일시';

comment on column tb_user_out.upd_user is '수정자';

comment on column tb_user_out.upd_tm is '수정일시';

comment on column tb_user_out.del_yn is '삭제여부';

comment on column tb_user_out.org_partner_id is '화주아이디';

alter table tb_user_out
    owner to binblur2024;

-- auto-generated definition
create table tb_vat
(
    id          serial
        primary key,
    partner_id  integer,
    seller_id   integer,
    work_ymd    date not null,
    vat_str_ymd date,
    vat_end_ymd date,
    vat_amt     integer,
    etc_cntn    varchar(1000),
    issu_yn     char,
    etc_prn_yn  char,
    cre_user    varchar(100) default 'SYSTEM'::character varying,
    cre_tm      timestamp    default now(),
    upd_user    varchar(100) default 'SYSTEM'::character varying,
    upd_tm      timestamp    default now(),
    del_yn      char         default 'N'::bpchar
);

comment on table tb_vat is '부가세 테이블';

comment on column tb_vat.id is '아이디 (PK)';

comment on column tb_vat.partner_id is '파트너ID';

comment on column tb_vat.seller_id is '판매처ID';

comment on column tb_vat.work_ymd is '영업일자';

comment on column tb_vat.vat_str_ymd is '대상기간시작';

comment on column tb_vat.vat_end_ymd is '대상기간종료';

comment on column tb_vat.vat_amt is '부가세금액';

comment on column tb_vat.etc_cntn is '비고';

comment on column tb_vat.issu_yn is '발행여부';

comment on column tb_vat.cre_user is '등록자';

comment on column tb_vat.cre_tm is '등록_일시';

comment on column tb_vat.upd_user is '수정자';

comment on column tb_vat.upd_tm is '수정_일시';

comment on column tb_vat.del_yn is '삭제_여부';

alter table tb_vat
    owner to binblur2024;

-- auto-generated definition
create table tb_vat
(
    id          serial
        primary key,
    partner_id  integer,
    seller_id   integer,
    work_ymd    date not null,
    vat_str_ymd date,
    vat_end_ymd date,
    vat_amt     integer,
    etc_cntn    varchar(1000),
    issu_yn     char,
    etc_prn_yn  char,
    cre_user    varchar(100) default 'SYSTEM'::character varying,
    cre_tm      timestamp    default now(),
    upd_user    varchar(100) default 'SYSTEM'::character varying,
    upd_tm      timestamp    default now(),
    del_yn      char         default 'N'::bpchar
);

comment on table tb_vat is '부가세 테이블';

comment on column tb_vat.id is '아이디 (PK)';

comment on column tb_vat.partner_id is '파트너ID';

comment on column tb_vat.seller_id is '판매처ID';

comment on column tb_vat.work_ymd is '영업일자';

comment on column tb_vat.vat_str_ymd is '대상기간시작';

comment on column tb_vat.vat_end_ymd is '대상기간종료';

comment on column tb_vat.vat_amt is '부가세금액';

comment on column tb_vat.etc_cntn is '비고';

comment on column tb_vat.issu_yn is '발행여부';

comment on column tb_vat.cre_user is '등록자';

comment on column tb_vat.cre_tm is '등록_일시';

comment on column tb_vat.upd_user is '수정자';

comment on column tb_vat.upd_tm is '수정_일시';

comment on column tb_vat.del_yn is '삭제_여부';

alter table tb_vat
    owner to binblur2024;

-- auto-generated definition
create table tb_vat_hist
(
    id              integer not null,
    partner_id      integer,
    seller_id       integer,
    order_id        integer,
    issu_type       char    not null,
    work_ymd        date    not null,
    vat_chit_no     integer not null,
    vat_cash_amt    integer,
    vat_account_amt integer,
    vat_tot_amt     integer,
    etc_cntn        varchar(1000),
    issu_yn         char         default 'N'::bpchar,
    cre_user        varchar(100) default 'SYSTEM'::character varying,
    cre_tm          timestamp    default now()
);

comment on table tb_vat_hist is '부가세 이력 테이블';

comment on column tb_vat_hist.id is '아이디 (PK)';

comment on column tb_vat_hist.partner_id is '파트너ID';

comment on column tb_vat_hist.seller_id is '판매처ID';

comment on column tb_vat_hist.order_id is '주문ID';

comment on column tb_vat_hist.issu_type is '발행유형';

comment on column tb_vat_hist.work_ymd is '영업일자';

comment on column tb_vat_hist.vat_chit_no is '부가세 전표번호';

comment on column tb_vat_hist.vat_cash_amt is '현금 부가세 금액';

comment on column tb_vat_hist.vat_account_amt is '통장 부가세 금액';

comment on column tb_vat_hist.vat_tot_amt is '부가세 총 금액';

comment on column tb_vat_hist.etc_cntn is '비고';

comment on column tb_vat_hist.issu_yn is '발행 여부';

comment on column tb_vat_hist.cre_user is '등록자';

comment on column tb_vat_hist.cre_tm is '등록일시';

alter table tb_vat_hist
    owner to binblur2024;

-- auto-generated definition
create table tb_vat_inout
(
    id              serial
        primary key,
    vat_id          integer not null,
    work_ymd        date    not null,
    vat_cash_amt    integer,
    vat_account_amt integer,
    vat_dc_amt      integer,
    vat_tot_amt     integer,
    etc_cntn        varchar(1000),
    etc_prn_yn      char,
    cre_user        varchar(100) default 'SYSTEM'::character varying,
    cre_tm          timestamp    default now(),
    upd_user        varchar(100) default 'SYSTEM'::character varying,
    upd_tm          timestamp    default now(),
    del_yn          char         default 'N'::bpchar
);

comment on table tb_vat_inout is '부가세 입출금 테이블';

comment on column tb_vat_inout.id is '아이디 (PK)';

comment on column tb_vat_inout.vat_id is '부가세ID (FK)';

comment on column tb_vat_inout.work_ymd is '영업일자';

comment on column tb_vat_inout.vat_cash_amt is '현금 부가세 금액';

comment on column tb_vat_inout.vat_account_amt is '통장입금 부가세 금액';

comment on column tb_vat_inout.vat_dc_amt is '할인금액';

comment on column tb_vat_inout.vat_tot_amt is '부가세 총금액';

comment on column tb_vat_inout.etc_cntn is '비고';

comment on column tb_vat_inout.cre_user is '등록자';

comment on column tb_vat_inout.cre_tm is '등록일시';

comment on column tb_vat_inout.upd_user is '수정자';

comment on column tb_vat_inout.upd_tm is '수정일시';

comment on column tb_vat_inout.del_yn is '삭제 여부';

alter table tb_vat_inout
    owner to binblur2024;

-- auto-generated definition
create table tb_wrong
(
    id            serial
        primary key,
    job_id        integer not null,
    reg_ymd       date    not null,
    wrong_stat_cd char    not null,
    cre_user      varchar(100) default 'SYSTEM'::character varying,
    cre_tm        timestamp    default now(),
    upd_user      varchar(100) default 'SYSTEM'::character varying,
    upd_tm        timestamp    default now(),
    del_yn        char         default 'N'::bpchar
);

comment on table tb_wrong is '오출고 테이블';

comment on column tb_wrong.id is '아이디 (PK)';

comment on column tb_wrong.job_id is 'JOB_ID (FK)';

comment on column tb_wrong.reg_ymd is '등록일';

comment on column tb_wrong.wrong_stat_cd is '오출고 상태 코드';

comment on column tb_wrong.cre_user is '등록자';

comment on column tb_wrong.cre_tm is '등록일시';

comment on column tb_wrong.upd_user is '수정자';

comment on column tb_wrong.upd_tm is '수정일시';

comment on column tb_wrong.del_yn is '삭제 여부';

alter table tb_wrong
    owner to binblur2024;

-- auto-generated definition
create table tb_wrong_det
(
    id            serial
        primary key,
    wrong_id      integer not null,
    wrong_tran_cd char    not null,
    job_det_id    integer,
    sku_id        integer not null,
    job_cnt       integer not null,
    chg_cnt       integer not null,
    wrong_reason  varchar(1000),
    cre_user      varchar(100) default 'SYSTEM'::character varying,
    cre_tm        timestamp    default now(),
    upd_user      varchar(100) default 'SYSTEM'::character varying,
    upd_tm        timestamp    default now(),
    del_yn        char         default 'N'::bpchar
);

comment on table tb_wrong_det is '오출고 상세 테이블';

comment on column tb_wrong_det.id is '아이디 (PK)';

comment on column tb_wrong_det.wrong_id is '오출고 ID (FK)';

comment on column tb_wrong_det.wrong_tran_cd is '처리구분 코드';

comment on column tb_wrong_det.job_det_id is '작업 상세 ID';

comment on column tb_wrong_det.sku_id is '스큐 ID';

comment on column tb_wrong_det.job_cnt is '작업 수량';

comment on column tb_wrong_det.chg_cnt is '변경 수량';

comment on column tb_wrong_det.cre_user is '등록자';

comment on column tb_wrong_det.cre_tm is '등록일시';

comment on column tb_wrong_det.upd_user is '수정자';

comment on column tb_wrong_det.upd_tm is '수정일시';

comment on column tb_wrong_det.del_yn is '삭제 여부';

alter table tb_wrong_det
    owner to binblur2024;















