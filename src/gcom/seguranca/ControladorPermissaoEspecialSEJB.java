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

package gcom.seguranca;

import gcom.cadastro.imovel.Imovel;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.FiltroUsuarioPemissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioPermissaoEspecial;
import gcom.util.*;
import gcom.util.filtro.ParametroSimples;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * Defini��o da l�gica de neg�cio do Session Bean de
 * ControladorPermissaoEspecial
 * 
 * @author Rodrigo Silveira
 * @created 07/11/2006
 */

public class ControladorPermissaoEspecialSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	SessionContext sessionContext;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @exception CreateException
	 *                Descri��o da exce��o
	 */
	public void ejbCreate() throws CreateException{

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
	 * Verifica as permiss�es especiais do usu�rio por funcionalidade informada
	 * no sistema
	 * 
	 * @author Rodrigo Silveira
	 * @date 07/11/2006
	 * @param permissaoEspecial
	 * @param usuario
	 * @return
	 */
	public boolean verificarPermissaoEspecial(int permissaoEspecial, Usuario usuario) throws ControladorException{

		boolean retorno = false;

		FiltroUsuarioPemissaoEspecial filtroUsuarioPemissaoEspecial = new FiltroUsuarioPemissaoEspecial();
		filtroUsuarioPemissaoEspecial.adicionarParametro(new ParametroSimples(FiltroUsuarioPemissaoEspecial.USUARIO_COMP_ID, usuario
						.getId()));
		filtroUsuarioPemissaoEspecial.adicionarParametro(new ParametroSimples(FiltroUsuarioPemissaoEspecial.PERMISSAO_ESPECIAL_COMP_ID,
						permissaoEspecial));

		if(getControladorUtil().pesquisar(filtroUsuarioPemissaoEspecial, UsuarioPermissaoEspecial.class.getName()).size() > 0){
			retorno = true;

		}

		return retorno;

	}

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
	 * Verifica as permiss�es especiais do usu�rio por funcionalidade informada
	 * no sistema
	 * 
	 * @author Vivianne Sousa
	 * @date 09/11/2006
	 * @param permissaoEspecial
	 * @param usuario
	 * @param objeto
	 */
	public void verificarPermissaoEspecial(int permissaoEspecial, Usuario usuario, Object objeto) throws ControladorException{

		switch(permissaoEspecial){

			case PermissaoEspecial.IMOVEL_EM_SITUACAO_COBRANCA:

				if(objeto instanceof Imovel){
					Imovel imovel = (Imovel) objeto;
					verificarPermissaoEspecialImovelSituacaoCobranca(permissaoEspecial, usuario, imovel);
				}

				break;

		}

	}

	/**
	 * Verifica as permiss�es especiais do usu�rio por funcionalidade informada
	 * no sistema
	 * 
	 * @author Vivianne Sousa
	 * @date 09/11/2006
	 * @param permissaoEspecial
	 * @param usuario
	 * @param imovel
	 */
	public void verificarPermissaoEspecialImovelSituacaoCobranca(int permissaoEspecial, Usuario usuario, Imovel imovel)
					throws ControladorException{

		if(imovel.getCobrancaSituacao() != null && imovel.getCobrancaSituacao().getId() != null
						&& !imovel.getCobrancaSituacao().getId().equals("") && !imovel.getCobrancaSituacao().getId().equals("0")){

			if(!verificarPermissaoEspecial(permissaoEspecial, usuario)){
				throw new ControladorException("atencao.imovel.em.situacao.cobranca", null, imovel.getCobrancaSituacao().getDescricao());
			}

		}

	}

	/**
	 * Verifica permiss�o especial para aceitar um valor de entrada menor q o
	 * valor m�nimo de entrada na terceira p�gina de Efetuar Parcelamento
	 * D�bitos
	 * 
	 * @author Vivianne Sousa
	 * @date 28/11/2006
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoValMinimoEntrada(Usuario usuario) throws ControladorException{

		boolean temPermissaoValMinimoEntrada = this.verificarPermissaoEspecial(PermissaoEspecial.TESTAR_VAL_MINIMO_ENTRADA, usuario);

		return temPermissaoValMinimoEntrada;
	}

	/**
	 * Verifica permiss�o especial para atualizar um cliente que seja usu�rio da
	 * tarifa social
	 * 
	 * @author Rafael Corr�a
	 * @date 16/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoAtualizarUsuarioTarifaSocial(Usuario usuario) throws ControladorException{

		boolean temPermissaoAtualizarUsuarioTarifaSocial = this.verificarPermissaoEspecial(PermissaoEspecial.CLIENTE_USUARIO_TARIFA_SOCIAL,
						usuario);

		return temPermissaoAtualizarUsuarioTarifaSocial;
	}

	/**
	 * Verifica permiss�o especial para atualizar um LOGRADOURO_BAIRRO
	 * 
	 * @author Raphael Rossiter, Romulo Aurelio
	 * @date 24/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoAtualizarLogradouroBairro(Usuario usuario) throws ControladorException{

		boolean retorno = this.verificarPermissaoEspecial(PermissaoEspecial.ATUALIZAR_LOGRADOURO_BAIRRO, usuario);

		return retorno;
	}

	/**
	 * Verifica permiss�o especial para N�O gerar d�bito no informar retorno OS fiscaliza��o
	 * 
	 * @author Raphael Rossiter
	 * @date 03/03/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoGeracaoDebitoOSFiscalizacao(Usuario usuario) throws ControladorException{

		boolean retorno = this.verificarPermissaoEspecial(PermissaoEspecial.GERACAO_DEBITO_OS_FISCALIZACAO, usuario);

		return retorno;
	}

	/**
	 * Verifica permiss�o especial para inserir Imovel com logradouro.municipio
	 * diferente de setorComercial.municipio
	 * 
	 * @author R�mulo Aur�lio
	 * @date 23/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirImovelMunicipioLogradouroDiferenteSetor(Usuario usuario) throws ControladorException{

		boolean temPermissaoInserirImovelMunicipioLogradouroDiferenteSetor = this.verificarPermissaoEspecial(
						PermissaoEspecial.INSERIR_IMOVEL_MUNICIPIO_LOGRADOURO_DIFERENTE_SETOR, usuario);

		return temPermissaoInserirImovelMunicipioLogradouroDiferenteSetor;
	}

	/**
	 * Verifica permiss�o especial para atualizar Imovel com
	 * logradouro.municipio diferente de setorComercial.municipio
	 * 
	 * @author R�mulo Aur�lio
	 * @date 23/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoAtualizarImovelMunicipioLogradouroDiferenteSetor(Usuario usuario) throws ControladorException{

		boolean temPermissaoAtualizarImovelMunicipioLogradouroDiferenteSetor = this.verificarPermissaoEspecial(
						PermissaoEspecial.ATUALIZAR_IMOVEL_MUNICIPIO_LOGRADOURO_DIFERENTE_SETOR, usuario);

		return temPermissaoAtualizarImovelMunicipioLogradouroDiferenteSetor;
	}

	/**
	 * Verifica permiss�o especial para inserir d�bito a cobrar
	 * sem valor da entrada e a taxa de juros
	 * 
	 * @author Ana Maria
	 * @date 27/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirDebitoACobrarSemEntradaSemJuros(Usuario usuario) throws ControladorException{

		boolean temPermissaoInserirDebitoACobrarSemEntradaSemJuros = this.verificarPermissaoEspecial(
						PermissaoEspecial.INSERIR_DEBITO_A_COBRAR_SEM_ENTRADA_SEM_JUROS, usuario);

		return temPermissaoInserirDebitoACobrarSemEntradaSemJuros;
	}

	/**
	 * Verifica permiss�o especial para inserir motivo
	 * da n�o cobran�a
	 * 
	 * @author Ana Maria
	 * @date 03/03/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInformarMotivoNaoCobranca(Usuario usuario) throws ControladorException{

		boolean temPermissaoInformarMotivoNaoCobranca = this.verificarPermissaoEspecial(PermissaoEspecial.INFORMAR_MOTIVO_NAO_COBRANCA,
						usuario);

		return temPermissaoInformarMotivoNaoCobranca;
	}

	/**
	 * Verifica permiss�o especial para informar nova data para vencimento alternativo
	 * 
	 * @author Vivianne Sousa
	 * @date 06/03/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInformarVencimentoAlternativoNovaData(Usuario usuario) throws ControladorException{

		boolean temPermissaoInformarMotivoNaoCobranca = this.verificarPermissaoEspecial(
						PermissaoEspecial.INFORMAR_VENCIMENTO_ALTERNATIVO_NOVA_DATA, usuario);

		return temPermissaoInformarMotivoNaoCobranca;
	}

	/**
	 * Verifica permiss�o especial para n�o testar quantidade de parcelas no Efetuar Parcelamento
	 * 
	 * @author Vivianne Sousa
	 * @date 16/03/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoNaoTestarQtdePrestacaoParcelamento(Usuario usuario) throws ControladorException{

		boolean temPermissaoInformarMotivoNaoCobranca = this.verificarPermissaoEspecial(
						PermissaoEspecial.PARCELAR_NAO_TESTAR_QTDE_DE_PRESTACAO, usuario);

		return temPermissaoInformarMotivoNaoCobranca;
	}

	/**
	 * Verifica permiss�o especial para informar
	 * nova data para vencimento alternativo antes do periodo v�lido
	 * 
	 * @author Vivianne Sousa
	 * @date 19/03/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInformarVencimentoAlternativoAntesDoPeriodoValido(Usuario usuario) throws ControladorException{

		boolean temPermissaoInformarMotivoNaoCobranca = this.verificarPermissaoEspecial(
						PermissaoEspecial.INFORMAR_VENCIMENTO_ALTERNATIVO_ANTES_DO_PERIODO_VALIDO, usuario);

		return temPermissaoInformarMotivoNaoCobranca;
	}

	/**
	 * Manter conta - Alterar vencimento sem ra
	 * 
	 * @author Ana Maria
	 * @date 26/03/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarVencimentoSemRa(Usuario usuario) throws ControladorException{

		boolean temPermissaoInformarMotivoNaoCobranca = this.verificarPermissaoEspecial(PermissaoEspecial.ALTERAR_VENCIMENTO_CONTA_SEM_RA,
						usuario);

		return temPermissaoInformarMotivoNaoCobranca;
	}

	/**
	 * Inserir conta - inserir conta sem cronograma de faturamento e sem atividade efetuar leitura
	 * 
	 * @author Raphael Rossiter
	 * @date 08/05/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirContaFaturamentoAntecipado(Usuario usuario) throws ControladorException{

		boolean temPermissaoInformarMotivoNaoCobranca = this.verificarPermissaoEspecial(
						PermissaoEspecial.INSERIR_CONTA_FATURAMENTO_ANTECIPADO, usuario);

		return temPermissaoInformarMotivoNaoCobranca;
	}

	/**
	 * Inseir D�bito a cobrar
	 * 
	 * @author Ana Maria
	 * @date 23/05/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirDebitoACobrarSemRa(Usuario usuario) throws ControladorException{

		boolean temPermissaoInserirDebitoACobrarSemRa = this.verificarPermissaoEspecial(PermissaoEspecial.INSERIR_DEBITO_A_COBRAR_SEM_RA,
						usuario);

		return temPermissaoInserirDebitoACobrarSemRa;
	}

	/**
	 * Atualizar Cliente
	 * 
	 * @author Vivianne Sousa
	 * @date 30/07/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarNomeCliente(Usuario usuario) throws ControladorException{

		boolean temPermissaoAlterarNomeCliente = this.verificarPermissaoEspecial(PermissaoEspecial.ALTERAR_NOME_CLIENTE, usuario);

		return temPermissaoAlterarNomeCliente;
	}

	/**
	 * Incluir Devolu��o
	 * 
	 * @author Vivianne Sousa
	 * @date 30/07/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoIcluirDevolucaoMaiorValorMaximo(Usuario usuario) throws ControladorException{

		boolean temPermissaoIcluirDevolucaoMaiorValorMaximo = this.verificarPermissaoEspecial(
						PermissaoEspecial.INCLUIR_DEVOLUCAO_MAIOR_VALOR_MAXIMO, usuario);

		return temPermissaoIcluirDevolucaoMaiorValorMaximo;
	}

	/**
	 * [UC0194] Cr�dito a Realizar Permite inserir um cr�dito a realizar
	 * 
	 * @author S�vio Luiz
	 * @since 21/08/2006
	 */
	public boolean verificarPermissaoIcluirCreditoARealizarValorMaximo(Usuario usuario) throws ControladorException{

		boolean temPermissaoIcluirDevolucaoMaiorValorMaximo = this.verificarPermissaoEspecial(
						PermissaoEspecial.INCLUIR_CREDITO_A_REALIZAR_VALOR_MAXIMO, usuario);

		return temPermissaoIcluirDevolucaoMaiorValorMaximo;
	}

	/**
	 * [UC0194] Cr�dito a Realizar Permite inserir um cr�dito a realizar
	 * 
	 * @author S�vio Luiz
	 * @since 21/08/2006
	 */
	public boolean verificarPermissaoIcluirCreditoARealizarQuantidadeParcelasMaximo(Usuario usuario) throws ControladorException{

		boolean temPermissaoIcluirDevolucaoMaiorValorMaximo = this.verificarPermissaoEspecial(
						PermissaoEspecial.INCLUIR_CREDITO_A_REALIZAR_QUANTIDADE_PARCELAS_MAXIMO, usuario);

		return temPermissaoIcluirDevolucaoMaiorValorMaximo;
	}

	/**
	 * [UC0630] Solicitar Emiss�o do Extrato de Debitos
	 * 
	 * @author Vivianne Sousa
	 * @since 30/08/2007
	 */
	public boolean verificarPermissaoIncluirAcrescimoImpontualidadeNoExtratoDeDebitosComDesconto(Usuario usuario)
					throws ControladorException{

		boolean temPermissaoIncluirAcrescimoImpontualidadeNoExtratoDeDebitosComDesconto = this.verificarPermissaoEspecial(
						PermissaoEspecial.INCLUIR_ACRESCIMO_IMPONTUALIDADE_NO_EXTRATO_DE_DEBITOS_COM_DESCONTO, usuario);

		return temPermissaoIncluirAcrescimoImpontualidadeNoExtratoDeDebitosComDesconto;
	}

	/**
	 * [UC0630] Solicitar Emiss�o do Extrato de Debitos
	 * 
	 * @author Vivianne Sousa
	 * @since 30/08/2007
	 */
	public boolean verificarPermissaoRetirarTaxaCobrancaDoExtratoDeDebitos(Usuario usuario) throws ControladorException{

		boolean temPermissaoRetirarTaxaCobrancaDoExtratoDeDebitos = this.verificarPermissaoEspecial(
						PermissaoEspecial.RETIRAR_TAXA_COBRANCA_DO_EXTRATO_DE_DEBITOS, usuario);

		return temPermissaoRetirarTaxaCobrancaDoExtratoDeDebitos;
	}

	/**
	 * [UC0XXX] Consultar D�bitos
	 * 
	 * @author Rafael Corr�a
	 * @since 13/09/2007
	 */
	public boolean verificarPermissaoConsultarDebitosIndicadoNaContaOuTodos(Usuario usuario) throws ControladorException{

		boolean verificarPermissaoConsultarDebitosIndicadoNaContaOuTodos = this.verificarPermissaoEspecial(
						PermissaoEspecial.CONSULTAR_DEBITOS_INDICADO_NA_CONTA_OU_TODOS, usuario);

		return verificarPermissaoConsultarDebitosIndicadoNaContaOuTodos;
	}

	/**
	 * Inserir d�bito a cobrar - inserir debito a cobrar independente da situacao da ligacao de agua
	 * e esgoto do
	 * imovel
	 * 
	 * @author Raphael Rossiter
	 * @date 03/10/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirDebitoACobrarImovelSituacao(Usuario usuario) throws ControladorException{

		boolean temPermissaoInserirDebitoACobrarImovelSituacao = this.verificarPermissaoEspecial(
						PermissaoEspecial.INSERIR_DEBITO_A_COBRAR_IMOVEL_SITUACAO, usuario);

		return temPermissaoInserirDebitoACobrarImovelSituacao;
	}

	/**
	 * Reiniciar um batch
	 * 
	 * @author Rafael Corr�a
	 * @date 06/11/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoReiniciarBatch(Usuario usuario) throws ControladorException{

		boolean temPermissaoReiniciarBatch = this.verificarPermissaoEspecial(PermissaoEspecial.REINICIAR_BATCH, usuario);

		return temPermissaoReiniciarBatch;
	}

	/**
	 * Permite retificar uma conta sem RA
	 * 
	 * @author Raphael Rossiter
	 * @date 09/11/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoRetificarContaSemRA(Usuario usuario) throws ControladorException{

		boolean temPermissaoRetificarContaSemRA = this.verificarPermissaoEspecial(PermissaoEspecial.RETIFICAR_CONTA_SEM_RA, usuario);

		return temPermissaoRetificarContaSemRA;
	}

	/**
	 * Permite excluir debito a cobrar
	 * 
	 * @author Marcio Roberto
	 * @date 26/11/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoExcluirDebitoACobrar(Usuario usuario) throws ControladorException{

		boolean temPermissaoExcluirDebitoACobrar = this.verificarPermissaoEspecial(PermissaoEspecial.EXCLUIR_DEBITO_A_COBRAR, usuario);

		return temPermissaoExcluirDebitoACobrar;
	}

	/**
	 * Permite Gerar OS Seletivas de Hidrometro
	 * 
	 * @author Ivan S�rgio
	 * @date 06/12/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoGerarOSSeletivasHidrometro(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.GERAR_OS_SELETIVA_HIDROMETRO, usuario);

		return temPermissao;
	}

	/**
	 * Permite Gerar Cr�dito para Imoveis com D�bito em cobran�a administrativa
	 * 
	 * @author Saulo Lima
	 * @date 07/08/2008
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirCreditoARealizarImovelComDebito(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.INSERIR_CREDITO_A_REALIZAR_IMOVEL_COM_DEBITO, usuario);

		return temPermissao;
	}

	/**
	 * Permite Cancelar Cr�dito para Imoveis com D�bito em cobran�a administrativa
	 * 
	 * @author Saulo Lima
	 * @date 08/08/2008
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarCreditoARealizarImovelComDebito(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.CANCELAR_CREDITO_A_REALIZAR_IMOVEL_COM_DEBITO, usuario);

		return temPermissao;
	}

	/**
	 * Permite Cancelar Cr�dito que tenha o valor superior ao m�ximo permitido.
	 * 
	 * @author Saulo Lima
	 * @date 08/08/2008
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarCreditoARealizarValorSuperirLimite(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.CANCELAR_CREDITO_A_REALIZAR_VALOR_SUPERIOR, usuario);

		return temPermissao;
	}

	/**
	 * Permite Cancelar Cr�dito que tenha o n�mero de parcelas superior ao n�mero m�ximo permitido.
	 * 
	 * @author Saulo Lima
	 * @date 08/08/2008
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarCreditoARealizarParcelasSuperirLimite(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.CANCELAR_CREDITO_A_REALIZAR_PARCELAS_SUPERIOR, usuario);

		return temPermissao;
	}

	/**
	 * Permite emitir certid�o negativa mesmo que o cliente tenha um superior.
	 * 
	 * @author Rafael Corr�a
	 * @date 12/11/2008
	 * @param usuario
	 */
	public boolean verificarPermissaoEmitirCertidaoNegativaComClienteSuperior(Usuario usuario) throws ControladorException{

		boolean temPermissaoEmitirCertidaoNegativaComClienteSuperior = this.verificarPermissaoEspecial(
						PermissaoEspecial.EMITIR_CERTIDAO_NEGATIVA_COM_CLIENTE_SUPERIOR, usuario);

		return temPermissaoEmitirCertidaoNegativaComClienteSuperior;
	}

	/**
	 * Permite Alterar o valor do servico de OS em Debito a Cobrar.
	 * 
	 * @author Yara Souza
	 * @date 30/04/2010
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarDebitoACobrarValorServico(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.ALTERAR_DEBITO_A_COBRAR_VALOR_SERVICO, usuario);

		return temPermissao;
	}

	/**
	 * Permite Alterar o valor da Data de Leitura do Hidr�metro no Retificar/Calcular/Simular Conta.
	 * 
	 * @author Ailton Sousa
	 * @date 22/07/2011
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarDataLeituraManterHidrometro(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.ALTERAR_DATA_LEITURA_MANTER_HIDROMETRO, usuario);

		return temPermissao;
	}

	/**
	 * Permite Inserir uma guia de pagamento com valor do d�bito maior que o valor limite
	 * pr�-definido para o
	 * tipo do d�bito
	 * 
	 * @author Anderson Italo
	 * @date 11/01/2012
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirGuiaPagamentoValorDebitoMaiorLimite(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.INSERIR_GUIA_PAGAMENTO_VALOR_DEBITO_MAIOR_LIMITE, usuario);

		return temPermissao;
	}

	/**
	 * Permiss�o para imprimir e cancelar conta em Cobran�a Banc�ria
	 * 
	 * @author Anderson Italo
	 * @date 23/04/2012
	 * @param usuario
	 */
	public boolean verificarPermissaoImprimirCancelarContaEmCobrancaBancaria(Usuario usuario) throws ControladorException{

		boolean temPermissaoImprimirCancelarContaEmCobrancaBancaria = this.verificarPermissaoEspecial(
						PermissaoEspecial.IMPRIMIR_CANCELAR_CONTA_EM_COBRANCA_BANCARIA, usuario);

		return temPermissaoImprimirCancelarContaEmCobrancaBancaria;
	}

	/**
	 * Permiss�o para gerar Ordem de Servi�o com restri��o
	 * 
	 * @author Hugo Lima
	 * @date 07/08/2012
	 * @param usuario
	 */
	public boolean verificarPermissaoGerarOrdemServicoRestricao(Usuario usuario) throws ControladorException{

		boolean temPermissaoGerarOrdemServicoRestricao = this.verificarPermissaoEspecial(
						PermissaoEspecial.GERAR_ORDEM_SERVICO_COM_RESTRICAO, usuario);

		return temPermissaoGerarOrdemServicoRestricao;
	}

	/**
	 * Permiss�o para acesso aos dados do im�vel em cobran�a administrativa
	 * 
	 * @author Hugo Lima
	 * @date 08/01/2012
	 * @param usuario
	 */
	public boolean verificarPermissaoAcessarDadosImovelCobrancaAdministrativa(Usuario usuario) throws ControladorException{

		boolean temPermissaoAcessarDadosImovelCobrancaAdministrativa = this.verificarPermissaoEspecial(
						PermissaoEspecial.ACESSAR_DADOS_IMOVEL_COBRANCA_ADMINISTRATIVA, usuario);

		return temPermissaoAcessarDadosImovelCobrancaAdministrativa;
	}

	/**
	 * Verifica permiss�o especial para informar a quantidade de
	 * parcelas excedentes
	 * 
	 * @author Anderson Italo
	 * @date 27/02/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoInformarQuantidadeParcelasExcedentes(Usuario usuario) throws ControladorException{

		boolean temPermissaoInformarMotivoNaoCobranca = this.verificarPermissaoEspecial(
						PermissaoEspecial.PERMITIR_INFORMAR_QTD_PARCELAS_EXCEDENTES, usuario);

		return temPermissaoInformarMotivoNaoCobranca;
	}

	/**
	 * Verifica permiss�o especial para informar o percentual de cobran�a excedente
	 * 
	 * @author Anderson Italo
	 * @date 27/02/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoInformarPercentualCobrancaExcedente(Usuario usuario) throws ControladorException{

		boolean temPermissaoInformarPercentual = this.verificarPermissaoEspecial(
						PermissaoEspecial.PERMITIR_INFORMAR_PERCENTUAL_COBRANCA_EXCEDENTES, usuario);

		return temPermissaoInformarPercentual;
	}

	/**
	 * Verifica permiss�o especial para optar por n�o cobrar taxa ao emitir 2� Via.
	 * 
	 * @author Andr� Lopes
	 * @date 30/07/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoNaoCobrarTaxaSegundaVia(Usuario usuario) throws ControladorException{

		boolean temPermissaoNaoCobrarTaxaSegundaVia = this.verificarPermissaoEspecial(PermissaoEspecial.PERMITIR_NAO_COBRAR_TAXA_DE_2VIA,
						usuario);

		return temPermissaoNaoCobrarTaxaSegundaVia;
	}

	/**
	 * Verifica permiss�o especial para optar por n�o cobrar taxa ao emitir extrato d�bito.
	 * 
	 * @author Andr� Lopes
	 * @date 03/09/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoNaoCobrarTaxaExtratoDebito(Usuario usuario) throws ControladorException{

		boolean temPermissaoNaoCobrarTaxaSegundaVia = this.verificarPermissaoEspecial(
						PermissaoEspecial.PERMITIR_NAO_COBRAR_TAXA_DE_EXTRATO_DEBITO, usuario);

		return temPermissaoNaoCobrarTaxaSegundaVia;
	}

	/**
	 * Permite retirar uma conta de revis�o sem RA
	 * 
	 * @author Anderson Italo
	 * @date 25/11/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetirarContaRevisaoSemRA(Usuario usuario) throws ControladorException{

		boolean temPermissaoRetirarContaRevisaoSemRA = this.verificarPermissaoEspecial(PermissaoEspecial.RETIRAR_CONTA_REVISAO_SEM_RA,
						usuario);

		return temPermissaoRetirarContaRevisaoSemRA;
	}

	/**
	 * Verifica permiss�o especial para optar por n�o cobrar taxa ao emitir extrato d�bito.
	 * 
	 * @author Andr� Lopes
	 * @date 03/09/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoEmitirExtratoDeDebitosoSemAcrescimo(Usuario usuario) throws ControladorException{

		boolean temPermissaoEmitirExtratoDebitoSemAcrescimo = this.verificarPermissaoEspecial(
						PermissaoEspecial.PERMITIR_EMITIR_EXTRATO_DEBITO_SEM_ACRESCIMO, usuario);

		return temPermissaoEmitirExtratoDebitoSemAcrescimo;
	}

	/**
	 * Verifica permiss�o especial para emitir extrato de d�bito de conta retida
	 * 
	 * @author Anderson Italo
	 * @date 26/11/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoEmitirExtratoDebitoContaRetida(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.EMITIR_EXTRATO_DEBITO_CONTA_RETIDA, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para emitir 2� via de conta retida
	 * 
	 * @author Anderson Italo
	 * @date 26/11/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoEmitirSegundaViaContaRetida(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.EMITIR_SEGUNDA_VIA_CONTA_RETIDA, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para manter conta retida
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoManterContaRetida(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.MANTER_CONTA_RETIDA, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para colocar conta retida em revis�o
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoColocarContaRetidaEmRevisao(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.COLOCAR_CONTA_RETIDA_EM_REVISAO, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para retirar conta retida de revis�o
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetirarContaRetidaDeRevisao(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.RETIRAR_CONTA_RETIDA_DE_REVISAO, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para cancelar conta retida
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarContaRetida(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.CANCELAR_CONTA_RETIDA, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para retificar conta retida
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetificarContaRetida(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.RETIFICAR_CONTA_RETIDA, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para manter conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoManterConjuntoContasRetidas(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.MANTER_CONJUNTO_CONTAS_RETIDAS, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para cancelar conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarConjuntoContasRetidas(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.CANCELAR_CONJUNTO_CONTAS_RETIDAS, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para alterar vencimento conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarVencimentoConjuntoContasRetidas(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.ALTERAR_VENCIMENTO_CONJUNTO_CONTAS_RETIDAS, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para alterar vencimento conta retida
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarVencimentoContaRetida(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.ALTERAR_VENCIMENTO_CONTA_RETIDA, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para retirar d�bito cobrado conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetirarDebitoCobradoConjuntoContasRetidas(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.RETIRAR_DEBITO_COBRADO_CONJUNTO_CONTAS_RETIDAS, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para retirar valor �gua esgoto cobrado conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetirarValorAguaEsgotoConjuntoContasRetidas(Usuario usuario) throws ControladorException{

		boolean temPermissao = this
						.verificarPermissaoEspecial(PermissaoEspecial.RETIRAR_VALOR_AGUA_ESGOTO_CONJUNTO_CONTAS_RETIDAS, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para retirar de revis�o conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 10/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetirarRevisaoConjuntoContasRetidas(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.RETIRAR_REVISAO_CONJUNTO_CONTAS_RETIDAS, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para alterar o indicador para inserir processo
	 * administrativo/judiciario
	 * 
	 * @author Carlos Chrystian
	 * @date 27/01/2014
	 * @param usuario
	 */
	public boolean verificarPermissaoIndicadorProcessoAdministrativoJuduciario(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.ALTERAR_INDICADOR_PROCESSO_ADMINISTRATVIVO_JUDICIARIO,
						usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para comandar prescri��o de d�bitos
	 * 
	 * @author Anderson Italo
	 * @date 20/02/2014
	 * @param usuario
	 */
	public boolean verificarPermissaoComandarPrescricaoDebito(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.COMANDAR_PRESCRICAO_DEBITO, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para emitir documento pag�vel para d�bito prescrito
	 * 
	 * @author Anderson Italo
	 * @date 20/02/2014
	 * @param usuario
	 */
	public boolean verificarPermissaoEmitirDocumentoPagavelDebitoPrescrito(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.EMITIR_DOCUMENTO_PAGAVEL_PARA_DEBITO_PRESCRITO, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para estornar em d�vida ativa
	 * 
	 * @author Yara Souza
	 * @date 17/02/2014
	 * @param usuario
	 */
	public boolean verificarPermissaoEstonoDividaAtiva(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.ESTORNAR_PAGAMENTO_DIVIDA_ATIVA, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para estornar em d�vida ativa
	 * 
	 * @author Yara Souza
	 * @date 17/02/2014
	 * @param usuario
	 */
	public boolean verificarPermissaoEstonoDividaAtivaNumMaxDias(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.ESTORNAR_PAGAMENTO_DIVIDA_ATIVA_NUM_MAX_DIAS, usuario);

		return temPermissao;
	}

	/**
	 * Verifica permiss�o especial para estornar em d�vida ativa
	 * 
	 * @author Yara Souza
	 * @date 17/02/2014
	 * @param usuario
	 */
	public boolean verificarPermissaoEstonoDividaAtivaValorMaximo(Usuario usuario) throws ControladorException{

		boolean temPermissao = this.verificarPermissaoEspecial(PermissaoEspecial.ESTORNAR_PAGAMENTO_DIVIDA_ATIVA_VALOR_MAX, usuario);

		return temPermissao;
	}


}