package com.huanhailiuxin.jet2020.othertest.shadow;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.huanhailiuxin.jet2020.othertest.shadow.ShadowPosition.BOTTOM;
import static com.huanhailiuxin.jet2020.othertest.shadow.ShadowPosition.END;
import static com.huanhailiuxin.jet2020.othertest.shadow.ShadowPosition.START;
import static com.huanhailiuxin.jet2020.othertest.shadow.ShadowPosition.TOP;

@IntDef({
        START,
        TOP,
        END,
        BOTTOM
})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@interface ShadowPosition {
    int START = 1;
    int TOP = 2;
    int END = 3;
    int BOTTOM = 4;
}
