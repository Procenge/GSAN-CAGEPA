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
 * Thiago Silva Toscano de Brito
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
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cobranca.*;
import gcom.cobranca.bean.ComandoNegativacaoHelper;
import gcom.cobranca.bean.ComandoNegativacaoTipoCriterioHelper;
import gcom.cobranca.bean.DadosConsultaNegativacaoHelper;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.spcserasa.bean.InserirComandoNegativacaoPorCriterioHelper;
import gcom.spcserasa.bean.NegativadorMovimentoHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Administrador
 */
public interface IRepositorioSpcSerasa {

	/**
	 * Seleciona o dados do Negativador
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007 *
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getDadosNegativador(int idNegativador) throws ErroRepositorioException;

	/**
	 * Seleciona o dados do Negativador
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getDadosContratoNegativador(int idNegativador) throws ErroRepositorioException;

	/**
	 * Insere comando Negativa��o
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public Integer inserirComandoNegativacao(int idNegativador, int idUsuarioResponsavel, String identificacaoCI)
					throws ErroRepositorioException;

	/**
	 * Insere comando Negativa��o
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getComandoCriterioNegativacao(int idNegativador, int idUsuarioResponsavel, String identificacaoCI)
					throws ErroRepositorioException;

	/**
	 * Obtem dados Imovel
	 * 
	 * @author Marcio Roberto
	 * @date 01/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getDadosImoveis(int idImovel) throws ErroRepositorioException;

	/**
	 * Obtem DadosCliente
	 * 
	 * @author Marcio Roberto
	 * @date 01/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getDadosCliente(int idCliente) throws ErroRepositorioException;

	/**
	 * Insere RegistroNegativacao
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public Integer geraRegistroNegativacao(int idNegativador, int idUsuarioResponsavel, int saenvio, int idNegativadorComando)
					throws ErroRepositorioException;

	/**
	 * Insere RegistroNegativacao
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public Integer geraRegistroNegativacaoReg(int idNegativador, int idUsuarioResponsavel, int saenvio, int idNegativadorComando,
					int idNegativacaoMovimento, StringBuilder registroHeader, int quantidadeRegistros) throws ErroRepositorioException;

	/**
	 * Obtem Negativador Movimento id
	 * 
	 * @author Marcio Roberto
	 * @date 07/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer getNegativadorMovimento() throws ErroRepositorioException;

	/**
	 * @author Marcio Roberto
	 * @date 07/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer getTpoRegistro(int idNegativador) throws ErroRepositorioException;

	/**
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer getSaEnvioContratoNegativador(int idNegativador) throws ErroRepositorioException;

	/**
	 * Obtem getDadosEnderecoCliente
	 * 
	 * @author Marcio Roberto
	 * @date 09/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getDadosEnderecoCliente(int idCliente) throws ErroRepositorioException;

	/**
	 * Obtem getDadosEnderecoClienteAlternativo
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getBairroCep(int idCliEnder) throws ErroRepositorioException;

	/**
	 * Obtem getBairro
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getCep(int idLogradouroBairro) throws ErroRepositorioException;

	/**
	 * Obtem getMunicipio
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getMunicipio(int idCliEnder) throws ErroRepositorioException;

	/**
	 * Obtem getMunicipioCep
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String getMunicipioCep(int idCliEnder) throws ErroRepositorioException;

	/**
	 * Obtem getUnidadeFederativa
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getUnidadeFederativa(int idLogradouroBairro) throws ErroRepositorioException;

	/**
	 * Obtem getDddFone
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getDddFone(int idCliente) throws ErroRepositorioException;

	/**
	 * Obtem geraRegistroNegativacaoRegDetalhe
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public void geraRegistroNegativacaoRegDetalhe(int idNegativador, int idUsuarioResponsavel, int saenvio, int idNegativadorComando,
					int idNegativacaoMovimento, StringBuilder registro, int quantidadeRegistros) throws ErroRepositorioException;

	/**
	 * Obtem geraRegistroNegativacaoRegDetalheSPC
	 * 
	 * @author Marcio Roberto
	 * @date 12/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public void geraRegistroNegativacaoRegDetalheSPC(int idNegativador, int idUsuarioResponsavel, int saenvio, int idNegativadorComando,
					int idNegativacaoMovimento, StringBuilder registro, int quantidadeRegistros, BigDecimal valorTotalDebitos,
					int idDebitoSituacao, int idImovel, int idLocalidade, int idQuadra, int stComercialCD, int numeroQuadra, int iper_id,
					int idCliente, int idCategoria, String cpfCliente, String cnpjCliente) throws ErroRepositorioException;

	/**
	 * obtemDebitoSituacao
	 * 
	 * @author Marcio Roberto
	 * @date 12/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obtemDebitoSituacao() throws ErroRepositorioException;

	/**
	 * geraRegistroNegativacaoMovimentoRegItem
	 * 
	 * @author Marcio Roberto
	 * @date 13/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public void geraRegistroNegativacaoMovimentoRegItem(int numeroRegistro, int idDebSit, BigDecimal valorDoc)
					throws ErroRepositorioException;

	/**
	 * geraRegistroImovelNegativacao
	 * 
	 * @author Marcio Roberto
	 * @date 13/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public void geraRegistroImovelNegativacao(int idNegativadorComando, int idImovel) throws ErroRepositorioException;

	/**
	 * getNextNegativadorMovimentoReg
	 * 
	 * @author Marcio Roberto
	 * @date 14/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer getNextNegativadorMovimentoReg() throws ErroRepositorioException;

	/**
	 * Obtem Negativacao Comando
	 * 
	 * @author Marcio Roberto
	 * @date 26/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getNegativacaoComando(int idNegativacaoComando) throws ErroRepositorioException;

	/**
	 * Obtem Negativacao Criterio
	 * 
	 * @author Marcio Roberto
	 * @date 26/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getNegativacaoCriterio(int idNegativacaoComando) throws ErroRepositorioException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 29/10/2007
	 * @param comandoNegativacaoHelper
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarComandoNegativacao(ComandoNegativacaoHelper comandoNegativacaoHelper) throws ErroRepositorioException;

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
					throws ErroRepositorioException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 29/10/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarDadosInclusoesComandoNegativacao(Integer idComandoNegativacao) throws ErroRepositorioException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 29/10/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarDadosInclusoesComandoNegativacaoParaPaginacao(Integer idComandoNegativacao, Integer numeroPagina)
					throws ErroRepositorioException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 09/11/2007
	 * @param idComandoNegativacao
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */

