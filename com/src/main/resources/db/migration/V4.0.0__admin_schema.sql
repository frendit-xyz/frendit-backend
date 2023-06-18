CREATE SEQUENCE "public".admin_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE "public".admin (
    id                   integer DEFAULT nextval('admin_id_seq'::regclass) NOT NULL  ,
    auth_id              integer NOT NULL REFERENCES "public".auth( id )  ,
    role                 varchar(20) DEFAULT 'USER' CHECK (role IN ('ADMIN', 'MODERATOR', 'USER')),
    created_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
    updated_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
    CONSTRAINT pk_admin PRIMARY KEY ( id )   ,
    CONSTRAINT unq_admin_auth_id UNIQUE ( auth_id )
);

CREATE TRIGGER update_updated_at_admin BEFORE UPDATE ON "public".admin FOR EACH ROW EXECUTE FUNCTION update_updated_at();