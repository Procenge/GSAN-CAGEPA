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

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;

/**
 * Defini��o da l�gica de neg�cio do Session Bean de
 * ControladorPermissaoEspecial
 * 
 * @author Rodrigo Silveira
 * @created 07/11/2006
 */

public interface ControladorPermissaoEspecialLocal
				extends javax.ejb.EJBLocalObject {

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
	public boolean verificarPermissaoEspecial(int permissaoEspecial, Usuario usuario) throws ControladorException;

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
	public void verificarPermissaoEspecial(int permissaoEspecial, Usuario usuario, Object objeto) throws ControladorException;

	/**
	 * Verifica permiss�o especial para aceitar um valor de entrada menor q o valor m�nimo de
	 * entrada
	 * na terceira p�gina de Efetuar Parcelamento D�bitos
	 * 
	 * @author Vivianne Sousa
	 * @date 28/11/2006
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoValMinimoEntrada(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para atualizar um cliente que seja usu�rio da tarifa social
	 * 
	 * @author Rafael Corr�a
	 * @date 16/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoAtualizarUsuarioTarifaSocial(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para atualizar um LOGRADOURO_BAIRRO
	 * 
	 * @author Raphael Rossiter, Romulo Aurelio
	 * @date 24/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoAtualizarLogradouroBairro(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para inserir Imovel com logradouro.municipio
	 * diferente de setorComercial.municipio
	 * 
	 * @author R�mulo Aur�lio
	 * @date 23/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirImovelMunicipioLogradouroDiferenteSetor(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para atualizar Imovel com logradouro.municipio
	 * diferente de setorComercial.municipio
	 * 
	 * @author R�mulo Aur�lio
	 * @date 23/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoAtualizarImovelMunicipioLogradouroDiferenteSetor(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para inserir d�bito a cobrar
	 * sem valor da entrada e a taxa de juros
	 * 
	 * @author Ana Maria
	 * @date 27/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirDebitoACobrarSemEntradaSemJuros(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para inserir motivo
	 * da n�o cobran�a
	 * 
	 * @author Ana Maria
	 * @date 03/03/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInformarMotivoNaoCobranca(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para N�O gerar d�bito no informar retorno OS fiscaliza��o
	 * 
	 * @author Raphael Rossiter
	 * @date 03/03/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoGeracaoDebitoOSFiscalizacao(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para informar nova data para vencimento alternativo
	 * 
	 * @author Vivianne Sousa
	 * @date 06/03/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInformarVencimentoAlternativoNovaData(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para n�o testar quantidade de parcelas no Efetuar Parcelamento
	 * 
	 * @author Vivianne Sousa
	 * @date 16/03/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoNaoTestarQtdePrestacaoParcelamento(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para informar
	 * nova data para vencimento alternativo antes do periodo v�lido
	 * 
	 * @author Vivianne Sousa
	 * @date 19/03/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInformarVencimentoAlternativoAntesDoPeriodoValido(Usuario usuario) throws ControladorException;

	/**
	 * Manter conta - Alterar vencimento sem ra
	 * 
	 * @author Ana Maria
	 * @date 26/03/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarVencimentoSemRa(Usuario usuario) throws ControladorException;

	/**
	 * Inserir conta - inserir conta sem cronograma de faturamento e sem atividade efetuar leitura
	 * 
	 * @author Raphael Rossiter
	 * @date 08/05/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirContaFaturamentoAntecipado(Usuario usuario) throws ControladorException;

	/**
	 * Inseir D�bito a cobrar sem RA
	 * 
	 * @author Ana Maria
	 * @date 23/05/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirDebitoACobrarSemRa(Usuario usuario) throws ControladorException;

	/**
	 * Atualizar Cliente
	 * 
	 * @author Vivianne Sousa
	 * @date 30/07/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarNomeCliente(Usuario usuario) throws ControladorException;

	/**
	 * Incluir Devolu��o
	 * 
	 * @author Vivianne Sousa
	 * @date 30/07/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoIcluirDevolucaoMaiorValorMaximo(Usuario usuario) throws ControladorException;

	/**
	 * [UC0194] Cr�dito a Realizar Permite inserir um cr�dito a realizar
	 * 
	 * @author S�vio Luiz
	 * @since 21/08/2006
	 */
	public boolean verificarPermissaoIcluirCreditoARealizarValorMaximo(Usuario usuario) throws ControladorException;

	/**
	 * [UC0194] Cr�dito a Realizar Permite inserir um cr�dito a realizar
	 * 
	 * @author S�vio Luiz
	 * @since 21/08/2006
	 */
	public boolean verificarPermissaoIcluirCreditoARealizarQuantidadeParcelasMaximo(Usuario usuario) throws ControladorException;

	/**
	 * [UC0630] Solicitar Emiss�o do Extrato de Debitos
	 * 
	 * @author Vivianne Sousa
	 * @since 30/08/2007
	 */
	public boolean verificarPermissaoIncluirAcrescimoImpontualidadeNoExtratoDeDebitosComDesconto(Usuario usuario)
					throws ControladorException;

	/**
	 * [UC0630] Solicitar Emiss�o do Extrato de Debitos
	 * 
	 * @author Vivianne Sousa
	 * @since 30/08/2007
	 */
	public boolean verificarPermissaoRetirarTaxaCobrancaDoExtratoDeDebitos(Usuario usuario) throws ControladorException;

	/**
	 * [UC0XXX] Consultar D�bitos
	 * 
	 * @author Rafael Corr�a
	 * @since 13/09/2007
	 */
	public boolean verificarPermissaoConsultarDebitosIndicadoNaContaOuTodos(Usuario usuario) throws ControladorException;

	/**
	 * Inserir d�bito a cobrar - inserir debito a cobrar independente da situacao da ligacao de agua
	 * e esgoto do
	 * imovel
	 * 
	 * @author Raphael Rossiter
	 * @date 03/10/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirDebitoACobrarImovelSituacao(Usuario usuario) throws ControladorException;

	/**
	 * Reiniciar um batch
	 * 
	 * @author Rafael Corr�a
	 * @date 06/11/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoReiniciarBatch(Usuario usuario) throws ControladorException;

	/**
	 * Permite retificar uma conta sem RA
	 * 
	 * @author Raphael Rossiter
	 * @date 09/11/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoRetificarContaSemRA(Usuario usuario) throws ControladorException;

	/**
	 * Permite excluir debito a cobrar
	 * 
	 * @author Marcio Roberto
	 * @date 26/11/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoExcluirDebitoACobrar(Usuario usuario) throws ControladorException;

	/**
	 * Permite Gerar OS Seletivas de Hidrometro
	 * 
	 * @author Ivan S�rgio
	 * @date 06/12/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoGerarOSSeletivasHidrometro(Usuario usuario) throws ControladorException;

	/**
	 * Permite Gerar Cr�dito para Imoveis com D�bito em cobran�a administrativa
	 * 
	 * @author Saulo Lima
	 * @date 07/08/2008
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirCreditoARealizarImovelComDebito(Usuario usuario) throws ControladorException;

	/**
	 * Permite Cancelar Cr�dito para Imoveis com D�bito em cobran�a administrativa
	 * 
	 * @author Saulo Lima
	 * @date 08/08/2008
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarCreditoARealizarImovelComDebito(Usuario usuario) throws ControladorException;

	/**
	 * Permite Cancelar Cr�dito que tenha o valor superior ao m�ximo permitido.
	 * 
	 * @author Saulo Lima
	 * @date 08/08/2008
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarCreditoARealizarValorSuperirLimite(Usuario usuario) throws ControladorException;

	/**
	 * Permite Cancelar Cr�dito que tenha o n�mero de parcelas superior ao n�mero m�ximo permitido.
	 * 
	 * @author Saulo Lima
	 * @date 08/08/2008
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarCreditoARealizarParcelasSuperirLimite(Usuario usuario) throws ControladorException;

	/**
	 * Permite emitir certid�o negativa mesmo que o cliente tenha um superior.
	 * 
	 * @author Rafael Corr�a
	 * @date 12/11/2008
	 * @param usuario
	 */
	public boolean verificarPermissaoEmitirCertidaoNegativaComClienteSuperior(Usuario usuario) throws ControladorException;

	/**
	 * Permite Alterar o valor do servico de OS em Debito a Cobrar.
	 * 
	 * @author Yara Souza
	 * @date 30/04/2010
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarDebitoACobrarValorServico(Usuario usuario) throws ControladorException;

	/**
	 * Permite Alterar o valor da Data de Leitura do Hidr�metro no Retificar/Calcular/Simular Conta.
	 * 
	 * @author Ailton Sousa
	 * @date 22/07/2011
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarDataLeituraManterHidrometro(Usuario usuario) throws ControladorException;

	/**
	 * Permite Inserir uma guia de pagamento com valor do d�bito maior que o valor limite
	 * pr�-definido para o
	 * tipo do d�bito
	 * 
	 * @author Anderson Italo
	 * @date 11/01/2012
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirGuiaPagamentoValorDebitoMaiorLimite(Usuario usuario) throws ControladorException;

	/**
	 * Permiss�o para imprimir e cancelar conta em Cobran�a Banc�ria
	 * 
	 * @author Anderson Italo
	 * @date 23/04/2012
	 * @param usuario
	 */
	public boolean verificarPermissaoImprimirCancelarContaEmCobrancaBancaria(Usuario usuario) throws ControladorException;

	/**
	 * Permiss�o para gerar Ordem de Servi�o com restri��o
	 * 
	 * @author Hugo Lima
	 * @date 07/08/2012
	 * @param usuario
	 */
	public boolean verificarPermissaoGerarOrdemServicoRestricao(Usuario usuario) throws ControladorException;

	/**
	 * Permiss�o para acesso aos dados do im�vel em cobran�a administrativa
	 * 
	 * @author Hugo Lima
	 * @date 08/01/2012
	 * @param usuario
	 */
	public boolean verificarPermissaoAcessarDadosImovelCobrancaAdministrativa(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para informar a quantidade de
	 * parcelas excedentes
	 * 
	 * @author Anderson Italo
	 * @date 27/02/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoInformarQuantidadeParcelasExcedentes(Usuario usuario) throws ControladorException;
	
	/**
	 * Verifica permiss�o especial para informar o percentual de cobran�a excedente
	 * 
	 * @author Anderson Italo
	 * @date 27/02/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoInformarPercentualCobrancaExcedente(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para optar por n�o cobrar taxa ao emitir 2� Via.
	 * 
	 * @author Andr� Lopes
	 * @date 30/07/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoNaoCobrarTaxaSegundaVia(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para optar por n�o cobrar taxa ao emitir extrato d�bito.
	 * 
	 * @author Andr� Lopes
	 * @date 03/09/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoNaoCobrarTaxaExtratoDebito(Usuario usuario) throws ControladorException;

	/**
	 * Permite retirar uma conta de revis�o sem RA
	 * 
	 * @author Anderson Italo
	 * @date 25/11/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetirarContaRevisaoSemRA(Usuario usuario) throws ControladorException;

	/**
	 * @param usuario
	 * @return
	 * @throws ControladorException
	 */
	public boolean verificarPermissaoEmitirExtratoDeDebitosoSemAcrescimo(Usuario usuario) throws ControladorException;


	/**
	 * Verifica permiss�o especial para emitir extrato de d�bito de conta retida
	 * 
	 * @author Anderson Italo
	 * @date 26/11/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoEmitirExtratoDebitoContaRetida(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para emitir 2� via de conta retida
	 * 
	 * @author Anderson Italo
	 * @date 26/11/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoEmitirSegundaViaContaRetida(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para manter conta retida
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoManterContaRetida(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para colocar conta retida em revis�o
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoColocarContaRetidaEmRevisao(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para retirar conta retida de revis�o
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetirarContaRetidaDeRevisao(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para cancelar conta retida
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarContaRetida(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para retificar conta retida
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetificarContaRetida(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para manter conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoManterConjuntoContasRetidas(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para cancelar conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarConjuntoContasRetidas(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para alterar vencimento conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarVencimentoConjuntoContasRetidas(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para alterar vencimento conta retida
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarVencimentoContaRetida(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para retirar d�bito cobrado conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetirarDebitoCobradoConjuntoContasRetidas(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para retirar valor �gua esgoto cobrado conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetirarValorAguaEsgotoConjuntoContasRetidas(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para retirar de revis�o conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 10/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetirarRevisaoConjuntoContasRetidas(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para alterar o indicador para inserir processo
	 * administrativo/judiciario
	 * 
	 * @author Carlos Chrystian
	 * @date 27/01/2014
	 * @param usuario
	 */
	public boolean verificarPermissaoIndicadorProcessoAdministrativoJuduciario(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para comandar prescri��o de d�bitos
	 * 
	 * @author Anderson Italo
	 * @date 20/02/2014
	 * @param usuario
	 */
	public boolean verificarPermissaoComandarPrescricaoDebito(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para emitir documento pag�vel para d�bito prescrito
	 * 
	 * @author Anderson Italo
	 * @date 20/02/2014
	 * @param usuario
	 */
	public boolean verificarPermissaoEmitirDocumentoPagavelDebitoPrescrito(Usuario usuario) throws ControladorException;


	/**
	 * Verifica permiss�o especial para estornar em d�vida ativa
	 * 
	 * @author Yara Souza
	 * @date 17/02/2014
	 * @param usuario
	 */

	public boolean verificarPermissaoEstonoDividaAtiva(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para estornar em d�vida ativa
	 * 
	 * @author Yara Souza
	 * @date 17/02/2014
	 * @param usuario
	 */

	public boolean verificarPermissaoEstonoDividaAtivaNumMaxDias(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permiss�o especial para estornar em d�vida ativa
	 * 
	 * @author Yara Souza
	 * @date 17/02/2014
	 * @param usuario
	 */

	public boolean verificarPermissaoEstonoDividaAtivaValorMaximo(Usuario usuario) throws ControladorException;


}