	public Object[] pesquisarParametrosComandoNegativacao(Integer idComandoNegativacao) throws ErroRepositorioException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 22/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarTitularidadeCpfCnpjNegativacao(Integer idComandoNegativacao) throws ErroRepositorioException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 23/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGrupoCobranca(Integer idComandoNegativacao) throws ErroRepositorioException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 23/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGerenciaRegional(Integer idComandoNegativacao) throws ErroRepositorioException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 23/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarUnidadeNegocio(Integer idComandoNegativacao) throws ErroRepositorioException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 23/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEloPolo(Integer idComandoNegativacao) throws ErroRepositorioException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 26/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarSubcategoria(Integer idComandoNegativacao) throws ErroRepositorioException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 26/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarPerfilImovel(Integer idComandoNegativacao) throws ErroRepositorioException;

	/**
	 * [UC 0653] Pesquisar Comando Negativa��o
	 * 
	 * @author K�ssia Albuquerque
	 * @date 26/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarTipoCliente(Integer idComandoNegativacao) throws ErroRepositorioException;

	/**
	 * Obtem Negativacao Criterio
	 * 
	 * @author Marcio Roberto
	 * @date 26/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection getImoveisClienteCriterio(int idCliente) throws ErroRepositorioException;

	/**
	 * Verifica se h� negativa��o para aquele imovel
	 * 
	 * @author Marcio Roberto
	 * @date 29/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificaExistenciaNegativacao(int idImovel, int idNegativador) throws ErroRepositorioException;

	/**
	 * obtem titularidade dos documentos
	 * 
	 * @author Marcio Roberto
	 * @date 29/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List obtemTitularidadesDocumentos(int idNegativadorCriterio) throws ErroRepositorioException;

	/**
	 * obtem dados cliente da negativacao
	 * 
	 * @author Marcio Roberto
	 * @date 29/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List obtemDadosClienteNegativacao(int idImovel, int idClienteRelacaoTipo) throws ErroRepositorioException;

	/**
	 * Verifica se h� ocorrencia do imovel na tabela cobranca situacao.
	 * 
	 * @author Marcio Roberto
	 * @date 03/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificaExistenciaImovelCobrancaSituacao(int idImovel) throws ErroRepositorioException;

	/**
	 * Verifica se as subCategorias do imovel corresponde as subCategorias do criterio da
	 * negativacao.
	 * 
	 * @author Marcio Roberto
	 * @date 03/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificaSubCategoriaImovelNegativacaoCriterio(int idImovel, int idCriterio) throws ErroRepositorioException;

	/**
	 * Verifica se os Perfis do imovel corresponde aos Perfis do criterio da negativacao.
	 * 
	 * @author Marcio Roberto
	 * @date 03/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificaPerfilImovelNegativacaoCriterio(int idCriterio, int imovelPerfil) throws ErroRepositorioException;

	/**
	 * Verifica se o cliente usuario do imovel corresponde ao cliente tipo da negativacao criterio
	 * 
	 * @author Marcio Roberto
	 * @date 03/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificaTipoClienteNegativacaoCriterio(int idImovel, int idCriterio) throws ErroRepositorioException;

	/**
	 * Verifica ocorrencia debito cobrado conta imovel.
	 * 
	 * @author Marcio Roberto
	 * @date 05/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificaDebitoCobradoConta(int idConta) throws ErroRepositorioException;

	/**
	 * Verifica ocorrencia debito cobrado conta imovel.
	 * 
	 * @author Marcio Roberto
	 * @date 05/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List verificaImovelParcelamento(int idImovel) throws ErroRepositorioException;

	/**
	 * [UC0651] Inserir Comando Negativa��o [FS0015] Verificar exist�ncia de negativa��o para o
	 * im�vel no negativador
	 * 
	 * @author Ana Maria
	 * @date 04/12/2007
	 * @param idNegativador
	 * @param idImovel
	 * @return Boolean
	 * @throws ErroRepositorioException
	 */
	public Boolean verificarExistenciaNegativacaoImovel(Integer idNegativador, Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0651] Inserir Comando Negativa��o [SB0003] Determinar Data Prevista para Execu��o do
	 * Comando
	 * 
	 * @author Ana Maria
	 * @date 11/12/2007
	 * @param idNegativador
	 * @return Date
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarUltimaDataRealizacaoComando(Integer idNegativador) throws ErroRepositorioException;

	/**
	 * [UC0651] Inserir Comando Negativa��o //[FS0014]- Verificar exist�ncia de comando para os
	 * mesmos par�metros
	 * 
	 * @author Ana Maria
	 * @date 13/12/2007
	 * @param InserirComandoNegativacaoPorCriterioHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public String verificarExistenciaComandoMesmoParametro(InserirComandoNegativacaoPorCriterioHelper helper)
					throws ErroRepositorioException;

	/**
	 * Verifica ocorrencia debito cobrado conta imovel.
	 * 
	 * @author Marcio Roberto
	 * @date 05/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificaCartaAvisoParcelamento(int idImovel, int numeroDiasAtrasoRecebCartaParcel) throws ErroRepositorioException;

	/**
	 * Referente a [SB0004] UC0671 - Gerar Movimento de Inclus�o de Negativa��o
	 * Condi��es 1,2,3,4,5 e 6 referente a diferentes crit�rios
	 * 
	 * @author Marcio Roberto
	 * @date 10/12/2007
	 * @param
	 * @return List
	 * @throws ErroRepositorioException
	 */
	public List getImovelCondicao(int idNegativacaoCriterio, int tipoCondicao) throws ErroRepositorioException;

