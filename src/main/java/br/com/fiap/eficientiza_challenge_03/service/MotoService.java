package br.com.fiap.eficientiza_challenge_03.service;

import br.com.fiap.eficientiza_challenge_03.model.Moto;
import br.com.fiap.eficientiza_challenge_03.repository.MotoRepository;
import br.com.fiap.eficientiza_challenge_03.repository.VagaRepository; // <-- IMPORTAR
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    private final MotoRepository motoRepository;
    private final VagaRepository vagaRepository; // <-- ADICIONAR

    // ATUALIZAR O CONSTRUTOR
    public MotoService(MotoRepository motoRepository, VagaRepository vagaRepository) {
        this.motoRepository = motoRepository;
        this.vagaRepository = vagaRepository;
    }

    @Transactional
    public Moto salvar(Moto moto) {
        // Salva a moto
        Moto motoSalva = motoRepository.save(moto);

        // Ocupa a vaga nova (se houver)
        ocuparVaga(motoSalva.getVaga(), motoSalva);

        return motoSalva;
    }

    @Transactional
    public Moto atualizar(Long id, Moto dados) {
        Moto existente = buscarPorId(id);

        String vagaAntigaDesc = existente.getVaga();
        String vagaNovaDesc = dados.getVaga();

        // Atualiza os dados da moto
        existente.setPlaca(dados.getPlaca());
        existente.setModelo(dados.getModelo());
        existente.setCor(dados.getCor());
        existente.setAno(dados.getAno());
        existente.setStatus(dados.getStatus());
        existente.setVaga(dados.getVaga());

        Moto motoAtualizada = motoRepository.save(existente);

        // Lógica de atualização das vagas
        if (vagaAntigaDesc == null || vagaAntigaDesc.isEmpty()) {
            if (vagaNovaDesc != null && !vagaNovaDesc.isEmpty()) {
                // Caso 1: Não tinha vaga, agora tem.
                ocuparVaga(vagaNovaDesc, motoAtualizada);
            }
        } else {
            if (vagaNovaDesc == null || vagaNovaDesc.isEmpty()) {
                // Caso 2: Tinha vaga, agora não tem.
                liberarVaga(vagaAntigaDesc);
            } else if (!vagaAntigaDesc.equals(vagaNovaDesc)) {
                // Caso 3: Tinha vaga, e mudou para outra.
                liberarVaga(vagaAntigaDesc);
                ocuparVaga(vagaNovaDesc, motoAtualizada);
            }
            // Se vagaAntigaDesc.equals(vagaNovaDesc), não faz nada.
        }

        return motoAtualizada;
    }

    public List<Moto> listar() {
        return motoRepository.findAll();
    }

    public Moto buscarPorId(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada: " + id));
    }

    @Transactional
    public void excluir(Long id) {
        Moto moto = buscarPorId(id); // Busca a moto ANTES de excluir
        String vagaDesc = moto.getVaga();

        if (!motoRepository.existsById(id)) {
            throw new IllegalArgumentException("Moto não encontrada: " + id);
        }

        // Libera a vaga que a moto estava usando
        liberarVaga(vagaDesc);

        // Exclui a moto
        motoRepository.deleteById(id);
    }

    private void ocuparVaga(String vagaDesc, Moto moto) {
        if (vagaDesc == null || vagaDesc.isEmpty()) return;

        vagaRepository.findByDescricaoVaga(vagaDesc).ifPresent(vaga -> {
            vaga.setStatus("OCUPADA");
            vaga.setMoto(moto);
            vagaRepository.save(vaga);
        });
    }

    private void liberarVaga(String vagaDesc) {
        if (vagaDesc == null || vagaDesc.isEmpty()) return;

        vagaRepository.findByDescricaoVaga(vagaDesc).ifPresent(vaga -> {
            vaga.setStatus("DISPONIVEL");
            vaga.setMoto(null);
            vagaRepository.save(vaga);
        });
    }
}