CREATE SEQUENCE "public".secure_token_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE "public".secure_token (
    id                   integer DEFAULT nextval('secure_token_id_seq'::regclass) NOT NULL  ,
    auth_id              integer NOT NULL REFERENCES "public".auth( id )  ,
    token                varchar(500) DEFAULT gen_random_uuid()   ,
    expires_at           timestamp NOT NULL  ,
    created_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
    updated_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
    CONSTRAINT pk_secure_token PRIMARY KEY ( id )   ,
    CONSTRAINT unq_secure_token_auth_id UNIQUE ( auth_id )
);

CREATE TRIGGER update_updated_at_secure_token BEFORE UPDATE ON "public".secure_token FOR EACH ROW EXECUTE FUNCTION update_updated_at();