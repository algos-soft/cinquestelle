/**
 * Title:        StampaMenuCliente.java
 * Package:      it.algos.albergo.ristorante.menu.stampa.cliente
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 19 maggio 2003 alle 11.01
 */

package it.algos.albergo.ristorante.menu.stampa.cliente;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.lingua.Lingua;
import it.algos.albergo.ristorante.lingua.LinguaModulo;
import it.algos.base.costante.CostanteCarattere;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.stampa.impostazione.PaginaStampa;
import it.algos.base.stampa.stampabile.StampabileDefault;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.ArrayList;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Stampare un Menu <br>
 * B - ... <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  19 maggio 2003 ore 11.01
 */
public final class StampaMenu extends StampabileDefault implements CostanteCarattere {

    /* asterisco ,per alimento congelato */
    private static final String ASTERISCO = "*";

    /* codice che identifica il piatto principale */
    private static final int PIATTO = 1;

    /* codice che identifica il contorno */
    private static final int CONTORNO = 2;

    /* codice che identifica la lingua principale */
    private static final int LINGUA1 = 1;

    /* codice che identifica la lingua secondaria */
    private static final int LINGUA2 = 2;


    /**
     * codice del menu da stampare
     */
    private int codiceMenu = 0;

    /**
     * codice della prima lingua per il menu
     */
    private int codL1 = 0;

    /**
     * codice della seconda lingua per il menu
     */
    private int codL2 = 0;

    /**
     * riferimento all'oggetto contenente i dati da stampare
     */
    private DatiStampa datiStampa = null;

    private String[] StringhePasto = new String[2]; // array stringhe pasto

    private String unaDataMenu = "";    // stringa data menu

    private ArrayList unElencoPiatti =
            new ArrayList(0);       // ArrayList di oggetti MenuCoppiaPiatti

    private int margineSx = 91;  // margine generale sinistro
    // era zero, modificato provvisoriamente per cartellina speciale

    private int margineUp = 35;  // margine generale superiore

    private int xData = margineSx + 0;  // posizione X della Data

    private int yData = margineUp + 16; // posizione Y della Data rispetto al margine superiore

    private int xColUno = margineSx + 0;    // posizione X della prima colonna (lingua principale)

    private int xColDue = margineSx + 312;    // posizione X della prima colonna (lingua secondaria)

    private int rientroCategoria = 0;    // rientro della Categoria relativo al margine

    private int rientroPiatto =
            rientroCategoria + 15;    // rientro del Piatto relativo alla Categoria

    private int rientroDescrizione =
            rientroPiatto + 5;    // rientro della Descrizione relativo al Piatto

    private int yPasto =
            margineUp + 60;    // posizione Y di inizio della scritta Pasto rispetto al margine sx

    private int yPiatti =
            margineUp +
                    60;    // posizione Y di inizio dell'area Piatti rispetto al margine superiore

    private int spazioCategoria = 32;   // spazio sopra alla Categoria

    private int spazioPiattoNome = 20;   // spazio sopra al nome del piatto

    private int spazioPiattoDescrizione = 12; // spazio sopra alla Descrizione del piatto

    private int yCongelato =
            margineUp +
                    460;    // posizione Y della dicitura Alimento Congelato rispetto al margine superiore

    // posizione Y della dicitura Intolleranze rispetto al testo congelato
    private int yIntolleranze =yCongelato+14;

    // font per la Data
    private Font unFontData = FontFactory.creaPrinterFontSerif(Font.BOLD + Font.ITALIC, 12f);

    // font per il Pasto
    private Font unFontPasto = FontFactory.creaPrinterFontSerif(Font.BOLD, 16f);

    // font per la Categoria
    private Font unFontCategoria = FontFactory.creaPrinterFontSerif(Font.BOLD, 12f);

    // font per il Piatto
    private Font unFontPiatto = FontFactory.creaPrinterFontSerif(12f);

    // font per la Descrizione
    private Font unFontDescrizione = FontFactory.creaPrinterFontSerif(Font.ITALIC, 10f);

