package principal;

import sefaz.SefazXml;
import sefaz.TrataXML;

public class principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String stpAmb = "1";           //Informar o Ambiente 1-Produção, 2-Homologação
		String sUFAutor ="";         //UF do Autor código IBGE pode homitir
		String sCNPJ = "08618022000202";   //CNPJ do Cliente
		//String sCNPJ = "00000000000000";
		String sultNSU = "000000000007210";  // Ultima NSU vem do Banco.
		//String sultNSU = "000000000000000";
		//String schNFe = "41190905607657000135550030008577431008637997";
		String schNFe = "00000000000000000000000000000000000000000000";
	
		
	SefazXml.setXml(stpAmb, sUFAutor, sCNPJ, sultNSU, schNFe);
	}

}
