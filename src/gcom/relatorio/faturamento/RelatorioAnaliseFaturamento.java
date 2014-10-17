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
 * classe respons�vel por criar o relat�rio de bairro manter de �gua
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
	 * M�todo que executa para a gera��o do relat�rio.
	 * 
	 * @param localidades
	 *            Description of the Parameter
	 * @param localidadeParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
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

		// cole��o de beans do relat�rio
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

			// adiciona o bean a cole��o
			relatorioBeans.add(relatorioBean);
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

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
		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ANALISE_FATURAMENTO, parametros, ds, tipoFormatoRelatorio);

		// retorna o relat�rio gerado
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
				titulo = "ESTADO (" + sistemaParametro.getNomeEstado() + ") POR ELO P�LO. ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL:
				titulo = "ESTADO (" + sistemaParametro.getNomeEstado() + ") POR GER�NCIA REGIONAL. ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_COBRANCA:
				titulo = "ESTADO (" + sistemaParametro.getNomeEstado() + ") POR GRUPO DE COBRAN�A. ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO:
				titulo = "ESTADO (" + sistemaParametro.getNomeEstado() + ") POR GRUPO DE FATURAMENTO. ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_LOCALIDADE:
				titulo = "ESTADO (" + sistemaParametro.getNomeEstado() + ") POR LOCALIDADE. ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO:
				titulo = "ESTADO (" + sistemaParametro.getNomeEstado() + ") POR UNIDADE DE NEG�CIO. ";
				break;
			case ConstantesSistema.CODIGO_GRUPO_FATURAMENTO:
				titulo = "GRUPO DE FATURAMENTO (" + dadosParametrosConsulta.getFaturamentoGrupo().getDescricao() + ")";
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL:
				titulo = "GER�NCIA REGIONAL (" + dadosParametrosConsulta.getGerenciaRegional().getNomeComId() + ")";
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO:
				titulo = "GER�NCIA REGIONAL (" + dadosParametrosConsulta.getGerenciaRegional().getNomeComId() + ") POR ELO P�LO. ";
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE:
				titulo = "GER�NCIA REGIONAL (" + dadosParametrosConsulta.getGerenciaRegional().getNomeComId() + ") POR LOCALIDADE. ";
				break;
			case ConstantesSistema.CODIGO_ELO_POLO:
				titulo = "ELO P�LO (" + dadosParametrosConsulta.getEloPolo().getDescricaoComId() + ")";
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE:
				titulo = "ELO P�LO (" + dadosParametrosConsulta.getEloPolo().getId() + " - "
								+ dadosParametrosConsulta.getEloPolo().getDescricao() + ") POR LOCALIDADE. ";
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL:
				titulo = "ELO P�LO (" + dadosParametrosConsulta.getEloPolo().getId() + " - "
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
				titulo = "UNIDADE NEG�CIO (" + dadosParametrosConsulta.getUnidadeNegocio().getId() + " - "
								+ dadosParametrosConsulta.getUnidadeNegocio().getNome() + ")";
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO:
				titulo = "UNIDADE NEG�CIO (" + dadosParametrosConsulta.getUnidadeNegocio().getId() + " - "
								+ dadosParametrosConsulta.getUnidadeNegocio().getNome() + ") POR ELO P�LO. ";
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE:
				titulo = "UNIDADE NEG�CIO (" + dadosParametrosConsulta.getUnidadeNegocio().getId() + " - "
								+ dadosParametrosConsulta.getUnidadeNegocio().getNome() + ") POR LOCALIDADE. ";
				break;

			default:
				titulo = "ERRO!!!";
				break;
		}

		parametros.put("tipoQuebra", titulo);
	}

	private void montarMapParametros(Map parametros, InformarDadosGeracaoRelatorioConsultaHelper dadosParametrosConsulta){

		// Perfil do Im�vel
		if(!Util.isVazioOrNulo(dadosParametrosConsulta.getColecaoImovelPerfil())){

			for(Object perfil : dadosParametrosConsulta.getColecaoImovelPerfil()){
				parametros.put("perfilImovel", ((ImovelPerfil) perfil).getDescricao());
			}
		}else{
			parametros.put("perfilImovel", STRING_TODOS);
		}

		// Liga��o de �gua
		if(!Util.isVazioOrNulo(dadosParametrosConsulta.getColecaoLigacaoAguaSituacao())){

			for(Object perfil : dadosParametrosConsulta.getColecaoLigacaoAguaSituacao()){
				parametros.put("sitLigAgua", ((LigacaoAguaSituacao) perfil).getDescricao());
			}
		}else{
			parametros.put("sitLigAgua", STRING_TODOS);
		}

		// Liga��o de Esgoto
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