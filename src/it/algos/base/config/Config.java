/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      02-mag-2005
 */
package it.algos.base.config;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibFile;
import it.algos.base.libreria.Libreria;
import it.algos.base.progetto.Progetto;
import it.algos.base.property.Property;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Gestione della configurazione iniziale del programma.
 * </p>
 * Questa classe: <ul>
 * <li> Implementa il design pattern <i>singleton</i> </li>
 * <li> Mantiene la codifica di ogni entry di configurazione </li>
 * <li> La costante è la chiave per recuperare un oggetto
 * nella collezione di tipo ConfigProperty mantenuta in memoria </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 02-mag-2005 ore 12.32.07
 */
public final class Config extends Object {

    /**
     * Istanza unica di questa classe, creata ad avvio del programma
     */
    private static Config ISTANZA = null;

    /**
     * Valore di default per le nuove properties
     */
    private static final String VALORE_DEFAULT = "default";

    /**
     * chiave per identificare la property
     * tipo di archivio di preferenze (testo, db, xml...)
     */
    public static final String TIPO_ARCHIVIO_PREF_SHARED = "tipo_arc_pref_shared";

    public static final String TIPO_ARCHIVIO_PREF_LOCAL = "tipo_arc_pref_local";

    /**
     * chiave per identificare la property
     * indirizzo del file di testo delle preferenze
     */
    public static final String INDIRIZZO_FILE_PREF_SHARED = "indirizzo_file_pref_shared";

    public static final String INDIRIZZO_FILE_PREF_LOCAL = "indirizzo_file_pref_local";

    /**
     * chiave per identificare la property
     * indirizzo del database preferenze
     */
    public static final String INDIRIZZO_DB_PREF_SHARED = "indirizzo_db_pref_shared";

    public static final String INDIRIZZO_DB_PREF_LOCAL = "indirizzo_db_pref_local";


    /**
     * chiave per identificare la property
     * tipo del database preferenze
     */
    public static final String TIPO_DB_PREF_SHARED = "tipo_db_pref_shared";

    public static final String TIPO_DB_PREF_LOCAL = "tipo_db_pref_local";

    /**
     * chiave per identificare la property
     * porta del database preferenze
     */
    public static final String PORTA_DB_PREF_SHARED = "porta_db_pref_shared";

    public static final String PORTA_DB_PREF_LOCAL = "porta_db_pref_local";

    /**
     * chiave per identificare la property
     * nome database preferenze
     */
    public static String NOME_DB_PREF_SHARED = "nome_db_pref_shared";

    public static String NOME_DB_PREF_LOCAL = "nome_db_pref_local";

    /**
     * chiave per identificare la property
     * login al database preferenze
     */
    public static String LOGIN_DB_PREF_SHARED = "login_db_pref_shared";

    public static String LOGIN_DB_PREF_LOCAL = "login_db_pref_local";

    /**
     * chiave per identificare la property
     * password per accedere al database preferenze
     */
    public static String PASS_DB_PREF_SHARED = "password_db_pref_shared";

    public static String PASS_DB_PREF_LOCAL = "password_db_pref_local";


    /**
     * collezione di properties di configurazione
     * (oggetti di tipo ConfigProperty)
     */
    private LinkedHashMap properties = null;


