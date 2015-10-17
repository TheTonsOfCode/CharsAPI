package pl.merbio.charsapi.animations;

import pl.merbio.charsapi.objects.CharsBlock;

public class Point {
    public int w;
    public int h;
    public CharsBlock cb;

    public Point(int w, int h, CharsBlock cb){
        this.w = w;
        this.h = h;
        this.cb = cb;
    }
    
    @Override
    public boolean equals(Object obj) {
        Point other = (Point) obj;
        if (w == other.w && h == other.h) return true;
        return false;
    }
}