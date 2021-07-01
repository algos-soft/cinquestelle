/**
 * Title:     RTOModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      1-mar-2005
 */
package it.algos.albergo.ristorante.righetavoloordini;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.modifica.Modifica;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.vista.Vista;

/**
 * Tracciato record della tavola RTOModello.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 1-mar-2005 ore 21.21.00
 */
public final class RTOModello extends ModelloAlgos implements RTO {

    /**
     * nome della tavola di archivio collegata (facoltativo).
     * se vuoto usa il nome del modulo
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;


    /**
     * Costruttore completo senza parametri.
     */
    public RTOModello() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(TAVOLA_ARCHIVIO);
    }// fine del metodo inizia


    public boolean inizializza(Modulo unModulo) {
        return super.inizializza(unModulo);
    } /* fine del metodo */


    /**
     * Creazione dei campi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Creazione dei campi record di questo modello <br>
     * I campi verranno visualizzati nell'ordine di inserimento <br>
     * Ogni campo viene creato con un costruttore semplice con solo le piu'
     * comuni informazioni; le altre vengono regolate con chiamate successive <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.campo.base.CampoFactory
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        Campo campoOrdine;

        /* invoca il metodo sovrascritto della superclasse */
        super.creaCampi();

        try { // prova ad eseguire il codice

            /* campo link al record di RMO */
            unCampo = CampoFactory.link(CAMPO_RMORDINI);
            unCampo.setNomeModuloLinkato(Ristorante.MODULO_RIGHE_MENU_ORDINI);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            this.addCampo(unCampo);

            /* campo link al record di modifica */
            unCampo = CampoFactory.comboLinkSel(CAMPO_MODIFICA);
            unCampo.setNomeModuloLinkato(Ristorante.MODULO_MODIFICA);
            unCampo.setNomeVistaLinkata(Modifica.VISTA_DESCRIZIONE);
            unCampo.setNomeCampoValoriLinkato(Modifica.CAMPO_DESCRIZIONE);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setVisibileVistaDefault();
            unCampo.setModificabileLista(true);
            unCampo.setUsaNuovo(true);
            campoOrdine = Ristorante.Moduli.Modifica().getCampo(Modifica.CAMPO_DESCRIZIONE);
            unCampo.setOrdineElenco(campoOrdine);
            this.addCampo(unCampo);

            /* campo flag anticipo */
            unCampo = CampoFactory.checkBox(CAMPO_ANTICIPO);
            unCampo.setVisibileVistaDefault();
            unCampo.setModificabileLista(true);
            unCampo.setLarLista(30);
            unCampo.setTitoloColonna("ant");
            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di viste aggiuntive, oltre alla vista base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Vista</code>) per
     * individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse sono stati costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista = null;

        try { // prova ad eseguire il codice

            vista = this.creaVista(VISTA_IN_MENU);
            vista.addCampo(this.getCampoOrdine());
            vista.addCampo(CAMPO_MODIFICA);
            vista.addCampo(CAMPO_ANTICIPO);
            this.addVista(vista);

//            VistaFactory.addCampo(vista, this.getCampoOrdine());
//            VistaFactory.addCampo(vista, CAMPO_MODIFICA);
//            VistaFactory.addCampo(vista,CAMPO_ANTICIPO);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia
     * dei campi delle viste; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella superclasse sono state
     * <strong>clonate</strong> tutte le viste <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.costante.CostanteModulo#VISTA_BASE_DEFAULT
     * @see it.algos.base.costante.CostanteModello#NOME_CAMPO_SIGLA
     */
    protected void regolaViste() {
        /* variabili e costanti locali di lavoro */
        Vista unaVista = null;
        Campo unCampo = null;
        Modulo modulo = null;

        try { // prova ad eseguire il codice

            unaVista = this.getVista(VISTA_IN_MENU);

            /* regola il campo Modifica
             * lo rende ridimensionabile */
            modulo = Progetto.getModulo(Ristorante.MODULO_MODIFICA);
            unCampo = modulo.getCampo(Modifica.CAMPO_DESCRIZIONE);
            unCampo = unaVista.getCampo(unCampo);
            unCampo.setRidimensionabile(true);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

}// fine della classe
