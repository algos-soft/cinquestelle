/**
 * Title:     LibRisorse
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      17-gen-2004
 */
package it.algos.base.libreria;

import it.algos.base.azione.Azione;
import it.algos.base.azione.AzioneBase;
import it.algos.base.bottone.BottoneAzione;
import it.algos.base.costante.CostanteCarattere;
import it.algos.base.errore.Errore;
import it.algos.base.finestra.Finestra;
import it.algos.base.finestra.FinestraBase;
import it.algos.base.menu.barra.MenuBarra;
import it.algos.base.menu.menu.Menu;
import it.algos.base.menu.menu.MenuArchivioNavigatore;
import it.algos.base.menu.menu.MenuBase;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.portale.Portale;
import it.algos.base.progetto.Progetto;
import it.algos.base.toolbar.ToolBar;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Repository di funzionalita' per la gestione delle Risorse. </p> Questa classe astratta: <ul> <li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 17-gen-2004 ore 9.41.24
 */
public abstract class LibRisorse {

    /**
     * costante per identificare una entry di tipo FILE.
     */
    public static final int FILE = 0;

    /**
     * costante per identificare una entry di tipo DIRECTORY.
     */
    public static final int DIRECTORY = 1;

    /**
     * costante per l'icona piccola.
     */
    private static final int SMALL = 16;

    /**
     * costante per l'icona media.
     */
    private static final int MEDIA = 24;


