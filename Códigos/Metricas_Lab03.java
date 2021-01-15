package Lab03;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


public class Metricas_Lab03 implements PlugInFilter {
	int w = 512;
	int h = 512;

	// Root mean squared error
	public double RMSE(ImageProcessor ip,ImageProcessor ip1) {

		double valorRef;// intensidade original
		double valorFil;// intensidade filtrada
		double dif;
		double soma1 = 0;
		double soma2 = 0;
		double rmse;
		
		int[][] img = ip.getIntArray();
		int[][] img_filtrada = ip1.getIntArray();

		for (int x = 0; x < w; x++) {// Varrendo as linhas até w-1
			for (int y = 0; y < h; y++) {
				valorRef = img[x][y]; // comparar os pixeis na mesma posição
				valorFil = img_filtrada[x][y];
				dif = (valorFil - valorRef);
				soma1 = soma1 + (dif * dif);
				soma2 = soma2 + (valorRef * valorRef);

			}
		}

		rmse = Math.sqrt((soma1 / soma2));
		return rmse;
	}

	// Signal noise relation
	public double SNR(ImageProcessor ip,ImageProcessor ip1) {
		double valorRef;// intensidade original
		double valorFil;// intensidade filtrada
		double dif;
		double quad, quad2;
		double soma1 = 0;
		double soma2 = 0;
		double snr;

		int[][] img = ip.getIntArray();
		int[][] img_filtrada = ip1.getIntArray();
		
		for (int x = 0; x < w; x++) {// Varrendo as linhas até w-1
			for (int y = 0; y < h; y++) {
				valorRef = img[x][y]; // comparar os pixeis na mesma posição
				valorFil = img_filtrada[x][y];
				quad = valorRef * valorRef;
				soma1 = soma1 + quad;

				dif = (valorRef - valorFil);
				quad2 = dif * dif;
				soma2 = soma2 + quad2;

			}
		}
		snr = 10 * Math.log10((soma1 / soma2));
		return snr;
	}

	public double Correlacao(ImageProcessor ip,ImageProcessor ip1) {
		double valorRef;// intensidade original
		double valorFil;// intensidade filtrada
		double soma1 = 0;
		double soma2 = 0;
		double cont = 0;
		double mediaRef, mediaFil;

		
		int[][] img = ip.getIntArray();
		int[][] img_filtrada = ip1.getIntArray();
		
		for (int x = 0; x < w; x++) {// Varrendo as linhas até w-1
			for (int y = 0; y < h; y++) {
				valorRef = img[x][y]; // comparar os pixeis na mesma posição
				valorFil = img_filtrada[x][y];
				soma1 = soma1 + valorRef;
				soma2 = soma2 + valorFil;
				cont++;

			}
		}
		mediaRef = (soma1 / cont);
		mediaFil = (soma2 / cont);
		// System.out.println("mediaref:" + mediaRef);
		// System.out.println("mediafil:" + mediaFil);

		double dif1, dif2;
		double temp, temp1, temp2;
		double cont1 = 0;
		double somatemp = 0;
		double covariancia;

		double somaFil = 0;
		double somaOri = 0;
		double desvioOri, desvioFil;
		double cr; // Coeficiente de correlação

		for (int x = 0; x < w; x++) {// Varrendo as linhas até w-1
			for (int y = 0; y < h; y++) {

				valorRef = img[x][y];
				valorFil = img_filtrada[x][y];

				dif1 = Math.abs((valorRef - mediaRef));
				dif2 = Math.abs((valorFil - mediaFil));

				temp = dif1 * dif2;
				somatemp = somatemp + temp;

				temp1 = dif1 * dif1;
				somaOri = somaOri + temp1;

				temp2 = dif2 * dif2;
				somaFil = somaFil + temp2;

				cont1++;
			}
		}

		desvioOri = Math.sqrt(somaOri / (cont1 - 1)); // correlação imagem original
		desvioFil = Math.sqrt(somaFil / (cont1 - 1)); // correlação imagem filtrada
		covariancia = (somatemp / (cont1 - 1)); // covariância da imagem original de filtrada
		cr = covariancia / (desvioOri * desvioFil);

		return cr;
	}

	// Sctrutural Similarity Index
	public double SSIM(ImageProcessor ip,ImageProcessor ip1) {

		double valorRef;// intensidade original
		double valorFil;// intensidade filtrada
		double soma1 = 0;
		double soma2 = 0;
		double cont = 0;
		double mediaRef, mediaFil;
		
		int[][] img = ip.getIntArray();
		int[][] img_filtrada = ip1.getIntArray();

		for (int x = 0; x < w; x++) {// Varrendo as linhas até w-1
			for (int y = 0; y < h; y++) {
				valorRef = img[x][y]; // comparar os pixeis na mesma posição
				valorFil = img_filtrada[x][y];
				soma1 = soma1 + valorRef;
				soma2 = soma2 + valorFil;
				cont++;

			}
		}
		mediaRef = (soma1 / cont);
		mediaFil = (soma2 / cont);

		double dif1, dif2;
		double temp, temp1, temp2;
		double cont1 = 0;
		double somatemp = 0;
		double covariancia;

		double somaFil = 0;
		double somaOri = 0;
		double desvioOri, desvioFil;

		for (int x = 0; x < w; x++) {// Varrendo as linhas até w-1
			for (int y = 0; y < h; y++) {

				valorRef = img[x][y];
				valorFil = img_filtrada[x][y];
				dif1 = valorRef - mediaRef;
				dif2 = valorFil - mediaFil;

				temp = dif1 * dif2;
				somatemp = somatemp + temp;

				temp1 = dif1 * dif1;
				somaOri = somaOri + temp1;

				temp2 = dif2 * dif2;
				somaFil = somaFil + temp2;

				cont1++;
			}
		}
		desvioOri = Math.sqrt(somaOri / (cont1 - 1)); // desvio padrão da imagem original
		desvioFil = Math.sqrt(somaFil / (cont1 - 1));// desvio padrão da imagem filtrada
		covariancia = somatemp / (cont1 - 1); // covariância da imagem original de filtrada

		double c1 = 2.55; // Esse valor é dado por C= 0,01 *255
		double c2 = 7.65; // Esse valor é dado por C= 0,03 *255
		double ssim;

		// dividindo os termos para facilitar a visualização
		double termo1 = (2 * mediaRef * mediaFil) + c1;
		double termo2 = (2 * covariancia) + c2;
		double termo3 = (mediaRef * mediaRef) + (mediaFil * mediaFil) + c1;
		double termo4 = (desvioOri * desvioOri) + (desvioFil * desvioFil) + c2;

		ssim = (termo1 * termo2) / (termo3 * termo4);
		return ssim;

	}

		@Override

		public void run(ImageProcessor ip) {
			// TODO Auto-generated method stub

		}

		@Override
		public int setup(String arg, ImagePlus imp) {
			// TODO Auto-generated method stub
			return DOES_8G;
		}
}
