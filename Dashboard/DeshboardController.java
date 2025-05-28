package com.lib.Controller;

import com.lib.Buttons.AddMemberSaveButton;
import com.lib.Function.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DeshboardController implements Initializable {

    DatabaseFunction retriveTotal = new DatabaseFunction();

    @FXML
    public StackPane root;
    @FXML
    public FlowPane flowPaneMember;
    @FXML
    public FlowPane flowPaneBook;
    @FXML
    public Label mainTitle;
    @FXML private AnchorPane B_anchor;
    @FXML
    public StackPane backgroundOverlay;
    @FXML
    public VBox buttonVBox;
    @FXML private  SplitPane leftSplit;
    @FXML private Label logo;
    @FXML
    protected Button addMemberButton;
    @FXML
    protected Button addBookButton;
    @FXML
    private Button issueBook;
    @FXML
    private Button returnBook;
    @FXML
    private Button updateMember;
    @FXML
    private Button updateBook;
    @FXML
    protected Button settings;
    @FXML
    private Button logout;
    @FXML
    public ImageView profileIcon;
    @FXML
    public ImageView profileIcon1;
    @FXML
    public TabPane tabPane;
    @FXML
    private ImageView backgroundImage;
    @FXML
    public Button MemberRefreshButton;
    @FXML
    private Button BookRefreshButton;
    @FXML
    public Button memberSearchButton;
    @FXML
    private TextField memberSearchField;
    @FXML
    private Button searchBookButton;

    @FXML
    private TextField bookSearchField;
    @FXML
    private Button DeshboardRefreshButton;

    @FXML
    DatabaseFunction d = new DatabaseFunction();
    RetriveLibraryDetails r = new RetriveLibraryDetails();
    ConnectionTest c = new ConnectionTest();
    Function fun=new Function();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableSplitPaneSlider(leftSplit, 0.1629);
        setupUIStyle();
        mainTitle.setText(r.retriveTilte());
        retrieveImage();
        loadBookImages("SELECT profile_pic FROM books", "SELECT book_id FROM books", "SELECT book_name FROM books");
        loadMemberImages("SELECT profile_pic FROM members", "SELECT std_id FROM members", "SELECT name FROM members");
        setupButtonActions();



    }

    private void setupUIStyle() {
        buttonVBox.setStyle(styleButtonBox);
        leftSplit.setStyle(styleSplit);
        tabPane.setStyle(styleforTab);
        mainTitle.setStyle(style);
//        logo.setStyle();
        buttonStyle();




    }

    private void setupButtonActions() {
        BookRefreshButton.setOnAction(actionEvent -> {
            flowPaneBook.getChildren().clear();
            bookSearchField.clear();
            loadBookImages("SELECT profile_pic FROM books", "SELECT book_id FROM books", "SELECT book_name FROM books");
        });

        MemberRefreshButton.setOnAction(actionEvent -> {
            memberSearchField.clear();
            flowPaneMember.getChildren().clear();
            loadMemberImages("SELECT profile_pic FROM members", "SELECT std_id FROM members", "SELECT name FROM members");
        });

        searchBookButton.setOnAction(actionEvent -> {
            String t = bookSearchField.getText().trim();
            if (t.isEmpty()) {
                flowPaneBook.getChildren().clear();
                loadBookImages("SELECT profile_pic FROM books", "SELECT book_id FROM books", "SELECT book_name FROM books");
            } else {
                String imageQuery = "SELECT profile_pic FROM books WHERE book_id = '" + t + "' OR book_name LIKE '%" + t + "%'";
                String idQuery = "SELECT book_id FROM books WHERE book_id = '" + t + "' OR book_name LIKE '%" + t + "%'";
                String nameQuery = "SELECT book_name FROM books WHERE book_id = '" + t + "' OR book_name LIKE '%" + t + "%'";
                flowPaneBook.getChildren().clear();
                loadBookImages(imageQuery, idQuery, nameQuery);
            }
        });

        memberSearchButton.setOnAction(actionEvent -> {
            String t = memberSearchField.getText().trim();
            if (t.isEmpty()) {
                flowPaneMember.getChildren().clear();
                loadMemberImages("SELECT profile_pic FROM members", "SELECT std_id FROM members", "select name from members");
            } else {
                String imageQuery = "SELECT profile_pic FROM members WHERE std_id = '" + t + "' OR name LIKE '%" + t + "%'";
                String idQuery = "SELECT std_id FROM members WHERE std_id = '" + t + "' OR name LIKE '%" + t + "%'";
                String nameQuery = "SELECT name FROM members WHERE std_id = '" + t + "' OR name LIKE '%" + t + "%'";
                flowPaneMember.getChildren().clear();
                loadMemberImages(imageQuery, idQuery, nameQuery);
            }
        });
        addMemberButton.setOnAction(event -> {

            addMemberLoad();
        });
        addBookButton.setOnAction(action -> {
                addBookLoad();
        });

        settings.setOnAction(event ->{
            settingsLoad();

        });

        issueBook.setOnAction(actionEvent -> {
            issueBookLoad();
        });
        DeshboardRefresh d=new DeshboardRefresh();
        DeshboardRefreshButton.setOnAction(actionEvent -> {
            d.refresh(bookSearchField);
        });

        logout.setOnAction(eve ->{
            Stage currentStage=(Stage) mainTitle.getScene().getWindow();
            currentStage.close();
        });


    }

    private void loadBookImages(String imageQuery, String idQuery, String nameQuery) {
        flowPaneBook.getChildren().clear();
        ArrayList<Integer> bookIds = retriveTotal.retrieveId(idQuery, "book_id");
        ArrayList<String> bookNames = retriveTotal.retrieveNames(nameQuery, "book_name");
        ImageView[] bookImages = d.getImagesFromDatabase(imageQuery, bookIds.size());

        for (int i = 0; i < bookIds.size(); i++) {
            if (bookImages[i] != null) {
                ImageView imgView = bookImages[i];
                imgView.setUserData(bookIds.get(i));
                imgView.setCursor(Cursor.HAND);
                imgView.setOnMouseClicked(e -> {
                    int clickedBookId = (int) imgView.getUserData();
                    System.out.println("Book image clicked with book_id: " + clickedBookId);
                });


                Label nameLabel = new Label("Name:"+bookNames.get(i) + "\nID: " + bookIds.get(i));

                nameLabel.setWrapText(true);
                nameLabel.setMaxWidth(100);
                nameLabel.setAlignment(Pos.CENTER);
                nameLabel.getStyleClass().add("member-name");

                VBox container = new VBox(5, imgView, nameLabel);
                container.setStyle(styleforTab);
                container.setAlignment(Pos.CENTER);
                container.getStyleClass().add("image-container");
                flowPaneBook.getChildren().add(container);
            }
        }
    }

    private void loadMemberImages(String imageQuery, String idQuery, String nameQuery) {
        flowPaneMember.getChildren().clear();
        ArrayList<Integer> memberIds = retriveTotal.retrieveId(idQuery, "std_id");
        ArrayList<String> memberNames = retriveTotal.retrieveNames(nameQuery, "name");
        ImageView[] memberImages = d.getImagesFromDatabase(imageQuery, memberIds.size());

        for (int i = 0; i < memberIds.size(); i++) {
            if (memberImages[i] != null && i < memberNames.size()) {
                ImageView imgView = memberImages[i];
                imgView.setUserData(memberIds.get(i));
                imgView.setCursor(Cursor.HAND);
                imgView.setOnMouseClicked(e -> {
                    int clickedStdId = (int) imgView.getUserData();
                    System.out.println("Clicked Member ID: " + clickedStdId);
                });

                Label nameLabel = new Label("Name:"+memberNames.get(i) + "\nID: " + memberIds.get(i));
                nameLabel.setWrapText(true);
                nameLabel.setMaxWidth(100);
                nameLabel.setAlignment(Pos.CENTER);
                nameLabel.getStyleClass().add("member-name");

                VBox container = new VBox(5, imgView, nameLabel);
                container.setStyle(styleforTab);
                container.setAlignment(Pos.CENTER);
                container.getStyleClass().add("image-container");
                flowPaneMember.getChildren().add(container);
            }
        }
    }

    public void retrieveImage() {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = c.getConnection();
            String query = "SELECT library_logo FROM libraryDetails WHERE id = 1";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();

            if (rs.next()) {
                byte[] imageBytes = rs.getBytes("library_logo");

                if (imageBytes != null && imageBytes.length > 0) {
                    Image image = new Image(new ByteArrayInputStream(imageBytes));
                    Platform.runLater(() -> {
                        profileIcon1.setImage(image);
                        profileIcon1.setFitWidth(200);
                        profileIcon1.setFitHeight(200);
                        profileIcon1.setPreserveRatio(true);
                        profileIcon1.setSmooth(true);
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
     String styleButtonBox = "-fx-background-color: linear-gradient(to bottom right, #51e2f5, #ffa8b6, #a29bfe); " +
            "-fx-background-radius: 15; " +
            "-fx-border-width: 5; " +
            "-fx-border-color: linear-gradient(to right, #51e2f5, #ffa8b6); " +
            "-fx-border-radius: 15; " +
            "-fx-padding: 15;";




    String style = "-fx-background-color: linear-gradient(to bottom right, #ffa8b6, #a29bfe,#51e2f5); " +
            "-fx-background-radius: 15; " +
            "-fx-border-width: 5; " +
            "-fx-border-color: linear-gradient(to right, #ffa8b6, #51e2f5); " +
            "-fx-border-radius: 15; " +
            "-fx-padding: 15;";

    String styleforTab = "-fx-background-color: rgba(0,0,0,0.8); " +
            "-fx-background-radius: 15; " +
            "-fx-border-width: 5; " +
            "-fx-border-color: linear-gradient(to right, #ffa8B6, #51e2f5); " +
            "-fx-border-radius: 15; " +
            "-fx-padding: 15;";

    String styleSplit = """
    -fx-background-color: rgba(10,10,20,0.9);
    -fx-background-radius: 15;
    -fx-border-width: 3;
    -fx-border-color: linear-gradient(to right, #ffa8B6, #55e7f7);
    -fx-border-radius: 0;
    -fx-padding: 15;
    -fx-effect: innershadow(gaussian,#c81818, 15, 0.5, 0, 0),
                dropshadow(gaussian, #ffa8B6, 15, 0.5, 0, 0);
    """;




    private void disableSplitPaneSlider(SplitPane splitPane, double fixedPosition) {
        splitPane.setDividerPositions(fixedPosition);

        Platform.runLater(() -> {
            splitPane.lookupAll(".split-pane-divider").forEach(divider -> divider.setMouseTransparent(true));
        });
    }



    private void buttonStyle() {
        String s = "-fx-background-color:rgb(42,60,87); " +
                "-fx-background-radius: 15; " +
                "-fx-border-width: 2; " +
                "-fx-border-color: linear-gradient(to right, #ffa8B6, #51e2f5); " +
                "-fx-border-radius: 15; " +
                "-fx-padding: 15;";
        searchBookButton.setStyle(s);
        MemberRefreshButton.setStyle(s);
        BookRefreshButton.setStyle(s);
        memberSearchButton.setStyle(s);
        DeshboardRefreshButton.setStyle(s);


    }

    public  void addMemberLoad(){

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lib/library/AddMember.fxml"));
            String css = getClass().getResource("/com/lib/library/CSS/AddMember.css").toExternalForm();
            Parent root = loader.load();
            root.getStylesheets().add(css);
            Stage newsStage = new Stage();
            newsStage.setResizable(false);
            newsStage.setScene(new Scene(root));
            newsStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public  void addBookLoad(){

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lib/library/AddBook.fxml"));
            String css = getClass().getResource("/com/lib/library/CSS/AddMember.css").toExternalForm();
            Parent root = loader.load();
            root.getStylesheets().add(css);
            Stage newsStage = new Stage();
            newsStage.setResizable(false);
            newsStage.setScene(new Scene(root));
            newsStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public  void settingsLoad(){

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lib/library/Settings.fxml"));
            String css = getClass().getResource("/com/lib/library/CSS/AddMember.css").toExternalForm();
            Parent root = loader.load();
            root.getStylesheets().add(css);
            Stage newsStage = new Stage();
            newsStage.setResizable(false);
            newsStage.setScene(new Scene(root));
            newsStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }



    public  void issueBookLoad(){

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lib/library/IssueBook.fxml"));
            String css = getClass().getResource("/com/lib/library/CSS/AddMember.css").toExternalForm();
            Parent root = loader.load();
            root.getStylesheets().add(css);
            Stage newsStage = new Stage();
            newsStage.setResizable(false);
            newsStage.setScene(new Scene(root));
            newsStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }


}
