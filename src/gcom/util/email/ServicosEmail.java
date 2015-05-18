/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.util.email;

import gcom.cadastro.sistemaparametro.bean.DadosEnvioEmailHelper;
import gcom.fachada.Fachada;
import gcom.util.ConstantesConfig;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.parametrizacao.ParametroGeral;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

/**
 * Essa classe tem o papel de fornecer ao sistema serviços de e-mail
 * 
 * @author Rodrigo Silveira
 * @author Saulo Lima
 * @date 10/02/2009
 *       Novo atributo 'nomeAbreviadoEmpresa'
 */
public final class ServicosEmail {

	private static Logger LOGGER = Logger.getLogger(ServicosEmail.class);

	private static String SERVIDOR_SMTP;

	private static String NOME_ABREVIADO_EMPRESA;

	public static String EMAIL_ADMINISTRADOR;

	public static String PORTA;

	public static String USUARIO;

	public static String SENHA;

	static{
		DadosEnvioEmailHelper dadosEnvioEmailHelper = Fachada.getInstancia().pesquisarDadosEmailSistemaParametros();
		SERVIDOR_SMTP = dadosEnvioEmailHelper.getIpServidorSmtp();
		NOME_ABREVIADO_EMPRESA = dadosEnvioEmailHelper.getNomeAbreviadoEmpresa();
		EMAIL_ADMINISTRADOR = dadosEnvioEmailHelper.getEmailResponsavel();

		try{
			/**
			 * Atenção!!! também é necessário verificar no servidor jboss se existe a configuração,
			 * no caso de necessidade de login em servidor de email.
			 * Observar o arquivo : run.bat (Windows) ou run.conf (Linux)
			 * set JAVA_OPTS= %JAVA_OPTS% -Dmail.smtp.auth=true -Dmail.smtp.port=587
			 * Pode ser necessário ativar também o starttls: -Dmail.smtp.starttls.enable=true
			 */

			PORTA = ParametroGeral.P_PORTA_SERVIDOR_SMTP.executar();
			USUARIO = ParametroGeral.P_USUARIO_CONEXAO_SMTP.executar();
			SENHA = ParametroGeral.P_SENHA_CONEXAO_SMTP.executar();

		}catch(Exception e){
			LOGGER.error("Falha no carregamento dos parametros de email.", e);
		}
	}

	/**
	 * Construtor da classe ServicosEmail
	 */
	private ServicosEmail() {

		super();
	}

	/**
	 * Método que envia mensagens pelo protocolo SMTP
	 * 
	 * @param remetente
	 *            - Remetente
	 * @param destinatario
	 *            - Destinatário
	 * @param nomeDestinatario
	 *            - nomeDestinatario
	 * @param assunto
	 *            - Assunto
	 * @param body
	 *            - Corpo
	 * @exception ErroEmailException
	 *                - Erro ao enviar email
	 * @author Felipe Rosacruz
	 * @date 13/11/2013
	 */
	public static void enviarMensagem(String remetente, String destinatario, String assunto, String body) throws ErroEmailException{

		enviarMensagem(remetente, destinatario, assunto, body, SERVIDOR_SMTP, PORTA, SENHA, USUARIO);
	}

	/**
	 * Método que envia mensagens com arquivo anexo pelo protocolo SMTP
	 * 
	 * @param destinatario
	 *            - Destinatário
	 * @param from
	 *            - nomeDestinatario
	 * @param subject
	 *            - Assunto
	 * @param body
	 *            - Corpo
	 * @param arquivo
	 *            -Anexo
	 * @exception ErroEmailException
	 *                - Erro ao enviar email
	 * @author Felipe Rosacruz
	 * @date 13/11/2013
	 */
	public static void enviarMensagemArquivoAnexado(String destinatario, String from, String subject, String body, File arquivo)
					throws Exception{

		enviarMensagemArquivoAnexado(destinatario, from, subject, body, arquivo, SERVIDOR_SMTP, PORTA, SENHA, USUARIO);
	}

	/**
	 * Método que envia mensagens pelo protocolo SMTP com as configurações do servidor SMTP
	 * personalizáveis.
	 * Criado inicialmente para fazer testes a partir da tela de testes localizada em
	 * Gsan->Seguranca->Sistema.
	 * Caso não queira enviar um anexo passar null no parâmetro "arquivo".
	 * 
	 * @param remetente
	 *            - Remetente
	 * @param destinatario
	 *            - Destinatário
	 * @param nomeDestinatario
	 *            - nomeDestinatario
	 * @param assunto
	 *            - Assunto
	 * @param body
	 *            - Corpo
	 * @param servidorSMTP
	 * @param NOME_ABREVIADO_EMPRESA
	 * @param porta
	 * @param senha
	 * @exception ErroEmailException
	 *                - Erro ao enviar email
	 * @author Virgínia Melo
	 * @date 16/02/2009
	 *       Alterações da mensagem de exceção
	 */
	public static void enviarMensagemConfiguracaoPersonalizada(String remetente, String destinatario, String assunto, String body,
					File arquivo, String servidorSMTP, String porta, String senha, String usuario) throws Exception{

		if(arquivo == null){
			enviarMensagem(remetente, destinatario, assunto, body, servidorSMTP, porta, senha, usuario);
		}else{
			enviarMensagemArquivoAnexado(destinatario, remetente, assunto, body, arquivo, servidorSMTP, porta, senha, usuario);
		}
	}

