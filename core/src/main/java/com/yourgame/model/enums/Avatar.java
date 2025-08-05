package com.yourgame.model.enums;

public enum Avatar {
    Abigail("Abigail"),
    Sam("Sam"),
    Pierre("Pierre"),
    Robin("Robin"),
    Harvey("Harvey");

    private final String name;

    Avatar(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static Avatar fromString(String name){
        for(Avatar avatar : Avatar.values()){
            if(avatar.getName().equals(name)){
                return avatar;
            }
        }
        return null;
    }
}
