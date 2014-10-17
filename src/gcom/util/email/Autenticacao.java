
package gcom.util.email;

import javax.mail.PasswordAuthentication;

public class Autenticacao
				extends javax.mail.Authenticator {

	public PasswordAuthentication getPasswordAuthentication(){

		String user = "vcavalcante@procenge.com.br"; // (ex: "teste@gmail.com")
		String pwd = "teste";
		return new PasswordAuthentication(user, pwd);

	}

}
