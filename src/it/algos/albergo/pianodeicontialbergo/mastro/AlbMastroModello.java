/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 21 apr 2006
 */

package it.algos.albergo.pianodeicontialbergo.mastro;

import it.algos.albergo.pianodeicontialbergo.conto.AlbConto;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.gestione.pianodeiconti.mastro.PCMastro;
import it.algos.gestione.pianodeiconti.mastro.PCMastroModello;

import java.util.ArrayList;

/**
 * Tracciato record della tavola AlbMastro.
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
 * @version 1.0 / 21 apr 2006
 */
public final class AlbMastroModello extends PCMastroModello implements AlbMastro {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = AlbMastro.NOME_TAVOLA;


    /**
     * Costruttore completo senza parametri.
     */
    public AlbMastroModello() {
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
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(TAVOLA_ARCHIVIO);
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
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        Modulo modulo;
        Navigatore nav;
        ArrayList<String> lista;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* elimina il campo della superclasse lista del conto */
            unCampo = (Campo)this.getCampiModello().get(PCMastro.CAMPO_CONTI);
            this.getCampiModello().remove(unCampo);

            /* campo lista conti specifico dell'albergo */
            modulo = Progetto.getModulo(AlbConto.NOME_MODULO);
            if (modulo != null) {
                nav = modulo.getNavigatore(AlbConto.NAVIGATORE_ALB_MASTRO);
                unCampo = CampoFactory.navigatore(AlbMastro.CAMPO_ALB_CONTI, nav);
                unCampo.decora().eliminaEtichetta();
                unCampo.decora().legenda(LEGENDA_CONTI);
                this.addCampo(unCampo);
            }// fine del blocco if

//            /* campo tipo mastro (pens/extra) specifico dell'albergo */
//            unCampo = CampoFactory.radioInterno(CAMPO_TIPO);
//            unCampo.decora().obbligatorio();
//            unCampo.setInit(InitFactory.intero(AlbMastro.Tipi.pensione.getCodice()));
//            unCampo.setVisibileVistaDefault();
//            unCampo.setLarLista(60);
//            unCampo.getCampoVideo().setPosizionamentoVerticale(false);
//            lista = new ArrayList<String>();
//            lista.add(AlbMastro.Tipi.pensione.getDescrizione());
//            lista.add(AlbMastro.Tipi.extra.getDescrizione());
//            unCampo.setValoriInterni(lista);
//            unCampo.setUsaNonSpecificato(false);
//            unCampo.setTitoloColonna("tipo");
//            unCampo.setTestoEtichetta("tipo");
//            unCampo.setLarScheda(80);
//            unCampo.getCampoLista().setRidimensionabile(false);
//            unCampo.getCampoVideo().setPosizionamentoVerticale(false);
//            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Metodo invocato dal modulo <br>
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
            switch ((AlbMastro.Estratto)estratto) {
                case sigla:
                    unEstratto = this.getEstratto(estratto, chiave, AlbMastro.CAMPO_SIGLA);
                    break;
                case descrizione:
                    unEstratto = this.getEstratto(estratto, chiave, AlbMastro.CAMPO_DESCRIZIONE);
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


} // fine della classe
