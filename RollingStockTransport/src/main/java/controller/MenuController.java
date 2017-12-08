package controller;

import static view.Regexp.*;
import static view.Multilingual.rs;

import model.train.RailwayStation;
import model.train.SuchTrainNotExist;
import model.train.Trains;
import model.train.entity.PassengerWaggon;
import model.train.entity.Train;
import view.Multilingual;
import view.View;
import java.util.Scanner;

public class MenuController {
    private final RailwayStation railway;
    private final View view;
    private final Scanner scanner;
    private Multilingual mult;
    private UserData userData;

    public MenuController(RailwayStation railway, View view,
                          Scanner scanner) {
        this.railway = railway;
        this.view = view;
        this.scanner = scanner;
        this.userData = UserData.getInstances();
    }

    public void execute() {
        while (true) {
            String input = scanner.nextLine();
            switch (input) {
                case CMD_SHOW:
                    show();
                    break;
                case CMD_SHOW_TRAIN:
                    showSelected(input);
                    break;
                case CMD_SELECT:
                    select(input);
                    break;
                case CMD_SELECTED:
                    showSelected();
                    break;
                case CMD_SORT:
                    sort();
                    break;
                case CMD_COUNT_PASSENGER:
                    countPassenger();
                    break;
                case CMD_COUNT_BAG:
                    ///Trains.countBaggage();
                    break;
                case COUNT_IN_RANGE:
                    break;
                case CMD_QUIT:
                    return;
            }
        }
    }

    private void select(String i) {
        int index = Integer.parseInt((i.split("\\s"))[1]);
        checkIndex(index);
        userData.setCurrentTrain(railway.getTrain(index));
        view.println(rs().getString("selected"));
    }

    private void countPassenger() {
        Train t;
        if ((t = userData.getCurrentTrain()) != null) {
            int q = Trains.countPassengers(t);
            view.println(rs().getString("quantity_passengers") + q);
        } else {
            view.println(rs().getString("not_selected"));
        }
    }

    private void sort() {
        Train t;
        if ((t = userData.getCurrentTrain()) != null) {
            Trains.sort(t, PassengerWaggon.comfortComparator());
        } else {
            view.println(rs().getString("not_selected"));
        }
    }

    private void showSelected() {
        Train t;
        if ((t = userData.getCurrentTrain()) != null) {
            view.println(t.toString());
        } else {
            view.println(rs().getString("not_selected"));
        }
    }

    private void showSelected(String i) {
        int index = Integer.parseInt((i.split("\\s"))[2]);
        checkIndex(index);
        view.println(railway.getTrain(index).toString());
    }

    private void checkIndex(int i) {
        if(i < -1 || i >= railway.numberOfTrains()) {
            throw new SuchTrainNotExist(rs().getString("incorrect_track"), i);
        }
    }

    private void inRange(String cmd) {
        cmd = cmd.substring(16, cmd.length()-1);
        String args[] = cmd.split(",");
        //Trains.diapason();
    }

    private void show() {
        for (int i = 0; i < railway.numberOfTrains(); i++) {
            view.print(rs().getString("track ") + i + ": ");
            view.print(railway.getTrain(i).toString());
        }
    }
}