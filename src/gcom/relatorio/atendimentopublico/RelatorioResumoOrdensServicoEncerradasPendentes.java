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
 * Ivan Sérgio Virginio da Silva Júnior
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

package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ordemservico.bean.RelacaoOrdensServicoEncerradasPendentesHelper;
import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe responsável por criar o relatório de regitro atendimento manter de
 * água
 * 
 * @author Ivan Sérgio
 * @created 12/12/2007
 */
public class RelatorioResumoOrdensServicoEncerradasPendentes
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioResumoOrdensServicoEncerradasPendentes(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_ENCERRADAS_PENDENTES);
	}

	@Deprecated
	public RelatorioResumoOrdensServicoEncerradasPendentes() {

		super(null, "");
	}

	public static final Integer TIPO_RELATORIO_ANALITICO = 1;

	public static final Integer TIPO_RELATORIO_SINTETICO = 2;

	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		String tipoOrdem = (String) getParametro("tipoOrdem");
		String situacaoOrdemServico = (String) getParametro("situacaoOrdemServico");
		String firma = (String) getParametro("firma");
		String nomeFirma = (String) getParametro("nomeFirma");
		String idLocalidade = (String) getParametro("idLocalidade");
		String nomeLocalidade = (String) getParametro("nomeLocalidade");
		String dataEncerramentoInicial = (String) getParametro("dataEncerramentoInicial");
		String dataEncerramentoFinal = (String) getParametro("dataEncerramentoFinal");
		String tipoRelatorio = (String) getParametro("tipoRelatorioAcompanhamento");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioResumoOrdensServicoEncerradasPendentesBean relatorioBean = null;

		Collection colecaoDadosHelper = fachada.pesquisarOrdemServicoGerarRelatorioAcompanhamentoHidrometro(firma, tipoOrdem,
						situacaoOrdemServico, idLocalidade, dataEncerramentoInicial, dataEncerramentoFinal, tipoRelatorio);
		Long quantidadeMotivos = new Long("0");
		if(colecaoDadosHelper != null && !colecaoDadosHelper.isEmpty()){
			Iterator iColecaoDados = colecaoDadosHelper.iterator();

			if(situacaoOrdemServico.equalsIgnoreCase("ENCERRADAS")){

				while(iColecaoDados.hasNext()){
					// dados = (Object[]) iColecaoDados.next();
					RelacaoOrdensServicoEncerradasPendentesHelper helper = (RelacaoOrdensServicoEncerradasPendentesHelper) iColecaoDados
									.next();

					relatorioBean = new RelatorioResumoOrdensServicoEncerradasPendentesBean();

					// Localidade
					relatorioBean.setIdLocalidade(idLocalidade);
					// Situacao selecionada
					relatorioBean.setSituacao(situacaoOrdemServico);
					// Tipo do Servico
					relatorioBean.setTipoServico(tipoOrdem);
					// Periodo encerramento
					if(dataEncerramentoInicial != null && !dataEncerramentoInicial.equals("") && dataEncerramentoFinal != null
									&& !dataEncerramentoFinal.equals("")){
						relatorioBean.setPeriodoEncerramento(dataEncerramentoInicial + " à " + dataEncerramentoFinal);
					}else{
						relatorioBean.setPeriodoEncerramento("");
					}
					// Nome Localidade
					relatorioBean.setNomeLocalidade(nomeLocalidade);
					// Firma
					relatorioBean.setFirma(firma);
					// Nome Firma
					relatorioBean.setNomeFirma(nomeFirma);

					// Motivo Encerramento
					if(helper.getMotivoEncerramento() != null){
						relatorioBean.setMotivoEncerramento(helper.getMotivoEncerramento());
						// Total de Ordens
						relatorioBean.setTotalOrdensServico("" + helper.getQuantidadeOS());
						quantidadeMotivos = quantidadeMotivos + helper.getQuantidadeOS();

					}else{
						relatorioBean.setMotivoEncerramento("");
						relatorioBean.setTotalOrdensServico("");
					}

					// adiciona o bean a coleção
					relatorioBeans.add(relatorioBean);
				}

			}else if(situacaoOrdemServico.equalsIgnoreCase("PENDENTES")){
				relatorioBean = new RelatorioResumoOrdensServicoEncerradasPendentesBean();

				// Localidade
				relatorioBean.setIdLocalidade(idLocalidade);
				// Situacao selecionada
				relatorioBean.setSituacao(situacaoOrdemServico);
				// Tipo do Servico
				relatorioBean.setTipoServico(tipoOrdem);
				// Periodo encerramento
				if(dataEncerramentoInicial != null && !dataEncerramentoInicial.equals("") && dataEncerramentoFinal != null
								&& !dataEncerramentoFinal.equals("")){
					relatorioBean.setPeriodoEncerramento(dataEncerramentoInicial + " à " + dataEncerramentoFinal);
				}else{
					relatorioBean.setPeriodoEncerramento("");
				}
				// Nome Localidade
				relatorioBean.setNomeLocalidade(nomeLocalidade);
				// Firma
				relatorioBean.setFirma(firma);
				// Nome Firma
				relatorioBean.setNomeFirma(nomeFirma);
				// Total de Ordens
				relatorioBean.setTotalOrdensServico("");

				quantidadeMotivos = Long.valueOf(colecaoDadosHelper.size());
				// Motivo Encerramento
				relatorioBean.setMotivoEncerramento("");

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);
			}

			// Parâmetros do relatório
			Map parametros = new HashMap();

			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			parametros.put("imagem", sistemaParametro.getImagemRelatorio());

			parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

			parametros.put("quantidadeMotivos", quantidadeMotivos + "");

			// cria uma instância do dataSource do relatório
			RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

			retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_ENCERRADAS_PENDENTES, parametros, ds,
							tipoFormatoRelatorio);

			// Grava o relatório no sistema
			try{
				persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_RESUMO_OS_ENCERRADAS_PENDENTES, idFuncionalidadeIniciada, null);
			}catch(ControladorException e){
				e.printStackTrace();
				throw new TarefaException("Erro ao gravar relatório no sistema", e);
			}
			// ------------------------------------
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioResumoOrdensServicoEncerradasPendentes", this);
	}
}