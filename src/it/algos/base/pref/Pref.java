/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 6 ott 2006
 */

package it.algos.base.pref;

import it.algos.base.config.Config;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.progetto.Progetto;
import it.algos.base.utenti.Utenti;

import javax.mail.Message;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;


/**
 * Preferenze base del programma.
 * </p>
 * Questa classe: <ul>
 * <li> Contiene 5 Enumeration generali </li>
 * <li> Può essere estesa da sottoclassi specifiche di un programma</li>
 * </ul>
 * <p/>
 * Parametri richiesti per costruire un oggetto preferenza:<ul>
 * <li> Alias </li>
 * <li> Sigla </li>
 * <li> Descrizione </li>
 * <li> Livello </li>
 * <li> Tipo di dati</li>
 * <li> Lista di valori (solo per il combo) </li>
 * <li> Valore standard </li>
 * <li> Nota </li>
 * <li> Nota programmatore </li>
 * <li> Visibile </li>
 * <li> Mostra valore di default </li>
 * <li> Prima istallazione </li>
 * <li> Registra e conserva tutti i valori utulizzati precwedentemente </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 6 ott 2006
 */
public class Pref {

    /**
     * Parametri in ingresso al programma
     */
    public static LinkedHashMap<String, Object> argomenti;

    /**
     * Collezione di gruppi tematici di preferenze
     */
    private static LinkedHashMap<String, ArrayList<PrefGruppi>> gruppi;

    /**
     * store per le preferenze locali
     */
    private static PrefStore storeLocali;

    /**
     * store per le preferenze comuni
     */
    private static PrefStore storeComuni;


    /**
     * Classe interna Enumerazione.
     */
    public enum Gen implements PrefGruppi {

        tipoUtente("",
                "Tipo utente",
                "Tipologia dell'utente collegato",
                Utente.user,
                TipoDati.combo,
                Utente.getLista(),
                Utente.admin.ordinal(),
                "",
                "Di default parte come amministratore",
                true,
                true,
                false,
                false),
        nomeUtente("",
                "Nome utente",
                "Nome dell'utente collegato",
                Utente.user,
                TipoDati.stringa,
                null,
                Utenti.TipoUte.admin.getSigla(),
                "Utente collegato di default (o l'ultima volta)",
                "",
                true,
                true,
                true,
                true),
        livello("",
                "Livello",
                "Livello di uso del programma",
                Utente.prog,
                TipoDati.combo,
                Livello.getLista(),
                Livello.basso.ordinal(),
                "",
                "Di default parte come livello più basso",
                true,
                true,
                false,
                false),
        setup("",
                "Inizializzato",
                "Inizializzazione iniziale del programma",
                Utente.admin,
                TipoDati.booleano,
                null,
                false,
                "true se il programma è stato inizializzato",
                "",
                true,
                true,
                false,
                false);

        /**
         * wrapper con tutti i valori
         */
        private PrefWrap wrap;


