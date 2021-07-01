/**
 * Title:     LibCampo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      03-feb-2005
 */
package it.algos.base.libreria;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.campo.lista.CampoLista;
import it.algos.base.costante.CostanteCarattere;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.wrapper.CampoValore;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;


/**
 * Repository di funzionalità per la gestione dei Campi.
 * <p/>
 * Tutti i metodi sono statici <br> I metodi non hanno modificatore così sono visibili all'esterno
 * del package solo utilizzando l'interfaccia unificata <b>Lib</b><br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 03-feb-2005 ore 12.04.04
 */
public abstract class LibCampo {

    /**
     * Ritorna la chiave completa di un campo.
     *
     * @param nomeCampo il nome interno del campo
     * @param modulo il modulo di riferimento del campo
     *
     * @return la chiave del campo
     */
    static String chiaveCampo(String nomeCampo, Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        String chiave = "";
        String nomeModulo = "";

        try { // prova ad eseguire il codice
            if (modulo != null) {
                nomeModulo = modulo.getNomeChiave() + CostanteCarattere.PUNTO;
            }// fine del blocco if

            chiave = nomeModulo + nomeCampo;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Ritorna la chiave completa di un campo.
     *
     * @param nomeCampo il nome interno del campo
     *
     * @return la chiave del campo
     */
    static String chiaveCampo(String nomeCampo) {
        return chiaveCampo(nomeCampo, null);
    }


    /**
     * Ritorna un campo dal Progetto data la chiave.
     * <p/>
     *
     * @param chiaveCampo la chiave del campo (modulo.campo)
     *
     * @return il campo corrispondente
     */
    static Campo getCampo(String chiaveCampo) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        StringTokenizer st;
        String nomeModulo;
        String nomeCampo;
        Modulo modulo;

        try { // prova ad eseguire il codice
            st = new StringTokenizer(chiaveCampo, ".");
            nomeModulo = st.nextToken();
            nomeCampo = st.nextToken();
            modulo = Progetto.getModulo(nomeModulo);
            if (modulo != null) {
                campo = modulo.getCampo(nomeCampo);
            } else {
                throw new Exception("Modulo non trovato");
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Ritorna una collezione contenente solo i campi fisici.
     * <p/>
     * Data una collezione di campi, restituisce un'altra collezione di campi contenente solo i
     * campi fisici <br>
     *
     * @param collezioneCampi completa
     *
     * @return collezione con i soli campi fisici
     */
    static LinkedHashMap<String, Campo> filtraCampiFisici(LinkedHashMap<String, Campo> collezioneCampi) {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, Campo> campi = null;
        CampoDB campoDB;

        try { // prova ad eseguire il codice
            /* crea la nuova collezione */
            campi = new LinkedHashMap<String, Campo>();

            /* traversa tutta la collezione e tiene solo i campi fisici */
            for (String chiave : collezioneCampi.keySet()) {
                campoDB = collezioneCampi.get(chiave).getCampoDB();
                if (campoDB.isCampoFisico()) {
                    campi.put(chiave, collezioneCampi.get(chiave));
                } /* fine del blocco if */
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


    /**
     * Ritorna una collezione contenente solo i campi fisici e non fissi (algos).
     * <p/>
     * Data una collezione di campi, restituisce un'altra collezione di campi contenente solo i
     * campi fisici e non fissi (algos) <br>
     *
     * @param collezioneCampi completa
     *
     * @return collezione con i soli campi fisici
     */
    static LinkedHashMap<String, Campo> filtraCampiFisiciNoAlgos(LinkedHashMap<String, Campo> collezioneCampi) {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, Campo> campi = null;
        LinkedHashMap<String, Campo> campiFisici;
        CampoDB campoDB;

        try { // prova ad eseguire il codice
            /* crea la nuova collezione */
            campi = new LinkedHashMap<String, Campo>();

            /* invoca il metodo delegato per recuperare i campi fisici */
            campiFisici = Lib.Camp.filtraCampiFisici(collezioneCampi);

            /* traversa tutta la collezione e tiene solo i campi non fissi */
            for (String chiave : campiFisici.keySet()) {
                campoDB = collezioneCampi.get(chiave).getCampoDB();
                if (campoDB.isFissoAlgos()) {
                    campi.put(chiave, collezioneCampi.get(chiave));
                } /* fine del blocco if */
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


    /**
     * Ritorna una collezione contenente solo i campi visibili.
     * <p/>
     * Data una collezione di campi, restituisce un'altra collezione di campi contenente solo i
     * campi visibili nella lista di default <br>
     *
     * @param collezioneCampi completa
     *
     * @return collezione con i soli campi visibili nella lista di default
     */
    static LinkedHashMap<String, Campo> filtraCampiVisibili(LinkedHashMap<String, Campo> collezioneCampi) {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, Campo> campi = null;
        CampoLista campoLista;

        try { // prova ad eseguire il codice
            /* crea la nuova collezione */
            campi = new LinkedHashMap<String, Campo>();

            /* traversa tutta la collezione e tiene solo i campi fisici */
            for (String chiave : collezioneCampi.keySet()) {
                campoLista = collezioneCampi.get(chiave).getCampoLista();
                if (campoLista.isVisibileVistaDefault()) {
                    campi.put(chiave, collezioneCampi.get(chiave));
                } /* fine del blocco if */
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


    /**
     * Recupera un campo-valore da una lista.
     * <p/>
     *
     * @param lista di elementi campo-valore
     * @param campo da recuperare
     *
     * @return il campo-valore richiesto
     */
    static CampoValore getCampoValore(ArrayList<CampoValore> lista, Campo campo) {
        /* variabili e costanti locali di lavoro */
        CampoValore campoValore = null;
        boolean continua;
        Campo unCampo;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = ((campo != null) && (lista != null));


            if (continua) {
                /* traverso tutta la collezione */
                for (CampoValore cv : lista) {
                    unCampo = cv.getCampo();
                    if (unCampo != null) {
                        if (unCampo.equals(campo)) {
                            campoValore = cv;
                            break;
                        }// fine del blocco if
                    }// fine del blocco if

                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campoValore;
    }


    /**
     * Si assicura che una lista di CampiValore contenga un dato campoValore con un valore valido
     * (non vuoto).
     * <p/>
     * Se manca o il valore è vuoto, lo aggiunge ora con il valore fornito.
     *
     * @param lista di elementi campo-valore
     * @param campo da recuperare
     * @param valore da utilizzare nel caso che il CampoValore venga creato ora.
     *
     * @return il campo-valore richiesto o creato
     */
    static CampoValore chkCampoValoreValido(ArrayList<CampoValore> lista,
                                            Campo campo,
                                            Object valore) {
        /* variabili e costanti locali di lavoro */
        CampoValore cv = null;
        boolean crea = false;

        try { // prova ad eseguire il codice

            cv = getCampoValore(lista, campo);
            if (cv == null) {
                crea = true;
            } else {
                Object val = cv.getValore();
                Object valVuoto = campo.getCampoDati().getValoreMemoriaVuoto();
                if (val == null) {
                    lista.remove(cv);
                    crea = true;
                } else {
                    if (val == valVuoto) {
                        lista.remove(cv);
                        crea = true;
                    }// fine del blocco if
                }// fine del blocco if-else
            }// fine del blocco if-else

            if (crea) {
                cv = new CampoValore(campo, valore);
                lista.add(cv);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return cv;
    }


    /**
     * Forza una lista ad oggetti Campo.
     * <p/>
     * In un elenco di oggetti, che possono essere Campi o Nomi, sostituisce tutti gli oggetti che
     * sono Nomi con i corrispondenti oggetti Campo <br> L'elenco originale non viene modificato
     *
     * @param unElencoCampi l'elenco di oggetti contenente Campi o Nomi
     * @param unModulo il modulo dal quale recuperare i campi
     *
     * @return lista di soli oggetti Campo
     */
    static ArrayList<Campo> convertiCampi(ArrayList unElencoCampi, Modulo unModulo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi = null;
        String unNome;
        Campo unCampo;

        try { // prova ad eseguire il codice
            /* crea l'istanza */
            campi = new ArrayList<Campo>();

            /* traverso tutta la collezione */
            for (Object unOggetto : unElencoCampi) {
                /* se non e' un oggetto campo, lo converte */
                if (unOggetto instanceof Campo) {
                    unCampo = (Campo)unOggetto;
                } else {
                    unNome = unOggetto.toString();
                    unCampo = unModulo.getModello().getCampo(unNome);
                }// fine del blocco if-else

                if (unCampo != null) {
                    campi.add(unCampo);
                }// fine del blocco if
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


}// fine della classe
