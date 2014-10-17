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

package gcom.spcserasa;

import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.CobrancaDebitoSituacao;
import gcom.cobranca.NegativacaoComando;
import gcom.cobranca.NegativacaoCriterio;
import gcom.cobranca.Negativador;
import gcom.cobranca.NegativadorContrato;
import gcom.cobranca.NegativadorMovimento;
import gcom.cobranca.NegativadorMovimentoReg;
import gcom.cobranca.NegativadorMovimentoRegItem;
import gcom.cobranca.bean.ComandoNegativacaoHelper;
import gcom.cobranca.bean.ComandoNegativacaoTipoCriterioHelper;
import gcom.cobranca.bean.DadosConsultaNegativacaoHelper;
import gcom.cobranca.bean.ParametrosComandoNegativacaoHelper;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.gui.cobranca.spcserasa.RelatorioNegativacoesExcluidasSomatorioDadosParcelamentoHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.spcserasa.bean.DadosNegativacaoPorImovelHelper;
import gcom.spcserasa.bean.InserirComandoNegativacaoPorCriterioHelper;
import gcom.spcserasa.bean.NegativadorMovimentoHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Marcio Roberto
 * @created 24 de Outubro de 2007
 * @version 1.0
 */

public interface ControladorSpcSerasaLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * Permite inserir negativacao SPC SERASA
	 * [UC0671] Gerar Movimento de Inclus�o de Negativa��o
	 * 
	 * @author Marcio Roberto
	 * @date 24/10/2007
	 */

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 29/10/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarComandoNegativacao(ComandoNegativacaoHelper comandoNegativacaoHelper) throws ControladorException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 29/10/2007
	 * @param comandoNegativacaoHelper
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoParaPaginacao(ComandoNegativacaoHelper comandoNegativacaoHelper, Integer numeroPagina)
					throws ControladorException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 31/10/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarDadosInclusoesComandoNegativacao(Integer idComandoNegativacao) throws ControladorException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 31/10/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarDadosInclusoesComandoNegativacaoParaPaginacao(Integer idComandoNegativacao, Integer numeroPagina)
					throws ControladorException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 09/11/2007
	 * @param idComandoNegativacao
	 * @return ParametrosComandoNegativacaoHelper
	 * @throws ControladorException
	 */
	public ParametrosComandoNegativacaoHelper pesquisarParametrosComandoNegativacao(Integer idComandoNegativacao)
					throws ControladorException;

	/**
	 * [UC0651] Inserir Comando Negativa��o [FS0015] Verificar exist�ncia de
	 * negativa��o para o im�vel no negativador
	 * 
	 * @author Ana Maria
	 * @date 04/12/2007
	 * @param idNegativador
	 * @param idImovel
	 * @return Boolean
	 * @throws ErroRepositorioException
	 */

	public Boolean verificarExistenciaNegativacaoImovel(Integer idNegativador, Integer idImovel) throws ControladorException;

	/**
	 * [UC0651] Inserir Comando Negativa��o
	 * [SB0003] Determinar Data Prevista para Execu��o do Comando
	 * 
	 * @author Ana Maria
	 * @date 11/12/2007
	 * @param idNegativador
	 * @return Date
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarUltimaDataRealizacaoComando(Integer idNegativador) throws ControladorException;

	/**
	 * [UC0365] Inserir Comando Negativa��o
	 * [SB0004] Inclui Comando de Negativa��o por crit�rio
	 * 
	 * @author Ana Maria
	 * @date 13/12/2007
	 * @throws ControladorException
	 */
	public Integer inserirComandoNegativacao(InserirComandoNegativacaoPorCriterioHelper helper) throws ControladorException;

	/**
	 * M�todo que gera Movimento de Inclus�o de Negativa��o
	 * [UC0671] - Gerar Movimento da Negativa��o
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 */

	/*
	 * public void gerarMovimentoInclusaoNegativacao(int idFuncionalidadeIniciada, String
	 * tipoComando,
	 * int idNegativador, String identificacaoCI, int idUsuarioResponsavel,
	 * Collection<DadosNegativacaoPorImovelHelper> dadosImoveis,
	 * int idComandoNegativacao) throws ControladorException;
	 */
	public void gerarMovimentoInclusaoNegativacao(
					// String tipoComando,
					int idNegativador, String identificacaoCI, int idUsuarioResponsavel,
					Collection<DadosNegativacaoPorImovelHelper> dadosImoveis) throws ControladorException;

	/**
	 * M�todo consuta os Negativadores que tenham movimento de Exclus�o do spc ou seraza
	 * [UC0673] - Gerar Movimento da Exclus�o de Negativa��o
	 * 
	 * @author Thiago Toscano
	 * @date 21/12/2007
	 */
	public Collection consultarNegativadoresParaExclusaoMovimento() throws ControladorException;

	/**
	 * M�todo consuta os Negativadores que tenham movimento de Exclus�o do spc ou serasa
	 * [UC0673] - Gerar Movimento da Exclus�o de Negativa��o
	 * [SB0001] - Gerar Movimento da Exclus�o de Negativa��o
	 * 
	 * @author Thiago Toscano
	 * @date 26/12/2007
	 */
	public Collection gerarMovimentoExclusaoNegativacao(Integer[] id) throws ControladorException;

	/**
	 * M�todo consuta os Negativadores que tenham movimento de Exclus�o do spc ou serasa
	 * [UC0673] - Gerar Movimento da Exclus�o de Negativa��o
	 * [SB0002] - Gerar TxT de Movimento de Exclus�o de Negativacao
	 * 
	 * @author Thiago Toscano
	 * @param idMovimento
	 *            -
	 * @return Collection - NegativadorMovimento
	 * @date 26/12/2007
	 */
	public Collection gerarArquivoTXTMovimentoExclusaoNegativacao(Integer idMovimento) throws ControladorException;

	/**
	 * M�todo usado para pesquisa de Comando Negativa��o (Helper)
	 * [UC0655] Filtrar Comando Negativa��o
	 * 
	 * @author Thiago Vieira
	 * @date 02/01/2008
	 * @param comandoNegativacaoHelper
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoHelper(ComandoNegativacaoHelper comandoNegativacaoHelper) throws ControladorException;

	/**
	 * M�todo usado para contar a quantidade de ocorr�ncias de
	 * negativadorMovimento Registro aceitos usado no caso de uso [UC0681]
	 * 
	 * @author Yara Taciane
	 * @date 21/01/2008
	 * @param negativadorMovimentoHelper
	 * @return
	 * @throws ControladorException
	 */
	public Integer verificarTotalRegistrosAceitos(Integer idNegativadorMovimento) throws ControladorException;

	/**
	 * [UC0651] Inserir Comando Negativa��o
	 * [FS0026] Verificar exist�ncia de comando para o negativador na data
	 * 
	 * @author Ana Maria
	 * @date 07/05/2008
	 * @param idNegativador
	 * @param Data
	 * @return boolean
	 */
	public boolean verificarExistenciaComandoNegativador(String idNegativador, Date dataPrevista) throws ControladorException;

	/**
	 * Validar o Arquivo de Movimento de Retorno.
	 * 
	 * @author Yara T. Souza
	 * @date 09/12/2008
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] validarArquivoMovimentoRetorno(StringBuilder stringBuilderTxt, Negativador negativador) throws ControladorException;

	/**
	 * [UC0317] Manter Comando de Negativa��o por Crit�rio
	 * [SB0001] Excluir Comando de Negativa��o por Crit�rio
	 * 
	 * @author Ana Maria
	 * @param ids
	 * @param usuarioLogado
	 * @created 21/01/2008
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void removerComandoNegativacaoPorCriterio(String[] ids) throws ControladorException;

	/**
	 * [UC0651] Inserir Comando Negativa��o
	 * [SB0003] Determinar Data Prevista para Execu��o do Comando
	 * 
	 * @author Ana Maria
	 * @date 11/12/2007
	 * @param idNegativador
	 * @return Date
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarUltimaDataRealizacaoComando(Integer idNegativador, Integer icSimulacao) throws ControladorException;

	/**
	 * Retorna o somat�rio do VALOR PAGO e do VALOR CANCELADO
	 * [UC0693] Gerar Relat�rio Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Vivianne Sousa
	 * @date 29/04/2009
	 */
	public Object[] pesquisarSomatorioValorPagoEValorCancelado(Integer idNegativadorMovimentoReg) throws ControladorException;

	/**
	 * Retorna o somat�rio do valor do D�bito do NegativadoMovimentoReg pela CobrancaDebitoSituacao
	 * [UC0693] Gerar Relat�rio Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public BigDecimal pesquisarSomatorioValorDebito(NegativadorMovimentoReg negativadorMovimentoReg,
					CobrancaDebitoSituacao cobrancaDebitoSituacao) throws ControladorException;

	/**
	 * Gerar Relat�rio Negatica��es Exclu�das
	 * Pesquisar o somat�rio do valor paga ou parcelado pelo registro negativador
	 * 
	 * @author Yara T. Souza
	 * @date 14/01/2009
	 * @param idNegativadorMovimentoReg
	 *            ,idCobrancaDebitoSituacao
	 * @return
	 * @throws ErroRepositorioException
	 */

	public BigDecimal pesquisarSomatorioNegativadorMovimentoRegItens(Integer idNegativadorMovimentoReg, Integer idCobrancaDebitoSituacao)
					throws ControladorException;

	/**
	 * Conta a quantidade de Neg
	 * [UC0693] Gerar Relat�rio Negativacoes Excluidas
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public Integer pesquisarRelatorioNegativacoesExcluidasCount(DadosConsultaNegativacaoHelper helper) throws ControladorException;

	/**
	 * Conta a quantidade de Neg
	 * [UC0693] Gerar Relat�rio Negativacoes Excluidas
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public Collection pesquisarRelatorioNegativacoesExcluidas(DadosConsultaNegativacaoHelper helper) throws ControladorException;

	/**
	 * Conta a quantidade de Clientes Negativados
	 * [UC0693] Gerar Relat�rio Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public Integer pesquisarRelatorioAcompanhamentoClientesNegativadorCount(DadosConsultaNegativacaoHelper helper)
					throws ControladorException;

	/**
	 * Conta a quantidade de Clientes Negativados
	 * [UC0693] Gerar Relat�rio Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public Collection pesquisarRelatorioAcompanhamentoClientesNegativador(DadosConsultaNegativacaoHelper helper)
					throws ControladorException;

	/**
	 * [UC0678] Relat�rio Negativador Resultado Simulacao
	 * pesquisar Negativador Resultado Simulacao
	 * 
	 * @author Yara Taciane
	 * @date 09/05/2008
	 * @param idNegativacaoComando
	 * @return NegativadorResultadoSimulacao
	 */
	public Collection pesquisarNegativadorResultadoSimulacao(Integer idNegativacaoComando) throws ControladorException;

	/**
	 * [UC0688] - Gerar Resumo Diario da Negativa��o
	 * [UC0694] - Gerar Relat�rio Negativa��es Exclu�das
	 * pesquisa ocorr�ncia na tabela NEGATIVADOR_MOVIMENTO_REG_PARCELAMENTO para NMRG_ID=NMRG_ID
	 * da tabela NEGATIVADOR_MOVIMENTO_REG)
	 * 
	 * @author Vivianne Sousa
	 * @data 28/04/2009
	 * @param idNegativadorMovimentoReg
	 * @return RelatorioNegativacoesExcluidasSomatorioDadosParcelamentoHelper
	 */
	public RelatorioNegativacoesExcluidasSomatorioDadosParcelamentoHelper pesquisarNegativadorMovimentoRegParcelamento(
					Integer idNegativadorMovimentoReg) throws ControladorException;

	/**
	 * M�todo usado para apresentar os registros de negativadorMovimento Registro aceitos
	 * usado no caso de uso [UC0681]
	 * 
	 * @author Yara Taciane
	 * @date 21/01/2008
	 * @param negativadorMovimentoHelper
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarNegativadorMovimentoRegistroAceito(NegativadorMovimentoHelper negativadorMovimentoHelper)
					throws ControladorException;

	/**
	 * Retorna o NegativadorMovimentoReg
	 * [UC0673] Excluir Negativa��o Online
	 * 
	 * @author Yara Taciane
	 * @date 27/03/2008
	 */
	public NegativadorMovimentoReg pesquisarNegativadorMovimentoRegInclusao(Imovel imovel, Negativador negativador)
					throws ControladorException;

	/**
	 * Gerar Relat�rio de Negativa��es Exclu�das
	 * 
	 * @author Yara T. Souza
	 * @date 16/01/2009
	 */
	public List pesquisarNegativadorMovimentoRegItens(Integer idNegativadorMovimentoReg) throws ControladorException;

	/**
	 * [UC0678] Relat�rio Negativador Resultado Simulacao
	 * pesquisar Negativador Resultado Simulacao
	 * 
	 * @author Yara Taciane
	 * @date 09/05/2008
	 * @param idNegativacaoComando
	 * @return NegativadorResultadoSimulacao
	 */
	public NegativacaoCriterio pesquisarNegativacaoCriterio(Integer idNegativacaoComando) throws ControladorException;

	/**
	 * M�todo usado para Pesquisar se a inclus�o do im�vel est� com retorno ou foi aceita.
	 * usado no caso de uso [0675]
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegatiacaoParaImovel(Imovel imovel, Negativador negativador) throws ControladorException;

	/**
	 * Pesquisar se a negativa��o do im�vel .
	 * [UC0675] Excluir Negativa��o Online.
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public Collection pesquisarImovelNegativado(Imovel imovel, Negativador negativador) throws ControladorException;

	/**
	 * [UC0694] Relat�rio Negativa��o Exclu�das Pesquisar data da Negativa��o Exclu�da
	 * 
	 * @author Yara Taciane
	 * @date 09/05/2008
	 * @param idImovel
	 * @param idNegativacaoComando
	 * @return Date
	 */
	public Date pesquisarDataExclusaoNegativacao(int idImovel, int idNegativacaoComando) throws ControladorException;

	public NegativacaoCriterio pesquisarComandoNegativacaoSimulado(Integer idComandoNegativacao) throws ControladorException;

	/**
	 * [UC0693] - Gerar Relat�rio Acompanhamento de Clientes Negativados
	 * pesquisa ocorr�ncia na tabela NEGATIVADOR_MOVIMENTO_REG_PARCELAMENTO para NMRG_ID=NMRG_ID
	 * da tabela NEGATIVADOR_MOVIMENTO_REG)
	 * 
	 * @author Vivianne Sousa
	 * @data 30/04/2009
	 */
	public Object[] pesquisarDadosParcelamentoRelatorioAcompanhamentoClientesNegativados(Integer idNegativadorMovimentoReg)
					throws ControladorException;

	/**
	 * M�todo usado para consulta de comandos de negativa��o por tipo de comando
	 * (nesse caso matr�cula)usado no caso de uso [UC0691]
	 * 
	 * @author Thiago Vieira
	 * @date 16/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoTipoMatricula(ComandoNegativacaoHelper comandoNegativacaoHelper, Integer numeroPagina)
					throws ControladorException;

	/**
	 * M�todo usado para consulta de comandos de negativa��o por tipo de comando
	 * (nesse caso crit�rio)usado no caso de uso [UC0691]
	 * 
	 * @author Thiago Vieira
	 * @date 16/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoTipoCriterio(ComandoNegativacaoTipoCriterioHelper comandoNegativacaoTipoCriterioHelper,
					Integer numeroPagina) throws ControladorException;

	/**
	 * Insere Processo para Registrar Movimento de Retorno do Negativadaor.
	 * 
	 * @author Yara T. Souza
	 * @param usuario
	 * @date 09/12/2008
	 * @return void
	 * @throws ErroRepositorioException
	 */
	public void inserirProcessoRegistrarNegativadorMovimentoRetorno(Usuario usuario) throws ControladorException;

	/**
	 * M�todo que inicia o caso de uso de Gerar Movimento de Inclusao de Negativacao
	 * [UC0671] Gerar Movimento de Inclusao de Nwegativa��o
	 * [Fluxo Principal]
	 * 
	 * @author Thiago Toscano
	 * @date 21/02/2008
	 * @param idComandoNegativacao
	 * @param tipoComando
	 * @param comunicacaoInterna
	 * @param idNegativador
	 * @param idUsuarioResponsaval
	 * @param ObjectImovel
	 *            - Collecao de
	 *            [0] Integer - Matricula do Imovel
	 *            [1] Integer - id do cliente da negativacao
	 *            [2] String - cpf do cliente da negativacao
	 *            [3] String - cnpj do cliente da negativaca
	 *            [4] Collection - lista da contas e guias de pagamento do imovel
	 *            [5] Intetger - quantidade de itens em d�bito do imovel
	 *            [6] BigDecimal - valor total dos d�bitos do imovel
	 * @param dataPrevista
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public Integer gerarMovimentoInclusaoNegativacao(
					// Integer idComandoNegativacao, //Short tipoComando,
					String comunicacaoInterna, Integer idNegativador, int idUsuarioResponsaval, Collection ObjectImovel, Date dataPrevista,
					Integer idCobrancaAcaoAtividadeCronograma, Integer idCobrancaAcaoAtividadeComando) throws ControladorException;

	/**
	 * [UC0652] Manter Comando de Negativa��o por Crit�rio
	 * [SB0002] Atualizar Comando de Negativa��o por crit�rio
	 * 
	 * @author Ana Maria
	 * @date 24/01/2008
	 * @throws ControladorException
	 */
	public void atualizarComandoNegativacao(InserirComandoNegativacaoPorCriterioHelper helper) throws ControladorException;

	/**
	 * M�todo que atualiza o n�mero da execu��o do resumo da negativa��o na tabela
	 * SISTEMA_PARAMETROS mais um [UC0688] Gerar Resumo Di�rio da
	 * Negativa��o. *
	 * 
	 * @author Yara Taciane
	 * @date 11/11/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarNumeroExecucaoResumoNegativacao(Integer idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * M�todo que gera os movimento de inclusao de negativacao por Matricula de Imovel
	 * [UC0671] Gerar Movimento de Inclusao de Nwegativa��o
	 * [SB0002] Gerar Movimento de Inclusao de Negativacao Por Crit�rio
	 * 
	 * @author Thiago Toscano
	 * @date 21/02/2008
	 * @param nc
	 * @param Object
	 *            [] obj
	 *            obj[0] Integer - quantidadeInclusao
	 *            obj[1] Integer - quantidadeItens
	 *            obj[2] BigDecimal - valorTotalDebito
	 * @return
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	// public void gerarMovimentodeInclusaoNegativacaoPorCriterio(Integer idLocalidade,
	// NegativacaoCriterio nCriterio, Negativador n,
	// NegativacaoComando nComando, NegativadorContrato nContrato, NegativadorMovimento
	// negMovimento,
	// Integer idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * M�todo usado para Regitrar Movimento de Retorno de Negativa��o
	 * [UC0672] Regitrar Movimento de Retorno de Negativa��o
	 * 
	 * @author Yara Taciane
	 * @date 11/01/2008
	 * @param registrarNegativadorMovimentoRetorno
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection registrarNegativadorMovimentoRetorno(Integer idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * M�todo usado para pesquisar um Negativador Movimento.
	 * [UC0682] - Filtrar Movimento Negativador
	 * 
	 * @author Yara Taciane
	 * @date 21/01/2008
	 * @param negativadorMovimentoHelper
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarNegativadorMovimento(NegativadorMovimentoHelper negativadorMovimentoHelper, Integer numeroPagina)
					throws ControladorException;

	/**
	 * M�todo usado para apresentar os registros de negativadorMovimento Registro aceitos
	 * usado no caso de uso [UC0681]
	 * 
	 * @author Yara Taciane
	 * @date 21/01/2008
	 * @param negativadorMovimentoHelper
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarNegativadorMovimentoRegistroAceito(NegativadorMovimentoHelper negativadorMovimentoHelper,
					Integer numeroPagina) throws ControladorException;

	/**
	 * Informa��es Atualizadas em (maior data e hora da �ltima execu��o
	 * 
	 * @author Yara Taciane
	 * @date 28/07/2008
	 */
	public Date getDataUltimaAtualizacaoResumoNegativacao(Integer numeroExecucaoResumoNegativacao) throws ControladorException;

	/**
	 * M�todo usado para pesquisar um Negativador Movimento.
	 * [UC0682] - Filtrar Movimento Negativador
	 * 
	 * @author Yara Taciane
	 * @date 21/01/2008
	 * @param negativadorMovimentoHelper
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarNegativadorMovimento(NegativadorMovimentoHelper negativadorMovimentoHelper) throws ControladorException;

	/**
	 * M�todo usado para consulta de comandos de negativa��o por tipo de comando
	 * (nesse caso crit�rio)usado no caso de uso [UC0691]
	 * 
	 * @author Thiago Vieira
	 * @date 16/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoTipoCriterio(ComandoNegativacaoTipoCriterioHelper comandoNegativacaoTipoCriterioHelper)
					throws ControladorException;

	/**
	 * M�todo usado para consulta de comandos de negativa��o por tipo de comando
	 * (nesse caso matr�cula)usado no caso de uso [UC0691]
	 * 
	 * @author Thiago Vieira
	 * @date 16/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoTipoMatricula(ComandoNegativacaoHelper comandoNegativacaoHelper)
					throws ControladorException;

	/**
	 * [UC0688] Gerar Resumo Diario de Negativaocao
	 * 
	 * @autor Genival Barbosa
	 * @date 26/10/2011
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void gerarResumoDiarioNegativacao(Integer idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Gerar Registro de negativacao tipo header
	 * [UC0671] Gerar Movimento de Inclusao de Nwegativa��o
	 * [SB0008] Gerar Registro de tipo header
	 * 
	 * @author Thiago Toscano
	 * @date 21/02/2008
	 * @param nc
	 * @param Object
	 *            [] obj
	 *            obj[0] Integer - quantidadeInclusao
	 *            obj[1] Integer - quantidadeItens
	 *            obj[2] BigDecimal - valorTotalDebito
	 * @return
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public Integer gerarRegistroDeInclusaoTipoHeader(
					// Short tipoComando,
					Integer quantidadeRegistro, Negativador n, NegativadorContrato nContrato,
					// NegativacaoComando nComando,
					NegativacaoCriterio nCriterio, NegativadorMovimento NegativacaoMovimento,
					DadosNegativacaoPorImovelHelper dadosNegativacaoPorImovelHelper) throws ControladorException;

	/**
	 * M�todo consuta os Negativadores que tenham movimento de Exclus�o do spc ou serasa
	 * [UC0673] - Gerar Movimento da Exclus�o de Negativa��o
	 * [SB0001] - Gerar Movimento da Exclus�o de Negativa��o
	 * 
	 * @author Thiago Toscano
	 * @date 26/12/2007
	 * @author isilva
	 * @date 01/10/2010
	 *       Receber usu�rio
	 * @param idFuncionalidadeIniciada
	 * @param id
	 * @param usuario
	 * @return
	 * @throws ControladorException
	 */
	public Collection gerarMovimentoExclusaoNegativacao(Integer idFuncionalidadeIniciada, Integer[] id, Usuario usuario)
					throws ControladorException;

	/**
	 * Consulta as rotas para a geracao do resumo diario da negativacao
	 * [UC0688] Gerar Resumo Di�rio da Negativa��o
	 * Fluxo principal Item 1.0
	 * 
	 * @author Francisco do Nascimento
	 * @date 03/02/2009
	 */
	public List consultarRotasParaGerarResumoDiarioNegativacao() throws ControladorException;

	/**
	 * M�todo que retorna todas NegativacaoComando que ainda nao tenha sido executada
	 * (dataHoraRealizacao == null)
	 * [UC0687] Executar Comando de Negativa��o
	 * [Fluxo Principal] - Item 2.0
	 * 
	 * @author Thiago Toscano
	 * @date 21/01/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public NegativacaoComando consultarNegativacaoComandadoParaExecutar() throws ControladorException;

	/**
	 * Consulta todos os contratos em vigencia de um negativador
	 * 
	 * @author Thiago Toscano
	 * @date 26/12/2007
	 * @param negativador
	 * @return
	 * @throws ErroRepositorioException
	 */
	public NegativadorContrato consultarNegativadorContratoVigente(Integer negativador) throws ControladorException;

	public Object[] pesquisarParametroNegativacaoCriterio(Integer idNegativacaoCriterio) throws ControladorException;

	/**
	 * Pesquisar as rotas dos Im�veis que est�o resultado da simula��o
	 * 
	 * @author Ana Maria
	 * @date 05/06/2008
	 */
	public Collection pesquisarRotasImoveisComandoSimulacao(Integer idNegativacaoComando) throws ControladorException;

	/**
	 * Pesquisar as rotas dos Im�veis
	 * 
	 * @author Ana Maria, Francisco do Nascimento
	 * @date 05/06/2008, 14/01/09
	 */
	public Collection pesquisarRotasImoveis() throws ControladorException;

	/**
	 * UC0671 - Gerar movimento de inclus�o da negativa��o
	 * Pesquisar rotas por grupo de cobran�a para um crit�rio de negativa��o
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRotasPorCobrancaGrupoParaNegativacao(NegativacaoCriterio nCriterio) throws ControladorException;

	/**
	 * UC0671 - Gerar movimento de inclus�o da negativa��o
	 * Pesquisar rotas por gerencial regional para um crit�rio de negativa��o
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRotasPorGerenciaRegionalParaNegativacao(NegativacaoCriterio nCriterio) throws ControladorException;

	/**
	 * UC0671 - Gerar movimento de inclus�o da negativa��o
	 * Pesquisar rotas por Unidade de Negocio para um crit�rio de negativa��o
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRotasPorUnidadeNegocioParaNegativacao(NegativacaoCriterio nCriterio) throws ControladorException;

	/**
	 * UC0671 - Gerar movimento de inclus�o da negativa��o
	 * Pesquisar rotas por Localidade para um crit�rio de negativa��o
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRotasPorLocalidadeParaNegativacao(NegativacaoCriterio nCriterio) throws ControladorException;

	/**
	 * UC0671 - Gerar movimento de inclus�o da negativa��o
	 * Pesquisar rotas por Localidade para um crit�rio de negativa��o
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRotasPorLocalidadesParaNegativacao(NegativacaoCriterio nCriterio) throws ControladorException;

	/**
	 * Gerar Registro de negativacao tipo header
	 * [UC0671] Gerar Movimento de Inclusao de Nwegativa��o
	 * [SB0008] Gerar Registro de tipo header
	 * 
	 * @author Thiago Toscano
	 * @date 21/02/2008
	 * @param nc
	 * @param Object
	 *            [] obj
	 *            obj[0] Integer - quantidadeInclusao
	 *            obj[1] Integer - quantidadeItens
	 *            obj[2] BigDecimal - valorTotalDebito
	 * @return
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public Integer gerarRegistroDeInclusaoTipoHeader(
	// Short tipoComando,
					Integer quantidadeRegistro, Negativador n, NegativadorContrato nContrato,
					// NegativacaoComando nComando,
					NegativacaoCriterio nCriterio, Integer idNegativacaoMovimento) throws ControladorException;

	/**
	 * Apaga todos os ResumoNegativacao
	 * [UC0688] Gerar Resumo Di�rio da Negativa��o
	 * Fluxo principal Item 2.0
	 * 
	 * @author Yara Taciane
	 * @date 28/07/2008
	 */
	public void apagarResumoNegativacao(Integer numeroPenultimaExecResumoNegat) throws ControladorException;

	/**
	 * Apagar todos os registros da Tabela de Resumo de Negativa��o
	 * [UC0688] Gerar Resumo Di�rio da Negativa��o
	 * 
	 * @author Thiago Toscano
	 * @date 07/01/2008
	 */

	public void apagarResumoNegativacao() throws ControladorException;

	/**
	 * @param collImovel
	 * @param idMotivoExclusao
	 * @param idNegativador
	 * @param usuarioLogado
	 */
	public Integer excluirNegativacaoOnLineEmLote(Collection collImovel, Integer idNegativador, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Boolean verificaNegativacaoImovel(Integer idImovel) throws ControladorException;

	/**
	 * [FS0019] - Verificar exist�ncia de negativa��o para o cliente-im�vel
	 * Caso o cliente do im�vel esteja em processo de negativa��o (existe ocorr�ncia na tabela
	 * NEGATIVADOR_MOVIMENTO_REG com IMOV_ID=Id do im�vel informado e CLIE_ID=Id do cliente
	 * selecionado para remo��o e NMRG_ICACEITO com o valor 1 ou nulo e NMRG_CDEXCLUSAOTIPO com o
	 * valor nulo e NMRG_IDREGINCLUSAO com o valor nulo), exibir a mensagem �ATEN��O: O cliente
	 * <<CLIE_NMCLIENTE com CLIE_ID=Id do cliente selecionado para remo��o>>, vinculado ao im�vel
	 * <<IMOV_ID>>.
	 * 
	 * @author Isaac Silva
	 * @date 03/08/2011
	 * @param idImovel
	 * @param idCliente
	 * @return
	 * @throws ControladorException
	 */
	public boolean verificarExistenciaNegativacaClienteImovel(Integer idImovel, Integer idCliente) throws ControladorException;

	/**
	 * [UC0937] Obter Itens de Negativa��o Associados � Conta
	 * 
	 * @author Vivianne Sousa
	 * @author isilva
	 * @date 10/09/2009
	 * @date 04/08/2011
	 * @param idImovel
	 * @param referencia
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> obterItensNegativacaoAssociadosAConta(Integer idImovel, Integer referencia,
					Boolean... ignorarSituacaoDefinitiva) throws ControladorException;

	/**
	 * [UCXXXX] Atualizar o indicadorSituacaoDefinitiva dos Itens de Negativa��o Associados � Conta
	 * (Usado no desfazer Parcelamento e no Estornar pagamento)
	 * 
	 * @author Genival
	 * @date 03/11/2011
	 * @param idImovel
	 * @param referencia
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> atualizarItensNegativacaoAssociadosAContaIndicadorSitDefinit(Integer idImovel, Integer referencia)
					throws ControladorException;

	/**
	 * [UC1104] Efetuar Contra A��o
	 * [SB0004] � Atualiza Item do item do d�bito da negativa��o
	 * 
	 * @author Isaac Silva
	 * @date 08/08/2011
	 * @param dataPagamento
	 * @throws ControladorException
	 */
	public void atualizaItemDoItemDoDebitoDaNegativacao(ContaHistorico conta) throws ControladorException;

	/**
	 * [UC0147] Cancelar Conta <br>
	 * [SB0001 � Atualizar Item da Negativa��o] <br>
	 * <br>
	 * 1. Caso a negativa��o n�o esteja exclu�da (NMRG_CDEXCLUSAOTIPO da tabela
	 * NEGATIVADOR_MOVIMENTO_REG com o valor nulo para NMRG_ID=NMRG_ID da tabela
	 * NEGATIVADOR_MOVIMENTO_REG_ITEM com NMRI_ID=Id do item de negativa��o), atualiza o item da
	 * negativa��o (tabela NEGATIVADOR_MOVIMENTO_REG_ITEM) com os seguintes valores:
	 * 
	 * @author Isaac Silva
	 * @date 05/08/2011
	 * @param negativadorMovimentoRegItem
	 * @throws ControladorException
	 */
	public void atualizarItemNegativacaoCancelarConta(NegativadorMovimentoRegItem negativadorMovimentoRegItem) throws ControladorException;

	/**
	 * [UC0147] Cancelar Conta
	 * Fluxo Principal:
	 * 1.1.7. Verificar se h� rela��o do cancelamento com itens de negativa��o:
	 * 
	 * @author Isaac Silva
	 * @date 05/08/2011
	 * @param conta
	 * @throws ControladorException
	 */
	public void verificarHaRelacaoCancelamentoComItensNegativacaoCancelarConta(Conta conta) throws ControladorException;

	/**
	 * [UC0150] Retificar Conta <br>
	 * [SB0005] � Atualizar Item da Negativa��o <br>
	 * <br>
	 * 1. Caso o valor total da conta seja zero (Valor total de �gua + Valor total de esgoto + Valor
	 * total dos d�bitos - Valor total dos cr�ditos � Valor total dos impostos):
	 * 
	 * @author Isaac Silva
	 * @date 05/08/2011
	 * @param negativadorMovimentoRegItem
	 * @throws ControladorException
	 */
	public void atualizarItemNegativacaoRetificarConta(NegativadorMovimentoRegItem negativadorMovimentoRegItem) throws ControladorException;

	/**
	 * [UC0147] Retificar Conta
	 * Fluxo Principal:
	 * 1.1.7. Verificar se h� rela��o do cancelamento com itens de negativa��o:
	 * 
	 * @author Isaac Silva
	 * @date 05/08/2011
	 * @param conta
	 * @throws ControladorException
	 */
	public void verificarHaRelacaoCancelamentoComItensNegativacaoRetificarConta(Conta conta) throws ControladorException;

	/**
	 * [UC1016] Estornar Pagamentos <br>
	 * [SF0003] � Atualiza Pagamento
	 * 
	 * @author Isaac Silva
	 * @date 08/08/2011
	 * @param negativadorMovimentoRegItem
	 * @throws ControladorException
	 */
	public void atualizarItemNegativacaoEstornarPagamentos(NegativadorMovimentoRegItem negativadorMovimentoRegItem)
					throws ControladorException;

	/**
	 * [UC1016] Estornar Pagamentos
	 * [SF0003] � Atualiza Pagamento
	 * 
	 * @author Isaac Silva
	 * @date 05/08/2011
	 * @param conta
	 * @throws ControladorException
	 */
	public void verificarHaRelacaoCancelamentoComItensNegativacaoEstornarPagementos(Conta conta) throws ControladorException;

	/**
	 * @author Isaac Silva
	 * @date 09/08/2011
	 * @param listaIdentificadoresNMRI
	 * @return
	 * @throws ControladorException
	 */
	public Collection<NegativadorMovimentoRegItem> pesquisarNegativadorMovimentoRegItensPorIds(List<Integer> listaIdentificadoresNMRI)
					throws ControladorException;

	/**
	 * Valida se o im�vel est� negativado
	 * 
	 * @author Isaac Silva
	 * @date 11/08/2011
	 * @param idImovel
	 * @param idAcaoCobranca
	 * @return
	 * @throws ControladorException
	 */
	public boolean isImovelNegativado(Integer idImovel, Integer idAcaoCobranca) throws ControladorException;

	/**
	 * @autor Bruno Ferreira dos Santos
	 * @date 11/10/2011
	 * @param colecao
	 * @throws ControladorException
	 */
	public void determinarConfirmacaoNegativacao(Integer idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * [SB0001] � Validar Im�veis para Exclus�o da Negativa��o ([UC0675] Excluir Negativa��o OnLine)
	 * 
	 * @author Genival Barbosa
	 * @date 18/10/2011
	 * @param colecaoImoveis
	 */
	public Map<String, Collection<Integer>> validarImoveisExclusaoNegativacao(Collection<Integer> colecaoImoveis, Integer idNegativador)
					throws ControladorException;

	public void atualizarItemDaNegativacao(int referencia, Imovel imovel, Integer idCobrancaDebitoSituacao, Date dataSituacao,
					BigDecimal valor, Boolean ignorarSituacaoDefinitiva, Short indicadorSituacaoDefinitiva) throws ControladorException;

	public void atualizarItemDaNegativacaoDesfazerParcelamento(int referencia, Imovel imovel, Date dataSituacao,
					Boolean ignorarSituacaoDefinitiva, Short indicadorSituacaoDefinitiva) throws ControladorException;

}