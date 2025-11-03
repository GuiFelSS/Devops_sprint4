package br.com.fiap.eficientiza_challenge_03.repository;

import br.com.fiap.eficientiza_challenge_03.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
    Optional<Vaga> findByDescricaoVaga(String descricaoVaga);
}
