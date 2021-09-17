package br.quixada.ufc.si.server;


import br.quixada.ufc.si.model.Historico;
import org.joda.time.DateTime;
import org.joda.time.Days;


import br.quixada.ufc.si.classProto.HospedeProtos.*;
import br.quixada.ufc.si.classProto.QuartoAlugadoProtos.*;
import br.quixada.ufc.si.classProto.QuartoProtos.*;
import br.quixada.ufc.si.classProto.ReservaProtos.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.*;
import java.util.ArrayList;

public class Servente {
	
	ArrayList<Hospede> listaHosp;
	ArrayList<Quarto> listaQuarto;
	ArrayList<QuartoAlugado> listaAlugado;
	ArrayList<Reserva> listaReserva;
	
	public Servente() {
		listaHosp = new ArrayList<Hospede>();
		Hospede h1 = Hospede.newBuilder()
				.setName("Jhon")
				.setCpf("123")
				.setServicoQuarto(0.0)
				.setAluguel(0.0)
				.setEmail("jhon@gmail")
				.setTelefone("4002-8922")
				.build();
		listaHosp.add(h1);
		listaQuarto = new ArrayList<Quarto>();
		Quarto q1 = Quarto.newBuilder()
				.setDescricao("Quarto Hotel Solteiro")
				.setDisponivel(true)
				.setNumero(101)
				.setValorDiaria(199.90)
				.build();
		listaQuarto.add(q1);
		Quarto q2 = Quarto.newBuilder()
				.setDescricao("Quarto Hotel Casal")
				.setDisponivel(true)
				.setNumero(201)
				.setValorDiaria(399.90)
				.build();
		listaQuarto.add(q2);
		Quarto q3 = Quarto.newBuilder()
				.setDescricao("Quarto Hotel PerNoite")
				.setDisponivel(true)
				.setNumero(301)
				.setValorDiaria(99.90)
				.build();
		listaQuarto.add(q3);
		listaAlugado = new ArrayList<QuartoAlugado>();
		listaReserva = new ArrayList<Reserva>();
	}
	public Hospede cad(Hospede hosp) {
		if(hosp != null) {
			listaHosp.add(hosp);
			System.out.println("Hospede Adicionado");
			return hosp;
		}
		System.out.println("Erro ao adicinar hospede, tente novamente");
		return null;
		
	}
	 public QuartoAlugado alu(QuartoAlugado quarto){
		Quarto disp = this.procQua(quarto.toBuilder().getNumeroQuarto());
		 
		 if(disp != null) {
			 if(disp.getDisponivel() == true) {
				 disp.toBuilder().setDisponivel(false);
				 listaAlugado.add(quarto);
				 Hospede alugo = this.procHosp(quarto.getCpfHospede());
				 alugo.toBuilder().setAluguel(disp.getValorDiaria());
				 System.out.println(alugo.toString());
				 System.out.println("Quarto Alugado com sucesso");
				 return quarto;
			 }else {
				 System.out.println("Quarto Já reservado");
			        return null;
			 }
		 }
		 	System.out.println("Erro ao alugar quarto");
	        return null;
	  }
	    
	    public Reserva res(Reserva resv){
	    	Quarto disp = this.procQua(resv.toBuilder().getNumeroQuarto());
			 
			 if(disp != null) {
				 if(disp.getDisponivel() == true) {
					 disp.toBuilder().setDisponivel(false);
					 listaReserva.add(resv);
					 Hospede alugo = this.procHosp(resv.getCpfHospede());
					 System.out.println(alugo.toString());
					 System.out.println("Quarto Reservado com sucesso");
					 return resv;
				 }else {
					 System.out.println("Quarto Já Utilizado");
				        return null;
				 }
			 }
			 	System.out.println("Erro ao alugar quarto");
		        return null;
	    }
	    
	    public Hospede procHosp(String Cpf) {
	    	for(Hospede hosp : listaHosp) {
	    		if(hosp.toBuilder().getCpf().equals(Cpf)) {
	    			return hosp;
	    		}
	    	}
	    	return null;
	    }
	    
	    public Quarto procQua(int numQuarto) {
	    	for(Quarto quar : listaQuarto) {
	    		if(quar.toBuilder().getNumero() == numQuarto) {
	    			return quar;
	    		}
	    	}
	    	return null;
	    }
	    
	    public QuartoAlugado procAlu(String Cpf) {
	    	for(QuartoAlugado quar : listaAlugado) {
	    		if(quar.toBuilder().getCpfHospede().equals(Cpf)) {
	    			return quar;
	    		}
	    	}
	    	return null;
	    }
	    
		public Hospede serv(String hosp){
	    	Hospede hos = this.procHosp(hosp);
	    	hos.toBuilder().setServicoQuarto(35.90);
	    	System.out.println("Servico de quarto realizado");
	    	return hos;
	    }

	    public String check(String hosp){
	    	QuartoAlugado quat = procAlu(hosp);
	    	Hospede hos = this.procHosp(hosp);
	    	if(quat != null) {
				DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
	    		DateTime dataInicial = formatter.parseDateTime(quat.toBuilder().getDataIni());
	    		DateTime dataFinal =  formatter.parseDateTime(quat.toBuilder().getDataFim());;
	    		int days = Days.daysBetween(dataInicial, dataFinal).getDays();
	    		
	    		double ValorTotal = (days * hos.toBuilder().getAluguel()) + hos.toBuilder().getServicoQuarto();
	    		System.out.println("Valor Total a pagar: " + ValorTotal);
	    		int index = 0;
	    		int index2 = 0;
	    		for(Quarto quar : listaQuarto) {
	    			index++;
		    		if(quar.toBuilder().getNumero() == quat.toBuilder().getNumeroQuarto()) {
		    			break;
		    		}
		    	}
	    		
	    		for(Hospede h : listaHosp) {
	    			index2++;
		    		if(h.toBuilder().getCpf().equals(hos.toBuilder().getCpf())) {
		    			break;
		    		}
		    	}
	    		System.out.println("Checkout realizado");
	    		if(listaAlugado.remove(index-1) != null) {
	    			System.out.println("Quarto liberado");
	    			Quarto q = this.procQua(quat.toBuilder().getNumeroQuarto());
	    			q.toBuilder().setDisponivel(false);
	    			if(listaHosp.remove(index2-1) != null){
	    				System.out.println("Hospede Excluido");
	    				return hosp;
	    			}
	    		}
	    		
	    	}
	        return "0";
	    }
}
