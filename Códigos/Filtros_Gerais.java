package Lab03;

import java.util.ArrayList;
import java.util.Collections;

import ij.ImagePlus;
import ij.gui.NewImage;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Filtros_Gerais implements PlugInFilter {

	ImagePlus imp; // ImagePlus para a imagem inicial
	ImagePlus imagem_AUX; // ImagePlus para a imagem a ser duplicada da inicial
	ImageProcessor ip1; // Image Processor para a imagem duplicada
	ArrayList<Integer> lista_aux = new ArrayList<Integer>();
	int w = 512;
	int h = 512;

	@Override
	public void run(ImageProcessor ip) {
		// TODO Auto-generated method stub

	}

	public ImageProcessor FiltroMediana(int raio, ImageProcessor ip1, int[][] img, int criar) {

		if (criar == 1) {
			int h = ip1.getHeight();// Pegando a altura da imagem original
			int w = ip1.getWidth();// Pegando a largura da imagem original
			imagem_AUX = NewImage.createByteImage("Filtro de Mediana", w, h, 1, NewImage.FILL_WHITE);
			ip1 = imagem_AUX.getProcessor();
			imagem_AUX.show();

		}

		for (int x = 0; x < w; x++) {// Varrendo as linhas até w-1
			for (int y = 0; y < h; y++) {

				for (int x1 = x - raio; x1 <= x + raio; x1++) {
					for (int y1 = y - raio; y1 <= y + raio; y1++) {
						int p = x1;// Não queremos que p e q mude para sempre o seu valor
						int q = y1;

						if (p >= 0 && p < w && q >= 0 && q < h) {
							int cor = img[p][q];
							lista_aux.add(cor);

						}

					}

				}
				int size = lista_aux.size();

				if ((size % 2) == 0) {// se houver uma quantidade par de pontos (ou seja, o último índice é ímpar)
					int div = size / 2;// variável recebendo a metade do tamanho
					Collections.sort(lista_aux); // ordena a lista
					int xx = lista_aux.get(div);// pega a coordenada x do ponto da metade
					int yy = lista_aux.get(div - 1);// pega a coordenada x do ponto da metade-1
					int mediana = (xx + yy) / 2;// aplicando a definição de mediana quando se tem uma quantidade par de
												// pontos
					ip1.set(x, y, mediana);// seta no pixel central
				}

				else {
					int div = size / 2;// aplicando a definição de mediana quando se tem uma quantidade ímpar de pontos
					Collections.sort(lista_aux);// ordena a lista
					int mediana = lista_aux.get(div); // pega o valor da mediana
					ip1.set(x, y, mediana);// seta no pixel central
				}

				lista_aux = new ArrayList<Integer>(); // redeclara a lista
			}

		}

		//int[][] img_filtro = ip1.getIntArray();
		return ip1;
	}

	public ImageProcessor FiltroMedia(int raio, ImageProcessor ip1, int[][] img, int criar) {

		if (criar == 1) {
			int h = ip1.getHeight();// Pegando a altura da imagem original
			int w = ip1.getWidth();// Pegando a largura da imagem original
			imagem_AUX = NewImage.createByteImage("Filtro de Média", w, h, 1, NewImage.FILL_WHITE);
			ip1 = imagem_AUX.getProcessor();
			imagem_AUX.show();

		}

		for (int x = 0; x < w; x++) {// Varrendo as linhas até w-1
			for (int y = 0; y < h; y++) {

				for (int x1 = x - raio; x1 <= x + raio; x1++) {
					for (int y1 = y - raio; y1 <= y + raio; y1++) {
						int p = x1;// Não queremos que p e q mude para sempre o seu valor
						int q = y1;

						if (p >= 0 && p < w && q >= 0 && q < h) {
							int cor = img[p][q];
							lista_aux.add(cor);

						}

					}

				}
				int size = lista_aux.size();
				int soma = 0; // variável que vai receber o somatório das intensidades
				int media = 0;

				for (int a = 0; a < lista_aux.size(); a++) {// varrendo a lista de pontos
					int temp = lista_aux.get(a);
					soma = soma + temp; // realizando o somatório

				}
				media = soma / size;
				ip1.set(x, y, media);// seta no pixel central

				lista_aux = new ArrayList<Integer>(); // redeclara a lista
			}

		}

		
		return ip1;

	}

	public ImageProcessor FiltroIbsf(ImageProcessor ip1, int[][] img, int criar) {

		if (criar == 1) {
			int h = ip1.getHeight();// Pegando a altura da imagem original
			int w = ip1.getWidth();// Pegando a largura da imagem original
			imagem_AUX = NewImage.createByteImage("Filtro IBSF", w, h, 1, NewImage.FILL_WHITE);
			ip1 = imagem_AUX.getProcessor();
			imagem_AUX.show();

		}

		ip1 = this.FiltroMediana(5, ip1, img, 0);
		int valor1, valor2, valor;
		int[][] img_filtromediana = ip1.getIntArray();
		
		for (int x = 0; x < w; x++) { // Varrendo as linhas até w-1
			for (int y = 0; y < h; y++) {
				
				valor1 = img[x][y];
				valor2 = img_filtromediana[x][y];

				if (valor1 > valor2) {
					valor = valor1;
					ip1.set(x, y, valor);

				}

				if (valor1 < valor2) {
					valor = valor2;
					ip1.set(x, y, valor);
				}

				if (valor1 == valor2) {
					valor = valor2;
					ip1.set(x, y, valor);
				}

			}

		}
		imagem_AUX.updateAndDraw();
		int[][] img2 = ip1.getIntArray();
		ip1 = this.FiltroMediana(3, ip1, img2, 0);
		return ip1;

	}

	public ImageProcessor FiltroWiener(int raio, ImageProcessor ip1, int[][] img, int criar) {

		if (criar == 1) {
			int h = ip1.getHeight();// Pegando a altura da imagem original
			int w = ip1.getWidth();// Pegando a largura da imagem original
			imagem_AUX = NewImage.createByteImage("Filtro Wiener", w, h, 1, NewImage.FILL_WHITE);
			ip1 = imagem_AUX.getProcessor();
			imagem_AUX.show();

		}

		// Criando a media da região 'homogênea' na imagem com ruido
		double valor;
		double soma = 0;
		double media;
		double cont = 0;

		for (int x = 31; x <= 163; x++) { // Varrendo as linhas até
			for (int y = 272; y <= 329; y++) {
				valor = img[x][y];
				soma = soma + valor;
				cont++;

			}
		}
		media = soma / cont;

		double dif;
		cont = 0;
		double desvio;
		soma = 0;
		for (int k = 31; k <= 163; k++) { // Varrendo as linhas até
			for (int p = 272; p <= 329; p++) {
				valor = img[k][p];
				dif = valor - media;
				soma = soma + (dif * dif);
				cont++;

			}
		}
		desvio = Math.sqrt(soma / (cont - 1));
		double qh;
		qh = desvio / media;

		double contador = 0;
		double valor1 = 0;
		double valor2 = 0;
		double valor3;
		double desvioP;
		double qxy;
		double alfa;
		double temp;
		int cor;
		double valorIni;

		ip1 = this.FiltroMedia(4, ip1, img, 0); // chama o filtro de media e retorna a matriz do
		int[][] img_filtromedia	= ip1.getIntArray();													// moving average

		for (int x = 0; x < w; x++) {// Varrendo as linhas até w-1
			for (int y = 0; y < h; y++) {
				soma = 0;
				valor2 = img_filtromedia[x][y];
				valorIni = img[x][y];
				contador = 0;
				for (int x1 = x - raio; x1 <= x + raio; x1++) {
					for (int y1 = y - raio; y1 <= y + raio; y1++) {

						if (x1 >= 0 && x1 < w && y1 >= 0 && y1 < h) {

							valor1 = img[x1][y1];

							// elimina os que tão fora da imagem
							dif = (valor1 - valor2); // intensidade do pixel - media do pixel/ n-1
							soma = soma + (dif * dif);
							contador++;
						}

					}
				}
				valor3 = contador - 1;
				desvioP = Math.sqrt(soma / valor3);
				qxy = desvioP / valor2;
				alfa = 1 - ((qh * qh) / (qxy * qxy));

				if (alfa < 0) { // Para não cometer aberrações na imagem vamos definir o intervalo de alfa
					alfa = 0;
				}
				if (alfa >= 1) {
					alfa = 1;
				}

				temp = (valorIni * alfa) + ((1 - alfa) * (valor2));
				cor = (int) Math.round(temp);
				ip1.set(x, y, cor);
			}
		}

		imagem_AUX.updateAndDraw();
		
		return ip1;

	}

	public ImageProcessor FiltroGaussiana(int raio, ImageProcessor ip1, int[][] img, int criar) {

		if (criar == 1) {
			int h = ip1.getHeight();// Pegando a altura da imagem original
			int w = ip1.getWidth();// Pegando a largura da imagem original
			imagem_AUX = NewImage.createByteImage("Filtro Gaussiana", w, h, 1, NewImage.FILL_WHITE);
			ip1 = imagem_AUX.getProcessor();
			imagem_AUX.show();

		}
		double soma, soma2, div;
		int cor;
		double kernel[] = { 1, 2, 1, 2, 4, 2, 1, 2, 1 };
		int cont = 0;
		for (int x = 0; x < w; x++) {// Varrendo as linhas até w-1
			for (int y = 0; y < h; y++) {
				soma = 0;
				soma2 = 0;
				cont = 0;
				for (int x1 = x - raio; x1 <= x + raio; x1++) {
					for (int y1 = y - raio; y1 <= y + raio; y1++) {

						if (x1 >= 0 && x1 < w && y1 >= 0 && y1 < h) {

							soma = soma + (img[x1][y1] * kernel[cont]);
							soma2 = soma2 + kernel[cont];
						}
						cont++;

					}
				}
				div = soma / soma2;
				cor = (int) Math.round(div);
				ip1.set(x, y, cor);

			}
		}

		imagem_AUX.updateAndDraw();
		
		return ip1;
	}

	@Override
	public int setup(String arg, ImagePlus imp) {
		// TODO Auto-generated method stub
		this.imp = imp;
		return DOES_ALL;
	}

}
