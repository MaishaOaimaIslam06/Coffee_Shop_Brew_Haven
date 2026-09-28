package com.example.coffeeshop;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CoffeeInfoController {

    @FXML
    private VBox coffeeBox;

    @FXML
    private ScrollPane scrollPane;


    private final HttpClient client =
            HttpClient.newHttpClient();

    private final ObjectMapper mapper =
            new ObjectMapper();


    @FXML
    private void getCoffeeInfo() {

        // Clear previous information
        coffeeBox.getChildren().clear();


        Label loadingLabel = new Label(
                "Loading coffee information..."
        );

        loadingLabel.setFont(
                new Font("Georgia", 18)
        );

        coffeeBox.getChildren().add(
                loadingLabel
        );


        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        "https://api.sampleapis.com/coffee/hot"
                                )
                        )
                        .GET()
                        .build();


        client.sendAsync(
                request,
                HttpResponse.BodyHandlers.ofString()
        ).thenAccept(response -> {

            try {

                String json = response.body();

                JsonNode root =
                        mapper.readTree(json);


                Platform.runLater(() -> {

                    coffeeBox.getChildren().clear();


                    // Show first 8 coffee information
                    for (
                            int i = 0;
                            i < 8 && i < root.size();
                            i++
                    ) {

                        JsonNode coffee =
                                root.get(i);


                        String title =
                                coffee.get("title")
                                        .asText();


                        String description =
                                coffee.get("description")
                                        .asText();


                        StringBuilder ingredients =
                                new StringBuilder();


                        for (
                                JsonNode item :
                                coffee.get("ingredients")
                        ) {

                            if (ingredients.length() > 0) {
                                ingredients.append(", ");
                            }

                            ingredients.append(
                                    item.asText()
                            );
                        }


                        // Coffee title
                        Label titleLabel =
                                new Label(
                                        (i + 1) +
                                                ". " +
                                                title
                                );

                        titleLabel.setFont(
                                new Font(
                                        "Georgia Bold",
                                        20
                                )
                        );

                        titleLabel.setStyle(
                                "-fx-text-fill: #5e1515;"
                        );


                        // Description
                        Label descriptionLabel =
                                new Label(
                                        "Description: "
                                                + description
                                );

                        descriptionLabel.setFont(
                                new Font(
                                        "Georgia",
                                        16
                                )
                        );

                        descriptionLabel.setWrapText(true);

                        descriptionLabel.setMaxWidth(
                                800
                        );


                        // Ingredients
                        Label ingredientsLabel =
                                new Label(
                                        "Ingredients: "
                                                + ingredients
                                );

                        ingredientsLabel.setFont(
                                new Font(
                                        "Georgia",
                                        16
                                )
                        );

                        ingredientsLabel.setWrapText(true);

                        ingredientsLabel.setMaxWidth(
                                800
                        );


                        // Separator
                        Label separator =
                                new Label(
                                        "────────────────────────────"
                                );

                        separator.setStyle(
                                "-fx-text-fill: #79492F;"
                        );


                        // Add everything
                        coffeeBox.getChildren().addAll(
                                titleLabel,
                                descriptionLabel,
                                ingredientsLabel,
                                separator
                        );
                    }

                });


            } catch (Exception e) {

                Platform.runLater(() -> {

                    coffeeBox.getChildren().clear();

                    Label errorLabel =
                            new Label(
                                    "Failed to parse coffee information."
                            );

                    errorLabel.setFont(
                            new Font("Georgia", 18)
                    );

                    coffeeBox.getChildren().add(
                            errorLabel
                    );
                });
            }


        }).exceptionally(error -> {

            Platform.runLater(() -> {

                coffeeBox.getChildren().clear();

                Label errorLabel =
                        new Label(
                                "Internet connection or API error."
                        );

                errorLabel.setFont(
                        new Font("Georgia", 18)
                );

                coffeeBox.getChildren().add(
                        errorLabel
                );
            });

            return null;
        });
    }


    @FXML
    private void back(ActionEvent event)
            throws IOException {

        Parent root =
                FXMLLoader.load(
                        getClass().getResource(
                                "AdminDashboard.fxml"
                        )
                );


        Stage stage =
                (Stage) ((Node) event.getSource())
                        .getScene()
                        .getWindow();


        Scene scene =
                new Scene(root);


        stage.setScene(scene);

        stage.show();
    }
}