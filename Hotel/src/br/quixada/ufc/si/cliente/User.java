package br.quixada.ufc.si.cliente;

import java.util.Scanner;
import br.quixada.ufc.si.classProto.HospedeProtos;
import br.quixada.ufc.si.classProto.HospedeProtos.Hospede;
import br.quixada.ufc.si.classProto.QuartoAlugadoProtos.QuartoAlugado;
import br.quixada.ufc.si.classProto.ReservaProtos.Reserva;

public class User {
    public static Scanner read = new Scanner(System.in);
   
    public static void main(String[] args){
    	Proxy proxyHotel = new Proxy();
        int cont = 0;
        do{
            System.out.println(" -------- MENU -------- ");
            System.out.println(" 1 - Cadastrar Hospede ");
            System.out.println(" 2 - Alugar Quarto ");
            System.out.println(" 3 - Reservar Quarto ");
            System.out.println(" 4 - Servico de Quarto ");
            System.out.println(" 5 - Realizar Checkout ");
            System.out.println(" 6 - Fechar Sistema ");

            //entrada do teclado
            int input = read.nextInt();
            read.nextLine();

            switch (input){
                case 1:
                    //System.gc();
                    System.out.println("Digite o CPF");
                    String cpfClien = read.nextLine();
                    System.out.println("Cpf; "+ cpfClien);

                    System.out.println("Digite o nome do hospede");
                    String nomeCli = read.nextLine();
                    System.out.println("Cpf; "+ nomeCli);

                    System.out.println("Digite o data de nascimento");
                    String dataNasc = read.nextLine();
                    System.out.println("Cpf; "+ dataNasc);

                    System.out.println("Digite o telefone ");
                    String telCli = read.nextLine();
                    System.out.println("Cpf; "+ telCli);

                    System.out.println("Digite o email");
                    String emailCli = read.nextLine();
                    System.out.println("Cpf; "+ emailCli);

                    Hospede hosp =  Hospede.newBuilder()
                            .setName(nomeCli)
                            .setCpf(cpfClien)
                            .setAluguel(0.0)
                            .setServicoQuarto(0.0)
                            .setDataNasc(dataNasc)
                            .setTelefone(telCli)
                            .setEmail(emailCli)
                            .build();
                    proxyHotel.cad(hosp);

                    break;

                case 2:
                    //System.gc();

                    System.out.println("Digite o CPF");
                    String cpfCli = read.nextLine();

                    System.out.println("Digite o N° do Quarto");
                    int numQuarto = read.nextInt();
                    read.nextLine();

                    System.out.println("Data de aluguel");
                    String dataIni = read.nextLine();

                    System.out.println("Data final");
                    String dataFim = read.nextLine();
                    

                    
                    QuartoAlugado quAlu = QuartoAlugado.newBuilder()
                    		.setCpfHospede(cpfCli)
                    		.setNumeroQuarto(numQuarto)
                    		.setDataIni(dataIni)
                    		.setDataFim(dataFim)
                    		.build();
                    		
                    //chamar metodo do Proxy
                   proxyHotel.alu(quAlu);

                    break;

                case 3:
                	 //System.gc();
                    System.out.println("Digite o CPF");
                	String cpfClie = read.nextLine();

                    System.out.println("Digite o NÂ° do Quarto");
                    int numeQuarto = read.nextInt();
                    read.nextLine();

                    System.out.println("Data de aluguel");
                    String dataaIni = read.nextLine();

                    System.out.println("Data final");
                    String dataaFim = read.nextLine();
                    
                    System.out.println("Chegou  aqui com esses dados:"+ numeQuarto + dataaIni + dataaFim);
                    
                    Reserva resHosp = Reserva.newBuilder()
                    		.setCpfHospede(cpfClie)
                    		.setNumeroQuarto(numeQuarto)
                    		.setDataIni(dataaIni)
                    		.setDataFim(dataaFim)
                    		.build();

                    //chamar metodo do Proxy
                   proxyHotel.res(resHosp);

                    break;

                case 4:
                	while(true) {
	                    System.out.println("Pedindo serviço de quarto");
                        System.out.println("Insira seu CPF:");
	                    String CPF = read.nextLine();
	                    //chamada do metodo
	                    if(CPF != null) {
	                    	hosp = proxyHotel.serv(CPF);
	                    	break;
	                    }else {
	                    	System.out.println("Hospede inexistente");
	                    }
            		}
                	System.out.println("Sua conta atual é de: " + (hosp.getAluguel() + hosp.getServicoQuarto()));
                    break;
                case 5:
                    System.out.println("Digite o CPF do cliente");
                    String cpfClient = read.nextLine();

                    System.out.println("Digite o tipo de pagamento");
                    String tipoPag = read.nextLine();

                    //chamada do metodo
                    if(proxyHotel.check(cpfClient) == true) {
                    	System.out.println("Pagamento realizado");
                    }

                    break;

                case 6:
                    cont = 1;
                    proxyHotel.finaliza();
                    break;

                default:
                    System.out.println("Comando invalido");
                break;
            }
        }while (cont == 0);
    }
    
}
