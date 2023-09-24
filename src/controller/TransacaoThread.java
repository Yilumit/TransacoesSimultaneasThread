package controller;

import java.util.concurrent.Semaphore;

public class TransacaoThread extends Thread{
	private int codConta;
	private double saldo;
	private double valorTransacao;
	private static Semaphore saque;
	private static Semaphore deposito;
	
	public TransacaoThread(int codConta, double saldo, double valorTransacao, Semaphore semaforo) {
		this.codConta = codConta;
		this.saldo = saldo;
		this.valorTransacao = valorTransacao;
		saque = semaforo;
		deposito = semaforo;
	}
	
	@Override
	public void run() {
		int transacao = (int)(Math.random()*1)+1;	//Escolha aleatoria (1 - Sacar; 2 - Depositar)
		
		if (transacao == 1) {
			
			//Permite que apenas 1 saque ocorre por vez
			try {
				//Quando uma thread estiver utilizando o metodo saque as outras que estiverem requisitando-o aguardarao em fila
				saque.acquire();
				sacar();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				saque.release();
			}
		} else {
			//Permite que apenas 1 deposito ocorra por vez
			try {
				deposito.acquire();
				depositar();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				deposito.release();
			}
		}
		
		
	}
	
	//1 - Sacar valor 
	private void sacar() {
		System.out.println("Saque na conta de nº "+codConta+"\nValor do saque: "+valorTransacao
				+ "\nSaldo anterior:"+saldo);
		saldo -= valorTransacao;
		try {
			sleep(1500);
			//Caso o saldo fique negativo
			if (saldo < 0) {
				System.err.println("O valor do saque é maior do que o saldo em conta \nA conta "+codConta+" esta em debito com o banco");
			}
			
			System.out.printf("Transacao finalizada! \nSaldo atual: %.2f %n", saldo);
			sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("*******************************************\n");
		
		
	}
	
	// 2 - Deposito na conta
	private void depositar() {
		System.out.println("Deposito na conta de nº "+codConta+"\nValor do deposito: "+valorTransacao
				+ "\nSaldo anterior:"+saldo);
		saldo += valorTransacao;
		try {
			sleep(1500);
			System.out.printf("Transacao finalizada! \nSaldo atual: %.2f %n", saldo);
			sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("*******************************************");
		
	}
	
}
