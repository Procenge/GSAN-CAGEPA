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
 * GSANPCG
 * Eduardo Henrique
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

package gcom.relatorio.arrecadacao.pagamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.parametrizacao.arrecadacao.ParametroArrecadacao;

import java.math.BigDecimal;
import java.util.*;

import br.com.procenge.comum.exception.NegocioException;

/**
 * [UC0379] Emitir Guia de Pagamento
 * 
 * @author Vivianne Sousa
 * @date 22/09/2006
 * @author eduardo henrique
 * @date 20/08/2008
 *       Altera��o para Impress�o de Guia de Pagamento por Presta��o
 */
public class RelatorioEmitirGuiaPagamento
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioEmitirGuiaPagamento(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_GUIA_PAGAMENTO_EMITIR);
	}

	@Deprecated
	public RelatorioEmitirGuiaPagamento() {

		super(null, "");
	}

	private Collection<RelatorioEmitirGuiaPagamentoBean> inicializarBeanRelatorio(Collection<GuiaPagamentoRelatorioHelper> dadosRelatorio,
					Short exibirNuContratoParcOrgaoPublico){

		Collection<RelatorioEmitirGuiaPagamentoDetailBean> colecaoDetail = null;
		Collection<RelatorioEmitirGuiaPagamentoBean> retorno = new ArrayList();

		GuiaPagamentoRelatorioHelper guiaPagamentoRelatorioHelper = null;
		Map<String, BigDecimal> mapValorPorTipoDebito = null;
		String indicadorExibirAcrescimos = null;
		String prestacaoFormatada = null;
		String descricaoServicosTarifas = null;
		BigDecimal valorTipoDebito = null;
		String valorFormatado = null;
		RelatorioEmitirGuiaPagamentoDetailBean relatorioEmitirGuiaPagamentoDetailBean = null;

		Iterator iterator = dadosRelatorio.iterator();

		while(iterator.hasNext()){
			guiaPagamentoRelatorioHelper = (GuiaPagamentoRelatorioHelper) iterator.next();
			mapValorPorTipoDebito = guiaPagamentoRelatorioHelper.getMapValorPorTipoDebito();
			indicadorExibirAcrescimos = guiaPagamentoRelatorioHelper.getIndicadorExibirAcrescimos();

			colecaoDetail = new ArrayList<RelatorioEmitirGuiaPagamentoDetailBean>();

			if(mapValorPorTipoDebito != null && !mapValorPorTipoDebito.isEmpty()){
				prestacaoFormatada = guiaPagamentoRelatorioHelper.getPrestacaoFormatada();

				for(String descricaoTipoDebito : mapValorPorTipoDebito.keySet()){
					descricaoServicosTarifas = descricaoTipoDebito + "   " + prestacaoFormatada;

					valorTipoDebito = mapValorPorTipoDebito.get(descricaoTipoDebito);
					valorFormatado = Util.formatarMoedaReal(valorTipoDebito);

					relatorioEmitirGuiaPagamentoDetailBean = new RelatorioEmitirGuiaPagamentoDetailBean(descricaoServicosTarifas,
									valorFormatado);

					colecaoDetail.add(relatorioEmitirGuiaPagamentoDetailBean);
				}
			}

			if(!Util.isVazioOrNulo(colecaoDetail)){
				Collections.sort((List) colecaoDetail, new Comparator() {

					public int compare(Object a, Object b){

						String descricao1 = ((RelatorioEmitirGuiaPagamentoDetailBean) a).getDescricaoServicosTarifas();
						String descricao2 = ((RelatorioEmitirGuiaPagamentoDetailBean) b).getDescricaoServicosTarifas();

						return descricao1.compareTo(descricao2);

					}
				});
			}

			String nomeCliente = "";
			if(guiaPagamentoRelatorioHelper.getNomeCliente() != null){
				nomeCliente = guiaPagamentoRelatorioHelper.getNomeCliente();
			}
			String dataVencimento = Util.formatarData(guiaPagamentoRelatorioHelper.getDataVencimento());
			String valor = Util.formatarMoedaReal(guiaPagamentoRelatorioHelper.getValorDebito());
			String parcelaFormatada = guiaPagamentoRelatorioHelper.getIdGuiaPagamento() + "/"
							+ guiaPagamentoRelatorioHelper.getNumeroPrestacaoDebito();

			String indicadorEmissaoObservacaoRA = "";
			if(guiaPagamentoRelatorioHelper.getIndicadorEmissaoObservacaoRA() != null
							&& !guiaPagamentoRelatorioHelper.getIndicadorEmissaoObservacaoRA().equals("")){
				indicadorEmissaoObservacaoRA = guiaPagamentoRelatorioHelper.getIndicadorEmissaoObservacaoRA().toString();
			}
			
			String numeroContratoParcelOrgaoPublico = null;
			if(exibirNuContratoParcOrgaoPublico != null && exibirNuContratoParcOrgaoPublico.equals(ConstantesSistema.SIM)){
				if(guiaPagamentoRelatorioHelper.getNumeroContratoParcelOrgaoPublico() != null){
					numeroContratoParcelOrgaoPublico = guiaPagamentoRelatorioHelper.getNumeroContratoParcelOrgaoPublico().toString();
				}
			}

			RelatorioEmitirGuiaPagamentoBean bean = new RelatorioEmitirGuiaPagamentoBean(colecaoDetail,
							guiaPagamentoRelatorioHelper.getMatriculaFormatada(), nomeCliente, dataVencimento,
							guiaPagamentoRelatorioHelper.getInscricao(), guiaPagamentoRelatorioHelper.getEnderecoImovel(),
							guiaPagamentoRelatorioHelper.getEnderecoClienteResponsavel(),
							guiaPagamentoRelatorioHelper.getRepresentacaoNumericaCodBarraFormatada(),
							guiaPagamentoRelatorioHelper.getRepresentacaoNumericaCodBarraSemDigito(),
							guiaPagamentoRelatorioHelper.getDataValidade(), valor, guiaPagamentoRelatorioHelper.getIdGuiaPagamento(),
							guiaPagamentoRelatorioHelper.getDescricaoTipoDocumento(), parcelaFormatada, indicadorEmissaoObservacaoRA,
							guiaPagamentoRelatorioHelper.getDescricaoObservacao(), numeroContratoParcelOrgaoPublico);

			bean.setIndicadorPrestacaoDebitoAutomatico(guiaPagamentoRelatorioHelper.getIndicadorPrestacaoDebitoAutomatico());
			bean.setIndicadorPrestacaoNoHistorico(guiaPagamentoRelatorioHelper.getIndicadorPrestacaoNoHistorico());
			bean.setIndicadorExibirAcrescimos(indicadorExibirAcrescimos);

			if(indicadorExibirAcrescimos != null && indicadorExibirAcrescimos.toString().equals(ConstantesSistema.SIM.toString())){
				String dataEmissaoGuia = Util.formatarData(new Date());
				bean.setDataEmissaoGuia(dataEmissaoGuia);
			}

			retorno.add(bean);

		}

		return retorno;
	}

	/**
	 * M�todo que executa a tarefa
	 * 
	 * @return Object
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		String[] ids = (String[]) getParametro("ids");
		String indicadorExibirMensagemGuiaPagamento = null;
		Collection<GuiaPagamentoPrestacaoHelper> colecaoPrestacoesImpressao = (Collection<GuiaPagamentoPrestacaoHelper>) getParametro("colecaoPrestacoesSelecionadas");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Short exibirNuContratoParcOrgaoPublico = (Short) getParametro("exibirNuContratoParcOrgaoPublico");

		// valor de retorno
		byte[] retorno = null;

		Fachada fachada = Fachada.getInstancia();

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		Collection<GuiaPagamentoRelatorioHelper> dadosRelatorio = fachada.pesquisarGuiaPagamentoRelatorio(colecaoPrestacoesImpressao, ids);

		Collection<RelatorioEmitirGuiaPagamentoBean> colecaoBean = this.inicializarBeanRelatorio(dadosRelatorio,
						exibirNuContratoParcOrgaoPublico);

		if(colecaoBean == null || colecaoBean.isEmpty()){
			// N�o existem dados para a exibi��o do relat�rio.
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		try{
			indicadorExibirMensagemGuiaPagamento = ParametroArrecadacao.P_MENSAGEM_GUIA_PAGAMENTO.executar().toString();
		}catch(ControladorException e1){
			e1.printStackTrace();
		}

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagemConta", sistemaParametro.getImagemConta());

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("nomeEmpresa", sistemaParametro.getNomeAbreviadoEmpresa());

		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
		try{
			parametros.put("P_ENDERECO", fachada.pesquisarEnderecoFormatadoEmpresa());
		}catch(ControladorException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}
		parametros.put("P_CNPJ", gcom.util.Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));
		parametros.put("P_FONE", sistemaParametro.getNumeroTelefone());
		try{
			parametros.put("P_INSC_EST",
							(String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.getCodigo()));
			parametros.put("P_JUCOR", (String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_JUNTA_COMERCIAL_EMPRESA.getCodigo()));
		}catch(NegocioException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}
		RelatorioDataSource ds = new RelatorioDataSource((List) colecaoBean);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_GUIA_PAGAMENTO_EMITIR, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.EMITIR_GUIA_PAGAMENTO, idFuncionalidadeIniciada, null);
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

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioEmitirGuiaPagamento", this);
	}
}
