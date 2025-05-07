

CREATE TYPE public.discount_type_enum AS ENUM (
    'PERCENTAGE',
    'FIXED_AMOUNT'
);


ALTER TYPE public.discount_type_enum OWNER TO postgres;

--
-- TOC entry 930 (class 1247 OID 16956)
-- Name: order_status_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.order_status_enum AS ENUM (
    'PENDDING',
    'PROCESSING',
    'SHIPPED',
    'DELIVERED',
    'RETURNED',
    'CANCELLED'
);


ALTER TYPE public.order_status_enum OWNER TO postgres;

--
-- TOC entry 942 (class 1247 OID 17227)
-- Name: payment_methods_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.payment_methods_enum AS ENUM (
    'COD',
    'BANK_TRANSFER'
);


ALTER TYPE public.payment_methods_enum OWNER TO postgres;

--
-- TOC entry 891 (class 1247 OID 16452)
-- Name: product_status_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.product_status_enum AS ENUM (
    'AVAILABLE',
    'OUT_OF_STOCK',
    'DISCONTINUED',
    'PRE_ORDER',
    'ARCHIVED'
);


ALTER TYPE public.product_status_enum OWNER TO postgres;

--
-- TOC entry 888 (class 1247 OID 16428)
-- Name: unit_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.unit_enum AS ENUM (
    'KG',
    'G',
    'L',
    'ML',
    'BOX',
    'CAN',
    'BOTTLE',
    'PIECE',
    'BAG',
    'BUNDLE',
    'PACK'
);


ALTER TYPE public.unit_enum OWNER TO postgres;

--
-- TOC entry 936 (class 1247 OID 16974)
-- Name: voucher_status_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.voucher_status_enum AS ENUM (
    'ACTIVE',
    'EXPIRED'
);


ALTER TYPE public.voucher_status_enum OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 239 (class 1259 OID 16882)
-- Name: cart_items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cart_items (
    id integer NOT NULL,
    cart_id integer,
    product_variant_id integer,
    quantity integer,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.cart_items OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 16881)
-- Name: cart_items_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cart_items_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cart_items_id_seq OWNER TO postgres;

--
-- TOC entry 5182 (class 0 OID 0)
-- Dependencies: 238
-- Name: cart_items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cart_items_id_seq OWNED BY public.cart_items.id;


--
-- TOC entry 236 (class 1259 OID 16806)
-- Name: carts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.carts (
    id integer NOT NULL,
    user_id integer,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.carts OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 16805)
-- Name: carts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.carts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.carts_id_seq OWNER TO postgres;

--
-- TOC entry 5183 (class 0 OID 0)
-- Dependencies: 235
-- Name: carts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.carts_id_seq OWNED BY public.carts.id;


--
-- TOC entry 220 (class 1259 OID 16404)
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categories (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    parent_category_id integer,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.categories OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16403)
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categories_id_seq OWNER TO postgres;

--
-- TOC entry 5184 (class 0 OID 0)
-- Dependencies: 219
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;


--
-- TOC entry 241 (class 1259 OID 16934)
-- Name: delivery_addresses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.delivery_addresses (
    id integer NOT NULL,
    name character varying(255),
    number_phone character varying(255),
    province_id integer,
    district_id integer,
    ward_id integer,
    province_name character varying(255),
    district_name character varying(255),
    ward_name character varying(255),
    detail_address character varying(255),
    user_id integer,
    is_default boolean,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.delivery_addresses OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 16933)
-- Name: delivery_addresses_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.delivery_addresses_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.delivery_addresses_id_seq OWNER TO postgres;

--
-- TOC entry 5185 (class 0 OID 0)
-- Dependencies: 240
-- Name: delivery_addresses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.delivery_addresses_id_seq OWNED BY public.delivery_addresses.id;


--
-- TOC entry 247 (class 1259 OID 17269)
-- Name: order_items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_items (
    id integer NOT NULL,
    order_id integer,
    product_variant_id integer,
    quantity integer,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.order_items OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 17268)
-- Name: order_items_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_items_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.order_items_id_seq OWNER TO postgres;

--
-- TOC entry 5186 (class 0 OID 0)
-- Dependencies: 246
-- Name: order_items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_items_id_seq OWNED BY public.order_items.id;


--
-- TOC entry 245 (class 1259 OID 17241)
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    id integer NOT NULL,
    order_code character varying(255),
    total_price double precision NOT NULL,
    note character varying(255),
    delivery_fee double precision NOT NULL,
    expected_delivery_time timestamp without time zone,
    payment_methods character varying(255) NOT NULL,
    order_status character varying(255) NOT NULL,
    voucher_id integer,
    delivery_address_id integer,
    user_id integer,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT orders_delivery_fee_check CHECK ((delivery_fee >= ((0)::numeric)::double precision)),
    CONSTRAINT orders_total_price_check CHECK ((total_price >= ((0)::numeric)::double precision))
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 17240)
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orders_id_seq OWNER TO postgres;

--
-- TOC entry 5187 (class 0 OID 0)
-- Dependencies: 244
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- TOC entry 218 (class 1259 OID 16393)
-- Name: parent_categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.parent_categories (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    image_url character varying(255),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.parent_categories OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16392)
-- Name: parent_categories_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.parent_categories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.parent_categories_id_seq OWNER TO postgres;

--
-- TOC entry 5188 (class 0 OID 0)
-- Dependencies: 217
-- Name: parent_categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.parent_categories_id_seq OWNED BY public.parent_categories.id;


--
-- TOC entry 224 (class 1259 OID 16621)
-- Name: product_images; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_images (
    id integer NOT NULL,
    image_url character varying(255) NOT NULL,
    alt_text character varying(255),
    product_id integer,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.product_images OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16620)
-- Name: product_images_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_images_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.product_images_id_seq OWNER TO postgres;

--
-- TOC entry 5189 (class 0 OID 0)
-- Dependencies: 223
-- Name: product_images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_images_id_seq OWNED BY public.product_images.id;


--
-- TOC entry 226 (class 1259 OID 16637)
-- Name: product_variants; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_variants (
    id integer NOT NULL,
    product_id integer NOT NULL,
    name character varying(255) NOT NULL,
    price double precision NOT NULL,
    discount_percentage integer,
    unit character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    thumbnail_url character varying(255),
    expiry_date timestamp without time zone,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT product_variants_discount_percentage_check CHECK (((discount_percentage >= 0) AND (discount_percentage <= 99))),
    CONSTRAINT product_variants_price_check CHECK ((price >= ((0)::numeric)::double precision))
);


ALTER TABLE public.product_variants OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16636)
-- Name: product_variants_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_variants_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.product_variants_id_seq OWNER TO postgres;

--
-- TOC entry 5190 (class 0 OID 0)
-- Dependencies: 225
-- Name: product_variants_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_variants_id_seq OWNED BY public.product_variants.id;


--
-- TOC entry 222 (class 1259 OID 16587)
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(1000),
    thumbnail_url character varying(255),
    category_id integer,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.products OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16586)
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.products_id_seq OWNER TO postgres;

--
-- TOC entry 5191 (class 0 OID 0)
-- Dependencies: 221
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- TOC entry 227 (class 1259 OID 16697)
-- Name: products_product_images; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products_product_images (
    product_id integer NOT NULL,
    product_images_id integer NOT NULL
);


ALTER TABLE public.products_product_images OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16715)
-- Name: products_product_variants; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products_product_variants (
    product_id integer NOT NULL,
    product_variants_id integer NOT NULL
);


ALTER TABLE public.products_product_variants OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 17377)
-- Name: review_images; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.review_images (
    id integer NOT NULL,
    review_id integer,
    image_url character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.review_images OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 17376)
-- Name: review_images_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.review_images_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.review_images_id_seq OWNER TO postgres;

--
-- TOC entry 5192 (class 0 OID 0)
-- Dependencies: 250
-- Name: review_images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.review_images_id_seq OWNED BY public.review_images.id;


--
-- TOC entry 253 (class 1259 OID 17393)
-- Name: review_replies; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.review_replies (
    id integer NOT NULL,
    review_id integer,
    admin_id integer,
    reply_text text NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.review_replies OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 17392)
-- Name: review_replies_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.review_replies_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.review_replies_id_seq OWNER TO postgres;

--
-- TOC entry 5193 (class 0 OID 0)
-- Dependencies: 252
-- Name: review_replies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.review_replies_id_seq OWNED BY public.review_replies.id;


--
-- TOC entry 249 (class 1259 OID 17337)
-- Name: reviews; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reviews (
    id integer NOT NULL,
    product_id integer,
    user_id integer,
    rating integer,
    comment character varying(255),
    is_verified boolean DEFAULT false,
    likes integer DEFAULT 0,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT reviews_rating_check CHECK (((rating >= 1) AND (rating <= 5)))
);


ALTER TABLE public.reviews OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 17336)
-- Name: reviews_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reviews_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reviews_id_seq OWNER TO postgres;

--
-- TOC entry 5194 (class 0 OID 0)
-- Dependencies: 248
-- Name: reviews_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reviews_id_seq OWNED BY public.reviews.id;


--
-- TOC entry 232 (class 1259 OID 16763)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(255),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 16762)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_id_seq OWNER TO postgres;

