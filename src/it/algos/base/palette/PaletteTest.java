/**
 * Title:     PaletteTest
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-giu-2009
 */
package it.algos.base.palette;

import it.algos.base.azione.AzioneBase;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import javax.swing.Icon;
import javax.swing.UIManager;

/**
 * Test della Palette
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-giu-2009 ore 14.29.25
 */
public final class PaletteTest extends Object {

    /**
     * Costruttore completo senza parametri.<br>
     */
    public PaletteTest() {
        /* regolazioni iniziali di riferimenti e variabili */
        this.inizia();
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     */
    private void inizia() {

        try { // prova ad eseguire il codice

            /* Usa il Look and Feel di sistema (Aqua su Mac OS X) */
            String laf = UIManager.getSystemLookAndFeelClassName();

//            /* Usa il Look and Feel cross-patform (metal) */
//            laf = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(laf);

            Palette pal = new Palette();
            pal.addAzione(new AzTest1());
            pal.addAzione(new AzTest2());
            pal.addAzione(new AzTest3());
            pal.addAzione(new AzTest4());

//            pal.setBackground(new Color(250,255,191));
            pal.setOpaque(false);

            pal.avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Azione di test
     * <p/>
     */
    private class AzTest1 extends AzioneBase {

        public AzTest1() {
            /* rimanda al costruttore della superclasse */
            super();
            Icon icona = Lib.Risorse.getIconaBase("clock24");
            this.setIconaPiccola(icona);
            this.setIconaMedia(icona);
            this.setIconaGrande(icona);
            this.setTooltip("azione clock");
        }// fine del metodo costruttore completo
    }

    /**
     * Azione di test
     * <p/>
     */
    private class AzTest2 extends AzioneBase {

        public AzTest2() {
            /* rimanda al costruttore della superclasse */
            super();
            Icon icona = Lib.Risorse.getIconaBase("conferma24");
            this.setIconaPiccola(icona);
            this.setIconaMedia(icona);
            this.setIconaGrande(icona);
            this.setTooltip("azione conferma");
        }// fine del metodo costruttore completo
    }

    /**
     * Azione di test
     * <p/>
     */
    private class AzTest3 extends AzioneBase {

        public AzTest3() {
            /* rimanda al costruttore della superclasse */
            super();
            Icon icona = Lib.Risorse.getIconaBase("magic24");
            this.setIconaPiccola(icona);
            this.setIconaMedia(icona);
            this.setIconaGrande(icona);
            this.setTooltip("azione magic");
        }// fine del metodo costruttore completo
    }

    /**
     * Azione di test
     * <p/>
     */
    private class AzTest4 extends AzioneBase {

        public AzTest4() {
            /* rimanda al costruttore della superclasse */
            super();
            Icon icona = Lib.Risorse.getIconaBase("Torta24");
            this.setIconaPiccola(icona);
            this.setIconaMedia(icona);
            this.setIconaGrande(icona);
            this.setTooltip("azione statistica");            
        }// fine del metodo costruttore completo
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        new PaletteTest();
    } // fine del metodo main


}// fine della classe