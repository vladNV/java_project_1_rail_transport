package model.train;

import model.train.entity.Passenger;
import model.train.entity.PassengerWaggon;
import model.train.entity.RailTransport;
import model.train.entity.Train;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Trains {
    //sort
    public static void sort(Train train, Comparator<RailTransport> comp) {
        train.getWaggons().sort(comp);
    }

    //counts passenger
    public static int countPassengers(Train train) {
        int numberOfPassengers = 0;
        PassengerWaggon pw;
        for (RailTransport rt : train.getWaggons()) {
            if((pw = PassengerWaggon.isPassenger(rt)) != null) {
                numberOfPassengers += pw.getCountPassengers();
            }
        }
        return numberOfPassengers;
    }
    //counts them baggage
    public static int countBaggage(Train train) {
        int weight = 0;
        PassengerWaggon pw;
        List<Passenger> passengers;
        for (RailTransport rt : train.getWaggons()) {
            if((pw = PassengerWaggon.isPassenger(rt)) != null) {
                passengers = pw.getPassengers();
                for (Passenger p : passengers) {
                    weight += p.getBaggageWeight();
                }
            }
        }
        return weight;
    }
    //waggons are in diapason for number of passengers
    public static List<PassengerWaggon> diapason(Train train, int left, int right) {
        List<PassengerWaggon> waggons = new ArrayList<>();
        if(left < 0 || right < 0 || left > right) {
            throw new IllegalArgumentException();
        }
        PassengerWaggon pw;
        for (RailTransport rt : train.getWaggons()) {
            if((pw = PassengerWaggon.isPassenger(rt)) != null) {
                if (pw.getCountPassengers() >= left && pw.getCountPassengers() <= right)
                waggons.add(pw);
            }
        }
        return waggons;
    }

}
