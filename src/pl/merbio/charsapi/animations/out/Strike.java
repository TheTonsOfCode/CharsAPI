package pl.merbio.charsapi.animations.out;

import java.util.ArrayList;
import java.util.Random;
import pl.merbio.charsapi.animations.OutputAnimation;
import pl.merbio.charsapi.animations.Point;
import pl.merbio.charsapi.objects.CharsBlock;
import pl.merbio.charsapi.objects.CharsString;

/**
 * @author Merbio
 */
public class Strike extends OutputAnimation {

    private ArrayList<Point> list;
    private int iterator;
    private Type t;

    public Strike() {
        super(1L);
    }

    @Override
    protected void onPrepare() {
        iterator = 0;
        list = new ArrayList<>();

        t = Type.a(new Random().nextInt(Type.values().length));

        for (int h = 0; h < cs.getHeight(); h++) {
            t = Type.a(t.ordinal());
            A a = new A(t, cs);

            for (int w = a.a; t.a(a, w); w += a.c) {
                CharsBlock cb = cs.getCharsBlock(w, h);
                if (cb != null) {
                    list.add(new Point(w, h, cb));
                }
            }
        }
    }

    @Override
    protected void onCancel() {

    }

    @Override
    public void run() {
        if (iterator >= list.size()) {
            stopTask();
            return;
        }

        Point p = list.get(iterator);
        setBeforeBlock(p.w, p.h);
            
        iterator++;
    }

    private enum Type {

        TO_RIGHT {
                    @Override
                    public boolean a(A a, int w) {
                        return w < a.b;
                    }
                },
        TO_LEFT {
                    @Override
                    public boolean a(A a, int w) {
                        return w > a.b;
                    }
                };

        public abstract boolean a(A a, int w);

        public static Type a(int a) {
            if (a == 0) {
                return Type.TO_LEFT;
            }
            return TO_RIGHT;
        }
    }

    private class A {

        public int a;
        public int b;
        public int c;
        
        public Type t;
        public CharsString cs;

        public A(Type t, CharsString cs) {
            this.t = t;
            this.cs = cs;
            if (t == Type.TO_RIGHT) {
                a = 0;
                b = cs.getWidth();
                c = 1;
            } else if (t == Type.TO_LEFT) {
                a = cs.getWidth() - 1;
                b = -1;
                c = -1;
            }
        }
    }

}
