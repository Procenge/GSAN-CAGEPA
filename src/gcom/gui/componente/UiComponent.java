
package gcom.gui.componente;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public interface UiComponent {

	public String getHtmlBody(PageContext pageContext) throws JspException;
}
