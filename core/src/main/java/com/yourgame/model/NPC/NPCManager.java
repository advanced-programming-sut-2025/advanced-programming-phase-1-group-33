package com.yourgame.model.NPC;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yourgame.model.Map.Map;

import java.util.ArrayList;
import java.util.List;

public class NPCManager {
    private final List<NPC> npcs;

    public NPCManager() {
        this.npcs = new ArrayList<>();
        spawnNPCs();
    }

    private void spawnNPCs() {
        // Create instances of your five NPCs at their starting locations
        npcs.add(new NPC(NPCType.Pierre, 500, 500)); // Example coordinates
        npcs.add(new NPC(NPCType.Sebastian, 600, 600));
        npcs.add(new NPC(NPCType.Harvey, 700, 700));
        npcs.add(new NPC(NPCType.Leah, 800, 800));
        npcs.add(new NPC(NPCType.Robin, 900, 900));
    }

    public void update(float delta, Map map) {
        for (NPC npc : npcs) {
            npc.update(delta, map);
        }
    }

    public void render(SpriteBatch batch) {
        for (NPC npc : npcs) {
            batch.draw(npc.getTextureFrame(), npc.position.x, npc.position.y);
        }
    }

    public NPC getNpcAt(float worldX, float worldY) {
        for (NPC npc : npcs) {
            // Simple bounding box check
            if (worldX >= npc.position.x && worldX <= npc.position.x + 16 &&
                worldY >= npc.position.y && worldY <= npc.position.y + 32) {
                return npc;
            }
        }
        return null;
    }
}
