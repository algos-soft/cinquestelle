/**
 * Title:        CostanteComando.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 22.05
 */

package it.algos.base.costante;

/**
 * Interfaccia per le costanti dei comandi.<br>
 * <p/>
 * Costanti 'chiave' per recuperare un oggetto (di tipo ComandoBase)
 * dalla collezione comandi (di tipo HashMap)<br>
 * La 'chiave' è di tipo stringa e può essere usata direttamente;
 * uso le costanti perché sono più comode da ricordare
 * I valori di queste costanti devono essere tutti diversi tra loro;
 * la classe HashMap non accetta due volte lo stesso oggetto<br>
 * Se inserisco nella classe per due (o tre) volte lo stesso oggetto,
 * non mi da errore, ma mi ritrovo una sola istanza dell'oggetto.<br>
 * Nei singoli pacchetti verranno eventualmente (se esistono classi
 * comando specifiche di quel pacchetto) dichiarate delle ulteriori
 * costanti il cui valore sia DIVERSO da ogni valore utilizzato in
 * questa interfaccia<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 22.05
 */
public interface CostanteComando {

    public static final String COMANDO_ESCE_PROGRAMMA = "ComandoEsceProgramma";

    public static final String COMANDO_FINESTRA_ESCE_PROGRAMMA = "ComandoFinestraEsceProgramma";

    public static final String COMANDO_CHIUDE_FINESTRA = "ComandoChiudeFinestra";

    public static final String COMANDO_ATTIVA_FINESTRA = "ComandoAttivaFinestra";


    public static final String COMANDO_LISTA_TITOLI = "ComandoListaTitoli";

    public static final String COMANDO_LISTA_CLICK = "ComandoListaClick";

    public static final String COMANDO_LISTA_DOPPIO_CLICK = "ComandoListaDoppioClick";

    public static final String COMANDO_LISTA_CARATTERE = "ComandoListaCarattere";

    public static final String COMANDO_LISTA_ENTER = "ComandoListaEnter";

    public static final String COMANDO_LISTA_FRECCE = "ComandoListaFrecceSingole";

    public static final String COMANDO_LISTA_PAGINE = "ComandoListaFreccePagine";

    public static final String COMANDO_LISTA_HOME = "ComandoListaHome";

    public static final String COMANDO_LISTA_END = "ComandoListaEnd";


    public static final String MOUSE_CLICCATO = "ComMouClic";

    public static final String MOUSE_ENTRATO = "ComMouEnt";

    public static final String MOUSE_USCITO = "ComMouUsc";

    public static final String MOUSE_PREMUTO = "ComMouPre";

    public static final String MOUSE_RILASCIATO = "ComMouRil";

    public static final String MOUSE_DOPPIO_CLICK = "ComMouDop";

    public static final String MOUSE_SCHEDA_CLICK = "ComMouSchClic";


    public static final String CARATTERE_TESTO_PREMUTO = "ComandoCarattereTestoPremuto";

    public static final String CARATTERE_TESTO_AREA_PREMUTO = "ComandoCarattereTestoAreaPremuto";

    public static final String CARATTERE_PREMUTO = "ComCarPre";

    public static final String COMANDO_ENTER = "ComandoEnter";

    public static final String CAMPO_USCITA = "ComCamUsc";

    public static final String CAMPO_FUOCO = "ComCamFuo";

    public static final String STATO_BOTTONE_MODIFICATO = "ComStaBotMod";

    public static final String LISTA_MODIFICATA = "ComLisMod";

    public static final String POPUP_MODIFICATO = "PopupModificato";


    public static final String COMANDO_ALIAS_CARATTERE = "ComandoAliasCarattere";


    public static final String COMANDO_FUOCO_COMBO = "ComandoFuocoCombo";


}// fine della interfaccia