/**
 * Title:        PannelloBase.java
 * Package:      it.algos.base.pannello
 * Description:
 * Copyright:    Copyright (c) 2005
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 26 luglio 2005 alle 13.29
 */

package it.algos.base.pannello;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.Eventi;
import it.algos.base.evento.pannello.PanModificatoAz;
import it.algos.base.evento.pannello.PanModificatoEve;
import it.algos.base.evento.pannello.PanModificatoLis;
import it.algos.base.font.FontFactory;
import it.algos.base.interfaccia.ContenitoreCampi;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.SetCampi;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.EventListenerList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementazione di un Pannello di base.
 * <p/>
 * Componente grafico che estende JPanel.
 * <p/>
 * Implementa metodi utili per:
 * - la regolazione del layout (se e' un Layout Algos)
 * - la regolazione delle dimensioni preferite, minime, massime
 * - il recupero dei componenti
 * - il debug del pannello
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  20 agosto 2005 ore 13.29
 */
public class PannelloBase extends JPanel implements Pannello {

    /**
     * Riferimento alla fonte dalla quale recuperare i campi.<br>
     * Puo' essere:
     * - un form,
     * - un modulo
     */
    private ContenitoreCampi fonteCampi = null;

    /**
     * A list of event listeners for this component.
     */
    private EventListenerList listaListener;

    private final static int MARGINE_STANDARD = 10;


    /**
     * Costruttore base senza parametri.
     * <p/>
     */
    public PannelloBase() {
        this(null);

    }


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param cont il contenitore di campi di riferimento
     */
    public PannelloBase(ContenitoreCampi cont) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice

            /* regolazione delle variabili di istanza con i parametri */
            this.setContenitoreCampi(cont);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            /* di default e' trasparente */
            this.setOpaque(false);

            /* di default e' allineato a sinistra/in alto */
            this.setAlignmentX(Component.LEFT_ALIGNMENT);

            /* attiva eventualmente il debug */
            if (Pannello.DEBUG) {
                this.debug(Pannello.DEBUG_CON_TITOLO);
            }// fine del blocco if

            /* lista dei propri eventi */
            this.setListaListener(new EventListenerList());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Attiva il debug del pannello.
     * <p/>
     * Il pannello diventa:
     * - opaco
     * - bordato con nome classe
     * - grigio
     */
    public void debug() {
        this.debug(false);
    }


    /**
     * Attiva il debug del pannello.
     * <p/>
     * Il pannello diventa bordato con eventuale nome classe
     *
     * @param usaTitolo true per usare il nome classe come titolo
     */
    public void debug(boolean usaTitolo) {
        /* variabili e costanti locali di lavoro */
        String titolo;
        Border bordo;
        Font font;

        try { // prova ad eseguire il codice

            /* crea il bordo di base */
            bordo = BorderFactory.createLineBorder(Color.white);
            /* eventualmente aggiunge il titolo */
            if (usaTitolo) {
                titolo = Lib.Clas.getNomeClasse(this.getClass());
                font = FontFactory.creaScreenFont(Font.PLAIN, 9);
                Border bordoVuoto = BorderFactory.createEmptyBorder(0, 0, 0, 0);
                Border bordointerno = BorderFactory.createTitledBorder(bordoVuoto,
                        titolo,
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        font);
                Border bordoesterno = bordo;
                bordo = BorderFactory.createCompoundBorder(bordoesterno, bordointerno);
            }// fine del blocco if

            this.setBorder(bordo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Allinea il bordo sinistro dei campi presenti nel pannello.
     * <p/>
     * Opera solo sui campi che hanno un'etichetta a sinistra del campo <br>
     */
    public void allineaCampi() {
        /* invoca il metodo delegato della classe */
        this.allineaCampi(Pannello.Bandiera.sinistra);
    }


    /**
     * Allinea il bordo sinistro dei campi presenti nel pannello.
     * <p/>
     * Opera solo sui campi che hanno un'etichetta a sinistra del campo <br>
     *
     * @param bandiera tipo di allineamento testo etichetta
     */
    public void allineaCampi(Bandiera bandiera) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi;
        ArrayList<Campo> campiSin = null;
        boolean continua;
        int lar;
        int larMax = 0;

        try { // prova ad eseguire il codice
            campi = getCampiPannello();
            continua = (campi != null);

            /* recupera tutti i campi con etichetta a sinistra */
            if (continua) {
                /* campi sinistri */
                campiSin = new ArrayList<Campo>();

                /* traverso tutta la collezione */
                for (Campo campo : campi) {
                    if (campo.isEtichettaSinistra()) {
                        campiSin.add(campo);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

            /* recupera la larghezza dell'etichetta più larga */
            if (continua) {
                /* traverso tutta la collezione */
                for (Campo campo : campiSin) {
                    lar = campo.getLarghezzaEtichetta();
                    larMax = Math.max(lar, larMax);
                } // fine del ciclo for-each
            }// fine del blocco if

            /* forza per tutti i campi (sinistri) la larghezza massima */
            if (continua) {
                /* traverso tutta la collezione */
                for (Campo campo : campiSin) {
                    campo.setLarghezzaEtichetta(larMax);
                    campo.setAllineamentoEtichetta(bandiera);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un componente senza modificarlo.
     * <p/>
     * Non modifica dimensioni e allineamento.<br>
     * Non agginge filler.<br>
     *
     * @param comp il componente da aggiungere
     */
    public void addOriginale(Component comp) {
        this.add(comp);
    }


    /**
     * Aggiunge un oggetto generico al pannello.
     * <p/>
     * Interpreta l'oggetto ed estrae i componenti.
     *
     * @param oggetto oggetti da disporre in un pannello; puo' essere:
     *                Component - un singolo componente (Campo od altro)
     *                String - un nome set, un nome di campo singolo, una lista di nomi
     *                List - di nomi, di oggetti Campo, di componenti <br>
     */
    public void add(Object oggetto) {
        /* variabili e costanti locali di lavoro */
        Component[] componenti;
        Component comp;

        try { // prova ad eseguire il codice

            /* recupera i componenti */
            componenti = this.getComponenti(oggetto);

            /* controllo di congruità */
            if (componenti != null) {
                /* aggiunge al pannello tutti i componenti */
                for (int k = 0; k < componenti.length; k++) {
                    comp = componenti[k];
                    this.add(comp);
                } /* fine del blocco for */
            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un oggetto generico al pannello.
     * <p/>
     *
     * @param unSet di campi
     */
    public void add(SetCampi unSet) {
        /* invoca il metodo delegato della classe */
        this.add(unSet.toString());
    }


    /**
     * Aggiunge un oggetto generico al pannello con constraint.
     * <p/>
     * Interpreta l'oggetto ed estrae i componenti.
     *
     * @param oggetto oggetti da disporre in un pannello; puo' essere:
     *                Component - un singolo componente (Campo od altro)
     *                String - un nome set, un nome di campo singolo, una lista di nomi
     *                List - di nomi, di oggetti Campo, di componenti <br>
     */
    public void add(Object oggetto, Object constraint) {
        /* variabili e costanti locali di lavoro */
        Component[] componenti;
        Component comp;

        try { // prova ad eseguire il codice

            /* recupera i componenti */
            componenti = this.getComponenti(oggetto);

            /* controllo di congruita' */
            if (componenti != null) {
                /* aggiunge al pannello tutti i componenti */
                for (int k = 0; k < componenti.length; k++) {
                    comp = componenti[k];
                    this.add(comp, constraint);
                } /* fine del blocco for */
            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un componente.
     * <p/>
     */
    public Component add(Component comp) {
//        /* variabili e costanti locali di lavoro */
//        Layout layout;
//        JScrollPane scorrevole=null;
//        Component compOut;
//        JPanel layer=null;
//
//
//        try { // prova ad eseguire il codice
//            layout = this.getLayoutAlgos();
//            if (layout != null) {
//                if (layout.isUsaScorrevole()) {
//                    if (layout.isScorrevoleAttivo()) {
//                        layer = layout.getLayer();
//                        int a = 87;
//                    }// fine del blocco if
//                }// fine del blocco if
//
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        if (layer!=null) {
//            compOut = layer.add(comp);
//        } else {
//            compOut = super.add(comp);
//        }// fine del blocco if-else
//
//

        return super.add(comp);
    }


    /**
     * Aggiunge un componente.
     * <p/>
     */
    public void add(Component comp, Object constraints) {
        super.add(comp, constraints);
    }


    /**
     * Recupera i componenti.
     * <p/>
     * Livelli di trasformazione successiva: <ul>
     * <li> Oggetto </li>
     * <li> Nome </li>
     * <li> Campo </li>
     * <li> Componente </li>
     * </ul>
     *
     * @param oggetto oggetti da disporre in un pannello; puo' essere:
     *                Component - un singolo componente (Campo od altro)
     *                String - un nome set, un nome di campo singolo, una lista di nomi
     *                ArrayList - di nomi, di oggetti Campo, di componenti <br>
     *
     * @return lista di oggetti Campo o Component <br>
     */
    private Component[] getComponenti(Object oggetto) {
        /* variabili e costanti locali di lavoro */
        Component[] componenti = new Component[0];
        Campo campo;
        Navigatore nav;
        Component componente;
        String nome;
        List lista;
        List listaSingoloComponente;
        List listaNomi;
        List listaCampi;
        List listaPannelliCampo;
        List listaComponenti;
        int num;

        try { // prova ad eseguire il codice
            /* Controllo base di nullita' */
            if (oggetto != null) {

                /* recupera il pannello video del campo */
                if (oggetto instanceof Campo) {
                    campo = (Campo)oggetto;
                    componente = campo.getPannelloCampo();
                    Dimension dim = componente.getPreferredSize();
                    componenti = new Component[1];
                    componenti[0] = componente;
                }// fine del blocco if

                /* recupera il portale navigatore del navigatore */
                if (oggetto instanceof Navigatore) {
                    nav = (Navigatore)oggetto;
                    componente = nav.getPortaleNavigatore();
                    componenti = new Component[1];
                    componenti[0] = componente;
                }// fine del blocco if

                /* crea un array di componenti con un solo elemento  */
                /* invoca ricorsivamente queto metodo */
                if (oggetto instanceof Component) {
                    listaSingoloComponente = new ArrayList();
                    listaSingoloComponente.add(oggetto);
                    componenti = this.getComponenti(listaSingoloComponente);
                }// fine del blocco if

                /* elemento di una enum Campi
                /* estrae il nome del campo
                /* invoca ricorsivamente queto metodo */
                if (oggetto instanceof Campi) {
                    nome = ((Campi)oggetto).get();
                    componenti = this.getComponenti(nome);
                }// fine del blocco if

                /* trasforma comunque la stringa in un array di nomi */
                /* invoca ricorsivamente queto metodo */
                /* per ricadere nel caso successivo */
                if (oggetto instanceof String) {
                    listaNomi = this.getNomi((String)oggetto);
                    componenti = this.getComponenti(listaNomi);
                }// fine del blocco if

                /* trasforma un array di nomi in un array di campi */
                /* trasforma un array di campi in un array di pannelliCampo */
                /* invoca ricorsivamente queto metodo */
                if (oggetto instanceof ArrayList) {
                    lista = (ArrayList)oggetto;

                    if (lista.size() > 0) {
                        if (lista.get(0) instanceof String) {
                            listaCampi = this.getCompGrafici(lista);
                            componenti = this.getComponenti(listaCampi);
                        }// fine del blocco if

                        if (lista.get(0) instanceof Campo) {
                            listaPannelliCampo = this.getCompGrafici(lista);
                            listaComponenti = this.getCompGrafici(listaPannelliCampo);
                            componenti = this.getComponenti(listaComponenti);
                        }// fine del blocco if

                        if (lista.get(0) instanceof Component) {
                            num = lista.size();
                            componenti = new Component[num];

                            for (int k = 0; k < componenti.length; k++) {
                                componenti[k] = (Component)lista.get(k);
                            } // fine del ciclo for
                        }// fine del blocco if
                    }// fine del blocco if

                }// fine del blocco if

            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return componenti;
    }


    /**
     * Recupera i componenti.
     * <p/>
     * Trasforma la lista in una lista di componenti: <ul>
     * <li> Se � una lista di nomi, recupera i campi </li>
     * <li> Se � una lista di campi, recupera i pannelliCampo </li>
     * <li> Se � una lista di JPanel, esegue un casting </li>
     * <li> Se � una lista di componenti, non fa nulla </li>
     * </ul>
     *
     * @param lista puo' essere:
     *              una lista di nomi
     *              una lista di oggetti Campo
     *              una lista di oggetti Component
     */
    private List getCompGrafici(List lista) {
        /* variabili e costanti locali di lavoro */
        List listaComponenti = null;
        boolean continua = true;
        String nome;
        Campo unCampo;
        PannelloCampo panCampo;
        Component comp;

        try {    // prova ad eseguire il codice

            /* lista di nomi */
            if (continua) {
                if (lista.get(0) instanceof String) {
                    listaComponenti = new ArrayList();

                    for (int k = 0; k < lista.size(); k++) {
                        nome = (String)lista.get(k);
                        unCampo = this.getCampo(nome);
                        listaComponenti.add(unCampo);
                    } // fine del ciclo for

                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* lista di oggetti campo */
            if (continua) {
                if (lista.get(0) instanceof Campo) {
                    listaComponenti = new ArrayList();

                    for (int k = 0; k < lista.size(); k++) {
                        unCampo = (Campo)lista.get(k);
                        comp = unCampo.getCampoVideo().getPannelloBaseCampo();
                        listaComponenti.add(comp);
                    } // fine del ciclo for

                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* lista di pannelliCampo */
            if (continua) {
                if (lista.get(0) instanceof PannelloCampo) {
                    listaComponenti = new ArrayList();

                    for (int k = 0; k < lista.size(); k++) {
                        panCampo = (PannelloCampo)lista.get(k);
                        listaComponenti.add((Component)panCampo);
                    } // fine del ciclo for

                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* lista di componenti */
            if (continua) {
                if (lista.get(0) instanceof Component) {
                    listaComponenti = lista;
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaComponenti;
    }


    /**
     * Recupera i nomi dei campi.
     * <p/>
     * Trasforma il nome in una lista di nomi: <ul>
     * <li> Se � un set, recupera i nomi dal Modello </li>
     * <li> Se � un nome singolo, crea un array di un solo elemento </li>
     * <li> Se � una lista, la trasforma in array </li>
     * </ul>
     *
     * @param nome puo' essere:
     *             una lista di nomi
     *             il nome di un campo singolo,
     *             un nome set
     *
     * @return lista di nomi di campi
     */
    private List getNomi(String nome) {
        /* variabili e costanti locali di lavoro */
        List listaNomi = null;
        boolean continua = true;
        String sep;

        sep = ",";

        try {    // prova ad eseguire il codice

            /* lista di nomi */
            if (continua) {
                if (nome.indexOf(sep) != -1) {
                    listaNomi = Lib.Array.creaLista(nome);
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* nome di un campo singolo */
            if (continua) {
                if (this.isEsisteCampo(nome)) {
                    listaNomi = new ArrayList();
                    listaNomi.add(nome);
                    continua = false;
                }// fine del blocco if

            }// fine del blocco if

            /* nome di un set */
            if (continua) {
                listaNomi = this.estraeNomi(nome);
                continua = false;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaNomi;
    }


    /**
     * Recupera i nomi dei campi del set.
     * <p/>
     * Recupera un set di campi dal Modello <br>
     * Prepara un array di nomi campi, individuandoli da quelli del Modello <br>
     *
     * @param nomeSet il nome di un Set di campi <br>
     *
     * @return lista dei campi - nullo se non trovato <br>
     */
    private List estraeNomi(String nomeSet) {
        /* variabili e costanti locali di lavoro */
        List listaNomi = null;
        List listaCampi;
        Campo campo;
        String nomeChiave;

        try {    // prova ad eseguire il codice

            /* recupera il set dal Modello */
            listaCampi = this.getSetCampi(nomeSet);

            /* controlla che esista il set */
            if (listaCampi != null) {
                listaNomi = new ArrayList();

                for (int k = 0; k < listaCampi.size(); k++) {
                    campo = (Campo)listaCampi.get(k);
                    if (campo != null) {
                        nomeChiave = campo.getNomeInterno();
                        listaNomi.add(nomeChiave);
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return listaNomi;
    }


    /**
     * Determina se un campo esiste nel contenitore di riferimento.
     * <p/>
     *
     * @return true se esiste
     */
    private boolean isEsisteCampo(String nome) {
        /* variabili e costanti locali di lavoro */
        ContenitoreCampi cont;
        boolean esiste = false;

        try { // prova ad eseguire il codice
            cont = this.getContenitoreCampi();
            if (cont != null) {
                nome = nome.toLowerCase();
                esiste = cont.isEsisteCampo(nome);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Ritorna un campo dato il nome.
     * <p/>
     * Recupera il campo dal contenitore di riferimento.
     *
     * @param nome il nome del campo
     *
     * @return il campo
     */
    private Campo getCampo(String nome) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        ContenitoreCampi fonteCampi;

        try { // prova ad eseguire il codice

            fonteCampi = this.getContenitoreCampi();
            if (fonteCampi != null) {
                campo = fonteCampi.getCampo(nome);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    private List getSetCampi(String nome) {
        /* variabili e costanti locali di lavoro */
        ArrayList lista = null;
        Modello modello;

        try { // prova ad eseguire il codice
            modello = this.getModelloRiferimento();

            if (modello != null) {
                lista = modello.getSetCampiScheda(nome);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera il modello di riferimento del contenitore di campi.
     * <p/>
     *
     * @return il modello di riferimento
     */
    private Modello getModelloRiferimento() {
        /* variabili e costanti locali di lavoro */
        Modello modello = null;
        Modulo modulo;
        ContenitoreCampi cont;

        try { // prova ad eseguire il codice

            cont = this.getContenitoreCampi();
            if (cont != null) {
                modulo = cont.getModulo();
                if (modulo != null) {
                    modello = modulo.getModello();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return modello;
    }


    /**
     * Attiva o disattiva il ridimensionamento dei componenti
     * in entrambi i versi del layout.
     * <p/>
     * Se il ridimensionamento e' attivo ridimensiona i componenti rispettando
     * la dimensione preferita, minima e massima. <br>
     * Se il ridimensionamento non e' attivo visualizza i componenti alla
     * loro dimensione preferita. <br>
     * Equivale ad effettuare le due chiamate setRidimensionaParallelo
     * e setRidimensionaPerpendicolare usando lo stesso parametro.
     *
     * @param flag per attivare o disattivare il ridimensionamento
     */
    public void setRidimensionaComponenti(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Layout layout;

        try {    // prova ad eseguire il codice
            layout = this.getLayoutAlgos();
            if (layout != null) {
                layout.setRidimensionaComponenti(flag);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Attiva o disattiva il ridimensionamento dei componenti
     * nel verso parallelo al layout.
     * <p/>
     * Se il ridimensionamento e' attivo ridimensiona i componenti rispettando
     * la dimensione preferita, minima e massima. <br>
     * Se il ridimensionamento non e' attivo visualizza i componenti alla
     * loro dimensione preferita. <br>
     *
     * @param flag per attivare o disattivare il ridimensionamento parallelo
     */
    public void setRidimensionaParallelo(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Layout layout;

        try {    // prova ad eseguire il codice
            layout = this.getLayoutAlgos();
            if (layout != null) {
                layout.setRidimensionaParallelo(flag);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Attiva o disattiva il ridimensionamento dei componenti
     * nel verso perpendicolare al layout.
     * <p/>
     * Se il ridimensionamento e' attivo ridimensiona i componenti rispettando
     * la dimensione preferita, minima e massima. <br>
     * Se il ridimensionamento non e' attivo visualizza i componenti alla
     * loro dimensione preferita. <br>
     *
     * @param flag per attivare o disattivare il ridimensionamento perpendicolare
     */
    public void setRidimensionaPerpendicolare(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Layout layout;

        try {    // prova ad eseguire il codice
            layout = this.getLayoutAlgos();
            if (layout != null) {
                layout.setRidimensionaPerpendicolare(flag);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Abilita l'uso dello scorrevole.
     * <p/>
     *
     * @param flag true per abilitare l'uso dello scorrevole, false per disabilitarlo
     */
    public void setUsaScorrevole(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Layout layout;

        try {    // prova ad eseguire il codice
            layout = this.getLayoutAlgos();
            if (layout != null) {
                layout.setUsaScorrevole(flag);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Attiva o disattiva l'uso del bordo nello scorrevole.
     * <p/>
     *
     * @param flag per attivare/disattivare l'uso del bordo.
     */
    public void setScorrevoleBordato(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Layout layout;

        try {    // prova ad eseguire il codice
            layout = this.getLayoutAlgos();
            if (layout != null) {
                layout.setScorrevoleBordato(flag);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Abilita l'uso del gap fisso.
     * <p/>
     *
     * @param usaGapFisso true per usare il gap fisso, false per il gap variabile
     */
    public void setUsaGapFisso(boolean usaGapFisso) {
        /* variabili e costanti locali di lavoro */
        Layout layout;

        try {    // prova ad eseguire il codice
            layout = this.getLayoutAlgos();
            if (layout != null) {
                layout.setUsaGapFisso(usaGapFisso);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il gap effettivo (fisso).
     * <p/>
     * Questo e' il gap che viene usato. <br>
     *
     * @param gap fisso
     */
    public void setGapFisso(int gap) {
        try {    // prova ad eseguire il codice
            this.setUsaGapFisso(true);
            this.setGapPreferito(gap);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il gap preferito.
     * <p/>
     * Se si usa il gap fisso, questo e' il gap che viene usato. <br>
     *
     * @param gapPreferito il gap preferito (o fisso)
     */
    public void setGapPreferito(int gapPreferito) {
        /* variabili e costanti locali di lavoro */
        Layout layout;

        try {    // prova ad eseguire il codice
            layout = this.getLayoutAlgos();
            if (layout != null) {
                layout.setGapPreferito(gapPreferito);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il gap minimo.
     * <p/>
     * Significativo solo se si usa il gap variabile. <br>
     *
     * @param gapMinimo il gap minimo
     */
    public void setGapMinimo(int gapMinimo) {
        /* variabili e costanti locali di lavoro */
        Layout layout;

        try {    // prova ad eseguire il codice
            layout = this.getLayoutAlgos();
            if (layout != null) {
                layout.setGapMinimo(gapMinimo);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il gap massimo.
     * <p/>
     * Significativo solo se si usa il gap variabile. <br>
     *
     * @param gapMassimo il gap massimo
     */
    public void setGapMassimo(int gapMassimo) {
        /* variabili e costanti locali di lavoro */
        Layout layout;

        try {    // prova ad eseguire il codice
            layout = this.getLayoutAlgos();
            if (layout != null) {
                layout.setGapMassimo(gapMassimo);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il tipo di allineamento dei componenti nel verso
     * perpendicolare a quello del layout.
     * <p/>
     *
     * @param allineamento il codice dell'allineamento
     *                     puo' essere ALLINEA_ALTO, ALLINEA_SX, ALLINEA_BASSO, ALLINEA_DX,
     *                     ALLINEA_CENTRO, ALLINEA_DA_COMPONENTI
     *
     * @see Layout
     */
    public void setAllineamento(int allineamento) {
        /* variabili e costanti locali di lavoro */
        Layout layout;

        try {    // prova ad eseguire il codice
            layout = this.getLayoutAlgos();
            if (layout != null) {
                layout.setAllineamento(allineamento);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Considera anche i componenti invisibili
     * disegnando il contenitore.
     * <p/>
     *
     * @param flag true per considerare anche i componenti invisibili
     */
    public void setConsideraComponentiInvisibili(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Layout layout;

        try {    // prova ad eseguire il codice
            layout = this.getLayoutAlgos();
            if (layout != null) {
                layout.setConsideraComponentiInvisibili(flag);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il layout Algos (oggetto Layout) di questo pannello.
     * <p/>
     * Potrebbe essere nullo se il layout non e' stato assegnato
     * oppure non e' di tipo Layout <br>
     *
     * @return il layout Algos di questo pannello.
     */
    public Layout getLayoutAlgos() {
        /* variabili e costanti locali di lavoro */
        Object oggetto;
        LayoutManager layoutManager;
        Layout layout = null;

        oggetto = this.getLayout();
        if (oggetto != null) {
            if (oggetto instanceof LayoutManager) {
                layoutManager = (LayoutManager)oggetto;
                if (layoutManager instanceof Layout) {
                    layout = (Layout)layoutManager;
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        /* valore di ritorno */
        return layout;
    }


    /**
     * Fissa la dimensione di questo pannello a una misura data.
     * <p/>
     * Regola le variabili preferredSize, minimumSize e maximumSize
     * tutte al valore dato. <br>
     *
     * @param lar larghezza
     * @param alt larghezza
     */
    public void setDimFissa(int lar, int alt) {
        this.setPreferredSize(lar, alt);
        this.bloccaDim();
    }


    /**
     * Regola la dimensione preferita del pannello.
     * <p/>
     *
     * @param w la larghezza
     * @param h l'altezza
     */
    public void setPreferredSize(int w, int h) {
        this.setPreferredSize(new Dimension(w, h));
    }


    /**
     * Regola la dimensione minima del pannello.
     * <p/>
     *
     * @param w la larghezza
     * @param h l'altezza
     */
    public void setMinimumSize(int w, int h) {
        this.setMinimumSize(new Dimension(w, h));
    }


    /**
     * Regola la dimensione massima del pannello.
     * <p/>
     *
     * @param w la larghezza
     * @param h l'altezza
     */
    public void setMaximumSize(int w, int h) {
        this.setMaximumSize(new Dimension(w, h));
    }


    /**
     * Regola la larghezza preferita del pannello.
     * <p/>
     * Mantiene l'altezza preferita corrente.<br>
     * (se non era specificata, la pone pari a zero)<br>
     *
     * @param w il valore da assegnare alla larghezza preferita
     */
    public void setPreferredWidth(int w) {
        Lib.Comp.setPreferredWidth(this, w);
    }


    /**
     * Regola l'altezza preferita del pannello.
     * <p/>
     * Mantiene la larghezza preferita corrente.<br>
     * (se non era specificata, la pone pari a zero)<br>
     *
     * @param h il valore da assegnare all'altezza preferita
     */
    public void setPreferredHeigth(int h) {
        Lib.Comp.setPreferredHeigth(this, h);
    }


    /**
     * Regola la larghezza minima del pannello.
     * <p/>
     * Mantiene l'altezza minima corrente.<br>
     * (se non era specificata, la pone pari a zero)<br>
     *
     * @param w il valore da assegnare alla larghezza minima
     */
    public void setMinimumWidth(int w) {
        Lib.Comp.setMinimumWidth(this, w);
    }


    /**
     * Regola l'altezza mimina del pannello.
     * <p/>
     * Mantiene la larghezza mimina corrente.<br>
     * (se non era specificata, la pone pari a zero)<br>
     *
     * @param h il valore da assegnare all'altezza mimina
     */
    public void setMinimumHeigth(int h) {
        Lib.Comp.setMinimumHeigth(this, h);
    }


    /**
     * Regola la larghezza massima di un componente.
     * <p/>
     * Mantiene l'altezza massima corrente.<br>
     * (se non era specificata, la pone pari a infinito)<br>
     *
     * @param w il valore da assegnare alla larghezza massima
     */
    public void setMaximumWidth(int w) {
        Lib.Comp.setMaximumWidth(this, w);
    }


    /**
     * Regola l'altezza massima di un componente.
     * <p/>
     * Mantiene la larghezza massima corrente.<br>
     * (se non era specificata, la pone pari a infinito)<br>
     *
     * @param h il valore da assegnare all'altezza massima
     */
    public void setMaximumHeigth(int h) {
        Lib.Comp.setMaximumHeigth(this, h);
    }


    /**
     * Blocca la larghezza massima di questo componente.
     * <p/>
     * Pone la larghezza massima pari a quella preferita.
     */
    public void bloccaLarMax() {
        Lib.Comp.bloccaLarMax(this);
    }


    /**
     * Blocca la larghezza minima di questo componente.
     * <p/>
     * Pone la larghezza minima pari a quella preferita.
     */
    public void bloccaLarMin() {
        Lib.Comp.bloccaLarMin(this);
    }


    /**
     * Blocca la larghezza di questo componente.
     * <p/>
     * Pone la larghezza minima e massima pari a quella preferita.
     */
    public void bloccaLarghezza() {
        Lib.Comp.bloccaLarghezza(this);
    }


    /**
     * Blocca l'altezza massima di questo componente.
     * <p/>
     * Pone l'altezza massima pari a quella preferita.
     */
    public void bloccaAltMax() {
        Lib.Comp.bloccaAltMax(this);
    }


    /**
     * Blocca l'altezza minima di questo componente.
     * <p/>
     * Pone l'altezza minima pari a quella preferita.
     */
    public void bloccaAltMin() {
        Lib.Comp.bloccaAltMin(this);
    }


    /**
     * Blocca l'altezza di questo componente.
     * <p/>
     * Pone l'altezza minima e massima pari a quella preferita.
     */
    public void bloccaAltezza() {
        Lib.Comp.bloccaAltezza(this);
    }


    /**
     * Blocca dimensione massima di questo componente.
     * <p/>
     * Pone la dimensione massima pari alla preferredSize.
     */
    public void bloccaDimMax() {
        Lib.Comp.bloccaDimMax(this);
    }


    /**
     * Blocca dimensione minima di questo componente.
     * <p/>
     * Pone la dimensione minima pari alla preferredSize.
     */
    public void bloccaDimMin() {
        Lib.Comp.bloccaDimMin(this);
    }


    /**
     * Blocca dimensione questo componente.
     * <p/>
     * Pone le dimensioni minima e massima pari alla preferredSize.
     */
    public void bloccaDim() {
        Lib.Comp.bloccaDim(this);
    }


    /**
     * Sblocca la larghezza massima di questo componente.
     * <p/>
     * Pone la larghezza massima pari a infinito.
     */
    public void sbloccaLarMax() {
        Lib.Comp.sbloccaLarMax(this);
    }


    /**
     * Sblocca la larghezza minima di questo componente.
     * <p/>
     * Pone la larghezza minima pari a zero.
     */
    public void sbloccaLarMin() {
        Lib.Comp.sbloccaLarMin(this);
    }


    /**
     * Sblocca la larghezza di questo componente.
     * <p/>
     * Pone la larghezza minima a zero e massima a infinito.
     */
    public void sbloccaLarghezza() {
        Lib.Comp.sbloccaLarghezza(this);
    }


    /**
     * Sblocca l'altezza massima di questo componente.
     * <p/>
     * Pone la altezza massima pari a infinito.
     */
    public void sbloccaAltMax() {
        Lib.Comp.sbloccaAltMax(this);
    }


    /**
     * Sblocca l'altezza minima di questo componente.
     * <p/>
     * Pone l'altezza minima pari a zero.
     */
    public void sbloccaAltMin() {
        Lib.Comp.sbloccaAltMin(this);
    }


    /**
     * Sblocca l'altezza di questo componente.
     * <p/>
     * Pone l'altezza minima a zero e massima a infinito.
     */
    public void sbloccaAltezza() {
        Lib.Comp.sbloccaAltezza(this);
    }


    /**
     * Sblocca dimensione massima in larghezza e in altezza di questo componente.
     * <p/>
     * Pone la larghezza massima a infinito e l'altezza massima a infinito.
     */
    public void sbloccaDimMax() {
        Lib.Comp.sbloccaDimMax(this);
    }


    /**
     * Sblocca dimensione minima in larghezza e in altezza di questo componente.
     * <p/>
     * Pone la larghezza minima a zero e l'altezza minima a zero.
     */
    public void sbloccaDimMin() {
        Lib.Comp.sbloccaDimMin(this);
    }


    /**
     * Sblocca dimensione massima in larghezza e in altezza di questo componente.
     * <p/>
     * Pone la dimensione minima a zero e la massima a infinito.
     */
    public void sbloccaDim() {
        Lib.Comp.sbloccaDim(this);
    }


    /**
     * Crea un bordo con margini interni e titolo.
     * <p/>
     *
     * @param top    margine
     * @param left   margine
     * @param bottom margine
     * @param right  margine
     * @param titolo eventuale del bordo
     *
     * @return il bordo creato
     */
    public Border creaBordo(int top, int left, int bottom, int right, String titolo) {
        /* variabili e costanti locali di lavoro */
        Border bordo = null;
        Border bordo0;
        Border bordo1;
        String spazio = " ";

        try {    // prova ad eseguire il codice
            if (Lib.Testo.isValida(titolo)) {
                titolo = spazio + titolo + spazio;
                bordo0 = BorderFactory.createEmptyBorder(top, left, bottom, right);
                bordo1 = BorderFactory.createTitledBorder(titolo);
                bordo = BorderFactory.createCompoundBorder(bordo1, bordo0);
            } else {
                bordo0 = BorderFactory.createEmptyBorder(top, left, bottom, right);
                bordo1 = BorderFactory.createTitledBorder("");
                bordo = BorderFactory.createCompoundBorder(bordo1, bordo0);
            }// fine del blocco if-else

            this.getPanFisso().setBorder(bordo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return bordo;
    }


    /**
     * Crea un bordo con margini interni senza titolo.
     * <p/>
     *
     * @param top    margine
     * @param left   margine
     * @param bottom margine
     * @param right  margine
     *
     * @return il bordo creato
     */
    public Border creaBordo(int top, int left, int bottom, int right) {
        /* invoca il metodo sovrascritto della superclasse */
        return this.creaBordo(top, left, bottom, right, "");
    }


    /**
     * Crea un bordo con margine interno e titolo.
     * <p/>
     * Inserisce un carattere vuoto all'inizio ed alla fine del testo <br>
     *
     * @param margine valido per tutti i lati
     * @param titolo  eventuale del bordo
     *
     * @return il bordo creato
     */
    public Border creaBordo(int margine, String titolo) {
        /* invoca il metodo sovrascritto della superclasse */
        return this.creaBordo(margine, margine, margine, margine, titolo);
    }


    /**
     * Crea un bordo con margine interno senza titolo.
     * <p/>
     *
     * @param margine valido per tutti i lati
     *
     * @return il bordo creato
     */
    public Border creaBordo(int margine) {
        /* invoca il metodo sovrascritto della superclasse */
        return this.creaBordo(margine, margine, margine, margine, "");
    }


    /**
     * Crea un bordo con titolo e margine interno di default di 10 pixel.
     * <p/>
     *
     * @param titolo del bordo
     *
     * @return il bordo creato
     */
    public Border creaBordo(String titolo) {
        /* invoca il metodo sovrascritto della superclasse */
        return this.creaBordo(MARGINE_STANDARD, titolo);
    }


    /**
     * Crea un bordo senza titolo e con margine interno di default di 10 pixel.
     * <p/>
     *
     * @return il bordo creato
     */
    public Border creaBordo() {
        /* invoca il metodo sovrascritto della superclasse */
        return this.creaBordo(MARGINE_STANDARD, "");
    }

//    /**
//     * Regola l'opacita' del pannelllo.
//     * <p/>
//     * @param flag true opaco, false trasparente
//     */
//    public void setOpaque(boolean flag) {
//        this.getPannello().setOpaque(flag);
//    }
//
//    /**
//     * Regola il colore di sfondo del pannello.
//     * <p/>
//     * @param colore di sfondo
//     */
//    public void setBackground(Color colore) {
//        this.getPannello().setBackground(colore);
//    }
//
//
//    /**
//     * Regola il colore di primo piano pannello.
//     * <p/>
//     * @param colore di primo piano
//     */
//    public void setForeground(Color colore) {
//        this.getPannello().setForeground(colore);
//    }


    /**
     * Ritorna questo pannello come oggetto di questa classe.
     * <p/>
     *
     * @return questo pannello
     */
    public PannelloBase getPanFisso() {
        return this;
    }


    /**
     * Recupera un singolo campo contenuto nel Pannello.
     * <p/>
     *
     * @param pos la posizione nell'elenco dei campi
     *            (0 per il primo)
     *
     * @return il campo recuperato
     */
    public Campo getCampoPannello(int pos) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        List campi = null;

        try { // prova ad eseguire il codice
            campi = this.getCampiPannello();
            if (campi.size() > pos) {
                campo = (Campo)campi.get(pos);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Recupera un singolo campo contenuto nel Pannello.
     * <p/>
     *
     * @param nome il nome interno del campo
     *
     * @return il campo recuperato
     */
    public Campo getCampoPannello(String nome) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        Campo unCampo = null;
        List campi = null;
        String unNome = "";

        try { // prova ad eseguire il codice
            campi = this.getCampiPannello();
            for (int k = 0; k < campi.size(); k++) {
                unCampo = (Campo)campi.get(k);
                unNome = unCampo.getNomeInterno();
                if (unNome.equalsIgnoreCase(nome)) {
                    campo = unCampo;
                    break;
                }// fine del blocco if
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Ritorna l'elenco dei campi contenuti nel Pannello.
     * <p/>
     * Discende ricorsivamente i contenuti grafici alla ricerca
     * di oggetti PannelloCampo.<br>
     *
     * @return la lista dei campi contenuti nel pannello
     */
    public ArrayList<Campo> getCampiPannello() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi = null;

        try { // prova ad eseguire il codice
            campi = Lib.Comp.estraeCampi(this);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


    /**
     * Ritorna i componenti effettivi di questo pannello.
     * <p/>
     * Ignora i componenti fittizi usati per il dimensionamento
     * (fillers, etc.) <br>
     *
     * @return i componenti effettivi.
     */
    public Component[] getComponentiEffettivi() {
        /* variabili e costanti locali di lavoro */
        Component[] compTutti;
        Component[] compEff = null;
        ArrayList compOut;
        Component comp;

        try {    // prova ad eseguire il codice
            compOut = new ArrayList();
            compTutti = super.getComponents();
            for (int k = 0; k < compTutti.length; k++) {
                comp = compTutti[k];
                if ((comp instanceof Box.Filler) == false) {
                    compOut.add(comp);
                }// fine del blocco if
            } // fine del ciclo for

            compEff = new Component[compOut.size()];
            for (int k = 0; k < compOut.size(); k++) {
                comp = (Component)compOut.get(k);
                compEff[k] = comp;
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return compEff;
    }


    /**
     * Aggiunge un listener.
     * <p/>
     * Aggiunge uno specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void addListener(Eventi evento, BaseListener listener) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            evento.add(listaListener, listener);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati
     * per questo tipo di evento <br>
     * L'evento viene creato al momento <br>
     * È responsabilità della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void fire(Eventi unEvento) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Lib.Eventi.fire(listaListener, unEvento, Pannello.class, this);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Avvisa tutti i listener dell'evento Pannello Modificato.
     */
    public void fireModificato() {
        this.fire(PannelloBase.Evento.modifica);
    }


    private EventListenerList getListaListener() {
        return listaListener;
    }


    private void setListaListener(EventListenerList listaListener) {
        this.listaListener = listaListener;
    }


    private ContenitoreCampi getContenitoreCampi() {
        return this.fonteCampi;
    }


    /**
     * Assegna il riferimento a un contenitore di campi.
     * <p/>
     *
     * @param cont il contenitore di campi di riferimento
     */
    public void setContenitoreCampi(ContenitoreCampi cont) {
        this.fonteCampi = cont;
    }


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Eventi che vengono lanciati dal modello <br>
     * Per ogni evento: <ul>
     * <li> classe interfaccia </li>
     * <li> classe evento </li>
     * <li> classe azione </li>
     * <li> metodo azione </li>
     * </ul>
     */
    public enum Evento implements Eventi {

        modifica(PanModificatoLis.class,
                PanModificatoEve.class,
                PanModificatoAz.class,
                "panModificatoAz");

        /**
         * interfaccia listener per l'evento
         */
        private Class listener;

        /**
         * classe evento
         */
        private Class evento;

        /**
         * classe azione
         */
        private Class azione;

        /**
         * metodo
         */
        private String metodo;


        /**
         * Costruttore completo con parametri.
         *
         * @param listener interfaccia
         * @param evento   classe
         * @param azione   classe
         * @param metodo   nome metodo azione
         */
        Evento(Class listener, Class evento, Class azione, String metodo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setListener(listener);
                this.setEvento(evento);
                this.setAzione(azione);
                this.setMetodo(metodo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Metodo statico <br>
         * Serve per utilizzare questa Enumeration <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista    degli eventi a cui aggiungersi
         * @param listener dell'evento da lanciare
         */
        public static void addLocale(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.add(Evento.values(), lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Metodo statico <br>
         * Serve per utilizzare questa Enumeration <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista    degli eventi da cui rimuoverlo
         * @param listener dell'evento da non lanciare
         */
        public static void removeLocale(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.remove(Evento.values(), lista, listener);
        }


        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Serve per utilizzare la Enumeration della sottoclasse <br>
         * Metodo (sovra)scritto nelle Enumeration specifiche
         * (le Enumeration delle sottoclassi della classe dove
         * e' questa Enumeration) <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista    degli eventi a cui aggiungersi
         * @param listener dell'evento da lanciare
         */
        public void add(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.add(Evento.values(), lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Serve per utilizzare la Enumeration della sottoclasse <br>
         * Metodo (sovra)scritto nelle Enumeration specifiche
         * (le Enumeration delle sottoclassi della classe dove
         * e' questa Enumeration) <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista    degli eventi da cui rimuoverlo
         * @param listener dell'evento da non lanciare
         */
        public void remove(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.remove(Evento.values(), lista, listener);
        }


        public Class getListener() {
            return listener;
        }


        private void setListener(Class listener) {
            this.listener = listener;
        }


        public Class getEvento() {
            return evento;
        }


        private void setEvento(Class evento) {
            this.evento = evento;
        }


        public Class getAzione() {
            return azione;
        }


        private void setAzione(Class azione) {
            this.azione = azione;
        }


        public String getMetodo() {
            return metodo;
        }


        private void setMetodo(String metodo) {
            this.metodo = metodo;
        }


    }// fine della classe

}// fine della classe