--
-- TOC entry 5195 (class 0 OID 0)
-- Dependencies: 231
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 234 (class 1259 OID 16795)
-- Name: tokens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tokens (
    id integer NOT NULL,
    username character varying(255),
    access_token character varying(255),
    refresh_token character varying(255),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.tokens OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 16794)
-- Name: tokens_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tokens_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tokens_id_seq OWNER TO postgres;

--
-- TOC entry 5196 (class 0 OID 0)
-- Dependencies: 233
-- Name: tokens_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tokens_id_seq OWNED BY public.tokens.id;


--
-- TOC entry 237 (class 1259 OID 16844)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_roles (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.user_roles OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16747)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(255),
    password character varying(255) NOT NULL,
    full_name character varying(255),
    number_phone character varying(255),
    email character varying(255),
    provider character varying(255),
    provider_id character varying(255),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT users_provider_check CHECK (((provider)::text = ANY ((ARRAY['LOCAL'::character varying, 'GOOGLE'::character varying, 'FACEBOOK'::character varying])::text[])))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16746)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 5197 (class 0 OID 0)
-- Dependencies: 229
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 243 (class 1259 OID 16980)
-- Name: vouchers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vouchers (
    id integer NOT NULL,
    code character varying(255) NOT NULL,
    discount_type character varying(255) NOT NULL,
    voucher_status character varying(255) NOT NULL,
    discount_value double precision NOT NULL,
    min_order_value double precision NOT NULL,
    max_discount double precision NOT NULL,
    start_date timestamp without time zone NOT NULL,
    end_date timestamp without time zone NOT NULL,
    description character varying(255),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT vouchers_check CHECK ((end_date > start_date)),
    CONSTRAINT vouchers_discount_value_check CHECK ((discount_value >= ((0)::numeric)::double precision)),
    CONSTRAINT vouchers_max_discount_check CHECK ((max_discount >= ((0)::numeric)::double precision)),
    CONSTRAINT vouchers_min_order_value_check CHECK ((min_order_value >= ((0)::numeric)::double precision))
);


ALTER TABLE public.vouchers OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 16979)
-- Name: vouchers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vouchers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.vouchers_id_seq OWNER TO postgres;

--
-- TOC entry 5198 (class 0 OID 0)
-- Dependencies: 242
-- Name: vouchers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vouchers_id_seq OWNED BY public.vouchers.id;


--
-- TOC entry 4879 (class 2604 OID 16885)
-- Name: cart_items id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart_items ALTER COLUMN id SET DEFAULT nextval('public.cart_items_id_seq'::regclass);


--
-- TOC entry 4876 (class 2604 OID 16809)
-- Name: carts id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.carts ALTER COLUMN id SET DEFAULT nextval('public.carts_id_seq'::regclass);


--
-- TOC entry 4855 (class 2604 OID 16407)
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);


--
-- TOC entry 4882 (class 2604 OID 16937)
-- Name: delivery_addresses id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.delivery_addresses ALTER COLUMN id SET DEFAULT nextval('public.delivery_addresses_id_seq'::regclass);


--
-- TOC entry 4891 (class 2604 OID 17272)
-- Name: order_items id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items ALTER COLUMN id SET DEFAULT nextval('public.order_items_id_seq'::regclass);


--
-- TOC entry 4888 (class 2604 OID 17244)
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


--
-- TOC entry 4852 (class 2604 OID 16396)
-- Name: parent_categories id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parent_categories ALTER COLUMN id SET DEFAULT nextval('public.parent_categories_id_seq'::regclass);


--
-- TOC entry 4861 (class 2604 OID 16624)
-- Name: product_images id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_images ALTER COLUMN id SET DEFAULT nextval('public.product_images_id_seq'::regclass);


--
-- TOC entry 4864 (class 2604 OID 16640)
-- Name: product_variants id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_variants ALTER COLUMN id SET DEFAULT nextval('public.product_variants_id_seq'::regclass);


--
-- TOC entry 4858 (class 2604 OID 16590)
-- Name: products id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- TOC entry 4899 (class 2604 OID 17380)
-- Name: review_images id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.review_images ALTER COLUMN id SET DEFAULT nextval('public.review_images_id_seq'::regclass);


--
-- TOC entry 4902 (class 2604 OID 17396)
-- Name: review_replies id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.review_replies ALTER COLUMN id SET DEFAULT nextval('public.review_replies_id_seq'::regclass);


--
-- TOC entry 4894 (class 2604 OID 17340)
-- Name: reviews id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews ALTER COLUMN id SET DEFAULT nextval('public.reviews_id_seq'::regclass);


--
-- TOC entry 4870 (class 2604 OID 16766)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 4873 (class 2604 OID 16798)
-- Name: tokens id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tokens ALTER COLUMN id SET DEFAULT nextval('public.tokens_id_seq'::regclass);


--
-- TOC entry 4867 (class 2604 OID 16750)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 4885 (class 2604 OID 16983)
-- Name: vouchers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vouchers ALTER COLUMN id SET DEFAULT nextval('public.vouchers_id_seq'::regclass);


--
-- TOC entry 5162 (class 0 OID 16882)
-- Dependencies: 239
-- Data for Name: cart_items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cart_items (id, cart_id, product_variant_id, quantity, created_at, updated_at) FROM stdin;
11	5	5	3	2025-03-11 13:35:30.367	2025-03-11 13:35:46.221
\.


--
-- TOC entry 5159 (class 0 OID 16806)
-- Dependencies: 236
-- Data for Name: carts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.carts (id, user_id, created_at, updated_at) FROM stdin;
2	2	2025-03-03 13:03:35.286	2025-03-03 13:03:35.286
3	3	2025-03-03 15:48:13.217	2025-03-03 15:48:13.217
4	5	2025-03-06 15:44:50.436	2025-03-06 15:44:50.436
5	7	2025-03-06 16:43:34.198	2025-03-06 16:43:34.198
6	8	2025-03-07 20:07:08.219	2025-03-07 20:07:08.219
8	10	2025-04-08 21:44:32.837	2025-04-08 21:44:32.837
9	11	2025-04-08 21:50:56.949	2025-04-08 21:50:56.949
\.


--
-- TOC entry 5143 (class 0 OID 16404)
-- Dependencies: 220
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categories (id, name, parent_category_id, created_at, updated_at) FROM stdin;
1	Thit Heo	1	2025-02-16 14:49:29.818	2025-02-16 14:49:29.818
4	Cá Tươi	2	2025-02-17 11:32:26.329	2025-02-17 11:32:26.329
5	Hải Sản	2	2025-02-17 11:32:39.034	2025-02-17 11:32:39.034
6	Trái Cây Tươi	3	2025-02-17 11:32:53.391	2025-02-17 11:32:53.391
7	Củ Quả	4	2025-02-17 11:33:05.501	2025-02-17 11:33:05.501
8	Rau Lá	4	2025-02-17 11:33:14.054	2025-02-17 11:33:14.054
9	Bia, đồ uống cố cồn	5	2025-02-17 11:33:26.589	2025-02-17 11:33:26.589
10	Nước ngọt	5	2025-02-17 11:33:37.076	2025-02-17 11:33:37.076
11	Sữa	5	2025-02-17 11:33:46.198	2025-02-17 11:33:46.198
12	Snack Gói	6	2025-02-17 11:33:57.237	2025-02-17 11:33:57.237
13	Gói Kẹo	6	2025-02-17 11:34:06.896	2025-02-17 11:34:06.896
2	Thịt Bò	1	2025-02-16 14:49:35.984	2025-02-16 14:49:35.984
3	Thịt Gà	1	2025-02-16 14:49:35.984	2025-02-16 14:49:35.984
16	Tôm viên	2	2025-04-06 13:25:46.258	2025-04-06 13:25:46.258
17	Dầu Ăn	9	2025-04-16 13:38:02.223	2025-04-16 13:38:02.223
\.


--
-- TOC entry 5164 (class 0 OID 16934)
-- Dependencies: 241
-- Data for Name: delivery_addresses; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.delivery_addresses (id, name, number_phone, province_id, district_id, ward_id, province_name, district_name, ward_name, detail_address, user_id, is_default, created_at, updated_at) FROM stdin;
1	Lee Min Ho	0345778312	212	2084	530406	Tiền Giang	Huyện Cai Lậy	Xã Long Trung	\N	\N	t	2025-03-16 21:32:38.941	2025-03-16 21:32:38.941
2	Lee Min Ho	0345778312	262	1771	370408	Bình Định	Thị xã Hoài Nhơn	Phường Hoài Hương	\N	\N	t	2025-03-16 21:36:00.102	2025-03-16 21:36:00.102
4	Lee Min Ho	0345778312	202	3695	90737	Hồ Chí Minh	Thành Phố Thủ Đức	Phường Linh Trung	Linh Trung, Thủ Đức, TPHCM	2	f	2025-03-16 21:43:28.133	2025-03-16 21:43:28.133
3	Lee Min Ho	0345778312	252	1783	610805	Cà Mau	Huyện Năm Căn	Xã Hiệp Tùng	64/3 ,Đường số 17,Phường Linh Trung, Thành phố Thủ Đức, Thành phố Hồ Chí Minh, Việt Nam.	2	t	2025-03-16 21:41:25.56	2025-03-16 21:41:25.56
\.


