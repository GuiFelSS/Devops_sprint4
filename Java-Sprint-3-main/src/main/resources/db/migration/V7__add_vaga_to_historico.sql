ALTER TABLE tb_mtt_historico_moto_c3_java ADD COLUMN id_vaga BIGINT;

ALTER TABLE tb_mtt_historico_moto_c3_java
ADD CONSTRAINT fk_historico_vaga
FOREIGN KEY (id_vaga) REFERENCES tb_mtt_vaga_c3_java(id_vaga);