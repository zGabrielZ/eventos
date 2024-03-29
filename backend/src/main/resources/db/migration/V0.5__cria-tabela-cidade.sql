create table TB_CIDADE(
   ID bigserial primary key,
   CEP varchar(12) not null,
   LOGRADOURO varchar(150) not null,
   COMPLEMENTO varchar(150),
   BAIRRO VARCHAR(150) not null,
   LOCALIDADE VARCHAR(150) not null,
   UF VARCHAR(2) not null,
   IBGE VARCHAR(150),
   GIA VARCHAR(150),
   DDD VARCHAR(10),
   SIAFI VARCHAR(25),
   DATA_CADASTRO TIMESTAMP not null,
   DATA_ATUALIZACAO TIMESTAMP
);

ALTER TABLE TB_CIDADE ALTER COLUMN DATA_CADASTRO SET DEFAULT current_timestamp AT TIME ZONE 'UTC';
ALTER TABLE TB_CIDADE ALTER COLUMN DATA_ATUALIZACAO SET DEFAULT current_timestamp AT TIME ZONE 'UTC';