        /**
         * Costruttore completo.
         *
         * @param alias          della preferenza
         * @param sigla          della preferenza
         * @param descrizione    della preferenza
         * @param livello        di utente che può accedere
         * @param tipoDati       di dato utilizzato
         * @param lista          di valori
         * @param standard       valore standard
         * @param nota           per l'utente
         * @param notaProgr      per il programmatore
         * @param visibile       nel dialogo di modifica
         * @param mostraDefault  nel dialogo di modifica
         * @param setup          nella prima istallazione
         * @param valoriMultipli registrati
         */
        Gen(String alias,
            String sigla,
            String descrizione,
            Utente livello,
            TipoDati tipoDati,
            ArrayList<PrefTipo> lista,
            Object standard,
            String nota,
            String notaProgr,
            boolean visibile,
            boolean mostraDefault,
            boolean setup,
            boolean valoriMultipli) {

            /* variabili e costanti locali di lavoro */
            PrefWrap wrap;

            try { // prova ad eseguire il codice

                wrap = new PrefWrap();

                wrap.setAlias(alias);
                wrap.setSigla(sigla);
                wrap.setDescrizione(descrizione);
                wrap.setLivello(livello);
                wrap.setTipoDati(tipoDati);
                wrap.setLista(lista);
                wrap.setStandard(standard);
                wrap.setNota(nota);
                wrap.setNotaProg(notaProgr);
                wrap.setVisibile(visibile);
                wrap.setMostraDefault(mostraDefault);
                wrap.setSetup(setup);
                wrap.setUsaValoriMultipli(valoriMultipli);

                this.setWrap(wrap);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String str() {
            /* invoca il metodo delegato della classe */
            return Pref.str(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int intero() {
            /* invoca il metodo delegato della classe */
            return Pref.intero(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di booleano.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public boolean is() {
            /* invoca il metodo delegato della classe */
            return Pref.is(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di data.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Date data() {
            /* invoca il metodo delegato della classe */
            return Pref.data(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int comboInt() {
            /* invoca il metodo delegato della classe */
            return Pref.comboInt(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String comboStr() {
            /* invoca il metodo delegato della classe */
            return Pref.comboStr(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di oggetto.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Object comboOgg() {
            /* invoca il metodo delegato della classe */
            return Pref.comboOgg(this);
        }


        public PrefWrap getWrap() {
            return wrap;
        }


        private void setWrap(PrefWrap wrap) {
            this.wrap = wrap;
        }
    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum DB implements PrefGruppi {

        uso("",
                "Uso del database",
                "Uso del database per archiviare i dati",
                Utente.admin,
                TipoDati.booleano,
                null,
                true,
                "true se il programma usa un database",
                "",
                true,
                true,
                false),
        tipo("",
                "Tipo DB",
                "Tipo di database utilizzato",
                Utente.admin,
                TipoDati.combo,
                TipoDb.getLista(),
                TipoDb.hsqldb.ordinal(),
                "",
                "",
                true,
                true,
                false),
        indirizzo("",
                "Indirizzo IP",
                "Indirizzo del database",
                Utente.admin,
                TipoDati.stringa,
                null,
                "localhost",
                "Indirizzo IP (o nome risolvibile dal DNS) per il collegamento al database.\n" +
                        "Significativo solo per database con funzionamento client-server.\n" +
                        "default = localhost",
                "Se non funziona localhost, prova con 127.0.0.1",
                true,
                true,
                false),
        archivio("",
                "Archivio",
                "Nome del database di archivio",
                Utente.admin,
                TipoDati.stringa,
                null,
                "algos",
                "Nome del database contenente i dati al quale collegarsi",
                "",
                true,
                true,
                true),
        utente("",
                "Nome utente",
                "Nome dell'utente (login)",
                Utente.admin,
                TipoDati.stringa,
                null,
                "",
                "Nome utente per l'accesso al database\n" +
                        "vuoto = utente di default del database",
                "",
                true,
                true,
                false),
        password("",
                "Password",
                "Password per il database",
                Utente.admin,
                TipoDati.stringa,
                null,
                "",
                "Password per l'accesso al database\n" + "vuoto = password di default del database",
                "",
                true,
                true,
                false),
        porta("",
                "Porta",
                "Porta di collegamento",
                Utente.admin,
                TipoDati.intero,
                null,
                0,
                "Numero della porta di collegamento al database\n" +
                        "0 = porta di default del database",
                "",
                true,
                true,
                false),
        modo("",
                "Modo di funzionamento",
                "Modo di funzionamento del database",
                Utente.admin,
                TipoDati.combo,
                ModoDb.getLista(),
                ModoDb.database.ordinal(),
                "",
                "",
                true,
                true,
                false),
        allinea("",
                "Allineamento iniziale",
                "Allineamento struttura dati all'avvio",
                Utente.admin,
                TipoDati.booleano,
                null,
                false,
                "Allinea la struttura dati del database all'avvio del programma",
                "Allinea la struttura del database in base al modello.\n" +
                        "Aggiunge, modifica o elimina i campi, gli indici e le constraints all'avvio.",
                true,
                true,
                false),
        congruita("",
                "Congruità",
                "Regolazione congruita' foreign keys",
                Utente.admin,
                TipoDati.booleano,
                null,
                false,
                "Controlla ed eventualmente regola la congruita' dei dati in relazione durante " +
                        "l'allineamento della struttura dati",
                "Controlla la congruita' dei dati ed eventualmente li regola prima di" +
                        " creare una foreign key (significativo solo quando ALLINEA_STRUTTURA e' true.\n" +
                        "Elimina i record o pone li campo di link a null per tutti i record orfani.\n" +
                        "La decisione se eliminare il record o porre il link a null dipende dalla" +
                        " impostazione dell'azione di integrita' referenziale del campo.\n" +
                        "Spazzola tutti i record della foreign table, impiega tempo.",
                true,
                true,
                false),
        superflui("",
                "Campi superflui",
                "Elimina campi superflui anche se non sono vuoti",
                Utente.admin,
                TipoDati.booleano,
                null,
                false,
                "Elimina campi superflui anche se non sono vuoti durante l'allineamento della struttura dati",
                "Elimina i campi superflui (presenti sul db ma non nel modello)" +
                        "anche se questi non sono vuoti.\n" +
                        "Significativo solo quando ALLINEA_STRUTTURA e' true.\n" +
                        "Normalmente i campi superflui vengono eliminati dal database solo" +
                        " se sono vuoti.\n" +
                        "Tramite questo flag si puo' forzarne l'eliminazione anche se " +
                        " contengono dei dati.",
                true,
                true,
                false),
        directory("",
                "Directory",
                "Percorso della directory dati del database",
                Utente.admin,
                TipoDati.stringa,
                null,
                "",
                "Percorso assoluto della directory dove il db mantiene i file dei dati\n" +
                        "Significativo solo per database di tipo HSQLDB in modo stand-alone.\n" +
                        "stringa vuota per il percorso di default (homeprogramma/dati)",
                "",
                true,
                true,
                false),
        tavole("",
                "Tavole",
                "Tipo di tavole del database",
                Utente.admin,
                TipoDati.combo,
                TipoTavola.getLista(),
                TipoTavola.cached.ordinal(),
                "Significativo solo per database di tipo HSQLDB",
                "",
                true,
                true,
                false),
        separatore("",
                "Separatore",
                "Separatore di campo per tavole text",
                Utente.admin,
                TipoDati.stringa,
                null,
                "",
                "Carattere separatore di campo per le tavole di tipo Text.\n" +
                        "\\\\semi = semicolon, \\\\quote = quote, \\\\space = spazio\n" +
                        "\\\\apos = apostrophe, \\\\n = newline, \\\\r =carriage return\n" +
                        "\\\\t =tab, \\\\\\\\ =backslash, \\\\u### = unicode in esadecimale.\n" +
                        "vuoto = separatore di default (tab).\n" +
                        "Significativo solo per database di tipo HSQLDB che usa tavole TEXT.",
                "",
                true,
                true,
                false);

        /**
         * wrapper con tutti i valori
         */
        private PrefWrap wrap;


        /**
         * Costruttore completo.
         *
         * @param alias         della preferenza
         * @param sigla         della preferenza
         * @param descrizione   della preferenza
         * @param livello       di utente che può accedere
         * @param tipoDati      di dato utilizzato
         * @param lista         di valori
         * @param standard      valore standard
         * @param nota          per l'utente
         * @param notaProgr     per il programmatore
         * @param visibile      nel dialogo di modifica
         * @param mostraDefault nel dialogo di modifica
         * @param setup         nella prima istallazione
         */
        DB(String alias,
           String sigla,
           String descrizione,
           Utente livello,
           TipoDati tipoDati,
           ArrayList<PrefTipo> lista,
           Object standard,
           String nota,
           String notaProgr,
           boolean visibile,
           boolean mostraDefault,
           boolean setup) {

            /* variabili e costanti locali di lavoro */
            PrefWrap wrap;

            try { // prova ad eseguire il codice

                wrap = new PrefWrap();

                wrap.setAlias(alias);
                wrap.setSigla(sigla);
                wrap.setDescrizione(descrizione);
                wrap.setLivello(livello);
                wrap.setTipoDati(tipoDati);
                wrap.setLista(lista);
                wrap.setStandard(standard);
                wrap.setNota(nota);
                wrap.setNotaProg(notaProgr);
                wrap.setVisibile(visibile);
                wrap.setMostraDefault(mostraDefault);
                wrap.setSetup(setup);

                this.setWrap(wrap);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String str() {
            /* invoca il metodo delegato della classe */
            return Pref.str(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int intero() {
            /* invoca il metodo delegato della classe */
            return Pref.intero(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di booleano.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public boolean is() {
            /* invoca il metodo delegato della classe */
            return Pref.is(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di data.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Date data() {
            /* invoca il metodo delegato della classe */
            return Pref.data(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int comboInt() {
            /* invoca il metodo delegato della classe */
            return Pref.comboInt(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String comboStr() {
            /* invoca il metodo delegato della classe */
            return Pref.comboStr(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di oggetto.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Object comboOgg() {
            /* invoca il metodo delegato della classe */
            return Pref.comboOgg(this);
        }


        public PrefWrap getWrap() {
            return wrap;
        }


        private void setWrap(PrefWrap wrap) {
            this.wrap = wrap;
        }
    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum GUI implements PrefGruppi {

        look("",
                "Look and feel",
                "Look and feel del programma",
                Utente.prog,
                TipoDati.combo,
                Look.getLista(),
                Look.mac.ordinal(),
                "",
                "",
                true,
                true,
                true),
        verboso("",
                "Verboso",
                "Mostra gli avvisi di conferma delle operazioni",
                Utente.user,
                TipoDati.booleano,
                null,
                false,
                "",
                "",
                true,
                true,
                false),
        attive("",
                "Azioni attive",
                "Visualizza nei menu solo le azioni attive",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        continua("",
                "Continua",
                "Continua dopo nuovo record",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        abilitati("",
                "Abilitati",
                "Lascia i menu Moduli e Tabelle sempre abilitati",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        espansione("",
                "Autoespansione",
                "?",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        deselezione("",
                "Deselezione",
                "Deseleziona tutte le righe della lista",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        ridimensionabile("",
                "Ridimensionabile",
                "Lista ridimensionabile",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        discontinua("",
                "Discontinua",
                "Selezione lista discontinua",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        tooltip("",
                "Tooltip",
                "Mostra tooltip colonne della lista",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        selezione("",
                "selezione",
                "Modalita' di selezione rapida da tastiera nelle liste",
                Utente.user,
                TipoDati.combo,
                TipoSel.getLista(),
                TipoSel.primo.ordinal(),
                "Ricerca nelle liste: flag per il comportamento di ricerca quando \n" +
                        "si scrive:\\n1 mostrare solo i record corrispondenti in lista, \n" +
                        "\\n2 puntare al primo record corrispondente in lista.",
                "",
                true,
                true,
                false),
        attesa("",
                "Attesa",
                "Tempo di attesa di un carattere di controllo",
                Utente.user,
                TipoDati.intero,
                null,
                2000,
                "Il tempo di attesa viene espresso in millisecondi",
                "",
                true,
                true,
                false),
        spostamento("",
                "Spostamento",
                "Mostra azioni spostamento",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        confronto("",
                "Confronto",
                "Confronto tra stringhe senza gli spazi vuoti",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        tutto("",
                "Tutto",
                "Seleziona tutto il testo entrando nel campo",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        chiude("",
                "Chiude",
                "Chiude la scheda dopo la registrazione",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        etichetta("",
                "Etichetta",
                "Etichetta scheda ridimensionabile",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        ricarica("",
                "Ricarica",
                "Ricarica i link esterni ad ogni cambio di record",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        conferma("",
                "Conferma",
                "Chiede conferma della registrazione prima dello spostamento",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false);

        /**
         * wrapper con tutti i valori
         */
        private PrefWrap wrap;


        /**
         * Costruttore completo.
         *
         * @param alias         della preferenza
         * @param sigla         della preferenza
         * @param descrizione   della preferenza
         * @param livello       di utente che può accedere
         * @param tipoDati      di dato utilizzato
         * @param lista         di valori
         * @param standard      valore standard
         * @param nota          per l'utente
         * @param notaProgr     per il programmatore
         * @param visibile      nel dialogo di modifica
         * @param mostraDefault nel dialogo di modifica
         * @param setup         nella prima istallazione
         */
        GUI(String alias,
            String sigla,
            String descrizione,
            Utente livello,
            TipoDati tipoDati,
            ArrayList<PrefTipo> lista,
            Object standard,
            String nota,
            String notaProgr,
            boolean visibile,
            boolean mostraDefault,
            boolean setup) {

            /* variabili e costanti locali di lavoro */
            PrefWrap wrap;

            try { // prova ad eseguire il codice

                wrap = new PrefWrap();

                wrap.setAlias(alias);
                wrap.setSigla(sigla);
                wrap.setDescrizione(descrizione);
                wrap.setLivello(livello);
                wrap.setTipoDati(tipoDati);
                wrap.setLista(lista);
                wrap.setStandard(standard);
                wrap.setNota(nota);
                wrap.setNotaProg(notaProgr);
                wrap.setVisibile(visibile);
                wrap.setMostraDefault(mostraDefault);
                wrap.setSetup(setup);

                this.setWrap(wrap);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String str() {
            /* invoca il metodo delegato della classe */
            return Pref.str(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int intero() {
            /* invoca il metodo delegato della classe */
            return Pref.intero(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di booleano.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public boolean is() {
            /* invoca il metodo delegato della classe */
            return Pref.is(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di data.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Date data() {
            /* invoca il metodo delegato della classe */
            return Pref.data(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int comboInt() {
            /* invoca il metodo delegato della classe */
            return Pref.comboInt(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String comboStr() {
            /* invoca il metodo delegato della classe */
            return Pref.comboStr(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di oggetto.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Object comboOgg() {
            /* invoca il metodo delegato della classe */
            return Pref.comboOgg(this);
        }


        public PrefWrap getWrap() {
            return wrap;
        }


        private void setWrap(PrefWrap wrap) {
            this.wrap = wrap;
        }
    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum Update implements PrefGruppi {

        usaAgg("",
                "Aggiornamento attivo",
                "Aggiornamento via web attivo",
                Utente.user,
                TipoDati.booleano,
                null,
                false,
                "",
                "",
                true,
                true,
                true),
        serverAgg("",
                "Server di aggiornamento",
                "Server di aggiornamento del programma",
                Utente.user,
                TipoDati.stringa,
                null,
                "213.140.8.187",
                "",
                "",
                true,
                true,
                false),
        nomeAgg("",
                "Nome utente aggiornamento",
                "Nome utente aggiornamento",
                Utente.user,
                TipoDati.stringa,
                null,
                "alex",
                "",
                "",
                false,
                true,
                false),
        passAgg("",
                "Password aggiornamento",
                "Password aggiornamento",
                Utente.user,
                TipoDati.stringa,
                null,
                "hal",
                "",
                "",
                false,
                true,
                false),
        ultimoAgg("",
                "Ultimo aggiornamento",
                "Data dell'ultimo aggiornamento effettuato",
                Utente.user,
                TipoDati.data,
                null,
                Lib.Data.getVuota(),
                "",
                "",
                true,
                false,
                false),
        confermaAgg("",
                "Conferma aggiornamento",
                "Dialogo di conferma prima di aggiornare",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        tempoAgg("",
                "Intervallo di aggiornamento",
                "Periodo di tempo prima del successivo aggiornamento",
                Utente.user,
                TipoDati.combo,
                Intervallo.getLista(),
                Intervallo.settimana.ordinal(),
                "",
                "",
                true,
                true,
                true);

        /**
         * wrapper con tutti i valori
         */
        private PrefWrap wrap;


        /**
         * Costruttore completo.
         *
         * @param alias         della preferenza
         * @param sigla         della preferenza
         * @param descrizione   della preferenza
         * @param livello       di utente che può accedere
         * @param tipoDati      di dato utilizzato
         * @param lista         di valori
         * @param standard      valore standard
         * @param nota          per l'utente
         * @param notaProgr     per il programmatore
         * @param visibile      nel dialogo di modifica
         * @param mostraDefault nel dialogo di modifica
         * @param setup         nella prima istallazione
         */
        Update(String alias,
               String sigla,
               String descrizione,
               Utente livello,
               TipoDati tipoDati,
               ArrayList<PrefTipo> lista,
               Object standard,
               String nota,
               String notaProgr,
               boolean visibile,
               boolean mostraDefault,
               boolean setup) {

            /* variabili e costanti locali di lavoro */
            PrefWrap wrap;

            try { // prova ad eseguire il codice

                wrap = new PrefWrap();

                wrap.setAlias(alias);
                wrap.setSigla(sigla);
                wrap.setDescrizione(descrizione);
                wrap.setLivello(livello);
                wrap.setTipoDati(tipoDati);
                wrap.setLista(lista);
                wrap.setStandard(standard);
                wrap.setNota(nota);
                wrap.setNotaProg(notaProgr);
                wrap.setVisibile(visibile);
                wrap.setMostraDefault(mostraDefault);
                wrap.setSetup(setup);

                this.setWrap(wrap);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String str() {
            /* invoca il metodo delegato della classe */
            return Pref.str(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int intero() {
            /* invoca il metodo delegato della classe */
            return Pref.intero(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di booleano.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public boolean is() {
            /* invoca il metodo delegato della classe */
            return Pref.is(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di data.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Date data() {
            /* invoca il metodo delegato della classe */
            return Pref.data(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int comboInt() {
            /* invoca il metodo delegato della classe */
            return Pref.comboInt(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String comboStr() {
            /* invoca il metodo delegato della classe */
            return Pref.comboStr(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di oggetto.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Object comboOgg() {
            /* invoca il metodo delegato della classe */
            return Pref.comboOgg(this);
        }


        public PrefWrap getWrap() {
            return wrap;
        }


        private void setWrap(PrefWrap wrap) {
            this.wrap = wrap;
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum Backup implements PrefGruppi {

        usaBackup("",
                "Backup attivo",
                "Backup attivo",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                true),
        ultimoBackup("",
                "Ultimo backup",
                "Data dell'ultimo backup effettuato",
                Utente.user,
                TipoDati.data,
                null,
                Lib.Data.getVuota(),
                "",
                "",
                true,
                false,
                false),
        confermaBackup("",
                "Conferma backup",
                "Dialogo di conferma prima di effetuare il backup",
                Utente.user,
                TipoDati.booleano,
                null,
                true,
                "",
                "",
                true,
                true,
                false),
        tempoBackup("",
                "Intervallo di backup",
                "Periodo di tempo prima del successivo backup",
                Utente.user,
                TipoDati.combo,
                Intervallo.getLista(),
                Intervallo.settimana.ordinal(),
                "",
                "",
                true,
                true,
                true),

        dirBackup("",
                "Cartella",
                "Nome della cartella in cui effettuare il bacup",
                Utente.user,
                TipoDati.stringa,
                null,
                "",
                "",
                "",
                true,
                false,
                false);

        /**
         * wrapper con tutti i valori
         */
        private PrefWrap wrap;


        /**
         * Costruttore completo.
         *
         * @param alias         della preferenza
         * @param sigla         della preferenza
         * @param descrizione   della preferenza
         * @param livello       di utente che può accedere
         * @param tipoDati      di dato utilizzato
         * @param lista         di valori
         * @param standard      valore standard
         * @param nota          per l'utente
         * @param notaProgr     per il programmatore
         * @param visibile      nel dialogo di modifica
         * @param mostraDefault nel dialogo di modifica
         * @param setup         nella prima istallazione
         */
        Backup(String alias,
               String sigla,
               String descrizione,
               Utente livello,
               TipoDati tipoDati,
               ArrayList<PrefTipo> lista,
               Object standard,
               String nota,
               String notaProgr,
               boolean visibile,
               boolean mostraDefault,
               boolean setup) {

            /* variabili e costanti locali di lavoro */
            PrefWrap wrap;

            try { // prova ad eseguire il codice

                wrap = new PrefWrap();

                wrap.setAlias(alias);
                wrap.setSigla(sigla);
                wrap.setDescrizione(descrizione);
                wrap.setLivello(livello);
                wrap.setTipoDati(tipoDati);
                wrap.setLista(lista);
                wrap.setStandard(standard);
                wrap.setNota(nota);
                wrap.setNotaProg(notaProgr);
                wrap.setVisibile(visibile);
                wrap.setMostraDefault(mostraDefault);
                wrap.setSetup(setup);

                this.setWrap(wrap);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String str() {
            /* invoca il metodo delegato della classe */
            return Pref.str(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int intero() {
            /* invoca il metodo delegato della classe */
            return Pref.intero(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di booleano.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public boolean is() {
            /* invoca il metodo delegato della classe */
            return Pref.is(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di data.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Date data() {
            /* invoca il metodo delegato della classe */
            return Pref.data(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int comboInt() {
            /* invoca il metodo delegato della classe */
            return Pref.comboInt(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String comboStr() {
            /* invoca il metodo delegato della classe */
            return Pref.comboStr(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di oggetto.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Object comboOgg() {
            /* invoca il metodo delegato della classe */
            return Pref.comboOgg(this);
        }


        public PrefWrap getWrap() {
            return wrap;
        }


        private void setWrap(PrefWrap wrap) {
            this.wrap = wrap;
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum Mail implements PrefGruppi {

        serverPosta("",
                "Server di posta",
                "Server SMTP di posta utilizzato",
                Utente.user,
                TipoDati.stringa,
                null,
                "mail.libero.it",
                "",
                "",
                true,
                true,
                false),
        testo("",
                "Tipo di testo",
                "Tipo di testo utilizzato nella posta",
                Utente.user,
                TipoDati.combo,
                WebTesto.getLista(),
                WebTesto.plain.ordinal(),
                "",
                "",
                true,
                true,
                false),
        multiple("",
                "Spedizioni multiple",
                "Numero di indirizzi multipli per ogni spedizione",
                Utente.user,
                TipoDati.intero,
                null,
                10,
                "",
                "",
                true,
                true,
                false),
        copie("",
                "Originale e copie",
                "Modalità di invio delle copie",
                Utente.user,
                TipoDati.combo,
                TipoPosta.getLista(),
                TipoPosta.copiaNascosta.ordinal(),
                "Serve per nascondere i destinatari negli invii a gruppi",
                "",
                true,
                true,
                false),
        mittente("",
                "Mittente di posta",
                "Nome del mittente utilizzato per la spedizione",
                Utente.user,
                TipoDati.stringa,
                null,
                "gac@algos.it",
                "",
                "",
                true,
                false,
                false),
        controllo("",
                "Controllo",
                "Mail di controllo",
                Utente.user,
                TipoDati.booleano,
                null,
                false,
                "",
                "",
                true,
                false,
                false);


        /**
         * wrapper con tutti i valori
         */
        private PrefWrap wrap;


        /**
         * Costruttore completo.
         *
         * @param alias         della preferenza
         * @param sigla         della preferenza
         * @param descrizione   della preferenza
         * @param livello       di utente che può accedere
         * @param tipoDati      di dato utilizzato
         * @param lista         di valori
         * @param standard      valore standard
         * @param nota          per l'utente
         * @param notaProgr     per il programmatore
         * @param visibile      nel dialogo di modifica
         * @param mostraDefault nel dialogo di modifica
         * @param setup         nella prima istallazione
         */
        Mail(String alias,
             String sigla,
             String descrizione,
             Utente livello,
             TipoDati tipoDati,
             ArrayList<PrefTipo> lista,
             Object standard,
             String nota,
             String notaProgr,
             boolean visibile,
             boolean mostraDefault,
             boolean setup) {

            /* variabili e costanti locali di lavoro */
            PrefWrap wrap;

            try { // prova ad eseguire il codice

                wrap = new PrefWrap();

                wrap.setAlias(alias);
                wrap.setSigla(sigla);
                wrap.setDescrizione(descrizione);
                wrap.setLivello(livello);
                wrap.setTipoDati(tipoDati);
                wrap.setLista(lista);
                wrap.setStandard(standard);
                wrap.setNota(nota);
                wrap.setNotaProg(notaProgr);
                wrap.setVisibile(visibile);
                wrap.setMostraDefault(mostraDefault);
                wrap.setSetup(setup);

                this.setWrap(wrap);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String str() {
            /* invoca il metodo delegato della classe */
            return Pref.str(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int intero() {
            /* invoca il metodo delegato della classe */
            return Pref.intero(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di booleano.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public boolean is() {
            /* invoca il metodo delegato della classe */
            return Pref.is(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di data.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Date data() {
            /* invoca il metodo delegato della classe */
            return Pref.data(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int comboInt() {
            /* invoca il metodo delegato della classe */
            return Pref.comboInt(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String comboStr() {
            /* invoca il metodo delegato della classe */
            return Pref.comboStr(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di oggetto.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Object comboOgg() {
            /* invoca il metodo delegato della classe */
            return Pref.comboOgg(this);
        }


        public PrefWrap getWrap() {
            return wrap;
        }


        private void setWrap(PrefWrap wrap) {
            this.wrap = wrap;
        }
    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum Cost implements PrefGruppi {

        prog("prog", Utente.user, TipoDati.booleano, null, false),
        admin("admin", Utente.user, TipoDati.booleano, null, false),
        importa("imp", Utente.user, TipoDati.booleano, null, false),
        background("back", Utente.user, TipoDati.booleano, null, false),
        posta("posta", Utente.user, TipoDati.booleano, null, false),
        controlloCampi("controllocampi", Utente.user, TipoDati.booleano, null, true);


        /**
         * wrapper con tutti i valori
         */
        private PrefWrap wrap;


        /**
         * Costruttore completo.
         *
         * @param chiave   parametro (argomento)
         * @param livello  di utente che può accedere
         * @param tipoDati di dato utilizzato
         * @param lista    di valori
         * @param standard valore standard
         */
        Cost(String chiave,
             Utente livello,
             TipoDati tipoDati,
             ArrayList<PrefTipo> lista,
             Object standard) {

            /* variabili e costanti locali di lavoro */
            PrefWrap wrap;

            try { // prova ad eseguire il codice

                wrap = new PrefWrap();

                wrap.setChiave(chiave);
                wrap.setLivello(livello);
                wrap.setTipoDati(tipoDati);
                wrap.setLista(lista);
                wrap.setStandard(standard);
                wrap.setValore(standard);

                this.setWrap(wrap);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String str() {
            /* variabili e costanti locali di lavoro */
            String str = "";

            try { // prova ad eseguire il codice
                if (this.getWrap().getTipoDati() == TipoDati.stringa) {
                    str = (String)this.getWrap().getValore();
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return str;
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int intero() {
            /* variabili e costanti locali di lavoro */
            int num = 0;

            try { // prova ad eseguire il codice
                if (this.getWrap().getTipoDati() == TipoDati.intero) {
                    num = (Integer)this.getWrap().getValore();
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return num;
        }


        /**
         * Restituisce il valore corrente sotto forma di booleano.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public boolean is() {
            /* variabili e costanti locali di lavoro */
            boolean bool = false;

            try { // prova ad eseguire il codice
                if (this.getWrap().getTipoDati() == TipoDati.booleano) {
                    bool = (Boolean)this.getWrap().getValore();
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return bool;
        }


        /**
         * Restituisce il valore corrente sotto forma di data.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Date data() {
            /* variabili e costanti locali di lavoro */
            Date data = null;

            try { // prova ad eseguire il codice
                if (this.getWrap().getTipoDati() == TipoDati.data) {
                    data = (Date)this.getWrap().getValore();
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return data;
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int comboInt() {
            /* invoca il metodo delegato della classe */
            return Pref.comboInt(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String comboStr() {
            /* invoca il metodo delegato della classe */
            return Pref.comboStr(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di oggetto.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Object comboOgg() {
            /* invoca il metodo delegato della classe */
            return Pref.comboOgg(this);
        }


        public void setValore(Object valore) {
            this.getWrap().setValore(valore);
        }


        public PrefWrap getWrap() {
            return wrap;
        }


        private void setWrap(PrefWrap wrap) {
            this.wrap = wrap;
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum Hard {

        pippo("", Hard.Tipo.vero, null),
        pluto("", Hard.Tipo.falso, null),
        paperino("", Hard.Tipo.livello, Livello.medio),;

        private String nome;

        private Hard.Tipo tipo;

        private Livello livello;


        /**
         * Costruttore completo.
         *
         * @param nome    della preferenza
         * @param tipo    della preferenza
         * @param livello della preferenza
         */
        Hard(String nome, Hard.Tipo tipo, Livello livello) {

            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNome(nome);
                this.setTipo(tipo);
                this.setLivello(livello);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce il valore corrente sotto forma di booleano.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public boolean is() {
            /* variabili e costanti locali di lavoro */
            boolean valida = false;
            Livello livelloPreferenza;
            Livello livelloProgramma;

            try { // prova ad eseguire il codice
                switch (this.getTipo()) {
                    case vero:
                        valida = true;
                        break;
                    case falso:
                        valida = false;
                        break;
                    case livello:
                        livelloPreferenza = this.getLivello();
                        livelloProgramma = (Livello)Pref.Gen.livello.comboOgg();

                        valida = (livelloPreferenza.ordinal() >= livelloProgramma.ordinal());
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
                ;
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return valida;
        }


        private String getNome() {
            return nome;
        }


        private void setNome(String nome) {
            this.nome = nome;
        }


        public Tipo getTipo() {
            return tipo;
        }


        private void setTipo(Tipo tipo) {
            this.tipo = tipo;
        }


        private Livello getLivello() {
            return livello;
        }


        private void setLivello(Livello livello) {
            this.livello = livello;
        }


        /**
         * Classe interna Enumerazione.
         */
        public enum Tipo {

            vero,
            falso,
            livello

        }// fine della classe

    }// fine della classe

    /**
     * Metodo statico eseguito quando la classe viene caricata.
     * <p/>
     */
    static {
        /* mappa statica per i parametri in ingresso (argomenti del main()) */
        Pref.setArgomenti(new LinkedHashMap<String, Object>());
    }


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public Pref() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     * <p/>
     * crea il contenitore per tutte le Enumeration <br>
     * aggiunge le Enumeration del progetto base <br>
     * le sottoclassi possono aggiungere altre Enumeration <br>
     * siccome le sottoclassi chiamano questo metodo quando vengono create,
     * aggiunge le Enumeration base solo la prima volta <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        PrefStore store;
        Config.TipoArchivio tipo;

        try { // prova ad eseguire il codice

            /* modalità di registrazione delle preferenze in rete */
            tipo = Config.getTipoArchivioNet();
            store = this.creaStore(tipo, true);
            setStoreComuni(store);

            /* modalità di registrazione delle preferenze in locale */
            tipo = Config.getTipoArchivioLoc();
            store = this.creaStore(tipo, false);
            setStoreLocali(store);

            /* crea il contenitore per i gruppi di Enumeration */
            if (Pref.getGruppi() == null) {
                Pref.setGruppi(new LinkedHashMap<String, ArrayList<PrefGruppi>>());
                continua = true;
            }// fine del blocco if

            /* aggiunge le Enumeration del progetto base */
            if (continua) {
                this.addGruppo(Gen.class);
                this.addGruppo(DB.class);
                this.addGruppo(GUI.class);
                this.addGruppo(Update.class);
                this.addGruppo(Backup.class);
                this.addGruppo(Mail.class);
            }// fine del blocco if

            Pref.carica();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un prefStore del tipo richiesto.
     * </p>
     *
     * @param tipo   di store da creare
     * @param comune true se comune, false se locale
     *
     * @return lo store creato
     */
    private PrefStore creaStore(Config.TipoArchivio tipo, boolean comune) {
        /* variabili e costanti locali di lavoro */
        PrefStore store = null;

        try { // prova ad eseguire il codice

            switch (tipo) {
                case database:
                    store = new PrefStoreSql(comune);
                    break;
                case testo:
                    store = new PrefStoreTxt(comune);
                    break;
                case xml:

                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch
            store.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return store;
    }


    /**
     * Carica le preferenze dall'archivio.
     */
    public static void carica() {
        /* variabili e costanti locali di lavoro */
        PrefStore archivioLocale;
        PrefStore archivioServer;

        try { // prova ad eseguire il codice

            archivioLocale = Pref.getStoreLocali();
            archivioServer = Pref.getStoreComuni();

            if (archivioServer != null) {
                archivioServer.carica();
            }// fine del blocco if

            if (!Progetto.isAmministratore()) {
                if (archivioLocale != null) {
                    archivioLocale.carica();
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Registra le preferenze nell''archivio.
     */
    public static void registra() {
        /* variabili e costanti locali di lavoro */
        PrefStore archivioLocale;
        PrefStore archivioServer;

        try { // prova ad eseguire il codice
            archivioLocale = Pref.getStoreLocali();
            archivioServer = Pref.getStoreComuni();

            if (Progetto.isAmministratore()) {
                archivioServer.registra();
            } else {
                archivioLocale.registra();
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge una Enumeration (gruppo logico di preferenze).
     * <p/>
     * Metodo chiamato dalle sottoclassi <br>
     * Controlla che l'oggetto in ingresso sia della classe corretta <br>
     *
     * @param enumeration con gli oggetti da aggiungere
     */
    protected void addGruppo(Class enumeration) {
        boolean continua;
        LinkedHashMap<String, ArrayList<PrefGruppi>> gruppi;
        Object[] oggetti;
        PrefGruppi pref;
        String nomeGruppo = "";
        ArrayList<PrefGruppi> gruppo;
        String tag = ".";
        int pos;

        try { // prova ad eseguire il codice
            gruppi = Pref.getGruppi();
            continua = (gruppi != null);

            if (continua) {
                continua = enumeration.isEnum();
            }// fine del blocco if

            if (continua) {
                nomeGruppo = enumeration.getCanonicalName();
                pos = nomeGruppo.lastIndexOf(tag);
                pos = nomeGruppo.lastIndexOf(tag, --pos);
                nomeGruppo = nomeGruppo.substring(++pos);

                continua = !(gruppi.containsKey(nomeGruppo));
            }// fine del blocco if

            if (continua) {
                oggetti = enumeration.getEnumConstants();
                gruppo = new ArrayList<PrefGruppi>();

                /* traverso tutta la collezione */
                for (Object ogg : oggetti) {
                    if (ogg instanceof PrefGruppi) {
                        pref = (PrefGruppi)ogg;
                        this.regola(pref, nomeGruppo);
                        gruppo.add(pref);
                    }// fine del blocco if
                } // fine del ciclo for-each

                gruppi.put(nomeGruppo, gruppo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Carica il valore di default nel valore corrente.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Effettua un rigoroso controllo di tipo <br>
     *
     * @param gruppo oggetto della Enumeration
     */
    protected void regola(PrefGruppi gruppo, String nomeGruppo) {
        /* variabili e costanti locali di lavoro */
        Object ogg;
        TipoDati tipoDati;
        Class classe;
        PrefWrap wrap;

        try { // prova ad eseguire il codice
            tipoDati = gruppo.getWrap().getTipoDati();
            classe = tipoDati.getClasse();

            wrap = gruppo.getWrap();
            ogg = wrap.getStandard();
            if (ogg.getClass().getCanonicalName().equals(classe.getCanonicalName())) {
                wrap.setValore(ogg);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce il valore corrente.
     * <p/>
     * Controlla che il valore corrente sia del tipo previsto <br>
     *
     * @param tema oggetto della Enumeration
     *
     * @return valore solo se il tipo dati è corretto
     */
    private static Object oggetto(PrefGruppi tema) {
        /* variabili e costanti locali di lavoro */
        Object oggetto = null;
        Object ogg;
        TipoDati tipoDati;
        Class classe;

        try { // prova ad eseguire il codice
            tipoDati = tema.getWrap().getTipoDati();
            classe = tipoDati.getClasse();

            ogg = tema.getWrap().getValore();
            if (ogg != null) {
                if (ogg.getClass().getCanonicalName().equals(classe.getCanonicalName())) {
                    oggetto = ogg;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return oggetto;
    }


    /**
     * Restituisce il valore corrente sotto forma di stringa.
     *
     * @param tema oggetto della Enumeration
     *
     * @return valore solo se il tipo dati è corretto
     */
    protected static String str(PrefGruppi tema) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Object ogg;

        try { // prova ad eseguire il codice
            ogg = Pref.oggetto(tema);

            if (ogg != null) {
                if (ogg instanceof String) {
                    stringa = (String)ogg;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Restituisce il valore corrente sotto forma di booleano.
     *
     * @param tema oggetto della Enumeration
     *
     * @return valore solo se il tipo dati è corretto
     */
    protected static boolean is(PrefGruppi tema) {
        /* variabili e costanti locali di lavoro */
        boolean bool = false;
        Object ogg;

        try { // prova ad eseguire il codice
            ogg = Pref.oggetto(tema);

            if (ogg != null) {
                if (ogg instanceof Boolean) {
                    bool = (Boolean)ogg;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bool;
    }


    /**
     * Restituisce il valore corrente sotto forma di intero.
     *
     * @param tema oggetto della Enumeration
     *
     * @return valore solo se il tipo dati è corretto
     */
    protected static int intero(PrefGruppi tema) {
        /* variabili e costanti locali di lavoro */
        Integer intero = 0;
        Object ogg;

        try { // prova ad eseguire il codice
            ogg = Pref.oggetto(tema);

            if (ogg != null) {
                if (ogg instanceof Integer) {
                    intero = (Integer)ogg;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return intero;
    }


    /**
     * Restituisce il valore corrente sotto forma di data.
     *
     * @param tema oggetto della Enumeration
     *
     * @return valore solo se il tipo dati è corretto
     */
    protected static Date data(PrefGruppi tema) {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        Object ogg;

        try { // prova ad eseguire il codice
            ogg = Pref.oggetto(tema);

            if (ogg != null) {
                if (ogg instanceof Date) {
                    data = (Date)ogg;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Restituisce il valore corrente sotto forma di intero.
     *
     * @param tema oggetto della Enumeration
     *
     * @return valore solo se il tipo dati è corretto
     */
    protected static int comboInt(PrefGruppi tema) {
        /* variabili e costanti locali di lavoro */
        Integer intero = 0;
        Object ogg;

        try { // prova ad eseguire il codice
            ogg = Pref.oggetto(tema);

            if (ogg != null) {
                if (ogg instanceof Integer) {
                    intero = (Integer)ogg;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return intero;
    }


    /**
     * Restituisce il valore corrente sotto forma di stringa.
     *
     * @param tema oggetto della Enumeration
     *
     * @return valore solo se il tipo dati è corretto
     */
    protected static String comboStr(PrefGruppi tema) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Integer intero = 0;
        Object ogg;
        ArrayList<PrefTipo> lista;
        int dim;

        try { // prova ad eseguire il codice
            ogg = Pref.oggetto(tema);

            if (ogg != null) {
                if (ogg instanceof Integer) {
                    intero = (Integer)ogg;
                    if (intero > 0) {
                        intero--;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            lista = tema.getWrap().getLista();

            if (lista != null) {
                dim = lista.size();
                if (dim > 0) {
                    if (intero < dim) {
                        stringa = lista.get(intero).toString();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Restituisce il valore corrente sotto forma di oggetto.
     *
     * @param tema oggetto della Enumeration
     *
     * @return valore solo se il tipo dati è corretto
     */
    protected static Object comboOgg(PrefGruppi tema) {
        /* variabili e costanti locali di lavoro */
        Object oggetto = null;
        Integer intero = 0;
        Object ogg;
        ArrayList<PrefTipo> lista;
        int dim;

        try { // prova ad eseguire il codice
            ogg = Pref.oggetto(tema);

            if (ogg != null) {
                if (ogg instanceof Integer) {
                    intero = (Integer)ogg;
                    if (intero > 0) {
                        intero--;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            lista = tema.getWrap().getLista();

            if (lista != null) {
                dim = lista.size();
                if (dim > 0) {
                    if (intero < dim) {
                        oggetto = lista.get(intero).getValore();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return oggetto;
    }


    /**
     * Restituisce il singolo gruppo di preferenze.
     *
     * @param nome del gruppo
     *
     * @return lista degli oggetti preferenza
     */
    public static ArrayList<PrefGruppi> getGruppo(String nome) {
        /* variabili e costanti locali di lavoro */
        ArrayList<PrefGruppi> gruppo = null;
        LinkedHashMap<String, ArrayList<PrefGruppi>> gruppi;

        try { // prova ad eseguire il codice
            gruppi = Pref.getGruppi();

            if (gruppi != null) {
                gruppo = gruppi.get(nome);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return gruppo;
    }


    public static LinkedHashMap<String, ArrayList<PrefGruppi>> getGruppi() {
        return Pref.gruppi;
    }


    private static void setGruppi(LinkedHashMap<String, ArrayList<PrefGruppi>> gruppi) {
        Pref.gruppi = gruppi;
    }


    /**
     * Restituisce una collezione di tutte le preferenze.
     * <p/>
     * La chiave collezione viene costruita col nome del gruppo e della preferenza
     * separati dal punto <br>
     *
     * @return collezione di tutte le preferenze
     */
    public static LinkedHashMap<String, PrefGruppi> getPref() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, PrefGruppi> preferenze = null;
        LinkedHashMap<String, ArrayList<PrefGruppi>> gruppi;
        ArrayList<PrefGruppi> gruppo;
        String chiavePref;

        try { // prova ad eseguire il codice
            gruppi = Pref.getGruppi();

            if (gruppi != null) {

                preferenze = new LinkedHashMap<String, PrefGruppi>();

                /* traverso tutta la collezione */
                for (String sigla : gruppi.keySet()) {
                    gruppo = Pref.getGruppo(sigla);

                    if (gruppo != null) {
                        /* traverso tutta la collezione */
                        for (PrefGruppi pref : gruppo) {
                            chiavePref = sigla + "." + pref;
                            preferenze.put(chiavePref, pref);
                        } // fine del ciclo for-each
                    }// fine del blocco if

                } // fine del ciclo for-each
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return preferenze;
    }


    /**
     * Restituisce una lista delle preferenze di startup.
     * <p/>
     *
     * @return lista di preferenze
     */
    public static ArrayList<PrefGruppi> getPrefStartup() {
        /* variabili e costanti locali di lavoro */
        ArrayList<PrefGruppi> lista = null;
        ArrayList<PrefGruppi> gruppo;
        LinkedHashMap<String, ArrayList<PrefGruppi>> gruppi;

        try { // prova ad eseguire il codice
            gruppi = Pref.getGruppi();

            if (gruppi != null) {

                lista = new ArrayList<PrefGruppi>();

                /* traverso tutta la collezione */
                for (String sigla : gruppi.keySet()) {
                    gruppo = Pref.getGruppo(sigla);

                    if (gruppo != null) {
                        /* traverso tutta la collezione */
                        for (PrefGruppi pref : gruppo) {
                            if (pref.getWrap().isSetup()) {
                                lista.add(pref);
                            }// fine del blocco if
                        } // fine del ciclo for-each
                    }// fine del blocco if

                } // fine del ciclo for-each
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Restituisce una singola preferenza.
     * <p/>
     *
     * @param nomeGruppo - nome del gruppo
     * @param nomePref   - nome della preferenza
     *
     * @return una preferenza
     */
    public static PrefGruppi getPref(String nomeGruppo, String nomePref) {
        /* variabili e costanti locali di lavoro */
        PrefGruppi preferenza = null;
        boolean continua;
        ArrayList<PrefGruppi> gruppo;

        try { // prova ad eseguire il codice
            gruppo = Pref.getGruppo(nomeGruppo);
            continua = (gruppo != null);

            if (continua) {
                /* traverso tutta la collezione */
                for (PrefGruppi pref : gruppo) {
                    if (pref.toString().equals(nomePref)) {
                        preferenza = pref;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return preferenza;
    }


    /**
     * Chiude l'oggetto Pref.
     * <p/>
     * Chiude gli archivi locale e comune
     */
    public static void close() {

        try {    // prova ad eseguire il codice
            getStoreLocali().close();
            getStoreComuni().close();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    private static PrefStore getStoreLocali() {
        return Pref.storeLocali;
    }


    private static void setStoreLocali(PrefStore prefStore) {
        Pref.storeLocali = prefStore;
    }


    public static PrefStore getStoreComuni() {
        return storeComuni;
    }


    private static void setStoreComuni(PrefStore storeComuni) {
        Pref.storeComuni = storeComuni;
    }


    public static LinkedHashMap<String, Object> getArgomenti() {
        return argomenti;
    }


    private static void setArgomenti(LinkedHashMap<String, Object> argomenti) {
        Pref.argomenti = argomenti;
    }


    /**
     * Classe 'interna'. </p>
     */
    public final static class Arg {

        public static boolean argBool(String chiave) {
            /* variabili e costanti locali di lavoro */
            boolean argomento = false;
            LinkedHashMap<String, Object> mappa;
            Object ogg;

            try { // prova ad eseguire il codice
                mappa = getArgomenti();

                if (mappa != null) {
                    ogg = mappa.get(chiave);
                    if (ogg instanceof Boolean) {
                        argomento = (Boolean)ogg;
                    }// fine del blocco if

                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            return argomento;
        }
    } // fine della classe 'interna'


    /**
     * Classe interna Enumerazione.
     */
    public enum TipoDati implements PrefTipo {

        stringa("stringa", String.class),
        intero("intero", Integer.class),
        doppio("double", Double.class),
        booleano("booleano", Boolean.class),
        data("data", Date.class),
        ora("ora", Time.class),
        time("time", Timestamp.class),
        combo("combo", Integer.class),
        radio("radio", Integer.class),
        area("testoarea", String.class);

        /**
         * sigla
         */
        private String sigla;

        /**
         * classe
         */
        private Class classe;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla  del tipo di dati
         * @param classe del tipo di dati del tipo di dati
         */
        TipoDati(String sigla, Class classe) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setSigla(sigla);
                this.setClasse(classe);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce una lista di tutti gli oggetti della Enumeration.
         *
         * @return arrayList di stringhe
         */
        public static ArrayList<PrefTipo> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<PrefTipo> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<PrefTipo>();

                /* traverso tutta la collezione */
                for (TipoDati ute : TipoDati.values()) {
                    lista.add(ute);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public Class getClasse() {
            return classe;
        }


        private void setClasse(Class classe) {
            this.classe = classe;
        }


        public Object getValore() {
            return null;
        }


        @Override
        public String toString() {
            return getSigla();
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum TipoDb implements PrefTipo {

        mysql("mysql", 2),
        postgres("postgres", 3),
        hsqldb("HSQLDB", 6);


        /**
         * sigla
         */
        private String sigla;

        /**
         * valore
         */
        private int valore;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla  del database
         * @param valore del database
         */
        TipoDb(String sigla, int valore) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setSigla(sigla);
                this.setValore(valore);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce una lista di tutti gli oggetti della Enumeration.
         *
         * @return arrayList di stringhe
         */
        public static ArrayList<PrefTipo> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<PrefTipo> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<PrefTipo>();

                /* traverso tutta la collezione */
                for (TipoDb tipoDb : TipoDb.values()) {
                    lista.add(tipoDb);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public Object getValore() {
            return valore;
        }


        private void setValore(int valore) {
            this.valore = valore;
        }


        @Override
        public String toString() {
            return getSigla();
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum ModoDb implements PrefTipo {

        server("Client-Server", 1),
        alone("Stand-Alone", 2),
        database("Default del database", 0);


        /**
         * sigla
         */
        private String sigla;

        /**
         * valore
         */
        private int valore;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla  del database
         * @param valore del database
         */
        ModoDb(String sigla, int valore) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setSigla(sigla);
                this.setValore(valore);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce una lista di tutti gli oggetti della Enumeration.
         *
         * @return arrayList di stringhe
         */
        public static ArrayList<PrefTipo> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<PrefTipo> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<PrefTipo>();

                /* traverso tutta la collezione */
                for (ModoDb modoDb : ModoDb.values()) {
                    lista.add(modoDb);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public Object getValore() {
            return valore;
        }


        private void setValore(int valore) {
            this.valore = valore;
        }


        @Override
        public String toString() {
            return getSigla();
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum Utente implements PrefTipo {

        prog("Programmatore", 1),
        admin("Amministratore", 2),
        user("Utente", 3),
        host("Ospite", 4);


        /**
         * sigla
         */
        private String sigla;

        /**
         * valore
         */
        private int valore;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla  del database
         * @param valore del database
         */
        Utente(String sigla, int valore) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setSigla(sigla);
                this.setValore(valore);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce una lista di tutti gli oggetti della Enumeration.
         *
         * @return arrayList di stringhe
         */
        public static ArrayList<PrefTipo> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<PrefTipo> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<PrefTipo>();

                /* traverso tutta la collezione */
                for (Utente ute : Utente.values()) {
                    lista.add(ute);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public Object getValore() {
            return valore;
        }


        private void setValore(int valore) {
            this.valore = valore;
        }


        @Override
        public String toString() {
            return getSigla();
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum TipoTavola implements PrefTipo {

        memoria("memoria", 1),
        cached("cached", 2),
        text("text", 3),
        standard("Default del database", 0);

        /**
         * sigla
         */
        private String sigla;

        /**
         * valore
         */
        private int valore;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla  del database
         * @param valore del database
         */
        TipoTavola(String sigla, int valore) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setSigla(sigla);
                this.setValore(valore);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce una lista di tutti gli oggetti della Enumeration.
         *
         * @return arrayList di stringhe
         */
        public static ArrayList<PrefTipo> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<PrefTipo> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<PrefTipo>();

                /* traverso tutta la collezione */
                for (TipoTavola tipoTavola : TipoTavola.values()) {
                    lista.add(tipoTavola);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public Object getValore() {
            return valore;
        }


        private void setValore(int valore) {
            this.valore = valore;
        }


        @Override
        public String toString() {
            return getSigla();
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum Livello implements PrefTipo {

        basso("basso"),
        medio("medio"),
        alto("complesso");

        /**
         * sigla
         */
        private String sigla;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla del database
         */
        Livello(String sigla) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setSigla(sigla);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce una lista di tutti gli oggetti della Enumeration.
         *
         * @return arrayList di stringhe
         */
        public static ArrayList<PrefTipo> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<PrefTipo> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<PrefTipo>();

                /* traverso tutta la collezione */
                for (Livello livello : Livello.values()) {
                    lista.add(livello);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String getSigla() {
            return sigla;
        }


        public Object getValore() {
            return null;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        @Override
        public String toString() {
            return getSigla();
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum Look implements PrefTipo {

        mac("mac"),
        java("java"),
        metal("metal");

        /**
         * sigla
         */
        private String sigla;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla del database
         */
        Look(String sigla) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setSigla(sigla);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce una lista di tutti gli oggetti della Enumeration.
         *
         * @return arrayList di stringhe
         */
        public static ArrayList<PrefTipo> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<PrefTipo> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<PrefTipo>();

                /* traverso tutta la collezione */
                for (Look look : Look.values()) {
                    lista.add(look);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public Object getValore() {
            return null;
        }


        @Override
        public String toString() {
            return getSigla();
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum TipoSel implements PrefTipo {

        corrispondenti("corrispondenti"),
        primo("primo record");

        /**
         * sigla
         */
        private String sigla;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla del database
         */
        TipoSel(String sigla) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setSigla(sigla);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce una lista di tutti gli oggetti della Enumeration.
         *
         * @return arrayList di stringhe
         */
        public static ArrayList<PrefTipo> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<PrefTipo> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<PrefTipo>();

                /* traverso tutta la collezione */
                for (TipoSel tipoSel : TipoSel.values()) {
                    lista.add(tipoSel);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public Object getValore() {
            return null;
        }


        @Override
        public String toString() {
            return getSigla();
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum WebTesto implements PrefTipo {

        plain("text/plain"),
        html("text/html");

        /**
         * sigla
         */
        private String sigla;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla del database
         */
        WebTesto(String sigla) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setSigla(sigla);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce una lista di tutti gli oggetti della Enumeration.
         *
         * @return arrayList di stringhe
         */
        public static ArrayList<PrefTipo> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<PrefTipo> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<PrefTipo>();

                /* traverso tutta la collezione */
                for (WebTesto tipo : WebTesto.values()) {
                    lista.add(tipo);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public Object getValore() {
            return null;
        }


        @Override
        public String toString() {
            return getSigla();
        }
    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum TipoPosta implements PrefTipo {

        normale("TO", Message.RecipientType.TO, "invio normale"),
        copia("CC", Message.RecipientType.CC, "invio con copia"),
        copiaNascosta("BCC", Message.RecipientType.BCC, "copia nascosta");
//        normale("TO", "", "invio normale"),
//        copia("CC", "", "invio con copia"),
//        copiaNascosta("BCC", "", "copia nascosta"),;

        /**
         * sigla
         */
        private String sigla;

        /**
         * valore
         */
        private Object valore;

        /**
         * legenda
         */
        private String legenda;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla   del database
         * @param valore  associato
         * @param legenda a fianco
         */
        TipoPosta(String sigla, Object valore, String legenda) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setSigla(sigla);
                this.setLegenda(legenda);
                this.setValore(valore);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce una lista di tutti gli oggetti della Enumeration.
         *
         * @return arrayList di stringhe
         */
        public static ArrayList<PrefTipo> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<PrefTipo> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<PrefTipo>();

                /* traverso tutta la collezione */
                for (TipoPosta tipo : TipoPosta.values()) {
                    lista.add((PrefTipo)tipo);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public Object getValore() {
            return valore;
        }


        private void setValore(Object valore) {
            this.valore = valore;
        }


        public String getLegenda() {
            return legenda;
        }


        private void setLegenda(String legenda) {
            this.legenda = legenda;
        }


        @Override
        public String toString() {
            return getSigla();
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum Intervallo implements PrefTipo {

        giorno("giorno", 1),
        settimana("settimana", 7),
        mese("mese", 30);

        /**
         * sigla
         */
        private String sigla;

        /**
         * valore
         */
        private int valore;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla  del database
         * @param valore del database
         */
        Intervallo(String sigla, int valore) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setSigla(sigla);
                this.setValore(valore);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce una lista di tutti gli oggetti della Enumeration.
         *
         * @return arrayList di stringhe
         */
        public static ArrayList<PrefTipo> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<PrefTipo> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<PrefTipo>();

                /* traverso tutta la collezione */
                for (Intervallo intervallo : Intervallo.values()) {
                    lista.add(intervallo);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public Object getValore() {
            return valore;
        }


        private void setValore(int valore) {
            this.valore = valore;
        }


        @Override
        public String toString() {
            return getSigla();
        }

    }// fine della classe

} // fine della classe
