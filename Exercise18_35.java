import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
public class Exercise18_35  extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        HTreeFractalPane hTreePane = new HTreeFractalPane();
        hTreePane.draw();
        BorderPane pane = new BorderPane(hTreePane);
        TextField treeOrder = new TextField();
        treeOrder.setPrefColumnCount(3);
        treeOrder.setAlignment(Pos.BASELINE_RIGHT);
        treeOrder.setText("0");
        treeOrder.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    hTreePane.setOrder(Integer.parseInt(treeOrder.getText()));
                }
                catch (Exception ex){
                    System.out.println("error");
                }
                hTreePane.draw();
            }
        });
        Label labelOrder = new Label("Order: ");
        HBox hBox = new HBox(10, labelOrder, treeOrder);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        pane.setBottom(hBox);
        hBox.setPadding(new Insets(10));
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fractal");
        primaryStage.show();
        hTreePane.requestFocus();
    }
    public class HTreeFractalPane extends Pane {
        double Size;
        int order = 0;
        double width;
        double height;
        HTreeFractalPane() {
           width= 800;
           height= 800;
           Size = Math.min(width, height) * 0.5;
           setMinSize(width, height);
           draw();
        }
        public void draw() {
            getChildren().clear();
            double x =width* 0.25;
            double y =height* 0.85;
            draw(x, y, order, Size);
        }
        private void draw(double x, double y, int order, double Size) {
            Line l1 = new Line(x, y, x, y - Size);
            Line l2 = new Line(x + Size, y, x + Size, y - Size);
            Line l3 = new Line(x, y - (Size / 2), x + Size, y - (Size / 2));
            getChildren().addAll(l1, l2, l3);
            if (order > 0) {
                double halfSize = Size / 2;
                double displacement = halfSize / 2;
                draw(l1.getStartX() - displacement, l1.getEndY() + halfSize / 2, order - 1, halfSize);
                draw(l2.getStartX() - displacement, l1.getEndY() + halfSize / 2, order - 1, halfSize);
                draw(l1.getEndX() - displacement, l1.getStartY() + halfSize / 2, order - 1, halfSize);
                draw(l2.getEndX() - displacement, l1.getStartY() + halfSize / 2, order - 1, halfSize);
            }
        }
        public void setOrder(int order) {
            this.order = order;
        }
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}