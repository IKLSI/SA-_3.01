import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Parametrage
{
	public Parametrage(AnchorPane panelCentre)
	{
		try
		{
			panelCentre.getChildren().clear();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("Parametres.fxml"));
			AnchorPane loadedPane = loader.load();

			panelCentre.getChildren().setAll(loadedPane.getChildren());
		}
		catch (Exception e) { e.printStackTrace(); }
	}
}