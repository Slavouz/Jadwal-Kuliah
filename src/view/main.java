/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author davinky
 */
public class main extends Application {

    public static class Jadwal {

        private final SimpleStringProperty id;
        private final SimpleStringProperty nama;
        private final SimpleStringProperty matkul;
        private final SimpleStringProperty gkb;
        private final SimpleStringProperty waktu;
        private final SimpleStringProperty ruangan;

        private Jadwal(String id, String nama, String matkul, String gkb, String waktu, String ruangan) {
            this.id = new SimpleStringProperty(id);
            this.nama = new SimpleStringProperty(nama);
            this.matkul = new SimpleStringProperty(matkul);
            this.gkb = new SimpleStringProperty(gkb);
            this.waktu = new SimpleStringProperty(waktu);
            this.ruangan = new SimpleStringProperty(ruangan);
        }

        public String getId() {
            return id.get();
        }

        public void setId(String fId) {
            id.set(fId);
        }

        public String getNama() {
            return nama.get();
        }

        public void setNama(String fNama) {
            nama.set(fNama);
        }

        public String getMatkul() {
            return matkul.get();
        }

        public void setMatkul(String fMatkul) {
            matkul.set(fMatkul);
        }

        public String getGKB() {
            return gkb.get();
        }

        public void setGKB(String fGKB) {
            gkb.set(fGKB);
        }

        public String getWaktu() {
            return waktu.get();
        }

        public void setWaktu(String fWaktu) {
            waktu.set(fWaktu);
        }

        public String getRuangan() {
            return ruangan.get();
        }

        public void setRuangan(String fRuangan) {
            ruangan.set(fRuangan);
        }
    }
    
