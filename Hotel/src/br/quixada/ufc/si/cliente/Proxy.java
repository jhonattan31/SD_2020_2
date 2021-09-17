package br.quixada.ufc.si.cliente;

import java.net.SocketException;

import br.quixada.ufc.si.classProto.HospedeProtos;
import br.quixada.ufc.si.classProto.QuartoAlugadoProtos;
import br.quixada.ufc.si.classProto.ReservaProtos;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import br.quixada.ufc.si.classProto.HospedeProtos.Hospede;
import br.quixada.ufc.si.classProto.MessageProtos.Message;
import br.quixada.ufc.si.classProto.QuartoAlugadoProtos.QuartoAlugado;
import br.quixada.ufc.si.classProto.ReservaProtos.Reserva;

import static br.quixada.ufc.si.classProto.HospedeProtos.Hospede.*;

public class Proxy{

    UDPCliente cliente;
    int id = 0;
    
    public Proxy() {
    	this.cliente = new UDPCliente("localhost", 4226);
    }

	public boolean cad(Hospede hosp){
		byte[] serie1 = hosp.toByteArray();
		Hospede resp1 = null;
		try {
			resp1 = Hospede.parseFrom(doOperation("Hospede", "cad", serie1));
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}

		if(resp1 == null) {
			System.out.println("Hospede cadastrado com sucesso");
			return true;
		}
		return false;
	}

    public boolean alu(QuartoAlugado quarto){
		byte[] serie = quarto.toByteArray();
		QuartoAlugado resp2 = null;
		try {
			resp2 = QuartoAlugado.parseFrom(doOperation("QuartoAlugado", "alu", serie));
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		if(resp2 != null) {
        	System.out.println("Aluguel Feito com sucesso");
        	return true;
        }
        return false;
    }
    
    
	
	public boolean res(Reserva resv){
    	byte[] serie = resv.toByteArray();
		Reserva resp2 = null;
		try {
			resp2 = Reserva.parseFrom(doOperation("Reserva", "res", serie));
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		if(resp2 != null) {
        	System.out.println("Reserva Feita com sucesso");
        	return true;
        }
        return false;
    }

    public Hospede serv(String hosp){
    	byte[] serie = hosp.getBytes();
        try {
        	Hospede alu = parseFrom(doOperation("Hospede", "serv", serie));
			return alu;
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }

	public boolean check(String hosp){
    	byte[] serie = hosp.getBytes();
    	String resp1 = new String(doOperation("Hospede", "check", serie));
    	
    	if(hosp.equals(resp1)) {
        	System.out.println("Checkout Feita com sucesso");
        	return true;
        }
        return false;
 
    }
    public byte[] doOperation(String ref, String methodId, byte[] args) {
		byte[] msg = empacotaResposta(ref, methodId, args);
    	cliente.sendRequest(msg);
    	byte[] resposta = cliente.getReply();
		try {
			cliente.socket.setSoTimeout(10000);
		} catch (SocketException e) {
			cliente.sendRequest(msg);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Message data = desempacotaRequisicao(resposta);
    	return data.getArguments().toByteArray();
    }
	public Message desempacotaRequisicao(byte[] array) {
		try {
			return Message.parseFrom(array);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
			return null;
		}
		// Desempacota mensagem de requisicao
	}

	public byte[] empacotaResposta(String ref, String methodId, byte[] args) {
		Message requisicao = Message.newBuilder()
				.setType(0)
				.setId(this.id++)
				.setMethodId(methodId)
				.setObfReference(ref)
				.setArguments(ByteString.copyFrom(args))
				.build();


		return requisicao.toByteArray();
		// Empacota mensagem de resposta
	}
    
    public void finaliza() {
    	cliente.finaliza();
    }
}
