package pl.merbio.charsapi.animations;

import com.google.common.collect.Maps;
import java.lang.reflect.Constructor;
import java.util.Map;
import pl.merbio.charsapi.animations.out.DropAll;
import pl.merbio.charsapi.animations.out.DropColumns;
import pl.merbio.charsapi.animations.out.DropLines;
import pl.merbio.charsapi.animations.out.Strike;
import pl.merbio.charsapi.animations.out.Throw;
import pl.merbio.charsapi.animations.out.ThrowAll;
import pl.merbio.charsapi.animations.out.Wulcano;
import pl.merbio.charsapi.other.Lang;

/**
 * @author Merbio
 */
public enum CharsOutputAnimation {

    DROP_ALL(DropAll.class, Lang.OUT_A_DESC_DROP_ALL),
    DROP_COLUMNS(DropColumns.class, Lang.OUT_A_DESC_DROP_COLUMNS),
    DROP_LINES(DropLines.class, Lang.OUT_A_DESC_DROP_LINES),
    WULCANO(Wulcano.class, Lang.OUT_A_DESC_WULCANO),
    THROW(Throw.class, Lang.OUT_A_DESC_THROW),
    THROW_ALL(ThrowAll.class, Lang.OUT_A_DESC_THROW_ALL),
    STRIKE(Strike.class, "");

    private final Constructor<? extends OutputAnimation> animation;
    private String description;

    private CharsOutputAnimation(Class<? extends OutputAnimation> animation, String description) {
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

    public OutputAnimation getAnimation() {
        try {
            return (OutputAnimation) this.animation.newInstance(new Object[]{});
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

    private static final Map<String, CharsOutputAnimation> BY_SHORTNAME;

    public static CharsOutputAnimation getByShortName(String name) {
        return (CharsOutputAnimation) BY_SHORTNAME.get(name.toUpperCase());
    }

    public static Map<String, CharsOutputAnimation> getShortNameMap() {
        return BY_SHORTNAME;
    }

    static {
        BY_SHORTNAME = Maps.newHashMap();
        String alf = "1234567890abcdefghijklmnopqrstuvwxyz";

        for (CharsOutputAnimation ca : values()) {
            Character c = alf.charAt(ca.ordinal());
            BY_SHORTNAME.put(c.toString().toUpperCase(), ca);
        }
    }
}
