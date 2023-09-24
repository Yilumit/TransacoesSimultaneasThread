package view;
import java.math.BigDecimal;
import java.math.RoundingMode;
/*
 * 	Sistema de Saque e Deposito usando Threads
 * 	O sistema permite um saque e um deposito por vez, mas nunca 2 saques ou 2 depositos 
 * 	20 transacoes simultaneas sao enviadas ao sistema
 * 
 */
import java.util.concurrent.Semaphore;

import controller.TransacaoThread;

public class Principal {

	public static void main(String[] args) {
		int permicoes = 1;
		Semaphore semaforo = new Semaphore(permicoes);
		
		for (int i = 0; i < 20; i++) {
			double saldo = (Math.random()*10000);
			//Formata o valor para que ele fique com apenas 2 casas decimais apos a virgula
			BigDecimal bSaldo = new BigDecimal(saldo).setScale(2, RoundingMode.HALF_EVEN);	// '2' == numero de casas decimais; 'HALF_EVEN' indica que o arredondamento sempre deve acontecer para o valor masi proximo
			
			double valorTransacao = (Math.random()*10000);
			BigDecimal bTransacao = new BigDecimal(valorTransacao).setScale(2, RoundingMode.HALF_EVEN);
			
			TransacaoThread tran = new TransacaoThread(100+i, bSaldo.doubleValue(), bTransacao.doubleValue(), semaforo);
			tran.start();
		}
	}

}
