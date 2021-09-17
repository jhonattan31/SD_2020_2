package br.quixada.ufc.si.cliente;

import java.io.*;
import java.net.*;

import br.quixada.ufc.si.classProto.HospedeProtos;
import br.quixada.ufc.si.classProto.MessageProtos;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import br.quixada.ufc.si.classProto.MessageProtos.Message;


public class UDPCliente{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	DatagramSocket socket;
	String ip;
	int port;

	public UDPCliente(String serverIP, int port) {
		try {
			socket = new DatagramSocket();
			this.ip = serverIP;
			this.port = port;
		} catch (SocketException e) {
			e.printStackTrace();
		}	
		
	}
	

	public void sendRequest(byte[] requisicao) {
		try {
			InetAddress aHost = InetAddress.getByName(ip);	                                                 
			DatagramPacket request = new DatagramPacket(requisicao,  requisicao.length, aHost, port);
			socket.send(request);
			System.out.println("Messagem enviada");
			
			}catch (SocketException e){
				System.out.println("Socket: " + e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public byte[] getReply() {
		byte[] buffer = new byte[1000];	
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		try {
			socket.receive(reply);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Messagem Recebida");
		byte[] rep = new byte[reply.getLength()];
		System.arraycopy(reply.getData(), reply.getOffset(), rep, 0, reply.getLength());
		return rep;
	}
	


	public void finaliza() {
		socket.close();
	}

}
