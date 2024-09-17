package dev.jay.ultimatepokedex.model.dto.response.pokemon;

import java.util.ArrayList;

public class Evolution {
    public ArrayList<ArrayList<String>> next;

    public Evolution(ArrayList<ArrayList<String>> next) {
        this.next = next;
    }

    public ArrayList<ArrayList<String>> getNext() {
        return next;
    }

    public void setNext(ArrayList<ArrayList<String>> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Evolution{" +
                "next=" + next +
                '}';
    }
}
