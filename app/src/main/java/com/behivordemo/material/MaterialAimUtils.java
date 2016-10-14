package com.behivordemo.material;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;

/**
 * Android L 转场动画工具类,
 *
 * Created by user on 16/10/14.
 */

public class MaterialAimUtils {
    private int animDuration;
    private int slideGrivaty;
    private Visibility materialVisibility;

    private MaterialAimUtils(Builder builder) {
        this.animDuration = builder.animDuration;
        MaterialAimType materialAimType = builder.materialAimType;
        this.slideGrivaty = builder.slideGrivaty;
        switch (materialAimType) {
            case SLIDE:
                materialVisibility = buildMySlideInstance();
                break;
            case FADE:
                materialVisibility = buildMyFadeInstance();
                break;
            case EXPLODE:
                materialVisibility = buildMyExplodeInstance();
                break;
        }
    }

    public static class Builder {
        private int animDuration;
        private MaterialAimType materialAimType;
        private int slideGrivaty;

        public Builder animDuration(int animDuration) {
            this.animDuration = animDuration;
            return this;
        }

        public Builder materialAimType(MaterialAimType materialAimType) {
            this.materialAimType = materialAimType;
            return this;
        }

        public Builder slideGrivaty(int slideGrivaty) {
            this.slideGrivaty = slideGrivaty;
            return this;
        }

        public MaterialAimUtils build() {
            return new MaterialAimUtils(this);
        }
    }

    /**
     * 枚举定义构建的专场动画类型
     */
    public enum MaterialAimType {
        SLIDE(),
        FADE(),
        EXPLODE();

        MaterialAimType() {
        }
    }

    private Slide buildMySlideInstance() {
        Slide slide = new Slide();
        slide.setDuration(animDuration);
        slide.setSlideEdge(slideGrivaty);
        return slide;
    }

    private Fade buildMyFadeInstance() {
        Fade fade = new Fade();
        fade.setDuration(animDuration);
        return fade;
    }

    private Explode buildMyExplodeInstance() {
        Explode explode = new Explode();
        explode.setDuration(animDuration);
        return explode;
    }

    /**
     * 专场动画跳转时候的工具方法
     *
     * @param currentActivity 当前activity
     * @param targetActivity  目标跳转的activity
     */
    public static void startActivityWithMaterialAim(Activity currentActivity, Class targetActivity) {
        Intent intent = new Intent(currentActivity, targetActivity);
        currentActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(currentActivity).toBundle());
    }

    /**
     * 给activity添加出去的动画
     *
     * @param activity 当前界面
     */
    public void setEixtMaterial(Activity activity) {
        activity.getWindow().setExitTransition(materialVisibility);

    }

    /**
     * 给activity应用进入的动画
     *
     * @param activity 当前界面
     */
    public void setEnterMaterial(Activity activity) {
        activity.getWindow().setEnterTransition(materialVisibility);
    }

}