    private TableView table = new TableView();

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        ObservableList<Jadwal> data = FXCollections.observableArrayList();
        ObservableList<String> gkbList = FXCollections.observableArrayList("1", "2", "3", "4");
        try {
            koneksi.koneksi.koneksiDB();
            System.out.println("Berhasil terkoneksi");
        } catch (SQLException e) {
            System.out.println(e);
        }
        stage.setTitle("Jadwal Kuliah 2F 2K22");
        stage.setWidth(520);
        stage.setHeight(550);
        table.setEditable(true);
        Button refBtn = new Button("Refresh");
        Button tambahBtn = new Button("Tambah");
        Button updateBtn = new Button("Ubah");
        Button hapusBtn = new Button("Hapus");
        TableColumn idC = new TableColumn("No.");
        TableColumn namaC = new TableColumn("Nama Dosen");
        TableColumn matkulC = new TableColumn("Mata Kuliah");
        TableColumn gkbC = new TableColumn("GKB");
        TableColumn waktuC = new TableColumn("Waktu");
        TableColumn ruanganC = new TableColumn("Ruangan");
        final ComboBox gkbBox = new ComboBox(gkbList);
        table.getColumns().addAll(idC, namaC, matkulC, gkbC, waktuC, ruanganC);
        final VBox vbox = new VBox();
        final HBox hbox = new HBox();
        final VBox tbox = new VBox();
        vbox.setSpacing(8);
        hbox.setSpacing(8);
        tbox.setSpacing(8);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setPadding(new Insets(5, 10, 10, 10));
        tbox.setPadding(new Insets(5, 0, 10, 10));
        hbox.setAlignment(Pos.CENTER);
        tbox.setAlignment(Pos.CENTER_RIGHT);
        tbox.getChildren().add(refBtn);
        hbox.getChildren().addAll(tambahBtn, updateBtn, hapusBtn);
        vbox.getChildren().addAll(tbox, table, hbox);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
        try {
            String sql = "SELECT * from Jadwal";
            Connection conn = koneksi.koneksi.koneksiDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nama = rs.getString("nama_dosen");
                String matkul = rs.getString("mata_kuliah");
//                String gkb = rs.getString("gkb");
                int gkb = rs.getInt("gkb");
                String waktu = rs.getString("waktu");
                String ruangan = rs.getString("ruangan");
                Jadwal jd = new Jadwal(String.valueOf(id), nama, matkul, String.valueOf(gkb), waktu, ruangan);
                data.add(jd);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        idC.setCellValueFactory(
                new PropertyValueFactory<Jadwal, String>("id")
        );
        namaC.setCellValueFactory(
                new PropertyValueFactory<Jadwal, String>("nama")
        );
        matkulC.setCellValueFactory(
                new PropertyValueFactory<Jadwal, String>("matkul")
        );
        gkbC.setCellValueFactory(
                new PropertyValueFactory<Jadwal, String>("GKB")
        );
        waktuC.setCellValueFactory(
                new PropertyValueFactory<Jadwal, String>("waktu")
        );
        ruanganC.setCellValueFactory(
                new PropertyValueFactory<Jadwal, String>("ruangan")
        );
        table.setItems(data);
        //refresh data
        refBtn.setOnAction((ActionEvent e) -> {
            refreshTable();
        });
        //tambah data
        tambahBtn.setOnAction((ActionEvent e) -> {
            Stage stage2 = new Stage();
            stage2.setTitle("Tambah Data");
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            Label nama = new Label("Nama Dosen: ");
            grid.add(nama, 0, 0);
            TextField namaT = new TextField();
            grid.add(namaT, 1, 0);

            Label matkul = new Label("Mata Kuliah: ");
            grid.add(matkul, 0, 1);
            TextField matkulT = new TextField();
            grid.add(matkulT, 1, 1);

            Label gkb = new Label("GKB: ");
            grid.add(gkb, 0, 2);
            gkbBox.getSelectionModel().select(0);
            grid.add(gkbBox, 1, 2);

            Label waktu = new Label("Waktu: ");
            grid.add(waktu, 0, 3);
            TextField waktuT = new TextField();
            grid.add(waktuT, 1, 3);

            Label ruangan = new Label("Ruangan: ");
            grid.add(ruangan, 0, 4);
            TextField ruanganT = new TextField();
            grid.add(ruanganT, 1, 4);

            Button btnT = new Button("Tambah");
            Button btnE = new Button("Keluar");
            HBox hbBtn = new HBox(10);
            HBox hbBtnE = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_LEFT);
            hbBtnE.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btnT);
            hbBtnE.getChildren().add(btnE);
            grid.add(hbBtn, 0, 6);
            grid.add(hbBtnE, 1, 6);
            btnT.setOnAction((ActionEvent eT) -> {
                if (namaT.getText().length() == 0 || matkulT.getText().length() == 0 || waktuT.getText().length() == 0 || ruanganT.getText().length() == 0) {
                    Alert em = new Alert(AlertType.ERROR);
                    em.setTitle("ERROR");
                    em.setHeaderText("Inputan tidak boleh kosong");
                    em.show();
                } else {
                    try {
                        String namadosen = namaT.getText();
                        String matakuliah = matkulT.getText();
                        String gkbb = gkbBox.getSelectionModel().getSelectedItem().toString();
                        String waktuu = waktuT.getText();
                        String ruang = ruanganT.getText();
                        String sqlT = "INSERT INTO jadwal (nama_dosen, mata_kuliah, gkb, waktu, ruangan) VALUES ('" + namadosen + "',  '" + matakuliah + "', '" + gkbb + "', '" + waktuu + "', '" + ruang + "')";
                        Connection conn = koneksi.koneksi.koneksiDB();
                        PreparedStatement pst = conn.prepareStatement(sqlT);
                        pst.executeUpdate();
                        Alert s = new Alert(AlertType.INFORMATION);
                        s.setTitle("Berhasil");
                        s.setHeaderText("Data berhasil diinput");
                        s.show();
                        stage2.close();
                        //refresh table
                        try {
                            String sql = "SELECT * from Jadwal";
                            pst = conn.prepareStatement(sql);
                            ResultSet rs = pst.executeQuery();
                            table.getItems().clear();
                            while (rs.next()) {
                                int id = rs.getInt("id");
                                String namaS = rs.getString("nama_dosen");
                                String matkulS = rs.getString("mata_kuliah");
                                String gkbS = rs.getString("gkb");
                                String waktuS = rs.getString("waktu");
                                String ruanganS = rs.getString("ruangan");
                                Jadwal jd = new Jadwal(String.valueOf(id), namaS, matkulS, gkbS, waktuS, ruanganS);
                                data.add(jd);
                            }
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        }
                    } catch (SQLException er) {
                        System.out.println(er);
                        Alert s = new Alert(AlertType.ERROR);
                        s.setTitle("ERROR");
                        s.setHeaderText("Error: " + er);
                        s.show();
                    }
                }
            });
            btnE.setOnAction((ActionEvent eX) -> {
                stage2.close();
            });

            Scene sceneT = new Scene(grid, 300, 275);
            stage2.setScene(sceneT);
            stage2.show();
        });

        updateBtn.setOnAction((ActionEvent e) -> {
            if (table.getSelectionModel().isEmpty()) {
                Alert em = new Alert(AlertType.INFORMATION);
                em.setTitle("Informasi");
                em.setHeaderText("Mohon untuk memilih item yang ingin di ubah");
                em.show();
            } else {
                Jadwal jadwal = (Jadwal) table.getSelectionModel().getSelectedItem();
                Stage stage3 = new Stage();
                stage3.setTitle("Ubah Jadwal");
                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(25, 25, 25, 25));
                String index = jadwal.getId();
                Label nama = new Label("Nama Dosen: ");
                grid.add(nama, 0, 0);
                TextField namaT = new TextField();
                namaT.setText(jadwal.getNama());
                grid.add(namaT, 1, 0);

                Label matkul = new Label("Mata Kuliah: ");
                grid.add(matkul, 0, 1);
                TextField matkulT = new TextField();
                matkulT.setText(jadwal.getMatkul());
                grid.add(matkulT, 1, 1);

                Label gkb = new Label("GKB: ");
                grid.add(gkb, 0, 2);
                gkbBox.getSelectionModel().select(0);
                grid.add(gkbBox, 1, 2);

                Label waktu = new Label("Waktu: ");
                grid.add(waktu, 0, 3);
                TextField waktuT = new TextField();
                waktuT.setText(jadwal.getWaktu());
                grid.add(waktuT, 1, 3);

                Label ruangan = new Label("Ruangan: ");
                grid.add(ruangan, 0, 4);
                TextField ruanganT = new TextField();
                ruanganT.setText(jadwal.getRuangan());
                grid.add(ruanganT, 1, 4);

                Button btnT = new Button("Ubah");
                Button btnE = new Button("Keluar");
                HBox hbBtn = new HBox(10);
                HBox hbBtnE = new HBox(10);
                hbBtn.setAlignment(Pos.BOTTOM_LEFT);
                hbBtnE.setAlignment(Pos.BOTTOM_RIGHT);
                hbBtn.getChildren().add(btnT);
                hbBtnE.getChildren().add(btnE);
                grid.add(hbBtn, 0, 6);
                grid.add(hbBtnE, 1, 6);

                btnT.setOnAction((ActionEvent eT) -> {
                    try {
                        String sql = "UPDATE jadwal SET nama_dosen = '" + namaT.getText() + "', mata_kuliah = '" + matkulT.getText() + "', gkb = '" + gkbBox.getSelectionModel().getSelectedItem().toString() + "', waktu = '" + waktuT.getText() + "', ruangan = '" + ruanganT.getText() + "' WHERE id = " + index + "";
                        Connection conn = koneksi.koneksi.koneksiDB();
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.execute();

                        Alert info = new Alert(AlertType.INFORMATION);
                        info.setTitle("Informasi");
                        info.setHeaderText("Data berhasil diubah");
                        info.show();
                        stage3.close();
                        refreshTable();
                    } catch (SQLException ex) {
                        System.out.println(ex);
                        Alert s = new Alert(AlertType.ERROR);
                        s.setTitle("ERROR");
                        s.setHeaderText("Error: " + ex);
                        s.show();
                    }
                });

                btnE.setOnAction((ActionEvent eX) -> {
                    stage3.close();
                });

                Scene sceneT = new Scene(grid, 300, 275);
                stage3.setScene(sceneT);
                stage3.show();
            }
        });

        hapusBtn.setOnAction((ActionEvent e) -> {
            Jadwal jadwal = (Jadwal) table.getSelectionModel().getSelectedItem();
            String index = jadwal.getId();
            Alert konfirm = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);
            konfirm.setTitle("Konfirmasi");
            konfirm.setHeaderText("Anda yakin ingin menghapus data no. " + index + " ?");
            konfirm.showAndWait();
            if (konfirm.getResult() == ButtonType.YES) {
                try {
                    String sql = "DELETE FROM jadwal WHERE id = '"+index+"'";
                    Connection conn = koneksi.koneksi.koneksiDB();
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.execute();
                    Alert info = new Alert(AlertType.INFORMATION);
                    info.setTitle("Informasi");
                    info.setHeaderText("Data berhasil dihapus");
                    info.show();
                    refreshTable();
                } catch (SQLException er) {
                    System.out.println(er);
                    Alert s = new Alert(AlertType.ERROR);
                    s.setTitle("ERROR");
                    s.setHeaderText("Error: " + er);
                    s.show();
                }
            }
        });
    }

    public void refreshTable() {
        ObservableList<Jadwal> data = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from Jadwal";
            Connection conn = koneksi.koneksi.koneksiDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            table.getItems().clear();
            while (rs.next()) {
                int id = rs.getInt("id");
                String namaS = rs.getString("nama_dosen");
                String matkulS = rs.getString("mata_kuliah");
                int gkbI = rs.getInt("gkb");
                String waktuS = rs.getString("waktu");
                String ruanganS = rs.getString("ruangan");
                Jadwal jd = new Jadwal(String.valueOf(id), namaS, matkulS, String.valueOf(gkbI), waktuS, ruanganS);
                data.add(jd);
            }
            table.setItems(data);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
