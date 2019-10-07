package principal;


import sefaz.SefazXml;
import sefaz.XmlEstrutura;
import sefaz.XmlRetorno;

public class principal {
	public static XmlEstrutura Estrutura = new XmlEstrutura();
	public static XmlRetorno retornoXml = new XmlRetorno();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String stpAmb = "1";           //Informar o Ambiente 1-Produção, 2-Homologação
		String sUFAutor ="";         //UF do Autor código IBGE pode homitir
		String sCNPJ = "08618022000202";   //CNPJ do Cliente
		//String sCNPJ = "00000000000000";
		String sultNSU = "000000000007245";  // Ultima NSU vem do Banco.
		//String sultNSU = "000000000000000";
		//String schNFe = "41190905607657000135550030008577431008637997";
		String schNFe = "00000000000000000000000000000000000000000000";
	
		
	retornoXml = SefazXml.setXml(stpAmb, sUFAutor, sCNPJ, sultNSU, schNFe);
	System.out.println("CNPJ: " + sCNPJ);
	System.out.println("UltNSU: "+ retornoXml.getUltNSU());
	System.out.println("MaxNSU: "+ retornoXml.getMaxNSU());
	
	
	//For para varer o objeto retornoXml que contém uma lista de XML
		for (XmlEstrutura retorno : retornoXml.getXmlEstrutura()) {
			if(retorno.getSchema().equals("procNFe_v4.00.xsd")) {
				System.out.println("NSU: "+ retorno.getNsu());
				System.out.println("Shema: "+ retorno.getSchema());
				System.out.println("XML: "+ retorno.getXml());
			}
		}
	}

}
