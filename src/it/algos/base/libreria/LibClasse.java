/**
 * Title:     LibClasse
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      28-feb-2004
 */
package it.algos.base.libreria;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.libro.Libro;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.pref.Pref;
import it.algos.base.campo.base.CampoBase;
import it.algos.base.campo.base.Campo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Repository per costruire istanze di varie classi. Repository di funzionalità per costruire
 * istanze di varie classi. </p> Tutti i metodi sono statici <br> I metodi non hanno modificatore
 * così sono visibili all'esterno del package solo utilizzando l'interfaccia unificata
 * <b>Lib</b><br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 28-feb-2004 ore 8.50.10
 */
public abstract class LibClasse {


    /**
     * Crea una istanza di una qualsiasi classe coi parametri. </p> Crea un array di parametri per
     * il costruttore <br> Recupera dalla classe il costruttore corrispondente <br> Crea un array di
     * valori per il costruttore <br> Invoca il costruttore coi necessari parametri <br>
     *
     * @param path completo della classe da creare
     * @param classiValori dei parametri
     *
     * @return l'istanza appena creata (occorre il casting)
     */
    static Object crea(String path, Parametro[] classiValori) {
        /* variabili locali di lavoro */
        boolean continua;
        Object unOggetto = null;
        Parametro parametro;
        Class classe;
        Object valore;
        Class[] parametri;
        String tag = "class";
        int pos;
        Class unaClasse = null;
        Constructor costruttore;
        Object[] valori;
        int dim;

        try { // prova ad eseguire il codice
            if (classiValori != null) {

                /* recupera dimensione */
                dim = classiValori.length;

                continua = dim > 0;

                /* pulisce il path dalla scritta class */
                if (continua) {
                    pos = path.indexOf(tag);
                    if (pos != -1) {
                        pos += tag.length();
                        path = path.substring(pos);
                        path = path.trim();
                    }// fine del blocco if

                    /* crea la classe dal nome */
                    unaClasse = Class.forName(path);

                }// fine del blocco if


                if (continua) {

                    /* crea l'array dei parametri */
                    parametri = new Class[dim];
                    for (int k = 0; k < dim; k++) {
                        parametro = classiValori[k];
                        classe = parametro.getClasse();
                        parametri[k] = classe;
                    } // fine del ciclo for

                    /* recupera il costruttore interessato */
                    costruttore = unaClasse.getConstructor(parametri);

                    /* crea un array di valori per il costruttore */
                    valori = new Object[dim];
                    for (int k = 0; k < dim; k++) {
                        parametro = classiValori[k];
                        valore = parametro.getValore();
                        valori[k] = valore;
                    } // fine del ciclo for

                    /* crea una istanza (oggetto) */
                    unOggetto = costruttore.newInstance(valori);

                }// fine del blocco if
            }// fine del blocco if

        } catch (ClassNotFoundException unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } catch (InvocationTargetException unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unOggetto;
    }


    /**
     * Crea una istanza di una qualsiasi classe con un parametro. </p> Crea un array di parametri
     * per il costruttore <br> Recupera dalla classe il costruttore corrispondente <br> Crea un
     * array di valori per il costruttore <br> Invoca il costruttore coi necessari parametri <br>
     *
     * @param path completo della classe da creare
     * @param classeValore del parametro
     *
     * @return l'istanza appena creata (occorre il casting)
     */
    static Object crea(String path, Parametro classeValore) {
        /* variabili locali di lavoro */
        Object unOggetto = null;
        Parametro[] classiValori;

        try { // prova ad eseguire il codice
            /* crea un array di classe e valore */
            classiValori = new Parametro[1];
            classiValori[0] = classeValore;

            /* invoca il metodo sovrascritto */
            unOggetto = LibClasse.crea(path, classiValori);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unOggetto;
    }


    /**
     * Crea una istanza di una qualsiasi classe con un parametro. </p> Crea un array di parametri
     * per il costruttore <br> Recupera dalla classe il costruttore corrispondente <br> Crea un
     * array di valori per il costruttore <br> Invoca il costruttore coi necessari parametri <br>
     *
     * @param path completo della classe da creare
     * @param classe del parametro
     * @param valore del parametro
     *
     * @return l'istanza appena creata (occorre il casting)
     */
    static Object crea(String path, Class classe, Object valore) {
        /* variabili locali di lavoro */
        Object unOggetto = null;
        Parametro classeValore;

        try { // prova ad eseguire il codice
            /* crea un wrapper di classe e valore */
            classeValore = new Parametro(classe, valore);

            /* invoca il metodo sovrascritto */
            unOggetto = LibClasse.crea(path, classeValore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unOggetto;
    }


    /**
     * Crea una istanza di una qualsiasi classe. </p> Invoca il metodo delegato <br>
     *
     * @param path nome completo della classe da creare
     *
     * @return l'istanza appena creata (occorre il casting)
     */
    static Object crea(String path) {
        /* invoca il metodo sovrascritto */
        return LibClasse.crea(path, null, null);
    }


    /**
     * Crea una istanza di una qualsiasi classe. </p> Invoca il metodo delegato <br>
     *
     * @param classe con cui creare l'oggetto (istanza)
     *
     * @return l'istanza appena creata (occorre il casting)
     */
    static Object crea(Class classe) {
        /* variabili e costanti locali di lavoro */
        Object istanza = null;
        Constructor costruttore;

        try { // prova ad eseguire il codice

            /* recupera il costruttore interessato */
            costruttore = classe.getConstructor();

            /* crea una istanza (oggetto) generica */
            istanza = costruttore.newInstance();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return istanza;
    }


    /**
     * Crea una istanza di classe Modulo, col parametro AlberoNodo. </p> Crea un array di parametri
     * per il costruttore <br> Recupera dalla classe il costruttore corrispondente <br> Crea un
     * array di valori per il costruttore <br> Invoca il costruttore coi necessari parametri <br>
     *
     * @param unPathCompleto nome completo della classe da creare
     * @param unNodo parametro del costruttore di Modulo
     *
     * @return l'istanza Modulo appena creata
     */
    static Modulo modulo(String unPathCompleto, AlberoNodo unNodo) {
        /* variabili locali di lavoro */
        Modulo unModulo = null;
        Class unaClasse;
        Object unOggetto;
        Class[] parametri;
        Constructor costruttore;
        AlberoNodo[] valori;

        try { // prova ad eseguire il codice
            /* crea la classe dal nome */
            unaClasse = Class.forName(unPathCompleto);

            /* crea l'array dei parametri (un solo parametro) */
            parametri = new Class[1];
            parametri[0] = AlberoNodo.class;

            /* recupera il costruttore interessato */
            costruttore = unaClasse.getConstructor(parametri);

            /* crea un array di valori per il costruttore */
            valori = new AlberoNodo[1];
            valori[0] = unNodo;

            /* crea una istanza (oggetto) di Modulo e forza il casting
             * per avere il tipo di oggetto desiderato */
            unOggetto = costruttore.newInstance(valori);
            unModulo = (Modulo)unOggetto;
        } catch (ClassNotFoundException unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unModulo;
    }


    /**
     * Ritorna il nome di una classe, senza il percorso.
     * <p/>
     *
     * @param classe l'oggetto Classe
     *
     * @return il nome della classe
     */
    static String getNomeClasse(Class classe) {
        /* variabili e costanti locali di lavoro */
        String nome = null;
        List lista;

        try {    // prova ad eseguire il codice
            nome = classe.getName();
            lista = Lib.Array.creaLista(nome, '.');
            nome = (String)lista.get(lista.size() - 1);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nome;
    }


    /**
     * Recupera un argomento di tipo intero.
     * <p/>
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     * @param pos argomento da esaminare
     * @param standard valore di default
     *
     * @return argomento
     */
    static int argInt(String[] argomenti, int pos, Object standard) {
        /* variabili e costanti locali di lavoro */
        int intVal = 0;
        boolean continua;
        String strVal;

        try {    // prova ad eseguire il codice
            continua = (standard instanceof Integer);

            if (continua) {
                intVal = (Integer)standard;
            }// fine del blocco if

            if (continua) {
                pos--;
                continua = (argomenti.length > pos);
            }// fine del blocco if

            if (continua) {
                strVal = argomenti[pos];
                try { // prova ad eseguire il codice
                    intVal = Integer.decode(strVal);
                } catch (Exception unErrore) { // intercetta l'errore
                }// fine del blocco try-catch
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return intVal;
    }


    /**
     * Recupera un argomento di tipo booleano.
     * <p/>
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     * @param pos argomento da esaminare
     * @param standard valore di default
     *
     * @return argomento
     */
    static boolean argBool(String[] argomenti, int pos, Object standard) {
        /* variabili e costanti locali di lavoro */
        boolean boolVal = false;
        boolean continua;
        String strVal;

        try {    // prova ad eseguire il codice
            continua = (standard instanceof Boolean);

            if (continua) {
                boolVal = (Boolean)standard;
            }// fine del blocco if

            if (continua) {
                pos--;
                continua = (argomenti.length > pos);
            }// fine del blocco if

            if (continua) {
                strVal = argomenti[pos];
                if ((strVal.equals("true") || (strVal.equals("vero")))) {
                    boolVal = true;
                }// fine del blocco if
                if ((strVal.equals("false") || (strVal.equals("falso")))) {
                    boolVal = false;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return boolVal;
    }


    /**
     * Recupera un argomento di tipo stringa.
     * <p/>
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     * @param pos argomento da esaminare
     * @param standard valore di default
     *
     * @return argomento
     */
    static String argStr(String[] argomenti, int pos, Object standard) {
        /* variabili e costanti locali di lavoro */
        String strVal = "";
        boolean continua;

        try {    // prova ad eseguire il codice
            continua = (standard instanceof String);

            if (continua) {
                strVal = (String)standard;
            }// fine del blocco if

            if (continua) {
                pos--;
                continua = (argomenti.length > pos);
            }// fine del blocco if

            if (continua) {
                strVal = argomenti[pos];
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return strVal;
    }


    /**
     * Inserisce i parametri in una mappa globale di chiave/valore.
     * <p/>
     * Esegue un ciclo per tutti i valori dei parametri <br> Il discriminante per individuare la
     * chiave è il carattere = <br> Elabora ogni valore trovato e cerca di estrarne: un intero
     * oppure un valore booleano valido. Gli altri valori restano stringa <br>
     *
     * @param parametri in ingresso
     */
    private static void setPar(String[] parametri) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String tag = "=";
        String tra = "-";
        int pos;
        String chiave;
        String valore;
        Integer intero;
        LinkedHashMap<String, Object> mappa = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (parametri != null) && (parametri.length > 0);

            if (continua) {
                mappa = Pref.getArgomenti();
                continua = (mappa != null);
            }// fine del blocco if

            /* traverso tutta la collezione */
            if (continua) {
                for (String str : parametri) {
                    continua = true;
                    str = str.trim();
                    if (str.startsWith(tra)) {
                        str = str.substring(tra.length());
                    }// fine del blocco if

                    pos = str.indexOf(tag);
                    if (pos != -1) {
                        chiave = str.substring(0, pos);
                        valore = str.substring(pos + 1);
                    } else {
                        valore = "";
                        chiave = str;
                    }// fine del blocco if-else

                    try { // prova ad eseguire il codice
                        intero = Integer.decode(valore);
                        mappa.put(chiave, intero);
                        continua = false;
                    } catch (Exception unErrore) { // intercetta l'errore
                    }// fine del blocco try-catch

                    if (continua) {
                        if (valore.equals("vero")) {
                            mappa.put(chiave, true);
                            continua = false;
                        }// fine del blocco if
                        if (valore.equals("Vero")) {
                            mappa.put(chiave, true);
                            continua = false;
                        }// fine del blocco if
                        if (valore.equals("true")) {
                            mappa.put(chiave, true);
                            continua = false;
                        }// fine del blocco if
                        if (valore.equals("True")) {
                            mappa.put(chiave, true);
                            continua = false;
                        }// fine del blocco if

                        if (valore.equals("falso")) {
                            mappa.put(chiave, false);
                            continua = false;
                        }// fine del blocco if
                        if (valore.equals("Falso")) {
                            mappa.put(chiave, false);
                            continua = false;
                        }// fine del blocco if
                        if (valore.equals("false")) {
                            mappa.put(chiave, false);
                            continua = false;
                        }// fine del blocco if
                        if (valore.equals("False")) {
                            mappa.put(chiave, false);
                            continua = false;
                        }// fine del blocco if
                    }// fine del blocco if

                    if (continua) {
                        if (Lib.Testo.isValida(valore)) {
                            mappa.put(chiave, valore);
                        } else {
                            mappa.put(chiave, true);
                        }// fine del blocco if-else
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera gli argomenti da una stringa di testo e costruisce una matrice di argomenti
     * <p/>
     * Gli argomenti possono essere separati da:<ul> <li> virgola </li> <li> punto e virgola </li>
     * <li> & (e commerciale) </li> <li> - (trattino preceduto da spazio) </li> </ul>
     * <p/>
     * Se gli argomenti vengono riconosciuti da Java, sono già separati negli elementi della matrice
     * <br> Se non riconosciuti, arrivano tutti insieme nel primo elemento della matrice e vengono
     * correttamente separati <br>
     * <p/>
     * Se gli elementi della matrice sono più di uno, si suppone che siano argomenti validi <br>
     *
     * @param argomenti stringa dei parametri in ingresso
     *
     * @return matrice degli argomenti (chiave e valore)
     */
    public static String[] getArg(String argomenti) {
        /* variabili e costanti locali di lavoro */
        String[] parti = null;
        boolean continua;
        String sep = "";
        String vir = ",";
        String pun = ";";
        String com = "&";
        String tra = " -";
        String traNoSpaz = "-";
        String tag = "=";
        String vero = "true";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            argomenti = argomenti.trim();
            continua = (Lib.Testo.isValida(argomenti));

            /* considera tutte le possibilità di suddivisione */
            if (continua) {
                if (argomenti.indexOf(vir) != -1) {
                    sep = vir;
                } else {
                    if (argomenti.indexOf(pun) != -1) {
                        sep = pun;
                    } else {
                        if (argomenti.indexOf(com) != -1) {
                            sep = com;
                        } else {
                            if (argomenti.indexOf(traNoSpaz) != -1) {
                                sep = tra;
                                if (argomenti.startsWith(traNoSpaz)) {
                                    argomenti = argomenti.substring(traNoSpaz.length());
                                }// fine del blocco if
                            }// fine del blocco if
                        }// fine del blocco if-else
                    }// fine del blocco if-else
                }// fine del blocco if-else
            }// fine del blocco if

            /* suddivide (col primo tipo di suddivisione trovato) */
            if (continua) {
                continua = (Lib.Testo.isValida(sep));
            }// fine del blocco if
            if (continua) {
                parti = argomenti.split(sep);
                continua = (parti != null) && (parti.length > 0);
            }// fine del blocco if

            /* controlla eventuali argomenti senza valore
             * si suppone che siano booleani e che manchi l'esplicito valore vero (true) */
            if (continua) {
                for (int k = 0; k < parti.length; k++) {
                    if (parti[k].indexOf(tag) == -1) {
                        parti[k] = parti[k].trim();
                        parti[k] += tag;
                        parti[k] += vero;
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

            /* c'era un solo argomento (forse) */
            if (!continua) {
                if (Lib.Testo.isValida(argomenti)) {
                    parti = new String[1];
                    parti[0] = argomenti.trim();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return parti;
    }


    /**
     * Elabora gli argomenti/parametri in ingresso al programma.
     * <p/>
     * Se gli argomenti vengono riconosciuti da Java, sono già separati negli elementi della matrice
     * <br> Se non riconosciuti, arrivano tutti insieme nel primo elemento della matrice e vengono
     * correttamente separati <br>
     * <p/>
     * Trasforma l'array di argomenti in una stringa unica <br> Trasforma la stringa in un array di
     * parametri chiave/valore <br> Inserisce i parametri in una mappa globale di chiave/valore
     * <br>
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    static void setArg(String[] argomenti) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String[] parametri;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (argomenti != null) && (argomenti.length > 0);

            if (continua) {
                if (argomenti.length == 1) {
                    parametri = getArg(argomenti[0]);
                    LibClasse.setPar(parametri);
                } else {
                    LibClasse.setPar(argomenti);
                }// fine del blocco if-else
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla la validità dei parametri passati.
     * <p/>
     * Ogni parametro viene convalidato in relazione al suo tipo di appartenenza <br>
     * <p/>
     *
     * @param parametri in ingresso al metodo
     *
     * @return vero se tutti i parametri sono validi
     */
    static boolean isValidi(Object... parametri) {
        /* variabili e costanti locali di lavoro */
        boolean validi = true;
        boolean continua;
        TipoClasse tipo;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (parametri != null) && (parametri.length > 0);

            if (continua) {
                for (Object ogg : parametri) {
                    if (ogg == null) {
                        validi = false;
                        break;
                    }// fine del blocco if

                    tipo = TipoClasse.get(ogg);

                    if (tipo == null) {
                        validi = false;
                        break;
                    }// fine del blocco if

                    switch (tipo) {
                        case stringa:
                            if (validi && Lib.Testo.isValida(ogg)) {
                                validi = true;
                            } else {
                                validi = false;
                            }// fine del blocco if-else
                            break;
                        case intero:
                            int num;
                            num = Integer.decode(ogg.toString());
                            if (validi && num > 0) {
                                validi = true;
                            } else {
                                validi = false;
                                continue;
                            }// fine del blocco if-else
                            break;
                        case bool:
                            validi = true;
                            break;
                        case data:
                            Date data;
                            data = Libreria.getDate(ogg);
                            if (validi && Lib.Data.isValida(data)) {
                                validi = true;
                            } else {
                                validi = false;
                                continue;
                            }// fine del blocco if-else
                            break;
                        case array:
                            ArrayList lista;
                            lista = (ArrayList)ogg;
                            if (validi && lista != null && lista.size() > 0) {
                                validi = true;
                            } else {
                                validi = false;
                                continue;
                            }// fine del blocco if-else
                            break;
                        case modulo:
                            if (ogg != null) {
                                validi = true;
                            } else {
                                validi = false;
                            }// fine del blocco if-else
                            break;
                        case pannello:
                        case pannello2:
                        case dialogo:
                            if (ogg != null) {
                                validi = true;
                            } else {
                                validi = false;
                            }// fine del blocco if-else
                            break;
                        case libro:
                            if (ogg != null) {
                                validi = true;
                            } else {
                                validi = false;
                            }// fine del blocco if-else
                            break;
                        case campo:
                        case campo2:
                            if (ogg != null) {
                                validi = true;
                            } else {
                                validi = false;
                            }// fine del blocco if-else
                            break;
                        default: // caso non definito
                            validi = false;
                            break;
                    } // fine del blocco switch

                } // fine del ciclo for-each

            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return validi;
    }


    /**
     * Classe interna Enumerazione.
     */
    public enum TipoClasse {

        stringa(String.class),
        intero(Integer.class),
        bool(Boolean.class),
        array(ArrayList.class),
        data(Date.class),
        modulo(ModuloBase.class),
        pannello(PannelloBase.class),
        pannello2(PannelloFlusso.class),
        dialogo(PannelloFlusso.class),
        libro(Libro.class),
        campo(Campo.class),
        campo2(CampoBase.class),
        ;

        private Class classe;


        /**
         * Costruttore completo con parametri.
         *
         * @param classe da riconoscere
         */
        TipoClasse(Class classe) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setClasse(classe);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static TipoClasse get(Class classe) {
            /* variabili e costanti locali di lavoro */
            TipoClasse tipo = null;
            boolean continua;

            try { // prova ad eseguire il codice
                continua = (classe != null);

                if (continua) {
                    for (TipoClasse tipoClasse : TipoClasse.values()) {
                        if (tipoClasse.getClasse() == classe) {
                            tipo = tipoClasse;
                            break;
                        }// fine del blocco if
                    } // fine del ciclo for-each
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return tipo;
        }


        public static TipoClasse get(Object ogg) {
            /* variabili e costanti locali di lavoro */
            TipoClasse tipo = null;
            boolean continua;
            Class classe = null;

            try { // prova ad eseguire il codice
                continua = (ogg != null);

                if (continua) {
                    classe = ogg.getClass();
                    continua = (classe != null);
                }// fine del blocco if

                if (continua) {
                    tipo = TipoClasse.get(classe);
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return tipo;
        }


        public Class getClasse() {
            return classe;
        }


        public void setClasse(Class classe) {
            this.classe = classe;
        }
    }// fine della classe

}// fine della classe
