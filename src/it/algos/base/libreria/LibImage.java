/**
 * Title:     LibImage
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      5-mar-2007
 */
package it.algos.base.libreria;

import com.wildcrest.j2printerworks.ImagePanel;
import it.algos.base.errore.Errore;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;

/**
 * Repository di funzionalità per la gestione delle immagini.
 * <p/>
 * Tutti i metodi sono statici <br> I metodi non hanno modificatore così sono visibili all'esterno
 * del package solo utilizzando l'interfaccia unificata <b>Lib</b><br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 03-feb-2005 ore 12.04.04
 */
public abstract class LibImage {


    /**
     * Crea una immagine da un componente.
     * <p/>
     * "Fotografa" il componente e restituisce la sua immagine grafica
     *
     * @param comp il componente
     * @param hScale il fattore di scala orizzontale
     * @param vScale il fattore di scala verticale
     *
     * @return l'immagine creata dal componente
     */
    static BufferedImage creaImmagine(Component comp, double hScale, double vScale) {
        /* variabili e costanti locali di lavoro */
        BufferedImage bImage = null;
        JFrame frame;
        Container cont = null;
        int pos = -1;
        int w, h;
        double wImage, hImage;
        Graphics2D g;

        try { // prova ad eseguire il codice

            /**
             * Per poter usare comp.createImage() il componente
             * deve essere displayable, cioè contenuto in un frame
             * visibile o di cui sia stato eseguito il pack().
             * Perciò se non è displayable devo metterlo provvisoriamente
             * in un frame e poi rimetterlo dove si trovava prima.
             */
            if (!comp.isDisplayable()) {
                /* determina il contenitore parente e l'indice
                 * del componente nel contenitore */
                cont = comp.getParent();
                if (cont != null) {
                    Component[] componenti = cont.getComponents();
                    for (int k = 0; k < componenti.length; k++) {
                        Component unComp = componenti[k];
                        if (comp.equals(unComp)) {
                            pos = k;
                            break;
                        }// fine del blocco if
                    } // fine del ciclo for
                }// fine del blocco if

                /* mette il componente in un frame ed esegue il pack()*/
                frame = new JFrame();
                frame.add(comp);
                frame.pack();

            }// fine del blocco if

            /* determina le dimensioni originali del componente */
            w = comp.getPreferredSize().width;
            h = comp.getPreferredSize().height;

            /* determina le dimensioni dell'immagine */
            wImage = w * vScale;
            hImage = h * hScale;

            /* crea una nuova BufferedImage che supporta la trasparenza (Alpha-RGB)*/
            bImage = new BufferedImage((int)wImage, (int)hImage, BufferedImage.TYPE_INT_ARGB);

            /* recupera il contesto grafico e lo scala */
            g = bImage.createGraphics();
            g.scale(vScale, hScale);

            /* disegna il componente */
            comp.paint(g);

            /* reinserisce il componente dov'era prima */
            if (pos >= 0) {
                cont.add(comp, pos);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bImage;
    }


    /**
     * Crea una immagine da un componente nelle dimensioni originali del componente.
     * <p/>
     * "Fotografa" il componente e restituisce la sua immagine grafica
     *
     * @param comp il componente
     *
     * @return l'immagine creata dal componente
     */
    static BufferedImage creaImmagine(Component comp) {
        return creaImmagine(comp, 1, 1);
    }


    /**
     * Crea un pannello contenente l'immagine di un componente a una data risoluzione.
     * <p/>
     * "Fotografa" il componente e crea un pannello contenente la sua immagine grafica alla
     * risuluzione desiderata
     *
     * @param comp il componente
     * @param dpi la risoluzione in dpi
     *
     * @return il pannello contenente l'immagine
     */
    static ImagePanel creaPannello(Component comp, double dpi) {
        /* variabili e costanti locali di lavoro */
        ImagePanel pan = null;
        Image image;
        double scale;

        try { // prova ad eseguire il codice

            scale = dpi / 72;
            image = creaImmagine(comp, scale, scale);
            pan = new ImagePanel(image);
            pan.setScale(1 / scale);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea un pannello contenente l'immagine di un componente alla risoluzione di default.
     * <p/>
     * "Fotografa" il componente e crea un pannello contenente la sua immagine grafica alla
     * risoluzione di default La risoluzione di default è quella dello schermo (72 dpi)
     *
     * @param comp il componente
     *
     * @return il pannello contenente l'immagine
     */
    static ImagePanel creaPannello(Component comp) {
        return creaPannello(comp, 72);
    }


    /**
     * Crea un pannello contenente l'immagine di un componente alla risoluzione di stampa standard.
     * <p/>
     * "Fotografa" il componente e crea un pannello contenente la sua immagine grafica alla
     * risoluzione di stampa standard La risoluzione di stampa standard è 300 dpi
     *
     * @param comp il componente
     *
     * @return il pannello contenente l'immagine
     */
    static ImagePanel creaPannelloStampa(Component comp) {
        return creaPannello(comp, 300);
    }


    /**
     * Ritorna una BufferedImage con il contenuto di una Image
     * <p/>
     *
     * @param image immagine da convertire
     *
     * @return la BufferedImage con  il contenuto della image
     */
    static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null),
                    image.getHeight(null),
                    transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }


    // This method returns true if the specified image has transparent pixels
    private static boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage)image;
            return bimage.getColorModel().hasAlpha();
        }

        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }

        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }


}// fine della classe
