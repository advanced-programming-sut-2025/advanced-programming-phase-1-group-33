package com.yourgame.view.GameViews;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.NPC.NPC;
import com.yourgame.view.AppViews.GameScreen;

public class DialogueView extends Window {
    private final Skin skin;
    private final Label dialogueLabel;
    private final String fullText;
    private float charTimer;
    private int charIndex;
    private static final float CHAR_TYPE_SPEED = 0.03f; // Seconds per character

    public DialogueView(NPC npc, String dialogueText, GameScreen gameScreen) {
        super("", MenuAssetManager.getInstance().getSkin(3));
        this.skin = MenuAssetManager.getInstance().getSkin(3);

        this.fullText = dialogueText;
        this.charIndex = 0;
        this.charTimer = 0;

        // --- Layout ---
        // The main table that holds the dialogue text and the portrait
        Table contentTable = new Table();

        // 1. The dialogue text label
        dialogueLabel = new Label("", skin);
        dialogueLabel.setWrap(true); // Allow text to wrap to the next line
        dialogueLabel.setAlignment(Align.topLeft);

        // 2. The NPC portrait image
        String portraitPath = "Game/NPC/" + npc.getType().name() + "_Portrait.png";
        Image portraitImage = new Image(GameAssetManager.getInstance().getTexture(portraitPath));

        // Add the elements to the table
        contentTable.add(dialogueLabel).width(400).pad(20); // Give the text a fixed width
        contentTable.add(portraitImage).size(128, 128).pad(20); // Set the portrait size

        this.add(contentTable);
        this.pack(); // Size the window to fit the content

        // Add a listener to the whole window to skip or close it
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // If the text is still typing, skip to the end
                if (charIndex < fullText.length()) {
                    charIndex = fullText.length();
                    dialogueLabel.setText(fullText);
                } else {
                    // If the text is finished, close the window
                    gameScreen.closeDialogue();
                }
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // --- Typing Effect Logic ---
        if (charIndex < fullText.length()) {
            charTimer += delta;
            if (charTimer >= CHAR_TYPE_SPEED) {
                charTimer = 0;
                charIndex++;
                dialogueLabel.setText(fullText.substring(0, charIndex));
            }
        }
    }
}

