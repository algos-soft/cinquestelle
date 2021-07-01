/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      31-mag-2006
 */
package it.algos.albergo.listino;

import it.algos.albergo.listino.rigalistino.RigaListino;
import it.algos.albergo.pianodeicontialbergo.sottoconto.AlbSottoconto;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.modello.Modello;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.tavola.Tavola;
import it.algos.base.tavola.renderer.RendererElenco;
import it.algos.base.tavola.renderer.RendererNumero;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.WrapFiltri;

import javax.swing.JTable;
import java.awt.Component;
import java.util.ArrayList;

/**
 * Tracciato record della tavola Listino.
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
 * @version 1.0 / 2 feb 2006
 */
public class ListinoModello extends ModelloAlgos implements Listino {


    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;

    /**
     * Testo della colonna della Lista come appare nella Vista
     */
    private static final String COLONNA_SIGLA = "sigla";

    /**
     * Testo della colonna della Lista come appare nella Vista
     */
    private static final String COLONNA_DESCRIZIONE = "descrizione";

    /**
     * Testo della colonna della Lista come appare nella Vista
     */
    private static final String COLONNA_SOTTOCONTO = "conto";

    /**
     * Testo della legenda sotto il campo sigla nella scheda
     */
    private static final String LEGENDA_SIGLA = "ad uso interno";

    /**
     * Testo della legenda sotto il campo descrizione nella scheda
     */
    private static final String LEGENDA_DESCRIZIONE = "stampata sul conto cliente";

    /**
     * Testo del campo sottoconto nella scheda
     */
    private static final String TESTO_SOTTOCONTO = "sottoconto";

    /**
     * Testo della legenda sotto il campo scheda
     */
    private static final String LEGENDA_SOTTOCONTO = "sottoconto del piano dei conti";


    private RendererTipo rendererTipo;

    private RendererPrezzo rendererPrezzo;


    /**
     * Costruttore completo senza parametri.
     */
    public ListinoModello() {
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
        super.setTavolaArchivio(ListinoModello.TAVOLA_ARCHIVIO);
    }


