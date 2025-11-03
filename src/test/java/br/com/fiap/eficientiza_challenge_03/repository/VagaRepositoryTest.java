package br.com.fiap.eficientiza_challenge_03.repository;

import br.com.fiap.eficientiza_challenge_03.model.Vaga;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

/**
 * Teste para a camada de persistência (Repository) da Vaga.
 * @DataJpaTest carrega apenas o contexto do JPA.
 * @ActiveProfiles("test") usa o application-test.properties (H2)
 */
@DataJpaTest
@ActiveProfiles("test")
public class VagaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager; // Para preparar o banco

    @Autowired
    private VagaRepository vagaRepository;

    @Test
    public void deveSalvarUmaVagaDisponivelComSucesso() {
        // 1. Arrange
        // Criamos uma vaga nova, sem moto (moto=null), como corrigimos
        Vaga vagaNova = new Vaga(null, "DISPONIVEL", "V500");

        // 2. Act
        Vaga vagaSalva = vagaRepository.save(vagaNova);

        // 3. Assert
        Assertions.assertNotNull(vagaSalva.getId());
        Assertions.assertEquals("V500", vagaSalva.getDescricaoVaga());
        Assertions.assertNull(vagaSalva.getMoto());
    }

    @Test
    public void deveBuscarVagaPelaDescricao() {

        Vaga vagaSetup = new Vaga(null, "MANUTENCAO", "V501");
        entityManager.persistAndFlush(vagaSetup);

        // 2. Act
        // Buscamos pela descrição que acabamos de salvar
        Optional<Vaga> vagaEncontrada = vagaRepository.findByDescricaoVaga("V501");

        // 3. Assert
        Assertions.assertTrue(vagaEncontrada.isPresent());
        Assertions.assertEquals("V501", vagaEncontrada.get().getDescricaoVaga());
        Assertions.assertEquals("MANUTENCAO", vagaEncontrada.get().getStatus());
    }
}