	/**
	 * M�todo consuta os Negativadores que tenham movimento de Exclus�o do spc ou serasa
	 * [UC0673] - Gerar Movimento da Exclus�o de Negativa��o [SB0003] - Selecionar Negativadores
	 * 
	 * @author Thiago Toscano
	 * @date 21/12/2007
	 */
	public Collection consultarNegativadoresParaExclusaoMovimento() throws ErroRepositorioException;

	/**
	 * M�todo consuta os NegativadoresMovimentoReg que tenham movimento de Exclus�o do spc ou serasa
	 * [UC0673] - Gerar Movimento da Exclus�o de Negativa��o [SB0003] - Selecionar Negativadores
	 * 
	 * @author Thiago Toscano
	 * @date 21/12/2007
	 */
	public Collection consultarNegativacoesParaExclusaoMovimento(Integer[] ids) throws ErroRepositorioException;

	/**
	 * Consulta todos os contratos em vigencia de um negativador
	 * 
	 * @author Thiago Toscano
	 * @date 26/12/2007
	 * @param negativador
	 * @return
	 * @throws ErroRepositorioException
	 */
	public NegativadorContrato consultarNegativadorContratoVigente(Integer negativador) throws ErroRepositorioException;

	/**
	 * M�todo que consulta os NegativadorMovimentoReg que representam o arquivo dos movimentos de
	 * exclusao de negativacao, para a geracao do arquvo
	 * txt
	 * [UC0673] - Gerar Movimento da Exclus�o de Negativa��o [SB0009] - Gerar Arquivo TxT para Envio
	 * ao Negativador
	 * 
	 * @author Thiago Toscano
	 * @date 27/12/2007
	 * @param idMovimento
	 * @return o arquivo
	 * @throws ErroRepositorioException
	 */
	public Collection consultarNegativadorMovimentoRegistroParaGerarArquivo(Integer codigoNegativadorMovimento, String tipoRegistro)
					throws ErroRepositorioException;

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

	public Collection pesquisarComandoNegativacaoHelper(ComandoNegativacaoHelper comandoNegativacaoHelper) throws ErroRepositorioException;

	/**
	 * Consulta os Negativadores para a geracao do resumo diario da negativacao [UC0688] Gerar
	 * Resumo Di�rio da Negativa��o Fluxo principal Item 1.0
	 * 
	 * @author Thiago Toscano
	 * @date 07/01/2008
	 */
	public List consultarNegativacaoParaGerarResumoDiarioNegativacao() throws ErroRepositorioException;

	/**
	 * Apaga todos os ResumoNegativacao [UC0688] Gerar Resumo Di�rio da Negativa��o Fluxo principal
	 * Item 2.0
	 * 
	 * @author Thiago Toscano
	 * @date 07/01/2008
	 */
	public void apagarResumoNegativacao() throws ErroRepositorioException;

	/**
	 * Consulta os itens do registro do NegativadorMovimentoReg passado [UC0688] Gerar Resumo Di�rio
	 * da Negativa��o [SB0001] Processar Itens da
	 * Negativa��o
	 * 
	 * @author Thiago Toscano
	 * @date 07/01/2008
	 */
	public List consultarNegativadorMovimentoRegItem(Integer codigoNegativadorMovimentoReg) throws ErroRepositorioException;

	/**
	 * @author Saulo Lima
	 * @date 30/09/2010
	 * @param idNegativadorMovimentoReg
	 * @throws ErroRepositorioException
	 */
	public void atualizarNegativadorMovimentoRegItem(Integer idNegativadorMovimentoReg) throws ErroRepositorioException;

	/**
	 * Consulta a NegativacaoComando de um negativadormovimento
	 * [UC0688] Gerar Resumo Di�rio da Negativa��o [SB0005] Determinar Negativa��o do Imovel
	 * 
	 * @author Thiago Toscano
	 * @date 07/01/2008
	 */
	public NegativacaoImovei consultarNegativacaoImoveiDoNegativadorMovimento(Integer codigoNegativadorMovimento)
					throws ErroRepositorioException;

	/**
	 * M�todo que atualiza o imovel cobranca situacao tipo [UC0688] Gerar Resumo Diario da
	 * Negativacao [SB0005] Determinar Negativa��o do Imovle Item
	 * 2.1.4
	 * 
	 * @author Thiago Toscano
	 * @date 08/01/2008
	 * @param codigoImovel
	 * @param codigoCobrancaSituacao
	 */
	// public List consultarImovelCobrancaSituacao(Integer codigoImovel, Integer
	// codigoCobrancaSituacao);
	public List consultarImovelCobrancaSituacao(Integer codigoImovel, Integer codigoCobrancaSituacao) throws ErroRepositorioException;

	//
	// /**
	// * Consultar a conta historico de uma contageral
	// *
	// * [UC0688] Gerar Resumo Di�rio da Negativa��o
	// * [SB0002] Determinar Situa��o do D�bito do Item da Negativa��o
	// * Item 1.1.2.1
	// *
	// * @author Thiago Toscano
	// * @date 09/01/2008
	// *
	// * @param codigoConta
	// * @return
	// */
	// public ContaHistorico consultaContaHistorico(Integer codigoConta) throws
	// ErroRepositorioException ;
	//
	//
	// /**
	// * Consultar a conta de uma contageral
	// *
	// * [UC0688] Gerar Resumo Di�rio da Negativa��o
	// * [SB0002] Determinar Situa��o do D�bito do Item da Negativa��o
	// * Item 1.1.3.1
	// *
	// * @author Thiago Toscano
	// * @date 09/01/2008
	// *
	// * @param codigoConta
	// * @return
	// */
	// public Conta consultaConta(Integer codigoConta) throws ErroRepositorioException ;