--
-- TOC entry 5170 (class 0 OID 17269)
-- Dependencies: 247
-- Data for Name: order_items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_items (id, order_id, product_variant_id, quantity, created_at, updated_at) FROM stdin;
1	1	3	1	2025-03-25 22:15:56.734	2025-03-25 22:15:56.734
2	1	1	1	2025-03-25 22:15:56.738	2025-03-25 22:15:56.738
3	2	4	4	2025-03-25 22:20:36.169	2025-03-25 22:20:36.169
4	3	5	2	2025-03-30 15:59:17.092	2025-03-30 15:59:17.092
5	3	3	1	2025-03-30 15:59:17.097	2025-03-30 15:59:17.097
6	4	4	1	2025-03-30 19:53:51.294	2025-03-30 19:53:51.294
7	4	3	1	2025-03-30 19:53:51.298	2025-03-30 19:53:51.298
8	4	2	1	2025-03-30 19:53:51.3	2025-03-30 19:53:51.3
9	5	3	1	2025-03-30 20:26:37.326	2025-03-30 20:26:37.326
10	5	2	1	2025-03-30 20:26:37.329	2025-03-30 20:26:37.329
11	6	2	1	2025-03-30 20:36:28.897	2025-03-30 20:36:28.897
12	6	1	1	2025-03-30 20:36:28.9	2025-03-30 20:36:28.9
13	7	2	1	2025-03-30 20:42:51.679	2025-03-30 20:42:51.679
14	7	5	1	2025-03-30 20:42:51.681	2025-03-30 20:42:51.681
15	8	3	1	2025-03-30 21:16:34.73	2025-03-30 21:16:34.73
16	9	5	2	2025-03-30 21:23:06.216	2025-03-30 21:23:06.217
17	10	3	1	2025-03-31 10:07:38.318	2025-03-31 10:07:38.318
18	10	2	1	2025-03-31 10:07:38.318	2025-03-31 10:07:38.318
19	11	1	1	2025-04-02 16:15:33.149	2025-04-02 16:15:33.149
20	11	2	2	2025-04-02 16:15:33.152	2025-04-02 16:15:33.152
21	11	5	2	2025-04-02 16:15:33.154	2025-04-02 16:15:33.154
22	11	3	2	2025-04-02 16:15:33.155	2025-04-02 16:15:33.155
23	11	4	1	2025-04-02 16:15:33.155	2025-04-02 16:15:33.155
\.


--
-- TOC entry 5168 (class 0 OID 17241)
-- Dependencies: 245
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orders (id, order_code, total_price, note, delivery_fee, expected_delivery_time, payment_methods, order_status, voucher_id, delivery_address_id, user_id, created_at, updated_at) FROM stdin;
3	order-20250330-3	97000		54201	2025-03-31 23:59:59	COD	PENDING	\N	3	2	2025-03-30 15:59:17.076	2025-03-30 15:59:17.107
5	order-20250330-5	131000		54201	2025-04-01 23:59:59	COD	PENDING	\N	3	2	2025-03-30 20:26:37.324	2025-03-30 20:26:37.335
6	order-20250330-6	117000		54201	2025-04-01 23:59:59	COD	PENDING	\N	3	2	2025-03-30 20:36:28.892	2025-03-30 20:36:28.913
7	order-20250330-7	102000		54201	2025-04-01 23:59:59	COD	PENDING	\N	3	2	2025-03-30 20:42:51.676	2025-03-30 20:42:51.69
9	ORD-20250330-9788	34000		54201	2025-04-01 23:59:59	BANK_TRANSFER	PENDING	\N	3	2	2025-03-30 21:23:06.209	2025-03-30 21:23:06.233
10	ORD-20250331-1074	131000		54201	2025-04-01 23:59:59	BANK_TRANSFER	CANCELLED	\N	3	2	2025-03-31 10:07:38.318	2025-03-31 10:07:38.335
11	ORD-20250402-1134	294000		54201	2025-04-03 23:59:59	COD	PENDING	\N	3	2	2025-04-02 16:15:33.144	2025-04-02 16:15:33.168
1	order-20250325-1	112000	Thanh Toan Luc 4h Chieu	54201	2025-03-27 23:59:59	COD	SHIPPED	\N	3	2	2025-03-25 22:15:56.725	2025-04-09 22:08:19.17
2	order-20250325-2	80000	Giao hàng cho tôi với nha hahahaha	54201	2025-03-27 23:59:59	COD	CANCELLED	\N	3	2	2025-03-25 22:20:36.167	2025-04-09 22:22:27.09
8	order-20250330-8	63000		54201	2025-04-01 23:59:59	BANK_TRANSFER	RETURNED	\N	3	2	2025-03-30 21:16:34.727	2025-04-09 22:32:39.546
4	order-20250330-4	211000		54201	2025-04-01 23:59:59	BANK_TRANSFER	PROCESSING	\N	3	2	2025-03-30 19:53:51.287	2025-04-09 22:32:54.058
\.


--
-- TOC entry 5141 (class 0 OID 16393)
-- Dependencies: 218
-- Data for Name: parent_categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.parent_categories (id, name, image_url, created_at, updated_at) FROM stdin;
1	Thịt	http://res.cloudinary.com/digtjnoh3/image/upload/v1727938067/a0pnu8uza5p0i52uz4ts.png	2025-02-12 16:17:47.396	2025-02-12 16:17:47.396
2	Cá	http://res.cloudinary.com/digtjnoh3/image/upload/v1727938106/uauhub444tpxjaspfwly.png	2025-02-12 16:17:47.396	2025-02-12 16:17:47.396
3	Trái Cây	http://res.cloudinary.com/digtjnoh3/image/upload/v1727938133/nsy9m9c7i7zcqyirgqk9.png	2025-02-12 16:17:47.396	2025-02-12 16:17:47.396
4	Rau Củ	http://res.cloudinary.com/digtjnoh3/image/upload/v1727938162/pcqr6427fehmbkiwdjin.png	2025-02-12 16:17:47.396	2025-02-12 16:17:47.396
5	Đồ Uống	http://res.cloudinary.com/digtjnoh3/image/upload/v1727938180/dsloo8ntagyha4hsoniu.png	2025-02-12 16:17:47.396	2025-02-12 16:17:47.396
6	Snack	http://res.cloudinary.com/digtjnoh3/image/upload/v1727938368/ub5y1hbtibzgycad2cbl.png	2025-02-12 16:17:47.396	2025-02-12 16:17:47.396
9	Gia Vị	http://res.cloudinary.com/digtjnoh3/image/upload/v1744785325/jvmarilq7rcoshdfysb8.png	2025-04-16 13:35:26.283	2025-04-16 13:35:26.283
\.


