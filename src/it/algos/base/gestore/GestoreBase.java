/**
 * Title:     GestoreBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      2-apr-2004
 */
package it.algos.base.gestore;

import it.algos.base.aggiornamento.Aggiornamento;
import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoBase;
import it.algos.base.errore.Errore;
import it.algos.base.finestra.Finestra;
import it.algos.base.lista.Lista;
import it.algos.base.lista.ListaBase;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.PannelloCampo;
import it.algos.base.pannello.PannelloComponenti;
import it.algos.base.portale.Portale;
import it.algos.base.portale.PortaleBase;
import it.algos.base.progetto.Progetto;
import it.algos.base.tavola.Tavola;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

/**
 * Flusso degli eventi e business logic.
 * </p>
 * Questa classe: <ul>
 * <li> Intercetta ogni <strong>evento</strong> generato dalla GUI o dal sistema </li>
 * <li> Ogni <code>Azione</code> genera un evento </li>
 * <li> Ogni <code>Azione</code> invoca un metodo differente di questa classe </li>
 * <li> Alcuni metodi gestiscono direttamente una business-logic </li>
 * <li> Alcuni altri metodi invocano (corrispondenti) metodi di classi delegate:
 * principalmente <code>Navigatore</code> e  <code>LogicaNavigatore</code> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 2-apr-2004 ore 11.28.54
 */
public class GestoreBase implements Gestore {


