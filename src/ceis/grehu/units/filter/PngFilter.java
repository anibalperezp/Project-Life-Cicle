package ceis.grehu.units.filter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PngFilter extends FileFilter {
    
    // Accept all directories and all gif, jpg, or tiff files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
	if (extension != null) {
            if ( extension.equals( "png" ) ) {//public final static String png = "png";
                    return true;
            } else {
                return false;
            }
    	}

        return false;
    }
    public String getExtension(File f) {//retorna la extension sin el punto
	if(f != null) {
	    String filename = f.getName();
	    int i = filename.lastIndexOf('.');
	    if(i>0 && i<filename.length()-1) {
		return filename.substring(i+1).toLowerCase();
	    };
	}
	return null;
    }
    
    // The description of this filter
    public String getDescription() {
    	return "Portable Network Graphics (*.png)";
    }
    
    public String getExtension() {
    	  return ".png";
      }
}