--
-- TOC entry 5147 (class 0 OID 16621)
-- Dependencies: 224
-- Data for Name: product_images; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product_images (id, image_url, alt_text, product_id, created_at, updated_at) FROM stdin;
1	http://res.cloudinary.com/digtjnoh3/image/upload/v1739948839/bx5xyquizx7pbnyem5ip.jpg	products	1	2025-02-19 14:07:23.956	2025-02-19 14:07:23.956
2	http://res.cloudinary.com/digtjnoh3/image/upload/v1739948843/vdzbxfplzecepm0m95ev.jpg	products	1	2025-02-19 14:07:23.959	2025-02-19 14:07:23.959
3	http://res.cloudinary.com/digtjnoh3/image/upload/v1739948841/qhqluxnyfn52wuwessow.jpg	products	1	2025-02-19 14:07:23.961	2025-02-19 14:07:23.961
4	http://res.cloudinary.com/digtjnoh3/image/upload/v1728028833/rrphanljplfhgctinucs.jpg	products	2	2025-02-19 14:07:23.956	2025-02-19 14:07:23.956
5	http://res.cloudinary.com/digtjnoh3/image/upload/v1728028836/ripempxa3z48aqc1clfa.jpg	products	2	2025-02-19 14:07:23.956	2025-02-19 14:07:23.956
6	http://res.cloudinary.com/digtjnoh3/image/upload/v1728029277/v1dccguyackj4w1qxjqk.jpg	products	3	2025-02-19 14:07:23.956	2025-02-19 14:07:23.956
7	http://res.cloudinary.com/digtjnoh3/image/upload/v1728029280/regbingom5rcysxombxn.png	products	3	2025-02-19 14:07:23.956	2025-02-19 14:07:23.956
8	http://res.cloudinary.com/digtjnoh3/image/upload/v1728029282/rcu73vzqfa39rvsxdgja.jpg	products	3	2025-02-19 14:07:23.956	2025-02-19 14:07:23.956
9	http://res.cloudinary.com/digtjnoh3/image/upload/v1728029616/vssldl7hvxh5cupwx0p7.jpg	products	4	2025-02-19 14:07:23.956	2025-02-19 14:07:23.956
10	http://res.cloudinary.com/digtjnoh3/image/upload/v1728029612/nra3pdygv9fkw0lhott9.jpg	products	4	2025-02-19 14:07:23.956	2025-02-19 14:07:23.956
11	http://res.cloudinary.com/digtjnoh3/image/upload/v1728029614/bkraymms9gvcvkclyyov.jpg	products	4	2025-02-19 14:07:23.956	2025-02-19 14:07:23.956
12	http://res.cloudinary.com/digtjnoh3/image/upload/v1728030063/sliou2vczoutonpm4frq.jpg	products	5	2025-02-19 14:07:23.956	2025-02-19 14:07:23.956
13	http://res.cloudinary.com/digtjnoh3/image/upload/v1728030060/nbzvncxnytgfxbtvhjlb.jpg	products	5	2025-02-19 14:07:23.956	2025-02-19 14:07:23.956
14	http://res.cloudinary.com/digtjnoh3/image/upload/v1728030058/jivlmgkqvruhevg7cpon.jpg	products	5	2025-02-19 14:07:23.956	2025-02-19 14:07:23.956
30	http://res.cloudinary.com/digtjnoh3/image/upload/v1744781845/lntsl4tmv0z0mp9embdu.jpg	products	26	2025-04-16 12:37:29.181	2025-04-16 12:37:29.181
31	http://res.cloudinary.com/digtjnoh3/image/upload/v1744781848/slkdush66hyndojjxmwl.png	products	26	2025-04-16 12:37:29.185	2025-04-16 12:37:29.185
32	http://res.cloudinary.com/digtjnoh3/image/upload/v1744781843/vd9rm56fke7pbziwsogl.jpg	products	26	2025-04-16 12:37:29.187	2025-04-16 12:37:29.187
33	http://res.cloudinary.com/digtjnoh3/image/upload/v1744782426/nkrjdnghxxlflksghibp.jpg	products	27	2025-04-16 12:47:12.146	2025-04-16 12:47:12.146
34	http://res.cloudinary.com/digtjnoh3/image/upload/v1744782431/ih2dsmfcozg8uxulczwa.jpg	products	27	2025-04-16 12:47:12.148	2025-04-16 12:47:12.148
35	http://res.cloudinary.com/digtjnoh3/image/upload/v1744782429/vbq4suavmaeakgbpllwd.jpg	products	27	2025-04-16 12:47:12.149	2025-04-16 12:47:12.15
36	http://res.cloudinary.com/digtjnoh3/image/upload/v1744782920/smugnxtvk82gumm5jj79.jpg	products	28	2025-04-16 12:55:23.32	2025-04-16 12:55:23.32
37	http://res.cloudinary.com/digtjnoh3/image/upload/v1744782923/t3isdponinsszmyvkafw.jpg	products	28	2025-04-16 12:55:23.321	2025-04-16 12:55:23.321
38	http://res.cloudinary.com/digtjnoh3/image/upload/v1744782916/chl57rd2wpa0biba5cb5.jpg	products	28	2025-04-16 12:55:23.323	2025-04-16 12:55:23.323
39	http://res.cloudinary.com/digtjnoh3/image/upload/v1744782918/l0mo7ahw8luafogzt0xw.jpg	products	28	2025-04-16 12:55:23.325	2025-04-16 12:55:23.325
40	http://res.cloudinary.com/digtjnoh3/image/upload/v1744783564/sqd0dwabqtsyhmjwqmsk.jpg	products	29	2025-04-16 13:06:05.488	2025-04-16 13:06:05.488
41	http://res.cloudinary.com/digtjnoh3/image/upload/v1744783560/fm8precftfjbireji5oc.jpg	products	29	2025-04-16 13:06:05.492	2025-04-16 13:06:05.492
42	http://res.cloudinary.com/digtjnoh3/image/upload/v1744783905/wwfeaoayr5lnqqbuayin.jpg	products	30	2025-04-16 13:11:48.878	2025-04-16 13:11:48.878
43	http://res.cloudinary.com/digtjnoh3/image/upload/v1744783908/xko0dh8le7zcg9l6dkzt.jpg	products	30	2025-04-16 13:11:48.879	2025-04-16 13:11:48.879
44	http://res.cloudinary.com/digtjnoh3/image/upload/v1744783902/q8rts78mdxeo5ztftcft.jpg	products	30	2025-04-16 13:11:48.88	2025-04-16 13:11:48.88
45	http://res.cloudinary.com/digtjnoh3/image/upload/v1744784416/hwe1mjbtwleadr4fidvr.jpg	products	31	2025-04-16 13:20:19.385	2025-04-16 13:20:19.385
46	http://res.cloudinary.com/digtjnoh3/image/upload/v1744784419/oetpy0jn0nxyolas5nr7.jpg	products	31	2025-04-16 13:20:19.387	2025-04-16 13:20:19.387
47	http://res.cloudinary.com/digtjnoh3/image/upload/v1744785974/kzi3dhpeqj0tmuhncsy5.jpg	products	32	2025-04-16 13:46:25.722	2025-04-16 13:46:25.722
48	http://res.cloudinary.com/digtjnoh3/image/upload/v1744785978/jmnvlaftoy8lkgonbcg3.jpg	products	32	2025-04-16 13:46:25.724	2025-04-16 13:46:25.724
49	http://res.cloudinary.com/digtjnoh3/image/upload/v1744785985/zitkaj2hcwhkpkkqgiga.jpg	products	32	2025-04-16 13:46:25.726	2025-04-16 13:46:25.726
50	http://res.cloudinary.com/digtjnoh3/image/upload/v1744785982/qbaznx3z9g8qhd8ryaco.jpg	products	32	2025-04-16 13:46:25.728	2025-04-16 13:46:25.728
51	http://res.cloudinary.com/digtjnoh3/image/upload/v1744786704/ykx6f2pl1ydagtuxwvx5.jpg	products	33	2025-04-16 13:58:25.605	2025-04-16 13:58:25.605
52	http://res.cloudinary.com/digtjnoh3/image/upload/v1744786697/fzejysnrdl8vegjipc6p.jpg	products	33	2025-04-16 13:58:25.608	2025-04-16 13:58:25.608
53	http://res.cloudinary.com/digtjnoh3/image/upload/v1744787136/uls2okjitvduffpvefjj.jpg	products	34	2025-04-16 14:05:39.722	2025-04-16 14:05:39.722
54	http://res.cloudinary.com/digtjnoh3/image/upload/v1744787139/cmtwert1gfj8icky8clw.jpg	products	34	2025-04-16 14:05:39.723	2025-04-16 14:05:39.723
55	http://res.cloudinary.com/digtjnoh3/image/upload/v1744787133/n0nqhd91l9r9wznwvqsm.jpg	products	34	2025-04-16 14:05:39.724	2025-04-16 14:05:39.724
56	http://res.cloudinary.com/digtjnoh3/image/upload/v1744787377/trpf7figcmlrzcnipo9f.jpg	products	35	2025-04-16 14:09:38.174	2025-04-16 14:09:38.174
57	http://res.cloudinary.com/digtjnoh3/image/upload/v1744787374/bk0jc1hsxqyj5decmwy9.jpg	products	35	2025-04-16 14:09:38.176	2025-04-16 14:09:38.176
\.


--
-- TOC entry 5149 (class 0 OID 16637)
-- Dependencies: 226
-- Data for Name: product_variants; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product_variants (id, product_id, name, price, discount_percentage, unit, status, thumbnail_url, expiry_date, created_at, updated_at) FROM stdin;
1	1	Ức gà 300g	49000	0	G	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1740147420/u72kxxkaiedgkvh4swbl.jpg	2030-03-05 07:00:00	2025-02-21 21:17:00.761	2025-02-21 21:17:00.761
2	2	Ba rọi 500g	68000	0	G	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1728028833/rrphanljplfhgctinucs.jpg	2030-03-05 07:00:00	2025-02-21 21:17:00.761	2025-02-21 21:17:00.761
4	4	Đùi Bò 300g	80000	0	G	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1728029616/vssldl7hvxh5cupwx0p7.jpg	2030-03-05 07:00:00	2025-02-21 21:17:00.761	2025-02-21 21:17:00.761
3	3	Sườn non heo 500g	70000	10	G	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1728029277/v1dccguyackj4w1qxjqk.jpg	2030-03-05 07:00:00	2025-02-21 21:17:00.761	2025-02-21 21:17:00.761
5	5	Cá basa cắt khúc 500g	40000	15	G	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1728030063/sliou2vczoutonpm4frq.jpg	2030-03-05 07:00:00	2025-02-21 21:17:00.761	2025-02-21 21:17:00.761
11	26	Chân Giò Heo 500g	59000	0	G	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744781851/zvvdwn977hezyt3wfqzr.jpg	2025-08-21 07:00:00	2025-04-16 12:37:31.27	2025-04-16 12:37:31.27
12	27	Thùng 24 lon bia Tiger Bạc 330ml	400000	0	CAN	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744782434/hupv9ret6dloubn3qhxt.jpg	2025-08-29 07:00:00	2025-04-16 12:47:14.73	2025-04-16 12:47:14.73
13	27	Tiger Bạc Lon 330ml	20000	0	CAN	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744782436/l3qtzpwnxewf4pqlobdz.jpg	2025-09-05 07:00:00	2025-04-16 12:47:17.187	2025-04-16 12:47:17.187
14	28	Thùng 24 Lon Pepsi	200000	0	CAN	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744782926/knvlxr2dirm1g2kkycnc.jpg	2025-08-27 07:00:00	2025-04-16 12:55:26.99	2025-04-16 12:55:26.99
15	28	Lon Pepsi	9000	0	CAN	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744782928/jxlq6760e7rnh8gfdmny.jpg	2025-08-22 07:00:00	2025-04-16 12:55:29.212	2025-04-16 12:55:29.212
16	29	Xoài Keo Trái 350g	30000	0	G	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744783570/dhmyimdhug0mfso69oys.jpg	2025-06-12 07:00:00	2025-04-16 13:06:11.115	2025-04-16 13:06:11.115
17	30	Sửa Tươi Vinamilk Bịch	8000	0	PACK	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744783911/jgshtj2udayid20xsukp.jpg	2025-06-11 07:00:00	2025-04-16 13:11:52.203	2025-04-16 13:11:52.203
18	31	Snack khoai tây vị tảo biển kiểu gói 75g	22000	0	PACK	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744784421/ig03grrtld7snjyf0upw.jpg	2025-08-26 07:00:00	2025-04-16 13:20:21.472	2025-04-16 13:20:21.472
19	32	Dầu Ăn Simply 1 lít	60000	0	BOTTLE	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744785988/ttzqb6ebbdjl5nz7wa1g.jpg	2025-07-24 07:00:00	2025-04-16 13:46:28.455	2025-04-16 13:46:28.455
20	32	Dầu Ăn Simply 2 lít	115000	0	BOTTLE	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744785990/bfxdymshmj7pifixczm2.jpg	2025-07-16 07:00:00	2025-04-16 13:46:31.02	2025-04-16 13:46:31.02
21	33	Sữa Ensure Hương Vani 1 Chai	43000	0	BOTTLE	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744786708/qfbngpocaszl3l4chxik.jpg	2025-07-17 07:00:00	2025-04-16 13:58:29.848	2025-04-16 13:58:29.848
22	33	Sữa Ensure Hương Vani Lốc 6 Chai	256000	0	BOTTLE	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744786714/lwlvi194ttmcbxxjzk1e.jpg	2025-06-25 07:00:00	2025-04-16 13:58:35.356	2025-04-16 13:58:35.356
23	34	Nước Tăng Lực Monster Ultra 1 Lon	27000	0	CAN	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744787141/uln7xq5iooygpze3wfjx.jpg	2025-07-16 07:00:00	2025-04-16 14:05:42.134	2025-04-16 14:05:42.134
24	34	Nước Tăng Lực Monster Ultra Lóc 6 Lon	160000	0	CAN	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744787145/mjculod8000sgzzgd17t.jpg	2025-08-21 07:00:00	2025-04-16 14:05:45.967	2025-04-16 14:05:45.967
25	34	Nước Tăng Lực Monster Ultra Thùng 24 lon	632000	0	CAN	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744787147/zszgagcwccww5rx88anu.jpg	2025-07-16 07:00:00	2025-04-16 14:05:48.383	2025-04-16 14:05:48.383
26	35	Cà Rốt 1KG	15000	0	KG	AVAILABLE	http://res.cloudinary.com/digtjnoh3/image/upload/v1744787380/jcjxoimftc7pksf88xg6.jpg	2025-07-17 07:00:00	2025-04-16 14:09:41.096	2025-04-16 14:09:41.096
\.