    /**
     * Costruttore semplice senza parametri. <br> Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public LibRisorse() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore semplice


    /**
     * Recupera l'URL relativo a una risorsa. <br>
     *
     * @param nomeRisorsa il nome della risorsa da cercare
     *
     * @return un URL che punta alla risorsa richiesta, null se la risorsa non esiste
     */
    private static URL getURLDaNomeRisorsa(String nomeRisorsa) {

        /* variabili e costanti locali di lavoro */
        URL unURL = null;

        try {    // prova ad eseguire il codice
            /* recupera l'URL relativo alla risorsa */
            unURL = ClassLoader.getSystemResource(nomeRisorsa);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unURL;
    } // fine del metodo


    /**
     * Apre un InputStream relativo a una risorsa. <br>
     *
     * @param nomeRisorsa il nome della risorsa da cercare
     *
     * @return un InputStream aperto che punta alla risorsa richiesta, null se la risorsa non
     *         esiste
     */
    public static InputStream apreStreamDaNomeRisorsa(String nomeRisorsa) {

        /* variabili e costanti locali di lavoro */
        InputStream unoStream = null;
        URL unURL;

        try {    // prova ad eseguire il codice
            /* recupera l'URL relativo alla risorsa */
            unURL = getURLDaNomeRisorsa(nomeRisorsa);
            /* recupera uno stream dall'URL */
            unoStream = unURL.openStream();
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unoStream;
    } // fine del metodo


    /**
     * Ritorna l'elenco dei contenuti (file o directory) di una directory<br> contenuta in un
     * filesystem jar disponibile come libreria.<br>
     *
     * @param percorsoDir il percorso della directory da listare (deve trovarsi in un filesystem jar
     * disponibile)
     * @param tipoEntry codice del tipo di oggetti da listare: LibRisorse.FILE per listare solo i
     * file, LibRisorse.DIRECTORY per listare solo le directory
     * @param includiNascosti true per listare gli elementi nascosti, false per escluderli
     *
     * @return un elenco di stringhe corrispondenti ai nomi degli oggetti contenuti nella directory
     *         richiesta (solo nome dell'oggetto, senza percorso) null se la risorsa non viene
     *         trovata
     */
    public static ArrayList listatoJar(String percorsoDir, int tipoEntry, boolean includiNascosti) {

        /* variabili e costanti locali di lavoro */
        ArrayList unElenco = null;
        URL baseURL = null;
        URLConnection connessione = null;
        JarURLConnection jURL;
        JarFile jFile = null;
        Enumeration enumeratore = null;
        Object unOggetto = null;
        JarEntry unEntry = null;
        String unNome = null;
        String radiceNome = null;
        String fogliaNome = null;
        String radiceRisorsa = null;
        String separatore = String.valueOf(CostanteCarattere.SEP_DIR);
        boolean continua = true;
        String messaggio = null;
        char c = ' ';
        boolean nascosto = false;
        boolean aggiungi = false;

        try {    // prova ad eseguire il codice

            /* recupera l'URL corrispondente alla risorsa richiesta
            *  e costruisce una connessione */
            if (continua) {
                baseURL = LibRisorse.getURLDaNomeRisorsa(percorsoDir);
                if (baseURL != null) {
                    connessione = baseURL.openConnection();
                } else {
                    messaggio = "Impossibile trovare la directory ";
                    messaggio += percorsoDir;
                    messaggio += " nelle librerie del sistema.";
                    new MessaggioAvviso(messaggio);
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* controlla se la connessione e' di tipo appropriato
            *  e recupera un enumeratore del contenuto */
            if (continua) {
                if (connessione instanceof JarURLConnection) {
                    // effettua il casting a JarURLConnection
                    jURL = (JarURLConnection)connessione;
                    // recupera il riferimento al file jar
                    jFile = jURL.getJarFile();
                    // recupera un enumeratore del contenuto
                    enumeratore = jFile.entries();
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* spazzola gli elementi e carica l'array */
            if (continua) {

                /* costruisce il percorso radice della risorsa richiesta */
                radiceRisorsa = percorsoDir + separatore;

                /* inizializza la lista di elementi da ritornare */
                unElenco = new ArrayList();
                
                while (enumeratore.hasMoreElements()) {
                    unOggetto = enumeratore.nextElement();
                    unEntry = (JarEntry)unOggetto;
                    unNome = unEntry.getName();

                    /* se il nome termina con un separatore, lo rimuove */
                    if (unNome.endsWith(separatore)) {
                        unNome = unNome.substring(0, unNome.length() - 1);
                    }// fine del blocco if

                    /* recupera il nome della radice e della foglia */
                    radiceNome = Libreria.radiceFile(unNome);
                    fogliaNome = Libreria.fogliaFile(unNome);

                    /* recupera il flag nascosto (se fogliaNome comincia con un punto) */
                    nascosto = false;
                    c = fogliaNome.charAt(0);
                    if (c == '.') {
                        nascosto = true;
                    }// fine del blocco if

                    /* esclude tutto cio' che non e' nella directory richiesta */
                    if (radiceNome.equals(radiceRisorsa)) {

                        /* controlla se si tratta di una directory o di un file */
                        aggiungi = false;
                        if (unEntry.isDirectory()) {  // e' una directory
                            if (tipoEntry == DIRECTORY) {
                                /* esclude le directory che si chiamano META-INF */
                                if (fogliaNome.equals("META-INF") == false) {
                                    aggiungi = true;
                                }// fine del blocco if

                            }// fine del blocco if
                        } else {    // e' un file
                            if (tipoEntry == FILE) {
                                /* esclude i file che si chiamano MANIFEST.MF */
                                if (fogliaNome.equals("MANIFEST.MF") == false) {
                                    aggiungi = true;
                                }// fine del blocco if

                            }// fine del blocco if
                        }// fine del blocco if-else

                        /* controlla se deve escludere i file nascosti */
                        if (aggiungi && nascosto) {
                            if (includiNascosti == false) {
                                aggiungi = false;
                            }// fine del blocco if
                        }// fine del blocco if-else

                        /* aggiunge il nome all'elenco */
                        if (aggiungi) {
                            unElenco.add(fogliaNome);
                        }// fine del blocco if

                    }// fine del blocco if

                }// fine del blocco while
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unElenco;
    } // fine del metodo


    /**
     * Ritorna l'elenco delle directory contenute in una directory di un filesystem jar disponibile
     * come libreria.<br>
     *
     * @param percorsoDir il percorso della directory da listare (deve trovarsi in un filesystem jar
     * disponibile)
     * @param includiNascosti true per listare gli elementi nascosti, false per escluderli
     *
     * @return un elenco di stringhe corrispondenti ai nomi delle directory contenute nella
     *         directory richiesta (solo nome della directory, senza percorso, null se la directory
     *         richiesta non e' stata trovata)
     */
    public static ArrayList directoryJar(String percorsoDir, boolean includiNascosti) {
        /* valore di ritorno */
        return listatoJar(percorsoDir, DIRECTORY, includiNascosti);
    } // fine del metodo


    /**
     * Ritorna l'elenco dei file contenuti in una directory<br> di un filesystem jar disponibile
     * come libreria.<br>
     *
     * @param percorsoDir il percorso della directory da listare (deve trovarsi in un filesystem jar
     * disponibile)
     * @param includiNascosti true per listare gli elementi nascosti, false per escluderli
     *
     * @return un elenco di stringhe corrispondenti ai nomi dei file contenuti nella directory
     *         richiesta (solo nome del file, senza percorso, null se la directory richiesta non e'
     *         stata trovata)
     */
    public static ArrayList filesJar(String percorsoDir, boolean includiNascosti) {
        /* valore di ritorno */
        return listatoJar(percorsoDir, FILE, includiNascosti);
    } // fine del metodo


    /**
     * Ritorna l'elenco delle directory visibili contenute in una directory<br> di un filesystem jar
     * disponibile come libreria.<br>
     *
     * @param percorsoDir il percorso della directory da listare (deve trovarsi in un filesystem jar
     * disponibile)
     *
     * @return un elenco di stringhe corrispondenti ai nomi delle directory contenute nella
     *         directory richiesta (solo nome della directory, senza percorso, null se la directory
     *         richiesta non e' stata trovata)
     */
    public static ArrayList directoryJar(String percorsoDir) {
        /* valore di ritorno */
        return directoryJar(percorsoDir, false);
    } // fine del metodo


    /**
     * Ritorna l'elenco dei file visibili contenuti in una directory<br> di un filesystem jar
     * disponibile come libreria.<br>
     *
     * @param percorsoDir il percorso della directory da listare (deve trovarsi in un filesystem jar
     * disponibile)
     *
     * @return un elenco di stringhe corrispondenti ai nomi dei file contenuti nella directory
     *         richiesta (solo nome del file, senza percorso, null se la directory richiesta non e'
     *         stata trovata)
     */
    public static ArrayList filesJar(String percorsoDir) {
        /* valore di ritorno */
        return filesJar(percorsoDir, false);
    } // fine del metodo


    /**
     * Controlla se esiste un dato percorso nel filesystem jar.
     * <p/>
     *
     * @param percorso il percorso da controllare
     *
     * @return true se il percorso esiste
     */
    public static boolean isEsistePercorso(String percorso) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        URL url;

        try {    // prova ad eseguire il codice

            url = LibRisorse.getURLDaNomeRisorsa(percorso);
            if (url != null) {
                esiste = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Recupera una icona dalle icone di base.
     * <p/>
     * Se arriva un path, usa quello <br> Se arriva un nome, aggiunge il path di base <br>
     *
     * @param nomeIcona nome o path dell'icona da recuperare
     *
     * @return l'icona
     */
    static ImageIcon getIconaBase(String nomeIcona) {
        /* variabili e costanti locali di lavoro */
        ImageIcon icona = null;
        String pathBase = "it/algos/";
        String indirizzo;
        URL url;

        try {    // prova ad eseguire il codice
            /* conversione */
            nomeIcona = Lib.Testo.convertePuntiInBarre(nomeIcona);

            /* costruisce il nome del file del disegno */
            if (nomeIcona.startsWith(pathBase)) {
                indirizzo = nomeIcona;
            } else {
                indirizzo = pathBase + Progetto.DIR_ICONE_BASE;
                indirizzo += "/";
                indirizzo += nomeIcona;
            }// fine del blocco if-else
            indirizzo += Progetto.EXT_ICONE;

            /* tenta di costruire l'icona */
            url = ClassLoader.getSystemResource(indirizzo);
            icona = getIcona(url);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return icona;
    }


    /**
     * Recupera una icona dato il suo URL.
     * <p/>
     *
     * @param url url dell'icona da recuperare
     *
     * @return l'icona, null se non trovata
     */
    static ImageIcon getIcona(URL url) {
        /* variabili e costanti locali di lavoro */
        ImageIcon icona = null;

        try {    // prova ad eseguire il codice
            if (url != null) {
                icona = new ImageIcon(url);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return icona;
    }


    /**
     * Recupera una icona di un modulo specifico.
     * <p/>
     * si suppone che esista una cartella icone e che sia allo stesso livello del modulo chiamante
     * <br>
     *
     * @param modulo di riferimento
     * @param nomeIcona nome dell'icona da recuperare (senza estensione)
     *
     * @return l'icona
     */
    static ImageIcon getIcona(Modulo modulo, String nomeIcona) {
        /* variabili e costanti locali di lavoro */
        ImageIcon icona = null;
        Class classe;

        try { // prova ad eseguire il codice
            classe = modulo.getClass();
            icona = getIcona(classe, nomeIcona);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return icona;
    }


    /**
     * Recupera una icona relativamente alla posizione di una data classe.
     * <p/>
     * si suppone che esista una cartella icone e che sia allo stesso livello della classe data
     * <p/>
     *
     * @param classe di riferimento
     * @param nomeIcona nome dell'icona da recuperare (senza estensione)
     *
     * @return l'icona
     */
    static ImageIcon getIcona(Class classe, String nomeIcona) {
        /* variabili e costanti locali di lavoro */
        ImageIcon icona = null;
        URL url;
        String indirizzo;

        try { // prova ad eseguire il codice

            indirizzo = Progetto.DIR_ICONE_MODULO;
            indirizzo += "/";
            indirizzo += nomeIcona;
            indirizzo += Progetto.EXT_ICONE;
            url = classe.getResource(indirizzo);
            icona = getIcona(url);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return icona;
    }


    /**
     * Crea un bottone con l'icona.
     * <p/>
     *
     * @param nomeIcona da caricare
     * @param dim dell'icona
     *
     * @return bottone creato
     */
    private static JButton getBottone(String nomeIcona, int dim) {
        /* variabili e costanti locali di lavoro */
        JButton bottone = null;
        Icon icona;

        try { // prova ad eseguire il codice
            /* recupera le icone */
            icona = Lib.Risorse.getIconaBase(nomeIcona);

            bottone = new JButton(icona);
            bottone.setOpaque(false);

            bottone.setPreferredSize(new Dimension(dim, dim));
            bottone.setSize(new Dimension(dim, dim));
            bottone.setMaximumSize(new Dimension(dim, dim));
            bottone.setMinimumSize(new Dimension(dim, dim));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Crea un mini-bottone con l'icona.
     * <p/>
     *
     * @param nomeIcona da caricare
     *
     * @return bottone creato
     */
    static JButton getBottoneSmall(String nomeIcona) {
        /* invoca il metodo sovrascritto della classe */
        return LibRisorse.getBottone(nomeIcona, SMALL);
    }


    /**
     * Crea un bottone con l'icona.
     * <p/>
     *
     * @param nomeIcona da caricare
     *
     * @return bottone creato
     */
    static JButton getBottone(String nomeIcona) {
        /* invoca il metodo sovrascritto della classe */
        return LibRisorse.getBottone(nomeIcona, MEDIA);
    }


    /**
     * Crea un'azione specifica e la aggiunge alla toolbar o al menu Archivio.
     * <p/>
     * L'azione viene aggiunta al navigatore corrente <br> L'azione viene aggiunta alla toolbar
     * della lista o al menu aArchivio <br> Metodo invocato dal ciclo inizializza <br>
     * <p/>
     * Crea un'istanza di BottoneAzione <br> Recupera l'icona <br> Recupera la toolbar del portale
     * Lista del navigatore corrente oppure il menu Archivio <br> Aggiunge bottone e icona alla
     * toolbar oppure al menu Archivio <br>
     *
     * @param mod modulo
     * @param path completo dell'immagine da usare
     * @param metodo nome del metodo nella classe modulo specifica
     * @param help string di help (tooltiptext)
     * @param toolBar flag per aggiungere alla toolbar
     */
    static void addAzione(Modulo mod, String path, String metodo, String help, boolean toolBar) {
        /* variabili e costanti locali di lavoro */
        BottoneAzione bottone;
        Navigatore nav;
        ToolBar toolbar;
        String pathModulo;
        String pathIcona;
        String cartella = "icone";
        String car = ".";
        int pos;
        ImageIcon icona;
        Portale portale;

        try { // prova ad eseguire il codice

            /* crea il bottone */
            bottone = new BottoneAzione(mod, metodo);

            /* aggiunge il testo di help */
            bottone.setToolTipText(help);

            /* recupera l'icona */
            icona = Lib.Risorse.getIconaBase(path);
            bottone.setIcon(icona);

            nav = mod.getNavigatoreCorrente();
            portale = nav.getPortaleLista();

            /* aggiunge l'azione alla collezione del portale
             * usa il nome del metodo come chiave */
            portale.addAzione(metodo, bottone.getAzione());

            /* aggiunge il bottone alla toolbar */
            toolbar = portale.getToolBar();
            toolbar.getToolBar().add(bottone);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un'azione al portale.
     * <p/>
     * L'azione viene aggiunta in coda alla toolbar del portale <br> Metodo invocato dal ciclo
     * inizializza <br>
     *
     * @param azione completa di icona, titolo ed help
     * @param portale che contiene la ToolBar
     */
    private static void addAzionePortale(Azione azione, Portale portale) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ToolBar toolbar;

        try { // prova ad eseguire il codice
            continua = (azione != null && portale != null);

            if (continua) {
                /* aggiunge l'azione al portale */
                portale.addAzione(azione.getChiave(), azione);

                /* aggiunge il bottone-azione alla toolbar */
                toolbar = portale.getToolBar();
                if (toolbar != null) {
                    toolbar.addBottone(azione);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un'azione al menu.
     * <p/>
     * L'azione viene  aggiunta nel menu specificato dall'azione <br> Di default viene aggiunta alla
     * quart'ultima riga del menu Archivio (sic!) <br> Metodo invocato dal ciclo inizializza <br>
     *
     * @param azione completa di icona, titolo ed help
     * @param portale che contiene il menu
     */
    private static void addAzioneMenu(Azione azione, Portale portale) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Finestra fin = null;
        FinestraBase finestra = null;
        MenuBarra menuBarra = null;
        Menu menu = null;
        MenuBase menuBase = null;
        AzioneBase azBase = null;
        int pos;

        try { // prova ad eseguire il codice
            continua = (azione != null && portale != null);

            if (continua) {
                fin = portale.getFinestra();
                continua = (fin != null);
            }// fine del blocco if

            if (continua) {
                finestra = fin.getFinestraBase();
                continua = (finestra != null);
            }// fine del blocco if

            if (continua) {
                menuBarra = finestra.getMenuBarra();
                continua = (menuBarra != null);
            }// fine del blocco if

            if (continua) {
                menu = menuBarra.getMenuArchivio();
                continua = (menu != null);
            }// fine del blocco if

            if (continua) {
                menuBase = menu.getMenu();
                continua = (menuBase != null);
            }// fine del blocco if

            if (continua) {
                azBase = azione.getAzione();
                continua = (azBase != null);
            }// fine del blocco if

            if (continua) {
                if (menuBase instanceof MenuArchivioNavigatore) {
                    azBase.inizializza();
                    pos = menuBase.getItemCount() - 3;
                    menuBase.add(azBase, pos);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un'azione al navigatore specificato.
     * <p/>
     * L'azione può essere aggiunta: <ul> <li> Alla toolbar della lista (in coda) </li> <li> Alla
     * toolbar della scheda (in coda) </li> <li> Ad un menu (posizione specificata nell'azione
     * stessa) </li> <ul/> Metodo invocato dal ciclo inizializza <br>
     *
     * @param azione completa di icona, titolo ed help
     * @param nav navigatore specifico
     * @param tool switch di selezione lista/scheda/menu
     */
    static void addAzione(Azione azione, Navigatore nav, Navigatore.Tool tool) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Portale portale;

        try { // prova ad eseguire il codice
            continua = (azione != null && nav != null && tool != null);

            if (continua) {
                switch (tool) {
                    case lista:
                        portale = nav.getPortaleLista();
                        LibRisorse.addAzionePortale(azione, portale);
                        break;
                    case scheda:
                        portale = nav.getPortaleScheda();
                        LibRisorse.addAzionePortale(azione, portale);
                        break;
                    case menu:
                        portale = nav.getPortaleNavigatore();
                        LibRisorse.addAzioneMenu(azione, portale);
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
     * Aggiunge un'azione al navigatore specificato.
     * <p/>
     * L'azione può essere aggiunta: <ul>
     * <li> Alla toolbar della lista (in coda) </li>
     * <li> Alla toolbar della scheda (in coda) </li>
     * <li> Ad un menu (posizione specificata nell'azione stessa) </li>
     * <ul/>
     * Metodo invocato dal ciclo inizializza <br>
     *
     * @param azione completa di icona, titolo ed help
     * @param nav navigatore specifico
     */
    static void addAzione(Azione azione, Navigatore nav) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Portale portale;

        try { // prova ad eseguire il codice
            continua = (azione != null && nav != null);

            if (continua) {
                if (azione.isUsoLista()) {
                    portale = nav.getPortaleLista();
                    LibRisorse.addAzionePortale(azione, portale);
                }// fine del blocco if

                if (azione.isUsoScheda()) {
                    portale = nav.getPortaleScheda();
                    LibRisorse.addAzionePortale(azione, portale);
                }// fine del blocco if

                if (azione.isUsoNavigatore()) {
                    portale = nav.getPortaleNavigatore();
                    LibRisorse.addAzioneMenu(azione, portale);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un tasto specifico di un'azione al bottone.
     * <p/>
     *
     * @param bottone da regolare col tasto
     * @param key tasto da associare al bottone
     * @param azione java completa di icona, titolo ed help da associare
     */
    static void addTasto(JButton bottone, int key, Action azione) {
        LibRisorse.addTasto(bottone, key, 0, azione);
    }


    /**
     * Aggiunge un tasto specifico di un'azione al bottone.
     * <p/>
     *
     * @param bottone da regolare col tasto
     * @param key tasto da associare al bottone
     * @param modificatore da classe InputEvent
     * @param azione java completa di icona, titolo ed help da associare
     */
    static void addTasto(JButton bottone, int key, int modificatore, Action azione) {
        boolean continua;
        InputMap iMap;
        KeyStroke ks;
        String chiave = "chiave";

        try { // prova ad eseguire il codice
            continua = (bottone != null && key != 0 && azione != null);

            if (continua) {
                iMap = bottone.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                ks = KeyStroke.getKeyStroke(key, modificatore, true);
                iMap.put(ks, chiave);
                bottone.getActionMap().put(chiave, azione);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
