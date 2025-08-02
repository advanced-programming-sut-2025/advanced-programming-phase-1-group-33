package com.yourgame.model.enums;

public enum Gender {
    Male("Male"),
    Female("Female");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGenderString() {
        return gender;
    }

    public Gender getGenderEnum(String string) {
        for(Gender gender : Gender.values()) {
            if(gender.getGenderString().equals(string)) {
                return gender;
            }
        }
        return null;
    }
}
