###Android应用中悬停效果
如下


----------

![这里写图片描述](http://img.blog.csdn.net/20161009184102103)


----------
###如果我们自己用之前的方法是实现的话,需要计算要移动view的距离,现在Android给我们提供了新的属性`app:layout_behavior`我们可以轻松实现GIF中的效果,一句代码都不用写是不是很爽,



-------------
###XML中的代码如下

```
// 最外层用CoordinatorLayout
<android.support.design.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.behivordemo.MainActivity">

    <android.support.design.widget.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:fitsSystemWindows="true"
        android:theme="@style/AppTheme.AppBarOverlay">
// 注意toolbar中的scrollFlags 一定要有scroll属性,view才能滑动到屏幕外
        <android.support.v7.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:background="?attr/colorPrimary"
            app:layout_scrollFlags="scroll|enterAlways"
            app:popupTheme="@style/AppTheme.PopupOverlay" />

        <android.support.design.widget.TabLayout
            android:id="@+id/tab_temp"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            app:tabIndicatorColor="@android:color/white">

        </android.support.design.widget.TabLayout>
    </android.support.design.widget.AppBarLayout>

// 滑动的view需要加入app:layout_behavior属性
    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv_test"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">

    </android.support.v7.widget.RecyclerView>

    <android.support.design.widget.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="@dimen/fab_margin"
        app:backgroundTint="@android:color/holo_purple"
        app:layout_behavior="@string/scroll_aware_fab"
        app:layout_scrollFlags="scroll|enterAlways|snap"
        app:rippleColor="@android:color/holo_green_light"
        app:srcCompat="@android:drawable/ic_dialog_email" />

</android.support.design.widget.CoordinatorLayout>

```

###OK结束了,把布局copy到项目了,在activity中模拟点假数据就能可以了

----------

源代码下载地址:[https://github.com/GuoFeilong/BehivorDemo](https://github.com/GuoFeilong/BehivorDemo)

#希望大家多多star,谢谢
