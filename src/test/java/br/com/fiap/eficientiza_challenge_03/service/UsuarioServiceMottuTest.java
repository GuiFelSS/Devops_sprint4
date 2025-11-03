package br.com.fiap.eficientiza_challenge_03.service;

import br.com.fiap.eficientiza_challenge_03.model.Usuario;
import br.com.fiap.eficientiza_challenge_03.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; // IMPORTAR
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks; // IMPORTAR
import org.mockito.Mock; // IMPORTAR
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension; // IMPORTAR

// 1. Mudar de @SpringBootTest para @ExtendWith(MockitoExtension.class)
// Isso cria um teste unitário puro, muito mais rápido, sem carregar o Spring.
// Não precisamos mais de @ActiveProfiles("test") aqui.
@ExtendWith(MockitoExtension.class)
public class UsuarioServiceMottuTest {

    // 2. Usar @InjectMocks para criar a instância do serviço
    // e injetar automaticamente os mocks declarados com @Mock nela.
    @InjectMocks
    private UsuarioServiceMottu usuarioService;

    // 3. Usar @Mock (do Mockito) para criar os mocks (substitui @MockBean)
    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    public void deveAdicionarPrefixoNoopAoSalvarNovoUsuario() {
        // 1. Arrange (Preparação)
        Usuario usuarioNovo = new Usuario("Teste User", "teste@email.com", "senha123", "OPERADOR");
        ArgumentCaptor<Usuario> usuarioCaptor = ArgumentCaptor.forClass(Usuario.class);

        // "Ensina" o mock: quando .save() for chamado, apenas retorne o que foi passado
        Mockito.when(usuarioRepository.save(usuarioCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. Act (Ação)
        usuarioService.salvar(usuarioNovo);

        // 3. Assert (Verificação)
        // Verifica se o método .save() do repositório foi chamado exatamente 1 vez
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(ArgumentMatchers.any(Usuario.class));

        // Pega o objeto Usuario que foi capturado no momento do .save()
        Usuario usuarioSalvo = usuarioCaptor.getValue();

        // Verifica se a lógica de negócio (adicionar {noop}) foi executada
        Assertions.assertEquals("teste@email.com", usuarioSalvo.getEmail());
        Assertions.assertTrue(
                usuarioSalvo.getSenha().startsWith("{noop}"),
                "A senha salva deveria começar com {noop}"
        );
        Assertions.assertEquals("{noop}senha123", usuarioSalvo.getSenha());
    }
}