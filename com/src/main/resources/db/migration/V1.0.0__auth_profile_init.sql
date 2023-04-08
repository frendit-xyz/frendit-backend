--Index Sequences
CREATE SEQUENCE "public".auth_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE "public".profile_id_seq START WITH 1 INCREMENT BY 1;

--Tables
CREATE TABLE "public".auth (
    id                   integer DEFAULT nextval('auth_id_seq'::regclass) NOT NULL  ,
	username             varchar(50)  DEFAULT gen_random_uuid()  ,
	hashed_password      varchar(100)    ,
	email                varchar(100)  NOT NULL    ,
	verified             boolean DEFAULT false  ,
	refresh_token        varchar(100)   ,
	refreshed_at         timestamp   ,
	created_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	updated_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	CONSTRAINT pk_auth PRIMARY KEY ( id ),
	CONSTRAINT unq_auth_username UNIQUE ( username ) ,
	CONSTRAINT unq_auth_email UNIQUE ( email )  ,
	CONSTRAINT check_username CHECK (username ~* '^(?=.{4,50}$)(?![_.-])(?!.*[_.-]{2})[a-zA-Z0-9._-]+(?<![_.-])$')  ,
	CONSTRAINT check_email CHECK (email ~* '^[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')
 );

CREATE TABLE "public".profiles (
    id                   integer DEFAULT nextval('profile_id_seq'::regclass) NOT NULL  ,
	issuer               varchar(10) DEFAULT 'NATIVE'   ,
	issuer_user_id       varchar(100)    ,
	first_name           varchar(20)    ,
	last_name            varchar(20)    ,
	avatar               varchar(200)    ,
	contact_email        varchar(100)   ,
	email_verified       boolean DEFAULT false  ,
	auth_id              integer NOT NULL REFERENCES "public".auth( id )  ,
	created_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	updated_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	CONSTRAINT pk_profile PRIMARY KEY ( id ),
	CONSTRAINT unq_profile_auth_id UNIQUE ( auth_id ) ,
	CONSTRAINT check_contact_email CHECK (contact_email ~* '^[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')
 );

--Update Function

CREATE OR REPLACE FUNCTION "public".update_updated_at()
RETURNS trigger
LANGUAGE plpgsql
AS $function$
BEGIN
   NEW.updated_at = now();
   RETURN NEW;
END;
$function$
;

--Slugify Function
CREATE EXTENSION IF NOT EXISTS "unaccent";

CREATE OR REPLACE FUNCTION slugify("value" TEXT)
RETURNS TEXT AS $$
  -- removes accents (diacritic signs) from a given string --
  WITH "unaccented" AS (
    SELECT unaccent("value") AS "value"
  ),
  -- lowercases the string
  "lowercase" AS (
    SELECT lower("value") AS "value"
    FROM "unaccented"
  ),
  -- remove single and double quotes
  "removed_quotes" AS (
    SELECT regexp_replace("value", '[''"]+', '', 'gi') AS "value"
    FROM "lowercase"
  ),
  -- replaces anything that's not a letter, number, hyphen('-'), or underscore('_') with a hyphen('-')
  "hyphenated" AS (
    SELECT regexp_replace("value", '[^a-z0-9\\-_]+', '-', 'gi') AS "value"
    FROM "removed_quotes"
  ),
  -- trims hyphens('-') if they exist on the head or tail of the string
  "trimmed" AS (
    SELECT regexp_replace(regexp_replace("value", '\-+$', ''), '^\-', '') AS "value"
    FROM "hyphenated"
  )
  SELECT "value" FROM "trimmed";
$$ LANGUAGE SQL STRICT IMMUTABLE;

--Title to Slug Function
CREATE FUNCTION public.set_slug_from_title() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  NEW.slug := slugify(NEW.title);
  RETURN NEW;
END
$$;

--Unique Array Integer Function
CREATE FUNCTION public.array_unique_int (a integer[]) RETURNS integer[] as $$
  SELECT array (
    SELECT DISTINCT v FROM unnest(a) AS b(v)
  )
$$ LANGUAGE SQL STRICT;

CREATE TRIGGER update_updated_at_auth BEFORE UPDATE ON "public".auth FOR EACH ROW EXECUTE FUNCTION update_updated_at();
CREATE TRIGGER update_updated_at_profile BEFORE UPDATE ON "public".profiles FOR EACH ROW EXECUTE FUNCTION update_updated_at();