package br.quixada.ufc.si.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.Class;

import com.google.protobuf.ByteString;

import br.quixada.ufc.si.classProto.MessageProtos.Message;

public class Despachante {
    @SuppressWarnings("deprecation")
	public byte[] selectEsqueleto(Message request){
        Class<?> objectRef = null;
        Method method = null;
        byte[] resposta = null;
        try {
            objectRef = Class.forName("br.quixada.ufc.si.server.Esqueleto");
            String methodName = request.getMethodId();
            System.out.println("Executando: " + objectRef + methodName);
            method = objectRef.getMethod(methodName, ByteString.class);
            resposta = (byte[]) (method.invoke(objectRef.newInstance(), request.getArguments()));
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (SecurityException e){
            e.printStackTrace();
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }

        return resposta;
    }
}
