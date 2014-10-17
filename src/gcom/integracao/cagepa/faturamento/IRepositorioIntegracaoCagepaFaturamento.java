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

package gcom.integracao.cagepa.faturamento;

import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Interface para o repositório de integração com as tabela do sistema Pirâmide no GSAN
 * 
 * @author Luciano Galvao
 * @created 20/11/2012
 */
public interface IRepositorioIntegracaoCagepaFaturamento {

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna o id do banco, de acordo com o Item 20 do Caso de Uso
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public Integer obterBancoId(Integer imovelId) throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna o telefone padrão do cliente usuário a partir do imovelId passado
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public String obterTelefonePadrao(Integer imovelId) throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna uma lista com o tipo de consumo dos últimos meses de consumo, a partir de um dado
	 * imóvel e a quantidade de meses a ser considerada
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public List<Integer> obterTipoConsumoUltimosMeses(Integer imovelId, Integer qtdeMeses) throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna uma lista com as anormalidades do tipo de consumo dos últimos meses de consumo, a
	 * partir de um dado
	 * imóvel e a quantidade de meses a ser considerada
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public List<Integer> obterAnormalidadeConsumoUltimosMeses(Integer imovelId, Integer qtdeMeses) throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna a soma das alíquotas dos impostos do ano/mês de referência mais recente
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public BigDecimal obterSomaAliquotasImpostos() throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna <true> se o imóvel tiver situação de faturamento de acordo com o
	 * faturamentoSituacaoTipoId. Retorna <false> caso contrário.
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public boolean possuiSituacaoFaturamentoImovel(Integer imovelId, Integer faturamentoSituacaoTipoId) throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna a data prevista (FTAC_DTPREVISTA) do Cronograma de Atividade do Faturamento
	 * (FATURAMENTO_GRUPO_CRON_MENSAL) a partir da referência, do id do grupo de faturamento e do id
	 * da atividade de faturamento
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public Date obterDataPrevistaFaturamentoAtivCronograma(Integer anoMesReferencia, Integer faturamentoGrupoId,
					Integer faturamentoAtividadeId) throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna o tipo de anormalidade consumo (CSAN_ID) do Histórico de Consumo do Imóvel
	 * (CONSUMO_HISTORICO)
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public Integer obterTipoAnormalidadeConsumo(Integer imovelId) throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna as informações valor de leitura real mais recente, junto com o seu ano/mês de
	 * faturamento
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public Object[] obterUltimaLeituraConsumoReal(Integer imovelId, Integer consumoTipoId) throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna o ano/Mês de Faturamento do consumo mais recente do imóvel
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public Integer obterAnoMesFaturamentoConsumoMaisRecente(Integer imovelId) throws ErroRepositorioException;

}
