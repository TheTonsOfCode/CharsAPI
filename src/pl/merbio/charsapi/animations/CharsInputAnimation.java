package pl.merbio.charsapi.animations;

import com.google.common.collect.Maps;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.merbio.charsapi.animations.in.AirToDown;
import pl.merbio.charsapi.animations.in.AirToRight;
import pl.merbio.charsapi.animations.in.ByColors;
import pl.merbio.charsapi.animations.in.ChessToDown;
import pl.merbio.charsapi.animations.in.ChessToRight;
import pl.merbio.charsapi.animations.in.DropColors;
import pl.merbio.charsapi.animations.in.FillToCenter;
import pl.merbio.charsapi.animations.in.FillToRight;
import pl.merbio.charsapi.animations.in.HangingsClose;
import pl.merbio.charsapi.animations.in.NoAirToDown;
import pl.merbio.charsapi.animations.in.NoAirToRight;
import pl.merbio.charsapi.animations.in.Randomize;
import pl.merbio.charsapi.animations.in.HangingsHeight;
import pl.merbio.charsapi.animations.in.HangingsOpen;
import pl.merbio.charsapi.animations.in.HangingsWidth;
import pl.merbio.charsapi.animations.in.Matrix_Film;
import pl.merbio.charsapi.animations.in.Snake;
import pl.merbio.charsapi.animations.in.StarFire;
import pl.merbio.charsapi.other.Lang;

public enum CharsInputAnimation {

    AIR_TO_RIGHT(AirToRight.class, Lang.IN_A_DESC_AIR_TO_RIGHT),
    AIR_TO_DOWN(AirToDown.class, Lang.IN_A_DESC_AIR_TO_DOWN),
    NOAIR_TO_DOWN(NoAirToDown.class, Lang.IN_A_DESC_NOAIR_TO_DOWN),
    NOAIR_TO_RIGHT(NoAirToRight.class, Lang.IN_A_DESC_NOAIR_TO_RIGHT),
    HANGINGS_HEIGHT(HangingsHeight.class, Lang.IN_A_DESC_HANGINGS_HEIGHT),
    HANGINGS_WIDTH(HangingsWidth.class, Lang.IN_A_DESC_HANGINGS_WIDTH),
    RANDOMIZE(Randomize.class, Lang.IN_A_DESC_RANDOMIZE),
    HANGINGS_OPEN(HangingsOpen.class, Lang.IN_A_DESC_HANGINGS_OPEN),
    HANGINGS_CLOSE(HangingsClose.class, Lang.IN_A_DESC_HANGINGS_CLOSE),
    CHESS_TO_RIGHT(ChessToRight.class, Lang.IN_A_DESC_CHESS_TO_RIGHT),
    CHESS_TO_DOWN(ChessToDown.class, Lang.IN_A_DESC_CHESS_TO_DOWN),
    MATRIX_FILM(Matrix_Film.class, Lang.IN_A_DESC_MATRIX_FILM),
    SNAKE(Snake.class, Lang.IN_A_DESC_SNAKE),
    BY_COLORS(ByColors.class, Lang.IN_A_DESC_BY_COLORS),
    DROP_COLORS(DropColors.class, Lang.IN_A_DESC_DROP_COLORS),
    STARFIRE(StarFire.class, Lang.IN_A_DESC_STARFIRE),
    FILL_TO_RIGHT(FillToRight.class, Lang.IN_A_DESC_FILL_TO_RIGHT),
    FILL_TO_CENTER(FillToCenter.class, Lang.IN_A_DESC_FILL_TO_CENTER);

    private final Constructor<? extends InputAnimation> animation;
    private String description;

    private CharsInputAnimation(Class<? extends InputAnimation> animation, String description) {
        try {
            this.animation = animation.getConstructor(new Class[]{});
        } catch (NoSuchMethodException ex) {
            throw new AssertionError(ex);
        } catch (SecurityException ex) {
            throw new AssertionError(ex);
        }
        synchronized (description) {
            this.description = description;
        }
    }

    public InputAnimation getAnimation() {
        try {
            return (InputAnimation) this.animation.newInstance(new Object[]{});
        } catch (InstantiationException ex) {
            Throwable t = ex.getCause();
            if ((t instanceof RuntimeException)) {
                throw ((RuntimeException) t);
            }
            if ((t instanceof Error)) {
                throw ((Error) t);
            }
            throw new AssertionError(t);
        } catch (Throwable t) {
            throw new AssertionError(t);
        }
    }

    public String getDescription() {
        return description;
    }

    private static final Map<String, CharsInputAnimation> BY_SHORTNAME;

    public static CharsInputAnimation getByShortName(String name) {
        return (CharsInputAnimation) BY_SHORTNAME.get(name.toUpperCase());
    }

    public static Map<String, CharsInputAnimation> getShortNameMap() {
        return BY_SHORTNAME;
    }

    static {
        BY_SHORTNAME = Maps.newHashMap();
        String alf = "1234567890abcdefghijklmnopqrstuvwxyz";

        for (CharsInputAnimation ca : values()) {
            Character c = alf.charAt(ca.ordinal());
            BY_SHORTNAME.put(c.toString().toUpperCase(), ca);
        }
    }
}
