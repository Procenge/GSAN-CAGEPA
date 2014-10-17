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

package gcom.relatorio.cadastro.imovel;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.*;

/**
 * [UC3113] Atualizacao Cadastral Coletor de Dados
 * 
 * @author Victon Malcolm
 * @since 08/10/2013
 */
public class RelatorioAtualizacaoCadastralColetorDados
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioAtualizacaoCadastralColetorDados(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ATUALIZACAO_CADASTRAL_COLETOR_DADOS);
	}

	@Deprecated
	public RelatorioAtualizacaoCadastralColetorDados() {

		super(null, "");
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descrição do retorno
	 * @exception RelatorioVazioException
	 *                Descrição da exceção
	 */
	public Object executar() throws TarefaException{
		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------
		Integer referenciaInicial = (Integer) getParametro("referenciaInicial");
		Integer referenciaFinal = (Integer) getParametro("referenciaFinal");
		Integer matricula = (Integer) getParametro("matricula");
		Integer localidade = (Integer) getParametro("localidade");
		Integer setorComercial = (Integer) getParametro("setorComercial");
		Integer rota = (Integer) getParametro("rota");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		byte[] retorno = null;

		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();
		
		RelatorioAtualizacaoCadastralColetorDadosBean relatorioBean = null;
		Collection colecaoAtualizacaoCadastralColetorDados = fachada.pesquisarAtualizacaoCadastralColetorDados(
						referenciaInicial, referenciaFinal, matricula, localidade, setorComercial, rota, true);
		if(colecaoAtualizacaoCadastralColetorDados != null && !colecaoAtualizacaoCadastralColetorDados.isEmpty()){
		 Iterator colecaoAtualizacaoCadastralColetorDadosIterator = colecaoAtualizacaoCadastralColetorDados.iterator();
			String referencia = "";
			String matriculaImovel = "";
			String inscricaoImovel = "";
			String leiturista = "";
			String dataHora = "";
			String numeroImovel = "";
			String complemento = "";
			String numeroHidrometro = "";
			String qntEconomiaResidencial = "";
			String qntEconomiaComercial = "";
			String qntEconomiaIndustrial = "";
			String qntEconomiaPublica = "";
			String numeroImovelAlteracao = "";
			String complementoAlteracao = "";
			String numeroHidrometroAlteracao = "";
			String qntEconomiaResidencialAlteracao = "";
			String qntEconomiaComercialAlteracao = "";
			String qntEconomiaIndustrialAlteracao = "";
			String qntEconomiaPublicaAlteracao = "";
			// laço para criar a coleção de parâmetros da analise
			while(colecaoAtualizacaoCadastralColetorDadosIterator.hasNext()){
				Object[] atualizacaoCadastralColetorDados = (Object[]) colecaoAtualizacaoCadastralColetorDadosIterator.next();
				if(atualizacaoCadastralColetorDados[3] != null){
					referencia = Util.formatarAnoMesParaMesAno(((BigDecimal) atualizacaoCadastralColetorDados[3]).intValue());
				}
				if(atualizacaoCadastralColetorDados[1] != null){
					matriculaImovel = atualizacaoCadastralColetorDados[1].toString();
				}
				if(atualizacaoCadastralColetorDados[2] != null){
					inscricaoImovel = atualizacaoCadastralColetorDados[2].toString();
				}
				if(atualizacaoCadastralColetorDados[4] != null){
					leiturista = atualizacaoCadastralColetorDados[4].toString();
				}
				if(atualizacaoCadastralColetorDados[5] != null){
					dataHora = Util.formatarDataComHora((Date) atualizacaoCadastralColetorDados[5]);
				}
				if(atualizacaoCadastralColetorDados[6] != null){
					numeroImovel = atualizacaoCadastralColetorDados[6].toString();
				}
				if(atualizacaoCadastralColetorDados[7] != null){
					numeroImovelAlteracao = atualizacaoCadastralColetorDados[7].toString();
				}
				if(atualizacaoCadastralColetorDados[8] != null){
					complemento = atualizacaoCadastralColetorDados[8].toString();
				}
				if(atualizacaoCadastralColetorDados[9] != null){
					complementoAlteracao = atualizacaoCadastralColetorDados[9].toString();
				}
				if(atualizacaoCadastralColetorDados[10] != null){
					numeroHidrometro = atualizacaoCadastralColetorDados[10].toString();
				}
				if(atualizacaoCadastralColetorDados[11] != null){
					numeroHidrometroAlteracao = atualizacaoCadastralColetorDados[11].toString();
				}
				if(atualizacaoCadastralColetorDados[12] != null){
					qntEconomiaResidencial = atualizacaoCadastralColetorDados[12].toString();
				}
				if(atualizacaoCadastralColetorDados[13] != null){
					qntEconomiaResidencialAlteracao = atualizacaoCadastralColetorDados[13].toString();
				}
				if(atualizacaoCadastralColetorDados[14] != null){
					qntEconomiaComercial = atualizacaoCadastralColetorDados[14].toString();
				}
				if(atualizacaoCadastralColetorDados[15] != null){
					qntEconomiaComercialAlteracao = atualizacaoCadastralColetorDados[15].toString();
				}
				if(atualizacaoCadastralColetorDados[16] != null){
					qntEconomiaIndustrial = atualizacaoCadastralColetorDados[16].toString();
				}
				if(atualizacaoCadastralColetorDados[17] != null){
					qntEconomiaIndustrialAlteracao = atualizacaoCadastralColetorDados[17].toString();
				}
				if(atualizacaoCadastralColetorDados[18] != null){
					qntEconomiaPublica = atualizacaoCadastralColetorDados[18].toString();
				}
				if(atualizacaoCadastralColetorDados[19] != null){
					qntEconomiaPublicaAlteracao = atualizacaoCadastralColetorDados[19].toString();
				}

				relatorioBean = new RelatorioAtualizacaoCadastralColetorDadosBean(referencia, matriculaImovel, inscricaoImovel, leiturista,
								dataHora, numeroImovel, complemento, numeroHidrometro, qntEconomiaResidencial, qntEconomiaComercial,
								qntEconomiaIndustrial, qntEconomiaPublica, numeroImovelAlteracao, complementoAlteracao,
								numeroHidrometroAlteracao, qntEconomiaResidencialAlteracao, qntEconomiaComercialAlteracao,
								qntEconomiaIndustrialAlteracao, qntEconomiaPublicaAlteracao);
				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);
			}
			}
		 // __________________________________________________________________
		 // Parâmetros do relatório
		 Map parametros = new HashMap();
		 // adiciona os parâmetros do relatório
		 // adiciona o laudo da análise
		 SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		 parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		 parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());
		parametros.put("referenciaInicial", Util.formatarAnoMesParaMesAno(referenciaInicial));
		parametros.put("referenciaFinal", Util.formatarAnoMesParaMesAno(referenciaFinal));
		parametros.put("matricula", matricula != null ? matricula.toString() : "");
		parametros.put("localidade", localidade != null ? localidade.toString() : "");
		parametros.put("setorComercial", setorComercial != null ? setorComercial.toString() : "");
		parametros.put("rota", rota != null ? rota.toString() : "");
		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ATUALIZACAO_CADASTRAL_COLETOR_DADOS, parametros, ds,
						tipoFormatoRelatorio);
		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_ATUALIZACAO_CADASTRAL_COLETOR_DADOS, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// -----------------------------------
		// retorna o relatório gerado

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer referenciaInicial = (Integer) getParametro("referenciaInicial");
		Integer referenciaFinal = (Integer) getParametro("referenciaFinal");
		Integer matricula = (Integer) getParametro("matricula");
		Integer localidade = (Integer) getParametro("localidade");
		Integer setorComercial = (Integer) getParametro("setorComercial");
		Integer rota = (Integer) getParametro("rota");
		Collection colecaoAtualizacaoCadastralColetorDados = Fachada.getInstancia()
						.pesquisarAtualizacaoCadastralColetorDados(referenciaInicial, referenciaFinal, matricula, localidade,
										setorComercial, rota, true);
		return colecaoAtualizacaoCadastralColetorDados.size();
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioFaturamentoConsumoDiretoIndiretoEstadual", this);
	}
}