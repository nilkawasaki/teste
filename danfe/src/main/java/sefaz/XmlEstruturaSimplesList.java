package sefaz;

import java.util.List;

public class XmlEstruturaSimplesList {
	private List<XmlEstruturaSimples> xmlES;

	
	public List<XmlEstruturaSimples> getXmlES() {
		return xmlES;
	}
	
	public XmlEstruturaSimples getXmlESI() {
		return xmlES.get(0);
	}

	public void setXmlES(List<XmlEstruturaSimples> xmlES) {
		this.xmlES = xmlES;
	}
	
	
}
