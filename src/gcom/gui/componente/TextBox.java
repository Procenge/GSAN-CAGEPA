
package gcom.gui.componente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.html.TextTag;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

public class TextBox
				extends TextTag
				implements UiComponent {

	public TextBox() {

	}

	public TextBox(HttpServletRequest request, String id) {

		request.setAttribute(id, this);
		setId(id);
	}

	public TextBox(HttpSession session, String id) {

		session.setAttribute(id, this);
		setId(id);
	}

	public String getHtmlBody(PageContext pageContext) throws JspException{

		setPageContext(pageContext);

		// Create an appropriate "input" element based on our parameters
		StringBuffer results = new StringBuffer("<input type=\"");
		results.append(type);
		results.append("\" name=\"");
		// * @since Struts 1.1
		if(indexed) prepareIndex(results, name);

		results.append(property);
		results.append("\"");

		if(accesskey != null){
			results.append(" accesskey=\"");
			results.append(accesskey);
			results.append("\"");
		}
		if(accept != null){
			results.append(" accept=\"");
			results.append(accept);
			results.append("\"");
		}
		if(maxlength != null){
			results.append(" maxlength=\"");
			results.append(maxlength);
			results.append("\"");
		}
		if(cols != null){
			results.append(" size=\"");
			results.append(cols);
			results.append("\"");
		}
		if(tabindex != null){
			results.append(" tabindex=\"");
			results.append(tabindex);
			results.append("\"");
		}
		results.append(" value=\"");
		if(value != null){
			results.append(ResponseUtils.filter(value));
		}else if(redisplay || !"password".equals(type)){
			Object value = RequestUtils.lookup(pageContext, name, property, null);
			if(value == null) value = "";
			results.append(ResponseUtils.filter(value.toString()));
		}
		results.append("\"");
		results.append(prepareEventHandlers());
		results.append(prepareStyles());
		results.append(getElementClose());

		return results.toString();
	}

	@Override
	public int doAfterBody() throws JspException{

		return SKIP_BODY;
	}

	@Override
	public int doStartTag() throws JspException{

		return SKIP_BODY;
	}

	@Override
	public void doInitBody() throws JspException{

	}

	@Override
	public int doEndTag() throws JspException{

		return SKIP_PAGE;
	}
}
