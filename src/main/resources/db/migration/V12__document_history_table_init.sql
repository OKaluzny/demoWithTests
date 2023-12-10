create table if not exists "history"
(
    "id"            serial,
    "description"   varchar(255),
    "date_and_time" timestamp,
    "document"      int,
    constraint "id_pk" primary key ("id"),
    constraint "document_fk" foreign key ("document") references documents ("id")
);
