
package gcom.agenciavirtual.cobranca;

import gcom.agenciavirtual.AbstractAgenciaVirtualBinarioWebservice;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.cobranca.RelatorioCertidaoNegativaModelo1;
import gcom.relatorio.cobranca.RelatorioCertidaoNegativaModelo2;
import gcom.relatorio.cobranca.RelatorioCertidaoNegativaModelo3;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.ModeloRelatorioCertidaoNegativaDebitos;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Retorna a Certidão Negativa de Débitos
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>idImovel [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/emitirCertidaoNegativaDeDebitosWebService.do
 * 
 * @author Ado Rocha
 */
public class EmitirCertidaoNegativaDeDebitosWebService
				extends AbstractAgenciaVirtualBinarioWebservice {

	@Override
	protected void processarArquivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		String idImovel = recuperarParametroString("matricula", LABEL_CAMPO_MATRICULA_DO_IMOVEL, false, false, request);
		String codigoCliente = recuperarParametroString("codigoCliente", "Código Cliente", false, false, request);
		String periodoInicial = recuperarParametroString("periodoInicial", "Período Inicial", false, false, request);
		String periodoFinal = recuperarParametroString("periodoFinal", "Período Final", false, false, request);

		boolean parametroInformado = false;

		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		if(idImovel != null && !idImovel.equals("")){
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
			parametroInformado = true;
		}
		if(codigoCliente != null && !codigoCliente.equals("")){
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_ID, codigoCliente));
			parametroInformado = true;
		}

		if(!parametroInformado){
			informarStatus(STATUS_TIPO_ALERTA,
							MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.informe_matricula_ou_cliente"));
		}

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoClienteImovel = (Collection) fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

		// Imovel e Cliente
		ClienteImovel clienteImovelNaBase = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);

		Imovel imovelCertidao = fachada.pesquisarImovel(clienteImovelNaBase.getImovel().getId());
		if(imovelCertidao == null){
			informarStatus(STATUS_TIPO_ALERTA,
							MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.imovel_certidao_nao_encontrado"));
		}

		String nomeCliente = "";
		String enderecoCliente = "";

		// Endereço do Cliente
		enderecoCliente = fachada.pesquisarEnderecoClienteAbreviado(clienteImovelNaBase.getCliente().getId()).toString();

		Cliente cliente = fachada.pesquisarClienteUsuarioImovel(clienteImovelNaBase.getImovel().getId());

		// pesquisar cliente usuário
		if(cliente != null){
			nomeCliente = cliente.getNome();
		}else{
			informarStatus(STATUS_TIPO_ALERTA,
							MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.cliente_usuario_nao_encontrado"));
		}

		Usuario usuario = Usuario.USUARIO_AGENCIA_VIRTUAL;

		String parametroModeloRelatorioCertidao = "";
		try{

			parametroModeloRelatorioCertidao = ParametroCobranca.P_MODELO_RELATORIO_CERTIDAO_NEGATIVA_DEBITOS.executar();
		}catch(ControladorException e){

			e.printStackTrace();
		}

		if(parametroModeloRelatorioCertidao.equals(ModeloRelatorioCertidaoNegativaDebitos.UM.getValor())){

			// Verifica se o período de ref. foi informado.
			if((periodoInicial == null || periodoInicial.equals("")) || (periodoFinal == null || periodoFinal.equals(""))){
				informarStatus(STATUS_TIPO_ALERTA,
								MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.informar_periodo_referencia_debito"));
			}

			// Verifica se o período final é maior do que a data atual
			int anoMesAtual = Util.getAnoMesComoInt(new Date());
			String anoMesAtualSemBarra = String.valueOf(anoMesAtual);
			String anoMesFinalSemBarra = Util.formatarMesAnoParaAnoMes(periodoFinal);

			if(Util.compararAnoMesReferencia(anoMesFinalSemBarra, anoMesAtualSemBarra, ">")){
				informarStatus(STATUS_TIPO_ALERTA,
								MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.periodo_referencia_maior_hoje"));
			}
		}

		// Verifica endereço
		if(enderecoCliente == null){
			informarStatus(STATUS_TIPO_ALERTA,
							MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.endereco_cliente_nao_encontrado"));
		}

		Integer tipoRelatorio = TarefaRelatorio.TIPO_PDF;

		TarefaRelatorio relatorio = null;

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		if(parametroModeloRelatorioCertidao.equals(ModeloRelatorioCertidaoNegativaDebitos.UM.getValor())){

			relatorio = new RelatorioCertidaoNegativaModelo1((Usuario) usuario);

			if(sistemaParametro != null){
				relatorio.addParametro("empresa", sistemaParametro.getNomeEmpresa());
			}

			// Dados do Imóvel (Inscrição, Matrícula, Dados Hidrômetro)
			relatorio.addParametro("inscricaoImovel", imovelCertidao.getInscricaoFormatada());
			relatorio.addParametro("matriculaImovel", imovelCertidao.getId().toString());
			// Hidrometro
			if(imovelCertidao.getLigacaoAgua() != null && imovelCertidao.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
							&& imovelCertidao.getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro() != null){
				relatorio.addParametro("numeroHidrometro", imovelCertidao.getLigacaoAgua().getHidrometroInstalacaoHistorico()
								.getHidrometro().getNumero());
			}else{
				relatorio.addParametro("numeroHidrometro", "NAO INSTALADO");
			}

			if(usuario.getFuncionario() != null && usuario.getFuncionario().getId() > 0){
				relatorio.addParametro("nomeUsuario", usuario.getFuncionario().getNome());
				relatorio.addParametro("emissorUsuario", usuario.getFuncionario().getId());
			}else{
				relatorio.addParametro("nomeUsuario", usuario.getNomeUsuario());
				relatorio.addParametro("emissorUsuario", usuario.getId());
			}

			relatorio.addParametro("nomeCliente", nomeCliente);
			relatorio.addParametro("enderecoCliente", enderecoCliente);
			relatorio.addParametro("periodoInicial", periodoInicial);
			relatorio.addParametro("periodoFinal", periodoFinal);
			relatorio.addParametro("tipoFormatoRelatorio", tipoRelatorio);

		}else if(parametroModeloRelatorioCertidao.equals(ModeloRelatorioCertidaoNegativaDebitos.DOIS.getValor())){

			relatorio = new RelatorioCertidaoNegativaModelo2((Usuario) usuario);

			if(periodoInicial == null){
				periodoInicial = "";
			}

			if(periodoFinal == null){
				periodoFinal = "";
			}

			relatorio.addParametro("inscricaoImovel", imovelCertidao.getInscricaoFormatada());
			relatorio.addParametro("matriculaImovel", imovelCertidao.getId().toString());
			relatorio.addParametro("nomeCliente", nomeCliente);
			relatorio.addParametro("enderecoCliente", enderecoCliente);
			relatorio.addParametro("tipoFormatoRelatorio", tipoRelatorio);
			relatorio.addParametro("periodoInicial", periodoInicial);
			relatorio.addParametro("periodoFinal", periodoFinal);

			if(usuario.getFuncionario() != null && usuario.getFuncionario().getId() > 0){
				relatorio.addParametro("nomeUsuario", usuario.getFuncionario().getNome());
				relatorio.addParametro("emissorUsuario", usuario.getFuncionario().getId());
			}else{
				relatorio.addParametro("nomeUsuario", usuario.getNomeUsuario());
				relatorio.addParametro("emissorUsuario", usuario.getId());
			}

		}else if(parametroModeloRelatorioCertidao.equals(ModeloRelatorioCertidaoNegativaDebitos.TRES.getValor())){

			relatorio = new RelatorioCertidaoNegativaModelo3((Usuario) usuario);

			relatorio.addParametro("inscricaoImovel", imovelCertidao.getInscricaoFormatada());
			relatorio.addParametro("matriculaImovel", imovelCertidao.getId().toString());
			relatorio.addParametro("nomeCliente", nomeCliente);
			relatorio.addParametro("enderecoCliente", enderecoCliente);
			String cidade = "";
			if(imovelCertidao.getLogradouroCep() != null && imovelCertidao.getLogradouroCep().getCep() != null){
				cidade = imovelCertidao.getLogradouroCep().getCep().getMunicipio();

			}
			relatorio.addParametro("cidade", cidade);
			relatorio.addParametro("tipoFormatoRelatorio", tipoRelatorio);

			if(usuario.getFuncionario() != null && usuario.getFuncionario().getId() > 0){
				relatorio.addParametro("nomeUsuario", usuario.getFuncionario().getNome());
				relatorio.addParametro("emissorUsuario", usuario.getFuncionario().getId());
			}else{
				relatorio.addParametro("nomeUsuario", usuario.getNomeUsuario());
				relatorio.addParametro("emissorUsuario", usuario.getId());
			}
		}

		try{
			if(relatorio != null){
				processarExibicaoRelatorio(relatorio, tipoRelatorio, request, response, mapping);
			}
		}catch(ActionServletException e){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}

	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
