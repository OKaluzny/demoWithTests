alter table users drop is_updated;

alter table users add if not exists is_updated boolean;