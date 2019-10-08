package sefaz;

import java.security.Security;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;



public class SefazXml {
	
	private String stpAmb;
	private String sUFAutor;
	private String sCNPJ;
	private String sultNSU;
	private String schNFe;
	public static XmlEstrutura Estrutura;
	public static XmlRetorno retornoXml;
	
	
	
	public static XmlRetorno setXml(String stpAmb,String sUFAutor,String sCNPJ,String sultNSU,String schNFe) {
		
		StringBuilder cnf = new StringBuilder();
		
		 cnf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
		 cnf.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\r\n");
		 cnf.append("  <soap12:Body>\r\n");
		 cnf.append("    <nfeDistDFeInteresse xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NFeDistribuicaoDFe\">\r\n") ;
		 cnf.append("      <nfeDadosMsg><distDFeInt xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.01\">");
		 cnf.append("<tpAmb>"+stpAmb+"</tpAmb>");
		 if (sUFAutor!="") {
		 cnf.append("<cUFAutor>"+sUFAutor+"</cUFAutor>");
		 }
		 cnf.append("<CNPJ>"+sCNPJ+"</CNPJ>");
		 if (sultNSU=="000000000000000" && schNFe=="00000000000000000000000000000000000000000000" ) {
				 cnf.append("<distNSU><ultNSU>"+sultNSU+"</ultNSU></distNSU>");
		 } else if(sultNSU!="000000000000000") {
			 cnf.append("<distNSU><ultNSU>"+sultNSU+"</ultNSU></distNSU>");
		 }
		 if (schNFe!="00000000000000000000000000000000000000000000") {
		 cnf.append("<consChNFe><chNFe>"+schNFe+"</chNFe></consChNFe>");
		 }
		 cnf.append("</distDFeInt></nfeDadosMsg>\r\n");
		 cnf.append("    </nfeDistDFeInteresse>\r\n");
		 cnf.append("  </soap12:Body>\r\n");
		 cnf.append("</soap12:Envelope>");
		 
		 // System.out.println(cnf); 

		/** 
         * 1) codigoDoEstado = Código do Estado conforme tabela IBGE. 
         * 
         * 
         * 2) url = Endereço do WebService para cada Estado. 
         *       Ver relação dos endereços em: 
         *       Para Homologação: http://hom.nfe.fazenda.gov.br/PORTAL/WebServices.aspx 
         *       Para Produção: http://www.nfe.fazenda.gov.br/portal/WebServices.aspx 
         * 
         * 3) caminhoDoCertificadoDoCliente = Caminho do Certificado do Cliente (A1). 
         * 
         * 4) senhaDoCertificadoDoCliente = Senha do Certificado A1 do Cliente. 
         * 
         * 5) arquivoCacertsGeradoParaCadaEstado = Arquivo com os Certificados necessarios para 
         * acessar o WebService. Pode ser gerado com a Classe NFeBuildCacerts. 
         */   
        
        
        String caminhoDoCertificadoDoCliente = "C:/NFE/medicamental.pfx";  
        String senhaDoCertificadoDoCliente = "master2012";  
        String arquivoCacertsGeradoParaCadaEstado = "C:/NFE/NFeCacerts/";  

        /** 
         * Informações do Certificado Digital. 
         */  
        System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");  
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());  

        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");  

        System.clearProperty("javax.net.ssl.keyStore");  
        System.clearProperty("javax.net.ssl.keyStorePassword");  
        System.clearProperty("javax.net.ssl.trustStore");  

        System.setProperty("javax.net.ssl.keyStore", caminhoDoCertificadoDoCliente);  
        System.setProperty("javax.net.ssl.keyStorePassword", senhaDoCertificadoDoCliente);  

        System.setProperty("javax.net.ssl.trustStoreType", "JKS");  
        System.setProperty("javax.net.ssl.trustStore", arquivoCacertsGeradoParaCadaEstado); 


			HttpClient httpClient = new HttpClient();
			 
			BufferedReader br = null;
			String data = cnf.toString();
					PostMethod methodPost = new PostMethod("https://www1.nfe.fazenda.gov.br/NFeDistribuicaoDFe/NFeDistribuicaoDFe.asmx");
							methodPost.setRequestBody(data);
							methodPost.setRequestHeader("Content-Type", "text/xml");
							try {
								int returnCode = httpClient.executeMethod(methodPost);
							 
								if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
									System.out
									.println("The Post method is not implemented by this URI");
									methodPost.getResponseBodyAsString();
								} else {
									br = new BufferedReader(new InputStreamReader(methodPost
									.getResponseBodyAsStream()));
									String readLine;
									while (((readLine = br.readLine()) != null)) {
											/** 
											 * Printa a linha do retorno xml que etá na readLine
											 */   
										
										//RecuperaXml.lerarq(readLine);
										//Estrutura = TrataXML1.lerarq(readLine);
										retornoXml = TrataXML.lerarq(readLine);

										
										
									}
							  	}
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								methodPost.releaseConnection();
								if (br != null)
									try {
									br.close();
									} catch (Exception fe) {
										fe.printStackTrace();
									}
							}
		//return Estrutura;
		return retornoXml;
	}
}
