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

package gcom.gui.atendimentopublico;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.RateioTipo;
import gcom.micromedicao.hidrometro.*;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author isilva
 * @created 22 de Dezembro de 2010
 */
public class EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);

		EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm = (EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_RESTABELECIMENTO_LIGACAO_AGUA_COM_SUBSTITUICAO_DE_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		String ordemServicoId = efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getIdOrdemServico();

		Imovel imovel = null;

		if(ordemServicoId != null && !ordemServicoId.equals("")){

			OrdemServico ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");
			String matriculaImovel = efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getMatriculaImovel();
			String localArmazenagemHidrometro = efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getLocalArmazenagem();
			String numeroLeituraRetiradaHidrometro = efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
							.getLeituraRetirada();

			if(ordemServico == null){
				throw new ActionServletException("atencao.ordem_servico_inexistente", null, "ORDEM DE SERVI�O INEXISTENTE");
			}

			// Guarda o usu�rio logado, que realizou a opera��o
			ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			if(sessao.getAttribute("imovel") != null){
				imovel = (Imovel) sessao.getAttribute("imovel");
				imovel.setUltimaAlteracao(new Date());
			}

			FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
			filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, imovel.getId()));

			Collection colecaoLigacoesAgua = fachada.pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName());

			LigacaoAgua ligacaoAgua = (LigacaoAgua) Util.retonarObjetoDeColecao(colecaoLigacoesAgua);
			if(ligacaoAgua != null){
				ligacaoAgua.setDataRestabelecimentoAgua(Util
								.converteStringParaDate(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
												.getDataRestabelecimento()));
				
				String idFuncionario = efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getIdFuncionario();

				if(idFuncionario != null && !idFuncionario.equals("")){
					Funcionario func = new Funcionario();
					func.setId(new Integer(idFuncionario));
					ligacaoAgua.setFuncionarioRestabelecimento(func);
				}

				if(ligacaoAgua.getNumeroRestabelecimento() != null){
					ligacaoAgua.setNumeroRestabelecimento(ligacaoAgua.getNumeroRestabelecimento() + 1);
				}else{
					ligacaoAgua.setNumeroRestabelecimento(1);
				}

				if(imovel.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.SUPR_PARC)){
					if(ligacaoAgua.getNumeroRestabelecimentoParcial() != null){
						ligacaoAgua.setNumeroRestabelecimentoParcial(ligacaoAgua.getNumeroRestabelecimentoParcial() + 1);
					}else{
						ligacaoAgua.setNumeroRestabelecimentoParcial(1);
					}
				}else if(imovel.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.SUPRIMIDO_DEFINITIVO)){
					if(ligacaoAgua.getNumeroRestabelecimentoTotal() != null){
						ligacaoAgua.setNumeroRestabelecimentoTotal(ligacaoAgua.getNumeroRestabelecimentoTotal() + 1);
					}else{
						ligacaoAgua.setNumeroRestabelecimentoTotal(1);
					}
				}
				
				HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = new HidrometroInstalacaoHistorico();
				hidrometroInstalacaoHistorico.setLigacaoAgua(ligacaoAgua);

				String numeroHidrometroNovo = efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
								.getNumeroHidrometroNovo();

				// Pesquisa o Novo Hidr�metro
				Hidrometro hidrometroNovo = fachada.pesquisarHidrometroPeloNumero(numeroHidrometroNovo);

				if(hidrometroNovo == null){
					throw new ActionServletException("atencao.hidrometro_inexistente");
				}

				if(hidrometroNovo.getHidrometroSituacao().getId().intValue() != HidrometroSituacao.DISPONIVEL){
					throw new ActionServletException("atencao.situacao_hidrometro_nao_permite_religacao_com_instalao_hidrometro", null,
									hidrometroNovo.getHidrometroSituacao().getDescricao());
				}

				hidrometroInstalacaoHistorico.setHidrometro(hidrometroNovo);

				// hidrometroInstalacaoHistorico =
				// this.setDadosHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico,efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm);
				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setFormValues(hidrometroInstalacaoHistorico);

				// *************** Hidrometro para ser Substituido (Antigo) ****************
				HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico = (HidrometroInstalacaoHistorico) sessao
								.getAttribute("restabelecimentoAguaSubstituicaoHidrometroHistorico");

				Date dataRetirada = Util.converteStringParaDate(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
								.getDataRetirada());

				hidrometroSubstituicaoHistorico.setDataRetirada(dataRetirada);

				if(numeroLeituraRetiradaHidrometro != null && !numeroLeituraRetiradaHidrometro.equalsIgnoreCase("")){
					hidrometroSubstituicaoHistorico.setNumeroLeituraRetirada(Integer.valueOf(numeroLeituraRetiradaHidrometro));
				}

				hidrometroSubstituicaoHistorico.setUltimaAlteracao(new Date());
				// *****************************************************************

				ordemServico = this.setDadosOrdemServico(ordemServico,
								efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm);

				String qtdParcelas = efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getQuantidadeParcelas();
				ordemServico.setOperacaoEfetuada(operacaoEfetuada);
				IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();

				integracaoComercialHelper.setLigacaoAgua(ligacaoAgua);
				integracaoComercialHelper.setImovel(imovel);
				integracaoComercialHelper.setOrdemServico(ordemServico);
				integracaoComercialHelper.setQtdParcelas(qtdParcelas);
				integracaoComercialHelper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
				integracaoComercialHelper.setHidrometroSubstituicaoHistorico(hidrometroSubstituicaoHistorico);
				integracaoComercialHelper.setSituacaoHidrometroSubstituido(hidrometroSubstituicaoHistorico.getHidrometro()
								.getHidrometroSituacao().getId()
								+ "");

				if(!Util.isVazioOuBranco(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getLocalArmazenagem())){
					integracaoComercialHelper.setLocalArmazenagemHidrometro(Integer
									.valueOf(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getLocalArmazenagem()));
				}

				if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

					try{

						fachada.verificarQuantidadeParcelas(usuario, Util.obterShort(integracaoComercialHelper.getQtdParcelas()));
					}catch(FachadaException e){

						String[] parametros = new String[e.getParametroMensagem().size()];
						e.getParametroMensagem().toArray(parametros);
						ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
						ex.setUrlBotaoVoltar("/gsan/exibirEfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroAction.do");
						throw ex;
					}
				}

				if(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getVeioEncerrarOS().equalsIgnoreCase("FALSE")){
					integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);

					try{

						fachada.efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometro(integracaoComercialHelper, usuario);
					}catch(FachadaException e){

						String[] parametros = new String[e.getParametroMensagem().size()];
						e.getParametroMensagem().toArray(parametros);
						ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
						ex.setUrlBotaoVoltar("/gsan/exibirEfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroAction.do");
						throw ex;

					}
				}else{
					integracaoComercialHelper.setVeioEncerrarOS(Boolean.TRUE);
					sessao.setAttribute("integracaoComercialHelper", integracaoComercialHelper);

					if(sessao.getAttribute("semMenu") == null){
						retorno = actionMapping.findForward("encerrarOrdemServicoAction");
					}else{
						retorno = actionMapping.findForward("encerrarOrdemServicoPopupAction");
					}
					sessao.removeAttribute("caminhoRetornoIntegracaoComercial");
				}
				if(retorno.getName().equalsIgnoreCase("telaSucesso")){
					// Monta a p�gina de sucesso
					montarPaginaSucesso(httpServletRequest,
									"Restabelecimento da Liga��o de �gua com Instala��o de Hidr�metro efetuada com Sucesso",
									"Efetuar outro Restabelecimento da Liga��o de �gua com Instala��o de Hidr�metro",
									"exibirEfetuarRestabelecimentoLigacaoAguaComInstalacaoHidrometroAction.do?menu=sim");
				}

				return retorno;
			}else{
				throw new ActionServletException("atencao.imovel_sem_ligacao_agua");
			}
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Ordem de Servi�o");
		}
	}

	// [SB0003] - Atualizar Ordem de Servi�o
	//
	// M�todo respons�vel por setar os dados da ordem de servi�o
	// de acordo com as exig�ncias do caso de uso
	public OrdemServico setDadosOrdemServico(
					OrdemServico ordemServico,
					EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm){

		String idServicoMotivoNaoCobranca = efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getMotivoNaoCobranca();
		String valorPercentual = efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getPercentualCobranca();

		if(ordemServico != null && efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getIdTipoDebito() != null){

			ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;

			ordemServico.setIndicadorComercialAtualizado(new Short("1"));

			if(idServicoMotivoNaoCobranca != null && !idServicoMotivoNaoCobranca.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
				servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
				servicoNaoCobrancaMotivo.setId(new Integer(idServicoMotivoNaoCobranca));
			}else{

				if(valorPercentual != null && !valorPercentual.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
					ordemServico.setPercentualCobranca(new BigDecimal(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
									.getPercentualCobranca()));
				}

				BigDecimal valorAtual = new BigDecimal(0);
				if(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getValorDebito() != null){
					String valorDebito = efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getValorDebito().toString()
									.replace(".", "");

					valorDebito = valorDebito.replace(",", ".");

					valorAtual = new BigDecimal(valorDebito);

					ordemServico.setValorAtual(valorAtual);
				}
			}

			ordemServico.setServicoNaoCobrancaMotivo(servicoNaoCobrancaMotivo);

			ordemServico.setDataEncerramento(new Date());

			ordemServico.setDataExecucao(Util.converteStringParaDate(
							efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getDataRestabelecimento(), true));

			ordemServico.setUltimaAlteracao(new Date());

		}

		return ordemServico;
	}

	// [SB0004] - Gerar Hist�rico de Instala��o do Hidr�metro
	//
	// M�todo respons�vel por setar os dados do hidr�metro instala��o hist�rico
	// de acordo com os dados selecionados pelo usu�rio e pelas exig�ncias do
	// caso de uso
	public HidrometroInstalacaoHistorico setDadosHidrometroInstalacaoHistorico(
					HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico,
					EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm){

		Fachada fachada = Fachada.getInstancia();

		String numeroHidrometro = efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometro();

		if(numeroHidrometro != null){
			// Pesquisa o Hidr�metro
			Hidrometro hidrometro = fachada.pesquisarHidrometroPeloNumero(numeroHidrometro);

			if(hidrometro == null){
				throw new ActionServletException("atencao.hidrometro_inexistente");
			}

			if(hidrometro.getHidrometroSituacao().getId().intValue() != HidrometroSituacao.DISPONIVEL){
				throw new ActionServletException("atencao.situacao_ligacao_agua_invalida_restabelecimento_situacao_hidromentro", null,
								hidrometro.getHidrometroSituacao().getDescricao());
			}

			hidrometroInstalacaoHistorico.setHidrometro(hidrometro);
			hidrometroInstalacaoHistorico.setNumeroHidrometro(hidrometro.getNumero());
		}

		// Data instala��o
		if(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getDataInstalacao() != null
						&& !efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getDataInstalacao().equals("")){
			Date dataInstalacao = Util.converteStringParaDate(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
							.getDataInstalacao());
			if(hidrometroInstalacaoHistorico.getLigacaoAgua().getDataLigacao() != null
							&& dataInstalacao.before(hidrometroInstalacaoHistorico.getLigacaoAgua().getDataLigacao())){
				throw new ActionServletException("atencao.data_instalacao_hidrometro_anterior_ligacao_agua");
			}else{
				hidrometroInstalacaoHistorico.setDataInstalacao(dataInstalacao);
			}

		}

		// Medi��o tipo
		MedicaoTipo medicaoTipo = new MedicaoTipo();
		medicaoTipo.setId(MedicaoTipo.LIGACAO_AGUA);
		hidrometroInstalacaoHistorico.setMedicaoTipo(medicaoTipo);

		// hidr�metro local instala��o
		HidrometroLocalInstalacao hidrometroLocalInstalacao = new HidrometroLocalInstalacao();
		hidrometroLocalInstalacao.setId(Integer.parseInt(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
						.getLocalInstalacao()));
		hidrometroInstalacaoHistorico.setHidrometroLocalInstalacao(hidrometroLocalInstalacao);

		// prote��o
		HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();
		hidrometroProtecao.setId(Integer.parseInt(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getProtecao()));
		hidrometroInstalacaoHistorico.setHidrometroProtecao(hidrometroProtecao);

		// leitura instala��o
		if(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getLeituraInstalacao() != null
						&& !efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getLeituraInstalacao().trim().equals("")){
			hidrometroInstalacaoHistorico.setNumeroLeituraInstalacao(Integer
							.parseInt(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getLeituraInstalacao()));
		}else{
			hidrometroInstalacaoHistorico.setNumeroLeituraInstalacao(0);
		}

		// cavalete
		hidrometroInstalacaoHistorico.setIndicadorExistenciaCavalete(Short
						.parseShort(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getSituacaoCavalete()));

		/*
		 * Campos opcionais
		 */

		// data da retirada
		hidrometroInstalacaoHistorico.setDataRetirada(null);
		// leitura retirada
		hidrometroInstalacaoHistorico.setNumeroLeituraRetirada(null);
		// leitura corte
		hidrometroInstalacaoHistorico.setNumeroLeituraCorte(null);
		// leitura supress�o
		hidrometroInstalacaoHistorico.setNumeroLeituraSupressao(null);
		// numero selo
		if(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getNumeroSelo() != null
						&& !efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getNumeroSelo().equals("")){
			hidrometroInstalacaoHistorico.setNumeroSelo(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
							.getNumeroSelo());
		}else{
			hidrometroInstalacaoHistorico.setNumeroSelo(null);
		}
		// tipo de rateio
		hidrometroInstalacaoHistorico.setRateioTipo(new RateioTipo(RateioTipo.RATEIO_POR_IMOVEL));
		hidrometroInstalacaoHistorico.setDataImplantacaoSistema(new Date());

		// indicador instala��o substitui��o
		hidrometroInstalacaoHistorico.setIndicadorInstalcaoSubstituicao(new Short("1"));

		// data �ltima altera��o
		hidrometroInstalacaoHistorico.setUltimaAlteracao(new Date());

		return hidrometroInstalacaoHistorico;

	}
}
