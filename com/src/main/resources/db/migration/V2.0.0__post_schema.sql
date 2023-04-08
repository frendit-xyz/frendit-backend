--Link Type Create
CREATE DOMAIN link AS text
CHECK(VALUE ~ '^[(http(s)?):\/\/(www\.)?a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)$')
CHECK(LENGTH(VALUE) <= 250);

--Index Sequences
CREATE SEQUENCE "public".category_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE "public".emoji_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE "public".image_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE "public".location_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE "public".mood_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE "public".activity_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE "public".post_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE "public".post_image_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE "public".circle_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE "public".comment_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE "public".categories (
	id                   integer DEFAULT nextval('category_id_seq'::regclass) NOT NULL  ,
	slug                 varchar(100)   ,
	title                varchar(100) NOT NULL   ,
	created_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	updated_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	CONSTRAINT pk_category PRIMARY KEY ( id )   ,
	CONSTRAINT unq_category_slug UNIQUE ( slug )  ,
	CONSTRAINT check_slug CHECK (slug ~* '^[a-z0-9]+(?:-[a-z0-9]+)*$')
 );

CREATE TABLE "public".emojis (
	id                   integer DEFAULT nextval('emoji_id_seq'::regclass) NOT NULL  ,
	code                 varchar(20)    ,
	name                 varchar(100) NOT NULL    ,
	shorthand            varchar(20)    ,
	css_class            varchar(100)    ,
	created_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	updated_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	CONSTRAINT pk_emoji PRIMARY KEY ( id )  ,
	CONSTRAINT unq_emoji_code UNIQUE ( code )
 );

CREATE TABLE "public".images (
	id                   integer DEFAULT nextval('image_id_seq'::regclass) NOT NULL  ,
	name                 varchar(100) NOT NULL    ,
	blur                 boolean DEFAULT false  ,
	created_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	updated_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	CONSTRAINT pk_image PRIMARY KEY ( id )  ,
	CONSTRAINT unq_image_name UNIQUE ( name )
 );

CREATE TABLE "public".locations (
	id                   integer DEFAULT nextval('location_id_seq'::regclass) NOT NULL  ,
	lon                  numeric    ,
	lat                  numeric    ,
	name                 varchar(100) NOT NULL    ,
	address              varchar(300)    ,
	thumbnail            varchar    ,
	created_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	updated_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	CONSTRAINT pk_location PRIMARY KEY ( id )   ,
	CONSTRAINT unq_location_name UNIQUE ( name )
 );

CREATE TABLE "public".moods (
	id                   integer DEFAULT nextval('mood_id_seq'::regclass) NOT NULL  ,
	emoji_id             integer NOT NULL REFERENCES "public".emojis( id )  ,
	name                 varchar(100) NOT NULL   ,
	created_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	updated_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	CONSTRAINT pk_mood PRIMARY KEY ( id )   ,
	CONSTRAINT unq_mood_name UNIQUE ( name )
 );

CREATE TABLE "public".activities (
    id                   integer DEFAULT nextval('activity_id_seq'::regclass) NOT NULL  ,
    emoji_id             integer NOT NULL REFERENCES "public".emojis( id )  ,
    name                 varchar(100) NOT NULL   ,
    created_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
    updated_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
    CONSTRAINT pk_activity PRIMARY KEY ( id )   ,
    CONSTRAINT unq_activity_name UNIQUE ( name )
);

CREATE TABLE "public".posts (
    id                   integer DEFAULT nextval('post_id_seq'::regclass) NOT NULL  ,
    content              varchar(1500)   ,
    bg_text              varchar(250)    ,
    bg_color             varchar(20)    ,
    video_link           link   ,
    gif_link             link   ,
    links                link[] ,
    publish_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
    activity_details     varchar(100)   ,
    activity_id          integer REFERENCES "public".activities( id )  ,
    author_id            integer NOT NULL REFERENCES "public".auth( id )  ,
    location_id          integer REFERENCES "public".locations( id )  ,
    mood_id              integer REFERENCES "public".moods( id )  ,
    category_id          integer REFERENCES "public".categories( id )  ,
    status               varchar(20) CHECK (status IN ( 'DRAFT', 'PUBLISHED', 'BANNED', 'TRASHED'  ))    ,
    can_react            boolean DEFAULT true   ,
    can_comment          boolean DEFAULT true   ,
    can_vote             boolean DEFAULT true   ,
    boost                numeric DEFAULT 1  ,
    created_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
    updated_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
    CONSTRAINT pk_post PRIMARY KEY ( id )
);

CREATE TABLE "public".post_views (
	post_id              integer NOT NULL REFERENCES "public".posts( id )  ,
	auth_id              integer NOT NULL REFERENCES "public".auth( id )  ,
	CONSTRAINT pk_post_view PRIMARY KEY ( post_id,  auth_id)
 );

