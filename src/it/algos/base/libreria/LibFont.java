/**
 * Title:     LibFont
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-gen-2004
 */
package it.algos.base.libreria;

import it.algos.base.costante.CostanteCarattere;
import it.algos.base.costante.CostanteFont;
import it.algos.base.errore.Errore;
import it.algos.base.font.FamigliaFont;
import it.algos.base.font.FontSingolo;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Repository di funzionalita' per la gestione dei Font. </p> Questa classe: <ul> <li> <li> </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 22-gen-2004 ore 11.10.32
 */
public final class LibFont {

    /**
     * Restituisce un oggetto metrico. </p>
     *
     * @param font tipo di font utilizzato
     *
     * @return oggetto metrico
     */
    private static FontMetrics getMetrica(Font font) {
        /* variabile locale di lavoro */
        FontMetrics metrica = null;
        Frame finestra;

        try {    // prova ad eseguire il codice
            /* crea un oggetto metrico (per fare i conti) */
            finestra = new Frame();
            metrica = finestra.getFontMetrics(font);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return metrica;
    }


    /**
     * Restituisce la larghezza in pixel di una stringa. </p>
     *
     * @param testo testo di cui calcolare la larghezza
     * @param font tipo di font utilizzato
     *
     * @return larghezza valore in pixel
     */
    static int getLarPixel(String testo, Font font) {
        /* variabile locale di lavoro */
        int larghezza = 0;
        boolean continua;
        FontMetrics metrica;

        try {    // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (Lib.Testo.isValida(testo) && font != null);

            if (continua) {
                /* recupera un oggetto metrico (per fare i conti) */
                metrica = LibFont.getMetrica(font);

                /* recupera la larghezza del testo calcolato secondo il tipo di font */
                larghezza = SwingUtilities.computeStringWidth(metrica, testo);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return larghezza;
    }


    /**
     * Restituisce la larghezza in pixel di una label.
     *
     * @param label col testo da calcolare
     *
     * @return valore in pixel
     */
    static int getLarPixel(JLabel label) {
        /* variabili e costanti locali di lavoro */
        int larghezza = 0;
        boolean continua;
        Font font;
        String testo;

        try {    // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (label != null);

            if (continua) {
                testo = label.getText();
                font = label.getFont();
                larghezza = Lib.Fonte.getLarPixel(testo, font);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return larghezza;
    }


    /**
     * Restituisce la larghezza in pixel di un radio bottone.
     *
     * @param radio col testo da calcolare
     *
     * @return valore in pixel
     */
    static int getLarPixel(JRadioButton radio) {
        /* variabili e costanti locali di lavoro */
        int larghezza = 0;
        boolean continua;
        Font font;
        String testo;

        try {    // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (radio != null);

            if (continua) {
                testo = radio.getText();
                font = radio.getFont();
                larghezza = Lib.Fonte.getLarPixel(testo, font);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return larghezza;
    }


    /**
     * Restituisce l'altezza totale standard di un font misurata come ascent + descent + leading
     *
     * @param font tipo di font utilizzato
     *
     * @return valore in pixel
     */
    static int altezzaTotaleStandard(Font font) {
        /* variabili e costanti locali di lavoro */
        int altezza = 0;
        boolean continua;
        FontMetrics metrica;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (font != null);

            if (continua) {
                /* recupera un oggetto metrico (per fare i conti) */
                metrica = LibFont.getMetrica(font);

                /* recupera l'altezza standard del font */
                altezza = metrica.getHeight();   // ascent + descent + leading
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return altezza;
    }


    /**
     * Restituisce l'altezza standard di un font misurata come ascent + descent (senza leading)
     *
     * @param font tipo di font utilizzato
     *
     * @return valore in pixel
     */
    static int altezzaADStandard(Font font) {
        /* variabili e costanti locali di lavoro */
        int altezza = 0;
        boolean continua;
        FontMetrics metrica;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (font != null);

            if (continua) {
                /* recupera un oggetto metrico (per fare i conti) */
                metrica = LibFont.getMetrica(font);

                /* recupera l'altezza standard del font */
                altezza = metrica.getAscent() + metrica.getDescent();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return altezza;
    }


    /**
     * Restituisce l'ascendente standard di un font misurata come ascent + descent
     *
     * @param font tipo di font utilizzato
     *
     * @return valore in pixel
     */
    static int ascendenteStandard(Font font) {
        /* variabili e costanti locali di lavoro */
        int ascendente = 0;
        boolean continua;
        FontMetrics metrica;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (font != null);

            if (continua) {
                /* recupera un oggetto metrico (per fare i conti) */
                metrica = LibFont.getMetrica(font);

                /* recupera l'ascendente standard del font */
                ascendente = metrica.getAscent();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ascendente;
    }


    /**
     * Restituisce la discendente standard di un font.
     *
     * @param font tipo di font utilizzato
     *
     * @return valore in pixel
     */
    static int discendenteStandard(Font font) {
        /* variabili e costanti locali di lavoro */
        int discendente = 0;
        boolean continua;
        FontMetrics metrica;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (font != null);

            if (continua) {
                /* recupera un oggetto metrico (per fare i conti) */
                metrica = LibFont.getMetrica(font);

                /* recupera l'ascendente standard del font */
                discendente = metrica.getDescent();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return discendente;
    }


    /**
     * Restituisce il bounding box di una stringa in un dato contesto grafico
     *
     * @param testo testo da calcolare
     * @param font il Font del testo
     * @param contestoGrafico il contesto grafico in cui si disegna il testo
     *
     * @return il rettangolo nel quale il testo è inscritto
     */
    static Rectangle2D boundingBoxStringa(String testo, Font font, Graphics contestoGrafico) {
        /* variabili e costanti locali di lavoro */
        Rectangle2D box = null;
        boolean continua;
        FontMetrics metrica;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = ((Lib.Testo.isValida(testo)) && (font != null) && (contestoGrafico != null));

            if (continua) {
                /* recupera un oggetto metrico (per fare i conti) */
                metrica = LibFont.getMetrica(font);

                /* recupera il bounding box del testo */
                box = metrica.getStringBounds(testo, contestoGrafico);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return box;
    }

    //----------------------------------------------------------


    /**
     * Ritorna una collezione delle font interne. <br> Recupera la collezione dalla risorsa interna
     * (.jar) contenente le famiglie di font.
     *
     * @return una collezione di oggetti FamigliaFont
     */
    public static ArrayList generaCollezioneFamiglieFontInterne() {
        /* variabili e costanti locali di lavoro */
        ArrayList collezioneFamiglie = new ArrayList();
        ArrayList listaNomiFamiglie = null;
        String nomeFamiglia = null;
        String indirizzoDirectory = null;
        String percorsoBaseFonts = CostanteFont.PERCORSO_CARTELLA_FONTS;
        ArrayList elencoFiles = null;
        FamigliaFont unaFamiglia = null;

        try {    // prova ad eseguire il codice

            /* recupera l'elenco delle famiglie disponibili nelle risorse */
            listaNomiFamiglie = LibRisorse.directoryJar(percorsoBaseFonts);

            /* spazzola l'elenco e crea le famiglie */
            for (int k = 0; k < listaNomiFamiglie.size(); k++) {
                /* recupera il nome della famiglia (nome della cartella)*/
                nomeFamiglia = (String)listaNomiFamiglie.get(k);
                /* costruisce il percorso per raggiungere il contenuto */
                indirizzoDirectory = percorsoBaseFonts + CostanteCarattere.SEP_DIR + nomeFamiglia;
                /* costruisce l'elenco dei file contenuti nella cartella */
                elencoFiles = LibRisorse.filesJar(indirizzoDirectory);
                /* crea la famiglia di fonts */
                unaFamiglia = creaFamiglia(nomeFamiglia, indirizzoDirectory, elencoFiles);
                /* aggiunge la famiglia alla collezione */
                collezioneFamiglie.add(unaFamiglia);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return collezioneFamiglie;
    } // fine del metodo


    /**
     * Crea una Famiglia di Font. <br>
     *
     * @param unNome nome interno della famiglia da creare
     * @param unIndirizzo percorso per raggiungere la cartella di risorse che contiene i file
     * @param unElencoFiles elenco di nomi dei singoli files di font da leggere
     *
     * @return la famiglia di font creata
     */
    private static FamigliaFont creaFamiglia(String unNome,
                                             String unIndirizzo,
                                             ArrayList unElencoFiles) {

        /* variabili e costanti locali di lavoro */
        FamigliaFont famiglia = null;
        String nomeFile = null;
        String percorso = null;
        Font font = null;
        FontSingolo fontSingolo = null;
        int stile = 0;

        try {    // prova ad eseguire il codice

            /* crea una nuova famiglia */
            famiglia = new FamigliaFont(unNome);

            /* spazzola l'elenco dei file, crea i font e li
            *  aggiunge alla famiglia */
            for (int k = 0; k < unElencoFiles.size(); k++) {
                nomeFile = (String)unElencoFiles.get(k);
                percorso = unIndirizzo + CostanteCarattere.SEP_DIR + nomeFile;
                /* crea l'oggetto Font dalla risorsa */
                font = creaFontDaRisorsa(percorso);
                /* determina lo stile dal nome del file */
                stile = stileFont(nomeFile);
                /* crea l'oggetto FontSingolo da aggiungere alla famiglia */
                fontSingolo = new FontSingolo(font, stile);
                /* aggiunge il FontSingolo alla famiglia */
                famiglia.addFont(fontSingolo);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return famiglia;
    } // fine del metodo


    /**
     * Crea un oggetto Font in base a una risorsa di sistema
     *
     * @param risorsa il nome dall risorsa di sistema (deve rappresentare un font TrueType di tipo
     * Unicode-enabled)
     *
     * @return un oggetto Font con stile Plain e dimensione 1 punto
     */
    private static Font creaFontDaRisorsa(String risorsa) {
        /** variabili e costanti locali di lavoro */
        Font unFont = null;
        InputStream unInputStream = null;

        try {                                   // prova ad eseguire il codice
            /* apre uno stream relativo alla risorsa */
            unInputStream = LibRisorse.apreStreamDaNomeRisorsa(risorsa);
            /* crea un font TrueType dallo stream */
            unFont = Font.createFont(Font.TRUETYPE_FONT, unInputStream);
            /* chiude lo stream */
            unInputStream.close();

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unFont;
    } /* fine del metodo */


    /**
     * Determina lo stile di un font dal nome del file. <br>
     *
     * @param unNomeFile nome del file che definisce il font
     *
     * @return un intero corrispondente allo stile (valori definiti nelle costanti di stile della
     *         classe Font) Regole per la determinazione dello stile: Viene analizzato il primo
     *         carattere del nome del file, e confrontato con la seguente tabella: - 0 = Plain - 1 =
     *         Bold - 2 = Italic - 3 = Bold Italic Se il primo carattere non e' tra questi, lo stile
     *         ritornato e' Plain.
     */
    private static int stileFont(String unNomeFile) {
        /* variabili e costanti locali di lavoro */
        int stile = 0;
        char c;
        String s;
        int n;

        /* estrae il primo carattere dal nome del file */
        c = unNomeFile.charAt(0);
        s = String.valueOf(c);

        /* estrae il numero corrispondente */
        try {    // prova ad eseguire il codice
            n = (Integer.valueOf(s)).intValue();
        } catch (Exception unErrore) {    // intercetta l'errore
            /* non riesce a convertire in numero, assegna zero */
            n = 0;
        } // fine del blocco try-catch

        /* selezione dello stile */
        switch (n) {
            case 0:
                stile = Font.PLAIN;
                break;
            case 1:
                stile = Font.BOLD;
                break;
            case 2:
                stile = Font.ITALIC;
                break;
            case 3:
                stile = Font.BOLD + Font.ITALIC;
                break;
            default:
                stile = Font.PLAIN;
                break;
        } /* fine del blocco switch */

        /* valore di ritorno */
        return stile;
    } // fine del metodo


    /**
     * Ritorna l'elenco delle famiglie di font disponibili sul sistema
     *
     * @return l'elenco delle famiglie disponibile, vuoto se nessuna famiglia disponibile
     */
    public static String[] getElencoFamiglieFont() {
        /** variabili e costanti locali di lavoro */
        String[] unElenco = new String[0];
        GraphicsEnvironment unAmbiente = null;

        try {                                   // prova ad eseguire il codice
            unAmbiente = GraphicsEnvironment.getLocalGraphicsEnvironment();
            unElenco = unAmbiente.getAvailableFontFamilyNames();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unElenco;
    } /* fine del metodo */


    /**
     * Ritorna l'altezza di un font
     * <p/>
     *
     * @param unFont il font
     *
     * @return l'altezza del font
     */
    public static int getAltezzaFont(Font unFont) {
        /* variabili e costanti locali di lavoro */
        FontMetrics unaMetrica = null;

        JPanel unComponenteFasullo = new JPanel();
        unaMetrica = unComponenteFasullo.getFontMetrics(unFont);
        return unaMetrica.getHeight();
    }


    /**
     * Ritorna la massima altezza di un font
     * <p/>
     *
     * @param unFont il font
     *
     * @return l'altezza massima del font
     */
    public static int getMaxAltezzaFont(Font unFont) {
        /* variabili e costanti locali di lavoro */
        FontMetrics unaMetrica = null;
        JPanel unComponenteFasullo = null;
        double maxAscent = 0;
        double maxDescent = 0;
        double leading = 0;
        int h = 0;

        try { // prova ad eseguire il codice
            unComponenteFasullo = new JPanel();
            unaMetrica = unComponenteFasullo.getFontMetrics(unFont);
            maxAscent = unaMetrica.getMaxAscent();
            maxDescent = unaMetrica.getMaxDescent();
            leading = unaMetrica.getLeading();
            h = (int)(maxAscent + maxDescent + leading);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return h;
    }


    /**
     * Ritorna l'altezza di un componente.
     *
     * @param unComponente componente grafico
     *
     * @return altezza in pixel del componente
     */
    public static int getAltezza(Component unComponente) {
        /* variabili e costanti locali di lavoro */
        int altezza = 0;
        Font unFont = null;
        FontMetrics unaMetrica = null;

        try { // prova ad eseguire il codice
            unFont = unComponente.getFont();

            if (unFont != null) {
                unaMetrica = unComponente.getFontMetrics(unFont);
            }// fine del blocco if

            if (unaMetrica != null) {
                altezza = unaMetrica.getHeight();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return altezza;
    }
    
    
    /**
     * Splitta una stringa su più righe considerando la dimensione
     * del font, l'ambiente grafico e una larghzza max in pixel
     * @param in la stringa da splittare
     * @param graphics il contesto grafico
     * @param font il font
     * @param wmax la larghezza massima sula quale splittare
     * @return l'array delle parti splittate
     */
    public static String[] splitString(String in, Graphics graphics, Font font, int wmax){
    	ArrayList<String> lines = new ArrayList();
    	String currLine="";
    	String testLine="";
    	String[] words = in.split(" ");
    	for(String word : words){
    		if(!currLine.equals("")){
        		testLine=currLine+" "+word;
    		}else{
        		testLine=word;    			
    		}
    		Rectangle2D rect = Lib.Fonte.boundingBoxStringa(testLine, font, graphics);
    		int width = (int)rect.getWidth();
    		if(width<=wmax){
    			currLine=testLine;
    		}else{
    			lines.add(currLine);
    			currLine=word;
    		}
    	}
    	
    	// ultima riga
    	if(!currLine.equals("")){
			lines.add(currLine);
    	}
    	
    	return lines.toArray(new String[0]);
    }



}// fine della classe
