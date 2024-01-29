alter table TB_EVENTO add column ID_USUARIO bigserial not null;
alter table TB_EVENTO add foreign key (ID_USUARIO) references TB_USUARIO(ID);