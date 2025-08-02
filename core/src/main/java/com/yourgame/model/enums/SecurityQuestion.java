package com.yourgame.model.enums;

public enum SecurityQuestion {
    Location("Where is your home located in?"),
    BirthDate("What is your birth date?"),
    PhoneNumber("What is your phone number?"),
    BornCity("What city were you born in?"),
    FavoriteFood("What is your favorite food?");

    private final String question;

    SecurityQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public static SecurityQuestion getQuestion(String question) {
        for(SecurityQuestion sq : SecurityQuestion.values()) {
            if(sq.getQuestion().equals(question)) {
                return sq;
            }
        }
        return null;
    }
}