--
-- TOC entry 5145 (class 0 OID 16587)
-- Dependencies: 222
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.products (id, name, description, thumbnail_url, category_id, created_at, updated_at) FROM stdin;
1	Ức gà có xương	Sản phẩm Ức gà do VinMart cung cấp được sản xuất và sơ chế theo quy trình khép kín, đảm bảo vệ sinh an toàn thực phẩm với sự giám sát và kiểm tra kỹ lưỡng. Ức gà là nguồn cung cấp protein nạc dồi dào, rất thích hợp cho những người tập luyện thể thao hoặc muốn kiểm soát cân nặng. Ngoài ra, ức gà còn chứa ít chất béo, giàu vitamin B6 và phốt pho, giúp tăng cường chức năng hệ miễn dịch và hỗ trợ sức khỏe xương khớp. Phần thịt ức gà mềm, ít mỡ, thường được chế biến thành các món ăn bổ dưỡng như gà nướng, gà luộc, gà áp chảo hay salad ức gà, phù hợp cho các bữa ăn lành mạnh và giàu dinh dưỡng.	http://res.cloudinary.com/digtjnoh3/image/upload/v1739948837/wutm8rcpyifekl0fobke.jpg	3	2025-02-19 14:07:23.915	2025-02-19 14:07:23.915
2	Ba rọi heo nhập khẩu	Ba rọi heo MEATDeli mềm ngon với lớp nạc và mỡ xen kẽ hài hòa. Hương vị thịt tươi ngọt, béo ngậy thơm ngon - là sản phẩm được ưa chuộng chế biến nhiều món ăn hấp dẫn. Thịt lợn chứa hàm lượng cao protein và các acid amin, rất cần thiết đối với những người quan tâm đến việc xây dựng hình thể. Thành phần bên trong thịt lợn chứa nhiều vitamin B1, vitamin B2 rất cần thiết cho sự tăng trưởng, phục hồi cơ bắp và nhanh hồi phục những tổn thương trên da. Thịt lợn thúc đẩy việc sản xuất tế bào máu của cơ thể có lượng máu lưu thông tốt hơn, hạn chế tình trạng tê chân, tay do ít vận động. Thịt lợn bảo vệ cấu trúc xương, giúp xương và răng chắc khỏe, cũng như đảm bảo năng lượng cho cơ thể đủ sức vận động.	http://res.cloudinary.com/digtjnoh3/image/upload/v1728028829/ohlh7elrwxokvmbbgozv.jpg	1	2025-02-19 14:07:23.915	2025-02-19 14:07:23.915
3	Sườn non heo	Sườn non heo là những miếng sườn hồng tươi với thịt mềm căng mọng được tuyển chọn kỹ lưỡng từ tảng sườn thăn ngon nhất. Phần xương sườn nhỏ, xương hình dẹt, chứa nhiều thịt xen chút mỡ, thường có sụn. Loại sườn này thích hợp nhất là dùng để chế biến các món như sườn nướng, rim, sườn xào, sườn chua ngọt,… Thịt heo chứa hàm lượng cao protein và các acid amin, rất cần thiết đối với những người quan tâm đến việc xây dựng hình thể. Thành phần bên trong thịt heo chứa nhiều vitamin B1, vitamin B2 rất cần thiết cho sự tăng trưởng, phục hồi cơ bắp và nhanh hồi phục những tổn thương trên da và giải độc cơ thể rất tốt. Thịt heo thúc đẩy việc sản xuất tế bào máu của cơ thể có lượng máu lưu thông tốt hơn, hạn chế tình trạng tê chân, tay do ít vận động. Thịt heo bảo vệ cấu trúc xương, giúp xương và răng chắc khỏe, cũng như đảm bảo năng lượng cho cơ thể đủ sức vận động.	http://res.cloudinary.com/digtjnoh3/image/upload/v1728029275/kdqvqexr5vktgdnybwo6.jpg	1	2025-02-19 14:07:23.915	2025-02-19 14:07:23.915
4	Đùi Bò	Sản phẩm Thăn thịt bò 300g do VinMart cung cấp được sản xuất và sơ chế theo quy trình khép kín dưới sự giám sát và kiểm tra nghiêm ngặt, đảm bảo vệ sinh an toàn thực phẩm. Thăn bò rất giàu chất sắt có tác dụng bổ sung lượng máu cho cơ thể và phòng tránh cơ thể bị thiếu máu nên đặc biệt thích hợp cho những người có thể chất yếu hoặc trí não đang bị suy giảm. Ngoài ra, thành phần axit amin, protein trong thăn bò rất cần thiết cho cơ thể của con người để gia tăng sức đề kháng của cơ thể. Thăn bò là phần thịt mềm, được xem là phần thịt ngon nhất của con bò nên thường được các bà nội trợ lựa chọn sử dụng. Thịt thường được kết hợp cùng các gia vị, thực phẩm khác chế biến thành các món ăn ngon thường ngày như bò nướng, bít tết, bò xào rau củ...	http://res.cloudinary.com/digtjnoh3/image/upload/v1728029610/sknqehv2p3djpsuk9nkb.jpg	2	2025-02-19 14:07:23.915	2025-02-19 14:07:23.915
5	Cá basa cắt khúc	Sản phẩm Cá ba sa cắt khúc do VinMart cung cấp được chế biến theo quy trình khép kín, đảm bảo vệ sinh an toàn thực phẩm với sự kiểm tra nghiêm ngặt. Cá ba sa rất giàu axit béo Omega-3, tốt cho tim mạch, hỗ trợ tăng cường trí nhớ và giúp giảm nguy cơ mắc các bệnh về tim. Bên cạnh đó, cá còn cung cấp lượng protein và vitamin cần thiết cho cơ thể, giúp tăng cường sức khỏe và hệ miễn dịch. Phần thịt cá ba sa mềm, ngọt và ít xương, thích hợp để chế biến nhiều món ăn ngon miệng như cá chiên giòn, cá nấu canh chua, hoặc cá kho tộ. Đây là lựa chọn tuyệt vời cho bữa ăn hàng ngày của gia đình.	http://res.cloudinary.com/digtjnoh3/image/upload/v1728030056/ogtgafan3sv6ccbralb9.jpg	4	2025-02-19 14:07:23.915	2025-02-19 14:07:23.915
26	Chân Giò Heo	Chân giò heo bao gồm chân giò trước và chân giò sau của con heo. Trong thịt chân giò heo chứa rất nhiều khoáng chất như canxi, collagen, vitamin B1, B2, sắt, protein giúp cung cấp chất dinh dưỡng cho cơ thể, chống lão hóa, điều trị các chứng bệnh mất ngủ,... hiệu quả. Không chỉ có lợi cho sức khỏe mà chân giò heo còn có thể chế biến ra nhiều món ăn siêu hấp dẫn. Vì thế, giá thành tương đối cao hơn so với các phần thịt heo khác.	http://res.cloudinary.com/digtjnoh3/image/upload/v1744781841/rcdy7vn4cbf24nhf5bn7.jpg	1	2025-04-16 12:37:29.141	2025-04-16 12:37:29.141
27	Bia Tiger Bạc	Sản phẩm còn có tên gọi là bia Tiger bạc được sản xuất theo quy trình Cold Suspension làm lạnh sâu đến -1 độ. Thùng 24 lon bia Tiger Bạc 330ml với hoa bia được tinh chế đặc biệt, giữ trọn vẹn hương vị tuyệt hảo của bia. Cam kết bia chính hãng, chất lượng và an toàn. Tiger là một thương hiệu bia nổi tiếng hàng trong trong thịt trường bia Việt Nam, thương hiệu đến từ Singapore được giới thiệu lần đầu từ năm 1932, thuộc sở hữu của tập đoàn APB rất được ưa thích trong nước và nhanh chóng chiếm được thị phần tại các nước Châu Á và nhanh chóng lan ra các nước, chính vì thể mà bia Tiger được xem là nhãn hiệu bia số 1 châu Á, được rất nhiều người đón nhận.	http://res.cloudinary.com/digtjnoh3/image/upload/v1744782423/axgeaewm73xf63ygtnsu.jpg	9	2025-04-16 12:47:12.141	2025-04-16 12:47:12.141
28	Nước Ngọt Pepsi	Nước ngọt từ thương hiệu nước ngọt Pepsi là sự lựa chọn tuyệt vời giúp bạn xua tan đi cơn khát, cung cấp nguồn năng lượng dồi dào cho cơ thể. Thùng 24 lon nước ngọt Pepsi Cola Sleek 235ml mang hương vị thơm ngon, sảng khoái nhờ hương vị tự nhiên cùng chất tạo ngọt tổng hơp an toàn, chất lượng. 	http://res.cloudinary.com/digtjnoh3/image/upload/v1744782914/ldiujvvdk2twurqxwiiu.jpg	10	2025-04-16 12:55:23.314	2025-04-16 12:55:23.314
29	Xoài Keo	Xoài keo chất lượng, tươi ngon, trái to, sống, căng trái, khi ăn khá giòn. Xoài keo thường được ăn sống, khi mua về có thể sử dụng ngay. Khi còn xanh, xoài có vị chua nhẹ và giòn. Khi chín thì xoài keo lại có vị ngọt và thơm nhẹ. Giao ngẫu nhiên xoài keo xanh hoặc xoài keo vàng.\nXoài keo có nguồn gốc từ Campuchia, đảm bảo xuất xứ rõ ràng, đóng gói đảm bảo cho người dùng.	http://res.cloudinary.com/digtjnoh3/image/upload/v1744783557/d5rnno3ytfvfqmfm8tow.jpg	6	2025-04-16 13:06:05.48	2025-04-16 13:06:05.48
30	Sữa Tươi Vinamilk Happy Start	Chế biến từ nguồn sữa tươi 100% chứa nhiều dưỡng chất như vitamin A, D3, canxi,... tốt cho xương và hệ miễn dịch. Sữa tươi Vinamilk là thương hiệu được tin dùng hàng đầu với chất lượng tuyệt vời. Thùng 48 bịch sữa dinh dưỡng tiệt trùng có đường Vinamilk Happy Star 220ml đóng thùng tiện dùng lâu dài	http://res.cloudinary.com/digtjnoh3/image/upload/v1744783899/e8wi2fcwb6vizgbi2umq.jpg	11	2025-04-16 13:11:48.877	2025-04-16 13:11:48.877
31	Snack khoai tây vị tảo biển	Snack khoai tây vị tảo biển kiểu Nhật Bản Lay's Max gói 75g là loại snack có lát cắt khoai tây siêu uốn lượn, giòn ngon hết sảy, hương thơm Tảo Biển nổi bật để lại hậu vị ngọt ngon vương vấn. Snack Lay's có gia vị mặn ngọt hài hòa phủ đều trên từng lát snack khoai tây, tạo nên sự hấp dẫn khó cưỡng.	http://res.cloudinary.com/digtjnoh3/image/upload/v1744784414/rxl1egh62s6eaucpdkfu.jpg	12	2025-04-16 13:20:19.382	2025-04-16 13:20:19.382
32	Dầu Đậu Nành Nguyên Chất Simply	Dầu đậu nành nguyên chất Simply chai 1 lít chứa tới 80% axit béo chưa bão hoà cùng lượng lớn chất chống oxy hoá giúp làm giảm lượng cholesterol xấu trong máu và cho bạn một trái tim khoẻ mạnh. Dầu ăn Simply là nhãn hiệu dầu ăn duy nhất được Hội Tim Mạch Học Việt Nam khuyên dùng.	http://res.cloudinary.com/digtjnoh3/image/upload/v1744785971/s9bwyjpfyjruantihoxd.jpg	17	2025-04-16 13:46:25.68	2025-04-16 13:46:25.681
33	Sữa Ensure Hương Vani	Sữa bột Ensure là sản phẩm dinh dưỡng đầy đủ và cân đối cho người lớn, hỗ trợ tiêu hóa. Sữa bột pha sẵn có thể thay thế bữa ăn hoặc dùng ăn bổ sung cho người cần cải thiện tình trạng dinh dưỡng. Lốc 6 chai sữa bột pha sẵn Ensure Original vani 237ml hỗ trợ tăng cường sức khỏe, thể chất hiệu quả.  	http://res.cloudinary.com/digtjnoh3/image/upload/v1744786694/t2bhvihwmwvmvhxrrgjk.jpg	11	2025-04-16 13:58:25.599	2025-04-16 13:58:25.599
34	Nước Tăng Lực Monster Energy Ultra	Sản phẩm thức uống tăng lực thơm ngon từ thương hiệu nước tăng lực Monster Energy sản xuất tại Hà Lan. 24 lon nước tăng lực Monster Energy Ultra 355ml giải khát nhanh chóng, mang lại nguồn năng lượng mạnh mẽ, thể hiện đẳng cấp, phong cách sống khác biệt của những người trẻ năng động	http://res.cloudinary.com/digtjnoh3/image/upload/v1744787130/y5d7fzgmvby1iwf3cb5j.jpg	10	2025-04-16 14:05:39.716	2025-04-16 14:05:39.716
35	Cà Rốt 1KG	Cà rốt tươi ngon, màu cam tươi, vỏ trơn láng, có màu sáng. Cà rốt không bị mềm, dập hay bị héo. Cà rốt giòn ngọt, được lựa chọn cho nhiều món ngon.\nCà rốt đảm bảo nguồn gốc xuất xứ rõ ràng.	http://res.cloudinary.com/digtjnoh3/image/upload/v1744787371/c5ddmhhovoqsdvnsq7k7.jpg	7	2025-04-16 14:09:38.169	2025-04-16 14:09:38.169
\.


