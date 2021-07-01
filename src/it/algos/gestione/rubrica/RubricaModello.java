/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2-7-2007
 */

package it.algos.gestione.rubrica;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.vista.Vista;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.AnagraficaModello;
import it.algos.gestione.anagrafica.categoria.CatAnagrafica;
import it.algos.gestione.anagrafica.categoria.CatAnagraficaModulo;
import it.algos.gestione.indirizzo.Indirizzo;

/**
 * Tracciato record della tavola Rubrica.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Regola il nome della tavola </li>
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
 * <li> Restituisce un estratto di informazioni </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2-7-2007
 */
public class RubricaModello extends AnagraficaModello implements Rubrica {

    /**
     * Costruttore completo senza parametri.
     */
    public RubricaModello() {
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
        super.setTavolaArchivio(Rubrica.NOME_TAVOLA);
    }


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
        String chiave;
        Navigatore nav;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo categoria della superclasse - ne modifico alcuni aspetti */
            unCampo= this.getCampo(Anagrafica.Cam.categoria);
            unCampo.setVisibileVistaDefault(true);
            unCampo.setRicercabile(true);
            unCampo.setLarScheda(130);
            unCampo.setUsaNuovo(true);


//            unCampo = CampoFactory.comboLinkPop(Anagrafica.Cam.categoria);
//            unCampo.setNomeModuloLinkato(CatAnagrafica.NOME_MODULO);
//            unCampo.setNomeColonnaListaLinkata(CatRubrica.CAMPO_SIGLA);
//            unCampo.setVisibileVistaDefault(true);
//            unCampo.setLarScheda(130);
//            unCampo.setUsaNuovo(true);
//            this.addCampo(unCampo);


            /* campo tipo (privato/società) della superclasse -  leva etichetta */
            unCampo = super.getCampo(Anagrafica.Cam.privatosocieta.get());
            unCampo.decora().eliminaEtichetta();

            /* campo cognome - non obbligatorio */
            unCampo = super.getCampo(Anagrafica.Cam.cognome.get());
            unCampo.decora().eliminaObbligatorio();

            /* recupera ed elimina il campo sesso dalla superclasse */
            chiave = Anagrafica.Cam.sesso.get();
            this.getCampiModello().remove(chiave);

            /* recupera ed elimina il campo data consenso dalla superclasse */
            chiave = Anagrafica.Cam.dataPrivacy.get();
            this.getCampiModello().remove(chiave);

            /* campo navigatore indirizzi */
            unCampo = super.getCampo(Anagrafica.Cam.indirizzi.get());
            nav = unCampo.getNavigatore();
            nav.getPortaleLista().setEstratto(Indirizzo.Est.indirizzo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br> Eventuale regolazione delle caratteristiche
     * specifiche di ogni copia dei campi delle viste; le variazioni modificano
     * <strong>solo</strong> le copie <br> Viene chiamato <strong>dopo</strong> che nella
     * superclasse sono state <strong>clonate</strong> tutte le viste <br> Metodo sovrascritto nelle
     * sottoclassi <br> (metodo chiamato dalla superclasse) <br>
     *
     * @see #creaViste
     */
    @Override
    protected void regolaViste() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modCat;
        Vista unaVista = null;
        Campo unCampo;

        try { // prova ad eseguire il codice

            modCat = CatAnagraficaModulo.get();
            continua = (modCat != null);

            /* regola la vista di default */
            if (continua) {
                unaVista = this.getVistaDefault();
                continua = (unaVista != null);
            }// fine del blocco if

            /* campo categoria */
            if (continua) {
                unCampo = modCat.getCampo(CatAnagrafica.Cam.sigla.get());
                unCampo = unaVista.getCampo(unCampo);
                if (unCampo != null) {
                    unCampo.setTitoloColonna("categoria");
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



} // fine della classe