    // font per la dicitura "Alimento congelato"
    private Font unFontCongelato = FontFactory.creaPrinterFontSerif(Font.ITALIC, 8f);

    // font per la dicitura "Intolleranze e allergie"
    private Font unFontIntolleranze = FontFactory.creaPrinterFontSerif(Font.PLAIN, 9f);


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param codiceMenu il codice del menu da stampare
     * @param codL2 il codice per la seconda lingua
     */
    public StampaMenu(int codiceMenu, int codL2) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza con i parametri */
        this.setCodiceMenu(codiceMenu);
        this.setCodL2(codL2);

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            /* regolazioni della Pagina */
            super.setOrientamentoPagina(PaginaStampa.ORIZZONTALE);
            super.setPresentaDialogoPagina(false);
            this.setMarginePagina(0, 0, 80, 20);

            /* imposta il codice della prima lingua */
            this.setCodL1(LinguaModulo.getCodLinguaPreferita());

            /* crea un oggetto per contenere i dati della stampa */
            datiStampa = new DatiStampa(this);

            /* regola il codice del menu */
            datiStampa.setCodiceMenu(this.getCodiceMenu());

            /* imposta il codice della prima lingua */
            datiStampa.setCodL1(this.getCodL1());

            /* imposta il codice della seconda lingua */
            datiStampa.setCodL2(this.getCodL2());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo inizia */


