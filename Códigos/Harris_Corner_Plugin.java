package Lab03;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Harris_Corner_Plugin implements PlugInFilter {
	ImagePlus im;

	static float alpha = HarrisCornerDetector.DEFAULT_ALPHA;
	static int threshold = HarrisCornerDetector.DEFAULT_THRESHOLD;
	static int nmax = 0; // points to show

	double a = 0.04;
	int t = 500;
	List<Corner> lista;
	List<Corner> listaoficial;
	
	
	public int setup(String arg, ImagePlus im) {
		this.im = im;

		return DOES_8G;

	}

	public void run(ImageProcessor ip) {
		if (!showDialog())
			return; // dialog canceled or error
		HarrisCornerDetector hcd =

				new HarrisCornerDetector(ip, alpha, threshold);
				
				
		hcd.findCorners();
		lista=hcd.collectCorners(20);
		listaoficial = hcd.cleanupCorners(lista);
		

		ImageProcessor result = hcd.showCornerPoints(ip);
		//ImagePlus win = new ImagePlus("Corners from " + im.getTitle(), result);
		//win.show();
	}

	void showAbout() {
		String cn = getClass().getName();
		IJ.showMessage("About " + cn + " ...", "Harris Corner Detector");
	}

	private boolean showDialog() {
		// display dialog, and return false if canceled or in error.
		GenericDialog dlg = new GenericDialog("Harris Corner Detector", IJ.getInstance());
		float def_alpha = HarrisCornerDetector.DEFAULT_ALPHA;
		dlg.addNumericField("Alpha (default: " + def_alpha + ")", alpha, 3);
		int def_threshold = HarrisCornerDetector.DEFAULT_THRESHOLD;
		dlg.addNumericField("Threshold (default: " + def_threshold + ")", threshold, 0);
		dlg.addNumericField("Max. points (0 = show all)", nmax, 0);
		//dlg.showDialog();
		if (dlg.wasCanceled())
			return false;
		if (dlg.invalidNumber()) {
			IJ.showMessage("Error", "Invalid input number");
			return false;
		}

		alpha = (float) dlg.getNextNumber();
		threshold = (int) dlg.getNextNumber();
		nmax = (int) dlg.getNextNumber();

		return true;
	}
	
	public int Numero_de_pontos (){
		
		int tamanho = listaoficial.size();
		return tamanho;
	}
	
	
}
