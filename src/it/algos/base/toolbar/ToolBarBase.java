/**
 * Title:        ToolBarBase.java
 * Package:      it.algos.base.toolbar
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 7 settembre 2003 alle 10.12
 */
package it.algos.base.toolbar;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.gestore.Gestore;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.portale.Portale;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

/**
 * ToolBar generica.
 * <p/>
 * Classe specializzata che estende <i>direttamente</i> la <code>JToolBar</code> <br>
 * &Egrave; costituita da una serie di bottoni/<code>Azione</code> <br>
 * La <i>toolbar</i> &egrave; collocata all'interno di un <code>Portale</code> che ne
 * &egrave; proprietario <br>
 * Questa classe astratta mantiene gli attributi: <ul>
 * <li> Orientamento verticale od orizzontale (default verticale) </li>
 * <li> Colore dello sfondo (default grigio) </li>
 * <li> Uso del testo descrittivo nel bottone (default falso) </li>
 * <li> Icone piccole, medie, grandi (default medie) </li>
 * <li> Abilitazione ToolTip (default vero) </li>
 * <li> Funzionalit&agrave; di rollover (default falso) </li>
 * <li> Separatore tra i bottoni (default falso) </li>
 * </ul>
 * <p/>
 * Il <i>Costruttore</i> prevede obbligatoriamente il <code>Portale</code> <br>
 * Il ciclo <i>Inizializza</i> viene eseguito una volta sola <br>
 * Viene mantenuto un flag <i>isInizializzato</i> per controllare l'unicit&agrave;
 * della inizializzazione <br>
 * Il ciclo <i>Avvia</i> non &egrave; previsto <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  7 settembre 2003 ore 10.12
 */
public abstract class ToolBarBase extends JToolBar implements ToolBar {

    /**
     * usa un colore di sfondo per vedere l'oggetto facilmente
     */
    protected static final boolean DEBUG = false;

    /**
     * margine di default
     */
    private static final int M = 1;

    /**
     * margini di default (sopra, sinistra, sotto, destra)
     */
    private static final Insets MARGINI = new Insets(M, M, M, M);

    /**
     * default per l'orientamento
     */
    private static final boolean USA_VERTICALE = true;

    /**
     * default per mostrare il testo
     */
    private static final boolean USA_TESTO = false;

    /**
     * default per la funzionalità di rollover
     */
    private static final boolean USA_ROLLOVER = true;

    /**
     * default per il separatore tra i botttoni
     */
    private static final boolean USA_SEPARATORE = false;

    /**
     * default per usare l'icona media
     */
    private static final int ICONA_DEFAULT = ToolBar.ICONA_MEDIA;

    /**
     * Riferimento al Portale proprietario di questa ToolBar <br>
     */
    private Portale portale = null;

    /**
     * Mappa ordinata dei bottoni presenti nella toolbar
     */
    private LinkedHashMap<String, JButton> mappaBottoni;

    /**
     * flag - visualizza il testo dell'azione oltre all'icona
     */
    private boolean isMostraTesto = false;

    /**
     * flag - separatore tra i bottoni
     */
    private boolean isUsaSeparatore = false;

    /**
     * flag - seleziona l'icona piccola, media o grande
     */
    private int tipoIcona = 0;

    /**
     * bottone di escape
     */
    private JButton botEscape = null;

    /**
     * bottone di enter
     */
    private JButton botEnter = null;


    /**
     * Costruttore completo con parametri.
     *
     * @param unPortale portale proprietario di questa toolbar
     */
    public ToolBarBase(Portale unPortale) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setPortale(unPortale);

        /* regolazioni iniziali di riferimenti e variabili */
        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* crea la mappa bottoni */
        this.setMappaBottoni(new LinkedHashMap<String, JButton>());

