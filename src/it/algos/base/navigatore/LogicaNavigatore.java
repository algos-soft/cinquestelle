/**
 * Title:     LogicaNavigatore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      16-nov-2004
 */
package it.algos.base.navigatore;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CDIntero;
import it.algos.base.campo.dati.CDTesto;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.lista.TavolaModello;
import it.algos.base.modulo.Modulo;
import it.algos.base.portale.Portale;
import it.algos.base.portale.PortaleLista;
import it.algos.base.portale.PortaleScheda;
import it.algos.base.pref.Pref;
import it.algos.base.progetto.Progetto;
import it.algos.base.proiezione.Proiezione;
import it.algos.base.proiezione.ProiezioneBase;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.scheda.Scheda;
import it.algos.base.selezione.SelezioneModulo;
import it.algos.base.tavola.Tavola;

import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Logiche di gestione di un Navigatore.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 16-nov-2004 ore 14.36.34
 */
public class LogicaNavigatore {

    /**
     * navigatore di riferimento, che crea questa Logica
     */
    private Navigatore navigatore = null;

    /**
     * buffer di caratteri per la selezionme della lista
     */
    private String bufferLista = "";

    /**
     * ultima scrittura del buffer di caratteri della lista
     */
    private long timeBufferLista = 0;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public LogicaNavigatore() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unNavigatore navigatore di riferimento
     */
    public LogicaNavigatore(Navigatore unNavigatore) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setNavigatore(unNavigatore);

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
    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche. <br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Tasto carattere generico in una <code>Lista</code>.
     * <p/>
     * <p/>
     * (non usa il modificatore public, cosi' il metodo
     * viene visto solo all'interno del package) <br>
     *
     * @param unEvento evento che ha generato il comando
     */
    void listaCarattere(KeyEvent unEvento) {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Navigatore nav;
        Lista lista = null;
        Filtro filtro = null;
        int carCod = 0;
        String car = "";
        Campo campo = null;
        int attesa;
        Integer attesaInt;
        long tempoAttesa;
        long tempoAttuale;
        long tempoPrecedente;
        long tempoTrascorso;
        int[] codici;
        int codice;
        Ordine ordine;
        int num;
        char c = ' ';

        try { // prova ad eseguire il codice

            nav = this.getNavigatore();

            /* recupera dalle preferenze il tempo di attesa
             * tra un carattere ed il successivo */
            attesa = Pref.GUI.attesa.intero();
            attesaInt = new Integer(attesa);
            tempoAttesa = attesaInt.longValue();

            /* recupera la lista */
            lista = this.getLista();

            /* recupera il codice del carattere premuto */
            if (continua) {
//            carCod = unEvento.getKeyCode();
                c = unEvento.getKeyChar();
                car = Character.toString(c);
            }// fine del blocco if

            /* test che non sia un carattere di controllo */
            if (continua) {
                continua = !(lista.isCarattere(carCod));
            }// fine del blocco if

            /* recupera il campo corrente della lista */
            if (continua) {
                campo = lista.getCampoCorrente();
                continua = campo != null;
            }// fine del blocco if

            /* controlla che sia un carattere accettabile per i tipi di campi */
            if (continua) {
                continua = false;

                /* test per i tipi di campi accettabili */
                if (campo.getCampoDati() instanceof CDIntero) {
                    if ((c >= '0') && (c <= '9')) {
                        continua = true;
                    }// fine del blocco if-else
                }// fine del blocco if

                /* test per i tipi di campi accettabili */
                if (campo.getCampoDati() instanceof CDTesto) {
                    if (((c >= '0') && (c <= '9')) ||
                            (c >= 'A') && (c <= 'Z') ||
                            (c >= 'a') && (c <= 'z')) {
                        continua = true;
                    }// fine del blocco if-else
                }// fine del blocco if
            }// fine del blocco if

            /* controlla che sia un carattere accettabile */
            if (continua) {

                /* svuota il buffer dopo un certo tempo */
                tempoAttuale = System.currentTimeMillis();
                tempoPrecedente = this.timeBufferLista;
                tempoTrascorso = tempoAttuale - tempoPrecedente;
                if (tempoTrascorso > tempoAttesa) {
                    this.bufferLista = "";
                    this.timeBufferLista = tempoAttuale;
                }// fine del blocco if

                /* incrementa il buffer col carattere attuale */
                this.bufferLista += car;

                /* test per i tipi di campi accettabili */
                if (campo.getCampoDati() instanceof CDIntero) {
                    num = Libreria.getInt(bufferLista);
                    filtro = FiltroFactory.crea(campo, num);
                }// fine del blocco if

                /* test per i tipi di campi accettabili */
                if (campo.getCampoDati() instanceof CDTesto) {
                    filtro = FiltroFactory.crea(campo, Operatore.COMINCIA, bufferLista);
                }// fine del blocco if

                /* distingue le modalita' di selezione */
                int pos = Pref.GUI.selezione.intero();

                if (pos == Pref.TipoSel.corrispondenti.ordinal() + 1) {
                    lista.addFiltroCorrente(filtro);
                    lista.caricaSelezione();
                }// fine del blocco if

                if (pos == Pref.TipoSel.primo.ordinal() + 1) {
                    ordine = campo.getCampoLista().getOrdinePrivato();
                    codici = nav.query().valoriChiave(filtro, ordine);
                    if (codici.length > 0) {
                        codice = codici[0];
                        lista.setRecordVisibileSelezionato(codice);
                    }// fine del blocco if
                }// fine del blocco if

                /* sincronizza il navigatore */
                nav.sincronizza();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


//    /**
//     * Comando del mouse cliccato nei titoli.
//     * <p/>
//     * Sincronizza lo stato della GUI <br>
//     * <p/>
//     * (non usa il modificatore public, cos� il metodo
//     * viene visto solo all'interno del package) <br>
//     *
//     * @param unEvento evento generato dall'interfaccia utente
//     */
//    void colonnaCliccata(MouseEvent unEvento) {
//        /* variabili e costanti locali di lavoro */
//        Lista unaLista = null;
//
//        try { // prova ad eseguire il codice
//
//            /* recupera la lista */
//            unaLista = this.getLista();
//
//            if (unaLista.isOrdinabile()) {
//
//                /* invoca il metodo nella classe delegata */
//                unaLista.colonnaCliccata(unEvento);
//
//                /* sincronizza il navigatore */
//                this.getNavigatore().sincronizza();
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    /**
     * Entrata in una cella della lista (che riceve il fuoco).
     * <p/>
     * (non usa il modificatore public, così il metodo
     * viene visto solo all'interno del package) <br>
     */
    void entrataCella() {
        /* variabili e costanti locali di lavoro */
        Component comp = null;
        JTextComponent textComp = null;
        int codice = 0;
        Campo campo = null;

        try { // prova ad eseguire il codice
            comp = this.getEditorComponent();

            /* seleziona tutto il testo */
            if (comp instanceof JTextComponent) {
                textComp = (JTextComponent)comp;
                textComp.selectAll();
            }// fine del blocco if

//            if (comp instanceof Combo) {
//                textComp = (JTextComponent)comp;
//                textComp.selectAll();
//            }// fine del blocco if

            /* recupera i dati del record e del campo */
            codice = this.getLista().getRecordSelezionato();
            campo = this.getLista().aggiornaCampoInfuocato();

            /* invoca il metodo delegato in NavigatoreBase per eventuali sottoclassi */
            this.getNavigatore().entrataCella(codice, campo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Uscita da una cella della lista (che perde il fuoco).
     * <p/>
     * (non usa il modificatore public, cos� il metodo
     * viene visto solo all'interno del package) <br>
     */
    void uscitaCella() {
    }


    /**
     * Frecce pagina in una <code>Lista</code>.
     * <p/>
     * Sincronizza lo stato della GUI <br>
     * Le righe nella tavola partono da zero <br>
     * <p/>
     * (non usa il modificatore public, cos� il metodo
     * viene visto solo all'interno del package) <br>
     *
     * @param unEvento evento che ha generato il comando
     */
    void pagine(KeyEvent unEvento) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Tavola tavola = null;
        int prima = 0;
        int ultima = 0;
        int riga = 0;
        int righeScroll = 0;
        Rectangle retVis = null;
        Rectangle ret = null;
        int alt = 0;

        try { // prova ad eseguire il codice
            codice = unEvento.getKeyCode();
            tavola = this.getNavigatore().getPortaleLista().getLista().getTavola();
            riga = tavola.getSelectedRow();
            ultima = tavola.getRowCount() - 1;

            retVis = tavola.getVisibleRect();
            alt = retVis.height;
            righeScroll = alt / 16;

            if (codice == KeyEvent.VK_PAGE_UP) {
                if (riga > (prima + righeScroll)) {
                    riga -= righeScroll;
                } else {
                    riga = prima;
                }// fine del blocco if-else
            } else if (codice == KeyEvent.VK_PAGE_DOWN) {
                if (riga < (ultima - righeScroll)) {
                    riga += righeScroll;
                } else {
                    riga = ultima;
                }// fine del blocco if-else
            }// fine del blocco if

            tavola.setRowSelectionInterval(riga, riga);

            ret = tavola.getCellRect(riga, riga, true);
            tavola.scrollRectToVisible(ret);

            /* sincronizza il navigatore */
            this.getNavigatore().sincronizza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Freccia Home in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * Le righe nella tavola partono da zero <br>
     * <p/>
     * (non usa il modificatore public, cos� il metodo
     * viene visto solo all'interno del package) <br>
     */
    void home() {
        /* variabili e costanti locali di lavoro */
        Tavola tavola = null;
        int prima = 0;
        Rectangle ret = null;

        try { // prova ad eseguire il codice
            tavola = this.getNavigatore().getPortaleLista().getLista().getTavola();
            tavola.setRowSelectionInterval(prima, prima);

            ret = tavola.getCellRect(0, 0, true);
            tavola.scrollRectToVisible(ret);

            /* sincronizza il navigatore */
            this.getNavigatore().sincronizza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Freccia End in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * Le righe nella tavola partono da zero <br>
     * <p/>
     * (non usa il modificatore public, cos� il metodo
     * viene visto solo all'interno del package) <br>
     */
    void end() {
        /* variabili e costanti locali di lavoro */
        Tavola tavola = null;
        int ultima = 0;
        Rectangle ret = null;

        try { // prova ad eseguire il codice
            tavola = this.getNavigatore().getPortaleLista().getLista().getTavola();
            ultima = tavola.getRowCount() - 1;
            tavola.setRowSelectionInterval(ultima, ultima);

            ret = tavola.getCellRect(ultima, ultima, true);
            tavola.scrollRectToVisible(ret);

            /* sincronizza il navigatore */
            this.getNavigatore().sincronizza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ordina sulla colonna a sinistra in una <code>Lista</code>.
     * <p/>
     * Sincronizza lo stato della GUI <br>
     * Le colonne partono da 1 <br>
     * <p/>
     * (non usa il modificatore public, cos� il metodo
     * viene visto solo all'interno del package) <br>
     */
    void colonnaSinistra() {
        /* variabili e costanti locali di lavoro */
        Lista lista = null;

        try { // prova ad eseguire il codice
            /* recupera la lista */
            lista = this.getLista();

            /* invoca il metodo delegato della classe */
            lista.colonnaSinistra();

            /* sincronizza il navigatore */
            this.getNavigatore().sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ordina sulla colonna a destra in una <code>Lista</code>.
     * <p/>
     * Sincronizza lo stato della GUI <br>
     * Le colonne partono da 1 <br>
     * <p/>
     * (non usa il modificatore public, cos� il metodo
     * viene visto solo all'interno del package) <br>
     */
    void colonnaDestra() {
        /* variabili e costanti locali di lavoro */
        Lista lista = null;

        try { // prova ad eseguire il codice
            /* recupera la lista */
            lista = this.getLista();

            /* invoca il metodo delegato della classe */
            lista.colonnaDestra();

            /* sincronizza il navigatore */
            this.getNavigatore().sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Modifica di un campo.
     * </p>
     * Controlla la validit� del Campo <br>
     * Aggiorna il campo (da GUI a memoria e poi di nuovo a GUI) <br>
     * Sincronizza il navigatore <br>
     * <p/>
     * (non usa il modificatore public, cos� il metodo
     * viene visto solo all'interno del package) <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    void modificaCampo(Campo unCampo) {
        try { // prova ad eseguire il codice
            if (unCampo.isValido()) {
                unCampo.aggiornaCampo();
            }// fine del blocco if
            this.getNavigatore().sincronizza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Uscita dal popup di un ComboBox (che diventa invisibile).
     * <p/>
     * Aggiorna il campo (da GUI a memoria e poi di nuovo a GUI) <br>
     * Invoca il metodo delegato della classe <br>
     * Sincronizza il navigatore <br>
     * <p/>
     * (non usa il modificatore public, cos� il metodo
     * viene visto solo all'interno del package) <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    void uscitaPopup(Campo unCampo) {
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
//            unCampo.aggiornaCampo();
//            ((CVCombo)unCampo.getCampoVideoNonDecorato()).uscitaPopup();
//            this.getNavigatore().sincronizza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Modifica della selezione di una lista </li>
     * <p/>
     * (non usa il modificatore public, cos� il metodo
     * viene visto solo all'interno del package) <br>
     */
    void selezioneModificata() {

        /* variabili e costanti locali di lavoro */
        Navigatore navPilotato;
        boolean valoriCambiati;

        try { // prova ad eseguire il codice

            /*
             * se ha un navigatore pilotato:
             * - ne regola i valori pilota, e se questi sono cambiati:
             *   - avvia il navigatore pilotato (ricorsivo)
             */
            navPilotato = this.getNavigatore().getNavPilotato();
            if (navPilotato != null) {
                valoriCambiati = navPilotato.regolaValoriPilota();
                if (valoriCambiati) {
                    navPilotato.avvia();
                }// fine del blocco if
            }// fine del blocco if

            this.getNavigatore().sincronizza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Modifica dello stato di un bottone.
     * </p>
     * Metodo invocato dal Gestore Eventi <br>
     * Aggiorna il campo (da GUI a memoria e poi di nuovo a GUI) <br>
     * Sincronizza il navigatore <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void statoBottoneModificato(Campo unCampo) {
        try { // prova ad eseguire il codice
            unCampo.aggiornaCampo();
            this.getNavigatore().sincronizza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione proietta.
     * <p/>
     * Apre un dialogo di proiezione dati <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public void proietta() {
        /* variabili e costanti locali di lavoro */
        Proiezione proiezione = null;

        try { // prova ad eseguire il codice

            /* crea un dialogo di proiezione per il modulo */
            proiezione = new ProiezioneBase(this.getModulo());

            /* presenta il dialogo */
            proiezione.presentaDialogo();

            if (proiezione.isConfermato()) {
                int a = 87;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Bottone salva la selezione della <code>Lista</code>.
     * <p/>
     * Sincronizza lo stato della GUI <br>
     * <p/>
     * (non usa il modificatore public, cos� il metodo
     * viene visto solo all'interno del package) <br>
     */
    void salvaSelezioneEsterna() {
        /* variabili e costanti locali di lavoro */
        int[] codici = null;
        String nomeModulo = "";
        SelezioneModulo modulo = null;

        try { // prova ad eseguire il codice

            /* recupera i valori */
            nomeModulo = this.getModulo().getNomeChiave();
            codici = this.getLista().getChiaviVisualizzate();
            modulo = Progetto.getModuloSelezione();

            /* invoca il metodo delegato della classe */
            modulo.salvaSelezioneEsterna(nomeModulo, codici);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone carica la selezione nella <code>Lista</code>.
     * <p/>
     * Sincronizza lo stato della GUI <br>
     * <p/>
     * (non usa il modificatore public, cos� il metodo
     * viene visto solo all'interno del package) <br>
     */
    void caricaSelezioneEsterna() {
        /* variabili e costanti locali di lavoro */
        String nomeModulo = "";
        SelezioneModulo modulo = null;
        int[] codici = null;

        try { // prova ad eseguire il codice
            /* recupera i valori */
            nomeModulo = this.getModulo().getNomeChiave();
            modulo = Progetto.getModuloSelezione();

            /* invoca il metodo delegato della classe */
            codici = modulo.caricaSelezioneEsterna(nomeModulo);

            /* sincronizza il navigatore */
            if (codici != null) {
                this.getLista().caricaSelezione(codici);

                this.getNavigatore().sincronizza();

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione duplica record selezionato nella Lista.
     * <p/>
     * Metodo invocato dal Navigatore <br>
     *
     * @return true se eseguito correttamente
     */
    boolean duplicaRecord() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        int codice = 0;
        int codDuplicato = 0;
        Navigatore nav;
        Lista lista;

        try { // prova ad eseguire il codice

            eseguito = true;
            nav = this.getNavigatore();

            /* recupera la chiave dalla lista e controlla che sia valida */
            if (eseguito) {
                codice = this.getChiaveMaster();
                if (codice < 1) {
                    eseguito = false;
                }// fine del blocco if
            }// fine del blocco if

            /* delega al modulo la duplicazione del record */
            if (eseguito) {
                codDuplicato = nav.query().duplicaRecord(codice);
                if (codDuplicato <= 0) {
                    eseguito = false;
                }// fine del blocco if
            }// fine del blocco if

            /* seleziona il record duplicato in area visibile */
            if (eseguito) {
                lista = nav.getLista();
                if (lista != null) {
                    nav.aggiornaLista();
                    lista.setRecordVisibileSelezionato(codDuplicato);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Controlla la possibilità di spostamento della scheda.
     *
     * @param codice il codice del record corrente nella scheda
     * @param spostamento direzione di spostamento desiderata
     *
     * @return true se e' spossibile spostare il record della scheda
     *         nella direzione richiesta
     */
    public boolean isSchedaSpostabile(int codice, Dati.Spostamento spostamento) {
        /* variabili e costanti locali di lavoro */
        boolean spostabile = false;
        boolean continua;
        Lista lista = null;
        int posizione = 0;
        int quanti = 0;
        int pos;

        try { // prova ad eseguire il codice

            /* se il codice e' zero, ritorna subito false */
            continua = codice > 0;

            if (continua) {
                lista = this.getLista();
                continua = lista != null;
            }// fine del blocco if


            if (continua) {
                quanti = lista.getModello().getRowCount();
//                posizione = lista.getModello().posizioneRelativa(codice);
                posizione = lista.getModello().posizione();
            }// fine del blocco if


            if (continua) {
                switch (posizione) {
                    case Dati.POSIZIONE_PRIMO:    // e' il primo in lista
                        switch (spostamento) {
                            case primo:   // voglio andare al primo
                                spostabile = false;
                                break;
                            case precedente:   // voglio andare al precedente
                                spostabile = false;
                                break;
                            case successivo:   // voglio andare al successivo
                                if (quanti > 1) {
                                    spostabile = true;
                                }// fine del blocco if
                                break;
                            case ultimo:   // voglio andare all'ultimo
                                if (quanti > 1) {
                                    spostabile = true;
                                }// fine del blocco if
                                break;
                            default: // caso non definito
                                spostabile = false;
                                break;
                        } // fine del blocco switch
                        break;
                    case Dati.POSIZIONE_INTERMEDIO:   // e' in mezzo alla lista
                        switch (spostamento) {
                            case primo:   // voglio andare al primo
                                spostabile = true;
                                break;
                            case precedente:   // voglio andare al precedente
                                spostabile = true;
                                break;
                            case successivo:   // voglio andare al successivo
                                spostabile = true;
                                break;
                            case ultimo:   // voglio andare all'ultimo
                                spostabile = true;
                                break;
                            default: // caso non definito
                                spostabile = false;
                                break;
                        } // fine del blocco switch
                        break;
                    case Dati.POSIZIONE_ULTIMO:   // e' l'ultimo della lista
                        switch (spostamento) {
                            case primo:   // voglio andare al primo
                                if (quanti > 1) {
                                    spostabile = true;
                                }// fine del blocco if
                                break;
                            case precedente:   // voglio andare al precedente
                                if (quanti > 1) {
                                    spostabile = true;
                                }// fine del blocco if
                                break;
                            case successivo:   // voglio andare al successivo
                                spostabile = false;
                                break;
                            case ultimo:   // voglio andare all'ultimo
                                spostabile = false;
                                break;
                            default: // caso non definito
                                spostabile = false;
                                break;
                        } // fine del blocco switch
                        break;
                    case Dati.POSIZIONE_ASSENTE:    // non e' nella lista
                        spostabile = false;
                        break;
                    default: // caso non definito
                        spostabile = false;
                        break;
                } // fine del blocco switch
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return spostabile;
    }


    /**
     * Restituisce il codice chiave della riga selezionata sulla lista Master.
     *
     * @return codice chiave
     */
    int getChiaveMaster() {
        /* variabili e costanti locali di lavoro */
        int chiave = 0;
        boolean continua = true;
        Navigatore nav;
        PortaleLista pl = null;

        try {    // prova ad eseguire il codice

            nav = this.getNavigatore();
            continua = (nav != null);

            if (continua) {
                pl = nav.getPortaleLista();
                continua = (pl != null);
            }// fine del blocco if

            if (continua) {
                chiave = pl.getRecordSelezionato();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Ritorna la Scheda del navigatore gestito.<br>
     *
     * @return la scheda
     */
    private Scheda getScheda() {
        /* variabili e costanti locali di lavoro */
        Scheda scheda = null;
        PortaleScheda ps = null;

        try { // prova ad eseguire il codice
            ps = this.getPortaleScheda();
            if (ps != null) {
                scheda = ps.getSchedaCorrente();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return scheda;
    } // fine del metodo


    /**
     * Restituisce la lista corrente.
     * <p/>
     * Recupera il navigatore corrente e da questo la Lista <br>
     *
     * @return la lista del navigatore corrente
     */
    private Lista getLista() {
        /* variabili e costanti locali di lavoro */
        Navigatore unNavigatore = null;
        Portale unPortale = null;
        Lista unaLista = null;

        try { // prova ad eseguire il codice
            /* recupera il riferimento alla Navigatore */
            unNavigatore = this.getNavigatore();

            /* recupera il riferimento al Portale */
            if (unNavigatore != null) {
                unPortale = unNavigatore.getPortaleLista();
            }// fine del blocco if

            /* recupera il riferimento alla Lista */
            if (unPortale != null) {
                unaLista = unPortale.getLista();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unaLista;
    }


    /**
     * Ritorna il modello dei dati della Lista master.
     *
     * @return il modello dati della lista master
     */
    private TavolaModello getListaModello() {
        return this.getLista().getModello();
    }


    /**
     * Ritorna il portale Lista del Navigatore.
     * <p/>
     *
     * @return il portale Lista
     */
    private PortaleLista getPortaleLista() {
        /* variabili e costanti locali di lavoro */
        PortaleLista pl = null;
        Navigatore nav = null;

        try { // prova ad eseguire il codice
            nav = this.getNavigatore();
            if (nav != null) {
                pl = nav.getPortaleLista();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pl;
    }


    /**
     * Ritorna il portale Scheda del Navigatore.
     * <p/>
     *
     * @return il portale Scheda
     */
    private PortaleScheda getPortaleScheda() {
        /* variabili e costanti locali di lavoro */
        PortaleScheda ps = null;
        Navigatore nav = null;

        try { // prova ad eseguire il codice
            nav = this.getNavigatore();
            if (nav != null) {
                ps = nav.getPortaleScheda();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ps;
    }


    public Modulo getModulo() {
        return this.navigatore.getModulo();
    }


    public Navigatore getNavigatore() {
        return navigatore;
    }


    private void setNavigatore(Navigatore navigatore) {
        this.navigatore = navigatore;
    }


    /**
     * Recupera dalla tavola il componente da editare.
     * <p/>
     */
    private Component getEditorComponent() {
        /* variabili e costanti locali di lavoro */
        Tavola tavola = null;
        Component comp = null;

        try { // prova ad eseguire il codice
            tavola = this.getLista().getTavola();
            if (tavola != null) {
                comp = tavola.getEditorComponent();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Azione importa.
     * <p/>
     * Apre una finestra di importazione <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    void importa() {
    }

}// fine della classe
