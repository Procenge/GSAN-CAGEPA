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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroFiscalizacaoSituacao;
import gcom.atendimentopublico.ordemservico.FiltroFiscalizacaoSituacaoServicoACobrar;
import gcom.atendimentopublico.ordemservico.FiscalizacaoSituacao;
import gcom.atendimentopublico.ordemservico.FiscalizacaoSituacaoServicoACobrar;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.ordemservico.bean.SituacaoEncontradaHelper;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.FiltroAtendimentoMotivoEncerramento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cobranca.CobrancaDocumento;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descrição da Classe>>
 * 
 * @author Sávio Luiz
 * @date 13/11/2006
 */
public class ExibirInformarRetornoOSFiscalizacaoAction
				extends GcomAction {

	/**
	 * [UC0448] Informar Retorno Ordem de Fiscalização
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("informarRetornoOSFiscalizacao");
		InformarRetornoOSFiscalizacaoActionForm informarRetornoOSFiscalizacaoActionForm = (InformarRetornoOSFiscalizacaoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		String idOS = informarRetornoOSFiscalizacaoActionForm.getIdOrdemServico();
		String nomeOS = informarRetornoOSFiscalizacaoActionForm.getNomeOrdemServico();

		// parte que valida o enter
		if((idOS != null && !idOS.trim().equals("")) && (nomeOS == null || nomeOS.equals(""))){

			Object[] parmsOS = fachada.pesquisarParmsOS(Util.converterStringParaInteger(idOS));

			if(parmsOS != null){

				OrdemServico ordemServico = new OrdemServico();

				if(parmsOS[7] != null){
					ServicoTipo servicoTipo = new ServicoTipo();
					servicoTipo.setIndicadorFiscalizacaoInfracao((Short) parmsOS[7]);

					ordemServico.setServicoTipo(servicoTipo);
				}

				if(parmsOS[8] != null){
					ordemServico.setSituacao((Short) parmsOS[8]);
				}

				if(parmsOS[9] != null){
					CobrancaDocumento cobrancaDocumento = new CobrancaDocumento();
					cobrancaDocumento.setId((Integer) parmsOS[9]);

					ordemServico.setCobrancaDocumento(cobrancaDocumento);
				}

				// [FS0001 - Validar Ordem de Serviço]
				fachada.validarOrdemServicoInformarRetornoOrdemFiscalizacao(ordemServico);

				/*
				 * Colocado por Raphael Rossiter em 26/01/2007 Verifica se a OS
				 * já foi fiscalizada
				 */
				fachada.verificarOSJaFiscalizada(new Integer(idOS));

				String nomeOSPesquisar = "";
				Integer idImovel = null;
				String descricaoSituacaoAgua = "";
				String descricaoSituacaoEsgoto = "";
				String ocorrencia = "";

				if(parmsOS[0] != null){
					nomeOSPesquisar = (String) parmsOS[0];
				}
				if(parmsOS[1] != null){
					idImovel = (Integer) parmsOS[1];
				}
				if(parmsOS[2] != null){
					descricaoSituacaoAgua = (String) parmsOS[2];
				}
				if(parmsOS[3] != null){
					descricaoSituacaoEsgoto = (String) parmsOS[3];
				}
				if(parmsOS[4] != null){
					ocorrencia = (String) parmsOS[4];
				}

				informarRetornoOSFiscalizacaoActionForm.setNomeOrdemServico(nomeOSPesquisar);
				informarRetornoOSFiscalizacaoActionForm.setMatriculaImovel("" + idImovel);

				// Inscrição Imóvel
				String inscricaoImovel = fachada.pesquisarInscricaoImovel(idImovel, true);
				informarRetornoOSFiscalizacaoActionForm.setInscricaoImovel(inscricaoImovel);

				// Situação da Ligação de Agua
				informarRetornoOSFiscalizacaoActionForm.setSituacaoLigacaoAgua(descricaoSituacaoAgua);

				// Situação da Ligação de Esgoto
				informarRetornoOSFiscalizacaoActionForm.setSituacaoLigacaoEsgoto(descricaoSituacaoEsgoto);

				// Cliente Usuário
				this.pesquisarCliente(informarRetornoOSFiscalizacaoActionForm);

				// ocorrencia
				informarRetornoOSFiscalizacaoActionForm.setOcorrencia(ocorrencia);

			}else{
				httpServletRequest.setAttribute("ordemServicoEncontrado", "exception");
				informarRetornoOSFiscalizacaoActionForm.setNomeOrdemServico("Ordem de Serviço inexistente");
				informarRetornoOSFiscalizacaoActionForm.setIdOrdemServico("");
			}

		}else{

			informarRetornoOSFiscalizacaoActionForm.setIdOrdemServico("");
			informarRetornoOSFiscalizacaoActionForm.setMatriculaImovel("");
			informarRetornoOSFiscalizacaoActionForm.setInscricaoImovel("");
			informarRetornoOSFiscalizacaoActionForm.setClienteUsuario("");
			informarRetornoOSFiscalizacaoActionForm.setCpfCnpjCliente("");
			informarRetornoOSFiscalizacaoActionForm.setSituacaoLigacaoAgua("");
			informarRetornoOSFiscalizacaoActionForm.setSituacaoLigacaoEsgoto("");
			informarRetornoOSFiscalizacaoActionForm.setNomeOrdemServico("");
			informarRetornoOSFiscalizacaoActionForm.setOcorrencia("");
			informarRetornoOSFiscalizacaoActionForm.setSituacao("");
			informarRetornoOSFiscalizacaoActionForm.setIndicadorTipoMedicao("");
			informarRetornoOSFiscalizacaoActionForm.setIndicadorDocumentoEntregue("");
			informarRetornoOSFiscalizacaoActionForm.setIndicadorGeracaoDebito("");

		}

		// Seta coleção de fiscalizacao situação
		getFiscalizacaoSituacao(sessao);

		// Permissão especial
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		if(fachada.verificarPermissaoGeracaoDebitoOSFiscalizacao(usuarioLogado)){
			httpServletRequest.setAttribute("disponibilizarNaoGeracaoDebito", "OK");
		}

		FiltroAtendimentoMotivoEncerramento filtroAtendimentoMotivoEncerramento = new FiltroAtendimentoMotivoEncerramento();

		filtroAtendimentoMotivoEncerramento.setCampoOrderBy(FiltroAtendimentoMotivoEncerramento.DESCRICAO);

		filtroAtendimentoMotivoEncerramento.adicionarParametro(new ParametroSimples(FiltroAtendimentoMotivoEncerramento.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Verifica se os dados foram informados da tabela existem e joga numa
		// colecao

		Collection<AtendimentoMotivoEncerramento> colecaoAtendimentoMotivoEncerramento = fachada.pesquisar(
						filtroAtendimentoMotivoEncerramento, AtendimentoMotivoEncerramento.class.getName());

		if(colecaoAtendimentoMotivoEncerramento == null || colecaoAtendimentoMotivoEncerramento.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Tabela Atendimento Motivo Encerramento");
		}

		httpServletRequest.setAttribute("colecaoAtendimentoMotivoEncerramento", colecaoAtendimentoMotivoEncerramento);

		informarRetornoOSFiscalizacaoActionForm.setIndicadorEncerramentoOS("1");

		return retorno;
	}

	/**
	 * Pesquisa Cliente
	 * 
	 * @author Rafael Pinto
	 * @date 01/09/2006
	 */
	private void pesquisarCliente(InformarRetornoOSFiscalizacaoActionForm informarRetornoOSFiscalizacaoActionForm){

		// Filtro para carregar o Cliente
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, informarRetornoOSFiscalizacaoActionForm
						.getMatriculaImovel()));

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, ClienteRelacaoTipo.USUARIO));

		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");

		Collection colecaoClienteImovel = Fachada.getInstancia().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

		if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){

			ClienteImovel clienteImovel = (ClienteImovel) colecaoClienteImovel.iterator().next();

			Cliente cliente = clienteImovel.getCliente();

			String documento = "";

			if(cliente.getCpf() != null && !cliente.getCpf().equals("")){
				documento = cliente.getCpfFormatado();
			}else{
				documento = cliente.getCnpjFormatado();
			}
			// Cliente Nome/CPF-CNPJ
			informarRetornoOSFiscalizacaoActionForm.setClienteUsuario(cliente.getNome());
			informarRetornoOSFiscalizacaoActionForm.setCpfCnpjCliente(documento);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}
	}

	/**
	 * Carrega coleção de motivo da não cobrança.
	 * 
	 * @author Leonardo Regis
	 * @date 16/09/2006
	 * @param sessao
	 */
	private void getFiscalizacaoSituacao(HttpSession sessao){

		Fachada fachada = Fachada.getInstancia();

		if(sessao.getAttribute("colecaoFiscalizacaoSituacao") == null || !sessao.getAttribute("colecaoFiscalizacaoSituacao").equals("")){
			FiltroFiscalizacaoSituacao filtroFiscalizacaoSituacao = new FiltroFiscalizacaoSituacao();
			filtroFiscalizacaoSituacao.setCampoOrderBy(FiltroFiscalizacaoSituacao.DESCRICAO);

			Collection colecaoFinal = new ArrayList();

			Collection colecaoServicoNaoCobrancaMotivo = fachada
							.pesquisar(filtroFiscalizacaoSituacao, FiscalizacaoSituacao.class.getName());

			if(colecaoServicoNaoCobrancaMotivo != null && !colecaoServicoNaoCobrancaMotivo.isEmpty()){

				Iterator iteratorColecaoServicoNaoCobrancaMotivo = colecaoServicoNaoCobrancaMotivo.iterator();
				FiscalizacaoSituacao fiscalizacaoSituacao = null;

				FiltroFiscalizacaoSituacaoServicoACobrar filtroFiscalizacaoSituacaoServicoACobrar = null;
				Collection colecaoFiscalizacaoSituacaoServicoACobrar = null;

				SituacaoEncontradaHelper situacaoEncontradaHelper = null;

				while(iteratorColecaoServicoNaoCobrancaMotivo.hasNext()){

					fiscalizacaoSituacao = (FiscalizacaoSituacao) iteratorColecaoServicoNaoCobrancaMotivo.next();

					situacaoEncontradaHelper = new SituacaoEncontradaHelper();
					situacaoEncontradaHelper.setFiscalizacaoSituacao(fiscalizacaoSituacao);

					filtroFiscalizacaoSituacaoServicoACobrar = new FiltroFiscalizacaoSituacaoServicoACobrar();

					filtroFiscalizacaoSituacaoServicoACobrar.adicionarParametro(new ParametroSimples(
									FiltroFiscalizacaoSituacaoServicoACobrar.ID_FISCALIZACAO_SITUACAO, fiscalizacaoSituacao.getId()));

					colecaoFiscalizacaoSituacaoServicoACobrar = fachada.pesquisar(filtroFiscalizacaoSituacaoServicoACobrar,
									FiscalizacaoSituacaoServicoACobrar.class.getName());

					if(colecaoFiscalizacaoSituacaoServicoACobrar != null && !colecaoFiscalizacaoSituacaoServicoACobrar.isEmpty()){

						situacaoEncontradaHelper.setGeracaoDebito(new Short("1"));
					}else{

						situacaoEncontradaHelper.setGeracaoDebito(new Short("2"));
					}

					colecaoFinal.add(situacaoEncontradaHelper);
				}

				sessao.setAttribute("colecaoFiscalizacaoSituacao", colecaoFinal);

			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Fiscalização Situação");
			}
		}
	}
}