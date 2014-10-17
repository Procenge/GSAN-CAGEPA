
package gcom.util.agendadortarefas;

import gcom.util.ConstantesConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class VerificadorIP {

	private final static VerificadorIP instancia = new VerificadorIP();

	private String ipAtivo;

	private String ip;

	public static VerificadorIP getInstancia(){

		return instancia;
	}

	private VerificadorIP() {

		try{
			InetAddress thisIP = InetAddress.getLocalHost();
			ipAtivo = thisIP.getHostAddress();
			ip = ipAtivo;
		}catch(UnknownHostException e){
			e.printStackTrace();
		}

		String flagIpAtivo = ConstantesConfig.getVerificadorIp();

		if("2".equals(flagIpAtivo)){
			ipAtivo = null;
		}
	}

	public String getIpAtivo(){

		return ipAtivo;
	}

	public String getIp(){

		return ip;
	}

}
