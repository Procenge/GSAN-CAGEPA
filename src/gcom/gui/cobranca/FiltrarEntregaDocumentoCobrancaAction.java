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

package gcom.gui.cobranca;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaAcaoSituacao;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeComando;
import gcom.cobranca.FiltroCobrancaDocumento;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.Intervalo;
import gcom.util.filtro.MaiorQue;
import gcom.util.filtro.MenorQue;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.ExecutorParametrosCobranca;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.comum.exception.PCGException;

/**
 * [UC3028] Filtrar Documento Cobrança para Informar Entrega
 * 
 * @author Cinthya Cavalcanti
 * @created 12 de Dezembro de 2011
 */
public class FiltrarEntregaDocumentoCobrancaAction
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
					HttpServletResponse httpServletResponse) throws PCGException{

		ActionForward retorno = actionMapping.findForward("exibirInformarEntregaDocumentoCobrancaAction");

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		FiltrarEntregaDocumentoCobrancaActionForm filtrarEntregaDocumentoCobrancaActionForm = (FiltrarEntregaDocumentoCobrancaActionForm) actionForm;

		String indicadorDocumentos = (String) filtrarEntregaDocumentoCobrancaActionForm.getIndicadorDocumentos();
		String indicadorOrdenacaoDocumentos = (String) filtrarEntregaDocumentoCobrancaActionForm.getIndicadorOrdenacaoDocumentos();

		FiltroCobrancaDocumento filtroCobrancaDocumento = new FiltroCobrancaDocumento();
		boolean isCamposPreenchidos = false;

		if(indicadorDocumentos.equals("1")){
			String comando = (String) filtrarEntregaDocumentoCobrancaActionForm.getCobrancaAcaoAtividadeComando();
			String empresaCriterioPeloComando = (String) filtrarEntregaDocumentoCobrancaActionForm.getEmpresaCriterioPeloComando();
			String sequencialInicialDocumentos = (String) filtrarEntregaDocumentoCobrancaActionForm.getSequencialInicialDocumentos();
			String sequencialFinalDocumentos = (String) filtrarEntregaDocumentoCobrancaActionForm.getSequencialFinalDocumentos();
			String localidadeInicial = (String) filtrarEntregaDocumentoCobrancaActionForm.getLocalidadeInicial();
			String setorComercialInicialCodigo = (String) filtrarEntregaDocumentoCobrancaActionForm.getSetorComercialInicialCodigo();
			String quadraInicial = (String) filtrarEntregaDocumentoCobrancaActionForm.getQuadraInicial();
			String localidadeFinal = (String) filtrarEntregaDocumentoCobrancaActionForm.getLocalidadeFinal();
			String setorComercialFinalCodigo = (String) filtrarEntregaDocumentoCobrancaActionForm.getSetorComercialFinalCodigo();
			String quadraFinal = (String) filtrarEntregaDocumentoCobrancaActionForm.getQuadraFinal();

			if(comando != null && !comando.equals("")){
				verificarComando(comando, fachada);
				filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ATIVIDADE_COMANDO_ID, Integer
								.parseInt(comando)));
				isCamposPreenchidos = true;
			}

			if(empresaCriterioPeloComando != null && !empresaCriterioPeloComando.equals("") && !empresaCriterioPeloComando.equals("-1")){
				filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.EMPRESA_ID, Integer
								.parseInt(empresaCriterioPeloComando)));
				isCamposPreenchidos = true;
			}

			if(sequencialInicialDocumentos != null && !sequencialInicialDocumentos.equals("") && sequencialFinalDocumentos != null
							&& !sequencialFinalDocumentos.equals("")){

				// FS0003 – Verificar intervalo final menor que intervalo inicial
				if(Integer.valueOf(sequencialFinalDocumentos) < Integer.valueOf(sequencialInicialDocumentos)){
					throw new ActionServletException("atencao.intervalo.final.anterior.intervalo.inicial");
				}

				filtroCobrancaDocumento.adicionarParametro(new MaiorQue(FiltroCobrancaDocumento.NUMERO_SEQUENCIAL,
								sequencialInicialDocumentos, ParametroSimples.CONECTOR_AND));
				filtroCobrancaDocumento.adicionarParametro(new MenorQue(FiltroCobrancaDocumento.NUMERO_SEQUENCIAL,
								sequencialFinalDocumentos));
				isCamposPreenchidos = true;
			}

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			if(localidadeInicial != null && !localidadeInicial.equals("") && localidadeFinal != null && !localidadeFinal.equals("")){

				verificarIntervalo(localidadeInicial, localidadeFinal);

				filtroLocalidade.limparListaParametros();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer.valueOf(localidadeInicial)));
				// pesquisa
				Collection<Localidade> localidadesInicial = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
				if(localidadesInicial == null || localidadesInicial.isEmpty()){
					throw new ActionServletException("atencao.pesquisa.localidade_inicial_inexistente");
				}

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer.valueOf(localidadeFinal)));
				// pesquisa
				Collection<Localidade> localidadesFinais = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
				if(localidadesFinais == null || localidadesFinais.isEmpty()){
					throw new ActionServletException("atencao.pesquisa.localidade_final_inexistente");
				}

				filtroCobrancaDocumento.adicionarParametro(new MaiorQue(FiltroCobrancaDocumento.LOCALIDADE_ID, localidadeInicial,
								ParametroSimples.CONECTOR_AND));
				filtroCobrancaDocumento.adicionarParametro(new MenorQue(FiltroCobrancaDocumento.LOCALIDADE_ID, localidadeFinal));
				isCamposPreenchidos = true;
			}

			if(setorComercialInicialCodigo != null && !setorComercialInicialCodigo.equals("") && setorComercialFinalCodigo != null
							&& !setorComercialFinalCodigo.equals("")){

				verificarIntervalo(setorComercialInicialCodigo, setorComercialFinalCodigo);

				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
				if(setorComercialInicialCodigo != null && !setorComercialInicialCodigo.toString().trim().equalsIgnoreCase("")){
					if(localidadeInicial != null && !localidadeInicial.toString().trim().equalsIgnoreCase("")){
						filtroSetorComercial.limparListaParametros();
						// coloca parametro no filtro
						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
										ConstantesSistema.INDICADOR_USO_ATIVO));
						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, Integer
										.valueOf(localidadeInicial)));
						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Integer
										.valueOf(setorComercialInicialCodigo)));
						// pesquisa
						Collection<SetorComercial> setorComerciais = fachada
										.pesquisar(filtroSetorComercial, SetorComercial.class.getName());
						if(setorComerciais == null || setorComerciais.isEmpty()){
							throw new ActionServletException("atencao.pesquisa.setor_inicial_inexistente");
						}
					}

				}

				filtroSetorComercial = new FiltroSetorComercial();
				if(setorComercialFinalCodigo != null && !setorComercialFinalCodigo.toString().trim().equalsIgnoreCase("")){
					if(localidadeFinal != null && !localidadeFinal.toString().trim().equalsIgnoreCase("")){
						filtroSetorComercial.limparListaParametros();
						// coloca parametro no filtro
						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
										ConstantesSistema.INDICADOR_USO_ATIVO));
						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, Integer
										.valueOf(localidadeFinal)));
						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Integer
										.valueOf(setorComercialFinalCodigo)));
						// pesquisa
						Collection<SetorComercial> setorComerciaisFinais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class
										.getName());
						if(setorComerciaisFinais == null || setorComerciaisFinais.isEmpty()){
							throw new ActionServletException("atencao.pesquisa.setor_final_inexistente");
						}
					}

				}

				filtroCobrancaDocumento.adicionarParametro(new MaiorQue(FiltroCobrancaDocumento.SETOR_COMERCIAL,
								setorComercialInicialCodigo, ParametroSimples.CONECTOR_AND));
				filtroCobrancaDocumento
								.adicionarParametro(new MenorQue(FiltroCobrancaDocumento.SETOR_COMERCIAL, setorComercialFinalCodigo));
				isCamposPreenchidos = true;
			}

			if(quadraInicial != null && !quadraInicial.equals("") && quadraFinal != null && !quadraFinal.equals("")){

				verificarIntervalo(quadraInicial, quadraFinal);

				filtroCobrancaDocumento.adicionarParametro(new MaiorQue(FiltroCobrancaDocumento.QUADRA_NM, quadraInicial,
								ParametroSimples.CONECTOR_AND));
				filtroCobrancaDocumento.adicionarParametro(new MenorQue(FiltroCobrancaDocumento.QUADRA_NM, quadraFinal));

				// [FS0006] – Verificar existência da quadra
				verificarQuadra(quadraInicial, setorComercialInicialCodigo, fachada);
				verificarQuadra(quadraFinal, setorComercialFinalCodigo, fachada);
				isCamposPreenchidos = true;
			}

			if(!isCamposPreenchidos){
				throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
			}

		}else if(indicadorDocumentos.equals("2")){

			String[] cobrancaAcao = (String[]) filtrarEntregaDocumentoCobrancaActionForm.getCobrancaAcao();
			String empresa = (String) filtrarEntregaDocumentoCobrancaActionForm.getEmpresa();
			String imovel = (String) filtrarEntregaDocumentoCobrancaActionForm.getImovel();
			String dataInicial = (String) filtrarEntregaDocumentoCobrancaActionForm.getDataInicialGeracao();
			String dataFinal = (String) filtrarEntregaDocumentoCobrancaActionForm.getDataFinalGeracao();

			if(cobrancaAcao != null && !cobrancaAcao.equals("-1") && !cobrancaAcao.equals("")){
				filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ID_COBRANCA_ACAO, cobrancaAcao[0]));
				isCamposPreenchidos = true;
			}

			if(empresa != null && !empresa.equals("-1") && !empresa.equals("")){
				filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.EMPRESA_ID, empresa));
				isCamposPreenchidos = true;
			}

			if(imovel != null && !imovel.equals("")){
				filtroCobrancaDocumento
								.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.IMOVEL_ID, Integer.parseInt(imovel)));
				isCamposPreenchidos = true;
			}

			if(dataInicial != null && !dataInicial.equals("") && dataFinal != null && !dataFinal.equals("")){

				if((dataInicial.trim().length() == 10) && (dataFinal.trim().length() == 10)){

					Date dataGeracaoInicialDocumentoCobranca = null;
					Date dataGeracaoFinalDocumentoCobranca = null;
					Date dataGeracaoInicialDocumentoCobrancaAux = null;
					Date dataGeracaoFinalDocumentoCobrancaAux = null;
					// Cria o formato da data
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					// [FS0008] – Validar data
					try{
						if(dataInicial.length() == 10){
							String dia = dataInicial.substring(0, 2);
							String mes = dataInicial.substring(3, 5);
							String ano = dataInicial.substring(6);
							String anoMesDiaSemBarra = ano + mes + dia;
							if(Util.validarAnoMesDiaSemBarra(anoMesDiaSemBarra)){
								throw new ActionServletException("atencao.dataemissaoinicial.documento.cobranca.invalida");
							}
						}

						dataGeracaoInicialDocumentoCobrancaAux = formato.parse(dataInicial);
						dataGeracaoInicialDocumentoCobranca = Util.formatarDataInicial(dataGeracaoInicialDocumentoCobrancaAux);

					}catch(ParseException e){
						throw new ActionServletException("atencao.dataemissaoinicial.documento.cobranca.invalida");
					}

					try{
						if(dataFinal.length() == 10){
							String dia = dataFinal.substring(0, 2);
							String mes = dataFinal.substring(3, 5);
							String ano = dataFinal.substring(6);
							String anoMesDiaSemBarra = ano + mes + dia;
							if(Util.validarAnoMesDiaSemBarra(anoMesDiaSemBarra)){
								throw new ActionServletException("atencao.dataemissaofinal.documento.cobranca.invalida");
							}
						}

						dataGeracaoFinalDocumentoCobrancaAux = formato.parse(dataFinal);
						dataGeracaoFinalDocumentoCobranca = Util.formatarDataFinal(dataGeracaoFinalDocumentoCobrancaAux);

					}catch(ParseException e){
						throw new ActionServletException("atencao.dataemissaofinal.documento.cobranca.invalida");
					}

					// [FS0009] – Verificar data final menor que data inicial

					if(dataGeracaoFinalDocumentoCobranca.before(dataGeracaoInicialDocumentoCobranca)){
						throw new ActionServletException("atencao.dataemissaofinal.menorque");
					}

					// dataInicio = Util.formatarDataInicial(dataI);
					// dataFim = Util.formatarDataFinal(dataF);

					// .setTimestamp("dataInicio",dataInicio)
					// .setTimestamp("dataFim", dataFim)

					filtroCobrancaDocumento.adicionarParametro(new Intervalo(FiltroCobrancaDocumento.DATA_EMISSAO, new Timestamp(
									dataGeracaoInicialDocumentoCobranca.getTime()), new Timestamp(dataGeracaoFinalDocumentoCobranca
									.getTime())));
					isCamposPreenchidos = true;

					/*
					 * Calendar calendarInicio = new GregorianCalendar();
					 * Calendar calendarFim = new GregorianCalendar();
					 * calendarInicio.set(Calendar.DAY_OF_MONTH,
					 * Integer.valueOf(dataInicial.substring(0, 2)).intValue());
					 * calendarInicio.set(Calendar.MONTH, Integer.valueOf(dataInicial.substring(3,
					 * 5)).intValue());
					 * calendarInicio.set(Calendar.YEAR, Integer.valueOf(dataInicial.substring(6,
					 * 10)).intValue());
					 * Date dataAux = new Date();
					 * dataAux.setYear(Integer.valueOf(dataInicial.substring(6, 10)).intValue());
					 * dataAux.setMonth(Integer.valueOf(dataInicial.substring(3, 5)).intValue());
					 * dataAux.setDate(Integer.valueOf(dataInicial.substring(0, 2)).intValue());
					 * dataAux.setHours(0);
					 * dataAux.setMinutes(0);
					 * dataAux.setSeconds(0);
					 * calendarInicio.setTime(dataAux.)
					 * calendarFim.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dataFinal.substring(0,
					 * 2)).intValue());
					 * calendarFim.set(Calendar.MONTH, Integer.valueOf(dataFinal.substring(3,
					 * 5)).intValue());
					 * calendarFim.set(Calendar.YEAR, Integer.valueOf(dataFinal.substring(6,
					 * 10)).intValue());
					 * if(calendarFim.compareTo(calendarInicio) < 0){
					 * throw new ActionServletException("atencao.data_fim_menor_inicio");
					 * }
					 * filtroCobrancaDocumento.adicionarParametro(new
					 * Intervalo(FiltroCobrancaDocumento.DATA_EMISSAO,
					 * calendarInicio.getTime(), calendarFim.getTime()));
					 */
				}
			}

			if(!isCamposPreenchidos){
				throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
			}
		}

		filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.COBRANCA_SITUACAO_ID,
						CobrancaAcaoSituacao.PENDENTE));

		if(indicadorOrdenacaoDocumentos.equals("1")){
			filtroCobrancaDocumento.setCampoOrderBy(FiltroCobrancaDocumento.EMPRESA_ID);
			filtroCobrancaDocumento.setCampoOrderBy(FiltroCobrancaDocumento.LOCALIDADE_ID);
			filtroCobrancaDocumento.setCampoOrderBy(FiltroCobrancaDocumento.SETOR_COMERCIAL);
			filtroCobrancaDocumento.setCampoOrderBy(FiltroCobrancaDocumento.QUADRA_NM);
			filtroCobrancaDocumento.setCampoOrderBy(FiltroCobrancaDocumento.NUMERO_SEQUENCIAL);

		}else if(indicadorOrdenacaoDocumentos.equals("2")){
			filtroCobrancaDocumento.setCampoOrderBy(FiltroCobrancaDocumento.EMPRESA_ID);
			filtroCobrancaDocumento.setCampoOrderBy(FiltroCobrancaDocumento.NUMERO_SEQUENCIAL);
		}

		sessao.setAttribute("filtroCobrancaDocumento", filtroCobrancaDocumento);
		sessao.setAttribute("indicadorOrdenacaoDocumentos", indicadorOrdenacaoDocumentos);

		return retorno;
	}

	// [FS0002] – Verificar seleção do comando
	private void verificarComando(String comando, Fachada fachada){

		FiltroCobrancaAcaoAtividadeComando filtroCobrancaAcaoAtividadeComando = new FiltroCobrancaAcaoAtividadeComando();
		filtroCobrancaAcaoAtividadeComando.adicionarParametro(new ParametroSimples(FiltroCobrancaAcaoAtividadeComando.ID, Integer
						.parseInt(comando)));
		filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcao");

		Collection colecaoCobrancaAcaoAtividadeComando = fachada.pesquisar(filtroCobrancaAcaoAtividadeComando,
						CobrancaAcaoAtividadeComando.class.getName());

		if(colecaoCobrancaAcaoAtividadeComando != null && !colecaoCobrancaAcaoAtividadeComando.isEmpty()){
			CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = (CobrancaAcaoAtividadeComando) Util
							.retonarObjetoDeColecao(colecaoCobrancaAcaoAtividadeComando);

			// Caso o comando selecionado ainda não tenha sido realizado
			if(cobrancaAcaoAtividadeComando.getRealizacao() == null){
				throw new ActionServletException("atencao.comando.ainda.nao.realizado");
			}

			// Caso o comando selecionado ainda já tenha sido encerrado
			if(cobrancaAcaoAtividadeComando.getDataEncerramentoRealizada() != null){
				throw new ActionServletException("atencao.comando.ja.realizado");
			}

			// retorna os valores do parâmetro P_COBRANCA_ACAO_COM_ENTREGA_DOCUMENTO
			String[] idsCobrancaAcaoComEntregaDocumento = null;
			try{
				idsCobrancaAcaoComEntregaDocumento = ((String) ParametroCobranca.P_COBRANCA_ACAO_COM_ENTREGA_DOCUMENTO
								.executar(ExecutorParametrosCobranca.getInstancia())).split(",");
			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			boolean encontrouAcao = false;

			// verifica se a ção de cobrança do cobrancaAcaoAtividadeComando corresponde a alguma
			// das ações com entrega de documento
			// do parâmetro
			if(idsCobrancaAcaoComEntregaDocumento != null){
				for(int i = 0; i < idsCobrancaAcaoComEntregaDocumento.length; i++){
					if(cobrancaAcaoAtividadeComando.getCobrancaAcao().getId()
									.equals(Integer.valueOf(idsCobrancaAcaoComEntregaDocumento[i]))){
						encontrouAcao = true;
					}
				}
			}

			// Caso a ação de cobrança do comando selecionado não corresponda a uma ação de cobrança
			// com entrega de documento
			if(!encontrouAcao){
				throw new ActionServletException("atencao.nao.acao.entrega.documento", cobrancaAcaoAtividadeComando.getCobrancaAcao()
								.getDescricaoCobrancaAcao());
			}

		}

	}

	// [FS0006] – Verificar existência da quadra
	public void verificarQuadra(String quadra, String setorComercial, Fachada fachada){

		FiltroQuadra filtroQuadra = new FiltroQuadra();

		// Objetos que serão retornados pelo Hibernate
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, quadra));
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, setorComercial));

		Collection colecaoQuadras = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

		if(colecaoQuadras == null || colecaoQuadras.isEmpty()){
			throw new ActionServletException("atencao.quadra.inexistente");
		}
	}

	public void verificarIntervalo(String valorInicial, String valorFinal){

		if(Integer.valueOf(valorFinal) < Integer.valueOf(valorInicial)){
			throw new ActionServletException("atencao.intervalo.final.anterior.intervalo.inicial");
		}

	}
}
