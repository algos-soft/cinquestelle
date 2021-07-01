package it.algos.gestione.tabelle.valuta;
/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      15-apr-2007
 */

import it.algos.base.dialogo.DialogoImport;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.TreStringhe;

import java.util.ArrayList;

/**
 * Importazione delle valute dal server di supporto.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 15-apr-2007 ore 10.10.46
 */
public final class ValutaImport extends DialogoImport {


    /**
     * Costruttore completo .
     *
     * @param modulo di riferimento
     * @param mostraDialogo flag per usare o meno il dialogo
     */
    public ValutaImport(Modulo modulo, boolean mostraDialogo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        /* regola le variabili di istanza coi parametri */
        super.setMostraDialogo(mostraDialogo);

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
        if (this.isMostraDialogo()) {
            this.creaDialogo();
        } else {
            super.creaCondizionato();
        }// fine del blocco if-else
    }// fine del metodo inizia


    /**
     * Creazione dialogo.
     * <p/>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     */
    private void creaDialogo() {

        try { // prova ad eseguire il codice

            super.creaDialogo("Valute codificate ISO 4217");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i records.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     * <p/>
     * Recupera i dati dal server di supporto <br>
     */
    @Override
    protected void creaRecords() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            this.creaValute();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i records.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     * <p/>
     * Recupera i dati da una pagina del server di supporto <br>
     * I dati vengono passati con un wrapper formato da tre stringhe <br>
     */
    private void creaValute() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<TreStringhe> lista;

        try { // prova ad eseguire il codice
            /* recupera la lista delle valute in ordine alfabetico di codice */
            lista = this.getValuteIso();
            continua = (lista != null && lista.size() > 0);

            /* ordina per importanza le prime 15 - poi alfabetico */
            if (continua) {
                lista = this.ordinaValute(lista);
                continua = (lista != null && lista.size() > 0);
            }// fine del blocco if

            if (continua) {
                /* traverso tutta la collezione */
                for (TreStringhe tre : lista) {
                    this.creaRecord(tre);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un singolo record.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     *
     * @param wrap wrapper formato da tre stringhe
     */
    private void creaRecord(TreStringhe wrap) {
        /* variabili e costanti locali di lavoro */
        Modulo mod;
        ArrayList<CampoValore> campi;
        CampoValore campoIso;
        CampoValore campoValuta;
        String valuta;
        String siglaIso;

        try { // prova ad eseguire il codice

            try { // prova ad eseguire il codice

                mod = this.getModulo();

                valuta = wrap.getSeconda();
                siglaIso = wrap.getPrima();

                campoValuta = new CampoValore(Valuta.Cam.valuta.get(), valuta);
                campoIso = new CampoValore(Valuta.Cam.codiceIso.get(), siglaIso);

                campi = new ArrayList<CampoValore>();
                campi.add(campoIso);
                campi.add(campoValuta);

                super.creaRecord(mod, campi, Valuta.Cam.valuta.get(), valuta);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Recupera la lista delle valute.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Recupera i dati da una pagina del server di supporto <br>
     * I dati sono quelli della pagina ISO 4217 di wikipedia, elaborati e tabellati <br>
     * Per ogni riga, crea un elemento della lista con un wrapper formata da tre stringhe <br>
     *
     * @return wrapper di tre stringhe
     */
    private ArrayList<TreStringhe> getValuteIso() {
        /* variabili e costanti locali di lavoro */
        ArrayList<TreStringhe> lista = null;
        boolean continua;
        String titolo = "gest/Valute";
        String testo;

        try { // prova ad eseguire il codice
            testo = Lib.Web.downLoadData(titolo);
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                lista = Lib.Web.getCsvTre(testo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Ordina per importanza le valute.
     * <p/>
     * Regola le prime 15 <br>
     * Le rimanenti in ordine alfabetico di valuta e non di codice iso <br>
     *
     * @param listaIn ingresso non ordinata
     *
     * @return lista ordinata
     */
    private ArrayList<TreStringhe> ordinaValute(ArrayList<TreStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<TreStringhe> listaOut = null;
        boolean inserita;

        try { // prova ad eseguire il codice
            listaOut = new ArrayList<TreStringhe>();

            /* inserisco le prime 15 */
            for (Valuta.Quindici valuta : Valuta.Quindici.values()) {
                /* traverso tutta la collezione */
                for (TreStringhe tre : listaIn) {
                    if (tre.getPrima().equals(valuta.getCodice())) {
                        listaOut.add(tre);
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            } // fine del ciclo for-each

            /* inserisco le rimanenti */
            for (TreStringhe treIn : listaIn) {
                inserita = false;
                for (TreStringhe treOut : listaOut) {
                    if (treOut.getPrima().equals(treIn.getPrima())) {
                        inserita = true;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each

                if (!inserita) {
                    listaOut.add(treIn);
                }// fine del blocco if
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


}// fine della classe
