/**
 * Title:        CDElencoEsterno.java
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Valbonesi
 * @version 1.0  /
 * Creato:       il 7 novembre 2003 alle 12.07
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.db.CDBLinkato;
import it.algos.base.campo.tipodati.tipoarchivio.TALink;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.errore.Errore;
import it.algos.base.matrice.MatriceDoppia;


/**
 * Classe concreta per implementare un oggetto da <code>CDElenco</code> <br>
 * <p/>
 * Questa classe concreta e' responsabile di: <br>
 * A - Gestire i dati per i gruppi di oggetti <br>
 * B - La lista valori e' esterna; viene regolata con una select ad una
 * tavola sul database <br>
 *
 * @author Guido Andrea Ceresa e Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  7 novembre 2003 ore 11.59
 */
public final class CDElencoLink extends CDElenco {

    private static final TipoArchivio TIPO_ARCHIVIO = TALink.getIstanza();


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDElencoLink() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDElencoLink(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        super.setTipoArchivio(TIPO_ARCHIVIO);

        /* di default non usa il renderer specifico nella lista */
        this.setUsaRendererElenco(false);

    } /* fine del metodo inizia */


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito' <br>
     * Metodo chiamato da altre classi <br>
     * Viene eseguito tutte le volte che necessita  <br>
     */
    public void avvia() {

        /* rimanda al metodo sovrascritto della superclasse */
        super.avvia();

        /* crea l'elenco di valori selezionabili */
        this.creaElenco();

    } /* fine del metodo */


    /**
     * viene recuperata da CDBLinkato, una nuova istanza di MatriceDoppia <br>
     */
    protected MatriceDoppia regolaValoriMatrice(MatriceDoppia nonUsata) {
        /* variabili e costanti locali di lavoro */
        CDBLinkato unCampoDBLinkato;
        MatriceDoppia unaMatriceDoppia = null;

        try {    // prova ad eseguire il codice
            /* recupera il campo db specializzato */
            unCampoDBLinkato = ((CDBLinkato)this.unCampoParente.getCampoDB());

            /* recupera un'istanza di MatriceDoppia */
            unaMatriceDoppia = unCampoDBLinkato.caricaListaValori();
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unaMatriceDoppia;
    }


    /**
     * Recupera il valore di elenco correntemente selezionato.
     * <p/>
     *
     * @return il valore corrente
     */
    public Object getValoreElenco() {
        /* variabili e costanti locali di lavoro */
        Object valore = null;

        try { // prova ad eseguire il codice
            valore = this.getElenco().getValoreSelezionato();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
        /* valore di ritorno */
        return valore;
    }

}// fine della classe


