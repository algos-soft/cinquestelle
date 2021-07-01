/**
 * Title:     RigaListinoModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-apr-2007
 */
package it.algos.albergo.listino.rigalistino;

import it.algos.albergo.listino.Listino;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.vista.Vista;

/**
 * Tracciato record della tavola rigalistino.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 224-apr-2007
 */
public final class RigaListinoModello extends ModelloAlgos implements RigaListino {

    /**
     * Costruttore completo senza parametri.
     */
    public RigaListinoModello() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* nome della tavola di archivio collegata
         * i nomi delle tavole sono sempre minuscoli
         * se vuoto usa il nome del modulo */
        super.setTavolaArchivio(NOME_TAVOLA);
    }


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
     * Invoca il metodo sovrascritto della superclasse <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli
     * @see it.algos.base.modello.ModelloAlgos#creaCampi
     * @see it.algos.base.campo.base.CampoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo listino di riferimento */
            unCampo = CampoFactory.link(Cam.listino);
            unCampo.setNomeModuloLinkato(Listino.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            unCampo.setAbilitato(false);
            unCampo.setPresenteScheda(false);
            this.addCampo(unCampo);

            /* campo data inizio */
            unCampo = CampoFactory.data(Cam.dataInizio);
            unCampo.setInit(null);
            unCampo.decora().obbligatorio();
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            /* campo data fine */
            unCampo = CampoFactory.data(Cam.dataFine);
            unCampo.setInit(null);
            unCampo.decora().obbligatorio();
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            /* campo prezzo */
            unCampo = CampoFactory.valuta(Cam.prezzo);
            unCampo.setVisibileVistaDefault();
            unCampo.setLarghezza(100);
            unCampo.setRidimensionabile(false);
            this.addCampo(unCampo);

//            /* filtra il campo link per il combobox dei sottoconti */
//            unCampo = this.getCampo(CAMPO_SOTTOCONTO);
//            unCampoDB = unCampo.getCampoDB();
//            campoFiltro = Albergo.Moduli.albsottoconto.getCampo(
//                    AlbSottoconto.CAMPO_FISSO);
//            filtro = FiltroFactory.crea(campoFiltro, true);
//            unCampoDB.setFiltro(filtro);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di viste aggiuntive, oltre alla vista base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Vista</code>)
     * per individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse sono stati
     * costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli()
     * @see #regolaViste
     */
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista;

        try { // prova ad eseguire il codice

            /* crea la vista per la riga all'interno del listino */
            vista = new Vista(Vis.listino.toString(), this.getModulo());
            vista.addCampo(Cam.dataInizio.get());
            vista.addCampo(Cam.dataFine.get());
            vista.addCampo(Cam.prezzo.get());
            this.addVista(vista);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia dei
     * campi delle viste; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella superclasse sono state
     * <strong>clonate</strong> tutte le viste <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #creaViste
     */
    protected void regolaViste() {
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


} // fine della classe
