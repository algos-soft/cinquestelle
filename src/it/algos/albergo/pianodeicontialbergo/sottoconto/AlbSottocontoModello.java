/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 21 apr 2006
 */

package it.algos.albergo.pianodeicontialbergo.sottoconto;

import it.algos.albergo.pianodeicontialbergo.conto.AlbConto;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.Modello;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.gestione.pianodeiconti.sottoconto.PCSottoconto;
import it.algos.gestione.pianodeiconti.sottoconto.PCSottocontoModello;

/**
 * Tracciato record della tavola AlbSottoconto.
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
public final class AlbSottocontoModello extends PCSottocontoModello {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = AlbSottoconto.NOME_TAVOLA;

    /**
     * Testo della colonna della Lista come appare nella Vista
     */
    private static final String COLONNA_SIGLA = AlbSottoconto.TITOLO_TABELLA;

    /**
     * Testo della colonna della Lista come appare nella Vista
     */
    private static final String COLONNA_DESCRIZIONE = AlbSottoconto.TITOLO_TABELLA;


    /**
     * Costruttore completo senza parametri.
     */
    public AlbSottocontoModello() {
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

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* elimina il campo della superclasse link al conto */
            /* se non esiste, non fa nulla */
            unCampo = (Campo)this.getCampiModello().get(PCSottoconto.CAMPO_CONTO);
            this.getCampiModello().remove(unCampo);

            /* campo link conto (piano dei conti albergo) */
            unCampo = CampoFactory.comboLinkPop(AlbSottoconto.CAMPO_ALB_CONTO);
            unCampo.setNomeModuloLinkato(AlbConto.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            unCampo.setVisibileVistaDefault();
            unCampo.decora().obbligatorio();
            unCampo.setUsaNuovo(true);
            unCampo.decora().etichetta(TESTO_CONTO);
            unCampo.decora().estrattoSotto(AlbConto.Estratto.descrizione);
            this.addCampo(unCampo);

//            /* campo disponibile per addebito fisso */
//            unCampo = CampoFactory.checkBox(AlbSottoconto.CAMPO_FISSO);
//            unCampo.setVisibileVistaDefault();
//            unCampo.decora().legenda("disponibile per addebiti fissi");
//            this.addCampo(unCampo);

//            /* campo tipo di prezzo (solo per addebito fisso) */
//            unCampo = CampoFactory.radioInterno(
//                    AlbSottoconto.CAMPO_TIPO_PREZZO);
//            unCampo.setVisibileVistaDefault();
//            unCampo.setValoriInterni(AlbSottoconto.Tipo.getElenco());
//            unCampo.setInit(
//                    InitFactory.intero(AlbSottoconto.Tipo.camera.getCodice()));
//            unCampo.decora().etichetta("tipo di prezzo");
//            this.addCampo(unCampo);

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
            /* crea la vista specifica (un solo campo) */
            super.addVista(VISTA_SIGLA, CAMPO_SIGLA);

            /* crea la vista specifica (un solo campo) */
            super.addVista(VISTA_DESCRIZIONE, CAMPO_DESCRIZIONE);

            /* vista all'interno del conto */
            vista = new Vista(AlbSottoconto.VISTA_PC_CONTO, this.getModulo());
            elem = vista.addCampo(Modello.NOME_CAMPO_SIGLA);
//            vista.addCampo(AlbSottoconto.CAMPO_FISSO);
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
        /* variabili e costanti locali di lavoro */
        Vista unaVista;
        Campo unCampo;

        try { // prova ad eseguire il codice
            unaVista = this.getVista(VISTA_SIGLA);
            unCampo = unaVista.getCampo(CAMPO_SIGLA);
            unCampo.getCampoLista().setRidimensionabile(false);
            unCampo.setTitoloColonna(COLONNA_SIGLA);

            unaVista = this.getVista(VISTA_DESCRIZIONE);
            unCampo = unaVista.getCampo(CAMPO_DESCRIZIONE);
            unCampo.getCampoLista().setRidimensionabile(false);
            unCampo.setTitoloColonna(COLONNA_DESCRIZIONE);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

//    /**
//     * Restituisce un estratto del tipo specificato.
//     * </p>
//     * E' una stringa costituita da descrizione
//     * descrizione del tipo prezzo (per camera / per persona) <br>
//     *
//     * @param estratto codifica dell'estratto desiderato
//     * @param codRec   codice del record sul quale effettuare la ricerca
//     *
//     * @return l'estratto costruito con la stringa ricavata
//     */
//    private EstrattoBase getEstrattoCP(Estratti estratto, Object codRec) {
//        /* variabili e costanti locali di lavoro */
//        EstrattoBase unEstratto = null;
//        EstrattoBase.Tipo tipoEst;
//        String testo="";
//        AlbSottoconto.Tipo tipo;
//        int codTipo;
//
//        try { // prova ad eseguire il codice
//            /* tipo di estratto codificato */
//            tipoEst = estratto.getTipo();
//
//            if (tipoEst == EstrattoBase.Tipo.STRINGA) {
//
//
//                /* recupera la descrizione del tipo */
//                codTipo = this.query().valoreInt(AlbSottoconto.CAMPO_TIPO_PREZZO, (Integer)codRec);
//                tipo = AlbSottoconto.Tipo.getTipo(codTipo);
//                if (tipo!=null) {
//                    testo = tipo.getTitolo();
//                }// fine del blocco if
//
//                /* crea l'estratto */
//                unEstratto = new EstrattoBase(testo, tipoEst);
//
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return unEstratto;
//    }

//    /**
//     * Restituisce un estratto del tipo specificato.
//     * </p>
//     * E' una stringa costituita da descrizione sottoconto +
//     * descrizione tipo prezzo (per camera / per persona) <br>
//     *
//     * @param estratto codifica dell'estratto desiderato
//     * @param codRec   codice del record sul quale effettuare la ricerca
//     *
//     * @return l'estratto costruito con la stringa ricavata
//     */
//    private EstrattoBase getEstrattoDCP(Estratti estratto, Object codRec) {
//        /* variabili e costanti locali di lavoro */
//        EstrattoBase unEstratto = null;
//        EstrattoBase.Tipo tipoEst;
//        String descSottoconto;
//        String descTipo=null;
//        String testo;
//        AlbSottoconto.Tipo tipo;
//        int codTipo;
//
//        try { // prova ad eseguire il codice
//            /* tipo di estratto codificato */
//            tipoEst = estratto.getTipo();
//
//            if (tipoEst == EstrattoBase.Tipo.STRINGA) {
//
//                /* recupera la descrizione del sottoconto */
//                descSottoconto = this.query().valoreStringa(AlbSottoconto.CAMPO_DESCRIZIONE, (Integer)codRec);
//
//                /* recupera la descrizione del tipo */
//                codTipo = this.query().valoreInt(AlbSottoconto.CAMPO_TIPO_PREZZO, (Integer)codRec);
//                tipo = AlbSottoconto.Tipo.getTipo(codTipo);
//                if (tipo!=null) {
//                    descTipo = tipo.getTitolo();
//                }// fine del blocco if
//
//
//                /* costruisce la stringa completa */
//                testo = descSottoconto;
//                if (Lib.Testo.isValida(descTipo)) {
//                    testo+=" - "+descTipo;
//                }// fine del blocco if
//
//                /* crea l'estratto */
//                unEstratto = new EstrattoBase(testo, tipoEst);
//
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return unEstratto;
//    }


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
            switch ((AlbSottoconto.Estratto)estratto) {
                case sigla:
                    unEstratto = this.getEstratto(estratto, chiave, AlbSottoconto.CAMPO_SIGLA);
                    break;
                case descrizione:
                    unEstratto = this.getEstratto(estratto,
                            chiave,
                            AlbSottoconto.CAMPO_DESCRIZIONE);
                    break;
//                case cameraPersona:
//                    unEstratto = this.getEstrattoCP(estratto, chiave);
//                    break;
//                case descrizioneCameraPersona:
//                    unEstratto = this.getEstrattoDCP(estratto, chiave);
//                    break;
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
