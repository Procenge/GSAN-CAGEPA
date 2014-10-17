
package gcom.gui.componente;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.ResponseUtils;

public class UiComponentTag
				extends TagSupport {

	private String name;

	private String scope = "request";

	@Override
	public int doEndTag() throws JspException{

		UiComponent uiComp = null;

		if("application".equals(getScope())){

			uiComp = (UiComponent) pageContext.getServletContext().getAttribute(getName());

		}else if("session".equals(getScope())){

			uiComp = (UiComponent) pageContext.getSession().getAttribute(getName());

		}else if("page".equals(getScope())){

			uiComp = (UiComponent) pageContext.getAttribute(getName());

		}else{

			uiComp = (UiComponent) pageContext.getRequest().getAttribute(getName());
		}

		if(uiComp != null){

			ResponseUtils.write(pageContext, uiComp.getHtmlBody(pageContext));
		}

		return EVAL_PAGE;
	}

	public void setName(String name){

		this.name = name;
	}

	public String getName(){

		return name;
	}

	public void setScope(String scope){

		this.scope = scope;
	}

	public String getScope(){

		return scope;
	}
}