    public boolean inizializza(Modulo unModulo) {
        /* variabili e costanti locali di lavoro */
        boolean flag = false;

        try { // prova ad eseguire il codice
            flag = super.inizializza(unModulo);

            /* inizializza i renderer specifici */
            this.getRendererTipo().inizializza(this);
            this.getRendererPrezzo().inizializza(this);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return flag;
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
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo sigla */
            unCampo = CampoFactory.testo(Cam.sigla);
            unCampo.getCampoLista().setRidimensionabile(true);
            unCampo.setLarghezza(120);
            unCampo.setVisibileVistaDefault();
            unCampo.decora().legenda(ListinoModello.LEGENDA_SIGLA);
            this.addCampo(unCampo);

            /* campo descrizione */
            unCampo = CampoFactory.descrizione();
            unCampo.setTitoloColonna(ListinoModello.COLONNA_DESCRIZIONE);
            unCampo.setLarghezza(280);
            unCampo.setVisibileVistaDefault();
            unCampo.decora().legenda(ListinoModello.LEGENDA_DESCRIZIONE);
            this.addCampo(unCampo);

            /* campo sottoconto */
            unCampo = CampoFactory.comboLinkSel(Cam.sottoconto);
            unCampo.setNomeModuloLinkato(AlbSottoconto.NOME_MODULO);
            unCampo.decora().obbligatorio();
            unCampo.setUsaNuovo(true);
            unCampo.setNomeColonnaListaLinkata(AlbSottoconto.CAMPO_SIGLA);
            unCampo.setTitoloColonna(ListinoModello.COLONNA_SOTTOCONTO);
            unCampo.decora().etichetta(ListinoModello.TESTO_SOTTOCONTO);
            unCampo.decora().estrattoSotto(AlbSottoconto.Estratto.descrizione);
            this.addCampo(unCampo);

            /* campo ambito prezzo (pensione o extra) */
            unCampo = CampoFactory.comboInterno(Cam.ambitoPrezzo);
            unCampo.setValoriInterni(Listino.AmbitoPrezzo.values());
            unCampo.decora().obbligatorio();
            unCampo.setUsaNonSpecificato(true);
            this.addCampo(unCampo);

            /* campo tipo prezzo (per persona o per camera) */
            unCampo = CampoFactory.comboInterno(Cam.tipoPrezzo);
            this.setRendererTipo(new RendererTipo(unCampo));
            unCampo.setRenderer(this.getRendererTipo());
            unCampo.setValoriInterni(Listino.TipoPrezzo.getElenco());
            unCampo.decora().obbligatorio();
            unCampo.setUsaNonSpecificato(true);
            this.addCampo(unCampo);

            /* campo modo prezzo (fisso o variabile a periodo) */
            unCampo = CampoFactory.comboInterno(Cam.modoPrezzo);
            unCampo.setValoriInterni(Listino.ModoPrezzo.getElenco());
            unCampo.decora().obbligatorio();
            unCampo.setUsaNonSpecificato(true);
            this.addCampo(unCampo);

            /* campo record disponibile per addebiti gionalieri */
            unCampo = CampoFactory.checkBox(Cam.giornaliero);
            this.addCampo(unCampo);

            /* campo record disattivato */
            unCampo = CampoFactory.checkBox(Cam.disattivato);
            unCampo.setTestoComponente("disattivato");
            this.addCampo(unCampo);

            /* campo prezzo */
            unCampo = CampoFactory.valuta(Cam.prezzo);
            this.setRendererPrezzo(new RendererPrezzo(unCampo));
            unCampo.setRenderer(this.getRendererPrezzo());
            this.addCampo(unCampo);

            /* campo navigatore righe listino */
            unCampo = CampoFactory.navigatore(Cam.righe,
                    RigaListino.NOME_MODULO,
                    RigaListino.Nav.navinlistino.toString());
            this.addCampo(unCampo);

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
     * @see it.algos.base.progetto.Progetto#preparaModuli()
     * @see #regolaViste
     */
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista;

        try { // prova ad eseguire il codice

            /* crea la vista specifica (un solo campo) */
            super.addVista(VISTA_SIGLA, Cam.sigla.get());

            /* crea la vista specifica (un solo campo) */
            super.addVista(VISTA_DESCRIZIONE, Cam.descrizione.get());

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
        Campo campo;
        Campo campoVista;
        Campo unCampo;
        Modulo modulo;

        try { // prova ad eseguire il codice

            /* regolo il titolo di colonna del campo sottoconto
             * nella vista di defautl */
            unaVista = this.getVistaDefault();
            modulo = Progetto.getModulo(AlbSottoconto.NOME_MODULO);
            campo = modulo.getCampo(AlbSottoconto.CAMPO_SIGLA);
            campoVista = unaVista.getCampo(campo);
            campoVista.setTitoloColonna("sottoconto");

            unaVista = this.getVista(VISTA_SIGLA);
            unCampo = unaVista.getCampo(Cam.sigla.get());
            unCampo.getCampoLista().setRidimensionabile(false);
            unCampo.setTitoloColonna(ListinoModello.COLONNA_SIGLA);

            unaVista = this.getVista(VISTA_DESCRIZIONE);
            unCampo = unaVista.getCampo(Cam.descrizione.get());
            unCampo.getCampoLista().setRidimensionabile(false);
            unCampo.setTitoloColonna(ListinoModello.COLONNA_DESCRIZIONE);


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
    @Override protected void regolaFiltriPop() {
        /* variabili e costanti locali di lavoro */
        WrapFiltri filtroPop;
        Filtro filtro;

        try { // prova ad eseguire il codice

            /* crea un popup di filtri su ambito prezzo */
            filtroPop = super.addPopFiltro();
            filtro = FiltroFactory.crea(Cam.ambitoPrezzo.get(),
                    AmbitoPrezzo.pensioniComplete.getCodice());
            filtroPop.add(filtro, AmbitoPrezzo.pensioniComplete.getTitolo());
            filtro = FiltroFactory.crea(Cam.ambitoPrezzo.get(),
                    AmbitoPrezzo.mezzePensioni.getCodice());
            filtroPop.add(filtro, AmbitoPrezzo.mezzePensioni.getTitolo());
            filtro = FiltroFactory.crea(Cam.ambitoPrezzo.get(),
                    AmbitoPrezzo.pernottamenti.getCodice());
            filtroPop.add(filtro, AmbitoPrezzo.pernottamenti.getTitolo());
            filtro = FiltroFactory.crea(Cam.ambitoPrezzo.get(), AmbitoPrezzo.altro.getCodice());
            filtroPop.add(filtro, AmbitoPrezzo.altro.getTitolo());
            filtro = FiltroFactory.crea(Cam.ambitoPrezzo.get(), AmbitoPrezzo.extra.getCodice());
            filtroPop.add(filtro, AmbitoPrezzo.extra.getTitolo());
            filtroPop.setTitolo("Pensione/extra");
//            filtro = FiltroFactory.crea(Cam.ambitoPrezzo.get(), AmbitoPrezzo.entrambi.getCodice());
//            filtroPop.add(filtro, AmbitoPrezzo.entrambi.getTitolo());

            /* crea un popup di filtri su tipo prezzo */
            filtroPop = super.addPopFiltro();
            filtro = FiltroFactory.crea(Cam.tipoPrezzo.get(), TipoPrezzo.persona.getCodice());
            filtroPop.add(filtro, TipoPrezzo.persona.getTitolo());
            filtro = FiltroFactory.crea(Cam.tipoPrezzo.get(), TipoPrezzo.camera.getCodice());
            filtroPop.add(filtro, TipoPrezzo.camera.getTitolo());
            filtroPop.setTitolo("Persona/camera");

            /* crea un popup di filtri su modo prezzo */
            filtroPop = super.addPopFiltro();
            filtro = FiltroFactory.crea(Cam.modoPrezzo.get(), ModoPrezzo.fisso.getCodice());
            filtroPop.add(filtro, ModoPrezzo.fisso.getTitolo());
            filtro = FiltroFactory.crea(Cam.modoPrezzo.get(), ModoPrezzo.variabile.getCodice());
            filtroPop.add(filtro, ModoPrezzo.variabile.getTitolo());
            filtroPop.setTitolo("Fisso/variabile");

            /* crea un popup di filtri su flag giornalliero */
            filtroPop = super.addPopFiltro();
            filtro = FiltroFactory.crea(Cam.giornaliero.get(), true);
            filtroPop.add(filtro, "giornaliero");
            filtro = FiltroFactory.crea(Cam.giornaliero.get(), false);
            filtroPop.add(filtro, "non giornaliero");
            filtroPop.setTitolo("Giornaliero/non giornaliero");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Regola i valori dei campi per un nuovo record.
     * <p/>
     * Invocato prima della registrazione.
     * Permette di modificare i campi e i valori che stanno per essere registrati<br>
     * Viene sovrascritto dalle classi specifiche <br>
     * Le eventuali modifiche vanno fatte sulla lista che viene
     * passata come parametro.
     *
     * @param lista array coppia campo-valore contenente i
     * dati che stanno per essere registrati
     *
     * @return true per continuare il processo di registrazione,
     *         false per non effettuare la registrazione
     */
    protected boolean nuovoRecordAnte(ArrayList<CampoValore> lista, Listino.ModoPrezzo tipo) {
        /* variabili e costanti locali di lavoro */
        Campo campoTipo;

        try { // prova ad eseguire il codice
            campoTipo = this.getCampo(Cam.modoPrezzo.get());

            /* traverso tutta la collezione */
            for (CampoValore campo : lista) {
                if (campo.getCampo().equals(campoTipo)) {
                    campo.setValore(tipo.getCodice());
                    break;
                }// fine del blocco if
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } // fine del metodo


    /**
     * Restituisce un estratto del tipo specificato.
     * </p>
     * E' una stringa costituita da descrizione listino + descrizione
     * del tipo di prezzo (per camera / per persona, dal sottoconto) <br>
     *
     * @param estratto codifica dell'estratto desiderato
     * @param codListino codice del record del listino sul quale effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstrattoDCP(Estratti estratto, Object codListino) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;
        EstrattoBase.Tipo tipoEst;
        int codLis;
        int codAmbito;
        int codTipo;
        String descListino;
        String descCP = "";
        String stringa;

        try { // prova ad eseguire il codice

            /* tipo di estratto codificato */
            tipoEst = estratto.getTipo();

            if (tipoEst == EstrattoBase.Tipo.stringa) {

                codLis = (Integer)codListino;

                /* recupera la descrizione del listino */
                unEstratto = this.getEstratto(Listino.Estratto.descrizione, codLis);
                descListino = unEstratto.getStringa();

                /* recupera la descrizione del tipo dal listino (se non è extra) */
                codAmbito = this.query().valoreInt(Cam.ambitoPrezzo.get(), codLis);
                if (codAmbito != AmbitoPrezzo.extra.getCodice()) {
                    codTipo = this.query().valoreInt(Cam.tipoPrezzo.get(), codLis);
                    descCP = TipoPrezzo.getDescrizione(codTipo);
                }// fine del blocco if

                /* costruisce la stringa completa */
                stringa = descListino;
                if (Lib.Testo.isValida(descCP)) {
                    stringa += " - ";
                    stringa += descCP;
                }// fine del blocco if

                /* costruisce l'estratto da ritornare */
                unEstratto = new EstrattoBase(stringa, tipoEst);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
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
            switch ((Listino.Estratto)estratto) {
                case sigla:
                    unEstratto = this.getEstratto(estratto, chiave, Cam.sigla.get());
                    break;
                case descrizione:
                    unEstratto = this.getEstratto(estratto, chiave, Cam.descrizione.get());
                    break;
                case descrizioneCameraPersona:
                    unEstratto = this.getEstrattoDCP(estratto, chiave);
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


    /**
     * Renderer per il campo Tipo nella lista.
     * <p/>
     */
    private final class RendererTipo extends RendererElenco {

        private Campo campoOsservato;


        /**
         * Costruttore completo con parametri. <br>
         */
        public RendererTipo(Campo campo) {
            /* rimanda al costruttore della superclasse */
            super(campo);
        }// fine del metodo costruttore completo


        /**
         * Inizializzazione dell'oggetto
         */
        private void inizializza(Modello modello) {
            try { // prova ad eseguire il codice
                campoOsservato = modello.getCampo(Cam.ambitoPrezzo.get());
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public Component getTableCellRendererComponent(JTable jTable,
                                                       Object object,
                                                       boolean flag,
                                                       boolean flag1,
                                                       int row,
                                                       int col) {

            /* variabili e costanti locali di lavoro */
            Tavola tavola;
            Lista lista;
            int indice;
            Object valore;

            /* se è un Extra, regola l'oggetto da visualizzare a nullo */
            if (jTable instanceof Tavola) {
                tavola = (Tavola)jTable;
                lista = tavola.getLista();
                indice = lista.getColonna(campoOsservato);
                valore = tavola.getValueAt(row, indice);
                if (Libreria.getInt(valore) == AmbitoPrezzo.extra.getCodice()) {
                    object = null;
                } else {
//                    object = new Integer(2);
                }// fine del blocco if-else


            }// fine del blocco if

            /* valore di ritorno */
            return super.getTableCellRendererComponent(jTable, object, flag, flag1, row, col);
        }

    } // fine della classe 'interna'


    /**
     * Renderer per il campo Prezzo nella lista.
     * <p/>
     */
    private final class RendererPrezzo extends RendererNumero {

        private Campo campoOsservato;


        /**
         * Costruttore completo con parametri. <br>
         */
        public RendererPrezzo(Campo campo) {
            /* rimanda al costruttore della superclasse */
            super(campo);
        }// fine del metodo costruttore completo


        /**
         * Inizializzazione dell'oggetto
         */
        private void inizializza(Modello modello) {
            try { // prova ad eseguire il codice
                campoOsservato = modello.getCampo(Cam.modoPrezzo.get());
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public Component getTableCellRendererComponent(JTable jTable,
                                                       Object object,
                                                       boolean flag,
                                                       boolean flag1,
                                                       int row,
                                                       int col) {

            /* variabili e costanti locali di lavoro */
            Tavola tavola;
            Lista lista;
            int indice;
            Object valore;

            /* se il prezzo è variabile, regola l'oggetto da visualizzare a nullo */
            if (jTable instanceof Tavola) {
                tavola = (Tavola)jTable;
                lista = tavola.getLista();
                indice = lista.getColonna(campoOsservato);
                valore = tavola.getValueAt(row, indice);
                if (Libreria.getInt(valore) == ModoPrezzo.variabile.getCodice()) {
                    object = null;
                }// fine del blocco if
            }// fine del blocco if

            /* valore di ritorno */
            return super.getTableCellRendererComponent(jTable, object, flag, flag1, row, col);
        }

    } // fine della classe 'interna'


    private RendererTipo getRendererTipo() {
        return rendererTipo;
    }


    private void setRendererTipo(RendererTipo rendererTipo) {
        this.rendererTipo = rendererTipo;
    }


    private RendererPrezzo getRendererPrezzo() {
        return rendererPrezzo;
    }


    private void setRendererPrezzo(RendererPrezzo rendererPrezzo) {
        this.rendererPrezzo = rendererPrezzo;
    }


} // fine della classe
