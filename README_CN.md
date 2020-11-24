# RoundShadowImageView
RoundShadowImageView 是1个为圆形图片的ImageView添加阴影的自定义控件.

## 使用步骤:
### 步骤1:
将源码拷贝至你的项目.
### 步骤2:
在布局文件中声明,或者直接通过java代码创建RoundShadowImageView实例.
### 步骤3:
在xml中直接设置其阴影相关属性,或通过java方法进行设置.

## 示例:
![](https://github.com/HuanHaiLiuXin/RoundShadowImageView/blob/main/RoundShadowImageView.gif)

## 自定义属性说明
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="RoundShadowImageView">
        <!--阴影宽度相对于内容区域半径的比例-->
        <attr name="shadowRatio" format="float" />
        <!--阴影中心相对于内容区域中心的角度,以内容区域垂直向下为0度/起始角度-->
        <attr name="shadowCircleAngle" format="float" />
        <!--阴影颜色-->
        <attr name="shadowColor" format="color|reference" />
        <!--阴影颜色初始透明度-->
        <attr name="shadowStartAlpha" format="float" />
        <!--阴影位置-->
        <attr name="shadowPosition" format="enum">
            <enum name="start" value="1" />
            <enum name="top" value="2" />
            <enum name="end" value="3" />
            <enum name="bottom" value="4" />
        </attr>
    </declare-styleable>
</resources>
```

## 参考文章
- [问题0011 - Android 阴影 轮廓 Outline](https://juejin.cn/post/6896723169705459719)



## Author
- [GitHub 幻海流心](https://github.com/HuanHaiLiuXin)
- [掘金 幻海流心](https://juejin.cn/user/3773179634913038)


License
=======

    Copyright 2020 HuanHaiLiuXin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

