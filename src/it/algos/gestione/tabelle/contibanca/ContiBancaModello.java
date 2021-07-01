/**
 * Title:     ContiBancaModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-feb-2007
 */
package it.algos.gestione.tabelle.contibanca;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.gestione.anagrafica.Anagrafica;

/**
 * Tracciato record della tavola ContiBanca.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il <strong>tracciato record</strong> (Abstract Data Types) di una
 * tavola </li>
 * <li> Mantiene il nome della tavola di archivo dove sono registrati tutti i
 * dati (records) del modello </li>
 * <li> Crea i <strong>campi</strong> di questo modello (oltre a quelli base
 * della superclasse) </li>
 * <li> Un eventuale file di dati iniziali va regolato come percorso e nomi dei
 * campi presenti </li>
 * <li> Eventuali <strong>moduli e tabelle</strong> vanno creati nel metodo <code>
 * regolaModuli</code> </li>
 * <li> Regola i titoli delle finestre lista e scheda
 * <li> Ogni campo viene creato con un costruttore semplice con solo le piu'
 * comuni informazioni; le altre vengono regolate con chiamate successive </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 26-feb-2007 ore 10:31:29
 */
public final class ContiBancaModello extends ModelloAlgos implements ContiBanca {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;

    /**
     * Testo della colonna della Lista come appare nella Vista
     */
    private static final String COLONNA_SIGLA = TITOLO_TABELLA;

    /**
     * Testo della legenda sotto il campo sigla nella scheda
     */
    private static final String LEGENDA_SIGLA = "sigla come appare nelle liste di altri moduli";


    /**
     * Costruttore completo senza parametri.<br>
     */
    public ContiBancaModello() {
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
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(ContiBancaModello.TAVOLA_ARCHIVIO);
    }// fine del metodo inizia


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
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo link all'anagrafica */
            unCampo = CampoFactory.comboLinkSel(Cam.soggetto);
            unCampo.setNomeModuloLinkato(Anagrafica.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Anagrafica.Cam.soggetto.get());
            unCampo.setNomeCampoValoriLinkato(Anagrafica.Cam.soggetto.get());
            this.addCampo(unCampo);

            /* campo sigla */
            unCampo = CampoFactory.sigla();
            this.addCampo(unCampo);

            /* campo nome banca */
            unCampo = CampoFactory.testo(Cam.nomeBanca);
            unCampo.decora().obbligatorio();
            unCampo.getValidatore().setLunghezzaMassima(50);
            unCampo.setLarghezza(250);
            this.addCampo(unCampo);

            /* campo cin */
            unCampo = CampoFactory.testo(Cam.cin);
            unCampo.getValidatore().setLunghezzaMassima(1);
            unCampo.setLarghezza(15);
            this.addCampo(unCampo);

            /* campo abi */
            unCampo = CampoFactory.testo(Cam.abi);
            unCampo.getValidatore().setLunghezzaMassima(5);
            unCampo.setLarghezza(50);
            this.addCampo(unCampo);

            /* campo cab */
            unCampo = CampoFactory.testo(Cam.cab);
            unCampo.getValidatore().setLunghezzaMassima(5);
            unCampo.setLarghezza(50);
            this.addCampo(unCampo);

            /* campo numero di conto */
            unCampo = CampoFactory.testo(Cam.conto);
            unCampo.getValidatore().setLunghezzaMassima(12);
            unCampo.decora().obbligatorio();
            unCampo.setLarghezza(100);
            this.addCampo(unCampo);

            /* campo iban */
            unCampo = CampoFactory.testo(Cam.iban);
            unCampo.getValidatore().setLunghezzaMassima(34);
            unCampo.setLarghezza(250);
            this.addCampo(unCampo);

            /* campo bic */
            unCampo = CampoFactory.testo(Cam.bic);
            unCampo.getValidatore().setLunghezzaMassima(11);
            unCampo.setLarghezza(70);
            this.addCampo(unCampo);

            /* campo "preferito" */
            this.setUsaCampoPreferito(true);

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

            /* crea la vista specifica (un solo campo) */
            super.addVista(VISTA_SIGLA, CAMPO_SIGLA);

            /* vista per la lista indirizzi nella scheda anagrafica */
            vista = new Vista(ContiBanca.Vis.contiInAnag.toString(), this.getModulo());
            vista.addCampo(ContiBanca.Cam.sigla.get());
            vista.addCampo(ContiBanca.Cam.nomeBanca.get());
            vista.addCampo(ContiBanca.Cam.conto.get());
            this.addVista(vista);

            /* vista per la lista dei conti nostri */
            vista = new Vista(ContiBanca.Vis.contiNostri.toString(), this.getModulo());
            vista.addCampo(ContiBanca.Cam.sigla.get());
            vista.addCampo(ContiBanca.Cam.nomeBanca.get());
            vista.addCampo(ContiBanca.Cam.conto.get());
            this.addVista(vista);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param estratto codifica dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito
     */
    public EstrattoBase getEstratto(Estratti estratto, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;

        try { // prova ad eseguire il codice

            /* selettore della variabile */
            switch ((ContiBanca.Estratto)estratto) {
                case nome:
                    unEstratto = this.getEstratto(estratto, chiave, ContiBanca.Cam.nomeBanca.get());
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


}// fine della classe
