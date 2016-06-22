package ceis.grehu.gui.dialog;

import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import ceis.grehu.gui.ProcessGraphDocking;
import ceis.grehu.units.printer.PrintObject;

//clase p�blica que se ejecuta donde debe de estar el main que llama a la
//otra clase.
public class Printer {
    
    private static ProcessGraphDocking graphDocking = null;
    
    public Printer(ProcessGraphDocking owner) {
	graphDocking = owner;
    }

    public static void main(String[] args) {
	// Creamos un objeto de impresi�n.
	PrinterJob job = PrinterJob.getPrinterJob();
	
	// Hacemos imprimible el objeto ObjetoAImprimir
	job.setPrintable(new PrintObject(getGraphDocking()));
	//Pondr� algo tipo Informaci�n job: sun.awt.windows.WPrinterJob@4a5ab2
	System.out.println("Informaci�n job: " + job.toString());
	
	//dialog para configurar pagina
	PageFormat pageFormat = new PageFormat();
	pageFormat=job.pageDialog(pageFormat);
	/*Paper paper = new Paper();
	paper.setImageableArea(0, 0, 8.5, 11);
	pageFormat.setPaper(paper);*/
	job.defaultPage(pageFormat);
	
	

	//Abre el cuadro de di�logo de la impresora, si queremos que imprima
	//directamente sin cuadro de di�logo quitamos el if...
	if (job.printDialog()) {
	    //Imprime, llama a la funci�n print del objeto a imprimir
	    //en nuestro caso el Objeto ObjetoAImprimir
	try {
		job.print();
	    } catch (PrinterException e) {
		System.out.println("Error de impresi�n: " + e);
	    }
	}
    }

    public static ProcessGraphDocking getGraphDocking() {
        return graphDocking;
    }
}
