package it.algos.albergo.stampeobbligatorie.testastampe;

import it.algos.albergo.tabelle.azienda.Azienda;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

import javax.swing.SwingConstants;

/**
 * Tracciato record della tavola Provincia.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il <strong>tracciato record</strong> (Abstract Data Types) di una
 * tavola </li>
 * <li> Crea i <strong>campi</strong> di questo modello (oltre a quelli base
 * della superclasse) nel metodo <code>creaCampi</code> </li>
 * <li> Ogni campo viene creato con un costruttore semplice con solo le piu'
 * comuni informazioni; le altre vengono regolate con chiamate successive </li>
 * <li> Crea eventuali <strong>viste</strong> della <code>Lista</code>
 * (oltre a quella base) nel metodo <code>creaViste</code> </li>
 * <li> Regola eventualmente i valori delle viste nel metodo <code>regolaViste</code> </li>
 * <li> Crea eventuali <strong>set</strong> della <code>Scheda</code>
 * (oltre a quello base) nel metodo <code>creaSet</code> </li>
 * <li> Regola eventualmente i valori dei set nel metodo <code>regolaSet</code> </li>
 * <li> Regola eventualmente i valori da inserire in un <code>nuovoRecord</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3-4-05
 */
public final class TestaStampeModello extends ModelloAlgos implements TestaStampe {


    /**
     * Costruttore completo senza parametri.
     */
    public TestaStampeModello() {

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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }


    /**
     * Regolazioni iniziali, dopo che sono stati regolati dalla sottoclasse
     * i parametri indispensabili (tra cui il riferimento al modulo)
     * Metodo chiamato dalla classe che crea questo oggetto
     * Viene eseguito una sola volta
     *
     * @param unModulo Abstract Data Types per le informazioni di un modulo
     */
    @Override public boolean inizializza(Modulo unModulo) {
        /* variabili e costanti locali di lavoro */
        boolean inizializzato = false;

        try { // prova ad eseguire il codice
            inizializzato = super.inizializza(unModulo);
            super.setCampoOrdineIniziale(Cam.data);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return inizializzato;
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

            /* campo tipo di record */
            unCampo = CampoFactory.intero(Cam.tipo);
            this.addCampo(unCampo);

            /* campo azienda */
            unCampo = CampoFactory.link(Cam.azienda);
            unCampo.setNomeModuloLinkato(Azienda.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Azienda.CAMPO_SIGLA);
            this.addCampo(unCampo);

            /* campo check stampa effettuata */
            unCampo = CampoFactory.checkBox(Cam.stampato);
            unCampo.setLarghezza(50);
            unCampo.setRenderer(new TestaRendererStampato(unCampo));
            this.addCampo(unCampo);

            /* campo data di riferimento */
            unCampo = CampoFactory.data(Cam.data);
            this.addCampo(unCampo);

            /* campo numero di gruppi arrivati nel giorno */
            unCampo = CampoFactory.intero(Cam.gruppi);
            unCampo.setLarghezza(40);
            unCampo.setAllineamentoLista(SwingConstants.CENTER);
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo numero di persone arrivate nel giorno */
            unCampo = CampoFactory.intero(Cam.numArrivati);
            unCampo.setAllineamentoLista(SwingConstants.CENTER);
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo numero di persone partite nel giorno */
            unCampo = CampoFactory.intero(Cam.numPartiti);
            unCampo.setAllineamentoLista(SwingConstants.CENTER);
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo numero di persone presenti al giorno precedente */
            unCampo = CampoFactory.intero(Cam.presPrec);
            unCampo.setAllineamentoLista(SwingConstants.CENTER);
            this.addCampo(unCampo);

            /* campo numero progressivo del documento all'interno dell'anno */
            unCampo = CampoFactory.intero(Cam.progressivo);
            unCampo.setAllineamentoLista(SwingConstants.CENTER);
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
        VistaElemento elem;


        try { // prova ad eseguire il codice

            /* crea la vista per il navigatore ps */
            vista = this.creaVista(Vis.ps.get());
            vista.addCampo(Cam.stampato.get());
            vista.addCampo(Cam.data.get());
            vista.addCampo(Cam.numArrivati.get());
            this.addVista(vista);

            /* crea la vista per il navigatore notifiche */
            vista = this.creaVista(Vis.notifica.get());
            vista.addCampo(Cam.stampato.get());
            vista.addCampo(Cam.data.get());
            vista.addCampo(Cam.gruppi.get());
            vista.addCampo(Cam.numArrivati.get());
            this.addVista(vista);

            /* crea la vista per il navigatore ISTAT */
            vista = this.creaVista(Vis.istat.get());
            vista.addCampo(Cam.stampato.get());
            vista.addCampo(Cam.progressivo.get());
            vista.addCampo(Cam.data.get());
            vista.addCampo(Cam.presPrec.get());
            vista.addCampo(Cam.numArrivati.get());
            vista.addCampo(Cam.numPartiti.get());
            this.addVista(vista);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

} // fine della classe
