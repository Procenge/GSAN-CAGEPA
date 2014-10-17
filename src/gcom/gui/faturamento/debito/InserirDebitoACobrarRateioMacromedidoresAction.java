package gcom.gui.faturamento.debito;

import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.CobrancaForma;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.micromedicao.ParametroMicromedicao;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3143] Inserir Débito a Cobrar de Rateio por Macromedidor.
 * 
 * @author Ado Rocha
 * @date 26/02/2014
 */
public class InserirDebitoACobrarRateioMacromedidoresAction
				extends GcomAction {


	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InserirDebitoACobrarRateioMacromedidoresActionForm form = (InserirDebitoACobrarRateioMacromedidoresActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);
		
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		Integer numeroPrestacoes = 1;
		BigDecimal valorTotalServico = Util.formatarMoedaRealparaBigDecimal(form.getValorDebitoImovel());
		BigDecimal valorEntrada = Util.formatarMoedaRealparaBigDecimal("0");
		BigDecimal percentualAbatimento = Util.formatarMoedaRealparaBigDecimal("0");

		// [FS0003] - Validar mês e ano de referência
		if(!Util.validarMesAno(form.getMesAnoReferenciaFaturamento())){
			throw new ActionServletException("atencao.ano_mes.invalido");
		}
		
		Integer anoMesCobrancaDebito = Integer.valueOf(Util.formatarMesAnoParaAnoMes(form.getMesAnoReferenciaFaturamento()));

		// [FS0004] - Verifica ano e mês do início da cobrança
		FiltroImovel filtroImovelCondominio = new FiltroImovel();
		filtroImovelCondominio.adicionarParametro(new ParametroSimples(FiltroImovel.ID, form.getMatriculaImovel()));
		filtroImovelCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
		filtroImovelCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
		filtroImovelCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);
		filtroImovelCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA_FATURAMENTO_GRUPO);
		Collection colecaoImovelCondominio = (Collection) fachada.pesquisar(filtroImovelCondominio, Imovel.class.getName());
		Imovel imovelCondominio = (Imovel) Util.retonarObjetoDeColecao(colecaoImovelCondominio);

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ID, imovelCondominio.getRota()
						.getFaturamentoGrupo().getId()));
		Collection colecaoFaturamentoGrupo = (Collection) fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());
		FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) Util.retonarObjetoDeColecao(colecaoFaturamentoGrupo);

		Integer anoMesFaturamentoGrupo = faturamentoGrupo.getAnoMesReferencia();
		boolean verificaAnoMesCobranca = Util.compararAnoMesReferencia(anoMesCobrancaDebito, anoMesFaturamentoGrupo, "<");


		if(anoMesFaturamentoGrupo.equals(anoMesCobrancaDebito) || verificaAnoMesCobranca){
			throw new ActionServletException("atencao.ano_mes_imovel_condominio.referencia.superior");
		}

		// Obtém os ids de inserção
		String[] ids = form.getIdImoveisCondominio();
		
		String parametroDebitoTipoRateio = "";

		try{
			parametroDebitoTipoRateio = (String) ParametroMicromedicao.P_DEBITO_TIPO_RATEIO.executar(0);
		}catch(ControladorException e){
			e.printStackTrace();
		}

		FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
		filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, new Integer(parametroDebitoTipoRateio)));
		Collection colecaoDebitoTipo = (Collection) fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());
		DebitoTipo debitoTipo = (DebitoTipo) Util.retonarObjetoDeColecao(colecaoDebitoTipo);
		
		CobrancaForma cobrancaForma = new CobrancaForma();
		cobrancaForma.setId(CobrancaForma.COBRANCA_EM_CONTA);

		FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();
		filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, form.getIdRegistroAtendimento()));
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.ATENDIMENTO_MOTIVO_ENCERRAMENTO);
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.SOLICITACAO_TIPO_ESPECIFICACAO);
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.IMOVEL);

		Collection colecaoRegistroAtendimento = fachada.pesquisar(filtroRegistroAtendimento, RegistroAtendimento.class.getName());

		RegistroAtendimento registroAtendimento = (RegistroAtendimento) colecaoRegistroAtendimento.iterator().next();

		fachada.inserirDebitoACobrarRateioCondominio(registroAtendimento, usuarioLogado, ids, numeroPrestacoes, valorTotalServico,
						valorEntrada,
						percentualAbatimento,
 anoMesCobrancaDebito, debitoTipo, cobrancaForma, parametroDebitoTipoRateio);


		// Monta a página de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			montarPaginaSucesso(httpServletRequest, ids.length + " Débito(s) a Cobrar foi(ram) inserido(s) com sucesso.",
							"Realizar outra Inserção de Débito a Cobrar Rateio",
							"exibirInserirDebitoACobrarRateioMacromedidoresAction.do?menu=sim");
		}

		return retorno;

	}

}
