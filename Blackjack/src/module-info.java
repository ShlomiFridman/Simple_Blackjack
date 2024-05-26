module Blackjack {
	requires javafx.base;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.web;
    requires javafx.fxml;
	requires javafx.graphics;
    
	opens application to javafx.graphics, javafx.fxml;
}
