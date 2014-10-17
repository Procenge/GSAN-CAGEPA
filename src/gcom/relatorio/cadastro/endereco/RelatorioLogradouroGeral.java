/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.relatorio.cadastro.endereco;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * [UC0XXX] Gerar Relat�rio Logradouro Geral
 * 
 * @author Anderson Italo
 * @date 25/01/2011
 */
public class RelatorioLogradouroGeral
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioLogradouroGeral(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_LOGRADOURO_GERAL);
	}

	@Deprecated
	public RelatorioLogradouroGeral() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// obt�m os par�metros passados
		Integer idLocalidadeInicial = new Integer((String) getParametro("idLocalidadeInicial"));
		Integer idLocalidadeFinal = new Integer((String) getParametro("idLocalidadeFinal"));
		String nomeLocalidadeInicial = (String) getParametro("nomeLocalidadeInicial");
		String nomeLocalidadeFinal = (String) getParametro("nomeLocalidadeFinal");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// monta a cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();
		// obt�m as localidades do intervalo informado
		Collection colecaoLocalidade = fachada.pesquisarLocalidadePorIdIntervalo(idLocalidadeInicial, idLocalidadeFinal);

		for(Iterator iterator = colecaoLocalidade.iterator(); iterator.hasNext();){

			Object[] localidade = (Object[]) iterator.next();
			// obt�m todos os logradouros do munic�pio associado a localidade
			Collection colecaoLogradouros = fachada.pesquisarLogradourosPorMunicipio(new Integer(localidade[2].toString()));

			int totalLogradourosPorLocalidade = 0;

			if(colecaoLogradouros != null && !colecaoLogradouros.isEmpty()){

				Iterator iColecaoLogradouros = colecaoLogradouros.iterator();
				while(iColecaoLogradouros.hasNext()){

					Object[] logradouro = (Object[]) iColecaoLogradouros.next();
					RelatorioLogradouroGeralBean bean = new RelatorioLogradouroGeralBean();

					// 4.1. C�digo da Localidade
					bean.setCodigoLocalidade(localidade[0].toString());

					// 4.2. Nome da Localidade
					bean.setNomeLocalidade(localidade[1].toString());

					// 4.3.1. C�digo do Logradouro
					// Adicionado Zeros a Esquerda para formata��o do c�digo do logradouro
					String formtLocalidade = Util.adicionarZerosEsqueda(1, logradouro[3].toString());
					bean.setCodigoLogradouro(formtLocalidade);

					// Descri��o do Logradouro � Composta pelos seguintes campos:
					// 4.3.2. Tipo do Logradouro
					String descricaoLogradouro = logradouro[1].toString().trim();

					// 4.3.3. Titulo do Logradouro � se existir
					if(logradouro[2] != null && !logradouro[2].toString().equals("")){
						descricaoLogradouro += "  " + logradouro[2].toString().trim();
					}

					// 4.3.4. Nome do Logradouro
					if(logradouro[0] != null && !logradouro[0].toString().equals("")){
						descricaoLogradouro += "  " + logradouro[0].toString().trim();
					}

					bean.setDescricaoLogradouro(descricaoLogradouro);
					totalLogradourosPorLocalidade++;

					if(!iColecaoLogradouros.hasNext()){
						bean.setTotalLogradouroPorLocalidade(Integer.valueOf(totalLogradourosPorLocalidade));
					}

					relatorioBeans.add(bean);
				}
			}else{
				RelatorioLogradouroGeralBean bean = new RelatorioLogradouroGeralBean();

				// 4.1. C�digo da Localidade
				bean.setCodigoLocalidade(localidade[0].toString());

				// 4.2. Nome da Localidade
				bean.setNomeLocalidade(localidade[1].toString());
				bean.setTotalLogradouroPorLocalidade(0);
				relatorioBeans.add(bean);
			}
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("nomeLocalidadeInicial", nomeLocalidadeInicial);
		parametros.put("codigoLocalidadeInicial", idLocalidadeInicial.toString());
		parametros.put("nomeLocalidadeFinal", nomeLocalidadeFinal);
		parametros.put("codigoLocalidadeFinal", idLocalidadeFinal.toString());
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a gera��o do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_LOGRADOURO_GERAL, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_LOGRADOURO_GERAL, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer retorno = 0;

		if(new Integer((String) getParametro("totalRegistrosRelatorio")) != null){
			retorno = new Integer((String) getParametro("totalRegistrosRelatorio"));
		}

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioLogradouroGeral", this);
	}

}