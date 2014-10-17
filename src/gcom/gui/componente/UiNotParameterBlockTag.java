
package gcom.gui.componente;

import gcom.seguranca.acesso.FuncionalidadeCorrente;
import gcom.util.ControladorException;
import gcom.util.parametrizacao.Parametro;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.com.procenge.parametrosistema.api.ParametroSistema;
import br.com.procenge.parametrosistema.api.TipoParametroSistema;

public class UiNotParameterBlockTag
				extends BodyTagSupport {

	private Parametro parameter;

	private String value;

	@Override
	public int doStartTag() throws JspException{

		Integer idFuncionalidade = FuncionalidadeCorrente.get().getId();
		ParametroSistema parametroSistema = null;
		try{
			parametroSistema = getParameter().getParametroSistema(idFuncionalidade);
		}catch(ControladorException e){
			throw new JspException(e.getMensagem(), e);
		}

		if(parametroSistema != null){

			String valor = parametroSistema.getValor();

			if(parametroSistema.getTipoParametroSistema().getCodigo() == TipoParametroSistema.TIPO_DINAMICO){

				if(valor != null){

					valor = valor.trim();

					if(valor.length() > 0){

						if(getValue() == null || getValue().equalsIgnoreCase(valor)){

							return SKIP_BODY;
						}
					}
				}
			}
		}

		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException{

		return EVAL_PAGE;
	}

	public void setValue(String value){

		this.value = value;
	}

	public String getValue(){

		return value;
	}

	public void setParameter(Parametro parameter){

		this.parameter = parameter;
	}

	public Parametro getParameter(){

		return parameter;
	}

}