--
-- TOC entry 5150 (class 0 OID 16697)
-- Dependencies: 227
-- Data for Name: products_product_images; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.products_product_images (product_id, product_images_id) FROM stdin;
1	1
1	2
1	3
2	4
2	5
3	6
3	7
3	8
4	9
4	10
4	11
5	12
5	13
5	14
26	30
26	31
26	32
27	33
27	34
27	35
28	36
28	37
28	38
28	39
29	40
29	41
30	42
30	43
30	44
31	45
31	46
32	47
32	48
32	49
32	50
33	51
33	52
34	53
34	54
34	55
35	56
35	57
\.


--
-- TOC entry 5151 (class 0 OID 16715)
-- Dependencies: 228
-- Data for Name: products_product_variants; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.products_product_variants (product_id, product_variants_id) FROM stdin;
\.


--
-- TOC entry 5174 (class 0 OID 17377)
-- Dependencies: 251
-- Data for Name: review_images; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.review_images (id, review_id, image_url, created_at, updated_at) FROM stdin;
1	1	http://res.cloudinary.com/digtjnoh3/image/upload/v1744362781/vuxe0jj95u4owkxgqknw.jpg	2025-04-11 16:13:00.448	2025-04-11 16:13:00.448
2	2	http://res.cloudinary.com/digtjnoh3/image/upload/v1744383097/vwhal7f7tqab0g9v3vbx.jpg	2025-04-11 21:51:42.189	2025-04-11 21:51:42.189
3	2	http://res.cloudinary.com/digtjnoh3/image/upload/v1744383101/k8n2u2krbpl4ysowq6eg.jpg	2025-04-11 21:51:42.189	2025-04-11 21:51:42.189
4	2	http://res.cloudinary.com/digtjnoh3/image/upload/v1744383099/lrjgkdjkqvtztpi7mcff.jpg	2025-04-11 21:51:42.189	2025-04-11 21:51:42.189
5	3	http://res.cloudinary.com/digtjnoh3/image/upload/v1744510107/dzagefu5jshpclbx8djm.jpg	2025-04-13 09:08:27.865	2025-04-13 09:08:27.865
\.


