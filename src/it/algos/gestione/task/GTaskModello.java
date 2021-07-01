/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 16-5-2007
 */

package it.algos.gestione.task;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.TavolaModello;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.tavola.Tavola;
import it.algos.base.tavola.renderer.RendererData;
import it.algos.base.wrapper.WrapFiltri;

import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;
import java.util.Date;

/**
 * Tracciato record della tavola Atask.
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
 * @version 1.0 / 16-5-2007
 */
public class GTaskModello extends ModelloAlgos implements GTask {

    /**
     * Costruttore completo senza parametri.
     */
    public GTaskModello() {
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

            /* campo sigla */
            unCampo = CampoFactory.testo(Cam.sigla);
            unCampo.decora().obbligatorio();
            unCampo.setRicercabile(true);
            unCampo.setLarghezza(250);
            this.addCampo(unCampo);

            /* campo descrizione estesa */
            unCampo = CampoFactory.testoArea(Cam.descrizione);
            unCampo.setNumeroRighe(8);
            unCampo.setLarScheda(350);
            this.addCampo(unCampo);

            /* campo giorni lavorazione necessari */
            unCampo = CampoFactory.intero(Cam.giornilavorazione);
            unCampo.decora().obbligatorio();
            unCampo.setLarLista(70);
            this.addCampo(unCampo);

            /* campo data inizio lavorazione suggerita */
            unCampo = CampoFactory.data(Cam.dataInizio);
            unCampo.setAbilitato(false);
            unCampo.setRenderer(new RendererCheckData(unCampo));
            unCampo.setInit(null);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo data utile */
            unCampo = CampoFactory.data(Cam.dataUtile);
            unCampo.setRenderer(new RendererCheckData(unCampo));
            unCampo.setInit(null);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo evaso */
            unCampo = CampoFactory.checkBox(Cam.evaso);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione dei filtri per i popup.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    @Override
    protected void regolaFiltriPop() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        WrapFiltri popFiltri;

        try { // prova ad eseguire il codice

            /* crea il popup task evasi / non evasi */
            popFiltri = super.addPopFiltro();
            filtro = FiltroFactory.crea(GTask.Cam.evaso.get(), false);
            popFiltri.add(filtro, "Non evasi");
            filtro = FiltroFactory.crea(GTask.Cam.evaso.get(), true);
            popFiltri.add(filtro, "Evasi");
            popFiltri.setTuttiFinale(false);
            popFiltri.setPos(1);
            popFiltri.setTitolo("Filtro evasi");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Renderer per i campi Data Inizio e Data Utile nella lista
     * </p>
     * Colora il campo di rosso se la data è posteriore a oggi e il task non è evaso
     */
    private final class RendererCheckData extends RendererData {

        /**
         * Costruttore base senza parametri.
         * <p/>
         *
         * @param campo di riferimento
         */
        public RendererCheckData(Campo campo) {
            /* rimanda al costruttore di questa classe */
            super(campo);
        }// fine del metodo costruttore base


        public Component getTableCellRendererComponent(JTable jTable,
                                                       Object object,
                                                       boolean b,
                                                       boolean b1,
                                                       int row,
                                                       int col) {
            /* variabili e costanti locali di lavoro */
            Component comp = null;
            Date data;
            Date oggi;
            Color colore;
            boolean evaso = false;
            Campo campoRend;
            Modulo mod;
            Campo campo;

            try { // prova ad eseguire il codice

                comp = super.getTableCellRendererComponent(jTable, object, b, b1, row, col);

                /* recupera il valore del flag evaso */
                if (jTable instanceof Tavola) {
                    Tavola tavola = (Tavola)jTable;
                    TavolaModello modello = tavola.getModello();
                    Dati dati = modello.getDati();
                    campoRend = getCampo();
                    mod = campoRend.getModulo();
                    campo = mod.getCampo(Cam.evaso.get());
                    evaso = dati.getBoolAt(row, campo);
                }// fine del blocco if

                /* se non evaso e posteriore alla data di oggi, colora di rosso */
                if (object != null) {
                    if (object instanceof Date) {
                        data = (Date)object;
                        oggi = Lib.Data.getCorrente();
                        colore = Color.black;
                        if (!evaso) {
                            if (Lib.Data.isPosteriore(data, oggi)) {
                                colore = Color.red;
                            }// fine del blocco if
                        }// fine del blocco if
                        comp.setForeground(colore);
                    }// fine del blocco if
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return comp;
        }
    } // fine della classe 'interna'


} // fine della classe
