package pbo_javafx;

/**
 *
 * @author davinky
 */
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Percobaan2 extends Application{

    private TableView table = new TableView();
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Group());
        stage.setTitle("Test TableView");
        stage.setWidth(450);
        stage.setHeight(550);
        final Label label = new Label("Daftar Mahasiswa");
        label.setFont(new Font("Arial", 30));
        
        table.setEditable(true);
        
        TableColumn nameCol = new TableColumn("Nama");
        TableColumn nimCol = new TableColumn("NIM");
        TableColumn emailCol = new TableColumn("Email");
        
        table.getColumns().addAll(nameCol, nimCol, emailCol);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(8);
        vbox.setPadding(new Insets(20, 10, 10, 10));
        vbox.getChildren().addAll(label, table);
        
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }
    
    public static class Mahasiswa {
        private final SimpleStringProperty name;
        private final SimpleStringProperty nim;
        private final SimpleStringProperty email;
        
        private Mahasiswa(String name, String nim, String email) {
            this.name = new SimpleStringProperty(name);
            this.nim = new SimpleStringProperty(nim);
            this.email = new SimpleStringProperty(email);            
        }
        
        public String getName() {
            return name.get();
        }
        
        public void setName(String fName) {
            name.set(fName);
        }
        
        public String getNim() {
            return nim.get();
        }
        
        public void setNim(String fNim) {
            nim.set(fNim);
        }
        
        public String getEmail() {
            return email.get();
        }
        
        public void setEmail(String fEmail) {
            email.set(fEmail);
        }
    }
    
    final ObservableList<Mahasiswa> data = FXCollections.observableArrayList(
            new Mahasiswa("Syahrul", "202110370311222", "asdf@gmaol.com"),
            new Mahasiswa("Azka", "2021111111111", "asdfa@gmail.com")
    );
    
    
    
    public static void main(String[] args) {
        launch(args);
    }

}
