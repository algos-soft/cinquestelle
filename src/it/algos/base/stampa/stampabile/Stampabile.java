/**
 * Title:        Pagina.java
 * Package:      it.algos.base.stampa
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi
 * @version 1.0  /
 * Creato:       il 6 novembre 2003 alle 11.58
 */
package it.algos.base.stampa.stampabile;

import it.algos.base.stampa.impostazione.Impostazione;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;

/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Pubblicare i metodi e definire le costanti di un oggetto stampabile <br>
 * B - ... <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  6 novembre 2003 ore 11.58
 */
public interface Stampabile extends Pageable {

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
    public abstract int print(Graphics g, PageFormat pf, int pageIndex);


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
    public abstract boolean printPagina(Graphics2D pagina, PageFormat formato, int numPagina);


    public abstract void setImpostazione(Impostazione impostazione);


    /* setter per regolare l'impostazione */
    public abstract void setAPI(int unaAPIStampa);


    public abstract void setNumeroCopie(int unNumeroCopie);


    public abstract void setNomePrintJob(String unNomePrintJob);


    public abstract void setPresentaDialogoPagina(boolean isPresentaDialogoPagina);


    public abstract void setPresentaDialogoStampa(boolean isPresentaDialogoStampa);


    /* setter per regolare la Pagina */
    public abstract void setCodiceCarta(Integer codiceCarta);


    public abstract void setDimensionePagina(double larghezza, double altezza);


    public abstract void setMarginePagina(double sx, double dx, double sopra, double sotto);


    public abstract void setOrientamentoPagina(int orientamentoPagina);


    public abstract Impostazione getImpostazione();

}// fine della interfaccia