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

package gcom.relatorio.faturamento;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.faturamento.ResumoAnaliseFaturamentoHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.gerencial.faturamento.RelatorioAnaliseFaturamentoBean;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * classe responsável por criar o relatório de bairro manter de água
 * 
 * @author Fernanda Paiva
 * @created 11 de Julho de 2005
 */
public class RelatorioAnaliseFaturamento
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	private static final String STRING_TODOS = "TODOS";

	public RelatorioAnaliseFaturamento(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ANALISE_FATURAMENTO);
	}

	/**
	 * Método que executa para a geração do relatório.
	 * 
	 * @param localidades
	 *            Description of the Parameter
	 * @param localidadeParametros
	 *            Description of the Parameter
	 * @return Descrição do retorno
	 * @exception RelatorioVazioException
	 *                Descrição da exceção
	 */
	public Object executar() throws TarefaException{

		Fachada fachada = Fachada.getInstancia();

		List<ResumoAnaliseFaturamentoHelper> retornoConsulta = (List<ResumoAnaliseFaturamentoHelper>) getParametro("colecaoAnaliseFaturamento");
		Integer tipoAnalise = (Integer) getParametro("tipoAnalise");
		// String descricao = (String) getParametro("descricao");
		String mesAnoFaturamento = (String) getParametro("mesAnoFaturamento");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		RelatorioAnaliseFaturamentoBean relatorioBean = null;

		for(ResumoAnaliseFaturamentoHelper item : retornoConsulta){

			relatorioBean = new RelatorioAnaliseFaturamentoBean();

			relatorioBean.setCreditosRealizados(Util.formataBigDecimal(item.getCreditosRealizados(), 2, true));
			relatorioBean.setDebitosCobrados(Util.formataBigDecimal(item.getDebitosCobrados(), 2, true));
			relatorioBean.setDescricao(item.getDescricao());
			// bean.setDescricaoCampoQuebra(item.getde);
			// bean.setIdCampoQuebra(idCampoQuebra)(""+ item.get);
			relatorioBean.setQuantidadeConta(item.getQuantidadeConta().toString());
			relatorioBean.setQuantidadeEconomia(item.getQuantidadeEconomia().toString());
			relatorioBean.setTotalCobrado(Util.formataBigDecimal(item.getTotalCobrado(), 2, true));
			relatorioBean.setValorFaturadoAgua(Util.formataBigDecimal(item.getValorFaturadoAgua(), 2, true));
			relatorioBean.setValorFaturadoEsgoto(Util.formataBigDecimal(item.getValorFaturadoEsgoto(), 2, true));
			relatorioBean.setVolumeColetadoEsgoto(item.getVolumeColetadoEsgoto().toString());
			relatorioBean.setVolumeConsumidoAgua(item.getVolumeConsumidoAgua().toString());

			// adiciona o bean a coleção
			relatorioBeans.add(relatorioBean);
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		parametros.put("mesAnoFaturamento", mesAnoFaturamento);
		String descricaoAnalise = "";
		if(tipoAnalise == 2){
			descricaoAnalise = "REAL";
		}else{
			descricaoAnalise = "SIMULADO";
		}
		parametros.put("tipoAnalise", descricaoAnalise);
		InformarDadosGeracaoRelatorioConsultaHelper dadosParametrosConsulta = (InformarDadosGeracaoRelatorioConsultaHelper) getParametro("informarDadosGeracaoRelatorioConsultaHelper");

		adicionarLabelTitulo(parametros, sistemaParametro, dadosParametrosConsulta);

		montarMapParametros(parametros, dadosParametrosConsulta);
		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ANALISE_FATURAMENTO, parametros, ds, tipoFormatoRelatorio);

		// retorna o relatório gerado
		return retorno;
	}

	private void adicionarLabelTitulo(Map parametros, SistemaParametro sistemaParametro,
					InformarDadosGeracaoRelatorioConsultaHelper dadosParametrosConsulta){

		Integer opcaoTotalizacao = dadosParametrosConsulta.getOpcaoTotalizacao();
		String titulo;
		switch(opcaoTotalizacao){
			case ConstantesSistema.CODIGO_ESTADO:
				titulo = "ESTADO (" + sistemaParametro.getNomeEstado() + ")";
				break;
			case ConstantesSistema.CODIGO_ESTADO_ELO_POLO:
				titulo = "ESTADO (" + sistemaParametro.getNomeEstado() + ") POR ELO PÓLO. ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL:
				titulo = "ESTADO (" + sistemaParametro.getNomeEstado() + ") POR GERÊNCIA REGIONAL. ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_COBRANCA:
				titulo = "ESTADO (" + sistemaParametro.getNomeEstado() + ") POR GRUPO DE COBRANÇA. ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO:
				titulo = "ESTADO (" + sistemaParametro.getNomeEstado() + ") POR GRUPO DE FATURAMENTO. ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_LOCALIDADE:
				titulo = "ESTADO (" + sistemaParametro.getNomeEstado() + ") POR LOCALIDADE. ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO:
				titulo = "ESTADO (" + sistemaParametro.getNomeEstado() + ") POR UNIDADE DE NEGÓCIO. ";
				break;
			case ConstantesSistema.CODIGO_GRUPO_FATURAMENTO:
				titulo = "GRUPO DE FATURAMENTO (" + dadosParametrosConsulta.getFaturamentoGrupo().getDescricao() + ")";
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL:
				titulo = "GERÊNCIA REGIONAL (" + dadosParametrosConsulta.getGerenciaRegional().getNomeComId() + ")";
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO:
				titulo = "GERÊNCIA REGIONAL (" + dadosParametrosConsulta.getGerenciaRegional().getNomeComId() + ") POR ELO PÓLO. ";
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE:
				titulo = "GERÊNCIA REGIONAL (" + dadosParametrosConsulta.getGerenciaRegional().getNomeComId() + ") POR LOCALIDADE. ";
				break;
			case ConstantesSistema.CODIGO_ELO_POLO:
				titulo = "ELO PÓLO (" + dadosParametrosConsulta.getEloPolo().getDescricaoComId() + ")";
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE:
				titulo = "ELO PÓLO (" + dadosParametrosConsulta.getEloPolo().getId() + " - "
								+ dadosParametrosConsulta.getEloPolo().getDescricao() + ") POR LOCALIDADE. ";
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL:
				titulo = "ELO PÓLO (" + dadosParametrosConsulta.getEloPolo().getId() + " - "
								+ dadosParametrosConsulta.getEloPolo().getDescricao() + ") POR SETOR COMERCIAL. ";
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE:
				titulo = "LOCALIDADE (" + dadosParametrosConsulta.getLocalidade().getDescricaoComId() + ")";
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_QUADRA:
				titulo = "LOCALIDADE (" + dadosParametrosConsulta.getLocalidade().getId() + " - "
								+ dadosParametrosConsulta.getLocalidade().getDescricao() + ") POR QUADRA. ";
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_SETOR_COMERCIAL:
				titulo = "LOCALIDADE (" + dadosParametrosConsulta.getLocalidade().getId() + " - "
								+ dadosParametrosConsulta.getLocalidade().getDescricao() + ") POR SETOR COMERCIAL. ";
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL:
				titulo = "LOCALIDADE (" + dadosParametrosConsulta.getLocalidade().getId() + " - "
								+ dadosParametrosConsulta.getLocalidade().getDescricao() + ")" + //
								", SETOR COMERCIAL (" + dadosParametrosConsulta.getSetorComercial().getDescricaoComCodigo() + ")";
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL_QUADRA:
				titulo = "LOCALIDADE (" + dadosParametrosConsulta.getLocalidade().getId() + " - "
								+ dadosParametrosConsulta.getLocalidade().getDescricao() + ") , SETOR ("
								+ dadosParametrosConsulta.getSetorComercial().getId() + " - "
								+ dadosParametrosConsulta.getSetorComercial().getDescricao() + ") POR QUADRA. ";
				break;
			case ConstantesSistema.CODIGO_QUADRA:
				titulo = "LOCALIDADE (" + dadosParametrosConsulta.getLocalidade().getId() + " - "
								+ dadosParametrosConsulta.getLocalidade().getDescricao()
								+ //
								"), SETOR (" + dadosParametrosConsulta.getSetorComercial().getId() + " - "
								+ dadosParametrosConsulta.getSetorComercial().getDescricao() + //
								"), QUADRA (" + dadosParametrosConsulta.getQuadra().getNumeroQuadra() + ")";
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO:
				titulo = "UNIDADE NEGÓCIO (" + dadosParametrosConsulta.getUnidadeNegocio().getId() + " - "
								+ dadosParametrosConsulta.getUnidadeNegocio().getNome() + ")";
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO:
				titulo = "UNIDADE NEGÓCIO (" + dadosParametrosConsulta.getUnidadeNegocio().getId() + " - "
								+ dadosParametrosConsulta.getUnidadeNegocio().getNome() + ") POR ELO PÓLO. ";
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE:
				titulo = "UNIDADE NEGÓCIO (" + dadosParametrosConsulta.getUnidadeNegocio().getId() + " - "
								+ dadosParametrosConsulta.getUnidadeNegocio().getNome() + ") POR LOCALIDADE. ";
				break;

			default:
				titulo = "ERRO!!!";
				break;
		}

		parametros.put("tipoQuebra", titulo);
	}

	private void montarMapParametros(Map parametros, InformarDadosGeracaoRelatorioConsultaHelper dadosParametrosConsulta){

		// Perfil do Imóvel
		if(!Util.isVazioOrNulo(dadosParametrosConsulta.getColecaoImovelPerfil())){

			for(Object perfil : dadosParametrosConsulta.getColecaoImovelPerfil()){
				parametros.put("perfilImovel", ((ImovelPerfil) perfil).getDescricao());
			}
		}else{
			parametros.put("perfilImovel", STRING_TODOS);
		}

		// Ligação de Água
		if(!Util.isVazioOrNulo(dadosParametrosConsulta.getColecaoLigacaoAguaSituacao())){

			for(Object perfil : dadosParametrosConsulta.getColecaoLigacaoAguaSituacao()){
				parametros.put("sitLigAgua", ((LigacaoAguaSituacao) perfil).getDescricao());
			}
		}else{
			parametros.put("sitLigAgua", STRING_TODOS);
		}

		// Ligação de Esgoto
		if(!Util.isVazioOrNulo(dadosParametrosConsulta.getColecaoLigacaoEsgotoSituacao())){

			for(Object perfil : dadosParametrosConsulta.getColecaoLigacaoEsgotoSituacao()){
				parametros.put("sitLigEsgoto", ((LigacaoEsgotoSituacao) perfil).getDescricao());
			}
		}else{
			parametros.put("sitLigEsgoto", STRING_TODOS);
		}

		// Categoria
		if(!Util.isVazioOrNulo(dadosParametrosConsulta.getColecaoCategoria())){

			for(Object perfil : dadosParametrosConsulta.getColecaoCategoria()){
				parametros.put("categoria", ((Categoria) perfil).getDescricao());

			}
		}else{
			parametros.put("categoria", STRING_TODOS);
		}

		// Esfera de Poder
		if(!Util.isVazioOrNulo(dadosParametrosConsulta.getColecaoEsferaPoder())){

			for(Object perfil : dadosParametrosConsulta.getColecaoEsferaPoder()){
				parametros.put("esferaPoder", ((EsferaPoder) perfil).getDescricao());
			}
		}else{
			parametros.put("esferaPoder", STRING_TODOS);
		}
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		// TODO Auto-generated method stub
		return 0;
	}

	public void agendarTarefaBatch(){

	}

}