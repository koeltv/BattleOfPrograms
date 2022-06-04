package com.view.component;

import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.view.utils.GeometryUtils.getAbsoluteBounds;

/**
 * This class contains everything to create drag and drop functionalities and assign them to components.
 */
public class DragAndDrop {
	/**
	 * The action called when the component is dragged.
	 */
	private Consumer<Rectangle> onDrag;
	/**
	 * The movements done while dragging the component.
	 */
	private final MouseMotionAdapter move = new MouseMotionAdapter() {
		@Override
		public void mouseDragged(MouseEvent e) {
			Component component = e.getComponent();
			int x = component.getX(), y = component.getY();

			x += e.getX() - component.getWidth() / 2;
			y += e.getY() - component.getHeight() / 2;
			component.setBounds(x, y, component.getWidth(), component.getHeight());

			Rectangle componentAbsoluteBounds = getAbsoluteBounds(component);
			onDrag.accept(componentAbsoluteBounds);
		}
	};
	/**
	 * The action called when the component is dropped.
	 */
	private MouseAdapter drop;
	/**
	 * The condition to allow the component to be dragged and dropped.
	 */
	private Predicate<Component> allowDragAndDrop;

	/**
	 * Sets on drag.
	 *
	 * @param onDrag the on drag
	 */
	public void setOnDrag(Consumer<Rectangle> onDrag) {
		this.onDrag = onDrag;
	}

	/**
	 * Sets on drop.
	 *
	 * @param mouseAdapter the mouse adapter
	 */
	public void setOnDrop(MouseAdapter mouseAdapter) {
		this.drop = mouseAdapter;
	}

	/**
	 * Sets drag and drop conditions.
	 *
	 * @param predicate the predicate
	 */
	public void setDragAndDropConditions(Predicate<Component> predicate) {
		allowDragAndDrop = predicate;
	}

	/**
	 * Add the drag and drop functionalities to the component.
	 *
	 * @param component the component
	 */
	public void addDragAndDrop(Component component) {
		removeListeners(component);
		if (allowDragAndDrop.test(component)) {
			addListeners(component);
		}
		component.addMouseListener(dragAndDropController);
	}

	/**
	 * Remove the listeners from the component.
	 *
	 * @param component the component
	 */
	private void removeListeners(Component component) {
		for (MouseListener listener : component.getMouseListeners()) component.removeMouseListener(listener);
		for (MouseMotionListener listener : component.getMouseMotionListeners())
			component.removeMouseMotionListener(listener);
	}

	/**
	 * Add the listeners to the component.
	 *
	 * @param component the component
	 */
	private void addListeners(Component component) {
		component.addMouseMotionListener(move);
		component.addMouseListener(drop);
	}

	/**
	 * The Drag and drop controller.
	 */
	private final MouseAdapter dragAndDropController = new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e) {
			Component component = e.getComponent();
			addDragAndDrop(component);

			component.getParent().repaint();
			component.getParent().revalidate();
		}
	};


	/**
	 * Allow drag and drop.
	 *
	 * @param component the component
	 * @param allow     whether to allow it or not
	 */
	public void allowDragAndDrop(Component component, boolean allow) {
		removeListeners(component);
		if (allow) addListeners(component);
		component.addMouseListener(dragAndDropController);
	}

}
