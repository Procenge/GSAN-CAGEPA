/**
 * 
 */

package gcom.gui.cadastro.aguaparatodos;

import gcom.cadastro.aguaparatodos.FiltroImovelAguaParaTodos;
import gcom.cadastro.aguaparatodos.ImovelAguaParaTodos;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoAtividadeCronograma;
import gcom.faturamento.FiltroFaturamentoAtividadeCronograma;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Ferreira dos Santos
 */
public class ManterImovelAguaParaTodosAction
				extends GcomAction {

	/**
	 * @author Bruno Ferreira dos Santos
	 * @date 26/01/2011
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		ManterImovelAguaParaTodosActionForm form = (ManterImovelAguaParaTodosActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		boolean reativacao = false;

		FiltroImovelAguaParaTodos filtroImovelAguaParaTodos = new FiltroImovelAguaParaTodos();

		filtroImovelAguaParaTodos.adicionarCaminhoParaCarregamentoEntidade("idImovel");

		filtroImovelAguaParaTodos.adicionarParametro(new ParametroSimples(FiltroImovelAguaParaTodos.ID_IMOVEL, form.getMatricula()));

		if(form.getFlagOperacao().equals(ManterImovelAguaParaTodosActionForm.HABILITAR)){
			filtroImovelAguaParaTodos.adicionarParametro(new ParametroSimples(FiltroImovelAguaParaTodos.CODIGO_SITUACAO,
							ImovelAguaParaTodos.CADASTRADO));
		}else if(form.getFlagOperacao().equals(ManterImovelAguaParaTodosActionForm.INSERIR)){
			FiltroImovelAguaParaTodos filtroImovelAguaParaTodosNic = new FiltroImovelAguaParaTodos();

			filtroImovelAguaParaTodosNic.adicionarCaminhoParaCarregamentoEntidade("idImovel");

			filtroImovelAguaParaTodosNic.adicionarParametro(new ParametroSimples(FiltroImovelAguaParaTodos.ID_CONTRIBUINTE, Long
							.valueOf(form.getNic())));
			Collection colNic = fachada.pesquisar(filtroImovelAguaParaTodosNic, ImovelAguaParaTodos.class.getName());
			if(!colNic.isEmpty()){
				boolean haveNic = false;
				Iterator it = colNic.iterator();
				while(it.hasNext()){
					ImovelAguaParaTodos imovelAguaParaTodosNic = (ImovelAguaParaTodos) it.next();
					if(imovelAguaParaTodosNic.getCodigoSituacao() != null
									&& !imovelAguaParaTodosNic.getCodigoSituacao().equals(ImovelAguaParaTodos.EXCLUIDO)){
						haveNic = true;
					}else if(imovelAguaParaTodosNic.getCodigoSituacao() != null
									&& imovelAguaParaTodosNic.getCodigoSituacao().equals(ImovelAguaParaTodos.EXCLUIDO)
									&& imovelAguaParaTodosNic.getId().equals(Integer.valueOf(form.getMatricula()))){
						reativacao = true;
					}
				}
				if(haveNic){
					throw new ActionServletException("atencao.agua.para.todos.nic.participante", null, form.getNic());
				}
			}
		}else{
			if(form.getNic() != null && !form.getNic().equals("")){
				filtroImovelAguaParaTodos
								.adicionarParametro(new ParametroSimples(FiltroImovelAguaParaTodos.ID_CONTRIBUINTE, form.getNic()));
			}
			if(form.getId() != null && !form.getId().equals("")){
				filtroImovelAguaParaTodos.adicionarParametro(new ParametroSimples(FiltroImovelAguaParaTodos.ID, form.getId()));
			}
		}

		ImovelAguaParaTodos imovelAguaParaTodos = null;

		Collection colImovelAguaParaTodos = fachada.pesquisar(filtroImovelAguaParaTodos, ImovelAguaParaTodos.class.getName());

		Iterator it = colImovelAguaParaTodos.iterator();
		if(it.hasNext()){
			imovelAguaParaTodos = (ImovelAguaParaTodos) it.next();
		}

		if(imovelAguaParaTodos == null){
			imovelAguaParaTodos = new ImovelAguaParaTodos();
		}

		Imovel imovel = fachada.pesquisarImovel(Integer.valueOf(form.getMatricula()));

		try{
			imovelAguaParaTodos.setIdImovel(imovel);
		}catch(NumberFormatException e){
			throw new ActionServletException("atencao.nao.numerico", null, "Mat.: " + form.getMatricula());
		}
		try{
			imovelAguaParaTodos.setIdContribuinte(Long.valueOf(form.getNic()));
		}catch(NumberFormatException e){
			throw new ActionServletException("atencao.nao.numerico", null, "Nic: " + form.getNic());
		}
		imovelAguaParaTodos.setNomeParticipante(form.getNomeContribuinte().toUpperCase());

		if(form.getFlagOperacao().equals(ManterImovelAguaParaTodosActionForm.INSERIR)){
			imovelAguaParaTodos.setIdUsuarioInclusao(usuarioLogado);
			imovelAguaParaTodos.setIdUsuarioExclusao(null);
			imovelAguaParaTodos.setDataCadastramento(new Date());
			imovelAguaParaTodos.setDataHabilitacao(null);
			imovelAguaParaTodos.setDataExclusao(null);
			imovelAguaParaTodos.setAnoMesReferenciaFinal(null);
			imovelAguaParaTodos.setAnoMesReferenciaInicial(null);
			imovelAguaParaTodos.setCodigoSituacao(ImovelAguaParaTodos.CADASTRADO);
			imovelAguaParaTodos.setMotivoExclusao(null);
			imovelAguaParaTodos.setUltimaAlteracao(null);
			if(reativacao){
				fachada.atualizarImovelAguaParaTodos(imovelAguaParaTodos, imovel, usuarioLogado);
			}else{
				fachada.inserirImovelAguaParaTodos(imovelAguaParaTodos, usuarioLogado);
			}
		}else if(form.getFlagOperacao().equals(ManterImovelAguaParaTodosActionForm.HABILITAR)){

			fachada.habilitarImovelAguaParaTodos(imovelAguaParaTodos, imovel, usuarioLogado);

		}else if(form.getFlagOperacao().equals(ManterImovelAguaParaTodosActionForm.EXCLUIR)){
			imovelAguaParaTodos.setUltimaAlteracao(new Date());
			imovelAguaParaTodos.setIdUsuarioExclusao(usuarioLogado);
			imovelAguaParaTodos.setDataExclusao(new Date());
			if(imovelAguaParaTodos.getCodigoSituacao().equals(ImovelAguaParaTodos.CADASTRADO)){
				imovelAguaParaTodos.setAnoMesReferenciaFinal(null);
				imovelAguaParaTodos.setCodigoSituacao(ImovelAguaParaTodos.EXCLUIDO);
				imovelAguaParaTodos.setAnoMesReferenciaInicial(null);
				imovelAguaParaTodos.setIdImovel(imovel);
				fachada.remover(imovelAguaParaTodos);

			}else{

				SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, Integer.valueOf(form.getMatricula())));
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("rota.faturamentoGrupo");

				Imovel imovelAnoMesRef = (Imovel) fachada.pesquisar(filtroImovel, Imovel.class.getName()).iterator().next();

				FiltroFaturamentoAtividadeCronograma filtroFatAtvCron = new FiltroFaturamentoAtividadeCronograma();
				filtroFatAtvCron.adicionarParametro(new ParametroSimples(FiltroFaturamentoAtividadeCronograma.FATURAMENTO_ATIVIDADE_ID,
								FaturamentoAtividade.FATURAR_GRUPO));
				filtroFatAtvCron.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_ANO_MES_REFERENCIA,
								sistemaParametro.getAnoMesFaturamento()));
				filtroFatAtvCron.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_FATURAMENTO_GRUPO_ID,
								imovelAnoMesRef.getRota().getFaturamentoGrupo().getId()));

				Collection<FaturamentoAtividadeCronograma> colecaoFaturamentoAtividadeCronograma = fachada.pesquisar(filtroFatAtvCron,
								FaturamentoAtividadeCronograma.class.getName());

				FaturamentoAtividadeCronograma faturamentoAtividadeCronograma = null;
				if(colecaoFaturamentoAtividadeCronograma != null && !colecaoFaturamentoAtividadeCronograma.isEmpty()){
					faturamentoAtividadeCronograma = colecaoFaturamentoAtividadeCronograma.iterator().next();
				}

				Integer anoMesFinal = sistemaParametro.getAnoMesFaturamento();
				if(faturamentoAtividadeCronograma != null){
					if(faturamentoAtividadeCronograma.getDataRealizacao() != null){
						String ano = sistemaParametro.getAnoMesFaturamento().toString().substring(0, 4);
						String mes = sistemaParametro.getAnoMesFaturamento().toString().substring(4);
						anoMesFinal = sistemaParametro.getAnoMesFaturamento() - 1;
						if(mes.equals("01")){
							anoMesFinal = new Integer(ano) - 1;
							String aux = anoMesFinal.toString() + "12";
							anoMesFinal = new Integer(aux);
						}
					}
				}

				imovelAguaParaTodos.setAnoMesReferenciaFinal(anoMesFinal);
				imovel.setImovelAguaParaTodos(null);
				imovel.setConsumoTarifaTemporaria(null);
				imovel.setDataValidadeTarifaTemporaria(null);
				imovelAguaParaTodos.setCodigoSituacao(ImovelAguaParaTodos.EXCLUIDO);
				if(form.getMotivoExclusao() == null){
					imovelAguaParaTodos.setMotivoExclusao(null);
				}else{
					imovelAguaParaTodos.setMotivoExclusao(new Integer(form.getMotivoExclusao()));
				}
				form.setMotivoExclusao(null);
				fachada.atualizarImovelAguaParaTodos(imovelAguaParaTodos, imovel, usuarioLogado);
			}

		}else if(form.getFlagOperacao().equals(ManterImovelAguaParaTodosActionForm.RENOVAR)){
			imovelAguaParaTodos.setDataRenovacao(new Date());
			imovelAguaParaTodos.setIdUsuarioRenovacao(usuarioLogado);
			imovelAguaParaTodos.setCodigoSituacao(ImovelAguaParaTodos.HABILITADO);

			imovel.setDataValidadeTarifaTemporaria(Util.adicionarNumeroMesDeUmaData(new Date(), 12));

			fachada.atualizarImovelAguaParaTodos(imovelAguaParaTodos, imovel, usuarioLogado);
		}

		// Monta a página de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			String mensagem = "";
			if(form.getFlagOperacao().equals(ManterImovelAguaParaTodosActionForm.INSERIR)){
				mensagem = "Matrícula do Imóvel " + form.getMatricula() + " inserido com sucesso.";
				form.setMatricula("");
			}else if(form.getFlagOperacao().equals(ManterImovelAguaParaTodosActionForm.HABILITAR)){
				mensagem = "Matrícula do Imóvel " + form.getMatricula() + " habilitado com sucesso.";
			}else if(form.getFlagOperacao().equals(ManterImovelAguaParaTodosActionForm.EXCLUIR)){
				mensagem = "Matrícula do Imóvel " + form.getMatricula() + " excluido do Programa Água Para Todos.";
			}else if(form.getFlagOperacao().equals(ManterImovelAguaParaTodosActionForm.RENOVAR)){
				mensagem = "Matrícula do Imóvel " + form.getMatricula() + " renovado no Programa Água Para Todos.";
			}
			montarPaginaSucesso(httpServletRequest, mensagem, "Inserir outro Imóvel",
							"exibirManterImovelAguaParaTodosAction.do?tela=inserir",
							"exibirManterImovelAguaParaTodosAction.do?matriculaImovel=" + form.getMatricula(), "Atualizar Imóvel Atual",
							"Atualizar Imóvel", "exibirManterImovelAguaParaTodosAction.do?menu=sim");
		}

		return retorno;
	}
}