    /**
     * Disegna la pagina da stampare.
     * <p/>
     *
     * @param unGrafico l'oggetto grafico sul quale disegnare la pagina
     */
    private void disegnaPagina(Graphics2D unGrafico) {

        /* variabili e costanti locali di lavoro */
        boolean esisteCongelato = false;
        String stringa = "";

        /** disegna un rettangolo di prova - PROVVISORIO */
//        Rectangle unRettangolo = null;
//        unGrafico.drawRect(0,0,650,450);

        /* data e titolo */
        unGrafico.setFont(unFontData);
        unGrafico.drawString(unaDataMenu, xData, yData);

        /* pasto (in due lingue) */
        unGrafico.setFont(unFontPasto);
        unGrafico.drawString(StringhePasto[0], xColUno, yPasto);
        unGrafico.drawString(StringhePasto[1], xColDue, yPasto);

        /* ciclo per tutti i piatti (categorie comprese) */
        CoppiaPiatti unaCoppiaPiatti = null;
        int unCodiceCategoriaOld = 0;
        int unaYCorrente = yPiatti;

        String unaCategoriaPiatto1 = "";
        String unNomePiatto1 = "";
        String unaDescrizionePiatto1 = "";
        String unNomeContorno1 = "";
        String unaCongiunzione1 = "";
        String unNomePiattoCompleto1 = "";

        String unaCategoriaPiatto2 = "";
        String unNomePiatto2 = "";
        String unaDescrizionePiatto2 = "";
        String unNomeContorno2 = "";
        String unaCongiunzione2 = "";
        String unNomePiattoCompleto2 = "";

        int unCodiceCategoria = 0;

        for (int k = 0; k < unElencoPiatti.size(); k++) {
            unaCoppiaPiatti = (CoppiaPiatti)unElencoPiatti.get(k);

            /* se esiste anche un solo alimento congelato attiva il flag
             * per la stampa della dicitura "alimento congelato"*/
            if (unaCoppiaPiatti.contieneCongelato()) {
                esisteCongelato = true;
            }// fine del blocco if

            unaCategoriaPiatto1 = unaCoppiaPiatti.getCategoriaPiatto1();
            unNomePiatto1 = this.stringaPiatto(unaCoppiaPiatti, PIATTO, LINGUA1);
            unaDescrizionePiatto1 = unaCoppiaPiatti.getDescrizionePiatto1();
            unaCongiunzione1 = unaCoppiaPiatti.getCongiunzione1();
            unNomeContorno1 = this.stringaPiatto(unaCoppiaPiatti, CONTORNO, LINGUA1);

            unaCategoriaPiatto2 = unaCoppiaPiatti.getCategoriaPiatto2();
            unNomePiatto2 = this.stringaPiatto(unaCoppiaPiatti, PIATTO, LINGUA2);
            unaDescrizionePiatto2 = unaCoppiaPiatti.getDescrizionePiatto2();
            unaCongiunzione2 = unaCoppiaPiatti.getCongiunzione2();
            unNomeContorno2 = this.stringaPiatto(unaCoppiaPiatti, CONTORNO, LINGUA2);

            unCodiceCategoria = unaCoppiaPiatti.getCodiceCategoria();

            /* controllo se stampare la categoria */
            if (unCodiceCategoria != unCodiceCategoriaOld) {

                unaYCorrente += spazioCategoria;
                unGrafico.setFont(unFontCategoria);
                unGrafico.drawString(unaCategoriaPiatto1, xColUno + rientroCategoria, unaYCorrente);
                unGrafico.drawString(unaCategoriaPiatto2, xColDue + rientroCategoria, unaYCorrente);

            } /* fine del blocco if */
            unCodiceCategoriaOld = unCodiceCategoria;

            /* stampo il piatto */
            unaYCorrente += spazioPiattoNome;
            unGrafico.setFont(unFontPiatto);

            /* se c'e' il contorno, lo aggiunge dopo il piatto (con la congiunzione) */
            if (Lib.Testo.isValida(unNomeContorno1)) {
                unNomePiattoCompleto1 = unNomePiatto1;
                unNomePiattoCompleto1 += SPAZIO;
                unNomePiattoCompleto1 += unaCongiunzione1;
                unNomePiattoCompleto1 += SPAZIO;
                unNomePiattoCompleto1 += unNomeContorno1;
            } else {
                unNomePiattoCompleto1 = unNomePiatto1;
            } /* fine del blocco if-else */

            if (Lib.Testo.isValida(unNomeContorno2)) {
                unNomePiattoCompleto2 = unNomePiatto2;
                unNomePiattoCompleto2 += SPAZIO;
                unNomePiattoCompleto2 += unaCongiunzione2;
                unNomePiattoCompleto2 += SPAZIO;
                unNomePiattoCompleto2 += unNomeContorno2;
            } else {
                unNomePiattoCompleto2 = unNomePiatto2;
            } /* fine del blocco if-else */

            unGrafico.drawString(unNomePiattoCompleto1, xColUno + rientroPiatto, unaYCorrente);
            unGrafico.drawString(unNomePiattoCompleto2, xColDue + rientroPiatto, unaYCorrente);

            /* stampo la descrizione (solo se e' presente almeno una Descrizione) */
            boolean descrizione1Piena = !Lib.Testo.isVuota((String)unaDescrizionePiatto1);
            boolean descrizione2Piena = !Lib.Testo.isVuota((String)unaDescrizionePiatto2);
            if ((descrizione1Piena) | (descrizione2Piena)) {
                unaYCorrente += spazioPiattoDescrizione;
                unGrafico.setFont(unFontDescrizione);
                unGrafico.drawString(unaDescrizionePiatto1,
                        xColUno + rientroDescrizione,
                        unaYCorrente);
                unGrafico.drawString(unaDescrizionePiatto2,
                        xColDue + rientroDescrizione,
                        unaYCorrente);
            } /* fine del blocco if */

        } /* fine del blocco for */

        /* eventuale dicitura alimento congelato (in due lingue) */
        if (esisteCongelato) {
            unGrafico.setFont(unFontCongelato);
            stringa = this.stringaCongelato(LINGUA1);
            unGrafico.drawString(ASTERISCO + " " + stringa, xColUno, yCongelato);
            stringa = this.stringaCongelato(LINGUA2);
            unGrafico.drawString(ASTERISCO + " " + stringa, xColDue, yCongelato);
        }// fine del blocco if
        
        /* eventuale dicitura intolleranze (in due lingue) */
        Font font = unFontIntolleranze;
        unGrafico.setFont(font);
        
        String[] lines;
        int y;
        int interlinea=Lib.Fonte.getAltezzaFont(font);
        int width = xColDue-xColUno-10;
        
        stringa = this.stringaIntolleranze(LINGUA1);
        lines = Lib.Fonte.splitString(stringa, unGrafico, font, width);
        y = yIntolleranze;
    	for(String line : lines){
            unGrafico.drawString(line, xColUno, y);
            y+=interlinea;
        }
    	
        stringa = this.stringaIntolleranze(LINGUA2);
        lines = Lib.Fonte.splitString(stringa, unGrafico, font, width);
        y = yIntolleranze;
    	for(String line : lines){
            unGrafico.drawString(line, xColDue, y);
            y+=interlinea;
        }

    } /* fine del metodo */
    
    
//    private String[] splitString(String in, Graphics graphics, Font font, int wmax){
//    	ArrayList<String> lines = new ArrayList();
//    	String currLine="";
//    	String testLine="";
//    	String[] words = in.split(" ");
//    	for(String word : words){
//    		if(!currLine.equals("")){
//        		testLine=currLine+" "+word;
//    		}else{
//        		testLine=word;    			
//    		}
//    		Rectangle2D rect = Lib.Fonte.boundingBoxStringa(testLine, font, graphics);
//    		int width = (int)rect.getWidth();
//    		if(width<=wmax){
//    			currLine=testLine;
//    		}else{
//    			lines.add(currLine);
//    			currLine=word;
//    		}
//    	}
//    	
//    	// ultima riga
//    	if(!currLine.equals("")){
//			lines.add(currLine);
//    	}
//    	
//    	return lines.toArray(new String[0]);
//    }


