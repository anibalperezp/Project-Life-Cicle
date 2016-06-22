package src;

import java.io.File;
import java.io.IOException;

import ceis.grehu.gui.ProcessGraphDocking;
import ceis.grehu.gui.splash.SplashScreen;

public class Run {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SplashScreen splash = new SplashScreen("/icons/splashscreen1.JPG");
		splash.getStatusBar().showProgress(0);
		splash.setVisible(true);

		System.out.println("Cargando librerias...");

		File[] libraries = (new File("lib")).listFiles();
		for (File file : libraries) {
			try {
				DynamicPackagesLoader.addFile(file);
				splash.getStatusBar().showStatus("Cargando librerias..." + file.getName());
				splash.getStatusBar().incProgress(2);

				System.out.println("Cargando librerias..." + file.getName());

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		//JFrame.setDefaultLookAndFeelDecorated(true);
		ProcessGraphDocking processGraphDocking = new ProcessGraphDocking(splash.getStatusBar());
		processGraphDocking.validate();
		processGraphDocking.initialize(splash.getStatusBar());
		
		splash.getStatusBar().showStatus("Cargando Componentes : Mostrando Ventana Principal");
		System.out.println("Cargando Componentes : Mostrando Ventana Principal");
		splash.getStatusBar().incProgress(4);
		processGraphDocking.setVisible(true);//solo esto es necesario
		
		if (splash != null) {
			splash.setVisible(false); //escondo la splash
			splash.dispose();
			splash = null;
		}
	}

}
