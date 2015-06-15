package spectogram;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageProcessor;

import com.musicg.wave.Wave;
import com.musicg.wave.extension.Spectrogram;

/**
 * Sheet 8, Ex 2
 *
 * @author Antonio Rajic, Daniel O'Grady
 *
 */
public class Spectogram {
	public static final String RSC_PATH = "rsc/";
	public static final String OUT_PATH = "out/";

	public static void main(final String[] args) {
		final String[] filenames = new String[] { "coldplay.wav", "gotorio.wav", "ritmo.wav", "top_of_the_world_7.wav" };
		for (final String f : filenames) {
			final ImagePlus imp = visualizeSpectogram(f, new Wave(RSC_PATH + f).getSpectrogram());
			final FileSaver saver = new FileSaver(imp);
			saver.saveAsJpeg(OUT_PATH + f + ".jpg");
		}
	}

	/**
	 * Creates a visual spectogram from the passed spectogram-data
	 *
	 * @param title
	 *            title of the image
	 * @param sp
	 *            spectogramdata
	 * @return visual representation of the spectogram in greyscale
	 */
	public static ImagePlus visualizeSpectogram(final String title, final Spectrogram sp) {
		final double[][] d = sp.getNormalizedSpectrogramData();
		final ImagePlus imp = IJ.createImage(title, "8G White", d.length, d[0].length, 16);
		final ImageProcessor p = imp.getProcessor();
		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < d[i].length; j++) {
				p.putPixel(i, j, (int) (d[i][j] * 255));
			}
		}
		return imp;
	}
}
