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

package gcom.gerencial.arrecadacao;

import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class RepositorioGerencialArrecadacaoHBM
				implements IRepositorioGerencialArrecadacao {

	private static IRepositorioGerencialArrecadacao instancia;

	/**
	 * Construtor da classe RepositorioArrecadacaoHBM
	 */
	private RepositorioGerencialArrecadacaoHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioGerencialArrecadacao getInstancia(){

		if(instancia == null){
			instancia = new RepositorioGerencialArrecadacaoHBM();
		}

		return instancia;
	}

	/**
	 * [UC0553 - Gerar Resumo da Arrecadacao - Aguá/Esgoto]
	 * 
	 * @author Ivan Sérgio
	 * @date 16/05/2007, 12/11/2007
	 * @alteracao: Adicionar Ano/Mes Referencia Pagamento
	 * @param idLocalidade
	 *            anoMesReferenciaPagamento
	 * @return list
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoArrecadacaoAguaEsgoto(int idSetorComercial, int anoMesReferenciaPagamento) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			// [FS0001] - Método que Verifica a Existencia de dados para o ano/mes de referencia da
			// Arrecadacao
			// verificarDeletarDadosAnoMesReferenciaArrecadacao(anoMesReferenciaPagamento);

			String hql = "select "
							+ "	imovel.id, "
							+ "	localidade.gerenciaRegional.id, "
							+ "	localidade.unidadeNegocio.id, "
							+ "	localidade.localidade.id, "
							+ "	localidade.id, "
							+ "	setorComercial.id, "
							+ "	imovel.rota.id, "
							+ "	quadra.id, "
							+ "	imovel.imovelPerfil.id, "
							+ "	imovel.ligacaoAguaSituacao.id, "
							+ "	imovel.ligacaoEsgotoSituacao.id, "
							+

							"	CASE WHEN (ligacaoAgua.ligacaoAguaPerfil.id is not null) THEN "
							+ "		ligacaoAgua.ligacaoAguaPerfil.id "
							+ "	ELSE "
							+ "		0 "
							+ "	END, "
							+

							"	CASE WHEN (ligacaoEsgoto.ligacaoEsgotoPerfil.id is not null) THEN "
							+ "		ligacaoEsgoto.ligacaoEsgotoPerfil.id "
							+ "	ELSE "
							+ "		0 "
							+ "	END, "
							+ "	pagamento.documentoTipo.id, "
							+
							// Situacao do pagamento
							"	CASE WHEN (pagamento.pagamentoSituacaoAtual.id is not null) THEN"
							+ "		pagamento.pagamentoSituacaoAtual.id "
							+ "	ELSE "
							+ "		0 "
							+ "	END, "
							+
							// Indicador de Contas Recebidas
							"	CASE WHEN ( (year(pagamento.dataPagamento) || month(pagamento.dataPagamento)) < "
							+ "			     anoMesReferenciaPagamento) THEN "
							+ "		2 "
							+ "	ELSE "
							+ "		1 "
							+ "	END, "
							+
							// Época de Pagamento
							"	CASE WHEN ( (pagamento.conta.id is null) and "
							+ "				(pagamento.guiaPagamentoGeral.guiaPagamento.id is null) and "
							+ "				(pagamento.debitoACobrar.id is null) ) THEN "
							+ "		9 "
							+ "	ELSE "
							+
							// CASO: Conta
							"		CASE WHEN (pagamento.conta.id is not null) THEN "
							+ "			CASE WHEN (pagamento.dataPagamento <= conta.dataVencimentoConta) THEN "
							+ "				0 "
							+ "			ELSE "
							+ "				CASE WHEN ( (pagamento.dataPagamento > conta.dataVencimentoConta) AND "
							+ "							( ( year(pagamento.dataPagamento) || month(pagamento.dataPagamento) ) = "
							+ "							  ( year(pagamento.conta.dataVencimentoConta) || month(pagamento.conta.dataVencimentoConta) ) ) ) THEN "
							+ "					1 "
							+ "				ELSE "
							+ "					CASE WHEN ( (pagamento.dataPagamento > conta.dataVencimentoConta) ) THEN "
							+ "						99 "
							+ "					END "
							+ "				END "
							+ "			END "
							+
							// CASO: Guia de Pagamento
							"		ELSE "
							+ "			CASE WHEN (pagamento.guiaPagamentoGeral.guiaPagamento.id is not null) THEN "
							+ "				CASE WHEN (pagamento.dataPagamento <= guiaPagamento.dataVencimento) THEN "
							+ "					0 "
							+ "				ELSE "
							+ "					CASE WHEN ( (pagamento.dataPagamento > guiaPagamento.dataVencimento) AND "
							+ "								( ( year(pagamento.dataPagamento) || month(pagamento.dataPagamento) ) = "
							+ "							      ( year(pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento) || month(pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento) ) ) ) THEN "
							+ "						1 "
							+ "					ELSE "
							+ "						CASE WHEN ( (pagamento.dataPagamento > guiaPagamento.dataVencimento) ) THEN "
							+ "							98 "
							+ "						END "
							+ "					END "
							+ "				END "
							+
							// CASO: Débito à Cobrar
							"			ELSE "
							+ "				CASE WHEN (pagamento.debitoACobrar.id is not null) THEN "
							+ "					0 "
							+ "				END "
							+ "			END "
							+ "		END "
							+ "	END, "
							+

							"	setorComercial.codigo, "
							+ "	quadra.numeroQuadra, "
							+ "	conta.valorAgua, "
							+ "	conta.valorEsgoto, "
							+
							// Valor não Identificado
							"	CASE WHEN (pagamento.conta.id is null AND "
							+ "			   guiaPagamento.id is null AND "
							+ "			   debitoACobrar.id is null) THEN "
							+ "		pagamento.valorPagamento "
							+ "	END as valorPagamento, "
							+

							// Alterado para corrigir o erro quando null
							"	CASE WHEN (pagamento.arrecadacaoForma.id is not null) THEN "
							+ "		pagamento.arrecadacaoForma.id "
							+ // 22
							"	ELSE "
							+ "		0 "
							+ "	END, "
							+

							"	avisoBancario.arrecadador.id, "
							+ // 23
							"	conta.valorImposto, "
							+ // 24
							"	pagamento.anoMesReferenciaPagamento, "
							+ // 25
							"	pagamento.dataPagamento, "
							+ // 26
							"	pagamento.conta.dataVencimentoConta, "
							+ // 27
							"	pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento "
							+ // 28

							"from " + "	gcom.arrecadacao.pagamento.Pagamento pagamento " + "	inner join pagamento.imovel imovel "
							+ "	left join imovel.ligacaoAgua ligacaoAgua " + "	left join imovel.ligacaoEsgoto ligacaoEsgoto "
							+ "	inner join imovel.localidade localidade " + "	inner join imovel.quadra quadra "
							+ "	inner join quadra.setorComercial setorComercial " + "	left join pagamento.conta conta "
							+ "	left join pagamento.guiaPagamentoGeral.guiaPagamento guiaPagamento "
							+ "	left join pagamento.debitoACobrar debitoACobrar " + "	left join pagamento.avisoBancario avisoBancario " +

							"where " + "	pagamento.anoMesReferenciaArrecadacao = :anoMesReferenciaPagamento AND "
							+ "	setorComercial.id = :idSetorComercial";

			retorno = session.createQuery(hql).setInteger("anoMesReferenciaPagamento", anoMesReferenciaPagamento).setInteger(
							"idSetorComercial", idSetorComercial).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}// getImoveisResumoArrecadacaoAguaEsgoto

	/**
	 * [UC0553 - Gerar Resumo da Arrecadacao - OUTROS]
	 * 
	 * @author Ivan Sérgio
	 * @date 09/01/2008
	 * @param idSetorComercial
	 *            anoMesReferenciaArrecadacao
	 * @return list
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoArrecadacaoOutros(int idSetorComercial, int anoMesReferenciaPagamento) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = "select "
							+ "	imovel.id, "
							+ // 0
							"	localidade.gerenciaRegional.id, "
							+ // 1
							"	localidade.unidadeNegocio.id, "
							+ // 2
							"	localidade.localidade.id, "
							+ // 3
							"	localidade.id, "
							+ // 4
							"	setorComercial.id, "
							+ // 5
							"	imovel.rota.id, "
							+ // 6
							"	quadra.id, "
							+ // 7
							"	imovel.imovelPerfil.id, "
							+ // 8
							"	imovel.ligacaoAguaSituacao.id, "
							+ // 9
							"	imovel.ligacaoEsgotoSituacao.id, "
							+ // 10

							"	CASE WHEN (ligacaoAgua.ligacaoAguaPerfil.id is not null) THEN "
							+ "		ligacaoAgua.ligacaoAguaPerfil.id "
							+ "	ELSE "
							+ "		0 "
							+ "	END, "
							+ // 11

							"	CASE WHEN (ligacaoEsgoto.ligacaoEsgotoPerfil.id is not null) THEN "
							+ "		ligacaoEsgoto.ligacaoEsgotoPerfil.id "
							+ "	ELSE "
							+ "		0 "
							+ "	END, "
							+ // 12

							"	pagamento.documentoTipo.id, "
							+ // 13
							// Situacao do pagamento
							"	CASE WHEN (pagamento.pagamentoSituacaoAtual.id is not null) THEN"
							+ "		pagamento.pagamentoSituacaoAtual.id "
							+ "	ELSE "
							+ "		0 "
							+ "	END, "
							+ // 14
							// Indicador de Contas Recebidas
							"	CASE WHEN ( (year(pagamento.dataPagamento) || month(pagamento.dataPagamento)) < "
							+ "			     anoMesReferenciaPagamento) THEN "
							+ "		2 "
							+ "	ELSE "
							+ "		1 "
							+ "	END, "
							+ // 15
							// Época de Pagamento
							"	CASE WHEN ( (pagamento.conta.id is null) and "
							+ "				(pagamento.guiaPagamentoGeral.guiaPagamento.id is null) and "
							+ "				(pagamento.debitoACobrar.id is null) ) THEN "
							+ "		9 "
							+ "	ELSE "
							+
							// CASO: Conta
							"		CASE WHEN (pagamento.conta.id is not null) THEN "
							+ "			CASE WHEN (pagamento.dataPagamento <= conta.dataVencimentoConta) THEN "
							+ "				0 "
							+ "			ELSE "
							+ "				CASE WHEN ( (pagamento.dataPagamento > conta.dataVencimentoConta) AND "
							+ "							( ( year(pagamento.dataPagamento) || month(pagamento.dataPagamento) ) = "
							+ "							  ( year(pagamento.conta.dataVencimentoConta) || month(pagamento.conta.dataVencimentoConta) ) ) ) THEN "
							+ "					1 "
							+ "				ELSE "
							+ "					CASE WHEN ( (pagamento.dataPagamento > conta.dataVencimentoConta) ) THEN "
							+ "						99 "
							+ "					END "
							+ "				END "
							+ "			END "
							+
							// CASO: Guia de Pagamento
							"		ELSE "
							+ "			CASE WHEN (pagamento.guiaPagamentoGeral.guiaPagamento.id is not null) THEN "
							+ "				CASE WHEN (pagamento.dataPagamento <= guiaPagamento.dataVencimento) THEN "
							+ "					0 "
							+ "				ELSE "
							+ "					CASE WHEN ( (pagamento.dataPagamento > guiaPagamento.dataVencimento) AND "
							+ "								( ( year(pagamento.dataPagamento) || month(pagamento.dataPagamento) ) = "
							+ "							      ( year(pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento) || month(pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento) ) ) ) THEN "
							+ "						1 "
							+ "					ELSE "
							+ "						CASE WHEN ( (pagamento.dataPagamento > guiaPagamento.dataVencimento) ) THEN "
							+ "							98 "
							+ "						END "
							+ "					END "
							+ "				END "
							+
							// CASO: Débito à Cobrar
							"			ELSE "
							+ "				CASE WHEN (pagamento.debitoACobrar.id is not null) THEN "
							+ "					0 "
							+ "				END "
							+ "			END "
							+ "		END "
							+ "	END, "
							+ // 16

							"	setorComercial.codigo, "
							+ // 17
							"	quadra.numeroQuadra, "
							+ // 18

							// Alterado para corrigir o erro quando null
							"	CASE WHEN (pagamento.arrecadacaoForma.id is not null) THEN "
							+ "		pagamento.arrecadacaoForma.id "
							+ "	ELSE "
							+ "		0 "
							+ "	END, "
							+ // 19

							"	avisoBancario.arrecadador.id, "
							+ // 20
							// "	conta.valorImposto, " +
							"	pagamento.anoMesReferenciaPagamento, "
							+ // 21
							"	pagamento.dataPagamento, "
							+ // 22
							"	pagamento.conta.dataVencimentoConta, "
							+ // 23
							"	pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento, "
							+ // 24

							/********************************************************************
							 * Dados de Arrecadacao OUTROS
							 *******************************************************************/
							// Tipo Financiamento
							"	case when (pagamento.conta.id is not null) then "
							+ "		case when (pagamento.conta.debitos is not null) then "
							+ "			debitoCobrado1.financiamentoTipo.id "
							+ "		end "
							+ "	else "
							+ "		case when (pagamento.guiaPagamentoGeral.guiaPagamento.id is not null) then "
							+ "			pagamento.guiaPagamentoGeral.guiaPagamento.financiamentoTipo.id "
							+ "		else "
							+ "			case when (pagamento.debitoACobrar.id is not null) then "
							+ "				pagamento.debitoACobrar.financiamentoTipo.id "
							+ "			end "
							+ "		end "
							+ "	end, "
							+ // 25

							// Lancamento Item Contabel
							"	case when (pagamento.conta.id is not null) then "
							+ "		debitoCobrado1.lancamentoItemContabil.id "
							+ "	else "
							+ "		case when (pagamento.guiaPagamentoGeral.guiaPagamento.id is not null) then "
							+ "			pagamento.guiaPagamentoGeral.guiaPagamento.lancamentoItemContabil.id "
							+ "		else "
							+ "			case when (pagamento.debitoACobrar.id is not null) then "
							+ "				pagamento.debitoACobrar.lancamentoItemContabil.id "
							+ "			end "
							+ "		end "
							+ "	end, "
							+ // 26

							// Valor Debito
							"	case when (pagamento.conta.id is not null) then "
							+ "		debitoCobrado1.valorPrestacao "
							+ "	else "
							+ "		case when (pagamento.guiaPagamentoGeral.guiaPagamento.id is not null) then "
							+ "			pagamento.guiaPagamentoGeral.guiaPagamento.valorDebito "
							+ "		else "
							+ "			case when (pagamento.debitoACobrar.id is not null) then "
							+ "				pagamento.debitoACobrar.valorDebito "
							+ "			end "
							+ "		end "
							+ "	end "
							+ // 27

							"from " + "	gcom.arrecadacao.pagamento.Pagamento pagamento " + "	inner join pagamento.imovel imovel "
							+ "	left join imovel.ligacaoAgua ligacaoAgua " + "	left join imovel.ligacaoEsgoto ligacaoEsgoto "
							+ "	inner join imovel.localidade localidade " + "	inner join imovel.quadra quadra "
							+ "	inner join quadra.setorComercial setorComercial " + "	left join pagamento.conta conta "
							+ "   left join pagamento.guiaPagamentoGeral guiaGeral " + "	left join guiaGeral.guiaPagamento guiaPagamento "
							+ "	left join pagamento.debitoACobrar debitoACobrar " + "	left join conta.debitoCobrados debitoCobrado1 "
							+ "	left join pagamento.avisoBancario avisoBancario " +

							"where " + "	pagamento.anoMesReferenciaArrecadacao = :anoMesReferenciaPagamento AND "
							+ "	setorComercial.id = :idSetorComercial " + "order by " + "	conta.id";

			retorno = session.createQuery(hql).setInteger("anoMesReferenciaPagamento", anoMesReferenciaPagamento).setInteger(
							"idSetorComercial", idSetorComercial).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}// getImoveisResumoArrecadacaoOutros

	/**
	 * @author Ivan Sérgio
	 * @date 10/01/2008
	 * @param idSetorComercial
	 * @param anoMesReferenciaPagamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoArrecadacaoCreditos(int idSetorComercial, int anoMesReferenciaPagamento) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = "select "
							+ "	imovel.id, "
							+ // 0
							"	localidade.gerenciaRegional.id, "
							+ // 1
							"	localidade.unidadeNegocio.id, "
							+ // 2
							"	localidade.localidade.id, "
							+ // 3
							"	localidade.id, "
							+ // 4
							"	setorComercial.id, "
							+ // 5
							"	imovel.rota.id, "
							+ // 6
							"	quadra.id, "
							+ // 7
							"	imovel.imovelPerfil.id, "
							+ // 8
							"	imovel.ligacaoAguaSituacao.id, "
							+ // 9
							"	imovel.ligacaoEsgotoSituacao.id, "
							+ // 10

							"	CASE WHEN (ligacaoAgua.ligacaoAguaPerfil.id is not null) THEN "
							+ "		ligacaoAgua.ligacaoAguaPerfil.id "
							+ "	ELSE "
							+ "		0 "
							+ "	END, "
							+ // 11

							"	CASE WHEN (ligacaoEsgoto.ligacaoEsgotoPerfil.id is not null) THEN "
							+ "		ligacaoEsgoto.ligacaoEsgotoPerfil.id "
							+ "	ELSE "
							+ "		0 "
							+ "	END, "
							+ // 12

							"	pagamento.documentoTipo.id, "
							+ // 13
							// Situacao do pagamento
							"	CASE WHEN (pagamento.pagamentoSituacaoAtual.id is not null) THEN"
							+ "		pagamento.pagamentoSituacaoAtual.id "
							+ "	ELSE "
							+ "		0 "
							+ "	END, "
							+ // 14
							// Indicador de Contas Recebidas
							"	CASE WHEN ( (year(pagamento.dataPagamento) || month(pagamento.dataPagamento)) < "
							+ "			     anoMesReferenciaPagamento) THEN "
							+ "		2 "
							+ "	ELSE "
							+ "		1 "
							+ "	END, "
							+ // 15
							// Época de Pagamento
							"	CASE WHEN ( (pagamento.conta.id is null) and "
							+ "				(pagamento.guiaPagamentoGeral.guiaPagamento.id is null) and "
							+ "				(pagamento.debitoACobrar.id is null) ) THEN "
							+ "		9 "
							+ "	ELSE "
							+
							// CASO: Conta
							"		CASE WHEN (pagamento.conta.id is not null) THEN "
							+ "			CASE WHEN (pagamento.dataPagamento <= conta.dataVencimentoConta) THEN "
							+ "				0 "
							+ "			ELSE "
							+ "				CASE WHEN ( (pagamento.dataPagamento > conta.dataVencimentoConta) AND "
							+ "							( ( year(pagamento.dataPagamento) || month(pagamento.dataPagamento) ) = "
							+ "							  ( year(pagamento.conta.dataVencimentoConta) || month(pagamento.conta.dataVencimentoConta) ) ) ) THEN "
							+ "					1 "
							+ "				ELSE "
							+ "					CASE WHEN ( (pagamento.dataPagamento > conta.dataVencimentoConta) ) THEN "
							+ "						99 "
							+ "					END "
							+ "				END "
							+ "			END "
							+
							// CASO: Guia de Pagamento
							"		ELSE "
							+ "			CASE WHEN (pagamento.guiaPagamentoGeral.guiaPagamento.id is not null) THEN "
							+ "				CASE WHEN (pagamento.dataPagamento <= guiaPagamento.dataVencimento) THEN "
							+ "					0 "
							+ "				ELSE "
							+ "					CASE WHEN ( (pagamento.dataPagamento > guiaPagamento.dataVencimento) AND "
							+ "								( ( year(pagamento.dataPagamento) || month(pagamento.dataPagamento) ) = "
							+ "							      ( year(pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento) || month(pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento) ) ) ) THEN "
							+ "						1 "
							+ "					ELSE "
							+ "						CASE WHEN ( (pagamento.dataPagamento > guiaPagamento.dataVencimento) ) THEN "
							+ "							98 "
							+ "						END "
							+ "					END "
							+ "				END "
							+
							// CASO: Débito à Cobrar
							"			ELSE "
							+ "				CASE WHEN (pagamento.debitoACobrar.id is not null) THEN "
							+ "					0 "
							+ "				END "
							+ "			END "
							+ "		END "
							+ "	END, "
							+ // 16

							"	setorComercial.codigo, "
							+ // 17
							"	quadra.numeroQuadra, "
							+ // 18

							// Alterado para corrigir o erro quando null
							"	CASE WHEN (pagamento.arrecadacaoForma.id is not null) THEN "
							+ "		pagamento.arrecadacaoForma.id "
							+ "	ELSE "
							+ "		0 "
							+ "	END, "
							+ // 19

							"	avisoBancario.arrecadador.id, "
							+ // 20
							"	pagamento.anoMesReferenciaPagamento, "
							+ // 21
							"	pagamento.dataPagamento, "
							+ // 22
							"	pagamento.conta.dataVencimentoConta, "
							+ // 23
							"	pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento, "
							+ // 24

							/********************************************************************
							 * Dados de Arrecadacao CREDITOS
							 *******************************************************************/
							"	creditoRealizados.creditoOrigem.id, "
							+ // 25
							"	creditoRealizados.lancamentoItemContabil.id, "
							+ // 26

							"	case when (creditoRealizados.conta.id is not null) then "
							+ "		creditoRealizados.valorCredito "
							+ "	else "
							+ "		0 "
							+ "	end "
							+ // 27

							"from " + "	gcom.arrecadacao.pagamento.Pagamento pagamento " + "	inner join pagamento.imovel imovel "
							+ "	left join imovel.ligacaoAgua ligacaoAgua " + "	left join imovel.ligacaoEsgoto ligacaoEsgoto "
							+ "	inner join imovel.localidade localidade " + "	inner join imovel.quadra quadra "
							+ "	inner join quadra.setorComercial setorComercial " + "	left join pagamento.conta conta "
							+ "   left join pagamento.guiaPagamentoGeral guiaGeral " + "	left join guiaGeral.guiaPagamento guiaPagamento "
							+ "	left join pagamento.debitoACobrar debitoACobrar " + "	left join pagamento.avisoBancario avisoBancario "
							+ "	left join conta.creditoRealizados creditoRealizados " +

							"where " + "	pagamento.anoMesReferenciaArrecadacao = :anoMesReferenciaPagamento AND "
							+ "	setorComercial.id = :idSetorComercial AND " + " 	creditoRealizados.creditoOrigem.id is not null "
							+ "order by " + "	conta.id";

			retorno = session.createQuery(hql).setInteger("anoMesReferenciaPagamento", anoMesReferenciaPagamento).setInteger(
							"idSetorComercial", idSetorComercial).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}// getImoveisResumoArrecadacaoCreditos

	/**
	 * [UC0553 - Gerar Resumo da Arrecadacao - Outros]
	 * 
	 * @author Ivan Sérgio
	 * @date 22/05/2007
	 * @param anoMesReferenciaPagamento
	 *            resumoArrecadacaoAguaEsgoto
	 * @return list
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public List getResumoArrecadacaoOutros(int anoMesReferenciaPagamento, UnResumoArrecadacao resumoArrecadacaoAguaEsgoto)
					throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = "select "
							+ "	imovel.id, "
							+ "	case when (pagamento.conta.id is not null) then "
							+ "		case when (pagamento.conta.debitos is not null) then "
							+ "			debitoCobrado1.financiamentoTipo.id "
							+ "		end "
							+ "	else "
							+ "		case when (pagamento.guiaPagamentoGeral.guiaPagamento.id is not null) then "
							+ "			pagamento.guiaPagamentoGeral.guiaPagamento.financiamentoTipo.id "
							+ "		else "
							+ "			case when (pagamento.debitoACobrar.id is not null) then "
							+ "				pagamento.debitoACobrar.financiamentoTipo.id "
							+ "			end "
							+ "		end "
							+ "	end, "
							+

							"	case when (pagamento.conta.id is not null) then "
							+ "		debitoCobrado1.lancamentoItemContabil.id "
							+ "	else "
							+ "		case when (pagamento.guiaPagamentoGeral.guiaPagamento.id is not null) then "
							+ "			pagamento.guiaPagamentoGeral.guiaPagamento.lancamentoItemContabil.id "
							+ "		else "
							+ "			case when (pagamento.debitoACobrar.id is not null) then "
							+ "				pagamento.debitoACobrar.lancamentoItemContabil.id "
							+ "			end "
							+ "		end "
							+ "	end, "
							+

							"	case when (pagamento.conta.id is not null) then "
							+ "		debitoCobrado1.valorPrestacao "
							+ "	else "
							+ "		case when (pagamento.guiaPagamentoGeral.guiaPagamento.id is not null) then "
							+ "			pagamento.guiaPagamentoGeral.guiaPagamento.valorDebito "
							+ "		else "
							+ "			case when (pagamento.debitoACobrar.id is not null) then "
							+ "				pagamento.debitoACobrar.valorDebito "
							+ "			end "
							+ "		end "
							+ "	end, "
							+

							// Época de Pagamento
							"	CASE WHEN ( (pagamento.conta.id is null) and "
							+ "				(pagamento.guiaPagamentoGeral.guiaPagamento.id is null) and "
							+ "				(pagamento.debitoACobrar.id is null) ) THEN "
							+ "		9 "
							+ "	ELSE "
							+
							// CASO: Conta
							"		CASE WHEN (pagamento.conta.id is not null) THEN "
							+ "			CASE WHEN (pagamento.dataPagamento <= conta.dataVencimentoConta) THEN "
							+ "				0 "
							+ "			ELSE "
							+ "				CASE WHEN ( (pagamento.dataPagamento > conta.dataVencimentoConta) AND "
							+ "							( ( year(pagamento.dataPagamento) || month(pagamento.dataPagamento) ) = "
							+ "							  ( year(pagamento.conta.dataVencimentoConta) || month(pagamento.conta.dataVencimentoConta) ) ) ) THEN "
							+ "					1 "
							+ "				ELSE "
							+ "					CASE WHEN ( (pagamento.dataPagamento > conta.dataVencimentoConta) ) THEN "
							+ "						99 "
							+ "					END "
							+ "				END "
							+ "			END "
							+
							// CASO: Guia de Pagamento
							"		ELSE "
							+ "			CASE WHEN (pagamento.guiaPagamentoGeral.guiaPagamento.id is not null) THEN "
							+ "				CASE WHEN (pagamento.dataPagamento <= guiaPagamento.dataVencimento) THEN "
							+ "					0 "
							+ "				ELSE "
							+ "					CASE WHEN ( (pagamento.dataPagamento > guiaPagamento.dataVencimento) AND "
							+ "								( ( year(pagamento.dataPagamento) || month(pagamento.dataPagamento) ) = "
							+ "							      ( year(pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento) || month(pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento) ) ) ) THEN "
							+ "						1 "
							+ "					ELSE "
							+ "						CASE WHEN ( (pagamento.dataPagamento > guiaPagamento.dataVencimento) ) THEN "
							+ "							98 "
							+ "						END "
							+ "					END "
							+ "				END "
							+
							// CASO: Débito à Cobrar
							"			ELSE " + "				CASE WHEN (pagamento.debitoACobrar.id is not null) THEN " + "					0 " + "				END "
							+ "			END " + "		END " + "	END, " +

							"	pagamento.dataPagamento, " + "	pagamento.conta.dataVencimentoConta, "
							+ "	pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento " +

							"from " + "	gcom.arrecadacao.pagamento.Pagamento pagamento " + "	inner join pagamento.imovel imovel "
							+ "	inner join imovel.localidade localidade " + "	inner join imovel.quadra quadra "
							+ "	inner join imovel.setorComercial setorComercial " + "	left join imovel.ligacaoAgua ligacaoAgua "
							+ "	left join imovel.ligacaoEsgoto ligacaoEsgoto " + "	left join pagamento.conta conta1 "
							+ " 	left join pagamento.guiaPagamentoGeral.guiaPagamento guiaPagamento "
							+ "	left join pagamento.debitoACobrar debitoACobrar "
							+ "	left join conta1.debitoCobrados debitoCobrado1 "
							+ "	left join pagamento.avisoBancario avisoBancario "
							+

							"where "
							+ "	pagamento.anoMesReferenciaArrecadacao = :anoMesReferenciaPagamento and "
							+ "	localidade.gerenciaRegional.id = :gerenciaRegional and "
							+ "	localidade.unidadeNegocio.id = :unidadeNegocio and "
							+ " 	localidade.localidade.id = :localidade_elo and "
							+ "	localidade.id = :localidade and "
							+ "	quadra.setorComercial.id = :setorComercial and "
							+ "	imovel.rota.id = :rota and "
							+ "	quadra.id = :quadra and "
							+ "	pagamento.imovel.imovelPerfil.id = :imovelPerfil and "
							+ "	pagamento.imovel.ligacaoAguaSituacao.id = :ligacaoAguaSituacao and "
							+ "	pagamento.imovel.ligacaoEsgotoSituacao.id = :ligacaoEsgotoSituacao and "
							+
							// Perfil da Ligacao da Agua
							"	case when (ligacaoAgua.ligacaoAguaPerfil.id is not null) then "
							+ "		ligacaoAgua.ligacaoAguaPerfil.id "
							+ "	else "
							+ "		0 "
							+ "	end = :ligacaoAguaPerfil and "
							+
							// Perfil da Ligacao do Esgoto
							"	case when (ligacaoEsgoto.ligacaoEsgotoPerfil.id is not null) then "
							+ "		ligacaoEsgoto.ligacaoEsgotoPerfil.id " + "	else "
							+ "		0 "
							+ "	end = :ligacaoEsgotoPerfil and "
							+

							"	pagamento.documentoTipo.id = :documentoTipo and "
							+
							// Situacao do Pagamento
							"	case when (pagamento.pagamentoSituacaoAtual.id is not null) then " + "		pagamento.pagamentoSituacaoAtual.id "
							+ "	else "
							+ "		0 "
							+ "	end = :pagamentoSituacaoAtual and "
							+
							// Indicador de Contas Recebidas
							"	CASE WHEN ( (year(pagamento.dataPagamento) || month(pagamento.dataPagamento)) < "
							+ "			          anoMesReferenciaPagamento) THEN " + "		2 " + "	ELSE " + "		1 " + "	END	= :indicadorContas "
							+ "and " + "	setorComercial.codigo = :codigoSetorComercial and "
							+ "	quadra.numeroQuadra = :numeroQuadra and "
							+

							// "	pagamento.arrecadacaoForma.id = :formaArrecadacao and " +
							// Alterado para corrigir o erro quando null
							"	CASE WHEN (pagamento.arrecadacaoForma.id is not null) THEN " + "		pagamento.arrecadacaoForma.id " + "	ELSE "
							+ "		0 " + "	END = :formaArrecadacao and " +

							"	pagamento.avisoBancario.arrecadador.id = :agenteArrecadador";

			retorno = session.createQuery(hql).setInteger("anoMesReferenciaPagamento", anoMesReferenciaPagamento).setInteger(
							"gerenciaRegional", resumoArrecadacaoAguaEsgoto.getGerGerenciaRegional().getId()).setInteger("unidadeNegocio",
							resumoArrecadacaoAguaEsgoto.getGerUnidadeNegocio().getId()).setInteger("localidade_elo",
							resumoArrecadacaoAguaEsgoto.getGerLocalidadeElo().getId()).setInteger("localidade",
							resumoArrecadacaoAguaEsgoto.getGerLocalidade().getId()).setInteger("setorComercial",
							resumoArrecadacaoAguaEsgoto.getGerSetorComercial().getId()).setInteger("rota",
							resumoArrecadacaoAguaEsgoto.getGerRota().getId()).setInteger("quadra",
							resumoArrecadacaoAguaEsgoto.getGerQuadra().getId()).setInteger("imovelPerfil",
							resumoArrecadacaoAguaEsgoto.getGerImovelPerfil().getId()).setInteger("ligacaoAguaSituacao",
							resumoArrecadacaoAguaEsgoto.getGerLigacaoAguaSituacao().getId()).setInteger("ligacaoEsgotoSituacao",
							resumoArrecadacaoAguaEsgoto.getGerLigacaoEsgotoSituacao().getId()).setInteger("ligacaoAguaPerfil",
							resumoArrecadacaoAguaEsgoto.getGerLigacaoAguaPerfil().getId()).setInteger("ligacaoEsgotoPerfil",
							resumoArrecadacaoAguaEsgoto.getGerLigacaoEsgotoPerfil().getId()).setInteger("documentoTipo",
							resumoArrecadacaoAguaEsgoto.getGerDocumentoTipo().getId()).setInteger("pagamentoSituacaoAtual",
							resumoArrecadacaoAguaEsgoto.getGerPagamentoSituacao().getId()).setInteger("indicadorContas",
							resumoArrecadacaoAguaEsgoto.getIndicadorRecebidasNomes())
			// .setInteger("epocaPagamento",
							// resumoArrecadacaoAguaEsgoto.getGerEpocaPagamento().getId())
							.setInteger("codigoSetorComercial", resumoArrecadacaoAguaEsgoto.getCodigoSetorComercial()).setInteger(
											"numeroQuadra", resumoArrecadacaoAguaEsgoto.getNumeroQuadra()).setInteger("formaArrecadacao",
											resumoArrecadacaoAguaEsgoto.getGerArrecadacaoForma().getId()).setInteger("agenteArrecadador",
											resumoArrecadacaoAguaEsgoto.getGerArrecadador().getId()).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}// getImoveisResumoArrecadacaoOutros

	/**
	 * [UC0553 - Gerar Resumo da Arrecadacao - Credito]
	 * 
	 * @author Ivan Sérgio
	 * @date 29/05/2007
	 * @param anoMesReferenciaPagamento
	 *            resumoArrecadacaoAguaEsgoto
	 * @return list
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public List getResumoArrecadacaoCredito(int anoMesReferenciaPagamento, UnResumoArrecadacao resumoArrecadacaoAguaEsgoto)
					throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = "select "
							+ "	imovel.id, "
							+ "	creditoRealizados.creditoOrigem.id,"
							+ "	creditoRealizados.lancamentoItemContabil.id, "
							+ "	case when (creditoRealizados.conta.id is not null) then "
							+ "		creditoRealizados.valorCredito "
							+ "	else "
							+ "		0 "
							+ "	end, "
							+

							// Época de Pagamento
							"	CASE WHEN ( (pagamento.conta.id is null) and "
							+ "				(pagamento.guiaPagamentoGeral.guiaPagamento.id is null) and "
							+ "				(pagamento.debitoACobrar.id is null) ) THEN "
							+ "		9 "
							+ "	ELSE "
							+
							// CASO: Conta
							"		CASE WHEN (pagamento.conta.id is not null) THEN "
							+ "			CASE WHEN (pagamento.dataPagamento <= conta.dataVencimentoConta) THEN "
							+ "				0 "
							+ "			ELSE "
							+ "				CASE WHEN ( (pagamento.dataPagamento > conta.dataVencimentoConta) AND "
							+ "							( ( year(pagamento.dataPagamento) || month(pagamento.dataPagamento) ) = "
							+ "							  ( year(pagamento.conta.dataVencimentoConta) || month(pagamento.conta.dataVencimentoConta) ) ) ) THEN "
							+ "					1 "
							+ "				ELSE "
							+ "					CASE WHEN ( (pagamento.dataPagamento > conta.dataVencimentoConta) ) THEN "
							+ "						99 "
							+ "					END "
							+ "				END "
							+ "			END "
							+
							// CASO: Guia de Pagamento
							"		ELSE "
							+ "			CASE WHEN (pagamento.guiaPagamentoGeral.guiaPagamento.id is not null) THEN "
							+ "				CASE WHEN (pagamento.dataPagamento <= guiaPagamento.dataVencimento) THEN "
							+ "					0 "
							+ "				ELSE "
							+ "					CASE WHEN ( (pagamento.dataPagamento > guiaPagamento.dataVencimento) AND "
							+ "								( ( year(pagamento.dataPagamento) || month(pagamento.dataPagamento) ) = "
							+ "							      ( year(pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento) || month(pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento) ) ) ) THEN "
							+ "						1 "
							+ "					ELSE "
							+ "						CASE WHEN ( (pagamento.dataPagamento > guiaPagamento.dataVencimento) ) THEN "
							+ "							98 "
							+ "						END "
							+ "					END "
							+ "				END "
							+
							// CASO: Débito à Cobrar
							"			ELSE " + "				CASE WHEN (pagamento.debitoACobrar.id is not null) THEN " + "					0 " + "				END "
							+ "			END " + "		END " + "	END, " +

							"	pagamento.dataPagamento, " + "	pagamento.conta.dataVencimentoConta, "
							+ "	pagamento.guiaPagamentoGeral.guiaPagamento.dataVencimento " +

							"from " + "	gcom.arrecadacao.pagamento.Pagamento pagamento " + "	inner join pagamento.imovel imovel "
							+ "	inner join imovel.localidade localidade " + "	inner join imovel.quadra quadra "
							+ "	inner join imovel.setorComercial setorComercial " + "	left join imovel.ligacaoAgua ligacaoAgua "
							+ "	left join imovel.ligacaoEsgoto ligacaoEsgoto "
							+ "	left join pagamento.conta conta1 "
							+ "   left join pagamento.guiaPagamentoGeral guiaGeral "
							+ "	left join guiaGeral.guiaPagamento guiaPagamento "
							+ "	left join pagamento.debitoACobrar debitoACobrar "
							+ "	left join pagamento.avisoBancario avisoBancario "
							+ "	inner join conta1.creditoRealizados creditoRealizados "
							+

							"where "
							+ "	pagamento.anoMesReferenciaArrecadacao = :anoMesReferenciaPagamento and "
							+ "	pagamento.imovel.localidade.gerenciaRegional.id = :gerenciaRegional and "
							+ "	pagamento.imovel.localidade.unidadeNegocio.id = :unidadeNegocio and "
							+ " 	pagamento.imovel.localidade.localidade.id = :localidade_elo and "
							+ "	pagamento.imovel.localidade.id = :localidade and "
							+ "	pagamento.imovel.quadra.setorComercial.id = :setorComercial and "
							+ "	pagamento.imovel.rota.id = :rota and "
							+ "	pagamento.imovel.quadra.id = :quadra and "
							+ "	pagamento.imovel.imovelPerfil.id = :imovelPerfil and "
							+ "	pagamento.imovel.ligacaoAguaSituacao.id = :ligacaoAguaSituacao and "
							+ "	pagamento.imovel.ligacaoEsgotoSituacao.id = :ligacaoEsgotoSituacao and "
							+
							// Perfil da Ligacao da Agua
							"	case when (ligacaoAgua.ligacaoAguaPerfil.id is not null) then "
							+ "		ligacaoAgua.ligacaoAguaPerfil.id "
							+ "	else "
							+ "		0 "
							+ "	end = :ligacaoAguaPerfil and "
							+
							// Perfil da Ligacao do Esgoto
							"	case when (ligacaoEsgoto.ligacaoEsgotoPerfil.id is not null) then "
							+ "		ligacaoEsgoto.ligacaoEsgotoPerfil.id " + "	else "
							+ "		0 "
							+ "	end = :ligacaoEsgotoPerfil and "
							+

							"	pagamento.documentoTipo.id = :documentoTipo and "
							+
							// Situacao do Pagamento
							"	case when (pagamento.pagamentoSituacaoAtual.id is not null) then " + "		pagamento.pagamentoSituacaoAtual.id "
							+ "	else "
							+ "		0 "
							+ "	end = :pagamentoSituacaoAtual and "
							+
							// Indicador de Contas Recebidas
							"	CASE WHEN ( (year(pagamento.dataPagamento) || month(pagamento.dataPagamento)) < "
							+ "			          anoMesReferenciaPagamento) THEN " + "		2 " + "	ELSE " + "		1 " + "	END	= :indicadorContas " +

							"and "
							+ "	pagamento.imovel.setorComercial.codigo = :codigoSetorComercial and "
							+ "	pagamento.imovel.quadra.numeroQuadra = :numeroQuadra and "
							+
							// "	pagamento.arrecadacaoForma.id = :formaArrecadacao and " +
							// Alterado para corrigir o erro quando null
							"	CASE WHEN (pagamento.arrecadacaoForma.id is not null) THEN " + "		pagamento.arrecadacaoForma.id " + "	ELSE "
							+ "		0 " + "	END = :formaArrecadacao and " +

							"	pagamento.avisoBancario.arrecadador.id = :agenteArrecadador";

			retorno = session.createQuery(hql).setInteger("anoMesReferenciaPagamento", anoMesReferenciaPagamento).setInteger(
							"gerenciaRegional", resumoArrecadacaoAguaEsgoto.getGerGerenciaRegional().getId()).setInteger("unidadeNegocio",
							resumoArrecadacaoAguaEsgoto.getGerUnidadeNegocio().getId()).setInteger("localidade_elo",
							resumoArrecadacaoAguaEsgoto.getGerLocalidadeElo().getId()).setInteger("localidade",
							resumoArrecadacaoAguaEsgoto.getGerLocalidade().getId()).setInteger("setorComercial",
							resumoArrecadacaoAguaEsgoto.getGerSetorComercial().getId()).setInteger("rota",
							resumoArrecadacaoAguaEsgoto.getGerRota().getId()).setInteger("quadra",
							resumoArrecadacaoAguaEsgoto.getGerQuadra().getId()).setInteger("imovelPerfil",
							resumoArrecadacaoAguaEsgoto.getGerImovelPerfil().getId()).setInteger("ligacaoAguaSituacao",
							resumoArrecadacaoAguaEsgoto.getGerLigacaoAguaSituacao().getId()).setInteger("ligacaoEsgotoSituacao",
							resumoArrecadacaoAguaEsgoto.getGerLigacaoEsgotoSituacao().getId()).setInteger("ligacaoAguaPerfil",
							resumoArrecadacaoAguaEsgoto.getGerLigacaoAguaPerfil().getId()).setInteger("ligacaoEsgotoPerfil",
							resumoArrecadacaoAguaEsgoto.getGerLigacaoEsgotoPerfil().getId()).setInteger("documentoTipo",
							resumoArrecadacaoAguaEsgoto.getGerDocumentoTipo().getId()).setInteger("pagamentoSituacaoAtual",
							resumoArrecadacaoAguaEsgoto.getGerPagamentoSituacao().getId()).setInteger("indicadorContas",
							resumoArrecadacaoAguaEsgoto.getIndicadorRecebidasNomes())
			// .setInteger("epocaPagamento",
							// resumoArrecadacaoAguaEsgoto.getGerEpocaPagamento().getId())
							.setInteger("codigoSetorComercial", resumoArrecadacaoAguaEsgoto.getCodigoSetorComercial()).setInteger(
											"numeroQuadra", resumoArrecadacaoAguaEsgoto.getNumeroQuadra()).setInteger("formaArrecadacao",
											resumoArrecadacaoAguaEsgoto.getGerArrecadacaoForma().getId()).setInteger("agenteArrecadador",
											resumoArrecadacaoAguaEsgoto.getGerArrecadador().getId()).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}// getImoveisResumoArrecadacaoOutros

	/**
	 * [FS0001] - Método que Verifica a Existencia de dados para o ano/mes de referencia da
	 * Arrecadacao
	 * 
	 * @author Ivan Sérgio
	 * @date 12/11/2007
	 */
	private void verificarDeletarDadosAnoMesReferenciaArrecadacao(int anoMesReferenciaArrecadacao) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String hql = "delete UnResumoArrecadacao " + "where anoMesReferencia = :anoMesReferenciaArrecadacao";

			session.createQuery(hql).setInteger("anoMesReferenciaArrecadacao", anoMesReferenciaArrecadacao).executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}
}