
package gcom.gui.cobranca;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.RelatorioReligacoesPorImovel;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.util.Constantes;

public class GerarRelatorioReligacoesPorImovelAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * @author rslo
	 * @date 28/07/2010
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public final ActionForward execute(final ActionMapping actionMapping, final ActionForm actionForm,
					final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){

		final FiltroRelatorioReligacoesPorImovelActionForm form = (FiltroRelatorioReligacoesPorImovelActionForm) actionForm;

		this.validarForm(form, httpServletRequest);
		final RelatorioReligacoesPorImovel relatorio = this.criarRelatorioReligacoesPorImovel(form, httpServletRequest);
		try{
			return this.processarExibicaoRelatorio(relatorio, TarefaRelatorio.TIPO_XLS, httpServletRequest, httpServletResponse,
							actionMapping);
		}catch(TarefaException e){
			throw new ActionServletException(e.getMessage(), e);
		}
	}

	/**
	 * @author wpereira
	 * @date 29/07/2010
	 * @param form
	 * @param httpServletRequest
	 */
	private void validarForm(final FiltroRelatorioReligacoesPorImovelActionForm form, final HttpServletRequest httpServletRequest){

		if(Util.isVazioOuBranco(form.getComando())){
			throw new ActionServletException("atencao.informe_campo", null, "Tipo de Comando");
		}

		if(Util.isVazioOuBranco(form.getPeriodoInicial()) && Util.isVazioOuBranco(form.getPeriodoFim())){
			throw new ActionServletException("atencao.informe_campo", null, "Período");
		}

		if(Util.isVazioOuBranco(form.getPeriodoInicial()) || Util.isVazioOuBranco(form.getPeriodoFim())){
			this.validarDatas(form);
		}
	}

	/**
	 * @author Isaac Silva
	 * @date 23/07/2010
	 * @param form
	 */
	private void validarDatas(final FiltroRelatorioReligacoesPorImovelActionForm form){

		Date periodoInicio = null;
		Date periodoFim = null;

		if(Util.isVazioOuBranco(form.getPeriodoInicial()) && Util.isVazioOuBranco(form.getPeriodoFim())){
			throw new ActionServletException("atencao.informe_campo", null, "Período");
		}

		// Data Inicial
		if(Util.isVazioOuBranco(form.getPeriodoInicial())){
			throw new ActionServletException("atencao.required", null, "Período Inicial");
		}

		try{
			periodoInicio = Util.converteStringParaDate(form.getPeriodoInicial());
		}catch(Exception e){
			throw new ActionServletException("atencao.campo.invalido", e, "Período Inicial");
		}

		// Data Final
		if(Util.isVazioOuBranco(form.getPeriodoFim())){
			throw new ActionServletException("atencao.required", null, "Período Final");
		}

		try{
			periodoFim = Util.converteStringParaDate(form.getPeriodoFim());
		}catch(Exception e){
			throw new ActionServletException("atencao.campo.invalido", e, "Período Final");
		}

		// Se data inicio maior que data fim
		if(Util.compararData(periodoInicio, periodoFim) == 1){
			throw new ActionServletException("atencao.data_inicial_maior_data_final", form.getPeriodoInicial(), form.getPeriodoFim());
		}
	}

	/**
	 * @author wpereira
	 * @date 29/07/2010
	 * @param form
	 * @param httpServletRequest
	 * @return
	 */
	private RelatorioReligacoesPorImovel criarRelatorioReligacoesPorImovel(final FiltroRelatorioReligacoesPorImovelActionForm form,
					final HttpServletRequest httpServletRequest){

		final RelatorioReligacoesPorImovel relatorio = new RelatorioReligacoesPorImovel(this.getUsuarioLogado(httpServletRequest));

		relatorio.addParametro("tipoFormatoRelatorio", Integer.valueOf(TarefaRelatorio.TIPO_XLS));

		if(form.getComando() != null && !"".equals(form.getComando())){
			final String comando = form.getComando();

			if("1".equals(comando)){
				relatorio.addParametro("comandoCronograma", true);
			}else if("2".equals(comando)){
				relatorio.addParametro("comandoEventual", true);
			}
		}

		if(form.getIdCobrancaAcaoAtividadeComando() != null && !"".equals(form.getIdCobrancaAcaoAtividadeComando())){
			relatorio.addParametro("idComandoEventual", Integer.valueOf(form.getIdCobrancaAcaoAtividadeComando()));
		}
		if(form.getIdCobrancaAcaoAtividadeCronograma() != null && !"".equals(form.getIdCobrancaAcaoAtividadeCronograma())){
			relatorio.addParametro("idComandoCronograma", Integer.valueOf(form.getIdCobrancaAcaoAtividadeCronograma()));
		}

		if(form.getAcao() != null && form.getAcao().length != 0){
			final Collection<Integer> idsAcaoCobranca = new ArrayList<Integer>();

			for(int i = 0; form.getAcao().length > i; i++){
				if(!form.getAcao()[i].equals("")){
					idsAcaoCobranca.add(Integer.valueOf(form.getAcao()[i]));
				}
			}

			if(!idsAcaoCobranca.isEmpty()){
				relatorio.addParametro("idsAcaoCobranca", idsAcaoCobranca);
			}
		}

		if((form.getPeriodoInicial() != null && !"".equals(form.getPeriodoInicial()))
						&& (form.getPeriodoFim() != null && !"".equals(form.getPeriodoFim()))){
			final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Constantes.LOCALE_PT_BR);

			try{
				relatorio.addParametro("dataInicio", dateFormat.parse(form.getPeriodoInicial()));
				relatorio.addParametro("dataFim", dateFormat.parse(form.getPeriodoFim()));
			}catch(ParseException e){
				throw new ActionServletException("atencao.campo.invalido", e);
			}
		}

		if(form.getIdGrupo() != null && !"".equals(form.getIdGrupo())){
			relatorio.addParametro("idGrupoCobranca", Integer.valueOf(form.getIdGrupo()));
		}

		if(form.getSetorComercial() != null && form.getSetorComercial().length != 0){
			final Collection<Integer> idsSetorComercial = new ArrayList<Integer>();

			for(int i = 0; form.getSetorComercial().length > i; i++){
				if(!form.getSetorComercial()[i].equals("")){
					idsSetorComercial.add(Integer.valueOf(form.getSetorComercial()[i]));
				}
			}

			if(!idsSetorComercial.isEmpty()){
				relatorio.addParametro("idsSetorComercial", idsSetorComercial);
			}

		}

		if(form.getBairro() != null && form.getBairro().length != 0){
			final Collection<Integer> idsBairro = new ArrayList<Integer>();

			for(int i = 0; form.getBairro().length > i; i++){
				if(!form.getBairro()[i].equals("")){
					idsBairro.add(Integer.valueOf(form.getBairro()[i]));
				}
			}

			if(!idsBairro.isEmpty()){
				relatorio.addParametro("idsBairro", idsBairro);
			}
		}

		if(form.getCategoria() != null && form.getCategoria().length != 0){
			final Collection<Integer> idsCategoria = new ArrayList<Integer>();

			for(int i = 0; form.getCategoria().length > i; i++){
				if(!form.getCategoria()[i].equals("")){
					idsCategoria.add(Integer.valueOf(form.getCategoria()[i]));
				}
			}

			if(!idsCategoria.isEmpty()){
				relatorio.addParametro("idsCategoria", idsCategoria);
			}
		}

		if(form.getServicoTipo() != null && form.getServicoTipo().length != 0){
			final Collection<Integer> idsServicoTipo = new ArrayList<Integer>();

			for(int i = 0; form.getServicoTipo().length > i; i++){
				if(!form.getServicoTipo()[i].equals("")){
					idsServicoTipo.add(Integer.valueOf(form.getServicoTipo()[i]));
				}
			}

			if(!idsServicoTipo.isEmpty()){
				relatorio.addParametro("idsServicoTipo", idsServicoTipo);
			}
		}

		final ServletContext context = getServlet().getServletContext();
		final String path = context.getRealPath("/");
		relatorio.addParametro("template", new File(path.concat("\\xls\\relatorioReligacaoPorImovelTemplate.xls")));

		return relatorio;
	}
}