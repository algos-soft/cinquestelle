/**
 * Title:     StampaTest
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19.05.2006
 */
package it.algos.albergo.conto;

import it.algos.base.errore.Errore;
import it.algos.base.stampa.stampabile.StampabileDefault;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

/**
 * Stampa di un conto.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 18-mag-2006 ore 14.39.46
 */
public final class StampaTest extends StampabileDefault implements Printable {

    /**
     * Costruttore completo
     * <p/>
     */
    public StampaTest() {
        /* rimanda al costruttore della superclasse */
        super();

        /* regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia();
        } catch (Exception unErrore) {
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


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
        /** variabili e costanti locali di lavoro */
        int ritorno = 0;
        Graphics2D unaPagina = null;

        try { // prova ad eseguire il codice

            if (pageIndex == 0) {

                /** richiama il metodo sovrascritto nella superclasse
                 *  che trasforma le coordinate, aggiunge all'oggetto
                 *  grafico eventuali parti fisse e ritorna un oggetto
                 *  Graphics2D sul quale disegnare */
                unaPagina = super.trasformaCoordinate(g, pf);

                /* recupera le misure della pagina */
                float lar = (float)pf.getImageableWidth();
                float alt = (float)pf.getImageableHeight();
                unaPagina.drawRect(0, 0, (int)lar, (int)alt);
                unaPagina.drawString("Questa e' una stringa", 0, 20);

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


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     */
    private void inizia() {
        /* regolazioni della Pagina */
//        super.setOrientamentoPagina(PaginaStampa.ORIZZONTALE);
//        super.setPresentaDialogoPagina(false);
//        this.setMarginePagina(0,0,80,20);

    } /* fine del metodo inizia */


    /**
     * Disegna una pagina.
     * <p/>
     * Metodo da implementare obbligatoriamente nelle sottoclassi.
     * E' responsabilita' del metodo:
     * - disegnare la pagina richiesta nell'oggetto grafico g che viene passato.
     * - ritornare true se la stampa e' terminata o false se non e' terminata.
     *
     * @param pagina l'oggetto grafico nel quale disegnare
     * @param formato il formato della pagina che verra' stampata
     * @param numPagina il numero di pagina da stampare (1 per la prima)
     *
     * @return true se la stampa e' terminata
     */
    public boolean printPagina(Graphics2D pagina, PageFormat formato, int numPagina) {
        /* variabili e costanti locali di lavoro */
        boolean terminato = false;


        try { // prova ad eseguire il codice

            /* recupera le misure della pagina */
            float lar = (float)formato.getImageableWidth();
            float alt = (float)formato.getImageableHeight();
            pagina.drawRect(0, 0, (int)lar, (int)alt);
            pagina.drawString("Questa e' una stringa", 0, 20);
            terminato = true;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return terminato;
    }


}// fine della classe
