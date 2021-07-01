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

import javax.swing.*;
import java.awt.*;

/**
 *  see also: Collapsible CollapseButton
 */

public class CollapsiblePanel extends JPanel implements Collapsible
{

   private CollapseButton _collapseHorizontalButton;
   private CollapseButton _collapseVerticalButton;
   private boolean _collapsed = false;
   private GridBagConstraints _constraints;

   private JPanel _content;
   private JPanel _spring;

   public CollapsiblePanel( JPanel content )
   {
      super();

      _content = content;

      setLayout( new GridBagLayout() );

      _constraints = new GridBagConstraints();

      _collapseHorizontalButton = new CollapseButton( this, SwingConstants.HORIZONTAL );
      _collapseVerticalButton = new CollapseButton( this, SwingConstants.VERTICAL );

      _spring = new JPanel();

      expand();
   }

   /**
    *  Tells you whether this component is currently collapsed. Useful for
    *  checking the component's status.
    *
    *@returns    true if this component is collapsed, false if it is not.
    */

   public boolean isCollapsed()
   {
      return _collapsed;
   }

   /**
    *  Tells you whether this component is collapsible.
    *
    *@returns    a boolean indicating this component is collapsible.
    */

   public boolean isCollapsible()
   {
      return collapsible;
   }

   /**
    *  Collapses the panel.
    */
   public void collapse()
   {

      setVisible( false );

      removeAll();

      _constraints.gridx = 0;
      _constraints.gridy = 0;
      _constraints.gridheight = 1;
      _constraints.gridwidth = 1;
      _constraints.ipadx = 0;
      _constraints.ipady = 0;
      _constraints.weightx = 0;
      _constraints.weighty = 0;
      _constraints.insets = new Insets( 0, 0, 0, 0 );
      _constraints.anchor = GridBagConstraints.NORTHWEST;
      _constraints.fill = GridBagConstraints.NONE;

      add( _collapseHorizontalButton, _constraints );

      // fix: does this make sense?
      Dimension dim = _collapseHorizontalButton.getSize();
      _collapseHorizontalButton.reshape( 0, 0, dim.width, dim.height );

      _constraints.gridx = GridBagConstraints.RELATIVE;
      _constraints.gridy = 0;
      _constraints.gridheight = GridBagConstraints.REMAINDER;
      _constraints.gridwidth = GridBagConstraints.REMAINDER;
      _constraints.weightx = 1.0;
      _constraints.weighty = 1.0;
      _constraints.anchor = GridBagConstraints.WEST;
      _constraints.fill = GridBagConstraints.BOTH;

      add( _spring, _constraints );

      _collapsed = true;

      revalidate();
      setVisible( true );
   }

   /**
    *  Uncollapses the panel.
    */

   public void expand()
   {

      setVisible( false );
      removeAll();

      _constraints.gridx = 0;
      _constraints.gridy = 0;
      _constraints.gridheight = 1;
      _constraints.gridwidth = 1;
      _constraints.ipadx = 0;
      _constraints.ipady = 0;
      _constraints.weightx = 0;
      _constraints.weighty = 0;
      _constraints.insets = new Insets( 0, 0, 0, 0 );
      _constraints.anchor = GridBagConstraints.NORTHWEST;
      _constraints.fill = GridBagConstraints.NONE;

      add( _collapseVerticalButton, _constraints );

      // fix: does this make sense?
      Dimension dim = _collapseVerticalButton.getSize();
      _collapseVerticalButton.reshape( 0, 0, dim.width, dim.height );

      //    constraints.insets = new Insets(5,5,5,5);
      _constraints.gridx = GridBagConstraints.RELATIVE;
      _constraints.gridy = 0;
      _constraints.gridheight = GridBagConstraints.REMAINDER;
      _constraints.gridwidth = GridBagConstraints.REMAINDER;
      _constraints.weightx = 1.0;
      _constraints.weighty = 1.0;
      _constraints.anchor = GridBagConstraints.WEST;
      _constraints.fill = GridBagConstraints.BOTH;

      add( _content, _constraints );

      _collapsed = false;

      revalidate();
      setVisible( true );
      // does toggling setVisible make a difference?
   }

}

