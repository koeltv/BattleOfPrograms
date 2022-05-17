/**
 * This package contains every element used to display things.
 * The procedure is the following :
 * 1 - The {@link com.view.MainView} is launched. It contains the menu bar and a panel with a card layout.
 * 2 - The first panel is displayed, {@link com.view.StartingPanel}. By triggering certains actions you can trigger a function of the card layout to change to a different panel.
 * 3 - To add a new panel, add a new identifier to {@link com.view.PanelIdentifier}, add the panel to mainPanel with your identifier, then call switchToPanel() with your identifier to make it appear
 */
package com.view;