	/**
	 * [UC0688] Gerar Resumo Di�rio da Negativa��o [SB0002] Determinar Situa��o do D�bito do Item da
	 * Negativa��o Item 1.1.2.2.3
	 * 
	 * @author Thiago Toscano
	 * @date 09/01/2008
	 * @param codigoConta
	 * @return
	 */
	public ContaHistorico consultaContaHistoricoMaisAtual(Integer codigoConta, int anoMesReferenciaConta) throws ErroRepositorioException;

	/**
	 * Consulta uma conta caso a conta mais atual ainda n�o esteja no historico
	 * [UC0688] Gerar Resumo Di�rio da Negativa��o [SB0002] Determinar Situa��o do D�bito do Item da
	 * Negativa��o Item 1.1.2.2.3
	 * 
	 * @author Thiago Toscano
	 * @date 09/01/2008
	 * @param codigoConta
	 * @return
	 */
	public Conta consultaContaMaisAtual(Integer codigoConta, int anoMesReferenciaConta) throws ErroRepositorioException;

	/**
	 * M�todo que consulta os pagamentos passando o codigo da conta ou do guia de pagamento [UC0688]
	 * Gerar Resumo Di�rio da Negativa��o [SB0002]
	 * Determinar Situa��o do D�bito do Item da Negativa��o Item 4.1.1 ou Item 4.2.1
	 * 
	 * @author Thiago Toscano
	 * @date 10/01/2008
	 * @param codigoConta
	 * @param codigoGuiaPagamento
	 * @return
	 */
	public Collection consultarPagamentoDoItem(Integer codigoConta, Integer codigoGuiaPagamento) throws ErroRepositorioException;

	/**
	 * M�todo que consulta os pagamentos historcio passando o codigo da conta historico ou do guia
	 * de pagamento [UC0688] Gerar Resumo Di�rio da
	 * Negativa��o [SB0002] Determinar Situa��o do D�bito do Item da Negativa��o Item 4.1.2 ou Item
	 * 4.2.2
	 * 
	 * @author Thiago Toscano
	 * @date 10/01/2008
	 * @param codigoContaHistociro
	 * @param codigoGuiaPagamento
	 * @return
	 */
	public Collection consultarPagamentoHistorcioDoItem(Integer codigoContaHistociro, Integer codigoGuiaPagamento)
					throws ErroRepositorioException;

	/**
	 * [UC0688] Gerar Resumo Di�rio da Negativa��o Fluxo principal Item 2.0
	 * 
	 * @author Vivianne Sousa
	 * @date 30/09/2009
	 */
	public void apagarResumoNegativacao(Integer numeroExecucaoResumoNegativacao, Integer idRota) throws ErroRepositorioException;

	/**
	 * Consulta os Negativadores para a geracao do resumo diario da negativacao [UC0688] Gerar
	 * Resumo Di�rio da Negativa��o Fluxo principal Item 1.0
	 * 
	 * @author Thiago Toscano
	 * @date 07/01/2008
	 */
	public List consultarNegativacaoParaGerarResumoDiarioNegativacao(Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0651] Manter Comando de Nagativa��o Crit�rio
	 * Remove Titularidades do CPF/CNPJ da Negativa��o, Subcategorias, Perfis de im�vel, Tipos de
	 * cliente, Grupos de Cobran�a, Ger�ncias Regionais,
	 * Unidades Neg�cio, Elos P�lo do crit�rio
	 * 
	 * @author Ana Maria
	 * @date 21/01/2008
	 * @param idNegativacaoCriterio
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public void removerParametrosCriterio(Integer idNegativacaoCriterio) throws ErroRepositorioException;

	/**
	 * [UC0652] Atualizar Comando Negativa��o //[FS0012]- Verificar exist�ncia de comando para os
	 * mesmos par�metros
	 * 
	 * @author Ana Maria
	 * @date 24/01/2008
	 * @param InserirComandoNegativacaoPorCriterioHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public String verificarExistenciaComandoMesmoParametroAtualizacao(InserirComandoNegativacaoPorCriterioHelper helper)
					throws ErroRepositorioException;

	/**
	 * [UC0672] Registrar Moviemento Retorno dos Negativadores
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */

	public Collection consultarImovelCobrancaSituacaoPorNegativador(Imovel imovel, Integer codigoNegativador)
					throws ErroRepositorioException;

	/**
	 * UC0671 - Gerar movimento de inclus�o da negativa��o
	 * Pesquisar imoveis do comando de simulacao por rota
	 * 
	 * @author Unknown, Francisco do Nascimento
	 * @date Unknown, 23/01/2009
	 * @param nComando
	 *            Comando de negativacao
	 * @param idRota
	 *            Identificador da rota
	 * @return Colecao de matriculas de imoveis
	 * @throws ErroRepositorioException
	 */
	public List consultarImoveisNegativacaoSimulada(NegativacaoComando nComando, Integer idRota) throws ErroRepositorioException;

	/**
	 * M�todo consutla um negativacaoComando [UC0671] Gerar Movimento de Inclusao de Negativacao
	 * [SB003] Gear moviemnto de inclusao de negativacao
	 * para os imoveis do clietne item 1.0
	 * 
	 * @author Thiago Toscano
	 * @date 21/02/2008
	 * @throws ErroRepositorioException
	 */
	public List consultarImoveisCliente(NegativacaoCriterio nCriterio, Integer idLocalidade) throws ErroRepositorioException;

	public List pesquisarImoveisParaNegativacao(Integer idLocalidade, Integer integer) throws ErroRepositorioException;

