/**
 * Title:        StampaDettaglio.java
 * Package:      it.algos.albergo.ristorante.menu.stampa.cucina
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 3 novembre 2003 alle 13.10
 */
package it.algos.albergo.ristorante.menu.stampa.cucina;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.categoria.Categoria;
import it.algos.albergo.ristorante.menu.Menu;
import it.algos.albergo.ristorante.modifica.ModificaModulo;
import it.algos.albergo.ristorante.tavolo.Tavolo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.stampa.stampabile.StampabileDefault;
import it.algos.base.wrapper.DueInt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.util.ArrayList;

/**
 * Stampa dettagliata degli ordini per la cucina
 * <p/>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  3 novembre 2003 ore 13.10
 */
public final class StampaDettaglio extends StampabileDefault {

    /**
     * Ordinata assoluta di inizio della stampa rispetto all'area stampabile
     */
    private static int Y_INIZIO_STAMPA;

    /**
     * Ascissa assoluta di inizio della stampa
     */
    private static int X_INIZIO_STAMPA;

    /**
     * Distanza verticale inizio area intestazione
     * rispetto all'inizio stampa
     */
    private static int Y_AREA_INTESTAZIONE;

    /**
     * Distanza verticale inizio area dettaglio
     * rispetto all'inizio stampa
     */
    private static int Y_AREA_DETTAGLIO;

    /**
     * Distanza verticale inizio filetti colonne
     * rispetto all'inizio dell'area dettaglio
     */
    private static float Y_FILO_COLONNA;

    /**
     * Distanza in discesa verticale dall'inizio del filetto colonna
     * fino all'inizio della stringa del titolo colonna
     * (quanto i titoli rientrano nelle colonne)
     */
    private static float RIENTRO_TITOLI_IN_COLONNA;

    /**
     * Rientro fisso per tutte righe di dettaglio
     * rispetto all'inizio area dettaglio
     */
    private static float RIENTRO_RIGA_DETTAGLIO;

    /**
     * Avanzamento verticale penna per ogni riga dettaglio
     */
    private static float STEP_RIGA_DETTAGLIO;

    /**
     * Rientro per i titoli categoria
     * rispetto all'inizio dell'area dettaglio
     */
    private static float X_RIGA_CATEGORIA;

    /**
     * Avanzamento verticale penna per righe categoria
     */
    private static float STEP_RIGA_CATEGORIA;

    /**
     * Avanzamento verticale penna per righe tipo
     */
    private static float STEP_RIGA_TIPO;

    /**
     * Distanza tra la linea di base della riga di dettaglio
     * e il filetto sottostante
     */
    private static float DISTANZA_FILETTO_RIGA;

    /**
     * Margine aggiuntivo della colonna dei nomi dei tavoli
     * (oltre alla lunghezza della riga piu' lunga, viene aggiunto questo spazio)
     */
    private static int SPAZIO_AGGIUNTIVO_COLONNA_RIGHE;

    /**
     * Flag - ripete la colonna con i nomi dei piatti su tutte le pagine
     * anziche' disegnarla solo sulla prima pagina
     */
    private static boolean RIPETI_PIATTI;

    /**
     * Dimensione e stile font per il titolo della stampa
     */
    private static int FONT_TITOLO_DIM;

    private static int FONT_TITOLO_STILE;

    /**
     * Dimensione e stile font per le righe Dettaglio
     */
    private static int FONT_DETTAGLIO_DIM;

    private static int FONT_DETTAGLIO_STILE;

    /**
     * Dimensione e stile font per le righe Categoria (Antipasti, Primi...)
     */
    private static int FONT_CATEGORIA_DIM;

    private static int FONT_CATEGORIA_STILE;

    /**
     * Dimensione e stile font per le righe Tipo (da Menu, Extra...)
     */
    private static int FONT_TIPO_DIM;

    private static int FONT_TIPO_STILE;

    /**
     * Dimensione e stile font per i nomi dei Tavoli (in verticale)
     */
    private static int FONT_TAVOLO_DIM;

    private static int FONT_TAVOLO_STILE;

    /**
     * Dimensione e stile font per la colonna totali dei piatti
     */
    private static int FONT_TOTALI_DIM;

    private static int FONT_TOTALI_STILE;

    /**
     * Dimensione e stile font per la colonna totali F.O. dei piatti
     */
    private static int FONT_TOTALI_FO_DIM;

    private static int FONT_TOTALI_FO_STILE;

    /**
     * Dimensione e stile font per le quantita' ordinate per tavolo/piatto
     */
    private static int FONT_QUANTITA_DIM;

    private static int FONT_QUANTITA_STILE;

    /**
     * Dimensione e stile font per i totali delle quantita' ordinate per piatto
     */
    private static int FONT_QUANTITA_TOT_DIM;

    private static int FONT_QUANTITA_TOT_STILE;

    /**
     * Dimensione e stile font per i totali delle quantita' fuori orario
     * ordinate per piatto
     */
    private static int FONT_QUANTITA_TOT_FO_DIM;

    private static int FONT_QUANTITA_TOT_FO_STILE;

    /**
     * Testo per il titolo della colonna Totali
     */
    private static String TESTO_TITOLO_TOTALI;

    /**
     * Testo per il titolo della colonna Totali Fuori Orario
     */
    private static String TESTO_TITOLO_TOTALI_FO;

    /**
     * Larghezza delle colonne Tavoli
     */
    private static int LARGHEZZA_COLONNA_TAVOLO;

    /**
     * Larghezza della colonna Totali
     */
    private static int LARGHEZZA_COLONNA_TOTALI;

    /**
     * Dimensione e stile font per i testi arrivi previsti/coperti mancanti
     */
    private static int FONT_AC_DIM;

    private static int FONT_AC_STILE;


    /**
     * Font per il titolo della stampa
     */
    private static Font fontTitoloStampa = null;

    /**
     * Font per le righe dettaglio
     */
    private static Font fontRigheDettaglio = null;

    /**
     * Font per le righe categoria
     */
    private static Font fontRigheCategoria = null;

    /**
     * Font per le righe tipo
     */
    private static Font fontRigheTipo = null;

    /**
     * Font per i nomi Tavolo
     */
    private static Font fontTavolo = null;

    /**
     * Font per la colonna Totali
     */
    private static Font fontTotali = null;

    /**
     * Font per la colonna Totali F.O.
     */
    private static Font fontTotaliFO = null;

    /**
     * Font per le quantita' ordinate
     */
    private static Font fontQuantita = null;

    /**
     * Font per i totali delle quantita' ordinate
     */
    private static Font fontQuantitaTotale = null;

    /**
     * Font per i totali delle quantita' Fuori Orario ordinate
     */
    private static Font fontQuantitaTotaleFO = null;

    /**
     * Font per i testi arrivi previsti/coperti mancanti
     */
    private static Font fontAc = null;

    /**
     * Coordinate assolute di inizio aree di stampa
     */
    int yAreaIntestazione = 0;

    int xAreaIntestazione = 0;

    int yAreaDettaglio = 0;

    int xAreaDettaglio = 0;

    /**
     * Codice del tipo di stampa da eseguire (Servizio, Cucina)
     *
     * @see Menu
     */
    int tipoStampa = 0;

    /**
     * Ordinata corrente della penna
     */
    private static float yPenna = 0;

    /**
     * Codice della categoria della riga di dettaglio corrente e precedente
     */
    private static int categoriaCorrente = 0;

    private static int categoriaPrecedente = 0;

    /**
     * Codice del tipo della riga di dettaglio corrente e precedente
     */
    private static int tipoCorrente = 0;

    private static int tipoPrecedente = 0;

    /**
     * Riferimento all'oggetto grafico sul quale stampare
     */
    private static Graphics2D pagina = null;

    /**
     * Larghezza stampabile della pagina
     */
    private static float larghezzaPagina = 0;

    /**
     * Altezza stampabile della pagina
     */
    private static float altezzaPagina = 0;

    /**
     * Numero della pagina correntemente in stampa (0 per la prima)
     */
    private static int numeroPagina = 0;

