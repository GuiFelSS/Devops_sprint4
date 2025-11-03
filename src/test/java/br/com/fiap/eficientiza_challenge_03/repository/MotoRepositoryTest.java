package br.com.fiap.eficientiza_challenge_03.repository;

import br.com.fiap.eficientiza_challenge_03.model.Moto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

/**
 * @DataJpaTest carrega apenas o contexto de persistência (Repositories, Entidades).
 * É ideal para testar a camada de repositório.
 * @ActiveProfiles("test") informa ao Spring para usar o application-test.properties.
 */
@DataJpaTest
@ActiveProfiles("test")
public class MotoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager; // Helper para preparar o banco antes do teste

    @Autowired
    private MotoRepository motoRepository;

    @Test
    public void deveSalvarUmaMotoComSucesso() {
        // Arrange
        Moto moto = new Moto("TESTE123", "Modelo Teste", "Preta", 2023, "DISPONIVEL", "V100");

        // Act
        Moto motoSalva = motoRepository.save(moto);

        // Assert
        Assertions.assertNotNull(motoSalva.getId());
        Assertions.assertEquals("TESTE123", motoSalva.getPlaca());
    }

    @Test
    public void deveBuscarMotoPorIdComSucesso() {
        // Arrange
        Moto moto = new Moto("FINDME456", "Modelo Find", "Azul", 2022, "OCUPADA", "V101");
        // Usamos o entityManager para persistir a moto no H2 antes de testar a busca
        entityManager.persistAndFlush(moto);

        // Act
        Optional<Moto> motoEncontrada = motoRepository.findById(moto.getId());

        // Assert
        Assertions.assertTrue(motoEncontrada.isPresent());
        Assertions.assertEquals("FINDME456", motoEncontrada.get().getPlaca());
    }
}