CREATE TABLE "public".post_loves (
    post_id              integer NOT NULL REFERENCES "public".posts( id )  ,
    auth_id              integer NOT NULL REFERENCES "public".auth( id )  ,
    amount               integer DEFAULT 1 CHECK (amount >= 1 AND amount <= 10)   ,
    CONSTRAINT pk_post_love PRIMARY KEY ( post_id,  auth_id)
);

CREATE TABLE "public".post_images (
	post_id              integer NOT NULL REFERENCES "public".posts( id )  ,
	image_id             integer NOT NULL REFERENCES "public".images( id )  ,
	CONSTRAINT pk_post_image PRIMARY KEY ( post_id,  image_id)
 );

CREATE TABLE "public".circles (
	id                   integer DEFAULT nextval('circle_id_seq'::regclass) NOT NULL  ,
	author_id            integer NOT NULL REFERENCES "public".auth( id )  ,
	name                 varchar(20)    ,
	description          varchar(20)    ,
	created_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
    updated_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
    CONSTRAINT pk_circle PRIMARY KEY ( id )
 );

CREATE TABLE "public".circle_members (
    circle_id            integer NOT NULL REFERENCES "public".circles( id ),
    member_id            integer NOT NULL REFERENCES "public".auth( id ),
    CONSTRAINT pk_circle_member PRIMARY KEY (circle_id, member_id)
);

CREATE TABLE "public".comments (
    id                   integer DEFAULT nextval('comment_id_seq'::regclass) NOT NULL  ,
    content              varchar(1500)   ,
    video_link           link   ,
    gif_link             link   ,
    links                link[] ,
    image_id             integer REFERENCES "public".images( id )  ,
    parent_id            integer REFERENCES "public".comments( id )  ,
    author_id            integer NOT NULL REFERENCES "public".auth( id )  ,
    status               varchar(20) CHECK (status IN ( 'DRAFT', 'PUBLISHED', 'BANNED', 'TRASHED'  ))    ,
    created_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
    updated_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
    CONSTRAINT pk_comment PRIMARY KEY ( id )
);

CREATE TABLE "public".comment_views (
	comment_id           integer NOT NULL REFERENCES "public".comments( id )  ,
	auth_id              integer NOT NULL REFERENCES "public".auth( id )  ,
	CONSTRAINT pk_comment_view PRIMARY KEY ( comment_id,  auth_id)
 );

CREATE TABLE "public".comment_loves (
	comment_id           integer NOT NULL REFERENCES "public".comments( id )  ,
	auth_id              integer NOT NULL REFERENCES "public".auth( id )  ,
	amount               integer DEFAULT 1 CHECK (amount >= 1 AND amount <= 10)   ,
	CONSTRAINT pk_comment_love PRIMARY KEY ( comment_id,  auth_id)
 );

CREATE TRIGGER update_updated_at_category BEFORE UPDATE ON "public".categories FOR EACH ROW EXECUTE FUNCTION update_updated_at();
CREATE TRIGGER update_updated_at_emoji BEFORE UPDATE ON "public".emojis FOR EACH ROW EXECUTE FUNCTION update_updated_at();
CREATE TRIGGER update_updated_at_image BEFORE UPDATE ON "public".images FOR EACH ROW EXECUTE FUNCTION update_updated_at();
CREATE TRIGGER update_updated_at_location BEFORE UPDATE ON "public".locations FOR EACH ROW EXECUTE FUNCTION update_updated_at();
CREATE TRIGGER update_updated_at_mood BEFORE UPDATE ON "public".moods FOR EACH ROW EXECUTE FUNCTION update_updated_at();
CREATE TRIGGER update_updated_at_activity BEFORE UPDATE ON "public".activities FOR EACH ROW EXECUTE FUNCTION update_updated_at();
CREATE TRIGGER update_updated_at_post BEFORE UPDATE ON "public".posts FOR EACH ROW EXECUTE FUNCTION update_updated_at();
CREATE TRIGGER update_updated_at_post_image BEFORE UPDATE ON "public".post_images FOR EACH ROW EXECUTE FUNCTION update_updated_at();
CREATE TRIGGER update_updated_at_circle BEFORE UPDATE ON "public".circles FOR EACH ROW EXECUTE FUNCTION update_updated_at();
CREATE TRIGGER update_updated_at_comment BEFORE UPDATE ON "public".comments FOR EACH ROW EXECUTE FUNCTION update_updated_at();

CREATE TRIGGER "category_slug_insert" BEFORE INSERT ON "public".categories FOR EACH ROW WHEN (NEW.title IS NOT NULL AND NEW.slug IS NULL) EXECUTE PROCEDURE set_slug_from_title();