	/**
	 * [UC0651] Inserir Comando Negativa��o [FS0015] Verificar exist�ncia de negativa��o para o
	 * im�vel no negativador
	 * 
	 * @author Ana Maria
	 * @date 04/12/2007
	 * @param idImovel
	 * @return Boolean
	 * @throws ErroRepositorioException
	 */
	public Boolean verificarExistenciaNegativacaoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Insere RegistroNegativacao
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public Integer geraRegistroNegativacaoReg(int idNegativador, int saenvio, // int
					// idNegativadorComando,
					int idNegativacaoMovimento, StringBuilder registroHeader, int quantidadeRegistros, Integer idNegCriterio)
					throws ErroRepositorioException;

	/**
	 * Consulta os Negativadores para a geracao do resumo diario da negativacao [UC0688] Gerar
	 * Resumo Di�rio da Negativa��o Fluxo principal Item 1.0
	 * 
	 * @author Vivianne Sousa
	 * @date 30/10/2009
	 */
	public List consultarNegativadorMovimentoReg(Integer idRota) throws ErroRepositorioException;

	/**
	 * Informa��es Atualizadas em (maior data e hora da �ltima execu��o
	 * 
	 * @author Yara Taciane
	 * @date 28/07/2008
	 */
	public Date getDataUltimaAtualizacaoResumoNegativacao(Integer numeroExecucaoResumoNegativacao) throws ErroRepositorioException;

