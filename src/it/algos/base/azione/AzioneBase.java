/**
 * Title:     ${NAME}
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      20-dic-2004
 */
package it.algos.base.azione;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.gestore.Gestore;
import it.algos.base.menu.menu.MenuBase;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.portale.Portale;
import it.algos.base.pref.Pref;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.URL;

/**
 * Superclasse astratta per le azioni.
 * </p>
 * Questa classe: <ul>
 * <li> &Egrave; la superclasse da cui derivano tutte le singole azioni </li>
 * <li> Le azioni sono un'implementazioni della interfaccia Java
 * <code>AbstractAction</code>; alcune implementano solo tooltips ed help, mentre altre
 * aggiungono una rappresentazione GUI con testo, icona, caratteri
 * acceleratori, ecc </li>
 * <li> Il metodo <code>actionPerformed</code> della interfaccia <code>
 * AbstractAction </code> viene implementato nella superclasse astratta </li>
 * <li> Il costruttore non accetta parametri. Il riferimento all'oggetto di classe
 * <code>Gestore</code>, indispensabile per indirizzare l'evento al metodo che deve
 * eseguire la funzionalit&agrave; prevista, viene passato col metodo .avvia </li>
 * <li> Questa classe deriva da <code>AbstractAction</code>, quindi usa la tecnica della
 * collezione chiave-valore (<code>HashMap</code>) per memorizzare una serie di parametri;
 * la chiave &egrave; sempre una stringa, mentre il valore puo essere un oggetto di
 * qualsiasi tipo </li>
 * <li> Le azioni vengono create all'avvio del programma nel ciclo
 * <i>Costruttore.inizia</i> </li>
 * <li> Per comodita di scrittura e di visibilita del codice, esistono delle variabili
 * (protette) di appoggio per ogni parametro; nel metodo <code>inizia</code> di
 * ogni sottoclasse concreta, viene regolata ogni singola variabile coi valori
 * delle corrispondenti costanti (private) della  sottoclasse stessa </li>
 * <li> Per costruire un oggetto azione usando un nome diverso da quello previsto
 * nella costante statica della sottoclasse, occorre costruirlo normalmente e poi
 * cambiare il testo con una chiamata al metodo <code>setChiave</code> della
 * superclasse </li>
 * <li> Ogni volta che viene premuto il menu/bottone/toolbar corrispondente ad una
 * azione, viene generato un evento che arriva nel metodo <code>actionPerformed
 * </code> della classe azione interessata; da qui viene invocato un metodo, diverso
 * per ogni azione, ma comunque indirizzato all' oggetto di classe <code>xxxGestore
 * </code>, che deve essere una sottoclasse di <code>GestoreBase</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 20-dic-2004 ore 16.27.20
 */
public abstract class AzioneBase extends AbstractAction implements Azione {

    /**
     * Path di default delle icone nel jar
     */
    private static final String RADICE = "it/algos/base/icone";

    /**
     * Suffisso del tipo di disegno
     */
    private static final String SUFFISSO = "gif";

    /**
     * Path delle icone
     */
    private String radice = "";

    /**
     * Portale proprietario dell' azione
     */
    private Portale portale = null;

    /**
     * gestore principale della logica e dei riferimenti (puntatori)
     */
    private Gestore gestore = null;

    /**
     * testo della chiave per recuperare l'azione dalla collezione
     */
    private String chiave = "";

    /**
     * testo che appare nei menu e nei bottoni
     */
    protected String nome = "";

    /**
     * nome dell' icona
     */
    private String iconaPiccola = "";

    private String iconaMedia = "";

    private String iconaGrande = "";

    /**
     * testo descrittivo breve del comando per eventuale tooltip
     */
    private String tooltip = "";

    /**
     * testo descrittivo lungo del comando per eventuale aiuto in linea
     */
    private String help = "";

    /**
     * carattere del tasto acceleratore
     */
    private char carattereAcceleratore = ' ';

    /**
     * codice intero per il tasto mnemonico
     */
    private int carattereMnemonico = 0;

    /**
     * Lettera per il tasto comando
     */
    private String carattereComando = "";

    /**
     * Flag per mostrare nei menu l'azione (disattivata finche' non funziona)
     */
    private boolean isAttiva = false;

    /**
     * Abilitazione dell'azione alla partenza
     */
    private boolean isAbilitataPartenza = true;

    /**
     * Menu per il posizionamento dell'azione
     */
    private MenuBase.MenuTipo menuTipo = null;

