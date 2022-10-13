alter table users drop is_deleted;

alter table users add if not exists is_deleted boolean;