        /* regolazioni di default */
        this.setVerticale(USA_VERTICALE);
        this.setMostraTesto(USA_TESTO);
        this.setTipoIcona(ICONA_DEFAULT);
        this.setRollover(USA_ROLLOVER);
        this.setUsaSeparatore(USA_SEPARATORE);
        this.setMargin(MARGINI);
        this.setFloatable(false);
        this.setBorderPainted(false);
        this.setOpaque(false);
        if (DEBUG) {
            this.setOpaque(true);
            this.setBackground(Color.CYAN);
        }// fine del blocco if
    } /* fine del metodo inizia */


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Viene chiamato una volta sola <br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */

        // se il portale lo prevede, aggiunge i bottoni standard
        Portale portale;
        portale = this.getPortale();
        if (portale!=null) {
            boolean usa;
            usa = portale.isUsaPulsantiStandard();
            if (usa) {
                this.addBottoniStandard();
            }// fine del blocco if

        }// fine del blocco if
        

    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa)
     * ogni volta che questo oggetto deve <i>ripartire</i>,
     * per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void avvia() {
    }


    /**
     * Aggiunge i bottoni standard.
     * <p/>
     * Implementato nelle sottoclassi
     */
    protected void addBottoniStandard() {
    }



    /**
     * Regola i caratteri speciali dell'azione.
     * <p/>
     * Regola il carattere acceleratore <br>
     * Regola il carattere mnemonico <br>
     * todo per ora non funziona bene
     *
     * @param unBottone bottone con l'azione da regolare
     */
    private void regolaAzione(JButton unBottone) {
        /* variabili e costanti locali di lavoro */
        Character carattere = null;
        Integer memo = null;
        Azione azione = null;

        try {    // prova ad eseguire il codice
//                carattere
//                        = (Character)unAzione.getAzione().getValue(
//                                Action.ACCELERATOR_KEY);
            azione = (Azione)unBottone.getAction();
            memo = (Integer)azione.getAzione().getValue(Action.MNEMONIC_KEY);
            if (memo != null) {

//                        this.putValue(
//                                ACCELERATOR_KEY,
//                                KeyStroke.getKeyStroke(
//                                        unCarattereAcceleratore, ActionEvent.CTRL_MASK));

            }// fine del blocco if
            if (carattere == null) {
//                      unBottone.setMnemonic(memo.intValue());
//                            } else {
//                                if (carattere.charValue()=='s') {
//                                    unBottone.setMnemonic(carattere.charValue());
//                                } else {
//                                    unBottone.setMnemonic(memo.intValue());
//                                }// fine del blocco if-else
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un bottone alla toolbar.
     * <p/>
     * Costruisce il bottone con l'azione<br>
     * Regola la visibilita' del testo secondo il flag <br>
     * Regola l'icona da mostrare secondo il flag <br>
     * Inserisce il separatore secondo il flag <br>
     * Invoca il metodo delegato per regolare i caratteri dell'azione <br>
     *
     * @param azione da aggiungere
     */
    public JButton addBottone(Azione azione) {
        /* invoca il metodo sovrascritto */
        return addBottone(azione, -1);
    }


    /**
     * Aggiunge un'azione alla toolbar.
     * <p/>
     * Costruisce il bottone con l'azione <br>
     * Recupera l'azione dalla collezione del Portale che contiene questa toolbar
     * Regola la visibilit&agrave; del testo secondo il flag <br>
     * Regola l'icona da mostrare secondo il flag <br>
     * Inserisce il separatore secondo il flag <br>
     * Invoca il metodo delegato per regolare i caratteri dell'azione <br>
     *
     * @param chiaveAzione dell'azione da aggiungere (dalla collezione del Portale)
     *
     * @return il bottone aggiunto
     */
    protected JButton addBottone(String chiaveAzione) {
        /* variabili e costanti locali di lavoro */
        JButton bottone = null;
        Azione azione;

        try { // prova ad eseguire il codice

            /* recupera l'azione da Portale */
            azione = this.getAzione(chiaveAzione);

            if (azione != null) {
                bottone = this.addBottone(azione);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Aggiunge un bottone alla toolbar alla posizione desiderata (0 per la prima).
     * <p/>
     * Costruisce il bottone con l'azione<br>
     *
     * @param azione da aggiungere
     * @param pos posizione nella lista di componenti
     *
     * @return il bottone aggiunto
     */
    public JButton addBottone(Azione azione, int pos) {
        /* variabili e costanti locali di lavoro */
        JButton unBottone = null;
        ImageIcon unIcona = null;
        boolean continua;
        LinkedHashMap<String, JButton> mappaBottoni;
        String chiave = "";
        ImageIcon rollIcon;

        try { // prova ad eseguire il codice

            continua = (azione != null);

            /* controllo di congruità */
            if (continua) {

                /* inizializza l'azione */
                azione.inizializza();

                /* recupera l'icona di questa azione */
                switch (this.getTipoIcona()) {
                    case ToolBar.ICONA_PICCOLA:
                        unIcona = azione.getIconaPiccola();
                        break;
                    case ToolBar.ICONA_MEDIA:
                        unIcona = azione.getIconaMedia();
                        break;
                    case ToolBar.ICONA_GRANDE:
                        unIcona = azione.getIconaGrande();
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

                /* costruisce il bottone con l'azione prevista */
                unBottone = new JButton(azione.getAzione());
                unBottone.setDisplayedMnemonicIndex(-1);
                if (!this.isMostraTesto()) {
                    unBottone.setText("");
                } /* fine del blocco if */

                unBottone.setMargin(new Insets(0, 0, 0, 0));
                unBottone.setOpaque(false);
                unBottone.setBorderPainted(false);
                unBottone.setRolloverEnabled(true);

                /* regola il bottone come non focusable
                 * in modo che non entri nel ciclo del fuoco */
                unBottone.setFocusable(false);

                /* controllo di congruità */
                if (unIcona != null) {

                    /* assegna l'icona al bottone */
                    unBottone.setIcon(unIcona);

//                    /* crea e assegna l'icona rollover */
//                    rollIcon = this.creaIconaRollover(unIcona);
//                    unBottone.setRolloverIcon(rollIcon);

                } /* fine del blocco if */

                /* aggiunge il bottone alla toolbar */
                this.add(unBottone, pos);

                /* aggiunge il bottone alla mappa bottoni
                 * usa la chiave dell'azione
                 * se manca, usa come chiave il nome della classe dell'azione */
                mappaBottoni = this.getMappaBottoni();
                chiave = azione.getChiave();
                if (!Lib.Testo.isValida(chiave)) {
                    chiave = azione.getClass().getName();
                }// fine del blocco if
                mappaBottoni.put(chiave, unBottone);

                /* aggiunge il separatore, se il flag lo prevede */
                if (this.isUsaSeparatore()) {
                    this.addSeparator();
                }// fine del blocco if

                /* Invoca il metodo delegato per regolare i caratteri dell'azione */
                this.regolaAzione(unBottone);

                /* aggiunge il gestore delle azioni */
                this.addGestore(azione);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unBottone;
    }


    /**
     * Crea l'icona Rollover in base all'icona principale.
     * <p/>
     *
     * @param mainIcon principale
     *
     * @return icona rollover
     */
    private ImageIcon creaIconaRollover(ImageIcon mainIcon) {
        /* variabili e costanti locali di lavoro */
        ImageIcon rollIcon = null;
        BufferedImage bIcona;
        BufferedImage bMask;
        Graphics graph;

        try {    // prova ad eseguire il codice

            /* recupero due copie della BufferedImage dell'icona */
            bIcona = Lib.Image.toBufferedImage(mainIcon.getImage());
            bMask = Lib.Image.toBufferedImage(mainIcon.getImage());

            /* disegno la mask */
            graph = bMask.createGraphics();
            graph.setXORMode(Color.gray);
            graph.drawImage(bIcona, 0, 0, null);
            graph.dispose();

            /* sposto la mask di 2 pixel a destra e in basso */
            AffineTransform tx = new AffineTransform();
            tx.translate(2, 2);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            bMask = op.filter(bMask, null);

            /* disegno l'icona principale sopra la mask */
            graph = bMask.createGraphics();
            graph.drawImage(bIcona, 0, 0, null);
            graph.dispose();

            /* creo l'icona da ritornare */
            rollIcon = new ImageIcon(bMask);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return rollIcon;
    }


    /**
     * Assegna il gestore a un'azione.
     * <p/>
     *
     * @param azione a cui aggiungere il gestore
     */
    public void addGestore(Azione azione) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Portale port;
        Navigatore nav = null;
        Modulo mod = null;
        Gestore gest = null;

        try { // prova ad eseguire il codice
            port = this.getPortale();
            continua = port != null;

            if (continua) {
                nav = port.getNavigatore();
                continua = nav != null;
            }// fine del blocco if

            if (continua) {
                mod = nav.getModulo();
                continua = mod != null;
            }// fine del blocco if

            if (continua) {
                gest = mod.getGestore();
                continua = gest != null;
            }// fine del blocco if

            if (continua) {
                azione.setGestore(gest);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un separatore.
     * <p/>
     * Sovrascrive il metodo della superclasse.
     * Non uso un JToolbar.Separator ma un normale bottone invisibile
     * perché il JToolbar.Separator non viene allineato correttamente
     * e provoca un disallineamento dei margini.
     */
    public void addSeparator() {
        /* variabili e costanti locali di lavoro */
        JButton bot;
        Dimension dim = new Dimension(8, 8);

        try {    // prova ad eseguire il codice
            bot = new JButton();
            bot.setPreferredSize(dim);
            Lib.Comp.bloccaDim(bot);
            bot.setEnabled(false);
            bot.setBorder(BorderFactory.createEmptyBorder());
            bot.setOpaque(false);
            if (DEBUG) {
                bot.setOpaque(true);
                bot.setBackground(Color.RED);
            }// fine del blocco if
            this.add(bot);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Recupera l'azione dal <code>Portale</code>.
     * <p/>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return l'azione da restituire
     */
    private Azione getAzione(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione = null;
        Portale portale;

        try {    // prova ad eseguire il codice
            portale = this.getPortale();
            if (portale != null) {
                azione = this.getPortale().getAzione(unaChiave);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return azione;
    }


    /**
     * Recupera un bottone dalla mappa bottoni tramite la chiave
     * <p/>
     *
     * @param chiave nome chiave del bottone
     * Se il bottone è stato costruito con un'azione che aveva la chiave, è la chiave dell'azione
     * Se l'azione non aveva chiave, è il nome della classe dell'azione
     *
     * @return il bottone richiesto
     */
    public JButton getBottone(String chiave) {
        /* variabili e costanti locali di lavoro */
        JButton bottone = null;
        LinkedHashMap<String, JButton> mappaBottoni;

        try {    // prova ad eseguire il codice
            mappaBottoni = this.getMappaBottoni();
            bottone = mappaBottoni.get(chiave);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Rimuove un bottone dalla toolbar.
     * <p/>
     *
     * @param chiave nome chiave del bottone
     * Se il bottone è stato costruito con un'azione che aveva la chiave, è la chiave dell'azione
     * Se l'azione non aveva chiave, è il nome della classe dell'azione
     */
    public void removeBottone(String chiave) {
        /* variabili e costanti locali di lavoro */
        Component bottone;

        try {    // prova ad eseguire il codice
            bottone = this.getBottone(chiave);
            if (bottone != null) {
                this.getToolBar().remove(bottone);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    protected Portale getPortale() {
        return portale;
    }


    public void setPortale(Portale portale) {
        this.portale = portale;
    }


    private LinkedHashMap<String, JButton> getMappaBottoni() {
        return mappaBottoni;
    }


    private void setMappaBottoni(LinkedHashMap<String, JButton> mappaBottoni) {
        this.mappaBottoni = mappaBottoni;
    }


    /**
     * Ritorna true se la toolbar e' verticale.
     * <p/>
     *
     * @return true se la toolbar e' verticale
     */
    public boolean isVerticale() {
        /* variabili e costanti locali di lavoro */
        boolean verticale = false;
        JToolBar jtb = null;
        int orient = 0;

        try { // prova ad eseguire il codice
            jtb = this.getToolBar();
            if (jtb != null) {
                orient = jtb.getOrientation();
                if (orient == JToolBar.VERTICAL) {
                    verticale = true;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return verticale;
    }


    /**
     * Regola l'orientamento della toolbar, orizzontale o verticale.
     * <p/>
     *
     * @param flag true per verticale, false per orizzontale
     */
    public void setVerticale(boolean flag) {
        /* variabili e costanti locali di lavoro */
        JToolBar jtb;

        try { // prova ad eseguire il codice
            jtb = this.getToolBar();
            if (jtb != null) {
                if (flag) {
                    jtb.setOrientation(JToolBar.VERTICAL);
                } else {
                    jtb.setOrientation(JToolBar.HORIZONTAL);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola l'orientamento della toolbar, orizzontale o verticale.
     * <p/>
     *
     * @param orientamento JToolBar.HORIZONTAL o JToolBar.VERTICAL
     */
    public void setOrientamento(int orientamento) {
        /* variabili e costanti locali di lavoro */
        JToolBar jtb;

        try { // prova ad eseguire il codice
            jtb = this.getToolBar();
            if (jtb != null) {
                jtb.setOrientation(orientamento);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private boolean isMostraTesto() {
        return isMostraTesto;
    }


    public void setMostraTesto(boolean mostraTesto) {
        isMostraTesto = mostraTesto;
    }


    private boolean isUsaSeparatore() {
        return isUsaSeparatore;
    }


    public void setUsaSeparatore(boolean usaSeparatore) {
        isUsaSeparatore = usaSeparatore;
    }


    /**
     * Ritorna il tipo di icone utilizzate
     * <p/>
     *
     * @return il tipo, ToolBar.ICONA_PICCOLA, ToolBar.ICONA_MEDIA, ToolBar.ICONA_GRANDE
     */
    public int getTipoIcona() {
        return tipoIcona;
    }


    /**
     * Assegna il tipo di icone utilizzate
     * <p/>
     *
     * @param tipo, ToolBar.ICONA_PICCOLA, ToolBar.ICONA_MEDIA, ToolBar.ICONA_GRANDE
     */
    public void setTipoIcona(int tipo) {
        tipoIcona = tipo;
    }


    /**
     * Determina se è usato il bottone Nuovo Record nella lista
     * <p/>
     *
     * @return true se è usato
     */
    public boolean isUsaNuovo() {
        return false;
    }


    public void setUsaNuovo(boolean usaNuovo) {
    }


    /**
     * Determina il tipo di bottoni nuovo/elimina della lista
     * <p/>
     * Usa la coppia nuovo/elimina oppure la coppia aggiungi/rimuovi <br>
     *
     * @param tipoBottoni nuovo/elimina oppure aggiungi/rimuovi
     */
    public void setTipoBottoni(Lista.Bottoni tipoBottoni) {
    }


    public void setUsaModifica(boolean usaModifica) {
    }


    public void setUsaElimina(boolean usaElimina) {
    }


    /**
     * flag - usa i bottoni di selezione.
     *
     * @param usaSelezione true per usare i tre bottoni di selezione dei records <br>
     */
    public void setUsaSelezione(boolean usaSelezione) {
    }


    public void setUsaFrecce(boolean usaFrecce) {
    }


    /**
     * Determina se la toolbar contiene i comandi per
     * l'ordinamento manuale sul campo Ordine
     * <p/>
     *
     * @return true se e' ordinabile manualmente
     */
    public boolean isUsaFrecce() {
        return false;
    }


    /**
     * Abilita l'uso delle del bottone Duplica.
     * <p/>
     *
     * @param flag per usare il bottone Duplica
     */
    public void setUsaDuplica(boolean flag) {
    }


    public void setUsaStampa(boolean usaStampa) {
    }


    /**
     * Abilita l'uso del pulsante Ricerca.
     * <p/>
     *
     * @param flag per abilitare il pulsante
     */
    public void setUsaRicerca(boolean flag) {
    }


    /**
     * Abilita l'uso del pulsante Proietta.
     * <p/>
     *
     * @param flag per abilitare il pulsante
     */
    public void setUsaProietta(boolean flag) {
    }


    /**
     * Controlla l'uso del pulsante Preferito nella lista
     * <p/>
     *
     * @return true se usa il pulsante Preferito
     */
    public boolean isUsaPreferito() {
        return false;
    }


    /**
     * Abilita l'uso del pulsante Preferito nella lista.
     * <p/>
     *
     * @param flag per usare il pulsante
     */
    public void setUsaPreferito(boolean flag) {
    }


    /**
     * Restituisce l'oggetto concreto della classe principale.
     *
     * @return oggetto grafico JToolBar restituito dall'interfaccia
     */
    public JToolBar getToolBar() {
        return this;
    }


    public JButton getBotEscape() {
        return botEscape;
    }


    protected void setBotEscape(JButton botEscape) {
        this.botEscape = botEscape;
    }


    public JButton getBotEnter() {
        return botEnter;
    }


    protected void setBotEnter(JButton botEnter) {
        this.botEnter = botEnter;
    }
}// fine della classe