    /**
     * Numero massimo della pagina richiesta dal sistema di stampa  (0 per la prima)
     */
    private static int maxPagina = 0;


    /**
     * Riferimento al campo descrizione categoria
     */
    private static Campo campoDescrizioneCategoria = null;

    /**
     * Ordinata dell'ultimo filetto stampato sotto all'ultima riga
     */
    private static int yUltimoFilettoRiga = 0;

    /**
     * Flag - indica se la stampa e' terminata
     */
    private static boolean stampaTerminata = false;

    /**
     * Riferimento all'oggetto che contiene i dati da stampare
     */
    private DatiDettaglio datiStampa = null;

    /**
     * Elenco delle righe stampabili per questa stampa
     */
    private ArrayList righeStampabili = null;


    /**
     * Costruttore completo
     * <p/>
     *
     * @param dati = l'oggetto contenente i dati da stampare
     * @param tipoStampa = il tipo di stampa da eseguire (Servzio, Cucina)
     */
    public StampaDettaglio(DatiDettaglio dati, int tipoStampa) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia(dati, tipoStampa);
        } catch (Exception unErrore) {
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     *
     * @param dati = l'oggetto contenente i dati da stampare
     * @param tipoStampa = il tipo di stampa da eseguire (Servzio, Cucina)
     */
    private void inizia(DatiDettaglio dati, int tipoStampa) {

        /* regola i margini di questa stampa */
//        this.setMarginePagina(30,30,20,30);
        this.setMarginePagina(40, 40, 20, 40);

        /* assegna il riferimento all'oggetto contenente i dati*/
        this.datiStampa = dati;

        /* regolazione del tipo di stampa */
        this.tipoStampa = tipoStampa;

        /*
         * Regola le costanti base della classe
         * (uguali per tutti i tipi di stampa)
         */
        this.regolaCostantiBase();

        /*
         * Regola le costanti della classe
         * (specifiche per il tipo di stampa)
         */
        this.regolaCostantiSpecifiche();

        /* Regola le variabili di lavoro della classe */
        this.regolaVariabili();

//        PageFormat pf = this.getImpostazione().getPagina().getPageFormat();
//        double w = pf.getImageableWidth();
//        double h = pf.getImageableHeight();
//        JPanel pan = new JPanel();
//        pan.setPreferredSize(new Dimension((int)w,(int)h));
//        Graphics g = pan.getGraphics();
//        this.print(g,pf,1);
//        int a = 87;

    } /* fine del metodo inizia */


    /**
     * Regolazione delle costanti di base della classe.
     * <p/>
     * Regola le costanti uguali per tutti i tipi di stampa<br>
     * Eventualmente possono essere regolate ulteriormente durante
     * la regolazione delle costanti specifiche
     */
    private void regolaCostantiBase() {
        /* Ordinata assoluta di inizio della stampa rispetto all'area stampabile */
        Y_INIZIO_STAMPA = 0;

        /* Ascissa assoluta di inizio della stampa */
        X_INIZIO_STAMPA = 0;

        /* Distanza verticale inizio area intestazione
         * rispetto all'inizio stampa */
        Y_AREA_INTESTAZIONE = 20;

        /* Distanza verticale inizio area dettaglio
         * rispetto all'inizio stampa */
        Y_AREA_DETTAGLIO = 50;

        /* distanza verticale inizio filetti colonne
         * rispetto all'inizio dell'area dettaglio */
        Y_FILO_COLONNA = -10;

        /* distanza in discesa verticale dall'inizio del filetto colonna
         * fino all'inizio della stringa del titolo colonna
         * (quanto i titoli rientrano nelle colonne)*/
        RIENTRO_TITOLI_IN_COLONNA = 50;

        /* rientro fisso per tutte righe di dettaglio
         * rispetto all'inizio area dettaglio */
        RIENTRO_RIGA_DETTAGLIO = 20;

        /* avanzamento verticale penna per ogni riga dettaglio */
        STEP_RIGA_DETTAGLIO = 14;

        /* rientro per i titoli categoria
         * rispetto all'inizio dell'area dettaglio */
        X_RIGA_CATEGORIA = 0;

        /* avanzamento verticale penna per righe categoria */
        STEP_RIGA_CATEGORIA = 30;

        /* avanzamento verticale penna per righe tipo */
        STEP_RIGA_TIPO = 18;

        /* distanza tra la linea di base della riga di dettaglio
         * e il filetto sottostante */
        DISTANZA_FILETTO_RIGA = 4;

        /* margine aggiuntivo della colonna dei nomi dei tavoli
         * (oltre alla lunghezza della riga piu' lunga, viene aggiunto questo spazio) */
        SPAZIO_AGGIUNTIVO_COLONNA_RIGHE = 10;

        /* flag - ripete la colonna con i nomi dei piatti su tutte le pagine
         * anziche' disegnarla solo sulla prima pagina */
        RIPETI_PIATTI = false;

        /* dimensione e stile font per il titolo della stampa */
        FONT_TITOLO_DIM = 14;
        FONT_TITOLO_STILE = Font.BOLD;

        /* dimensione e stile font per le righe Dettaglio */
        FONT_DETTAGLIO_DIM = 10;
        FONT_DETTAGLIO_STILE = Font.PLAIN;

        /* dimensione e stile font per le righe Categoria (Antipasti, Primi...)*/
        FONT_CATEGORIA_DIM = 12;
        FONT_CATEGORIA_STILE = Font.BOLD;

        /* dimensione e stile font per le righe Tipo (da Menu, Extra...) */
        FONT_TIPO_DIM = 10;
        FONT_TIPO_STILE = Font.BOLD;

        /* dimensione e stile font per i nomi dei Tavoli (in verticale)*/
        FONT_TAVOLO_DIM = 10;
        FONT_TAVOLO_STILE = Font.BOLD;

        /* dimensione e stile font per la colonna totali dei piatti */
        FONT_TOTALI_DIM = 10;
        FONT_TOTALI_STILE = Font.BOLD;

        /* dimensione e stile font per la colonna totali F.O. dei piatti */
        FONT_TOTALI_FO_DIM = 10;
        FONT_TOTALI_FO_STILE = Font.PLAIN;

        /* dimensione e stile font per le quantita' ordinate per tavolo/piatto*/
        FONT_QUANTITA_DIM = 10;
        FONT_QUANTITA_STILE = Font.PLAIN;

        /* dimensione e stile font per i totali delle quantita' ordinate per piatto */
        FONT_QUANTITA_TOT_DIM = 10;
        FONT_QUANTITA_TOT_STILE = Font.BOLD;

        /* dimensione e stile font per i totali delle quantita' fuori orario
         * ordinate per piatto */
        FONT_QUANTITA_TOT_FO_DIM = 10;
        FONT_QUANTITA_TOT_FO_STILE = Font.PLAIN;

        /* testo per il titolo della colonna Totali */
        TESTO_TITOLO_TOTALI = "Totali";

        /* testo per il titolo della colonna Totali Fuori Orario*/
        TESTO_TITOLO_TOTALI_FO = "Di cui F.O.";

        /* larghezza delle colonne Tavoli */
        LARGHEZZA_COLONNA_TAVOLO = 20;

        /* larghezza della colonna Totali */
        LARGHEZZA_COLONNA_TOTALI = 30;

        /* dimensione e stile font per i testi arrivi previsti/coperti mancanti */
        FONT_AC_DIM = 12;
        FONT_AC_STILE = Font.PLAIN;

    } // fine del metodo


    /**
     * Regolazione delle costanti specifiche della classe.
     * <p/>
     * Regola le costanti specifiche rispetto al tipo di stampa.<br>
     * Regolo solo le costanti che voglio diverse da quelle di base.
     */
    private void regolaCostantiSpecifiche() {
        try {    // prova ad eseguire il codice
            switch (this.tipoStampa) {
                case Menu.STAMPA_SERVIZIO:
                    break;

                case Menu.STAMPA_CUCINA:

                    /* avanzamento verticale penna per ogni riga dettaglio */
                    STEP_RIGA_DETTAGLIO = 20;

                    /* dimensione font per le righe Dettaglio */
                    FONT_DETTAGLIO_DIM = 12;

                    /* avanzamento verticale penna per righe categoria */
                    STEP_RIGA_CATEGORIA = 45;

                    /* avanzamento verticale penna per righe tipo */
                    STEP_RIGA_TIPO = 30;

                    /* distanza tra la linea di base della riga di dettaglio
                     * e il filetto sottostante */
                    DISTANZA_FILETTO_RIGA = 6;

                    /* dimensione font per la colonna totali dei piatti */
                    FONT_TOTALI_DIM = 12;

                    /* dimensione font per la colonna totali F.O. dei piatti */
                    FONT_TOTALI_FO_DIM = 12;

                    /* dimensione font per i totali delle quantita' ordinate per piatto */
                    FONT_QUANTITA_TOT_DIM = 12;

                    /* dimensione font per i totali delle quantita' fuori orario
                     * ordinate per piatto */
                    FONT_QUANTITA_TOT_FO_DIM = 12;

                    /* larghezza della colonna Totali */
                    LARGHEZZA_COLONNA_TOTALI = 40;

                    /* distanza in discesa verticale dall'inizio del filetto colonna
                     * fino all'inizio della stringa del titolo colonna
                     * (quanto i titoli rientrano nelle colonne)*/
                    RIENTRO_TITOLI_IN_COLONNA = 80;

                    break;

                default: // caso non definito
                    throw new Exception("Tipo di stampa non definito");
            } // fine del blocco switch
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo


    /**
     * Regola le variabili di lavoro della classe
     */
    private void regolaVariabili() {
        /* variabili e costanti locali di lavoro */
        Modulo unModulo;

        /* font per il titolo della stampa */
        fontTitoloStampa = FontFactory.creaPrinterFont(FONT_TITOLO_STILE, (float)FONT_TITOLO_DIM);
        /* font per le righe Dettaglio */
        fontRigheDettaglio = FontFactory.creaPrinterFont(FONT_DETTAGLIO_STILE,
                (float)FONT_DETTAGLIO_DIM);
        /* font per le righe Categoria */
        fontRigheCategoria = FontFactory.creaPrinterFont(FONT_CATEGORIA_STILE,
                (float)FONT_CATEGORIA_DIM);
        /* font per le righe Tipo */
        fontRigheTipo = FontFactory.creaPrinterFont(FONT_TIPO_STILE, (float)FONT_TIPO_DIM);
        /* font per i nomi Tavolo */
        fontTavolo = FontFactory.creaPrinterFont(FONT_TAVOLO_STILE, (float)FONT_TAVOLO_DIM);
        /* font per la colonna Totali */
        fontTotali = FontFactory.creaPrinterFont(FONT_TOTALI_STILE, (float)FONT_TOTALI_DIM);
        /* font per la colonna Totali F.O. */
        fontTotaliFO = FontFactory.creaPrinterFont(FONT_TOTALI_FO_STILE, (float)FONT_TOTALI_FO_DIM);
        /* font per le quantita' ordinate */
        fontQuantita = FontFactory.creaPrinterFont(FONT_QUANTITA_STILE, (float)FONT_QUANTITA_DIM);
        /* font per i totali delle le quantita' ordinate */
        fontQuantitaTotale = FontFactory.creaPrinterFont(FONT_QUANTITA_TOT_STILE,
                (float)FONT_QUANTITA_TOT_DIM);
        /* font per i totali delle le quantita' Fuori Orario ordinate */
        fontQuantitaTotaleFO = FontFactory.creaPrinterFont(FONT_QUANTITA_TOT_FO_STILE,
                (float)FONT_QUANTITA_TOT_FO_DIM);
        /* font per i testi arrivi previsti / coperti mancanti */
        fontAc = FontFactory.creaPrinterFont(FONT_AC_STILE, (float)FONT_AC_DIM);

        /* riferimento al campo descrizione categoria */
        unModulo = Progetto.getModulo(Ristorante.MODULO_CATEGORIA);
        campoDescrizioneCategoria = unModulo.getCampo(Categoria.CAMPO_ITALIANO);

        /* regolazione dell'elenco delle righe stampabili per
         * questa stampa */
        this.righeStampabili = this.getRigheStampabili();

        /* regolazione delle coordinate assolute di
         * inizio delle aree di stampa */
        yAreaIntestazione = Y_INIZIO_STAMPA + Y_AREA_INTESTAZIONE;
        xAreaIntestazione = X_INIZIO_STAMPA;
        yAreaDettaglio = Y_INIZIO_STAMPA + Y_AREA_DETTAGLIO;
        xAreaDettaglio = X_INIZIO_STAMPA;

        /* regolazione iniziale del flag stampa terminata */
        stampaTerminata = false;
        maxPagina=0;

    } /* fine del metodo */


    /**
     * Disegna la pagina correntemente richiesta
     * <p/>
     *
     * @param g l'oggetto grafico sul quale disegnare la pagina
     * @param unFormato il formato della pagina
     */
    private void disegnaPagina(Graphics2D g, PageFormat unFormato) {

        /* recupera il riferimento all'oggetto grafico da stampare */
        pagina = g;

        /* regola il paint in nero - devo farlo se poi
        voglio stampare con J2PrinterWorks */
        Paint p =  new Color(0,0,0);
        pagina.setPaint(p);


        /* recupera la larghezza della pagina */
        larghezzaPagina = (float)unFormato.getImageableWidth();

        /* recupera l'altezza della pagina */
        altezzaPagina = (float)unFormato.getImageableHeight();

        /* disegna il bounding box della pagina - PROVVISORIO */
        //g.drawRect(0,0,(int)larghezzaPagina, (int)altezzaPagina);

        /* disegna la parte generale */
        this.disegnaPaginaGenerale();

//        stampaTerminata = true;

        /* disegna la parte di dettaglio */
        this.disegnaPaginaDettaglio();

    } /* fine del metodo */


    /**
     * Disegna la parte relativa ai dati generali
     */
    private void disegnaPaginaGenerale() {
        /** variabili e costanti locali di lavoro */
        String unTesto = null;
        int x = 0;
        int y = 0;
        int x1 = 0;
        int x2 = 0;
        int copertiNonOrd = 0;

        try {

            /* costruisce il testo del titolo */
            unTesto = "Menu del ";
            unTesto += this.datiStampa.getDataMenu();
            unTesto += " - ";
            unTesto += this.datiStampa.getPastoMenu();

            /* regola il font per il titolo */
            pagina.setFont(fontTitoloStampa);

            /* determina le coordinate di inizio area intestazione*/
            x = xAreaIntestazione;
            y = yAreaIntestazione;

            /* disegna il testo */
            pagina.drawString(unTesto, x, y);

            /* per la stampa cucina, scrive in fondo alla pagina
             * il numero di arrivi previsti e di coperti mancanti */
            if (this.tipoStampa == Menu.STAMPA_CUCINA) {

                /* recupera il numero di coperti che devono ancora ordinare */
                copertiNonOrd = this.datiStampa.getQuantiCopertiNonOrdinati();
                unTesto = new Integer(copertiNonOrd).toString();

                /* determina le coordinate */
                x1 = x;
                x2 = x + 200;
                y = (int)altezzaPagina - 20;

                /* regola il font */
                pagina.setFont(fontAc);

                /* disegna il testo Arrivi Previsti */
                pagina.drawString("Arrivi previsti __________", x1, y);

                /* disegna il testo Coperti Mancanti (se ce ne sono) */
                if (copertiNonOrd > 0) {
                    pagina.drawString("Coperti che devono ancora ordinare: " + unTesto, x2, y);
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Disegna la parte di dettaglio per la pagina corrente
     */
    private void disegnaPaginaDettaglio() {

        /* disegna le righe orizzontali con i piatti */
        this.disegnaRighe();

        /* disegna le colonne dei totali */
        this.disegnaColonneTotali();

        /* disegna le colonne dei tavoli (solo stampa Servizio)*/
        if (this.isStampaServizio()) {
            this.disegnaColonneTavoli();
        } else {
            stampaTerminata = true;
        }// fine del blocco if-else

    } /* fine del metodo */


    /**
     * Disegna le righe di dettaglio per la pagina corrente
     */
    private void disegnaRighe() {
        /* variabili e costanti locali di lavoro */
        RigaDettaglio unaRiga = null;
        String unaDescrizione = null;
        boolean cambioTipo = false;
        int x = 0;
        boolean disegnaFondino = false;

        /* regola la posizione iniziale della penna */
        yPenna = yAreaDettaglio;

        /* regola l'ascissa di inizio per le righe di dettaglio */
        x = xAreaDettaglio + (int)RIENTRO_RIGA_DETTAGLIO;

        /* spazzola le righe di dettaglio stampabili */
        for (int k = 0; k < this.righeStampabili.size(); k++) {

            /* recupera i dati dalla riga */
            unaRiga = (RigaDettaglio)this.righeStampabili.get(k);
            categoriaCorrente = unaRiga.getCodiceCategoria();
            tipoCorrente = unaRiga.getTipoRiga();

            /* controlla se e' cambiata la categoria
             * ed eventualmente stampa l'intestazione per la nuova Categoria */
            if (this.isCambioCategoria()) {
                this.cambioCategoria();
            } /* fine del blocco if */

            /* controlla se e' cambiato il tipo
             * ed eventualmente stampa l'intestazione per il nuovo Tipo */
            if (this.isCambioTipo()) {
                this.cambioTipo();
                cambioTipo = true;
            } /* fine del blocco if */

            /* sposta la penna sulla prossima riga */
            yPenna = yPenna + STEP_RIGA_DETTAGLIO;

            /* se e' la prima riga dopo un cambio tipo,
             * disegna un filetto anche sopra, senza fondino */
            if (cambioTipo) {
                this.filoOrizzontale(yPenna + DISTANZA_FILETTO_RIGA - STEP_RIGA_DETTAGLIO, false);
                cambioTipo = false;
            } /* fine del blocco if */

            /* determina se deve disegnare il fondino per questo tipo di riga
             * il fondino viene disegnato per le righe di tipo Variante */
            disegnaFondino = false;
            if (tipoCorrente == RigaDettaglio.TIPO_VARIANTE) {
                disegnaFondino = true;
            }// fine del blocco if

            /* disegna un filetto sotto alla riga (con eventuale fondino) */
            this.filoOrizzontale(yPenna + DISTANZA_FILETTO_RIGA, disegnaFondino);

            /* eventualmente stampa la riga di dettaglio */
            if (this.devoStamparePiatti()) {
                unaDescrizione = unaRiga.getTestoRiga();
                pagina.setFont(fontRigheDettaglio);
                pagina.drawString(unaDescrizione, x, yPenna);
            } /* fine del blocco if */

            /* stampa le quantita' ordinate da ogni tavolo per questa riga */
            if (this.isStampaServizio()) {
                this.disegnaQuantitaRiga(unaRiga);
            }// fine del blocco if

            /* stampa le quantita' complessive ordinate per questa riga */
            if (this.isStampaCucina()) {
                this.disegnaQuantitaRigaCucina(unaRiga);
            }// fine del blocco if

            /* memorizza i dati chiave relativi alla riga precedentemente stampata */
            categoriaPrecedente = categoriaCorrente;
            tipoPrecedente = tipoCorrente;

        } /* fine del blocco for */

        /* al termine della stampa delle righe, memorizza
         * l'ordinata dell'ultimo filetto stampato */
        yUltimoFilettoRiga = (int)yPenna + (int)DISTANZA_FILETTO_RIGA;

    } /* fine del metodo */


    /**
     * Restituisce un elenco delle righe da stampare in
     * funzione del tipo di stampa.
     * <p/>
     * Spazzola le righe di dettaglio dell'oggetto dati ed estrae
     * le righe stampabili.<br>
     *
     * @return una lista di oggetti RigaDettaglio da stampare.
     */
    private ArrayList getRigheStampabili() {
        /* variabili e costanti locali di lavoro */
        ArrayList righeStampabili = null;
        ArrayList righeTotali = null;
        RigaDettaglio unaRiga = null;
        int tipoRiga = 0;
        int codiceModifica = 0;
        boolean includi = false;
        ModificaModulo moduloModifica = null;

        try {    // prova ad eseguire il codice

            /* crea la lista da ritornare */
            righeStampabili = new ArrayList();

            /* recupera tutte le righe dell'oggetto dati */
            righeTotali = this.datiStampa.getRigheDettaglio();

            switch (this.tipoStampa) {
                case Menu.STAMPA_SERVIZIO:
                    /* tutte le righe sono stampabili */
                    righeStampabili = righeTotali;
                    break;
                case Menu.STAMPA_CUCINA:

                    /* recupera il modulo Modifica */
                    moduloModifica = (ModificaModulo)Progetto.getModulo(Ristorante.MODULO_MODIFICA);

                    /* tiene tutte le righe, escluse le modifiche che
                     * non interessano la cucina */
                    for (int k = 0; k < righeTotali.size(); k++) {
                        includi = true;
                        unaRiga = (RigaDettaglio)righeTotali.get(k);
                        tipoRiga = unaRiga.getTipoRiga();

                        /* se la riga e' una modifica che non interessa la
                         * cucina, la esclude dalle righe stampabili */
                        if (tipoRiga == RigaDettaglio.TIPO_VARIANTE) {
                            /* recupera il codice della modifica */
                            codiceModifica = unaRiga.getCodiceModifica();
                            /* esclude se non interessa la cucina */
                            includi = moduloModifica.getLogica().isInteressaCucina(codiceModifica);
                        }// fine del blocco if

                        /* Se richiesto aggiunge la riga */
                        if (includi) {
                            righeStampabili.add(unaRiga);
                        }// fine del blocco if

                    } // fine del ciclo for

                    break;
                default: // caso non definito
                    throw new Exception("Tipo di stampa non definito");
            } // fine del blocco switch

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return righeStampabili;
    } // fine del metodo


    /**
     * Disegna la le quantita' ordinate dai tavoli per una data riga
     * per la pagina corrente, con i totali.
     * <p/>
     * il disegno viene eseguito alla ordinata di penna corrente (yPenna)
     *
     * @param unaRiga la RigaDettaglio per la quale stampare le quantita'
     */
    private void disegnaQuantitaRiga(RigaDettaglio unaRiga) {
        /** variabili e costanti locali di lavoro */
        DueInt coppiaIndici = null;
        int indicePrima = 0;
        int indiceUltima = 0;
        int xColonna = 0;
        int quantita = 0;
        boolean flagModifiche = false;

        /* recupera gli indici della prima e dell'ultima colonna tavoli
         * da stampare sulla pagina corrente */
        coppiaIndici = indiciColonne(numeroPagina);
        indicePrima = coppiaIndici.x;
        indiceUltima = coppiaIndici.y;

        /* determina l'ascissa della prima colonna tavoli per questa pagina*/
        xColonna = ascissaPrimaColonnaTavoli(numeroPagina);

        /* recupera le quantita' della riga per questa pagina
         * e le stampa nelle rispettive colonne */
        for (int k = indicePrima; k <= indiceUltima; k++) {

            /* recupera una singola quantita' e il corrispondente flag modifiche */
            quantita = unaRiga.getQuantitaTavolo(k);
            flagModifiche = unaRiga.isEsistonoModifiche(k);

            /* stampa nella colonna */
            stampaQuantitaColonna(quantita,
                    flagModifiche,
                    fontQuantita,
                    (int)yPenna,
                    xColonna,
                    LARGHEZZA_COLONNA_TAVOLO);

            /* incrementa l'ascissa della colonna per il prossimo tavolo */
            xColonna = xColonna + LARGHEZZA_COLONNA_TAVOLO;

        } /* fine del blocco for */

        /* se per questa pagina e' prevista la stampa dei totali,
           e se non si tratta di una riga di variante,
           calcola e stampa il totale della riga */
        if (devoStampareTotali()) {
            if (unaRiga.getTipoRiga() != RigaDettaglio.TIPO_VARIANTE) {
                stampaQuantitaColonna(unaRiga.getQuantitaTotale(),
                        false,
                        fontQuantitaTotale,
                        (int)yPenna,
                        this.ascissaColonnaTotali(),
                        LARGHEZZA_COLONNA_TOTALI);
            } /* fine del blocco if */
        } /* fine del blocco if */

    } /* fine del metodo */


    /**
     * Disegna i totali per la cucina delle quantita' ordinate
     * per una data riga per la pagina corrente.
     * <p/>
     * il disegno viene eseguito alla ordinata di penna corrente (yPenna)
     *
     * @param unaRiga la RigaDettaglio per la quale stampare i totali
     */
    private void disegnaQuantitaRigaCucina(RigaDettaglio unaRiga) {
        /** variabili e costanti locali di lavoro */
        int quantitaTotale = 0;
        int quantitaFO = 0;
        boolean esistonoModifiche = false;
        boolean esistonoModificheFO = false;

        try {    // prova ad eseguire il codice

            /* recupera la qta totale per la riga */
            quantitaTotale = unaRiga.getQuantitaTotale();

            /* recupera il flag indicatore di presenza modifiche cucina */
            esistonoModifiche = unaRiga.isEsistonoModificheCucina();

            /* stampa la quantita' totale della riga */
            stampaQuantitaColonna(quantitaTotale,
                    esistonoModifiche,
                    fontQuantitaTotale,
                    (int)yPenna,
                    this.ascissaColonnaTotali(),
                    LARGHEZZA_COLONNA_TOTALI);

            /* recupera la qta fuori orario per la riga */
            quantitaFO = unaRiga.getQuantitaTotaleFuoriOrario();

            /* recupera il flag indicatore di presenza modifiche FO cucina */
            esistonoModificheFO = unaRiga.isEsistonoModificheFOCucina();

            /* stampa la quantita' fuori orario della riga */
            stampaQuantitaColonna(quantitaFO,
                    esistonoModificheFO,
                    fontQuantitaTotaleFO,
                    (int)yPenna,
                    this.ascissaColonnaTotali() + LARGHEZZA_COLONNA_TOTALI,
                    LARGHEZZA_COLONNA_TOTALI);


        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Stampa una quantita' centrata in una colonna
     * <p/>
     *
     * @param q la quantita' da stampare
     * @param b true per stampare il richiamo alle modifiche
     * @param f il font da utilizzare
     * @param y l'ordinata alla quale stampare
     * @param x l'ascissa iniziale della colonna
     * @param w la larghezza della colonna
     */
    private void stampaQuantitaColonna(int q, boolean b, Font f, int y, int x, int w) {
        /* variabili e costanti locali di lavoro */
        String stringaQuantita = null;
        int lunghezzaTesto = 0;
        int xTesto = 0;

        /* genera un testo da stampare per la quantita' */
        stringaQuantita = stringaQuantita(q);

        /* aggiunge l'eventuale richiamo alle modifiche */
        if (b) {
            stringaQuantita = stringaQuantita + "*";
        } /* fine del blocco if */

        /* imposta il font da utilizzare */
        pagina.setFont(f);

        /* determina la lunghezza del testo da stampare */
        lunghezzaTesto = Lib.Fonte.getLarPixel(stringaQuantita, f);

        /* determina l'ascissa alla quale stampare il totale */
        xTesto = x + (int)(w / 2) - (int)(lunghezzaTesto / 2);

        /* disegna il testo */
        pagina.drawString(stringaQuantita, xTesto, y);

    } /* fine del metodo */


    /**
     * Disegna le colonne dei totali per la pagina corrente
     */
    private void disegnaColonneTotali() {
        /** variabili e costanti locali di lavoro */
        int xColonna = 0;
        int yColonna = 0;
        int hColonna = 0;
        int xTitolo = 0;
        int yTitolo = 0;
        int hTitolo = 0;

        /* esegue solo se e' prevista la stampa dei
         * totali per la pag. corrente */
        if (devoStampareTotali()) {

            /* determina l'ascissa della colonna totali generali */
            xColonna = this.ascissaColonnaTotali();

            /* determina l'ordinata di partenza della colonna */
            yColonna = yAreaDettaglio + (int)Y_FILO_COLONNA;

            /* determina l'altezza della colonna */
            hColonna = yUltimoFilettoRiga - yColonna;

            /* traccia il filo verticale */
            filoVerticale(xColonna, yColonna, hColonna);

            /* determina l'altezza del font del titolo colonna
             * (solo ascendente) */
            hTitolo = Lib.Fonte.ascendenteStandard(fontTotali);

            /* determina la posizione della baseline del testo */
            xTitolo = xColonna;
            xTitolo = xTitolo + (int)(LARGHEZZA_COLONNA_TOTALI / 2);
            xTitolo = xTitolo + (int)(hTitolo / 2);

            /* determina l'ordinata di partenza per il testo */
            yTitolo = yColonna + (int)RIENTRO_TITOLI_IN_COLONNA;

            /* stampa il testo in verticale */
            pagina.setFont(fontTotali);
            this.testoVerticale(TESTO_TITOLO_TOTALI, xTitolo, yTitolo);

            /* Se si tratta della stampa cucina, disegna
             * anche la colonna per i totali F.O. */
            if (this.isStampaCucina()) {
                /* traccia il filo verticale prima della colonna */
                xColonna += LARGHEZZA_COLONNA_TOTALI;
                filoVerticale(xColonna, yColonna, hColonna);

                /* stampa il testo in verticale */
                pagina.setFont(fontTotaliFO);
                this.testoVerticale(TESTO_TITOLO_TOTALI_FO,
                        xTitolo + LARGHEZZA_COLONNA_TOTALI,
                        yTitolo);

                /* traccia il filo verticale dopo la colonna */
                xColonna += LARGHEZZA_COLONNA_TOTALI;
                filoVerticale(xColonna, yColonna, hColonna);
            }// fine del blocco if

        }// fine del blocco if

    } /* fine del metodo */


    /**
     * Disegna le colonne dei tavoli per la pagina corrente
     */
    private void disegnaColonneTavoli() {
        /** variabili e costanti locali di lavoro */
        DueInt coppiaIndici = null;
        int indicePrima = 0;
        int indiceUltima = 0;
        int xColonna = 0;
        int y1 = 0;
        int lunghezzaFilo = 0;
        int yTavolo = 0;
        int xTavolo = 0;
        String nomeTavolo = null;
        int hTavolo = 0;

        /* recupera gli indici della prima e dell'ultima colonna da stampare
         * sulla pagina corrente */
        coppiaIndici = indiciColonne(numeroPagina);
        indicePrima = coppiaIndici.x;
        indiceUltima = coppiaIndici.y;

        /* se l'indice dell'ultima colonna e' uguale (o superiore)
         * al numero di tavoli, allora la stampa e' terminata. */
        if (indiceUltima >= this.datiStampa.getNumeroTavoli() - 1) {
            stampaTerminata = true;
        }// fine del blocco if

        /* determina l'ascissa della prima colonna per questa pagina*/
        xColonna = ascissaPrimaColonnaTavoli(numeroPagina);

        /* determina l'ordinata di partenza del filetto */
        y1 = yAreaDettaglio + (int)Y_FILO_COLONNA;

        /* determina la lunghezza del filetto */
        lunghezzaFilo = yUltimoFilettoRiga - y1;

        /* determina l'ordinata di partenza per il nome del tavolo */
        yTavolo = y1 + (int)RIENTRO_TITOLI_IN_COLONNA;

        /* determina l'altezza del font del tavolo
         * (solo ascendente perche' generalmente sono numeri) */
        hTavolo = Lib.Fonte.ascendenteStandard(fontTavolo);

        /* spazzola le colonne di questa pagina */
        for (int k = indicePrima; k <= indiceUltima; k++) {

            /* traccia il filo verticale */
            filoVerticale(xColonna, y1, lunghezzaFilo);

            /* determina la posizione della baseline del testo tavolo */
            xTavolo = xColonna;
            xTavolo = xTavolo + (int)(LARGHEZZA_COLONNA_TAVOLO / 2);
            xTavolo = xTavolo + (int)(hTavolo / 2);

            /* stampa il nome del tavolo in verticale */
            nomeTavolo = this.getStringaNumeroTavolo(k);
            pagina.setFont(fontTavolo);
            this.testoVerticale(nomeTavolo, xTavolo, yTavolo);

            /* incrementa l'ascissa della colonna */
            xColonna = xColonna + LARGHEZZA_COLONNA_TAVOLO;

            /* se e' l'iltima colonna, disegna il filetto finale */
            if (k == indiceUltima) {
                filoVerticale(xColonna, y1, lunghezzaFilo);
            } /* fine del blocco if */

        } /* fine del blocco for */

    } /* fine del metodo */


    /**
     * Determina lindice della prima e dell'ultima colonna tavoli
     * da stampare su una data pagina
     * <p/>
     *
     * @param indicePagina il numero della pagina (0 per la prima)
     *
     * @return un oggetto DueInt contenente l'indice della prima e
     *         dell'ultima colonna da stampare
     */
    private DueInt indiciColonne(int indicePagina) {
        /* variabili e costanti locali di lavoro */
        DueInt unRitorno = null;
        int colonnePaginePrecedenti = 0;
        int colonneQuestaPagina = 0;
        int primaColonna = 0;
        int ultimaColonna = 0;

        /* regolazioni */
        unRitorno = new DueInt();

        /* spazzola tutte le pagine dalla prima (0) fino a quella
         * precedente a quella richiesta
         * e determina il numero totale di colonne
         * contenute nelle pagine precedenti */
        for (int k = 0; k <= indicePagina - 1; k++) {
            colonnePaginePrecedenti = colonnePaginePrecedenti + colonneStampabili(k);
        } /* fine del blocco for */

        /* determina l'indice della prima colonna di questa pagina */
        primaColonna = colonnePaginePrecedenti;

        /* determina il numero di colonne stampabili su questa pagina */
        colonneQuestaPagina = colonneStampabili(indicePagina);

        /* determina l'indice dell'ultima colonna di questa pagina */
        ultimaColonna = primaColonna + colonneQuestaPagina;

        /* limita al numero massimo di tavoli */
        if (ultimaColonna > this.datiStampa.getNumeroTavoli()) {
            ultimaColonna = this.datiStampa.getNumeroTavoli();
        } /* fine del blocco if */

        /* allinea all'indice degli array (partono da zero)*/
        ultimaColonna = ultimaColonna - 1;

        /* prepara il valore di ritorno */
        unRitorno.x = primaColonna;
        unRitorno.y = ultimaColonna;

        /* valore di ritorno */
        return unRitorno;
    } /* fine del metodo */


    /**
     * Determina il numero di colonne stampabili su una data pagina
     * <p/>
     *
     * @param indicePagina il numero della pagina (0 per la prima)
     *
     * @return un il numero di colonne stampabili sulla pagina
     */
    private int colonneStampabili(int indicePagina) {
        /* variabili e costanti locali di lavoro */
        int numeroColonne = 0;
        int spazioColonne = 0;
        float unFloat = 0;

        /* recupera lo spazio disponibile per le colonne sulla pagina */
        spazioColonne = spazioColonneTavoli(indicePagina);

        /* determina il numero di colonne stampabili nello spazio disponibile */
        unFloat = spazioColonne / LARGHEZZA_COLONNA_TAVOLO;
        numeroColonne = (int)Math.abs(unFloat);

        /* valore di ritorno */
        return numeroColonne;

    } /* fine del metodo */


    /**
     * Determina lo spazio disponibile per le colonne
     * dei tavoli per una data pagina
     * <p/>
     *
     * @param indicePagina il numero della pagina (0 per la prima)
     *
     * @return lo spazio disponibile sulla pagina
     */
    private int spazioColonneTavoli(int indicePagina) {
        /* variabili e costanti locali di lavoro */
        int spazioDisponibile = 0;
        int xColonna = 0;

        /* recupera lo spazio totale disponibile sulla pagina */
        spazioDisponibile = (int)larghezzaPagina;

        /* recupera l'ascissa della prima colonna su questa pagina */
        xColonna = ascissaPrimaColonnaTavoli(indicePagina);

        /* calcola lo spazio rimanente disponibile */
        spazioDisponibile = spazioDisponibile - xColonna;

        /* valore di ritorno */
        return spazioDisponibile;
    } /* fine del metodo */


    /**
     * Determina l'ascissa di partenza della prima colonna tavoli
     * per una data pagina
     * <p/>
     *
     * @param indicePagina il numero della pagina (0 per la prima)
     *
     * @return l'ascissa (x) di partenza della prima colonna tavoli
     */
    private int ascissaPrimaColonnaTavoli(int indicePagina) {
        /* variabili e costanti locali di lavoro */
        int unaPosizione = 0;

        /*
         * - Se e' la prima pagina, viene collocata dopo la colonna dei totali
         * - Se non e' la prima pagina:
         *   - se sulla pagina deve stampare le righe, si parte dalla riga
         *     di piatto piu' lunga piu' il margine sx delle righe
         *     piu' il margine fisso
         *   - se sulla pagina non deve stampare le righe, si parte da zero
         */
        if (indicePagina == 0) {    // e' la prima pagina
            /* l'ascissa della colonna totali piu' la larghezza
             * della colonna totali */
            unaPosizione = unaPosizione + ascissaColonnaTotali();
            unaPosizione = unaPosizione + LARGHEZZA_COLONNA_TOTALI;
        } else {    // non e' la prima pagina

            if (this.devoStamparePiatti()) {
                unaPosizione = xAreaDettaglio;
                unaPosizione = unaPosizione + (int)RIENTRO_RIGA_DETTAGLIO;
                unaPosizione = unaPosizione + maxLunghezzaRiga();
                unaPosizione = unaPosizione + SPAZIO_AGGIUNTIVO_COLONNA_RIGHE;
            } else {    // non devo stampare la colonna dei piatti
                unaPosizione = 0;
            } /* fine del blocco if-else */

        } /* fine del blocco if-else */

        /* valore di ritorno */
        return unaPosizione;
    } /* fine del metodo */


    /**
     * Determina l'ascissa della colonna totali
     * per la pagina corrente
     */
    private int ascissaColonnaTotali() {
        /* variabili e costanti locali di lavoro */
        int unaPosizione = 0;

        /* Nota: I totali si stampano solo sulla prima pagina */

        /* il margine sx delle righe dettaglio
         * piu' la lunghezza della riga piu' lunga
         * piu' il margine fisso dopo la colonna piatti */
        unaPosizione = xAreaDettaglio;
        unaPosizione = unaPosizione + (int)RIENTRO_RIGA_DETTAGLIO;
        unaPosizione = unaPosizione + this.maxLunghezzaRiga();
        unaPosizione = unaPosizione + SPAZIO_AGGIUNTIVO_COLONNA_RIGHE;

        /** valore di ritorno */
        return unaPosizione;
    } /* fine del metodo */


    /**
     * Determina la lunghezza della riga di dettaglio piu' lunga.
     * <p/>
     *
     * @return la lunghezza della riga piu' lunga
     */
    private int maxLunghezzaRiga() {
        /* variabili e costanti locali di lavoro */
        int lunghezzaMassima = 0;
        int lunghezzaRiga = 0;
        RigaDettaglio unaRiga = null;
        String unTesto = null;

        /* spazzola l'elenco delle righe e determina la
         * lunghezza massima nel font di stampa */
        for (int k = 0; k < this.righeStampabili.size(); k++) {
            unaRiga = (RigaDettaglio)righeStampabili.get(k);
            unTesto = unaRiga.getTestoRiga();
            lunghezzaRiga = Lib.Fonte.getLarPixel(unTesto, fontRigheDettaglio);

            if (lunghezzaRiga > lunghezzaMassima) {
                lunghezzaMassima = lunghezzaRiga;
            } /* fine del blocco if */

        } /* fine del blocco for */

        /* valore di ritorno */
        return lunghezzaMassima;
    } /* fine del metodo */


    /**
     * Gestisce il cambio di categoria del piatto mentre
     * si disegnano le righe di dettaglio
     */
    private void cambioCategoria() {
        /* variabili e costanti locali di lavoro */
        String unaStringa = "";
        int x = 0;

        /* avanza la penna */
        yPenna = yPenna + STEP_RIGA_CATEGORIA;

        /* determina l'ascissa di stampa */
        x = xAreaDettaglio + (int)X_RIGA_CATEGORIA;

        /* eventualmente disegna la riga di testo */
        if (this.devoStamparePiatti()) {
            unaStringa = this.stringaCategoriaCorrente();
            pagina.setFont(fontRigheCategoria);
            pagina.drawString(unaStringa, x, yPenna);
        } /* fine del blocco if */

        /* essendo cambiata la categoria, forza un cambio di tipo
         * (altrimenti se il tipo resta uguale non rileva il cambio di tipo) */
        tipoPrecedente = 0;

    } /* fine del metodo */


    /**
     * Gestisce il cambio di tipo (menu, extra, variante) mentre
     * si disegnano le righe di dettaglio
     */
    private void cambioTipo() {
        /* variabili e costanti locali di lavoro */
        String unaStringa = "";
        int x = 0;

        /* avanza la penna */
        yPenna = yPenna + STEP_RIGA_TIPO;

        /* determina l'ascissa di stampa */
        x = xAreaDettaglio + (int)RIENTRO_RIGA_DETTAGLIO;

        /* eventualmente disegna la riga di testo */
        if (this.devoStamparePiatti()) {
            unaStringa = this.stringaTipoCorrente();
            pagina.setFont(fontRigheTipo);
            pagina.drawString(unaStringa, x, yPenna);
        } /* fine del blocco if */

    } /* fine del metodo */


    /**
     * Determina se la categoria e' cambiata rispetto
     * all'ultima riga dettaglio stampata.
     * <p/>
     *
     * @return true se la categoria e' cambiata
     */
    private boolean isCambioCategoria() {
        /* variabili e costanti locali di lavoro */
        boolean cambio = false;

        /* confronta il valore corrente con il valore precedente */
        if (categoriaCorrente != categoriaPrecedente) {
            cambio = true;
        } /* fine del blocco if */

        /* valore di ritorno */
        return cambio;
    } /* fine del metodo */


    /**
     * Determina se il tipo di riga e' cambiato rispetto
     * all'ultima riga dettaglio stampata.
     * <p/>
     *
     * @return true se il tipo di riga e' cambiato
     */
    private boolean isCambioTipo() {
        /* variabili e costanti locali di lavoro */
        boolean cambio = false;

        /* confronta il valore corrente con il valore precedente */
        if (tipoCorrente != tipoPrecedente) {
            cambio = true;
        } /* fine del blocco if */

        /* valore di ritorno */
        return cambio;
    } /* fine del metodo */


    /**
     * Recupera la stringa relativa alla categoria corrente.
     * <p/>
     *
     * @return una stringa con la descrizione della categoria
     */
    private String stringaCategoriaCorrente() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Modulo moduloCategoria = null;

        try { // prova ad eseguire il codice
            moduloCategoria = Progetto.getModulo(Categoria.NOME_MODULO);
            stringa = moduloCategoria.query().valoreStringa(campoDescrizioneCategoria,
                    categoriaCorrente);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    } /* fine del metodo */


    /**
     * Recupera la stringa relativa al tipo corrente.
     * <p/>
     *
     * @return una stringa con la descrizione del tipo
     */
    private String stringaTipoCorrente() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        /* recupera la stringa */
        stringa = RigaDettaglio.getDescrizioneTipo(tipoCorrente);

        /* valore di ritorno */
        return stringa;
    } /* fine del metodo */


    /**
     * Restituisce una stringa relativa a una quantita'.
     * <p/>
     * Se la quantita' e' diversa da null o zero
     * ritorna la stringa corrispondente,
     * altrimenti ritorna stringa vuota<br>
     *
     * @param quantita la quantita' Integer da convertire in stringa
     *
     * @return la stringa relativa alla quantita'
     */
    private String stringaQuantita(Integer quantita) {
        /* variabili e costanti locali di lavoro */
        String unaStringa = "";

        /* Controllo di congruita' */
        if ((quantita != null) && (quantita.intValue() != 0)) {
            unaStringa = quantita.toString();
        } /* fine del blocco if */

        /* valore di ritorno */
        return unaStringa;
    } /* fine del metodo */


    /**
     * Restituisce una stringa relativa a una quantita'.
     * <p/>
     *
     * @param quantita la quantita' int da convertire in stringa
     *
     * @return la stringa relativa alla quantita'
     */
    private String stringaQuantita(int quantita) {
        /* valore di ritorno */
        return stringaQuantita(new Integer(quantita));
    } /* fine del metodo */


    /**
     * Recupera il numero di un tavolo dell'elenco dei tavoli.
     * <p/>
     *
     * @param posizione la posizione nell'elenco dei tavoli
     *
     * @return il numero del tavolo (null se non trovato)
     */
    private Integer getNumeroTavolo(int posizione) {
        /* variabili e costanti locali di lavoro */
        Integer numeroTavolo = null;
        Integer codiceTavolo = null;
        Modulo moduloTavolo = null;

        try { // prova ad eseguire il codice
            moduloTavolo = Progetto.getModulo(Tavolo.NOME_MODULO);
            if (moduloTavolo != null) {

                /* recupera il codice del tavolo */
                codiceTavolo = (Integer)this.datiStampa.getTavoli().get(posizione);

                /* recupera il numero del tavolo */
                if (codiceTavolo != null) {
                    numeroTavolo = (Integer)moduloTavolo.query().valoreCampo(Tavolo.Cam.numtavolo.get(),
                            codiceTavolo.intValue());
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return numeroTavolo;
    } /* fine del metodo */


    /**
     * Recupera la stringa rappresentante il numero di un tavolo
     * dell'elenco dei tavoli.
     * <p/>
     *
     * @param posizione la posizione nell'elenco dei tavoli
     *
     * @return il numero del tavolo
     */
    private String getStringaNumeroTavolo(int posizione) {
        /* variabili e costanti locali di lavoro */
        Integer numeroTavolo = null;
        String stringaTavolo = "";

        /* recupera il numero del tavolo */
        numeroTavolo = this.getNumeroTavolo(posizione);

        /* controllo di congruita' */
        if (numeroTavolo != null) {
            stringaTavolo = numeroTavolo.toString();
        }// fine del blocco if

        /* valore di ritorno */
        return stringaTavolo;
    } /* fine del metodo */


    /**
     * Disegna un filetto orizzontale con eventuale fondino sulla pagina.
     * <p/>
     *
     * @param y l'ordinata alla quale disegnare
     * @param fondino true per disegnare un fondino grigio
     */
    private void filoOrizzontale(float y, boolean fondino) {
        /* variabili e costanti locali di lavoro */
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;

        try {    // prova ad eseguire il codice

            /* regolazione */
            y1 = (int)y;
            y2 = y1;

            switch (this.tipoStampa) {

                case Menu.STAMPA_SERVIZIO:

                    /* determina l'ascissa iniziale del filetto orizzontale */
                    if (this.devoStamparePiatti()) {
                        /* regola l'ascissa di inizio per le righe di dettaglio */
                        x1 = this.xAreaDettaglio + (int)RIENTRO_RIGA_DETTAGLIO;
                    } else {
                        x1 = ascissaPrimaColonnaTavoli(numeroPagina);
                    }// fine del blocco if-else

                    /* determina l'ascissa finale del filetto */
                    x2 = (int)larghezzaPagina;

                    break;

                case Menu.STAMPA_CUCINA:
                    /* determina l'ascissa iniziale del filetto orizzontale */
                    x1 = this.xAreaDettaglio + (int)RIENTRO_RIGA_DETTAGLIO;
                    /* determina l'ascissa finale del filetto */
                    x2 = this.ascissaColonnaTotali() + (LARGHEZZA_COLONNA_TOTALI * 2);

                    break;

                default: // caso non definito
                    throw new Exception("Tipo di stampa non definito");
            } // fine del blocco switch
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* disegna il filetto */
        pagina.drawLine(x1, y1, x2, y2);

        /* disegna un eventuale fondino sopra al filetto */
        if (fondino) {
            /* determina l'altezza del fondino */
            BasicStroke unoStroke = (BasicStroke)pagina.getStroke(); // Stroke
            float hPenna = unoStroke.getLineWidth();    // spessore penna
            float h = STEP_RIGA_DETTAGLIO - hPenna; // altezza fondino float
            int hf = (int)h;    // altezza fondino int
            Color c = pagina.getColor();  // colore originale
            pagina.setPaint(Color.LIGHT_GRAY); // set colore per fondino
            pagina.fillRect(x1, y1 - hf, x2 - x1, hf);  // disegna fondino
            pagina.setColor(c); // ripristina colore originale
        }// fine del blocco if

    } /* fine del metodo */


    /**
     * Disegna un filetto verticale sulla pagina partendo
     * da un punto dato e di una data lunghezza,
     * dall'alto verso il basso.
     * <p/>
     *
     * @param x l'ascissa del punto di partenza
     * @param y l'ordinata del punto di partenza
     * @param l la lunghezza del filetto
     */
    private void filoVerticale(int x, int y, int l) {
        /* variabili e costanti locali di lavoro */
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;

        /* regolazione */
        x1 = x;
        y1 = y;
        x2 = x;
        y2 = y1 + l;

        /* disegna il filetto */
        pagina.drawLine(x1, y1, x2, y2);

    } /* fine del metodo */


    /**
     * Disegna un testo in verticale
     * ruotato a sinistra.
     * <p/>
     *
     * @param unTesto il testo da disegnare
     * @param x l'ascissa di partenza
     * @param y l'ordinata di partenza
     */
    private void testoVerticale(String unTesto, int x, int y) {
        /* variabili e costanti locali di lavoro */
        AffineTransform trasformazioneCorrente = null;
        int px = 0;
        int py = 0;

        /* salva la trasformazione corrente */
        trasformazioneCorrente = pagina.getTransform();

        /* ruota di 90 gradi a sinistra */
        pagina.rotate(Math.toRadians(-90));

        /* scambia la coordinata y
         * per mantenere gli stessi riferimenti
         * al sistema di coordinate */
        px = -y;
        py = x;

        /* disegna il testo */
        pagina.drawString(unTesto, px, py);

        /* ripristina la trasformazione corrente */
        pagina.setTransform(trasformazioneCorrente);

    } /* fine del metodo */


    /**
     * Decide se va stampata la colonna dei piatti
     * sulla pagina corrente.
     * <p/>
     *
     * @return true se la colonna dei piatti va stampata
     */
    private boolean devoStamparePiatti() {
        /* variabili e costanti locali di lavoro */
        boolean devoStampare = false;

        /* sulla prima pagina, deve stampare  sempre i piatti
         * sulle altre pagine, li deve stampare solo se l'apposito
         * flag e' acceso*/
        if (numeroPagina == 0) {
            // sulla prima pagina, stampa sempre i piatti
            devoStampare = true;
        } else {
            // sulle altre pagine, li stampa solo se l'apposito flag e' acceso
            if (RIPETI_PIATTI) {
                devoStampare = true;
            } /* fine del blocco if */
        } /* fine del blocco if-else */

        /* valore di ritorno */
        return devoStampare;
    } /* fine del metodo */


    /**
     * Decide se va stampata la colonna dei totali
     * sulla pagina corrente.
     * <p/>
     *
     * @return true se la colonna dei totali va stampata
     */
    private boolean devoStampareTotali() {
        /* variabili e costanti locali di lavoro */
        boolean devoStampare = false;

        /* sulla prima pagina, deve stampare sempre i totali
         * sulle altre pagine, non li deve mai stampare */
        if (numeroPagina == 0) {
            devoStampare = true;
        } /* fine del blocco if-else */

        /* valore di ritorno */
        return devoStampare;
    } /* fine del metodo */


    /**
     * Determina se la stampa corrente e' di tipo Servizio.
     * <p/>
     *
     * @return true se e' di tipo Servizio
     */
    private boolean isStampaServizio() {
        return this.tipoStampa == Menu.STAMPA_SERVIZIO;
    } // fine del metodo


    /**
     * Determina se la stampa corrente e' di tipo Cucina.
     * <p/>
     *
     * @return true se e' di tipo Cucina
     */
    private boolean isStampaCucina() {
        return this.tipoStampa == Menu.STAMPA_CUCINA;
    } // fine del metodo


    /**
     * Regola il flag per la ripetizione della colonna piatti su tutte
     * le pagine.
     * <p/>
     *
     * @param flag true per ripetere la colonna piatti su tutte le pagine,
     * false per stampare la colonna piatti solo sula prima pagina
     */
    public void setRipetiPiatti(boolean flag) {
        RIPETI_PIATTI = flag;
    }


    /**
     * Stampa di una pagina.
     * <p/>
     * (metodo invocato direttamente dal sottosistema di stampa)
     *
     * @param g l'oggetto grafico nel quale disegnare
     * @param pf il formato della pagina che verra' stampata
     * @param pageIndex il numero di pagina da stampare (0 per la prima)
     *
     * @return PAGE_EXISTS se la pagina e' stata costruita correttamente,
     *         o NO_SUCH_PAGE se la pagina richiesta non esiste o non
     *         puo' essere costruita
     */
    public int print(Graphics g, PageFormat pf, int pageIndex) {

        /* - se il flag stampaTerminata non a' ancora stato
     * acceso, procede alla costruzione della pagina
     * richiesta e ritorna PAGE_EXISTS
     * - se il flag e' stato acceso, risponde solo alle
     * richieste per una pagina non superiore a quella corrente.
     * se viene richiesta una pagina superiore alla corrente,
     * ritorna NO_SUCH_PAGE */
        if (stampaTerminata) {
            if (pageIndex > numeroPagina) {
                return NO_SUCH_PAGE;
            } else {
                stampaPagina(g, pf, pageIndex);
                return PAGE_EXISTS;
            }// fine del blocco if-else
        } else {
            stampaPagina(g, pf, pageIndex);
            return PAGE_EXISTS;
        }// fine del blocco if-else

    } /* fine del metodo */


    /**
     * Esegue la costruzione della pagina richiesta.
     * <p/>
     *
     * @param g l'oggetto grafico nel quale disegnare
     * @param pf il formato della pagina che verra' stampata
     * @param pageIndex il numero di pagina da stampare (0 per la prima)
     */
    private void stampaPagina(Graphics g, PageFormat pf, int pageIndex) {

        /* variabili e costanti locali di lavoro */
        Graphics2D unaPagina = null;

        /* regola il numero di pagina correntemente in stampa */
        numeroPagina = pageIndex;

        if (numeroPagina>maxPagina) {
            maxPagina=numeroPagina;
        }// fine del blocco if

                /* richiama il metodo nella superclasse
           * che trasforma le coordinate, aggiunge all'oggetto
           * grafico eventuali parti fisse e ritorna un oggetto
           * Graphics2D sul quale disegnare */
        unaPagina = super.trasformaCoordinate(g, pf);

        /* disegna le parti specifiche della pagina */
        this.disegnaPagina(unaPagina, pf);

    } // fine del metodo


    /**
     * Pageable interface implementation
     */
     public int getNumberOfPages(){
        /* variabili e costanti locali di lavoro */
        int quantePag;

        if (this.isStampaServizio()) {
            quantePag=maxPagina+1;
        } else {
            quantePag=1;
        }// fine del blocco if-else

        /* valore di ritorno */
        return quantePag;
     }


}// fine della classe