    /**
     * Campo base
     */
    private Campo campo = null;


    /**
     * tipologia di utente abilitato ad eseguire questa azione
     */
    private Pref.Utente utente = null;

    /**
     * utilizzo nel portale lista
     */
    private boolean usoLista = false;

    /**
     * utilizzo nel portale scheda
     */
    private boolean usoScheda = false;

    /**
     * utilizzo nel portale navigatore
     */
    private boolean usoNavigatore = false;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public AzioneBase() {
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
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola il path di default della azioni base */
        this.setRadice(RADICE);

        /* utilizzo standar delle azioni */
        /* può venir modificato nella sottoclasse */
        this.setUtente(Pref.Utente.host);

//        /* di default è abilitata alla partenza */
//        this.setAbilitataPartenza(true);

    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @return vero se viene inizializzato adesso;
     *         falso se era gi&agrave; stato inizializzato
     */
    public boolean inizializza() {
        /* regolazioni delle proprieta dell'azione */
        this.regola();

        /* valore di ritorno */
        return true;
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa) ogni volta che questo oggetto
     * deve <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi
     *
     * @param unGestore gestore degli eventi
     */
    public void avvia(Gestore unGestore) {
        /* regola le variabili di istanza coi parametri */

        try { // prova ad eseguire il codice
            if (unGestore == null) {
                throw new Exception("Gestore mancante.");
            }// fine del blocco if

            this.setGestore(unGestore);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni delle proprieta dell'azione.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void regola() {
        /* variabili e costanti locali di lavoro */
        String versione = "";
        int modificatore = 0;
        ImageIcon icona;

        try { // prova ad eseguire il codice
            versione = System.getProperty("os.name").toLowerCase();
            if (versione.indexOf("windows") != -1) {
                modificatore = ActionEvent.CTRL_MASK;
            } else if (versione.indexOf("mac") != -1) {
                modificatore = ActionEvent.META_MASK;
            }

            //@todo su mac non funziona - scrive meta, ma funziona col ctrl/10-12-04/gac
//            modificatore = ActionEvent.CTRL_MASK;

            /** regola la proprieta dell'azione (ACCELERATOR_KEY),
             *  con la variabile d'istanza (unCarattereAcceleratore)<br>
             *  la proprieta dell'azione verra usata per mostrare ed usare
             *  il carattere acceleratore
             * Usato nei meu <br>
             */
            /* controllo di congruita' */
            if (carattereAcceleratore != ' ') {

                this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(carattereAcceleratore,
                        modificatore));
            } /* fine del blocco if */

            /**
             *  regola la proprieta dell'azione (MNEMONIC_KEY),
             *  con la variabile d'istanza (unCarattereMnemonico)<br>
             *  la proprieta dell'azione verra usata per sottolineare
             *  un carattere (stile windows)
             */
            if (carattereMnemonico != 0) {
                this.putValue(MNEMONIC_KEY, new Integer(carattereMnemonico));
            }// fine del blocco if

            /**
             *  regola la proprieta dell'azione (ACTION_COMMAND_KEY),
             *  con la variabile d'istanza (unCarattereComando)<br>
             *  la proprieta dell'azione verra usata per mostrare ed usare
             *  il carattere di comando
             */
            this.putValue(ACTION_COMMAND_KEY, carattereComando);

            /**
             *
             *  regola la proprieta dell'azione (SHORT_DESCRIPTION),
             *  con la variabile d'istanza (unTestoTooltip)<br>
             *  la proprieta dell'azione verra usata per il ToolTipText
             */
            this.putValue(SHORT_DESCRIPTION, tooltip);

            /**
             *  regola la proprieta dell'azione (LONG_DESCRIPTION),
             *  con la variabile d'istanza (unTestoHelp)<br>
             *  la proprieta dell'azione verra usata per l'help in linea
             */
            this.putValue(LONG_DESCRIPTION, help);

            /**
             *  regola la proprieta dell'azione (NAME),
             *  con la variabile d'istanza (unTestoNome)<br>
             *  la proprieta dell'azione verra usata per mostrare
             *  il testo nei menu e nei bottoni
             */
            this.putValue(NAME, nome);

            /**
             *  regola la proprieta dell'azione (SMALL_ICON, MEDIUM_ICON,
             *  LARGE_ICON), col metodo creaIcona che restituisce un'icona<br>
             *  la proprieta dell'azione verra usata per mostrare
             *  il disegno nei menu e nei bottoni
             */
            if (this.getValue(ICONA_PICCOLA) == null) {
                icona = creaIcona(iconaPiccola);
                this.putValue(ICONA_PICCOLA, icona);
            }// fine del blocco if
            if (this.getValue(ICONA_MEDIA) == null) {
                icona = creaIcona(iconaMedia);
                this.putValue(ICONA_MEDIA, icona);
            }// fine del blocco if
            if (this.getValue(ICONA_GRANDE) == null) {
                icona = creaIcona(iconaGrande);
                this.putValue(ICONA_GRANDE, icona);
            }// fine del blocco if

            /* regola alcuni parametri del componente */
            this.regolaParametri();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea inizialmente l'icona.
     * <p/>
     *
     * @param nomeIcona il nome dell'icona da cercare
     *
     * @return l'oggetto Icona (null se non trovato)
     */
    protected ImageIcon creaIcona(String nomeIcona) {
        /* variabili e costanti locali di lavoro */
        String indirizzo = "";
        ImageIcon unaIcona = null;
        URL url;
        String radice;

        try { // prova ad eseguire il codice
            /* costruisce il nome del file del disegno */
            radice = this.getRadice();
            indirizzo += radice;
            indirizzo += "/";
            indirizzo += nomeIcona;
            indirizzo += ".";
            indirizzo += SUFFISSO;

            /* tenta di costruire l'icona */
            url = ClassLoader.getSystemResource(indirizzo);
            if (url != null) {
                unaIcona = new ImageIcon(url);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unaIcona;
    }


    /**
     * Regola alcuni parametri.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     */
    private void regolaParametri() {
//        String unTestoTip = (String)this.getValue(this.SHORT_DESCRIPTION);
        this.setEnabled(this.isAbilitataPartenza);
    }


    public AzioneBase getAzione() {
        return this;
    }


    public Portale getPortale() {
        return portale;
    }


    public void setPortale(Portale portale) {
        this.portale = portale;
    }


    public Gestore getGestore() {
        return gestore;
    }


    public void setGestore(Gestore gestore) {
        this.gestore = gestore;
    }


    public String getChiave() {
        return chiave;
    }


    protected void setChiave(String chiave) {
        this.chiave = chiave;
    }


    public String getNome() {
        return nome;
    }


    protected void setNome(String nome) {
        this.nome = nome;
    }


    public String getTooltip() {
        return tooltip;
    }


    /**
     * Regola il testo del Tooltip.
     * <p/>
     *
     * @param tooltip il testo del Tooltip
     */
    public void setTooltip(String tooltip) {
        /* variabili e costanti locali di lavoro */
        Azione azione;

        try { // prova ad eseguire il codice
            this.tooltip = tooltip;

            azione = this.getAzione();
            if (azione != null) {
                azione.getAzione().putValue(Action.SHORT_DESCRIPTION, tooltip);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    public String getHelp() {
        return help;
    }


    protected void setHelp(String help) {
        this.help = help;
    }


    protected String getNomeIconaPiccola() {
        return iconaPiccola;
    }


    public void setIconaPiccola(String iconaPiccola) {
        this.iconaPiccola = iconaPiccola;
    }


    protected String getNomeIconaMedia() {
        return iconaMedia;
    }


    public void setIconaMedia(String iconaMedia) {
        this.iconaMedia = iconaMedia;
    }


    protected String getNomeIconaGrande() {
        return iconaGrande;
    }


    public void setIconaGrande(String iconaGrande) {
        this.iconaGrande = iconaGrande;
    }


    /**
     * Assegna una icona piccola a questa azione
     * <p/>
     *
     * @param icona da assegnare
     */
    public void setIconaPiccola(Icon icona) {
        this.putValue(ICONA_PICCOLA, icona);
    }


    /**
     * Assegna una icona media a questa azione
     * <p/>
     *
     * @param icona da assegnare
     */
    public void setIconaMedia(Icon icona) {
        this.putValue(ICONA_MEDIA, icona);
    }


    /**
     * Assegna una icona grande a questa azione
     * <p/>
     *
     * @param icona da assegnare
     */
    public void setIconaGrande(Icon icona) {
        this.putValue(ICONA_GRANDE, icona);
    }


    /**
     * Restituisce l'icona associata ad una specifica chiave.
     *
     * @param unaChiave nome dell'icona specifica
     *
     * @return l'icona associata alla chiave specifica; se la chiave non
     *         esiste, ritorna <code>nullo</code>
     */
    public ImageIcon getIcona(String unaChiave) {
        return (ImageIcon)super.getValue(unaChiave);
    }


    /**
     * Ritorna l'icona piccola.
     * <p/>
     *
     * @return l'icona piccola
     */
    public ImageIcon getIconaPiccola() {
        return this.getIcona(Azione.ICONA_PICCOLA);
    }


    /**
     * Ritorna l'icona media.
     * <p/>
     *
     * @return l'icona media
     */
    public ImageIcon getIconaMedia() {
        return this.getIcona(Azione.ICONA_MEDIA);
    }


    /**
     * Ritorna l'icona grande.
     * <p/>
     *
     * @return l'icona grande
     */
    public ImageIcon getIconaGrande() {
        return this.getIcona(Azione.ICONA_GRANDE);
    }


    public char getCarattereAcceleratore() {
        return carattereAcceleratore;
    }


    protected void setCarattereAcceleratore(char carattereAcceleratore) {
        this.carattereAcceleratore = carattereAcceleratore;
    }


    public int getCarattereMnemonico() {
        return carattereMnemonico;
    }


    protected void setCarattereMnemonico(int carattereMnemonico) {
        this.carattereMnemonico = carattereMnemonico;
    }


    public String getCarattereComando() {
        return carattereComando;
    }


    protected void setCarattereComando(String carattereComando) {
        this.carattereComando = carattereComando;
    }


    public boolean isAttiva() {
        return isAttiva;
    }


    protected void setAttiva(boolean attiva) {
        isAttiva = attiva;
    }


    public boolean isAbilitataPartenza() {
        return isAbilitataPartenza;
    }


    protected void setAbilitataPartenza(boolean abilitataPartenza) {
        isAbilitataPartenza = abilitataPartenza;
    }


    private String getRadice() {
        return radice;
    }


    protected void setRadice(String radice) {
        this.radice = radice;
    }


    public MenuBase.MenuTipo getColonnaMenu() {
        return menuTipo;
    }


    public void setColonnaMenu(MenuBase.MenuTipo menuTipo) {
        this.menuTipo = menuTipo;
    }


    public Campo getCampo() {
        return campo;
    }


    public void setCampo(Campo campo) {
        this.campo = campo;
    }


    public Pref.Utente getUtente() {
        return utente;
    }


    protected void setUtente(Pref.Utente utente) {
        this.utente = utente;
    }


    public boolean isUsoLista() {
        return usoLista;
    }


    public void setUsoLista(boolean usoLista) {
        this.usoLista = usoLista;
    }


    public boolean isUsoScheda() {
        return usoScheda;
    }


    protected void setUsoScheda(boolean usoScheda) {
        this.usoScheda = usoScheda;
    }


    public boolean isUsoNavigatore() {
        return usoNavigatore;
    }


    protected void setUsoNavigatore(boolean usoNavigatore) {
        this.usoNavigatore = usoNavigatore;
    }


    /**
     * actionPerformed, da ActionListener.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void actionPerformed(ActionEvent unEvento) {
    }


    /**
     * Per fare una copia completa di questo oggetto occorre:
     * Prima copiare l'oggetto nel suo insieme, richiamando il metodo
     * sovrascritto che copia e regola tutte le variabili dell'oggetto con
     * gli stessi valori delle variabili originarie
     * Secondo copiare tutte le variabili che sono puntatori ad altri
     * oggetti, per evitare che nella copia ci sia il puntatore all'oggetto
     * originale (in genere tutti gli oggetti che vengono creati nella
     * classe col comando new)
     * Terzo in ogni sottoclasse occorre fare le copie dei puntatori
     * esistenti nelle sottoclassi stesse
     */
    public Object clone() {
        /* variabili e costanti locali di lavoro */
        Object oggetto = null;
        AzioneBase unAzione = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse Object */
            oggetto = super.clone();
            if (oggetto instanceof AzioneBase) {
                unAzione = (AzioneBase)oggetto;
            } else {
                new MessaggioAvviso("La classe non coincide");
            }// fine del blocco if-else
        } catch (CloneNotSupportedException unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unAzione;
    }


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy).
     *
     * @return azione clonata
     */
    public Azione clonaAzione() {
        /* variabili e costanti locali di lavoro */
        Azione unAzione = null;

        try { // prova ad eseguire il codice
            /* esegue la clonazione standard (col casting)*/
            unAzione = (Azione)this.clone();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unAzione;
    }

}// fine della classe