    /**
     * Costruttore completo senza parametri.
     */
    public GestoreBase() {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Recupera il Navigatore che ha generato un evento.
     * <br>
     * Recupera il componente che ha generato l'evento
     * Risale tutta la gerarchia degli oggetti <i>parente</i>, cioe' i
     * componente che contengono il componente, fino a che trova un oggetto
     * di classe <code>Navigatore</code> oppure termina la catena di
     * componenti.
     * Se ha trovato un <code>Navigatore</code>, lo restituisce.
     *
     * @param unEvento evento ricevuto
     *
     * @return il navigatore che ha generato l'evento,
     *         null se non riesce a trovarlo
     */
    protected Navigatore getNavigatoreEvento(EventObject unEvento) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore = null;
        Object sorgente;
        Component componente;
        Portale portale = null;
        JMenuItem menu;
        Azione azione = null;
        DefaultListSelectionModel lista;
        ListSelectionListener[] listener;
        Tavola tavola = null;

        try {    // prova ad eseguire il codice

            sorgente = unEvento.getSource();
            if (sorgente instanceof Component) {
                componente = (Component)sorgente;

                if (sorgente instanceof JMenuItem) {
                    menu = (JMenuItem)componente;
                    azione = (Azione)menu.getAction();
                }// fine del blocco if

                if (azione != null) {
                    portale = azione.getAzione().getPortale();
                }// fine del blocco if

                while ((componente != null) && ((componente instanceof Portale) == false)) {
                    componente = componente.getParent();
                }// fine del blocco while

                if (componente instanceof Portale) {
                    portale = (Portale)componente;
                }// fine del blocco if

                if (portale != null) {
                    navigatore = portale.getNavigatore();
                }// fine del blocco if
            }// fine del blocco if

            if (sorgente instanceof DefaultListSelectionModel) {
                lista = (DefaultListSelectionModel)sorgente;
                listener = lista.getListSelectionListeners();

                /* cerca la tavola nei listener */
                ListSelectionListener unListener = null;
                for (int k = 0; k < listener.length; k++) {
                    unListener = listener[k];
                    if (unListener instanceof Tavola) {
                        tavola = (Tavola)unListener;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for

//                tavola = (Tavola)listener[1];
                navigatore = tavola.getLista().getPortale().getNavigatore();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return navigatore;
    }


    /**
     * Recupera il campo che ha generato un evento.
     * <p/>
     * Recupera il componente che ha generato l'evento
     * Risale tutta la gerarchia degli oggetti <i>parente</i>, cioe' i
     * componente che contengono il componente, fino a che trova un oggetto
     * di classe <code>PannelloCampo</code> oppure termina la catena di
     * componenti.
     * Se ha trovato un <code>PannelloCampo</code>, recupera il campo.
     *
     * @param unEvento evento ricevuto
     *
     * @return il campo che contiene il componente GUI che ha generato l'evento,
     *         null se non riesce a trovare il campo
     */
    protected Campo getCampoEvento(EventObject unEvento) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        Object sorgente;
        Component componente;
        PannelloCampo pannelloCampo;
        PannelloComponenti pan;


        try {    // prova ad eseguire il codice

            sorgente = unEvento.getSource();
            componente = (Component)sorgente;

            while ((componente != null) && (!(componente instanceof PannelloComponenti))) {
                componente = componente.getParent();
            }// fine del blocco while

            if (componente instanceof PannelloComponenti) {
//                pannelloCampo = (PannelloCampo) componente;
//                unCampo = pannelloCampo.getCampo();

                pan = (PannelloComponenti)componente;
                unCampo = pan.getCampo();

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }// fine del metodo


    /**
     * Recupera il portale che ha generato un evento.
     *
     * @param unEvento evento ricevuto
     *
     * @return la lista che contiene il componente GUI che ha generato l'evento,
     *         null se non riesce a trovare la lista
     */
    protected Portale getPortaleEvento(EventObject unEvento) {
        /* variabili e costanti locali di lavoro */
        Portale portale = null;
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                portale = navigatore.getPortaleLista();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return portale;
    }// fine del metodo


    /**
     * Recupera la lista che ha generato un evento.
     *
     * @param unEvento evento ricevuto
     *
     * @return la lista che contiene il componente GUI che ha generato l'evento,
     *         null se non riesce a trovare la lista
     */
    protected Lista getListaEvento(EventObject unEvento) {
        /* variabili e costanti locali di lavoro */
        Lista lista = null;
        Portale portale;

        try { // prova ad eseguire il codice
            portale = this.getPortaleEvento(unEvento);
            if (portale != null) {
                lista = portale.getLista();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }// fine del metodo


    /**
     * Recupera il modulo che ha generato un evento.
     *
     * @param unEvento evento ricevuto
     *
     * @return il modulo che contiene il componente GUI che ha generato l'evento,
     *         null se non riesce a trovare il modulo
     */
    protected Modulo getModuloEvento(EventObject unEvento) {
        /* variabili e costanti locali di lavoro */
        Modulo modulo = null;
        Portale portale;

        try { // prova ad eseguire il codice
            portale = this.getPortaleEvento(unEvento);
            if (portale != null) {
                modulo = portale.getModulo();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return modulo;
    }// fine del metodo


    /**
     * Controlla se l'evento e' stato generato dal campo.
     * <p/>
     * Invoca il metodo delegato per recuperare il campo interessato
     * dall'evento <br>
     * Estrae il nome interno del campo <br>
     * Confronta il nome interno col parametro in ingresso <br>
     *
     * @param unEvento evento ricevuto
     * @param nomeCampo nome del campo da controllare
     *
     * @return vero se l'evento e' stato generato dal campo
     */
    protected boolean isCampoEvento(EventObject unEvento, String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        boolean risposta = false;
        Campo campo;
        String nomeInterno = "";

        try { // prova ad eseguire il codice
            /* recupera il campo che ha generato l'evento */
            campo = getCampoEvento(unEvento);

            /* controlla che non sia nullo */
            if (campo != null) {
                nomeInterno = campo.getNomeInterno();
            }// fine del blocco if

            /* confronta per stabilire se � quello richiesto */
            risposta = nomeInterno.equalsIgnoreCase(nomeCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return risposta;
    }


    /**
     * Recupera la Finestra che ha generato un evento.
     * <br>
     * Recupera il componente che ha generato l'evento
     * Se ha trovato una <code>Finestra</code>, lo restituisce.
     *
     * @param unEvento evento ricevuto
     *
     * @return la finestra che ha generato l'evento,
     *         null se non riesce a trovarla
     */
    protected Finestra getFinestraEvento(EventObject unEvento) {
        /* variabili e costanti locali di lavoro */
        Finestra finestra = null;
        Object sorgente;

        try {    // prova ad eseguire il codice

            sorgente = unEvento.getSource();
            if (sorgente instanceof Finestra) {
                finestra = (Finestra)sorgente;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return finestra;
    }


    /**
     * Recupera il Navigatore che ha generato un evento.
     * <br>
     * Recupera il Navigatore che ha generato l'evento in una finestra <br>
     * Se ha trovato una <code>Finestra</code>, restituisce il Navigatore corrispondente <br>
     *
     * @param unEvento evento ricevuto
     *
     * @return il Navigatore della Finestra che ha generato l'evento,
     *         null se non riesce a trovarlo
     */
    protected Navigatore getNavigatoreEventoFinestra(EventObject unEvento) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore = null;
        Finestra finestra;

        try {    // prova ad eseguire il codice

            finestra = this.getFinestraEvento(unEvento);

            if (finestra != null) {
                navigatore = finestra.getFinestraBase().getNavigatore();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return navigatore;
    }


    /**
     * Azione informazioni sul programma.
     * <p/>
     * Metodo invocato da azione/evento <code>AzAbout</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void about(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;

        try { // prova ad eseguire il codice
            modulo = Progetto.getPrimoModulo();
            if (modulo != null) {
                modulo.apreAbout();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione aggiornamento del programma.
     * <p/>
     * Metodo invocato da azione/evento <code>AzAbout</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void update(ActionEvent unEvento, Azione unAzione) {
        Aggiornamento agg;

        try { // prova ad eseguire il codice
            agg = new Aggiornamento();
            agg.dialogoEsegue();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione help generico.
     * <p/>
     * Metodo invocato da azione/evento <code>AzHelp</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void help(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.apreHelp();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione help programmatore.
     * <p/>
     * Metodo invocato da azione/evento <code>AzHelpProgrammatore</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void helpProgrammatore(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.apreHelpProgrammatore();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione chiude il programma.
     * <p/>
     * Metodo invocato da azione/evento <code>AzChiudeProgramma</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void chiudeProgramma(ActionEvent unEvento, Azione unAzione) {

        try { // prova ad eseguire il codice

            // todo bisognerebbe andare alla logica navigatore
            // todo che gestisce gli eventi, ma questo evento
            // todo proviene da un menuItem e non si riesce ad
            // todo estrarre la logice con getLogicaEvento.
            // todo per ora vado direttamente al Progetto
            // todo sistemare, alex 1-12-2004

//            logica = this.getLogicaEvento(unEvento);
//            logica.chiudiProgramma();

            Progetto.chiudeProgramma();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione chiude la Scheda.
     * <p/>
     * Metodo invocato da azione/evento <code>AzChiudeScheda</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void chiudeScheda(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice
            nav = this.getNavigatoreEvento(unEvento);
            if (nav != null) {
                nav.chiudeScheda();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione preferenze.
     * <p/>
     * Metodo invocato da azione/evento <code>AzPreferenze</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void preferenze(ActionEvent unEvento, Azione unAzione) {
        try { // prova ad eseguire il codice
            /* crea e presenta il dialogo  */
            Progetto.mostraPreferenze();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione registra dati default.
     * <p/>
     * Metodo invocato da azione/evento <code>AzRegistraDatiDefault</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void registraDatiDefault(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.registraDatiDefault();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione ricerca.
     * <p/>
     * Metodo invocato da azione/evento <code>AzRicerca</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void ricerca(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.apreRicerca();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione proietta.
     * <p/>
     * Metodo invocato da azione/evento <code>AzProietta</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void proietta(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.proietta();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione imposta come Preferito.
     * <p/>
     * Metodo invocato da azione/evento <code>AzPreferito</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void setPreferito(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.setPreferito();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione di esportazione dei dati.
     * <p/>
     * Metodo invocato da azione/evento <code>AzEsporta</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void esporta(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.apreEsporta();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione di importazione dei dati.
     * <p/>
     * Metodo invocato da azione/evento <code>AzEsporta</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void importa(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.apreImporta();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione stampa in una Lista.
     * <p/>
     * Metodo invocato da azione/evento <code>AzStampa</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void stampa(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.stampaLista();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Attiva una Finestra (la porta in primo piano).
     * <p/>
     * Metodo invocato da azione/evento <code>AzAttivaFinestra</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void attivaFinestra(WindowEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.attivaFinestra();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Chiude una Finestra.
     * <p/>
     * Metodo invocato da azione/evento <code>AzChiudeFinestra</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void chiudeFinestra(WindowEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Navigatore navigatore;
        boolean affermativo;
        MessaggioDialogo dialogo;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEventoFinestra(unEvento);
            continua = (navigatore != null);

            if (continua) {
                if (navigatore.isNavigatoreMain()) {
                    dialogo = new MessaggioDialogo("Sei sicuro di voler uscire?");
                    /* procede solo dopo ulteriore conferma esplicita */
                    if (dialogo.getRisposta() == JOptionPane.YES_OPTION) {
                        Progetto.chiudeProgramma();
                    }// fine del blocco if

                } else {
                    affermativo = navigatore.richiediChiusura();
                    if (affermativo) {
                        navigatore.chiudiNavigatore();
                    }// fine del blocco if
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione chiude il Navigatore in una finestra.
     * <p/>
     * Metodo invocato da azione/evento <code>AzChiudeNavigatore</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void chiudeNavigatore(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;
        boolean affermativo;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                affermativo = navigatore.richiediChiusura();
                if (affermativo) {
                    navigatore.chiudiNavigatore();
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone nuovo record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzNuovoRecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void nuovoRecord(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Portale portale;

        try { // prova ad eseguire il codice
            portale = this.getPortaleEvento(unEvento);
            if (portale != null) {
                portale.fire(PortaleBase.Evento.nuovoRecord);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone aggiungi record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzAggiungiRecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void aggiungiRecord(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Portale portale;

        try { // prova ad eseguire il codice
            portale = this.getPortaleEvento(unEvento);
            if (portale != null) {
                portale.fire(PortaleBase.Evento.aggiungiRecord);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone modifica record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzModificaRecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void modificaRecord(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Portale portale;

        try { // prova ad eseguire il codice
            portale = this.getPortaleEvento(unEvento);
            if (portale != null) {
                portale.fire(PortaleBase.Evento.modificaRecord);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone duplica record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzDuplicaRecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void duplicaRecord(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Portale portale;

        try { // prova ad eseguire il codice
            portale = this.getPortaleEvento(unEvento);
            if (portale != null) {
                portale.fire(PortaleBase.Evento.duplicaRecord);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone elimina record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzEliminaRecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void eliminaRecord(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Portale portale;

        try { // prova ad eseguire il codice
            portale = this.getPortaleEvento(unEvento);
            if (portale != null) {
                portale.fire(PortaleBase.Evento.eliminaRecord);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone rimuovi record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzRimuoviRecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void rimuoviRecord(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Portale portale;

        try { // prova ad eseguire il codice
            portale = this.getPortaleEvento(unEvento);
            if (portale != null) {
                portale.fire(PortaleBase.Evento.rimuoviRecord);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento di un carattere in una Lista.
     * <p/>
     * Metodo invocato dal comando/evento <code>AzListaCarattere</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void listaCarattere(KeyEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.listaCarattere(unEvento);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Mouse cliccato in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzListaClick</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void listaClick(MouseEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getListaEvento(unEvento);
            if (lista != null) {
//                lista.fire(ListaBase.Evento.click);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Mouse cliccato due volte in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzListaDoppioClick</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void listaDoppioClick(MouseEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getListaEvento(unEvento);
            if (lista != null) {
                lista.fire(ListaBase.Evento.doppioClick);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento del carattere Enter in una Lista.
     * <p/>
     * Metodo invocato dal comando/evento <code>AzListaEnter</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void listaEnter(KeyEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getListaEvento(unEvento);
            if (lista != null) {
                lista.fire(ListaBase.Evento.enter);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento del carattere Return in una Lista.
     * <p/>
     * Metodo invocato dal comando/evento <code>AzListaReturn</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void listaReturn(KeyEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getListaEvento(unEvento);
            if (lista != null) {
                lista.fire(ListaBase.Evento.ritorno);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Freccia in alto.
     * <p/>
     * Sposta in alto di una riga la selezione in una <code>Lista</code> <br>
     * Inserimento del carattere -freccia in alto- <br>
     * Metodo invocato dal comando/evento <code>AzFrecce</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void frecciaAlto(KeyEvent evento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice

            lista = this.getListaEvento(evento);

            if (lista != null) {
                lista.fire(ListaBase.Evento.frecciaAlto);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Freccia in basso.
     * <p/>
     * Sposta in basso di una riga la selezione in una <code>Lista</code> <br>
     * Inserimento del carattere -freccia in alto- <br>
     * Metodo invocato dal comando/evento <code>AzFrecce</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void frecciaBasso(KeyEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getListaEvento(unEvento);
            if (lista != null) {
                lista.fire(ListaBase.Evento.frecciaBasso);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Frecce pagina in una <code>Lista</code>.
     * <p/>
     * Inserimento del carattere -pagina s&ugrave;- o -pagina gi&ugrave;- <br>
     * Metodo invocato dal comando/evento <code>AzPagine</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void pagine(KeyEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Lista lista;
        int cod;

        try { // prova ad eseguire il codice
            cod = unEvento.getKeyCode();

            lista = this.getListaEvento(unEvento);
            if (lista != null) {
                switch (cod) {
                    case KeyEvent.VK_PAGE_UP:
                        lista.fire(ListaBase.Evento.paginaSu);
                        break;
                    case KeyEvent.VK_PAGE_DOWN:
                        lista.fire(ListaBase.Evento.paginaGiu);
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Freccia Home in una <code>Lista</code>.
     * <p/>
     * Inserimento del carattere -Home- <br>
     * Metodo invocato dal comando/evento <code>AzHome</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void home(KeyEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getListaEvento(unEvento);
            if (lista != null) {
                lista.fire(ListaBase.Evento.home);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Freccia End in una <code>Lista</code>.
     * <p/>
     * Inserimento del carattere -End- <br>
     * Metodo invocato dal comando/evento <code>AzEnd</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void end(KeyEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getListaEvento(unEvento);
            if (lista != null) {
                lista.fire(ListaBase.Evento.end);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Mouse cliccato in un voce della <code>Lista</code>.
     * <p/>
     * Metodo invocato dal comando/evento <code>AzTitolo</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void titolo(MouseEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice

            Object source = unEvento.getSource();
            if (source!=null) {
                if (source instanceof JTableHeader) {
                    JTableHeader header = (JTableHeader)source;
                    JTable table = header.getTable();
                    if (table!=null) {
                        if (table instanceof Tavola) {
                            Tavola tavola = (Tavola)table;
                            Lista lista = tavola.getLista();
                            if (lista!=null) {
                                if (lista.isOrdinabile()) {
                                    lista.colonnaCliccata(unEvento);

                                    /* sincronizza il navigatore */
                                    navigatore = this.getNavigatoreEvento(unEvento);
                                    if (navigatore!=null) {
                                        navigatore.sincronizza();
                                    }// fine del blocco if

                                }// fine del blocco if

                            }// fine del blocco if
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Entrata in una cella della lista (che riceve il fuoco).
     * <p/>
     * Metodo invocato da azione/evento <code>AzEntrataCella</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void entrataCella(FocusEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.entrataCella();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Uscita da una cella della lista (che perde il fuoco).
     * <p/>
     * Metodo invocato da azione/evento <code>AzUscitaCella</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void uscitaCella(FocusEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.uscitaCella();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone carica tutti in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzCaricaTutti</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void caricaTutti(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.caricaTutti();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone mostra solo selezionati in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzSoloSelezionati</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void soloSelezionati(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.soloSelezionati();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone nasconde selezionati in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzNascondeSelezionati</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void nascondeSelezionati(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.nascondeSelezionati();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone salva la selezione della <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzSalvaSelezione</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void salvaSelezione(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.salvaSelezioneEsterna();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone carica la selezione nella <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzCaricaSelezione</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void caricaSelezione(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.caricaSelezioneEsterna();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone riga su in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzRigaSu</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void rigaSu(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.rigaSu();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone riga giu in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzRigaGiu</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void rigaGiu(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.rigaGiu();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ordina sulla colonna a sinistra in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal comando/evento <code>AzColonna</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void colonnaSinistra(KeyEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.colonnaSinistra();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ordina sulla colonna a destra in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal comando/evento <code>AzColonna</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void colonnaDestra(KeyEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.colonnaDestra();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone annulla modifiche in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzAnnullaModifiche</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void annullaModifiche(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.annullaModifiche();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione di conferma di un form.
     * <p/>
     * Metodo invocato da azione/evento <code>AzEnter</code> <br>
     * Invoca il metodo delegato <br>
     * Associato all'azione Registra della scheda
     * Associato alle azioni Conferma o Registra del dialogo
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void confermaForm(EventObject unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.registraScheda();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone conferma in un <code>Dialogo</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzioneConfermaDialogo</code> <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void confermaDialogo(ActionEvent unEvento, Azione unAzione) {
    }

//    /**
//     * Bottone registra record in una <code>Scheda</code>.
//     * <p/>
//     * Metodo invocato da azione/evento <code>AzEnter</code> <br>
//     * Invoca il metodo delegato <br>
//     *
//     * @param unEvento evento generato dall'interfaccia utente
//     * @param unAzione oggetto interessato dall'evento
//     */
//    public void registraScheda(KeyEvent unEvento, Azione unAzione) {
//        /* variabili e costanti locali di lavoro */
//        Navigatore navigatore;
//
//        try { // prova ad eseguire il codice
//            navigatore = this.getNavigatoreEvento(unEvento);
//            if (navigatore != null) {
//                navigatore.registraScheda();
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    /**
     * Bottone primo record in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzPrimorecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void primoRecord(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.primoRecord();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone record precedente in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzRecordPrecedente</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void recordPrecedente(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.recordPrecedente();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone record successivo in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzRecordSuccessivo</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void recordSuccessivo(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.recordSuccessivo();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone ultimo record in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzUltimorecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void ultimoRecord(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.ultimoRecord();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Mouse cliccato in un <code>Campo</code> della <code>Scheda</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzMouseClick</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void mouseClick(MouseEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;
        Campo campo;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                campo = this.getCampoEvento(unEvento);
                navigatore.mouseClick(campo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Mouse cliccato due volte in un <code>Campo</code> della <code>Scheda</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzMouseDoppioClick</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void mouseDoppioClick(MouseEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampoEvento(unEvento);
            if (campo != null) {
                campo.fire(CampoBase.Evento.doppioClick);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizzazione del testo di un campo calcolato.
     * <p/>
     * Metodo invocato da azione/evento <code>AzCalcolaCampo</code> <br>
     * Evento lanciato da un componente utilizzato per il calcolo del campo <br>
     * L'evento viene lanciato quando il componente subisce delle modifiche <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     * @param unCampo campo calcolato da sincronizzare
     */
    public void calcolaCampo(KeyEvent unEvento, Azione unAzione, Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.calcolaCampo(unCampo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizzazione della label di un campo calcolato.
     * <p/>
     * Metodo invocato da azione/evento <code>AzCalcolaLabel</code> <br>
     * Evento lanciato da un componente utilizzato per il calcolo del campo <br>
     * L'evento viene lanciato quando il componente subisce delle modifiche <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     * @param unCampo campo calcolato da sincronizzare
     */
    public void calcolaLabel(KeyEvent unEvento, Azione unAzione, Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.calcolaLabel(unCampo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento di un carattere a video in un campo testo.
     * <p/>
     * Metodo invocato da azione/evento <code>AzCarattere</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void carattere(KeyEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice

            /* cerca prima un campo dell'azione */
            campo = unAzione.getCampo();

            if (campo == null) {
                campo = this.getCampoEvento(unEvento);
            }// fine del blocco if-else

            if (campo != null) {
                campo.fire(CampoBase.Evento.GUIModificata);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Modifica di un item in un componente.
     * <p/>
     * Metodo invocato da azione/evento <code>AzItemModificato</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void itemModificato(ItemEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampoEvento(unEvento);
            if (campo != null) {
                campo.fire(CampoBase.Evento.GUIModificata);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Modifica di un popup.
     * <p/>
     * Metodo invocato da azione/evento <code>AzPopupModificato</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void popupModificato(ItemEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice
            if (unEvento.getStateChange() == ItemEvent.SELECTED) {
                campo = this.getCampoEvento(unEvento);
                if (campo != null) {
                    campo.fire(CampoBase.Evento.GUIModificata);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Modifica della selezione di un componente lista </li>
     * <p/>
     * JList e non JTable <br>
     * Metodo invocato da azione/evento <code>AzSelezioneModificata</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void selezioneModificata(ListSelectionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampoEvento(unEvento);
            if (campo != null) {
                campo.fire(CampoBase.Evento.GUIModificata);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento del carattere Enter in un campo testo.
     * <p/>
     * Metodo invocato da azione/evento <code>AzEnter</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     *
     * @deprecated
     */
    public void enter(KeyEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
//                navigatore.enter();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento del carattere Escape in un campo testo.
     * <p/>
     * Metodo invocato da azione/evento <code>AzEnter</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void escape(KeyEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                navigatore.escape();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento del carattere Tab in un campo testo.
     * <p/>
     * Metodo invocato da azione/evento <code>AzTab</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void tab(KeyEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
//                navigatore.escape();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Entrata nel campo (che riceve il fuoco).
     * <p/>
     * Metodo invocato da azione/evento <code>AzEntrataCampo</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void entrataCampo(FocusEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice

            /* recupera il campo sorgente
             * prima guarda se l'azione ha il riferimento al campo
             * se non lo trova lo cerca nella catena grafica */
            campo = unAzione.getCampo();
            if (campo == null) {
                campo = this.getCampoEvento(unEvento);
            }// fine del blocco if

            if (campo != null) {
                campo.getCampoVideo().entrataCampo(unEvento);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Uscita dal campo (che perde il fuoco).
     * <p/>
     * Metodo invocato da azione/evento <code>AzUscitaCampo</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void uscitaCampo(FocusEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice

            /* recupera il campo sorgente
             * prima guarda se l'azione ha il riferimento al campo
             * se non lo trova lo cerca nella catena grafica */
            campo = unAzione.getCampo();
            if (campo == null) {
                campo = this.getCampoEvento(unEvento);
            }// fine del blocco if

            if (campo != null) {
                campo.getCampoVideo().uscitaCampo(unEvento);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Entrata nel popup di un ComboBox (che diventa visibile).
     * <p/>
     * Metodo invocato da azione/evento <code>AzEntrataPopup</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void entrataPopup(PopupMenuEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;
        Campo campo;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                campo = this.getCampoEvento(unEvento);
                navigatore.entrataPopup(campo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Uscita dal popup di un ComboBox (che diventa invisibile).
     * <p/>
     * Metodo invocato da azione/evento <code>AzUscitaPopup</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void uscitaPopup(PopupMenuEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;
        Campo campo;

        try { // prova ad eseguire il codice
            navigatore = this.getNavigatoreEvento(unEvento);
            if (navigatore != null) {
                campo = this.getCampoEvento(unEvento);
                navigatore.uscitaPopup(campo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone annulla in un <code>Dialogo</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzAnnullaDialogo</code> <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void annullaDialogo(ActionEvent unEvento, Azione unAzione) {
    }


    public void chiudeFinestra(WindowEvent unEvento) {
    }// fine del metodo


    /**
     * Restituisce l'oggetto concreto della classe principale.
     *
     * @return oggetto grafico GestoreBase restituito dall'interfaccia
     */
    public GestoreBase getGestore() {
        return this;
    }

}// fine della classe
