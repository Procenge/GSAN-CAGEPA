
package gcom.gui.cobranca;

import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.bean.AcompanhamentoExecucaoServicoCobrancaRelatorioHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.micromedicao.Rota;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.RelatorioAcompanhamentoExecucaoServicoCobranca;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author isilva
 */
public class GerarRelatorioAcompanhamentoExecucaoServicoCobrancaAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws Exception{

		ActionForward retorno = null;
		int tempoSessao = httpServletRequest.getSession().getMaxInactiveInterval();

		try{

			httpServletRequest.getSession().setMaxInactiveInterval(-1);

			GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm form = (GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm) actionForm;

			this.validarFormulario(form);

			Integer comandoTipo = null;
			if(form.getComando() != null && !form.getComando().equals("")){
				comandoTipo = Integer.valueOf(form.getComando());
			}
			Integer comandoCronograma = null;
			if(form.getIdCobrancaAcaoAtividadeCronograma() != null && !form.getIdCobrancaAcaoAtividadeCronograma().equals("")){
				comandoCronograma = Integer.valueOf(form.getIdCobrancaAcaoAtividadeCronograma());
			}
			Integer comandoEventual = null;
			if(form.getIdCobrancaAcaoAtividadeComando() != null && !form.getIdCobrancaAcaoAtividadeComando().equals("")){
				comandoEventual = Integer.valueOf(form.getIdCobrancaAcaoAtividadeComando());
			}
			Integer cobrancaAcao = null;
			if(form.getAcao() != null && !form.getAcao().equals("")){
				cobrancaAcao = Integer.valueOf(form.getAcao());
			}
			Date periodoInicial = null;
			if(form.getPeriodoInicial() != null && !form.getPeriodoInicial().equals("")){
				periodoInicial = Util.converteStringParaDate(form.getPeriodoInicial());
			}
			Date periodoFinal = null;
			if(form.getPeriodoFim() != null && !form.getPeriodoFim().equals("")){
				periodoFinal = Util.converteStringParaDate(form.getPeriodoFim());
			}
			Integer situacao = null;
			if(form.getSituacao() != null && !form.getSituacao().equals("")){
				situacao = Integer.valueOf(form.getSituacao());
			}
			Integer religado = null;
			if(form.getReligado() != null && !form.getReligado().equals("")){
				religado = Integer.valueOf(form.getReligado());
			}
			Integer servico = null;
			if(form.getServico() != null && !form.getServico().equals("")){
				servico = Integer.valueOf(form.getServico());
			}
			Integer localidade = null;
			if(form.getLocalidade() != null && !form.getLocalidade().equals("")){
				localidade = Integer.valueOf(form.getLocalidade());
			}
			Integer grupo = null;
			if(form.getGrupo() != null && !form.getGrupo().equals("")){
				grupo = Integer.valueOf(form.getGrupo());
			}
			File template = new File(httpServletRequest.getRealPath("")
							+ "\\xls\\relatorioAcompanhamentoExecucaoServicoCobrancaTemplate.xls");

			Collection colecaoRetorno = Fachada.getInstancia().filtrarAcompanhamentoExecucaoServicoCobranca(comandoTipo, comandoCronograma,
							comandoEventual, cobrancaAcao, periodoInicial, periodoFinal, situacao, religado, servico, localidade, grupo,
							form.getBairro(), form.getSetorComercial());
			colecaoRetorno = this.montaHelp(colecaoRetorno);

			if(colecaoRetorno != null && !colecaoRetorno.isEmpty()){
				RelatorioAcompanhamentoExecucaoServicoCobranca relatorio = new RelatorioAcompanhamentoExecucaoServicoCobranca(
								(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

				if(!Util.isVazioOuBranco(form.getPeriodoInicial()) && !Util.isVazioOuBranco(form.getPeriodoFim())){
					relatorio.addParametro("periodoInicial", form.getPeriodoInicial());
					relatorio.addParametro("periodoFim", form.getPeriodoFim());
				}else{
					relatorio.addParametro("periodoInicial", "");
					relatorio.addParametro("periodoFim", "");
				}

				relatorio.addParametro("dataGeracao", Util.formatarData(new Date()));

				relatorio.addParametro("colecaoRetorno", colecaoRetorno);

				relatorio.addParametro("tipoFormatoRelatorio", Integer.valueOf(TarefaRelatorio.TIPO_XLS));

				relatorio.addParametro("template", template);
				try{
					retorno = processarExibicaoRelatorio(relatorio, TarefaRelatorio.TIPO_XLS, httpServletRequest, httpServletResponse,
									actionMapping);
				}catch(TarefaException e){
					throw new ActionServletException("atencao.planilha_template_nao_encontrado");
				}
			}else{
				throw new ActionServletException("atencao.pesquisa.nenhumresultado");
			}

		}catch(SistemaException ex){
			reportarErros(httpServletRequest, "erro.sistema");
			retorno = actionMapping.findForward("telaErroPopup");
		}catch(RelatorioVazioException ex1){
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}finally{

			httpServletRequest.getSession().setMaxInactiveInterval(tempoSessao);

		}

		return retorno;

	}

	@SuppressWarnings("unchecked")
	private Collection montaHelp(Collection retorno){

		Collection<AcompanhamentoExecucaoServicoCobrancaRelatorioHelper> colecaoAcompanhamentoExecucaoServicoCobrancaRelatorioHelper = new ArrayList<AcompanhamentoExecucaoServicoCobrancaRelatorioHelper>();

		AcompanhamentoExecucaoServicoCobrancaRelatorioHelper helper = null;

		for(Object objeto : retorno){
			helper = new AcompanhamentoExecucaoServicoCobrancaRelatorioHelper();
			CobrancaDocumento cobrancaDocumento = (CobrancaDocumento) objeto;
			OrdemServico ordemServico = cobrancaDocumento.getOrdensServico().iterator().next();
			Imovel imovel = cobrancaDocumento.getImovel();
			LigacaoAgua ligacaoAgua = imovel.getLigacaoAgua();
			Cliente cliente = imovel.getClienteImoveis().iterator().next().getCliente();
			Rota rota = imovel.getRota();

			helper.setIdImovel(imovel.getId());
			helper.setDataEmissaoCorte(ordemServico.getDataEmissao());
			helper.setDataExecucao(ordemServico.getDataExecucao());
			helper.setQuantidadeDebitosCobrados(Fachada.getInstancia().consultarQtdeDocumentosItensPorCobrancaDocumento(cobrancaDocumento));
			helper.setValor(cobrancaDocumento.getValorDocumento());
			helper.setNumeroInscricao(imovel.getInscricaoFormatada());
			helper.setNomeCliente(cliente.getNome());
			helper.setGrupo(rota.getCobrancaGrupo().getDescricao());
			helper.setEndereco(imovel.getEnderecoFormatado());
			helper.setBairro(imovel.getLogradouroBairro().getBairro().getNome());
			if(cliente.getClienteFones() != null && !cliente.getClienteFones().isEmpty()){
				helper.setTelefoneResidencial(((ClienteFone) cliente.getClienteFones().iterator().next()).getDddTelefone());
			}
			helper.setCpf(cliente.getCpfFormatado());
			helper.setRg(cliente.getRg());
			helper.setGrandeCliente(imovel.getImovelPerfil().getId().intValue() == ImovelPerfil.GRANDE.intValue() ? "SIM" : "NÃO");
			if(ligacaoAgua != null){
				helper.setDataEmissaoReligacao(ligacaoAgua.getDataReligacao());
				if(ligacaoAgua.getCorteTipo() != null){
					helper.setTipoCorte(ligacaoAgua.getCorteTipo().getDescricao());
				}else if(ligacaoAgua.getSupressaoTipo() != null){
					helper.setTipoSupressao(ligacaoAgua.getSupressaoTipo().getDescricao());
				}
				if(ligacaoAgua.getHidrometroInstalacaoHistorico() == null){
					helper.setTipoLigacao("ESTIMADO");
					helper.setNumeroHidrometro("");
				}else{
					helper.setTipoLigacao("HIDROMETRADO");
					helper.setNumeroHidrometro(ligacaoAgua.getHidrometroInstalacaoHistorico().getNumeroHidrometro());
				}
			}
			helper.setObservacao(ordemServico.getObservacao());

			colecaoAcompanhamentoExecucaoServicoCobrancaRelatorioHelper.add(helper);
		}

		return colecaoAcompanhamentoExecucaoServicoCobrancaRelatorioHelper;
	}

	/**
	 * @author isilva
	 * @param form
	 */
	private void validarFormulario(GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm form){

		if(form.getComando() == null || form.getComando().equalsIgnoreCase("") || form.getComando().length() < 1){
			throw new ActionServletException("atencao.informe_campo", null, "Tipo de Comando");
		}

		// Ambos
		if(form.getComando().equalsIgnoreCase("3")){
			this.validarDatas(form);
			form.setIdCobrancaAcaoAtividadeComando("");
			form.setIdCobrancaAcaoAtividadeCronograma("");
		}else if(form.getComando().equalsIgnoreCase("1")){

			if(Util.isVazioOuBranco(form.getIdCobrancaAcaoAtividadeCronograma())){
				throw new ActionServletException("atencao.informe_campo", null, "Comando Cronograma");
			}

		}else if(form.getComando().equalsIgnoreCase("2")){

			if(Util.isVazioOuBranco(form.getIdCobrancaAcaoAtividadeComando())){
				throw new ActionServletException("atencao.informe_campo", null, "Título do Comando");
			}
		}
		if(form.getGrupo() != null && !form.getGrupo().equals("")){
			this.validarSetorEBairro(form);
		}
	}

	/**
	 * @author isilva
	 * @param form
	 */
	private void validarSetorEBairro(GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm form){

		if(!isNullOuVazioSetorComercial(form.getSetorComercial()) && !isNullOuVazioBairros(form.getBairro())){
			throw new ActionServletException("atencao.so.pode.ser.informado.setores.ou.bairros");
		}
	}

	/**
	 * @author isilva
	 * @param strings
	 * @return
	 */
	private boolean isNullOuVazioSetorComercial(String[] setorComercial){

		boolean retorno = false;

		if(setorComercial != null && setorComercial.length >= 1){
			retorno = true;
		}

		return retorno;
	}

	/**
	 * @author isilva
	 * @param bairros
	 * @return
	 */
	private boolean isNullOuVazioBairros(String[] bairros){

		boolean retorno = false;

		if(bairros != null && bairros.length >= 1){
			retorno = true;
		}

		return retorno;
	}

	/**
	 * @author isilva
	 * @param form
	 */
	private void validarDatas(GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm form){

		if(Util.isVazioOuBranco(form.getPeriodoInicial()) && Util.isVazioOuBranco(form.getPeriodoInicial())){
			throw new ActionServletException("atencao.informe_campo", null, "Período");
		}

		Date periodoInicio = null;
		Date periodoFim = null;

		// Data Inicial
		if(Util.isVazioOuBranco(form.getPeriodoInicial())){
			throw new ActionServletException("atencao.required", null, "Período Inicial");
		}else{

			try{
				periodoInicio = Util.converteStringParaDate(form.getPeriodoInicial());
			}catch(Exception e){
				throw new ActionServletException("atencao.campo.invalido", null, "Período Inicial");
			}

		}

		// Data Final
		if(Util.isVazioOuBranco(form.getPeriodoFim())){
			throw new ActionServletException("atencao.required", null, "Período Final");
		}else{

			try{
				periodoFim = Util.converteStringParaDate(form.getPeriodoFim());
			}catch(Exception e){
				throw new ActionServletException("atencao.campo.invalido", null, "Período Final");
			}

		}

		// Se data inicio maior que data fim
		if(Util.compararData(periodoInicio, periodoFim) == 1){
			throw new ActionServletException("atencao.data_inicial_maior_data_final", form.getPeriodoInicial(), form.getPeriodoFim());
		}
	}
}