--
-- TOC entry 5176 (class 0 OID 17393)
-- Dependencies: 253
-- Data for Name: review_replies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.review_replies (id, review_id, admin_id, reply_text, created_at, updated_at) FROM stdin;
1	1	11	Cảm ơn bạn đã gớp ý kiến. Chúc bạn ngày mới vui vẻ	2025-04-13 13:56:55.281	2025-04-13 13:56:55.281
2	3	2	Cảm ơn bạn đã đóng góp ý kiến nha!!!	2025-04-13 14:04:24.24	2025-04-13 15:15:49.86
\.


--
-- TOC entry 5172 (class 0 OID 17337)
-- Dependencies: 249
-- Data for Name: reviews; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reviews (id, product_id, user_id, rating, comment, is_verified, likes, created_at, updated_at) FROM stdin;
1	2	2	5	Sản phẩm tươi ngon, tốt cho sức khỏe, sẽ mua lại lần sau.	t	0	2025-04-11 16:13:00.404	2025-04-11 16:13:00.404
2	3	2	4	Sản phẩm tốt, rất phù hợp cho người thích thịt bò.!!!!	t	0	2025-04-11 21:51:42.146	2025-04-11 21:51:42.146
3	2	7	4	Sản phẩm tốt, tươi ngon, sẽ mua lại lần sau!!	t	0	2025-04-13 09:08:27.841	2025-04-13 09:08:27.841
\.


--
-- TOC entry 5155 (class 0 OID 16763)
-- Dependencies: 232
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name, created_at, updated_at) FROM stdin;
1	customer	2025-03-03 11:51:46.195075	2025-03-03 11:51:46.195075
2	staff	2025-03-03 11:51:46.198737	2025-03-03 11:51:46.198737
3	admin	2025-03-03 11:51:46.199438	2025-03-03 11:51:46.199438
\.


--
-- TOC entry 5157 (class 0 OID 16795)
-- Dependencies: 234
-- Data for Name: tokens; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tokens (id, username, access_token, refresh_token, created_at, updated_at) FROM stdin;
1	leminhhieu2003	eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOltdLCJzdWIiOiJsZW1pbmhoaWV1MjAwMyIsImlhdCI6MTc0MDkwODAyNiwiZXhwIjoxNzQwOTExNjI2fQ.p94Zst-Yte4ovz6zhJoQE1MSvph__dM-8zj8qe18H7o	eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOltdLCJzdWIiOiJsZW1pbmhoaWV1MjAwMyIsImlhdCI6MTc0MDkwODAyNiwiZXhwIjoxNzQwOTExNjI2fQ.p94Zst-Yte4ovz6zhJoQE1MSvph__dM-8zj8qe18H7o	2025-03-02 16:33:46.114	2025-03-02 16:33:46.114
24	117570612332210970560	eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOltdLCJzdWIiOiIxMTc1NzA2MTIzMzIyMTA5NzA1NjAiLCJpYXQiOjE3NDE1MDAzNDksImV4cCI6MTc0MTUwMzk0OX0.2V3WFKx5jvx1xEW8gEYDCovR-hxvKezAoArcQH1JPN0	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTc1NzA2MTIzMzIyMTA5NzA1NjAiLCJpYXQiOjE3NDE0NDc3MDcsImV4cCI6MTc0MjA1MjUwN30.TVNFuCEEMj6jSIaGtOZJI7kBprnx8SgFqvzE3pkBaQM	2025-03-09 09:15:21.388	2025-03-09 13:05:49.291
43	hoaroicuaphat2	eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOltdLCJzdWIiOiJob2Fyb2ljdWFwaGF0MiIsImlhdCI6MTc0NDUzMjMzNSwiZXhwIjoxNzQ0NTM1OTM1fQ.nGDbKPgX1ZDMhhX372rvPqWZOxsOftCUuTKhGoIXmAQ	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJob2Fyb2ljdWFwaGF0MiIsImlhdCI6MTc0NDUzMDg0NywiZXhwIjoxNzQ1MTM1NjQ3fQ.zjBmghOH44rkpuShxvc4DwUZyZ8oWBDpz4m2welLED0	2025-04-13 14:54:07.402	2025-04-13 15:18:55.077
\.


--
-- TOC entry 5160 (class 0 OID 16844)
-- Dependencies: 237
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_roles (user_id, role_id) FROM stdin;
2	1
3	1
5	1
7	1
8	1
10	1
11	3
\.


--
-- TOC entry 5153 (class 0 OID 16747)
-- Dependencies: 230
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, username, password, full_name, number_phone, email, provider, provider_id, created_at, updated_at) FROM stdin;
7	1949747738867541	$2a$10$QWK/vY4yOxvl8/0FGQL9deLKnEOg9ywBmLGALNoMzhD9ihjJiGhQu	Lê Minh Hiếu	\N	hieu290503@gmail.com	FACEBOOK	1949747738867541	2025-03-06 16:43:34.151	2025-03-06 16:43:34.151
8	117570612332210970560	$2a$10$xw4UZA1w4.dBpv66wI6G0eUOjIpjtDNaHOjYmT2Lv33dg/ogkSFxy	MinHieu Le	\N	leminhhieu.ltp2021@gmail.com	GOOGLE	117570612332210970560	2025-03-07 20:07:08.176	2025-03-07 20:07:08.176
2	tieuminh123	$2a$10$VArlQnnHxexqBSyOtsewW.BSpoxgOU.UonTjvUoIqelA5ueYk.Aiu	Tieu Thi Minh	0345778312	tieuthiminh@gmail.com	LOCAL	\N	2025-03-03 13:03:35.231	2025-03-03 13:03:35.232
5	105210063005302798572	$2a$10$vDmwG/gxqJXiCSxMyGjJOO9qkRJFAjVpoNh8JD0o.hw/Sf9lnOZ/y	Lê Minh Hiếu	\N	21130354@st.hcmuaf.edu.vn	LOCAL	105210063005302798572	2025-03-06 15:44:50.397	2025-03-06 15:44:50.397
10	hoaroicuaphat	$2a$10$CotXqi7MDd3s.Ftig926fORx2wxYTJ6kZqBfRTbqB/lw7kdsA6IvG	Le Minh Hieu	0345778312	leminhhieu.ltp2021@gmail.com	LOCAL	\N	2025-04-08 21:44:32.773	2025-04-08 21:44:32.773
3	hieule290503	$2a$10$gwX3D8NNvOXCsuiJ7VOa..zTTYUsil79j1AOYrKdidZUlAWACLhPe	Hoàng Quốc Tuấn	0345778312	21130354@st.hcmuaf.edu.vn	LOCAL	\N	2025-03-03 15:48:13.113	2025-04-08 22:18:11.39
11	hoaroicuaphat2	$2a$10$IyhYfS3/5lvSCbX3cqEdTOmVa3dFFXMbdXnLzIetznm1AN5hdswsS	Lee Min HoHo	0345778312	21130354@st.hcmuaf.edu.vn	LOCAL		2025-04-08 21:50:56.907	2025-04-08 22:27:06.488
\.


--
-- TOC entry 5166 (class 0 OID 16980)
-- Dependencies: 243
-- Data for Name: vouchers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vouchers (id, code, discount_type, voucher_status, discount_value, min_order_value, max_discount, start_date, end_date, description, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 5199 (class 0 OID 0)
-- Dependencies: 238
-- Name: cart_items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cart_items_id_seq', 32, true);


--
-- TOC entry 5200 (class 0 OID 0)
-- Dependencies: 235
-- Name: carts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.carts_id_seq', 9, true);


--
-- TOC entry 5201 (class 0 OID 0)
-- Dependencies: 219
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categories_id_seq', 17, true);


--
-- TOC entry 5202 (class 0 OID 0)
-- Dependencies: 240
-- Name: delivery_addresses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.delivery_addresses_id_seq', 4, true);


--
-- TOC entry 5203 (class 0 OID 0)
-- Dependencies: 246
-- Name: order_items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_items_id_seq', 23, true);


--
-- TOC entry 5204 (class 0 OID 0)
-- Dependencies: 244
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orders_id_seq', 11, true);


--
-- TOC entry 5205 (class 0 OID 0)
-- Dependencies: 217
-- Name: parent_categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.parent_categories_id_seq', 9, true);


--
-- TOC entry 5206 (class 0 OID 0)
-- Dependencies: 223
-- Name: product_images_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_images_id_seq', 57, true);


--
-- TOC entry 5207 (class 0 OID 0)
-- Dependencies: 225
-- Name: product_variants_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_variants_id_seq', 26, true);


--
-- TOC entry 5208 (class 0 OID 0)
-- Dependencies: 221
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.products_id_seq', 35, true);


--
-- TOC entry 5209 (class 0 OID 0)
-- Dependencies: 250
-- Name: review_images_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.review_images_id_seq', 5, true);


--
-- TOC entry 5210 (class 0 OID 0)
-- Dependencies: 252
-- Name: review_replies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.review_replies_id_seq', 2, true);


--
-- TOC entry 5211 (class 0 OID 0)
-- Dependencies: 248
-- Name: reviews_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reviews_id_seq', 3, true);


--
-- TOC entry 5212 (class 0 OID 0)
-- Dependencies: 231
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 1, false);


--
-- TOC entry 5213 (class 0 OID 0)
-- Dependencies: 233
-- Name: tokens_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tokens_id_seq', 43, true);


--
-- TOC entry 5214 (class 0 OID 0)
-- Dependencies: 229
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 11, true);


