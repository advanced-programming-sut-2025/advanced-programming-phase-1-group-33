package com.yourgame.model.enums;

public enum SecurityQuestion {
    PET_QUESTION("What is your favorite pet?"),
    GAME_QUESTION("What is your favorite game?"),
    CAR_QUESTION("What is your favorite car?"),
    COLOR_QUESTION("What is your favorite color?");

    final private String question;

    SecurityQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return question;
    }
}
