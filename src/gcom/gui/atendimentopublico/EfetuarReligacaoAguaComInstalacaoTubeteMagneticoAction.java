
package gcom.gui.atendimentopublico;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EfetuarReligacaoAguaComInstalacaoTubeteMagneticoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// // Obtém a instância da fachada
		// Fachada fachada = Fachada.getInstancia();
		//
		// HttpSession sessao = httpServletRequest.getSession(false);
		// EfetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm
		// efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm =
		// (EfetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm) actionForm;
		//
		// // Usuario logado no sistema
		// Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// OrdemServico ordemServico = null;
		// /*
		// * [UC0107] Registrar Transação
		// */
		//
		// RegistradorOperacao registradorOperacao = new RegistradorOperacao(
		// Operacao.OPERACAO_EFETUAR_LIGACAO_AGUA_COM_INSTALACAO_TUBETE_MAGNETICO, new
		// UsuarioAcaoUsuarioHelper(usuario,
		// UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
		//
		// Operacao operacao = new Operacao();
		// operacao.setId(Operacao.OPERACAO_EFETUAR_LIGACAO_AGUA_COM_INSTALACAO_TUBETE_MAGNETICO);
		//
		// OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		// operacaoEfetuada.setOperacao(operacao);
		//
		// Boolean veioEncerrarOS = null;
		// if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
		// veioEncerrarOS = Boolean.TRUE;
		// }else{
		// veioEncerrarOS = Boolean.FALSE;
		// }
		//
		// if(sessao.getAttribute("ordemServico") == null){
		// String idOrdemServico = null;
		//
		// if(efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getIdOrdemServico() !=
		// null){
		// idOrdemServico =
		// efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getIdOrdemServico();
		// }else{
		// idOrdemServico = (String) httpServletRequest.getAttribute("veioEncerrarOS");
		//
		// sessao.setAttribute("caminhoRetornoIntegracaoComercial", httpServletRequest
		// .getAttribute("caminhoRetornoIntegracaoComercial"));
		// }
		//
		// if(idOrdemServico != null && !idOrdemServico.trim().equals("")){
		//
		// ordemServico = fachada.recuperaOSPorId(new Integer(idOrdemServico));
		//
		// if(ordemServico != null){
		//
		// fachada.validarReligacaoAguaComInstalacaoTubeteMagneticoExibir(ordemServico,
		// veioEncerrarOS);
		// }else{
		// throw new ActionServletException("atencao.ordem_servico.inexistente");
		// }
		// }
		// }else{
		//
		// ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");
		// }
		//
		// String idServicoMotivoNaoCobranca =
		// efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getMotivoNaoCobranca();
		// String valorPercentual =
		// efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getPercentualCobranca();
		// String qtdParcelas =
		// efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getQuantidadeParcelas();
		// String idFuncionario =
		// efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getIdFuncionario();
		//
		// if(ordemServico != null &&
		// efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getIdTipoDebito() != null){
		//
		// ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;
		//
		// ordemServico.setIndicadorComercialAtualizado(new Short("1"));
		//
		// BigDecimal valorAtual = new BigDecimal(0);
		// if(efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getValorDebito() != null){
		// String valorDebito =
		// efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getValorDebito().toString()
		// .replace(".", "");
		//
		// valorDebito = valorDebito.replace(",", ".");
		//
		// valorAtual = new BigDecimal(valorDebito);
		//
		// ordemServico.setValorAtual(valorAtual);
		// }
		//
		// if(idServicoMotivoNaoCobranca != null){
		// servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
		// servicoNaoCobrancaMotivo.setId(new Integer(idServicoMotivoNaoCobranca));
		// }
		//
		// if(idServicoMotivoNaoCobranca == null){
		// ordemServico.setServicoNaoCobrancaMotivo(null);
		// }else{
		// ordemServico.setServicoNaoCobrancaMotivo(servicoNaoCobrancaMotivo);
		// }
		//
		// if(valorPercentual != null){
		// ordemServico.setPercentualCobranca(new
		// BigDecimal(efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm
		// .getPercentualCobranca()));
		// }else{
		// ordemServico.setPercentualCobranca(null);
		// }
		//
		// if(ordemServico.getImovel().getLigacaoAgua() != null){
		// ordemServico.setCorteTipo(ordemServico.getImovel().getLigacaoAgua().getCorteTipo());
		// }
		//
		// ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.SIM);
		//
		// }
		//
		// Imovel imovel = null;
		//
		// if(ordemServico.getRegistroAtendimento() != null){
		// imovel = ordemServico.getRegistroAtendimento().getImovel();
		// }else{
		// imovel = ordemServico.getCobrancaDocumento().getImovel();
		// }
		//
		// LigacaoAgua ligacaoAgua = fachada.pesquisarLigacaoAgua(imovel.getId());
		//
		// if(ligacaoAgua == null){
		//
		// ligacaoAgua = new LigacaoAgua();
		//
		// }
		//
		// if(ligacaoAgua.getNumeroReligacao() != null){
		// ligacaoAgua.setNumeroReligacao(ligacaoAgua.getNumeroReligacao() + 1);
		// }else{
		// ligacaoAgua.setNumeroReligacao(1);
		// }
		//
		// // regitrando operacao
		// ordemServico.setOperacaoEfetuada(operacaoEfetuada);
		// ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		// registradorOperacao.registrarOperacao(ordemServico);
		//
		// IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();
		//
		// integracaoComercialHelper.setImovel(imovel);
		// integracaoComercialHelper.setLigacaoAgua(ligacaoAgua);
		// integracaoComercialHelper.setRegistroMagneticoInstalacaoHistorico(ligacaoAgua.getHidrometroInstalacaoHistorico());
		// integracaoComercialHelper.setOrdemServico(ordemServico);
		// integracaoComercialHelper.setQtdParcelas(qtdParcelas);
		//
		// if(efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getVeioEncerrarOS().equalsIgnoreCase("FALSE")){
		// integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);
		//
		// fachada.efetuarReligacaoAguaComInstalacaoTubeteMagnetico(integracaoComercialHelper,
		// usuario);
		//
		// }else{
		// integracaoComercialHelper.setVeioEncerrarOS(Boolean.TRUE);
		// sessao.setAttribute("integracaoComercialHelper", integracaoComercialHelper);
		//
		// if(sessao.getAttribute("semMenu") == null){
		// retorno = actionMapping.findForward("encerrarOrdemServicoAction");
		// }else{
		// retorno = actionMapping.findForward("encerrarOrdemServicoPopupAction");
		// }
		// sessao.removeAttribute("caminhoRetornoIntegracaoComercial");
		// }
		//
		// fachada.efetuarReligacaoAguaComInstalacaoRegistroMagnetico(integracaoComercialHelper,
		// usuario);
		//
		// if(retorno.getName().equalsIgnoreCase("telaSucesso")){
		// montarPaginaSucesso(httpServletRequest,
		// "Religação de Água com Instalação de Tubete Magnético para o imóvel " + imovel.getId()
		// + " efetuada com sucesso",
		// "Efetuar outra Religação de Água com Instalação de Tubete Magnético",
		// "exibirEfetuarReligacaoAguaComInstalacaoTubeteMagneticoAction.do?menu=sim");
		// }
		//
		return retorno;
		//
		// }
		//
		// // [SB0001] - Gerar Ligação de Água
		// //
		// // Método responsável por setar os dados da ligação de água
		// // de acordo com os dados selecionados pelo usuário e pelas exigências do
		// // caso de uso
		// public LigacaoAgua setDadosLigacaoAgua(
		// EfetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm
		// efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm,
		// Fachada fachada){
		//
		// FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
		// filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID,
		// efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getMatriculaImovel()));
		// Collection colecaoLigacaoAguaBase = fachada.pesquisar(filtroLigacaoAgua,
		// LigacaoAgua.class.getName());
		//
		// LigacaoAgua ligacaoAgua = (LigacaoAgua)
		// Util.retonarObjetoDeColecao(colecaoLigacaoAguaBase);
		//
		// if(efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getDataReligacao() != null
		// &&
		// !efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getDataReligacao().equals("")){
		// Date data =
		// Util.converteStringParaDate(efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getDataReligacao());
		// ligacaoAgua.setDataReligacao(data);
		// }else{
		// throw new ActionServletException("atencao.informe_campo", null, " Data da Religação");
		// }
		//
		// if(ligacaoAgua.getNumeroReligacao() != null){
		// ligacaoAgua.setNumeroReligacao(ligacaoAgua.getNumeroReligacao() + 1);
		// }else{
		// ligacaoAgua.setNumeroReligacao(1);
		// }
		//
		// return ligacaoAgua;
		// }
		//
		// // [SB0003] - Atualizar Ordem de Serviço
		// //
		// // Método responsável por setar os dados da ordem de serviço
		// // de acordo com as exigências do caso de uso
		// public OrdemServico setDadosOrdemServico(HttpServletRequest httpServletRequest,
		// OrdemServico ordemServico,
		// EfetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm
		// efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm){
		//
		// if(efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getIdTipoDebito() != null){
		//
		// String idServicoMotivoNaoCobranca =
		// efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getMotivoNaoCobranca();
		// ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;
		//
		// if(idServicoMotivoNaoCobranca != null &&
		// !idServicoMotivoNaoCobranca.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
		// servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
		// servicoNaoCobrancaMotivo.setId(new Integer(idServicoMotivoNaoCobranca));
		// }else{
		// // Calcula valores do Quadro: Dados da Geração do Débito
		// BigDecimal valorAtual = this.calcularValores(httpServletRequest, ordemServico,
		// efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm);
		//
		// if(Util.verificarNaoVazio(efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getPercentualCobranca())
		// &&
		// !efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getPercentualCobranca().equalsIgnoreCase(
		// "-1")){
		// ordemServico.setPercentualCobranca(new
		// BigDecimal(efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm
		// .getPercentualCobranca()));
		// }
		//
		// // if
		// //
		// (Util.verificarNaoVazio(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getUnidadeMedida())
		// // &&
		// //
		// Util.verificarNaoVazio(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getQuantidade()))
		// // {
		//
		// if(efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getValorServico() != null
		// &&
		// !efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm.getValorServico().equalsIgnoreCase("")){
		// // ordemServico.setValorOriginal(new
		// //
		// BigDecimal(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorServico().toString()
		// // .replace(".", "").replace(",", ".")));
		// ordemServico.setValorOriginal(Util
		// .formatarMoedaRealparaBigDecimal(efetuarReligacaoAguaComInstalacaoTubeteMagneticoActionForm
		// .getValorServico().toString()));
		// }
		//
		// ordemServico.setValorAtual(valorAtual);
		//
		// // if (efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorDebito() !=
		// // null &&
		// //
		// !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorDebito().equalsIgnoreCase(""))
		// // {
		// // ordemServico.setValorAtual(new
		// //
		// BigDecimal(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorDebito().toString()
		// // .replace(".", "").replace(",", ".")));
		// // }
		// // } else if
		// //
		// (Util.verificarNaoVazio(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorServico()))
		// // {
		// // ordemServico.setValorAtual(new
		// //
		// BigDecimal(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorServico().toString()
		// // .replace(".", "").replace(",", ".")));
		// // }
		// }
		//
		// ordemServico.setServicoNaoCobrancaMotivo(servicoNaoCobrancaMotivo);
		// }
		// ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.SIM);
		// ordemServico.setUltimaAlteracao(new Date());
		//
		// return ordemServico;
		// }
		//
		// private BigDecimal calcularValores(HttpServletRequest httpServletRequest, OrdemServico
		// ordemServico,
		// EfetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm form){
		//
		// String calculaValores = httpServletRequest.getParameter("calculaValores");
		//
		// BigDecimal valorDebito = new BigDecimal(0);
		// SistemaParametro sistemaParametro = this.getFachada().pesquisarParametrosDoSistema();
		// Integer qtdeParcelas = null;
		//
		// if(calculaValores != null && calculaValores.equals("S")){
		//
		// // [UC0186] - Calcular Prestação
		// BigDecimal taxaJurosFinanciamento = null;
		// qtdeParcelas = new Integer(form.getQuantidadeParcelas());
		//
		// if(ordemServico.getServicoTipo().getIndicadorCobrarJuros() ==
		// ConstantesSistema.SIM.shortValue() && qtdeParcelas.intValue() > 1){
		//
		// taxaJurosFinanciamento = sistemaParametro.getPercentualTaxaJurosFinanciamento();
		// }else{
		// taxaJurosFinanciamento = new BigDecimal(0);
		// qtdeParcelas = 1;
		// }
		//
		// BigDecimal valorPrestacao = null;
		// if(taxaJurosFinanciamento != null){
		//
		// valorDebito = Util.formatarMoedaRealparaBigDecimal(form.getValorDebito());
		//
		// String percentualCobranca = httpServletRequest.getParameter("percentualCobranca");
		//
		// if(percentualCobranca == null || percentualCobranca.equalsIgnoreCase("")
		// || percentualCobranca.equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
		// throw new ActionServletException("atencao.informe_campo", null,
		// "Percentual de Cobrança");
		// }
		//
		// valorDebito = valorDebito.multiply(Util.converterEmPercentagem(percentualCobranca, 1));
		//
		// valorPrestacao = this.getFachada().calcularPrestacao(taxaJurosFinanciamento,
		// qtdeParcelas, valorDebito,
		// new BigDecimal("0.00"));
		//
		// valorPrestacao.setScale(2, BigDecimal.ROUND_HALF_UP);
		// }
		//
		// if(valorPrestacao != null){
		// String valorPrestacaoComVirgula = Util.formataBigDecimal(valorPrestacao, 2, true);
		// form.setValorParcelas(valorPrestacaoComVirgula);
		// }else{
		// form.setValorParcelas("0,00");
		// }
		//
		// // }else if(ordemServico.getRegistroAtendimento() != null){
		// }else{
		//
		// HttpSession sessao = httpServletRequest.getSession(false);
		// Imovel imovel = (Imovel) sessao.getAttribute("imovel");
		//
		// valorDebito =
		// Fachada.getInstancia().obterValorDebito(ordemServico.getServicoTipo().getId(),
		// imovel.getId(), new Short("3"));
		//
		// if(valorDebito != null){
		// form.setValorDebito(Util.formataBigDecimal(valorDebito, 2, true));
		// }else{
		// form.setValorDebito("0");
		// }
		// }
		//
		// return valorDebito;
	}

}
