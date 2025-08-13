package com.yourgame.model.NPC;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PointMapObject;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.MapManager;

import java.util.ArrayList;
import java.util.List;

public class NPCManager {
    private final List<NPC> npcs;

    public NPCManager(MapManager manager) {
        this.npcs = new ArrayList<>();
        spawnNPCs(manager.getTown());
    }

    private void spawnNPCs(Map town) {
        for (NPCType type : NPCType.values()) {
            Schedule schedule = new Schedule();

            try {
                MapObjects objects = town.getTiledMap().getLayers().get(type.name() + "Schedule").getObjects();
                for (MapObject object : objects) {
                    if (object instanceof PointMapObject pointObject) {
                        schedule.add(Integer.parseInt(pointObject.getName()), pointObject.getPoint());
                    }
                }

                type.setSchedule(schedule);
                npcs.add(new NPC(type, schedule.getDestination(600)));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(type.name() + " has no schedule. Skipping...");
            }
        }
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