--
-- TOC entry 5215 (class 0 OID 0)
-- Dependencies: 242
-- Name: vouchers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vouchers_id_seq', 1, false);


--
-- TOC entry 4948 (class 2606 OID 16889)
-- Name: cart_items cart_items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_pkey PRIMARY KEY (id);


--
-- TOC entry 4944 (class 2606 OID 16813)
-- Name: carts carts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT carts_pkey PRIMARY KEY (id);


--
-- TOC entry 4918 (class 2606 OID 16411)
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- TOC entry 4950 (class 2606 OID 16943)
-- Name: delivery_addresses delivery_addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.delivery_addresses
    ADD CONSTRAINT delivery_addresses_pkey PRIMARY KEY (id);


--
-- TOC entry 4958 (class 2606 OID 17276)
-- Name: order_items order_items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_pkey PRIMARY KEY (id);


--
-- TOC entry 4954 (class 2606 OID 17294)
-- Name: orders orders_order_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_order_code_key UNIQUE (order_code);


--
-- TOC entry 4956 (class 2606 OID 17250)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 4916 (class 2606 OID 16402)
-- Name: parent_categories parent_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parent_categories
    ADD CONSTRAINT parent_categories_pkey PRIMARY KEY (id);


--
-- TOC entry 4922 (class 2606 OID 16630)
-- Name: product_images product_images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_images
    ADD CONSTRAINT product_images_pkey PRIMARY KEY (id);


--
-- TOC entry 4924 (class 2606 OID 16648)
-- Name: product_variants product_variants_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_variants
    ADD CONSTRAINT product_variants_pkey PRIMARY KEY (id);


--
-- TOC entry 4920 (class 2606 OID 16596)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 4926 (class 2606 OID 16701)
-- Name: products_product_images products_product_images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products_product_images
    ADD CONSTRAINT products_product_images_pkey PRIMARY KEY (product_id, product_images_id);


--
-- TOC entry 4930 (class 2606 OID 16719)
-- Name: products_product_variants products_product_variants_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products_product_variants
    ADD CONSTRAINT products_product_variants_pkey PRIMARY KEY (product_id, product_variants_id);


--
-- TOC entry 4966 (class 2606 OID 17386)
-- Name: review_images review_images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.review_images
    ADD CONSTRAINT review_images_pkey PRIMARY KEY (id);


--
-- TOC entry 4968 (class 2606 OID 17402)
-- Name: review_replies review_replies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.review_replies
    ADD CONSTRAINT review_replies_pkey PRIMARY KEY (id);


--
-- TOC entry 4960 (class 2606 OID 17349)
-- Name: reviews reviews_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_pkey PRIMARY KEY (id);


--
-- TOC entry 4940 (class 2606 OID 16770)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 4942 (class 2606 OID 16804)
-- Name: tokens tokens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tokens
    ADD CONSTRAINT tokens_pkey PRIMARY KEY (id);


--
-- TOC entry 4962 (class 2606 OID 17425)
-- Name: reviews uk1nv3auyahyyy79hvtrcqgtfo9; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT uk1nv3auyahyyy79hvtrcqgtfo9 UNIQUE (user_id, product_id);


--
-- TOC entry 4932 (class 2606 OID 16721)
-- Name: products_product_variants uk5quudb1a0odmf37wtlgasqkmy; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products_product_variants
    ADD CONSTRAINT uk5quudb1a0odmf37wtlgasqkmy UNIQUE (product_variants_id);


--
-- TOC entry 4970 (class 2606 OID 17423)
-- Name: review_replies uknieod5xg5fgu50mleik4u0911; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.review_replies
    ADD CONSTRAINT uknieod5xg5fgu50mleik4u0911 UNIQUE (review_id);


--
-- TOC entry 4928 (class 2606 OID 16703)
-- Name: products_product_images uksqila6k6hg24nbdxmhfas20f3; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products_product_images
    ADD CONSTRAINT uksqila6k6hg24nbdxmhfas20f3 UNIQUE (product_images_id);


--
-- TOC entry 4964 (class 2606 OID 17351)
-- Name: reviews unique_user_product_review; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT unique_user_product_review UNIQUE (user_id, product_id);


--
-- TOC entry 4946 (class 2606 OID 16871)
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 4934 (class 2606 OID 16757)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4936 (class 2606 OID 16761)
-- Name: users users_provider_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_provider_id_key UNIQUE (provider_id);


--
-- TOC entry 4938 (class 2606 OID 16759)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 4952 (class 2606 OID 16993)
-- Name: vouchers vouchers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vouchers
    ADD CONSTRAINT vouchers_pkey PRIMARY KEY (id);


--
-- TOC entry 4982 (class 2606 OID 17329)
-- Name: cart_items cart_items_cart_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_cart_id_fkey FOREIGN KEY (cart_id) REFERENCES public.carts(id) ON DELETE CASCADE;


--
-- TOC entry 4983 (class 2606 OID 16895)
-- Name: cart_items cart_items_product_variant_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_product_variant_id_fkey FOREIGN KEY (product_variant_id) REFERENCES public.product_variants(id);


--
-- TOC entry 4979 (class 2606 OID 17319)
-- Name: carts carts_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT carts_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 4984 (class 2606 OID 16944)
-- Name: delivery_addresses delivery_addresses_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.delivery_addresses
    ADD CONSTRAINT delivery_addresses_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4975 (class 2606 OID 16709)
-- Name: products_product_images fk1eb1dkh350j2g13f88uoa8j8x; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products_product_images
    ADD CONSTRAINT fk1eb1dkh350j2g13f88uoa8j8x FOREIGN KEY (product_id) REFERENCES public.products(id);


--
-- TOC entry 4977 (class 2606 OID 16722)
-- Name: products_product_variants fk57g77rov9fea8uqcyts2wlmkp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products_product_variants
    ADD CONSTRAINT fk57g77rov9fea8uqcyts2wlmkp FOREIGN KEY (product_variants_id) REFERENCES public.product_variants(id);


--
-- TOC entry 4971 (class 2606 OID 16412)
-- Name: categories fk_categories_parent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT fk_categories_parent FOREIGN KEY (parent_category_id) REFERENCES public.parent_categories(id) ON DELETE CASCADE;


--
-- TOC entry 4978 (class 2606 OID 16727)
-- Name: products_product_variants fkg2ftav4jgqy8k50o7ok8t64gw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products_product_variants
    ADD CONSTRAINT fkg2ftav4jgqy8k50o7ok8t64gw FOREIGN KEY (product_id) REFERENCES public.products(id);


--
-- TOC entry 4976 (class 2606 OID 16704)
-- Name: products_product_images fktq8o61quse5x5uhoy0yrxiumm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products_product_images
    ADD CONSTRAINT fktq8o61quse5x5uhoy0yrxiumm FOREIGN KEY (product_images_id) REFERENCES public.product_images(id);


--
-- TOC entry 4988 (class 2606 OID 17277)
-- Name: order_items order_items_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- TOC entry 4989 (class 2606 OID 17282)
-- Name: order_items order_items_product_variant_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_product_variant_id_fkey FOREIGN KEY (product_variant_id) REFERENCES public.product_variants(id);


--
-- TOC entry 4985 (class 2606 OID 17253)
-- Name: orders orders_delivery_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_delivery_address_id_fkey FOREIGN KEY (delivery_address_id) REFERENCES public.delivery_addresses(id) ON DELETE SET NULL;


--
-- TOC entry 4986 (class 2606 OID 17258)
-- Name: orders orders_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4987 (class 2606 OID 17263)
-- Name: orders orders_voucher_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_voucher_id_fkey FOREIGN KEY (voucher_id) REFERENCES public.vouchers(id);


--
-- TOC entry 4973 (class 2606 OID 16631)
-- Name: product_images product_images_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_images
    ADD CONSTRAINT product_images_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- TOC entry 4974 (class 2606 OID 16649)
-- Name: product_variants product_variants_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_variants
    ADD CONSTRAINT product_variants_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- TOC entry 4972 (class 2606 OID 16597)
-- Name: products products_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.categories(id) ON DELETE SET NULL;


--
-- TOC entry 4992 (class 2606 OID 17387)
-- Name: review_images review_images_review_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.review_images
    ADD CONSTRAINT review_images_review_id_fkey FOREIGN KEY (review_id) REFERENCES public.reviews(id) ON DELETE CASCADE;


--
-- TOC entry 4993 (class 2606 OID 17408)
-- Name: review_replies review_replies_admin_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.review_replies
    ADD CONSTRAINT review_replies_admin_id_fkey FOREIGN KEY (admin_id) REFERENCES public.users(id) ON DELETE SET NULL;


--
-- TOC entry 4994 (class 2606 OID 17403)
-- Name: review_replies review_replies_review_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.review_replies
    ADD CONSTRAINT review_replies_review_id_fkey FOREIGN KEY (review_id) REFERENCES public.reviews(id) ON DELETE CASCADE;


--
-- TOC entry 4990 (class 2606 OID 17352)
-- Name: reviews reviews_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- TOC entry 4991 (class 2606 OID 17357)
-- Name: reviews reviews_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE SET NULL;


--
-- TOC entry 4980 (class 2606 OID 16872)
-- Name: user_roles user_roles_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 4981 (class 2606 OID 17324)
-- Name: user_roles user_roles_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


-- Completed on 2025-04-18 10:47:27

--
-- PostgreSQL database dump complete
--

