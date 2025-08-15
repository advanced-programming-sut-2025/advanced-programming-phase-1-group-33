package com.yourgame.model.ManuFactor.Artisan;

import java.util.ArrayList;

public class ArtisanMachineManager {
    private static ArtisanMachineManager instance;
    public static ArtisanMachineManager getInstance() {
        if (instance == null) {
            instance = new ArtisanMachineManager();
            instance.loadMachines();
        }
        return instance;
    }

    private final ArrayList<ArtisanMachine> artisanMachines = new ArrayList<>();

    private void loadMachines() {
        for(ArtisanMachineType artisanMachineType : ArtisanMachineType.values()) {
            artisanMachines.add(new ArtisanMachine(artisanMachineType));
        }
    }

    public ArrayList<ArtisanMachine> getArtisanMachines() {
        return artisanMachines;
    }

    public ArtisanMachine getArtisanMachine(ArtisanMachineType artisanMachineType) {
        for(ArtisanMachine machine : artisanMachines) {
            if (machine.getArtisanMachineType() == artisanMachineType) {
                return machine;
            }
        }
        return null;
    }
}
