package Lab03;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


public class Classe_Principal_Lab03 implements PlugInFilter {
	ImagePlus imp;
	ImageProcessor ip1,ip2,ip3,ip4,ip5;
	
	

	@Override
	public void run(ImageProcessor ip) {
		// TODO Auto-generated method stub
		

		int[][] img = ip.getIntArray(); // pega a imagem original e transforma em matriz
		
		// CHAMANDO OS FILTROS
		
		Filtros_Gerais f1 = new Filtros_Gerais();// chamando a função;
		ip1 = f1.FiltroMediana(3, ip, img, 1); 
		ip2= f1.FiltroMedia(2, ip, img, 1);
		ip3= f1.FiltroIbsf(ip, img, 1);
		ip4= f1.FiltroWiener(4, ip, img, 1);
		ip5= f1.FiltroGaussiana(1, ip, img, 1);
		
		
		
		//CHAMANDO AS METRICAS
		
		Metricas_Lab03 f2 = new Metricas_Lab03();// chamando a função;
		//Root mean squared error
		double  rmse1 = f2.RMSE(ip, ip1);
		double  rmse2 = f2.RMSE(ip, ip2);
		double  rmse3 = f2.RMSE(ip, ip3);
		double  rmse4 = f2.RMSE(ip, ip4);
		double  rmse5 = f2.RMSE(ip, ip5);
		
		//Root mean squared error
		double  snr1 = f2.SNR(ip, ip1);
		double  snr2 = f2.SNR(ip, ip2);
		double  snr3 = f2.SNR(ip, ip3);
		double  snr4 = f2.SNR(ip, ip4);
		double  snr5 = f2.SNR(ip, ip5);
		
		
		//Coeficiente de Correlação
		double  cr1 = f2.Correlacao(ip, ip1);
		double  cr2 = f2.Correlacao(ip, ip2);
		double  cr3 = f2.Correlacao(ip, ip3);
		double  cr4 = f2.Correlacao(ip, ip4);
		double  cr5 = f2.Correlacao(ip, ip5);
		
		
		//Sctrutural Similarity Index
		double  ssim1 = f2.SSIM(ip, ip1);
		double  ssim2 = f2.SSIM(ip, ip2);
		double  ssim3 = f2.SSIM(ip, ip3);
		double  ssim4 = f2.SSIM(ip, ip4);
		double  ssim5 = f2.SSIM(ip, ip5);
		
		
		//Chamando a classe Corners Plugin
		Harris_Corner_Plugin f3 = new Harris_Corner_Plugin();
				
		f3.run(ip1);
		double corner1 = f3.Numero_de_pontos();
		f3.run(ip2);
		double corner2 = f3.Numero_de_pontos();
		f3.run(ip3);
		double corner3 = f3.Numero_de_pontos();
		f3.run(ip4);
		double corner4 = f3.Numero_de_pontos();
		f3.run(ip5);
		double corner5 = f3.Numero_de_pontos();
		f3.run(ip5);
		/*Harris Corner Detector
		double  corner1 = f2.Corners(ip, ip1);
		double  corner2 = f2.Corners(img, img_filtromedia);
		double  corner3 = f2.Corners(img, img_filtroibsf);
		double  corner4 = f2.Corners(img, img_filtrowiener);
		double  corner5 = f2.Corners(img, img_filtrogaussiana);
		
		*/
		
		//CRIANDO AS TABELAS
		ResultsTable tabela = new ResultsTable();
		tabela.incrementCounter();
		tabela.addValue("Filtro", "Mediana");
		tabela.addValue("RMSE", rmse1);
		tabela.addValue("SNR", snr1);
		tabela.addValue("CR", cr1);
		tabela.addValue("SSIM", ssim1);
		tabela.addValue("Corner", corner1);
		
		
		tabela.incrementCounter();
		tabela.addValue("Filtro", "Media");
		tabela.addValue("RMSE", rmse2);
		tabela.addValue("SNR", snr2);
		tabela.addValue("CR", cr2);
		tabela.addValue("SSIM", ssim2);
		tabela.addValue("Corner", corner2);
		
		tabela.incrementCounter();
		tabela.addValue("Filtro", "IBSF");
		tabela.addValue("RMSE", rmse3);
		tabela.addValue("SNR", snr3);
		tabela.addValue("CR", cr3);
		tabela.addValue("SSIM", ssim3);
		tabela.addValue("Corner", corner3);
		
		tabela.incrementCounter();
		tabela.addValue("Filtro", "Wiener");
		tabela.addValue("RMSE", rmse4);
		tabela.addValue("SNR", snr4);
		tabela.addValue("CR", cr4);
		tabela.addValue("SSIM", ssim4);
		tabela.addValue("Corner", corner4);
		
		tabela.incrementCounter();
		tabela.addValue("Filtro", "GaussianBlur");
		tabela.addValue("RMSE", rmse5);
		tabela.addValue("SNR", snr5);
		tabela.addValue("CR", cr5);
		tabela.addValue("SSIM", ssim5);
		tabela.addValue("Corner", corner5);
		
		tabela.show("Tabela de Estatísticas");
		
		
		
		
		

	}
	@Override
	public int setup(String arg, ImagePlus imp) {
				
		return DOES_8G;
	}
}
