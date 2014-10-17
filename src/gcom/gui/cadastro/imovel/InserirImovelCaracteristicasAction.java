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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.AreaConstruidaFaixa;
import gcom.cadastro.imovel.FiltroAreaConstruidaFaixa;
import gcom.cadastro.imovel.FiltroPiscinaVolumeFaixa;
import gcom.cadastro.imovel.FiltroReservatorioVolumeFaixa;
import gcom.cadastro.imovel.PiscinaVolumeFaixa;
import gcom.cadastro.imovel.ReservatorioVolumeFaixa;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.MaiorQue;
import gcom.util.filtro.MenorQue;
import gcom.util.validacao.ValidarCampos;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class InserirImovelCaracteristicasAction
				extends GcomAction {

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("gerenciadorProcesso");

		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		// instanciando o ActionForm de InserirImovelLocalidadeActionForm
		DynaValidatorForm inserirImovelCaracteristicasActionForm = (DynaValidatorForm) sessao.getAttribute("InserirImovelActionForm");

		// Cria variaveis e carrega com o conteudo do form
		FormFile fotoFachada = (FormFile) inserirImovelCaracteristicasActionForm.get("fotoFachada");

		// String faixaConstruida = (String) inserirImovelCaracteristicasActionForm
		// .get("faixaAreaConstruida");
		String reservatorioInferior = (String) inserirImovelCaracteristicasActionForm.get("reservatorioInferior");
		// String faixaReservatorioInferior = (String) inserirImovelCaracteristicasActionForm
		// .get("faixaReservatorioInferior");
		String reservatorioSuperior = (String) inserirImovelCaracteristicasActionForm.get("reservatorioSuperior");
		// String faixaResevatorioSuperior = (String) inserirImovelCaracteristicasActionForm
		// .get("faixaResevatorioSuperior");
		String piscina = (String) inserirImovelCaracteristicasActionForm.get("piscina");
		// String faixaPiscina = (String) inserirImovelCaracteristicasActionForm
		// .get("faixaPiscina");

		Fachada fachada = Fachada.getInstancia();

		FiltroAreaConstruidaFaixa filtroAreaConstruida = new FiltroAreaConstruidaFaixa();
		FiltroReservatorioVolumeFaixa filtroReservatorioVolumeFaixa = new FiltroReservatorioVolumeFaixa();
		FiltroPiscinaVolumeFaixa filtroPiscina = new FiltroPiscinaVolumeFaixa();

		// Paramentro para diferenciar a companhia que o sistema está rodando
		if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_ADA)){
			// ********** Companhia - ADA ******************************
			String areaConstruida = (String) inserirImovelCaracteristicasActionForm.get("areaConstruida");

			if(areaConstruida != null && !areaConstruida.trim().equalsIgnoreCase("")){
				filtroAreaConstruida.adicionarParametro(new MaiorQue(FiltroAreaConstruidaFaixa.MENOR_FAIXA, Util
								.formatarMoedaRealparaBigDecimal(areaConstruida)));
				filtroAreaConstruida.adicionarParametro(new MenorQue(FiltroAreaConstruidaFaixa.MAIOR_FAIXA, Util
								.formatarMoedaRealparaBigDecimal(areaConstruida)));

				Collection areaConstruidas = fachada.pesquisar(filtroAreaConstruida, AreaConstruidaFaixa.class.getName());

				if(areaConstruidas != null && !areaConstruidas.isEmpty()){
					inserirImovelCaracteristicasActionForm.set("faixaAreaConstruida", ((AreaConstruidaFaixa) ((List) areaConstruidas)
									.get(0)).getId());
				}
			}
			// ********** Companhia - ADA ******************************
		}

		if(fotoFachada != null){

			// Verifica o tipo e o tamanho da foto
			if(ValidarCampos.validaFoto(fotoFachada)){
				inserirImovelCaracteristicasActionForm.set("fotoFachada", fotoFachada);

			}
		}

		if(reservatorioInferior != null && !reservatorioInferior.trim().equalsIgnoreCase("")){
			filtroReservatorioVolumeFaixa.adicionarParametro(new MaiorQue(FiltroReservatorioVolumeFaixa.VOLUME_MENOR_FAIXA, Util
							.formatarMoedaRealparaBigDecimal(reservatorioInferior)));
			filtroReservatorioVolumeFaixa.adicionarParametro(new MenorQue(FiltroReservatorioVolumeFaixa.VOLUME_MAIOR_FAIXA, Util
							.formatarMoedaRealparaBigDecimal(reservatorioInferior)));

			Collection reservatorioInferiores = fachada.pesquisar(filtroReservatorioVolumeFaixa, ReservatorioVolumeFaixa.class.getName());

			if(reservatorioInferiores != null && !reservatorioInferiores.isEmpty()){
				inserirImovelCaracteristicasActionForm.set("faixaReservatorioInferior",
								((ReservatorioVolumeFaixa) ((List) reservatorioInferiores).get(0)).getId());
			}
		}

		if(reservatorioSuperior != null && !reservatorioSuperior.trim().equalsIgnoreCase("")){
			filtroReservatorioVolumeFaixa.adicionarParametro(new MaiorQue(FiltroReservatorioVolumeFaixa.VOLUME_MENOR_FAIXA, Util
							.formatarMoedaRealparaBigDecimal(reservatorioSuperior)));
			filtroReservatorioVolumeFaixa.adicionarParametro(new MenorQue(FiltroReservatorioVolumeFaixa.VOLUME_MAIOR_FAIXA, Util
							.formatarMoedaRealparaBigDecimal(reservatorioSuperior)));

			Collection reservatorioSuperiores = fachada.pesquisar(filtroReservatorioVolumeFaixa, ReservatorioVolumeFaixa.class.getName());

			if(reservatorioSuperiores != null && !reservatorioSuperiores.isEmpty()){
				inserirImovelCaracteristicasActionForm.set("faixaReservatorioSuperior",
								((ReservatorioVolumeFaixa) ((List) reservatorioSuperiores).get(0)).getId());
			}
		}

		if(piscina != null && !piscina.trim().equalsIgnoreCase("")){
			filtroPiscina.adicionarParametro(new MaiorQue(FiltroPiscinaVolumeFaixa.VOLUME_MENOR_FAIXA, Util
							.formatarMoedaRealparaBigDecimal(piscina)));
			filtroPiscina.adicionarParametro(new MenorQue(FiltroPiscinaVolumeFaixa.VOLUME_MAIOR_FAIXA, Util
							.formatarMoedaRealparaBigDecimal(piscina)));

			Collection piscinas = fachada.pesquisar(filtroPiscina, PiscinaVolumeFaixa.class.getName());

			if(piscinas != null && !piscinas.isEmpty()){
				inserirImovelCaracteristicasActionForm.set("faixaPiscina", ((ReservatorioVolumeFaixa) ((List) piscinas).get(0)).getId());
			}
		}

		// HttpServletRequest request = httpServletRequest;
		return retorno;
	}

}
