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

package gcom.gui.relatorio.faturamento.conta;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FaturaContasPreFaturadasHelper;
import gcom.gui.faturamento.conta.FiltrarContasPreFaturadasActionForm;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioContasPreFaturadasAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * [UC3035] Concluir Faturamento Contas Pré-Faturadas
	 * [SB0002] - Imprimir Relação das Contas Pré-faturadas
	 * 
	 * @author Anderson Italo
	 * @date 23/02/2012
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();

		FiltrarContasPreFaturadasActionForm formulario = (FiltrarContasPreFaturadasActionForm) actionForm;

		FaturaContasPreFaturadasHelper faturaContasPreFaturadasHelper = (FaturaContasPreFaturadasHelper) sessao
						.getAttribute("faturaContasPreFaturadasHelper");

		Collection<Conta> colecaoConta = fachada.pesquisarContasPreFaturadas(faturaContasPreFaturadasHelper, -1, true);

		String periodoReferenciaFaturamento = "";
		if(!Util.isVazioOuBranco(formulario.getDataReferenciaContaInicial())){

			periodoReferenciaFaturamento = formulario.getDataReferenciaContaInicial();
		}

		if(!Util.isVazioOuBranco(formulario.getDataReferenciaContaFinal())){

			periodoReferenciaFaturamento += " a " + formulario.getDataReferenciaContaFinal();
		}

		String periodoVencimento = "";
		if(!Util.isVazioOuBranco(formulario.getDataVencimentoContaInicial())){

			periodoVencimento = formulario.getDataVencimentoContaInicial();
		}

		if(!Util.isVazioOuBranco(formulario.getDataVencimentoContaFinal())){

			periodoVencimento += " a " + formulario.getDataVencimentoContaFinal();
		}

		String matriculaImovel = "";
		if(!Util.isVazioOuBranco(formulario.getImovelID())){

			matriculaImovel = formulario.getImovelID();
		}

		String grupoFaturamento = "";
		if(Util.verificarIdNaoVazio(formulario.getFaturamentoGrupoID())){

			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ID, formulario.getFaturamentoGrupoID()));

			Collection<FaturamentoGrupo> colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class
							.getName());

			if(!Util.isVazioOrNulo(colecaoFaturamentoGrupo)){

				grupoFaturamento = ((FaturamentoGrupo) Util.retonarObjetoDeColecao(colecaoFaturamentoGrupo)).getDescricao();
			}
		}

		String localidadeInicial = "";
		String setorComercialInicial = "";
		if(Util.verificarIdNaoVazio(formulario.getLocalidadeOrigemID())){

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, formulario.getLocalidadeOrigemID()));

			Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(!Util.isVazioOrNulo(colecaoLocalidade)){

				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);
				localidadeInicial = localidade.getDescricaoComId();
			}

			if(Util.verificarIdNaoVazio(formulario.getSetorComercialOrigemCD())){

				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, formulario
								.getSetorComercialOrigemCD()));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, formulario
								.getLocalidadeOrigemID()));

				Collection<SetorComercial> colecaoSetor = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(!Util.isVazioOrNulo(colecaoSetor)){

					SetorComercial setor = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetor);
					setorComercialInicial = setor.getDescricaoComCodigo();
				}
			}
		}

		String localidadeFinal = "";
		String setorComercialFinal = "";
		if(Util.verificarIdNaoVazio(formulario.getLocalidadeDestinoID())){

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, formulario.getLocalidadeDestinoID()));

			Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(!Util.isVazioOrNulo(colecaoLocalidade)){

				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);
				localidadeFinal = localidade.getDescricaoComId();
			}

			if(Util.verificarIdNaoVazio(formulario.getSetorComercialDestinoCD())){

				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, formulario
								.getSetorComercialDestinoCD()));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, formulario
								.getLocalidadeDestinoID()));

				Collection<SetorComercial> colecaoSetor = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(!Util.isVazioOrNulo(colecaoSetor)){

					SetorComercial setor = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetor);
					setorComercialFinal = setor.getDescricaoComCodigo();
				}
			}
		}

		String rotaInicial = "";
		if(Util.verificarIdNaoVazio(formulario.getRotaOrigemID())){

			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, formulario.getRotaOrigemID()));
			filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);

			Collection<Rota> colecaoRota = fachada.pesquisar(filtroRota, Rota.class.getName());

			if(!Util.isVazioOrNulo(colecaoRota)){

				Rota rota = (Rota) Util.retonarObjetoDeColecao(colecaoRota);
				rotaInicial = rota.getDescricao();
			}
		}

		String rotaFinal = "";
		if(Util.verificarIdNaoVazio(formulario.getRotaDestinoID())){

			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, formulario.getRotaDestinoID()));
			filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);

			Collection<Rota> colecaoRota = fachada.pesquisar(filtroRota, Rota.class.getName());

			if(!Util.isVazioOrNulo(colecaoRota)){

				Rota rota = (Rota) Util.retonarObjetoDeColecao(colecaoRota);
				rotaFinal = rota.getDescricao();
			}
		}

		RelatorioContasPreFaturadas relatorio = new RelatorioContasPreFaturadas((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));

		relatorio.addParametro("colecaoConta", colecaoConta);
		relatorio.addParametro("periodoReferenciaFaturamento", periodoReferenciaFaturamento);
		relatorio.addParametro("periodoVencimento", periodoVencimento);
		relatorio.addParametro("matriculaImovel", matriculaImovel);
		relatorio.addParametro("grupoFaturamento", grupoFaturamento);
		relatorio.addParametro("localidadeInicial", localidadeInicial);
		relatorio.addParametro("localidadeFinal", localidadeFinal);
		relatorio.addParametro("setorComercialInicial", setorComercialInicial);
		relatorio.addParametro("setorComercialFinal", setorComercialFinal);
		relatorio.addParametro("rotaInicial", rotaInicial);
		relatorio.addParametro("rotaFinal", rotaFinal);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
		retorno = processarExibicaoRelatorio(relatorio, String.valueOf(TarefaRelatorio.TIPO_PDF), httpServletRequest, httpServletResponse,
						actionMapping);
		return retorno;
	}
}
