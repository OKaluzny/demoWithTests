create table public.users (
                              id integer primary key not null default nextval('users_id_seq'::regclass),
                              name character varying(255),
                              country character varying(255),
                              email character varying(255),
                              phone_number bigint,
                              is_updated boolean default false,
                              is_deleted boolean default false,
                              employee_id integer,
                              foreign key (employee_id) references public.salary (id)
                                  match simple on update no action on delete no action
);

create table public.addresses (
                                  id bigint primary key not null default nextval('addresses_id_seq'::regclass),
                                  address_has_active boolean,
                                  city character varying(255),
                                  country character varying(255),
                                  house_number character varying(255),
                                  street character varying(255),
                                  employee_id integer,
                                  foreign key (employee_id) references public.users (id)
                                      match simple on update no action on delete no action
);

create table public.salary (
                               id integer primary key not null default nextval('salary_id_seq'::regclass),
                               commission_rate double precision,
                               hourly_rate integer
);

