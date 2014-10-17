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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.bean.OSFiltroHelper;
import gcom.atendimentopublico.ordemservico.bean.ObterDescricaoSituacaoOSHelper;
import gcom.atendimentopublico.ordemservico.bean.PesquisarOrdemServicoHelper;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarOrdemServicoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("resultadoPesquisa");

		// Sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Form
		FiltrarOrdemServicoActionForm filtrarOrdemServicoActionForm = (FiltrarOrdemServicoActionForm) actionForm;

		boolean parametroInformado = false;

		// Numero OS
		Integer numeroOS = null;
		if(filtrarOrdemServicoActionForm.getNumeroOS() != null && !filtrarOrdemServicoActionForm.getNumeroOS().equals("")){

			numeroOS = new Integer(filtrarOrdemServicoActionForm.getNumeroOS());
			parametroInformado = true;
		}

		// Numero RA
		Integer numeroRA = null;
		if(filtrarOrdemServicoActionForm.getNumeroRA() != null && !filtrarOrdemServicoActionForm.getNumeroRA().equals("")){

			numeroRA = new Integer(filtrarOrdemServicoActionForm.getNumeroRA());
			parametroInformado = true;
		}

		// Documento Cobran�a
		Integer idDocumentoCobranca = null;
		if(filtrarOrdemServicoActionForm.getDocumentoCobranca() != null && !filtrarOrdemServicoActionForm.getDocumentoCobranca().equals("")){

			idDocumentoCobranca = new Integer(filtrarOrdemServicoActionForm.getDocumentoCobranca());
			parametroInformado = true;
		}

		// vari�veis utilizadas no filtro
		short situacaoOrdemServico = ConstantesSistema.NUMERO_NAO_INFORMADO;
		short situacaoProgramacao = ConstantesSistema.NUMERO_NAO_INFORMADO;
		Integer[] idsTipoServicoSelecionado = null;
		Integer matriculaImovel = null;
		Integer codigoCliente = null;
		Integer unidadeGeracao = null;
		Integer unidadeAtual = null;
		Integer unidadeSuperior = null;
		Integer equipe = null;
		Integer diasAtraso = null;
		Short programado = null;
		Date dataAtendimentoInicial = null;
		Date dataAtendimentoFinal = null;
		Date dataGeracaoInicial = null;
		Date dataGeracaoFinal = null;
		Date dataProgramacaoInicial = null;
		Date dataProgramacaoFinal = null;
		Date dataEncerramentoInicial = null;
		Date dataEncerramentoFinal = null;
		Date dataExecucaoInicial = null;
		Date dataExecucaoFinal = null;
		Integer idMunicipio = null;
		Integer idBairro = null;
		Integer idAreaBairro = null;
		Integer idLogradouro = null;

		if(Util.isVazioOuBranco(filtrarOrdemServicoActionForm.getDesabilitaCampos())
						|| filtrarOrdemServicoActionForm.getDesabilitaCampos().equals("false")){

			// Situacao da Ordem de Servico
			if(filtrarOrdemServicoActionForm.getSituacaoOrdemServico() != null
							&& !filtrarOrdemServicoActionForm.getSituacaoOrdemServico().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				situacaoOrdemServico = new Short(filtrarOrdemServicoActionForm.getSituacaoOrdemServico()).shortValue();

				parametroInformado = true;
			}

			// Situacao da Programa��o
			if(filtrarOrdemServicoActionForm.getSituacaoProgramacao() != null){

				// Informou todos
				if(!filtrarOrdemServicoActionForm.getSituacaoProgramacao().equals("0")){
					situacaoProgramacao = new Short(filtrarOrdemServicoActionForm.getSituacaoProgramacao()).shortValue();
				}

			}

			// Tipo Servico
			idsTipoServicoSelecionado = (Integer[]) filtrarOrdemServicoActionForm.getTipoServicoSelecionados();

			if(idsTipoServicoSelecionado != null && idsTipoServicoSelecionado.length > 0){
				if(idsTipoServicoSelecionado[0].intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO){
					idsTipoServicoSelecionado = null;
				}else{
					parametroInformado = true;
				}
			}

			// Imovel
			if(filtrarOrdemServicoActionForm.getMatriculaImovel() != null && !filtrarOrdemServicoActionForm.getMatriculaImovel().equals("")){

				matriculaImovel = new Integer(filtrarOrdemServicoActionForm.getMatriculaImovel());

				parametroInformado = true;
			}

			// Cliente
			if(filtrarOrdemServicoActionForm.getCodigoCliente() != null && !filtrarOrdemServicoActionForm.getCodigoCliente().equals("")){

				codigoCliente = new Integer(filtrarOrdemServicoActionForm.getCodigoCliente());

				parametroInformado = true;
			}

			// Unidade de Gera��o
			if(filtrarOrdemServicoActionForm.getUnidadeGeracao() != null && !filtrarOrdemServicoActionForm.getUnidadeGeracao().equals("")){

				unidadeGeracao = new Integer(filtrarOrdemServicoActionForm.getUnidadeGeracao());

				parametroInformado = true;
			}

			// Unidade Atual
			if(filtrarOrdemServicoActionForm.getUnidadeAtual() != null && !filtrarOrdemServicoActionForm.getUnidadeAtual().equals("")){

				unidadeAtual = new Integer(filtrarOrdemServicoActionForm.getUnidadeAtual());

				parametroInformado = true;
			}

			// Unidade Superior
			if(filtrarOrdemServicoActionForm.getUnidadeSuperior() != null && !filtrarOrdemServicoActionForm.getUnidadeSuperior().equals("")){

				unidadeSuperior = new Integer(filtrarOrdemServicoActionForm.getUnidadeSuperior());

				parametroInformado = true;
			}

			// Equipe
			if(filtrarOrdemServicoActionForm.getEquipe() != null && !filtrarOrdemServicoActionForm.getEquipe().equals("")){

				equipe = new Integer(filtrarOrdemServicoActionForm.getEquipe());

				parametroInformado = true;
			}

			// Dias de Atraso
			if(filtrarOrdemServicoActionForm.getDiasAtraso() != null && !filtrarOrdemServicoActionForm.getDiasAtraso().equals("")){

				diasAtraso = new Integer(filtrarOrdemServicoActionForm.getDiasAtraso());

				parametroInformado = true;
			}

			// Programado
			if(filtrarOrdemServicoActionForm.getProgramada() != null && !filtrarOrdemServicoActionForm.getProgramada().equals("")){

				programado = new Short(filtrarOrdemServicoActionForm.getProgramada());

				parametroInformado = true;
			}

			// Data de Atendimento
			if(filtrarOrdemServicoActionForm.getPeriodoAtendimentoInicial() != null
							&& !filtrarOrdemServicoActionForm.getPeriodoAtendimentoInicial().equals("")){

				dataAtendimentoInicial = Util.converteStringParaDate(filtrarOrdemServicoActionForm.getPeriodoAtendimentoInicial());

				dataAtendimentoFinal = null;
				if(filtrarOrdemServicoActionForm.getPeriodoAtendimentoFinal() != null
								&& !filtrarOrdemServicoActionForm.getPeriodoAtendimentoFinal().equals("")){

					dataAtendimentoFinal = Util.converteStringParaDate(filtrarOrdemServicoActionForm.getPeriodoAtendimentoFinal());
				}else{
					dataAtendimentoFinal = new Date();
				}

				parametroInformado = true;
			}

			// Data de Gera��o
			if(filtrarOrdemServicoActionForm.getPeriodoGeracaoInicial() != null
							&& !filtrarOrdemServicoActionForm.getPeriodoGeracaoInicial().equals("")){

				dataGeracaoInicial = Util.converteStringParaDate(filtrarOrdemServicoActionForm.getPeriodoGeracaoInicial());

				dataGeracaoFinal = null;

				if(filtrarOrdemServicoActionForm.getPeriodoGeracaoFinal() != null
								&& !filtrarOrdemServicoActionForm.getPeriodoGeracaoFinal().equals("")){

					dataGeracaoFinal = Util.converteStringParaDate(filtrarOrdemServicoActionForm.getPeriodoGeracaoFinal());

				}else{
					dataGeracaoFinal = new Date();
				}

				parametroInformado = true;
			}

			// Data de Programa��o
			if(filtrarOrdemServicoActionForm.getPeriodoProgramacaoInicial() != null
							&& !filtrarOrdemServicoActionForm.getPeriodoProgramacaoInicial().equals("")){

				dataProgramacaoInicial = Util.converteStringParaDate(filtrarOrdemServicoActionForm.getPeriodoProgramacaoInicial());

				dataProgramacaoFinal = null;

				if(filtrarOrdemServicoActionForm.getPeriodoProgramacaoFinal() != null
								&& !filtrarOrdemServicoActionForm.getPeriodoProgramacaoFinal().equals("")){

					dataProgramacaoFinal = Util.converteStringParaDate(filtrarOrdemServicoActionForm.getPeriodoProgramacaoFinal());

				}else{
					dataProgramacaoFinal = new Date();
				}

				parametroInformado = true;
			}

			// Data de Encerramento
			if(filtrarOrdemServicoActionForm.getPeriodoEncerramentoInicial() != null
							&& !filtrarOrdemServicoActionForm.getPeriodoEncerramentoInicial().equals("")){

				dataEncerramentoInicial = Util.converteStringParaDate(filtrarOrdemServicoActionForm.getPeriodoEncerramentoInicial());

				dataEncerramentoFinal = null;

				if(filtrarOrdemServicoActionForm.getPeriodoEncerramentoFinal() != null
								&& !filtrarOrdemServicoActionForm.getPeriodoEncerramentoFinal().equals("")){

					dataEncerramentoFinal = Util.converteStringParaDate(filtrarOrdemServicoActionForm.getPeriodoEncerramentoFinal());

				}else{
					dataEncerramentoFinal = new Date();
				}

				parametroInformado = true;
			}

			// Data de Execu��o
			if(filtrarOrdemServicoActionForm.getPeriodoExecucaoInicial() != null
							&& !filtrarOrdemServicoActionForm.getPeriodoExecucaoInicial().equals("")){

				dataExecucaoInicial = Util.converteStringParaDate(filtrarOrdemServicoActionForm.getPeriodoExecucaoInicial(), true);

				dataExecucaoFinal = null;

				if(filtrarOrdemServicoActionForm.getPeriodoExecucaoFinal() != null
								&& !filtrarOrdemServicoActionForm.getPeriodoExecucaoFinal().equals("")){

					dataExecucaoFinal = Util.converteStringParaDate(filtrarOrdemServicoActionForm.getPeriodoExecucaoFinal(), true);

				}else{
					dataExecucaoFinal = new Date();
				}

				parametroInformado = true;
			}

			// Munic�pio
			if(filtrarOrdemServicoActionForm.getMunicipio() != null && !filtrarOrdemServicoActionForm.getMunicipio().equals("")){

				idMunicipio = new Integer(filtrarOrdemServicoActionForm.getMunicipio());

				parametroInformado = true;
			}

			// Bairro
			if(filtrarOrdemServicoActionForm.getCodigoBairro() != null && !filtrarOrdemServicoActionForm.getCodigoBairro().equals("")){

				idBairro = this.pesquisarBairro(filtrarOrdemServicoActionForm);

				parametroInformado = true;
			}

			// Bairro �rea
			if(new Integer(filtrarOrdemServicoActionForm.getAreaBairro()).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){

				idAreaBairro = new Integer(filtrarOrdemServicoActionForm.getAreaBairro());

				parametroInformado = true;
			}

			// Logradouro
			if(filtrarOrdemServicoActionForm.getLogradouro() != null && !filtrarOrdemServicoActionForm.getLogradouro().equals("")){

				idLogradouro = new Integer(filtrarOrdemServicoActionForm.getLogradouro());

				parametroInformado = true;
			}

			if(programado != null && programado.intValue() == ConstantesSistema.TODOS.intValue()){

				/**
				 * Os per�odo (Atendimento, Gera��o, Programa��o ou Encerramento) deve ser limitado
				 * a 1
				 * m�s
				 */
				if(dataAtendimentoInicial != null && dataAtendimentoFinal != null){
					long qtdDias = Util.diferencaDias(dataAtendimentoInicial, dataAtendimentoFinal);

					if(qtdDias > 30){
						throw new ActionServletException("atencao.periodo.data.deve.ser.limitado.um.mes", null, "Per�odo de Atendimento");
					}
				}

				if(dataGeracaoInicial != null && dataGeracaoFinal != null){
					long qtdDias = Util.diferencaDias(dataGeracaoInicial, dataGeracaoFinal);

					if(qtdDias > 30){
						throw new ActionServletException("atencao.periodo.data.deve.ser.limitado.um.mes", null, "Per�odo de Gera��o");
					}
				}

				if(dataProgramacaoInicial != null && dataProgramacaoFinal != null){
					long qtdDias = Util.diferencaDias(dataProgramacaoInicial, dataProgramacaoFinal);

					if(qtdDias > 30){
						throw new ActionServletException("atencao.periodo.data.deve.ser.limitado.um.mes", null, "Per�odo de Programa��o");
					}
				}

				if(dataEncerramentoInicial != null && dataEncerramentoFinal != null){
					long qtdDias = Util.diferencaDias(dataEncerramentoInicial, dataEncerramentoFinal);

					if(qtdDias > 30){
						throw new ActionServletException("atencao.periodo.data.deve.ser.limitado.um.mes", null, "Per�odo de Encerramento");
					}
				}

				if(dataExecucaoInicial != null && dataExecucaoFinal != null){
					long qtdDias = Util.diferencaDias(dataExecucaoInicial, dataExecucaoFinal);

					if(qtdDias > 30){
						throw new ActionServletException("atencao.periodo.data.deve.ser.limitado.um.mes", null, "Per�odo de Execu��o");
					}
				}

			}
		}

		PesquisarOrdemServicoHelper pesquisarOrdemServicoHelper = new PesquisarOrdemServicoHelper();

		pesquisarOrdemServicoHelper.setNumeroOS(numeroOS);
		pesquisarOrdemServicoHelper.setNumeroRA(numeroRA);
		pesquisarOrdemServicoHelper.setDocumentoCobranca(idDocumentoCobranca);
		pesquisarOrdemServicoHelper.setSituacaoOrdemServico(situacaoOrdemServico);
		pesquisarOrdemServicoHelper.setSituacaoProgramacao(situacaoProgramacao);
		pesquisarOrdemServicoHelper.setTipoServicos(idsTipoServicoSelecionado);

		pesquisarOrdemServicoHelper.setMatriculaImovel(matriculaImovel);
		pesquisarOrdemServicoHelper.setCodigoCliente(codigoCliente);

		pesquisarOrdemServicoHelper.setUnidadeGeracao(unidadeGeracao);
		pesquisarOrdemServicoHelper.setUnidadeAtual(unidadeAtual);
		pesquisarOrdemServicoHelper.setUnidadeSuperior(unidadeSuperior);

		pesquisarOrdemServicoHelper.setDataAtendimentoInicial(dataAtendimentoInicial);
		pesquisarOrdemServicoHelper.setDataAtendimentoFinal(dataAtendimentoFinal);
		pesquisarOrdemServicoHelper.setDataGeracaoInicial(dataGeracaoInicial);
		pesquisarOrdemServicoHelper.setDataGeracaoFinal(dataGeracaoFinal);
		pesquisarOrdemServicoHelper.setDataProgramacaoInicial(dataProgramacaoInicial);
		pesquisarOrdemServicoHelper.setDataProgramacaoFinal(dataProgramacaoFinal);
		pesquisarOrdemServicoHelper.setDataEncerramentoInicial(dataEncerramentoInicial);
		pesquisarOrdemServicoHelper.setDataEncerramentoFinal(dataEncerramentoFinal);
		pesquisarOrdemServicoHelper.setDataExecucaoInicial(dataExecucaoInicial);
		pesquisarOrdemServicoHelper.setDataExecucaoFinal(dataExecucaoFinal);

		pesquisarOrdemServicoHelper.setMunicipio(idMunicipio);
		pesquisarOrdemServicoHelper.setBairro(idBairro);
		pesquisarOrdemServicoHelper.setAreaBairro(idAreaBairro);
		pesquisarOrdemServicoHelper.setLogradouro(idLogradouro);

		pesquisarOrdemServicoHelper.setProgramado(programado);
		pesquisarOrdemServicoHelper.setEquipe(equipe);
		pesquisarOrdemServicoHelper.setDiasAtraso(diasAtraso);

		// Pesquisar Ordem Servico
		if(sessao.getAttribute("parametroInformado") != null){
			parametroInformado = ((Boolean) sessao.getAttribute("parametroInformado")).booleanValue();
		}

		if(httpServletRequest.getParameter("voltar") != null){
			pesquisarOrdemServicoHelper.setNumeroOS(null);
			pesquisarOrdemServicoHelper.setNumeroRA(null);
		}

		sessao.setAttribute("pesquisarOrdemServicoHelper", pesquisarOrdemServicoHelper);

		if(parametroInformado){

			int totalRegistros = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Integer tamanho = null;

			if(httpServletRequest.getParameter("page.offset") != null){
				tamanho = (Integer) sessao.getAttribute("totalRegistros");
			}else{
				tamanho = Fachada.getInstancia().pesquisarOrdemServicoTamanho(pesquisarOrdemServicoHelper);
			}

			if(tamanho == null || tamanho == 0){
				throw new ActionServletException("atencao.pesquisa.nenhumresultado");

			}else if(tamanho.intValue() == 1){

				Collection<OrdemServico> colecaoOrdemServico = Fachada.getInstancia().pesquisarOrdemServico(pesquisarOrdemServicoHelper);

				OrdemServico os = (OrdemServico) Util.retonarObjetoDeColecao(colecaoOrdemServico);

				httpServletRequest.setAttribute("numeroOS", os.getId());
				retorno = actionMapping.findForward("manterOrdemServico");

				sessao.removeAttribute("manterOs");
			}else{
				sessao.setAttribute("manterOs", "N�o");

				totalRegistros = tamanho.intValue();

				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				int numeroPaginasPesquisa = ((Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa")).intValue();

				if(sessao.getAttribute("pesquisarOrdemServicoHelper") != null){
					pesquisarOrdemServicoHelper = (PesquisarOrdemServicoHelper) sessao.getAttribute("pesquisarOrdemServicoHelper");
				}

				pesquisarOrdemServicoHelper.setNumeroPaginasPesquisa(numeroPaginasPesquisa);

				Collection<OrdemServico> colecaoOrdemServico = Fachada.getInstancia().pesquisarOrdemServico(pesquisarOrdemServicoHelper);

				Collection colecaoOSHelper = loadColecaoOSHelper(colecaoOrdemServico);
				sessao.setAttribute("pesquisarOrdemServicoHelper", pesquisarOrdemServicoHelper);
				sessao.setAttribute("colecaoOSHelper", colecaoOSHelper);
				sessao.setAttribute("parametroInformado", new Boolean(parametroInformado));
			}
		}else{
			throw new ActionServletException("atencao.filtrar_informar_um_filtro");
		}

		return retorno;
	}

	/**
	 * Carrega cole��o de ordem de servico, situa��o da unidade atual no
	 * objeto facilitador
	 * 
	 * @author Rafael Pinto
	 * @date 18/08/2006
	 * @param colecaoRegistroAtendimento
	 * @return
	 */
	private Collection loadColecaoOSHelper(Collection<OrdemServico> colecaoOrdemServico){

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoOSHelper = new ArrayList();
		UnidadeOrganizacional unidadeAtual = null;
		ObterDescricaoSituacaoOSHelper situacao = null;
		Imovel imovel = null;
		OSFiltroHelper helper = null;

		if(Util.isVazioOrNulo(colecaoOrdemServico)){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}else{
			for(Iterator iter = colecaoOrdemServico.iterator(); iter.hasNext();){

				OrdemServico ordemServico = (OrdemServico) iter.next();

				situacao = fachada.obterDescricaoSituacaoOS(ordemServico.getId());

				// [UC3040] Obter Unidade Atual da OS
				unidadeAtual = fachada.obterUnidadeAtualOrdemServico(ordemServico.getId());

				if(ordemServico.getRegistroAtendimento() != null){
					imovel = ordemServico.getRegistroAtendimento().getImovel();
				}else if(ordemServico.getCobrancaDocumento() != null){
					imovel = ordemServico.getCobrancaDocumento().getImovel();
				}else{
					imovel = ordemServico.getImovel();
				}

				helper = new OSFiltroHelper();

				helper.setOrdemServico(ordemServico);
				helper.setImovel(imovel);
				helper.setUnidadeAtual(unidadeAtual);
				helper.setSituacao(situacao.getDescricaoAbreviadaSituacao());

				colecaoOSHelper.add(helper);
			}
		}

		return colecaoOSHelper;
	}

	/**
	 * Pesquisa Bairro
	 * 
	 * @author Rafael Pinto
	 * @date 16/08/2006
	 */
	private Integer pesquisarBairro(FiltrarOrdemServicoActionForm form){

		// [FS0013] - Verificar informa��o do mun�cipio
		String codigoMunicipio = form.getMunicipio();
		if(codigoMunicipio == null || codigoMunicipio.equals("")){
			throw new ActionServletException("atencao.filtrar_informar_municipio");
		}

		Integer retorno = null;

		FiltroBairro filtroBairro = new FiltroBairro();

		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, new Integer(form.getCodigoBairro())));

		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, new Integer(codigoMunicipio)));

		// Pesquisa de acordo com os par�metros informados no filtro
		Collection colecaoBairro = Fachada.getInstancia().pesquisar(filtroBairro, Bairro.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a cole��o
		if(colecaoBairro != null && !colecaoBairro.isEmpty()){

			// Obt�m o objeto da cole��o pesquisada
			Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);

			retorno = bairro.getId();
		}else{
			throw new ActionServletException("atencao.bairro.inexistente");
		}

		return retorno;
	}
}