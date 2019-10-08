package principal;


import java.io.UnsupportedEncodingException;

import javax.swing.JOptionPane;

import sefaz.LeXml;
import sefaz.SefazXml;
import sefaz.XmlEstrutura;
import sefaz.XmlRetorno;

public class principal {
	public static XmlEstrutura Estrutura = new XmlEstrutura();
	public static XmlRetorno retornoXml = new XmlRetorno();

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String stpAmb = "1";           //Informar o Ambiente 1-Produção, 2-Homologação
		String sUFAutor ="";         //UF do Autor código IBGE pode homitir
		String sCNPJ = "08618022000202";   //CNPJ do Cliente
		//String sCNPJ = "00000000000000";
		//String sultNSU = "000000000007245";  // Ultima NSU vem do Banco.
		String sultNSU = "000000000000000";
		//String schNFe = "35190647960950089432550010007427911084598432";
		//String schNFe = "35191047960950089432550010008834231026047263";
		//String schNFe = "00000000000000000000000000000000000000000000";
		
		String schNFe =  JOptionPane.showInputDialog("Informe a chave");
		retornoXml = SefazXml.setXml(stpAmb, sUFAutor, sCNPJ, sultNSU, schNFe);
	    System.out.println("UltNSU: "+ retornoXml.getUltNSU());
	    System.out.println("MaxNSU: "+ retornoXml.getMaxNSU());
	    
		String campo ="CNPJ";
	    String campo1 ="dest";
	
	
	//For para varer o objeto retornoXml que contém uma lista de XML
		for (XmlEstrutura retorno : retornoXml.getXmlEstrutura()) {
			if(retorno.getSchema().equals("procNFe_v4.00.xsd")) {
				System.out.println("NSU: "+ retorno.getNsu());
				System.out.println("Shema: "+ retorno.getSchema());
				System.out.println("XML: "+ retorno.getXml());
				System.out.println(LeXml.lerarq(retorno.getXml(), campo, campo1));
				System.out.println(LeXml.lerarq(retorno.getXml(),"chNFe", ""));
				System.out.println(LeXml.lerarq(retorno.getXml(), "UF", "emit"));
				System.out.println(LeXml.lerarq(retorno.getXml(), "vPag", "detPag"));
			}
		}
	}

}
