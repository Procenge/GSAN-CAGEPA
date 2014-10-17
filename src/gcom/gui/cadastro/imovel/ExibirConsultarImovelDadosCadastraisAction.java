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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.Quadra;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.Bacia;
import gcom.operacional.SetorAbastecimento;
import gcom.operacional.SistemaAbastecimento;
import gcom.operacional.SubBacia;
import gcom.util.ConstantesAplicacao;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 1° Aba do [UC0472] Consultar Imóvel
 * 
 * @author Rafael Santos
 * @since 07/09/2006
 */
public class ExibirConsultarImovelDadosCadastraisAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("consultarImovelDadosCadastrais");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarImovelActionForm consultarImovelActionForm = (ConsultarImovelActionForm) actionForm;

		// id do imovel da aba dados cadastrais
		String idImovelDadosCadastrais = consultarImovelActionForm.getIdImovelDadosCadastrais();
		String limparForm = httpServletRequest.getParameter("limparForm");
		String indicadorNovo = httpServletRequest.getParameter("indicadorNovo");

		String idImovelPrincipalAba = null;
		if(sessao.getAttribute("idImovelPrincipalAba") != null){
			idImovelPrincipalAba = (String) sessao.getAttribute("idImovelPrincipalAba");
		}

		try{
			if(ParametroCadastro.P_INDICADOR_IMOVEL_CONSUMO_FAIXA_AREA_CATG.executar().equals(ConstantesSistema.SIM.toString())){
				sessao.setAttribute("indicadorImovelConsumoFaixaAreaCatg", Short.toString(ConstantesSistema.SIM));
			}else{
				sessao.removeAttribute("indicadorImovelConsumoFaixaAreaCatg");
			}
		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e);
		}

		if(limparForm != null && !limparForm.equals("")){
			this.limparForm(consultarImovelActionForm, httpServletRequest);
		}
		if((idImovelDadosCadastrais != null && !idImovelDadosCadastrais.equalsIgnoreCase(""))
						|| (idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase(""))){

			if(idImovelDadosCadastrais != null && !idImovelDadosCadastrais.equalsIgnoreCase("")){

				if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){

					if(indicadorNovo != null && !indicadorNovo.equals("")){
						consultarImovelActionForm.setIdImovelDadosCadastrais(idImovelDadosCadastrais);
						// idImovelDadosCadastrais = idImovelDadosCadastrais;

					}else if(!(idImovelDadosCadastrais.equals(idImovelPrincipalAba))){
						consultarImovelActionForm.setIdImovelDadosCadastrais(idImovelPrincipalAba);
						idImovelDadosCadastrais = idImovelPrincipalAba;
					}

				}
			}else if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){
				consultarImovelActionForm.setIdImovelRegistroAtendimento(idImovelPrincipalAba);
				idImovelDadosCadastrais = idImovelPrincipalAba;
			}

			// Obtém a instância da Fachada
			Fachada fachada = Fachada.getInstancia();
			Imovel imovel = null;
			// verifica se o objeto imovel é o mesmo ja pesquisado, para não precisar pesquisar mas.
			boolean imovelNovoPesquisado = false;

			if(sessao.getAttribute("imovelDadosCadastrais") != null){
				imovel = (Imovel) sessao.getAttribute("imovelDadosCadastrais");
				if(!(imovel.getId().toString().equals(idImovelDadosCadastrais.trim()))){
					imovel = fachada.consultarImovelDadosCadastrais(Integer.valueOf(idImovelDadosCadastrais.trim()));
					imovelNovoPesquisado = true;
				}

			}else{
				imovel = fachada.consultarImovelDadosCadastrais(Integer.valueOf(idImovelDadosCadastrais.trim()));
				imovelNovoPesquisado = true;
			}

			if(imovel != null){

				sessao.setAttribute("imovelDadosCadastrais", imovel);
				sessao.setAttribute("idImovelPrincipalAba", imovel.getId().toString());
				consultarImovelActionForm.setIdImovelDadosCadastrais(imovel.getId().toString());

				// caso o imovel pesquisado seja diferente do pesquisado anterior ou seja a primeira
				// vez que se esteja pesquisando
				if(imovelNovoPesquisado){

					// this.limparForm(consultarImovelActionForm, httpServletRequest);

					// seta na tela a inscrição do imovel
					httpServletRequest.setAttribute("idImovelDadosCadastraisNaoEncontrado", null);

					consultarImovelActionForm.setMatriculaImovelDadosCadastrais(fachada.pesquisarInscricaoImovel(Integer
									.valueOf(idImovelDadosCadastrais.trim()), true));

					// pesquisa o endereço
					String enderecoImovelDadosCadastrais = fachada.pesquisarEndereco(Integer.valueOf(idImovelDadosCadastrais.trim()));

					// seta o endereço do imovel
					sessao.setAttribute("enderecoImovelDadosCadastrais", enderecoImovelDadosCadastrais);
					consultarImovelActionForm.setEnderecoImovelDadosCadastrais(enderecoImovelDadosCadastrais);

					// seta a situação de agua
					if(imovel.getLigacaoAguaSituacao() != null){
						consultarImovelActionForm.setSituacaoAguaDadosCadastrais(imovel.getLigacaoAguaSituacao().getDescricao());
					}
					// seta a situação de esgoto
					if(imovel.getLigacaoEsgotoSituacao() != null){
						consultarImovelActionForm.setSituacaoEsgotoDadosCadastrais(imovel.getLigacaoEsgotoSituacao().getDescricao());
					}

					// pesquisar cliente do imovel
					Collection clientesImovel = fachada.pesquisarClientesImovel(Integer.valueOf(idImovelDadosCadastrais.trim()));
					sessao.setAttribute("imovelClientes", clientesImovel);

					FiltroImovelConsumoFaixaAreaCategoria filtroImovelConsumoFaixaAreaCategoria = new FiltroImovelConsumoFaixaAreaCategoria();
					filtroImovelConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(
									FiltroImovelConsumoFaixaAreaCategoria.IMOVEL_ID, Integer.valueOf(idImovelDadosCadastrais.trim())));
					filtroImovelConsumoFaixaAreaCategoria
									.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelConsumoFaixaAreaCategoria.CATEGORIA);

					Collection<ImovelConsumoFaixaAreaCategoria> colecaoImovelConsumoFaixaAreaCategoria = fachada.pesquisar(
									filtroImovelConsumoFaixaAreaCategoria, ImovelConsumoFaixaAreaCategoria.class.getName());
					sessao.setAttribute("colecaoImovelConsumoFaixaAreaCategoria", colecaoImovelConsumoFaixaAreaCategoria);

					Collection imovelSubcategorias = fachada.pesquisarCategoriasImovel(Integer.valueOf(idImovelDadosCadastrais.trim()));
					sessao.setAttribute("imovelSubcategorias", imovelSubcategorias);

					// perfil
					if(imovel.getImovelPerfil() != null){
						consultarImovelActionForm.setImovelPerfilDadosCadastrais(imovel.getImovelPerfil().getDescricao());
					}
					// despejo
					if(imovel.getDespejo() != null){
						consultarImovelActionForm.setDespejoDadosCadastrais(imovel.getDespejo().getDescricao());
					}

					// tipo esgotamento
					if(imovel.getEsgotamento() != null){
						consultarImovelActionForm.setEsgotamento(imovel.getEsgotamento().getDescricao());
					}

					// padrão construção
					if(imovel.getPadraoConstrucao() != null){
						consultarImovelActionForm.setPadraoConstrucao(imovel.getPadraoConstrucao().getDescricao());
					}

					// numero quarto
					if(imovel.getNumeroQuarto() != null){
						consultarImovelActionForm.setNumeroQuarto("" + imovel.getNumeroQuarto());

					}

					// numero banheiro
					if(imovel.getNumeroBanheiro() != null){
						consultarImovelActionForm.setNumeroBanheiro("" + imovel.getNumeroBanheiro());
					}

					// renda familiar faixa
					if(imovel.getRendaFamiliarFaixa() != null){
						consultarImovelActionForm.setRendaFamiliarFaixa(imovel.getRendaFamiliarFaixa().getFaixaCompleta());
					}

					// numero adulto
					if(imovel.getNumeroAdulto() != null){
						consultarImovelActionForm.setNumeroAdulto("" + imovel.getNumeroAdulto());
						httpServletRequest.getSession().setAttribute("numeroAdulto", "" + imovel.getNumeroAdulto());
					}

					// numero crianca
					if(imovel.getNumeroCrianca() != null){
						consultarImovelActionForm.setNumeroCrianca("" + imovel.getNumeroCrianca());
						httpServletRequest.getSession().setAttribute("numeroCrianca", "" + imovel.getNumeroCrianca());
					}

					// numero morador trabalhador
					if(imovel.getNumeroMoradorTrabalhador() != null){
						consultarImovelActionForm.setNumeroMoradorTrabalhador("" + imovel.getNumeroMorador());
						httpServletRequest.getSession().setAttribute("numeroMoradorTrabalhador", "" + imovel.getNumeroMoradorTrabalhador());
					}

					// foto fachada
					if(imovel.getFotoFachada() != null && imovel.getFotoFachada().length > 0){
						consultarImovelActionForm.setFotoFachada("" + imovel.getFotoFachada().length);
					}

					// area construida
					if(imovel.getAreaConstruida() != null){
						consultarImovelActionForm.setAreaConstruidaDadosDadosCadastrais(Util.formatarMoedaReal(imovel.getAreaConstruida()));
					}else{
						if(imovel.getAreaConstruidaFaixa() != null){
							if(imovel.getAreaConstruidaFaixa().getVolumeMenorFaixa() != null
											&& imovel.getAreaConstruidaFaixa().getVolumeMaiorFaixa() != null){
								consultarImovelActionForm.setAreaConstruidaDadosDadosCadastrais(imovel.getAreaConstruidaFaixa()
												.getVolumeMenorFaixa().toString()
												+ " a " + imovel.getAreaConstruidaFaixa().getVolumeMaiorFaixa().toString());
							}
						}
					}

					// testada do lote
					if(imovel.getTestadaLote() != null){
						consultarImovelActionForm.setTestadaLoteDadosCadastrais(imovel.getTestadaLote().toString());
					}
					// reservatorio inferior
					if(imovel.getVolumeReservatorioInferior() != null){
						consultarImovelActionForm.setVolumeReservatorioInferiorDadosCadastrais(Util.formatarMoedaReal(imovel
										.getVolumeReservatorioInferior()));
					}else{
						if(imovel.getReservatorioVolumeFaixaInferior() != null){
							if(imovel.getReservatorioVolumeFaixaInferior().getVolumeMenorFaixa() != null
											&& imovel.getReservatorioVolumeFaixaInferior().getVolumeMaiorFaixa() != null){
								consultarImovelActionForm
												.setVolumeReservatorioInferiorDadosCadastrais(Util.formatarMoedaReal(imovel
																.getReservatorioVolumeFaixaInferior().getVolumeMenorFaixa())
																+ " a "
																+ Util.formatarMoedaReal(imovel.getReservatorioVolumeFaixaInferior()
																				.getVolumeMaiorFaixa()));
							}
						}
					}
					// reservatorio superior
					if(imovel.getVolumeReservatorioSuperior() != null){
						consultarImovelActionForm.setVolumeReservatorioSuperiorDadosCadastrais(Util.formatarMoedaReal(imovel
										.getVolumeReservatorioSuperior()));
					}else{
						if(imovel.getReservatorioVolumeFaixaSuperior() != null){
							if(imovel.getReservatorioVolumeFaixaSuperior().getVolumeMenorFaixa() != null
											&& imovel.getReservatorioVolumeFaixaSuperior().getVolumeMaiorFaixa() != null){
								consultarImovelActionForm
												.setVolumeReservatorioSuperiorDadosCadastrais(Util.formatarMoedaReal(imovel
																.getReservatorioVolumeFaixaSuperior().getVolumeMenorFaixa())
																+ " a "
																+ Util.formatarMoedaReal(imovel.getReservatorioVolumeFaixaSuperior()
																				.getVolumeMaiorFaixa()));
							}
						}
					}

					// piscina
					if(imovel.getVolumePiscina() != null){
						consultarImovelActionForm.setVolumePiscinaDadosCadastrais(Util.formatarMoedaReal(imovel.getVolumePiscina()));
					}else{
						if(imovel.getPiscinaVolumeFaixa() != null){
							if(imovel.getPiscinaVolumeFaixa().getVolumeMenorFaixa() != null
											&& imovel.getPiscinaVolumeFaixa().getVolumeMaiorFaixa() != null){
								consultarImovelActionForm.setVolumePiscinaDadosCadastrais(Util.formatarMoedaReal(imovel
												.getPiscinaVolumeFaixa().getVolumeMenorFaixa())
												+ " a " + Util.formatarMoedaReal(imovel.getPiscinaVolumeFaixa().getVolumeMaiorFaixa()));
							}
						}
					}

					// fonte abastecimento
					if(imovel.getFonteAbastecimento() != null){
						consultarImovelActionForm.setFonteAbastecimentoDadosCadastrais(imovel.getFonteAbastecimento().getDescricao());
					}

					// poco tipo
					if(imovel.getPocoTipo() != null){
						consultarImovelActionForm.setPocoTipoDadosCadastrais(imovel.getPocoTipo().getDescricao());
					}

					// quadra
					Quadra quadra = imovel.getQuadra();
					if(quadra != null){
						int numeroQuadra = quadra.getNumeroQuadra();
						String numeroQuadraStr = Integer.toString(numeroQuadra);
						consultarImovelActionForm.setNumeroQuadra(numeroQuadraStr);
					}
					// distrio operacional
					if(imovel.getDistritoOperacional() != null){
						consultarImovelActionForm.setDistritoOperacionalDadosCadastrais(imovel.getDistritoOperacional().getDescricao());
					}
					// divisão de esgoto
					if(quadra != null && quadra.getBacia() != null && quadra.getBacia().getSistemaEsgoto() != null
									&& quadra.getBacia().getSistemaEsgoto().getDivisaoEsgoto() != null){
						consultarImovelActionForm.setDivisaoEsgotoDadosCadastrais(quadra.getBacia().getSistemaEsgoto().getDivisaoEsgoto()
										.getDescricao());
					}
					// pavimento rua
					if(imovel.getPavimentoRua() != null){
						consultarImovelActionForm.setPavimentoRuaDadosCadastrais(imovel.getPavimentoRua().getDescricao());
					}
					// pavimento calcada
					if(imovel.getPavimentoCalcada() != null){
						consultarImovelActionForm.setPavimentoCalcadaDadosCadastrais(imovel.getPavimentoCalcada().getDescricao());
					}
					// numero iptu
					if(imovel.getNumeroIptu() != null){
						consultarImovelActionForm.setNumeroIptuDadosCadastrais(Util.formatarMoedaReal(imovel.getNumeroIptu()));
					}
					// numero celpe
					if(imovel.getNumeroCelpe() != null){
						consultarImovelActionForm.setNumeroCelpeDadosCadastrais(imovel.getNumeroCelpe().toString());
					}
					// coordenada X
					if(imovel.getCoordenadaX() != null){
						consultarImovelActionForm.setCoordenadaXDadosCadastrais(Util.formataBigDecimal(imovel.getCoordenadaX(), 8, false));
					}
					// coordenada Y
					if(imovel.getCoordenadaY() != null){
						consultarImovelActionForm.setCoordenadaYDadosCadastrais(Util.formataBigDecimal(imovel.getCoordenadaY(), 8, false));
					}
					// cadastro ocorrencia
					if(imovel.getCadastroOcorrencia() != null){
						consultarImovelActionForm.setCadastroOcorrenciaDadosCadastrais(imovel.getCadastroOcorrencia().getDescricao());
					}
					// elo anormalidade
					if(imovel.getEloAnormalidade() != null){
						consultarImovelActionForm.setEloAnormalidadeDadosCadastrais(imovel.getEloAnormalidade().getDescricao());
					}
					// indicador de imovel condominio
					if(imovel.getIndicadorImovelCondominio() != null){
						if(imovel.getIndicadorImovelCondominio().shortValue() == 1){
							consultarImovelActionForm.setIndicadorImovelCondominioDadosCadastrais("SIM");
						}else{
							consultarImovelActionForm.setIndicadorImovelCondominioDadosCadastrais("NÃO");
						}
					}
					// id do imovel condominio
					if(imovel.getImovelCondominio() != null){
						consultarImovelActionForm.setImovelCondominioDadosCadastrais(imovel.getImovelCondominio().getId().toString());
					}
					// imovel principal
					if(imovel.getImovelPrincipal() != null){
						consultarImovelActionForm.setImovelPrincipalDadosCadastrais(imovel.getImovelPrincipal().getId().toString());
					}
					// numero pontos utilização
					if(imovel.getNumeroPontosUtilizacao() != null){
						consultarImovelActionForm.setNumeroPontosUtilizacaoDadosCadastrais(imovel.getNumeroPontosUtilizacao().toString());
					}
					// numero moradores
					if(imovel.getNumeroMorador() != null){
						consultarImovelActionForm.setNumeroMoradoresDadosCadastrais(imovel.getNumeroMorador().toString());
					}
					// jardim
					if(imovel.getIndicadorJardim() != null){
						if(imovel.getIndicadorJardim().shortValue() == 1){
							consultarImovelActionForm.setJardimDadosCadastrais("SIM");
						}else{
							consultarImovelActionForm.setJardimDadosCadastrais("NÃO");
						}
					}

					if(imovel.getImovelContaEnvio() != null){
						consultarImovelActionForm.setDescricaoOpcaoEnvioConta(imovel.getImovelContaEnvio().getDescricao());
					}

					// opção de consumo
					Short indicadorContratoConsumo = imovel.getIndicadorContratoConsumo();
					if(indicadorContratoConsumo != null){
						if(indicadorContratoConsumo.equals(ConstantesSistema.SIM)){
							consultarImovelActionForm.setIndicadorContratoConsumo("SIM");
						}else{
							consultarImovelActionForm.setIndicadorContratoConsumo("NÃO");
						}
					}

					// Setor de Abastecimento e Sistema de Abastecimento
					String descricaoSistemaAbastecimento = "";
					String descricaoSetorAbastecimento = "";
					SetorAbastecimento setorAbastecimento = imovel.getSetorAbastecimento();

					if(!Util.isVazioOuBranco(setorAbastecimento)){
						descricaoSetorAbastecimento = setorAbastecimento.getDescricao();

						SistemaAbastecimento sistemaAbastecimento = setorAbastecimento.getSistemaAbastecimento();
						if(!Util.isVazioOuBranco(sistemaAbastecimento)){
							descricaoSistemaAbastecimento = sistemaAbastecimento.getDescricao();
						}
					}

					consultarImovelActionForm.setSetorAbastecimento(descricaoSetorAbastecimento);
					consultarImovelActionForm.setSistemaAbastecimento(descricaoSistemaAbastecimento);

					// Sub-bacia e Bacia
					String descricaoSubBacia = "";
					String descricaoBacia = "";
					SubBacia subBacia = imovel.getSubBacia();

					if(!Util.isVazioOuBranco(subBacia)){
						descricaoSubBacia = subBacia.getDescricao();

						Bacia bacia = subBacia.getBacia();
						if(!Util.isVazioOuBranco(bacia)){
							descricaoBacia = bacia.getDescricao();
						}
					}

					consultarImovelActionForm.setSubBacia(descricaoSubBacia);
					consultarImovelActionForm.setBacia(descricaoBacia);
				}

				// 3.4. Caso o imóvel esteja com alteração da inscrição agendada para o encerramento
				// do faturamento (existe ocorrência na tabela IMOVEL_INSCR_ALTERADA para
				// IMOV_ID=IMOV_ID da tabela IMOVEL e IMIA_ICATUALIZADO=2 (não) e
				// IMIA_ICALTERACAOEXCLUIDA=2 (não) e IMIA_ICERROALTERACAO=nulo)
				FiltroImovelInscricaoAlterada filtroImovelInscricaoAlterada = new FiltroImovelInscricaoAlterada();
				filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.IMOVEL_ID, imovel
								.getId()));
				filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.INDICADOR_ATUALIZADO,
								ConstantesSistema.NAO));
				filtroImovelInscricaoAlterada.adicionarParametro(new ParametroNulo(FiltroImovelInscricaoAlterada.INDICADOR_ERRO_ALTERACAO));
				filtroImovelInscricaoAlterada.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.LOCALIDADE_ATUAL);
				filtroImovelInscricaoAlterada.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.SETOR_COMERCIAL_ATUAL);
				filtroImovelInscricaoAlterada.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.QUADRA_ATUAL);

				ImovelInscricaoAlterada imovelInscricaoAlterada = (ImovelInscricaoAlterada) Util.retonarObjetoDeColecao(fachada.pesquisar(
								filtroImovelInscricaoAlterada, ImovelInscricaoAlterada.class.getName()));

				if(imovelInscricaoAlterada != null){
					String novaInscricaoFormatada = imovelInscricaoAlterada.getNovaInscricaoFormatada();

					if(!Util.isVazioOuBranco(novaInscricaoFormatada)){
						String mensagemInscricaoAlterada = ConstantesAplicacao.get("atencao.consulta.imovel.alteracao.inscricao",
										novaInscricaoFormatada);

						if(!Util.isVazioOuBranco(mensagemInscricaoAlterada)){
							httpServletRequest.setAttribute("mensagemAlteracaoInscricao", mensagemInscricaoAlterada);
						}
					}
				}

			}else{
				httpServletRequest.setAttribute("idImovelDadosCadastraisNaoEncontrado", "true");
				consultarImovelActionForm.setMatriculaImovelDadosCadastrais("IMÓVEL INEXISTENTE");

				// limpar os dados pesquisados
				sessao.removeAttribute("imovelDadosCadastrais");
				sessao.removeAttribute("imovelClientes");
				sessao.removeAttribute("colecaoImovelConsumoFaixaAreaCategoria");
				sessao.removeAttribute("enderecoImovelDadosCadastrais");
				sessao.removeAttribute("imovelSubcategorias");
				sessao.removeAttribute("numeroAdulto");
				sessao.removeAttribute("numeroCrianca");
				sessao.removeAttribute("numeroMoradorTrabalhador");
				consultarImovelActionForm.setIdImovelDadosCadastrais(null);
				consultarImovelActionForm.setEnderecoImovelDadosCadastrais(null);
				consultarImovelActionForm.setSituacaoAguaDadosCadastrais(null);
				consultarImovelActionForm.setSituacaoEsgotoDadosCadastrais(null);
				consultarImovelActionForm.setImovelPerfilDadosCadastrais(null);
				consultarImovelActionForm.setDespejoDadosCadastrais(null);
				consultarImovelActionForm.setAreaConstruidaDadosDadosCadastrais(null);
				consultarImovelActionForm.setTestadaLoteDadosCadastrais(null);
				consultarImovelActionForm.setVolumeReservatorioInferiorDadosCadastrais(null);
				consultarImovelActionForm.setVolumeReservatorioSuperiorDadosCadastrais(null);
				consultarImovelActionForm.setVolumePiscinaDadosCadastrais(null);
				consultarImovelActionForm.setFonteAbastecimentoDadosCadastrais(null);
				consultarImovelActionForm.setPocoTipoDadosCadastrais(null);
				consultarImovelActionForm.setDistritoOperacionalDadosCadastrais(null);
				consultarImovelActionForm.setPavimentoRuaDadosCadastrais(null);
				consultarImovelActionForm.setPavimentoCalcadaDadosCadastrais(null);
				consultarImovelActionForm.setNumeroIptuDadosCadastrais(null);
				consultarImovelActionForm.setNumeroCelpeDadosCadastrais(null);
				consultarImovelActionForm.setCoordenadaXDadosCadastrais(null);
				consultarImovelActionForm.setCoordenadaYDadosCadastrais(null);
				consultarImovelActionForm.setCadastroOcorrenciaDadosCadastrais(null);
				consultarImovelActionForm.setEloAnormalidadeDadosCadastrais(null);
				consultarImovelActionForm.setIndicadorImovelCondominioDadosCadastrais(null);
				consultarImovelActionForm.setImovelCondominioDadosCadastrais(null);
				consultarImovelActionForm.setImovelPrincipalDadosCadastrais(null);
				consultarImovelActionForm.setNumeroPontosUtilizacaoDadosCadastrais(null);
				consultarImovelActionForm.setNumeroMoradoresDadosCadastrais(null);
				consultarImovelActionForm.setNumeroAdulto(null);
				consultarImovelActionForm.setNumeroBanheiro(null);
				consultarImovelActionForm.setNumeroMoradorTrabalhador(null);
				consultarImovelActionForm.setFotoFachada(null);
				consultarImovelActionForm.setNumeroQuarto(null);
				consultarImovelActionForm.setRendaFamiliarFaixa(null);
				consultarImovelActionForm.setEsgotamento(null);
				consultarImovelActionForm.setPadraoConstrucao(null);
				consultarImovelActionForm.setNumeroCrianca(null);
				consultarImovelActionForm.setNumeroQuadra(null);
				consultarImovelActionForm.setIndicadorContratoConsumo(null);
				consultarImovelActionForm.setSistemaAbastecimento(null);
				consultarImovelActionForm.setSetorAbastecimento(null);
				consultarImovelActionForm.setBacia(null);
				consultarImovelActionForm.setSubBacia(null);
			}
		}else{
			consultarImovelActionForm.setIdImovelDadosCadastrais(idImovelDadosCadastrais);

			httpServletRequest.setAttribute("idImovelDadosCadastraisNaoEncontrado", null);
			sessao.removeAttribute("imovelDadosCadastrais");
			sessao.removeAttribute("imovelClientes");
			sessao.removeAttribute("colecaoImovelConsumoFaixaAreaCategoria");
			sessao.removeAttribute("enderecoImovelDadosCadastrais");
			sessao.removeAttribute("imovelSubcategorias");
			sessao.removeAttribute("idImovelPrincipalAba");
			sessao.removeAttribute("numeroAdulto");
			sessao.removeAttribute("numeroCrianca");
			sessao.removeAttribute("numeroMoradorTrabalhador");

			consultarImovelActionForm.setMatriculaImovelDadosCadastrais(null);
			consultarImovelActionForm.setEnderecoImovelDadosCadastrais(null);
			consultarImovelActionForm.setSituacaoAguaDadosCadastrais(null);
			consultarImovelActionForm.setSituacaoEsgotoDadosCadastrais(null);
			consultarImovelActionForm.setImovelPerfilDadosCadastrais(null);
			consultarImovelActionForm.setDespejoDadosCadastrais(null);
			consultarImovelActionForm.setAreaConstruidaDadosDadosCadastrais(null);
			consultarImovelActionForm.setTestadaLoteDadosCadastrais(null);
			consultarImovelActionForm.setVolumeReservatorioInferiorDadosCadastrais(null);
			consultarImovelActionForm.setVolumeReservatorioSuperiorDadosCadastrais(null);
			consultarImovelActionForm.setVolumePiscinaDadosCadastrais(null);
			consultarImovelActionForm.setFonteAbastecimentoDadosCadastrais(null);
			consultarImovelActionForm.setPocoTipoDadosCadastrais(null);
			consultarImovelActionForm.setDistritoOperacionalDadosCadastrais(null);
			consultarImovelActionForm.setPavimentoRuaDadosCadastrais(null);
			consultarImovelActionForm.setPavimentoCalcadaDadosCadastrais(null);
			consultarImovelActionForm.setNumeroIptuDadosCadastrais(null);
			consultarImovelActionForm.setNumeroCelpeDadosCadastrais(null);
			consultarImovelActionForm.setCoordenadaXDadosCadastrais(null);
			consultarImovelActionForm.setCoordenadaYDadosCadastrais(null);
			consultarImovelActionForm.setCadastroOcorrenciaDadosCadastrais(null);
			consultarImovelActionForm.setEloAnormalidadeDadosCadastrais(null);
			consultarImovelActionForm.setIndicadorImovelCondominioDadosCadastrais(null);
			consultarImovelActionForm.setImovelCondominioDadosCadastrais(null);
			consultarImovelActionForm.setImovelPrincipalDadosCadastrais(null);
			consultarImovelActionForm.setNumeroPontosUtilizacaoDadosCadastrais(null);
			consultarImovelActionForm.setNumeroMoradoresDadosCadastrais(null);
			consultarImovelActionForm.setNumeroAdulto(null);
			consultarImovelActionForm.setNumeroBanheiro(null);
			consultarImovelActionForm.setNumeroMoradorTrabalhador(null);
			consultarImovelActionForm.setFotoFachada(null);
			consultarImovelActionForm.setNumeroQuarto(null);
			consultarImovelActionForm.setRendaFamiliarFaixa(null);
			consultarImovelActionForm.setEsgotamento(null);
			consultarImovelActionForm.setPadraoConstrucao(null);
			consultarImovelActionForm.setNumeroCrianca(null);
			consultarImovelActionForm.setNumeroQuadra(null);
			consultarImovelActionForm.setIndicadorContratoConsumo(null);
			consultarImovelActionForm.setSistemaAbastecimento(null);
			consultarImovelActionForm.setSetorAbastecimento(null);
			consultarImovelActionForm.setBacia(null);
			consultarImovelActionForm.setSubBacia(null);
		}

		return retorno;
	}

	private void limparForm(ConsultarImovelActionForm consultarImovelActionForm, HttpServletRequest httpServletRequest){

		// limpar os dados
		httpServletRequest.setAttribute("idImovelDadosCadastraisNaoEncontrado", null);
		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);
		sessao.removeAttribute("imovelDadosCadastrais");
		sessao.removeAttribute("imovelClientes");

		sessao.removeAttribute("enderecoImovelDadosCadastrais");
		sessao.removeAttribute("imovelSubcategorias");
		sessao.removeAttribute("idImovelPrincipalAba");
		sessao.removeAttribute("numeroAdulto");
		sessao.removeAttribute("numeroCrianca");
		sessao.removeAttribute("numeroMoradorTrabalhador");
		consultarImovelActionForm.setIdImovelDadosComplementares(null);
		consultarImovelActionForm.setIdImovelDadosCadastrais(null);
		consultarImovelActionForm.setIdImovelAnaliseMedicaoConsumo(null);
		consultarImovelActionForm.setIdImovelHistoricoFaturamento(null);
		consultarImovelActionForm.setIdImovelDebitos(null);
		consultarImovelActionForm.setIdImovelPagamentos(null);
		consultarImovelActionForm.setIdImovelDevolucoesImovel(null);
		consultarImovelActionForm.setIdImovelDocumentosCobranca(null);
		consultarImovelActionForm.setIdImovelParcelamentosDebitos(null);
		consultarImovelActionForm.setIdImovelRegistroAtendimento(null);
		consultarImovelActionForm.setMatriculaImovelDadosCadastrais(null);
		consultarImovelActionForm.setDescricaoOpcaoEnvioConta(null);
		consultarImovelActionForm.setEnderecoImovelDadosCadastrais(null);
		consultarImovelActionForm.setSituacaoAguaDadosCadastrais(null);
		consultarImovelActionForm.setSituacaoEsgotoDadosCadastrais(null);
		consultarImovelActionForm.setImovelPerfilDadosCadastrais(null);
		consultarImovelActionForm.setDespejoDadosCadastrais(null);
		consultarImovelActionForm.setAreaConstruidaDadosDadosCadastrais(null);
		consultarImovelActionForm.setTestadaLoteDadosCadastrais(null);
		consultarImovelActionForm.setVolumeReservatorioInferiorDadosCadastrais(null);
		consultarImovelActionForm.setVolumeReservatorioSuperiorDadosCadastrais(null);
		consultarImovelActionForm.setVolumePiscinaDadosCadastrais(null);
		consultarImovelActionForm.setFonteAbastecimentoDadosCadastrais(null);
		consultarImovelActionForm.setPocoTipoDadosCadastrais(null);
		consultarImovelActionForm.setDistritoOperacionalDadosCadastrais(null);
		consultarImovelActionForm.setPavimentoRuaDadosCadastrais(null);
		consultarImovelActionForm.setPavimentoCalcadaDadosCadastrais(null);
		consultarImovelActionForm.setNumeroIptuDadosCadastrais(null);
		consultarImovelActionForm.setNumeroCelpeDadosCadastrais(null);
		consultarImovelActionForm.setCoordenadaXDadosCadastrais(null);
		consultarImovelActionForm.setCoordenadaYDadosCadastrais(null);
		consultarImovelActionForm.setCadastroOcorrenciaDadosCadastrais(null);
		consultarImovelActionForm.setEloAnormalidadeDadosCadastrais(null);
		consultarImovelActionForm.setIndicadorImovelCondominioDadosCadastrais(null);
		consultarImovelActionForm.setImovelCondominioDadosCadastrais(null);
		consultarImovelActionForm.setImovelPrincipalDadosCadastrais(null);
		consultarImovelActionForm.setNumeroPontosUtilizacaoDadosCadastrais(null);
		consultarImovelActionForm.setNumeroMoradoresDadosCadastrais(null);
		consultarImovelActionForm.setNumeroAdulto(null);
		consultarImovelActionForm.setNumeroBanheiro(null);
		consultarImovelActionForm.setNumeroMoradorTrabalhador(null);
		consultarImovelActionForm.setFotoFachada(null);
		consultarImovelActionForm.setNumeroQuarto(null);
		consultarImovelActionForm.setRendaFamiliarFaixa(null);
		consultarImovelActionForm.setEsgotamento(null);
		consultarImovelActionForm.setPadraoConstrucao(null);
		consultarImovelActionForm.setNumeroCrianca(null);
		consultarImovelActionForm.setNumeroQuadra(null);
		consultarImovelActionForm.setIndicadorContratoConsumo(null);
		consultarImovelActionForm.setSistemaAbastecimento(null);
		consultarImovelActionForm.setSetorAbastecimento(null);
		consultarImovelActionForm.setBacia(null);
		consultarImovelActionForm.setSubBacia(null);
		consultarImovelActionForm.setQuantidadeEconomia(null);

		consultarImovelActionForm.setSituacaoCobrancaDadosComplementares(null);
	}

}