    /**
     * Costruttore completo senza parametri.
     */
    public Config() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
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
        /* crea la collezione di properties vuota */
        this.setProperties(new LinkedHashMap());
        this.avvia();
    }


    /**
     * Avvia l'istanza.
     * <p/>
     *
     * @return true se riuscito
     */
    public boolean avvia() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua = true;

        try {    // prova ad eseguire il codice

            /* crea il file di properies vuoto se non esiste */
            if (continua) {
                continua = creaFileProperties();
            }// fine del blocco if

            /* carica la mappa con tutte le properties previste
             * usando valori di default */
            if (continua) {
                this.caricaMappa();
            }// fine del blocco if

            /* carica i valori dal file negli elementi della mappa */
            if (continua) {
                this.caricaValoriDaFile();
            }// fine del blocco if

            /* se il file non e' sincronizzato, rigenera il file
             * usa i valori della mappa memoria che sono stati
             * regolati dal file */
            if (continua) {
                if (!this.isFileSincronizzato()) {
                    continua = this.generaFileDaMappaMemoria();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Crea il file di properties vuoto se non esiste.
     * <p/>
     * Controlla se il file esiste
     * Se non esiste lo crea
     *
     * @return true se riuscito
     */
    private static boolean creaFileProperties() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        File file = null;
        boolean continua;

        try {    // prova ad eseguire il codice

            /* recupera l'indirizzo del file di configurazione */
            file = Lib.Sist.getFileConfig();
            continua = (file != null);

            /* regolazione iniziale del ritorno */
            if (continua) {
                riuscito = true;
            }// fine del blocco if

            /* crea il file di configurazione se non esiste */
            if (continua) {
                if (!LibFile.isEsisteFile(file)) {
                    if (!LibFile.creaFile(file)) {
                        riuscito = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Carica la mappa delle properties con tutte le
     * properties previste usando il valore di default.
     * <p/>
     */
    private void caricaMappa() {
        /* variabili e costanti locali di lavoro */
        String commento = null;
        Property p = null;

        try {    // prova ad eseguire il codice

            /* tipo di archivio per le preferenze locali */
            commento = "Tipo di archivio di memorizzazione delle preferenze locali.";
            commento += "\nValori: 1 = database, 2 = testo, 3 = xml";
            commento += "\nDi default usa file di testo";
            p = creaProperty(TIPO_ARCHIVIO_PREF_LOCAL, commento);
            this.addProperty(p);

            /* indirizzo del file di preferenze locali */
            commento = "Indirizzo del file delle preferenze locali.";
            commento +=
                    "\nSignificativo solo se le preferenze locali sono mantenute su file di testo";
            commento += "\nDi default usa [prog]Pref.txt nella directory Preferenze del programma";
            p = creaProperty(INDIRIZZO_FILE_PREF_LOCAL, commento);
            this.addProperty(p);

            /* indirizzo del database preferenze locali */
            commento = "Indirizzo del database delle preferenze locali.";
            commento += "\n(Percorso di filesystem, indirizzo IP o nome risolvibile dal DNS)";
            commento += "\nSignificativo solo se le preferenze locali sono mantenute su database";
            commento +=
                    "\n- Se è un percorso di filesystem, è il percorso alla directory dati del database";
            commento += "\n- Se è un indirizzo di rete, è l'indirizzo di un server di database";
            commento += "\n(di default è la directory Preferenze del programma)";
            p = creaProperty(INDIRIZZO_DB_PREF_LOCAL, commento);
            this.addProperty(p);

            /* tipo del database preferenze locali */
            commento = "Tipo di database delle preferenze locali.";
            commento += "\nSignificativo solo se le preferenze locali sono mantenute su database";
            commento += "\nValori: 2 = mysql, 3 = postgres, 6 = HSQLDB";
            commento += "\nDi default usa HSQLDB";
            p = creaProperty(TIPO_DB_PREF_LOCAL, commento);
            this.addProperty(p);

            /* porta del database preferenze locali */
            commento = "Porta del database delle preferenze locali.";
            commento += "\nSignificativo solo se le preferenze locali sono mantenute su database";
            commento += "\ndi default usa la porta di default del tipo";
            commento += "\ndi database utilizzato";
            p = creaProperty(PORTA_DB_PREF_LOCAL, commento);
            this.addProperty(p);

            /* nome del database preferenze locali*/
            commento = "Nome del database delle preferenze locali.";
            commento += "\nSignificativo solo se le preferenze locali sono mantenute su database";
            commento += "\nDi default uguale al nome del programma";
            p = creaProperty(NOME_DB_PREF_LOCAL, commento);
            this.addProperty(p);

            /* login al database preferenze locali */
            commento = "Login per accedere al database delle preferenze locali.";
            commento += "\nSignificativo solo se le preferenze locali sono mantenute su database";
            commento += "\nDi default usa il login di default del database";
            p = creaProperty(LOGIN_DB_PREF_LOCAL, commento);
            this.addProperty(p);

            /* password per il database preferenze locali */
            commento = "Password per accedere al database delle preferenze locali.";
            commento += "\nSignificativo solo se le preferenze locali sono mantenute su database";
            commento += "\nDi default usa la password di default del database";
            p = creaProperty(PASS_DB_PREF_LOCAL, commento);
            this.addProperty(p);

            /* tipo di archivio per le preferenze comuni */
            commento = "Tipo di archivio di memorizzazione delle preferenze comuni.";
            commento += "\nValori: 1 = database, 2 = testo, 3 = xml";
            commento += "\nDi default usa file di testo";
            p = creaProperty(TIPO_ARCHIVIO_PREF_SHARED, commento);
            this.addProperty(p);

            /* indirizzo del file di preferenze comuni */
            commento = "Indirizzo del file delle preferenze comuni.";
            commento +=
                    "\nSignificativo solo se le preferenze comuni sono mantenute su file di testo";
            commento +=
                    "\nDi default usa [prog]_shared.txt nella directory Preferenze del programma";
            p = creaProperty(INDIRIZZO_FILE_PREF_SHARED, commento);
            this.addProperty(p);

            /* indirizzo del database preferenze comuni */
            commento = "Indirizzo del database delle preferenze comuni.";
            commento += "\n(Percorso di filesystem, indirizzo IP o nome risolvibile dal DNS)";
            commento += "\nSignificativo solo se le preferenze comuni sono mantenute su database";
            commento +=
                    "\n- Se è un percorso di filesystem, è il percorso alla directory dati del database";
            commento += "\n- Se è un indirizzo di rete, è l'indirizzo di un server di database";
            commento += "\n(di default è la directory Preferenze del programma)";
            p = creaProperty(INDIRIZZO_DB_PREF_SHARED, commento);
            this.addProperty(p);

            /* tipo del database preferenze comuni */
            commento = "Tipo di database delle preferenze comuni.";
            commento += "\nSignificativo solo se le preferenze comuni sono mantenute su database";
            commento += "\nValori: 2 = mysql, 3 = postgres, 6 = HSQLDB";
            commento += "\nDi default usa HSQLDB";
            p = creaProperty(TIPO_DB_PREF_SHARED, commento);
            this.addProperty(p);

            /* porta del database preferenze comuni */
            commento = "Porta del database delle preferenze comuni.";
            commento += "\nSignificativo solo se le preferenze comuni sono mantenute su database";
            commento += "\ndi default usa la porta di default del tipo";
            commento += "\ndi database utilizzato";
            p = creaProperty(PORTA_DB_PREF_SHARED, commento);
            this.addProperty(p);

            /* nome del database preferenze comuni*/
            commento = "Nome del database delle preferenze comuni.";
            commento += "\nSignificativo solo se le preferenze comuni sono mantenute su database";
            commento += "\nDi default uguale al nome del programma";
            p = creaProperty(NOME_DB_PREF_SHARED, commento);
            this.addProperty(p);

            /* login al database preferenze comuni */
            commento = "Login per accedere al database delle preferenze comuni.";
            commento += "\nSignificativo solo se le preferenze comuni sono mantenute su database";
            commento += "\nDi default usa il login di default del database";
            p = creaProperty(LOGIN_DB_PREF_SHARED, commento);
            this.addProperty(p);

            /* password per il database preferenze comuni */
            commento = "Password per accedere al database delle preferenze comuni.";
            commento += "\nSignificativo solo se le preferenze comuni sono mantenute su database";
            commento += "\nDi default usa la password di default del database";
            p = creaProperty(PASS_DB_PREF_SHARED, commento);
            this.addProperty(p);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Riempie gli elementi della mappa con i valori trovati nel file.
     * <p/>
     */
    private void caricaValoriDaFile() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap mappa;
        HashMap mappaFile;
        File fileConfig;
        Collection valori;
        Iterator i;
        Object o;
        Property property;
        Property fileProperty;
        String chiave;
        String valore;

        try {    // prova ad eseguire il codice

            /* recupera la mappa delle properties presenti nel file */
            fileConfig = Lib.Sist.getFileConfig();
            mappaFile = LibFile.leggeProperties(fileConfig);

            /* recupera la mappa in memoria e assegna i valori
             * agli elementi prendendoli dalla mappa da file */
            mappa = this.getProperties();
            valori = mappa.values();
            i = valori.iterator();
            while (i.hasNext()) {
                o = i.next();
                if (o != null) {
                    if (o instanceof Property) {
                        property = (Property)o;
                        chiave = property.getChiave();
                        o = mappaFile.get(chiave);
                        if (o != null) {
                            if (o instanceof Property) {
                                fileProperty = (Property)o;
                                valore = fileProperty.getValore();
                                property.setValore(valore);
                            }// fine del blocco if
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if

            }// fine del blocco while

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla se il contenuto del file di configurazione
     * e' sincronizzato con il contenuto della mappa delle properties
     * <p/>
     * Il contenuto e' sincronizzato se:
     * - le entries hanno lo stesso numero e lo stesso ordine
     * - tutti i campi delle properties sono uguali, tranne il valore
     * Il valore delle properties non e' preso in considerazione
     * perche' e' stato appena letto dal file.
     *
     * @return true se le entry del file sono sincronizzate con la mappa
     */
    private boolean isFileSincronizzato() {
        /* variabili e costanti locali di lavoro */
        boolean isSincronizzato = false;
        File fileConfig;
        LinkedHashMap mappaMemoria;
        LinkedHashMap mappaFile;
        ArrayList propertiesMem;
        ArrayList propertiesFile;
        Property propertyMem;
        Property propertyFile;

        try {    // prova ad eseguire il codice
            mappaMemoria = this.getProperties();
            fileConfig = Lib.Sist.getFileConfig();
            mappaFile = LibFile.leggeProperties(fileConfig);
            propertiesMem = Libreria.hashMapToArrayList(mappaMemoria);
            propertiesFile = Libreria.hashMapToArrayList(mappaFile);
            if (propertiesMem.size() == propertiesFile.size()) {
                isSincronizzato = true;
                for (int k = 0; k < propertiesMem.size(); k++) {
                    propertyMem = (Property)propertiesMem.get(k);
                    propertyFile = (Property)propertiesFile.get(k);
                    if (!isPropertyUguali(propertyMem, propertyFile)) {
                        isSincronizzato = false;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return isSincronizzato;
    }


    /**
     * Genera il file di configurazione dalla mappa in memoria.
     * <p/>
     * Usa chiave, valore e commento dalla mappa memoria.
     *
     * @return true se riuscito
     */
    private boolean generaFileDaMappaMemoria() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua = true;
        LinkedHashMap mappa;
        ArrayList properties;
        Object o;
        Property p;
        File fileConfig;
        FileWriter fstream;
        BufferedWriter out;
        String nomeProg;

        try {    // prova ad eseguire il codice
            fileConfig = Lib.Sist.getFileConfig();
            mappa = this.getProperties();
            properties = Libreria.hashMapToArrayList(mappa);

            /* svuota il file se esistente */
            if (continua) {
                continua = LibFile.svuotaFile(fileConfig);
            }// fine del blocco if

            /* aggiunge le informazioni generali */
            if (continua) {

                nomeProg = Progetto.getNomePrimoModulo().toLowerCase();

                fstream = new FileWriter(fileConfig);
                out = new BufferedWriter(fstream);

                out.write("* File di configurazione del programma " + nomeProg + ".\n");
                out.write(
                        "* Mantiene i parametri necessari ad accedere agli archivi delle preferenze.\n");
                out.write("* Esistono due archivi di preferenze: locale e comune.\n");
                out.write("* Le preferenze locali hanno la precedenza su quelle comuni.\n");
                out.write("* Ognuno puo' essere mantenuto su file di testo o su database SQL.\n");
                out.write(
                        "* Ognuno puo' risiedere sulla macchina locale o su un indirizzo di rete.\n");
                out.write("* Tipicamente, l'archivio preferenze locale e' mantenuto su un file\n");
                out.write("* di testo sulla macchina locale e l'archivio preferenze comune e'\n");
                out.write("* mantenuto su un server SQL in rete.\n");
                out.write("* Se le preferenze comuni sono mantenute su un database, il server.\n");
                out.write("* puo' anche essere quello dove sono mantenuti i dati.\n");

                out.close();
            }// fine del blocco if

            /* aggiunge le properties al file nell'ordine di creazione */
            if (continua) {
                riuscito = true;

                for (int k = 0; k < properties.size(); k++) {
                    o = properties.get(k);
                    if (o != null) {
                        if (o instanceof Property) {
                            p = (Property)o;
                            if (!LibFile.addProperty(fileConfig, p)) {
                                riuscito = false;
                                break;
                            }// fine del blocco if-else
                        }// fine del blocco if
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Confronta due properties e determina se la loro struttura
     * e' uguale.
     * <p/>
     * Considera tutti i campi tranne il valore.
     *
     * @param p1 property 1
     * @param p2 property 2
     *
     * @return true se le strutture sono uguali
     */
    private static boolean isPropertyUguali(Property p1, Property p2) {
        /* variabili e costanti locali di lavoro */
        boolean uguali = false;

        /* Controllo preliminare, devono essere entrambi non nulli */
        if ((p1 == null) || (p2 == null)) {
            return false;
        }// fine del blocco if
        /* comparo le chiavi */
        if (p1.getChiave().equals(p2.getChiave())) {
            /* comparo i commenti */
            if (p1.getCommento().equals(p2.getCommento())) {
                uguali = true;
            } /* fine del blocco if-else */
        } /* fine del blocco if-else */

        /* valore di ritorno */
        return uguali;
    }


    /**
     * Crea una nuova Property.
     * <p/>
     *
     * @param chiave la chiave per la property
     * @param commento il commento alla property (testo separato da \n)
     * @param valore il valore della property
     *
     * @return la property creata
     */
    private static Property creaProperty(String chiave, String commento, String valore) {
        /* variabili e costanti locali di lavoro */
        Property property = null;

        try {    // prova ad eseguire il codice
            property = new Property();
            property.setChiave(chiave);
            property.setCommento(commento);
            property.setValore(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return property;
    }


    /**
     * Crea una nuova Property con il valore di default.
     * <p/>
     *
     * @param chiave la chiave per la property
     * @param commento il commento alla property (testo separato da \n)
     *
     * @return la property creata
     */
    private static Property creaProperty(String chiave, String commento) {
        return creaProperty(chiave, commento, VALORE_DEFAULT);
    }


    /**
     * Aggiunge una nuova Property alla collezione.
     * <p/>
     *
     * @param p la property da aggiungere
     */
    private void addProperty(Property p) {
        /* variabili e costanti locali di lavoro */
        String chiave = null;

        try {    // prova ad eseguire il codice
            chiave = p.getChiave();
            if (Lib.Testo.isValida(chiave)) {
                this.getProperties().put(chiave, p);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Restituisce il singolo elemento della collezione properties.
     *
     * @param chiave per recuperare l'elemento dalla collezione
     *
     * @return elemento di tipo ConfigProperty; nullo se non lo trova
     */
    private Property getProperty(String chiave) {
        /* variabili e costanti locali di lavoro */
        Property property = null;
        Object oggetto = null;

        try { // prova ad eseguire il codice
            oggetto = this.getProperties().get(chiave);

            if ((oggetto != null) && (oggetto instanceof Property)) {
                property = (Property)oggetto;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return property;
    }


    /**
     * Restituisce il valore stringa di una property.
     *
     * @param chiave per recuperare il valore dalla collezione
     *
     * @return valore stringa della property
     *         una stringa vuota indica che si deve usare il valore di default
     */
    public static String getValore(String chiave) {
        /* variabili e costanti locali di lavoro */
        String valore = "";
        Property property;

        try { // prova ad eseguire il codice
            property = getIstanza().getProperty(chiave);
            if (property != null) {
                valore = property.getValore();
                if (valore.equals(Config.VALORE_DEFAULT)) {
                    valore = "";
                }// fine del blocco if

            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    private LinkedHashMap getProperties() {
        return properties;
    }


    private void setProperties(LinkedHashMap properties) {
        this.properties = properties;
    }


    public static TipoArchivio getTipoArchivioNet() {
        /* variabili e costanti locali di lavoro */
        TipoArchivio tipo = null;
        Object val;
        int ordinale;

        try { // prova ad eseguire il codice
            val = getValore(TIPO_ARCHIVIO_PREF_SHARED);

            ordinale = Libreria.getInt(val);

            if (ordinale == 0) {  // default
                tipo = TipoArchivio.testo;
            } else {
                tipo = TipoArchivio.values()[ordinale - 1];
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return tipo;
    }


    public static TipoArchivio getTipoArchivioLoc() {
        /* variabili e costanti locali di lavoro */
        TipoArchivio tipo = null;
        Object val;
        int ordinale;

        try { // prova ad eseguire il codice
            val = getValore(TIPO_ARCHIVIO_PREF_LOCAL);

            ordinale = Libreria.getInt(val);

            if (ordinale == 0) {  // default
                tipo = TipoArchivio.testo;
            } else {
                tipo = TipoArchivio.values()[ordinale - 1];
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return tipo;
    }


    /**
     * Ritorna l'oggetto di configurazione del programma (Singleton)
     * <p/>
     *
     * @return l'oggetto di configurazione
     */
    public static Config getIstanza() {
        if (ISTANZA == null) {
            ISTANZA = new Config();
        }// fine del blocco if

        return ISTANZA;
    } /* fine del metodo getter */


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei Navigatori del modulo
     * (oltre a quelli standard della superclasse)
     */
    public enum TipoArchivio {

        database, testo, xml;

    }

}// fine della classe