    /**
     * Ritorna una stringa corrispondente al nome del piatto in una lingua.
     * <p/>
     *
     * @param coppia la coppia piatti dalla quale estrarre i dati
     * @param pc PIATTO per il piatto, CONTORNO per il contorno
     * @param lingua LINGUA1 per la lingua principale, LINGUA2 per la lingua secondaria
     *
     * @return la stringa corrispondente al piatto richiesto nella lingua richiesta.
     */
    private String stringaPiatto(CoppiaPiatti coppia, int pc, int lingua) {
        /* variabili e costanti locali di lavoro */
        String piatto = "";

        switch (pc) {

            case PIATTO:
                switch (lingua) {
                    case LINGUA1:
                        piatto = coppia.getNomePiatto1();
                        break;
                    case LINGUA2:
                        piatto = coppia.getNomePiatto2();
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
                if (coppia.isCongelatoPiatto()) {
                    piatto = this.addAsterisco(piatto);
                }// fine del blocco if

                break;

            case CONTORNO:
                switch (lingua) {
                    case LINGUA1:
                        piatto = coppia.getNomeContorno1();
                        break;
                    case LINGUA2:
                        piatto = coppia.getNomeContorno2();
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
                if (coppia.isCongelatoContorno()) {
                    piatto = this.addAsterisco(piatto);
                }// fine del blocco if

                break;
            default: // caso non definito
                break;
        } // fine del blocco switch

        /* valore di ritorno */
        return piatto;
    }


    /**
     * Aggiunge un asterisco a una stringa.
     * <p/>
     *
     * @return la stringa con l'asterisco aggiunto in coda
     */
    private String addAsterisco(String stringaIn) {
        /* variabili e costanti locali di lavoro */
        String stringaOut = "";

        try {    // prova ad eseguire il codice
            if (Lib.Testo.isValida(stringaIn)) {
                stringaOut = stringaIn + ASTERISCO;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringaOut;
    }


    /**
     * Ritorna la dicitura per "Alimento congelato" nella lingua principale o secondaria.
     * <p/>
     *
     * @param lingua il codice della lingua (LINGUA1/LINGUA2)
     *
     * @return la dicitura per "Alimento congelato" nella lingua richiesta
     */
    private String stringaCongelato(int lingua) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Modulo moduloLingua = null;
        int codLingua = 0;

        try {    // prova ad eseguire il codice

            moduloLingua = Progetto.getModulo(Ristorante.MODULO_LINGUA);

            switch (lingua) {
                case LINGUA1:
                    codLingua = this.getCodL1();
                    break;
                case LINGUA2:
                    codLingua = this.getCodL2();
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

            if (codLingua > 0) {
                stringa = moduloLingua.query().valoreStringa(Lingua.CAMPO_SURGELATI, codLingua);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }
    
    
    /**
     * Ritorna la dicitura per il testo allergie e intolleranze 
     * nella lingua principale o secondaria.
     * <p/>
     *
     * @param lingua il codice della lingua (LINGUA1/LINGUA2)
     *
     * @return la dicitura per il testo allergie nella lingua richiesta
     */
    private String stringaIntolleranze(int lingua) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Modulo moduloLingua = null;
        int codLingua = 0;

        try {    // prova ad eseguire il codice

            moduloLingua = Progetto.getModulo(Ristorante.MODULO_LINGUA);

            switch (lingua) {
                case LINGUA1:
                    codLingua = this.getCodL1();
                    break;
                case LINGUA2:
                    codLingua = this.getCodL2();
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

            if (codLingua > 0) {
                stringa = moduloLingua.query().valoreStringa(Lingua.CAMPO_INTOLLERANZE, codLingua);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }



    /**
     * Stampa l'oggetto grafico.
     * <p/>
     *
     * @param g
     * @param pf
     * @param pageIndex
     *
     * @return
     */
    public int print(Graphics g, PageFormat pf, int pageIndex) {

        /** variabili e costanti locali di lavoro */
        int ritorno = 0;
        Graphics2D unaPagina = null;

        try { // prova ad eseguire il codice

            /* alla prima chiamata, carica i dati per la stampa */
            if (this.getDatiStampa().isDatiCaricati() == false) {
                this.getDatiStampa().caricaDati();
            }// fine del blocco if

            if (pageIndex == 0) {

                /** richiama il metodo sovrascritto nella superclasse
                 *  che trasforma le coordinate, aggiunge all'oggetto
                 *  grafico eventuali parti fisse e ritorna un oggetto
                 *  Graphics2D sul quale disegnare */
                unaPagina = super.trasformaCoordinate(g, pf);

                /* regola il paint in nero - devo farlo se poi
                voglio stampare con J2PrinterWorks */
                Paint p =  new Color(0,0,0);
                unaPagina.setPaint(p);

                /** disegna le parti specifiche della pagina */
                this.disegnaPagina(unaPagina);

                ritorno = Printable.PAGE_EXISTS;

            } else {
                ritorno = Printable.NO_SUCH_PAGE;
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ritorno;

    } /* fine del metodo */


    private int getCodiceMenu() {
        return codiceMenu;
    }


    private void setCodiceMenu(int codiceMenu) {
        this.codiceMenu = codiceMenu;
    }


    private int getCodL1() {
        return codL1;
    }


    private void setCodL1(int codL1) {
        this.codL1 = codL1;
    }


    private int getCodL2() {
        return codL2;
    }


    private void setCodL2(int codL2) {
        this.codL2 = codL2;
    }


    private DatiStampa getDatiStampa() {
        return datiStampa;
    }


    private void setDatiStampa(DatiStampa datiStampa) {
        this.datiStampa = datiStampa;
    }


    /**
     * Regola la Data del menu
     *
     * @param unaDataMenu
     */
    public void setData(String unaDataMenu) {
        this.unaDataMenu = unaDataMenu;
    } /* fine del metodo setter */


    /**
     * Regola il Pasto del menu
     *
     * @param StringhePasto
     */
    public void setPasto(String[] StringhePasto) {
        this.StringhePasto = StringhePasto;
    } /* fine del metodo setter */


    /**
     * Regola l'elenco dei piatti
     *
     * @param unElencoPiatti
     */
    public void setPiatti(ArrayList unElencoPiatti) {
        this.unElencoPiatti = unElencoPiatti;
    } /* fine del metodo setter */




}// fine della classe