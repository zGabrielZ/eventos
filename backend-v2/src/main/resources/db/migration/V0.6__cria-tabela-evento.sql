create table TB_EVENTO(
   ID bigserial primary key,
   NOME VARCHAR(255) not null,
   DATA_EVENTO DATE not null,
   URL VARCHAR(255) not null,
   ID_CIDADE bigserial not null,
   DATA_CADASTRO TIMESTAMP not null,
   DATA_ATUALIZACAO TIMESTAMP
);

alter table TB_EVENTO add foreign key (ID_CIDADE) references TB_CIDADE(ID);
ALTER TABLE TB_EVENTO ALTER COLUMN DATA_CADASTRO SET DEFAULT current_timestamp AT TIME ZONE 'UTC';
ALTER TABLE TB_EVENTO ALTER COLUMN DATA_ATUALIZACAO SET DEFAULT current_timestamp AT TIME ZONE 'UTC';