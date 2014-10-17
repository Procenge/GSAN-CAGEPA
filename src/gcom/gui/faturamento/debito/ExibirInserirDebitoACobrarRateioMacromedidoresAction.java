package gcom.gui.faturamento.debito;

import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.ClienteImovelCondominioHelper;
import gcom.faturamento.debito.ClienteUsuarioImovelCondominioHelper;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroLigacaoTipo;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.parametrosistema.api.ParametroSistema;

/**
 * [UC3143] Inserir Débito a Cobrar de Rateio por Macromedidor.
 * 
 * @author Ado Rocha
 * @date 26/02/2014
 */

public class ExibirInserirDebitoACobrarRateioMacromedidoresAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirDebitoACobrarRateioMacromedidores");

		Fachada fachada = Fachada.getInstancia();

		InserirDebitoACobrarRateioMacromedidoresActionForm form = (InserirDebitoACobrarRateioMacromedidoresActionForm) actionForm;

		String idImovel = null;
		String idRegistroAtendimento = form.getIdRegistroAtendimento();
		String anoMesReferenciaFaturamento = "-1";
		String tipoDebitosRateio = "";

		if(form.getMesAnoReferenciaFaturamento() != null && !form.getMesAnoReferenciaFaturamento().equals("")){
			anoMesReferenciaFaturamento = Util.formatarMesAnoParaAnoMes(form.getMesAnoReferenciaFaturamento());
		}

		// Tipo Ligacao
		FiltroLigacaoTipo filtroLigacaoTipo = new FiltroLigacaoTipo();
		filtroLigacaoTipo.adicionarParametro(new ParametroSimples(FiltroLigacaoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection<LigacaoTipo> colecaoLigacaoTipo = fachada.pesquisar(filtroLigacaoTipo, LigacaoTipo.class.getName());

		httpServletRequest.setAttribute("colecaoLigacaoTipo", colecaoLigacaoTipo);

		// Débito Tipo
		try{
			tipoDebitosRateio = ((ParametroSistema) Fachada.getInstancia().obterParametroSistema("P_DEBITO_TIPO_RATEIO")).getValor();
		}catch(Exception e){
			throw new ActionServletException("erro.sistema");
		}
		FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
		filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, Integer.valueOf(tipoDebitosRateio)));
		Collection<DebitoTipo> colecaoDebitoTipo = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());
		DebitoTipo debitoTipoBase = (DebitoTipo) Util.retonarObjetoDeColecao(colecaoDebitoTipo);

		form.setTipoDebitosRateio(debitoTipoBase.getDescricaoFormatada());


		form.setIdTipoLigacao(LigacaoTipo.LIGACAO_AGUA.toString());
		form.setNumeroPrestacoes("1");


		if(httpServletRequest.getParameter("objetoConsulta") != null
						&& httpServletRequest.getParameter("objetoConsulta").equalsIgnoreCase("1")){

			// pesquisa o imovel pelo registro de atendimento
			if(idRegistroAtendimento != null && !idRegistroAtendimento.trim().equals("")){

				FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();
				filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, idRegistroAtendimento));
				filtroRegistroAtendimento
								.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.ATENDIMENTO_MOTIVO_ENCERRAMENTO);
				filtroRegistroAtendimento
								.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.SOLICITACAO_TIPO_ESPECIFICACAO);
				filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.IMOVEL);

				Collection colecaoRegistroAtendimento = fachada.pesquisar(filtroRegistroAtendimento, RegistroAtendimento.class.getName());

				if(colecaoRegistroAtendimento != null && !colecaoRegistroAtendimento.isEmpty()){

					RegistroAtendimento registroAtendimento = (RegistroAtendimento) colecaoRegistroAtendimento.iterator().next();

					// Registro de Atendimento está encerrado
					if(registroAtendimento.getAtendimentoMotivoEncerramento() != null){
						// FS0001 - Validar Registro de Atendimento
						throw new ActionServletException("atencao.registro_atendimento.esta.encerrado");
					}

					// Especificação do Tipo de Solicitação do Registro de Atendimento não
					// permite cobrança de débito
					if(registroAtendimento.getSolicitacaoTipoEspecificacao().getIndicadorGeracaoDebito() == 2){
						// FS0001 - Validar Registro de Atendimento
						throw new ActionServletException("atencao.registro_atendimento.nao.permite.cobranca.debito");
					}

					// FS0002 - Verificar se o imóvel do RA é um condomínio
					if(registroAtendimento.getImovel() == null){
						throw new ActionServletException("atencao.registro_atendimento.nao.associado.imovel");
					}

					if(registroAtendimento.getImovel().getIndicadorImovelCondominio().equals(ConstantesSistema.NAO)){
						throw new ActionServletException("atencao.imovel.nao_condominio");
					}

					// caso tenha o imovel
					idImovel = registroAtendimento.getImovel().getId().toString();

					FiltroImovel filtroImovel = new FiltroImovel();
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA_SETOR_COMERCIAL_LOCALIDADE);


					Collection colecaoImovel = (Collection) fachada.pesquisar(filtroImovel, Imovel.class.getName());
					
					Imovel imovelBase = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

					form.setMatriculaImovel(imovelBase.getId().toString());
					form.setInscricaoImovel(imovelBase.getInscricaoFormatada());
					form.setDescricaoLigacaoAguaSituacao(imovelBase.getLigacaoAguaSituacao().getDescricao());
					form.setDescricaoLigacaoEsgotoSituacao(imovelBase.getLigacaoEsgotoSituacao().getDescricao());

					List<ClienteImovelCondominioHelper> listaClienteImovelCondominio;
					if(Util.isNaoNuloBrancoZero(form.getMatriculaImovel())){
						listaClienteImovelCondominio = fachada.pesquisarClienteImovelCondominioHelper(Integer.valueOf(idImovel),
										Integer.valueOf(form.getIdTipoLigacao()), Integer.valueOf(anoMesReferenciaFaturamento));
						httpServletRequest.setAttribute("listaClienteImovelCondominio", listaClienteImovelCondominio);

						try{
							String enderecoImovel = fachada.pesquisarEnderecoFormatado(Integer.valueOf(idImovel));
							form.setEnderecoImovel(enderecoImovel);
						}catch(ControladorException e){
							throw new ActionServletException("atencao.imovel_endereco.nao_cadastrado");
						}

						FiltroImovel filtroImoveisCondominio = new FiltroImovel();
						filtroImoveisCondominio.adicionarParametro(new ParametroSimples(FiltroImovel.IMOVEL_CONDOMINIO_ID, Integer
										.valueOf(idImovel)));
						filtroImoveisCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
						filtroImoveisCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);

						Collection colecaoImoveisCondominio = (Collection) fachada.pesquisar(filtroImoveisCondominio,
										Imovel.class.getName());

						this.pesquisarImoveisCondominio(colecaoImoveisCondominio, httpServletRequest);


					form.setIdRegistroAtendimento(registroAtendimento.getId().toString());
					form.setNomeRegistroAtendimento(registroAtendimento.getSolicitacaoTipoEspecificacao()
									.getDescricao());

					}
				}else{

					form.setIdRegistroAtendimento("");
					form.setNomeRegistroAtendimento("RA inexistente");
					httpServletRequest.setAttribute("idRegistroAtendimentoNaoEncontrado", "true");

				}
			}	
		}
		return retorno;
	}

	/**
	 * Pesquisar Imóveis vinculados ao Imóvel Condomínio
	 * 
	 * @author Ado Rocha
	 * @date 28/02/2014
	 */
	private void pesquisarImoveisCondominio(Collection colecaoImoveisCondominio, HttpServletRequest httpServletRequest){

		List<ClienteUsuarioImovelCondominioHelper> colecaoHelper = new ArrayList<ClienteUsuarioImovelCondominioHelper>();
		ClienteUsuarioImovelCondominioHelper clienteUsuarioImovelCondominioHelper = null;
		
		Iterator imoveisCondominioIterator = colecaoImoveisCondominio.iterator();
	      while(imoveisCondominioIterator.hasNext()) {
			Imovel imovel = (Imovel) imoveisCondominioIterator.next();
	         
			clienteUsuarioImovelCondominioHelper = new ClienteUsuarioImovelCondominioHelper();

			clienteUsuarioImovelCondominioHelper.setIdImovel(imovel.getId());
			clienteUsuarioImovelCondominioHelper.setNomeCliente(Fachada.getInstancia().consultarClienteUsuarioImovel(imovel.getId()));
			clienteUsuarioImovelCondominioHelper.setDescricaoLigacaoAguaSituacao(imovel.getLigacaoAguaSituacao().getDescricao());
			clienteUsuarioImovelCondominioHelper.setDescricaoLigacaoEsgotoSituacao(imovel.getLigacaoEsgotoSituacao().getDescricao());
	         
			colecaoHelper.add(clienteUsuarioImovelCondominioHelper);
	      }

		httpServletRequest.setAttribute("colecaoClienteUsuarioImovelCondominioHelper", colecaoHelper);
	}

}
