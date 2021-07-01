/**
 * Title:        Impostazione.java
 * Package:      it.algos.base.stampa.impostazione
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi
 * @version 1.0  /
 * Creato:       il 13 novembre 2003 alle 9.51
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.stampa.impostazione;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Pubblicare all'esterno i metodi della impostazione di stampa <br>
 * B - Mantenere le costanti delle impostazioni di stampa <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  13 novembre 2003 ore 9.51
 */
public interface Impostazione {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    public static final int API_AWT = 1;   // identifica la API di stampa AWT

    public static final int API_SWING = 2; // identifica la API di stampa SWING


    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    /**
     * la API di stampa da utilizzare
     */
    public abstract void setAPI(int unaAPIStampa);


    /**
     * il numero di copie da stampare
     */
    public abstract void setNumeroCopie(int unNumeroCopie);


    /**
     * il nome del print job per la stampante
     */
    public abstract void setNomePrintJob(String unNomePrintJob);


    /**
     * flag - presenta il dialogo di impostazione pagina all'utente (solo AWT)
     */
    public abstract void setPresentaDialogoPagina(boolean isPresentaDialogoPagina);


    /**
     * flag - presenta il dialogo di impostazione stampa all'utente
     */
    public abstract void setPresentaDialogoStampa(boolean isPresentaDialogoStampa);


    /**
     * -- setter per regolare la Pagina --
     */
    public abstract void setCodiceCarta(Integer codiceCarta);


    public abstract void setDimensionePagina(double larghezza, double altezza);


    public abstract void setMarginePagina(double sx, double dx, double sopra, double sotto);


    public abstract void setOrientamentoPagina(int orientamentoPagina);


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * la API di stampa da utilizzare
     */
    public abstract int getAPI();


    /**
     * la pagina da utilizzare
     */
    public abstract PaginaStampa getPagina();


    /**
     * il numero di copie da stampare
     */
    public abstract int getNumeroCopie();


    /**
     * il nome del print job per la stampante
     */
    public abstract String getNomePrintJob();


    /**
     * flag - presenta il dialogo di impostazione pagina all'utente (solo AWT)
     */
    public abstract boolean isPresentaDialogoPagina();


    /**
     * flag - presenta il dialogo di impostazione stampa all'utente
     */
    public abstract boolean isPresentaDialogoStampa();

    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.stampa.impostazione.impostazione.java
//-----------------------------------------------------------------------------

