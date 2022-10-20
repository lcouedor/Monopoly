package view;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;

import back.Case;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

@SuppressWarnings("restriction")
public class ChanceView extends CaseView{

	public ChanceView(Case uneCase) {
		super(uneCase);
		nom.setStyle("-fx-text-fill:"+uneCase.getCouleur());
		FontMetrics metrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(nom.getFont());
		nom.fontProperty().bind(Bindings.createObjectBinding(()->{
			nom.setPadding(new Insets(-metrics.getDescent(), 0, 0, 0));
			return new Font("Nunito",fontSize.multiply(2).doubleValue());
		},fontSize));
		nom.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        setOnMouseClicked(e->MonopolyIUT.getPartie().getView().afficherFiche(uneCase.getFiche()));
	}

}
