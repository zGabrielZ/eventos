create table TB_USUARIO(
   ID bigserial primary key,
   NOME varchar(255) not null,
   EMAIL varchar(255) not null,
   SENHA varchar(255) not null,
   DATA_CADASTRO TIMESTAMP not null,
   DATA_ATUALIZACAO TIMESTAMP
);

alter table TB_USUARIO add constraint CK_USUARIO_EMAIL unique(EMAIL);
ALTER TABLE TB_USUARIO ALTER COLUMN DATA_CADASTRO SET DEFAULT current_timestamp AT TIME ZONE 'UTC';
ALTER TABLE TB_USUARIO ALTER COLUMN DATA_ATUALIZACAO SET DEFAULT current_timestamp AT TIME ZONE 'UTC';