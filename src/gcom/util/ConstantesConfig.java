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
 */

package gcom.util;

import gcom.gui.util.FiltroRecursosExternos;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Esta classe é usada para guardar todos os valores que devem ser constantes
 * durante a execução do sistema. O carregamento das constantes é feita
 * automaticamente durante o classloader desta classe. Após esta inicialização,
 * que é feita antes de qualquer acesso a classe, as constantes podem ser
 * consultadas de forma muito simples. Para o funcionamento desejado o usuário
 * deve pôr um arquivo chamado application.properties no mesmo diretório onde
 * localiza-se esta classe, este arquivo deve conter todos os nomes das
 * constantes do sistemas juntamente com os seus valores como pode ser visto
 * abaixo.
 * 
 * <pre>
 * 
 * 
 *  # Arquivo de Configurações das constantes do sistema
 *  SERVER = www.compesa.com.br
 *  PORT = 8080
 * 
 * @author   rodrigo
 */

public class ConstantesConfig {

	// Nome do Arquivo de propriedades
	private static final String NOME_ARQUIVO_PROPRIEDADES;

	public static final String DIR_RECURSOS_EXTERNOS;

	private static final String URL_WEBSERVICE_ACQUAGIS_TRAMITAROS = "url.webservice.acquagis.tramitaros";

	private static final String AGENDADOR = "servidor.agendador_ativo";

	private static final String VERIFICADOR_IP = "servidor.verificador_ip_ativo";

	private static final String ID_EMPRESA = "id.empresa";

	private static final String EXIBIR_IP_PROCESSO = "exibir_ip_processo";


	// EM SEGUNDOS
	private static final String TEMPO_VERIFICADOR = "servidor.tempo_verificador";

	private static final String ABORTAR_JBOSS_PARAMETROS_AUSENTE = "servidor.abortar_jboss_parametro_ausente";

	private static final String PATH_ARQUIVOS_ANEXADOS = "path_arquivos_anexados";

	private static final String PATH_PADRAO_ARQUIVOS_ANEXADOS;

	private static final String PATH_PADRAO_ARQUIVOS_A_PROCESSAR_ARRECADACAO;

	private static final String PATH_PADRAO_ARQUIVOS_PROCESSADOS_ARRECADACAO;

	private static final String DIRETORIO_DEPLOY_APLICACAO = "diretorio_deploy_aplicacao";

	private static final String PATH_ARQUIVOS_A_PROCESSAR_ARRECADACAO = "path_arquivos_a_processar_arrecadacao";

	private static final String PATH_ARQUIVOS_PROCESSADOS_ARRECADACAO = "path_arquivos_processados_arrecadacao";


	// guarda as constantes contidas no application.properties juntamente com
	// seus valores
	private static Properties propriedades = null;

	// inicialização estática
	static{
		// Nome do Arquivo de propriedades
		DIR_RECURSOS_EXTERNOS = FiltroRecursosExternos.DIR_RECURSOS_EXTERNOS;
		NOME_ARQUIVO_PROPRIEDADES = FiltroRecursosExternos.DIR_RECURSOS_EXTERNOS + "gcom/properties/config.properties";
		PATH_PADRAO_ARQUIVOS_ANEXADOS = FiltroRecursosExternos.DIR_RECURSOS_EXTERNOS + "ARQUIVOS_ANEXADOS";

		PATH_PADRAO_ARQUIVOS_A_PROCESSAR_ARRECADACAO = FiltroRecursosExternos.DIR_RECURSOS_EXTERNOS + "ARQUIVOS_A_PROCESSAR_ARRECADACAO";
		PATH_PADRAO_ARQUIVOS_PROCESSADOS_ARRECADACAO = FiltroRecursosExternos.DIR_ARQUIVOS_PROCESSADOS_ARRECADACAO;


		loadResources();
	}

	/**
	 * Este método retorna o valor da constante passada como parâmetro.
	 * 
	 * @param key
	 *            Nome da constante
	 * @return Descrição do retorno
	 */

	public static String get(String key){

		return propriedades.getProperty(key);
	}

	public static String getAgendador(){

		return ConstantesConfig.get(AGENDADOR);
	}

	public static String getVerificadorIp(){

		return ConstantesConfig.get(VERIFICADOR_IP);
	}

	public static String getIdEmpresa(){

		return ConstantesConfig.get(ID_EMPRESA);
	}

	public static String getExibirIpProcesso(){

		return ConstantesConfig.get(EXIBIR_IP_PROCESSO);
	}

	public static String getTempoVerificador(){

		return ConstantesConfig.get(TEMPO_VERIFICADOR);
	}

	public static String getAbortarJbossParametrosAusente(){

		return ConstantesConfig.get(ABORTAR_JBOSS_PARAMETROS_AUSENTE);
	}

	public static String getUrlWebserviceAcquaGisTramitarOS(){

		return ConstantesConfig.get(URL_WEBSERVICE_ACQUAGIS_TRAMITAROS);
	}

	/**
	 * Carrega o arquivo de properties do sistema
	 */
	private static void loadResources(){

		propriedades = new Properties();

		try{

			propriedades.load(new FileInputStream(NOME_ARQUIVO_PROPRIEDADES));

		}catch(IOException e){
			e.printStackTrace();
			System.err.println("Nao foi possivel localizar o arquivo de propriedades. Certifique-se " + "de que o arquivo "
							+ NOME_ARQUIVO_PROPRIEDADES + " esteja na raiz do CLASSPATH");

		}

	}

	/**
	 * @return
	 */
	public static String getPathRepositorioAnexos(){

		String path = get(PATH_ARQUIVOS_ANEXADOS);
		return Util.isVazioOuBranco(path) ? PATH_PADRAO_ARQUIVOS_ANEXADOS : path;
	}

	public static String getDiretorioDeployAplicacao(){

		String dev_path = get(DIRETORIO_DEPLOY_APLICACAO);
		String path;
		if(Util.isVazioOuBranco(dev_path)){
			path = System.getProperty("jboss.server.home.url").substring(6) + "deploy/gsan/";
		}else{
			path = dev_path;
		}
		return path;
	}

	/**
	 * @return
	 */
	public static String getPathRepositorioArquivosAProcessarArrecadacao(){

		String path = get(PATH_ARQUIVOS_A_PROCESSAR_ARRECADACAO);
		return Util.isVazioOuBranco(path) ? PATH_PADRAO_ARQUIVOS_A_PROCESSAR_ARRECADACAO : path;
	}

	/**
	 * @return
	 */
	public static String getPathRepositorioArquivosProcessadosArrecadacao(){

		String path = get(PATH_ARQUIVOS_PROCESSADOS_ARRECADACAO);
		return Util.isVazioOuBranco(path) ? PATH_PADRAO_ARQUIVOS_PROCESSADOS_ARRECADACAO : path;
	}
}
