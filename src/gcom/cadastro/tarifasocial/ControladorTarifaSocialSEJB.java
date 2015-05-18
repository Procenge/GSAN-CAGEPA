/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.cadastro.tarifasocial;

import gcom.atendimentopublico.ligacaoagua.*;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.registroatendimento.ControladorRegistroAtendimentoLocal;
import gcom.atendimentopublico.registroatendimento.ControladorRegistroAtendimentoLocalHome;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoUnidade;
import gcom.cadastro.cliente.*;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.tarifasocial.bean.TarifaSocialHelper;
import gcom.cobranca.ControladorCobrancaLocal;
import gcom.cobranca.ControladorCobrancaLocalHome;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.faturamento.FaturamentoSituacaoHistorico;
import gcom.faturamento.FaturamentoSituacaoMotivo;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.*;
import gcom.util.filtro.ConectorOr;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * < <Descri��o da Classe>>
 * 
 * @author rodrigo
 */

public class ControladorTarifaSocialSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	private IRepositorioImovelTarifaSocial repositorioImovelTarifaSocial = null;

	SessionContext sessionContext;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @exception CreateException
	 *                Descri��o da exce��o
	 */
	public void ejbCreate() throws CreateException{

		repositorioImovelTarifaSocial = RepositorioImovelTarifaSocialHBM.getInstancia();
	}

	/**
	 * < <Descri��o do m�todo>>
	 */
	public void ejbRemove(){

	}

	/**
	 * < <Descri��o do m�todo>>
	 */
	public void ejbActivate(){

	}

	/**
	 * < <Descri��o do m�todo>>
	 */
	public void ejbPassivate(){

	}

	/**
	 * Seta o valor de sessionContext
	 * 
	 * @param sessionContext
	 *            O novo valor de sessionContext
	 */
	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;
	}

	/**
	 * Faz verifica��es da inser��o de dados de tarifa social de um im�vel
	 * [UC00054] Inserir Tarifa Social
	 * [FS0004] Verificar o cliente proprietario do imovel
	 * 
	 * @param idImovel
	 *            C�digo do Im�vel
	 * @throws ControladorException
	 */
	public void verificarProprietarioImovel(Integer idImovel) throws ControladorException{

		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		// Objetos que ser�o retornados pelo hibernate
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente.clienteTipo");

		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.FIM_RELACAO_MOTIVO));
		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO,
						ClienteRelacaoTipo.PROPRIETARIO));

		// Realiza uma pesquisa pelos parametros fornecidos
		Collection clienteImoveis = getControladorUtil().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

		// verifica se existe cliente propriet�rio
		if(clienteImoveis == null || clienteImoveis.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.tarifa_social.obrigatoria.existente.proprietario");
		}

		// Iterator para percorrer a cole��o de ClienteImoveis
		Iterator clienteImovelIterator = clienteImoveis.iterator();

		// Objeto ClienteImovel
		ClienteImovel clienteImovelObj = new ClienteImovel();

		while(clienteImovelIterator.hasNext()){

			clienteImovelObj = (ClienteImovel) clienteImovelIterator.next();

			Cliente cliente = clienteImovelObj.getCliente();

			if((cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica().shortValue() == 1)
							&& ((clienteImovelObj.getCliente().getRg() == null || clienteImovelObj.getCliente().getRg()
											.equalsIgnoreCase(""))

							&& (clienteImovelObj.getCliente().getCpf() == null || clienteImovelObj.getCliente().getCpf().equalsIgnoreCase(
											"")))){
				throw new ControladorException("atencao.proprietario.rg_cpf_nao_cadastrado");
			}

			if(cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica().shortValue() == 2 && cliente.getCnpj() == null){
				throw new ControladorException("atencao.proprietario.cnpj_nao_cadastrado");
			}

		}

	}

	/**
	 * Faz verifica��es da inser��o de dados de tarifa social de um im�vel
	 * 
	 * @author Deyverson
	 * @date 26/11/2008
	 * @param idImovel
	 *            C�digo do Im�vel
	 * @throws ControladorException
	 */
	public Cliente verificarUsuarioImovel(Integer idImovel) throws ControladorException{

		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		// Objetos que ser�o retornados pelo hibernate
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente.clienteTipo");

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.FIM_RELACAO_MOTIVO));
		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, ClienteRelacaoTipo.USUARIO));

		// Realiza uma pesquisa pelos parametros fornecidos
		Collection clienteImoveis = getControladorUtil().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

		// Verifica se a colecao clienteImoveis retornou vazia ou nula
		if(clienteImoveis == null || clienteImoveis.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.tarifa_social.obrigatoria.existente.usuario.tenham.cpf_rg");
		}

		// Iterator para percorrer a cole��o de ClienteImoveis
		Iterator clienteImovelIterator = clienteImoveis.iterator();

		// Objeto ClienteImovel
		ClienteImovel clienteImovelObj = new ClienteImovel();

		clienteImovelObj = (ClienteImovel) clienteImovelIterator.next();

		Cliente cliente = clienteImovelObj.getCliente();

		if((cliente.getRg() == null || clienteImovelObj.getCliente().getRg().equalsIgnoreCase(""))
						&& (cliente.getCpf() == null || clienteImovelObj.getCliente().getCpf().equalsIgnoreCase(""))){

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.tarifa_social.obrigatoria.existente.usuario.tenham.cpf_rg");
		}

		return cliente;
	}

	/**
	 * M�todo que verifica se o usuario esta cadastrado em outro imovel que
	 * esteja na tarifa social e verifica se ja esta cadastrado como usuario de
	 * algum imovel economia,
	 * Caso o idImovel seja diferente de nula ele verifa se o usuario esta
	 * cadastrado num imovel diferente do id passado.
	 * Caso o idImovelEconomia seja diferente de nula ele verifaca se o usuario
	 * esta cadastrado num imovel economia do idImovelEconomia passado.
	 * 
	 * @param idImovel
	 * @param idImovelEconomia
	 * @param idEconomiaAtual
	 * @param idClienteUsuario
	 */
	public void verificarClienteUsuarioEmOutroEconomia(Integer idImovel, Integer idImovelEconomia, Integer idClienteUsuario)
					throws ControladorException{

		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		// Adiciona novos parametros para pesquisa de usuarios existente em
		// tarifa social, na tabela CLIENTE_IMOVEL
		if(idImovel != null){
			filtroClienteImovel.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroClienteImovel.IMOVEL_ID, idImovel));
		}
		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_PERFIL, ImovelPerfil.TARIFA_SOCIAL));
		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_ID, idClienteUsuario));
		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, ClienteRelacaoTipo.USUARIO));
		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.FIM_RELACAO_MOTIVO));
		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

		// Carrega a cole��o com os registros encontrados
		Collection clienteImoveis = getControladorUtil().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

		if(clienteImoveis != null && !clienteImoveis.isEmpty()){
			// caso ele ja seja usuario de algum imovel
			ClienteImovel clienteImovelObj = (ClienteImovel) clienteImoveis.iterator().next();
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.usuario.ja_cadastrado_tarifasocial", null, clienteImovelObj.getImovel().getId()
							.toString());
		}

		// Instancia do filtro de ClienteImovelEconomia
		FiltroClienteImovelEconomia filtroClienteImovelEconomia = new FiltroClienteImovelEconomia();

		// Adiciona novos parametros para pesquisa de usuarios existente em
		// tarifa social, na tabela CLIENTE_IMOVEL_ECONOMIA
		if(idImovelEconomia != null){
			filtroClienteImovelEconomia
							.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroClienteImovelEconomia.ID, idImovelEconomia));
		}

		filtroClienteImovelEconomia.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.IMOVEL);

		filtroClienteImovelEconomia.adicionarParametro(new ParametroSimples(FiltroClienteImovelEconomia.IMOVEL_PERFIL,
						ImovelPerfil.TARIFA_SOCIAL));
		filtroClienteImovelEconomia.adicionarParametro(new ParametroSimples(FiltroClienteImovelEconomia.CLIENTE_ID, idClienteUsuario));
		filtroClienteImovelEconomia.adicionarParametro(new ParametroSimples(FiltroClienteImovelEconomia.CLIENTE_RELACAO_TIPO,
						ClienteRelacaoTipo.USUARIO));
		filtroClienteImovelEconomia.adicionarParametro(new ParametroNulo(FiltroClienteImovelEconomia.FIM_RELACAO_MOTIVO_ID));
		filtroClienteImovelEconomia.adicionarParametro(new ParametroNulo(FiltroClienteImovelEconomia.DATA_FIM_RELACAO));

		// Cole��o de Cliente_Imovel_Economia
		Collection clienteImovelEconomias = getControladorUtil().pesquisar(filtroClienteImovelEconomia,
						ClienteImovelEconomia.class.getName());

		if(clienteImovelEconomias != null && !clienteImovelEconomias.isEmpty()){

			ClienteImovelEconomia clienteImovelEconomiaObj = (ClienteImovelEconomia) clienteImovelEconomias.iterator().next();

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.usuario.ja_cadastrado_tarifasocial", null, clienteImovelEconomiaObj.getImovelEconomia()
							.getImovelSubcategoria().getComp_id().getImovel().getId().toString());
		}

	}

	/**
	 * Verificar os pr�-requisitos para o cadastramento de um im�vel na tarifa
	 * social
	 * 
	 * @param idImovel
	 *            C�digo do imovel
	 * @throws ControladorException
	 */
	public String[] verificarPreRequisitosCadastramentoTarifaSocial(Integer idImovel, Integer qtdEconomiasImovel)
					throws ControladorException{

		String[] retorno = new String[2];

		// Procura o Im�vel para fazer as verifica��es
		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("rota.faturamentoGrupo");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPerfil");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");

		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
		Imovel imovel = (Imovel) ((List) getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName())).get(0);

		// CATEGORIA RESIDENCIAL - E
		// Verificar se o im�vel possui alguma categoria que n�o seja
		// RESIDENCIAL
		FiltroImovelSubCategoria filtroImovelSubcategoria = new FiltroImovelSubCategoria();
		filtroImovelSubcategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, idImovel));
		filtroImovelSubcategoria.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovelSubCategoria.SUBCATEGORIA_CATEGORIA_ID,
						Categoria.RESIDENCIAL));
		Collection imovelCategoriasNaoResidencial = getControladorUtil().pesquisar(filtroImovelSubcategoria,
						ImovelSubcategoria.class.getName());

		// CATEGORIA RESIDENCIAL - E
		if(!imovelCategoriasNaoResidencial.isEmpty()){
			retorno[0] = "1";
			return retorno;
		}

		Short validaQuantidadeEconomiaTarifaSocial = null;
		try{
			validaQuantidadeEconomiaTarifaSocial = Short.valueOf(ParametroCadastro.P_VALIDA_QUANTIDADE_ECONOMIA_TARIFA_SOCIAL.executar());
		}catch(ControladorException e){
			throw new ActionServletException("erro.parametro.sistema.inexistente" + " - P_VALIDA_QUANTIDADE_ECONOMIA_TARIFA_SOCIAL.");
		}

		if(validaQuantidadeEconomiaTarifaSocial.equals(ConstantesSistema.SIM) && qtdEconomiasImovel > ConstantesSistema.SIM.intValue()){
			retorno[0] = "16";
			return retorno;
		}

		// SUBCATEGORIA CADA DE VERANEIO - E
		// Verificar se o im�vel possui alguma categoria que n�o seja
		// SUBCATEGORIA CADA DE VERANEIO - E
		filtroImovelSubcategoria = new FiltroImovelSubCategoria();
		filtroImovelSubcategoria.limparListaParametros();
		filtroImovelSubcategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, idImovel));
		filtroImovelSubcategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.SUBCATEGORIA_CATEGORIA_ID,
						Categoria.RESIDENCIAL));
		filtroImovelSubcategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.CODIGO_TARIFA_SOCIAL, "E"));

		filtroImovelSubcategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelSubCategoria.SUBCATEGORIA);

		imovelCategoriasNaoResidencial = getControladorUtil().pesquisar(filtroImovelSubcategoria, ImovelSubcategoria.class.getName());

		// SUBCATEGORIA CADA DE VERANEIO - E
		if(!imovelCategoriasNaoResidencial.isEmpty()){
			retorno[0] = "2";

			retorno[1] = "";

			Iterator imovelCategoriasNaoResidencialIterator = imovelCategoriasNaoResidencial.iterator();

			while(imovelCategoriasNaoResidencialIterator.hasNext()){

				ImovelSubcategoria imovelSubcategoria = (ImovelSubcategoria) imovelCategoriasNaoResidencialIterator.next();

				if(!retorno[1].equals("")){
					retorno[1] = retorno[1] + ", " + imovelSubcategoria.getComp_id().getSubcategoria().getDescricao();
				}else{
					retorno[1] = imovelSubcategoria.getComp_id().getSubcategoria().getDescricao();
				}

			}

			return retorno;
		}

		boolean existeMedicaoHistoricoMesAtual = false;
		Integer anoMesReferenciaFaturamentoAnterior = null;
		Integer anoMesReferenciaFaturamento = null;

		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = null;

		FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
		filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, imovel.getId()));
		filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("hidrometroInstalacaoHistorico");
		filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaPerfil");

		Collection colecaoLigaAgua = getControladorUtil().pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName());

		LigacaoAgua ligacaoAgua = null;
		if(colecaoLigaAgua != null && !colecaoLigaAgua.isEmpty()){
			ligacaoAgua = (LigacaoAgua) (colecaoLigaAgua).iterator().next();
		}

		if(ligacaoAgua != null){
			hidrometroInstalacaoHistorico = ligacaoAgua.getHidrometroInstalacaoHistorico();
		}

		// Pesquisa a categoria Residencial
		FiltroCategoria filtroCategoria = new FiltroCategoria();

		filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, Categoria.RESIDENCIAL));

		Categoria categoriaResidencial = (Categoria) ((List) getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName()))
						.get(0);

		if(ParametroCadastro.P_INDICADOR_LIMITE_CONSUMO_TARIFA_SOCIAL.executar().equals(ConstantesSistema.SIM.toString())){
			// CONSUMO M�DIO MAIOR QUE 10M3 - E
			// Hidr�metro na liga��o
			if(hidrometroInstalacaoHistorico != null){

				// caso n�o tenha medicao historico no mes atual
				if(!existeMedicaoHistoricoMesAtual){

					// Monta o Medicao Tipo para obterConsumoMedioHidrometro
					MedicaoTipo medicaoTipoLigacaoAgua = new MedicaoTipo();

					medicaoTipoLigacaoAgua.setId(MedicaoTipo.LIGACAO_AGUA);

					Collection consumoAtual = null;

					try{
						consumoAtual = repositorioImovelTarifaSocial.pesquisarConsumoHistoricoImovel(imovel.getId());
					}catch(ErroRepositorioException ex){
						sessionContext.setRollbackOnly();
						throw new ControladorException("erro.sistema", ex);
					}

					if(consumoAtual != null && !consumoAtual.isEmpty()){
						Iterator iterator = consumoAtual.iterator();

						while(iterator.hasNext()){
							ConsumoHistorico consumoHistorico = (ConsumoHistorico) iterator.next();
							// Tarifa social s� � permitida para im�veis com consumo
							// m�dio por
							// economia dos �ltimos seis meses inferior a xm3
							if(!Util.isVazioOuBranco(categoriaResidencial.getConsumoMinimo())
											&& !Util.isVazioOuBranco(consumoHistorico.getConsumoMedio())
											&& !Util.isVazioOuBranco(imovel.getQuantidadeEconomias())
											&& new BigDecimal(categoriaResidencial.getConsumoMinimo().intValue()).compareTo(new BigDecimal(
															consumoHistorico.getConsumoMedio().intValue()).divide(new BigDecimal(imovel
															.getQuantidadeEconomias().intValue()))) < 0){
								sessionContext.setRollbackOnly();

								retorno[0] = "8";

								retorno[1] = categoriaResidencial.getConsumoMinimo().toString();

								return retorno;
							}
						}

					}

				}

			}
		}

		// Consumo Fixado Maior que xm�
		Integer consumoFixado = getControladorLigacaoAgua().pesquisarConsumoMinimoFixado(imovel.getId());

		if(consumoFixado != null){

			// Tarifa social s� � permitida para im�veis com consumo
			// m�nimo fixado inferior a xm3
			if(new BigDecimal(categoriaResidencial.getConsumoMinimo().intValue()).compareTo(new BigDecimal(consumoFixado.intValue())
							.divide(new BigDecimal(imovel.getQuantidadeEconomias().intValue()))) < 0){
				sessionContext.setRollbackOnly();

				retorno[0] = "9";

				retorno[1] = categoriaResidencial.getConsumoMinimo().toString();

				return retorno;
			}
		}

		// SUBCATEGORIA IGREJA, CHAFARIZ, TERRENO - T
		// Verificar se o im�vel possui alguma categoria que n�o seja
		// SUBCATEGORIA IGREJA, CHAFARIZ, TERRENO - T
		filtroImovelSubcategoria = new FiltroImovelSubCategoria();
		filtroImovelSubcategoria.limparListaParametros();
		filtroImovelSubcategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, idImovel));
		filtroImovelSubcategoria.adicionarCaminhoParaCarregamentoEntidade("comp_id.subcategoria");
		filtroImovelSubcategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.SUBCATEGORIA_CATEGORIA_ID,
						Categoria.RESIDENCIAL));
		filtroImovelSubcategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.CODIGO_TARIFA_SOCIAL, "T"));

		imovelCategoriasNaoResidencial = getControladorUtil().pesquisar(filtroImovelSubcategoria, ImovelSubcategoria.class.getName());

		// SUBCATEGORIA IGREJA, CHAFARIZ, TERRENO - T
		if(!imovelCategoriasNaoResidencial.isEmpty()){
			retorno[0] = "3";

			retorno[1] = "";

			Iterator imovelCategoriasNaoResidencialIterator = imovelCategoriasNaoResidencial.iterator();

			while(imovelCategoriasNaoResidencialIterator.hasNext()){

				ImovelSubcategoria imovelSubcategoria = (ImovelSubcategoria) imovelCategoriasNaoResidencialIterator.next();

				if(!retorno[1].equals("")){
					retorno[1] = retorno[1] + ", " + imovelSubcategoria.getComp_id().getSubcategoria().getDescricao();
				}else{
					retorno[1] = imovelSubcategoria.getComp_id().getSubcategoria().getDescricao();
				}

			}

			return retorno;
		}

		// PERFIL GRANDE CONSUMIDOR
		if(imovel.getImovelPerfil().getId().intValue() == ImovelPerfil.GRANDE.intValue()){

			retorno[0] = "4";

			return retorno;
		}

		// LIGA��O DIFERENTE DE LIGADO, SUPRIMIDO OU CORTADO - T

		// Caso a situa��o da liga��o de �gua do im�vel n�o seja ligada ou cortada
		// (LAST_ID com o valor diferente de ligado, ligado em revis�o, suprimido ou cortado)
		if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.LIGADO.intValue()
						&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.CORTADO.intValue()
						&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.CORTADO_PEDIDO.intValue()
						&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPRIMIDO.intValue()
						&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPRIMIDO_DEFINITIVO.intValue()
						&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPR_PARC.intValue()
						&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPR_PARC_PEDIDO.intValue()
						// Altera��o referente a OC0857313
						// .......................................................
						&& !imovel.getLigacaoAguaSituacao().getIndicadorFaturamentoSituacao().equals(LigacaoAguaSituacao.FATURAMENTO_ATIVO)
		// .......................................................
		){

			retorno[0] = "5";

			return retorno;
		}

		// LIGA��O CLANDESTINA - T

		// Caso o perfil da liga��o de �gua do im�vel seja clandestina
		// (LAPF_ID da tabela LIGACAO_AGUA com o valor igual a �clandestino�)
		if(ligacaoAgua != null && ligacaoAgua.getLigacaoAguaPerfil() != null
						&& ligacaoAgua.getLigacaoAguaPerfil().getId().equals(LigacaoAguaPerfil.AGUA_LIGACAO_CLANDESTINA)){

			retorno[0] = "15";

			return retorno;

		}

		// ANORMALIDADE DE LEITURA - T
		// Hidr�metro na liga��o
		if(hidrometroInstalacaoHistorico != null){
			// Anormalidade de leitura

			anoMesReferenciaFaturamento = imovel.getRota().getFaturamentoGrupo().getAnoMesReferencia();

			// Setar o m�s anterior da refer�ncia
			anoMesReferenciaFaturamentoAnterior = Util.subtrairData(anoMesReferenciaFaturamento);

			FiltroMedicaoHistorico filtroMedicaoHistorico = new FiltroMedicaoHistorico();

			// Objetos que ser�o retornados pelo hibernate
			filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeFaturamento");

			filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.LIGACAO_AGUA_ID, imovel.getId()));

			filtroMedicaoHistorico
							.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.MEDICAO_TIPO_ID, MedicaoTipo.LIGACAO_AGUA));

			filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO,
							anoMesReferenciaFaturamento, ConectorOr.CONECTOR_OR, 2));

			filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO,
							anoMesReferenciaFaturamentoAnterior));

			Collection medicao = (Collection) getControladorUtil().pesquisar(filtroMedicaoHistorico, MedicaoHistorico.class.getName());

			if(medicao != null && !medicao.isEmpty()){
				Iterator iterator = medicao.iterator();

				while(iterator.hasNext()){
					MedicaoHistorico medicaoHistorico = (MedicaoHistorico) iterator.next();
					existeMedicaoHistoricoMesAtual = true;
					if(medicaoHistorico.getLeituraAnormalidadeFaturamento() != null){
						// Foi detectada anomalidade

						if(medicaoHistorico.getLeituraAnormalidadeFaturamento().getIndicadorPerdaTarifaSocial().intValue() == ConstantesSistema.SIM
										.intValue()){

							retorno[0] = "6";

							retorno[1] = medicaoHistorico.getLeituraAnormalidadeFaturamento().getDescricao();

							return retorno;
						}
					}
				}
			}
		}

		// EXIST�NCIA DE D�BITOS DO CLIENTE - T

		// O sistema verifica se existem d�bitos para o Cliente do im�vel ou para cada um dos
		// clientes usu�rios das economias, caso o im�vel possua mais de uma economia.
		boolean existeDebito = false;

		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		// Objetos que ser�o retornados pelo hibernate
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente.clienteTipo");

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.FIM_RELACAO_MOTIVO));

		// Realiza uma pesquisa pelos parametros fornecidos
		// caso o im�vel possua uma economia
		Collection<ClienteImovel> clienteImoveis = getControladorUtil().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

		if(!Util.isVazioOrNulo(clienteImoveis)){
			for(ClienteImovel clienteImovel : clienteImoveis){
				int clienteDebito = this.verificarExistenciaDebitosCliente(clienteImovel.getCliente().getId());

				if(clienteDebito == 7){
					existeDebito = true;
				}
			}
		}

		// caso o im�vel possua mais de uma economia
		Collection<Cliente> colecaoClienteImovelEconomia = this.pesquisarClientesUsuariosImovelEconomia(idImovel);

		if(!Util.isVazioOrNulo(colecaoClienteImovelEconomia)){
			for(Cliente clienteImovelEconomia : colecaoClienteImovelEconomia){
				int clienteDebito = -1;

				if(!Util.isVazioOuBranco(clienteImovelEconomia.getCliente())){
					clienteDebito = this.verificarExistenciaDebitosCliente(clienteImovelEconomia.getCliente().getId());
				}

				if(clienteDebito == 7){
					existeDebito = true;
				}
			}
		}

		/*
		 * Caso exista algum d�bito (retorno n�o nulo do UC0067), exibir a mensagem:
		 * �Tarifa Social s� � permitida para Clientes adimplentes�
		 * Esta situa��o exige a tramita��o do Registro de Atendimento. Confirma Tramita��o?
		 */
		if(existeDebito){

			retorno[0] = "7";

			return retorno;
		}

		// caso n�o ocorra nenhum situa��o
		retorno[0] = "-1";

		return retorno;
	}

	/**
	 * Atualiza um cliente no sistema
	 * 
	 * @param tarifaSocialCartaoTipo
	 *            Descri��o do par�metro
	 * @throws ControladorException
	 */
	public void atualizarTarifaSocialCartaoTipo(TarifaSocialCartaoTipo tarifaSocialCartaoTipo) throws ControladorException{

		// Cria o filtro
		FiltroTarifaSocialCartaoTipo filtroTarifaSocialCartaoTipo = new FiltroTarifaSocialCartaoTipo();

		// -------------Parte que atualiza um bairro na
		// base----------------------

		// Parte de Validacao com Timestamp

		// Seta o filtro para buscar o cliente na base
		filtroTarifaSocialCartaoTipo.adicionarParametro(new ParametroSimples(FiltroTarifaSocialCartaoTipo.ID, tarifaSocialCartaoTipo
						.getId()));

		// Procura o filtro na base
		TarifaSocialCartaoTipo tarifaSocialCartaoTipoNaBase = (TarifaSocialCartaoTipo) ((List) (getControladorUtil().pesquisar(
						filtroTarifaSocialCartaoTipo, TarifaSocialCartaoTipo.class.getName()))).get(0);

		// Verificar se o cliente j� foi atualizado por outro usu�rio
		// durante
		// esta atualiza��o
		if(tarifaSocialCartaoTipoNaBase.getUltimaAlteracao().after(tarifaSocialCartaoTipo.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// Atualiza a data de �ltima altera��o
		tarifaSocialCartaoTipo.setUltimaAlteracao(new Date());

		// Atualiza o cliente
		getControladorUtil().atualizar(tarifaSocialCartaoTipo);

		// -------------Fim da parte que atualiza um cliente na
		// base---------------
	}

	/**
	 * Atualiza um tarifaSocialDadoEconomia
	 * 
	 * @param tarifaSocialDadoEconomia
	 *            Descri��o do par�metro
	 * @throws ControladorException
	 */
	public void atualizarTarifaSocialDadoEconomia(TarifaSocialDadoEconomia tarifaSocialDadoEconomia) throws ControladorException{

		// Cria o filtro
		FiltroTarifaSocialDadoEconomia filtroTarifaSocialDadoEconomia = new FiltroTarifaSocialDadoEconomia();

		// -------------Parte que atualiza um bairro na
		// base----------------------

		// Parte de Validacao com Timestamp

		// Seta o filtro para buscar o cliente na base
		filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroSimples(FiltroTarifaSocialDadoEconomia.ID, tarifaSocialDadoEconomia
						.getId()));

		// Procura o filtro na base
		TarifaSocialDadoEconomia tarifaSocialDadoEconomiaNaBase = (TarifaSocialDadoEconomia) ((List) (getControladorUtil().pesquisar(
						filtroTarifaSocialDadoEconomia, TarifaSocialDadoEconomia.class.getName()))).get(0);

		// Verificar se o registro j� foi atualizado por outro usu�rio
		// durante
		// esta atualiza��o
		if(tarifaSocialDadoEconomiaNaBase.getUltimaAlteracao().after(tarifaSocialDadoEconomia.getUltimaAlteracao())){
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// Atualiza a data de �ltima altera��o
		tarifaSocialDadoEconomia.setUltimaAlteracao(new Date());

		// Atualiza o cliente
		getControladorUtil().atualizar(tarifaSocialDadoEconomia);

		// -------------Fim da parte que atualiza um cliente na
		// base---------------
	}

	/**
	 * Verificar o preenchimento dos campos para uma economia
	 * 
	 * @param clienteImovel
	 *            Descri��o do par�metro
	 * @param numeroCartaoSocial
	 *            Descri��o do par�metro
	 * @param dataValidadeCartaoSocial
	 *            Descri��o do par�metro
	 * @param numeroParcelasCartaoSocial
	 *            Descri��o do par�metro
	 * @param valorRendaFamiliar
	 *            Descri��o do par�metro
	 * @throws ControladorException
	 */
	public String[] verificarPreenchimentoInserirDadosTarifaSocial(Long numeroCelpe, BigDecimal areaConstruida, BigDecimal numeroIPTU,
					Integer idImovel, String numeroCartaoSocial, String dataValidadeCartaoSocial, String numeroParcelasCartaoSocial,
					Integer consumoMedio, BigDecimal valorRendaFamiliar, String tarifaSocialCartaoTipo, String tipoRenda)
					throws ControladorException{

		String[] retorno = new String[2];

		FiltroTarifaSocialCartaoTipo filtroTarifaSocialCartaoTipo = new FiltroTarifaSocialCartaoTipo();
		boolean dadosCartaoSocialPreenchidos = false;

		// Verifica se o usu�rio deixou de informar os dados do Cart�o do
		// Programa Social
		if((numeroCartaoSocial != null && !numeroCartaoSocial.trim().equals(""))
						|| (numeroParcelasCartaoSocial != null && !numeroParcelasCartaoSocial.trim().equals(""))
						|| (dataValidadeCartaoSocial != null && !dataValidadeCartaoSocial.trim().equals(""))
						|| (tarifaSocialCartaoTipo != null && !tarifaSocialCartaoTipo.equals(String
										.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))){

			if(numeroCartaoSocial == null || numeroCartaoSocial.trim().equals("")){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.preencher.tarifa_social.numero");
			}else if(tarifaSocialCartaoTipo == null
							|| tarifaSocialCartaoTipo.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.preencher.tarifa_social.tipo_cartao");
			}else{
				// [FS0018 � Validar n�mero do Cart�o do Programa Social]
				// V�lida o d�gito verificador do cart�o social
				if(!Util.isVazioOuBranco(numeroCartaoSocial)){
					this.validarNumeroCartaoProgramaSocial(numeroCartaoSocial);
				}

				filtroTarifaSocialCartaoTipo.adicionarParametro(new ParametroSimples(FiltroTarifaSocialCartaoTipo.ID,
								tarifaSocialCartaoTipo));

				Collection colecaoTarifaSocialCartaoTipo = getControladorUtil().pesquisar(filtroTarifaSocialCartaoTipo,
								TarifaSocialCartaoTipo.class.getName());

				TarifaSocialCartaoTipo objetoCartaTipo = null;
				if(colecaoTarifaSocialCartaoTipo == null || colecaoTarifaSocialCartaoTipo.isEmpty()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.pesquisa.tarifa_social.tipo_cartao.inexistente");
				}else{
					objetoCartaTipo = (TarifaSocialCartaoTipo) Util.retonarObjetoDeColecao(colecaoTarifaSocialCartaoTipo);

					if((dataValidadeCartaoSocial == null || dataValidadeCartaoSocial.trim().equals(""))
									&& objetoCartaTipo.getIndicadorValidade().equals(
													TarifaSocialCartaoTipo.INDICADOR_EXISTENCIA_VALIDADE_SIM)){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.preencher.tarifa_social.data.validade");
					}else if((numeroParcelasCartaoSocial == null || numeroParcelasCartaoSocial.trim().equals(""))
									&& objetoCartaTipo.getNumeroMesesAdesao() != null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.preencher.tarifa_social.numero.parcelas");
					}else{
						dadosCartaoSocialPreenchidos = true;
					}
				}
			}
		}

		// Verifica o preenchimento dos campos referentes a renda familiar
		if(dadosCartaoSocialPreenchidos){
			if(valorRendaFamiliar.intValue() != 0
							|| (tipoRenda != null && !tipoRenda.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))){

				if(valorRendaFamiliar.intValue() == 0){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.preencher.tarifa_social.valor_renda_familiar");
				}else if(tipoRenda == null || tipoRenda.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.preencher.tarifa_social.tipo_renda");
				}

			}
		}else{

			if(valorRendaFamiliar.intValue() == 0){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.preencher.tarifa_social.valor_renda_familiar");
			}else if(tipoRenda == null || tipoRenda.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.preencher.tarifa_social.tipo_renda");
			}

		}

		// ==========================================================
		// NOVA VERS�O
		// ==========================================================

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		// Valor da Renda
		// Caso o valor da renda tenha sido informado e seja maior que o
		// sal�rio m�nimo
		// Caso o valor da renda tenha sido informado e seja maior que um o sal�rio m�nimo
		// multiplicado pelo fator �P_FATOR_SALARIO_MINIMO_TARIFA_SOCIAL�

		String parametroFatorSalarioMinimo = ParametroCadastro.P_FATOR_SALARIO_MINIMO_TARIFA_SOCIAL.executar();

		if(valorRendaFamiliar != null
						&& (valorRendaFamiliar.doubleValue() > (sistemaParametro.getValorSalarioMinimo().doubleValue() * Double.valueOf(
										parametroFatorSalarioMinimo).doubleValue()))){

			retorno[0] = "10";
			retorno[1] = Util.formatarMoedaReal(sistemaParametro.getValorSalarioMinimo().multiply(
							new BigDecimal(parametroFatorSalarioMinimo)));

			return retorno;

		}

		// [FS0015] - Verificar informa��o do n�mero do contrato da companhia
		// el�trica
		// if(consumoMedio != null && consumoMedio != 0 && numeroCelpe == null){
		//
		// sessionContext.setRollbackOnly();
		// throw new ControladorException("atencao.preencher.tarifa_social.numero_contrato_celpe");
		// }

		// Consumo Energia
		/*
		 * Caso o consumo m�dio de energia do im�vel ou da economia do im�vel
		 * seja superior ao consumo m�dio de energia permitido para o
		 * cadastramento na tarifa sociual
		 */
		if(consumoMedio != null && consumoMedio != 0 && consumoMedio > sistemaParametro.getConsumoEnergiaMaximoTarifaSocial().intValue()){

			retorno[0] = "11";
			retorno[1] = sistemaParametro.getConsumoEnergiaMaximoTarifaSocial() + "";

			return retorno;
		}

		// 1 - O usu�rio n�o informou os dados do cart�o do programa social
		if(!dadosCartaoSocialPreenchidos){

			if(valorRendaFamiliar.intValue() == 0){

				retorno[0] = "12";
				retorno[1] = "" + sistemaParametro.getAreaMaximaTarifaSocial();

				return retorno;
			}
			// 2 - O valor da renda foi informado e o tipo � renda COMPROVADA
			else if(!RendaTipo.COMPROVADA.equals(new Integer(tipoRenda))){

				/*
				 * 3 - O valor da renda foi informado e o tipo � renda DECLARADA
				 * e existir a informa��o da �rea constru�da sendo menor ou
				 * igual ao m�ximo permitido
				 */
				if(RendaTipo.DECLARADA.equals(new Integer(tipoRenda))
								&& (areaConstruida == null || areaConstruida.intValue() > sistemaParametro.getAreaMaximaTarifaSocial()
												.intValue())){
					retorno[0] = "12";
					retorno[1] = "" + sistemaParametro.getAreaMaximaTarifaSocial();

					return retorno;
				}
			}
		}else{
			// [FS0014] - Verificar duplicidade do cart�o do programa social
			Long numeroCartaoProgramaSocial = new Long(numeroCartaoSocial);
			TarifaSocialCartaoTipo objetoTarifaSocialCartaoTipo = new TarifaSocialCartaoTipo();
			objetoTarifaSocialCartaoTipo.setId(new Integer(tarifaSocialCartaoTipo));

			this.verificarDuplicidadeCartaoProgramaSocial(numeroCartaoProgramaSocial, objetoTarifaSocialCartaoTipo, idImovel);
		}

		retorno[0] = "9";
		retorno[1] = "";

		return retorno;

		// ==========================================================
		// FIM NOVA VERS�O
		// ==========================================================

	}

	/**
	 * Verificar o preenchimento dos campos para uma economia
	 * 
	 * @param clienteImovel
	 *            Descri��o do par�metro
	 * @param numeroCartaoSocial
	 *            Descri��o do par�metro
	 * @param dataValidadeCartaoSocial
	 *            Descri��o do par�metro
	 * @param numeroParcelasCartaoSocial
	 *            Descri��o do par�metro
	 * @param valorRendaFamiliar
	 *            Descri��o do par�metro
	 * @throws ControladorException
	 */

	public String[] verificarPreenchimentoInserirDadosTarifaSocialMultiplas(Long numeroCelpe, BigDecimal areaConstruida,
					BigDecimal numeroIPTU, Integer idImovelEconomia, String numeroCartaoSocial, String dataValidadeCartaoSocial,
					String numeroParcelasCartaoSocial, Integer consumoMedio, BigDecimal valorRendaFamiliar, String tarifaSocialCartaoTipo,
					String tipoRenda) throws ControladorException{

		String[] retorno = new String[2];

		FiltroTarifaSocialCartaoTipo filtroTarifaSocialCartaoTipo = new FiltroTarifaSocialCartaoTipo();
		boolean dadosCartaoSocialPreenchidos = false;

		// Verifica se o usu�rio deixou de informar os dados do Cart�o do
		// Programa Social
		if((numeroCartaoSocial != null && !numeroCartaoSocial.trim().equals(""))
						|| (numeroParcelasCartaoSocial != null && !numeroParcelasCartaoSocial.trim().equals(""))
						|| (dataValidadeCartaoSocial != null && !dataValidadeCartaoSocial.trim().equals(""))
						|| (tarifaSocialCartaoTipo != null && !tarifaSocialCartaoTipo.equals(String
										.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))){

			if(numeroCartaoSocial == null || numeroCartaoSocial.trim().equals("")){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.preencher.tarifa_social.numero");
			}else if(tarifaSocialCartaoTipo == null
							|| tarifaSocialCartaoTipo.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.preencher.tarifa_social.tipo_cartao");
			}else{
				// [FS0018 � Validar n�mero do Cart�o do Programa Social]
				// V�lida o d�gito verificador do cart�o social
				if(!Util.isVazioOuBranco(numeroCartaoSocial)){
					this.validarNumeroCartaoProgramaSocial(numeroCartaoSocial);
				}

				filtroTarifaSocialCartaoTipo.adicionarParametro(new ParametroSimples(FiltroTarifaSocialCartaoTipo.ID,
								tarifaSocialCartaoTipo));

				Collection colecaoTarifaSocialCartaoTipo = getControladorUtil().pesquisar(filtroTarifaSocialCartaoTipo,
								TarifaSocialCartaoTipo.class.getName());

				TarifaSocialCartaoTipo objetoCartaTipo = null;
				if(colecaoTarifaSocialCartaoTipo == null || colecaoTarifaSocialCartaoTipo.isEmpty()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.pesquisa.tarifa_social.tipo_cartao.inexistente");
				}else{
					objetoCartaTipo = (TarifaSocialCartaoTipo) Util.retonarObjetoDeColecao(colecaoTarifaSocialCartaoTipo);

					if((dataValidadeCartaoSocial == null || dataValidadeCartaoSocial.trim().equals(""))
									&& objetoCartaTipo.getIndicadorValidade().equals(
													TarifaSocialCartaoTipo.INDICADOR_EXISTENCIA_VALIDADE_SIM)){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.preencher.tarifa_social.data.validade");
					}else if((numeroParcelasCartaoSocial == null || numeroParcelasCartaoSocial.trim().equals(""))
									&& objetoCartaTipo.getNumeroMesesAdesao() != null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.preencher.tarifa_social.numero.parcelas");
					}else{
						dadosCartaoSocialPreenchidos = true;
					}
				}
			}
		}

		// Verifica o preenchimento dos campos referentes a renda familiar
		if(dadosCartaoSocialPreenchidos){
			if(valorRendaFamiliar.intValue() != 0
							|| (tipoRenda != null && !tipoRenda.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))){

				if(valorRendaFamiliar.intValue() == 0){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.preencher.tarifa_social.valor_renda_familiar");
				}else if(tipoRenda == null || tipoRenda.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.preencher.tarifa_social.tipo_renda");
				}

			}
		}else{

			if(valorRendaFamiliar.intValue() == 0){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.preencher.tarifa_social.valor_renda_familiar");
			}else if(tipoRenda == null || tipoRenda.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.preencher.tarifa_social.tipo_renda");
			}

		}

		// ==========================================================
		// NOVA VERS�O
		// ==========================================================

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		// Valor da Renda
		// Caso o valor da renda tenha sido informado e seja maior que o
		// sal�rio m�nimo
		// Caso o valor da renda tenha sido informado e seja maior que um o sal�rio m�nimo
		// multiplicado pelo fator �P_FATOR_SALARIO_MINIMO_TARIFA_SOCIAL�

		String parametroFatorSalarioMinimo = ParametroCadastro.P_FATOR_SALARIO_MINIMO_TARIFA_SOCIAL.executar();

		if(valorRendaFamiliar != null
						&& (valorRendaFamiliar.doubleValue() > (sistemaParametro.getValorSalarioMinimo().doubleValue() * Double.valueOf(
										parametroFatorSalarioMinimo).doubleValue()))){

			retorno[0] = "10";
			retorno[1] = Util.formatarMoedaReal(sistemaParametro.getValorSalarioMinimo().multiply(
							new BigDecimal(parametroFatorSalarioMinimo)));

			return retorno;

		}

		// [FS0015] - Verificar informa��o do n�mero do contrato da companhia
		// el�trica
		if(consumoMedio != null && consumoMedio != 0 && numeroCelpe == null){

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.preencher.tarifa_social.numero_contrato_celpe");
		}

		// Consumo Energia
		/*
		 * Caso o consumo m�dio de energia do im�vel ou da economia do im�vel
		 * seja superior ao consumo m�dio de energia permitido para o
		 * cadastramento na tarifa sociual
		 */
		if(consumoMedio != null && consumoMedio != 0 && consumoMedio > sistemaParametro.getConsumoEnergiaMaximoTarifaSocial().intValue()){

			retorno[0] = "11";
			retorno[1] = sistemaParametro.getConsumoEnergiaMaximoTarifaSocial() + "";

			return retorno;

		}

		// 1 - O usu�rio n�o informou os dados do cart�o do programa social
		if(!dadosCartaoSocialPreenchidos){

			if(valorRendaFamiliar.intValue() == 0){

				retorno[0] = "12";
				retorno[1] = "" + sistemaParametro.getAreaMaximaTarifaSocial();

				return retorno;

			}
			// 2 - O valor da renda foi informado e o tipo � renda COMPROVADA
			else if(!RendaTipo.COMPROVADA.equals(new Integer(tipoRenda))){

				/*
				 * 3 - O valor da renda foi informado e o tipo � renda DECLARADA
				 * e existir a informa��o da �rea constru�da sendo menor ou
				 * igual ao m�ximo permitido
				 */
				if(RendaTipo.DECLARADA.equals(new Integer(tipoRenda))
								&& (areaConstruida == null || areaConstruida.intValue() > sistemaParametro.getAreaMaximaTarifaSocial()
												.intValue())){

					retorno[0] = "12";
					retorno[1] = "" + sistemaParametro.getAreaMaximaTarifaSocial();

					return retorno;
				}
			}
		}else{

			// [FS0014] - Verificar duplicidade do cart�o do programa social
			Long numeroCartaoProgramaSocial = new Long(numeroCartaoSocial);
			TarifaSocialCartaoTipo objetoTarifaSocialCartaoTipo = new TarifaSocialCartaoTipo();
			objetoTarifaSocialCartaoTipo.setId(new Integer(tarifaSocialCartaoTipo));

			this.verificarDuplicidadeCartaoProgramaSocialImovelEconomia(numeroCartaoProgramaSocial, objetoTarifaSocialCartaoTipo,
							idImovelEconomia);
		}

		retorno[0] = "9";
		retorno[1] = "";

		return retorno;

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social [FS0014] - Verificar duplicidade
	 * do cart�o do programa social
	 * 
	 * @param numeroCartao
	 * @throws ControladorException
	 */
	public void verificarDuplicidadeCartaoProgramaSocial(Long numeroCartao, TarifaSocialCartaoTipo tipoCartao, Integer idImovel)
					throws ControladorException{

		FiltroTarifaSocialDadoEconomia filtroTarifaSocialDadoEconomia = new FiltroTarifaSocialDadoEconomia();

		filtroTarifaSocialDadoEconomia.adicionarCaminhoParaCarregamentoEntidade("imovel");

		filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroSimples(
						FiltroTarifaSocialDadoEconomia.NUMERO_CARTAO_PROGRAMA_SOCIAL, numeroCartao));

		filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroSimples(FiltroTarifaSocialDadoEconomia.TARIFA_SOCIAL_CARTAO_TIPO_ID,
						tipoCartao.getId()));

		filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroNulo(FiltroTarifaSocialDadoEconomia.DATA_EXCLUSAO));

		filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroTarifaSocialDadoEconomia.IMOVEL_ID,
						idImovel, ConectorOr.CONECTOR_OR, 2));

		filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroNulo(FiltroTarifaSocialDadoEconomia.IMOVEL_ID));

		Collection colecaoPesquisa = this.getControladorUtil().pesquisar(filtroTarifaSocialDadoEconomia,
						TarifaSocialDadoEconomia.class.getName());

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){

			TarifaSocialDadoEconomia resgatarImovel = (TarifaSocialDadoEconomia) Util.retonarObjetoDeColecao(colecaoPesquisa);
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.tarifa_social.numero_cartao_programa_social.existente", null, ""
							+ resgatarImovel.getImovel().getId());
		}
	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * [FS0018] � Validar N�mero Cart�o do Programa Social
	 * 
	 * @param numeroCartao
	 * @throws ControladorException
	 */
	public void validarNumeroCartaoProgramaSocial(String numeroCartaoSocial) throws ControladorException{

		// O sistema calcula o digito verificar do cart�o
		// [UC0261 � Obter D�gito Verificador M�dulo 11]
		Integer digitoModulo11 = Util.obterDigitoVerificadorModulo11(numeroCartaoSocial.substring(0, numeroCartaoSocial.length() - 1));

		// Caso o d�gito verificador encontrado seja diferente do d�gito verificador informado,
		// exibir a mensagem �N�mero de Cart�o do Programa Social inv�lido�
		// e retornar para o passo correspondente no fluxo principal.
		Integer digitoVerificadorInformado = Integer.valueOf(numeroCartaoSocial.substring(numeroCartaoSocial.length() - 1,
						numeroCartaoSocial.length()));

		if(!digitoModulo11.equals(digitoVerificadorInformado)){
			throw new ControladorException("atencao.tarifa_social.numero_cartao_programa_social.invalido");
		}

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social [FS0014] - Verificar duplicidade
	 * do cart�o do programa social
	 * 
	 * @param numeroCartao
	 * @throws ControladorException
	 */
	public void verificarDuplicidadeCartaoProgramaSocialImovelEconomia(Long numeroCartao, TarifaSocialCartaoTipo tipoCartao,
					Integer idImovelEconomia) throws ControladorException{

		FiltroTarifaSocialDadoEconomia filtroTarifaSocialDadoEconomia = new FiltroTarifaSocialDadoEconomia();

		filtroTarifaSocialDadoEconomia.adicionarCaminhoParaCarregamentoEntidade("imovel");

		filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroSimples(
						FiltroTarifaSocialDadoEconomia.NUMERO_CARTAO_PROGRAMA_SOCIAL, numeroCartao));

		filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroSimples(FiltroTarifaSocialDadoEconomia.TARIFA_SOCIAL_CARTAO_TIPO_ID,
						tipoCartao.getId()));

		filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroNulo(FiltroTarifaSocialDadoEconomia.DATA_EXCLUSAO));

		filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroSimplesDiferenteDe(
						FiltroTarifaSocialDadoEconomia.IMOVEL_ECONOMIA_ID, idImovelEconomia.intValue(), ConectorOr.CONECTOR_OR, 2));

		filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroNulo(FiltroTarifaSocialDadoEconomia.IMOVEL_ECONOMIA_ID));

		Collection colecaoPesquisa = this.getControladorUtil().pesquisar(filtroTarifaSocialDadoEconomia,
						TarifaSocialDadoEconomia.class.getName());

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){

			TarifaSocialDadoEconomia resgatarImovel = (TarifaSocialDadoEconomia) Util.retonarObjetoDeColecao(colecaoPesquisa);
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.tarifa_social.numero_cartao_programa_social.existente", null, ""
							+ resgatarImovel.getImovel().getId());
		}
	}

	/**
	 * Atualiza o perfil do im�vel para tarifa social
	 * 
	 * @param imovel
	 * @throws ControladorException
	 */
	public void atualizarImovelPerfilTarifaSocial(Imovel imovel, boolean recadastramento) throws ControladorException{

		// Atualizar o Perfil do Imovel
		ImovelPerfil imovelPerfil = new ImovelPerfil();
		imovelPerfil.setId(ImovelPerfil.TARIFA_SOCIAL);
		imovel.setImovelPerfil(imovelPerfil);

		// Inclui consumo tarifa para o im�vel com o valor "CONSUMO SOCIAL"
		ConsumoTarifa consumoTarifa = new ConsumoTarifa();
		String parametroTarifaSocial = ParametroCadastro.P_TARIFA_CONSUMO_TARIFA_SOCIAL.executar();
		Integer tarifaSocial = Integer.valueOf(parametroTarifaSocial);

		consumoTarifa.setId(tarifaSocial);
		imovel.setConsumoTarifa(consumoTarifa);

		// Recupera o par�metro Tarifa Consumo
		String parametroTarifaTemp = ParametroCadastro.P_TARIFA_CONSUMO_TARIFA_SOCIAL.executar();

		// Inclui consumo tarifa tempor�ria para o im�vel com o valor do par�metro
		ConsumoTarifa consumoTarifaTemporaria = new ConsumoTarifa();
		consumoTarifaTemporaria.setId(Integer.valueOf(parametroTarifaTemp));
		imovel.setConsumoTarifaTemporaria(consumoTarifaTemporaria);
		
		Integer validadeBeneficioTarifaSocial = null;
		try{
			validadeBeneficioTarifaSocial = Integer.valueOf(ParametroCadastro.P_VALIDADE_BENEFICIO_TARIFA_SOCIAL.executar());
		}catch(ControladorException e){
			throw new ActionServletException("erro.parametro.sistema.inexistente" + " - P_VALIDA_BENEFICIO_TARIFA_SOCIAL.");
		}
		
		imovel.setDataValidadeTarifaTemporaria(Util.somaMesData(new Date(), validadeBeneficioTarifaSocial, true));
		
		// Recupera o par�metro Tarifa Percentual de Esgoto
		String parametroPercentualEsgoto = ParametroCadastro.P_PERCENTUAL_ESGOTO_TARIFA_SOCIAL.executar();

		if(recadastramento){
			if(imovel.getLigacaoEsgotoSituacao() != null){
				// Inclui faturamento situa��o tipo para o im�vel com o valor "PERCENTUAL ESGOTO"
				FaturamentoSituacaoTipo faturamentoSituacaoTipo = new FaturamentoSituacaoTipo();
				faturamentoSituacaoTipo.setId(FaturamentoSituacaoTipo.PERCENTUAL_DE_ESGOTO);
	
				imovel.setFaturamentoSituacaoTipo(faturamentoSituacaoTipo);
			}
		}else{
			// Caso o im�vel esteja ligado de esgoto e exista percentual diferenciado para o
			// benef�cio
			// da tarifa social
			if(imovel.getLigacaoEsgotoSituacao() != null && imovel.getLigacaoEsgotoSituacao().getId().equals(LigacaoEsgotoSituacao.LIGADO)
							&& !Util.isVazioOuBrancoOuZero(parametroPercentualEsgoto)){
				// Inclui faturamento situa��o tipo para o im�vel com o valor "PERCENTUAL ESGOTO"
							FaturamentoSituacaoTipo faturamentoSituacaoTipo = new FaturamentoSituacaoTipo();
							faturamentoSituacaoTipo.setId(FaturamentoSituacaoTipo.PERCENTUAL_DE_ESGOTO);
	
							imovel.setFaturamentoSituacaoTipo(faturamentoSituacaoTipo);
			}
		}
		this.getControladorUtil().atualizar(imovel);
	}

	/**
	 * Atualiza o perfil do im�vel para tarifa social
	 * 
	 * @param imovel
	 * @throws ControladorException
	 */
	public void incluirFaturamentoSituacaoHistorico(Integer idImovel, Usuario usuarioLogado) throws ControladorException{

		// Procura o Im�vel para fazer as verifica��es
		FiltroImovel filtroImovel = new FiltroImovel();

		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));

		Collection colecaoImovel = this.getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

		if(!Util.isVazioOrNulo(colecaoImovel)){
			Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

			String parametroPercentualEsgoto = ParametroCadastro.P_PERCENTUAL_ESGOTO_TARIFA_SOCIAL.executar();

			// 10.4.Caso o im�vel esteja ligado de esgoto (LEST_ID = �ligado� da tabela IMOVEL)
			// e exista percentual diferenciado para o benef�cio da tarifa social
			// (PASI_VLPARAMETRO diferente de nulo e de zero (0) da tabela PARAMETRO_SISTEMA
			// com PASI_CDPARAMETRO = �P_PERCENTUAL_ESGOTO_TARIFA_SOCIAL�)
			if(imovel.getLigacaoEsgotoSituacao() != null && imovel.getLigacaoEsgotoSituacao().getId().equals(LigacaoEsgotoSituacao.LIGADO)
							&& !Util.isVazioOuBrancoOuZero(parametroPercentualEsgoto)){
				// inclui uma linha na tabela FATURAMENTO_SITUACAO_HISTORICO com os seguintes dados:

				// FTSH_ID Id gerado pelo sistema
				FaturamentoSituacaoHistorico faturamentoSituacaoHistorico = new FaturamentoSituacaoHistorico();

				// IMOV_ID Id do im�vel
				faturamentoSituacaoHistorico.setImovel(imovel);

				// FTSH_AMFATURAMENTOSITUACAOINICIO PARM_AMREFERENCIAFATURAMENTO da tabela
				// SISTEMA_PARAMETRO
				SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();

				if(sistemaParametros == null || sistemaParametros.getAnoMesFaturamento() == null){
					throw new ControladorException("atencao.naocadastrado", null, "Ano/M�s Faturamento Parametros Sistema");
				}

				faturamentoSituacaoHistorico.setAnoMesFaturamentoSituacaoInicio(sistemaParametros.getAnoMesFaturamento());

				// FTSH_AMFATURAMENTOSITUACAOFIM 99999
				faturamentoSituacaoHistorico.setAnoMesFaturamentoSituacaoFim(Integer.valueOf("999999"));

				// FTSH_AMFATURAMENTORETIRADA Nulo
				faturamentoSituacaoHistorico.setAnoMesFaturamentoRetirada(null);

				// FTST_ID da tabela FATURAMENTO_SITUACAO_TIPO com valor igual a �percentual de
				// esgoto�
				// Inclui faturamento situa��o tipo com o valor "PERCENTUAL ESGOTO"
				FaturamentoSituacaoTipo faturamentoSituacaoTipo = new FaturamentoSituacaoTipo();
				faturamentoSituacaoTipo.setId(FaturamentoSituacaoTipo.PERCENTUAL_DE_ESGOTO);

				faturamentoSituacaoHistorico.setFaturamentoSituacaoTipo(faturamentoSituacaoTipo);

				// FTSM_ID da tabela FATURAMENTO_SITUACAO_MOTIVO com valor igual a �benef�cio tarifa
				// social�
				FaturamentoSituacaoMotivo faturamentoSituacaoMotivo = new FaturamentoSituacaoMotivo();
				faturamentoSituacaoMotivo.setId(FaturamentoSituacaoMotivo.BENEFICIO_TARIFA_SOCIAL);

				faturamentoSituacaoHistorico.setFaturamentoSituacaoMotivo(faturamentoSituacaoMotivo);

				// FTSH_TMULTIMAALTERACAO Data e hora correntes
				faturamentoSituacaoHistorico.setUltimaAlteracao(Calendar.getInstance().getTime());

				// USUR_ID Usu�rio logado no sistema
				faturamentoSituacaoHistorico.setUsuario(usuarioLogado);

				// FTSH_NNVOLUME nulo
				faturamentoSituacaoHistorico.setVolume(null);

				// FTSH_PCESGOTO PASI_VLPARAMETRO da tabela PARAMETRO_SISTEMA
				// com PASI_CDPARAMETRO = �P_PERCENTUAL_ESGOTO_TARIFA_SOCIAL�
				faturamentoSituacaoHistorico.setPercentualEsgoto(new BigDecimal(parametroPercentualEsgoto));

				getControladorUtil().inserir(faturamentoSituacaoHistorico);
			}
		}
	}

	/**
	 * Atualiza o enquadramento de um imovel no regime de tarifa social
	 * 
	 * @param imovel
	 *            Descri��o do par�metro
	 * @param tarifaSocialDado
	 *            Descri��o do par�metro
	 * @param tarifaSocialDadoEconomia
	 *            Descri��o do par�metro
	 */
	public void atualizarDadosTarifaSocialImovel(TarifaSocialDadoEconomia tarifaSocialDadoEconomia) throws ControladorException{

		// Atualizar a tarifaSocialDadoEconomia
		getControladorUtil().atualizar(tarifaSocialDadoEconomia);

	}

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	private ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Retorna o valor de controladorImovel
	 * 
	 * @return O valor de controladorImovel
	 */
	private ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Retorna o valor de controladorMicroMedicao
	 * 
	 * @return O valor de controladorMicroMedicao
	 */
	private ControladorRegistroAtendimentoLocal getControladorRegistroAtendimento(){

		ControladorRegistroAtendimentoLocalHome localHome = null;
		ControladorRegistroAtendimentoLocal local = null;

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorRegistroAtendimentoLocalHome) locator
							.getLocalHome(ConstantesJNDI.CONTROLADOR_REGISTRO_ATENDIMENTO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Retorna o valor de controladorLigacaoAgua
	 * 
	 * @return O valor de controladorLigacaoAgua
	 */
	private ControladorLigacaoAguaLocal getControladorLigacaoAgua(){

		ControladorLigacaoAguaLocalHome localHome = null;
		ControladorLigacaoAguaLocal local = null;

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorLigacaoAguaLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_LIGACAO_AGUA_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Pesquisa uma cole��o de Tarifa Social Dado Economia.
	 * 
	 * @param filtroTarifaSocialDadoEconomia
	 *            Description of the Parameter
	 * @author Thiago
	 * @date 12/12/2005
	 * @return Description of the Return Value
	 */

	public Collection pesquisarTarifaSocialDadoEconomia(FiltroTarifaSocialDadoEconomia filtroTarifaSocialDadoEconomia)
					throws ControladorException{

		Collection tarifasocialDadoEconomeia = getControladorUtil().pesquisar(filtroTarifaSocialDadoEconomia,
						TarifaSocialDadoEconomia.class.getName());
		/*
		 * if (!tarifasocialDadoEconomeia.isEmpty()) { Iterator it =
		 * tarifasocialDadoEconomeia.iterator(); TarifaSocialDadoEconomia tarifa =
		 * (TarifaSocialDadoEconomia) it.next();
		 * System.out.print('r'); FiltroClienteImovel filtroClienteImovel = new
		 * FiltroClienteImovel(); filtroClienteImovel.adicionarParametro(new
		 * ParametroSimples(FiltroClienteImovel.IMOVEL_ID,tarifa.getTarifaSocialDado().getId()));
		 * Collection clienteImovel =
		 * this.getControladorUtil().pesquisarClienteImovel(filtroClienteImovel); }
		 */

		return tarifasocialDadoEconomeia;
	}

	/**
	 * M�todo que remover o imover da tarifa social
	 * 
	 * @param idImovel
	 * @param idMotivoTarifaSocial
	 * @throws ControladorException
	 */
	public void removerImovelTarfiaSocial(Integer idImovel, Integer idMotivoTarifaSocial) throws ControladorException{

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPerfil");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("tarifaSocialDado");
		Collection coll = this.getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

		Imovel imovel = null;
		if(coll != null && !coll.isEmpty()){
			imovel = (Imovel) coll.iterator().next();

			imovel.getImovelPerfil().setId(ImovelPerfil.NORMAL);

			getControladorUtil().atualizar(imovel);

			TarifaSocialExclusaoMotivo tarifaSocialExclusaoMotivo = new TarifaSocialExclusaoMotivo();
			tarifaSocialExclusaoMotivo.setId(idMotivoTarifaSocial);
		}
	}

	/**
	 * M�todo que pesquisa uma tarifa social com o parametros do fitlro passado
	 * 
	 * @param filtroClienteImovel
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarImovelTarfiaSocial(FiltroClienteImovel filtroClienteImovel, Integer numeroPagina)
					throws ControladorException{

		try{
			return repositorioImovelTarifaSocial.pesquisarImovelTarfiaSocial(filtroClienteImovel, numeroPagina);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * M�todo que pesquisa a quantidade de tarifa social
	 * 
	 * @author Rafael Santos
	 * @since 05/09/2006
	 * @param filtroClienteImovel
	 * @return
	 * @throws ControladorException
	 */
	public int pesquisarQuantidadeImovelTarfiaSocial(FiltroClienteImovel filtroClienteImovel) throws ControladorException{

		try{
			return repositorioImovelTarifaSocial.pesquisarQuantidadeImovelTarfiaSocial(filtroClienteImovel);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Retorna o valor de controladorCobranca
	 * 
	 * @return O valor de controladorCobranca
	 */
	private ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Pesquisa as Tarifas Sociais Dado Economia pelo id do Im�vel carregando a
	 * Tarifa Social Revisao Motivo
	 * Autor: Rafael Corr�a
	 * Data: 27/12/2006
	 */
	public Collection pesquisarTarifaSocialDadoEconomia(Integer idImovel) throws ControladorException{

		try{
			return repositorioImovelTarifaSocial.pesquisarTarifaSocialDadoEconomia(idImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Pesquisa as Tarifas Sociais Dado Economia pelo id do Im�vel carregando a
	 * Tarifa Social Revisao Motivo
	 * Autor: Rafael Corr�a
	 * Data: 27/12/2006
	 */
	public Collection pesquisarTarifaSocialDadoEconomiaImovelEconomia(Integer idImovelEconomia) throws ControladorException{

		try{
			return repositorioImovelTarifaSocial.pesquisarTarifaSocialDadoEconomiaImovelEconomia(idImovelEconomia);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Verifica se o cliente usu�rio do im�vel j� est� relacionado em outro
	 * im�vel na tarifa social
	 * Autor: Rafael Corr�a
	 * Data: 02/01/2007
	 */
	public Collection verificarClienteCadastradoTarifaSocial(Integer idCliente) throws ControladorException{

		Collection colecaoImovel = new ArrayList();

		Collection colecaoIdsImovel = null;

		try{
			colecaoIdsImovel = repositorioImovelTarifaSocial.verificarClienteCadastradoTarifaSocial(idCliente);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoIdsImovel != null && !colecaoIdsImovel.isEmpty()){

			Iterator colecaoIdsImovelIterator = colecaoIdsImovel.iterator();

			while(colecaoIdsImovelIterator.hasNext()){

				Integer idImovel = (Integer) colecaoIdsImovelIterator.next();

				Imovel imovel = new Imovel();
				imovel.setId(idImovel);

				colecaoImovel.add(imovel);

			}

		}

		return colecaoImovel;

	}

	/**
	 * [UC0009] - Manter Cliente
	 * Verifica se o cliente usu�rio est� na tarifa social
	 * Autor: Rafael Corr�a
	 * Data: 16/02/2007
	 */
	public Collection verificarClienteUsuarioCadastradoTarifaSocial(Integer idCliente) throws ControladorException{

		Collection colecaoImovel = new ArrayList();

		Collection colecaoIdsImovel = null;

		try{
			colecaoIdsImovel = repositorioImovelTarifaSocial.verificarClienteUsuarioCadastradoTarifaSocial(idCliente);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoIdsImovel != null && !colecaoIdsImovel.isEmpty()){

			Iterator colecaoIdsImovelIterator = colecaoIdsImovel.iterator();

			while(colecaoIdsImovelIterator.hasNext()){

				Integer idImovel = (Integer) colecaoIdsImovelIterator.next();

				Imovel imovel = new Imovel();
				imovel.setId(idImovel);

				colecaoImovel.add(imovel);

			}

		}

		return colecaoImovel;

	}

	/**
	 * [UC0069] - Manter Dados Tarifa Social
	 * Verifica se o cliente usu�rio do im�vel j� est� relacionado em outro
	 * im�vel na tarifa social
	 * Autor: Rafael Corr�a
	 * Data: 30/01/2007
	 */
	public Collection verificarClienteCadastradoManterTarifaSocialUmaEconomia(Integer idCliente, Integer idImovel)
					throws ControladorException{

		Collection colecaoImovel = new ArrayList();

		Collection colecaoIdsImovel = null;

		try{
			colecaoIdsImovel = repositorioImovelTarifaSocial.verificarClienteCadastradoManterTarifaSocialUmaEconomia(idCliente, idImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoIdsImovel != null && !colecaoIdsImovel.isEmpty()){

			Iterator colecaoIdsImovelIterator = colecaoIdsImovel.iterator();

			while(colecaoIdsImovelIterator.hasNext()){

				Integer idImovelPesquisado = (Integer) colecaoIdsImovelIterator.next();

				Imovel imovel = new Imovel();
				imovel.setId(idImovelPesquisado);

				colecaoImovel.add(imovel);

			}

		}

		return colecaoImovel;

	}

	/**
	 * [UC0069] - Manter Dados Tarifa Social
	 * Verifica se o cliente usu�rio da economia do im�vel j� est� relacionado
	 * em outro im�vel na tarifa social
	 * Autor: Rafael Corr�a
	 * Data: 30/01/2007
	 */
	public Collection verificarClienteCadastradoManterTarifaSocialMultiplasEconomias(Integer idCliente, Integer idImovelEconomia)
					throws ControladorException{

		Collection colecaoImovel = new ArrayList();

		Collection colecaoIdsImovel = null;

		try{
			colecaoIdsImovel = repositorioImovelTarifaSocial.verificarClienteCadastradoManterTarifaSocialMultiplasEconomias(idCliente,
							idImovelEconomia);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoIdsImovel != null && !colecaoIdsImovel.isEmpty()){

			Iterator colecaoIdsImovelIterator = colecaoIdsImovel.iterator();

			while(colecaoIdsImovelIterator.hasNext()){

				Integer idImovelPesquisado = (Integer) colecaoIdsImovelIterator.next();

				Imovel imovel = new Imovel();
				imovel.setId(idImovelPesquisado);

				colecaoImovel.add(imovel);

			}

		}

		return colecaoImovel;

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Verifica se o mesmo cliente est� vinculado a mais de uma economia como
	 * usu�rio
	 * Autor: Rafael Corr�a
	 * Data: 03/01/2007
	 */
	public int pesquisarClienteImovelEconomiaCount(Integer idImovel, Integer idCliente) throws ControladorException{

		try{
			return this.repositorioImovelTarifaSocial.pesquisarClienteImovelEconomiaCount(idImovel, idCliente);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Retorna os clientes usu�rios das economias do im�vel
	 * Autor: Rafael Corr�a
	 * Data: 03/01/2007
	 */
	public Collection pesquisarClientesUsuariosImovelEconomia(Integer idImovel) throws ControladorException{

		Collection colecaoClientes = new ArrayList();

		Collection colecaoDadosClientesImovelEconomia = null;

		try{
			colecaoDadosClientesImovelEconomia = repositorioImovelTarifaSocial.pesquisarClientesUsuariosImovelEconomia(idImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoDadosClientesImovelEconomia != null && !colecaoDadosClientesImovelEconomia.isEmpty()){

			Iterator colecaoDadosClientesImovelEconomiaIterator = colecaoDadosClientesImovelEconomia.iterator();

			while(colecaoDadosClientesImovelEconomiaIterator.hasNext()){

				Integer idCliente = (Integer) colecaoDadosClientesImovelEconomiaIterator.next();

				Cliente cliente = new Cliente();

				cliente.setId(idCliente);

				colecaoClientes.add(cliente);

			}

		}

		return colecaoClientes;

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Verifica se o mesmo cliente est� vinculado a mais de uma economia como
	 * usu�rio
	 * Autor: Rafael Corr�a
	 * Data: 03/01/2007
	 */
	public int verificarExistenciaDebitosCliente(Integer idCliente) throws ControladorException{

		// data inicio vencimento debito
		Calendar dataInicioVencimentoDebito = new GregorianCalendar();
		dataInicioVencimentoDebito.set(Calendar.YEAR, new Integer("0001").intValue());
		dataInicioVencimentoDebito.set(Calendar.MONTH, 0);
		dataInicioVencimentoDebito.set(Calendar.DATE, new Integer("01").intValue());

		// data final de vencimento de debito
		Calendar dataFimVencimentoDebito = new GregorianCalendar();
		dataFimVencimentoDebito.add(Calendar.DATE, -45);

		ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = getControladorCobranca().obterDebitoImovelOuCliente(2, null,
						idCliente.toString(), null, "000101", "999912", dataInicioVencimentoDebito.getTime(),
						dataFimVencimentoDebito.getTime(), 1, 2, 2, 2, 1, 1, 2, null, null, null, null, null, ConstantesSistema.SIM,
						ConstantesSistema.SIM, ConstantesSistema.SIM, 2, null);

		boolean existeDebito = false;

		if(obterDebitoImovelOuClienteHelper != null){
			// contas
			if(obterDebitoImovelOuClienteHelper.getColecaoContasValores() != null
							&& !obterDebitoImovelOuClienteHelper.getColecaoContasValores().isEmpty()){
				existeDebito = true;
			}else
			// credito a realizar
			if(obterDebitoImovelOuClienteHelper.getColecaoCreditoARealizar() != null
							&& !obterDebitoImovelOuClienteHelper.getColecaoCreditoARealizar().isEmpty()){
				existeDebito = true;
			}else
			// debito a cobrar
			if(obterDebitoImovelOuClienteHelper.getColecaoDebitoACobrar() != null
							&& !obterDebitoImovelOuClienteHelper.getColecaoDebitoACobrar().isEmpty()){
				existeDebito = true;
			}else
			// guias pagamento
			if(obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores() != null
							&& !obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores().isEmpty()){
				existeDebito = true;
			}
		}

		// Se existir debito para o imovel
		if(existeDebito){
			return 7;
		}
		return -1;
	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Verifica se o cliente usu�rio est� vinculado na tarifa social a outro
	 * im�vel ou economia com motivo de revis�o que permita recadastramento
	 * Autor: Rafael Corr�a
	 * Data: 04/01/2007
	 */
	public Collection pesquisarClientesUsuarioExistenteTarifaSocial(Integer idCliente) throws ControladorException{

		Collection colecaoTarifaSocialDadoEconomia = new ArrayList();

		Collection colecaoDadosTarifaSocialDadoEconomia = null;

		try{
			colecaoDadosTarifaSocialDadoEconomia = repositorioImovelTarifaSocial.pesquisarClientesUsuarioExistenteTarifaSocial(idCliente);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoDadosTarifaSocialDadoEconomia != null && !colecaoDadosTarifaSocialDadoEconomia.isEmpty()){

			Iterator colecaoDadosTarifaSocialDadoEconomiaIterator = colecaoDadosTarifaSocialDadoEconomia.iterator();

			while(colecaoDadosTarifaSocialDadoEconomiaIterator.hasNext()){

				Object[] dadosTarifaSocial = (Object[]) colecaoDadosTarifaSocialDadoEconomiaIterator.next();

				TarifaSocialDadoEconomia tarifaSocialDadoEconomia = new TarifaSocialDadoEconomia();

				// Id do Im�vel
				if(dadosTarifaSocial[0] != null){
					Imovel imovel = new Imovel();
					imovel.setId((Integer) dadosTarifaSocial[0]);
					tarifaSocialDadoEconomia.setImovel(imovel);
				}

				// Descri��o do Motivo da Revis�o da Tarifa Social
				if(dadosTarifaSocial[1] != null){
					TarifaSocialRevisaoMotivo tarifaSocialRevisaoMotivo = new TarifaSocialRevisaoMotivo();
					tarifaSocialRevisaoMotivo.setDescricao((String) dadosTarifaSocial[1]);
					tarifaSocialDadoEconomia.setTarifaSocialRevisaoMotivo(tarifaSocialRevisaoMotivo);
				}

				// Id da Tarifa Social Dado Economia
				if(dadosTarifaSocial[2] != null){
					tarifaSocialDadoEconomia.setId((Integer) dadosTarifaSocial[2]);
				}

				colecaoTarifaSocialDadoEconomia.add(tarifaSocialDadoEconomia);

			}

		}

		return colecaoTarifaSocialDadoEconomia;

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Verificar se existe um motivo de exclus�o para o cliente que n�o permite
	 * recadastramento na tarifa social
	 * Autor: Rafael Corr�a
	 * Data: 05/01/2007
	 */
	public void verificarClienteMotivoExclusaoRecadastramento(Integer idCliente) throws ControladorException{

		Collection colecaoDadosExclusao = null;

		try{
			colecaoDadosExclusao = repositorioImovelTarifaSocial.verificarClienteMotivoExclusaoRecadastramento(idCliente);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoDadosExclusao != null && !colecaoDadosExclusao.isEmpty()){

			Iterator colecaoDadosExclusaoIterator = colecaoDadosExclusao.iterator();

			while(colecaoDadosExclusaoIterator.hasNext()){

				Short indicadorExclusaoPermiteRecadastramento = (Short) colecaoDadosExclusaoIterator.next();

				if(indicadorExclusaoPermiteRecadastramento == 2){
					throw new ControladorException("atencao.motivo_exclusao_cliente_nao_permite_recadastramento");
				}

			}
		}

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Retorna os cliente a partir do id do clienteImovelEconomia
	 * Autor: Rafael Corr�a
	 * Data: 08/01/2007
	 */
	public Integer pesquisarClienteImovelEconomia(Integer idClienteImovelEconomia) throws ControladorException{

		try{
			return this.repositorioImovelTarifaSocial.pesquisarClienteImovelEconomia(idClienteImovelEconomia);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0069] - Manter Dados Tarifa Social
	 * Pesquisa os dados da tarifa social e do cliente usu�rio
	 * Autor: Rafael Corr�a
	 * Data: 15/01/2007
	 */
	public Collection pesquisarDadosClienteTarifaSocial(Integer idImovel) throws ControladorException{

		Collection colecaoTarifaSocialHelper = new ArrayList();

		Collection colecaoDadosTarifaSocial = null;

		try{
			colecaoDadosTarifaSocial = repositorioImovelTarifaSocial.pesquisarDadosClienteTarifaSocial(idImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoDadosTarifaSocial != null && !colecaoDadosTarifaSocial.isEmpty()){

			Iterator colecaoDadosTarifaSocialIterator = colecaoDadosTarifaSocial.iterator();

			while(colecaoDadosTarifaSocialIterator.hasNext()){

				Object[] dadosTarifaSocial = (Object[]) colecaoDadosTarifaSocialIterator.next();

				TarifaSocialHelper tarifaSocialHelper = new TarifaSocialHelper();

				ClienteImovel clienteImovel = new ClienteImovel();

				TarifaSocialDadoEconomia tarifaSocialDadoEconomia = new TarifaSocialDadoEconomia();

				Imovel imovel = new Imovel();

				Cliente cliente = new Cliente();

				TarifaSocialCartaoTipo tarifaSocialCartaoTipo = null;

				RendaTipo rendaTipo = null;

				// Id do Tarifa Social Dado Economia
				if(dadosTarifaSocial[0] != null){
					tarifaSocialDadoEconomia.setId((Integer) dadosTarifaSocial[0]);
				}

				// Nome do Cliente
				if(dadosTarifaSocial[1] != null){
					cliente.setNome((String) dadosTarifaSocial[1]);
				}

				// Complemento Endere�o
				if(dadosTarifaSocial[2] != null){
					imovel.setComplementoEndereco((String) dadosTarifaSocial[2]);
				}

				// CPF
				if(dadosTarifaSocial[3] != null){
					cliente.setCpf((String) dadosTarifaSocial[3]);
				}

				// RG
				if(dadosTarifaSocial[4] != null){
					cliente.setRg((String) dadosTarifaSocial[4]);
				}

				// �rg�o Expedidor do RG
				if(dadosTarifaSocial[5] != null){
					OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();
					orgaoExpedidorRg.setDescricaoAbreviada((String) dadosTarifaSocial[5]);
					cliente.setOrgaoExpedidorRg(orgaoExpedidorRg);
				}

				// Unidade da Federa��o
				if(dadosTarifaSocial[6] != null){
					UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
					unidadeFederacao.setSigla((String) dadosTarifaSocial[6]);
					cliente.setUnidadeFederacao(unidadeFederacao);
				}

				// N�mero do Cart�o do Programa Social
				if(dadosTarifaSocial[7] != null){
					tarifaSocialDadoEconomia.setNumeroCartaoProgramaSocial((Long) dadosTarifaSocial[7]);
				}

				// Id do Tipo do Cart�o do Programa Social
				if(dadosTarifaSocial[8] != null){
					tarifaSocialCartaoTipo = new TarifaSocialCartaoTipo();
					tarifaSocialCartaoTipo.setId((Integer) dadosTarifaSocial[8]);
				}

				// Renda Familiar
				if(dadosTarifaSocial[9] != null){
					tarifaSocialDadoEconomia.setValorRendaFamiliar((BigDecimal) dadosTarifaSocial[9]);
				}

				// Id do Tipo da Renda
				if(dadosTarifaSocial[10] != null){
					rendaTipo = new RendaTipo();
					rendaTipo.setId((Integer) dadosTarifaSocial[10]);
				}

				// Motivo de Exclus�o
				if(dadosTarifaSocial[11] != null){
					TarifaSocialExclusaoMotivo tarifaSocialExclusaoMotivo = new TarifaSocialExclusaoMotivo();
					tarifaSocialExclusaoMotivo.setId((Integer) dadosTarifaSocial[11]);
					tarifaSocialExclusaoMotivo.setDescricao((String) dadosTarifaSocial[31]);
					tarifaSocialDadoEconomia.setTarifaSocialExclusaoMotivo(tarifaSocialExclusaoMotivo);
				}

				// Descri�ao do Tipo do Cart�o do Programa Social
				if(dadosTarifaSocial[12] != null){
					tarifaSocialCartaoTipo.setDescricao((String) dadosTarifaSocial[12]);
				}

				// Data de Validade do Cart�o
				if(dadosTarifaSocial[13] != null){
					tarifaSocialDadoEconomia.setDataValidadeCartao((Date) dadosTarifaSocial[13]);
				}

				// N�mero de Parcelas
				if(dadosTarifaSocial[14] != null){
					tarifaSocialDadoEconomia.setNumeroMesesAdesao((Short) dadosTarifaSocial[14]);
				}

				// N�mero do Contrato da Companhia El�trica
				if(dadosTarifaSocial[15] != null){
					imovel.setNumeroCelpe((Long) dadosTarifaSocial[15]);
				}

				// Consumo M�dio
				if(dadosTarifaSocial[16] != null){
					tarifaSocialDadoEconomia.setConsumoCelpe((Integer) dadosTarifaSocial[16]);
				}

				// N�mero do IPTU
				if(dadosTarifaSocial[17] != null){
					imovel.setNumeroIptu((BigDecimal) dadosTarifaSocial[17]);
				}

				// �rea Constru�da
				if(dadosTarifaSocial[18] != null){
					imovel.setAreaConstruida((BigDecimal) dadosTarifaSocial[18]);
				}

				// �rea Constru�da Faixa
				if(dadosTarifaSocial[19] != null){
					AreaConstruidaFaixa areaConstruidaFaixa = new AreaConstruidaFaixa();
					areaConstruidaFaixa.setId((Integer) dadosTarifaSocial[19]);
				}

				// Descri��o do Tipo da Renda
				if(dadosTarifaSocial[20] != null){
					rendaTipo.setDescricao((String) dadosTarifaSocial[20]);
				}

				// Motivo de Revis�o
				if(dadosTarifaSocial[21] != null){
					TarifaSocialRevisaoMotivo tarifaSocialRevisaoMotivo = new TarifaSocialRevisaoMotivo();
					tarifaSocialRevisaoMotivo.setId((Integer) dadosTarifaSocial[21]);
					tarifaSocialRevisaoMotivo.setDescricao((String) dadosTarifaSocial[32]);
					tarifaSocialDadoEconomia.setTarifaSocialRevisaoMotivo(tarifaSocialRevisaoMotivo);
				}

				// Id do Im�vel
				if(dadosTarifaSocial[22] != null){
					imovel.setId((Integer) dadosTarifaSocial[22]);
				}

				// Id do Munic�pio
				if(dadosTarifaSocial[23] != null){
					SetorComercial setorComercial = new SetorComercial();
					Municipio municipio = new Municipio();
					setorComercial.setId((Integer) dadosTarifaSocial[23]);
					municipio.setId((Integer) dadosTarifaSocial[24]);
					setorComercial.setMunicipio(municipio);
					imovel.setSetorComercial(setorComercial);
				}

				// Data de Exclus�o
				if(dadosTarifaSocial[25] != null){
					tarifaSocialDadoEconomia.setDataExclusao((Date) dadosTarifaSocial[25]);
				}

				// Data de Implanta��o
				if(dadosTarifaSocial[26] != null){
					tarifaSocialDadoEconomia.setDataImplantacao((Date) dadosTarifaSocial[26]);
				}

				// Data de Revis�o
				if(dadosTarifaSocial[27] != null){
					tarifaSocialDadoEconomia.setDataRevisao((Date) dadosTarifaSocial[27]);
				}

				// Quantidade de Recadastramentos
				if(dadosTarifaSocial[28] != null){
					tarifaSocialDadoEconomia.setQuantidadeRecadastramento((Short) dadosTarifaSocial[28]);
				}

				// Data do Recadastramento
				if(dadosTarifaSocial[29] != null){
					tarifaSocialDadoEconomia.setDataRecadastramento((Date) dadosTarifaSocial[29]);
				}

				// N�mero de Moradores
				if(dadosTarifaSocial[30] != null){
					imovel.setNumeroMorador((Short) dadosTarifaSocial[30]);
				}

				// �ltima Altera��o
				if(dadosTarifaSocial[33] != null){
					tarifaSocialDadoEconomia.setUltimaAlteracao((Date) dadosTarifaSocial[33]);
				}

				clienteImovel.setImovel(imovel);
				clienteImovel.setCliente(cliente);

				tarifaSocialDadoEconomia.setTarifaSocialCartaoTipo(tarifaSocialCartaoTipo);
				tarifaSocialDadoEconomia.setImovel(imovel);
				tarifaSocialDadoEconomia.setRendaTipo(rendaTipo);

				tarifaSocialHelper.setTarifaSocialDadoEconomia(tarifaSocialDadoEconomia);
				tarifaSocialHelper.setClienteImovel(clienteImovel);

				colecaoTarifaSocialHelper.add(tarifaSocialHelper);

			}

		}

		return colecaoTarifaSocialHelper;
	}

	/**
	 * [UC0069] - Manter Dados Tarifa Social
	 * Retorna a tarifa social a partir do seu id
	 * Autor: Rafael Corr�a
	 * Data: 16/01/2007
	 */
	public TarifaSocialDadoEconomia pesquisarTarifaSocial(Integer idTarifaSocial) throws ControladorException{

		try{
			return this.repositorioImovelTarifaSocial.pesquisarTarifaSocial(idTarifaSocial);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0069] - Manter Dados Tarifa Social
	 * Verifica se existe tarifa social para o im�vel que n�o tenha sido
	 * exclu�do
	 * Autor: Rafael Corr�a
	 * Data: 16/01/2007
	 */
	public Collection pesquisarTarifaSocialImovel(Integer idImovel) throws ControladorException{

		Collection colecaoTarifaSocial = new ArrayList();

		Collection colecaoIdsTarifaSocial = null;

		try{
			colecaoIdsTarifaSocial = repositorioImovelTarifaSocial.pesquisarTarifaSocialImovel(idImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoIdsTarifaSocial != null && !colecaoIdsTarifaSocial.isEmpty()){

			Iterator colecaoIdsTarifaSocialIterator = colecaoIdsTarifaSocial.iterator();

			while(colecaoIdsTarifaSocialIterator.hasNext()){

				TarifaSocialDadoEconomia tarifaSocialDadoEconomia = new TarifaSocialDadoEconomia();

				Integer idTarifaSocial = (Integer) colecaoIdsTarifaSocialIterator.next();

				tarifaSocialDadoEconomia.setId(idTarifaSocial);

				colecaoTarifaSocial.add(tarifaSocialDadoEconomia);

			}

		}

		return colecaoTarifaSocial;
	}

	/**
	 * [UC0069] - Manter Dados Tarifa Social
	 * [FS0008] - Verificar Preenchimento dos Campos
	 * Verificar o preenchimento dos campos para uma economia
	 * 
	 * @date 18/01/2007
	 * @author Rafael Corr�a
	 * @throws ControladorException
	 * @param clienteImovel
	 *            Descri��o do par�metro
	 * @param numeroCartaoSocial
	 *            Descri��o do par�metro
	 * @param dataValidadeCartaoSocial
	 *            Descri��o do par�metro
	 * @param numeroParcelasCartaoSocial
	 *            Descri��o do par�metro
	 * @param valorRendaFamiliar
	 *            Descri��o do par�metro
	 * @throws ControladorException
	 */
	public void verificarPreenchimentoManterDadosTarifaSocial(Long numeroCelpe, BigDecimal areaConstruida, BigDecimal numeroIPTU,
					Integer idImovel, String numeroCartaoSocial, String dataValidadeCartaoSocial, String numeroParcelasCartaoSocial,
					Integer consumoMedio, BigDecimal valorRendaFamiliar, String tarifaSocialCartaoTipo, String tipoRenda)
					throws ControladorException{

		FiltroTarifaSocialCartaoTipo filtroTarifaSocialCartaoTipo = new FiltroTarifaSocialCartaoTipo();
		boolean dadosCartaoSocialPreenchidos = false;

		// Verifica se o usu�rio deixou de informar os dados do Cart�o do
		// Programa Social
		if((numeroCartaoSocial != null && !numeroCartaoSocial.trim().equals(""))
						|| (numeroParcelasCartaoSocial != null && !numeroParcelasCartaoSocial.trim().equals(""))
						|| (dataValidadeCartaoSocial != null && !dataValidadeCartaoSocial.trim().equals(""))
						|| (tarifaSocialCartaoTipo != null && !tarifaSocialCartaoTipo.equals(String
										.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))){

			if(numeroCartaoSocial == null || numeroCartaoSocial.trim().equals("")){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.preencher.tarifa_social.numero");
			}else if(tarifaSocialCartaoTipo == null
							|| tarifaSocialCartaoTipo.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.preencher.tarifa_social.tipo_cartao");
			}else{
				// [FS0027 � Validar n�mero do Cart�o do Programa Social]
				// V�lida o d�gito verificador do cart�o social
				if(!Util.isVazioOuBranco(numeroCartaoSocial)){
					this.validarNumeroCartaoProgramaSocial(numeroCartaoSocial);
				}

				filtroTarifaSocialCartaoTipo.adicionarParametro(new ParametroSimples(FiltroTarifaSocialCartaoTipo.ID,
								tarifaSocialCartaoTipo));

				Collection colecaoTarifaSocialCartaoTipo = getControladorUtil().pesquisar(filtroTarifaSocialCartaoTipo,
								TarifaSocialCartaoTipo.class.getName());

				TarifaSocialCartaoTipo objetoCartaoTipo = null;
				if(colecaoTarifaSocialCartaoTipo == null || colecaoTarifaSocialCartaoTipo.isEmpty()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.pesquisa.tarifa_social.tipo_cartao.inexistente");
				}else{
					objetoCartaoTipo = (TarifaSocialCartaoTipo) Util.retonarObjetoDeColecao(colecaoTarifaSocialCartaoTipo);

					// Informar Data de Validade
					if((dataValidadeCartaoSocial == null || dataValidadeCartaoSocial.trim().equals(""))
									&& objetoCartaoTipo.getIndicadorValidade().equals(
													TarifaSocialCartaoTipo.INDICADOR_EXISTENCIA_VALIDADE_SIM)){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.preencher.tarifa_social.data.validade");
					}
					// Informar N�mero de Parcelas
					else if((numeroParcelasCartaoSocial == null || numeroParcelasCartaoSocial.trim().equals(""))
									&& objetoCartaoTipo.getNumeroMesesAdesao() != null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.preencher.tarifa_social.numero.parcelas");
					}
					// N�mero de Parcelas maior que N�mero de Meses Ades�o
					else if((numeroParcelasCartaoSocial != null && !numeroParcelasCartaoSocial.trim().equals(""))
									&& objetoCartaoTipo.getNumeroMesesAdesao() != null
									&& new Integer(numeroParcelasCartaoSocial).intValue() > objetoCartaoTipo.getNumeroMesesAdesao()
													.intValue()){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.numero.parcelas.maior.que.numero.maximo.meses.adesao", null,
										objetoCartaoTipo.getNumeroMesesAdesao().toString());
					}
					// N�o Informar Data de Validade
					else if((dataValidadeCartaoSocial != null && !dataValidadeCartaoSocial.trim().equals(""))
									&& objetoCartaoTipo.getIndicadorValidade().equals(
													TarifaSocialCartaoTipo.INDICADOR_EXISTENCIA_VALIDADE_NAO)){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.nao_preencher.tarifa_social.data.validade");
					}
					// N�o Informar N�mero de Parcelas
					else if((numeroParcelasCartaoSocial != null && !numeroParcelasCartaoSocial.trim().equals(""))
									&& objetoCartaoTipo.getNumeroMesesAdesao() == null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.nao_preencher.tarifa_social.numero.parcelas");
					}else{
						dadosCartaoSocialPreenchidos = true;
					}
				}
			}
		}

		// Verifica o preenchimento dos campos referentes a renda familiar
		if(!dadosCartaoSocialPreenchidos){
			if((valorRendaFamiliar == null || valorRendaFamiliar.intValue() == 0)
							|| (tipoRenda != null && !tipoRenda.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))){

				if(valorRendaFamiliar == null || valorRendaFamiliar.intValue() == 0){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.preencher.tarifa_social.valor_renda_familiar");
				}else if(tipoRenda == null || tipoRenda.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.preencher.tarifa_social.tipo_renda");
				}

			}
		}

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		// Valor da Renda
		// Caso o valor da renda tenha sido informado e seja maior que o
		// sal�rio m�nimo
		// Caso o valor da renda tenha sido informado e seja maior que um o sal�rio m�nimo
		// multiplicado pelo fator �P_FATOR_SALARIO_MINIMO_TARIFA_SOCIAL�

		String parametroFatorSalarioMinimo = ParametroCadastro.P_FATOR_SALARIO_MINIMO_TARIFA_SOCIAL.executar();

		if(valorRendaFamiliar != null
						&& (valorRendaFamiliar.doubleValue() > (sistemaParametro.getValorSalarioMinimo().doubleValue() * Double.valueOf(
										parametroFatorSalarioMinimo).doubleValue()))){

			String valorSalario = Util.formatarMoedaReal(sistemaParametro.getValorSalarioMinimo().multiply(
							new BigDecimal(parametroFatorSalarioMinimo)));

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.tarifa_social.renda_familiar.maior.salario_minimo.sem.encerramento", null, valorSalario);

		}

		// [FS0015] - Verificar informa��o do n�mero do contrato da companhia
		// el�trica
		// if(consumoMedio != null && consumoMedio != 0 && numeroCelpe == null){
		//
		// sessionContext.setRollbackOnly();
		// throw new ControladorException("atencao.preencher.tarifa_social.numero_contrato_celpe");
		// }

		// Consumo Energia
		/*
		 * Caso o consumo m�dio de energia do im�vel ou da economia do im�vel
		 * seja superior ao consumo m�dio de energia permitido para o
		 * cadastramento na tarifa sociual
		 */
		if(consumoMedio != null && consumoMedio != 0 && consumoMedio > sistemaParametro.getConsumoEnergiaMaximoTarifaSocial().intValue()){

			String valorMaximoEnergia = sistemaParametro.getConsumoEnergiaMaximoTarifaSocial().toString();

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.preencher.tarifa_social.valor.consumo_energia.sem.encerramento", null,
							valorMaximoEnergia);

		}

		// 1 - O usu�rio n�o informou os dados do cart�o do programa social
		if(!dadosCartaoSocialPreenchidos){

			if(valorRendaFamiliar.intValue() == 0){

				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.preencher.tarifa_social.requisitos", null, sistemaParametro
								.getAreaMaximaTarifaSocial().toString());
			}
			// 2 - O valor da renda foi informado e o tipo � renda COMPROVADA
			else if(!RendaTipo.COMPROVADA.equals(new Integer(tipoRenda))){

				/*
				 * 3 - O valor da renda foi informado e o tipo � renda DECLARADA
				 * e existir a informa��o da �rea constru�da sendo menor ou
				 * igual ao m�ximo permitido
				 */
				if(RendaTipo.DECLARADA.equals(new Integer(tipoRenda))
								&& (areaConstruida == null || areaConstruida.intValue() > sistemaParametro.getAreaMaximaTarifaSocial()
												.intValue())){

					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.preencher.tarifa_social.requisitos_sem_encerramento", null, sistemaParametro
									.getAreaMaximaTarifaSocial().toString());
				}
			}

		}else{

			// [FS0014] - Verificar duplicidade do cart�o do programa social
			Long numeroCartaoProgramaSocial = new Long(numeroCartaoSocial);
			TarifaSocialCartaoTipo objetoTarifaSocialCartaoTipo = new TarifaSocialCartaoTipo();
			objetoTarifaSocialCartaoTipo.setId(new Integer(tarifaSocialCartaoTipo));

			this.verificarDuplicidadeCartaoProgramaSocial(numeroCartaoProgramaSocial, objetoTarifaSocialCartaoTipo, idImovel);
		}

	}

	/**
	 * [UC0069] - Manter Dados Tarifa Social
	 * [FS0008] - Verificar Preenchimento dos Campos
	 * Verificar o preenchimento dos campos para m�ltiplas economias
	 * 
	 * @date 18/01/2007
	 * @author Rafael Corr�a
	 * @throws ControladorException
	 * @param clienteImovel
	 *            Descri��o do par�metro
	 * @param numeroCartaoSocial
	 *            Descri��o do par�metro
	 * @param dataValidadeCartaoSocial
	 *            Descri��o do par�metro
	 * @param numeroParcelasCartaoSocial
	 *            Descri��o do par�metro
	 * @param valorRendaFamiliar
	 *            Descri��o do par�metro
	 * @throws ControladorException
	 */
	public void verificarPreenchimentoManterDadosTarifaSocialMultiplasEconomias(Long numeroCelpe, BigDecimal areaConstruida,
					BigDecimal numeroIPTU, Integer idImovelEconomia, String numeroCartaoSocial, String dataValidadeCartaoSocial,
					String numeroParcelasCartaoSocial, Integer consumoMedio, BigDecimal valorRendaFamiliar, String tarifaSocialCartaoTipo,
					String tipoRenda) throws ControladorException{

		FiltroTarifaSocialCartaoTipo filtroTarifaSocialCartaoTipo = new FiltroTarifaSocialCartaoTipo();
		boolean dadosCartaoSocialPreenchidos = false;

		// Verifica se o usu�rio deixou de informar os dados do Cart�o do
		// Programa Social
		if((numeroCartaoSocial != null && !numeroCartaoSocial.trim().equals(""))
						|| (numeroParcelasCartaoSocial != null && !numeroParcelasCartaoSocial.trim().equals(""))
						|| (dataValidadeCartaoSocial != null && !dataValidadeCartaoSocial.trim().equals(""))
						|| (tarifaSocialCartaoTipo != null && !tarifaSocialCartaoTipo.equals(String
										.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))){

			if(numeroCartaoSocial == null || numeroCartaoSocial.trim().equals("")){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.preencher.tarifa_social.numero");
			}else if(tarifaSocialCartaoTipo == null
							|| tarifaSocialCartaoTipo.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.preencher.tarifa_social.tipo_cartao");
			}else{
				// [FS0027 � Validar n�mero do Cart�o do Programa Social]
				// V�lida o d�gito verificador do cart�o social
				if(!Util.isVazioOuBranco(numeroCartaoSocial)){
					this.validarNumeroCartaoProgramaSocial(numeroCartaoSocial);
				}

				filtroTarifaSocialCartaoTipo.adicionarParametro(new ParametroSimples(FiltroTarifaSocialCartaoTipo.ID,
								tarifaSocialCartaoTipo));

				Collection colecaoTarifaSocialCartaoTipo = getControladorUtil().pesquisar(filtroTarifaSocialCartaoTipo,
								TarifaSocialCartaoTipo.class.getName());

				TarifaSocialCartaoTipo objetoCartaoTipo = null;
				if(colecaoTarifaSocialCartaoTipo == null || colecaoTarifaSocialCartaoTipo.isEmpty()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.pesquisa.tarifa_social.tipo_cartao.inexistente");
				}else{
					objetoCartaoTipo = (TarifaSocialCartaoTipo) Util.retonarObjetoDeColecao(colecaoTarifaSocialCartaoTipo);

					// Informar Data de Validade
					if((dataValidadeCartaoSocial == null || dataValidadeCartaoSocial.trim().equals(""))
									&& objetoCartaoTipo.getIndicadorValidade().equals(
													TarifaSocialCartaoTipo.INDICADOR_EXISTENCIA_VALIDADE_SIM)){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.preencher.tarifa_social.data.validade");
					}
					// Informar N�mero de Parcelas
					else if((numeroParcelasCartaoSocial == null || numeroParcelasCartaoSocial.trim().equals(""))
									&& objetoCartaoTipo.getNumeroMesesAdesao() != null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.preencher.tarifa_social.numero.parcelas");
					}
					// N�mero de Parcelas maior que N�mero de Meses Ades�o
					else if((numeroParcelasCartaoSocial != null && !numeroParcelasCartaoSocial.trim().equals(""))
									&& objetoCartaoTipo.getNumeroMesesAdesao() != null
									&& new Integer(numeroParcelasCartaoSocial).intValue() > objetoCartaoTipo.getNumeroMesesAdesao()
													.intValue()){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.numero.parcelas.maior.que.numero.maximo.meses.adesao", null,
										objetoCartaoTipo.getNumeroMesesAdesao().toString());
					}
					// N�o Informar Data de Validade
					else if((dataValidadeCartaoSocial != null && !dataValidadeCartaoSocial.trim().equals(""))
									&& objetoCartaoTipo.getIndicadorValidade().equals(
													TarifaSocialCartaoTipo.INDICADOR_EXISTENCIA_VALIDADE_NAO)){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.nao_preencher.tarifa_social.data.validade");
					}
					// N�o Informar N�mero de Parcelas
					else if((numeroParcelasCartaoSocial != null && !numeroParcelasCartaoSocial.trim().equals(""))
									&& objetoCartaoTipo.getNumeroMesesAdesao() == null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.nao_preencher.tarifa_social.numero.parcelas");
					}else{
						dadosCartaoSocialPreenchidos = true;
					}
				}
			}
		}

		// Verifica o preenchimento dos campos referentes a renda familiar
		if(!dadosCartaoSocialPreenchidos){
			if((valorRendaFamiliar == null || valorRendaFamiliar.intValue() == 0)
							|| (tipoRenda != null && !tipoRenda.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))){

				if(valorRendaFamiliar == null || valorRendaFamiliar.intValue() == 0){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.preencher.tarifa_social.valor_renda_familiar");
				}else if(tipoRenda == null || tipoRenda.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.preencher.tarifa_social.tipo_renda");
				}

			}
		}

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		// Valor da Renda
		// Caso o valor da renda tenha sido informado e seja maior que o
		// sal�rio m�nimo
		// Caso o valor da renda tenha sido informado e seja maior que um o sal�rio m�nimo
		// multiplicado pelo fator �P_FATOR_SALARIO_MINIMO_TARIFA_SOCIAL�

		String parametroFatorSalarioMinimo = ParametroCadastro.P_FATOR_SALARIO_MINIMO_TARIFA_SOCIAL.executar();

		if(valorRendaFamiliar != null
						&& (valorRendaFamiliar.doubleValue() > (sistemaParametro.getValorSalarioMinimo().doubleValue() * Double.valueOf(
										parametroFatorSalarioMinimo).doubleValue()))){

			String valorSalario = Util.formatarMoedaReal(sistemaParametro.getValorSalarioMinimo().multiply(
							new BigDecimal(parametroFatorSalarioMinimo)));

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.tarifa_social.renda_familiar.maior.salario_minimo.sem.encerramento", null, valorSalario);

		}

		// [FS0015] - Verificar informa��o do n�mero do contrato da companhia
		// el�trica
		if(consumoMedio != null && consumoMedio != 0 && numeroCelpe == null){

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.preencher.tarifa_social.numero_contrato_celpe");
		}

		// Consumo Energia
		/*
		 * Caso o consumo m�dio de energia do im�vel ou da economia do im�vel
		 * seja superior ao consumo m�dio de energia permitido para o
		 * cadastramento na tarifa sociual
		 */
		if(consumoMedio != null && consumoMedio != 0 && consumoMedio > sistemaParametro.getConsumoEnergiaMaximoTarifaSocial().intValue()){

			String valorMaximoEnergia = sistemaParametro.getConsumoEnergiaMaximoTarifaSocial().toString();

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.preencher.tarifa_social.valor.consumo_energia.sem.encerramento", null,
							valorMaximoEnergia);

		}

		// 1 - O usu�rio n�o informou os dados do cart�o do programa social
		if(!dadosCartaoSocialPreenchidos){

			if(valorRendaFamiliar.intValue() == 0){

				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.preencher.tarifa_social.requisitos", null, sistemaParametro
								.getAreaMaximaTarifaSocial().toString());
			}
			// 2 - O valor da renda foi informado e o tipo � renda COMPROVADA
			else if(!RendaTipo.COMPROVADA.equals(new Integer(tipoRenda))){

				/*
				 * 3 - O valor da renda foi informado e o tipo � renda DECLARADA
				 * e existir a informa��o da �rea constru�da sendo menor ou
				 * igual ao m�ximo permitido
				 */
				if(RendaTipo.DECLARADA.equals(new Integer(tipoRenda))
								&& (areaConstruida == null || areaConstruida.intValue() > sistemaParametro.getAreaMaximaTarifaSocial()
												.intValue())){

					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.preencher.tarifa_social.requisitos_sem_encerramento", null, sistemaParametro
									.getAreaMaximaTarifaSocial().toString());
				}
			}

			/*
			 * Caso o usu�rio n�o informe os dados do cart�o do programa social,
			 * o tipo de renda n�o corresponda a renda comprovada e n�o existam
			 * as informa��es n�mero do IPTU e n�o exista uma ordem de servi�o
			 * de vistoria associado ao RA de cadastramentona tarifa social para
			 * o im�vel
			 */
			if(!RendaTipo.COMPROVADA.equals(new Integer(tipoRenda)) && numeroIPTU == null){

				// Collection colecaoOS = null;
				//
				// try {
				//
				// colecaoOS = repositorioImovelTarifaSocial
				// .verificarOSVistoriaImovelEconomia(idImovelEconomia);
				//
				// } catch (ErroRepositorioException ex) {
				// sessionContext.setRollbackOnly();
				// throw new ControladorException("erro.sistema", ex);
				// }
				//
				// if (colecaoOS == null || colecaoOS.isEmpty()) {
				//
				// sessionContext.setRollbackOnly();
				// throw new ControladorException(
				// "atencao.preencher.tarifa_social.valor.numero_celpe");
				//
				// }
			}
		}else{

			// [FS0014] - Verificar duplicidade do cart�o do programa social
			Long numeroCartaoProgramaSocial = new Long(numeroCartaoSocial);
			TarifaSocialCartaoTipo objetoTarifaSocialCartaoTipo = new TarifaSocialCartaoTipo();
			objetoTarifaSocialCartaoTipo.setId(new Integer(tarifaSocialCartaoTipo));

			this.verificarDuplicidadeCartaoProgramaSocialImovelEconomia(numeroCartaoProgramaSocial, objetoTarifaSocialCartaoTipo,
							idImovelEconomia);
		}

	}

	/**
	 * [UC0069] - Manter Dados Tarifa Social
	 * Retorna os clientes do im�vel
	 * Autor: Rafael Corr�a
	 * Data: 19/01/2007
	 */
	public Collection pesquisarClientesImovelTarifaSocial(Integer idImovel) throws ControladorException{

		Collection colecaoClienteImovel = new ArrayList();

		Collection colecaoDadosClienteImovel = null;

		try{
			colecaoDadosClienteImovel = repositorioImovelTarifaSocial.pesquisarClientesImovelTarifaSocial(idImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoDadosClienteImovel != null && !colecaoDadosClienteImovel.isEmpty()){

			Iterator colecaoDadosClienteImovelIterator = colecaoDadosClienteImovel.iterator();

			while(colecaoDadosClienteImovelIterator.hasNext()){

				Object[] dadosClienteImovel = (Object[]) colecaoDadosClienteImovelIterator.next();

				ClienteImovel clienteImovel = new ClienteImovel();

				Imovel imovel = new Imovel();

				Cliente cliente = new Cliente();

				ClienteRelacaoTipo clienteRelacaoTipo = new ClienteRelacaoTipo();

				// Id do Cliente
				if(dadosClienteImovel[0] != null){
					cliente.setId((Integer) dadosClienteImovel[0]);
				}

				// Nome do Cliente
				if(dadosClienteImovel[1] != null){
					cliente.setNome((String) dadosClienteImovel[1]);
				}

				// Id do Tipo da Rela��o
				if(dadosClienteImovel[2] != null){
					clienteRelacaoTipo.setId((Integer) dadosClienteImovel[2]);
				}

				// Descri��o do Tipo da Rela��o
				if(dadosClienteImovel[3] != null){
					clienteRelacaoTipo.setDescricao((String) dadosClienteImovel[3]);
				}

				// CPF
				if(dadosClienteImovel[4] != null){
					cliente.setCpf((String) dadosClienteImovel[4]);
				}

				// RG
				if(dadosClienteImovel[5] != null){
					cliente.setRg((String) dadosClienteImovel[5]);
				}

				// �rg�o Expedidor do RG
				if(dadosClienteImovel[6] != null){
					OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();
					orgaoExpedidorRg.setDescricaoAbreviada((String) dadosClienteImovel[6]);
					cliente.setOrgaoExpedidorRg(orgaoExpedidorRg);
				}

				// Unidade da Federa��o
				if(dadosClienteImovel[7] != null){
					UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
					unidadeFederacao.setSigla((String) dadosClienteImovel[7]);
					cliente.setUnidadeFederacao(unidadeFederacao);
				}

				// Data de In�cio da Rela��o
				if(dadosClienteImovel[8] != null){
					clienteImovel.setDataInicioRelacao((Date) dadosClienteImovel[8]);
				}

				// Id do Cliente Im�vel
				if(dadosClienteImovel[9] != null){
					clienteImovel.setId((Integer) dadosClienteImovel[9]);
				}

				// CNPJ do Cliente
				if(dadosClienteImovel[10] != null){
					cliente.setCnpj((String) dadosClienteImovel[10]);
				}

				imovel.setId(idImovel);

				clienteImovel.setImovel(imovel);
				clienteImovel.setCliente(cliente);
				clienteImovel.setClienteRelacaoTipo(clienteRelacaoTipo);

				colecaoClienteImovel.add(clienteImovel);

			}

		}

		return colecaoClienteImovel;
	}

	/**
	 * [UC0069] - Manter Dados Tarifa Social
	 * Retorna os clientes do im�vel
	 * Autor: Rafael Corr�a
	 * Data: 19/01/2007
	 */
	public Collection pesquisarClientesImovelEconomiaTarifaSocial(Integer idImovelEconomia) throws ControladorException{

		Collection colecaoClienteImovelEconomia = new ArrayList();

		Collection colecaoDadosClienteImovelEconomia = null;

		try{
			colecaoDadosClienteImovelEconomia = repositorioImovelTarifaSocial.pesquisarClientesImovelEconomiaTarifaSocial(idImovelEconomia);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoDadosClienteImovelEconomia != null && !colecaoDadosClienteImovelEconomia.isEmpty()){

			Iterator colecaoDadosClienteImovelEconomiaIterator = colecaoDadosClienteImovelEconomia.iterator();

			while(colecaoDadosClienteImovelEconomiaIterator.hasNext()){

				Object[] dadosClienteImovelEconomia = (Object[]) colecaoDadosClienteImovelEconomiaIterator.next();

				ClienteImovelEconomia clienteImovelEconomia = new ClienteImovelEconomia();

				ImovelEconomia imovelEconomia = new ImovelEconomia();

				Cliente cliente = new Cliente();

				ClienteRelacaoTipo clienteRelacaoTipo = new ClienteRelacaoTipo();

				// Id do Cliente
				if(dadosClienteImovelEconomia[0] != null){
					cliente.setId((Integer) dadosClienteImovelEconomia[0]);
				}

				// Nome do Cliente
				if(dadosClienteImovelEconomia[1] != null){
					cliente.setNome((String) dadosClienteImovelEconomia[1]);
				}

				// Id do Tipo da Rela��o
				if(dadosClienteImovelEconomia[2] != null){
					clienteRelacaoTipo.setId((Integer) dadosClienteImovelEconomia[2]);
				}

				// Descri��o do Tipo da Rela��o
				if(dadosClienteImovelEconomia[3] != null){
					clienteRelacaoTipo.setDescricao((String) dadosClienteImovelEconomia[3]);
				}

				// CPF
				if(dadosClienteImovelEconomia[4] != null){
					cliente.setCpf((String) dadosClienteImovelEconomia[4]);
				}

				// RG
				if(dadosClienteImovelEconomia[5] != null){
					cliente.setRg((String) dadosClienteImovelEconomia[5]);
				}

				// �rg�o Expedidor do RG
				if(dadosClienteImovelEconomia[6] != null){
					OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();
					orgaoExpedidorRg.setDescricaoAbreviada((String) dadosClienteImovelEconomia[6]);
					cliente.setOrgaoExpedidorRg(orgaoExpedidorRg);
				}

				// Unidade da Federa��o
				if(dadosClienteImovelEconomia[7] != null){
					UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
					unidadeFederacao.setSigla((String) dadosClienteImovelEconomia[7]);
					cliente.setUnidadeFederacao(unidadeFederacao);
				}

				// Data de In�cio da Rela��o
				if(dadosClienteImovelEconomia[8] != null){
					clienteImovelEconomia.setDataInicioRelacao((Date) dadosClienteImovelEconomia[8]);
				}

				// Id do Cliente Im�vel Economia
				if(dadosClienteImovelEconomia[9] != null){
					clienteImovelEconomia.setId((Integer) dadosClienteImovelEconomia[9]);
				}

				// CNPJ do Cliente
				if(dadosClienteImovelEconomia[10] != null){
					cliente.setCnpj((String) dadosClienteImovelEconomia[10]);
				}

				imovelEconomia.setId(idImovelEconomia);

				clienteImovelEconomia.setImovelEconomia(imovelEconomia);
				clienteImovelEconomia.setCliente(cliente);
				clienteImovelEconomia.setClienteRelacaoTipo(clienteRelacaoTipo);

				colecaoClienteImovelEconomia.add(clienteImovelEconomia);

			}

		}

		return colecaoClienteImovelEconomia;
	}

	/**
	 * [UC0069] - Manter Dados Tarifa Social
	 * Pesquisa o cliente pelo seu id carregando o seu tipo
	 * Autor: Rafael Corr�a
	 * Data: 22/01/2007
	 */
	public Cliente pesquisarClienteComClienteTipoTarifaSocial(Integer idCliente) throws ControladorException{

		Cliente cliente = null;

		Collection colecaoDadosCliente = null;

		try{
			colecaoDadosCliente = repositorioImovelTarifaSocial.pesquisarClienteComClienteTipoTarifaSocial(idCliente);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoDadosCliente != null && !colecaoDadosCliente.isEmpty()){

			Iterator colecaoDadosClienteIterator = colecaoDadosCliente.iterator();

			while(colecaoDadosClienteIterator.hasNext()){

				Object[] dadosCliente = (Object[]) colecaoDadosClienteIterator.next();

				cliente = new Cliente();

				// Id do Cliente
				if(dadosCliente[0] != null){
					cliente.setId((Integer) dadosCliente[0]);
				}

				// Nome do Cliente
				if(dadosCliente[1] != null){
					cliente.setNome((String) dadosCliente[1]);
				}

				// CPF
				if(dadosCliente[2] != null){
					cliente.setCpf((String) dadosCliente[2]);
				}

				// RG
				if(dadosCliente[3] != null){
					cliente.setRg((String) dadosCliente[3]);
				}

				// CNPJ
				if(dadosCliente[4] != null){
					cliente.setCnpj((String) dadosCliente[4]);
				}

				// Data de Nascimento
				if(dadosCliente[5] != null){
					cliente.setDataNascimento((Date) dadosCliente[5]);
				}

				// Cliente Tipo
				if(dadosCliente[6] != null){
					ClienteTipo clienteTipo = new ClienteTipo();
					clienteTipo.setId((Integer) dadosCliente[6]);
					clienteTipo.setIndicadorPessoaFisicaJuridica((Short) dadosCliente[7]);
					cliente.setClienteTipo(clienteTipo);
				}

				// Data de Emiss�o do RG
				if(dadosCliente[8] != null){
					cliente.setDataEmissaoRg((Date) dadosCliente[8]);
				}

				// Org�o Expedidor RG
				if(dadosCliente[9] != null){
					OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();
					orgaoExpedidorRg.setDescricaoAbreviada((String) dadosCliente[9]);
					cliente.setOrgaoExpedidorRg(orgaoExpedidorRg);
				}

				// Unidade da Federa��o
				if(dadosCliente[10] != null){
					UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
					unidadeFederacao.setSigla((String) dadosCliente[10]);
					cliente.setUnidadeFederacao(unidadeFederacao);
				}
			}

		}

		return cliente;
	}

	/**
	 * [UC0069] - Manter Dados Tarifa Social
	 * Pesquisa os dados da tarifa social e do cliente usu�rio para cada
	 * economia
	 * Autor: Rafael Corr�a
	 * Data: 25/01/2007
	 */
	public Collection pesquisarDadosClienteEconomiaTarifaSocial(Integer idImovel) throws ControladorException{

		Collection colecaoTarifaSocialHelper = new ArrayList();

		Collection colecaoDadosTarifaSocial = null;

		try{
			colecaoDadosTarifaSocial = repositorioImovelTarifaSocial.pesquisarDadosClienteEconomiaTarifaSocial(idImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoDadosTarifaSocial != null && !colecaoDadosTarifaSocial.isEmpty()){

			Iterator colecaoDadosTarifaSocialIterator = colecaoDadosTarifaSocial.iterator();

			while(colecaoDadosTarifaSocialIterator.hasNext()){

				Object[] dadosTarifaSocial = (Object[]) colecaoDadosTarifaSocialIterator.next();

				TarifaSocialHelper tarifaSocialHelper = new TarifaSocialHelper();

				ClienteImovelEconomia clienteImovelEconomia = new ClienteImovelEconomia();

				TarifaSocialDadoEconomia tarifaSocialDadoEconomia = new TarifaSocialDadoEconomia();

				ImovelEconomia imovelEconomia = new ImovelEconomia();

				Imovel imovel = new Imovel();

				Cliente cliente = new Cliente();

				TarifaSocialCartaoTipo tarifaSocialCartaoTipo = null;

				RendaTipo rendaTipo = null;

				// Id do Tarifa Social Dado Economia
				if(dadosTarifaSocial[0] != null){
					tarifaSocialDadoEconomia.setId((Integer) dadosTarifaSocial[0]);
				}

				// Nome do Cliente
				if(dadosTarifaSocial[1] != null){
					cliente.setNome((String) dadosTarifaSocial[1]);
				}

				// Complemento Endere�o
				if(dadosTarifaSocial[2] != null){
					imovelEconomia.setComplementoEndereco((String) dadosTarifaSocial[2]);
				}

				// CPF
				if(dadosTarifaSocial[3] != null){
					cliente.setCpf((String) dadosTarifaSocial[3]);
				}

				// RG
				if(dadosTarifaSocial[4] != null){
					cliente.setRg((String) dadosTarifaSocial[4]);
				}

				// �rg�o Expedidor do RG
				if(dadosTarifaSocial[5] != null){
					OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();
					orgaoExpedidorRg.setDescricaoAbreviada((String) dadosTarifaSocial[5]);
					cliente.setOrgaoExpedidorRg(orgaoExpedidorRg);
				}

				// Unidade da Federa��o
				if(dadosTarifaSocial[6] != null){
					UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
					unidadeFederacao.setSigla((String) dadosTarifaSocial[6]);
					cliente.setUnidadeFederacao(unidadeFederacao);
				}

				// N�mero do Cart�o do Programa Social
				if(dadosTarifaSocial[7] != null){
					tarifaSocialDadoEconomia.setNumeroCartaoProgramaSocial((Long) dadosTarifaSocial[7]);
				}

				// Id do Tipo do Cart�o do Programa Social
				if(dadosTarifaSocial[8] != null){
					tarifaSocialCartaoTipo = new TarifaSocialCartaoTipo();
					tarifaSocialCartaoTipo.setId((Integer) dadosTarifaSocial[8]);
				}

				// Renda Familiar
				if(dadosTarifaSocial[9] != null){
					tarifaSocialDadoEconomia.setValorRendaFamiliar((BigDecimal) dadosTarifaSocial[9]);
				}

				// Id do Tipo da Renda
				if(dadosTarifaSocial[10] != null){
					rendaTipo = new RendaTipo();
					rendaTipo.setId((Integer) dadosTarifaSocial[10]);
				}

				// Motivo de Exclus�o
				if(dadosTarifaSocial[11] != null){
					TarifaSocialExclusaoMotivo tarifaSocialExclusaoMotivo = new TarifaSocialExclusaoMotivo();
					tarifaSocialExclusaoMotivo.setId((Integer) dadosTarifaSocial[11]);
					tarifaSocialExclusaoMotivo.setDescricao((String) dadosTarifaSocial[33]);
					tarifaSocialDadoEconomia.setTarifaSocialExclusaoMotivo(tarifaSocialExclusaoMotivo);
				}

				// Descri�ao do Tipo do Cart�o do Programa Social
				if(dadosTarifaSocial[12] != null){
					tarifaSocialCartaoTipo.setDescricao((String) dadosTarifaSocial[12]);
				}

				// Data de Validade do Cart�o
				if(dadosTarifaSocial[13] != null){
					tarifaSocialDadoEconomia.setDataValidadeCartao((Date) dadosTarifaSocial[13]);
				}

				// N�mero de Parcelas
				if(dadosTarifaSocial[14] != null){
					tarifaSocialDadoEconomia.setNumeroMesesAdesao((Short) dadosTarifaSocial[14]);
				}

				// N�mero do Contrato da Companhia El�trica
				if(dadosTarifaSocial[15] != null){
					imovelEconomia.setNumeroCelpe((Long) dadosTarifaSocial[15]);
				}

				// Consumo M�dio
				if(dadosTarifaSocial[16] != null){
					tarifaSocialDadoEconomia.setConsumoCelpe((Integer) dadosTarifaSocial[16]);
				}

				// N�mero do IPTU
				if(dadosTarifaSocial[17] != null){
					imovelEconomia.setNumeroIptu((BigDecimal) dadosTarifaSocial[17]);
				}

				// �rea Constru�da
				if(dadosTarifaSocial[18] != null){
					imovelEconomia.setAreaConstruida((BigDecimal) dadosTarifaSocial[18]);
				}

				// �rea Constru�da Faixa
				if(dadosTarifaSocial[19] != null){
					AreaConstruidaFaixa areaConstruidaFaixa = new AreaConstruidaFaixa();
					areaConstruidaFaixa.setId((Integer) dadosTarifaSocial[19]);
				}

				// Descri��o do Tipo da Renda
				if(dadosTarifaSocial[20] != null){
					rendaTipo.setDescricao((String) dadosTarifaSocial[20]);
				}

				// Motivo de Revis�o
				if(dadosTarifaSocial[21] != null){
					TarifaSocialRevisaoMotivo tarifaSocialRevisaoMotivo = new TarifaSocialRevisaoMotivo();
					tarifaSocialRevisaoMotivo.setId((Integer) dadosTarifaSocial[21]);
					tarifaSocialRevisaoMotivo.setDescricao((String) dadosTarifaSocial[34]);
					tarifaSocialDadoEconomia.setTarifaSocialRevisaoMotivo(tarifaSocialRevisaoMotivo);
				}

				// Id da Economia do Im�vel
				if(dadosTarifaSocial[22] != null){
					imovelEconomia.setId((Integer) dadosTarifaSocial[22]);
				}

				// Id do Munic�pio
				if(dadosTarifaSocial[23] != null){
					// Cria os objeto necess�rios para setar o im�vel dentro de
					// im�vel economia
					SetorComercial setorComercial = new SetorComercial();
					ImovelSubcategoria imovelSubcategoria = new ImovelSubcategoria();
					ImovelSubcategoriaPK imovelSubcategoriaPK = new ImovelSubcategoriaPK();
					Municipio municipio = new Municipio();
					Subcategoria subcategoria = new Subcategoria();
					setorComercial.setId((Integer) dadosTarifaSocial[23]);
					municipio.setId((Integer) dadosTarifaSocial[24]);
					setorComercial.setMunicipio(municipio);
					imovel.setSetorComercial(setorComercial);
					imovel.setId(idImovel);
					subcategoria.setId((Integer) dadosTarifaSocial[32]);
					imovelSubcategoriaPK.setImovel(imovel);
					imovelSubcategoriaPK.setSubcategoria(subcategoria);
					imovelSubcategoria.setComp_id(imovelSubcategoriaPK);
					imovelEconomia.setImovelSubcategoria(imovelSubcategoria);
				}

				// Data de Exclus�o
				if(dadosTarifaSocial[25] != null){
					tarifaSocialDadoEconomia.setDataExclusao((Date) dadosTarifaSocial[25]);
				}

				// Data de Implanta��o
				if(dadosTarifaSocial[26] != null){
					tarifaSocialDadoEconomia.setDataImplantacao((Date) dadosTarifaSocial[26]);
				}

				// Data de Revis�o
				if(dadosTarifaSocial[27] != null){
					tarifaSocialDadoEconomia.setDataRevisao((Date) dadosTarifaSocial[27]);
				}

				// Quantidade de Recadastramentos
				if(dadosTarifaSocial[28] != null){
					tarifaSocialDadoEconomia.setQuantidadeRecadastramento((Short) dadosTarifaSocial[28]);
				}

				// Data do Recadastramento
				if(dadosTarifaSocial[29] != null){
					tarifaSocialDadoEconomia.setDataRecadastramento((Date) dadosTarifaSocial[29]);
				}

				// Id do Cliente
				if(dadosTarifaSocial[30] != null){
					cliente.setId((Integer) dadosTarifaSocial[30]);
				}

				// N�mero de Moradores
				if(dadosTarifaSocial[31] != null){
					imovelEconomia.setNumeroMorador((Short) dadosTarifaSocial[31]);
				}

				// �ltima Altera��o
				if(dadosTarifaSocial[35] != null){
					tarifaSocialDadoEconomia.setUltimaAlteracao((Date) dadosTarifaSocial[35]);
				}

				clienteImovelEconomia.setImovelEconomia(imovelEconomia);
				clienteImovelEconomia.setCliente(cliente);

				tarifaSocialDadoEconomia.setTarifaSocialCartaoTipo(tarifaSocialCartaoTipo);
				tarifaSocialDadoEconomia.setRendaTipo(rendaTipo);
				tarifaSocialDadoEconomia.setImovel(imovel);
				tarifaSocialDadoEconomia.setImovelEconomia(imovelEconomia);

				tarifaSocialHelper.setTarifaSocialDadoEconomia(tarifaSocialDadoEconomia);
				tarifaSocialHelper.setClienteImovelEconomia(clienteImovelEconomia);

				colecaoTarifaSocialHelper.add(tarifaSocialHelper);

			}

		}

		return colecaoTarifaSocialHelper;
	}

	/**
	 * [UC0069] - Manter Dados Tarifa Social
	 * Pesquisa a economia do im�vel pelo seu id
	 * Autor: Rafael Corr�a
	 * Data: 01/02/2007
	 */
	public ImovelEconomia pesquisarImovelEconomiaPeloId(Integer idImovelEconomia) throws ControladorException{

		try{
			return this.repositorioImovelTarifaSocial.pesquisarImovelEconomiaPeloId(idImovelEconomia);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Seta o indicador do nome da conta para 2 nos clientes propriet�rio e
	 * usu�rios
	 * Autor: Rafael Corr�a
	 * Data: 01/02/2007
	 */
	public void atualizarNomeContaClienteImovelTarifaSocial(Integer idImovel) throws ControladorException{

		try{
			this.repositorioImovelTarifaSocial.atualizarNomeContaClienteImovelTarifaSocial(idImovel);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0069] - Manter Dados Tarifa Social
	 * Retorna o id cliente usu�rio da economias do im�vel
	 * Autor: Rafael Corr�a
	 * Data: 19/01/2007
	 */
	public Integer pesquisarClienteUsuarioImovelEconomiaTarifaSocial(Integer idImovelEconomia) throws ControladorException{

		try{
			return this.repositorioImovelTarifaSocial.pesquisarClienteUsuarioImovelEconomiaTarifaSocial(idImovelEconomia);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0069] - Manter Dados Tarifa Social
	 * Recadastrar, atualiza ou remove a tarifa social
	 * Autor: Rafael Corr�a
	 * Data: 13/02/2007
	 */
	public void manterTarifaSocial(Imovel imovelSessao, Collection colecaoTarifaSocialHelperAtualizar,
					Collection colecaoImoveisExcluidosTarifaSocial, Collection colecaoTarifaSocialExcluida,
					Collection colecaoTarifasSociaisRecadastradas, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSA��O----------------------------

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_MANTER_TARIFA_SOCIAL,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_MANTER_TARIFA_SOCIAL);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		// --------FIM---- REGISTRAR TRANSA��O----------------------------

		// Recadastramento
		if(colecaoTarifasSociaisRecadastradas != null && !colecaoTarifasSociaisRecadastradas.isEmpty()){

			Iterator colecaoTarifasSociaisRecadastradasIterator = colecaoTarifasSociaisRecadastradas.iterator();

			while(colecaoTarifasSociaisRecadastradasIterator.hasNext()){

				TarifaSocialDadoEconomia tarifaSocialDadoEconomia = (TarifaSocialDadoEconomia) colecaoTarifasSociaisRecadastradasIterator
								.next();

				FiltroTarifaSocialDadoEconomia filtroTarifaSocialDadoEconomia = new FiltroTarifaSocialDadoEconomia();
				filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroSimples(FiltroTarifaSocialDadoEconomia.ID,
								tarifaSocialDadoEconomia.getId()));

				Collection colecaoTarifaSocialBase = getControladorUtil().pesquisar(filtroTarifaSocialDadoEconomia,
								TarifaSocialDadoEconomia.class.getName());

				TarifaSocialDadoEconomia tarifaSocialDadoEconomiaBase = (TarifaSocialDadoEconomia) Util
								.retonarObjetoDeColecao(colecaoTarifaSocialBase);

				tarifaSocialDadoEconomia.setDataImplantacao(tarifaSocialDadoEconomiaBase.getDataImplantacao());

				// Verifica se o usu�rio n�o excluiu o im�vel anterior da tarifa
				// social
				Integer idClienteUsuario = null;

				if(tarifaSocialDadoEconomia.getImovelEconomia() != null){
					idClienteUsuario = this.pesquisarClienteUsuarioImovelEconomiaTarifaSocial(tarifaSocialDadoEconomia.getImovelEconomia()
									.getId());
				}

				Collection colecaoTarifaSocialDadoEconomiaOutroImovel = null;

				if(idClienteUsuario != null){
					colecaoTarifaSocialDadoEconomiaOutroImovel = this.pesquisarClientesUsuarioExistenteTarifaSocial(idClienteUsuario);
				}

				if(colecaoTarifaSocialDadoEconomiaOutroImovel != null && !colecaoTarifaSocialDadoEconomiaOutroImovel.isEmpty()){

					Iterator colecaoImoveisExcluidosTarifaSocialIterator = colecaoImoveisExcluidosTarifaSocial.iterator();

					while(colecaoImoveisExcluidosTarifaSocialIterator.hasNext()){
						TarifaSocialHelper tarifaSocialHelperImovelAnterior = (TarifaSocialHelper) colecaoImoveisExcluidosTarifaSocialIterator
										.next();

						if(tarifaSocialHelperImovelAnterior.getClienteImovel().getCliente().getId().equals(idClienteUsuario)){
							TarifaSocialDadoEconomia tarifaSocialDadoEconomiaImovelAnterior = tarifaSocialHelperImovelAnterior
											.getTarifaSocialDadoEconomia();

							tarifaSocialDadoEconomiaImovelAnterior.setDataExclusao(new Date());
							tarifaSocialDadoEconomiaImovelAnterior.setUltimaAlteracao(new Date());

							TarifaSocialDadoEconomiaHistorico tarifaSocialDadoEconomiaHist = new TarifaSocialDadoEconomiaHistorico();

							// Inclus�o Tarifa Social Dado Economia Historico
							tarifaSocialDadoEconomiaHist.setId(tarifaSocialDadoEconomiaBase.getId());
							tarifaSocialDadoEconomiaHist.setNumeroCartaoProgramaSocial(tarifaSocialDadoEconomiaBase
											.getNumeroCartaoProgramaSocial());
							tarifaSocialDadoEconomiaHist.setNumeroMesesAdesao(tarifaSocialDadoEconomiaBase.getNumeroMesesAdesao());
							tarifaSocialDadoEconomiaHist.setConsumoCelpe(tarifaSocialDadoEconomiaBase.getConsumoCelpe());
							tarifaSocialDadoEconomiaHist.setValorRendaFamiliar(tarifaSocialDadoEconomiaBase.getValorRendaFamiliar());
							tarifaSocialDadoEconomiaHist.setDataValidadeCartao(tarifaSocialDadoEconomiaBase.getDataValidadeCartao());
							tarifaSocialDadoEconomiaHist.setRendaTipo(tarifaSocialDadoEconomiaBase.getRendaTipo());
							tarifaSocialDadoEconomiaHist
											.setTarifaSocialCartaoTipo(tarifaSocialDadoEconomiaBase.getTarifaSocialCartaoTipo());
							tarifaSocialDadoEconomiaHist.setImovelEconomia(tarifaSocialDadoEconomiaBase.getImovelEconomia());
							tarifaSocialDadoEconomiaHist.setDataImplantacao(tarifaSocialDadoEconomiaBase.getDataImplantacao());
							tarifaSocialDadoEconomiaHist.setDataExclusao(tarifaSocialDadoEconomiaBase.getDataExclusao());
							tarifaSocialDadoEconomiaHist.setDataRevisao(tarifaSocialDadoEconomiaBase.getDataRevisao());
							tarifaSocialDadoEconomiaHist.setDataRecadastramento(tarifaSocialDadoEconomiaBase.getDataRecadastramento());
							tarifaSocialDadoEconomiaHist.setTarifaSocialRevisaoMotivo(tarifaSocialDadoEconomiaBase
											.getTarifaSocialRevisaoMotivo());
							tarifaSocialDadoEconomiaHist.setTarifaSocialExclusaoMotivo(tarifaSocialDadoEconomiaBase
											.getTarifaSocialExclusaoMotivo());
							tarifaSocialDadoEconomiaHist.setImovel(tarifaSocialDadoEconomiaBase.getImovel());
							tarifaSocialDadoEconomiaHist.setUltimaAlteracao(tarifaSocialDadoEconomiaBase.getUltimaAlteracao());
							tarifaSocialDadoEconomiaHist.setUltimaAlteracaoTarifaSocialHistorico(new Date());

							getControladorUtil().inserir(tarifaSocialDadoEconomiaHist);

							getControladorUtil().remover(tarifaSocialDadoEconomiaBase);

							getControladorUtil().inserir(tarifaSocialDadoEconomiaImovelAnterior);

							// getControladorUtil().atualizar(tarifaSocialDadoEconomiaImovelAnterior);

							Collection colecaoTarifaSocialNaoExcluidasImovel = this.pesquisarTarifaSocialImovel(tarifaSocialDadoEconomia
											.getImovel().getId());

							if(colecaoTarifaSocialNaoExcluidasImovel == null || colecaoTarifaSocialNaoExcluidasImovel.isEmpty()){
								// 4.1.2.1.1.2. Caso ap�s as exclus�es, o im�vel n�o apresente mais
								// nenhuma economia
								// na tarifa social:
								// 4.1.2.1.1.2.1. Atualizar a tabela IMOVEL:
								getControladorImovel().atualizarImovelPerfilNormal(tarifaSocialDadoEconomia.getImovel().getId(), true);

								// 4.1.2.1.1.2.2. Encerra a situa��o especial de faturamento para o
								// im�vel
								this.encerraSituacaoEspecialFaturamentoImovel(tarifaSocialDadoEconomia.getImovel().getId());
							}

							break;

						}
					}
				}

				if(colecaoTarifaSocialDadoEconomiaOutroImovel != null && !colecaoTarifaSocialDadoEconomiaOutroImovel.isEmpty()){
					if(colecaoImoveisExcluidosTarifaSocial == null || colecaoImoveisExcluidosTarifaSocial.isEmpty()){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.tarifa_social_motivo_exclusao_nao_informado");
					}
				}
				// ----- REGISTRAR TRANSA��O -------
				tarifaSocialDadoEconomia.setUltimaAlteracao(new Date());
				tarifaSocialDadoEconomia.setOperacaoEfetuada(operacaoEfetuada);
				tarifaSocialDadoEconomia.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(tarifaSocialDadoEconomia);
				// ---- REGISTRAR TRANSA��O-------

				getControladorUtil().atualizar(tarifaSocialDadoEconomia);

			}
		}

		if(colecaoTarifaSocialHelperAtualizar != null && !colecaoTarifaSocialHelperAtualizar.isEmpty()){

			Iterator colecaoTarifaSocialHelperAtualizarIterator = colecaoTarifaSocialHelperAtualizar.iterator();

			while(colecaoTarifaSocialHelperAtualizarIterator.hasNext()){

				TarifaSocialHelper tarifaSocialHelper = (TarifaSocialHelper) colecaoTarifaSocialHelperAtualizarIterator.next();
				TarifaSocialDadoEconomia tarifaSocialDadoEconomiaAtualizar = tarifaSocialHelper.getTarifaSocialDadoEconomia();

				tarifaSocialDadoEconomiaAtualizar.setUltimaAlteracao(new Date());

				// ----- REGISTRAR TRANSA��O -------
				tarifaSocialDadoEconomiaAtualizar.setUltimaAlteracao(new Date());
				tarifaSocialDadoEconomiaAtualizar.setOperacaoEfetuada(operacaoEfetuada);
				tarifaSocialDadoEconomiaAtualizar.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(tarifaSocialDadoEconomiaAtualizar);
				// ---- REGISTRAR TRANSA��O-------

				// Verifica se o usu�rio fez alguma altera��o cadastral
				boolean alterou = false;

				FiltroTarifaSocialDadoEconomia filtroTarifaSocialDadoEconomia = new FiltroTarifaSocialDadoEconomia();
				filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroSimples(FiltroTarifaSocialDadoEconomia.ID,
								tarifaSocialDadoEconomiaAtualizar.getId()));
				filtroTarifaSocialDadoEconomia.adicionarCaminhoParaCarregamentoEntidade("tarifaSocialRevisaoMotivo");
				filtroTarifaSocialDadoEconomia.adicionarCaminhoParaCarregamentoEntidade("tarifaSocialExclusaoMotivo");
				filtroTarifaSocialDadoEconomia.adicionarCaminhoParaCarregamentoEntidade("rendaTipo");
				filtroTarifaSocialDadoEconomia.adicionarCaminhoParaCarregamentoEntidade("tarifaSocialCartaoTipo");

				Collection colecaoTarifaSocialDadoEconomiaBase = getControladorUtil().pesquisar(filtroTarifaSocialDadoEconomia,
								TarifaSocialDadoEconomia.class.getName());

				TarifaSocialDadoEconomia tarifaSocialDadoEconomiaBase = (TarifaSocialDadoEconomia) Util
								.retonarObjetoDeColecao(colecaoTarifaSocialDadoEconomiaBase);

				tarifaSocialDadoEconomiaAtualizar.setDataImplantacao(tarifaSocialDadoEconomiaBase.getDataImplantacao());

				boolean recadastrou = false;

				TarifaSocialDadoEconomia tarifaSocialDadoEconomiaRecadastrada = null;

				if(colecaoTarifasSociaisRecadastradas != null && !colecaoTarifasSociaisRecadastradas.isEmpty()){
					Iterator colecaoTarifasSociaisRecadastradasIterator = colecaoTarifasSociaisRecadastradas.iterator();
					while(colecaoTarifasSociaisRecadastradasIterator.hasNext()){
						tarifaSocialDadoEconomiaRecadastrada = (TarifaSocialDadoEconomia) colecaoTarifasSociaisRecadastradasIterator.next();

						if(tarifaSocialDadoEconomiaRecadastrada.getId().equals(tarifaSocialDadoEconomiaAtualizar.getId())){
							recadastrou = true;
							break;
						}

					}

				}

				if(!recadastrou){

					boolean igual = false;

					if(tarifaSocialDadoEconomiaAtualizar.getTarifaSocialRevisaoMotivo() == null
									&& tarifaSocialDadoEconomiaBase.getTarifaSocialRevisaoMotivo() == null){
						igual = true;
					}else if(tarifaSocialDadoEconomiaAtualizar.getTarifaSocialRevisaoMotivo() != null
									&& tarifaSocialDadoEconomiaBase.getTarifaSocialRevisaoMotivo() != null
									&& tarifaSocialDadoEconomiaAtualizar.getTarifaSocialRevisaoMotivo().getId().equals(
													tarifaSocialDadoEconomiaBase.getTarifaSocialRevisaoMotivo().getId())){
						igual = true;
					}

					if(igual){
						alterou = true;
					}else{

						ImovelEconomia imovelEconomiaBase = null;
						Imovel imovelBase = null;

						if(tarifaSocialHelper.getClienteImovelEconomia() != null){
							imovelEconomiaBase = this.pesquisarImovelEconomiaPeloId(tarifaSocialDadoEconomiaAtualizar.getImovelEconomia()
											.getId());
						}else{
							imovelBase = imovelSessao;
						}

						// Seta os valores do campo dentro de um array para ser
						// comparado se houve alguma altera��o
						Object[][] dados = new Object[11][11];

						// N�mero Cart�o
						dados[0][0] = tarifaSocialDadoEconomiaAtualizar.getNumeroCartaoProgramaSocial();
						dados[1][0] = tarifaSocialDadoEconomiaBase.getNumeroCartaoProgramaSocial();

						// Tipo do Cart�o
						if(tarifaSocialDadoEconomiaAtualizar.getTarifaSocialCartaoTipo() != null){
							dados[0][1] = tarifaSocialDadoEconomiaAtualizar.getTarifaSocialCartaoTipo().getId();
						}else{
							dados[0][1] = null;
						}
						if(tarifaSocialDadoEconomiaBase.getTarifaSocialCartaoTipo() != null){
							dados[1][1] = tarifaSocialDadoEconomiaBase.getTarifaSocialCartaoTipo().getId();
						}else{
							dados[1][1] = null;
						}

						// Data de Validade do Cart�o
						dados[0][2] = tarifaSocialDadoEconomiaAtualizar.getDataValidadeCartao();
						dados[1][2] = tarifaSocialDadoEconomiaBase.getDataValidadeCartao();

						// N�mero de Parcelas
						dados[0][3] = tarifaSocialDadoEconomiaAtualizar.getNumeroMesesAdesao();
						dados[1][3] = tarifaSocialDadoEconomiaBase.getNumeroMesesAdesao();

						// N�mero de Moradores
						if(tarifaSocialHelper.getClienteImovelEconomia() != null){
							dados[0][4] = tarifaSocialHelper.getClienteImovelEconomia().getImovelEconomia().getNumeroMorador();
							dados[1][4] = imovelEconomiaBase.getNumeroMorador();
						}else{
							dados[0][4] = tarifaSocialHelper.getClienteImovel().getImovel().getNumeroMorador();
							dados[1][4] = imovelBase.getNumeroMorador();
						}

						// N�mero Contrato Energia
						if(tarifaSocialHelper.getClienteImovelEconomia() != null){
							dados[0][5] = tarifaSocialHelper.getClienteImovelEconomia().getImovelEconomia().getNumeroCelpe();
							dados[1][5] = imovelEconomiaBase.getNumeroCelpe();
						}else{
							dados[0][5] = tarifaSocialHelper.getClienteImovel().getImovel().getNumeroCelpe();
							dados[1][5] = imovelBase.getNumeroCelpe();
						}

						// Consumo M�dio
						dados[0][6] = tarifaSocialDadoEconomiaAtualizar.getConsumoCelpe();
						dados[1][6] = tarifaSocialDadoEconomiaBase.getConsumoCelpe();

						// IPTU
						if(tarifaSocialHelper.getClienteImovelEconomia() != null){
							dados[0][7] = tarifaSocialHelper.getClienteImovelEconomia().getImovelEconomia().getNumeroIptu();
							dados[1][7] = imovelEconomiaBase.getNumeroCelpe();
						}else{
							dados[0][7] = tarifaSocialHelper.getClienteImovel().getImovel().getNumeroIptu();
							dados[1][7] = imovelBase.getNumeroIptu();
						}

						// �rea Constru�da
						if(tarifaSocialHelper.getClienteImovelEconomia() != null){
							dados[0][8] = tarifaSocialHelper.getClienteImovelEconomia().getImovelEconomia().getAreaConstruida();
							dados[1][8] = imovelEconomiaBase.getAreaConstruida();
						}else{
							dados[0][8] = tarifaSocialHelper.getClienteImovel().getImovel().getAreaConstruida();
							dados[1][8] = imovelBase.getAreaConstruida();
						}

						// Valor Renda
						dados[0][9] = tarifaSocialDadoEconomiaAtualizar.getValorRendaFamiliar();
						dados[1][9] = tarifaSocialDadoEconomiaBase.getValorRendaFamiliar();

						// Tipo Renda
						if(tarifaSocialDadoEconomiaAtualizar.getRendaTipo() != null){
							dados[0][10] = tarifaSocialDadoEconomiaAtualizar.getRendaTipo().getId();
						}else{
							dados[0][10] = null;
						}
						if(tarifaSocialDadoEconomiaBase.getRendaTipo() != null){
							dados[1][10] = tarifaSocialDadoEconomiaBase.getRendaTipo().getId();
						}else{
							dados[1][10] = null;
						}

						alterou = this.verificarAlteracaoDadosTarifaSocial(dados);

					}

				}

				if(alterou){
					int qtdeRecadastramentos = 1;

					if(tarifaSocialDadoEconomiaBase.getQuantidadeRecadastramento() != null){
						qtdeRecadastramentos = tarifaSocialDadoEconomiaBase.getQuantidadeRecadastramento().shortValue() + 1;
					}
					getControladorUtil().atualizar(tarifaSocialDadoEconomiaBase);

					tarifaSocialDadoEconomiaAtualizar.setQuantidadeRecadastramento(new Short("" + qtdeRecadastramentos));
					tarifaSocialDadoEconomiaAtualizar.setDataRecadastramento(new Date());

				}else{
					if(!recadastrou){
						tarifaSocialDadoEconomiaAtualizar.setQuantidadeRecadastramento(tarifaSocialDadoEconomiaBase
										.getQuantidadeRecadastramento());
						tarifaSocialDadoEconomiaAtualizar.setDataRecadastramento(tarifaSocialDadoEconomiaBase.getDataRecadastramento());
					}else{
						tarifaSocialDadoEconomiaAtualizar.setQuantidadeRecadastramento(tarifaSocialDadoEconomiaRecadastrada
										.getQuantidadeRecadastramento());
						tarifaSocialDadoEconomiaAtualizar.setDataRecadastramento(tarifaSocialDadoEconomiaRecadastrada
										.getDataRecadastramento());
					}
				}

				// TarifaSocialDadoEconomiaHistorico tarifaSocialDadoEconomiaHist = new
				// TarifaSocialDadoEconomiaHistorico();
				//
				// // Inclus�o Tarifa Social Dado Economia Historico
				// tarifaSocialDadoEconomiaHist.setId(tarifaSocialDadoEconomiaBase.getId());
				// tarifaSocialDadoEconomiaHist.setNumeroCartaoProgramaSocial(tarifaSocialDadoEconomiaBase.getNumeroCartaoProgramaSocial());
				// tarifaSocialDadoEconomiaHist.setNumeroMesesAdesao(tarifaSocialDadoEconomiaBase.getNumeroMesesAdesao());
				// tarifaSocialDadoEconomiaHist.setConsumoCelpe(tarifaSocialDadoEconomiaBase.getConsumoCelpe());
				// tarifaSocialDadoEconomiaHist.setValorRendaFamiliar(tarifaSocialDadoEconomiaBase.getValorRendaFamiliar());
				// tarifaSocialDadoEconomiaHist.setDataValidadeCartao(tarifaSocialDadoEconomiaBase.getDataValidadeCartao());
				// tarifaSocialDadoEconomiaHist.setRendaTipo(tarifaSocialDadoEconomiaBase.getRendaTipo());
				// tarifaSocialDadoEconomiaHist.setTarifaSocialCartaoTipo(tarifaSocialDadoEconomiaBase.getTarifaSocialCartaoTipo());
				// tarifaSocialDadoEconomiaHist.setImovelEconomia(tarifaSocialDadoEconomiaBase.getImovelEconomia());
				// tarifaSocialDadoEconomiaHist.setDataImplantacao(tarifaSocialDadoEconomiaBase.getDataImplantacao());
				// tarifaSocialDadoEconomiaHist.setDataExclusao(tarifaSocialDadoEconomiaBase.getDataExclusao());
				// tarifaSocialDadoEconomiaHist.setDataRevisao(tarifaSocialDadoEconomiaBase.getDataRevisao());
				// tarifaSocialDadoEconomiaHist.setDataRecadastramento(tarifaSocialDadoEconomiaBase.getDataRecadastramento());
				// tarifaSocialDadoEconomiaHist.setTarifaSocialRevisaoMotivo(tarifaSocialDadoEconomiaBase.getTarifaSocialRevisaoMotivo());
				// tarifaSocialDadoEconomiaHist.setTarifaSocialExclusaoMotivo(tarifaSocialDadoEconomiaBase.getTarifaSocialExclusaoMotivo());
				// tarifaSocialDadoEconomiaHist.setImovel(tarifaSocialDadoEconomiaBase.getImovel());
				// tarifaSocialDadoEconomiaHist.setUltimaAlteracao(tarifaSocialDadoEconomiaBase.getUltimaAlteracao());
				// tarifaSocialDadoEconomiaHist.setUltimaAlteracaoTarifaSocialHistorico(new Date());
				//
				// getControladorUtil().inserir(tarifaSocialDadoEconomiaHist);
				//
				// getControladorUtil().remover(tarifaSocialDadoEconomiaBase);

				getControladorUtil().atualizar(tarifaSocialDadoEconomiaAtualizar);

				if(tarifaSocialDadoEconomiaAtualizar.getImovelEconomia() != null){

					ImovelEconomia imovelEconomia = tarifaSocialDadoEconomiaAtualizar.getImovelEconomia();
					imovelEconomia.setUltimaAlteracao(new Date());

					// ----- REGISTRAR TRANSA��O -------
					imovelEconomia.setUltimaAlteracao(new Date());
					imovelEconomia.setOperacaoEfetuada(operacaoEfetuada);
					imovelEconomia.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					registradorOperacao.registrarOperacao(imovelEconomia);
					// ---- REGISTRAR TRANSA��O-------

					getControladorUtil().atualizar(imovelEconomia);

				}else{

					Imovel imovel = tarifaSocialDadoEconomiaAtualizar.getImovel();
					imovel.setUltimaAlteracao(new Date());

					// ----- REGISTRAR TRANSA��O -------
					imovel.setUltimaAlteracao(new Date());
					imovel.setOperacaoEfetuada(operacaoEfetuada);
					imovel.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					registradorOperacao.registrarOperacao(imovel);
					// ---- REGISTRAR TRANSA��O-------

					getControladorUtil().atualizar(imovel);

				}

				// Inserir os clientes selecionados
				Collection colecaoClienteImovelInseridos = tarifaSocialHelper.getColecaoClientesInseridos();

				Collection colecaoClienteImovelEconomiaInseridos = tarifaSocialHelper.getColecaoClientesEconomiaInseridos();

				// Uma Economia
				if(colecaoClienteImovelInseridos != null && !colecaoClienteImovelInseridos.isEmpty()){

					Iterator colecaoClienteImovelInseridosIterator = colecaoClienteImovelInseridos.iterator();

					while(colecaoClienteImovelInseridosIterator.hasNext()){
						ClienteImovel clienteImovelInserido = (ClienteImovel) colecaoClienteImovelInseridosIterator.next();

						if(clienteImovelInserido.getClienteRelacaoTipo().getId().toString().equals(ClienteRelacaoTipo.USUARIO.toString())){

							clienteImovelInserido.setIndicadorNomeConta(new Short("1"));

							Collection colecaoClientesUsuarioExistenteTarifaSocial = this
											.pesquisarClientesUsuarioExistenteTarifaSocial(clienteImovelInserido.getCliente().getId());

							if(colecaoClientesUsuarioExistenteTarifaSocial != null
											&& !colecaoClientesUsuarioExistenteTarifaSocial.isEmpty()){

								Iterator colecaoImoveisExcluidosTarifaSocialIterator = colecaoImoveisExcluidosTarifaSocial.iterator();

								while(colecaoImoveisExcluidosTarifaSocialIterator.hasNext()){
									TarifaSocialHelper tarifaSocialHelperImovelAnterior = (TarifaSocialHelper) colecaoImoveisExcluidosTarifaSocialIterator
													.next();

									if(tarifaSocialHelperImovelAnterior.getClienteImovel().getCliente().getId().equals(
													clienteImovelInserido.getCliente().getId())){
										TarifaSocialDadoEconomia tarifaSocialDadoEconomiaImovelAnterior = tarifaSocialHelperImovelAnterior
														.getTarifaSocialDadoEconomia();

										tarifaSocialDadoEconomiaImovelAnterior.setDataExclusao(new Date());
										tarifaSocialDadoEconomiaImovelAnterior.setUltimaAlteracao(new Date());
										// ----- REGISTRAR TRANSA��O -------
										tarifaSocialDadoEconomiaImovelAnterior.setUltimaAlteracao(new Date());
										tarifaSocialDadoEconomiaImovelAnterior.setOperacaoEfetuada(operacaoEfetuada);
										tarifaSocialDadoEconomiaImovelAnterior.adicionarUsuario(usuarioLogado,
														UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
										registradorOperacao.registrarOperacao(tarifaSocialDadoEconomiaImovelAnterior);
										// ---- REGISTRAR TRANSA��O-------

										getControladorUtil().atualizar(tarifaSocialDadoEconomiaImovelAnterior);
										break;
									}
								}
							}
						}else{
							clienteImovelInserido.setIndicadorNomeConta(new Short("2"));
						}

						clienteImovelInserido.setUltimaAlteracao(new Date());
						this.atualizarNomeContaClienteImovelTarifaSocial(imovelSessao.getId());

						// ----- REGISTRAR TRANSA��O -------
						clienteImovelInserido.setUltimaAlteracao(new Date());
						clienteImovelInserido.setOperacaoEfetuada(operacaoEfetuada);
						clienteImovelInserido.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
						registradorOperacao.registrarOperacao(clienteImovelInserido);
						// ---- REGISTRAR TRANSA��O-------

						getControladorUtil().inserir(clienteImovelInserido);

					}

				}
				// M�ltiplas Economias
				else if(colecaoClienteImovelEconomiaInseridos != null && !colecaoClienteImovelEconomiaInseridos.isEmpty()){

					Iterator colecaoClienteImovelEconomiaInseridosIterator = colecaoClienteImovelEconomiaInseridos.iterator();

					while(colecaoClienteImovelEconomiaInseridosIterator.hasNext()){

						ClienteImovelEconomia clienteImovelEconomiaInserido = (ClienteImovelEconomia) colecaoClienteImovelEconomiaInseridosIterator
										.next();

						if(clienteImovelEconomiaInserido.getClienteRelacaoTipo().getId().toString().equals(
										ClienteRelacaoTipo.USUARIO.toString())){
							Collection colecaoClientesUsuarioExistenteTarifaSocial = this
											.pesquisarClientesUsuarioExistenteTarifaSocial(clienteImovelEconomiaInserido.getCliente()
															.getId());

							if(colecaoClientesUsuarioExistenteTarifaSocial != null
											&& !colecaoClientesUsuarioExistenteTarifaSocial.isEmpty()){

								Iterator colecaoImoveisExcluidosTarifaSocialIterator = colecaoImoveisExcluidosTarifaSocial.iterator();

								while(colecaoImoveisExcluidosTarifaSocialIterator.hasNext()){
									TarifaSocialHelper tarifaSocialHelperImovelAnterior = (TarifaSocialHelper) colecaoImoveisExcluidosTarifaSocialIterator
													.next();

									if(tarifaSocialHelperImovelAnterior.getClienteImovel().getCliente().getId().equals(
													clienteImovelEconomiaInserido.getCliente().getId())){
										TarifaSocialDadoEconomia tarifaSocialDadoEconomiaImovelAnterior = tarifaSocialHelperImovelAnterior
														.getTarifaSocialDadoEconomia();

										tarifaSocialDadoEconomiaImovelAnterior.setDataExclusao(new Date());
										tarifaSocialDadoEconomiaImovelAnterior.setUltimaAlteracao(new Date());

										// ----- REGISTRAR TRANSA��O -------
										tarifaSocialDadoEconomiaImovelAnterior.setUltimaAlteracao(new Date());
										tarifaSocialDadoEconomiaImovelAnterior.setOperacaoEfetuada(operacaoEfetuada);
										tarifaSocialDadoEconomiaImovelAnterior.adicionarUsuario(usuarioLogado,
														UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
										registradorOperacao.registrarOperacao(tarifaSocialDadoEconomiaImovelAnterior);
										// ---- REGISTRAR TRANSA��O-------

										getControladorUtil().atualizar(tarifaSocialDadoEconomiaImovelAnterior);
										break;
									}
								}
							}
						}

						clienteImovelEconomiaInserido.setUltimaAlteracao(new Date());

						// ----- REGISTRAR TRANSA��O -------
						clienteImovelEconomiaInserido.setUltimaAlteracao(new Date());
						clienteImovelEconomiaInserido.setOperacaoEfetuada(operacaoEfetuada);
						clienteImovelEconomiaInserido.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
						registradorOperacao.registrarOperacao(clienteImovelEconomiaInserido);
						// ---- REGISTRAR TRANSA��O-------
						getControladorUtil().inserir(clienteImovelEconomiaInserido);

					}

				}

				// Remover os clientes selecionados
				Collection colecaoClienteImovelRemovidos = tarifaSocialHelper.getColecaoClientesRemovidos();
				Collection colecaoClienteImovelEconomiaRemovidos = tarifaSocialHelper.getColecaoClientesEconomiaRemovidos();

				if(colecaoClienteImovelRemovidos != null && !colecaoClienteImovelRemovidos.isEmpty()){

					Iterator colecaoClienteImovelRemovidosIterator = colecaoClienteImovelRemovidos.iterator();

					while(colecaoClienteImovelRemovidosIterator.hasNext()){
						ClienteImovel clienteImovelRemovido = (ClienteImovel) colecaoClienteImovelRemovidosIterator.next();
						clienteImovelRemovido.setUltimaAlteracao(new Date());
						clienteImovelRemovido.setIndicadorNomeConta(new Short("2"));

						// ----- REGISTRAR TRANSA��O -------
						clienteImovelRemovido.setUltimaAlteracao(new Date());
						clienteImovelRemovido.setOperacaoEfetuada(operacaoEfetuada);
						clienteImovelRemovido.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
						registradorOperacao.registrarOperacao(clienteImovelRemovido);
						// ---- REGISTRAR TRANSA��O-------

						getControladorUtil().atualizar(clienteImovelRemovido);

					}

				}else if(colecaoClienteImovelEconomiaRemovidos != null && !colecaoClienteImovelEconomiaRemovidos.isEmpty()){

					Iterator colecaoClienteImovelEconomiaRemovidosIterator = colecaoClienteImovelEconomiaRemovidos.iterator();

					while(colecaoClienteImovelEconomiaRemovidosIterator.hasNext()){
						ClienteImovelEconomia clienteImovelEconomiaRemovido = (ClienteImovelEconomia) colecaoClienteImovelEconomiaRemovidosIterator
										.next();
						clienteImovelEconomiaRemovido.setUltimaAlteracao(new Date());

						// ----- REGISTRAR TRANSA��O -------
						clienteImovelEconomiaRemovido.setUltimaAlteracao(new Date());
						clienteImovelEconomiaRemovido.setOperacaoEfetuada(operacaoEfetuada);
						clienteImovelEconomiaRemovido.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
						registradorOperacao.registrarOperacao(clienteImovelEconomiaRemovido);
						// ---- REGISTRAR TRANSA��O-------

						getControladorUtil().atualizar(clienteImovelEconomiaRemovido);

					}

				}

			}

		}

		// Para apenas uma economia
		if(colecaoTarifaSocialExcluida != null && !colecaoTarifaSocialExcluida.isEmpty()){

			Iterator colecaoTarifaSocialExcluidaIterator = colecaoTarifaSocialExcluida.iterator();

			while(colecaoTarifaSocialExcluidaIterator.hasNext()){

				TarifaSocialDadoEconomia tarifaSocialDadoEconomia = (TarifaSocialDadoEconomia) colecaoTarifaSocialExcluidaIterator.next();

				tarifaSocialDadoEconomia.setDataExclusao(new Date());
				tarifaSocialDadoEconomia.setUltimaAlteracao(new Date());
				tarifaSocialDadoEconomia.setTarifaSocialRevisaoMotivo(null);
				tarifaSocialDadoEconomia.setDataRevisao(null);
				tarifaSocialDadoEconomia.setImovel(imovelSessao);

				// ----- REGISTRAR TRANSA��O -------
				tarifaSocialDadoEconomia.setUltimaAlteracao(new Date());
				tarifaSocialDadoEconomia.setOperacaoEfetuada(operacaoEfetuada);
				tarifaSocialDadoEconomia.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(tarifaSocialDadoEconomia);
				// ---- REGISTRAR TRANSA��O-------

				getControladorUtil().atualizar(tarifaSocialDadoEconomia);

			}

			Collection colecaoTarifaSocialNaoExcluidasImovel = this.pesquisarTarifaSocialImovel(imovelSessao.getId());

			if(colecaoTarifaSocialNaoExcluidasImovel == null || colecaoTarifaSocialNaoExcluidasImovel.isEmpty()){
				// 4.1.2.1.1.2. Caso ap�s as exclus�es, o im�vel n�o apresente mais nenhuma economia
				// na tarifa social:
				// 4.1.2.1.1.2.1. Atualizar a tabela IMOVEL:
				getControladorImovel().atualizarImovelPerfilNormal(imovelSessao.getId(), true);

				// 4.1.2.1.1.2.2. Encerra a situa��o especial de faturamento para o im�vel
				this.encerraSituacaoEspecialFaturamentoImovel(imovelSessao.getId());
			}

		}

	}

	private boolean verificarAlteracaoDadosTarifaSocial(Object[][] dados){

		boolean alterou = false;

		for(int i = 0; dados.length > i; i++){
			if(dados[0][i] != null && dados[1][i] == null){
				alterou = true;
				break;
			}else if(dados[0][i] == null && dados[1][i] != null){
				alterou = true;
				break;
			}else if(dados[0][i] != null && dados[1][i] != null && !dados[0][i].equals(dados[1][i])){
				alterou = true;
				break;
			}
		}

		return alterou;
	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Recadastrar ou insere a tarifa social
	 * Autor: Rafael Corr�a
	 * Data: 13/02/2007
	 */
	public void inserirTarifaSocial(Imovel imovelSessao, ClienteImovel clienteImovel, RegistroAtendimento registroAtendimento,
					RegistroAtendimentoUnidade registroAtendimentoUnidade, Usuario usuarioLogado,
					Integer idTarifaSocialDadoEconomiaExcluida, Collection colecaoTarifaSocialDadoEconomia,
					Collection colecaoClienteImovelEconomia, Collection colecaoTarifaSocialRecadastrar, Imovel imovelAtualizar,
					Collection colecaoImovelEconomiaAtualizar) throws ControladorException{

		// Identificar a realiza��o de alguma manipula��o de registros
		boolean manipulacaoRegistro = false;

		// Vari�vel utilizada para recuperar o id do im�vel da tarifa social que
		// ser� exclu�da, caso seja necess�rio
		Integer idImovel = null;

		// ------------ REGISTRAR
		// TRANSa��o----------------------------

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_TARIFA_SOCIAL,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_TARIFA_SOCIAL);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		// -----------------------------------------

		// Verifica a exist�ncia de alguma Tarifa Social para ser inserida ou
		// alterada
		// PARA UMA ECONOMIA
		if(colecaoTarifaSocialDadoEconomia != null && !colecaoTarifaSocialDadoEconomia.isEmpty()){

			TarifaSocialDadoEconomia tarifaSocialDadoEconomia = (TarifaSocialDadoEconomia) Util
							.retonarObjetoDeColecao(colecaoTarifaSocialDadoEconomia);

			Imovel imovel = tarifaSocialDadoEconomia.getImovel();

			// INSERIR
			if(colecaoTarifaSocialRecadastrar != null){

				TarifaSocialDadoEconomia tarifaSocialDadoEconomiaRecadastrar = (TarifaSocialDadoEconomia) Util
								.retonarObjetoDeColecao(colecaoTarifaSocialRecadastrar);

				if(tarifaSocialDadoEconomiaRecadastrar.getDataExclusao() != null){
					// ATUALIZAR - RECADASTRAMENTO

					Integer idTarifaSocial = tarifaSocialDadoEconomiaRecadastrar.getId();

					Collection colecaoTarifaSocialDadoEconomiaOutroImovel = this
									.pesquisarClientesUsuarioExistenteTarifaSocial(clienteImovel.getCliente().getId());

					if(colecaoTarifaSocialDadoEconomiaOutroImovel != null && !colecaoTarifaSocialDadoEconomiaOutroImovel.isEmpty()){
						if(idTarifaSocialDadoEconomiaExcluida == null){
							throw new ControladorException("atencao.tarifa_social_motivo_exclusao_nao_informado");
						}else{
							TarifaSocialDadoEconomia tarifaSocialDadoEconomiaExcluir = (TarifaSocialDadoEconomia) Util
											.retonarObjetoDeColecao(colecaoTarifaSocialDadoEconomiaOutroImovel);

							tarifaSocialDadoEconomiaExcluir = this.pesquisarTarifaSocial(tarifaSocialDadoEconomiaExcluir.getId());

							tarifaSocialDadoEconomiaExcluir.setTarifaSocialExclusaoMotivo(tarifaSocialDadoEconomia
											.getTarifaSocialExclusaoMotivo());
							tarifaSocialDadoEconomiaExcluir.setTarifaSocialRevisaoMotivo(null);
							tarifaSocialDadoEconomiaExcluir.setDataRevisao(null);
							tarifaSocialDadoEconomiaExcluir.setDataExclusao(new Date());
							tarifaSocialDadoEconomiaExcluir.setUltimaAlteracao(new Date());
							idImovel = tarifaSocialDadoEconomiaExcluir.getImovel().getId();

							// }

							if(tarifaSocialDadoEconomiaExcluir != null){

								getControladorUtil().atualizar(tarifaSocialDadoEconomiaExcluir);

								Collection colecaoTarifaSocialNaoExcluidasImovel = this.pesquisarTarifaSocialImovel(idImovel);

								if(colecaoTarifaSocialNaoExcluidasImovel == null || colecaoTarifaSocialNaoExcluidasImovel.isEmpty()){
									getControladorImovel().atualizarImovelPerfilNormal(idImovel, false);
								}

							}

						}
					}

					// 10.1. Transferir a tarifa social atual para hist�rico
					TarifaSocialDadoEconomiaHistorico tarifaSocialDadoEconomiaHistorico = new TarifaSocialDadoEconomiaHistorico(
									tarifaSocialDadoEconomiaRecadastrar);
					tarifaSocialDadoEconomiaHistorico.setUltimaAlteracaoTarifaSocialHistorico(new Date());
					getControladorUtil().inserir(tarifaSocialDadoEconomiaHistorico);

					// 10.4. Atualizar a tabela
					int quantidade = 1;
					if(tarifaSocialDadoEconomiaRecadastrar.getQuantidadeRecadastramento() != null){
						quantidade = tarifaSocialDadoEconomiaRecadastrar.getQuantidadeRecadastramento().shortValue() + 1;
					}

					// 10.2. Incluir os novos dados da tarifa social do im�vel, por economia, na
					// tabela TARIFA_SOCIAL_DADO_ECONOMIA
					// atualizar a quantidade e data recadastramento
					tarifaSocialDadoEconomia.setId(idTarifaSocial);
					// tarifaSocialDadoEconomia.setId(null);
					tarifaSocialDadoEconomia.setTarifaSocialExclusaoMotivo(null);
					tarifaSocialDadoEconomia.setQuantidadeRecadastramento(new Short(quantidade + ""));
					tarifaSocialDadoEconomia.setDataRecadastramento(new Date());
					tarifaSocialDadoEconomia.setDataExclusao(null);
					tarifaSocialDadoEconomia.setTarifaSocialExclusaoMotivo(null);

					// 10.3. Excluir a tarifa social anterior da tabela TARIFA_SOCIAL_DADO_ECONOMIA

					// getControladorUtil().remover(tarifaSocialDadoEconomiaRecadastrar);
					try{
						repositorioImovelTarifaSocial.deletarTarifaSocialDadoEconomia(tarifaSocialDadoEconomiaRecadastrar.getId());
					}catch(ErroRepositorioException e){
						throw new ControladorException("atencao.erro.deletar_tarifa_social_anterior");
					}

					getControladorUtil().inserir(tarifaSocialDadoEconomia);

					// atualizar cliente usuario para indicador de nome conta
					clienteImovel.setIndicadorNomeConta(new Short("1"));

					// ------------ REGISTRAR
					// TRANSa��o----------------------------

					clienteImovel.setOperacaoEfetuada(operacaoEfetuada);
					clienteImovel.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					registradorOperacao.registrarOperacao(clienteImovel);

					// --------FIM---- REGISTRAR
					// TRANSa��o----------------------------

					getControladorUtil().atualizar(clienteImovel);

					this.atualizarNomeContaClienteImovelTarifaSocial(imovel.getId());

					// atualizar o perfil para Tarifa Social
					this.atualizarImovelPerfilTarifaSocial(imovel, true);

				}
			}else{

				TarifaSocialDadoEconomia tarifaSocialDadoEconomiaExcluir = null;

				// Verifica se o cliente est� em revis�o e em caso afirmativo
				// recupera o id da antiga tarifa social
				if(tarifaSocialDadoEconomia.getTarifaSocialExclusaoMotivo() != null){

					tarifaSocialDadoEconomiaExcluir = this.pesquisarTarifaSocial(tarifaSocialDadoEconomia.getId());

					tarifaSocialDadoEconomiaExcluir.setTarifaSocialExclusaoMotivo(tarifaSocialDadoEconomia.getTarifaSocialExclusaoMotivo());
					tarifaSocialDadoEconomiaExcluir.setTarifaSocialRevisaoMotivo(null);
					tarifaSocialDadoEconomiaExcluir.setDataRevisao(null);
					tarifaSocialDadoEconomiaExcluir.setDataExclusao(new Date());
					tarifaSocialDadoEconomiaExcluir.setUltimaAlteracao(new Date());

					idImovel = tarifaSocialDadoEconomiaExcluir.getImovel().getId();

					tarifaSocialDadoEconomia.setId(null);
					tarifaSocialDadoEconomia.setTarifaSocialExclusaoMotivo(null);

				}

				// ------------ REGISTRAR
				// TRANSA��O----------------------------

				tarifaSocialDadoEconomia.setOperacaoEfetuada(operacaoEfetuada);
				tarifaSocialDadoEconomia.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(tarifaSocialDadoEconomia);

				// --------FIM---- REGISTRAR
				// TRANSA��O----------------------------

				tarifaSocialDadoEconomia.setDataImplantacao(new Date());

				getControladorUtil().inserir(tarifaSocialDadoEconomia);

				clienteImovel.setIndicadorNomeConta(new Short("1"));

				// ------------ REGISTRAR
				// TRANSA��O----------------------------

				clienteImovel.setOperacaoEfetuada(operacaoEfetuada);
				clienteImovel.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(clienteImovel);

				// --------FIM---- REGISTRAR
				// TRANSA��O----------------------------

				getControladorUtil().atualizar(clienteImovel);

				this.atualizarNomeContaClienteImovelTarifaSocial(imovel.getId());

				this.atualizarImovelPerfilTarifaSocial(imovel, false);

				if(tarifaSocialDadoEconomiaExcluir != null){

					getControladorUtil().atualizar(tarifaSocialDadoEconomiaExcluir);

					Collection colecaoTarifaSocialNaoExcluidasImovel = this.pesquisarTarifaSocialImovel(idImovel);

					if(colecaoTarifaSocialNaoExcluidasImovel == null || colecaoTarifaSocialNaoExcluidasImovel.isEmpty()){
						getControladorImovel().atualizarImovelPerfilNormal(idImovel, false);
					}

				}
			}

			if(imovelAtualizar != null){
				imovelAtualizar.setUltimaAlteracao(new Date());

				// ------------ REGISTRAR
				// TRANSA��O----------------------------

				imovelAtualizar.setOperacaoEfetuada(operacaoEfetuada);
				imovelAtualizar.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(imovelAtualizar);

				// --------FIM---- REGISTRAR
				// TRANSA��O----------------------------
				getControladorUtil().atualizar(imovelAtualizar);
			}

			manipulacaoRegistro = true;

			// PARA MAIS DE UMA ECONOMIA
		}else if(colecaoClienteImovelEconomia != null && !colecaoClienteImovelEconomia.isEmpty()){

			Iterator colecaoClienteImovelEconomiaIterator = colecaoClienteImovelEconomia.iterator();
			ClienteImovelEconomia clienteImovelEconomia = null;
			Collection colecaoTarifaSocialDadoMultiplasEconomia = null;

			int qtdEconomiaTarifaSocial = 0;

			while(colecaoClienteImovelEconomiaIterator.hasNext()){

				clienteImovelEconomia = (ClienteImovelEconomia) colecaoClienteImovelEconomiaIterator.next();
				colecaoTarifaSocialDadoMultiplasEconomia = clienteImovelEconomia.getImovelEconomia().getTarifaSocialDadoEconomias();

				if(colecaoTarifaSocialDadoMultiplasEconomia != null && !colecaoTarifaSocialDadoMultiplasEconomia.isEmpty()){

					qtdEconomiaTarifaSocial++;
				}
			}

			// Todas as economias ter�o que ser informadas
			// =============================================================================
			if(qtdEconomiaTarifaSocial != getControladorImovel().obterQuantidadeEconomias(imovelSessao)){
				throw new ControladorException("atencao.informar.tarifa_social.economias");
			}
			// =============================================================================

			colecaoClienteImovelEconomiaIterator = colecaoClienteImovelEconomia.iterator();
			clienteImovelEconomia = null;
			colecaoTarifaSocialDadoMultiplasEconomia = null;

			while(colecaoClienteImovelEconomiaIterator.hasNext()){

				clienteImovelEconomia = (ClienteImovelEconomia) colecaoClienteImovelEconomiaIterator.next();
				colecaoTarifaSocialDadoMultiplasEconomia = clienteImovelEconomia.getImovelEconomia().getTarifaSocialDadoEconomias();

				if(colecaoTarifaSocialDadoMultiplasEconomia != null && !colecaoTarifaSocialDadoMultiplasEconomia.isEmpty()){

					manipulacaoRegistro = true;

					TarifaSocialDadoEconomia tarifaSocialDadoEconomia = (TarifaSocialDadoEconomia) Util
									.retonarObjetoDeColecao(colecaoTarifaSocialDadoMultiplasEconomia);

					Collection colecaoTarifaSocialDadoEconomiaOutroImovel = this
									.pesquisarClientesUsuarioExistenteTarifaSocial(clienteImovelEconomia.getCliente().getId());

					if(colecaoTarifaSocialDadoEconomiaOutroImovel != null && !colecaoTarifaSocialDadoEconomiaOutroImovel.isEmpty()){
						if(idTarifaSocialDadoEconomiaExcluida == null){
							throw new ControladorException("atencao.tarifa_social_motivo_exclusao_nao_informado");
						}else{
							TarifaSocialDadoEconomia tarifaSocialDadoEconomiaExcluir = (TarifaSocialDadoEconomia) Util
											.retonarObjetoDeColecao(colecaoTarifaSocialDadoEconomiaOutroImovel);

							// Verifica se o cliente est� em revis�o e em caso
							// afirmativo
							// recupera o id da antiga tarifa social

							tarifaSocialDadoEconomiaExcluir = this.pesquisarTarifaSocial(tarifaSocialDadoEconomiaExcluir.getId());

							tarifaSocialDadoEconomiaExcluir.setTarifaSocialExclusaoMotivo(tarifaSocialDadoEconomia
											.getTarifaSocialExclusaoMotivo());
							tarifaSocialDadoEconomiaExcluir.setTarifaSocialRevisaoMotivo(null);
							tarifaSocialDadoEconomiaExcluir.setDataRevisao(null);
							tarifaSocialDadoEconomiaExcluir.setDataExclusao(new Date());
							tarifaSocialDadoEconomiaExcluir.setUltimaAlteracao(new Date());
							idImovel = tarifaSocialDadoEconomiaExcluir.getImovel().getId();

							if(tarifaSocialDadoEconomiaExcluir != null){

								// ------------ REGISTRAR
								// TRANSA��O----------------------------

								tarifaSocialDadoEconomiaExcluir.setOperacaoEfetuada(operacaoEfetuada);
								tarifaSocialDadoEconomiaExcluir.adicionarUsuario(Usuario.USUARIO_TESTE,
												UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
								registradorOperacao.registrarOperacao(tarifaSocialDadoEconomiaExcluir);

								// --------FIM---- REGISTRAR
								// TRANSA��O----------------------------

								getControladorUtil().atualizar(tarifaSocialDadoEconomiaExcluir);

								Collection colecaoTarifaSocialNaoExcluidasImovel = this.pesquisarTarifaSocialImovel(idImovel);

								if(colecaoTarifaSocialNaoExcluidasImovel == null || colecaoTarifaSocialNaoExcluidasImovel.isEmpty()){
									getControladorImovel().atualizarImovelPerfilNormal(idImovel, false);
								}

							}

						}
					}

					// Para mais de uma economia � obrigat�rio informar o
					// imovelEconomia
					tarifaSocialDadoEconomia.setImovelEconomia(clienteImovelEconomia.getImovelEconomia());
					tarifaSocialDadoEconomia.setImovel(imovelSessao);

					// Carregando o im�vel
					FiltroImovel filtroImovel = new FiltroImovel();

					// Objetos que ser�o retornados pelo hibernate
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPerfil");

					// Inseri ou atualiza na base.
					if(tarifaSocialDadoEconomia.getId() != null){

						FiltroTarifaSocialDadoEconomia filtroTarifaSocialDadoEconomia = new FiltroTarifaSocialDadoEconomia();
						filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroSimples(FiltroTarifaSocialDadoEconomia.ID,
										tarifaSocialDadoEconomia.getId()));

						Collection colecaoTarifaSocialAtualizar = getControladorUtil().pesquisar(filtroTarifaSocialDadoEconomia,
										TarifaSocialDadoEconomia.class.getName());

						TarifaSocialDadoEconomia tarifaSocialDadoEconomiaAtualizar = (TarifaSocialDadoEconomia) Util
										.retonarObjetoDeColecao(colecaoTarifaSocialAtualizar);

						int qtdeRecadastramento = 1;

						if(tarifaSocialDadoEconomiaAtualizar != null
										&& tarifaSocialDadoEconomiaAtualizar.getQuantidadeRecadastramento() != null){
							qtdeRecadastramento = tarifaSocialDadoEconomiaAtualizar.getQuantidadeRecadastramento().shortValue() + 1;
						}

						tarifaSocialDadoEconomia.setUltimaAlteracao(new Date());
						tarifaSocialDadoEconomia.setDataExclusao(null);
						tarifaSocialDadoEconomia.setTarifaSocialExclusaoMotivo(null);
						tarifaSocialDadoEconomia.setQuantidadeRecadastramento(new Short("" + qtdeRecadastramento));
						tarifaSocialDadoEconomia.setDataRecadastramento(new Date());
						this.atualizarDadosTarifaSocialImovel(tarifaSocialDadoEconomia);
					}else{
						tarifaSocialDadoEconomia.setUltimaAlteracao(new Date());
						tarifaSocialDadoEconomia.setDataExclusao(null);
						tarifaSocialDadoEconomia.setTarifaSocialExclusaoMotivo(null);
						tarifaSocialDadoEconomia.setDataImplantacao(new Date());
						getControladorUtil().inserir(tarifaSocialDadoEconomia);
					}
				}
			}

			if(colecaoImovelEconomiaAtualizar != null && !colecaoImovelEconomiaAtualizar.isEmpty()){

				Iterator colecaoImovelEconomiaAtualizarIterator = colecaoImovelEconomiaAtualizar.iterator();

				while(colecaoImovelEconomiaAtualizarIterator.hasNext()){
					ImovelEconomia imovelEconomiaAtualizar = (ImovelEconomia) colecaoImovelEconomiaAtualizarIterator.next();

					imovelEconomiaAtualizar.setUltimaAlteracao(new Date());

					// ------------ REGISTRAR
					// TRANSA��O----------------------------

					imovelEconomiaAtualizar.setOperacaoEfetuada(operacaoEfetuada);
					imovelEconomiaAtualizar.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					registradorOperacao.registrarOperacao(imovelEconomiaAtualizar);

					// --------FIM---- REGISTRAR
					// TRANSA��O----------------------------

					getControladorUtil().atualizar(imovelEconomiaAtualizar);

				}
			}

		}else{
			// Nenhuma tarifa social foi encontrada
			throw new ControladorException("atencao.nenhuma.tarifa_social.encontrada");
		}

		if(!manipulacaoRegistro){
			// Nenhuma tarifa social foi encontrada
			throw new ControladorException("atencao.nenhuma.tarifa_social.encontrada");
		}

		this.atualizarImovelPerfilTarifaSocial(imovelSessao, false);

		// inclui na tabela FATURAMENTO_SITUACAO_HISTORICO
		this.incluirFaturamentoSituacaoHistorico(imovelSessao.getId(), usuarioLogado);

		// 11. Encerrar o Registro de atendimento como executado
		this.getControladorRegistroAtendimento()
						.encerrarRegistroAtendimento(registroAtendimento, registroAtendimentoUnidade, usuarioLogado);

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Retorna a economia do im�vel a partir do id do clienteImovelEconomia
	 * Autor: Rafael Corr�a
	 * Data: 15/02/2007
	 */
	public ImovelEconomia pesquisarImovelEconomiaPeloCliente(Integer idClienteImovelEconomia) throws ControladorException{

		try{
			return this.repositorioImovelTarifaSocial.pesquisarImovelEconomiaPeloCliente(idClienteImovelEconomia);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * UC0069-ManterDadosTarifaSocial
	 * Recadastrar ou insere a tarifa social
	 * Autor: Rafael Corr�a
	 * Data: 13/02/2007
	 */
	public void encerraSituacaoEspecialFaturamentoImovel(Integer idImovel) throws ControladorException{

		SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();

		if(sistemaParametros == null || sistemaParametros.getAnoMesFaturamento() == null){
			throw new ControladorException("atencao.naocadastrado", null, "Ano/M�s Faturamento Parametros Sistema");
		}
		// 4.1.2.1.1.2.2. Encerra a situa��o especial de faturamento para o im�vel
		// (da tabela FATURAMENTO_SITUACAO_HISTORICO com FTSH_AMFATURAMENTORETIRADA com o
		// valor nulo e FTST_ID com o valor igual a �percentual esgoto�)
		Collection colecaoFaturamentosSituacaoHistorico = getControladorImovel().pesquisarFaturamentoSituacaoHistorico(idImovel);

		FaturamentoSituacaoHistorico faturamentoSituacaoHistorico = null;

		if(!Util.isVazioOrNulo(colecaoFaturamentosSituacaoHistorico)){
			faturamentoSituacaoHistorico = (FaturamentoSituacaoHistorico) Util.retonarObjetoDeColecao(colecaoFaturamentosSituacaoHistorico);

			// FTSH_AMFATURAMENTORETIRADA = PARM_AMREFERENCIAFATURAMENTO da tabela
			// SISTEMA_PARAMETRO
			faturamentoSituacaoHistorico.setAnoMesFaturamentoRetirada(sistemaParametros.getAnoMesFaturamento());

			getControladorUtil().atualizar(faturamentoSituacaoHistorico);
		}
	}

	/**
	 * [UC3058] Verificar Perman�ncia Tarifa Social
	 * Verificar Perman�ncia Tarifa Social
	 * Autor: Carlos Chrystian
	 * Data: 01/07/2012
	 */
	public void verificarPermanenciaTarifaSocial(Integer idImovel, Usuario usuarioLogado) throws ControladorException{

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPerfil");

		Collection colecaoImovel = this.getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());
		// 1. O sistema verifica se o im�vel est� cadastrado na tarifa social.
		Imovel imovel = null;
		if(!Util.isVazioOrNulo(colecaoImovel)){
			imovel = (Imovel) colecaoImovel.iterator().next();

			if(!Util.isVazioOuBranco(imovel)){

				// Cria o filtro
				FiltroTarifaSocialDadoEconomia filtroTarifaSocialDadoEconomia = new FiltroTarifaSocialDadoEconomia();

				filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroSimples(FiltroTarifaSocialDadoEconomia.IMOVEL, imovel));

				filtroTarifaSocialDadoEconomia
								.adicionarCaminhoParaCarregamentoEntidade(FiltroTarifaSocialDadoEconomia.TARIFA_SOCIAL_REVISAO_MOTIVO);

				Collection colecaoTarifaSocial = this.getControladorUtil().pesquisar(filtroTarifaSocialDadoEconomia,
								TarifaSocialDadoEconomia.class.getName());

				boolean tarifaVerificada = false;
				TarifaSocialDadoEconomia tarifaSocial = null;

				// Verifica Tarifa Social Suspensa
				if(!Util.isVazioOrNulo(colecaoTarifaSocial)){

					tarifaSocial = (TarifaSocialDadoEconomia) Util.retonarObjetoDeColecao(colecaoTarifaSocial);

					if(!Util.isVazioOuBranco(tarifaSocial)
									&& Util.isVazioOuBranco(tarifaSocial.getDataExclusao())
									&& !Util.isVazioOuBranco(tarifaSocial.getDataRevisao())
									&& !Util.isVazioOuBranco(tarifaSocial.getTarifaSocialRevisaoMotivo())
									&& !Util.isVazioOuBranco(tarifaSocial.getTarifaSocialRevisaoMotivo()
													.getIndicadorPermiteRecadastramento())
									&& tarifaSocial.getTarifaSocialRevisaoMotivo().getIndicadorPermiteRecadastramento().intValue() == 1){
						tarifaVerificada = true;
					}

					// 1.1. Caso o Im�vel esteja na tarifa social
					// ou com tarifa social suspensa, verifica perman�ncia ou suspens�o.
					if((!Util.isVazioOuBranco(imovel.getImovelPerfil()) && imovel.getImovelPerfil().getId().equals(
									ImovelPerfil.TARIFA_SOCIAL))
									|| tarifaVerificada){

						// 2. O sistema verifica se o im�vel recebido atende aos crit�rios de perda
						// da
						// tarifa social:
						FiltroImovelSubCategoria filtroImovelSubcategoria = new FiltroImovelSubCategoria();

						filtroImovelSubcategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, idImovel));
						filtroImovelSubcategoria.adicionarCaminhoParaCarregamentoEntidade("comp_id.subcategoria");
						filtroImovelSubcategoria.adicionarParametro(new ParametroSimplesDiferenteDe(
										FiltroImovelSubCategoria.SUBCATEGORIA_CATEGORIA_ID, Categoria.RESIDENCIAL));

						Collection colecaoImovelCategoriasNaoResidencial = getControladorUtil().pesquisar(filtroImovelSubcategoria,
										ImovelSubcategoria.class.getName());

						boolean possuiImovelNaoResidencial = false;

						// 2.1. Categoria: existe na tabela IMOVEL_SUBCATEGORIA IMOV_ID = matr�cula
						// do
						// im�vel
						// e SCAT_ID com o valor correspondente a categoria n�o residencial.
						if(!Util.isVazioOrNulo(colecaoImovelCategoriasNaoResidencial)){
							possuiImovelNaoResidencial = true;
						}

						// 2.2. Quantidade de Economia:
						boolean quantidadeEconomia = false;

						Integer validaQuantidadeEconomia = null;
						try{
							validaQuantidadeEconomia = Integer.valueOf(ParametroCadastro.P_VALIDA_QUANTIDADE_ECONOMIA_TARIFA_SOCIAL
											.executar());
						}catch(ControladorException e){
							throw new ActionServletException("erro.parametro.sistema.inexistente"
											+ " - P_VALIDA_QUANTIDADE_ECONOMIA_TARIFA_SOCIAL.");
						}

						filtroImovelSubcategoria = new FiltroImovelSubCategoria();

						filtroImovelSubcategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, idImovel));
						filtroImovelSubcategoria.adicionarCaminhoParaCarregamentoEntidade("comp_id.subcategoria");
						filtroImovelSubcategoria.adicionarParametro(new ParametroSimples(
										FiltroImovelSubCategoria.SUBCATEGORIA_CATEGORIA_ID, Categoria.RESIDENCIAL));

						Collection colecaoImovelCategoriasResidencial = getControladorUtil().pesquisar(filtroImovelSubcategoria,
										ImovelSubcategoria.class.getName());

						Integer imovelSubcategoriaQtdEconomias = null;
						if(!Util.isVazioOrNulo(colecaoImovelCategoriasResidencial)){
							ImovelSubcategoria imovelSubcategoria = (ImovelSubcategoria) Util
											.retonarObjetoDeColecao(colecaoImovelCategoriasResidencial);
							imovelSubcategoriaQtdEconomias = imovelSubcategoria.getComp_id().getSubcategoria().getQuantidadeEconomias();
						}

						if(Util.isNaoNuloBrancoZero(validaQuantidadeEconomia)
										&& validaQuantidadeEconomia.equals(ConstantesSistema.SIM.intValue())
										&& !possuiImovelNaoResidencial
										&& (Util.isNaoNuloBrancoZero(imovelSubcategoriaQtdEconomias) && imovelSubcategoriaQtdEconomias > ConstantesSistema.SIM
														.intValue())){
							quantidadeEconomia = true;
						}

						// 2.3. Data de Validade:
						boolean dataValidade = false;
						Integer validaDataValidadeTarifaSocial = null;
						try{
							validaDataValidadeTarifaSocial = Integer.valueOf(ParametroCadastro.P_VALIDA_DATA_VALIDADE_TARIFA_SOCIAL
											.executar());
						}catch(ControladorException e){
							throw new ActionServletException("erro.parametro.sistema.inexistente"
											+ " - P_VALIDA_DATA_VALIDADE_TARIFA_SOCIAL.");
						}

						if(Util.isNaoNuloBrancoZero(validaDataValidadeTarifaSocial)
										&& validaDataValidadeTarifaSocial.equals(ConstantesSistema.SIM.intValue())
										&& (Util.isNaoNuloBrancoZero(imovel.getDataValidadeTarifaTemporaria()) && Util.compararData(
														imovel.getDataValidadeTarifaTemporaria(), new Date()) == ConstantesSistema.NUMERO_NAO_INFORMADO)){
							dataValidade = true;
						}

						// Inicializando os par�metros que ser�o passados para a consulta do
						// [UC0067]
						int indicadorDebito = 1;
						String codigoCliente = null;
						Integer clienteRelacaoTipo = null;
						String anoMesInicialReferenciaDebito = "000101";
						String anoMesFinalReferenciaDebito = "999912";
						Date anoMesInicialVencimentoDebito = Util.criarData(1, 1, 1);
						Date anoMesFinalVencimentoDebito = new Date();
						int indicadorPagamento = 2;
						int indicadorConta = 1;
						int indicadorDebitoACobrar = 2;
						int indicadorCreditoARealizar = 2;
						int indicadorNotasPromissorias = 2;
						int indicadorGuiasPagamento = 2;
						int indicadorCalcularAcrescimoImpontualidade = 2;
						int indicadorCalcularAcrescimosSucumbenciaAnterior = 2;
						Boolean indicadorContas = Boolean.TRUE;
						SistemaParametro sistemaParametro = null;

						// [UC0067] - Obter D�bito do Im�vel ou Cliente
						ObterDebitoImovelOuClienteHelper colecaoDebitoImovel = getControladorCobranca().obterDebitoImovelOuCliente(
										indicadorDebito, imovel.getId().toString(), codigoCliente, clienteRelacaoTipo,
										anoMesInicialReferenciaDebito, anoMesFinalReferenciaDebito, anoMesInicialVencimentoDebito,
										anoMesFinalVencimentoDebito, indicadorPagamento, indicadorConta, indicadorDebitoACobrar,
										indicadorCreditoARealizar, indicadorNotasPromissorias, indicadorGuiasPagamento,
										indicadorCalcularAcrescimoImpontualidade, indicadorContas, sistemaParametro, null, null, null,
										ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM,
										indicadorCalcularAcrescimosSucumbenciaAnterior, null);

						// 2.4. Inadimpl�ncia: possui mais de dois (2) d�bitos
						boolean possuiMaisDoisDebitos = false;

						Integer quantidadeMinimaDebitos = null;
						try{
							quantidadeMinimaDebitos = Integer.valueOf(ParametroCadastro.P_QUANTIDADE_MINIMA_DE_DEBITOS.executar());
						}catch(ControladorException e){
							throw new ActionServletException("erro.parametro.sistema.inexistente" + " - P_QUANTIDADE_M�NIMA_DE_DEBITOS.");
						}

						if(!Util.isVazioOuBranco(colecaoDebitoImovel)
										&& (colecaoDebitoImovel.getColecaoContasValoresImovel().size() > quantidadeMinimaDebitos)){
							possuiMaisDoisDebitos = true;
						}

						// 3. Caso o im�vel seja do perfil tarifa social (IPER_ID com o valor
						// correspondente
						// a �tarifa social� na tabela IMOVEL) e atenda a um dos crit�rios do item
						// 2,
						// ent�o o sistema suspende o uso da tarifa social.
						if(imovel.getImovelPerfil() != null && imovel.getImovelPerfil().getId().equals(ImovelPerfil.TARIFA_SOCIAL)
										&& (possuiImovelNaoResidencial || dataValidade || quantidadeEconomia || possuiMaisDoisDebitos)){
							// Atualizar o Dados do Imovel
							ImovelPerfil imovelPerfil = new ImovelPerfil();
							imovelPerfil.setId(ImovelPerfil.NORMAL);
							imovel.setImovelPerfil(imovelPerfil);

							// Inclui consumo tarifa tempor�ria para o im�vel com valor "NULO"
							imovel.setConsumoTarifaTemporaria(null);

							imovel.setDataValidadeTarifaTemporaria(null);

							// Recupera o par�metro Tarifa Percentual de Esgoto
							String parametroPercentualEsgoto = ParametroCadastro.P_PERCENTUAL_ESGOTO_TARIFA_SOCIAL.executar();

							// Caso o im�vel esteja ligado de esgoto e exista percentual
							// diferenciado
							// para o benef�cio
							// da tarifa social
							if(imovel.getLigacaoEsgotoSituacao() != null
											&& imovel.getLigacaoEsgotoSituacao().getId().equals(LigacaoEsgotoSituacao.LIGADO)
											&& !Util.isVazioOuBrancoOuZero(parametroPercentualEsgoto)){
								// Inclui faturamento situa��o tipo para o im�vel com o valor
								// "NULO"
								imovel.setFaturamentoSituacaoTipo(null);

								// Encerra Situa��o Especial de Faturamento.
								this.encerraSituacaoEspecialFaturamentoImovel(imovel.getId());
							}

							// Atualiza o Im�vel
							this.getControladorUtil().atualizar(imovel);

							// Atualiza a Tarifa Social Dado Economia
							if(!Util.isVazioOuBranco(tarifaSocial)){
								tarifaSocial.setUltimaAlteracao(new Date());
								tarifaSocial.setDataRevisao(new Date());
								tarifaSocial.setDataExclusao(new Date());

								if(possuiImovelNaoResidencial){
									TarifaSocialRevisaoMotivo tarifaSocialRevisaoMotivo = new TarifaSocialRevisaoMotivo();
									tarifaSocialRevisaoMotivo.setId(TarifaSocialRevisaoMotivo.MUDANCA_CATEGORIA);
									tarifaSocial.setTarifaSocialRevisaoMotivo(tarifaSocialRevisaoMotivo);
								}else if(dataValidade){
									TarifaSocialRevisaoMotivo tarifaSocialRevisaoMotivo = new TarifaSocialRevisaoMotivo();
									tarifaSocialRevisaoMotivo.setId(TarifaSocialRevisaoMotivo.DATA_VALIDADE_FORA_VIGENCIA);
									tarifaSocial.setTarifaSocialRevisaoMotivo(tarifaSocialRevisaoMotivo);
								}else if(quantidadeEconomia){
									TarifaSocialRevisaoMotivo tarifaSocialRevisaoMotivo = new TarifaSocialRevisaoMotivo();
									tarifaSocialRevisaoMotivo.setId(TarifaSocialRevisaoMotivo.QUANTIDADE_ECONOMIA_FORA_LIMITE);
									tarifaSocial.setTarifaSocialRevisaoMotivo(tarifaSocialRevisaoMotivo);
								}else if(possuiMaisDoisDebitos){
									TarifaSocialRevisaoMotivo tarifaSocialRevisaoMotivo = new TarifaSocialRevisaoMotivo();
									tarifaSocialRevisaoMotivo.setId(TarifaSocialRevisaoMotivo.INADIMPLENCIA);
									tarifaSocial.setTarifaSocialRevisaoMotivo(tarifaSocialRevisaoMotivo);
								}
								this.getControladorUtil().atualizar(tarifaSocial);
							}
						}else{

							Integer reativacaoAutomaticaTarifaSocial = null;
							try{
								reativacaoAutomaticaTarifaSocial = Integer.valueOf(ParametroCadastro.P_REATIVACAO_AUTOMATICA_TARIFA_SOCIAL
												.executar());
							}catch(ControladorException e){
								throw new ActionServletException("erro.parametro.sistema.inexistente"
												+ "P_REATIVACAO_AUTOMATICA_TARIFA_SOCIAL.");
							}

							if(Util.isNaoNuloBrancoZero(reativacaoAutomaticaTarifaSocial)
											&& reativacaoAutomaticaTarifaSocial.equals(ConstantesSistema.SIM.intValue())){

								String parametroPercentualEsgoto = ParametroCadastro.P_PERCENTUAL_ESGOTO_TARIFA_SOCIAL.executar();

								// Caso o im�vel esteja ligado de esgoto e exista percentual
								// diferenciado
								// para o benef�cio
								// da tarifa social
								if(imovel.getLigacaoEsgotoSituacao() != null
												&& imovel.getLigacaoEsgotoSituacao().getId().equals(LigacaoEsgotoSituacao.LIGADO)
												&& !Util.isVazioOuBrancoOuZero(parametroPercentualEsgoto)){
									// inclui uma linha na tabela FATURAMENTO_SITUACAO_HISTORICO com
									// os
									// seguintes dados:

									// FTSH_ID Id gerado pelo sistema
									FaturamentoSituacaoHistorico faturamentoSituacaoHistorico = new FaturamentoSituacaoHistorico();

									// IMOV_ID Id do im�vel
									faturamentoSituacaoHistorico.setImovel(imovel);

									// FTSH_AMFATURAMENTOSITUACAOINICIO PARM_AMREFERENCIAFATURAMENTO
									// da
									// tabela
									// SISTEMA_PARAMETRO
									SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();

									if(sistemaParametros == null || sistemaParametros.getAnoMesFaturamento() == null){
										throw new ControladorException("atencao.naocadastrado", null,
														"Ano/M�s Faturamento Parametros Sistema");
									}

									faturamentoSituacaoHistorico.setAnoMesFaturamentoSituacaoInicio(sistemaParametros
													.getAnoMesFaturamento());

									// FTSH_AMFATURAMENTOSITUACAOFIM 99999
									faturamentoSituacaoHistorico.setAnoMesFaturamentoSituacaoFim(Integer.valueOf("999999"));

									// FTSH_AMFATURAMENTORETIRADA Nulo
									faturamentoSituacaoHistorico.setAnoMesFaturamentoRetirada(null);

									// FTST_ID da tabela FATURAMENTO_SITUACAO_TIPO com valor igual a
									// �percentual
									// de
									// esgoto�
									// Inclui faturamento situa��o tipo com o valor
									// "PERCENTUAL ESGOTO"
									FaturamentoSituacaoTipo faturamentoSituacaoTipo = new FaturamentoSituacaoTipo();
									faturamentoSituacaoTipo.setId(FaturamentoSituacaoTipo.PERCENTUAL_DE_ESGOTO);

									faturamentoSituacaoHistorico.setFaturamentoSituacaoTipo(faturamentoSituacaoTipo);

									// FTSM_ID da tabela FATURAMENTO_SITUACAO_MOTIVO com valor igual
									// a
									// �benef�cio tarifa
									// social�
									FaturamentoSituacaoMotivo faturamentoSituacaoMotivo = new FaturamentoSituacaoMotivo();
									faturamentoSituacaoMotivo.setId(FaturamentoSituacaoMotivo.BENEFICIO_TARIFA_SOCIAL);

									faturamentoSituacaoHistorico.setFaturamentoSituacaoMotivo(faturamentoSituacaoMotivo);

									// FTSH_TMULTIMAALTERACAO Data e hora correntes
									faturamentoSituacaoHistorico.setUltimaAlteracao(Calendar.getInstance().getTime());

									// USUR_ID Usu�rio logado no sistema
									faturamentoSituacaoHistorico.setUsuario(usuarioLogado);

									// FTSH_NNVOLUME nulo
									faturamentoSituacaoHistorico.setVolume(null);

									// FTSH_PCESGOTO PASI_VLPARAMETRO da tabela PARAMETRO_SISTEMA
									// com PASI_CDPARAMETRO = �P_PERCENTUAL_ESGOTO_TARIFA_SOCIAL�
									faturamentoSituacaoHistorico.setPercentualEsgoto(new BigDecimal(parametroPercentualEsgoto));

									getControladorUtil().inserir(faturamentoSituacaoHistorico);
								}

								// Atualizar os Dados do Imovel
								ImovelPerfil imovelPerfil = new ImovelPerfil();
								imovelPerfil.setId(ImovelPerfil.TARIFA_SOCIAL);
								imovel.setImovelPerfil(imovelPerfil);

								// Recupera o par�metro Tarifa Percentual de Esgoto
								String parametroTarifaSocial = ParametroCadastro.P_TARIFA_CONSUMO_TARIFA_SOCIAL.executar();

								// Inclui consumo tarifa tempor�ria para o im�vel com o valor do
								// par�metro
								ConsumoTarifa consumoTarifaTemporaria = new ConsumoTarifa();
								consumoTarifaTemporaria.setId(Integer.valueOf(parametroTarifaSocial));
								imovel.setConsumoTarifaTemporaria(consumoTarifaTemporaria);

								Integer validadeBeneficioTarifaSocial = null;
								try{
									validadeBeneficioTarifaSocial = Integer.valueOf(ParametroCadastro.P_VALIDADE_BENEFICIO_TARIFA_SOCIAL
													.executar());
								}catch(ControladorException e){
									throw new ActionServletException("erro.parametro.sistema.inexistente"
													+ " - P_VALIDA_BENEFICIO_TARIFA_SOCIAL.");
								}

								imovel.setDataValidadeTarifaTemporaria(Util.somaMesData(new Date(), validadeBeneficioTarifaSocial, true));

								// Inclui faturamento situa��o tipo para o im�vel com o valor
								// "PERCENTUAL ESGOTO"
								FaturamentoSituacaoTipo faturamentoSituacaoTipo = new FaturamentoSituacaoTipo();
								faturamentoSituacaoTipo.setId(FaturamentoSituacaoTipo.PERCENTUAL_DE_ESGOTO);

								imovel.setFaturamentoSituacaoTipo(faturamentoSituacaoTipo);

								// Atualiza o Im�vel
								this.getControladorUtil().atualizar(imovel);

								// Atualiza a Tarifa Social Dado Economia
								if(!Util.isVazioOuBranco(tarifaSocial)){
									tarifaSocial.setUltimaAlteracao(new Date());
									tarifaSocial.setDataRevisao(null);
									tarifaSocial.setTarifaSocialRevisaoMotivo(null);

									int qtdeRecadastramentos = 1;

									if(tarifaSocial.getQuantidadeRecadastramento() != null){
										qtdeRecadastramentos = tarifaSocial.getQuantidadeRecadastramento().shortValue() + 1;
									}

									tarifaSocial.setQuantidadeRecadastramento(Short.valueOf("" + qtdeRecadastramentos));
									tarifaSocial.setDataRecadastramento(new Date());
									this.getControladorUtil().atualizar(tarifaSocial);

								}
							}
						}
					}
				}
			}
		}
	}

}
