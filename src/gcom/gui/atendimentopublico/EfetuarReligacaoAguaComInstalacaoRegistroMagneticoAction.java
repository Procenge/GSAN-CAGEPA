
package gcom.gui.atendimentopublico;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EfetuarReligacaoAguaComInstalacaoRegistroMagneticoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		//		
		//		
		// // Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		//
		// // Obt�m a inst�ncia da fachada
		// Fachada fachada = Fachada.getInstancia();
		//
		// HttpSession sessao = httpServletRequest.getSession(false);
		// EfetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm
		// efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm =
		// (EfetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm) actionForm;
		//
		// // Usuario logado no sistema
		// Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// OrdemServico ordemServico = null;
		// /*
		// * [UC0107] Registrar Transa��o
		// */
		//
		// // RegistradorOperacao registradorOperacao = new RegistradorOperacao(
		// // Operacao.OPERACAO_EFETUAR_RELIGACAO_AGUA_COM_INSTALACAO_REGISTRO_MAGNETICO, new
		// // UsuarioAcaoUsuarioHelper(usuario,
		// // UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
		//
		// Operacao operacao = new Operacao();
		// //
		// operacao.setId(Operacao.OPERACAO_EFETUAR_RELIGACAO_AGUA_COM_INSTALACAO_REGISTRO_MAGNETICO);
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
		// if(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getIdOrdemServico() !=
		// null){
		// idOrdemServico =
		// efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getIdOrdemServico();
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
		// fachada.validarReligacaoAguaComInstalacaoRegistroMagneticoExibir(ordemServico,
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
		// efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getMotivoNaoCobranca();
		// String valorPercentual =
		// efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getPercentualCobranca();
		// String qtdParcelas =
		// efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getQuantidadeParcelas();
		// String idFuncionario =
		// efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getIdFuncionario();
		//
		// if(ordemServico != null &&
		// efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getIdTipoDebito() != null){
		//
		// ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;
		//
		// ordemServico.setIndicadorComercialAtualizado(new Short("1"));
		//
		// BigDecimal valorAtual = new BigDecimal(0);
		// if(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getValorDebito() !=
		// null){
		// String valorDebito =
		// efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getValorDebito().toString().replace(".",
		// "");
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
		// BigDecimal(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm
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
		// // registradorOperacao.registrarOperacao(ordemServico);
		//
		// IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();
		//
		// integracaoComercialHelper.setImovel(imovel);
		// integracaoComercialHelper.setLigacaoAgua(ligacaoAgua);
		// integracaoComercialHelper.setRegistroMagneticoInstalacaoHistorico(ligacaoAgua.getHidrometroInstalacaoHistorico());
		// integracaoComercialHelper.setOrdemServico(ordemServico);
		// integracaoComercialHelper.setQtdParcelas(qtdParcelas);
		//
		// if(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getVeioEncerrarOS().equalsIgnoreCase("FALSE")){
		// integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);
		//
		// // fachada.efetuarReligacaoAguaComInstalacaoRegistroMagnetico(integracaoComercialHelper,
		// // usuario);
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
		// "Religa��o de �gua com Instala��o de Registro Magn�tico para o im�vel "
		// + imovel.getId() + " efetuada com sucesso",
		// "Efetuar outra Religa��o de �gua com Instala��o de Registro Magn�tico",
		// "exibirEfetuarReligacaoAguaComInstalacaoRegistroMagneticoAction.do?menu=sim");
		// }
		//
		return retorno;
		//
		// }
		//
		// // [SB0001] - Gerar Liga��o de �gua
		// //
		// // M�todo respons�vel por setar os dados da liga��o de �gua
		// // de acordo com os dados selecionados pelo usu�rio e pelas exig�ncias do
		// // caso de uso
		// public LigacaoAgua setDadosLigacaoAgua(
		// EfetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm
		// efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm,
		// Fachada fachada){
		//
		// FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
		// filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID,
		// efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getMatriculaImovel()));
		// Collection colecaoLigacaoAguaBase = fachada.pesquisar(filtroLigacaoAgua,
		// LigacaoAgua.class.getName());
		//
		// LigacaoAgua ligacaoAgua = (LigacaoAgua)
		// Util.retonarObjetoDeColecao(colecaoLigacaoAguaBase);
		//
		// if(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getDataReligacao() !=
		// null
		// &&
		// !efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getDataReligacao().equals("")){
		// Date data =
		// Util.converteStringParaDate(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getDataReligacao());
		// ligacaoAgua.setDataReligacao(data);
		// }else{
		// throw new ActionServletException("atencao.informe_campo", null, " Data da Religa��o");
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
		// // [SB0003] - Atualizar Ordem de Servi�o
		// //
		// // M�todo respons�vel por setar os dados da ordem de servi�o
		// // de acordo com as exig�ncias do caso de uso
		// public OrdemServico setDadosOrdemServico(
		// HttpServletRequest httpServletRequest,
		// OrdemServico ordemServico,
		// EfetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm
		// efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm){
		//
		// if(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getIdTipoDebito() !=
		// null){
		//
		// String idServicoMotivoNaoCobranca =
		// efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getMotivoNaoCobranca();
		// ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;
		//
		// if(idServicoMotivoNaoCobranca != null &&
		// !idServicoMotivoNaoCobranca.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
		// servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
		// servicoNaoCobrancaMotivo.setId(new Integer(idServicoMotivoNaoCobranca));
		// }else{
		// // Calcula valores do Quadro: Dados da Gera��o do D�bito
		// BigDecimal valorAtual = this.calcularValores(httpServletRequest, ordemServico,
		// efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm);
		//
		// if(Util.verificarNaoVazio(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getPercentualCobranca())
		// &&
		// !efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getPercentualCobranca().equalsIgnoreCase(
		// "-1")){
		// ordemServico.setPercentualCobranca(new
		// BigDecimal(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm
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
		// if(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getValorServico() != null
		// &&
		// !efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getValorServico().equalsIgnoreCase("")){
		// // ordemServico.setValorOriginal(new
		// //
		// BigDecimal(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorServico().toString()
		// // .replace(".", "").replace(",", ".")));
		// ordemServico.setValorOriginal(Util
		// .formatarMoedaRealparaBigDecimal(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm
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
		// // [UC0186] - Calcular Presta��o
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
		// "Percentual de Cobran�a");
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