	public Object[] pesquisarComandoNegativacaoSimulado(Integer idComandoNegativacao) throws ErroRepositorioException;

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
					Integer numeroPagina) throws ErroRepositorioException;

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
					throws ErroRepositorioException;

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
					throws ErroRepositorioException;

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
					throws ErroRepositorioException;

	/**
	 * [UC0688] - Gerar Resumo Diario da Negativa��o
	 * [UC0694] - Gerar Relat�rio Negativa��es Exclu�das
	 * 
	 * @author Vivianne Sousa
	 * @data 22/04/2009
	 * @param idNegativadorMovimentoReg
	 * @return NegativadorMovimentoRegParcelamento
	 */
	public Collection pesquisarNegativadorMovimentoRegParcelamento(Integer idNegativadorMovimentoReg) throws ErroRepositorioException;

	/**
	 * Pesquisa a Data da Exclus�o da Negativa��o
	 * 
	 * @author Yara Taciane
	 * @date 9/05/2008
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarDataExclusaoNegativacao(int idImovel, int idNegativacaoComando) throws ErroRepositorioException;

	/**
	 * Pesquisar a negativa��o do im�vel . [UC0675] Excluir Negativa��o Online.
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public Collection pesquisarImovelNegativado(Imovel imovel, Negativador negativador) throws ErroRepositorioException;

	/**
	 * M�todo usado para apresentar os registros de negativadorMovimento
	 * Registro aceitos usado no caso de uso [UC0681]
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 * @param negativadorMovimentoHelper
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegatiacaoParaImovel(Imovel imovel, Negativador negativador) throws ErroRepositorioException;

	/**
	 * [UC0651] Manter Comando de Nagativa��o Crit�rio
	 * 
	 * @author Ana Maria
	 * @date 21/01/2008
	 * @param idComandoNegativacao
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public NegativacaoCriterio pesquisarNegativacaoCriterio(Integer idComandoNegativacao) throws ErroRepositorioException;

	/**
	 * M�todo usado para pesquisa de negativador movimento usado no caso de uso [UC0682]
	 * 
	 * @author Yara Taciane
	 * @date 21/01/2008
	 * @param negativadorMovimentoHelper
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegativadorMovimento(NegativadorMovimentoHelper negativadorMovimentoHelper, Integer numeroPagina)
					throws ErroRepositorioException;

	/**
	 * M�todo usado para pesquisa de negativador movimento usado no caso de uso [UC0682]
	 * 
	 * @author Yara Taciane
	 * @date 21/01/2008
	 * @param negativadorMovimentoHelper
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegativadorMovimento(NegativadorMovimentoHelper negativadorMovimentoHelper) throws ErroRepositorioException;

	/**
	 * Retorna o NegativadorMovimentoReg [UC0673] Excluir Negativa��o Online
	 * 
	 * @author Yara Taciane
	 * @date 27/03/2008
	 */
	public NegativadorMovimentoReg pesquisarNegativadorMovimentoRegInclusao(Imovel imovel, Negativador negativador)
					throws ErroRepositorioException;

	/**
	 * M�todo usado para apresentar os registros de negativadorMovimento
	 * Registro aceitos usado no caso de uso [UC0681]
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 * @param negativadorMovimentoHelper
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegativadorMovimentoRegistroAceito(NegativadorMovimentoHelper negativadorMovimentoHelper)
					throws ErroRepositorioException;

	/**
	 * M�todo usado para apresentar os registros de negativadorMovimento
	 * Registro aceitos usado no caso de uso [UC0681]
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 * @param negativadorMovimentoHelper
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegativadorMovimentoRegistroAceito(NegativadorMovimentoHelper negativadorMovimentoHelper,
					Integer numeroPagina) throws ErroRepositorioException;

	/**
	 * [UC0694] - Gerar Relat�rio de Negativa��es Exclu�das
	 * 
	 * @author Yara T. Souza
	 * @date 16/01/2009
	 */
	public List pesquisarNegativadorMovimentoRegItens(Integer idNegativadorMovimentoReg) throws ErroRepositorioException;

	/**
	 * Pesquisa a Negativador Resultado Simulacao
	 * 
	 * @author Yara Taciane
	 * @date 9/05/2008
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarNegativadorResultadoSimulacao(Integer idNegativacaoComando) throws ErroRepositorioException;

	/**
	 * Conta a quantidade de Clientes Negativados [UC0693] Gerar Relat�rio
	 * Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */

	public Collection pesquisarRelatorioAcompanhamentoClientesNegativador(DadosConsultaNegativacaoHelper helper)
					throws ErroRepositorioException;

	/**
	 * Conta a quantidade de Clientes Negativados [UC0693] Gerar Relat�rio
	 * Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */

	public Integer pesquisarRelatorioAcompanhamentoClientesNegativadorCount(DadosConsultaNegativacaoHelper helper)
					throws ErroRepositorioException;

	/**
	 * [UC0693] Gerar Relat�rio Negativacoes Excluidas
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public Collection pesquisarRelatorioNegativacoesExcluidas(DadosConsultaNegativacaoHelper helper) throws ErroRepositorioException;

	/**
	 * Conta a quantidade de Neg [UC0693] Gerar Relat�rio Negativacoes Excluidas
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public Integer pesquisarRelatorioNegativacoesExcluidasCount(DadosConsultaNegativacaoHelper helper) throws ErroRepositorioException;

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
					throws ErroRepositorioException;

	/**
	 * Retorna o somat�rio do valor do D�bito do NegativadoMovimentoReg pela CobrancaDebitoSituacao
	 * [UC0693] Gerar Relat�rio Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public BigDecimal pesquisarSomatorioValorDebito(NegativadorMovimentoReg negativadorMovimentoReg,
					CobrancaDebitoSituacao cobrancaDebitoSituacao) throws ErroRepositorioException;

	/**
	 * Retorna o somat�rio do VALOR PAGO e do VALOR CANCELADO
	 * [UC0693] Gerar Relat�rio Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Vivianne Sousa
	 * @date 29/04/2009
	 */
	public Object[] pesquisarSomatorioValorPagoEValorCancelado(Integer idNegativadorMovimentoReg) throws ErroRepositorioException;

	/**
	 * [UC0651] Inserir Comando Negativa��o [SB0003] Determinar Data Prevista
	 * para Execu��o do Comando
	 * 
	 * @author Ana Maria
	 * @date 11/12/2007
	 * @param idNegativador
	 * @return Date
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarUltimaDataRealizacaoComando(Integer idNegativador, Integer icSimulacao) throws ErroRepositorioException;

	/**
	 * M�todo usado para contar a quantidade de ocorr�ncias de
	 * negativadorMovimento Registro aceitos usado no caso de uso [UC0681]
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 * @param negativadorMovimentoHelper
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Integer verificarTotalRegistrosAceitos(Integer idNegativadorMovimento) throws ErroRepositorioException;

	/**
	 * [UC0651] Inserir Comando Negativa��o [FS0026] Verificar exist�ncia de
	 * comando para o negativador na data
	 * 
	 * @author Ana Maria
	 * @date 07/05/2008
	 * @param idNegativador
	 * @param Data
	 * @return boolean
	 */
	public boolean verificarExistenciaComandoNegativador(String idNegativador, Date dataPrevista) throws ErroRepositorioException;

	public Integer pesquisarNegativadorMovimentoRegRetMot(Integer idNegativadorMovimentoReg, Integer idNegativadorRetornoMotivo)
					throws ErroRepositorioException;

	/**
	 * [UC0672] Registrar Moviemento Retorno dos Negativadores
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public ImovelCobrancaSituacao getImovelCobrancaSituacao(Imovel imovel, CobrancaSituacao cobrancaSituacao)
					throws ErroRepositorioException;

	/**
	 * [UC0672] Registrar Moviemento Retorno dos Negativadores
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */

	public NegativadorMovimentoReg getNegativadorMovimentoReg(NegativadorMovimento negativadorMovimento, Integer numRegistro)
					throws ErroRepositorioException;

	/**
	 * [UC0672] Registrar Moviemento Retorno dos Negativadores
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */

	public NegativadorMovimento getNegativadorMovimento(Negativador negativador, Integer numeroSequencialEnvio, Boolean idRetorno)
					throws ErroRepositorioException;

	/**
	 * [UC0651] Manter Comando de Nagativa��o Crit�rio
	 * Remove Negativa��o Comando
	 * 
	 * @author Ana Maria
	 * @date 22/01/2008
	 * @param idNegativacaoCriterio
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public void removerNegativacaoComando(Integer idNegativacaoComando) throws ErroRepositorioException;

	/**
	 * [UC0671 - [SB0012] - Gerar Arquivo TXT para envio ao negativador] -
	 * geraRegistroNegativacaoRegTrailler
	 * 
	 * @author Marcio Roberto
	 * @date 30/01/2008
	 * @param int
	 *        idNegativador, int idNegativacaoMovimento, StringBuilder
	 *        registro, int quantidadeRegistros, int idNegCriterio
	 * @return void
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public void geraRegistroNegativacaoRegTrailler(int idNegativador, int idNegativacaoMovimento, StringBuilder registro,
					int quantidadeRegistros, Integer idNegCriterio) throws ErroRepositorioException;

	/**
	 * Insere RegistroNegativacao
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public Integer geraRegistroNegativacao(int idNegativador, int saenvio, int idNegativadorComando) throws ErroRepositorioException;

	/**
	 * geraRegistroNegativacaoMovimentoRegItem
	 * 
	 * @author Marcio Roberto
	 * @date 13/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public void geraRegistroNegativacaoMovimentoRegItem(int idDebSit, BigDecimal valorDoc, int idDetalheRegSPC, int idDocTipo,
					Integer idGuiaPagamento, Integer idConta) throws ErroRepositorioException;

	/**
	 * Pesquisa a cole��o de clientes do im�vel para negativa��o sem o cliente
	 * empresa do sistema par�metro
	 * 
	 * @author Ana Maria
	 * @date 17/12/2008
	 * @param idImovel
	 * @return Collection
	 * @exception ErroRepositorioException
	 */
	public Collection pesquisarClienteImovelParaNegativacao(Integer idImovel, String cnpjEmpresa) throws ErroRepositorioException;

	/**
	 * Verificar exist�ncia crit�rios do comando
	 * 
	 * @author Ana Maria
	 * @date 09/06/2008
	 * @param Integer
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] verificarExistenciaCriterio(Integer idCriterio) throws ErroRepositorioException;

	/**
	 * Verifica se a situa��o da liga��o de �gua do imovel corresponde as
	 * situa��o da liga��o de �gua do criterio da negativacao.
	 * 
	 * @author Ana Maria
	 * @date 12/06/2008
	 * @param int
	 * @param int
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificaLigacaoAguaImovelNegativacaoCriterio(int idCriterio, int idLigacaoAguaSituacao) throws ErroRepositorioException;

	/**
	 * Verifica se a situa��o da liga��o de esgoto do imovel corresponde as
	 * situa��o da liga��o de esgoto do criterio da negativacao.
	 * 
	 * @author Ana Maria
	 * @date 12/06/2008
	 * @param int
	 * @param int
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificaLigacaoEsgotoImovelNegativacaoCriterio(int idCriterio, int idLigacaoEsgotoSituacao)
					throws ErroRepositorioException;

	public Object[] pesquisarDadosImovelParaNegativacao(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Consultar o Motivo da Exclusao do Negativador
	 * 
	 * @author Yara Taciane
	 * @date 22/07/2008
	 * @param Integer
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public NegativadorExclusaoMotivo pesquisarMotivoExclusao(Integer idMotivoExclusao) throws ErroRepositorioException;

	/**
	 * Consulta as rotas para a geracao do resumo diario da negativacao
	 * [UC0688] Gerar Resumo Di�rio da Negativa��o
	 * Fluxo principal Item 1.0
	 * 
	 * @author Francisco do Nascimento
	 * @date 03/02/2009
	 */
	public List consultarRotasParaGerarResumoDiarioNegativacao() throws ErroRepositorioException;

	/**
	 * M�todo que retorna todas NegativacaoComando que ainda nao tenha sido
	 * executada (dataHoraRealizacao == null) [UC0687] Executar Comando de
	 * Negativa��o [Fluxo Principal] - Item 2.0
	 * 
	 * @author Thiago Toscano
	 * @date 21/01/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public NegativacaoComando consultarNegativacaoComandadoParaExecutar() throws ErroRepositorioException;

	public Object[] pesquisarParametroNegativacaoCriterio(Integer idNegativacaoCriterio) throws ErroRepositorioException;

	/**
	 * Pesquisar as localidades dos Im�veis que est�o resultado da simula��o
	 * 
	 * @author Ana Maria
	 * @date 05/06/2008
	 */
	public Collection pesquisarRotasImoveisComandoSimulacao(Integer idNegativacaoComando) throws ErroRepositorioException;

	/**
	 * Pesquisar as rotas dos Im�veis
	 * 
	 * @author Ana Maria, Francisco do Nascimento
	 * @date 05/06/2008, 14/01/09
	 */
	public Collection pesquisarRotasImoveis() throws ErroRepositorioException;

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
	public List pesquisarRotasPorCobrancaGrupoParaNegativacao(NegativacaoCriterio nCriterio) throws ErroRepositorioException;

	/**
	 * UC0671 - Gerar movimento de inclus�o da negativa��o
	 * Pesquisar rotas por gerencia regional para um crit�rio de negativa��o
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRotasPorGerenciaRegionalParaNegativacao(NegativacaoCriterio nCriterio) throws ErroRepositorioException;

	/**
	 * UC0671 - Gerar movimento de inclus�o da negativa��o
	 * Pesquisar rotas por unidade de neg�cio para um crit�rio de negativa��o
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */

	public List pesquisarRotasPorUnidadeNegocioParaNegativacao(NegativacaoCriterio nCriterio) throws ErroRepositorioException;

	/**
	 * UC0671 - Gerar movimento de inclus�o da negativa��o
	 * Pesquisar rotas por localidade para um crit�rio de negativa��o
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */

	public List pesquisarRotasPorLocalidadeParaNegativacao(NegativacaoCriterio nCriterio) throws ErroRepositorioException;

	/**
	 * UC0671 - Gerar movimento de inclus�o da negativa��o
	 * Pesquisar rotas por localidade inicial e final para um crit�rio de
	 * negativa��o
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */

	public List pesquisarRotasPorLocalidadesParaNegativacao(NegativacaoCriterio nCriterio) throws ErroRepositorioException;

	/**
	 * Apaga todos os ResumoNegativacao [UC0688] Gerar Resumo Di�rio da
	 * Negativa��o Fluxo principal Item 2.0
	 * 
	 * @author Yara Taciane
	 * @date 28/07/2008
	 */
	public void apagarResumoNegativacao(Integer numeroPenultimaExecResumoNegat) throws ErroRepositorioException;

	/**
	 * @param idNegativadorMovimentoReg
	 * @param idDocumentoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */

	public List consultarNegativadorMovimentoRegItem(Integer idNegativadorMovimentoReg, Integer idDocumentoTipo)
					throws ErroRepositorioException;

	/**
	 * @param idNegativadorMovimentoReg
	 * @param idDocumentoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarNegativadorMovimentoRegItem(Integer idNegativadorMovimentoReg, Integer idDocumentoTipo)
					throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param idCobrancaAcaoAtividadeCronograma
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarNegativacaoImovel(Integer idImovel, Integer idCobrancaAcaoAtividadeCronograma,
					Integer idCobrancaAcaoAtividadeComando) throws ErroRepositorioException;

	/**
	 * @param imovel
	 * @param negativador
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarNegativadorMovimentoRegIncluidos(Imovel imovel, Negativador negativador) throws ErroRepositorioException;

	/**
	 * @param idConta
	 * @param idDocumentoTipo
	 * @param idNegativador
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarNegativadorMovimentoRegPorConta(Integer idConta, Integer idDocumentoTipo, Integer idNegativador)
					throws ErroRepositorioException;

	/**
	 * @param idCobrancaDebitoSituacao
	 * @param idNegativador
	 * @return
	 * @throws ErroRepositorioException
	 */
	public NegativadorExclusaoMotivo pesquisarNegativadorMotivoExclusao(Integer idCobrancaDebitoSituacao, Integer idNegativador)
					throws ErroRepositorioException;

	/**
	 * @param codigoRetorno
	 * @return
	 * @throws ErroRepositorioException
	 */
	public NegativadorRetornoMotivo pesquisarNegativadorRetornoMotivo(Integer codigoRetorno, Integer idNegativador)
					throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Boolean verificaNegativacaoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * @param numeroRegistro
	 * @param idNegMovimento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public NegativadorMovimentoReg pesquisarRegistroTipoConsumidor(Integer numeroRegistro, Integer idNegMovimento)
					throws ErroRepositorioException;

	/**
	 * Pesquisar quantidade de negativa��es ativas
	 * 
	 * @author Hebert Falc�o
	 * @date 21/06/2011
	 */
	public Integer pesquisarQuantidadeNegativacoesAtivas(Integer idImovel) throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaNegativacaClienteImovel(Integer idImovel, Integer idCliente) throws ErroRepositorioException;

	/**
	 * [UC0937] Obter Itens de Negativa��o Associados � Conta
	 * pesquisa a partir da tabela NEGATIVADOR_MOVIMENTO_REG_ITEM
	 * com NMRI_ICSITDEFINITIVA=2 E CNTA_ID com o valor diferente de nulo
	 * E ( (CNTA_ID=CNTA_ID da tabela CONTA com IMOV_ID=Id do Im�vel recebido
	 * e CNTA_AMREFERENCIACONTA=Refer�ncia recebida)
	 * E NMRG_ID=NMRG_ID da tabela NEGATIVADOR_MOVIMENTO_REG com IMOV_ID=Id do Im�vel recebido
	 * 
	 * @author Vivianne Sousa
	 * @author isilva
	 * @data 10/09/2009
	 * @data 04/08/2011
	 * @param idImovel
	 * @param referencia
	 * @return id do NegativadorMovimentoRegItem
	 */
	public Collection obterNegativadorMovimentoRegItemAssociadosAConta(Integer idImovel, Integer referencia,
					Boolean ignorarSituacaoDefinitiva) throws ErroRepositorioException;

	/**
	 * [UC0937] Obter Itens de Negativa��o Associados � Conta
	 * pesquisa a partir da tabela NEGATIVADOR_MOVIMENTO_REG_ITEM
	 * com NMRI_ICSITDEFINITIVA=2 E CNTA_ID com o valor diferente de nulo
	 * E ((CNTA_ID=CNTA_ID da tabela CONTA_HISTORICO com IMOV_ID=Id do Im�vel recebido
	 * e CNHI_AMREFERENCIACONTA=Refer�ncia recebida) )
	 * E NMRG_ID=NMRG_ID da tabela NEGATIVADOR_MOVIMENTO_REG com IMOV_ID=Id do Im�vel recebido
	 * 
	 * @author Vivianne Sousa
	 * @author isilva
	 * @data 10/09/2009
	 * @data 04/08/2011
	 * @param idImovel
	 * @param referencia
	 * @return id do NegativadorMovimentoRegItem
	 */
	public Collection obterNegativadorMovimentoRegItemAssociadosAContaHistorico(Integer idImovel, Integer referencia,
					Boolean ignorarSituacaoDefinitiva) throws ErroRepositorioException;

	/**
	 * [UCXXXX]
	 * 
	 * @author Genival Barbosa
	 * @data 03/11/2011
	 * @param idImovel
	 * @param referencia
	 * @return id do NegativadorMovimentoRegItem
	 */
	public Collection obterNegativadorMovimentoRegItemAssociadosAContaSitDefinit(Integer idImovel, Integer referencia)
					throws ErroRepositorioException;

	/**
	 * [UCXXXX]
	 * 
	 * @author Genival Barbosa
	 * @data 03/11/2011
	 * @param idImovel
	 * @param referencia
	 * @return id do NegativadorMovimentoRegItem
	 */
	public Collection obterNegativadorMovimentoRegItemAssociadosAContaHistoricoSitDefinit(Integer idImovel, Integer referencia)
					throws ErroRepositorioException;

	/**
	 * Pesquisar NegativadorMovimentoRegItem filtrando por uma cole��o de Ids
	 * 
	 * @author Isaac Silva
	 * @date 09/08/2011
	 * @param listaIdentificadoresNMRI
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<NegativadorMovimentoRegItem> pesquisarNegativadorMovimentoRegItensPorIds(Collection<Integer> listaIdentificadoresNMRI)
					throws ErroRepositorioException;

	/**
	 * Valida se o im�vel est� negativado
	 * 
	 * @author Isaac Silva
	 * @date 11/08/2011
	 * @param idImovel
	 * @param idCobrancaAcao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean isImovelNegativado(Integer idImovel, Integer idCobrancaAcao) throws ErroRepositorioException;

	/**
	 * @autor Bruno Ferreira dos Santos
	 * @date 11/10/2011
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarNegativacoesDeterminarConfirmacao() throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param idNegativador
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarNegativacaoASerExcluidaImovel(Integer idImovel, Integer idNegativador) throws ErroRepositorioException;

	/**
	 * @param idNegativadorMovimentoReg
	 * @param idCobrancaDebitoSituacao
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegativadorMovimentoRegItemPorNegMovimentoRegCobrancaSituacao(Integer idNegativadorMovimentoReg,
					Integer idCobrancaDebitoSituacao) throws ErroRepositorioException;

	/**
	 * Consulta usada no fluxoprincipal step 1 do caso de uso [UC0688]-Gerar Resumo Diario de
	 * Negativacao
	 * 
	 * @autor Genival Barbosa
	 * @date 26/10/2011
	 * @return cole��o de negativadorMovimentoReg
	 * @throws ControladorException
	 */
	public Collection pesquisarNegativadorMovimentoRegParaGerarResumoDiarioNegativacao() throws ErroRepositorioException;

}