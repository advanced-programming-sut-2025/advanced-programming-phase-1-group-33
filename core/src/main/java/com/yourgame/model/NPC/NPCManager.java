package com.yourgame.model.NPC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PointMapObject;
import com.badlogic.gdx.math.Vector3;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.MapManager;
import com.yourgame.model.UserInfo.Player;

import java.util.ArrayList;
import java.util.List;

public class NPCManager {
    private static final float INTERACTION_RADIUS = 32f;

    private final List<NPC> npcs;
    private final Texture dialogueReadyEmote;

    public NPCManager(MapManager manager) {
        this.npcs = new ArrayList<>();
        this.dialogueReadyEmote = GameAssetManager.getInstance().getTexture("Game/NPC/Dialogue_Ready.png");
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

    public void update(float delta, Map map, Player player) {
        for (NPC npc : npcs) {
            npc.update(delta, map);
            float distance = npc.position.dst(player.playerPosition);
            npc.setPlayerInRange(distance < INTERACTION_RADIUS);
        }
    }

    public void render(SpriteBatch batch) {
        for (NPC npc : npcs) {
            batch.draw(npc.getTextureFrame(), npc.position.x, npc.position.y);
            if (npc.isPlayerInRange()) {
                float emoteX = npc.position.x + 1;
                float emoteY = npc.position.y + 32; // Position it above the head
                batch.draw(dialogueReadyEmote, emoteX, emoteY);
            }
        }
    }

    public NPC getNpcAt(float worldX, float worldY) {
        for (NPC npc : npcs) {
            if (worldX >= npc.position.x && worldX <= npc.position.x + 16 &&
                worldY >= npc.position.y && worldY <= npc.position.y + 32) {
                return npc;
            }
        }
        return null;
    }

    public NPC getNpcAt(OrthographicCamera camera) {
        Vector3 worldCoordinates = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return getNpcAt(worldCoordinates.x, worldCoordinates.y);
    }
}
