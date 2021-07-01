/**
 * Title:     RTOModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      1-mar-2005
 */
package it.algos.albergo.ristorante.menu.recupero;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;

/**
 * Tracciato record della tavola RTOModello.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 1-mar-2005 ore 21.21.00
 */
public final class RTOModelloOld extends ModelloAlgos implements RTOOld {

    /**
     * nome della tavola di archivio collegata (facoltativo).
     * se vuoto usa il nome del modulo
     */
    private static final String TAVOLA_ARCHIVIO = RTOOld.NOME_TAVOLA;


    /**
     * Costruttore completo senza parametri.
     */
    public RTOModelloOld() {
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

        /* invoca il metodo sovrascritto della superclasse */
        super.creaCampi();

        try { // prova ad eseguire il codice

            /* campo link al record di RMT */
            unCampo = CampoFactory.intero(CAMPO_LINKRMT);
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            /* campo link al record di RMP */
            unCampo = CampoFactory.intero(CAMPO_LINKRMP);
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            /* campo flag extra */
            unCampo = CampoFactory.checkBox(CAMPO_FLAG_EXTRA);
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            /* campo link al piatto extra */
            unCampo = CampoFactory.intero(CAMPO_LINK_EXTRAPIATTO);
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            /* campo link al contorno extra */
            unCampo = CampoFactory.intero(CAMPO_LINK_EXTRACONTORNO);
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            /* campo flag modifica */
            unCampo = CampoFactory.checkBox(CAMPO_FLAG_MODIFICA);
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            /* campo link alla modifica */
            unCampo = CampoFactory.intero(CAMPO_LINKMODIFICA);
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            /* campo flag anticipo */
            unCampo = CampoFactory.checkBox(CAMPO_FLAG_ANTICIPO);
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            this.setCampoOrdineVisibileLista();
            this.getCampoChiave().setVisibileVistaDefault(true);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


}// fine della classe