	/**
	 * Método que envia mensagens pelo protocolo SMTP
	 * 
	 * @param remetente
	 *            - Remetente
	 * @param destinatario
	 *            - Destinatário
	 * @param nomeDestinatario
	 *            - nomeDestinatario
	 * @param assunto
	 *            - Assunto
	 * @param body
	 *            - Corpo
	 * @param servidorSMTP
	 * @param nomeAbreviadoEmpresa
	 * @param porta
	 * @param senha
	 * @exception ErroEmailException
	 *                - Erro ao enviar email
	 * @author Virgínia Melo
	 * @date 16/02/2009
	 *       Alterações da mensagem de exceção
	 */
	private static void enviarMensagem(String remetente, String destinatario, String assunto, String body, String servidorSMTP,
					String porta, String senha, String usuario) throws ErroEmailException{

		try{
			Properties props = System.getProperties();
			props.put("mail.smtp.host", servidorSMTP);

			if(!Util.isVazioOuBrancoOuZero(porta)){
				props.put("mail.smtp.port", porta);

				String indicadorSmtpSocketFactory = null;
				try{
					indicadorSmtpSocketFactory = ParametroGeral.P_PORTA_SERVIDOR_SMTP_SOCKET_FACTORY_INDICADOR.executar();
				}catch(Exception e){
					LOGGER.error("Falha no carregamento dos parametros de email.", e);
				}
				if(indicadorSmtpSocketFactory != null && indicadorSmtpSocketFactory.equals(ConstantesSistema.ATIVO)){
					props.put("mail.smtp.socketFactory.port", porta);
					// props.put("mail.smtp.socketFactory.port", "587");
					props.put("mail.smtp.auth", "true");
					// props.put("mail.smtp.socketFactory.fallback", "false");
					// props.put("mail.smtp.starttls.enable", "true");
				}
			}

			Session session = Session.getDefaultInstance(props, null);

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(remetente, NOME_ABREVIADO_EMPRESA));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario, false));
			msg.setSubject(assunto);
			msg.setText(body);
			msg.setSentDate(new Date());

			if(!Util.isVazioOuBrancoOuZero(usuario) && !Util.isVazioOuBrancoOuZero(senha)){
				Transport transport = session.getTransport("smtp");
				transport.connect(usuario, senha);
				transport.sendMessage(msg, msg.getAllRecipients());
				transport.close();
			}else{
				Transport.send(msg);
			}

		}catch(Exception ex){
			ex.printStackTrace();
			// throw new ErroEmailException("atencao.envio_email");
		}
	}

	private static void enviarMensagemArquivoAnexado(String destinatario, String from, String subject, String body, File arquivo,
					String servidorSMTP, String porta, String senha, String usuario) throws Exception{

		try{
			copiarParaRepositorioDeAnexos(arquivo);

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(body);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			DataSource source = new FileDataSource(arquivo);

			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(source.getName());
			multipart.addBodyPart(messageBodyPart);

			Properties props = System.getProperties();
			props.put("mail.smtp.host", servidorSMTP);

			if(!Util.isVazioOuBrancoOuZero(porta)){
				props.put("mail.smtp.port", porta);

				String indicadorSmtpSocketFactory = null;
				try{
					indicadorSmtpSocketFactory = ParametroGeral.P_PORTA_SERVIDOR_SMTP_SOCKET_FACTORY_INDICADOR.executar();
				}catch(Exception e){
					LOGGER.error("Falha no carregamento dos parametros de email.", e);
				}
				if(indicadorSmtpSocketFactory != null && indicadorSmtpSocketFactory.equals(ConstantesSistema.ATIVO)){
					props.put("mail.smtp.socketFactory.port", porta);
					props.put("mail.smtp.auth", "true");
				}
			}

			Session session = Session.getDefaultInstance(props, null);

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from, NOME_ABREVIADO_EMPRESA));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario, false));
			msg.setSubject(subject);
			msg.setContent(multipart);
			msg.setSentDate(new Date());
			msg.saveChanges();

			Transport transport = session.getTransport("smtp");

			if(!Util.isVazioOuBrancoOuZero(usuario) && !Util.isVazioOuBrancoOuZero(senha)){
				transport.connect(usuario, senha);
			}else{
				transport.connect();
			}

			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

		}catch(Exception ex){
			ex.printStackTrace();
			// throw new Exception("erro.sistema", ex);
		}
	}

	/**
	 * @param arquivo
	 * @throws IOException
	 */
	private static void copiarParaRepositorioDeAnexos(File arquivo){

		try{
			File destination = new File(ConstantesConfig.getPathRepositorioAnexos() + File.separator + arquivo.getName());
			Util.copyFile(arquivo, destination);
		}catch(IOException e){
			LOGGER.error("Falha ao fazer copia de segurança de arquivo[" + arquivo.getAbsolutePath() + "]");
		}
	}

	/**
	 * Prepara a exceção para ser mostrada como conteúdo no e-mail
	 * 
	 * @param exception
	 *            Exceção
	 * @return String com o stacktrace da exceção
	 */
	public static String processarExceptionParaEnvio(Exception exception){

		StringBuffer retorno = new StringBuffer();

		retorno.append("Erro ocorrido no sistema GSAN: \n\n");
		retorno.append(exception.getMessage() + "\n");

		for(int i = 0; i < exception.getStackTrace().length; i++){
			retorno.append(exception.getStackTrace()[i] + "\n");
		}

		return retorno.toString();
	}

}
