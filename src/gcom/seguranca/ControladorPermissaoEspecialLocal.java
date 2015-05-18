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

package gcom.seguranca;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;

/**
 * Definição da lógica de negócio do Session Bean de
 * ControladorPermissaoEspecial
 * 
 * @author Rodrigo Silveira
 * @created 07/11/2006
 */

public interface ControladorPermissaoEspecialLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * Verifica as permissões especiais do usuário por funcionalidade informada
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
	 * Verifica as permissões especiais do usuário por funcionalidade informada
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
	 * Verifica permissão especial para aceitar um valor de entrada menor q o valor mínimo de
	 * entrada
	 * na terceira página de Efetuar Parcelamento Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 28/11/2006
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoValMinimoEntrada(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para atualizar um cliente que seja usuário da tarifa social
	 * 
	 * @author Rafael Corrêa
	 * @date 16/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoAtualizarUsuarioTarifaSocial(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para atualizar um LOGRADOURO_BAIRRO
	 * 
	 * @author Raphael Rossiter, Romulo Aurelio
	 * @date 24/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoAtualizarLogradouroBairro(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para inserir Imovel com logradouro.municipio
	 * diferente de setorComercial.municipio
	 * 
	 * @author Rômulo Aurélio
	 * @date 23/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirImovelMunicipioLogradouroDiferenteSetor(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para atualizar Imovel com logradouro.municipio
	 * diferente de setorComercial.municipio
	 * 
	 * @author Rômulo Aurélio
	 * @date 23/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoAtualizarImovelMunicipioLogradouroDiferenteSetor(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para inserir débito a cobrar
	 * sem valor da entrada e a taxa de juros
	 * 
	 * @author Ana Maria
	 * @date 27/02/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirDebitoACobrarSemEntradaSemJuros(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para inserir motivo
	 * da não cobrança
	 * 
	 * @author Ana Maria
	 * @date 03/03/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInformarMotivoNaoCobranca(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para NÃO gerar débito no informar retorno OS fiscalização
	 * 
	 * @author Raphael Rossiter
	 * @date 03/03/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoGeracaoDebitoOSFiscalizacao(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para informar nova data para vencimento alternativo
	 * 
	 * @author Vivianne Sousa
	 * @date 06/03/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoInformarVencimentoAlternativoNovaData(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para não testar quantidade de parcelas no Efetuar Parcelamento
	 * 
	 * @author Vivianne Sousa
	 * @date 16/03/2007
	 * @param httpServletRequest
	 * @param usuario
	 */
	public boolean verificarPermissaoNaoTestarQtdePrestacaoParcelamento(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para informar
	 * nova data para vencimento alternativo antes do periodo válido
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
	 * Inseir Débito a cobrar sem RA
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
	 * Incluir Devolução
	 * 
	 * @author Vivianne Sousa
	 * @date 30/07/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoIcluirDevolucaoMaiorValorMaximo(Usuario usuario) throws ControladorException;

	/**
	 * [UC0194] Crédito a Realizar Permite inserir um crédito a realizar
	 * 
	 * @author Sávio Luiz
	 * @since 21/08/2006
	 */
	public boolean verificarPermissaoIcluirCreditoARealizarValorMaximo(Usuario usuario) throws ControladorException;

	/**
	 * [UC0194] Crédito a Realizar Permite inserir um crédito a realizar
	 * 
	 * @author Sávio Luiz
	 * @since 21/08/2006
	 */
	public boolean verificarPermissaoIcluirCreditoARealizarQuantidadeParcelasMaximo(Usuario usuario) throws ControladorException;

	/**
	 * [UC0630] Solicitar Emissão do Extrato de Debitos
	 * 
	 * @author Vivianne Sousa
	 * @since 30/08/2007
	 */
	public boolean verificarPermissaoIncluirAcrescimoImpontualidadeNoExtratoDeDebitosComDesconto(Usuario usuario)
					throws ControladorException;

	/**
	 * [UC0630] Solicitar Emissão do Extrato de Debitos
	 * 
	 * @author Vivianne Sousa
	 * @since 30/08/2007
	 */
	public boolean verificarPermissaoRetirarTaxaCobrancaDoExtratoDeDebitos(Usuario usuario) throws ControladorException;

	/**
	 * [UC0XXX] Consultar Débitos
	 * 
	 * @author Rafael Corrêa
	 * @since 13/09/2007
	 */
	public boolean verificarPermissaoConsultarDebitosIndicadoNaContaOuTodos(Usuario usuario) throws ControladorException;

	/**
	 * Inserir débito a cobrar - inserir debito a cobrar independente da situacao da ligacao de agua
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
	 * @author Rafael Corrêa
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
	 * @author Ivan Sérgio
	 * @date 06/12/2007
	 * @param usuario
	 */
	public boolean verificarPermissaoGerarOSSeletivasHidrometro(Usuario usuario) throws ControladorException;

	/**
	 * Permite Gerar Crédito para Imoveis com Débito em cobrança administrativa
	 * 
	 * @author Saulo Lima
	 * @date 07/08/2008
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirCreditoARealizarImovelComDebito(Usuario usuario) throws ControladorException;

	/**
	 * Permite Cancelar Crédito para Imoveis com Débito em cobrança administrativa
	 * 
	 * @author Saulo Lima
	 * @date 08/08/2008
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarCreditoARealizarImovelComDebito(Usuario usuario) throws ControladorException;

	/**
	 * Permite Cancelar Crédito que tenha o valor superior ao máximo permitido.
	 * 
	 * @author Saulo Lima
	 * @date 08/08/2008
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarCreditoARealizarValorSuperirLimite(Usuario usuario) throws ControladorException;

	/**
	 * Permite Cancelar Crédito que tenha o número de parcelas superior ao número máximo permitido.
	 * 
	 * @author Saulo Lima
	 * @date 08/08/2008
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarCreditoARealizarParcelasSuperirLimite(Usuario usuario) throws ControladorException;

	/**
	 * Permite emitir certidão negativa mesmo que o cliente tenha um superior.
	 * 
	 * @author Rafael Corrêa
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
	 * Permite Alterar o valor da Data de Leitura do Hidrômetro no Retificar/Calcular/Simular Conta.
	 * 
	 * @author Ailton Sousa
	 * @date 22/07/2011
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarDataLeituraManterHidrometro(Usuario usuario) throws ControladorException;

	/**
	 * Permite Inserir uma guia de pagamento com valor do débito maior que o valor limite
	 * pré-definido para o
	 * tipo do débito
	 * 
	 * @author Anderson Italo
	 * @date 11/01/2012
	 * @param usuario
	 */
	public boolean verificarPermissaoInserirGuiaPagamentoValorDebitoMaiorLimite(Usuario usuario) throws ControladorException;

	/**
	 * Permissão para imprimir e cancelar conta em Cobrança Bancária
	 * 
	 * @author Anderson Italo
	 * @date 23/04/2012
	 * @param usuario
	 */
	public boolean verificarPermissaoImprimirCancelarContaEmCobrancaBancaria(Usuario usuario) throws ControladorException;

	/**
	 * Permissão para gerar Ordem de Serviço com restrição
	 * 
	 * @author Hugo Lima
	 * @date 07/08/2012
	 * @param usuario
	 */
	public boolean verificarPermissaoGerarOrdemServicoRestricao(Usuario usuario) throws ControladorException;

	/**
	 * Permissão para acesso aos dados do imóvel em cobrança administrativa
	 * 
	 * @author Hugo Lima
	 * @date 08/01/2012
	 * @param usuario
	 */
	public boolean verificarPermissaoAcessarDadosImovelCobrancaAdministrativa(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para informar a quantidade de
	 * parcelas excedentes
	 * 
	 * @author Anderson Italo
	 * @date 27/02/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoInformarQuantidadeParcelasExcedentes(Usuario usuario) throws ControladorException;
	
	/**
	 * Verifica permissão especial para informar o percentual de cobrança excedente
	 * 
	 * @author Anderson Italo
	 * @date 27/02/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoInformarPercentualCobrancaExcedente(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para optar por não cobrar taxa ao emitir 2° Via.
	 * 
	 * @author André Lopes
	 * @date 30/07/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoNaoCobrarTaxaSegundaVia(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para optar por não cobrar taxa ao emitir extrato débito.
	 * 
	 * @author André Lopes
	 * @date 03/09/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoNaoCobrarTaxaExtratoDebito(Usuario usuario) throws ControladorException;

	/**
	 * Permite retirar uma conta de revisão sem RA
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
	 * Verifica permissão especial para emitir extrato de débito de conta retida
	 * 
	 * @author Anderson Italo
	 * @date 26/11/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoEmitirExtratoDebitoContaRetida(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para emitir 2ª via de conta retida
	 * 
	 * @author Anderson Italo
	 * @date 26/11/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoEmitirSegundaViaContaRetida(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para manter conta retida
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoManterContaRetida(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para colocar conta retida em revisão
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoColocarContaRetidaEmRevisao(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para retirar conta retida de revisão
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetirarContaRetidaDeRevisao(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para cancelar conta retida
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarContaRetida(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para retificar conta retida
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetificarContaRetida(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para manter conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoManterConjuntoContasRetidas(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para cancelar conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoCancelarConjuntoContasRetidas(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para alterar vencimento conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarVencimentoConjuntoContasRetidas(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para alterar vencimento conta retida
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoAlterarVencimentoContaRetida(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para retirar débito cobrado conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetirarDebitoCobradoConjuntoContasRetidas(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para retirar valor água esgoto cobrado conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 02/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetirarValorAguaEsgotoConjuntoContasRetidas(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para retirar de revisão conjunto contas retidas
	 * 
	 * @author Anderson Italo
	 * @date 10/12/2013
	 * @param usuario
	 */
	public boolean verificarPermissaoRetirarRevisaoConjuntoContasRetidas(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para alterar o indicador para inserir processo
	 * administrativo/judiciario
	 * 
	 * @author Carlos Chrystian
	 * @date 27/01/2014
	 * @param usuario
	 */
	public boolean verificarPermissaoIndicadorProcessoAdministrativoJuduciario(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para comandar prescrição de débitos
	 * 
	 * @author Anderson Italo
	 * @date 20/02/2014
	 * @param usuario
	 */
	public boolean verificarPermissaoComandarPrescricaoDebito(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para emitir documento pagável para débito prescrito
	 * 
	 * @author Anderson Italo
	 * @date 20/02/2014
	 * @param usuario
	 */
	public boolean verificarPermissaoEmitirDocumentoPagavelDebitoPrescrito(Usuario usuario) throws ControladorException;


	/**
	 * Verifica permissão especial para estornar em dívida ativa
	 * 
	 * @author Yara Souza
	 * @date 17/02/2014
	 * @param usuario
	 */

	public boolean verificarPermissaoEstonoDividaAtiva(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para estornar em dívida ativa
	 * 
	 * @author Yara Souza
	 * @date 17/02/2014
	 * @param usuario
	 */

	public boolean verificarPermissaoEstonoDividaAtivaNumMaxDias(Usuario usuario) throws ControladorException;

	/**
	 * Verifica permissão especial para estornar em dívida ativa
	 * 
	 * @author Yara Souza
	 * @date 17/02/2014
	 * @param usuario
	 */

	public boolean verificarPermissaoEstonoDividaAtivaValorMaximo(Usuario usuario) throws ControladorException;


}