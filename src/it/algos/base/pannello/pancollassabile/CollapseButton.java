/*
** Luxor - XML User Interface Language (XUL) Toolkit
** Copyright (c) 2001, 2002 by Gerald Bauer
**
** This program is free software.
**
** You may redistribute it and/or modify it under the terms of the GNU
** General Public License as published by the Free Software Foundation.
** Version 2 of the license should be included with this distribution in
** the file LICENSE, as well as License.html. If the license is not
** included with this distribution, you may find a copy at the FSF web
** site at 'www.gnu.org' or 'www.fsf.org', or you may write to the
** Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139 USA.
**
** THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND,
** NOT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR
** OF THIS SOFTWARE, ASSUMES _NO_ RESPONSIBILITY FOR ANY
** CONSEQUENCE RESULTING FROM THE USE, MODIFICATION, OR
** REDISTRIBUTION OF THIS SOFTWARE.
**
*/

package it.algos.base.pannello.pancollassabile;

import it.algos.base.libreria.Lib;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  fix: get Icons from luxor.jar don't use xul itself
 */

class CollapseButton extends JButton implements ActionListener
{

   private Collapsible _collapsible;
   private ImageIcon collapseButtonHorizontalPressed;
   private ImageIcon collapseButtonHorizontalRollover;
   private ImageIcon collapseButtonIconHorizontal;
   private ImageIcon collapseButtonIconVertical;
   private ImageIcon collapseButtonVerticalPressed;
   private ImageIcon collapseButtonVerticalRollover;

   public CollapseButton( Collapsible collapsible, int orientation )
   {
      super();

      _collapsible = collapsible;

      // get icons
      /*
       *  <icon id="COLLAPSE_BUTTON_VERTICAL_ICON"            ref="images/collapseButton-vertical.gif" />
       *  <icon id="COLLAPSE_BUTTON_VERTICAL_ROLLOVER_ICON"   ref="images/collapseButton-vertical-rollover.gif" />
       *  <icon id="COLLAPSE_BUTTON_VERTICAL_PRESSED_ICON"    ref="images/collapseButton-vertical-pressed.gif" />
       *  <icon id="COLLAPSE_BUTTON_HORIZONTAL_ICON"          ref="images/collapseButton-horizontal.gif" />
       *  <icon id="COLLAPSE_BUTTON_HORIZONTAL_ROLLOVER_ICON" ref="images/collapseButton-horizontal-rollover.gif" />
       *  <icon id="COLLAPSE_BUTTON_HORIZONTAL_PRESSED_ICON"  ref="images/collapseButton-horizontal-pressed.gif" />
       *  <icon id="COLLAPSE_BUTTON_VERTICAL_ICON"            ref="images/expanded.gif" />
       *  <icon id="COLLAPSE_BUTTON_VERTICAL_ROLLOVER_ICON"   ref="images/expanded.gif" />
       *  <icon id="COLLAPSE_BUTTON_VERTICAL_PRESSED_ICON"    ref="images/expanded.gif" />
       *  <icon id="COLLAPSE_BUTTON_HORIZONTAL_ICON"          ref="images/collapsed.gif" />
       *  <icon id="COLLAPSE_BUTTON_HORIZONTAL_ROLLOVER_ICON" ref="images/collapsed.gif" />
       *  <icon id="COLLAPSE_BUTTON_HORIZONTAL_PRESSED_ICON"  ref="images/collapsed.gif" />
       */
      // fix: use properties to let user change icons
      collapseButtonIconVertical = new ImageIcon( "images/expanded.gif" );
      collapseButtonVerticalRollover = new ImageIcon( "images/expanded.gif" );
      collapseButtonVerticalPressed = new ImageIcon( "images/expanded.gif" );
      collapseButtonIconHorizontal = new ImageIcon( "images/collapsed.gif" );
      collapseButtonHorizontalRollover = new ImageIcon( "images/collapsed.gif" );
      collapseButtonHorizontalPressed = new ImageIcon( "images/collapsed.gif" );

       collapseButtonIconVertical= Lib.Risorse.getIconaBase("Add16");
       collapseButtonVerticalRollover= Lib.Risorse.getIconaBase("Add16");
       collapseButtonVerticalPressed = Lib.Risorse.getIconaBase("Add16");
       collapseButtonIconHorizontal= Lib.Risorse.getIconaBase("Add16");
       collapseButtonHorizontalRollover = Lib.Risorse.getIconaBase("Add16");
       collapseButtonHorizontalPressed = Lib.Risorse.getIconaBase("Add16");

      setRolloverEnabled( true );
      setFocusPainted( false );
      setDefaultCapable( false );
      setBorder( null );
      setBorderPainted( false );
      setMargin( new Insets( 0, 0, 0, 0 ) );
      setToolTipText( "Collapses/Expands Panel" );
       
       setText("Testo del bottone");

      if( orientation == SwingConstants.VERTICAL )
      {
         setIcon( collapseButtonIconVertical );
         setRolloverIcon( collapseButtonVerticalRollover );
         setPressedIcon( collapseButtonVerticalPressed );
      }
      else
      {
         // if it is not VERTICAL, it's HORIZONTAL

         setIcon( collapseButtonIconHorizontal );
         setRolloverIcon( collapseButtonHorizontalRollover );
         setPressedIcon( collapseButtonHorizontalPressed );
      }

      addActionListener( this );
   }

   public void actionPerformed( ActionEvent evt )
   {
      if( _collapsible.isCollapsed() == true )
         _collapsible.expand();
      else
         _collapsible.collapse();
   }

}
