/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.gestione.indirizzo.tabelle.nazione;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.pref.Pref;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.WrapFiltri;
import it.algos.gestione.indirizzo.tabelle.regione.Regione;
import it.algos.gestione.indirizzo.tabelle.regione.RegioneModulo;
import it.algos.gestione.tabelle.valuta.Valuta;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;

/**
 * Tracciato record della tavola Nazione.
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
public class NazioneModello extends ModelloAlgos implements Nazione {

    private static final String COLONNA_NAZIONE = "naz";


    /**
     * Costruttore completo senza parametri.
     */
    public NazioneModello() {

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
        boolean usato;
        Modulo mod;
        Navigatore nav;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* livello di uso del programma */
            usato = (Pref.Gen.livello.comboInt() >= Pref.Livello.medio.ordinal() + 1);

            /* campo nazione */
            unCampo = CampoFactory.testo(Cam.nazione);
            unCampo.setVisibileVistaDefault();
            unCampo.setLarLista(150);
            unCampo.setLarScheda(250);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            /* campo nazione (nome completo ufficale) */
            unCampo = CampoFactory.testo(Cam.nazioneCompleto);
            unCampo.setLarScheda(400);
            this.addCampo(unCampo);

            /* campo capitale */
            unCampo = CampoFactory.testo(Cam.capitale);
            unCampo.setLarLista(150);
            unCampo.setLarScheda(250);
            this.addCampo(unCampo);

            /* campo sigla due */
            unCampo = CampoFactory.testo(Cam.sigla2);
            unCampo.setValidatore(null);
            unCampo.setLarghezza(40);
            unCampo.getCampoLista().setModificabile(false);
            this.addCampo(unCampo);

            /* campo sigla tre */
            unCampo = CampoFactory.testo(Cam.sigla3);
            unCampo.setVisibileVistaDefault(usato);
            unCampo.setLarghezza(40);
            this.addCampo(unCampo);

            /* campo internet */
            unCampo = CampoFactory.testo(Cam.tld);
            unCampo.setVisibileVistaDefault(usato);
            unCampo.setLarghezza(30);
            this.addCampo(unCampo);

            /* campo appartenenza all'unione europea */
            unCampo = CampoFactory.checkBox(Cam.checkEuropa);
            unCampo.addOrdinePrivato(getCampo(Cam.nazione.get()));
            if (usato) {
                this.addCampo(unCampo);
            }// fine del blocco if

            /* campo link valuta del paese */
            unCampo = CampoFactory.comboLinkSel(Cam.linkValuta);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setLarScheda(200);
            unCampo.setNomeModuloLinkato(Valuta.NOME_MODULO);
            unCampo.setNomeVistaLinkata(Valuta.Vis.codiceIso.toString());
            unCampo.setNomeCampoValoriLinkato(Valuta.Cam.valuta.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setUsaNuovo(false);
            unCampo.decora().estratto(Valuta.Est.codiceIso);
            if (usato) {
                this.addCampo(unCampo);
            }// fine del blocco if

            /* campo prefisso telefonico */
            unCampo = CampoFactory.testo(Cam.telefono);
            this.addCampo(unCampo);

            /* campo giorno festivo - festa nazionale */
//            unCampo = CampoFactory.testo(Cam.festivo);
//            this.addCampo(unCampo);

            /* divisioni di 1° livello */
            /* non visibile - serve per i popup dei filtri nella lista di Regione */
            /* (ogni stato usa un nome diverso) */
            unCampo = CampoFactory.testo(Cam.divisioniUno);
            this.addCampo(unCampo);

            /* divisioni di 2° livello */
            /* non visibile - serve per i popup dei filtri nella lista di Provincia */
            /* (ogni stato usa un nome diverso) */
            unCampo = CampoFactory.testo(Cam.divisioniDue);
            this.addCampo(unCampo);

            /* offset GMT della nazione */
            /* per le nazioni che coprono più fusi orari, rappresenta l'offset medio */
            unCampo = CampoFactory.intero(Cam.offsetGMT);
            this.addCampo(unCampo);

            /* campo navigatore sub-lista */
            mod = RegioneModulo.get();
            if (mod != null) {
                nav = mod.getNavigatore(Regione.Nav.reg.toString());
                unCampo = CampoFactory.navigatore(Cam.subRegioni, nav);
                unCampo.decora().eliminaEtichetta();
                if (Pref.Gen.livello.comboInt() >= Pref.Livello.alto.ordinal() + 1) {
                    this.addCampo(unCampo);
                }// fine del blocco if
            }// fine del blocco if

            /* campo note standard in basso */
            super.setUsaCampoNote(true);

            /* rende visibile il campo ordine */
            super.setCampoOrdineVisibileLista(); //

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
     * @see Progetto#preparaModuli()
     * @see #regolaViste
     */
    protected void creaViste() {
        try { // prova ad eseguire il codice
            /* crea la vista specifica */
            super.addVista(Nazione.Vis.sigla.toString(), Nazione.Cam.sigla2.get());
            super.addVista(Nazione.Vis.naz.toString(), Nazione.Cam.nazione.get());
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
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        Vista unaVista;

        try { // prova ad eseguire il codice
            /* regola il voce della colonna sigla nella vista sigla */
            unaVista = this.getVista(Nazione.Vis.sigla);
            unCampo = unaVista.getCampo(Nazione.Cam.sigla2);
            unCampo.setTitoloColonna(COLONNA_NAZIONE);
            unCampo.setLarLista(30);

//            unaVista = this.getVistaDefault();
//            unaVista.setCampoOrdineDefault(unaVista.getCampo(Nazione.Cam.checkEuropa.get()));
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
     * <p/>
     * Crea uno o più filtri alla lista, tramite un popup posizionato in basso a destra <br>
     * I popup si posizionano bandierati a destra,
     * ma iniziando da sinistra (secondo l'ordine di creazione) <br>
     */
    @Override
    protected void regolaFiltriPop() {
        /* variabili e costanti locali di lavoro */
        WrapFiltri lista;

        try { // prova ad eseguire il codice

            /* crea una lista di filtri */
            lista = super.addPopFiltro();
            lista.setTitolo("Unione Europea");
            lista.setTesto("Tutte");
            lista.add(FiltroFactory.creaVero(Nazione.Cam.checkEuropa.get()), "Europee");
            lista.add(FiltroFactory.creaFalso(Nazione.Cam.checkEuropa.get()), "Altre");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce un estratto di sigla e nome.
     * </p>
     * Controlla che il tipo di estratto richiesto sia un pannello <br>
     *
     * @param tipo codifica dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstrattoComposto(Estratti tipo, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        EstrattoBase.Tipo tipoEst;
        int cod;
        String campoSigla = Nazione.Cam.sigla2.get();
        String campoNazione = Nazione.Cam.nazione.get();
        String sigla;
        String nazione;
        JPanel pan;

        try { // prova ad eseguire il codice
            /* tipo di estratto codificato */
            tipoEst = tipo.getTipo();

            if (tipoEst == EstrattoBase.Tipo.pannello) {

                /* recupera i valori dal database */
                cod = (Integer)chiave;
                sigla = this.query().valoreStringa(campoSigla, cod);
                nazione = this.query().valoreStringa(campoNazione, cod);

                /* crea il pannello */
                pan = new PannelloFlusso();
//                pan = PannelloFactory.verticale();
//                pan = new JPanel();
//                pan.setOpaque(false);
//                pan.setBackground(Color.gray);
                pan.setPreferredSize(new Dimension(200, 20));
                pan.add(new JLabel(sigla + " - " + nazione));

                /* crea l'estratto */
                estratto = new EstrattoBase(pan, tipoEst);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
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
            switch ((Est)estratto) {
                case sigla:
                    unEstratto = this.getEstratto(estratto, chiave, Nazione.Cam.sigla2.toString());
                    break;
                case descrizione:
                    unEstratto = this.getEstratto(estratto, chiave, Nazione.Cam.nazione.toString());
                    break;
                case sigladescrizione:
                    unEstratto = this.getEstrattoDoppio(estratto,
                            chiave,
                            Nazione.Cam.sigla2.toString(),
                            Nazione.Cam.nazione.toString());
                    break;
                case composto:
                    unEstratto = this.getEstrattoComposto(estratto, chiave);
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
