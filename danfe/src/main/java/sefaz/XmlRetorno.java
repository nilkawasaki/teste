package sefaz;

import java.util.List;


public class XmlRetorno {
	private String ultNSU;
	private String maxNSU;
	private List<XmlEstrutura> xmlEstrutura;
	
	
	public String getUltNSU() {
		return ultNSU;
	}
	public void setUltNSU(String ultNSU) {
		this.ultNSU = ultNSU;
	}
	public String getMaxNSU() {
		return maxNSU;
	}
	public void setMaxNSU(String maxNSU) {
		this.maxNSU = maxNSU;
	}
	public List<XmlEstrutura> getXmlEstrutura() {
		return xmlEstrutura;
	}
	
	public XmlEstrutura getXmlEstruturaP() {
		return xmlEstrutura.get(0);
	}
	
	public void setXmlEstrutura(List<XmlEstrutura> xmlEstrutura) {
		this.xmlEstrutura = xmlEstrutura;
	}
	
}
