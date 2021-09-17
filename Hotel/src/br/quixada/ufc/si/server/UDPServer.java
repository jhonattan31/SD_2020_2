package br.quixada.ufc.si.server;

import java.io.*;
import java.net.*;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import br.quixada.ufc.si.classProto.MessageProtos.*;
import br.quixada.ufc.si.model.Historico;


public class UDPServer{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	static DatagramPacket request = null;
	static DatagramPacket reply = null;
	static int id = 0;

	public static void main(String args[]) {
		DatagramSocket serverSocket = null;
		Despachante despachante;
		try {
			serverSocket = new DatagramSocket(4226);
			despachante = new Despachante();

			System.out.println("conectado a " + ":" + serverSocket.getLocalPort());

			while (true) {
				Message requisicao = desempacotaRequisicao(getRequest(serverSocket));
				byte[] resultado = null;
				Historico hist = lerArquivo();
				id++;
				if(hist == null){
					resultado = despachante.selectEsqueleto(requisicao);
					escreverArquivo(requisicao.toBuilder().getId(), request.getPort(), request.getAddress().toString(), resultado);
				}

				if(id > 1
					&& hist.getIp().equals(request.getAddress().toString())
					&& hist.getPort() == request.getPort()
					&& hist.getIdRequisicao() != requisicao.toBuilder().getId()) {
					resultado = despachante.selectEsqueleto(requisicao);
					escreverArquivo(requisicao.toBuilder().getId(), request.getPort(), request.getAddress().toString(), resultado);
				}
				sendReply(empacotaResposta(resultado, requisicao.getId()), serverSocket);

			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} finally {
			if (serverSocket != null)
				serverSocket.close( );
		}
	}
	public static byte[] getRequest(DatagramSocket serverSocket) {
		byte[] buffer = new byte[1000];
		request = new DatagramPacket(buffer, buffer.length);
		try {
			serverSocket.receive(request);
			System.out.println("Chegou aqui");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Messagem Recebida");
		byte[] rep = new byte[request.getLength()];
		System.arraycopy(request.getData(), request.getOffset(), rep, 0, request.getLength());
		return rep;
		// enviado via moodle
	}

	public static void sendReply(byte[] resposta, DatagramSocket serverSocket) {
		try {
			reply = new DatagramPacket(resposta,  resposta.length, request.getAddress(), request.getPort());
			serverSocket.send(reply);
			System.out.println("Messagem enviada");

		}catch (SocketException e){
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// enviado via moodle
	}


	public static Message desempacotaRequisicao(byte[] array) {
		try {
			return Message.parseFrom(array);
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		// Desempacota mensagem de requisicao
	}

	public static byte[] empacotaResposta(byte[] resultado, int requestId) {
		Historico hist = lerArquivo();
		Message msg = Message.newBuilder().build();
		if(hist != null) {
			if (requestId == hist.getIdRequisicao()) {
				msg.toBuilder().setType(1);
				msg.toBuilder().setId(requestId);
				msg.toBuilder().setArguments(ByteString.copyFrom(resultado));
				return msg.toByteArray();
			}
		}
		return null;

		// Empacota mensagem de resposta
	}

	public static Historico lerArquivo() {
		String caminho = new String("Message.txt");
		try {
			FileReader ler = new FileReader(caminho);
			BufferedReader lendo = new BufferedReader(ler);
			try {
				Historico hist = new Historico();
				try {
					hist.setIp(lendo.readLine());
					hist.setPort(Integer.parseInt(lendo.readLine()));
					hist.setIdRequisicao(Integer.parseInt(lendo.readLine()));
					hist.setResposta(lendo.readLine().getBytes());
					System.out.println(hist.toString());
					ler.close();
					return hist;
				} catch (NumberFormatException ex) {
					System.out.println("Erro ao ler");
				}
			} catch (IOException ex) {
				System.out.println("Erro ao ler");
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Erro arquivo nao encontrado ao escrever");
		}
		return null;
	}
	public static void escreverArquivo(int id, int porta, String ip, byte[] resp){
		File file = new File("Message.txt");

		try {
			file.createNewFile();
			FileWriter escrever = new FileWriter(file);
			PrintWriter escrita = new PrintWriter(escrever);
			escrita.println(ip);
			escrita.println(porta);
			escrita.println(id);
			escrita.println(resp);
			escrita.close();

		} catch (IOException ex) {
			System.out.println("Erro ao escrever");
		}
	}
}