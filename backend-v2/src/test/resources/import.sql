INSERT INTO TB_PERFIL(DESCRICAO, AUTORIEDADE) VALUES ('Administrador', 'ROLE_ADMIN');
INSERT INTO TB_PERFIL(DESCRICAO, AUTORIEDADE) VALUES ('Cliente', 'ROLE_CLIENTE');

INSERT INTO TB_USUARIO(NOME, EMAIL, SENHA, DATA_CADASTRO) VALUES('José da Silva', 'jose@email.com', '123', NOW());
INSERT INTO TB_USUARIO_PERFIL(ID_USUARIO, ID_PERFIL) VALUES (1, 1);
INSERT INTO TB_USUARIO_PERFIL(ID_USUARIO, ID_PERFIL) VALUES (1, 2);