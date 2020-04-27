--create table user
CREATE TABLE public.users
(
    id serial NOT NULL ,
    name text ,
    password text ,
    token text ,
    CONSTRAINT users_pkey PRIMARY KEY (id)
)

--create table cars
CREATE TABLE public.cars
(
    id serial NOT NULL ,
    externid text NOT NULL,
    CONSTRAINT cars_pkey PRIMARY KEY (id)
)

--create table images
CREATE TABLE public.images
(
    id serial NOT NULL DEFAULT,
    carid integer NOT NULL,
    "imageBase64" text NOT NULL,
    CONSTRAINT images_pkey PRIMARY KEY (id),
    CONSTRAINT car_fk FOREIGN KEY (carid) REFERENCES public.cars (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
