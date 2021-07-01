/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      23-gen-2006
 */
package it.algos.base.libreria;

import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.Eventi;

import javax.swing.event.EventListenerList;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Repository di funzionalità per la gestione degli eventi. </p> Tutti i metodi sono statici <br> I
 * metodi non hanno modificatore così sono visibili all'esterno del package solo utilizzando
 * l'interfaccia unificata <b>Lib</b><br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 23-gen-2006 ore 8.21.02
 */
public abstract class LibEventi {

    /**
     * Aggiunge/rimuove un listener alla/dalla lista.
     * <p/>
     * Controlla che il listener appartenga/non appartenga all'enumerazione <br>
     *
     * @param valori enumeration interna alla classe
     * @param lista dei listener degli eventi del componente
     * @param listener interfaccia
     * @param operazione codifica interna di selezione
     */
    private static void operazione(Object[] valori,
                                   EventListenerList lista,
                                   BaseListener listener,
                                   Operazione operazione) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Object[] oggetti;
        Class classeLis;
        Class classeAz;
        Class classe;

        try { // prova ad eseguire il codice
            /* controlla che la lista non sia nulla */
            continua = (lista != null);

            /* controlla che esista/non esista */
            if (continua) {
                oggetti = lista.getListenerList();

                switch (operazione) {
                    case add:
                        continua = !Lib.Array.isEsiste(oggetti, listener);
                        break;
                    case remove:
                        continua = Lib.Array.isEsiste(oggetti, listener);
                        break;
                    default: // caso non definito
                        continua = false;
                        break;
                } // fine del blocco switch
            }// fine del blocco if

            if (continua) {
                /* spazzola tutta l'enumerazione */
                for (Object evento : valori) {
                    classe = listener.getClass();
                    classeLis = ((Eventi)evento).getListener();
                    classeAz = ((Eventi)evento).getAzione();

                    while (classe != Object.class) {
                        if (classe == classeAz) {
                            switch (operazione) {
                                case add:
                                    lista.add(classeLis, listener);
                                    break;
                                case remove:
                                    lista.remove(classeLis, listener);
                                    break;
                                default: // caso non definito
                                    break;
                            } // fine del blocco switch
                            break;
                        }// fine del blocco if
                        classe = classe.getSuperclass();
                    }// fine del blocco while

                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            int a = 87;
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un listener alla lista.
     * <p/>
     * Controlla che il listener appartenga/non appartenga all'enumerazione <br>
     *
     * @param valori enumeration interna alla classe
     * @param lista dei listener degli eventi del componente
     * @param listener interfaccia
     */
    static void add(Object[] valori, EventListenerList lista, BaseListener listener) {
        LibEventi.operazione(valori, lista, listener, LibEventi.Operazione.add);
    }


    /**
     * Rimuove un listener dalla lista.
     * <p/>
     * Controlla che il listener appartenga/non appartenga all'enumerazione <br>
     *
     * @param valori enumeration interna alla classe
     * @param lista dei listener degli eventi del componente
     * @param listener interfaccia
     */
    static void remove(Object[] valori, EventListenerList lista, BaseListener listener) {
        LibEventi.operazione(valori, lista, listener, LibEventi.Operazione.remove);
    }


    /**
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati per questo tipo di evento <br> L'evento viene
     * creato al momento <br> È responsabilità della classe invocare questo metodo quando si creano
     * le condizioni per generare l'evento <br>
     *
     * @param lista dei listener degli eventi del componente
     * @param evento da lanciare
     * @param param lista variabile di parametri (classe, valore)
     *
     * @see javax.swing.event.EventListenerList
     */
    static void fire(EventListenerList lista, Eventi evento, Parametro... param) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Object[] oggetti = null;
        Object istanza;
        Object azione;
        String nomeClasse;
        String nomeMetodo;
        Method metodo;
        ArrayList<Object> oggPari;
        ArrayList<Object> oggDispari;

        try {    // prova ad eseguire il codice
            continua = (lista != null);

            if (continua) {
                oggetti = lista.getListenerList();
                continua = (oggetti != null);
            }// fine del blocco if

            if (oggetti.length > 16) {
                int a = 87;
            }// fine del blocco if

            if (continua) {
                nomeClasse = evento.getEvento().toString();
                nomeMetodo = evento.getMetodo();

                /* todo - In teoria la lista di listener, dovrebbe essere univoca */
                /* todo - Univoca la coppia oggetti e azioni */
                /* todo - Per risolvere il bug, controllo qui che venga lanciato
                 * todo - un solo evento per ogni coppia */
                /* todo - Pomaro, 30-10-06/gac (dicesi patch) */
                oggPari = new ArrayList<Object>();
                oggDispari = new ArrayList<Object>();
                //@todo comunque non funziona lo stesso !

                for (int k = oggetti.length - 2; k >= 0; k -= 2) {

                    if (oggetti[k] == evento.getListener()) {

                        istanza = LibClasse.crea(nomeClasse, param);

                        metodo = evento.getAzione().getMethod(nomeMetodo, evento.getEvento());

                        azione = oggetti[k + 1];

                        if ((Lib.Array.isNonEsiste(oggPari, oggetti[k])) || (Lib.Array.isNonEsiste(
                                oggDispari,
                                oggetti[k + 1]))) {
                            metodo.invoke(azione, istanza);
                            oggPari.add(oggetti[k]);
                            oggDispari.add(oggetti[k + 1]);
                        }// fine del blocco if

                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }

//    /**
//     * Avvisa tutti i listener.
//     * <p/>
//     * Avvisa tutti i listener che si sono registrati
//     * per questo tipo di evento <br>
//     * L'evento viene creato al momento <br>
//     * È responsabilità della classe invocare questo metodo quando
//     * si creano le condizioni per generare l'evento <br>
//     *
//     * @param lista  dei listener degli eventi del componente
//     * @param evento da lanciare
//     * @param classe che richiede il lancio dell'evento
//     * @param valore istanza della classe che richiede il lancio dell'evento
//     *
//     * @see javax.swing.event.EventListenerList
//     */
//    static void fire(EventListenerList lista,
//                     Eventi evento,
//                     Class classe,
//                     Object valore) {
//        /* invoca il metodo delegato della classe */
//        LibEventi.fire(lista, evento, classe, valore, new Parametro(classe,valore));
//
//    }


    /**
     * Classe interna Enumerazione.
     */
    private enum Operazione {

        add(),
        remove()

    }// fine della classe

}// fine della classe
