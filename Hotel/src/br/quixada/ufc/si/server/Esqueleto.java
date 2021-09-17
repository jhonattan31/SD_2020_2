package br.quixada.ufc.si.server;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import br.quixada.ufc.si.classProto.HospedeProtos.Hospede;
import br.quixada.ufc.si.classProto.QuartoAlugadoProtos.QuartoAlugado;
import br.quixada.ufc.si.classProto.ReservaProtos.Reserva;


public class Esqueleto {
	Servente servente;
	
	public Esqueleto() {
		this.servente = new Servente();
	}
	
	@SuppressWarnings("static-access")
	public byte[] cad(ByteString args){
		try {
			Hospede hosp = Hospede.newBuilder().build();
			hosp = hosp.parseFrom(args.toByteArray());
			byte[] n = (servente.cad(hosp)).toByteArray();
			return n;
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

    @SuppressWarnings("static-access")
	public byte[] alu(ByteString args){
    	try {
    		QuartoAlugado quarto = QuartoAlugado.newBuilder().build();
    		quarto = quarto.parseFrom(args.toByteArray());
			byte[] n = (servente.alu(quarto)).toByteArray();
			return n;
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    @SuppressWarnings("static-access")
    public byte[] res(ByteString args){
    	try {
    		Reserva resv = Reserva.newBuilder().build();
    		resv = resv.parseFrom(args.toByteArray());
			byte[] n = (servente.res(resv)).toByteArray();
			return n;
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public byte[] serv(ByteString args){
		byte[] n = (servente.serv(new String(args.toByteArray()))).toByteArray();
		if(n != null){
    		return n;
		}
		return null;
    }
    
    public byte[] check(ByteString args){
		byte[] n = (servente.check(new String(args.toByteArray()))).getBytes();
		if(n != null){
			return n;
		}
		return null;
    }
}
