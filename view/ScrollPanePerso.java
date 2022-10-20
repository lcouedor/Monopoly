package view;

import java.util.Set;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;

public class ScrollPanePerso extends ScrollPane{
	// Nouvelle ScrollBar verticale
	private ScrollBar vBar = new ScrollBar();
	// Nouvelle ScrollBar horizontale
	private ScrollBar hBar = new ScrollBar();

	private int pos=0;
	private int minPos=0;
	private int maxPos=2;

	/**
	 * Cr�e un nouveau BeauScrollPane
	 */
	public ScrollPanePerso() {
		super();
		getStylesheets().add("style.css");
		// Rajoute les nouvelles barres de d�filement au ScrollPane
		skinProperty().addListener(it -> {
			bindScrollBars();
			getChildren().addAll(hBar);
		});
		setHmin(minPos);
		setHmax(maxPos);
		setVbarPolicy(ScrollBarPolicy.NEVER);
		setHbarPolicy(ScrollBarPolicy.NEVER);
		// Cr�e les nouvelles barres de d�filement
		vBar.setManaged(false);
		vBar.setOrientation(Orientation.VERTICAL);
		vBar.getStyleClass().add("scroll-bar-perso");
		vBar.visibleProperty().bind(vBar.visibleAmountProperty().isNotEqualTo(0));

		hBar.setManaged(false);
		hBar.setOrientation(Orientation.HORIZONTAL);
		hBar.getStyleClass().add("scroll-bar-perso");
		hBar.visibleProperty().bind(hBar.visibleAmountProperty().isNotEqualTo(0));
	}

	// Place les nouvelles barres de d�filement � l'int�rieur de la ListView
	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		Insets insets = getInsets();
		double w = getWidth();
		double h = getHeight();
		final double prefWidth = vBar.prefWidth(-1);
		vBar.resizeRelocate(w - prefWidth - insets.getRight(), insets.getTop(), prefWidth, h - insets.getTop() - insets.getBottom());
		final double prefHeight = hBar.prefHeight(-1);
		hBar.resizeRelocate(insets.getLeft(), h - prefHeight - insets.getBottom(), w - insets.getLeft() - insets.getRight(), prefHeight);
	}

	// Lie les anciennes barres de d�filement horizontales et vertivales avec les nouvelles
	private void bindScrollBars() {
		final Set<Node> nodes = lookupAll("ScrollBar");
		for (Node node : nodes) {
			if (node instanceof ScrollBar) {
				ScrollBar bar = (ScrollBar) node;
				if (bar.getOrientation().equals(Orientation.VERTICAL)) {
					bindScrollBars(vBar, bar);
				} else if (bar.getOrientation().equals(Orientation.HORIZONTAL)) {
					bindScrollBars(hBar, bar);
				}
			}
		}
	}

	/**
	 * Lie les barres de d�filement de deux ListeViewTriable.
	 * 
	 * @param scrollBarA
	 * Barre � lier de la premi�re ListeViewTriable
	 * @param scrollBarB
	 * Barre � lier de la deuxi�me ListeViewTriable
	 */
	// Lie deux barres de d�filement en liant leurs propri�t�s
	public static void bindScrollBars(ScrollBar scrollBarA, ScrollBar scrollBarB) {
		scrollBarA.valueProperty().bindBidirectional(scrollBarB.valueProperty());
		scrollBarA.minProperty().bindBidirectional(scrollBarB.minProperty());
		scrollBarA.maxProperty().bindBidirectional(scrollBarB.maxProperty());
		scrollBarA.visibleAmountProperty().bindBidirectional(scrollBarB.visibleAmountProperty());
		scrollBarA.unitIncrementProperty().bindBidirectional(scrollBarB.unitIncrementProperty());
		scrollBarA.blockIncrementProperty().bindBidirectional(scrollBarB.blockIncrementProperty());
	}

	/**
	 * Renvoie la berre de d�filement verticale de la liste
	 * 
	 * @return
	 * Barre de d�filement verticale
	 */
	public ScrollBar getVBar() {
		return vBar;
	}

	/**
	 * Renvoie la berre de d�filement horizontale de la liste
	 * 
	 * @return
	 * Barre de d�filement horizontale
	 */
	public ScrollBar getHBar() {
		return hBar;
	}

	public void setHorizontalScroll(boolean bool) {
		if(bool) {
			setOnScroll(new EventHandler<ScrollEvent>() {
				@Override
				public void handle(ScrollEvent event) {
					if (event.getDeltaY() > 0)
						((ScrollPane) event.getSource()).setHvalue(pos == minPos ? minPos : pos--);
					else
						((ScrollPane) event.getSource()).setHvalue(pos == maxPos ? maxPos : pos++);
				}
			});
		}else {
			setOnScroll(new EventHandler<ScrollEvent>() {
				@Override
				public void handle(ScrollEvent event) {}
			});
		}
	}
	
	public void requestFocus() {}
}
