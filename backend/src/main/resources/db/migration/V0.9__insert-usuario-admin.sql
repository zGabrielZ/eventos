INSERT INTO TB_USUARIO (NOME, EMAIL, SENHA, DATA_CADASTRO, DATA_ATUALIZACAO)
VALUES ('Gabriel Ferreira', 'ferreiragabriel@email.com', '$2a$10$5SOmXdoSbwD/MN.HVeCRXuOGAGsv2wtCrrOwLstyBiuzj.2AfMZdy', NOW(), NULL);

INSERT INTO TB_USUARIO_PERFIL(ID_USUARIO, ID_PERFIL) VALUES ((select id from tb_usuario where email = 'ferreiragabriel@email.com'), 1);
INSERT INTO TB_USUARIO_PERFIL(ID_USUARIO, ID_PERFIL) VALUES ((select id from tb_usuario where email = 'ferreiragabriel@email.com'), 2);