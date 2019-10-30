package br.com.ibm.challenge.service;

import br.com.ibm.challenge.dao.MovimentoCaixaDao;
import br.com.ibm.challenge.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lucas
 */
@Service
@Transactional
public class MovimentoCaixaServiceImpl implements MovimentoCaixaService {

    @Autowired
    private MovimentoCaixaDao movimentoCaixaDao;

    @Autowired
    private CaixaAtmService caixaAtmService;

    @Autowired
    private ContaService contaService;

    @Autowired
    private MovimentoContaService movimentoContaService;

    /**
     * Metodo responsavel pelo movimento de Caixa
     * @param movimentoCaixa
     */
    @Override
    public void save(MovimentoCaixa movimentoCaixa) throws Exception {

        // Verifica o objeto movimentoCaixa
        if (movimentoCaixa == null) {
            throw new Exception("Movimento de Caixa invalido.");
        }

        // Verifica caixa ATM
        if (movimentoCaixa.getCaixaAtm() == null) {
            throw new Exception("Caixa ATM invalido.");

        } else if (caixaAtmService.findById(movimentoCaixa.getCaixaAtm().getId()) == null) {
            throw new Exception("Caixa ATM inexistente.");
        }

        // Caixa ATM
        movimentoCaixa.setCaixaAtm(caixaAtmService.findById(movimentoCaixa.getCaixaAtm().getId()));

        // Verifica o status do caixa ATM
        if (!movimentoCaixa.getCaixaAtm().getStatus().equals(StatusCaixa.ABERTO)) {
            throw new Exception("Caixa fechado!");
        }

        // Verifica a conta do cliente
        if ((movimentoCaixa.getConta() == null) || (movimentoCaixa.getConta().getId() <= 0)) {
            throw new Exception("Conta invalida.");

        } else if (contaService.findById(movimentoCaixa.getConta().getId()) == null) {
            throw new Exception("Conta invalida.");
        }

        // Conta do cliente
        movimentoCaixa.setConta(contaService.findById(movimentoCaixa.getConta().getId()));

        // Verifica valor e saldo para operacoes de SAQUE ou TRANSFERENCIA
        if (movimentoCaixa.getTipoMovimento().equals(TipoMovimento.SAQUE) ||
                movimentoCaixa.getTipoMovimento().equals(TipoMovimento.TRANSFERENCIA)) {

            // Verifica se o valor requisitado para saque e menor que 10 ou mod(10) diferente de zero
            if (movimentoCaixa.getTipoMovimento().equals(TipoMovimento.SAQUE)) {
                if ((movimentoCaixa.getValor().floatValue() < 10f) ||
                        ((movimentoCaixa.getValor().floatValue() % 10) != 0)) {
                    throw new Exception("Valor invalido para saque.");
                }
            }

            // Verifica o saldo
            if (movimentoCaixa.getConta().getSaldo().floatValue() < movimentoCaixa.getValor().floatValue()) {
                throw new Exception("Saldo insuficiente!");
            }
        }

        // Data do movimento
        movimentoCaixa.setDataMov(new Date());

        // Conta destino (transferencia)
        // Variavel de controle para indicar transferencia para conta destino
        boolean Transferencia = false;
        Conta contaDestino = new Conta();

        // Tipo de movimento
        // SAQUE
        if (movimentoCaixa.getTipoMovimento().equals(TipoMovimento.SAQUE)) {

            // Subtrai do saldo
            movimentoCaixa.getConta().setSaldo(movimentoCaixa.getConta().getSaldo().subtract(movimentoCaixa.getValor()));
            movimentoCaixa.setTipoMovimento(TipoMovimento.SAQUE);
            movimentoCaixa.setDescricao(calcularCedulas(movimentoCaixa.getValor().floatValue()));

            // DEPOSITO
        } else if (movimentoCaixa.getTipoMovimento().equals(TipoMovimento.DEPOSITO_CHEQUE) ||
                movimentoCaixa.getTipoMovimento().equals(TipoMovimento.DEPOSITO_DINHEIRO)) {

            // Adiciona ao saldo
            movimentoCaixa.getConta().setSaldo(movimentoCaixa.getConta().getSaldo().add(movimentoCaixa.getValor()));

            if (movimentoCaixa.getTipoMovimento().equals(TipoMovimento.DEPOSITO_CHEQUE)) {
                movimentoCaixa.setDescricao(TipoMovimento.DEPOSITO_CHEQUE
                        + " | Valor: " + movimentoCaixa.getValor());
            } else {
                movimentoCaixa.setDescricao(TipoMovimento.DEPOSITO_DINHEIRO
                        + " | Valor: " + movimentoCaixa.getValor());
            }

            // TRANSFERENCIA
        } else if (movimentoCaixa.getTipoMovimento().equals(TipoMovimento.TRANSFERENCIA)) {

            // Verifica conta destino
            if ((movimentoCaixa.getContaDestino() == null) || (movimentoCaixa.getContaDestino().getId() <= 0)) {
                throw new Exception("Conta destino invalida.");
            }

            contaDestino = contaService.findById(movimentoCaixa.getContaDestino().getId());

            if ((contaDestino == null) || (contaDestino.getId() <= 0)) {
                throw new Exception("Conta destino inexistente.");
            }

            Transferencia = true;

            movimentoCaixa.setContaDestino(contaDestino);

            // Subtrai valor do saldo (conta origem)
            movimentoCaixa.getConta().setSaldo(movimentoCaixa.getConta().getSaldo().subtract(movimentoCaixa.getValor()));
            movimentoCaixa.setTipoMovimento(TipoMovimento.TRANSFERENCIA);
            movimentoCaixa.setDescricao(TipoMovimento.TRANSFERENCIA
                    + " De CONTA DEBITO - Agencia: " + movimentoCaixa.getConta().getCodigoAgencia()
                    + " | Conta: " + movimentoCaixa.getConta().getNumeroConta()
                    + " | Nome: "  + movimentoCaixa.getConta().getNome()
                    + " | Valor: " + movimentoCaixa.getValor()
                    + " Para CONTA CREDITO - Agencia: " + contaDestino.getCodigoAgencia()
                    + " | Conta: " + contaDestino.getNumeroConta());

        } else {
            throw new Exception("Tipo de movimento (operacao) invalido.");
        }

        // Dados do movimento da conta
        MovimentoConta movimentoConta = new MovimentoConta();
        movimentoConta.setConta(movimentoCaixa.getConta());
        movimentoConta.setTipoMovimento(movimentoCaixa.getTipoMovimento());
        movimentoConta.setDataMov(movimentoCaixa.getDataMov());
        movimentoConta.setValor(movimentoCaixa.getValor());
        movimentoConta.setDescricao(movimentoCaixa.getDescricao());

        // Salva dados do movimento de conta
        movimentoContaService.save(movimentoConta);

        // Atualiza saldo da conta
        contaService.update(movimentoCaixa.getConta());

        // BLOCO: Transferencia [dados da conta destino OK]
        if (Transferencia) {
            MovimentoCaixa movimentoCaixaDestino = new MovimentoCaixa();
            movimentoCaixaDestino.setCaixaAtm(movimentoCaixa.getCaixaAtm());
            movimentoCaixaDestino.setDataMov(new Date());
            movimentoCaixaDestino.setConta(contaDestino);
            movimentoCaixaDestino.setContaDestino(null);
            movimentoCaixaDestino.setTipoMovimento(TipoMovimento.TRANSFERENCIA);
            movimentoCaixaDestino.setValor(movimentoCaixa.getValor());

            // Dados do movimento da conta (destino)
            MovimentoConta movimentoContaDestino = new MovimentoConta();
            movimentoContaDestino.setConta(movimentoCaixaDestino.getConta());
            movimentoContaDestino.setTipoMovimento(TipoMovimento.TRANSFERENCIA);
            movimentoContaDestino.setDataMov(movimentoCaixaDestino.getDataMov());
            movimentoContaDestino.setValor(movimentoCaixaDestino.getValor());
            movimentoContaDestino.setDescricao(movimentoCaixa.getDescricao());

            // Adiciona ao saldo da conta (destino)
            movimentoCaixaDestino.getConta().setSaldo(contaDestino.getSaldo().add(movimentoCaixaDestino.getValor()));

            // Atualiza saldo da conta destino
            contaService.update(movimentoCaixaDestino.getConta());

            // Salva dados do movimento da conta destino
            movimentoContaService.save(movimentoContaDestino);

            // Salva dados do movimento de caixa da conta destino
            movimentoCaixaDao.save(movimentoCaixaDestino);
        }

        // Salva dados do movimento de caixa (conta origem)
        movimentoCaixaDao.save(movimentoCaixa);
    }

    /**
     * Metodo responsavel pelo fechamento do caixa
     * @param caixaAtm
     * @return lista de movimentos do caixa
     */
    @Override
    public List<MovimentoCaixa> findAllByCaixaAtm(CaixaAtm caixaAtm) throws Exception {
        // Verifica caixa ATM
        CaixaAtm caixaFechar = caixaAtmService.findById(caixaAtm.getId());

        if (caixaFechar == null) {
            throw new Exception("Caixa ATM inexistente.");
        }

        // Atualiza dados do caixa a fechar
        caixaFechar.setStatus(StatusCaixa.FECHADO);
        caixaFechar.setDataFechamento(new Date());

        caixaAtmService.update(caixaFechar);

        return movimentoCaixaDao.findAllByCaixaAtm(caixaFechar);
    }

    private String calcularCedulas(float valor) {
        String notas = "";
        float[] cedulas = {100f, 50f, 20f, 10f};

        for (int i = 0; i < cedulas.length; i++) {
            if (valor >= cedulas[i]) {
                notas.concat("| notas de " + cedulas[i]);
                valor = valor % cedulas[i];
            }
        }
        notas.concat("| sobram: " + valor );
        return notas.trim();
    }
}
