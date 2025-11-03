package br.com.fiap.eficientiza_challenge_03.service;

import br.com.fiap.eficientiza_challenge_03.model.Moto;
import br.com.fiap.eficientiza_challenge_03.model.Vaga;
import br.com.fiap.eficientiza_challenge_03.repository.MotoRepository;
import br.com.fiap.eficientiza_challenge_03.repository.VagaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; // IMPORTAR
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks; // IMPORTAR
import org.mockito.Mock; // IMPORTAR
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension; // IMPORTAR

import java.util.Optional;

// 1. Mudar de @SpringBootTest para @ExtendWith(MockitoExtension.class)
@ExtendWith(MockitoExtension.class)
public class MotoServiceTest {

    // 2. Usar @InjectMocks para injetar os mocks no serviço
    @InjectMocks
    private MotoService motoService;

    // 3. Usar @Mock (do Mockito) para criar os mocks (substitui @MockBean)
    @Mock
    private MotoRepository motoRepository;

    @Mock
    private VagaRepository vagaRepository;

    @Test
    public void deveSalvarMotoNoServiceEOcuparVaga() {
        // 1. Arrange (Preparação)
        Moto motoParaSalvar = new Moto("MOCK123", "Modelo Mock", "Verde", 2024, "DISPONIVEL", "V200");
        Moto motoSalvaMock = new Moto("MOCK123", "Modelo Mock", "Verde", 2024, "DISPONIVEL", "V200");
        motoSalvaMock.setId(1L);
        Vaga vagaMock = new Vaga(null, "DISPONIVEL", "V200");
        vagaMock.setId(10L);

        // 2. "Ensinar" os Mocks:
        Mockito.when(motoRepository.save(ArgumentMatchers.any(Moto.class)))
                .thenReturn(motoSalvaMock);
        Mockito.when(vagaRepository.findByDescricaoVaga("V200"))
                .thenReturn(Optional.of(vagaMock));

        // 3. Act (Ação)
        Moto resultado = motoService.salvar(motoParaSalvar);

        // 4. Assert (Verificação)
        Assertions.assertNotNull(resultado.getId());
        Assertions.assertEquals("MOCK123", resultado.getPlaca());

        // Verifica se os repositórios foram chamados como esperado
        Mockito.verify(motoRepository, Mockito.times(1)).save(motoParaSalvar);
        Mockito.verify(vagaRepository, Mockito.times(1)).findByDescricaoVaga("V200");
        Mockito.verify(vagaRepository, Mockito.times(1)).save(ArgumentMatchers.any(Vaga.class));
    }
}