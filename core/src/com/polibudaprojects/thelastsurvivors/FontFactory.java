package com.polibudaprojects.thelastsurvivors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FontFactory {
    // This static resource could potentially cause problems on Android
    @SuppressWarnings("GDXJavaStaticResource")
    private static final FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/VT323.ttf"));
    private static final Map<SizeAndColor, BitmapFont> fonts = new HashMap<>();

    public static BitmapFont getFont(int size) {
        return getFont(size, Color.WHITE);
    }

    public static BitmapFont getFont(int size, Color color) {
        SizeAndColor sizeAndColor = new SizeAndColor(size, color);
        if (!fonts.containsKey(sizeAndColor)) {
            FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            fontParameter.size = size;
            fontParameter.color = color;
            fonts.put(sizeAndColor, fontGenerator.generateFont(fontParameter));
        }

        return fonts.get(sizeAndColor);
    }

    public static void dispose() {
        fontGenerator.dispose();
        fonts.values().forEach(BitmapFont::dispose);
    }

    private static class SizeAndColor {
        private final int size;
        private final Color color;

        public SizeAndColor(int size, Color color) {
            this.size = size;
            this.color = color;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SizeAndColor that = (SizeAndColor) o;
            return size == that.size && Objects.equals(color, that.color);
        }

        @Override
        public int hashCode() {
            return Objects.hash(size, color);
        }
    }
}
