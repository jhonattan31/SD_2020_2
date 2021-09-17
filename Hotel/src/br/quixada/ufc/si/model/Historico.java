package br.quixada.ufc.si.model;


import java.net.InetAddress;
import java.util.Arrays;

public class Historico {

	private String ip;
	private int port;
	private int idRequisicao;
	private byte[] resposta;
	
	public Historico() {
		
	}
	
	public Historico(int idRequisicao, String ip, int port, byte[] resposta) {
		super();
		this.idRequisicao = idRequisicao;
		this.ip = ip;
		this.port = port;
		this.resposta = resposta;
	}

	public int getIdRequisicao() {
		return this.idRequisicao;
	}

	public void setIdRequisicao(int idRequisicao) {
		this.idRequisicao = idRequisicao;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public byte[] getResposta() {
		return resposta;
	}
	public void setResposta(byte[] resposta) {
		this.resposta = resposta;
	}

	@Override
	public String toString() {
		return "Historico{" +
				"ip=" + ip +
				", port=" + port +
				", idRequisicao=" + idRequisicao +
				", resposta=" + Arrays.toString(resposta) +
				'}';
	}
}
