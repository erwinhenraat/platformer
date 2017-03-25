package com.erwinhenraat.platformer;

/**
 * Created by e.henraat on 23-3-2017.
 */
public enum AnimationEnum {
    IDLE(0),
    WALK(1),
    JUMP(2),
    DIE(3);

    private final int index;
    AnimationEnum(int index){
        this.index = index;
    }
    public int index(){
        return index;
    }

}
