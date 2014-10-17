
package gcom.tagslib;

import javax.servlet.jsp.JspException;

import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

/**
 * Tag com comportamento padrão de máscaras.
 * 
 * @author João Santana
 */
public class GcomMaskTag
				extends GcomBaseTag {

	private static final long serialVersionUID = 1L;

	private int size = 20;

	private String mask = null;

	public int doStartTag() throws JspException{

		StringBuilder numberTag = new StringBuilder();

		numberTag.append(prepareField());

		numberTag.append(prepareScripts());

		ResponseUtils.write(pageContext, numberTag.toString());

		return (EVAL_PAGE);
	}

	private String prepareField() throws JspException{

		StringBuilder fieldText = new StringBuilder("<input type=\"text\"");
		fieldText.append(" name=\"");
		fieldText.append(property);
		fieldText.append("\"");
		fieldText.append(" id=\"");
		fieldText.append(property);
		fieldText.append("\"");
		if(accesskey != null){
			fieldText.append(" accesskey=\"");
			fieldText.append(accesskey);
			fieldText.append("\"");
		}
		if(maxlength != null){
			fieldText.append(" maxlength=\"");
			fieldText.append(maxlength);
			fieldText.append("\"");
		}
		fieldText.append(" size=\"");
		fieldText.append(getSize());
		fieldText.append("\"");
		if(tabindex != null){
			fieldText.append(" tabindex=\"");
			fieldText.append(tabindex);
			fieldText.append("\"");
		}
		fieldText.append(" value=\"");
		if(value != null){
			fieldText.append(ResponseUtils.filter(value));
		}else if(redisplay){
			Object value = RequestUtils.lookup(pageContext, name, property, null);
			if(value == null) value = "";
			fieldText.append(ResponseUtils.filter(value.toString()));
		}
		fieldText.append("\"");
		fieldText.append(prepareEventHandlers());
		fieldText.append(prepareStyles());
		fieldText.append(getElementClose());
		fieldText.append("&nbsp;");

		return (fieldText.toString());
	}

	private String prepareScripts() throws JspException{

		StringBuilder scripts = new StringBuilder();

		scripts.append("<script language=\"JavaScript\"> ");
		scripts.append(" j(document).ready(function() { ");
		scripts.append(" j(\"#" + property + "\").mask(\"").append(mask).append("\"); ");
		scripts.append(" }); ");
		scripts.append("</script> ");

		return (scripts.toString());
	}

	public void release(){

		super.release();
		size = 20;
		mask = null;
	}

	public String getMask(){

		return mask;
	}

	public void setMask(String mask){

		this.mask = mask;
	}

	public void setSize(int size){

		this.size = size;
	}

	public int getSize(){

		return size;
	}

}
